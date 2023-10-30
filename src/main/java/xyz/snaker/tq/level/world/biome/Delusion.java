package xyz.snaker.tq.level.world.biome;

import xyz.snaker.tq.rego.Entities;
import xyz.snaker.tq.rego.Sounds;
import xyz.snaker.tq.utility.level.WorldGenStuff;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.*;

/**
 * Created by SnakerBone on 23/08/2023
 **/
public class Delusion
{
    public static Biome create(BootstapContext<Biome> context)
    {
        AmbientParticleSettings particles = new AmbientParticleSettings(ParticleTypes.ASH, WorldGenStuff.PARTICLE_SPAWN_CHANCE);
        AmbientMoodSettings mood = new AmbientMoodSettings(WorldGenStuff.RANDOM_SOUND_FX, 6000, 8, 2);
        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
                .fogColor(0x033c25)
                .waterColor(0x04311f)
                .waterFogColor(0x012416)
                .skyColor(-16777216)
                .foliageColorOverride(0x0a2f20)
                .grassColorOverride(0x0a2f20)
                .ambientParticle(particles)
                .ambientMoodSound(mood)
                .ambientLoopSound(Holder.direct(Sounds.AMBIENT2.get()));

        WorldGenStuff.addDelusiveTree(gen);
        WorldGenStuff.addDefaultPlants(gen);
        WorldGenStuff.addFoggyRubble(gen);
        WorldGenStuff.addDefaultCarvers(gen);
        WorldGenStuff.addDefaultEntitySpawns(spawns);

        WorldGenStuff.addMonsterSpawn(spawns, Entities.COSMIC_CREEPER, 10, 1, 3);
        WorldGenStuff.addMonsterSpawn(spawns, Entities.SNIPE, 12, 1, 2);
        WorldGenStuff.addCreatureSpawn(spawns, Entities.FROLICKER, 8, 1, 3);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0)
                .temperature(0.7F)
                .generationSettings(gen.build())
                .mobSpawnSettings(spawns.build())
                .specialEffects(effects.build())
                .build();
    }
}
