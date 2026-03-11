/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.SquidModel;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.renderer.entity.player.PlayerRenderer;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.animal.Squid;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class EntityRenderers {
/*  20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  22 */   private static final Map<EntityType<?>, EntityRendererProvider<?>> PROVIDERS = (Map<EntityType<?>, EntityRendererProvider<?>>)new Object2ObjectOpenHashMap(); static {
/*  23 */     PLAYER_PROVIDERS = Map.of(PlayerSkin.Model.WIDE, $$0 -> new PlayerRenderer($$0, false), PlayerSkin.Model.SLIM, $$0 -> new PlayerRenderer($$0, true));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  29 */     register(EntityType.ALLAY, AllayRenderer::new);
/*  30 */     register(EntityType.AREA_EFFECT_CLOUD, NoopRenderer::new);
/*  31 */     register(EntityType.ARMOR_STAND, ArmorStandRenderer::new);
/*  32 */     register(EntityType.ARROW, TippableArrowRenderer::new);
/*  33 */     register(EntityType.AXOLOTL, AxolotlRenderer::new);
/*  34 */     register(EntityType.BAT, BatRenderer::new);
/*  35 */     register(EntityType.BEE, BeeRenderer::new);
/*  36 */     register(EntityType.BLAZE, BlazeRenderer::new);
/*  37 */     register(EntityType.BLOCK_DISPLAY, BlockDisplayRenderer::new);
/*  38 */     register(EntityType.BOAT, $$0 -> new BoatRenderer($$0, false));
/*  39 */     register(EntityType.BREEZE, BreezeRenderer::new);
/*  40 */     register(EntityType.CAT, CatRenderer::new);
/*  41 */     register(EntityType.CAMEL, $$0 -> new CamelRenderer($$0, ModelLayers.CAMEL));
/*  42 */     register(EntityType.CAVE_SPIDER, CaveSpiderRenderer::new);
/*  43 */     register(EntityType.CHEST_BOAT, $$0 -> new BoatRenderer($$0, true));
/*  44 */     register(EntityType.CHEST_MINECART, $$0 -> new MinecartRenderer<>($$0, ModelLayers.CHEST_MINECART));
/*  45 */     register(EntityType.CHICKEN, ChickenRenderer::new);
/*  46 */     register(EntityType.COD, CodRenderer::new);
/*  47 */     register(EntityType.COMMAND_BLOCK_MINECART, $$0 -> new MinecartRenderer<>($$0, ModelLayers.COMMAND_BLOCK_MINECART));
/*  48 */     register(EntityType.COW, CowRenderer::new);
/*  49 */     register(EntityType.CREEPER, CreeperRenderer::new);
/*  50 */     register(EntityType.DOLPHIN, DolphinRenderer::new);
/*  51 */     register(EntityType.DONKEY, $$0 -> new ChestedHorseRenderer<>($$0, 0.87F, ModelLayers.DONKEY));
/*  52 */     register(EntityType.DRAGON_FIREBALL, DragonFireballRenderer::new);
/*  53 */     register(EntityType.DROWNED, DrownedRenderer::new);
/*  54 */     register(EntityType.EGG, ThrownItemRenderer::new);
/*  55 */     register(EntityType.ELDER_GUARDIAN, ElderGuardianRenderer::new);
/*  56 */     register(EntityType.ENDERMAN, EndermanRenderer::new);
/*  57 */     register(EntityType.ENDERMITE, EndermiteRenderer::new);
/*  58 */     register(EntityType.ENDER_DRAGON, EnderDragonRenderer::new);
/*  59 */     register(EntityType.ENDER_PEARL, ThrownItemRenderer::new);
/*  60 */     register(EntityType.END_CRYSTAL, EndCrystalRenderer::new);
/*  61 */     register(EntityType.EVOKER, EvokerRenderer::new);
/*  62 */     register(EntityType.EVOKER_FANGS, EvokerFangsRenderer::new);
/*  63 */     register(EntityType.EXPERIENCE_BOTTLE, ThrownItemRenderer::new);
/*  64 */     register(EntityType.EXPERIENCE_ORB, ExperienceOrbRenderer::new);
/*  65 */     register(EntityType.EYE_OF_ENDER, $$0 -> new ThrownItemRenderer<>($$0, 1.0F, true));
/*  66 */     register(EntityType.FALLING_BLOCK, FallingBlockRenderer::new);
/*  67 */     register(EntityType.FIREBALL, $$0 -> new ThrownItemRenderer<>($$0, 3.0F, true));
/*  68 */     register(EntityType.FIREWORK_ROCKET, FireworkEntityRenderer::new);
/*  69 */     register(EntityType.FISHING_BOBBER, FishingHookRenderer::new);
/*  70 */     register(EntityType.FOX, FoxRenderer::new);
/*  71 */     register(EntityType.FROG, FrogRenderer::new);
/*  72 */     register(EntityType.FURNACE_MINECART, $$0 -> new MinecartRenderer<>($$0, ModelLayers.FURNACE_MINECART));
/*  73 */     register(EntityType.GHAST, GhastRenderer::new);
/*  74 */     register(EntityType.GIANT, $$0 -> new GiantMobRenderer($$0, 6.0F));
/*  75 */     register(EntityType.GLOW_ITEM_FRAME, ItemFrameRenderer::new);
/*  76 */     register(EntityType.GLOW_SQUID, $$0 -> new GlowSquidRenderer($$0, new SquidModel($$0.bakeLayer(ModelLayers.GLOW_SQUID))));
/*  77 */     register(EntityType.GOAT, GoatRenderer::new);
/*  78 */     register(EntityType.GUARDIAN, GuardianRenderer::new);
/*  79 */     register(EntityType.HOGLIN, HoglinRenderer::new);
/*  80 */     register(EntityType.HOPPER_MINECART, $$0 -> new MinecartRenderer<>($$0, ModelLayers.HOPPER_MINECART));
/*  81 */     register(EntityType.HORSE, HorseRenderer::new);
/*  82 */     register(EntityType.HUSK, HuskRenderer::new);
/*  83 */     register(EntityType.ILLUSIONER, IllusionerRenderer::new);
/*  84 */     register(EntityType.INTERACTION, NoopRenderer::new);
/*  85 */     register(EntityType.IRON_GOLEM, IronGolemRenderer::new);
/*  86 */     register(EntityType.ITEM, ItemEntityRenderer::new);
/*  87 */     register(EntityType.ITEM_DISPLAY, ItemDisplayRenderer::new);
/*  88 */     register(EntityType.ITEM_FRAME, ItemFrameRenderer::new);
/*  89 */     register(EntityType.LEASH_KNOT, LeashKnotRenderer::new);
/*  90 */     register(EntityType.LIGHTNING_BOLT, LightningBoltRenderer::new);
/*  91 */     register(EntityType.LLAMA, $$0 -> new LlamaRenderer($$0, ModelLayers.LLAMA));
/*  92 */     register(EntityType.LLAMA_SPIT, LlamaSpitRenderer::new);
/*  93 */     register(EntityType.MAGMA_CUBE, MagmaCubeRenderer::new);
/*  94 */     register(EntityType.MARKER, NoopRenderer::new);
/*  95 */     register(EntityType.MINECART, $$0 -> new MinecartRenderer<>($$0, ModelLayers.MINECART));
/*  96 */     register(EntityType.MOOSHROOM, MushroomCowRenderer::new);
/*  97 */     register(EntityType.MULE, $$0 -> new ChestedHorseRenderer<>($$0, 0.92F, ModelLayers.MULE));
/*  98 */     register(EntityType.OCELOT, OcelotRenderer::new);
/*  99 */     register(EntityType.PAINTING, PaintingRenderer::new);
/* 100 */     register(EntityType.PANDA, PandaRenderer::new);
/* 101 */     register(EntityType.PARROT, ParrotRenderer::new);
/* 102 */     register(EntityType.PHANTOM, PhantomRenderer::new);
/* 103 */     register(EntityType.PIG, PigRenderer::new);
/* 104 */     register(EntityType.PIGLIN, $$0 -> new PiglinRenderer($$0, ModelLayers.PIGLIN, ModelLayers.PIGLIN_INNER_ARMOR, ModelLayers.PIGLIN_OUTER_ARMOR, false));
/* 105 */     register(EntityType.PIGLIN_BRUTE, $$0 -> new PiglinRenderer($$0, ModelLayers.PIGLIN_BRUTE, ModelLayers.PIGLIN_BRUTE_INNER_ARMOR, ModelLayers.PIGLIN_BRUTE_OUTER_ARMOR, false));
/* 106 */     register(EntityType.PILLAGER, PillagerRenderer::new);
/* 107 */     register(EntityType.POLAR_BEAR, PolarBearRenderer::new);
/* 108 */     register(EntityType.POTION, ThrownItemRenderer::new);
/* 109 */     register(EntityType.PUFFERFISH, PufferfishRenderer::new);
/* 110 */     register(EntityType.RABBIT, RabbitRenderer::new);
/* 111 */     register(EntityType.RAVAGER, RavagerRenderer::new);
/* 112 */     register(EntityType.SALMON, SalmonRenderer::new);
/* 113 */     register(EntityType.SHEEP, SheepRenderer::new);
/* 114 */     register(EntityType.SHULKER, ShulkerRenderer::new);
/* 115 */     register(EntityType.SHULKER_BULLET, ShulkerBulletRenderer::new);
/* 116 */     register(EntityType.SILVERFISH, SilverfishRenderer::new);
/* 117 */     register(EntityType.SKELETON, SkeletonRenderer::new);
/* 118 */     register(EntityType.SKELETON_HORSE, $$0 -> new UndeadHorseRenderer($$0, ModelLayers.SKELETON_HORSE));
/* 119 */     register(EntityType.SLIME, SlimeRenderer::new);
/* 120 */     register(EntityType.SMALL_FIREBALL, $$0 -> new ThrownItemRenderer<>($$0, 0.75F, true));
/* 121 */     register(EntityType.SNIFFER, SnifferRenderer::new);
/* 122 */     register(EntityType.SNOWBALL, ThrownItemRenderer::new);
/* 123 */     register(EntityType.SNOW_GOLEM, SnowGolemRenderer::new);
/* 124 */     register(EntityType.SPAWNER_MINECART, $$0 -> new MinecartRenderer<>($$0, ModelLayers.SPAWNER_MINECART));
/* 125 */     register(EntityType.SPECTRAL_ARROW, SpectralArrowRenderer::new);
/* 126 */     register(EntityType.SPIDER, SpiderRenderer::new);
/* 127 */     register(EntityType.SQUID, $$0 -> new SquidRenderer<>($$0, new SquidModel($$0.bakeLayer(ModelLayers.SQUID))));
/* 128 */     register(EntityType.STRAY, StrayRenderer::new);
/* 129 */     register(EntityType.STRIDER, StriderRenderer::new);
/* 130 */     register(EntityType.TADPOLE, TadpoleRenderer::new);
/* 131 */     register(EntityType.TEXT_DISPLAY, TextDisplayRenderer::new);
/* 132 */     register(EntityType.TNT, TntRenderer::new);
/* 133 */     register(EntityType.TNT_MINECART, TntMinecartRenderer::new);
/* 134 */     register(EntityType.TRADER_LLAMA, $$0 -> new LlamaRenderer($$0, ModelLayers.TRADER_LLAMA));
/* 135 */     register(EntityType.TRIDENT, ThrownTridentRenderer::new);
/* 136 */     register(EntityType.TROPICAL_FISH, TropicalFishRenderer::new);
/* 137 */     register(EntityType.TURTLE, TurtleRenderer::new);
/* 138 */     register(EntityType.VEX, VexRenderer::new);
/* 139 */     register(EntityType.VILLAGER, VillagerRenderer::new);
/* 140 */     register(EntityType.VINDICATOR, VindicatorRenderer::new);
/* 141 */     register(EntityType.WARDEN, WardenRenderer::new);
/* 142 */     register(EntityType.WANDERING_TRADER, WanderingTraderRenderer::new);
/* 143 */     register(EntityType.WIND_CHARGE, WindChargeRenderer::new);
/* 144 */     register(EntityType.WITCH, WitchRenderer::new);
/* 145 */     register(EntityType.WITHER, WitherBossRenderer::new);
/* 146 */     register(EntityType.WITHER_SKELETON, WitherSkeletonRenderer::new);
/* 147 */     register(EntityType.WITHER_SKULL, WitherSkullRenderer::new);
/* 148 */     register(EntityType.WOLF, WolfRenderer::new);
/* 149 */     register(EntityType.ZOGLIN, ZoglinRenderer::new);
/* 150 */     register(EntityType.ZOMBIE, ZombieRenderer::new);
/* 151 */     register(EntityType.ZOMBIE_HORSE, $$0 -> new UndeadHorseRenderer($$0, ModelLayers.ZOMBIE_HORSE));
/* 152 */     register(EntityType.ZOMBIE_VILLAGER, ZombieVillagerRenderer::new);
/* 153 */     register(EntityType.ZOMBIFIED_PIGLIN, $$0 -> new PiglinRenderer($$0, ModelLayers.ZOMBIFIED_PIGLIN, ModelLayers.ZOMBIFIED_PIGLIN_INNER_ARMOR, ModelLayers.ZOMBIFIED_PIGLIN_OUTER_ARMOR, true));
/*     */   }
/*     */   private static final Map<PlayerSkin.Model, EntityRendererProvider<AbstractClientPlayer>> PLAYER_PROVIDERS;
/*     */   private static <T extends Entity> void register(EntityType<? extends T> $$0, EntityRendererProvider<T> $$1) {
/* 157 */     PROVIDERS.put($$0, $$1);
/*     */   }
/*     */   
/*     */   public static Map<EntityType<?>, EntityRenderer<?>> createEntityRenderers(EntityRendererProvider.Context $$0) {
/* 161 */     ImmutableMap.Builder<EntityType<?>, EntityRenderer<?>> $$1 = ImmutableMap.builder();
/* 162 */     PROVIDERS.forEach(($$2, $$3) -> {
/*     */           try {
/*     */             $$0.put($$2, $$3.create($$1));
/* 165 */           } catch (Exception $$4) {
/*     */             throw new IllegalArgumentException("Failed to create model for " + BuiltInRegistries.ENTITY_TYPE.getKey($$2), $$4);
/*     */           } 
/*     */         });
/* 169 */     return (Map<EntityType<?>, EntityRenderer<?>>)$$1.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public static Map<PlayerSkin.Model, EntityRenderer<? extends Player>> createPlayerRenderers(EntityRendererProvider.Context $$0) {
/* 174 */     ImmutableMap.Builder<PlayerSkin.Model, EntityRenderer<? extends Player>> $$1 = ImmutableMap.builder();
/* 175 */     PLAYER_PROVIDERS.forEach(($$2, $$3) -> {
/*     */           try {
/*     */             $$0.put($$2, $$3.create($$1));
/* 178 */           } catch (Exception $$4) {
/*     */             throw new IllegalArgumentException("Failed to create player model for " + $$2, $$4);
/*     */           } 
/*     */         });
/* 182 */     return (Map<PlayerSkin.Model, EntityRenderer<? extends Player>>)$$1.build();
/*     */   }
/*     */   
/*     */   public static boolean validateRegistrations() {
/* 186 */     boolean $$0 = true;
/* 187 */     for (EntityType<?> $$1 : (Iterable<EntityType<?>>)BuiltInRegistries.ENTITY_TYPE) {
/* 188 */       if ($$1 == EntityType.PLAYER) {
/*     */         continue;
/*     */       }
/* 191 */       if (!PROVIDERS.containsKey($$1)) {
/* 192 */         LOGGER.warn("No renderer registered for {}", BuiltInRegistries.ENTITY_TYPE.getKey($$1));
/* 193 */         $$0 = false;
/*     */       } 
/*     */     } 
/* 196 */     return !$$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EntityRenderers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */