package icu.gensoukyo.neo_mystias_izakaya.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.client.network.ClientPayloadSender;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT)
public class KeyMappingRegister {
    public static final KeyMapping.Category MAID_CATEGORY = new KeyMapping.Category(
            NeoMystiasIzakaya.id("main")
    );
    public static final KeyMapping CANTEEN_INFO_KEY = new KeyMapping("key.neo_mystias_izakaya.canteen_info",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            MAID_CATEGORY
    );

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(MAID_CATEGORY);

        event.register(CANTEEN_INFO_KEY);
    }

    @SubscribeEvent
    public static void onSttChatPress(InputEvent.Key event) {
        if (CANTEEN_INFO_KEY.matches(event.getKeyEvent())) {
            ClientPayloadSender.sendOpenDishServingMessage();
            CANTEEN_INFO_KEY.consumeClick();
        }
    }
}
