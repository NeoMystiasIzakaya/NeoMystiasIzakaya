package icu.gensoukyo.neo_mystias_izakaya.datagen.api;

import icu.gensoukyo.neo_mystias_izakaya.NeoMystiasIzakaya;
import icu.gensoukyo.neo_mystias_izakaya.common.util.NMICommonItemUtil;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.NMIEconomy;
import icu.gensoukyo.neo_mystias_izakaya.content.economy.NMIEconomyList;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class EconomyProvider implements DataProvider {

    private final String modid;
    private final PackOutput output;
    private final Map<Identifier, NMIEconomyList> economyListMap = new HashMap<>();
    private final Builder defaultBuilder;

    public EconomyProvider(PackOutput output,String modid) {
        this.modid = modid;
        this.output = output;
        this.defaultBuilder = new Builder(economyListMap, NeoMystiasIzakaya.id("default"));
    }

    protected abstract void addEconomy();

    protected Builder builder(){
        return defaultBuilder;
    }

    protected Builder builder(Identifier id){
        return new Builder(economyListMap, id);
    }

    protected void addEconomy(NMIEconomy... economies){
        defaultBuilder.add(economies);
    }

    protected void addEconomy(Identifier itemId, int price){
        defaultBuilder.add(itemId, price);
    }

    protected void addEconomy(Item item, int price){
        defaultBuilder.add(NMICommonItemUtil.get(item), price);
    }

    protected void addEconomy(DeferredItem<? extends Item> item, int price){
        defaultBuilder.add(item.getId(), price);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        this.addEconomy();
        this.defaultBuilder.build();
        PackOutput.PathProvider provider = output.createPathProvider(PackOutput.Target.DATA_PACK, NeoMystiasIzakaya.path("economy"));
        return DataProvider.saveAll(cachedOutput, NMIEconomyList.CODEC, provider, economyListMap);
    }


    @Override
    public @NonNull String getName() {
        return "neo_mystias_izakaya:economy:" + modid;
    }

    protected static class Builder {
        private Identifier id;
        private List<NMIEconomy> economies = new ArrayList<>();
        private final Map<Identifier, NMIEconomyList> economyListMap;

        protected Builder(Map<Identifier, NMIEconomyList> economyListMap,Identifier id) {
            this.economyListMap = economyListMap;
            this.id = id;
        }

        public Builder add(NMIEconomy... economies) {
            this.economies.addAll(Arrays.asList(economies));
            return this;
        }

        public Builder add(Identifier itemId, int price) {
            return add(new NMIEconomy(itemId, price));
        }

        public void build() {
            if (id == null) {
                throw new IllegalStateException("Economy ID cannot be null");
            }
            economyListMap.put(id, new NMIEconomyList(economies));
        }
    }
}
