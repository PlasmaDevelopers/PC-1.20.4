/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import com.mojang.blaze3d.pipeline.RenderTarget;
/*      */ import com.mojang.blaze3d.platform.GlStateManager;
/*      */ import com.mojang.blaze3d.platform.Lighting;
/*      */ import com.mojang.blaze3d.shaders.Uniform;
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*      */ import com.mojang.blaze3d.vertex.BufferUploader;
/*      */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*      */ import com.mojang.blaze3d.vertex.PoseStack;
/*      */ import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
/*      */ import com.mojang.blaze3d.vertex.Tesselator;
/*      */ import com.mojang.blaze3d.vertex.VertexBuffer;
/*      */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*      */ import com.mojang.blaze3d.vertex.VertexFormat;
/*      */ import com.mojang.blaze3d.vertex.VertexMultiConsumer;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.math.Axis;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*      */ import java.io.IOException;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import java.util.function.Supplier;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.CrashReport;
/*      */ import net.minecraft.CrashReportCategory;
/*      */ import net.minecraft.ReportedException;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.Camera;
/*      */ import net.minecraft.client.CloudStatus;
/*      */ import net.minecraft.client.GraphicsStatus;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.ParticleStatus;
/*      */ import net.minecraft.client.PrioritizeChunkUpdates;
/*      */ import net.minecraft.client.multiplayer.ClientLevel;
/*      */ import net.minecraft.client.particle.Particle;
/*      */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
/*      */ import net.minecraft.client.renderer.chunk.RenderRegionCache;
/*      */ import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*      */ import net.minecraft.client.resources.model.ModelBakery;
/*      */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SoundInstance;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Position;
/*      */ import net.minecraft.core.SectionPos;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.particles.ItemParticleOption;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.core.particles.SculkChargeParticleOptions;
/*      */ import net.minecraft.core.particles.ShriekParticleOption;
/*      */ import net.minecraft.core.particles.SimpleParticleType;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.level.BlockDestructionProgress;
/*      */ import net.minecraft.server.packs.PackResources;
/*      */ import net.minecraft.server.packs.resources.ResourceManager;
/*      */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.tags.FluidTags;
/*      */ import net.minecraft.util.FastColor;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.ParticleUtils;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.profiling.ProfilerFiller;
/*      */ import net.minecraft.util.valueproviders.IntProvider;
/*      */ import net.minecraft.util.valueproviders.UniformInt;
/*      */ import net.minecraft.world.TickRateManager;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.item.BoneMealItem;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.RecordItem;
/*      */ import net.minecraft.world.level.BlockAndTintGetter;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.ChunkPos;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelAccessor;
/*      */ import net.minecraft.world.level.LevelHeightAccessor;
/*      */ import net.minecraft.world.level.LightLayer;
/*      */ import net.minecraft.world.level.biome.Biome;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.BrushableBlock;
/*      */ import net.minecraft.world.level.block.CampfireBlock;
/*      */ import net.minecraft.world.level.block.ComposterBlock;
/*      */ import net.minecraft.world.level.block.MultifaceBlock;
/*      */ import net.minecraft.world.level.block.PointedDripstoneBlock;
/*      */ import net.minecraft.world.level.block.SculkShriekerBlock;
/*      */ import net.minecraft.world.level.block.SoundType;
/*      */ import net.minecraft.world.level.block.entity.BlockEntity;
/*      */ import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import net.minecraft.world.level.border.WorldBorder;
/*      */ import net.minecraft.world.level.levelgen.Heightmap;
/*      */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*      */ import net.minecraft.world.level.material.FluidState;
/*      */ import net.minecraft.world.level.material.FogType;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.BlockHitResult;
/*      */ import net.minecraft.world.phys.HitResult;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.phys.shapes.CollisionContext;
/*      */ import net.minecraft.world.phys.shapes.Shapes;
/*      */ import net.minecraft.world.phys.shapes.VoxelShape;
/*      */ import org.joml.Matrix3f;
/*      */ import org.joml.Matrix4f;
/*      */ import org.joml.Matrix4fc;
/*      */ import org.joml.Vector3d;
/*      */ import org.joml.Vector4f;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class LevelRenderer
/*      */   implements ResourceManagerReloadListener, AutoCloseable {
/*  144 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*      */   public static final int SECTION_SIZE = 16;
/*      */   
/*      */   public static final int HALF_SECTION_SIZE = 8;
/*      */   
/*      */   private static final float SKY_DISC_RADIUS = 512.0F;
/*      */   
/*      */   private static final int MIN_FOG_DISTANCE = 32;
/*      */   private static final int RAIN_RADIUS = 10;
/*      */   private static final int RAIN_DIAMETER = 21;
/*      */   private static final int TRANSPARENT_SORT_COUNT = 15;
/*  156 */   private static final ResourceLocation MOON_LOCATION = new ResourceLocation("textures/environment/moon_phases.png");
/*  157 */   private static final ResourceLocation SUN_LOCATION = new ResourceLocation("textures/environment/sun.png");
/*  158 */   private static final ResourceLocation CLOUDS_LOCATION = new ResourceLocation("textures/environment/clouds.png");
/*  159 */   private static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");
/*  160 */   private static final ResourceLocation FORCEFIELD_LOCATION = new ResourceLocation("textures/misc/forcefield.png");
/*  161 */   private static final ResourceLocation RAIN_LOCATION = new ResourceLocation("textures/environment/rain.png");
/*  162 */   private static final ResourceLocation SNOW_LOCATION = new ResourceLocation("textures/environment/snow.png");
/*      */   
/*  164 */   public static final Direction[] DIRECTIONS = Direction.values();
/*      */   
/*      */   private final Minecraft minecraft;
/*      */   
/*      */   private final EntityRenderDispatcher entityRenderDispatcher;
/*      */   
/*      */   private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
/*      */   
/*      */   private final RenderBuffers renderBuffers;
/*      */   
/*      */   @Nullable
/*      */   private ClientLevel level;
/*  176 */   private final SectionOcclusionGraph sectionOcclusionGraph = new SectionOcclusionGraph();
/*  177 */   private final ObjectArrayList<SectionRenderDispatcher.RenderSection> visibleSections = new ObjectArrayList(10000);
/*  178 */   private final Set<BlockEntity> globalBlockEntities = Sets.newHashSet();
/*      */   
/*      */   @Nullable
/*      */   private ViewArea viewArea;
/*      */   
/*      */   @Nullable
/*      */   private VertexBuffer starBuffer;
/*      */   
/*      */   @Nullable
/*      */   private VertexBuffer skyBuffer;
/*      */   
/*      */   @Nullable
/*      */   private VertexBuffer darkBuffer;
/*      */   private boolean generateClouds = true;
/*      */   @Nullable
/*      */   private VertexBuffer cloudBuffer;
/*  194 */   private final RunningTrimmedMean frameTimes = new RunningTrimmedMean(100);
/*      */   
/*      */   private int ticks;
/*      */   
/*  198 */   private final Int2ObjectMap<BlockDestructionProgress> destroyingBlocks = (Int2ObjectMap<BlockDestructionProgress>)new Int2ObjectOpenHashMap();
/*  199 */   private final Long2ObjectMap<SortedSet<BlockDestructionProgress>> destructionProgress = (Long2ObjectMap<SortedSet<BlockDestructionProgress>>)new Long2ObjectOpenHashMap();
/*      */   
/*  201 */   private final Map<BlockPos, SoundInstance> playingRecords = Maps.newHashMap();
/*      */   
/*      */   @Nullable
/*      */   private RenderTarget entityTarget;
/*      */   
/*      */   @Nullable
/*      */   private PostChain entityEffect;
/*      */   
/*      */   @Nullable
/*      */   private RenderTarget translucentTarget;
/*      */   @Nullable
/*      */   private RenderTarget itemEntityTarget;
/*      */   @Nullable
/*      */   private RenderTarget particlesTarget;
/*      */   @Nullable
/*      */   private RenderTarget weatherTarget;
/*      */   @Nullable
/*      */   private RenderTarget cloudsTarget;
/*      */   @Nullable
/*      */   private PostChain transparencyChain;
/*  221 */   private int lastCameraSectionX = Integer.MIN_VALUE;
/*  222 */   private int lastCameraSectionY = Integer.MIN_VALUE;
/*  223 */   private int lastCameraSectionZ = Integer.MIN_VALUE;
/*      */   
/*  225 */   private double prevCamX = Double.MIN_VALUE;
/*  226 */   private double prevCamY = Double.MIN_VALUE;
/*  227 */   private double prevCamZ = Double.MIN_VALUE;
/*  228 */   private double prevCamRotX = Double.MIN_VALUE;
/*  229 */   private double prevCamRotY = Double.MIN_VALUE;
/*      */   
/*  231 */   private int prevCloudX = Integer.MIN_VALUE;
/*  232 */   private int prevCloudY = Integer.MIN_VALUE;
/*  233 */   private int prevCloudZ = Integer.MIN_VALUE;
/*  234 */   private Vec3 prevCloudColor = Vec3.ZERO;
/*      */   
/*      */   @Nullable
/*      */   private CloudStatus prevCloudsType;
/*      */   
/*      */   @Nullable
/*      */   private SectionRenderDispatcher sectionRenderDispatcher;
/*  241 */   private int lastViewDistance = -1;
/*      */   
/*      */   private int renderedEntities;
/*      */   
/*      */   private int culledEntities;
/*      */   
/*      */   private Frustum cullingFrustum;
/*      */   
/*      */   private boolean captureFrustum;
/*      */   @Nullable
/*      */   private Frustum capturedFrustum;
/*  252 */   private final Vector4f[] frustumPoints = new Vector4f[8];
/*  253 */   private final Vector3d frustumPos = new Vector3d(0.0D, 0.0D, 0.0D);
/*      */   
/*      */   private double xTransparentOld;
/*      */   
/*      */   private double yTransparentOld;
/*      */   
/*      */   private double zTransparentOld;
/*      */   
/*      */   private int rainSoundTime;
/*  262 */   private final float[] rainSizeX = new float[1024];
/*  263 */   private final float[] rainSizeZ = new float[1024];
/*      */   
/*      */   public LevelRenderer(Minecraft $$0, EntityRenderDispatcher $$1, BlockEntityRenderDispatcher $$2, RenderBuffers $$3) {
/*  266 */     this.minecraft = $$0;
/*  267 */     this.entityRenderDispatcher = $$1;
/*  268 */     this.blockEntityRenderDispatcher = $$2;
/*  269 */     this.renderBuffers = $$3;
/*      */     
/*  271 */     for (int $$4 = 0; $$4 < 32; $$4++) {
/*  272 */       for (int $$5 = 0; $$5 < 32; $$5++) {
/*  273 */         float $$6 = ($$5 - 16);
/*  274 */         float $$7 = ($$4 - 16);
/*  275 */         float $$8 = Mth.sqrt($$6 * $$6 + $$7 * $$7);
/*  276 */         this.rainSizeX[$$4 << 5 | $$5] = -$$7 / $$8;
/*  277 */         this.rainSizeZ[$$4 << 5 | $$5] = $$6 / $$8;
/*      */       } 
/*      */     } 
/*      */     
/*  281 */     createStars();
/*  282 */     createLightSky();
/*  283 */     createDarkSky();
/*      */   }
/*      */   
/*      */   private void renderSnowAndRain(LightTexture $$0, float $$1, double $$2, double $$3, double $$4) {
/*  287 */     float $$5 = this.minecraft.level.getRainLevel($$1);
/*  288 */     if ($$5 <= 0.0F) {
/*      */       return;
/*      */     }
/*  291 */     $$0.turnOnLightLayer();
/*      */     
/*  293 */     ClientLevel clientLevel = this.minecraft.level;
/*      */     
/*  295 */     int $$7 = Mth.floor($$2);
/*  296 */     int $$8 = Mth.floor($$3);
/*  297 */     int $$9 = Mth.floor($$4);
/*      */     
/*  299 */     Tesselator $$10 = Tesselator.getInstance();
/*  300 */     BufferBuilder $$11 = $$10.getBuilder();
/*  301 */     RenderSystem.disableCull();
/*  302 */     RenderSystem.enableBlend();
/*  303 */     RenderSystem.enableDepthTest();
/*      */     
/*  305 */     int $$12 = 5;
/*  306 */     if (Minecraft.useFancyGraphics()) {
/*  307 */       $$12 = 10;
/*      */     }
/*      */     
/*  310 */     RenderSystem.depthMask(Minecraft.useShaderTransparency());
/*      */     
/*  312 */     int $$13 = -1;
/*  313 */     float $$14 = this.ticks + $$1;
/*      */     
/*  315 */     RenderSystem.setShader(GameRenderer::getParticleShader);
/*  316 */     BlockPos.MutableBlockPos $$15 = new BlockPos.MutableBlockPos();
/*  317 */     for (int $$16 = $$9 - $$12; $$16 <= $$9 + $$12; $$16++) {
/*  318 */       for (int $$17 = $$7 - $$12; $$17 <= $$7 + $$12; $$17++) {
/*  319 */         int $$18 = ($$16 - $$9 + 16) * 32 + $$17 - $$7 + 16;
/*  320 */         double $$19 = this.rainSizeX[$$18] * 0.5D;
/*  321 */         double $$20 = this.rainSizeZ[$$18] * 0.5D;
/*      */         
/*  323 */         $$15.set($$17, $$3, $$16);
/*  324 */         Biome $$21 = (Biome)clientLevel.getBiome((BlockPos)$$15).value();
/*  325 */         if ($$21.hasPrecipitation()) {
/*      */ 
/*      */ 
/*      */           
/*  329 */           int $$22 = clientLevel.getHeight(Heightmap.Types.MOTION_BLOCKING, $$17, $$16);
/*  330 */           int $$23 = $$8 - $$12;
/*  331 */           int $$24 = $$8 + $$12;
/*      */           
/*  333 */           if ($$23 < $$22) {
/*  334 */             $$23 = $$22;
/*      */           }
/*  336 */           if ($$24 < $$22) {
/*  337 */             $$24 = $$22;
/*      */           }
/*      */           
/*  340 */           int $$25 = $$22;
/*  341 */           if ($$25 < $$8) {
/*  342 */             $$25 = $$8;
/*      */           }
/*      */           
/*  345 */           if ($$23 != $$24) {
/*  346 */             RandomSource $$26 = RandomSource.create(($$17 * $$17 * 3121 + $$17 * 45238971 ^ $$16 * $$16 * 418711 + $$16 * 13761));
/*      */             
/*  348 */             $$15.set($$17, $$23, $$16);
/*  349 */             Biome.Precipitation $$27 = $$21.getPrecipitationAt((BlockPos)$$15);
/*  350 */             if ($$27 == Biome.Precipitation.RAIN) {
/*  351 */               if ($$13 != 0) {
/*  352 */                 if ($$13 >= 0) {
/*  353 */                   $$10.end();
/*      */                 }
/*  355 */                 $$13 = 0;
/*  356 */                 RenderSystem.setShaderTexture(0, RAIN_LOCATION);
/*  357 */                 $$11.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
/*      */               } 
/*      */               
/*  360 */               int $$28 = this.ticks & 0x1FFFF;
/*  361 */               int $$29 = $$17 * $$17 * 3121 + $$17 * 45238971 + $$16 * $$16 * 418711 + $$16 * 13761 & 0xFF;
/*  362 */               float $$30 = 3.0F + $$26.nextFloat();
/*  363 */               float $$31 = -(($$28 + $$29) + $$1) / 32.0F * $$30;
/*  364 */               float $$32 = $$31 % 32.0F;
/*      */               
/*  366 */               double $$33 = $$17 + 0.5D - $$2;
/*  367 */               double $$34 = $$16 + 0.5D - $$4;
/*  368 */               float $$35 = (float)Math.sqrt($$33 * $$33 + $$34 * $$34) / $$12;
/*      */               
/*  370 */               float $$36 = ((1.0F - $$35 * $$35) * 0.5F + 0.5F) * $$5;
/*      */               
/*  372 */               $$15.set($$17, $$25, $$16);
/*  373 */               int $$37 = getLightColor((BlockAndTintGetter)clientLevel, (BlockPos)$$15);
/*      */               
/*  375 */               $$11.vertex($$17 - $$2 - $$19 + 0.5D, $$24 - $$3, $$16 - $$4 - $$20 + 0.5D).uv(0.0F, $$23 * 0.25F + $$32).color(1.0F, 1.0F, 1.0F, $$36).uv2($$37).endVertex();
/*  376 */               $$11.vertex($$17 - $$2 + $$19 + 0.5D, $$24 - $$3, $$16 - $$4 + $$20 + 0.5D).uv(1.0F, $$23 * 0.25F + $$32).color(1.0F, 1.0F, 1.0F, $$36).uv2($$37).endVertex();
/*  377 */               $$11.vertex($$17 - $$2 + $$19 + 0.5D, $$23 - $$3, $$16 - $$4 + $$20 + 0.5D).uv(1.0F, $$24 * 0.25F + $$32).color(1.0F, 1.0F, 1.0F, $$36).uv2($$37).endVertex();
/*  378 */               $$11.vertex($$17 - $$2 - $$19 + 0.5D, $$23 - $$3, $$16 - $$4 - $$20 + 0.5D).uv(0.0F, $$24 * 0.25F + $$32).color(1.0F, 1.0F, 1.0F, $$36).uv2($$37).endVertex();
/*  379 */             } else if ($$27 == Biome.Precipitation.SNOW) {
/*  380 */               if ($$13 != 1) {
/*  381 */                 if ($$13 >= 0) {
/*  382 */                   $$10.end();
/*      */                 }
/*  384 */                 $$13 = 1;
/*  385 */                 RenderSystem.setShaderTexture(0, SNOW_LOCATION);
/*      */                 
/*  387 */                 $$11.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
/*      */               } 
/*  389 */               float $$38 = -((this.ticks & 0x1FF) + $$1) / 512.0F;
/*      */               
/*  391 */               float $$39 = (float)($$26.nextDouble() + $$14 * 0.01D * (float)$$26.nextGaussian());
/*  392 */               float $$40 = (float)($$26.nextDouble() + ($$14 * (float)$$26.nextGaussian()) * 0.001D);
/*      */               
/*  394 */               double $$41 = $$17 + 0.5D - $$2;
/*  395 */               double $$42 = $$16 + 0.5D - $$4;
/*  396 */               float $$43 = (float)Math.sqrt($$41 * $$41 + $$42 * $$42) / $$12;
/*      */               
/*  398 */               float $$44 = ((1.0F - $$43 * $$43) * 0.3F + 0.5F) * $$5;
/*      */               
/*  400 */               $$15.set($$17, $$25, $$16);
/*  401 */               int $$45 = getLightColor((BlockAndTintGetter)clientLevel, (BlockPos)$$15);
/*      */               
/*  403 */               int $$46 = $$45 >> 16 & 0xFFFF;
/*  404 */               int $$47 = $$45 & 0xFFFF;
/*      */               
/*  406 */               int $$48 = ($$46 * 3 + 240) / 4;
/*  407 */               int $$49 = ($$47 * 3 + 240) / 4;
/*      */               
/*  409 */               $$11.vertex($$17 - $$2 - $$19 + 0.5D, $$24 - $$3, $$16 - $$4 - $$20 + 0.5D).uv(0.0F + $$39, $$23 * 0.25F + $$38 + $$40).color(1.0F, 1.0F, 1.0F, $$44).uv2($$49, $$48).endVertex();
/*  410 */               $$11.vertex($$17 - $$2 + $$19 + 0.5D, $$24 - $$3, $$16 - $$4 + $$20 + 0.5D).uv(1.0F + $$39, $$23 * 0.25F + $$38 + $$40).color(1.0F, 1.0F, 1.0F, $$44).uv2($$49, $$48).endVertex();
/*  411 */               $$11.vertex($$17 - $$2 + $$19 + 0.5D, $$23 - $$3, $$16 - $$4 + $$20 + 0.5D).uv(1.0F + $$39, $$24 * 0.25F + $$38 + $$40).color(1.0F, 1.0F, 1.0F, $$44).uv2($$49, $$48).endVertex();
/*  412 */               $$11.vertex($$17 - $$2 - $$19 + 0.5D, $$23 - $$3, $$16 - $$4 - $$20 + 0.5D).uv(0.0F + $$39, $$24 * 0.25F + $$38 + $$40).color(1.0F, 1.0F, 1.0F, $$44).uv2($$49, $$48).endVertex();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  417 */     }  if ($$13 >= 0) {
/*  418 */       $$10.end();
/*      */     }
/*      */     
/*  421 */     RenderSystem.enableCull();
/*  422 */     RenderSystem.disableBlend();
/*  423 */     $$0.turnOffLightLayer();
/*      */   }
/*      */   
/*      */   public void tickRain(Camera $$0) {
/*  427 */     float $$1 = this.minecraft.level.getRainLevel(1.0F) / (Minecraft.useFancyGraphics() ? 1.0F : 2.0F);
/*  428 */     if ($$1 <= 0.0F) {
/*      */       return;
/*      */     }
/*      */     
/*  432 */     RandomSource $$2 = RandomSource.create(this.ticks * 312987231L);
/*  433 */     ClientLevel clientLevel = this.minecraft.level;
/*  434 */     BlockPos $$4 = BlockPos.containing((Position)$$0.getPosition());
/*      */     
/*  436 */     BlockPos $$5 = null;
/*      */     
/*  438 */     int $$6 = (int)(100.0F * $$1 * $$1) / ((this.minecraft.options.particles().get() == ParticleStatus.DECREASED) ? 2 : 1);
/*  439 */     for (int $$7 = 0; $$7 < $$6; $$7++) {
/*  440 */       int $$8 = $$2.nextInt(21) - 10;
/*  441 */       int $$9 = $$2.nextInt(21) - 10;
/*      */ 
/*      */       
/*  444 */       BlockPos $$10 = clientLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, $$4.offset($$8, 0, $$9));
/*  445 */       if ($$10.getY() > clientLevel.getMinBuildHeight() && $$10.getY() <= $$4.getY() + 10 && $$10.getY() >= $$4.getY() - 10) {
/*      */ 
/*      */ 
/*      */         
/*  449 */         Biome $$11 = (Biome)clientLevel.getBiome($$10).value();
/*  450 */         if ($$11.getPrecipitationAt($$10) == Biome.Precipitation.RAIN) {
/*  451 */           $$5 = $$10.below();
/*      */           
/*  453 */           if (this.minecraft.options.particles().get() == ParticleStatus.MINIMAL) {
/*      */             break;
/*      */           }
/*      */ 
/*      */           
/*  458 */           double $$12 = $$2.nextDouble();
/*  459 */           double $$13 = $$2.nextDouble();
/*      */           
/*  461 */           BlockState $$14 = clientLevel.getBlockState($$5);
/*  462 */           FluidState $$15 = clientLevel.getFluidState($$5);
/*  463 */           VoxelShape $$16 = $$14.getCollisionShape((BlockGetter)clientLevel, $$5);
/*      */           
/*  465 */           double $$17 = $$16.max(Direction.Axis.Y, $$12, $$13);
/*  466 */           double $$18 = $$15.getHeight((BlockGetter)clientLevel, $$5);
/*  467 */           double $$19 = Math.max($$17, $$18);
/*      */           
/*  469 */           SimpleParticleType simpleParticleType = ($$15.is(FluidTags.LAVA) || $$14.is(Blocks.MAGMA_BLOCK) || CampfireBlock.isLitCampfire($$14)) ? ParticleTypes.SMOKE : ParticleTypes.RAIN;
/*  470 */           this.minecraft.level.addParticle((ParticleOptions)simpleParticleType, $$5.getX() + $$12, $$5.getY() + $$19, $$5.getZ() + $$13, 0.0D, 0.0D, 0.0D);
/*      */         } 
/*      */       } 
/*      */     } 
/*  474 */     if ($$5 != null && $$2.nextInt(3) < this.rainSoundTime++) {
/*  475 */       this.rainSoundTime = 0;
/*  476 */       if ($$5.getY() > $$4.getY() + 1 && clientLevel.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, $$4).getY() > Mth.floor($$4.getY())) {
/*      */         
/*  478 */         this.minecraft.level.playLocalSound($$5, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
/*      */       } else {
/*  480 */         this.minecraft.level.playLocalSound($$5, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() {
/*  488 */     if (this.entityEffect != null) {
/*  489 */       this.entityEffect.close();
/*      */     }
/*      */     
/*  492 */     if (this.transparencyChain != null) {
/*  493 */       this.transparencyChain.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(ResourceManager $$0) {
/*  499 */     initOutline();
/*  500 */     if (Minecraft.useShaderTransparency()) {
/*  501 */       initTransparency();
/*      */     }
/*      */   }
/*      */   
/*      */   public void initOutline() {
/*  506 */     if (this.entityEffect != null) {
/*  507 */       this.entityEffect.close();
/*      */     }
/*  509 */     ResourceLocation $$0 = new ResourceLocation("shaders/post/entity_outline.json");
/*      */     try {
/*  511 */       this.entityEffect = new PostChain(this.minecraft.getTextureManager(), this.minecraft.getResourceManager(), this.minecraft.getMainRenderTarget(), $$0);
/*  512 */       this.entityEffect.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
/*  513 */       this.entityTarget = this.entityEffect.getTempTarget("final");
/*  514 */     } catch (IOException $$1) {
/*  515 */       LOGGER.warn("Failed to load shader: {}", $$0, $$1);
/*  516 */       this.entityEffect = null;
/*  517 */       this.entityTarget = null;
/*  518 */     } catch (JsonSyntaxException $$2) {
/*  519 */       LOGGER.warn("Failed to parse shader: {}", $$0, $$2);
/*  520 */       this.entityEffect = null;
/*  521 */       this.entityTarget = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initTransparency() {
/*  526 */     deinitTransparency();
/*      */     
/*  528 */     ResourceLocation $$0 = new ResourceLocation("shaders/post/transparency.json");
/*      */     try {
/*  530 */       PostChain $$1 = new PostChain(this.minecraft.getTextureManager(), this.minecraft.getResourceManager(), this.minecraft.getMainRenderTarget(), $$0);
/*  531 */       $$1.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
/*      */       
/*  533 */       RenderTarget $$2 = $$1.getTempTarget("translucent");
/*  534 */       RenderTarget $$3 = $$1.getTempTarget("itemEntity");
/*  535 */       RenderTarget $$4 = $$1.getTempTarget("particles");
/*  536 */       RenderTarget $$5 = $$1.getTempTarget("weather");
/*  537 */       RenderTarget $$6 = $$1.getTempTarget("clouds");
/*      */       
/*  539 */       this.transparencyChain = $$1;
/*  540 */       this.translucentTarget = $$2;
/*  541 */       this.itemEntityTarget = $$3;
/*  542 */       this.particlesTarget = $$4;
/*  543 */       this.weatherTarget = $$5;
/*  544 */       this.cloudsTarget = $$6;
/*  545 */     } catch (Exception $$7) {
/*  546 */       String $$8 = ($$7 instanceof JsonSyntaxException) ? "parse" : "load";
/*  547 */       String $$9 = "Failed to " + $$8 + " shader: " + $$0;
/*  548 */       TransparencyShaderException $$10 = new TransparencyShaderException($$9, $$7);
/*      */ 
/*      */       
/*  551 */       if (this.minecraft.getResourcePackRepository().getSelectedIds().size() > 1) {
/*  552 */         Component $$11 = this.minecraft.getResourceManager().listPacks().findFirst().map($$0 -> Component.literal($$0.packId())).orElse(null);
/*  553 */         this.minecraft.options.graphicsMode().set(GraphicsStatus.FANCY);
/*  554 */         this.minecraft.clearResourcePacksOnError($$10, $$11, null);
/*      */       } else {
/*      */         
/*  557 */         this.minecraft.options.graphicsMode().set(GraphicsStatus.FANCY);
/*  558 */         this.minecraft.options.save();
/*      */ 
/*      */         
/*  561 */         LOGGER.error(LogUtils.FATAL_MARKER, $$9, $$10);
/*  562 */         this.minecraft.emergencySaveAndCrash(new CrashReport($$9, $$10));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void deinitTransparency() {
/*  568 */     if (this.transparencyChain != null) {
/*  569 */       this.transparencyChain.close();
/*      */       
/*  571 */       this.translucentTarget.destroyBuffers();
/*  572 */       this.itemEntityTarget.destroyBuffers();
/*  573 */       this.particlesTarget.destroyBuffers();
/*  574 */       this.weatherTarget.destroyBuffers();
/*  575 */       this.cloudsTarget.destroyBuffers();
/*      */       
/*  577 */       this.transparencyChain = null;
/*  578 */       this.translucentTarget = null;
/*  579 */       this.itemEntityTarget = null;
/*  580 */       this.particlesTarget = null;
/*  581 */       this.weatherTarget = null;
/*  582 */       this.cloudsTarget = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void doEntityOutline() {
/*  587 */     if (shouldShowEntityOutlines()) {
/*  588 */       RenderSystem.enableBlend();
/*  589 */       RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
/*  590 */       this.entityTarget.blitToScreen(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight(), false);
/*  591 */       RenderSystem.disableBlend();
/*  592 */       RenderSystem.defaultBlendFunc();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean shouldShowEntityOutlines() {
/*  597 */     return (!this.minecraft.gameRenderer.isPanoramicMode() && this.entityTarget != null && this.entityEffect != null && this.minecraft.player != null);
/*      */   }
/*      */   
/*      */   private void createDarkSky() {
/*  601 */     Tesselator $$0 = Tesselator.getInstance();
/*  602 */     BufferBuilder $$1 = $$0.getBuilder();
/*      */     
/*  604 */     if (this.darkBuffer != null) {
/*  605 */       this.darkBuffer.close();
/*      */     }
/*      */     
/*  608 */     this.darkBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
/*  609 */     BufferBuilder.RenderedBuffer $$2 = buildSkyDisc($$1, -16.0F);
/*      */     
/*  611 */     this.darkBuffer.bind();
/*  612 */     this.darkBuffer.upload($$2);
/*  613 */     VertexBuffer.unbind();
/*      */   }
/*      */   
/*      */   private void createLightSky() {
/*  617 */     Tesselator $$0 = Tesselator.getInstance();
/*  618 */     BufferBuilder $$1 = $$0.getBuilder();
/*      */     
/*  620 */     if (this.skyBuffer != null) {
/*  621 */       this.skyBuffer.close();
/*      */     }
/*      */     
/*  624 */     this.skyBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
/*  625 */     BufferBuilder.RenderedBuffer $$2 = buildSkyDisc($$1, 16.0F);
/*      */     
/*  627 */     this.skyBuffer.bind();
/*  628 */     this.skyBuffer.upload($$2);
/*  629 */     VertexBuffer.unbind();
/*      */   }
/*      */   
/*      */   private static BufferBuilder.RenderedBuffer buildSkyDisc(BufferBuilder $$0, float $$1) {
/*  633 */     float $$2 = Math.signum($$1) * 512.0F;
/*  634 */     float $$3 = 512.0F;
/*      */     
/*  636 */     RenderSystem.setShader(GameRenderer::getPositionShader);
/*  637 */     $$0.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION);
/*  638 */     $$0.vertex(0.0D, $$1, 0.0D).endVertex();
/*  639 */     for (int $$4 = -180; $$4 <= 180; $$4 += 45) {
/*  640 */       $$0.vertex(($$2 * Mth.cos($$4 * 0.017453292F)), $$1, (512.0F * Mth.sin($$4 * 0.017453292F))).endVertex();
/*      */     }
/*  642 */     return $$0.end();
/*      */   }
/*      */   
/*      */   private void createStars() {
/*  646 */     Tesselator $$0 = Tesselator.getInstance();
/*  647 */     BufferBuilder $$1 = $$0.getBuilder();
/*      */     
/*  649 */     RenderSystem.setShader(GameRenderer::getPositionShader);
/*      */     
/*  651 */     if (this.starBuffer != null) {
/*  652 */       this.starBuffer.close();
/*      */     }
/*      */     
/*  655 */     this.starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
/*  656 */     BufferBuilder.RenderedBuffer $$2 = drawStars($$1);
/*      */     
/*  658 */     this.starBuffer.bind();
/*  659 */     this.starBuffer.upload($$2);
/*  660 */     VertexBuffer.unbind();
/*      */   }
/*      */   
/*      */   private BufferBuilder.RenderedBuffer drawStars(BufferBuilder $$0) {
/*  664 */     RandomSource $$1 = RandomSource.create(10842L);
/*      */     
/*  666 */     $$0.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
/*  667 */     for (int $$2 = 0; $$2 < 1500; $$2++) {
/*  668 */       double $$3 = ($$1.nextFloat() * 2.0F - 1.0F);
/*  669 */       double $$4 = ($$1.nextFloat() * 2.0F - 1.0F);
/*  670 */       double $$5 = ($$1.nextFloat() * 2.0F - 1.0F);
/*  671 */       double $$6 = (0.15F + $$1.nextFloat() * 0.1F);
/*  672 */       double $$7 = $$3 * $$3 + $$4 * $$4 + $$5 * $$5;
/*  673 */       if ($$7 < 1.0D && $$7 > 0.01D) {
/*  674 */         $$7 = 1.0D / Math.sqrt($$7);
/*  675 */         $$3 *= $$7;
/*  676 */         $$4 *= $$7;
/*  677 */         $$5 *= $$7;
/*  678 */         double $$8 = $$3 * 100.0D;
/*  679 */         double $$9 = $$4 * 100.0D;
/*  680 */         double $$10 = $$5 * 100.0D;
/*      */         
/*  682 */         double $$11 = Math.atan2($$3, $$5);
/*  683 */         double $$12 = Math.sin($$11);
/*  684 */         double $$13 = Math.cos($$11);
/*      */         
/*  686 */         double $$14 = Math.atan2(Math.sqrt($$3 * $$3 + $$5 * $$5), $$4);
/*  687 */         double $$15 = Math.sin($$14);
/*  688 */         double $$16 = Math.cos($$14);
/*      */         
/*  690 */         double $$17 = $$1.nextDouble() * Math.PI * 2.0D;
/*  691 */         double $$18 = Math.sin($$17);
/*  692 */         double $$19 = Math.cos($$17);
/*      */         
/*  694 */         for (int $$20 = 0; $$20 < 4; $$20++) {
/*  695 */           double $$21 = 0.0D;
/*  696 */           double $$22 = (($$20 & 0x2) - 1) * $$6;
/*  697 */           double $$23 = (($$20 + 1 & 0x2) - 1) * $$6;
/*      */           
/*  699 */           double $$24 = 0.0D;
/*  700 */           double $$25 = $$22 * $$19 - $$23 * $$18;
/*  701 */           double $$26 = $$23 * $$19 + $$22 * $$18;
/*      */           
/*  703 */           double $$27 = $$26;
/*  704 */           double $$28 = $$25 * $$15 + 0.0D * $$16;
/*  705 */           double $$29 = 0.0D * $$15 - $$25 * $$16;
/*      */           
/*  707 */           double $$30 = $$29 * $$12 - $$27 * $$13;
/*  708 */           double $$31 = $$28;
/*  709 */           double $$32 = $$27 * $$12 + $$29 * $$13;
/*      */           
/*  711 */           $$0.vertex($$8 + $$30, $$9 + $$31, $$10 + $$32).endVertex();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  716 */     return $$0.end();
/*      */   }
/*      */   
/*      */   public void setLevel(@Nullable ClientLevel $$0) {
/*  720 */     this.lastCameraSectionX = Integer.MIN_VALUE;
/*  721 */     this.lastCameraSectionY = Integer.MIN_VALUE;
/*  722 */     this.lastCameraSectionZ = Integer.MIN_VALUE;
/*      */     
/*  724 */     this.entityRenderDispatcher.setLevel((Level)$$0);
/*  725 */     this.level = $$0;
/*  726 */     if ($$0 != null) {
/*  727 */       allChanged();
/*      */     } else {
/*  729 */       if (this.viewArea != null) {
/*  730 */         this.viewArea.releaseAllBuffers();
/*  731 */         this.viewArea = null;
/*      */       } 
/*  733 */       if (this.sectionRenderDispatcher != null) {
/*  734 */         this.sectionRenderDispatcher.dispose();
/*      */       }
/*  736 */       this.sectionRenderDispatcher = null;
/*  737 */       this.globalBlockEntities.clear();
/*  738 */       this.sectionOcclusionGraph.waitAndReset(null);
/*  739 */       this.visibleSections.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void graphicsChanged() {
/*  744 */     if (Minecraft.useShaderTransparency()) {
/*  745 */       initTransparency();
/*      */     } else {
/*  747 */       deinitTransparency();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void allChanged() {
/*  752 */     if (this.level == null) {
/*      */       return;
/*      */     }
/*      */     
/*  756 */     graphicsChanged();
/*      */     
/*  758 */     this.level.clearTintCaches();
/*      */     
/*  760 */     if (this.sectionRenderDispatcher == null) {
/*  761 */       this.sectionRenderDispatcher = new SectionRenderDispatcher(this.level, this, Util.backgroundExecutor(), this.renderBuffers);
/*      */     } else {
/*  763 */       this.sectionRenderDispatcher.setLevel(this.level);
/*      */     } 
/*  765 */     this.generateClouds = true;
/*      */     
/*  767 */     ItemBlockRenderTypes.setFancy(Minecraft.useFancyGraphics());
/*      */     
/*  769 */     this.lastViewDistance = this.minecraft.options.getEffectiveRenderDistance();
/*      */     
/*  771 */     if (this.viewArea != null) {
/*  772 */       this.viewArea.releaseAllBuffers();
/*      */     }
/*      */     
/*  775 */     this.sectionRenderDispatcher.blockUntilClear();
/*      */     
/*  777 */     synchronized (this.globalBlockEntities) {
/*  778 */       this.globalBlockEntities.clear();
/*      */     } 
/*      */     
/*  781 */     this.viewArea = new ViewArea(this.sectionRenderDispatcher, (Level)this.level, this.minecraft.options.getEffectiveRenderDistance(), this);
/*  782 */     this.sectionOcclusionGraph.waitAndReset(this.viewArea);
/*  783 */     this.visibleSections.clear();
/*      */     
/*  785 */     Entity $$0 = this.minecraft.getCameraEntity();
/*  786 */     if ($$0 != null) {
/*  787 */       this.viewArea.repositionCamera($$0.getX(), $$0.getZ());
/*      */     }
/*      */   }
/*      */   
/*      */   public void resize(int $$0, int $$1) {
/*  792 */     needsUpdate();
/*      */     
/*  794 */     if (this.entityEffect != null) {
/*  795 */       this.entityEffect.resize($$0, $$1);
/*      */     }
/*  797 */     if (this.transparencyChain != null) {
/*  798 */       this.transparencyChain.resize($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   public String getSectionStatistics() {
/*  803 */     int $$0 = this.viewArea.sections.length;
/*  804 */     int $$1 = countRenderedSections();
/*      */     
/*  806 */     return String.format(Locale.ROOT, "C: %d/%d %sD: %d, %s", new Object[] {
/*  807 */           Integer.valueOf($$1), 
/*  808 */           Integer.valueOf($$0), 
/*  809 */           this.minecraft.smartCull ? "(s) " : "", 
/*  810 */           Integer.valueOf(this.lastViewDistance), 
/*  811 */           (this.sectionRenderDispatcher == null) ? "null" : this.sectionRenderDispatcher.getStats()
/*      */         });
/*      */   }
/*      */   
/*      */   public SectionRenderDispatcher getSectionRenderDispatcher() {
/*  816 */     return this.sectionRenderDispatcher;
/*      */   }
/*      */   
/*      */   public double getTotalSections() {
/*  820 */     return this.viewArea.sections.length;
/*      */   }
/*      */   
/*      */   public double getLastViewDistance() {
/*  824 */     return this.lastViewDistance;
/*      */   }
/*      */   
/*      */   public int countRenderedSections() {
/*  828 */     int $$0 = 0;
/*  829 */     for (ObjectListIterator<SectionRenderDispatcher.RenderSection> objectListIterator = this.visibleSections.iterator(); objectListIterator.hasNext(); ) { SectionRenderDispatcher.RenderSection $$1 = objectListIterator.next();
/*  830 */       if (!$$1.getCompiled().hasNoRenderableLayers()) {
/*  831 */         $$0++;
/*      */       } }
/*      */     
/*  834 */     return $$0;
/*      */   }
/*      */   
/*      */   public String getEntityStatistics() {
/*  838 */     return "E: " + this.renderedEntities + "/" + this.level.getEntityCount() + ", B: " + this.culledEntities + ", SD: " + this.level.getServerSimulationDistance();
/*      */   }
/*      */   
/*      */   private void setupRender(Camera $$0, Frustum $$1, boolean $$2, boolean $$3) {
/*  842 */     Vec3 $$4 = $$0.getPosition();
/*      */     
/*  844 */     if (this.minecraft.options.getEffectiveRenderDistance() != this.lastViewDistance) {
/*  845 */       allChanged();
/*      */     }
/*      */     
/*  848 */     this.level.getProfiler().push("camera");
/*  849 */     double $$5 = this.minecraft.player.getX();
/*  850 */     double $$6 = this.minecraft.player.getY();
/*  851 */     double $$7 = this.minecraft.player.getZ();
/*  852 */     int $$8 = SectionPos.posToSectionCoord($$5);
/*  853 */     int $$9 = SectionPos.posToSectionCoord($$6);
/*  854 */     int $$10 = SectionPos.posToSectionCoord($$7);
/*  855 */     if (this.lastCameraSectionX != $$8 || this.lastCameraSectionY != $$9 || this.lastCameraSectionZ != $$10) {
/*  856 */       this.lastCameraSectionX = $$8;
/*  857 */       this.lastCameraSectionY = $$9;
/*  858 */       this.lastCameraSectionZ = $$10;
/*      */       
/*  860 */       this.viewArea.repositionCamera($$5, $$7);
/*      */     } 
/*      */     
/*  863 */     this.sectionRenderDispatcher.setCamera($$4);
/*      */     
/*  865 */     this.level.getProfiler().popPush("cull");
/*      */     
/*  867 */     this.minecraft.getProfiler().popPush("culling");
/*      */ 
/*      */     
/*  870 */     BlockPos $$11 = $$0.getBlockPosition();
/*      */     
/*  872 */     double $$12 = Math.floor($$4.x / 8.0D);
/*  873 */     double $$13 = Math.floor($$4.y / 8.0D);
/*  874 */     double $$14 = Math.floor($$4.z / 8.0D);
/*      */     
/*  876 */     if ($$12 != this.prevCamX || $$13 != this.prevCamY || $$14 != this.prevCamZ) {
/*  877 */       this.sectionOcclusionGraph.invalidate();
/*      */     }
/*      */     
/*  880 */     this.prevCamX = $$12;
/*  881 */     this.prevCamY = $$13;
/*  882 */     this.prevCamZ = $$14;
/*      */     
/*  884 */     this.minecraft.getProfiler().popPush("update");
/*  885 */     if (!$$2) {
/*  886 */       boolean $$15 = this.minecraft.smartCull;
/*  887 */       if ($$3 && this.level.getBlockState($$11).isSolidRender((BlockGetter)this.level, $$11)) {
/*  888 */         $$15 = false;
/*      */       }
/*      */       
/*  891 */       Entity.setViewScale(Mth.clamp(this.minecraft.options.getEffectiveRenderDistance() / 8.0D, 1.0D, 2.5D) * ((Double)this.minecraft.options.entityDistanceScaling().get()).doubleValue());
/*  892 */       this.minecraft.getProfiler().push("section_occlusion_graph");
/*  893 */       this.sectionOcclusionGraph.update($$15, $$0, $$1, (List<SectionRenderDispatcher.RenderSection>)this.visibleSections);
/*  894 */       this.minecraft.getProfiler().pop();
/*      */       
/*  896 */       double $$16 = Math.floor(($$0.getXRot() / 2.0F));
/*  897 */       double $$17 = Math.floor(($$0.getYRot() / 2.0F));
/*  898 */       if (this.sectionOcclusionGraph.consumeFrustumUpdate() || $$16 != this.prevCamRotX || $$17 != this.prevCamRotY) {
/*  899 */         applyFrustum(offsetFrustum($$1));
/*  900 */         this.prevCamRotX = $$16;
/*  901 */         this.prevCamRotY = $$17;
/*      */       } 
/*      */     } 
/*  904 */     this.minecraft.getProfiler().pop();
/*      */   }
/*      */   
/*      */   public static Frustum offsetFrustum(Frustum $$0) {
/*  908 */     return (new Frustum($$0)).offsetToFullyIncludeCameraCube(8);
/*      */   }
/*      */   
/*      */   private void applyFrustum(Frustum $$0) {
/*  912 */     if (!Minecraft.getInstance().isSameThread()) {
/*  913 */       throw new IllegalStateException("applyFrustum called from wrong thread: " + Thread.currentThread().getName());
/*      */     }
/*  915 */     this.minecraft.getProfiler().push("apply_frustum");
/*  916 */     this.visibleSections.clear();
/*  917 */     this.sectionOcclusionGraph.addSectionsInFrustum($$0, (List<SectionRenderDispatcher.RenderSection>)this.visibleSections);
/*  918 */     this.minecraft.getProfiler().pop();
/*      */   }
/*      */   
/*      */   public void addRecentlyCompiledSection(SectionRenderDispatcher.RenderSection $$0) {
/*  922 */     this.sectionOcclusionGraph.onSectionCompiled($$0);
/*      */   }
/*      */   
/*      */   private void captureFrustum(Matrix4f $$0, Matrix4f $$1, double $$2, double $$3, double $$4, Frustum $$5) {
/*  926 */     this.capturedFrustum = $$5;
/*      */     
/*  928 */     Matrix4f $$6 = new Matrix4f((Matrix4fc)$$1);
/*  929 */     $$6.mul((Matrix4fc)$$0);
/*  930 */     $$6.invert();
/*      */     
/*  932 */     this.frustumPos.x = $$2;
/*  933 */     this.frustumPos.y = $$3;
/*  934 */     this.frustumPos.z = $$4;
/*      */ 
/*      */     
/*  937 */     this.frustumPoints[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
/*  938 */     this.frustumPoints[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
/*  939 */     this.frustumPoints[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
/*  940 */     this.frustumPoints[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
/*      */ 
/*      */     
/*  943 */     this.frustumPoints[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
/*  944 */     this.frustumPoints[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
/*  945 */     this.frustumPoints[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  946 */     this.frustumPoints[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/*  948 */     for (int $$7 = 0; $$7 < 8; $$7++) {
/*  949 */       $$6.transform(this.frustumPoints[$$7]);
/*  950 */       this.frustumPoints[$$7].div(this.frustumPoints[$$7].w());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void prepareCullFrustum(PoseStack $$0, Vec3 $$1, Matrix4f $$2) {
/*  955 */     Matrix4f $$3 = $$0.last().pose();
/*      */     
/*  957 */     double $$4 = $$1.x();
/*  958 */     double $$5 = $$1.y();
/*  959 */     double $$6 = $$1.z();
/*      */     
/*  961 */     this.cullingFrustum = new Frustum($$3, $$2);
/*  962 */     this.cullingFrustum.prepare($$4, $$5, $$6);
/*      */   }
/*      */   public void renderLevel(PoseStack $$0, float $$1, long $$2, boolean $$3, Camera $$4, GameRenderer $$5, LightTexture $$6, Matrix4f $$7) {
/*      */     Frustum $$18;
/*  966 */     TickRateManager $$8 = this.minecraft.level.tickRateManager();
/*  967 */     float $$9 = $$8.runsNormally() ? $$1 : 1.0F;
/*      */     
/*  969 */     RenderSystem.setShaderGameTime(this.level.getGameTime(), $$9);
/*      */     
/*  971 */     this.blockEntityRenderDispatcher.prepare((Level)this.level, $$4, this.minecraft.hitResult);
/*  972 */     this.entityRenderDispatcher.prepare((Level)this.level, $$4, this.minecraft.crosshairPickEntity);
/*      */     
/*  974 */     ProfilerFiller $$10 = this.level.getProfiler();
/*  975 */     $$10.popPush("light_update_queue");
/*  976 */     this.level.pollLightUpdates();
/*  977 */     $$10.popPush("light_updates");
/*  978 */     this.level.getChunkSource().getLightEngine().runLightUpdates();
/*      */     
/*  980 */     Vec3 $$11 = $$4.getPosition();
/*  981 */     double $$12 = $$11.x();
/*  982 */     double $$13 = $$11.y();
/*  983 */     double $$14 = $$11.z();
/*      */     
/*  985 */     Matrix4f $$15 = $$0.last().pose();
/*      */     
/*  987 */     $$10.popPush("culling");
/*  988 */     boolean $$16 = (this.capturedFrustum != null);
/*      */ 
/*      */     
/*  991 */     if ($$16) {
/*  992 */       Frustum $$17 = this.capturedFrustum;
/*  993 */       $$17.prepare(this.frustumPos.x, this.frustumPos.y, this.frustumPos.z);
/*      */     } else {
/*  995 */       $$18 = this.cullingFrustum;
/*      */     } 
/*      */     
/*  998 */     this.minecraft.getProfiler().popPush("captureFrustum");
/*  999 */     if (this.captureFrustum) {
/* 1000 */       captureFrustum($$15, $$7, $$11.x, $$11.y, $$11.z, $$16 ? new Frustum($$15, $$7) : $$18);
/* 1001 */       this.captureFrustum = false;
/*      */     } 
/*      */     
/* 1004 */     $$10.popPush("clear");
/* 1005 */     FogRenderer.setupColor($$4, $$9, this.minecraft.level, this.minecraft.options.getEffectiveRenderDistance(), $$5.getDarkenWorldAmount($$9));
/* 1006 */     FogRenderer.levelFogColor();
/* 1007 */     RenderSystem.clear(16640, Minecraft.ON_OSX);
/*      */     
/* 1009 */     float $$19 = $$5.getRenderDistance();
/* 1010 */     boolean $$20 = (this.minecraft.level.effects().isFoggyAt(Mth.floor($$12), Mth.floor($$13)) || this.minecraft.gui.getBossOverlay().shouldCreateWorldFog());
/*      */     
/* 1012 */     $$10.popPush("sky");
/* 1013 */     RenderSystem.setShader(GameRenderer::getPositionShader);
/* 1014 */     renderSky($$0, $$7, $$9, $$4, $$20, () -> FogRenderer.setupFog($$0, FogRenderer.FogMode.FOG_SKY, $$1, $$2, $$3));
/*      */     
/* 1016 */     $$10.popPush("fog");
/* 1017 */     FogRenderer.setupFog($$4, FogRenderer.FogMode.FOG_TERRAIN, Math.max($$19, 32.0F), $$20, $$9);
/* 1018 */     $$10.popPush("terrain_setup");
/*      */     
/* 1020 */     setupRender($$4, $$18, $$16, this.minecraft.player.isSpectator());
/*      */     
/* 1022 */     $$10.popPush("compile_sections");
/* 1023 */     compileSections($$4);
/*      */     
/* 1025 */     $$10.popPush("terrain");
/* 1026 */     renderSectionLayer(RenderType.solid(), $$0, $$12, $$13, $$14, $$7);
/* 1027 */     renderSectionLayer(RenderType.cutoutMipped(), $$0, $$12, $$13, $$14, $$7);
/* 1028 */     renderSectionLayer(RenderType.cutout(), $$0, $$12, $$13, $$14, $$7);
/*      */     
/* 1030 */     if (this.level.effects().constantAmbientLight()) {
/* 1031 */       Lighting.setupNetherLevel($$0.last().pose());
/*      */     } else {
/* 1033 */       Lighting.setupLevel($$0.last().pose());
/*      */     } 
/*      */     
/* 1036 */     $$10.popPush("entities");
/*      */     
/* 1038 */     this.renderedEntities = 0;
/* 1039 */     this.culledEntities = 0;
/*      */     
/* 1041 */     if (this.itemEntityTarget != null) {
/* 1042 */       this.itemEntityTarget.clear(Minecraft.ON_OSX);
/* 1043 */       this.itemEntityTarget.copyDepthFrom(this.minecraft.getMainRenderTarget());
/* 1044 */       this.minecraft.getMainRenderTarget().bindWrite(false);
/*      */     } 
/*      */     
/* 1047 */     if (this.weatherTarget != null) {
/* 1048 */       this.weatherTarget.clear(Minecraft.ON_OSX);
/*      */     }
/*      */     
/* 1051 */     if (shouldShowEntityOutlines()) {
/* 1052 */       this.entityTarget.clear(Minecraft.ON_OSX);
/* 1053 */       this.minecraft.getMainRenderTarget().bindWrite(false);
/*      */     } 
/*      */     
/* 1056 */     boolean $$21 = false;
/*      */     
/* 1058 */     MultiBufferSource.BufferSource $$22 = this.renderBuffers.bufferSource();
/*      */     
/* 1060 */     for (Entity $$23 : this.level.entitiesForRendering()) {
/* 1061 */       MultiBufferSource $$28; if (!this.entityRenderDispatcher.shouldRender($$23, $$18, $$12, $$13, $$14) && !$$23.hasIndirectPassenger((Entity)this.minecraft.player)) {
/*      */         continue;
/*      */       }
/* 1064 */       BlockPos $$24 = $$23.blockPosition();
/* 1065 */       if (!this.level.isOutsideBuildHeight($$24.getY()) && !isSectionCompiled($$24)) {
/*      */         continue;
/*      */       }
/* 1068 */       if ($$23 == $$4.getEntity() && !$$4.isDetached() && (!($$4.getEntity() instanceof LivingEntity) || !((LivingEntity)$$4.getEntity()).isSleeping())) {
/*      */         continue;
/*      */       }
/* 1071 */       if ($$23 instanceof net.minecraft.client.player.LocalPlayer && $$4.getEntity() != $$23) {
/*      */         continue;
/*      */       }
/*      */       
/* 1075 */       this.renderedEntities++;
/* 1076 */       if ($$23.tickCount == 0) {
/* 1077 */         $$23.xOld = $$23.getX();
/* 1078 */         $$23.yOld = $$23.getY();
/* 1079 */         $$23.zOld = $$23.getZ();
/*      */       } 
/*      */ 
/*      */       
/* 1083 */       if (shouldShowEntityOutlines() && this.minecraft.shouldEntityAppearGlowing($$23)) {
/* 1084 */         $$21 = true;
/* 1085 */         OutlineBufferSource $$25 = this.renderBuffers.outlineBufferSource();
/* 1086 */         MultiBufferSource $$26 = $$25;
/* 1087 */         int $$27 = $$23.getTeamColor();
/* 1088 */         $$25.setColor(
/* 1089 */             FastColor.ARGB32.red($$27), 
/* 1090 */             FastColor.ARGB32.green($$27), 
/* 1091 */             FastColor.ARGB32.blue($$27), 255);
/*      */       }
/*      */       else {
/*      */         
/* 1095 */         $$28 = $$22;
/*      */       } 
/* 1097 */       float $$29 = $$8.isEntityFrozen($$23) ? $$9 : $$1;
/* 1098 */       renderEntity($$23, $$12, $$13, $$14, $$29, $$0, $$28);
/*      */     } 
/*      */     
/* 1101 */     $$22.endLastBatch();
/*      */     
/* 1103 */     checkPoseStack($$0);
/*      */     
/* 1105 */     $$22.endBatch(RenderType.entitySolid(TextureAtlas.LOCATION_BLOCKS));
/* 1106 */     $$22.endBatch(RenderType.entityCutout(TextureAtlas.LOCATION_BLOCKS));
/* 1107 */     $$22.endBatch(RenderType.entityCutoutNoCull(TextureAtlas.LOCATION_BLOCKS));
/* 1108 */     $$22.endBatch(RenderType.entitySmoothCutout(TextureAtlas.LOCATION_BLOCKS));
/*      */     
/* 1110 */     $$10.popPush("blockentities");
/*      */     
/* 1112 */     for (ObjectListIterator<SectionRenderDispatcher.RenderSection> objectListIterator = this.visibleSections.iterator(); objectListIterator.hasNext(); ) { SectionRenderDispatcher.RenderSection $$30 = objectListIterator.next();
/* 1113 */       List<BlockEntity> $$31 = $$30.getCompiled().getRenderableBlockEntities();
/* 1114 */       if ($$31.isEmpty()) {
/*      */         continue;
/*      */       }
/*      */       
/* 1118 */       for (BlockEntity $$32 : $$31) {
/* 1119 */         BlockPos $$33 = $$32.getBlockPos();
/* 1120 */         MultiBufferSource $$34 = $$22;
/*      */         
/* 1122 */         $$0.pushPose();
/* 1123 */         $$0.translate($$33.getX() - $$12, $$33.getY() - $$13, $$33.getZ() - $$14);
/*      */         
/* 1125 */         SortedSet<BlockDestructionProgress> $$35 = (SortedSet<BlockDestructionProgress>)this.destructionProgress.get($$33.asLong());
/* 1126 */         if ($$35 != null && !$$35.isEmpty()) {
/* 1127 */           int $$36 = ((BlockDestructionProgress)$$35.last()).getProgress();
/* 1128 */           if ($$36 >= 0) {
/* 1129 */             PoseStack.Pose $$37 = $$0.last();
/* 1130 */             SheetedDecalTextureGenerator sheetedDecalTextureGenerator = new SheetedDecalTextureGenerator(this.renderBuffers.crumblingBufferSource().getBuffer(ModelBakery.DESTROY_TYPES.get($$36)), $$37.pose(), $$37.normal(), 1.0F);
/* 1131 */             $$34 = ($$2 -> {
/*      */                 VertexConsumer $$3 = $$0.getBuffer($$2);
/*      */ 
/*      */                 
/*      */                 return $$2.affectsCrumbling() ? VertexMultiConsumer.create($$1, $$3) : $$3;
/*      */               });
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1141 */         this.blockEntityRenderDispatcher.render($$32, $$9, $$0, $$34);
/* 1142 */         $$0.popPose();
/*      */       }  }
/*      */ 
/*      */     
/* 1146 */     synchronized (this.globalBlockEntities) {
/* 1147 */       for (BlockEntity $$39 : this.globalBlockEntities) {
/* 1148 */         BlockPos $$40 = $$39.getBlockPos();
/* 1149 */         $$0.pushPose();
/* 1150 */         $$0.translate($$40.getX() - $$12, $$40.getY() - $$13, $$40.getZ() - $$14);
/* 1151 */         this.blockEntityRenderDispatcher.render($$39, $$9, $$0, $$22);
/* 1152 */         $$0.popPose();
/*      */       } 
/*      */     } 
/*      */     
/* 1156 */     checkPoseStack($$0);
/*      */ 
/*      */     
/* 1159 */     $$22.endBatch(RenderType.solid());
/* 1160 */     $$22.endBatch(RenderType.endPortal());
/* 1161 */     $$22.endBatch(RenderType.endGateway());
/* 1162 */     $$22.endBatch(Sheets.solidBlockSheet());
/* 1163 */     $$22.endBatch(Sheets.cutoutBlockSheet());
/* 1164 */     $$22.endBatch(Sheets.bedSheet());
/* 1165 */     $$22.endBatch(Sheets.shulkerBoxSheet());
/* 1166 */     $$22.endBatch(Sheets.signSheet());
/* 1167 */     $$22.endBatch(Sheets.hangingSignSheet());
/* 1168 */     $$22.endBatch(Sheets.chestSheet());
/*      */ 
/*      */     
/* 1171 */     this.renderBuffers.outlineBufferSource().endOutlineBatch();
/*      */ 
/*      */     
/* 1174 */     if ($$21) {
/* 1175 */       this.entityEffect.process($$9);
/* 1176 */       this.minecraft.getMainRenderTarget().bindWrite(false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1182 */     $$10.popPush("destroyProgress");
/*      */     
/* 1184 */     for (ObjectIterator<Long2ObjectMap.Entry<SortedSet<BlockDestructionProgress>>> objectIterator = this.destructionProgress.long2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ObjectMap.Entry<SortedSet<BlockDestructionProgress>> $$41 = objectIterator.next();
/* 1185 */       BlockPos $$42 = BlockPos.of($$41.getLongKey());
/*      */       
/* 1187 */       double $$43 = $$42.getX() - $$12;
/* 1188 */       double $$44 = $$42.getY() - $$13;
/* 1189 */       double $$45 = $$42.getZ() - $$14;
/* 1190 */       if ($$43 * $$43 + $$44 * $$44 + $$45 * $$45 > 1024.0D) {
/*      */         continue;
/*      */       }
/*      */       
/* 1194 */       SortedSet<BlockDestructionProgress> $$46 = (SortedSet<BlockDestructionProgress>)$$41.getValue();
/* 1195 */       if ($$46 == null || $$46.isEmpty()) {
/*      */         continue;
/*      */       }
/* 1198 */       int $$47 = ((BlockDestructionProgress)$$46.last()).getProgress();
/* 1199 */       $$0.pushPose();
/* 1200 */       $$0.translate($$42.getX() - $$12, $$42.getY() - $$13, $$42.getZ() - $$14);
/* 1201 */       PoseStack.Pose $$48 = $$0.last();
/* 1202 */       SheetedDecalTextureGenerator sheetedDecalTextureGenerator = new SheetedDecalTextureGenerator(this.renderBuffers.crumblingBufferSource().getBuffer(ModelBakery.DESTROY_TYPES.get($$47)), $$48.pose(), $$48.normal(), 1.0F);
/* 1203 */       this.minecraft.getBlockRenderer().renderBreakingTexture(this.level.getBlockState($$42), $$42, (BlockAndTintGetter)this.level, $$0, (VertexConsumer)sheetedDecalTextureGenerator);
/* 1204 */       $$0.popPose(); }
/*      */ 
/*      */     
/* 1207 */     checkPoseStack($$0);
/*      */     
/* 1209 */     HitResult $$50 = this.minecraft.hitResult;
/*      */     
/* 1211 */     if ($$3 && $$50 != null && $$50.getType() == HitResult.Type.BLOCK) {
/* 1212 */       $$10.popPush("outline");
/* 1213 */       BlockPos $$51 = ((BlockHitResult)$$50).getBlockPos();
/* 1214 */       BlockState $$52 = this.level.getBlockState($$51);
/* 1215 */       if (!$$52.isAir() && this.level.getWorldBorder().isWithinBounds($$51)) {
/* 1216 */         VertexConsumer $$53 = $$22.getBuffer(RenderType.lines());
/* 1217 */         renderHitOutline($$0, $$53, $$4.getEntity(), $$12, $$13, $$14, $$51, $$52);
/*      */       } 
/*      */     } 
/*      */     
/* 1221 */     this.minecraft.debugRenderer.render($$0, $$22, $$12, $$13, $$14);
/* 1222 */     $$22.endLastBatch();
/*      */     
/* 1224 */     PoseStack $$54 = RenderSystem.getModelViewStack();
/* 1225 */     RenderSystem.applyModelViewMatrix();
/*      */     
/* 1227 */     $$22.endBatch(Sheets.translucentCullBlockSheet());
/* 1228 */     $$22.endBatch(Sheets.bannerSheet());
/* 1229 */     $$22.endBatch(Sheets.shieldSheet());
/*      */ 
/*      */     
/* 1232 */     $$22.endBatch(RenderType.armorGlint());
/* 1233 */     $$22.endBatch(RenderType.armorEntityGlint());
/* 1234 */     $$22.endBatch(RenderType.glint());
/* 1235 */     $$22.endBatch(RenderType.glintDirect());
/* 1236 */     $$22.endBatch(RenderType.glintTranslucent());
/* 1237 */     $$22.endBatch(RenderType.entityGlint());
/* 1238 */     $$22.endBatch(RenderType.entityGlintDirect());
/*      */ 
/*      */     
/* 1241 */     $$22.endBatch(RenderType.waterMask());
/*      */     
/* 1243 */     this.renderBuffers.crumblingBufferSource().endBatch();
/*      */     
/* 1245 */     if (this.transparencyChain != null) {
/* 1246 */       $$22.endBatch(RenderType.lines());
/* 1247 */       $$22.endBatch();
/*      */       
/* 1249 */       this.translucentTarget.clear(Minecraft.ON_OSX);
/* 1250 */       this.translucentTarget.copyDepthFrom(this.minecraft.getMainRenderTarget());
/*      */       
/* 1252 */       $$10.popPush("translucent");
/* 1253 */       renderSectionLayer(RenderType.translucent(), $$0, $$12, $$13, $$14, $$7);
/*      */       
/* 1255 */       $$10.popPush("string");
/* 1256 */       renderSectionLayer(RenderType.tripwire(), $$0, $$12, $$13, $$14, $$7);
/*      */       
/* 1258 */       this.particlesTarget.clear(Minecraft.ON_OSX);
/* 1259 */       this.particlesTarget.copyDepthFrom(this.minecraft.getMainRenderTarget());
/* 1260 */       RenderStateShard.PARTICLES_TARGET.setupRenderState();
/*      */       
/* 1262 */       $$10.popPush("particles");
/* 1263 */       this.minecraft.particleEngine.render($$0, $$22, $$6, $$4, $$9);
/*      */       
/* 1265 */       RenderStateShard.PARTICLES_TARGET.clearRenderState();
/*      */     } else {
/* 1267 */       $$10.popPush("translucent");
/* 1268 */       if (this.translucentTarget != null) {
/* 1269 */         this.translucentTarget.clear(Minecraft.ON_OSX);
/*      */       }
/* 1271 */       renderSectionLayer(RenderType.translucent(), $$0, $$12, $$13, $$14, $$7);
/*      */       
/* 1273 */       $$22.endBatch(RenderType.lines());
/* 1274 */       $$22.endBatch();
/*      */       
/* 1276 */       $$10.popPush("string");
/* 1277 */       renderSectionLayer(RenderType.tripwire(), $$0, $$12, $$13, $$14, $$7);
/*      */       
/* 1279 */       $$10.popPush("particles");
/* 1280 */       this.minecraft.particleEngine.render($$0, $$22, $$6, $$4, $$9);
/*      */     } 
/*      */     
/* 1283 */     $$54.pushPose();
/* 1284 */     $$54.mulPoseMatrix($$0.last().pose());
/* 1285 */     RenderSystem.applyModelViewMatrix();
/*      */     
/* 1287 */     if (this.minecraft.options.getCloudsType() != CloudStatus.OFF) {
/* 1288 */       if (this.transparencyChain != null) {
/* 1289 */         this.cloudsTarget.clear(Minecraft.ON_OSX);
/* 1290 */         RenderStateShard.CLOUDS_TARGET.setupRenderState();
/*      */         
/* 1292 */         $$10.popPush("clouds");
/* 1293 */         renderClouds($$0, $$7, $$9, $$12, $$13, $$14);
/*      */         
/* 1295 */         RenderStateShard.CLOUDS_TARGET.clearRenderState();
/*      */       } else {
/* 1297 */         $$10.popPush("clouds");
/* 1298 */         RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
/* 1299 */         renderClouds($$0, $$7, $$9, $$12, $$13, $$14);
/*      */       } 
/*      */     }
/*      */     
/* 1303 */     if (this.transparencyChain != null) {
/* 1304 */       RenderStateShard.WEATHER_TARGET.setupRenderState();
/*      */       
/* 1306 */       $$10.popPush("weather");
/* 1307 */       renderSnowAndRain($$6, $$9, $$12, $$13, $$14);
/* 1308 */       renderWorldBorder($$4);
/*      */       
/* 1310 */       RenderStateShard.WEATHER_TARGET.clearRenderState();
/*      */       
/* 1312 */       this.transparencyChain.process($$9);
/* 1313 */       this.minecraft.getMainRenderTarget().bindWrite(false);
/*      */     } else {
/* 1315 */       RenderSystem.depthMask(false);
/*      */       
/* 1317 */       $$10.popPush("weather");
/* 1318 */       renderSnowAndRain($$6, $$9, $$12, $$13, $$14);
/* 1319 */       renderWorldBorder($$4);
/*      */       
/* 1321 */       RenderSystem.depthMask(true);
/*      */     } 
/*      */     
/* 1324 */     $$54.popPose();
/* 1325 */     RenderSystem.applyModelViewMatrix();
/*      */     
/* 1327 */     renderDebug($$0, $$22, $$4);
/* 1328 */     $$22.endLastBatch();
/*      */     
/* 1330 */     RenderSystem.depthMask(true);
/* 1331 */     RenderSystem.disableBlend();
/*      */     
/* 1333 */     FogRenderer.setupNoFog();
/*      */   }
/*      */   
/*      */   private void checkPoseStack(PoseStack $$0) {
/* 1337 */     if (!$$0.clear()) {
/* 1338 */       throw new IllegalStateException("Pose stack not empty");
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderEntity(Entity $$0, double $$1, double $$2, double $$3, float $$4, PoseStack $$5, MultiBufferSource $$6) {
/* 1343 */     double $$7 = Mth.lerp($$4, $$0.xOld, $$0.getX());
/* 1344 */     double $$8 = Mth.lerp($$4, $$0.yOld, $$0.getY());
/* 1345 */     double $$9 = Mth.lerp($$4, $$0.zOld, $$0.getZ());
/* 1346 */     float $$10 = Mth.lerp($$4, $$0.yRotO, $$0.getYRot());
/*      */     
/* 1348 */     this.entityRenderDispatcher.render($$0, $$7 - $$1, $$8 - $$2, $$9 - $$3, $$10, $$4, $$5, $$6, this.entityRenderDispatcher.getPackedLightCoords($$0, $$4));
/*      */   }
/*      */   
/*      */   private void renderSectionLayer(RenderType $$0, PoseStack $$1, double $$2, double $$3, double $$4, Matrix4f $$5) {
/* 1352 */     RenderSystem.assertOnRenderThread();
/*      */     
/* 1354 */     $$0.setupRenderState();
/* 1355 */     if ($$0 == RenderType.translucent()) {
/* 1356 */       this.minecraft.getProfiler().push("translucent_sort");
/*      */       
/* 1358 */       double $$6 = $$2 - this.xTransparentOld;
/* 1359 */       double $$7 = $$3 - this.yTransparentOld;
/* 1360 */       double $$8 = $$4 - this.zTransparentOld;
/* 1361 */       if ($$6 * $$6 + $$7 * $$7 + $$8 * $$8 > 1.0D) {
/* 1362 */         int $$9 = SectionPos.posToSectionCoord($$2);
/* 1363 */         int $$10 = SectionPos.posToSectionCoord($$3);
/* 1364 */         int $$11 = SectionPos.posToSectionCoord($$4);
/*      */ 
/*      */ 
/*      */         
/* 1368 */         boolean $$12 = ($$9 != SectionPos.posToSectionCoord(this.xTransparentOld) || $$11 != SectionPos.posToSectionCoord(this.zTransparentOld) || $$10 != SectionPos.posToSectionCoord(this.yTransparentOld));
/*      */         
/* 1370 */         this.xTransparentOld = $$2;
/* 1371 */         this.yTransparentOld = $$3;
/* 1372 */         this.zTransparentOld = $$4;
/*      */         
/* 1374 */         int $$13 = 0;
/* 1375 */         for (ObjectListIterator<SectionRenderDispatcher.RenderSection> objectListIterator = this.visibleSections.iterator(); objectListIterator.hasNext(); ) { SectionRenderDispatcher.RenderSection $$14 = objectListIterator.next();
/* 1376 */           if ($$13 < 15 && ($$12 || $$14.isAxisAlignedWith($$9, $$10, $$11)) && $$14.resortTransparency($$0, this.sectionRenderDispatcher)) {
/* 1377 */             $$13++;
/*      */           } }
/*      */       
/*      */       } 
/* 1381 */       this.minecraft.getProfiler().pop();
/*      */     } 
/*      */     
/* 1384 */     this.minecraft.getProfiler().push("filterempty");
/*      */     
/* 1386 */     this.minecraft.getProfiler().popPush(() -> "render_" + $$0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1396 */     boolean $$15 = ($$0 != RenderType.translucent());
/* 1397 */     ObjectListIterator<SectionRenderDispatcher.RenderSection> $$16 = this.visibleSections.listIterator($$15 ? 0 : this.visibleSections.size());
/*      */     
/* 1399 */     ShaderInstance $$17 = RenderSystem.getShader();
/*      */     
/* 1401 */     for (int $$18 = 0; $$18 < 12; $$18++) {
/* 1402 */       int $$19 = RenderSystem.getShaderTexture($$18);
/* 1403 */       $$17.setSampler("Sampler" + $$18, Integer.valueOf($$19));
/*      */     } 
/*      */     
/* 1406 */     if ($$17.MODEL_VIEW_MATRIX != null) {
/* 1407 */       $$17.MODEL_VIEW_MATRIX.set($$1.last().pose());
/*      */     }
/* 1409 */     if ($$17.PROJECTION_MATRIX != null) {
/* 1410 */       $$17.PROJECTION_MATRIX.set($$5);
/*      */     }
/*      */     
/* 1413 */     if ($$17.COLOR_MODULATOR != null) {
/* 1414 */       $$17.COLOR_MODULATOR.set(RenderSystem.getShaderColor());
/*      */     }
/*      */     
/* 1417 */     if ($$17.GLINT_ALPHA != null) {
/* 1418 */       $$17.GLINT_ALPHA.set(RenderSystem.getShaderGlintAlpha());
/*      */     }
/* 1420 */     if ($$17.FOG_START != null) {
/* 1421 */       $$17.FOG_START.set(RenderSystem.getShaderFogStart());
/*      */     }
/* 1423 */     if ($$17.FOG_END != null) {
/* 1424 */       $$17.FOG_END.set(RenderSystem.getShaderFogEnd());
/*      */     }
/* 1426 */     if ($$17.FOG_COLOR != null) {
/* 1427 */       $$17.FOG_COLOR.set(RenderSystem.getShaderFogColor());
/*      */     }
/* 1429 */     if ($$17.FOG_SHAPE != null) {
/* 1430 */       $$17.FOG_SHAPE.set(RenderSystem.getShaderFogShape().getIndex());
/*      */     }
/*      */     
/* 1433 */     if ($$17.TEXTURE_MATRIX != null) {
/* 1434 */       $$17.TEXTURE_MATRIX.set(RenderSystem.getTextureMatrix());
/*      */     }
/* 1436 */     if ($$17.GAME_TIME != null) {
/* 1437 */       $$17.GAME_TIME.set(RenderSystem.getShaderGameTime());
/*      */     }
/*      */     
/* 1440 */     RenderSystem.setupShaderLights($$17);
/* 1441 */     $$17.apply();
/*      */     
/* 1443 */     Uniform $$20 = $$17.CHUNK_OFFSET;
/*      */     
/* 1445 */     while ($$15 ? $$16.hasNext() : $$16.hasPrevious()) {
/* 1446 */       SectionRenderDispatcher.RenderSection $$21 = $$15 ? (SectionRenderDispatcher.RenderSection)$$16.next() : (SectionRenderDispatcher.RenderSection)$$16.previous();
/*      */       
/* 1448 */       if ($$21.getCompiled().isEmpty($$0)) {
/*      */         continue;
/*      */       }
/*      */       
/* 1452 */       VertexBuffer $$22 = $$21.getBuffer($$0);
/* 1453 */       BlockPos $$23 = $$21.getOrigin();
/*      */       
/* 1455 */       if ($$20 != null) {
/* 1456 */         $$20.set((float)($$23.getX() - $$2), (float)($$23.getY() - $$3), (float)($$23.getZ() - $$4));
/* 1457 */         $$20.upload();
/*      */       } 
/*      */       
/* 1460 */       $$22.bind();
/* 1461 */       $$22.draw();
/*      */     } 
/*      */     
/* 1464 */     if ($$20 != null) {
/* 1465 */       $$20.set(0.0F, 0.0F, 0.0F);
/*      */     }
/* 1467 */     $$17.clear();
/*      */     
/* 1469 */     VertexBuffer.unbind();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1475 */     this.minecraft.getProfiler().pop();
/*      */     
/* 1477 */     $$0.clearRenderState();
/*      */   }
/*      */   
/*      */   private void renderDebug(PoseStack $$0, MultiBufferSource $$1, Camera $$2) {
/* 1481 */     if (this.minecraft.sectionPath || this.minecraft.sectionVisibility) {
/* 1482 */       double $$3 = $$2.getPosition().x();
/* 1483 */       double $$4 = $$2.getPosition().y();
/* 1484 */       double $$5 = $$2.getPosition().z();
/*      */       
/* 1486 */       for (ObjectListIterator<SectionRenderDispatcher.RenderSection> objectListIterator = this.visibleSections.iterator(); objectListIterator.hasNext(); ) { SectionRenderDispatcher.RenderSection $$6 = objectListIterator.next();
/* 1487 */         SectionOcclusionGraph.Node $$7 = this.sectionOcclusionGraph.getNode($$6);
/* 1488 */         if ($$7 == null) {
/*      */           continue;
/*      */         }
/* 1491 */         BlockPos $$8 = $$6.getOrigin();
/*      */         
/* 1493 */         $$0.pushPose();
/* 1494 */         $$0.translate($$8.getX() - $$3, $$8.getY() - $$4, $$8.getZ() - $$5);
/* 1495 */         Matrix4f $$9 = $$0.last().pose();
/*      */         
/* 1497 */         if (this.minecraft.sectionPath) {
/* 1498 */           VertexConsumer $$10 = $$1.getBuffer(RenderType.lines());
/* 1499 */           int $$11 = ($$7.step == 0) ? 0 : Mth.hsvToRgb($$7.step / 50.0F, 0.9F, 0.9F);
/* 1500 */           int $$12 = $$11 >> 16 & 0xFF;
/* 1501 */           int $$13 = $$11 >> 8 & 0xFF;
/* 1502 */           int $$14 = $$11 & 0xFF;
/* 1503 */           for (int $$15 = 0; $$15 < DIRECTIONS.length; $$15++) {
/* 1504 */             if ($$7.hasSourceDirection($$15)) {
/* 1505 */               Direction $$16 = DIRECTIONS[$$15];
/* 1506 */               $$10.vertex($$9, 8.0F, 8.0F, 8.0F).color($$12, $$13, $$14, 255).normal($$16.getStepX(), $$16.getStepY(), $$16.getStepZ()).endVertex();
/* 1507 */               $$10.vertex($$9, (8 - 16 * $$16
/* 1508 */                   .getStepX()), (8 - 16 * $$16
/* 1509 */                   .getStepY()), (8 - 16 * $$16
/* 1510 */                   .getStepZ()))
/* 1511 */                 .color($$12, $$13, $$14, 255)
/* 1512 */                 .normal($$16.getStepX(), $$16.getStepY(), $$16.getStepZ()).endVertex();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1517 */         if (this.minecraft.sectionVisibility && !$$6.getCompiled().hasNoRenderableLayers()) {
/* 1518 */           VertexConsumer $$17 = $$1.getBuffer(RenderType.lines());
/*      */           
/* 1520 */           int $$18 = 0;
/* 1521 */           for (Direction $$19 : DIRECTIONS) {
/* 1522 */             for (Direction $$20 : DIRECTIONS) {
/* 1523 */               boolean $$21 = $$6.getCompiled().facesCanSeeEachother($$19, $$20);
/* 1524 */               if (!$$21) {
/* 1525 */                 $$18++;
/* 1526 */                 $$17.vertex($$9, (8 + 8 * $$19
/* 1527 */                     .getStepX()), (8 + 8 * $$19
/* 1528 */                     .getStepY()), (8 + 8 * $$19
/* 1529 */                     .getStepZ()))
/* 1530 */                   .color(255, 0, 0, 255)
/* 1531 */                   .normal($$19.getStepX(), $$19.getStepY(), $$19.getStepZ()).endVertex();
/* 1532 */                 $$17.vertex($$9, (8 + 8 * $$20
/* 1533 */                     .getStepX()), (8 + 8 * $$20
/* 1534 */                     .getStepY()), (8 + 8 * $$20
/* 1535 */                     .getStepZ()))
/* 1536 */                   .color(255, 0, 0, 255)
/* 1537 */                   .normal($$20.getStepX(), $$20.getStepY(), $$20.getStepZ()).endVertex();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/* 1542 */           if ($$18 > 0) {
/* 1543 */             VertexConsumer $$22 = $$1.getBuffer(RenderType.debugQuads());
/*      */             
/* 1545 */             float $$23 = 0.5F;
/* 1546 */             float $$24 = 0.2F;
/* 1547 */             $$22.vertex($$9, 0.5F, 15.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1548 */             $$22.vertex($$9, 15.5F, 15.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1549 */             $$22.vertex($$9, 15.5F, 15.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1550 */             $$22.vertex($$9, 0.5F, 15.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/*      */             
/* 1552 */             $$22.vertex($$9, 0.5F, 0.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1553 */             $$22.vertex($$9, 15.5F, 0.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1554 */             $$22.vertex($$9, 15.5F, 0.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1555 */             $$22.vertex($$9, 0.5F, 0.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/*      */             
/* 1557 */             $$22.vertex($$9, 0.5F, 15.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1558 */             $$22.vertex($$9, 0.5F, 15.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1559 */             $$22.vertex($$9, 0.5F, 0.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1560 */             $$22.vertex($$9, 0.5F, 0.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/*      */             
/* 1562 */             $$22.vertex($$9, 15.5F, 0.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1563 */             $$22.vertex($$9, 15.5F, 0.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1564 */             $$22.vertex($$9, 15.5F, 15.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1565 */             $$22.vertex($$9, 15.5F, 15.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/*      */             
/* 1567 */             $$22.vertex($$9, 0.5F, 0.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1568 */             $$22.vertex($$9, 15.5F, 0.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1569 */             $$22.vertex($$9, 15.5F, 15.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1570 */             $$22.vertex($$9, 0.5F, 15.5F, 0.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/*      */             
/* 1572 */             $$22.vertex($$9, 0.5F, 15.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1573 */             $$22.vertex($$9, 15.5F, 15.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1574 */             $$22.vertex($$9, 15.5F, 0.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/* 1575 */             $$22.vertex($$9, 0.5F, 0.5F, 15.5F).color(0.9F, 0.9F, 0.0F, 0.2F).endVertex();
/*      */           } 
/*      */         } 
/*      */         
/* 1579 */         $$0.popPose(); }
/*      */     
/*      */     } 
/*      */     
/* 1583 */     if (this.capturedFrustum != null) {
/* 1584 */       $$0.pushPose();
/* 1585 */       $$0.translate((float)(this.frustumPos.x - ($$2.getPosition()).x), (float)(this.frustumPos.y - ($$2.getPosition()).y), (float)(this.frustumPos.z - ($$2.getPosition()).z));
/* 1586 */       Matrix4f $$25 = $$0.last().pose();
/*      */       
/* 1588 */       VertexConsumer $$26 = $$1.getBuffer(RenderType.debugQuads());
/*      */ 
/*      */       
/* 1591 */       addFrustumQuad($$26, $$25, 0, 1, 2, 3, 0, 1, 1);
/*      */ 
/*      */       
/* 1594 */       addFrustumQuad($$26, $$25, 4, 5, 6, 7, 1, 0, 0);
/*      */ 
/*      */       
/* 1597 */       addFrustumQuad($$26, $$25, 0, 1, 5, 4, 1, 1, 0);
/*      */ 
/*      */       
/* 1600 */       addFrustumQuad($$26, $$25, 2, 3, 7, 6, 0, 0, 1);
/*      */ 
/*      */       
/* 1603 */       addFrustumQuad($$26, $$25, 0, 4, 7, 3, 0, 1, 0);
/*      */ 
/*      */       
/* 1606 */       addFrustumQuad($$26, $$25, 1, 5, 6, 2, 1, 0, 1);
/*      */       
/* 1608 */       VertexConsumer $$27 = $$1.getBuffer(RenderType.lines());
/*      */       
/* 1610 */       addFrustumVertex($$27, $$25, 0);
/* 1611 */       addFrustumVertex($$27, $$25, 1);
/* 1612 */       addFrustumVertex($$27, $$25, 1);
/* 1613 */       addFrustumVertex($$27, $$25, 2);
/*      */       
/* 1615 */       addFrustumVertex($$27, $$25, 2);
/* 1616 */       addFrustumVertex($$27, $$25, 3);
/*      */       
/* 1618 */       addFrustumVertex($$27, $$25, 3);
/* 1619 */       addFrustumVertex($$27, $$25, 0);
/*      */ 
/*      */       
/* 1622 */       addFrustumVertex($$27, $$25, 4);
/* 1623 */       addFrustumVertex($$27, $$25, 5);
/*      */       
/* 1625 */       addFrustumVertex($$27, $$25, 5);
/* 1626 */       addFrustumVertex($$27, $$25, 6);
/*      */       
/* 1628 */       addFrustumVertex($$27, $$25, 6);
/* 1629 */       addFrustumVertex($$27, $$25, 7);
/*      */       
/* 1631 */       addFrustumVertex($$27, $$25, 7);
/* 1632 */       addFrustumVertex($$27, $$25, 4);
/*      */ 
/*      */       
/* 1635 */       addFrustumVertex($$27, $$25, 0);
/* 1636 */       addFrustumVertex($$27, $$25, 4);
/*      */       
/* 1638 */       addFrustumVertex($$27, $$25, 1);
/* 1639 */       addFrustumVertex($$27, $$25, 5);
/*      */       
/* 1641 */       addFrustumVertex($$27, $$25, 2);
/* 1642 */       addFrustumVertex($$27, $$25, 6);
/*      */       
/* 1644 */       addFrustumVertex($$27, $$25, 3);
/* 1645 */       addFrustumVertex($$27, $$25, 7);
/*      */       
/* 1647 */       $$0.popPose();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addFrustumVertex(VertexConsumer $$0, Matrix4f $$1, int $$2) {
/* 1652 */     $$0.vertex($$1, this.frustumPoints[$$2].x(), this.frustumPoints[$$2].y(), this.frustumPoints[$$2].z()).color(0, 0, 0, 255).normal(0.0F, 0.0F, -1.0F).endVertex();
/*      */   }
/*      */   
/*      */   private void addFrustumQuad(VertexConsumer $$0, Matrix4f $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8) {
/* 1656 */     float $$9 = 0.25F;
/* 1657 */     $$0.vertex($$1, this.frustumPoints[$$2].x(), this.frustumPoints[$$2].y(), this.frustumPoints[$$2].z()).color($$6, $$7, $$8, 0.25F).endVertex();
/* 1658 */     $$0.vertex($$1, this.frustumPoints[$$3].x(), this.frustumPoints[$$3].y(), this.frustumPoints[$$3].z()).color($$6, $$7, $$8, 0.25F).endVertex();
/* 1659 */     $$0.vertex($$1, this.frustumPoints[$$4].x(), this.frustumPoints[$$4].y(), this.frustumPoints[$$4].z()).color($$6, $$7, $$8, 0.25F).endVertex();
/* 1660 */     $$0.vertex($$1, this.frustumPoints[$$5].x(), this.frustumPoints[$$5].y(), this.frustumPoints[$$5].z()).color($$6, $$7, $$8, 0.25F).endVertex();
/*      */   }
/*      */   
/*      */   public void captureFrustum() {
/* 1664 */     this.captureFrustum = true;
/*      */   }
/*      */   
/*      */   public void killFrustum() {
/* 1668 */     this.capturedFrustum = null;
/*      */   }
/*      */   
/*      */   public void tick() {
/* 1672 */     if (this.level.tickRateManager().runsNormally()) {
/* 1673 */       this.ticks++;
/*      */     }
/*      */     
/* 1676 */     if (this.ticks % 20 != 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1680 */     ObjectIterator<BlockDestructionProgress> objectIterator = this.destroyingBlocks.values().iterator();
/* 1681 */     while (objectIterator.hasNext()) {
/* 1682 */       BlockDestructionProgress $$1 = objectIterator.next();
/*      */       
/* 1684 */       int $$2 = $$1.getUpdatedRenderTick();
/*      */       
/* 1686 */       if (this.ticks - $$2 > 400) {
/* 1687 */         objectIterator.remove();
/* 1688 */         removeProgress($$1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void removeProgress(BlockDestructionProgress $$0) {
/* 1694 */     long $$1 = $$0.getPos().asLong();
/* 1695 */     Set<BlockDestructionProgress> $$2 = (Set<BlockDestructionProgress>)this.destructionProgress.get($$1);
/* 1696 */     $$2.remove($$0);
/* 1697 */     if ($$2.isEmpty()) {
/* 1698 */       this.destructionProgress.remove($$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void renderEndSky(PoseStack $$0) {
/* 1703 */     RenderSystem.enableBlend();
/*      */     
/* 1705 */     RenderSystem.depthMask(false);
/* 1706 */     RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
/* 1707 */     RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
/* 1708 */     Tesselator $$1 = Tesselator.getInstance();
/* 1709 */     BufferBuilder $$2 = $$1.getBuilder();
/* 1710 */     for (int $$3 = 0; $$3 < 6; $$3++) {
/* 1711 */       $$0.pushPose();
/* 1712 */       if ($$3 == 1) {
/* 1713 */         $$0.mulPose(Axis.XP.rotationDegrees(90.0F));
/*      */       }
/* 1715 */       if ($$3 == 2) {
/* 1716 */         $$0.mulPose(Axis.XP.rotationDegrees(-90.0F));
/*      */       }
/* 1718 */       if ($$3 == 3) {
/* 1719 */         $$0.mulPose(Axis.XP.rotationDegrees(180.0F));
/*      */       }
/* 1721 */       if ($$3 == 4) {
/* 1722 */         $$0.mulPose(Axis.ZP.rotationDegrees(90.0F));
/*      */       }
/* 1724 */       if ($$3 == 5) {
/* 1725 */         $$0.mulPose(Axis.ZP.rotationDegrees(-90.0F));
/*      */       }
/*      */       
/* 1728 */       Matrix4f $$4 = $$0.last().pose();
/*      */       
/* 1730 */       $$2.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
/* 1731 */       $$2.vertex($$4, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
/* 1732 */       $$2.vertex($$4, -100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
/* 1733 */       $$2.vertex($$4, 100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
/* 1734 */       $$2.vertex($$4, 100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
/* 1735 */       $$1.end();
/*      */       
/* 1737 */       $$0.popPose();
/*      */     } 
/* 1739 */     RenderSystem.depthMask(true);
/* 1740 */     RenderSystem.disableBlend();
/*      */   }
/*      */   
/*      */   public void renderSky(PoseStack $$0, Matrix4f $$1, float $$2, Camera $$3, boolean $$4, Runnable $$5) {
/* 1744 */     $$5.run();
/* 1745 */     if ($$4) {
/*      */       return;
/*      */     }
/* 1748 */     FogType $$6 = $$3.getFluidInCamera();
/* 1749 */     if ($$6 == FogType.POWDER_SNOW || $$6 == FogType.LAVA || doesMobEffectBlockSky($$3)) {
/*      */       return;
/*      */     }
/* 1752 */     if (this.minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.END) {
/* 1753 */       renderEndSky($$0);
/*      */       return;
/*      */     } 
/* 1756 */     if (this.minecraft.level.effects().skyType() != DimensionSpecialEffects.SkyType.NORMAL) {
/*      */       return;
/*      */     }
/*      */     
/* 1760 */     Vec3 $$7 = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), $$2);
/* 1761 */     float $$8 = (float)$$7.x;
/* 1762 */     float $$9 = (float)$$7.y;
/* 1763 */     float $$10 = (float)$$7.z;
/*      */     
/* 1765 */     FogRenderer.levelFogColor();
/*      */     
/* 1767 */     BufferBuilder $$11 = Tesselator.getInstance().getBuilder();
/*      */     
/* 1769 */     RenderSystem.depthMask(false);
/*      */     
/* 1771 */     RenderSystem.setShaderColor($$8, $$9, $$10, 1.0F);
/*      */     
/* 1773 */     ShaderInstance $$12 = RenderSystem.getShader();
/*      */     
/* 1775 */     this.skyBuffer.bind();
/* 1776 */     this.skyBuffer.drawWithShader($$0.last().pose(), $$1, $$12);
/* 1777 */     VertexBuffer.unbind();
/*      */     
/* 1779 */     RenderSystem.enableBlend();
/*      */     
/* 1781 */     float[] $$13 = this.level.effects().getSunriseColor(this.level.getTimeOfDay($$2), $$2);
/* 1782 */     if ($$13 != null) {
/* 1783 */       RenderSystem.setShader(GameRenderer::getPositionColorShader);
/*      */       
/* 1785 */       RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*      */       
/* 1787 */       $$0.pushPose();
/* 1788 */       $$0.mulPose(Axis.XP.rotationDegrees(90.0F));
/* 1789 */       float $$14 = (Mth.sin(this.level.getSunAngle($$2)) < 0.0F) ? 180.0F : 0.0F;
/* 1790 */       $$0.mulPose(Axis.ZP.rotationDegrees($$14));
/* 1791 */       $$0.mulPose(Axis.ZP.rotationDegrees(90.0F));
/*      */       
/* 1793 */       float $$15 = $$13[0];
/* 1794 */       float $$16 = $$13[1];
/* 1795 */       float $$17 = $$13[2];
/*      */       
/* 1797 */       Matrix4f $$18 = $$0.last().pose();
/* 1798 */       $$11.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
/* 1799 */       $$11.vertex($$18, 0.0F, 100.0F, 0.0F).color($$15, $$16, $$17, $$13[3]).endVertex();
/* 1800 */       int $$19 = 16;
/* 1801 */       for (int $$20 = 0; $$20 <= 16; $$20++) {
/* 1802 */         float $$21 = $$20 * 6.2831855F / 16.0F;
/* 1803 */         float $$22 = Mth.sin($$21);
/* 1804 */         float $$23 = Mth.cos($$21);
/* 1805 */         $$11.vertex($$18, $$22 * 120.0F, $$23 * 120.0F, -$$23 * 40.0F * $$13[3]).color($$13[0], $$13[1], $$13[2], 0.0F).endVertex();
/*      */       } 
/* 1807 */       BufferUploader.drawWithShader($$11.end());
/* 1808 */       $$0.popPose();
/*      */     } 
/*      */     
/* 1811 */     RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1812 */     $$0.pushPose();
/*      */     
/* 1814 */     float $$24 = 1.0F - this.level.getRainLevel($$2);
/* 1815 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, $$24);
/* 1816 */     $$0.mulPose(Axis.YP.rotationDegrees(-90.0F));
/* 1817 */     $$0.mulPose(Axis.XP.rotationDegrees(this.level.getTimeOfDay($$2) * 360.0F));
/*      */     
/* 1819 */     Matrix4f $$25 = $$0.last().pose();
/* 1820 */     float $$26 = 30.0F;
/* 1821 */     RenderSystem.setShader(GameRenderer::getPositionTexShader);
/* 1822 */     RenderSystem.setShaderTexture(0, SUN_LOCATION);
/* 1823 */     $$11.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
/* 1824 */     $$11.vertex($$25, -$$26, 100.0F, -$$26).uv(0.0F, 0.0F).endVertex();
/* 1825 */     $$11.vertex($$25, $$26, 100.0F, -$$26).uv(1.0F, 0.0F).endVertex();
/* 1826 */     $$11.vertex($$25, $$26, 100.0F, $$26).uv(1.0F, 1.0F).endVertex();
/* 1827 */     $$11.vertex($$25, -$$26, 100.0F, $$26).uv(0.0F, 1.0F).endVertex();
/* 1828 */     BufferUploader.drawWithShader($$11.end());
/*      */     
/* 1830 */     $$26 = 20.0F;
/* 1831 */     RenderSystem.setShaderTexture(0, MOON_LOCATION);
/* 1832 */     int $$27 = this.level.getMoonPhase();
/* 1833 */     int $$28 = $$27 % 4;
/* 1834 */     int $$29 = $$27 / 4 % 2;
/* 1835 */     float $$30 = ($$28 + 0) / 4.0F;
/* 1836 */     float $$31 = ($$29 + 0) / 2.0F;
/* 1837 */     float $$32 = ($$28 + 1) / 4.0F;
/* 1838 */     float $$33 = ($$29 + 1) / 2.0F;
/* 1839 */     $$11.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
/* 1840 */     $$11.vertex($$25, -$$26, -100.0F, $$26).uv($$32, $$33).endVertex();
/* 1841 */     $$11.vertex($$25, $$26, -100.0F, $$26).uv($$30, $$33).endVertex();
/* 1842 */     $$11.vertex($$25, $$26, -100.0F, -$$26).uv($$30, $$31).endVertex();
/* 1843 */     $$11.vertex($$25, -$$26, -100.0F, -$$26).uv($$32, $$31).endVertex();
/* 1844 */     BufferUploader.drawWithShader($$11.end());
/*      */     
/* 1846 */     float $$34 = this.level.getStarBrightness($$2) * $$24;
/* 1847 */     if ($$34 > 0.0F) {
/* 1848 */       RenderSystem.setShaderColor($$34, $$34, $$34, $$34);
/* 1849 */       FogRenderer.setupNoFog();
/* 1850 */       this.starBuffer.bind();
/* 1851 */       this.starBuffer.drawWithShader($$0.last().pose(), $$1, GameRenderer.getPositionShader());
/* 1852 */       VertexBuffer.unbind();
/* 1853 */       $$5.run();
/*      */     } 
/* 1855 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/* 1857 */     RenderSystem.disableBlend();
/* 1858 */     RenderSystem.defaultBlendFunc();
/*      */     
/* 1860 */     $$0.popPose();
/* 1861 */     RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
/*      */     
/* 1863 */     double $$35 = (this.minecraft.player.getEyePosition($$2)).y - this.level.getLevelData().getHorizonHeight((LevelHeightAccessor)this.level);
/* 1864 */     if ($$35 < 0.0D) {
/* 1865 */       $$0.pushPose();
/* 1866 */       $$0.translate(0.0F, 12.0F, 0.0F);
/* 1867 */       this.darkBuffer.bind();
/* 1868 */       this.darkBuffer.drawWithShader($$0.last().pose(), $$1, $$12);
/* 1869 */       VertexBuffer.unbind();
/* 1870 */       $$0.popPose();
/*      */     } 
/*      */     
/* 1873 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 1874 */     RenderSystem.depthMask(true);
/*      */   }
/*      */   
/*      */   private boolean doesMobEffectBlockSky(Camera $$0) {
/* 1878 */     Entity entity = $$0.getEntity(); if (entity instanceof LivingEntity) { LivingEntity $$1 = (LivingEntity)entity;
/* 1879 */       return ($$1.hasEffect(MobEffects.BLINDNESS) || $$1.hasEffect(MobEffects.DARKNESS)); }
/*      */     
/* 1881 */     return false;
/*      */   }
/*      */   
/*      */   public void renderClouds(PoseStack $$0, Matrix4f $$1, float $$2, double $$3, double $$4, double $$5) {
/* 1885 */     float $$6 = this.level.effects().getCloudHeight();
/* 1886 */     if (Float.isNaN($$6)) {
/*      */       return;
/*      */     }
/*      */     
/* 1890 */     RenderSystem.disableCull();
/* 1891 */     RenderSystem.enableBlend();
/* 1892 */     RenderSystem.enableDepthTest();
/* 1893 */     RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 1894 */     RenderSystem.depthMask(true);
/*      */     
/* 1896 */     float $$7 = 12.0F;
/* 1897 */     float $$8 = 4.0F;
/* 1898 */     double $$9 = 2.0E-4D;
/*      */     
/* 1900 */     double $$10 = ((this.ticks + $$2) * 0.03F);
/* 1901 */     double $$11 = ($$3 + $$10) / 12.0D;
/* 1902 */     double $$12 = ($$6 - (float)$$4 + 0.33F);
/* 1903 */     double $$13 = $$5 / 12.0D + 0.33000001311302185D;
/* 1904 */     $$11 -= (Mth.floor($$11 / 2048.0D) * 2048);
/* 1905 */     $$13 -= (Mth.floor($$13 / 2048.0D) * 2048);
/* 1906 */     float $$14 = (float)($$11 - Mth.floor($$11));
/* 1907 */     float $$15 = (float)($$12 / 4.0D - Mth.floor($$12 / 4.0D)) * 4.0F;
/* 1908 */     float $$16 = (float)($$13 - Mth.floor($$13));
/* 1909 */     Vec3 $$17 = this.level.getCloudColor($$2);
/*      */     
/* 1911 */     int $$18 = (int)Math.floor($$11);
/* 1912 */     int $$19 = (int)Math.floor($$12 / 4.0D);
/* 1913 */     int $$20 = (int)Math.floor($$13);
/* 1914 */     if ($$18 != this.prevCloudX || $$19 != this.prevCloudY || $$20 != this.prevCloudZ || this.minecraft.options
/*      */ 
/*      */       
/* 1917 */       .getCloudsType() != this.prevCloudsType || this.prevCloudColor
/* 1918 */       .distanceToSqr($$17) > 2.0E-4D) {
/*      */       
/* 1920 */       this.prevCloudX = $$18;
/* 1921 */       this.prevCloudY = $$19;
/* 1922 */       this.prevCloudZ = $$20;
/* 1923 */       this.prevCloudColor = $$17;
/* 1924 */       this.prevCloudsType = this.minecraft.options.getCloudsType();
/* 1925 */       this.generateClouds = true;
/*      */     } 
/*      */     
/* 1928 */     if (this.generateClouds) {
/* 1929 */       this.generateClouds = false;
/*      */       
/* 1931 */       BufferBuilder $$21 = Tesselator.getInstance().getBuilder();
/*      */       
/* 1933 */       if (this.cloudBuffer != null) {
/* 1934 */         this.cloudBuffer.close();
/*      */       }
/*      */       
/* 1937 */       this.cloudBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
/* 1938 */       BufferBuilder.RenderedBuffer $$22 = buildClouds($$21, $$11, $$12, $$13, $$17);
/*      */       
/* 1940 */       this.cloudBuffer.bind();
/* 1941 */       this.cloudBuffer.upload($$22);
/* 1942 */       VertexBuffer.unbind();
/*      */     } 
/*      */     
/* 1945 */     RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
/* 1946 */     RenderSystem.setShaderTexture(0, CLOUDS_LOCATION);
/* 1947 */     FogRenderer.levelFogColor();
/*      */     
/* 1949 */     $$0.pushPose();
/* 1950 */     $$0.scale(12.0F, 1.0F, 12.0F);
/* 1951 */     $$0.translate(-$$14, $$15, -$$16);
/*      */     
/* 1953 */     if (this.cloudBuffer != null) {
/* 1954 */       this.cloudBuffer.bind();
/*      */       
/* 1956 */       int $$23 = (this.prevCloudsType == CloudStatus.FANCY) ? 0 : 1;
/* 1957 */       for (int $$24 = $$23; $$24 < 2; $$24++) {
/* 1958 */         if ($$24 == 0) {
/* 1959 */           RenderSystem.colorMask(false, false, false, false);
/*      */         } else {
/* 1961 */           RenderSystem.colorMask(true, true, true, true);
/*      */         } 
/*      */         
/* 1964 */         ShaderInstance $$25 = RenderSystem.getShader();
/* 1965 */         this.cloudBuffer.drawWithShader($$0.last().pose(), $$1, $$25);
/*      */       } 
/*      */       
/* 1968 */       VertexBuffer.unbind();
/*      */     } 
/*      */     
/* 1971 */     $$0.popPose();
/*      */     
/* 1973 */     RenderSystem.enableCull();
/* 1974 */     RenderSystem.disableBlend();
/* 1975 */     RenderSystem.defaultBlendFunc();
/*      */   }
/*      */   
/*      */   private BufferBuilder.RenderedBuffer buildClouds(BufferBuilder $$0, double $$1, double $$2, double $$3, Vec3 $$4) {
/* 1979 */     float $$5 = 4.0F;
/* 1980 */     float $$6 = 0.00390625F;
/* 1981 */     int $$7 = 8;
/* 1982 */     int $$8 = 4;
/* 1983 */     float $$9 = 9.765625E-4F;
/*      */ 
/*      */     
/* 1986 */     float $$10 = Mth.floor($$1) * 0.00390625F;
/* 1987 */     float $$11 = Mth.floor($$3) * 0.00390625F;
/*      */     
/* 1989 */     float $$12 = (float)$$4.x;
/* 1990 */     float $$13 = (float)$$4.y;
/* 1991 */     float $$14 = (float)$$4.z;
/*      */     
/* 1993 */     float $$15 = $$12 * 0.9F;
/* 1994 */     float $$16 = $$13 * 0.9F;
/* 1995 */     float $$17 = $$14 * 0.9F;
/*      */     
/* 1997 */     float $$18 = $$12 * 0.7F;
/* 1998 */     float $$19 = $$13 * 0.7F;
/* 1999 */     float $$20 = $$14 * 0.7F;
/*      */     
/* 2001 */     float $$21 = $$12 * 0.8F;
/* 2002 */     float $$22 = $$13 * 0.8F;
/* 2003 */     float $$23 = $$14 * 0.8F;
/*      */     
/* 2005 */     RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
/* 2006 */     $$0.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);
/*      */     
/* 2008 */     float $$24 = (float)Math.floor($$2 / 4.0D) * 4.0F;
/*      */     
/* 2010 */     if (this.prevCloudsType == CloudStatus.FANCY) {
/* 2011 */       for (int $$25 = -3; $$25 <= 4; $$25++) {
/* 2012 */         for (int $$26 = -3; $$26 <= 4; $$26++) {
/* 2013 */           float $$27 = ($$25 * 8);
/* 2014 */           float $$28 = ($$26 * 8);
/*      */           
/* 2016 */           if ($$24 > -5.0F) {
/* 2017 */             $$0.vertex(($$27 + 0.0F), ($$24 + 0.0F), ($$28 + 8.0F)).uv(($$27 + 0.0F) * 0.00390625F + $$10, ($$28 + 8.0F) * 0.00390625F + $$11).color($$18, $$19, $$20, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2018 */             $$0.vertex(($$27 + 8.0F), ($$24 + 0.0F), ($$28 + 8.0F)).uv(($$27 + 8.0F) * 0.00390625F + $$10, ($$28 + 8.0F) * 0.00390625F + $$11).color($$18, $$19, $$20, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2019 */             $$0.vertex(($$27 + 8.0F), ($$24 + 0.0F), ($$28 + 0.0F)).uv(($$27 + 8.0F) * 0.00390625F + $$10, ($$28 + 0.0F) * 0.00390625F + $$11).color($$18, $$19, $$20, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2020 */             $$0.vertex(($$27 + 0.0F), ($$24 + 0.0F), ($$28 + 0.0F)).uv(($$27 + 0.0F) * 0.00390625F + $$10, ($$28 + 0.0F) * 0.00390625F + $$11).color($$18, $$19, $$20, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 2023 */           if ($$24 <= 5.0F) {
/* 2024 */             $$0.vertex(($$27 + 0.0F), ($$24 + 4.0F - 9.765625E-4F), ($$28 + 8.0F)).uv(($$27 + 0.0F) * 0.00390625F + $$10, ($$28 + 8.0F) * 0.00390625F + $$11).color($$12, $$13, $$14, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 2025 */             $$0.vertex(($$27 + 8.0F), ($$24 + 4.0F - 9.765625E-4F), ($$28 + 8.0F)).uv(($$27 + 8.0F) * 0.00390625F + $$10, ($$28 + 8.0F) * 0.00390625F + $$11).color($$12, $$13, $$14, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 2026 */             $$0.vertex(($$27 + 8.0F), ($$24 + 4.0F - 9.765625E-4F), ($$28 + 0.0F)).uv(($$27 + 8.0F) * 0.00390625F + $$10, ($$28 + 0.0F) * 0.00390625F + $$11).color($$12, $$13, $$14, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 2027 */             $$0.vertex(($$27 + 0.0F), ($$24 + 4.0F - 9.765625E-4F), ($$28 + 0.0F)).uv(($$27 + 0.0F) * 0.00390625F + $$10, ($$28 + 0.0F) * 0.00390625F + $$11).color($$12, $$13, $$14, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 2030 */           if ($$25 > -1) {
/* 2031 */             for (int $$29 = 0; $$29 < 8; $$29++) {
/* 2032 */               $$0.vertex(($$27 + $$29 + 0.0F), ($$24 + 0.0F), ($$28 + 8.0F)).uv(($$27 + $$29 + 0.5F) * 0.00390625F + $$10, ($$28 + 8.0F) * 0.00390625F + $$11).color($$15, $$16, $$17, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 2033 */               $$0.vertex(($$27 + $$29 + 0.0F), ($$24 + 4.0F), ($$28 + 8.0F)).uv(($$27 + $$29 + 0.5F) * 0.00390625F + $$10, ($$28 + 8.0F) * 0.00390625F + $$11).color($$15, $$16, $$17, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 2034 */               $$0.vertex(($$27 + $$29 + 0.0F), ($$24 + 4.0F), ($$28 + 0.0F)).uv(($$27 + $$29 + 0.5F) * 0.00390625F + $$10, ($$28 + 0.0F) * 0.00390625F + $$11).color($$15, $$16, $$17, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 2035 */               $$0.vertex(($$27 + $$29 + 0.0F), ($$24 + 0.0F), ($$28 + 0.0F)).uv(($$27 + $$29 + 0.5F) * 0.00390625F + $$10, ($$28 + 0.0F) * 0.00390625F + $$11).color($$15, $$16, $$17, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2039 */           if ($$25 <= 1) {
/* 2040 */             for (int $$30 = 0; $$30 < 8; $$30++) {
/* 2041 */               $$0.vertex(($$27 + $$30 + 1.0F - 9.765625E-4F), ($$24 + 0.0F), ($$28 + 8.0F)).uv(($$27 + $$30 + 0.5F) * 0.00390625F + $$10, ($$28 + 8.0F) * 0.00390625F + $$11).color($$15, $$16, $$17, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 2042 */               $$0.vertex(($$27 + $$30 + 1.0F - 9.765625E-4F), ($$24 + 4.0F), ($$28 + 8.0F)).uv(($$27 + $$30 + 0.5F) * 0.00390625F + $$10, ($$28 + 8.0F) * 0.00390625F + $$11).color($$15, $$16, $$17, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 2043 */               $$0.vertex(($$27 + $$30 + 1.0F - 9.765625E-4F), ($$24 + 4.0F), ($$28 + 0.0F)).uv(($$27 + $$30 + 0.5F) * 0.00390625F + $$10, ($$28 + 0.0F) * 0.00390625F + $$11).color($$15, $$16, $$17, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 2044 */               $$0.vertex(($$27 + $$30 + 1.0F - 9.765625E-4F), ($$24 + 0.0F), ($$28 + 0.0F)).uv(($$27 + $$30 + 0.5F) * 0.00390625F + $$10, ($$28 + 0.0F) * 0.00390625F + $$11).color($$15, $$16, $$17, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2048 */           if ($$26 > -1) {
/* 2049 */             for (int $$31 = 0; $$31 < 8; $$31++) {
/* 2050 */               $$0.vertex(($$27 + 0.0F), ($$24 + 4.0F), ($$28 + $$31 + 0.0F)).uv(($$27 + 0.0F) * 0.00390625F + $$10, ($$28 + $$31 + 0.5F) * 0.00390625F + $$11).color($$21, $$22, $$23, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 2051 */               $$0.vertex(($$27 + 8.0F), ($$24 + 4.0F), ($$28 + $$31 + 0.0F)).uv(($$27 + 8.0F) * 0.00390625F + $$10, ($$28 + $$31 + 0.5F) * 0.00390625F + $$11).color($$21, $$22, $$23, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 2052 */               $$0.vertex(($$27 + 8.0F), ($$24 + 0.0F), ($$28 + $$31 + 0.0F)).uv(($$27 + 8.0F) * 0.00390625F + $$10, ($$28 + $$31 + 0.5F) * 0.00390625F + $$11).color($$21, $$22, $$23, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 2053 */               $$0.vertex(($$27 + 0.0F), ($$24 + 0.0F), ($$28 + $$31 + 0.0F)).uv(($$27 + 0.0F) * 0.00390625F + $$10, ($$28 + $$31 + 0.5F) * 0.00390625F + $$11).color($$21, $$22, $$23, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2057 */           if ($$26 <= 1) {
/* 2058 */             for (int $$32 = 0; $$32 < 8; $$32++) {
/* 2059 */               $$0.vertex(($$27 + 0.0F), ($$24 + 4.0F), ($$28 + $$32 + 1.0F - 9.765625E-4F)).uv(($$27 + 0.0F) * 0.00390625F + $$10, ($$28 + $$32 + 0.5F) * 0.00390625F + $$11).color($$21, $$22, $$23, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 2060 */               $$0.vertex(($$27 + 8.0F), ($$24 + 4.0F), ($$28 + $$32 + 1.0F - 9.765625E-4F)).uv(($$27 + 8.0F) * 0.00390625F + $$10, ($$28 + $$32 + 0.5F) * 0.00390625F + $$11).color($$21, $$22, $$23, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 2061 */               $$0.vertex(($$27 + 8.0F), ($$24 + 0.0F), ($$28 + $$32 + 1.0F - 9.765625E-4F)).uv(($$27 + 8.0F) * 0.00390625F + $$10, ($$28 + $$32 + 0.5F) * 0.00390625F + $$11).color($$21, $$22, $$23, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 2062 */               $$0.vertex(($$27 + 0.0F), ($$24 + 0.0F), ($$28 + $$32 + 1.0F - 9.765625E-4F)).uv(($$27 + 0.0F) * 0.00390625F + $$10, ($$28 + $$32 + 0.5F) * 0.00390625F + $$11).color($$21, $$22, $$23, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } else {
/* 2068 */       int $$33 = 1;
/* 2069 */       int $$34 = 32;
/* 2070 */       for (int $$35 = -32; $$35 < 32; $$35 += 32) {
/* 2071 */         for (int $$36 = -32; $$36 < 32; $$36 += 32) {
/* 2072 */           $$0.vertex(($$35 + 0), $$24, ($$36 + 32)).uv(($$35 + 0) * 0.00390625F + $$10, ($$36 + 32) * 0.00390625F + $$11).color($$12, $$13, $$14, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2073 */           $$0.vertex(($$35 + 32), $$24, ($$36 + 32)).uv(($$35 + 32) * 0.00390625F + $$10, ($$36 + 32) * 0.00390625F + $$11).color($$12, $$13, $$14, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2074 */           $$0.vertex(($$35 + 32), $$24, ($$36 + 0)).uv(($$35 + 32) * 0.00390625F + $$10, ($$36 + 0) * 0.00390625F + $$11).color($$12, $$13, $$14, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2075 */           $$0.vertex(($$35 + 0), $$24, ($$36 + 0)).uv(($$35 + 0) * 0.00390625F + $$10, ($$36 + 0) * 0.00390625F + $$11).color($$12, $$13, $$14, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2080 */     return $$0.end();
/*      */   }
/*      */   
/*      */   private void compileSections(Camera $$0) {
/* 2084 */     this.minecraft.getProfiler().push("populate_sections_to_compile");
/*      */     
/* 2086 */     LevelLightEngine $$1 = this.level.getLightEngine();
/* 2087 */     RenderRegionCache $$2 = new RenderRegionCache();
/* 2088 */     BlockPos $$3 = $$0.getBlockPosition();
/* 2089 */     List<SectionRenderDispatcher.RenderSection> $$4 = Lists.newArrayList();
/* 2090 */     for (ObjectListIterator<SectionRenderDispatcher.RenderSection> objectListIterator = this.visibleSections.iterator(); objectListIterator.hasNext(); ) { SectionRenderDispatcher.RenderSection $$5 = objectListIterator.next();
/* 2091 */       SectionPos $$6 = SectionPos.of($$5.getOrigin());
/* 2092 */       if ($$5.isDirty() && $$1.lightOnInSection($$6)) {
/* 2093 */         boolean $$7 = false;
/* 2094 */         if (this.minecraft.options.prioritizeChunkUpdates().get() == PrioritizeChunkUpdates.NEARBY) {
/* 2095 */           BlockPos $$8 = $$5.getOrigin().offset(8, 8, 8);
/* 2096 */           $$7 = ($$8.distSqr((Vec3i)$$3) < 768.0D || $$5.isDirtyFromPlayer());
/* 2097 */         } else if (this.minecraft.options.prioritizeChunkUpdates().get() == PrioritizeChunkUpdates.PLAYER_AFFECTED) {
/* 2098 */           $$7 = $$5.isDirtyFromPlayer();
/*      */         } 
/* 2100 */         if ($$7) {
/* 2101 */           this.minecraft.getProfiler().push("build_near_sync");
/* 2102 */           this.sectionRenderDispatcher.rebuildSectionSync($$5, $$2);
/* 2103 */           $$5.setNotDirty();
/* 2104 */           this.minecraft.getProfiler().pop(); continue;
/*      */         } 
/* 2106 */         $$4.add($$5);
/*      */       }  }
/*      */ 
/*      */     
/* 2110 */     this.minecraft.getProfiler().popPush("upload");
/*      */     
/* 2112 */     this.sectionRenderDispatcher.uploadAllPendingUploads();
/*      */     
/* 2114 */     this.minecraft.getProfiler().popPush("schedule_async_compile");
/*      */     
/* 2116 */     for (SectionRenderDispatcher.RenderSection $$9 : $$4) {
/* 2117 */       $$9.rebuildSectionAsync(this.sectionRenderDispatcher, $$2);
/* 2118 */       $$9.setNotDirty();
/*      */     } 
/*      */     
/* 2121 */     this.minecraft.getProfiler().pop();
/*      */   }
/*      */   
/*      */   private void renderWorldBorder(Camera $$0) {
/* 2125 */     BufferBuilder $$1 = Tesselator.getInstance().getBuilder();
/*      */     
/* 2127 */     WorldBorder $$2 = this.level.getWorldBorder();
/*      */     
/* 2129 */     double $$3 = (this.minecraft.options.getEffectiveRenderDistance() * 16);
/*      */     
/* 2131 */     if (($$0.getPosition()).x < $$2.getMaxX() - $$3 && ($$0.getPosition()).x > $$2.getMinX() + $$3 && ($$0.getPosition()).z < $$2.getMaxZ() - $$3 && ($$0.getPosition()).z > $$2.getMinZ() + $$3) {
/*      */       return;
/*      */     }
/*      */     
/* 2135 */     double $$4 = 1.0D - $$2.getDistanceToBorder(($$0.getPosition()).x, ($$0.getPosition()).z) / $$3;
/* 2136 */     $$4 = Math.pow($$4, 4.0D);
/* 2137 */     $$4 = Mth.clamp($$4, 0.0D, 1.0D);
/*      */     
/* 2139 */     double $$5 = ($$0.getPosition()).x;
/* 2140 */     double $$6 = ($$0.getPosition()).z;
/* 2141 */     double $$7 = this.minecraft.gameRenderer.getDepthFar();
/*      */     
/* 2143 */     RenderSystem.enableBlend();
/* 2144 */     RenderSystem.enableDepthTest();
/* 2145 */     RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 2146 */     RenderSystem.setShaderTexture(0, FORCEFIELD_LOCATION);
/*      */     
/* 2148 */     RenderSystem.depthMask(Minecraft.useShaderTransparency());
/*      */     
/* 2150 */     PoseStack $$8 = RenderSystem.getModelViewStack();
/* 2151 */     $$8.pushPose();
/* 2152 */     RenderSystem.applyModelViewMatrix();
/*      */     
/* 2154 */     int $$9 = $$2.getStatus().getColor();
/* 2155 */     float $$10 = ($$9 >> 16 & 0xFF) / 255.0F;
/* 2156 */     float $$11 = ($$9 >> 8 & 0xFF) / 255.0F;
/* 2157 */     float $$12 = ($$9 & 0xFF) / 255.0F;
/* 2158 */     RenderSystem.setShaderColor($$10, $$11, $$12, (float)$$4);
/* 2159 */     RenderSystem.setShader(GameRenderer::getPositionTexShader);
/* 2160 */     RenderSystem.polygonOffset(-3.0F, -3.0F);
/* 2161 */     RenderSystem.enablePolygonOffset();
/* 2162 */     RenderSystem.disableCull();
/*      */     
/* 2164 */     float $$13 = (float)(Util.getMillis() % 3000L) / 3000.0F;
/* 2165 */     float $$14 = (float)-Mth.frac(($$0.getPosition()).y * 0.5D);
/* 2166 */     float $$15 = $$14 + (float)$$7;
/* 2167 */     $$1.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
/*      */     
/* 2169 */     double $$16 = Math.max(Mth.floor($$6 - $$3), $$2.getMinZ());
/* 2170 */     double $$17 = Math.min(Mth.ceil($$6 + $$3), $$2.getMaxZ());
/* 2171 */     float $$18 = (Mth.floor($$16) & 0x1) * 0.5F;
/* 2172 */     if ($$5 > $$2.getMaxX() - $$3) {
/* 2173 */       float $$19 = $$18;
/* 2174 */       for (double $$20 = $$16; $$20 < $$17; $$20++, $$19 += 0.5F) {
/* 2175 */         double $$21 = Math.min(1.0D, $$17 - $$20);
/* 2176 */         float $$22 = (float)$$21 * 0.5F;
/* 2177 */         $$1.vertex($$2.getMaxX() - $$5, -$$7, $$20 - $$6).uv($$13 - $$19, $$13 + $$15).endVertex();
/* 2178 */         $$1.vertex($$2.getMaxX() - $$5, -$$7, $$20 + $$21 - $$6).uv($$13 - $$22 + $$19, $$13 + $$15).endVertex();
/* 2179 */         $$1.vertex($$2.getMaxX() - $$5, $$7, $$20 + $$21 - $$6).uv($$13 - $$22 + $$19, $$13 + $$14).endVertex();
/* 2180 */         $$1.vertex($$2.getMaxX() - $$5, $$7, $$20 - $$6).uv($$13 - $$19, $$13 + $$14).endVertex();
/*      */       } 
/*      */     } 
/* 2183 */     if ($$5 < $$2.getMinX() + $$3) {
/* 2184 */       float $$23 = $$18;
/* 2185 */       for (double $$24 = $$16; $$24 < $$17; $$24++, $$23 += 0.5F) {
/* 2186 */         double $$25 = Math.min(1.0D, $$17 - $$24);
/* 2187 */         float $$26 = (float)$$25 * 0.5F;
/* 2188 */         $$1.vertex($$2.getMinX() - $$5, -$$7, $$24 - $$6).uv($$13 + $$23, $$13 + $$15).endVertex();
/* 2189 */         $$1.vertex($$2.getMinX() - $$5, -$$7, $$24 + $$25 - $$6).uv($$13 + $$26 + $$23, $$13 + $$15).endVertex();
/* 2190 */         $$1.vertex($$2.getMinX() - $$5, $$7, $$24 + $$25 - $$6).uv($$13 + $$26 + $$23, $$13 + $$14).endVertex();
/* 2191 */         $$1.vertex($$2.getMinX() - $$5, $$7, $$24 - $$6).uv($$13 + $$23, $$13 + $$14).endVertex();
/*      */       } 
/*      */     } 
/*      */     
/* 2195 */     $$16 = Math.max(Mth.floor($$5 - $$3), $$2.getMinX());
/* 2196 */     $$17 = Math.min(Mth.ceil($$5 + $$3), $$2.getMaxX());
/* 2197 */     $$18 = (Mth.floor($$16) & 0x1) * 0.5F;
/* 2198 */     if ($$6 > $$2.getMaxZ() - $$3) {
/* 2199 */       float $$27 = $$18;
/* 2200 */       for (double $$28 = $$16; $$28 < $$17; $$28++, $$27 += 0.5F) {
/* 2201 */         double $$29 = Math.min(1.0D, $$17 - $$28);
/* 2202 */         float $$30 = (float)$$29 * 0.5F;
/* 2203 */         $$1.vertex($$28 - $$5, -$$7, $$2.getMaxZ() - $$6).uv($$13 + $$27, $$13 + $$15).endVertex();
/* 2204 */         $$1.vertex($$28 + $$29 - $$5, -$$7, $$2.getMaxZ() - $$6).uv($$13 + $$30 + $$27, $$13 + $$15).endVertex();
/* 2205 */         $$1.vertex($$28 + $$29 - $$5, $$7, $$2.getMaxZ() - $$6).uv($$13 + $$30 + $$27, $$13 + $$14).endVertex();
/* 2206 */         $$1.vertex($$28 - $$5, $$7, $$2.getMaxZ() - $$6).uv($$13 + $$27, $$13 + $$14).endVertex();
/*      */       } 
/*      */     } 
/* 2209 */     if ($$6 < $$2.getMinZ() + $$3) {
/* 2210 */       float $$31 = $$18;
/* 2211 */       for (double $$32 = $$16; $$32 < $$17; $$32++, $$31 += 0.5F) {
/* 2212 */         double $$33 = Math.min(1.0D, $$17 - $$32);
/* 2213 */         float $$34 = (float)$$33 * 0.5F;
/* 2214 */         $$1.vertex($$32 - $$5, -$$7, $$2.getMinZ() - $$6).uv($$13 - $$31, $$13 + $$15).endVertex();
/* 2215 */         $$1.vertex($$32 + $$33 - $$5, -$$7, $$2.getMinZ() - $$6).uv($$13 - $$34 + $$31, $$13 + $$15).endVertex();
/* 2216 */         $$1.vertex($$32 + $$33 - $$5, $$7, $$2.getMinZ() - $$6).uv($$13 - $$34 + $$31, $$13 + $$14).endVertex();
/* 2217 */         $$1.vertex($$32 - $$5, $$7, $$2.getMinZ() - $$6).uv($$13 - $$31, $$13 + $$14).endVertex();
/*      */       } 
/*      */     } 
/*      */     
/* 2221 */     BufferUploader.drawWithShader($$1.end());
/*      */     
/* 2223 */     RenderSystem.enableCull();
/* 2224 */     RenderSystem.polygonOffset(0.0F, 0.0F);
/* 2225 */     RenderSystem.disablePolygonOffset();
/* 2226 */     RenderSystem.disableBlend();
/* 2227 */     RenderSystem.defaultBlendFunc();
/*      */     
/* 2229 */     $$8.popPose();
/* 2230 */     RenderSystem.applyModelViewMatrix();
/*      */     
/* 2232 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 2233 */     RenderSystem.depthMask(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderHitOutline(PoseStack $$0, VertexConsumer $$1, Entity $$2, double $$3, double $$4, double $$5, BlockPos $$6, BlockState $$7) {
/* 2243 */     renderShape($$0, $$1, $$7.getShape((BlockGetter)this.level, $$6, CollisionContext.of($$2)), $$6.getX() - $$3, $$6.getY() - $$4, $$6.getZ() - $$5, 0.0F, 0.0F, 0.0F, 0.4F);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vec3 mixColor(float $$0) {
/* 2249 */     float $$1 = 5.99999F;
/* 2250 */     int $$2 = (int)(Mth.clamp($$0, 0.0F, 1.0F) * 5.99999F);
/* 2251 */     float $$3 = $$0 * 5.99999F - $$2;
/* 2252 */     switch ($$2) { case 0: 
/*      */       case 1: 
/*      */       case 2: 
/*      */       case 3: 
/*      */       case 4:
/*      */       
/*      */       case 5:
/* 2259 */        }  throw new IllegalStateException("Unexpected value: " + $$2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Vec3 shiftHue(float $$0, float $$1, float $$2, float $$3) {
/* 2265 */     Vec3 $$4 = mixColor($$3).scale($$0);
/* 2266 */     Vec3 $$5 = mixColor(($$3 + 0.33333334F) % 1.0F).scale($$1);
/* 2267 */     Vec3 $$6 = mixColor(($$3 + 0.6666667F) % 1.0F).scale($$2);
/* 2268 */     Vec3 $$7 = $$4.add($$5).add($$6);
/* 2269 */     double $$8 = Math.max(Math.max(1.0D, $$7.x), Math.max($$7.y, $$7.z));
/* 2270 */     return new Vec3($$7.x / $$8, $$7.y / $$8, $$7.z / $$8);
/*      */   }
/*      */   
/*      */   public static void renderVoxelShape(PoseStack $$0, VertexConsumer $$1, VoxelShape $$2, double $$3, double $$4, double $$5, float $$6, float $$7, float $$8, float $$9, boolean $$10) {
/* 2274 */     List<AABB> $$11 = $$2.toAabbs();
/* 2275 */     if ($$11.isEmpty()) {
/*      */       return;
/*      */     }
/* 2278 */     int $$12 = $$10 ? $$11.size() : ($$11.size() * 8);
/* 2279 */     renderShape($$0, $$1, Shapes.create($$11.get(0)), $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/* 2280 */     for (int $$13 = 1; $$13 < $$11.size(); $$13++) {
/* 2281 */       AABB $$14 = $$11.get($$13);
/* 2282 */       float $$15 = $$13 / $$12;
/* 2283 */       Vec3 $$16 = shiftHue($$6, $$7, $$8, $$15);
/* 2284 */       renderShape($$0, $$1, Shapes.create($$14), $$3, $$4, $$5, (float)$$16.x, (float)$$16.y, (float)$$16.z, $$9);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void renderShape(PoseStack $$0, VertexConsumer $$1, VoxelShape $$2, double $$3, double $$4, double $$5, float $$6, float $$7, float $$8, float $$9) {
/* 2289 */     PoseStack.Pose $$10 = $$0.last();
/* 2290 */     $$2.forAllEdges(($$9, $$10, $$11, $$12, $$13, $$14) -> {
/*      */           float $$15 = (float)($$12 - $$9);
/*      */           float $$16 = (float)($$13 - $$10);
/*      */           float $$17 = (float)($$14 - $$11);
/*      */           float $$18 = Mth.sqrt($$15 * $$15 + $$16 * $$16 + $$17 * $$17);
/*      */           $$15 /= $$18;
/*      */           $$16 /= $$18;
/*      */           $$17 /= $$18;
/*      */           $$0.vertex($$1.pose(), (float)($$9 + $$2), (float)($$10 + $$3), (float)($$11 + $$4)).color($$5, $$6, $$7, $$8).normal($$1.normal(), $$15, $$16, $$17).endVertex();
/*      */           $$0.vertex($$1.pose(), (float)($$12 + $$2), (float)($$13 + $$3), (float)($$14 + $$4)).color($$5, $$6, $$7, $$8).normal($$1.normal(), $$15, $$16, $$17).endVertex();
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderLineBox(VertexConsumer $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, float $$7, float $$8, float $$9, float $$10) {
/* 2305 */     renderLineBox(new PoseStack(), $$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$7, $$8, $$9);
/*      */   }
/*      */   
/*      */   public static void renderLineBox(PoseStack $$0, VertexConsumer $$1, AABB $$2, float $$3, float $$4, float $$5, float $$6) {
/* 2309 */     renderLineBox($$0, $$1, $$2.minX, $$2.minY, $$2.minZ, $$2.maxX, $$2.maxY, $$2.maxZ, $$3, $$4, $$5, $$6, $$3, $$4, $$5);
/*      */   }
/*      */   
/*      */   public static void renderLineBox(PoseStack $$0, VertexConsumer $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7, float $$8, float $$9, float $$10, float $$11) {
/* 2313 */     renderLineBox($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$8, $$9, $$10);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderLineBox(PoseStack $$0, VertexConsumer $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7, float $$8, float $$9, float $$10, float $$11, float $$12, float $$13, float $$14) {
/* 2318 */     Matrix4f $$15 = $$0.last().pose();
/* 2319 */     Matrix3f $$16 = $$0.last().normal();
/* 2320 */     float $$17 = (float)$$2;
/* 2321 */     float $$18 = (float)$$3;
/* 2322 */     float $$19 = (float)$$4;
/* 2323 */     float $$20 = (float)$$5;
/* 2324 */     float $$21 = (float)$$6;
/* 2325 */     float $$22 = (float)$$7;
/*      */     
/* 2327 */     $$1.vertex($$15, $$17, $$18, $$19).color($$8, $$13, $$14, $$11).normal($$16, 1.0F, 0.0F, 0.0F).endVertex();
/* 2328 */     $$1.vertex($$15, $$20, $$18, $$19).color($$8, $$13, $$14, $$11).normal($$16, 1.0F, 0.0F, 0.0F).endVertex();
/*      */     
/* 2330 */     $$1.vertex($$15, $$17, $$18, $$19).color($$12, $$9, $$14, $$11).normal($$16, 0.0F, 1.0F, 0.0F).endVertex();
/* 2331 */     $$1.vertex($$15, $$17, $$21, $$19).color($$12, $$9, $$14, $$11).normal($$16, 0.0F, 1.0F, 0.0F).endVertex();
/*      */     
/* 2333 */     $$1.vertex($$15, $$17, $$18, $$19).color($$12, $$13, $$10, $$11).normal($$16, 0.0F, 0.0F, 1.0F).endVertex();
/* 2334 */     $$1.vertex($$15, $$17, $$18, $$22).color($$12, $$13, $$10, $$11).normal($$16, 0.0F, 0.0F, 1.0F).endVertex();
/*      */ 
/*      */     
/* 2337 */     $$1.vertex($$15, $$20, $$18, $$19).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 1.0F, 0.0F).endVertex();
/* 2338 */     $$1.vertex($$15, $$20, $$21, $$19).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 1.0F, 0.0F).endVertex();
/*      */     
/* 2340 */     $$1.vertex($$15, $$20, $$21, $$19).color($$8, $$9, $$10, $$11).normal($$16, -1.0F, 0.0F, 0.0F).endVertex();
/* 2341 */     $$1.vertex($$15, $$17, $$21, $$19).color($$8, $$9, $$10, $$11).normal($$16, -1.0F, 0.0F, 0.0F).endVertex();
/*      */     
/* 2343 */     $$1.vertex($$15, $$17, $$21, $$19).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 0.0F, 1.0F).endVertex();
/* 2344 */     $$1.vertex($$15, $$17, $$21, $$22).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 0.0F, 1.0F).endVertex();
/*      */     
/* 2346 */     $$1.vertex($$15, $$17, $$21, $$22).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, -1.0F, 0.0F).endVertex();
/* 2347 */     $$1.vertex($$15, $$17, $$18, $$22).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, -1.0F, 0.0F).endVertex();
/*      */     
/* 2349 */     $$1.vertex($$15, $$17, $$18, $$22).color($$8, $$9, $$10, $$11).normal($$16, 1.0F, 0.0F, 0.0F).endVertex();
/* 2350 */     $$1.vertex($$15, $$20, $$18, $$22).color($$8, $$9, $$10, $$11).normal($$16, 1.0F, 0.0F, 0.0F).endVertex();
/*      */     
/* 2352 */     $$1.vertex($$15, $$20, $$18, $$22).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 0.0F, -1.0F).endVertex();
/* 2353 */     $$1.vertex($$15, $$20, $$18, $$19).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 0.0F, -1.0F).endVertex();
/*      */ 
/*      */     
/* 2356 */     $$1.vertex($$15, $$17, $$21, $$22).color($$8, $$9, $$10, $$11).normal($$16, 1.0F, 0.0F, 0.0F).endVertex();
/* 2357 */     $$1.vertex($$15, $$20, $$21, $$22).color($$8, $$9, $$10, $$11).normal($$16, 1.0F, 0.0F, 0.0F).endVertex();
/*      */     
/* 2359 */     $$1.vertex($$15, $$20, $$18, $$22).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 1.0F, 0.0F).endVertex();
/* 2360 */     $$1.vertex($$15, $$20, $$21, $$22).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 1.0F, 0.0F).endVertex();
/*      */     
/* 2362 */     $$1.vertex($$15, $$20, $$21, $$19).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 0.0F, 1.0F).endVertex();
/* 2363 */     $$1.vertex($$15, $$20, $$21, $$22).color($$8, $$9, $$10, $$11).normal($$16, 0.0F, 0.0F, 1.0F).endVertex();
/*      */   }
/*      */   
/*      */   public static void addChainedFilledBoxVertices(PoseStack $$0, VertexConsumer $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7, float $$8, float $$9, float $$10, float $$11) {
/* 2367 */     addChainedFilledBoxVertices($$0, $$1, (float)$$2, (float)$$3, (float)$$4, (float)$$5, (float)$$6, (float)$$7, $$8, $$9, $$10, $$11);
/*      */   }
/*      */   
/*      */   public static void addChainedFilledBoxVertices(PoseStack $$0, VertexConsumer $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10, float $$11) {
/* 2371 */     Matrix4f $$12 = $$0.last().pose();
/*      */     
/* 2373 */     $$1.vertex($$12, $$2, $$3, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2374 */     $$1.vertex($$12, $$2, $$3, $$4).color($$8, $$9, $$10, $$11).endVertex();
/*      */     
/* 2376 */     $$1.vertex($$12, $$2, $$3, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2377 */     $$1.vertex($$12, $$2, $$3, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2378 */     $$1.vertex($$12, $$2, $$6, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2379 */     $$1.vertex($$12, $$2, $$6, $$7).color($$8, $$9, $$10, $$11).endVertex();
/*      */     
/* 2381 */     $$1.vertex($$12, $$2, $$6, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2382 */     $$1.vertex($$12, $$2, $$3, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2383 */     $$1.vertex($$12, $$5, $$6, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2384 */     $$1.vertex($$12, $$5, $$3, $$7).color($$8, $$9, $$10, $$11).endVertex();
/*      */     
/* 2386 */     $$1.vertex($$12, $$5, $$3, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2387 */     $$1.vertex($$12, $$5, $$3, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2388 */     $$1.vertex($$12, $$5, $$6, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2389 */     $$1.vertex($$12, $$5, $$6, $$4).color($$8, $$9, $$10, $$11).endVertex();
/*      */     
/* 2391 */     $$1.vertex($$12, $$5, $$6, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2392 */     $$1.vertex($$12, $$5, $$3, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2393 */     $$1.vertex($$12, $$2, $$6, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2394 */     $$1.vertex($$12, $$2, $$3, $$4).color($$8, $$9, $$10, $$11).endVertex();
/*      */     
/* 2396 */     $$1.vertex($$12, $$2, $$3, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2397 */     $$1.vertex($$12, $$5, $$3, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2398 */     $$1.vertex($$12, $$2, $$3, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2399 */     $$1.vertex($$12, $$5, $$3, $$7).color($$8, $$9, $$10, $$11).endVertex();
/*      */     
/* 2401 */     $$1.vertex($$12, $$5, $$3, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2402 */     $$1.vertex($$12, $$2, $$6, $$4).color($$8, $$9, $$10, $$11).endVertex();
/*      */     
/* 2404 */     $$1.vertex($$12, $$2, $$6, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2405 */     $$1.vertex($$12, $$2, $$6, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2406 */     $$1.vertex($$12, $$5, $$6, $$4).color($$8, $$9, $$10, $$11).endVertex();
/* 2407 */     $$1.vertex($$12, $$5, $$6, $$7).color($$8, $$9, $$10, $$11).endVertex();
/*      */     
/* 2409 */     $$1.vertex($$12, $$5, $$6, $$7).color($$8, $$9, $$10, $$11).endVertex();
/* 2410 */     $$1.vertex($$12, $$5, $$6, $$7).color($$8, $$9, $$10, $$11).endVertex();
/*      */   }
/*      */   
/*      */   public void blockChanged(BlockGetter $$0, BlockPos $$1, BlockState $$2, BlockState $$3, int $$4) {
/* 2414 */     setBlockDirty($$1, (($$4 & 0x8) != 0));
/*      */   }
/*      */   
/*      */   private void setBlockDirty(BlockPos $$0, boolean $$1) {
/* 2418 */     for (int $$2 = $$0.getZ() - 1; $$2 <= $$0.getZ() + 1; $$2++) {
/* 2419 */       for (int $$3 = $$0.getX() - 1; $$3 <= $$0.getX() + 1; $$3++) {
/* 2420 */         for (int $$4 = $$0.getY() - 1; $$4 <= $$0.getY() + 1; $$4++) {
/* 2421 */           setSectionDirty(SectionPos.blockToSectionCoord($$3), SectionPos.blockToSectionCoord($$4), SectionPos.blockToSectionCoord($$2), $$1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBlocksDirty(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 2428 */     for (int $$6 = $$2 - 1; $$6 <= $$5 + 1; $$6++) {
/* 2429 */       for (int $$7 = $$0 - 1; $$7 <= $$3 + 1; $$7++) {
/* 2430 */         for (int $$8 = $$1 - 1; $$8 <= $$4 + 1; $$8++) {
/* 2431 */           setSectionDirty(SectionPos.blockToSectionCoord($$7), SectionPos.blockToSectionCoord($$8), SectionPos.blockToSectionCoord($$6));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setBlockDirty(BlockPos $$0, BlockState $$1, BlockState $$2) {
/* 2438 */     if (this.minecraft.getModelManager().requiresRender($$1, $$2)) {
/* 2439 */       setBlocksDirty($$0.getX(), $$0.getY(), $$0.getZ(), $$0.getX(), $$0.getY(), $$0.getZ());
/*      */     }
/*      */   }
/*      */   
/*      */   public void setSectionDirtyWithNeighbors(int $$0, int $$1, int $$2) {
/* 2444 */     for (int $$3 = $$2 - 1; $$3 <= $$2 + 1; $$3++) {
/* 2445 */       for (int $$4 = $$0 - 1; $$4 <= $$0 + 1; $$4++) {
/* 2446 */         for (int $$5 = $$1 - 1; $$5 <= $$1 + 1; $$5++) {
/* 2447 */           setSectionDirty($$4, $$5, $$3);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setSectionDirty(int $$0, int $$1, int $$2) {
/* 2454 */     setSectionDirty($$0, $$1, $$2, false);
/*      */   }
/*      */   
/*      */   private void setSectionDirty(int $$0, int $$1, int $$2, boolean $$3) {
/* 2458 */     this.viewArea.setDirty($$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public void playStreamingMusic(@Nullable SoundEvent $$0, BlockPos $$1) {
/* 2462 */     SoundInstance $$2 = this.playingRecords.get($$1);
/*      */     
/* 2464 */     if ($$2 != null) {
/* 2465 */       this.minecraft.getSoundManager().stop($$2);
/* 2466 */       this.playingRecords.remove($$1);
/*      */     } 
/*      */     
/* 2469 */     if ($$0 != null) {
/* 2470 */       RecordItem $$3 = RecordItem.getBySound($$0);
/* 2471 */       if ($$3 != null) {
/* 2472 */         this.minecraft.gui.setNowPlaying((Component)$$3.getDisplayName());
/*      */       }
/*      */       
/* 2475 */       SimpleSoundInstance simpleSoundInstance = SimpleSoundInstance.forRecord($$0, Vec3.atCenterOf((Vec3i)$$1));
/* 2476 */       this.playingRecords.put($$1, simpleSoundInstance);
/* 2477 */       this.minecraft.getSoundManager().play((SoundInstance)simpleSoundInstance);
/*      */     } 
/*      */     
/* 2480 */     notifyNearbyEntities((Level)this.level, $$1, ($$0 != null));
/*      */   }
/*      */   
/*      */   private void notifyNearbyEntities(Level $$0, BlockPos $$1, boolean $$2) {
/* 2484 */     List<LivingEntity> $$3 = $$0.getEntitiesOfClass(LivingEntity.class, (new AABB($$1)).inflate(3.0D));
/* 2485 */     for (LivingEntity $$4 : $$3) {
/* 2486 */       $$4.setRecordPlayingNearby($$1, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   public void addParticle(ParticleOptions $$0, boolean $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 2491 */     addParticle($$0, $$1, false, $$2, $$3, $$4, $$5, $$6, $$7);
/*      */   }
/*      */   
/*      */   public void addParticle(ParticleOptions $$0, boolean $$1, boolean $$2, double $$3, double $$4, double $$5, double $$6, double $$7, double $$8) {
/*      */     try {
/* 2496 */       addParticleInternal($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
/* 2497 */     } catch (Throwable $$9) {
/* 2498 */       CrashReport $$10 = CrashReport.forThrowable($$9, "Exception while adding particle");
/* 2499 */       CrashReportCategory $$11 = $$10.addCategory("Particle being added");
/*      */       
/* 2501 */       $$11.setDetail("ID", BuiltInRegistries.PARTICLE_TYPE.getKey($$0.getType()));
/* 2502 */       $$11.setDetail("Parameters", $$0.writeToString());
/* 2503 */       $$11.setDetail("Position", () -> CrashReportCategory.formatLocation((LevelHeightAccessor)this.level, $$0, $$1, $$2));
/*      */       
/* 2505 */       throw new ReportedException($$10);
/*      */     } 
/*      */   }
/*      */   
/*      */   private <T extends ParticleOptions> void addParticle(T $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 2510 */     addParticle((ParticleOptions)$$0, $$0.getType().getOverrideLimiter(), $$1, $$2, $$3, $$4, $$5, $$6);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private Particle addParticleInternal(ParticleOptions $$0, boolean $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 2515 */     return addParticleInternal($$0, $$1, false, $$2, $$3, $$4, $$5, $$6, $$7);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private Particle addParticleInternal(ParticleOptions $$0, boolean $$1, boolean $$2, double $$3, double $$4, double $$5, double $$6, double $$7, double $$8) {
/* 2520 */     Camera $$9 = this.minecraft.gameRenderer.getMainCamera();
/* 2521 */     ParticleStatus $$10 = calculateParticleLevel($$2);
/*      */     
/* 2523 */     if ($$1) {
/* 2524 */       return this.minecraft.particleEngine.createParticle($$0, $$3, $$4, $$5, $$6, $$7, $$8);
/*      */     }
/*      */     
/* 2527 */     if ($$9.getPosition().distanceToSqr($$3, $$4, $$5) > 1024.0D) {
/* 2528 */       return null;
/*      */     }
/*      */     
/* 2531 */     if ($$10 == ParticleStatus.MINIMAL)
/*      */     {
/* 2533 */       return null;
/*      */     }
/*      */     
/* 2536 */     return this.minecraft.particleEngine.createParticle($$0, $$3, $$4, $$5, $$6, $$7, $$8);
/*      */   }
/*      */   
/*      */   private ParticleStatus calculateParticleLevel(boolean $$0) {
/* 2540 */     ParticleStatus $$1 = (ParticleStatus)this.minecraft.options.particles().get();
/*      */     
/* 2542 */     if ($$0 && $$1 == ParticleStatus.MINIMAL)
/*      */     {
/* 2544 */       if (this.level.random.nextInt(10) == 0) {
/* 2545 */         $$1 = ParticleStatus.DECREASED;
/*      */       }
/*      */     }
/*      */     
/* 2549 */     if ($$1 == ParticleStatus.DECREASED)
/*      */     {
/* 2551 */       if (this.level.random.nextInt(3) == 0) {
/* 2552 */         $$1 = ParticleStatus.MINIMAL;
/*      */       }
/*      */     }
/*      */     
/* 2556 */     return $$1;
/*      */   }
/*      */   
/*      */   public void clear() {}
/*      */   
/*      */   public void globalLevelEvent(int $$0, BlockPos $$1, int $$2) {
/*      */     Camera $$3;
/* 2563 */     switch ($$0) {
/*      */       case 1023:
/*      */       case 1028:
/*      */       case 1038:
/* 2567 */         $$3 = this.minecraft.gameRenderer.getMainCamera();
/* 2568 */         if ($$3.isInitialized()) {
/*      */           
/* 2570 */           double $$4 = $$1.getX() - ($$3.getPosition()).x;
/* 2571 */           double $$5 = $$1.getY() - ($$3.getPosition()).y;
/* 2572 */           double $$6 = $$1.getZ() - ($$3.getPosition()).z;
/*      */           
/* 2574 */           double $$7 = Math.sqrt($$4 * $$4 + $$5 * $$5 + $$6 * $$6);
/* 2575 */           double $$8 = ($$3.getPosition()).x;
/* 2576 */           double $$9 = ($$3.getPosition()).y;
/* 2577 */           double $$10 = ($$3.getPosition()).z;
/*      */           
/* 2579 */           if ($$7 > 0.0D) {
/* 2580 */             $$8 += $$4 / $$7 * 2.0D;
/* 2581 */             $$9 += $$5 / $$7 * 2.0D;
/* 2582 */             $$10 += $$6 / $$7 * 2.0D;
/*      */           } 
/*      */           
/* 2585 */           if ($$0 == 1023) {
/* 2586 */             this.level.playLocalSound($$8, $$9, $$10, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.0F, 1.0F, false); break;
/* 2587 */           }  if ($$0 == 1038) {
/* 2588 */             this.level.playLocalSound($$8, $$9, $$10, SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 1.0F, 1.0F, false); break;
/*      */           } 
/* 2590 */           this.level.playLocalSound($$8, $$9, $$10, SoundEvents.ENDER_DRAGON_DEATH, SoundSource.HOSTILE, 5.0F, 1.0F, false);
/*      */         }  break;
/*      */     }  } public void levelEvent(int $$0, BlockPos $$1, int $$2) { double $$4; Vec3 $$9; BlockState $$23; int $$10; float $$11; BlockState $$25; double $$5; float $$12; int $$27, $$31; float $$13; Block block; boolean $$44; int $$52; BlockState $$53; double $$6; SimpleParticleType simpleParticleType; int $$45; boolean $$54; int $$15; float $$46;
/*      */     int $$55, $$56, $$60, $$64, $$71, $$7;
/*      */     double $$8;
/*      */     float $$47;
/*      */     Item item;
/*      */     int $$48;
/* 2598 */     RandomSource $$3 = this.level.random;
/* 2599 */     switch ($$0) {
/*      */       case 1035:
/* 2601 */         this.level.playLocalSound($$1, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       case 1033:
/* 2604 */         this.level.playLocalSound($$1, SoundEvents.CHORUS_FLOWER_GROW, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       case 1034:
/* 2607 */         this.level.playLocalSound($$1, SoundEvents.CHORUS_FLOWER_DEATH, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       case 1032:
/* 2610 */         this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forLocalAmbience(SoundEvents.PORTAL_TRAVEL, $$3.nextFloat() * 0.4F + 0.8F, 0.25F));
/*      */         break;
/*      */       case 1001:
/* 2613 */         this.level.playLocalSound($$1, SoundEvents.DISPENSER_FAIL, SoundSource.BLOCKS, 1.0F, 1.2F, false);
/*      */         break;
/*      */       case 1000:
/* 2616 */         this.level.playLocalSound($$1, SoundEvents.DISPENSER_DISPENSE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       case 1049:
/* 2619 */         this.level.playLocalSound($$1, SoundEvents.CRAFTER_CRAFT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       case 1050:
/* 2622 */         this.level.playLocalSound($$1, SoundEvents.CRAFTER_FAIL, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       case 1003:
/* 2625 */         this.level.playLocalSound($$1, SoundEvents.ENDER_EYE_LAUNCH, SoundSource.NEUTRAL, 1.0F, 1.2F, false);
/*      */         break;
/*      */       case 1004:
/* 2628 */         this.level.playLocalSound($$1, SoundEvents.FIREWORK_ROCKET_SHOOT, SoundSource.NEUTRAL, 1.0F, 1.2F, false);
/*      */         break;
/*      */       case 1002:
/* 2631 */         this.level.playLocalSound($$1, SoundEvents.DISPENSER_LAUNCH, SoundSource.BLOCKS, 1.0F, 1.2F, false);
/*      */         break;
/*      */       case 2010:
/* 2634 */         shootParticles($$2, $$1, $$3, ParticleTypes.WHITE_SMOKE);
/*      */         break;
/*      */       case 2000:
/* 2637 */         shootParticles($$2, $$1, $$3, ParticleTypes.SMOKE);
/*      */         break;
/*      */       case 2003:
/* 2640 */         $$4 = $$1.getX() + 0.5D;
/* 2641 */         $$5 = $$1.getY();
/* 2642 */         $$6 = $$1.getZ() + 0.5D;
/*      */         
/* 2644 */         for ($$7 = 0; $$7 < 8; $$7++) {
/* 2645 */           addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack((ItemLike)Items.ENDER_EYE)), $$4, $$5, $$6, $$3.nextGaussian() * 0.15D, $$3.nextDouble() * 0.2D, $$3.nextGaussian() * 0.15D);
/*      */         }
/* 2647 */         for ($$8 = 0.0D; $$8 < 6.283185307179586D; $$8 += 0.15707963267948966D) {
/* 2648 */           addParticle(ParticleTypes.PORTAL, $$4 + Math.cos($$8) * 5.0D, $$5 - 0.4D, $$6 + Math.sin($$8) * 5.0D, Math.cos($$8) * -5.0D, 0.0D, Math.sin($$8) * -5.0D);
/* 2649 */           addParticle(ParticleTypes.PORTAL, $$4 + Math.cos($$8) * 5.0D, $$5 - 0.4D, $$6 + Math.sin($$8) * 5.0D, Math.cos($$8) * -7.0D, 0.0D, Math.sin($$8) * -7.0D);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 2002:
/*      */       case 2007:
/* 2655 */         $$9 = Vec3.atBottomCenterOf((Vec3i)$$1);
/*      */         
/* 2657 */         for ($$10 = 0; $$10 < 8; $$10++) {
/* 2658 */           addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack((ItemLike)Items.SPLASH_POTION)), $$9.x, $$9.y, $$9.z, $$3.nextGaussian() * 0.15D, $$3.nextDouble() * 0.2D, $$3.nextGaussian() * 0.15D);
/*      */         }
/*      */         
/* 2661 */         $$11 = ($$2 >> 16 & 0xFF) / 255.0F;
/* 2662 */         $$12 = ($$2 >> 8 & 0xFF) / 255.0F;
/* 2663 */         $$13 = ($$2 >> 0 & 0xFF) / 255.0F;
/*      */         
/* 2665 */         simpleParticleType = ($$0 == 2007) ? ParticleTypes.INSTANT_EFFECT : ParticleTypes.EFFECT;
/* 2666 */         for ($$15 = 0; $$15 < 100; $$15++) {
/* 2667 */           double $$16 = $$3.nextDouble() * 4.0D;
/* 2668 */           double $$17 = $$3.nextDouble() * Math.PI * 2.0D;
/* 2669 */           double $$18 = Math.cos($$17) * $$16;
/* 2670 */           double $$19 = 0.01D + $$3.nextDouble() * 0.5D;
/* 2671 */           double $$20 = Math.sin($$17) * $$16;
/*      */           
/* 2673 */           Particle $$21 = addParticleInternal((ParticleOptions)simpleParticleType, simpleParticleType.getType().getOverrideLimiter(), $$9.x + $$18 * 0.1D, $$9.y + 0.3D, $$9.z + $$20 * 0.1D, $$18, $$19, $$20);
/* 2674 */           if ($$21 != null) {
/* 2675 */             float $$22 = 0.75F + $$3.nextFloat() * 0.25F;
/* 2676 */             $$21.setColor($$11 * $$22, $$12 * $$22, $$13 * $$22);
/* 2677 */             $$21.setPower((float)$$16);
/*      */           } 
/*      */         } 
/* 2680 */         this.level.playLocalSound($$1, SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, $$3.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 2001:
/* 2684 */         $$23 = Block.stateById($$2);
/* 2685 */         if (!$$23.isAir()) {
/* 2686 */           SoundType $$24 = $$23.getSoundType();
/* 2687 */           this.level.playLocalSound($$1, $$24.getBreakSound(), SoundSource.BLOCKS, ($$24.getVolume() + 1.0F) / 2.0F, $$24.getPitch() * 0.8F, false);
/*      */         } 
/* 2689 */         this.level.addDestroyBlockEffect($$1, $$23);
/*      */         break;
/*      */       case 3008:
/* 2692 */         $$25 = Block.stateById($$2);
/* 2693 */         block = $$25.getBlock(); if (block instanceof BrushableBlock) { BrushableBlock $$26 = (BrushableBlock)block;
/* 2694 */           this.level.playLocalSound($$1, $$26.getBrushCompletedSound(), SoundSource.PLAYERS, 1.0F, 1.0F, false); }
/*      */         
/* 2696 */         this.level.addDestroyBlockEffect($$1, $$25);
/*      */         break;
/*      */       case 2004:
/* 2699 */         for ($$27 = 0; $$27 < 20; $$27++) {
/* 2700 */           double $$28 = $$1.getX() + 0.5D + ($$3.nextDouble() - 0.5D) * 2.0D;
/* 2701 */           double $$29 = $$1.getY() + 0.5D + ($$3.nextDouble() - 0.5D) * 2.0D;
/* 2702 */           double $$30 = $$1.getZ() + 0.5D + ($$3.nextDouble() - 0.5D) * 2.0D;
/*      */           
/* 2704 */           this.level.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$28, $$29, $$30, 0.0D, 0.0D, 0.0D);
/* 2705 */           this.level.addParticle((ParticleOptions)ParticleTypes.FLAME, $$28, $$29, $$30, 0.0D, 0.0D, 0.0D);
/*      */         } 
/*      */         break;
/*      */       case 3011:
/* 2709 */         TrialSpawner.addSpawnParticles((Level)this.level, $$1, $$3);
/*      */         break;
/*      */       case 3012:
/* 2712 */         this.level.playLocalSound($$1, SoundEvents.TRIAL_SPAWNER_SPAWN_MOB, SoundSource.BLOCKS, 1.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, true);
/* 2713 */         TrialSpawner.addSpawnParticles((Level)this.level, $$1, $$3);
/*      */         break;
/*      */       case 3013:
/* 2716 */         this.level.playLocalSound($$1, SoundEvents.TRIAL_SPAWNER_DETECT_PLAYER, SoundSource.BLOCKS, 1.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, true);
/* 2717 */         TrialSpawner.addDetectPlayerParticles((Level)this.level, $$1, $$3, $$2);
/*      */         break;
/*      */       case 3014:
/* 2720 */         this.level.playLocalSound($$1, SoundEvents.TRIAL_SPAWNER_EJECT_ITEM, SoundSource.BLOCKS, 1.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, true);
/* 2721 */         TrialSpawner.addEjectItemParticles((Level)this.level, $$1, $$3);
/*      */         break;
/*      */       case 2005:
/* 2724 */         BoneMealItem.addGrowthParticles((LevelAccessor)this.level, $$1, $$2);
/*      */         break;
/*      */       case 1505:
/* 2727 */         BoneMealItem.addGrowthParticles((LevelAccessor)this.level, $$1, $$2);
/* 2728 */         this.level.playLocalSound($$1, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       case 3009:
/* 2731 */         ParticleUtils.spawnParticlesOnBlockFaces((Level)this.level, $$1, (ParticleOptions)ParticleTypes.EGG_CRACK, (IntProvider)UniformInt.of(3, 6));
/*      */         break;
/*      */       case 3002:
/* 2734 */         if ($$2 >= 0 && $$2 < Direction.Axis.VALUES.length) {
/* 2735 */           ParticleUtils.spawnParticlesAlongAxis(Direction.Axis.VALUES[$$2], (Level)this.level, $$1, 0.125D, (ParticleOptions)ParticleTypes.ELECTRIC_SPARK, UniformInt.of(10, 19)); break;
/*      */         } 
/* 2737 */         ParticleUtils.spawnParticlesOnBlockFaces((Level)this.level, $$1, (ParticleOptions)ParticleTypes.ELECTRIC_SPARK, (IntProvider)UniformInt.of(3, 5));
/*      */         break;
/*      */       
/*      */       case 3006:
/* 2741 */         $$31 = $$2 >> 6;
/* 2742 */         if ($$31 > 0) {
/*      */           
/* 2744 */           if ($$3.nextFloat() < 0.3F + $$31 * 0.1F) {
/* 2745 */             float $$32 = 0.15F + 0.02F * $$31 * $$31 * $$3.nextFloat();
/* 2746 */             float $$33 = 0.4F + 0.3F * $$31 * $$3.nextFloat();
/* 2747 */             this.level.playLocalSound($$1, SoundEvents.SCULK_BLOCK_CHARGE, SoundSource.BLOCKS, $$32, $$33, false);
/*      */           } 
/* 2749 */           byte $$34 = (byte)($$2 & 0x3F);
/* 2750 */           UniformInt uniformInt = UniformInt.of(0, $$31);
/* 2751 */           float $$36 = 0.005F;
/* 2752 */           Supplier<Vec3> $$37 = () -> new Vec3(Mth.nextDouble($$0, -0.004999999888241291D, 0.004999999888241291D), Mth.nextDouble($$0, -0.004999999888241291D, 0.004999999888241291D), Mth.nextDouble($$0, -0.004999999888241291D, 0.004999999888241291D));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2757 */           if ($$34 == 0) {
/* 2758 */             for (Direction $$38 : Direction.values()) {
/* 2759 */               float $$39 = ($$38 == Direction.DOWN) ? 3.1415927F : 0.0F;
/* 2760 */               double $$40 = ($$38.getAxis() == Direction.Axis.Y) ? 0.65D : 0.57D;
/* 2761 */               ParticleUtils.spawnParticlesOnBlockFace((Level)this.level, $$1, (ParticleOptions)new SculkChargeParticleOptions($$39), (IntProvider)uniformInt, $$38, $$37, $$40);
/*      */             }  break;
/*      */           } 
/* 2764 */           for (Direction $$41 : MultifaceBlock.unpack($$34)) {
/* 2765 */             float $$42 = ($$41 == Direction.UP) ? 3.1415927F : 0.0F;
/* 2766 */             double $$43 = 0.35D;
/* 2767 */             ParticleUtils.spawnParticlesOnBlockFace((Level)this.level, $$1, (ParticleOptions)new SculkChargeParticleOptions($$42), (IntProvider)uniformInt, $$41, $$37, 0.35D);
/*      */           } 
/*      */           break;
/*      */         } 
/* 2771 */         this.level.playLocalSound($$1, SoundEvents.SCULK_BLOCK_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/* 2772 */         $$44 = this.level.getBlockState($$1).isCollisionShapeFullBlock((BlockGetter)this.level, $$1);
/* 2773 */         $$45 = $$44 ? 40 : 20;
/* 2774 */         $$46 = $$44 ? 0.45F : 0.25F;
/* 2775 */         $$47 = 0.07F;
/* 2776 */         for ($$48 = 0; $$48 < $$45; $$48++) {
/* 2777 */           float $$49 = 2.0F * $$3.nextFloat() - 1.0F;
/* 2778 */           float $$50 = 2.0F * $$3.nextFloat() - 1.0F;
/* 2779 */           float $$51 = 2.0F * $$3.nextFloat() - 1.0F;
/* 2780 */           this.level.addParticle((ParticleOptions)ParticleTypes.SCULK_CHARGE_POP, $$1
/*      */               
/* 2782 */               .getX() + 0.5D + ($$49 * $$46), $$1.getY() + 0.5D + ($$50 * $$46), $$1.getZ() + 0.5D + ($$51 * $$46), ($$49 * 0.07F), ($$50 * 0.07F), ($$51 * 0.07F));
/*      */         } 
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3007:
/* 2789 */         for ($$52 = 0; $$52 < 10; $$52++) {
/* 2790 */           this.level.addParticle((ParticleOptions)new ShriekParticleOption($$52 * 5), false, $$1.getX() + 0.5D, $$1.getY() + SculkShriekerBlock.TOP_Y, $$1.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
/*      */         }
/* 2792 */         $$53 = this.level.getBlockState($$1);
/* 2793 */         $$54 = ($$53.hasProperty((Property)BlockStateProperties.WATERLOGGED) && ((Boolean)$$53.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue());
/*      */         
/* 2795 */         if (!$$54) {
/* 2796 */           this.level.playLocalSound($$1.getX() + 0.5D, $$1.getY() + SculkShriekerBlock.TOP_Y, $$1.getZ() + 0.5D, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 2.0F, 0.6F + this.level.random.nextFloat() * 0.4F, false);
/*      */         }
/*      */         break;
/*      */       case 3003:
/* 2800 */         ParticleUtils.spawnParticlesOnBlockFaces((Level)this.level, $$1, (ParticleOptions)ParticleTypes.WAX_ON, (IntProvider)UniformInt.of(3, 5));
/* 2801 */         this.level.playLocalSound($$1, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       case 3004:
/* 2804 */         ParticleUtils.spawnParticlesOnBlockFaces((Level)this.level, $$1, (ParticleOptions)ParticleTypes.WAX_OFF, (IntProvider)UniformInt.of(3, 5));
/*      */         break;
/*      */       case 3005:
/* 2807 */         ParticleUtils.spawnParticlesOnBlockFaces((Level)this.level, $$1, (ParticleOptions)ParticleTypes.SCRAPE, (IntProvider)UniformInt.of(3, 5));
/*      */         break;
/*      */       case 2008:
/* 2810 */         this.level.addParticle((ParticleOptions)ParticleTypes.EXPLOSION, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
/*      */         break;
/*      */       case 3010:
/* 2813 */         ParticleUtils.spawnParticlesOnBlockFaces((Level)this.level, $$1, (ParticleOptions)ParticleTypes.GUST_DUST, (IntProvider)UniformInt.of(3, 6));
/*      */         break;
/*      */       case 1500:
/* 2816 */         ComposterBlock.handleFill((Level)this.level, $$1, ($$2 > 0));
/*      */         break;
/*      */       case 1504:
/* 2819 */         PointedDripstoneBlock.spawnDripParticle((Level)this.level, $$1, this.level.getBlockState($$1));
/*      */         break;
/*      */       case 1501:
/* 2822 */         this.level.playLocalSound($$1, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + ($$3.nextFloat() - $$3.nextFloat()) * 0.8F, false);
/* 2823 */         for ($$55 = 0; $$55 < 8; $$55++) {
/* 2824 */           this.level.addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, $$1.getX() + $$3.nextDouble(), $$1.getY() + 1.2D, $$1.getZ() + $$3.nextDouble(), 0.0D, 0.0D, 0.0D);
/*      */         }
/*      */         break;
/*      */       case 1502:
/* 2828 */         this.level.playLocalSound($$1, SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS, 0.5F, 2.6F + ($$3.nextFloat() - $$3.nextFloat()) * 0.8F, false);
/* 2829 */         for ($$56 = 0; $$56 < 5; $$56++) {
/* 2830 */           double $$57 = $$1.getX() + $$3.nextDouble() * 0.6D + 0.2D;
/* 2831 */           double $$58 = $$1.getY() + $$3.nextDouble() * 0.6D + 0.2D;
/* 2832 */           double $$59 = $$1.getZ() + $$3.nextDouble() * 0.6D + 0.2D;
/*      */           
/* 2834 */           this.level.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$57, $$58, $$59, 0.0D, 0.0D, 0.0D);
/*      */         } 
/*      */         break;
/*      */       case 1503:
/* 2838 */         this.level.playLocalSound($$1, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0F, 1.0F, false);
/* 2839 */         for ($$60 = 0; $$60 < 16; $$60++) {
/* 2840 */           double $$61 = $$1.getX() + (5.0D + $$3.nextDouble() * 6.0D) / 16.0D;
/* 2841 */           double $$62 = $$1.getY() + 0.8125D;
/* 2842 */           double $$63 = $$1.getZ() + (5.0D + $$3.nextDouble() * 6.0D) / 16.0D;
/*      */           
/* 2844 */           this.level.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$61, $$62, $$63, 0.0D, 0.0D, 0.0D);
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 2006:
/* 2849 */         for ($$64 = 0; $$64 < 200; $$64++) {
/* 2850 */           float $$65 = $$3.nextFloat() * 4.0F;
/* 2851 */           float $$66 = $$3.nextFloat() * 6.2831855F;
/* 2852 */           double $$67 = (Mth.cos($$66) * $$65);
/* 2853 */           double $$68 = 0.01D + $$3.nextDouble() * 0.5D;
/* 2854 */           double $$69 = (Mth.sin($$66) * $$65);
/*      */           
/* 2856 */           Particle $$70 = addParticleInternal((ParticleOptions)ParticleTypes.DRAGON_BREATH, false, $$1.getX() + $$67 * 0.1D, $$1.getY() + 0.3D, $$1.getZ() + $$69 * 0.1D, $$67, $$68, $$69);
/* 2857 */           if ($$70 != null) {
/* 2858 */             $$70.setPower($$65);
/*      */           }
/*      */         } 
/*      */         
/* 2862 */         if ($$2 == 1) {
/* 2863 */           this.level.playLocalSound($$1, SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.HOSTILE, 1.0F, $$3.nextFloat() * 0.1F + 0.9F, false);
/*      */         }
/*      */         break;
/*      */       case 2009:
/* 2867 */         for ($$71 = 0; $$71 < 8; $$71++) {
/* 2868 */           this.level.addParticle((ParticleOptions)ParticleTypes.CLOUD, $$1.getX() + $$3.nextDouble(), $$1.getY() + 1.2D, $$1.getZ() + $$3.nextDouble(), 0.0D, 0.0D, 0.0D);
/*      */         }
/*      */         break;
/*      */       case 1009:
/* 2872 */         if ($$2 == 0) {
/* 2873 */           this.level.playLocalSound($$1, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + ($$3.nextFloat() - $$3.nextFloat()) * 0.8F, false); break;
/* 2874 */         }  if ($$2 == 1) {
/* 2875 */           this.level.playLocalSound($$1, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 0.7F, 1.6F + ($$3.nextFloat() - $$3.nextFloat()) * 0.4F, false);
/*      */         }
/*      */         break;
/*      */       case 1029:
/* 2879 */         this.level.playLocalSound($$1, SoundEvents.ANVIL_DESTROY, SoundSource.BLOCKS, 1.0F, $$3.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 1030:
/* 2882 */         this.level.playLocalSound($$1, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, $$3.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 1044:
/* 2885 */         this.level.playLocalSound($$1, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 1031:
/* 2888 */         this.level.playLocalSound($$1, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS, 0.3F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 1039:
/* 2891 */         this.level.playLocalSound($$1, SoundEvents.PHANTOM_BITE, SoundSource.HOSTILE, 0.3F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 1010:
/* 2894 */         item = Item.byId($$2); if (item instanceof RecordItem) { RecordItem $$72 = (RecordItem)item;
/* 2895 */           playStreamingMusic($$72.getSound(), $$1); }
/*      */         
/*      */         break;
/*      */       case 1011:
/* 2899 */         playStreamingMusic(null, $$1);
/*      */         break;
/*      */       case 1015:
/* 2902 */         this.level.playLocalSound($$1, SoundEvents.GHAST_WARN, SoundSource.HOSTILE, 10.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1017:
/* 2905 */         this.level.playLocalSound($$1, SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.HOSTILE, 10.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1016:
/* 2908 */         this.level.playLocalSound($$1, SoundEvents.GHAST_SHOOT, SoundSource.HOSTILE, 10.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1019:
/* 2911 */         this.level.playLocalSound($$1, SoundEvents.ZOMBIE_ATTACK_WOODEN_DOOR, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1022:
/* 2914 */         this.level.playLocalSound($$1, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1021:
/* 2917 */         this.level.playLocalSound($$1, SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1020:
/* 2920 */         this.level.playLocalSound($$1, SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1018:
/* 2923 */         this.level.playLocalSound($$1, SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1024:
/* 2926 */         this.level.playLocalSound($$1, SoundEvents.WITHER_SHOOT, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1026:
/* 2929 */         this.level.playLocalSound($$1, SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1027:
/* 2932 */         this.level.playLocalSound($$1, SoundEvents.ZOMBIE_VILLAGER_CONVERTED, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1040:
/* 2935 */         this.level.playLocalSound($$1, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1041:
/* 2938 */         this.level.playLocalSound($$1, SoundEvents.HUSK_CONVERTED_TO_ZOMBIE, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1025:
/* 2941 */         this.level.playLocalSound($$1, SoundEvents.BAT_TAKEOFF, SoundSource.NEUTRAL, 0.05F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       case 1042:
/* 2944 */         this.level.playLocalSound($$1, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 1043:
/* 2947 */         this.level.playLocalSound($$1, SoundEvents.BOOK_PAGE_TURN, SoundSource.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 3000:
/* 2950 */         this.level.addParticle((ParticleOptions)ParticleTypes.EXPLOSION_EMITTER, true, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
/* 2951 */         this.level.playLocalSound($$1, SoundEvents.END_GATEWAY_SPAWN, SoundSource.BLOCKS, 10.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
/*      */         break;
/*      */       case 3001:
/* 2954 */         this.level.playLocalSound($$1, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.HOSTILE, 64.0F, 0.8F + this.level.random.nextFloat() * 0.3F, false);
/*      */         break;
/*      */       case 1045:
/* 2957 */         this.level.playLocalSound($$1, SoundEvents.POINTED_DRIPSTONE_LAND, SoundSource.BLOCKS, 2.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 1046:
/* 2960 */         this.level.playLocalSound($$1, SoundEvents.POINTED_DRIPSTONE_DRIP_LAVA_INTO_CAULDRON, SoundSource.BLOCKS, 2.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 1047:
/* 2963 */         this.level.playLocalSound($$1, SoundEvents.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON, SoundSource.BLOCKS, 2.0F, this.level.random.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       case 1048:
/* 2966 */         this.level.playLocalSound($$1, SoundEvents.SKELETON_CONVERTED_TO_STRAY, SoundSource.HOSTILE, 2.0F, ($$3.nextFloat() - $$3.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */     }  }
/*      */ 
/*      */   
/*      */   public void destroyBlockProgress(int $$0, BlockPos $$1, int $$2) {
/* 2972 */     if ($$2 < 0 || $$2 >= 10) {
/* 2973 */       BlockDestructionProgress $$3 = (BlockDestructionProgress)this.destroyingBlocks.remove($$0);
/* 2974 */       if ($$3 != null) {
/* 2975 */         removeProgress($$3);
/*      */       }
/*      */     } else {
/* 2978 */       BlockDestructionProgress $$4 = (BlockDestructionProgress)this.destroyingBlocks.get($$0);
/* 2979 */       if ($$4 != null) {
/* 2980 */         removeProgress($$4);
/*      */       }
/*      */       
/* 2983 */       if ($$4 == null || $$4.getPos().getX() != $$1.getX() || $$4.getPos().getY() != $$1.getY() || $$4.getPos().getZ() != $$1.getZ()) {
/* 2984 */         $$4 = new BlockDestructionProgress($$0, $$1);
/* 2985 */         this.destroyingBlocks.put($$0, $$4);
/*      */       } 
/*      */       
/* 2988 */       $$4.setProgress($$2);
/* 2989 */       $$4.updateTick(this.ticks);
/* 2990 */       ((SortedSet<BlockDestructionProgress>)this.destructionProgress.computeIfAbsent($$4.getPos().asLong(), $$0 -> Sets.newTreeSet())).add($$4);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean hasRenderedAllSections() {
/* 2995 */     return this.sectionRenderDispatcher.isQueueEmpty();
/*      */   }
/*      */   
/*      */   public void onChunkLoaded(ChunkPos $$0) {
/* 2999 */     this.sectionOcclusionGraph.onChunkLoaded($$0);
/*      */   }
/*      */   
/*      */   public void needsUpdate() {
/* 3003 */     this.sectionOcclusionGraph.invalidate();
/* 3004 */     this.generateClouds = true;
/*      */   }
/*      */   
/*      */   public void updateGlobalBlockEntities(Collection<BlockEntity> $$0, Collection<BlockEntity> $$1) {
/* 3008 */     synchronized (this.globalBlockEntities) {
/* 3009 */       this.globalBlockEntities.removeAll($$0);
/* 3010 */       this.globalBlockEntities.addAll($$1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static int getLightColor(BlockAndTintGetter $$0, BlockPos $$1) {
/* 3015 */     return getLightColor($$0, $$0.getBlockState($$1), $$1);
/*      */   }
/*      */   
/*      */   public static int getLightColor(BlockAndTintGetter $$0, BlockState $$1, BlockPos $$2) {
/* 3019 */     if ($$1.emissiveRendering((BlockGetter)$$0, $$2))
/*      */     {
/* 3021 */       return 15728880;
/*      */     }
/* 3023 */     int $$3 = $$0.getBrightness(LightLayer.SKY, $$2);
/* 3024 */     int $$4 = $$0.getBrightness(LightLayer.BLOCK, $$2);
/* 3025 */     int $$5 = $$1.getLightEmission();
/* 3026 */     if ($$4 < $$5) {
/* 3027 */       $$4 = $$5;
/*      */     }
/* 3029 */     return $$3 << 20 | $$4 << 4;
/*      */   }
/*      */   
/*      */   public boolean isSectionCompiled(BlockPos $$0) {
/* 3033 */     SectionRenderDispatcher.RenderSection $$1 = this.viewArea.getRenderSectionAt($$0);
/* 3034 */     return ($$1 != null && $$1.compiled.get() != SectionRenderDispatcher.CompiledSection.UNCOMPILED);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RenderTarget entityTarget() {
/* 3039 */     return this.entityTarget;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RenderTarget getTranslucentTarget() {
/* 3044 */     return this.translucentTarget;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RenderTarget getItemEntityTarget() {
/* 3049 */     return this.itemEntityTarget;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RenderTarget getParticlesTarget() {
/* 3054 */     return this.particlesTarget;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RenderTarget getWeatherTarget() {
/* 3059 */     return this.weatherTarget;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RenderTarget getCloudsTarget() {
/* 3064 */     return this.cloudsTarget;
/*      */   }
/*      */   
/*      */   public static class TransparencyShaderException extends RuntimeException {
/*      */     public TransparencyShaderException(String $$0, Throwable $$1) {
/* 3069 */       super($$0, $$1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void shootParticles(int $$0, BlockPos $$1, RandomSource $$2, SimpleParticleType $$3) {
/* 3074 */     Direction $$4 = Direction.from3DDataValue($$0);
/* 3075 */     int $$5 = $$4.getStepX();
/* 3076 */     int $$6 = $$4.getStepY();
/* 3077 */     int $$7 = $$4.getStepZ();
/*      */     
/* 3079 */     double $$8 = $$1.getX() + $$5 * 0.6D + 0.5D;
/* 3080 */     double $$9 = $$1.getY() + $$6 * 0.6D + 0.5D;
/* 3081 */     double $$10 = $$1.getZ() + $$7 * 0.6D + 0.5D;
/*      */     
/* 3083 */     for (int $$11 = 0; $$11 < 10; $$11++) {
/* 3084 */       double $$12 = $$2.nextDouble() * 0.2D + 0.01D;
/* 3085 */       double $$13 = $$8 + $$5 * 0.01D + ($$2.nextDouble() - 0.5D) * $$7 * 0.5D;
/* 3086 */       double $$14 = $$9 + $$6 * 0.01D + ($$2.nextDouble() - 0.5D) * $$6 * 0.5D;
/* 3087 */       double $$15 = $$10 + $$7 * 0.01D + ($$2.nextDouble() - 0.5D) * $$5 * 0.5D;
/* 3088 */       double $$16 = $$5 * $$12 + $$2.nextGaussian() * 0.01D;
/* 3089 */       double $$17 = $$6 * $$12 + $$2.nextGaussian() * 0.01D;
/* 3090 */       double $$18 = $$7 * $$12 + $$2.nextGaussian() * 0.01D;
/* 3091 */       addParticle($$3, $$13, $$14, $$15, $$16, $$17, $$18);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\LevelRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */