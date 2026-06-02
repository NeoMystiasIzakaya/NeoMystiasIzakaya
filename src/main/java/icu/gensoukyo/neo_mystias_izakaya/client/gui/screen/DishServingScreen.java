package icu.gensoukyo.neo_mystias_izakaya.client.gui.screen;

import icu.gensoukyo.neo_mystias_izakaya.client.gui.menu.DishServingMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

public class DishServingScreen extends AbstractContainerScreen<DishServingMenu> {
    private static final Identifier BACKGROUND = id("textures/gui/dish_serve.png");
    public DishServingScreen(DishServingMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 230, 219);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, i, j, 0.0F, 0.0F, 256, 256, 256, 256);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
    }
}
