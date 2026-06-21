package icu.gensoukyo.neo_mystias_izakaya.common.blockentity;

import icu.gensoukyo.neo_mystias_izakaya.common.resource.InfiniteItemResourceHandler;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIBlockEntities;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIBeveragesItems;
import icu.gensoukyo.neo_mystias_izakaya.registry.item.NMIIngredientItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreativeCupboardBlockEntity extends CupboardBlockEntity {

    private final InfiniteItemResourceHandler infiniteItemResourceHandler;

    public CreativeCupboardBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(NMIBlockEntities.CREATIVE_CUPBOARD.get(), worldPosition, blockState);

        List<DeferredItem<Item>> itemList = new ArrayList<>();
        itemList.addAll(NMIIngredientItems.ITEM_LIST);
        itemList.addAll(NMIBeveragesItems.ITEM_LIST);
        this.infiniteItemResourceHandler = InfiniteItemResourceHandler.withDeferredItem(itemList);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("blockentity.neo_mystias_izakaya.creative_cupboard");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return NonNullList.create();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
    }

    @Override
    protected @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
    }

    public ResourceHandler<ItemResource> getItemHandler() {
        return infiniteItemResourceHandler;
    }

}
