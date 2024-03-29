package xyz.snaker.tq.rego;

import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.level.levelgen.preset.ConcreteFlatLevelPreset;
import xyz.snaker.tq.level.levelgen.preset.CryingObsidianFlatLevelPreset;
import xyz.snaker.tq.level.levelgen.preset.IronFlatLevelPreset;
import xyz.snaker.tq.level.levelgen.preset.ObsidianFlatLevelPreset;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;

/**
 * Created by SnakerBone on 18/10/2023
 **/
public class FlatLevelPresets
{
    public static final ResourceKey<FlatLevelGeneratorPreset> CONCRETE = key("concrete");
    public static final ResourceKey<FlatLevelGeneratorPreset> IRON = key("iron");
    public static final ResourceKey<FlatLevelGeneratorPreset> OBSIDIAN = key("obsidian");
    public static final ResourceKey<FlatLevelGeneratorPreset> CRYING_OBSIDIAN = key("crying_obsidian");

    public static void bootstrap(BootstapContext<FlatLevelGeneratorPreset> context)
    {
        context.register(CONCRETE, ConcreteFlatLevelPreset.create(context));
        context.register(IRON, IronFlatLevelPreset.create(context));
        context.register(OBSIDIAN, ObsidianFlatLevelPreset.create(context));
        context.register(CRYING_OBSIDIAN, CryingObsidianFlatLevelPreset.create(context));
    }

    static ResourceKey<FlatLevelGeneratorPreset> key(String name)
    {
        return ResourceKey.create(Registries.FLAT_LEVEL_GENERATOR_PRESET, new ResourceReference(name));
    }
}
