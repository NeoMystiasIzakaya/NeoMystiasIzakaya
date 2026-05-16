package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalance;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIAttachmentTypes;
import net.minecraft.world.entity.player.Player;

public class NMIBalanceUtil {

    public static NMIBalance get(Player player){
        return player.getData(NMIAttachmentTypes.BALANCE).copy();
    }

    public static void set(Player player, NMIBalance balance){
        player.setData(NMIAttachmentTypes.BALANCE, balance);
    }

}
