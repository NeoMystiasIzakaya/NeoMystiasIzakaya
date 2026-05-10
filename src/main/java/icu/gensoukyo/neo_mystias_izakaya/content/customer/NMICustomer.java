/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.content.customer;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.List;

public interface NMICustomer {

    CustomerBudget budget();

    List<Identifier> locations();

    List<Identifier> likes();

    List<Identifier> dislikes();

    List<Identifier> beverage();

    List<Identifier> tagRequests();

    List<Identifier> spellCards();

}
