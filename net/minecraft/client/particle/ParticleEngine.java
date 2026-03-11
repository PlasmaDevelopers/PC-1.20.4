/*     */ package net.minecraft.client.particle;
/*     */ import com.google.common.collect.EvictingQueue;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.renderer.GameRenderer;
/*     */ import net.minecraft.client.renderer.LightTexture;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.SpriteLoader;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleGroup;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleType;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.FileToIdConverter;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ParticleEngine implements PreparableReloadListener {
/*  69 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  70 */   private static final FileToIdConverter PARTICLE_LISTER = FileToIdConverter.json("particles");
/*  71 */   private static final ResourceLocation PARTICLES_ATLAS_INFO = new ResourceLocation("particles");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_PARTICLES_PER_LAYER = 16384;
/*     */ 
/*     */ 
/*     */   
/*  80 */   private static final List<ParticleRenderType> RENDER_ORDER = (List<ParticleRenderType>)ImmutableList.of(ParticleRenderType.TERRAIN_SHEET, ParticleRenderType.PARTICLE_SHEET_OPAQUE, ParticleRenderType.PARTICLE_SHEET_LIT, ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT, ParticleRenderType.CUSTOM);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ClientLevel level;
/*     */ 
/*     */ 
/*     */   
/*  89 */   private final Map<ParticleRenderType, Queue<Particle>> particles = Maps.newIdentityHashMap();
/*  90 */   private final Queue<TrackingEmitter> trackingEmitters = Queues.newArrayDeque();
/*     */   private final TextureManager textureManager;
/*  92 */   private final RandomSource random = RandomSource.create();
/*  93 */   private final Int2ObjectMap<ParticleProvider<?>> providers = (Int2ObjectMap<ParticleProvider<?>>)new Int2ObjectOpenHashMap();
/*  94 */   private final Queue<Particle> particlesToAdd = Queues.newArrayDeque();
/*  95 */   private final Map<ResourceLocation, MutableSpriteSet> spriteSets = Maps.newHashMap();
/*     */   private final TextureAtlas textureAtlas;
/*  97 */   private final Object2IntOpenHashMap<ParticleGroup> trackedParticleCounts = new Object2IntOpenHashMap();
/*     */   @FunctionalInterface
/*     */   private static interface SpriteParticleRegistration<T extends ParticleOptions> {
/*     */     ParticleProvider<T> create(SpriteSet param1SpriteSet); }
/*     */   private static class MutableSpriteSet implements SpriteSet { private List<TextureAtlasSprite> sprites;
/*     */     
/*     */     public TextureAtlasSprite get(int $$0, int $$1) {
/* 104 */       return this.sprites.get($$0 * (this.sprites.size() - 1) / $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public TextureAtlasSprite get(RandomSource $$0) {
/* 109 */       return this.sprites.get($$0.nextInt(this.sprites.size()));
/*     */     }
/*     */     
/*     */     public void rebind(List<TextureAtlasSprite> $$0) {
/* 113 */       this.sprites = (List<TextureAtlasSprite>)ImmutableList.copyOf($$0);
/*     */     } }
/*     */ 
/*     */   
/*     */   public ParticleEngine(ClientLevel $$0, TextureManager $$1) {
/* 118 */     this.textureAtlas = new TextureAtlas(TextureAtlas.LOCATION_PARTICLES);
/* 119 */     $$1.register(this.textureAtlas.location(), (AbstractTexture)this.textureAtlas);
/*     */     
/* 121 */     this.level = $$0;
/* 122 */     this.textureManager = $$1;
/*     */     
/* 124 */     registerProviders();
/*     */   }
/*     */   
/*     */   private void registerProviders() {
/* 128 */     register((ParticleType<ParticleOptions>)ParticleTypes.AMBIENT_ENTITY_EFFECT, AmbientMobProvider::new);
/* 129 */     register((ParticleType<ParticleOptions>)ParticleTypes.ANGRY_VILLAGER, AngryVillagerProvider::new);
/* 130 */     register(ParticleTypes.BLOCK_MARKER, new BlockMarker.Provider());
/* 131 */     register(ParticleTypes.BLOCK, new TerrainParticle.Provider());
/* 132 */     register((ParticleType<ParticleOptions>)ParticleTypes.BUBBLE, Provider::new);
/* 133 */     register((ParticleType<ParticleOptions>)ParticleTypes.BUBBLE_COLUMN_UP, Provider::new);
/* 134 */     register((ParticleType<ParticleOptions>)ParticleTypes.BUBBLE_POP, Provider::new);
/* 135 */     register((ParticleType<ParticleOptions>)ParticleTypes.CAMPFIRE_COSY_SMOKE, CosyProvider::new);
/* 136 */     register((ParticleType<ParticleOptions>)ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, SignalProvider::new);
/* 137 */     register((ParticleType<ParticleOptions>)ParticleTypes.CLOUD, Provider::new);
/* 138 */     register((ParticleType<ParticleOptions>)ParticleTypes.COMPOSTER, ComposterFillProvider::new);
/* 139 */     register((ParticleType<ParticleOptions>)ParticleTypes.CRIT, Provider::new);
/* 140 */     register((ParticleType<ParticleOptions>)ParticleTypes.CURRENT_DOWN, Provider::new);
/* 141 */     register((ParticleType<ParticleOptions>)ParticleTypes.DAMAGE_INDICATOR, DamageIndicatorProvider::new);
/* 142 */     register((ParticleType<ParticleOptions>)ParticleTypes.DRAGON_BREATH, Provider::new);
/* 143 */     register((ParticleType<ParticleOptions>)ParticleTypes.DOLPHIN, DolphinSpeedProvider::new);
/* 144 */     register((ParticleType<ParticleOptions>)ParticleTypes.DRIPPING_LAVA, DripParticle::createLavaHangParticle);
/* 145 */     register((ParticleType<ParticleOptions>)ParticleTypes.FALLING_LAVA, DripParticle::createLavaFallParticle);
/* 146 */     register((ParticleType<ParticleOptions>)ParticleTypes.LANDING_LAVA, DripParticle::createLavaLandParticle);
/* 147 */     register((ParticleType<ParticleOptions>)ParticleTypes.DRIPPING_WATER, DripParticle::createWaterHangParticle);
/* 148 */     register((ParticleType<ParticleOptions>)ParticleTypes.FALLING_WATER, DripParticle::createWaterFallParticle);
/* 149 */     register(ParticleTypes.DUST, Provider::new);
/* 150 */     register(ParticleTypes.DUST_COLOR_TRANSITION, Provider::new);
/* 151 */     register((ParticleType<ParticleOptions>)ParticleTypes.EFFECT, Provider::new);
/* 152 */     register((ParticleType<ParticleOptions>)ParticleTypes.ELDER_GUARDIAN, new MobAppearanceParticle.Provider());
/* 153 */     register((ParticleType<ParticleOptions>)ParticleTypes.ENCHANTED_HIT, MagicProvider::new);
/* 154 */     register((ParticleType<ParticleOptions>)ParticleTypes.ENCHANT, Provider::new);
/* 155 */     register((ParticleType<ParticleOptions>)ParticleTypes.END_ROD, Provider::new);
/* 156 */     register((ParticleType<ParticleOptions>)ParticleTypes.ENTITY_EFFECT, MobProvider::new);
/* 157 */     register((ParticleType<ParticleOptions>)ParticleTypes.EXPLOSION_EMITTER, new HugeExplosionSeedParticle.Provider());
/* 158 */     register((ParticleType<ParticleOptions>)ParticleTypes.EXPLOSION, Provider::new);
/* 159 */     register((ParticleType<ParticleOptions>)ParticleTypes.SONIC_BOOM, Provider::new);
/* 160 */     register(ParticleTypes.FALLING_DUST, Provider::new);
/* 161 */     register((ParticleType<ParticleOptions>)ParticleTypes.GUST, Provider::new);
/* 162 */     register((ParticleType<ParticleOptions>)ParticleTypes.GUST_EMITTER, new GustSeedParticle.Provider());
/* 163 */     register((ParticleType<ParticleOptions>)ParticleTypes.FIREWORK, SparkProvider::new);
/* 164 */     register((ParticleType<ParticleOptions>)ParticleTypes.FISHING, Provider::new);
/* 165 */     register((ParticleType<ParticleOptions>)ParticleTypes.FLAME, Provider::new);
/* 166 */     register((ParticleType<ParticleOptions>)ParticleTypes.SCULK_SOUL, EmissiveProvider::new);
/* 167 */     register(ParticleTypes.SCULK_CHARGE, Provider::new);
/* 168 */     register((ParticleType<ParticleOptions>)ParticleTypes.SCULK_CHARGE_POP, Provider::new);
/* 169 */     register((ParticleType<ParticleOptions>)ParticleTypes.SOUL, Provider::new);
/* 170 */     register((ParticleType<ParticleOptions>)ParticleTypes.SOUL_FIRE_FLAME, Provider::new);
/* 171 */     register((ParticleType<ParticleOptions>)ParticleTypes.FLASH, FlashProvider::new);
/* 172 */     register((ParticleType<ParticleOptions>)ParticleTypes.HAPPY_VILLAGER, HappyVillagerProvider::new);
/* 173 */     register((ParticleType<ParticleOptions>)ParticleTypes.HEART, Provider::new);
/* 174 */     register((ParticleType<ParticleOptions>)ParticleTypes.INSTANT_EFFECT, InstantProvider::new);
/* 175 */     register(ParticleTypes.ITEM, new BreakingItemParticle.Provider());
/* 176 */     register((ParticleType<ParticleOptions>)ParticleTypes.ITEM_SLIME, new BreakingItemParticle.SlimeProvider());
/* 177 */     register((ParticleType<ParticleOptions>)ParticleTypes.ITEM_SNOWBALL, new BreakingItemParticle.SnowballProvider());
/* 178 */     register((ParticleType<ParticleOptions>)ParticleTypes.LARGE_SMOKE, Provider::new);
/* 179 */     register((ParticleType<ParticleOptions>)ParticleTypes.LAVA, Provider::new);
/* 180 */     register((ParticleType<ParticleOptions>)ParticleTypes.MYCELIUM, Provider::new);
/* 181 */     register((ParticleType<ParticleOptions>)ParticleTypes.NAUTILUS, NautilusProvider::new);
/* 182 */     register((ParticleType<ParticleOptions>)ParticleTypes.NOTE, Provider::new);
/* 183 */     register((ParticleType<ParticleOptions>)ParticleTypes.POOF, Provider::new);
/* 184 */     register((ParticleType<ParticleOptions>)ParticleTypes.PORTAL, Provider::new);
/* 185 */     register((ParticleType<ParticleOptions>)ParticleTypes.RAIN, Provider::new);
/* 186 */     register((ParticleType<ParticleOptions>)ParticleTypes.SMOKE, Provider::new);
/* 187 */     register((ParticleType<ParticleOptions>)ParticleTypes.WHITE_SMOKE, Provider::new);
/* 188 */     register((ParticleType<ParticleOptions>)ParticleTypes.SNEEZE, SneezeProvider::new);
/* 189 */     register((ParticleType<ParticleOptions>)ParticleTypes.SNOWFLAKE, Provider::new);
/* 190 */     register((ParticleType<ParticleOptions>)ParticleTypes.SPIT, Provider::new);
/* 191 */     register((ParticleType<ParticleOptions>)ParticleTypes.SWEEP_ATTACK, Provider::new);
/* 192 */     register((ParticleType<ParticleOptions>)ParticleTypes.TOTEM_OF_UNDYING, Provider::new);
/* 193 */     register((ParticleType<ParticleOptions>)ParticleTypes.SQUID_INK, Provider::new);
/* 194 */     register((ParticleType<ParticleOptions>)ParticleTypes.UNDERWATER, UnderwaterProvider::new);
/* 195 */     register((ParticleType<ParticleOptions>)ParticleTypes.SPLASH, Provider::new);
/* 196 */     register((ParticleType<ParticleOptions>)ParticleTypes.WITCH, WitchProvider::new);
/* 197 */     register((ParticleType<ParticleOptions>)ParticleTypes.DRIPPING_HONEY, DripParticle::createHoneyHangParticle);
/* 198 */     register((ParticleType<ParticleOptions>)ParticleTypes.FALLING_HONEY, DripParticle::createHoneyFallParticle);
/* 199 */     register((ParticleType<ParticleOptions>)ParticleTypes.LANDING_HONEY, DripParticle::createHoneyLandParticle);
/* 200 */     register((ParticleType<ParticleOptions>)ParticleTypes.FALLING_NECTAR, DripParticle::createNectarFallParticle);
/* 201 */     register((ParticleType<ParticleOptions>)ParticleTypes.FALLING_SPORE_BLOSSOM, DripParticle::createSporeBlossomFallParticle);
/* 202 */     register((ParticleType<ParticleOptions>)ParticleTypes.SPORE_BLOSSOM_AIR, SporeBlossomAirProvider::new);
/* 203 */     register((ParticleType<ParticleOptions>)ParticleTypes.ASH, Provider::new);
/* 204 */     register((ParticleType<ParticleOptions>)ParticleTypes.CRIMSON_SPORE, CrimsonSporeProvider::new);
/* 205 */     register((ParticleType<ParticleOptions>)ParticleTypes.WARPED_SPORE, WarpedSporeProvider::new);
/* 206 */     register((ParticleType<ParticleOptions>)ParticleTypes.DRIPPING_OBSIDIAN_TEAR, DripParticle::createObsidianTearHangParticle);
/* 207 */     register((ParticleType<ParticleOptions>)ParticleTypes.FALLING_OBSIDIAN_TEAR, DripParticle::createObsidianTearFallParticle);
/* 208 */     register((ParticleType<ParticleOptions>)ParticleTypes.LANDING_OBSIDIAN_TEAR, DripParticle::createObsidianTearLandParticle);
/* 209 */     register((ParticleType<ParticleOptions>)ParticleTypes.REVERSE_PORTAL, ReversePortalProvider::new);
/* 210 */     register((ParticleType<ParticleOptions>)ParticleTypes.WHITE_ASH, Provider::new);
/* 211 */     register((ParticleType<ParticleOptions>)ParticleTypes.SMALL_FLAME, SmallFlameProvider::new);
/*     */     
/* 213 */     register((ParticleType<ParticleOptions>)ParticleTypes.DRIPPING_DRIPSTONE_WATER, DripParticle::createDripstoneWaterHangParticle);
/* 214 */     register((ParticleType<ParticleOptions>)ParticleTypes.FALLING_DRIPSTONE_WATER, DripParticle::createDripstoneWaterFallParticle);
/*     */     
/* 216 */     register((ParticleType<ParticleOptions>)ParticleTypes.CHERRY_LEAVES, $$0 -> ());
/*     */     
/* 218 */     register((ParticleType<ParticleOptions>)ParticleTypes.DRIPPING_DRIPSTONE_LAVA, DripParticle::createDripstoneLavaHangParticle);
/* 219 */     register((ParticleType<ParticleOptions>)ParticleTypes.FALLING_DRIPSTONE_LAVA, DripParticle::createDripstoneLavaFallParticle);
/* 220 */     register(ParticleTypes.VIBRATION, Provider::new);
/* 221 */     register((ParticleType<ParticleOptions>)ParticleTypes.GLOW_SQUID_INK, GlowInkProvider::new);
/* 222 */     register((ParticleType<ParticleOptions>)ParticleTypes.GLOW, GlowSquidProvider::new);
/* 223 */     register((ParticleType<ParticleOptions>)ParticleTypes.WAX_ON, WaxOnProvider::new);
/* 224 */     register((ParticleType<ParticleOptions>)ParticleTypes.WAX_OFF, WaxOffProvider::new);
/* 225 */     register((ParticleType<ParticleOptions>)ParticleTypes.ELECTRIC_SPARK, ElectricSparkProvider::new);
/* 226 */     register((ParticleType<ParticleOptions>)ParticleTypes.SCRAPE, ScrapeProvider::new);
/* 227 */     register(ParticleTypes.SHRIEK, Provider::new);
/*     */     
/* 229 */     register((ParticleType<ParticleOptions>)ParticleTypes.EGG_CRACK, EggCrackProvider::new);
/* 230 */     register((ParticleType<ParticleOptions>)ParticleTypes.DUST_PLUME, Provider::new);
/* 231 */     register((ParticleType<ParticleOptions>)ParticleTypes.GUST_DUST, GustDustParticleProvider::new);
/* 232 */     register((ParticleType<ParticleOptions>)ParticleTypes.TRIAL_SPAWNER_DETECTION, Provider::new);
/*     */   }
/*     */   
/*     */   private <T extends ParticleOptions> void register(ParticleType<T> $$0, ParticleProvider<T> $$1) {
/* 236 */     this.providers.put(BuiltInRegistries.PARTICLE_TYPE.getId($$0), $$1);
/*     */   }
/*     */   
/*     */   private <T extends ParticleOptions> void register(ParticleType<T> $$0, ParticleProvider.Sprite<T> $$1) {
/* 240 */     register($$0, $$1 -> ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T extends ParticleOptions> void register(ParticleType<T> $$0, SpriteParticleRegistration<T> $$1) {
/* 250 */     MutableSpriteSet $$2 = new MutableSpriteSet();
/* 251 */     this.spriteSets.put(BuiltInRegistries.PARTICLE_TYPE.getKey($$0), $$2);
/* 252 */     this.providers.put(BuiltInRegistries.PARTICLE_TYPE.getId($$0), $$1.create($$2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/* 260 */     CompletableFuture<List<ParticleDefinition>> $$6 = CompletableFuture.supplyAsync(() -> PARTICLE_LISTER.listMatchingResources($$0), $$4).thenCompose($$1 -> {
/*     */           List<CompletableFuture<ParticleDefinition>> $$2 = new ArrayList<>($$1.size());
/*     */ 
/*     */           
/*     */           $$1.forEach(());
/*     */ 
/*     */           
/*     */           return Util.sequence($$2);
/*     */         });
/*     */ 
/*     */     
/* 271 */     CompletableFuture<SpriteLoader.Preparations> $$7 = SpriteLoader.create(this.textureAtlas).loadAndStitch($$1, PARTICLES_ATLAS_INFO, 0, $$4).thenCompose(SpriteLoader.Preparations::waitForUpload);
/*     */ 
/*     */     
/* 274 */     Objects.requireNonNull($$0); return CompletableFuture.allOf((CompletableFuture<?>[])new CompletableFuture[] { $$7, $$6 }).thenCompose($$0::wait)
/* 275 */       .thenAcceptAsync($$3 -> {
/*     */           clearParticles();
/*     */           $$0.startTick();
/*     */           $$0.push("upload");
/*     */           SpriteLoader.Preparations $$4 = $$1.join();
/*     */           this.textureAtlas.upload($$4);
/*     */           $$0.popPush("bindSpriteSets");
/*     */           Set<ResourceLocation> $$5 = new HashSet<>();
/*     */           TextureAtlasSprite $$6 = $$4.missing();
/*     */           ((List)$$2.join()).forEach(());
/*     */           if (!$$5.isEmpty()) {
/*     */             LOGGER.warn("Missing particle sprites: {}", $$5.stream().sorted().map(ResourceLocation::toString).collect(Collectors.joining(",")));
/*     */           }
/*     */           $$0.pop();
/*     */           $$0.endTick();
/*     */         }$$5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 313 */     this.textureAtlas.clearTextureData();
/*     */   }
/*     */   
/*     */   private Optional<List<ResourceLocation>> loadParticleDescription(ResourceLocation $$0, Resource $$1) {
/* 317 */     if (!this.spriteSets.containsKey($$0)) {
/* 318 */       LOGGER.debug("Redundant texture list for particle: {}", $$0);
/* 319 */       return Optional.empty();
/*     */     }  
/* 321 */     try { Reader $$2 = $$1.openAsReader(); 
/* 322 */       try { ParticleDescription $$3 = ParticleDescription.fromJson(GsonHelper.parse($$2));
/* 323 */         Optional<List<ResourceLocation>> optional = Optional.of($$3.getTextures());
/* 324 */         if ($$2 != null) $$2.close();  return optional; } catch (Throwable throwable) { if ($$2 != null) try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$4)
/* 325 */     { throw new IllegalStateException("Failed to load description for particle " + $$0, $$4); }
/*     */   
/*     */   }
/*     */   
/*     */   public void createTrackingEmitter(Entity $$0, ParticleOptions $$1) {
/* 330 */     this.trackingEmitters.add(new TrackingEmitter(this.level, $$0, $$1));
/*     */   }
/*     */   
/*     */   public void createTrackingEmitter(Entity $$0, ParticleOptions $$1, int $$2) {
/* 334 */     this.trackingEmitters.add(new TrackingEmitter(this.level, $$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Particle createParticle(ParticleOptions $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 339 */     Particle $$7 = makeParticle($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/* 340 */     if ($$7 != null) {
/* 341 */       add($$7);
/* 342 */       return $$7;
/*     */     } 
/* 344 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private <T extends ParticleOptions> Particle makeParticle(T $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 350 */     ParticleProvider<T> $$7 = (ParticleProvider<T>)this.providers.get(BuiltInRegistries.PARTICLE_TYPE.getId($$0.getType()));
/* 351 */     if ($$7 == null) {
/* 352 */       return null;
/*     */     }
/* 354 */     return $$7.createParticle($$0, this.level, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public void add(Particle $$0) {
/* 358 */     Optional<ParticleGroup> $$1 = $$0.getParticleGroup();
/* 359 */     if ($$1.isPresent()) {
/* 360 */       if (hasSpaceInParticleLimit($$1.get())) {
/* 361 */         this.particlesToAdd.add($$0);
/* 362 */         updateCount($$1.get(), 1);
/*     */       } 
/*     */     } else {
/* 365 */       this.particlesToAdd.add($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tick() {
/* 370 */     this.particles.forEach(($$0, $$1) -> {
/*     */           this.level.getProfiler().push($$0.toString());
/*     */           
/*     */           tickParticleList($$1);
/*     */           this.level.getProfiler().pop();
/*     */         });
/* 376 */     if (!this.trackingEmitters.isEmpty()) {
/* 377 */       List<TrackingEmitter> $$0 = Lists.newArrayList();
/* 378 */       for (TrackingEmitter $$1 : this.trackingEmitters) {
/* 379 */         $$1.tick();
/* 380 */         if (!$$1.isAlive()) {
/* 381 */           $$0.add($$1);
/*     */         }
/*     */       } 
/* 384 */       this.trackingEmitters.removeAll($$0);
/*     */     } 
/*     */     
/* 387 */     if (!this.particlesToAdd.isEmpty()) {
/*     */       Particle $$2;
/* 389 */       while (($$2 = this.particlesToAdd.poll()) != null) {
/* 390 */         ((Queue<Particle>)this.particles.computeIfAbsent($$2.getRenderType(), $$0 -> EvictingQueue.create(16384))).add($$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tickParticleList(Collection<Particle> $$0) {
/* 396 */     if (!$$0.isEmpty()) {
/* 397 */       for (Iterator<Particle> $$1 = $$0.iterator(); $$1.hasNext(); ) {
/* 398 */         Particle $$2 = $$1.next();
/* 399 */         tickParticle($$2);
/*     */         
/* 401 */         if (!$$2.isAlive()) {
/* 402 */           $$2.getParticleGroup().ifPresent($$0 -> updateCount($$0, -1));
/* 403 */           $$1.remove();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateCount(ParticleGroup $$0, int $$1) {
/* 410 */     this.trackedParticleCounts.addTo($$0, $$1);
/*     */   }
/*     */   
/*     */   private void tickParticle(Particle $$0) {
/*     */     try {
/* 415 */       $$0.tick();
/* 416 */     } catch (Throwable $$1) {
/* 417 */       CrashReport $$2 = CrashReport.forThrowable($$1, "Ticking Particle");
/* 418 */       CrashReportCategory $$3 = $$2.addCategory("Particle being ticked");
/* 419 */       Objects.requireNonNull($$0); $$3.setDetail("Particle", $$0::toString);
/* 420 */       Objects.requireNonNull($$0.getRenderType()); $$3.setDetail("Particle Type", $$0.getRenderType()::toString);
/*     */       
/* 422 */       throw new ReportedException($$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void render(PoseStack $$0, MultiBufferSource.BufferSource $$1, LightTexture $$2, Camera $$3, float $$4) {
/* 427 */     $$2.turnOnLightLayer();
/* 428 */     RenderSystem.enableDepthTest();
/*     */     
/* 430 */     PoseStack $$5 = RenderSystem.getModelViewStack();
/* 431 */     $$5.pushPose();
/* 432 */     $$5.mulPoseMatrix($$0.last().pose());
/* 433 */     RenderSystem.applyModelViewMatrix();
/*     */     
/* 435 */     for (ParticleRenderType $$6 : RENDER_ORDER) {
/* 436 */       Iterable<Particle> $$7 = this.particles.get($$6);
/* 437 */       if ($$7 == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 441 */       RenderSystem.setShader(GameRenderer::getParticleShader);
/*     */       
/* 443 */       Tesselator $$8 = Tesselator.getInstance();
/* 444 */       BufferBuilder $$9 = $$8.getBuilder();
/* 445 */       $$6.begin($$9, this.textureManager);
/* 446 */       for (Particle $$10 : $$7) {
/*     */         try {
/* 448 */           $$10.render((VertexConsumer)$$9, $$3, $$4);
/* 449 */         } catch (Throwable $$11) {
/* 450 */           CrashReport $$12 = CrashReport.forThrowable($$11, "Rendering Particle");
/* 451 */           CrashReportCategory $$13 = $$12.addCategory("Particle being rendered");
/* 452 */           Objects.requireNonNull($$10); $$13.setDetail("Particle", $$10::toString);
/* 453 */           Objects.requireNonNull($$6); $$13.setDetail("Particle Type", $$6::toString);
/* 454 */           throw new ReportedException($$12);
/*     */         } 
/*     */       } 
/* 457 */       $$6.end($$8);
/*     */     } 
/*     */     
/* 460 */     $$5.popPose();
/* 461 */     RenderSystem.applyModelViewMatrix();
/*     */     
/* 463 */     RenderSystem.depthMask(true);
/* 464 */     RenderSystem.disableBlend();
/* 465 */     $$2.turnOffLightLayer();
/*     */   }
/*     */   
/*     */   public void setLevel(@Nullable ClientLevel $$0) {
/* 469 */     this.level = $$0;
/* 470 */     clearParticles();
/* 471 */     this.trackingEmitters.clear();
/*     */   }
/*     */   
/*     */   public void destroy(BlockPos $$0, BlockState $$1) {
/* 475 */     if ($$1.isAir() || !$$1.shouldSpawnTerrainParticles()) {
/*     */       return;
/*     */     }
/*     */     
/* 479 */     VoxelShape $$2 = $$1.getShape((BlockGetter)this.level, $$0);
/*     */     
/* 481 */     double $$3 = 0.25D;
/*     */ 
/*     */     
/* 484 */     $$2.forAllBoxes(($$2, $$3, $$4, $$5, $$6, $$7) -> {
/*     */           double $$8 = Math.min(1.0D, $$5 - $$2);
/*     */           double $$9 = Math.min(1.0D, $$6 - $$3);
/*     */           double $$10 = Math.min(1.0D, $$7 - $$4);
/*     */           int $$11 = Math.max(2, Mth.ceil($$8 / 0.25D));
/*     */           int $$12 = Math.max(2, Mth.ceil($$9 / 0.25D));
/*     */           int $$13 = Math.max(2, Mth.ceil($$10 / 0.25D));
/*     */           for (int $$14 = 0; $$14 < $$11; $$14++) {
/*     */             for (int $$15 = 0; $$15 < $$12; $$15++) {
/*     */               for (int $$16 = 0; $$16 < $$13; $$16++) {
/*     */                 double $$17 = ($$14 + 0.5D) / $$11;
/*     */                 double $$18 = ($$15 + 0.5D) / $$12;
/*     */                 double $$19 = ($$16 + 0.5D) / $$13;
/*     */                 double $$20 = $$17 * $$8 + $$2;
/*     */                 double $$21 = $$18 * $$9 + $$3;
/*     */                 double $$22 = $$19 * $$10 + $$4;
/*     */                 add(new TerrainParticle(this.level, $$0.getX() + $$20, $$0.getY() + $$21, $$0.getZ() + $$22, $$17 - 0.5D, $$18 - 0.5D, $$19 - 0.5D, $$1, $$0));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void crack(BlockPos $$0, Direction $$1) {
/* 512 */     BlockState $$2 = this.level.getBlockState($$0);
/* 513 */     if ($$2.getRenderShape() == RenderShape.INVISIBLE || !$$2.shouldSpawnTerrainParticles()) {
/*     */       return;
/*     */     }
/*     */     
/* 517 */     int $$3 = $$0.getX();
/* 518 */     int $$4 = $$0.getY();
/* 519 */     int $$5 = $$0.getZ();
/*     */     
/* 521 */     float $$6 = 0.1F;
/*     */     
/* 523 */     AABB $$7 = $$2.getShape((BlockGetter)this.level, $$0).bounds();
/* 524 */     double $$8 = $$3 + this.random.nextDouble() * ($$7.maxX - $$7.minX - 0.20000000298023224D) + 0.10000000149011612D + $$7.minX;
/* 525 */     double $$9 = $$4 + this.random.nextDouble() * ($$7.maxY - $$7.minY - 0.20000000298023224D) + 0.10000000149011612D + $$7.minY;
/* 526 */     double $$10 = $$5 + this.random.nextDouble() * ($$7.maxZ - $$7.minZ - 0.20000000298023224D) + 0.10000000149011612D + $$7.minZ;
/*     */ 
/*     */     
/* 529 */     if ($$1 == Direction.DOWN) {
/* 530 */       $$9 = $$4 + $$7.minY - 0.10000000149011612D;
/*     */     }
/* 532 */     if ($$1 == Direction.UP) {
/* 533 */       $$9 = $$4 + $$7.maxY + 0.10000000149011612D;
/*     */     }
/* 535 */     if ($$1 == Direction.NORTH) {
/* 536 */       $$10 = $$5 + $$7.minZ - 0.10000000149011612D;
/*     */     }
/* 538 */     if ($$1 == Direction.SOUTH) {
/* 539 */       $$10 = $$5 + $$7.maxZ + 0.10000000149011612D;
/*     */     }
/* 541 */     if ($$1 == Direction.WEST) {
/* 542 */       $$8 = $$3 + $$7.minX - 0.10000000149011612D;
/*     */     }
/* 544 */     if ($$1 == Direction.EAST) {
/* 545 */       $$8 = $$3 + $$7.maxX + 0.10000000149011612D;
/*     */     }
/*     */     
/* 548 */     add((new TerrainParticle(this.level, $$8, $$9, $$10, 0.0D, 0.0D, 0.0D, $$2, $$0)).setPower(0.2F).scale(0.6F));
/*     */   }
/*     */   
/*     */   public String countParticles() {
/* 552 */     return String.valueOf(this.particles.values().stream().mapToInt(Collection::size).sum());
/*     */   }
/*     */   
/*     */   private boolean hasSpaceInParticleLimit(ParticleGroup $$0) {
/* 556 */     return (this.trackedParticleCounts.getInt($$0) < $$0.getLimit());
/*     */   }
/*     */   
/*     */   private void clearParticles() {
/* 560 */     this.particles.clear();
/* 561 */     this.particlesToAdd.clear();
/* 562 */     this.trackingEmitters.clear();
/* 563 */     this.trackedParticleCounts.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ParticleEngine.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */