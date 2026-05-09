package icu.gensoukyo.neo_mystias_izakaya.datagen;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockTags;
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
        this.tag(NMIBlockTags.KITCHENWARE_BLOCK)
                .addOptionalTag(NMIBlockTags.BOILING_POT)
                .addOptionalTag(NMIBlockTags.CUTTING_BOARD)
                .addOptionalTag(NMIBlockTags.FRYING_PAN)
                .addOptionalTag(NMIBlockTags.GRILL)
                .addOptionalTag(NMIBlockTags.STEAMER)
        ;

        this.tag(NMIBlockTags.BOILING_POT).add(NMIBlocks.BOILING_POT.get());
        this.tag(NMIBlockTags.CUTTING_BOARD).add(NMIBlocks.CUTTING_BOARD.get());
        this.tag(NMIBlockTags.FRYING_PAN).add(NMIBlocks.FRYING_PAN.get());
        this.tag(NMIBlockTags.GRILL).add(NMIBlocks.GRILL.get());
        this.tag(NMIBlockTags.STEAMER).add(NMIBlocks.STEAMER.get());
    }
}
