package icu.gensoukyo.neo_mystias_izakaya.common.dal;

import icu.gensoukyo.neo_mystias_izakaya.api.dal.NMIDataAccessor;
import icu.gensoukyo.neo_mystias_izakaya.content.recipe.NMIRecipeMap;
import icu.gensoukyo.neo_mystias_izakaya.content.tag.TagItemListMap;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServerNMIDataAccessor implements NMIDataAccessor {

    public static final ServerNMIDataAccessor INSTANCE = new ServerNMIDataAccessor();

    private TagItemListMap tagItemListMap;

    private NMIRecipeMap recipeMap;
}
