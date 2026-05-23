package icu.gensoukyo.neo_mystias_izakaya.content.cooking;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking.GetAdditionalItemsEvent;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking.IzakayaCookingEvent;
import icu.gensoukyo.neo_mystias_izakaya.api.event.server.cooking.IzakayaCookingTagEvent;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.AbstractKitchenwareBE;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerRecipeUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipe;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMICuisinesItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public final class IzakayaCookingUtil {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static List<ItemStack> getAdditionalItems(Player player, AbstractKitchenwareBE kitchenwareBE, NMIRecipe recipe, List<ItemStack> inputs){

        NeoForge.EVENT_BUS.post(new GetAdditionalItemsEvent.Pre(player, kitchenwareBE, recipe, inputs));

        List<ItemStack> list = inputs.stream().filter(stack -> recipe.input().stream()
                .noneMatch(ingredient -> ingredient.test(stack))).toList();

        NeoForge.EVENT_BUS.post(new GetAdditionalItemsEvent.Post(player, kitchenwareBE, recipe, inputs, list));

        return list;
    }
    public static List<Identifier> collectTag(Player player, AbstractKitchenwareBE kitchenwareBE, ItemTagList cuisine, List<ItemTagList> additional){
        // 没有冲突，计算两者的 positiveTags 的并集
        List<Identifier> resultPositiveTags = new ArrayList<>(cuisine.positiveTags());
        for (ItemTagList tagList : additional) {
            for (Identifier tag : tagList.positiveTags()) {
                if (!resultPositiveTags.contains(tag)) {
                    resultPositiveTags.add(tag);
                }
            }
        }

        IzakayaCookingTagEvent.Collect post = NeoForge.EVENT_BUS.post(new IzakayaCookingTagEvent.Collect(player, kitchenwareBE, cuisine, additional));

        return post.getResult();
    }

    public static boolean hasConflictTag(Player player, AbstractKitchenwareBE kitchenwareBE, ItemTagList cuisine, List<ItemTagList> additional){
        // 检查 cuisineList 的 negativeTags 与 additionalList 中的 positiveTags 是否有交集
        boolean hasConflict = additional.stream()
                .anyMatch(tagList ->
                        tagList.positiveTags().stream().anyMatch(cuisine.negativeTags()::contains));

        IzakayaCookingTagEvent.Check post = NeoForge.EVENT_BUS.post(new IzakayaCookingTagEvent.Check(player, kitchenwareBE, cuisine, additional, hasConflict));

        return post.isHasConflict();
    }

    public static void spawnDarkMatter(Player player, AbstractKitchenwareBE kitchenwareBE){
        IzakayaCookingEvent.SpawnDarkMatter.Pre post = NeoForge.EVENT_BUS.post(new IzakayaCookingEvent.SpawnDarkMatter.Pre(player, kitchenwareBE));
        if (post.isCanceled()) return;
        spawnResult(player, kitchenwareBE, NMICuisinesItems.DARK_MATTER.toStack());
        NeoForge.EVENT_BUS.post(new IzakayaCookingEvent.SpawnDarkMatter.Post(player, kitchenwareBE));
    }

    public static void spawnResult(Player player, AbstractKitchenwareBE kitchenwareBE, ItemStack result){

        IzakayaCookingEvent.ConsumeIngredients ingredients = NeoForge.EVENT_BUS.post(new IzakayaCookingEvent.ConsumeIngredients(player, kitchenwareBE, NonNullList.copyOf(kitchenwareBE.getItems().subList(0, 4))));
        kitchenwareBE.setIngredients(ingredients.getIngredients());

        IzakayaCookingEvent.SpawnResult.Pre post = NeoForge.EVENT_BUS.post(new IzakayaCookingEvent.SpawnResult.Pre(player, kitchenwareBE, result));
        if (post.isCanceled()) return;
        kitchenwareBE.setTargetItem(post.getResult());
        NeoForge.EVENT_BUS.post(new IzakayaCookingEvent.SpawnResult.Post(player, kitchenwareBE, post.getResult()));
    }

    public static void setCookingTime(Player player, AbstractKitchenwareBE kitchenwareBE, int cookingTime){
        IzakayaCookingEvent.SetCookingTime post = NeoForge.EVENT_BUS.post(new IzakayaCookingEvent.SetCookingTime(player, kitchenwareBE, cookingTime * 20));
        kitchenwareBE.setCookingTime(post.getCookingTimeTick());
        kitchenwareBE.setTotalCookingTime(post.getCookingTotalTimeTick());
        if (kitchenwareBE.getLevel() != null) {
            kitchenwareBE.getLevel().setBlock(kitchenwareBE.getBlockPos(), kitchenwareBE.getBlockState().setValue(BlockStateProperties.LIT, true), Block.UPDATE_CLIENTS);
        }
    }

    public static void processCooking(Player player, Identifier recipe, BlockPos pos){
        if(!player.level().isLoaded(pos)){
            LOGGER.error("Player {} attempted to cook at position {}, but the chunk is not loaded. This may be caused by a desync between the client and server. Ignoring the cooking attempt to prevent potential issues.", player.getName().getString(), pos);
            return;
        }

        BlockEntity blockEntity = player.level().getBlockEntity(pos);
        if (blockEntity instanceof AbstractKitchenwareBE kitchenware) {
            IzakayaCookingEvent.Trigger post = NeoForge.EVENT_BUS.post(new IzakayaCookingEvent.Trigger(player, kitchenware));
            if (post.isCanceled()) return;

            List<NMIRecipeHolder> recipes = NMIServerRecipeUtil.getRecipesByInputAndKitchenware(player,kitchenware.getIngredientItems(), kitchenware.getKitchenwareType().KITCHENWARE_TYPE);

            boolean valid = recipes.stream().anyMatch(holder -> holder.key().equals(recipe));

            if (!valid) return;

            NMIRecipe cuisine = NMIServerRecipeUtil.getRecipe(recipe).recipe();

            List<ItemTagList> additionalList = IzakayaCookingUtil.getAdditionalItems(player, kitchenware, cuisine, kitchenware.getIngredientItems()).stream()
                    .map(NMIServerItemTagUtil::get)
                    .toList();
            ItemStack stack = cuisine.output().create();
            ItemTagList cuisineList = NMIServerItemTagUtil.get(stack);

            boolean hasConflict = IzakayaCookingUtil.hasConflictTag(player, kitchenware, cuisineList, additionalList);

            if (hasConflict) {
                IzakayaCookingUtil.spawnDarkMatter(player, kitchenware);
            } else {
                List<Identifier> resultPositiveTags = IzakayaCookingUtil.collectTag(player, kitchenware, cuisineList, additionalList);
                NMIServerItemTagUtil.set(stack, new ItemTagList(resultPositiveTags, cuisineList.negativeTags()));
                IzakayaCookingUtil.spawnResult(player, kitchenware,stack);
            }

            IzakayaCookingUtil.setCookingTime(player, kitchenware, cuisine.time());
        }
    }
}
