/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.compat.tlm.task;

import com.github.tartaricacid.touhoulittlemaid.entity.ai.brain.task.MaidCheckRateTask;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.init.InitBrains;
import com.google.common.collect.ImmutableMap;
import icu.gensoukyo.neo_mystias_izakaya.api.common.ICupboard;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.CanteenControllerBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.DiningTableBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.KitchenwareBlockEntity;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerItemTagUtil;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMIServerRecipeUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.IzakayaCookingUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrder;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeHolder;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.ItemTagList;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIKitchenware;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIMemoryTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * 女仆根据普客订单备菜：从橱柜取食材 → 放入匹配厨具烹饪 → 完成后放回橱柜。
 */
public class MaidPrepCookTask extends MaidCheckRateTask {

    public MaidPrepCookTask() {
        super(ImmutableMap.of(
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                InitBrains.TARGET_POS.get(), MemoryStatus.VALUE_ABSENT
        ));
        this.setMaxCheckRate(20);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, EntityMaid maid) {
        if (!super.checkExtraStartConditions(level, maid) || !maid.canBrainMoving()) return false;

        CanteenControllerBlockEntity controller = MaidTaskUtil.getController(level, maid);
        if (controller == null) return false;

        // 阶段1：找一张需要上菜的普客餐桌，获取所需 cuisineId
        Identifier cuisineId = findCuisineNeeded(level, controller);
        if (cuisineId == null) return false;

        // 阶段2：查食谱
        NMIRecipeHolder recipe = NMIServerRecipeUtil.getRecipe(cuisineId);
        if (recipe == null) return false;
        TagKey<Block> requiredKitchenware = recipe.recipe().kitchenware();

        // 阶段3：找匹配的厨具
        BlockPos kitchenwarePos = findMatchingKitchenware(level, controller, requiredKitchenware);
        if (kitchenwarePos == null) return false;
        if (!(level.getBlockEntity(kitchenwarePos) instanceof KitchenwareBlockEntity kwBe)) return false;

        // 阶段3a：厨具有完成品 → 取走放入橱柜
        if (!kwBe.getResultItem().isEmpty()) {
            if (MaidTaskUtil.isCloseEnough(maid, kitchenwarePos)) {
                takeResultToCupboard(level, maid, controller, kwBe);
                return false;
            }
            MaidTaskUtil.setNavigateTo(maid, kitchenwarePos);
            this.setNextCheckTickCount(5);
            return false;
        }

        // 阶段3b：正在烹饪 → 等待
        if (kwBe.getBlockState().getValue(BlockStateProperties.LIT)) {
            if (!MaidTaskUtil.isCloseEnough(maid, kitchenwarePos)) {
                MaidTaskUtil.setNavigateTo(maid, kitchenwarePos);
                this.setNextCheckTickCount(5);
            }
            return false;
        }

        // 阶段3c：空闲 → 取食材，开始烹饪
        if (!MaidTaskUtil.isCloseEnough(maid, kitchenwarePos)) {
            MaidTaskUtil.setNavigateTo(maid, kitchenwarePos);
            this.setNextCheckTickCount(5);
            return false;
        }
        startCooking(level, maid, controller, kwBe, recipe, kitchenwarePos);
        return false;
    }

    @Override
    protected void start(ServerLevel level, EntityMaid maid, long gameTimeIn) {
        MaidTaskUtil.clearFetchStartMemories(maid);
    }

    // ==================== 查找 ====================

    /** 遍历餐桌，返回第一张普客订单所需的 cuisineId */
    private static Identifier findCuisineNeeded(ServerLevel level, CanteenControllerBlockEntity controller) {
        for (BlockPos pos : controller.getDiningTableList()) {
            if (level.getBlockEntity(pos) instanceof DiningTableBlockEntity table) {
                if (table.isOccupied() && table.getCuisine().isEmpty()
                        && !table.getCurrentOrder().isRare()) {
                    Identifier cuisine = table.getCurrentOrder().cuisine();
                    if (!cuisine.equals(IzakayaOrder.EMPTY_ORDER)) {
                        return cuisine;
                    }
                }
            }
        }
        return null;
    }

