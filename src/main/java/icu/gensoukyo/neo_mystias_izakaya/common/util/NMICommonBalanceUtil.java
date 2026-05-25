/*
 * Copyright 2026 NeoMystiasIzakaya Team
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package icu.gensoukyo.neo_mystias_izakaya.common.util;

import com.mojang.logging.LogUtils;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalance;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalanceEntry;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalanceUnits;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIAttachmentTypes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.slf4j.Logger;


public class NMICommonBalanceUtil {

    public static final Logger LOGGER = LogUtils.getLogger();

    public static NMIBalance get(Player player) {
        return player.getData(NMIAttachmentTypes.BALANCE).copy();
    }

    public static NMIBalance getWithOutCopy(Player player) {
        return player.getData(NMIAttachmentTypes.BALANCE);
    }

    public static void set(Player player, NMIBalance balance) {
        player.setData(NMIAttachmentTypes.BALANCE, balance);
    }

    public static int getEn(Player player) {
        try (Transaction transaction = Transaction.openRoot()) {
            int count = 0;
            NMIBalance balance = getWithOutCopy(player);
            for (int i = 0; i < balance.size(); i++) {
                if (balance.getResource(i).is(NMIBalanceUnits.EN)) {
                    count += balance.getAmountAsInt(i);
                }
            }
            transaction.commit();
            return count;
        } catch (Exception e) {
            LOGGER.trace("Failed to get EN balance for player {}: {}", player.getName().getString(), e.getMessage());
            return 0;
        }
    }

    public static void setEn(Player player, int en) {
        try (Transaction transaction = Transaction.openRoot()) {
            NMIBalance balance = getWithOutCopy(player);
            balance.insert(NMIBalanceUnits.EN_ENTRY, en + getEn(player), transaction);
            transaction.commit();
            set(player, balance);
        } catch (Exception e) {
            LOGGER.trace("Failed to set EN balance for player {}: {}", player.getName().getString(), e.getMessage());
        }
    }

    public static void addEn(Player player, int en) {
        try (Transaction transaction = Transaction.openRoot()) {
            NMIBalance balance = getWithOutCopy(player);
            balance.insert(new NMIBalanceEntry(NMIBalanceUnits.EN, en), en, transaction);
            transaction.commit();
            set(player, balance);
        } catch (Exception e) {
            LOGGER.trace("Failed to add EN balance for player {}: {}", player.getName().getString(), e.getMessage());
        }

    }

}
