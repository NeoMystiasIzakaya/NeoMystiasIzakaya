package icu.gensoukyo.neo_mystias_izakaya.api.dal;

import icu.gensoukyo.neo_mystias_izakaya.client.dal.ClientNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.common.dal.ServerNMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;

public interface NMIDataAccessor {

    static NMIDataAccessor server(){
        return ServerNMIDataAccessor.INSTANCE;
    }

    static NMIDataAccessor client(){
        return ClientNMIDataAccessor.INSTANCE;
    }

    TagItemListMap getTagItemListMap();
}