    /** 在厨房用具列表中找到与食谱匹配的厨具 */
    private static BlockPos findMatchingKitchenware(ServerLevel level, CanteenControllerBlockEntity controller,
                                                     TagKey<Block> requiredTag) {
        for (BlockPos pos : controller.getKitchenwareList()) {
            if (level.getBlockEntity(pos) instanceof KitchenwareBlockEntity kwBe) {
                var kw = NMIKitchenware.REGISTRY.getValue(kwBe.getKitchenwareTypeId());
                if (kw != null && kw.blockTagKey().equals(requiredTag)) {
                    return pos;
                }
            }
        }
        return null;
    }

    // ==================== 烹饪 ====================

    private static void startCooking(ServerLevel level, EntityMaid maid, CanteenControllerBlockEntity controller,
                                      KitchenwareBlockEntity kwBe, NMIRecipeHolder recipe, BlockPos kitchenwarePos) {
        if (!kwBe.canStartCooking()) return;

        // 从橱柜收集所需食材
        NonNullList<ItemStack> ingredients = NonNullList.withSize(5, ItemStack.EMPTY);
        int slot = 0;
        for (Ingredient ingredient : recipe.recipe().input()) {
            if (slot >= 5) break;
            ItemStack found = extractIngredientFromCupboards(level, controller, ingredient);
            if (!found.isEmpty()) {
                ingredients.set(slot, found);
            }
            slot++;
        }

        // 检查所有食材齐备
        for (Ingredient ing : recipe.recipe().input()) {
            boolean found = ingredients.stream().anyMatch(ing);
            if (!found) return;
        }

        // 参照 IzakayaCookingUtil.processCooking 的标准流程
        // 女仆只放入食谱食材，无额外物品 → 跳过 additionalItems、conflictTag

        // ① collectTag: 合并菜品 positiveTags + 厨具 tag（无 additional，直接手动合并）
        ItemStack output = recipe.recipe().output().create();
        ItemTagList cuisineTags = NMIServerItemTagUtil.get(output);
        List<Identifier> resultPositiveTags = new ArrayList<>(cuisineTags.positiveTags());
        resultPositiveTags.add(NMIKitchenware.REGISTRY.getValue(kwBe.getKitchenwareTypeId()).kitchenwareTag());
        NMIServerItemTagUtil.set(output, new ItemTagList(resultPositiveTags, cuisineTags.negativeTags()));

        // 放入完整食材（spawnResult 会读取并消耗）
        kwBe.setIngredients(ingredients);

        // ② spawnResult: 消耗食材 + 设置目标产物
        IzakayaCookingUtil.spawnResult(maid, kwBe, output);

        // ③ setCookingTime: 设置烹饪时间并点火
        IzakayaCookingUtil.setCookingTime(maid, kwBe, recipe.recipe().time());
    }

    // ==================== 取物 ====================

    private static ItemStack extractIngredientFromCupboards(ServerLevel level, CanteenControllerBlockEntity controller,
                                                             Ingredient ingredient) {
        for (BlockPos cupboardPos : controller.getCupboardList()) {
            if (level.getBlockEntity(cupboardPos) instanceof ICupboard cupboard) {
                var handler = cupboard.getItemHandler();
                for (int i = 0; i < handler.size(); i++) {
                    var resource = handler.getResource(i);
                    if (ingredient.test(handler.getResource(i).getItem().getDefaultInstance())) {
                        Transaction tx = Transaction.openRoot();
                        handler.extract(i, resource, 1, tx);
                        tx.commit();
                        return resource.toStack().copyWithCount(1);
                    }
                }
            }
        }
        return ItemStack.EMPTY;
    }

    /** 从厨具取走成品放入橱柜 */
    private static void takeResultToCupboard(ServerLevel level, EntityMaid maid,
                                              CanteenControllerBlockEntity controller,
                                              KitchenwareBlockEntity kwBe) {
        ItemStack result = kwBe.getResultItem();
        if (result.isEmpty()) return;

        // 找到第一个可以放入该物品的橱柜
        Transaction tx = Transaction.openRoot();
        for (BlockPos cupboardPos : controller.getCupboardList()) {
            if (level.getBlockEntity(cupboardPos) instanceof ICupboard cupboard) {
                var handler = cupboard.getItemHandler();
                var resource = net.neoforged.neoforge.transfer.item.ItemResource.of(result.copy());
                long inserted = handler.insert(resource, 1, tx);
                if (inserted > 0) {
                    tx.commit();
                    kwBe.setResultItem(ItemStack.EMPTY);
                    kwBe.setChanged();
                    return;
                }
            }
        }
    }
}
