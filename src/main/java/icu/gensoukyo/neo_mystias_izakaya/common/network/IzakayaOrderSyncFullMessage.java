package icu.gensoukyo.neo_mystias_izakaya.common.network;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrderList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record IzakayaOrderSyncFullMessage(IzakayaOrderList list) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<IzakayaOrderSyncFullMessage> TYPE = new CustomPacketPayload.Type<>(NeoMystiasIzakaya.id("izakaya_order_sync_full"));

    public static final StreamCodec<RegistryFriendlyByteBuf, IzakayaOrderSyncFullMessage> STREAM_CODEC = StreamCodec.composite(
            IzakayaOrderList.STREAM_CODEC, IzakayaOrderSyncFullMessage::list,
            IzakayaOrderSyncFullMessage::new
    );

    @Override
    public Type<IzakayaOrderSyncFullMessage> type() {
        return TYPE;
    }
}
