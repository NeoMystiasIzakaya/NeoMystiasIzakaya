/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry.item;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.item.CanteenConfigItem;
import icu.gensoukyo.neo_mystias_izakaya.common.item.ChromeBallItem;
import icu.gensoukyo.neo_mystias_izakaya.common.item.MystiaHatItem;
import icu.gensoukyo.neo_mystias_izakaya.common.item.RecipeItem;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NMIMainItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoMystiasIzakaya.MODID);

    public static final DeferredItem<BlockItem> BOILING_POT = ITEMS.registerSimpleBlockItem("cooker/boiling_pot",NMIBlocks.BOILING_POT);
    public static final DeferredItem<BlockItem> GRILL = ITEMS.registerSimpleBlockItem("cooker/grill", NMIBlocks.GRILL);
    public static final DeferredItem<BlockItem> FRYING_PAN = ITEMS.registerSimpleBlockItem("cooker/frying_pan", NMIBlocks.FRYING_PAN);
    public static final DeferredItem<BlockItem> STEAMER = ITEMS.registerSimpleBlockItem("cooker/steamer",NMIBlocks.STEAMER);
    public static final DeferredItem<BlockItem> CUTTING_BOARD = ITEMS.registerSimpleBlockItem("cooker/cutting_board", NMIBlocks.CUTTING_BOARD);
    public static final DeferredItem<Item> MYSTIAS_HAT = ITEMS.registerItem("misc/mystias_hat", MystiaHatItem::new, properties -> properties);
    public static final DeferredItem<BlockItem> CANTEEN = ITEMS.registerSimpleBlockItem("misc/canteen",NMIBlocks.CANTEEN);
    public static final DeferredItem<BlockItem> DINING_TABLE = ITEMS.registerSimpleBlockItem("misc/dining_table",NMIBlocks.DINING_TABLE);
    public static final DeferredItem<Item> CANTEEN_CONFIG = ITEMS.registerItem("misc/canteen_config", CanteenConfigItem::new, properties -> properties);
    public static final DeferredItem<BlockItem> STORE = ITEMS.registerSimpleBlockItem("misc/store", NMIBlocks.STORE);
    public static final DeferredItem<BlockItem> CUPBOARD = ITEMS.registerSimpleBlockItem("misc/cupboard",NMIBlocks.CUPBOARD);
    public static final DeferredItem<BlockItem> CREATIVE_CUPBOARD = ITEMS.registerSimpleBlockItem("misc/creative_cupboard",NMIBlocks.CREATIVE_CUPBOARD);
    public static final DeferredItem<BlockItem> INCUBATOR = ITEMS.registerSimpleBlockItem("misc/incubator",NMIBlocks.INCUBATOR);
    public static final DeferredItem<BlockItem> CREATIVE_INCUBATOR = ITEMS.registerSimpleBlockItem("misc/creative_incubator",NMIBlocks.CREATIVE_INCUBATOR);

    public static final DeferredItem<Item> CHROME_BALL = ITEMS.registerItem("misc/chrome_ball", ChromeBallItem::new, properties -> properties);
    public static final DeferredItem<Item> RECIPE_BOOK = ITEMS.registerItem("misc/recipe_book", RecipeItem::new, properties -> properties);
}
