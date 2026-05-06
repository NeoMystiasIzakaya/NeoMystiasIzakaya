package icu.gensoukyo.neo_mystias_izakaya.api.event;

import com.google.gson.JsonElement;
import lombok.Getter;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.Event;

import java.util.Map;
import java.util.Optional;

public class ModifyItemTagJsonsEvent extends Event {
    private final RegistryOps.RegistryInfoLookup registryInfoLookup;
    @Getter
    private final RegistryOps<JsonElement> ops;
    @Getter
    private final Map<Identifier, JsonElement> recipeJsons;

    public ModifyItemTagJsonsEvent(final RegistryOps<JsonElement> ops, final Map<Identifier, JsonElement> recipeJsons) {
        this.registryInfoLookup = ops.lookupProvider;
        this.ops = ops;
        this.recipeJsons = recipeJsons;
    }

    public <T> Optional<RegistryOps.RegistryInfo<T>> lookup(ResourceKey<? extends Registry<? extends T>> registryKey) {
        return registryInfoLookup.lookup(registryKey);
    }

    public <T> RegistryOps.RegistryInfo<T> lookupOrThrow(ResourceKey<? extends Registry<? extends T>> registryKey) {
        return registryInfoLookup.lookup(registryKey).orElseThrow();
    }
}