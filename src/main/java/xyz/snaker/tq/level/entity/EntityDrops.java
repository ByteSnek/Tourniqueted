package xyz.snaker.tq.level.entity;

import java.util.Map;
import java.util.function.Supplier;

import xyz.snaker.snakerlib.concurrent.AsyncHashMap;
import xyz.snaker.snakerlib.math.Maths;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.tq.level.entity.mob.Cosmo;
import xyz.snaker.tq.rego.Items;

import net.minecraft.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by SnakerBone on 31/03/2023
 **/
public class EntityDrops
{
    private final RandomSource random = RandomSource.create();
    private final Supplier<Map<EntityVariants.Cosmo, Item>> cosmoDropVariants = () -> Util.make(new AsyncHashMap<>(), map ->
    {
        map.put(EntityVariants.Cosmo.RED, Items.RED_COSMO_SPINE.get());
        map.put(EntityVariants.Cosmo.GREEN, Items.GREEN_COSMO_SPINE.get());
        map.put(EntityVariants.Cosmo.BLUE, Items.BLUE_COSMO_SPINE.get());
        map.put(EntityVariants.Cosmo.YELLOW, Items.YELLOW_COSMO_SPINE.get());
        map.put(EntityVariants.Cosmo.PINK, Items.PINK_COSMO_SPINE.get());
        map.put(EntityVariants.Cosmo.PURPLE, Items.PURPLE_COSMO_SPINE.get());
        map.put(EntityVariants.Cosmo.ALPHA, Items.ALPHA_COSMO_SPINE.get());
    });

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event)
    {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Cosmo) {
            addCosmoDrops(event);
        }

        if (entity instanceof Flutterfly) {
            addDrops(event, Items.FLUTTERFLY_KERATIN);
        }
    }

    @SafeVarargs
    private <T extends ItemLike> void addDrops(LivingDropsEvent event, RegistryObject<T>... items)
    {
        addDrop(event, items[random.nextInt(items.length) % items.length].get());
    }

    private void addDrops(LivingDropsEvent event, ItemLike... items)
    {
        addDrop(event, items[random.nextInt(items.length) % items.length]);
    }

    private void addDrop(LivingDropsEvent event, ItemLike item)
    {
        int looting = (int) Maths.clamp(event.getLootingLevel(), 0, Integer.MAX_VALUE);
        int roll = random.nextInt(100);

        if (roll < getDropAmount(looting) || roll < getDropChance(looting)) {
            addItem(event, item);
        }
    }

    private void addCosmoDrops(LivingDropsEvent event)
    {
        Cosmo cosmo = (Cosmo) event.getEntity();

        switch (cosmo.getVariant()) {
            case RED -> addDrop(event, cosmoDropVariants.get().get(EntityVariants.Cosmo.RED));
            case GREEN -> addDrop(event, cosmoDropVariants.get().get(EntityVariants.Cosmo.GREEN));
            case BLUE -> addDrop(event, cosmoDropVariants.get().get(EntityVariants.Cosmo.BLUE));
            case YELLOW -> addDrop(event, cosmoDropVariants.get().get(EntityVariants.Cosmo.YELLOW));
            case PINK -> addDrop(event, cosmoDropVariants.get().get(EntityVariants.Cosmo.PINK));
            case PURPLE -> addDrop(event, cosmoDropVariants.get().get(EntityVariants.Cosmo.PURPLE));
            case ALPHA -> addDrop(event, cosmoDropVariants.get().get(EntityVariants.Cosmo.ALPHA));
        }
    }

    private void addItem(LivingDropsEvent event, ItemLike item)
    {
        ItemStack stack = item.asItem().getDefaultInstance();
        ItemEntity entity = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), stack);

        event.getDrops().add(entity);
    }

    private int getDropChance(int looting)
    {
        int chance = 50;
        int bonus = 5 * looting;

        return (int) Maths.clamp(chance + bonus, 0, 100);
    }

    private int getDropAmount(int looting)
    {
        int amount = 1;
        int bonus = random.nextInt(looting + 1);

        return amount + bonus;
    }

    public static void initialize()
    {
        MinecraftForge.EVENT_BUS.register(new EntityDrops());
    }
}