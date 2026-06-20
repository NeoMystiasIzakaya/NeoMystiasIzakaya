/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry.item;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.item.CanteenConfigItem;
import icu.gensoukyo.neo_mystias_izakaya.common.item.MystiaHatItem;
import icu.gensoukyo.neo_mystias_izakaya.common.item.RecipeItem;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIMainItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoMystiasIzakaya.MODID);

    public static final DeferredItem<Item> BOILING_POT = ITEMS.registerItem("cooker/boiling_pot", (properties) -> new BlockItem(NMIBlocks.BOILING_POT.get(), properties), properties -> properties);
    public static final DeferredItem<Item> GRILL = ITEMS.registerItem("cooker/grill", (properties) -> new BlockItem(NMIBlocks.GRILL.get(), properties), properties -> properties);
    public static final DeferredItem<Item> FRYING_PAN = ITEMS.registerItem("cooker/frying_pan", (properties) -> new BlockItem(NMIBlocks.FRYING_PAN.get(), properties), properties -> properties);
    public static final DeferredItem<Item> STEAMER = ITEMS.registerItem("cooker/steamer", (properties) -> new BlockItem(NMIBlocks.STEAMER.get(), properties), properties -> properties);
    public static final DeferredItem<Item> CUTTING_BOARD = ITEMS.registerItem("cooker/cutting_board", (properties) -> new BlockItem(NMIBlocks.CUTTING_BOARD.get(), properties), properties -> properties);
    public static final DeferredItem<Item> MYSTIAS_HAT = ITEMS.registerItem("misc/mystias_hat", MystiaHatItem::new, properties -> properties);
    public static final DeferredItem<Item> CANTEEN = ITEMS.registerItem("misc/canteen", (properties) -> new BlockItem(NMIBlocks.CANTEEN.get(), properties), properties -> properties);
    public static final DeferredItem<Item> DINING_TABLE = ITEMS.registerItem("misc/dining_table", (properties) -> new BlockItem(NMIBlocks.DINING_TABLE.get(), properties), properties -> properties);
    public static final DeferredItem<Item> CANTEEN_CONFIG = ITEMS.registerItem("misc/canteen_config", CanteenConfigItem::new, properties -> properties);
    public static final DeferredItem<Item> STORE = ITEMS.registerItem("misc/store", (properties) -> new BlockItem(NMIBlocks.STORE.get(), properties), properties -> properties);
    public static final DeferredItem<Item> CUPBOARD = ITEMS.registerItem("misc/cupboard", (properties) -> new BlockItem(NMIBlocks.CUPBOARD.get(), properties), properties -> properties);

    public static final DeferredItem<Item> CHROME_BALL = ITEMS.registerItem("misc/chrome_ball", Item::new, properties -> properties);
    public static final DeferredItem<Item> RECIPE_BOOK = ITEMS.registerItem("misc/recipe_book", RecipeItem::new, properties -> properties);
}
