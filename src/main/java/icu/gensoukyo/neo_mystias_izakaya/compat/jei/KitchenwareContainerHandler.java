package icu.gensoukyo.neo_mystias_izakaya.compat.jei;

import com.google.common.collect.ImmutableList;
import icu.gensoukyo.neo_mystias_izakaya.client.gui.screen.KitchenwareScreen;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;

import java.util.List;

public class KitchenwareContainerHandler implements IGuiContainerHandler<KitchenwareScreen> {
    @Override
    public List<Rect2i> getGuiExtraAreas(KitchenwareScreen containerScreen) {
        // 在 GUI 右侧增加 25px 额外区域
        int x = containerScreen.getLeftPos() + containerScreen.getImageWidth();
        int y = containerScreen.getTopPos();
        return ImmutableList.of(new Rect2i(x, y, 25, containerScreen.getImageHeight()));
    }
}
