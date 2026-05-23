/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.registry;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalance;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaMenu;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrderList;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class NMIAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NeoMystiasIzakaya.MODID);


    public static final DeferredHolder<AttachmentType<?>, AttachmentType<NMIBalance>> BALANCE = ATTACHMENT_TYPES.register(
            "balance",
            () -> AttachmentType.builder(() -> NMIBalance.EMPTY).sync(NMIBalance.STREAM_CODEC).serialize(NMIBalance.MAP_CODEC).copyOnDeath().build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<IzakayaMenu>> MENU = ATTACHMENT_TYPES.register(
            "menu",
            () -> AttachmentType.builder(()->IzakayaMenu.EMPTY).sync(IzakayaMenu.STREAM_CODEC).serialize(IzakayaMenu.MAP_CODEC).copyOnDeath().build()
    );

    // 点单手动同步，减小网络包
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<IzakayaOrderList>> ORDER = ATTACHMENT_TYPES.register(
            "order",
            () -> AttachmentType.builder(()-> IzakayaOrderList.EMPTY).serialize(IzakayaOrderList.MAP_CODEC).copyOnDeath().build()
    );
}
