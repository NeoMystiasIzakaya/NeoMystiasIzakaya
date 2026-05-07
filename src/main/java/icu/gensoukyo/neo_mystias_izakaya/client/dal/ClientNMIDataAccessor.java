package icu.gensoukyo.neo_mystias_izakaya.client.dal;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientNMIDataAccessor implements NMIDataAccessor {

    public static final ClientNMIDataAccessor INSTANCE = new ClientNMIDataAccessor();

    private TagItemListMap tagItemListMap;
}
