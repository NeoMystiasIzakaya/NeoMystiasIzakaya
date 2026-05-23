package icu.gensoukyo.neo_mystias_izakaya.client.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jspecify.annotations.NonNull;

public record NMIIzakayaMenuSyncMessage(IzakayaMenu izakayaMenu)implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<NMIIzakayaMenuSyncMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("izakaya_menu_sync"));

    public static final StreamCodec<ByteBuf,NMIIzakayaMenuSyncMessage> STREAM_CODEC = StreamCodec.composite(
            IzakayaMenu.STREAM_CODEC, NMIIzakayaMenuSyncMessage::izakayaMenu,
            NMIIzakayaMenuSyncMessage::new
    );

    @Override
    public @NonNull Type<? extends NMIIzakayaMenuSyncMessage> type() {
        return TYPE;
    }
}
