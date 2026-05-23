package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaMenu;
import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaOrderList;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIAttachmentTypes;
import net.minecraft.world.entity.player.Player;

public final class NMICommonIzakayaUtil {

    public static IzakayaMenu getMenu(Player player){
        return player.getData(NMIAttachmentTypes.MENU).copy();
    }

    public static void setMenu(Player player, IzakayaMenu menu){
        player.setData(NMIAttachmentTypes.MENU, menu);
    }

    public static IzakayaOrderList getOrder(Player player){
        return player.getData(NMIAttachmentTypes.ORDER);
    }

    public static void setOrder(Player player, IzakayaOrderList orderList){
        player.setData(NMIAttachmentTypes.ORDER, orderList);
    }
}
