package xyz.snaker.tq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import xyz.snaker.hiss.keyboard.KeyPair;
import xyz.snaker.hiss.keyboard.Keyboard;
import xyz.snaker.snakerlib.event.EntityAttrCreationManager;
import xyz.snaker.snakerlib.event.EntityLayerDefRegoManager;
import xyz.snaker.snakerlib.event.EntityRendererRegoManager;
import xyz.snaker.snakerlib.event.SpawnPlacementRegoManager;
import xyz.snaker.snakerlib.resources.ResourceReference;
import xyz.snaker.tq.client.model.entity.*;
import xyz.snaker.tq.client.model.item.ComaCrystalSwordModel;
import xyz.snaker.tq.client.model.item.CosmoSpineModel;
import xyz.snaker.tq.client.renderer.block.ShaderBlockRenderer;
import xyz.snaker.tq.client.renderer.entity.*;
import xyz.snaker.tq.client.renderer.type.ItemLikeRenderType;
import xyz.snaker.tq.commands.ConfigCommand;
import xyz.snaker.tq.config.Config;
import xyz.snaker.tq.level.entity.creature.Flutterfly;
import xyz.snaker.tq.level.entity.creature.Frolicker;
import xyz.snaker.tq.level.entity.mob.*;
import xyz.snaker.tq.level.entity.utterfly.Utterfly;
import xyz.snaker.tq.level.item.Tourniquet;
import xyz.snaker.tq.level.levelgen.dimension.Comatose;
import xyz.snaker.tq.rego.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import com.mojang.blaze3d.platform.Window;
import com.mojang.brigadier.CommandDispatcher;

import org.lwjgl.glfw.GLFW;

/**
 * Created by SnakerBone on 2/01/2023
 **/
