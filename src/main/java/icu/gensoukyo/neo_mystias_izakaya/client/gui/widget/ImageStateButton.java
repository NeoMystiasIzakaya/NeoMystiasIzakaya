package icu.gensoukyo.neo_mystias_izakaya.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import static icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya.id;

public class ImageStateButton extends Button {
    private final Identifier onImage = id("textures/gui/recipe_note.png");
    private final Identifier offImage = id("textures/gui/recipe_note_press.png");
    private final Component message;
    @Getter
    @Setter
    private boolean selected;

    public ImageStateButton(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration) {
        super(x, y, width, height, message, onPress, createNarration);
        this.message = message;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor guiGraphicsExtractor, int i, int i1, float v) {
        Identifier image = selected ? this.onImage : this.offImage;
        guiGraphicsExtractor.blit(RenderPipelines.GUI_TEXTURED, image, getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        guiGraphicsExtractor.text(Minecraft.getInstance().font, message, getX() + 15, getY() + 4, 0xFF000000, false);
    }
}
