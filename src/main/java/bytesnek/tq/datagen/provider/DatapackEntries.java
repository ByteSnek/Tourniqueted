package bytesnek.tq.datagen.provider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import bytesnek.tq.Tourniqueted;
import bytesnek.tq.rego.Biomes;
import bytesnek.tq.rego.DamageTypes;
import bytesnek.tq.rego.Features;
import bytesnek.tq.rego.FlatLevelPresets;

/**
 * Created by SnakerBone on 30/08/2023
 **/
public class DatapackEntries extends DatapackBuiltinEntriesProvider
{
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, Features::configuredFeatures)
            .add(Registries.PLACED_FEATURE, Features::placedFeatures)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, Features::biomeModifiers)
            .add(Registries.FLAT_LEVEL_GENERATOR_PRESET, FlatLevelPresets::bootstrap)
            .add(Registries.BIOME, Biomes::bootstrap)
            .add(Registries.DAMAGE_TYPE, DamageTypes::bootstrap);

    public DatapackEntries(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries, BUILDER, Set.of(Tourniqueted.MODID));
    }
}