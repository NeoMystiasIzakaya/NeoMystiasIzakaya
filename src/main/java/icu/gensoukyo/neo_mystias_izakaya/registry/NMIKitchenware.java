/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.blockentity.*;
import icu.gensoukyo.neo_mystias_izakaya.content.cooking.Kitchenware;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.consts.NMICuisinesTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMainItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public final class NMIKitchenware {

    public static Registry<Kitchenware> REGISTRY;

    public static final ResourceKey<Registry<Kitchenware>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(NeoMystiasIzakaya.id("kitchenware"));

    public static final DeferredRegister<Kitchenware> KITCHENWARE = DeferredRegister.create(REGISTRY_KEY, NeoMystiasIzakaya.MODID);


    public static final DeferredHolder<Kitchenware, Kitchenware> BOILING_POT = KITCHENWARE.register("boiling_pot",
            () -> new Kitchenware(NMIVanillaTags.BOILING_POT, NMIMainItems.BOILING_POT.get(), NMICuisinesTags.Boiling_Pot,NMIBlockEntities.BOILING_POT.get(), BoilingPotBE::new));

    public static final DeferredHolder<Kitchenware, Kitchenware> CUTTING_BOARD = KITCHENWARE.register("cutting_board",
            () -> new Kitchenware(NMIVanillaTags.CUTTING_BOARD, NMIMainItems.CUTTING_BOARD.get(), NMICuisinesTags.Cutting_Board,NMIBlockEntities.CUTTING_BOARD.get(), CuttingBoardBE::new));

    public static final DeferredHolder<Kitchenware, Kitchenware> FRYING_PAN = KITCHENWARE.register("frying_pan",
            () -> new Kitchenware(NMIVanillaTags.FRYING_PAN, NMIMainItems.FRYING_PAN.get(), NMICuisinesTags.Frying_Pan,NMIBlockEntities.FRYING_PAN.get(),  FryingPanBE::new));

    public static final DeferredHolder<Kitchenware, Kitchenware> GRILL = KITCHENWARE.register("grill",
            () -> new Kitchenware(NMIVanillaTags.GRILL, NMIMainItems.GRILL.get(), NMICuisinesTags.Grill,NMIBlockEntities.GRILL.get(),  GrillBE::new));

    public static final DeferredHolder<Kitchenware, Kitchenware> STEAMER = KITCHENWARE.register("steamer",
            () -> new Kitchenware(NMIVanillaTags.STEAMER, NMIMainItems.STEAMER.get(), NMICuisinesTags.Steamer,NMIBlockEntities.STEAMER.get(),  SteamerBE::new));


}
