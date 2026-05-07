package icu.gensoukyo.neo_mystias_izakaya.datagen;

import icu.gensoukyo.neo_mystias_izakaya.datagen.api.TagItemListProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

public class NMITagItemListProvider extends TagItemListProvider {
    public NMITagItemListProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void addTags() {
        addTag("test", fromItems(Items.DIAMOND));
    }
}