public class Subscriptions
{
    static final Map<byte[], Boolean> postChainActivity = new ConcurrentHashMap<>();

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client
    {
        @SubscribeEvent
        public static void onLayerDefinitionRego(EntityRenderersEvent.RegisterLayerDefinitions event)
        {
            EntityLayerDefRegoManager manager = new EntityLayerDefRegoManager(event);

            manager.register(SnipeModel.LAYER_LOCATION, SnipeModel::createBodyLayer);
            manager.register(CosmoModel.LAYER_LOCATION, CosmoModel::createBodyLayer);
            manager.register(FlareModel.LAYER_LOCATION, FlareModel::createBodyLayer);
            manager.register(FlutterflyModel.LAYER_LOCATION, FlutterflyModel::createBodyLayer);
            manager.register(CrankyFlutterflyModel.LAYER_LOCATION, CrankyFlutterflyModel::createBodyLayer);
            manager.register(FrolickerModel.LAYER_LOCATION, FrolickerModel::createBodyLayer);
            manager.register(UtterflyModel.LAYER_LOCATION, UtterflyModel::createBodyLayer);
            manager.register(CosmicCreeperModel.LAYER_LOCATION, CosmicCreeperModel::createBodyLayer);
            manager.register(CosmicCreeperiteModel.LAYER_LOCATION, CosmicCreeperiteModel::createBodyLayer);
            manager.register(CosmoSpineModel.LAYER_LOCATION, CosmoSpineModel::createBodyLayer);
            manager.register(ComaCrystalSwordModel.LAYER_LOCATION, ComaCrystalSwordModel::createBodyLayer);
            manager.register(ComaCrystalModel.LAYER_LOCATION, ComaCrystalModel::createBodyLayer);

            manager.close();
        }

        @SubscribeEvent
        @SuppressWarnings({"RedundantSuppression", "unchecked"})
        public static void onRendererRego(EntityRenderersEvent.RegisterRenderers event)
        {
            EntityRendererRegoManager manager = new EntityRendererRegoManager(event);

            manager.registerBlockEntity(BlockEntities.SWIRL, new ShaderBlockRenderer<>(ItemLikeRenderType.SWIRLY));
            manager.registerBlockEntity(BlockEntities.SNOWFLAKE, new ShaderBlockRenderer<>(ItemLikeRenderType.WINTER));
            manager.registerBlockEntity(BlockEntities.WATERCOLOUR, new ShaderBlockRenderer<>(ItemLikeRenderType.WCOLOUR));
            manager.registerBlockEntity(BlockEntities.MULTICOLOUR, new ShaderBlockRenderer<>(ItemLikeRenderType.MCOLOUR));
            manager.registerBlockEntity(BlockEntities.FLAMES, new ShaderBlockRenderer<>(ItemLikeRenderType.FIRE));
            manager.registerBlockEntity(BlockEntities.STARRY, new ShaderBlockRenderer<>(ItemLikeRenderType.BLACK_STARS));
            manager.registerBlockEntity(BlockEntities.GEOMETRIC, new ShaderBlockRenderer<>(ItemLikeRenderType.CLIP));
            manager.registerBlockEntity(BlockEntities.BURNING, new ShaderBlockRenderer<>(ItemLikeRenderType.BURN));
            manager.registerBlockEntity(BlockEntities.FOGGY, new ShaderBlockRenderer<>(ItemLikeRenderType.BLUR_FOG));
            manager.registerBlockEntity(BlockEntities.STATIC, new ShaderBlockRenderer<>(ItemLikeRenderType.STRANDS));

            manager.registerEntity(Entities.COSMO, CosmoRenderer::new);
            manager.registerEntity(Entities.SNIPE, SnipeRenderer::new);
            manager.registerEntity(Entities.FLARE, FlareRenderer::new);
            manager.registerEntity(Entities.COSMIC_CREEPER, CosmicCreeperRenderer::new);
            manager.registerEntity(Entities.COSMIC_CREEPERITE, CosmicCreeperiteRenderer::new);
            manager.registerEntity(Entities.FROLICKER, FrolickerRenderer::new);
            manager.registerEntity(Entities.FLUTTERFLY, FlutterflyRenderer::new);
            manager.registerEntity(Entities.CRANKY_FLUTTERFLY, CrankyFlutterflyRenderer::new);
            manager.registerEntity(Entities.UTTERFLY, UtterflyRenderer::new);
            manager.registerEntity(Entities.HOMMING_ARROW, HommingArrowRenderer::new);
            manager.registerEntity(Entities.EXPLOSIVE_HOMMING_ARROW, ExplosiveHommingArrowRenderer::new);
            manager.registerEntity(Entities.COSMIC_RAY, CosmicRayRenderer::new);
            manager.registerEntity(Entities.COMA_CRYSTAL, ComaCrystalRenderer::new);
            manager.registerEntity(Entities.COMA_CRYSTAL_LIGHTNING_BOLT, ComaCrystalLightningBoltRenderer::new);

            manager.close();
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Common
    {
        @SubscribeEvent
        public static void onEntityAttributeCreation(EntityAttributeCreationEvent event)
        {
            EntityAttrCreationManager manager = new EntityAttrCreationManager(event);

            manager.put(Entities.COSMO, Cosmo.attributes());
            manager.put(Entities.SNIPE, Snipe.attributes());
            manager.put(Entities.FLARE, Flare.attributes());
            manager.put(Entities.COSMIC_CREEPER, CosmicCreeper.attributes());
            manager.put(Entities.COSMIC_CREEPERITE, CosmicCreeperite.attributes());
            manager.put(Entities.FROLICKER, Frolicker.attributes());
            manager.put(Entities.FLUTTERFLY, Flutterfly.attributes());
            manager.put(Entities.CRANKY_FLUTTERFLY, CrankyFlutterfly.attributes());
            manager.put(Entities.UTTERFLY, Utterfly.attributes());

            manager.close();
        }

        @SubscribeEvent
        public static void onSpawnPlacementRego(SpawnPlacementRegisterEvent event)
        {
            SpawnPlacementRegoManager manager = new SpawnPlacementRegoManager(event);

            manager.register(Entities.COSMO, Cosmo::spawnRules);
            manager.register(Entities.FLARE, Flare::spawnRules);
            manager.register(Entities.COSMIC_CREEPER, CosmicCreeper::spawnRules);
            manager.register(Entities.FROLICKER, Frolicker::spawnRules);
            manager.register(Entities.SNIPE, Snipe::spawnRules);
            manager.register(Entities.FLUTTERFLY, Flutterfly::spawnRules);

            manager.close();
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeCommon
    {
        private static long twineTickCount;
        private static byte comaStage;

        @SubscribeEvent
        public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
        {
            Player player = event.player;
            Level level = player.level();
            CompoundTag data = player.getPersistentData();
            RandomSource random = level.getRandom();
            Inventory inventory = player.getInventory();

            ItemStack saturatedTwine = Items.SATURATED_TWINE.get().getDefaultInstance();
            ItemStack weatheredTwine = Items.WEATHERED_TWINE.get().getDefaultInstance();

            if (inventory.contains(saturatedTwine)) {
                twineTickCount++;

                if (twineTickCount >= 720000) {
                    int index = inventory.findSlotMatchingItem(saturatedTwine);
                    ItemStack stack = new ItemStack(weatheredTwine.getItem(), inventory.getItem(index).getCount());
                    inventory.setItem(index, stack);
                    twineTickCount = 0;
                }
            }

            if (level.dimension() == Levels.COMATOSE) {
                if (!data.contains("ComaStage")) {
                    data.putByte("ComaStage", comaStage);
                }

                if (random.nextInt(Config.COMMON.comaStageProgressionOccurrence.get() * 1000) == 0) {
                    comaStage++;
                }

                if (comaStage >= 10) {
                    wakeUpPlayer(player);
                    comaStage = 0;
                }
            }

            if (player instanceof LocalPlayer || level instanceof ClientLevel) {
                Minecraft minecraft = Minecraft.getInstance();
                Window window = minecraft.getWindow();
                long handle = window.getWindow();

                if (KeyPair.SHIFT.apply(handle).sequentialDown() && Keyboard.isKeyDown(handle, GLFW.GLFW_KEY_KP_ENTER)) {
                    if (Config.COMMON.healthRepairKeybindingsActive.get()) {
                        float health = player.getHealth();
                        float maxHealth = player.getMaxHealth();

                        if (health != health) {
                            player.setHealth(maxHealth);
                            player.displayClientMessage(Component.translatable("message.tq.health_repair_success"), true);
                        }
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event)
        {
            Player player = event.getEntity();
            CompoundTag data = player.getPersistentData();

            if (twineTickCount != 0) {
                data.putLong("TwineTickCount", twineTickCount);
            }
        }

        @SubscribeEvent
        public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
        {
            Player player = event.getEntity();
            CompoundTag data = player.getPersistentData();

            if (data.contains("TwineTickCount")) {
                twineTickCount = data.getLong("TwineTickCount");
            }

            if (data.contains("ComaStage")) {
                comaStage = data.getByte("ComaStage");
            }
        }

        @SubscribeEvent
        public static void onCommandRego(RegisterCommandsEvent event)
        {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            ConfigCommand.register(dispatcher);
        }

        static void wakeUpPlayer(Player player)
        {
            Level level = player.level();

            if (player.level().dimension() != Levels.COMATOSE) {
                return;
            }

            if (level instanceof ServerLevel serverLevel) {
                String wakeUpDest = Level.OVERWORLD.location().toString();
                CompoundTag data = player.getPersistentData();
                Function<BlockPos, Comatose.Teleporter> teleporter = Comatose.getTeleporter();

                if (data.contains("PlayerWakeUpDestination")) {
                    wakeUpDest = data.getString("PlayerWakeUpDestination");
                }

                MinecraftServer server = serverLevel.getServer();
                ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(wakeUpDest));
                ServerLevel dest = server.getLevel(key);

                if (dest != null && player.canChangeDimensions()) {
                    player.changeDimension(dest, teleporter.apply(player.getOnPos()));
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Tourniqueted.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ForgeClient
    {
        @SubscribeEvent
        public static void onGuiOverlayRender(RenderGuiOverlayEvent.Post event)
        {
            Minecraft minecraft = Minecraft.getInstance();
            Player player = minecraft.player;
            GuiGraphics graphics = event.getGuiGraphics();
            Window window = event.getWindow();

            if (player != null) {
                int width = window.getWidth();
                int height = window.getHeight();

                if (player.hasEffect(Effects.FLASHBANG.get()) && Config.COMMON.flashBangOverlay.get()) {
                    MobEffectInstance effect = player.getEffect(Effects.FLASHBANG.get());
                    if (effect != null) {
                        float percent = Math.min(effect.getDuration() / 150F, 1F);
                        int alpha = (int) (percent * 255 + 0.5);
                        graphics.fill(RenderType.guiOverlay(), 0, 0, width, height, FastColor.ARGB32.color(alpha, 255, 255, 255));
                    }
                }

                ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                CompoundTag data = player.getPersistentData();

                if (stack.getItem() instanceof Tourniquet tourniquet) {
                    int useDuration = tourniquet.getUseDuration(stack);
                    int remaningUseDuration = tourniquet.getRemainingUseDuration();

                    if (remaningUseDuration != 0) {
                        int alpha = useDuration - remaningUseDuration;

                        if (!player.isUsingItem()) {
                            alpha = 0;
                            graphics.flush();
                        }

                        graphics.fill(RenderType.guiOverlay(), 0, 0, width, height, FastColor.ARGB32.color(alpha, 0, 0, 0));
                    }
                }

                if (player.level().dimension() == Levels.COMATOSE && player.getPersistentData().contains("ComaStage") && Config.CLIENT.showComaStage.get()) {
                    byte stage = data.getByte("ComaStage");
                    String text = "Coma Stage: " + stage + "/10";
                    graphics.drawString(minecraft.fontFilterFishy, text, 5, 5, 0xFFFFFF);
                }
            }
        }

        @SubscribeEvent
        public static void onViewportRender(ViewportEvent.ComputeFov event)
        {
            GameRenderer renderer = event.getRenderer();
            Minecraft minecraft = renderer.getMinecraft();
            Level level = minecraft.level;

            if (level != null) {
                ResourceKey<Level> dimension = level.dimension();

                if (!Config.COMMON.visionConvolveActive.get()) {
                    if (renderer.postEffect != null || renderer.effectActive) {
                        shutdownEffect(minecraft, "vision_convolve");
                    }
                    return;
                }

                if (dimension == Levels.COMATOSE) {
                    if (renderer.postEffect == null || !renderer.effectActive) {
                        loadEffect(minecraft, "vision_convolve");
                    }
                } else {
                    if (renderer.postEffect != null || renderer.effectActive) {
                        shutdownEffect(minecraft, "vision_convolve");
                    }
                }
            }
        }

        private static void shutdownEffect(Minecraft minecraft, String name)
        {
            byte[] nibbles = name.getBytes();

            minecraft.tell(() ->
            {
                minecraft.gameRenderer.shutdownEffect();
                postChainActivity.remove(nibbles);
            });
        }

        private static void loadEffect(Minecraft minecraft, String name)
        {
            byte[] nibbles = name.getBytes();
            ResourceLocation effect = new ResourceReference("shaders/post/" + name + ".json");

            minecraft.tell(() ->
            {
                minecraft.gameRenderer.loadEffect(effect);
                postChainActivity.put(nibbles, true);
            });
        }
    }
}