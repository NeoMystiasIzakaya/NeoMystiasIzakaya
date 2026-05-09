package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIBeveragesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMICuisinesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonComponentUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMICreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB,NeoMystiasIzakaya.MODID);

    public static final Identifier CUISINES_ID = NeoMystiasIzakaya.id("cuisines");

    public static final DeferredHolder<CreativeModeTab,?> CUISINES = CREATIVE_MODE_TABS.register("cuisines", () ->
            CreativeModeTab.builder()
                    .title(NMICommonComponentUtil.translatableItemGroup(CUISINES_ID))
                    .icon(()->NMICuisinesItems.GRILLED_LAMPREY.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        NMICuisinesItems.ITEMS.getEntries().forEach(
                                item -> output.accept(item.get())
                        );
                    })
                    .build()
    );

    public static final Identifier INGREDIENT_ID = NeoMystiasIzakaya.id("ingredient");

    public static final DeferredHolder<CreativeModeTab,?> INGREDIENT = CREATIVE_MODE_TABS.register("ingredient", () ->
            CreativeModeTab.builder()
                    .title(NMICommonComponentUtil.translatableItemGroup(INGREDIENT_ID))
                    .icon(()->NMIIngredientItems.DEW.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(Items.PUFFERFISH);
                        output.accept(Items.PUMPKIN);
                        output.accept(Items.KELP);
                        output.accept(Items.BROWN_MUSHROOM);
                        output.accept(Items.POTATO);
                        output.accept(Items.PORKCHOP);
                        output.accept(Items.BEEF);
                        output.accept(Items.EGG);
                        output.accept(Items.ICE);
                        output.accept(Items.HONEY_BOTTLE);
                        output.accept(Items.COCOA_BEANS);
                        NMIIngredientItems.ITEMS.getEntries().forEach(
                                item -> output.accept(item.get())
                        );
                    })
                    .build()
    );

    public static final Identifier BEVERAGES_ID = NeoMystiasIzakaya.id("beverages");

    public static final DeferredHolder<CreativeModeTab,?> BEVERAGES = CREATIVE_MODE_TABS.register("beverages", () ->
            CreativeModeTab.builder()
                    .title(NMICommonComponentUtil.translatableItemGroup(BEVERAGES_ID))
                    .icon(()-> NMIBeveragesItems.GREEN_TEA.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        NMIBeveragesItems.ITEMS.getEntries().forEach(
                                item -> output.accept(item.get())
                        );
                    })
                    .build()
    );

    public static final Identifier MAIN_ID = NeoMystiasIzakaya.id("main");

    public static final DeferredHolder<CreativeModeTab,?> MAIN = CREATIVE_MODE_TABS.register("main", () ->
            CreativeModeTab.builder()
                    .title(NMICommonComponentUtil.translatableItemGroup(MAIN_ID))
                    .icon(()-> NMIMainItems.CHROME_BALL.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        NMIMainItems.ITEMS.getEntries().forEach(
                                item -> output.accept(item.get())
                        );
                    })
                    .build()
    );
}
