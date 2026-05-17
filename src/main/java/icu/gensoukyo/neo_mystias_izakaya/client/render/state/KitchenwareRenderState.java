package icu.gensoukyo.neo_mystias_izakaya.client.render.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class KitchenwareRenderState extends BlockEntityRenderState {
    public boolean isRender;
    public final ItemStackRenderState targetItemModel = new ItemStackRenderState();
}
