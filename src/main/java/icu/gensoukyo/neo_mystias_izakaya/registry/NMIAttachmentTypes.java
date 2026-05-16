package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalance;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class NMIAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NeoMystiasIzakaya.MODID);


    public static final DeferredHolder<AttachmentType<?>, AttachmentType<NMIBalance>> BALANCE = ATTACHMENT_TYPES.register(
            "balance",
            () -> AttachmentType.builder(()->NMIBalance.EMPTY).sync(NMIBalance.STREAM_CODEC).serialize(NMIBalance.MAP_CODEC).copyOnDeath().build()
    );
}
