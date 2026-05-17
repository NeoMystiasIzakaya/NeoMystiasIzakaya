package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalance;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.balance.NMIBalanceUnits;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIAttachmentTypes;
import net.minecraft.world.entity.player.Player;

public class NMICommonBalanceUtil {

    public static NMIBalance get(Player player){
        return player.getData(NMIAttachmentTypes.BALANCE).copy();
    }

    public static NMIBalance getWithOutCopy(Player player){
        return player.getData(NMIAttachmentTypes.BALANCE);
    }

    public static void set(Player player, NMIBalance balance){
        player.setData(NMIAttachmentTypes.BALANCE, balance);
    }

    public static int getEn(Player player) {
        return getWithOutCopy(player).getBalanceMap().get(NMIBalanceUnits.EN);
    }

    public static void setEn(Player player, int en) {
        NMIBalance balance = get(player);
        NMIBalance newBalance = balance.setCount(NMIBalanceUnits.EN, en);
        set(player, newBalance);
    }

     public static void addEn(Player player, int en) {
        NMIBalance balance = get(player);
        int currentEn = balance.getBalanceMap().get(NMIBalanceUnits.EN);
        int newEn = currentEn + en;
        NMIBalance newBalance = balance.setCount(NMIBalanceUnits.EN, newEn);
        set(player, newBalance);
    }

}
