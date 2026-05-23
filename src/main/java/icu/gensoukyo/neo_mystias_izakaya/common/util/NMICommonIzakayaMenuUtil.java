package icu.gensoukyo.neo_mystias_izakaya.common.util;

import icu.gensoukyo.neo_mystias_izakaya.content.izakaya.IzakayaMenu;
import icu.gensoukyo.neo_mystias_izakaya.registry.NMIAttachmentTypes;
import net.minecraft.world.entity.player.Player;

public final class NMICommonIzakayaMenuUtil {

    public static IzakayaMenu get(Player player){
        return player.getData(NMIAttachmentTypes.MENU).copy();
    }

    public static void set(Player player, IzakayaMenu menu){
        player.setData(NMIAttachmentTypes.MENU, menu);
    }
}
