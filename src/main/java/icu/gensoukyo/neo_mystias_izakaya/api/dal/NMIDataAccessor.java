package icu.gensoukyo.neo_mystias_izakaya.api.dal;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.dal.ServerNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;
import net.neoforged.fml.loading.FMLEnvironment;

public interface NMIDataAccessor {

    static NMIDataAccessor server() {
        return ServerNMIDataAccessor.INSTANCE;
    }

    static NMIDataAccessor client() {
        return ClientNMIDataAccessor.INSTANCE;
    }

    TagItemListMap getTagItemListMap();

    NMIRecipeMap getRecipeMap();
}
