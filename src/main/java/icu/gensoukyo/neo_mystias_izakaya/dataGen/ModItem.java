package icu.gensoukyo.neo_mystias_izakaya.dataGen;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.ItemRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;

public class ModItem extends ModelProvider {
    public ModItem(PackOutput output) {
        super(output, NeoMystiasIzakaya.MODID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        simpleItem(itemModels, ItemRegistry.CHROME_BALL.get());
    }

    private void simpleItem(ItemModelGenerators itemModels, Item item) {
        itemModels.itemModelOutput.register(
                item, new ClientItem(
                        new CuboidItemModelWrapper.Unbaked(
                                ModelLocationUtils.getModelLocation(item),
                                Optional.empty(),
                                Collections.emptyList()
                        ),
                        new ClientItem.Properties(true, true, 1.0F)
                )
        );
    }
}
