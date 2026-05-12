/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.datagen;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIVanillaTags;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class NMIBlockTagProvider extends BlockTagsProvider {
    public NMIBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, NeoMystiasIzakaya.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.tag(NMIVanillaTags.KITCHENWARE_BLOCK)
                .addOptionalTag(NMIVanillaTags.BOILING_POT)
                .addOptionalTag(NMIVanillaTags.CUTTING_BOARD)
                .addOptionalTag(NMIVanillaTags.FRYING_PAN)
                .addOptionalTag(NMIVanillaTags.GRILL)
                .addOptionalTag(NMIVanillaTags.STEAMER)
        ;

        this.tag(NMIVanillaTags.BOILING_POT).add(NMIBlocks.BOILING_POT.get());
        this.tag(NMIVanillaTags.CUTTING_BOARD).add(NMIBlocks.CUTTING_BOARD.get());
        this.tag(NMIVanillaTags.FRYING_PAN).add(NMIBlocks.FRYING_PAN.get());
        this.tag(NMIVanillaTags.GRILL).add(NMIBlocks.GRILL.get());
        this.tag(NMIVanillaTags.STEAMER).add(NMIBlocks.STEAMER.get());
    }
}
