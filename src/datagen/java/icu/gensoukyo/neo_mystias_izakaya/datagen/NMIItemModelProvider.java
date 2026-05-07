package icu.gensoukyo.neo_mystias_izakaya.datagen;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIMiscItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIDrinkItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIFoodItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NMIItemModelProvider extends ModelProvider {
    public NMIItemModelProvider(PackOutput output) {
        super(output, NeoMystiasIzakaya.MODID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        SimpleItemModelRegister simple = new SimpleItemModelRegister(itemModels.itemModelOutput);

        simple.register(NMIMiscItems.CHROME_BALL);

        this.registerBlockModels(blockModels);
        this.registerItemModels(itemModels);
    }

    private void registerBlockModels(BlockModelGenerators blockModels) {

    }

    private void registerItemModels(ItemModelGenerators itemModels) {
        for (List<DeferredItem<Item>> items : List.of(NMIDrinkItems.ITEM_LIST, NMIFoodItems.ITEM_LIST, NMIIngredientItems.ITEM_LIST)) {
            for (DeferredItem<Item> item : items) {
                itemModels.generateFlatItem(item.asItem(), ModelTemplates.FLAT_ITEM);
            }
        }
    }

    public static class SimpleItemModelRegister {
        private final ItemModelOutput itemModelOutput;

        public SimpleItemModelRegister(ItemModelOutput itemModelOutput) {
            this.itemModelOutput = itemModelOutput;
        }

        public void register(Item item) {
            this.itemModelOutput.register(
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

        public void register(DeferredItem<@NotNull Item> item) {
            this.register(item.get());
        }
    }

}
