/*     */ package net.minecraft.client.gui.components;
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.blaze3d.platform.GlUtil;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import java.lang.management.GarbageCollectorMXBean;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.debugchart.BandwidthDebugChart;
/*     */ import net.minecraft.client.gui.components.debugchart.FpsDebugChart;
/*     */ import net.minecraft.client.gui.components.debugchart.PingDebugChart;
/*     */ import net.minecraft.client.gui.components.debugchart.TpsDebugChart;
/*     */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*     */ import net.minecraft.client.renderer.PostChain;
/*     */ import net.minecraft.client.server.IntegratedServer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.ServerTickRateManager;
/*     */ import net.minecraft.server.level.ChunkHolder;
/*     */ import net.minecraft.server.level.ServerChunkCache;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.SampleLogger;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.TickRateManager;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.NaturalSpawner;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeSource;
/*     */ import net.minecraft.world.level.biome.Climate;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.RandomState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ 
/*     */ public class DebugScreenOverlay {
/*     */   private static final int COLOR_GREY = 14737632;
/*     */   private static final int MARGIN_RIGHT = 2;
/*     */   
/*     */   static {
/*  79 */     HEIGHTMAP_NAMES = (Map<Heightmap.Types, String>)Util.make(new EnumMap<>(Heightmap.Types.class), $$0 -> {
/*     */           $$0.put(Heightmap.Types.WORLD_SURFACE_WG, "SW");
/*     */           $$0.put(Heightmap.Types.WORLD_SURFACE, "S");
/*     */           $$0.put(Heightmap.Types.OCEAN_FLOOR_WG, "OW");
/*     */           $$0.put(Heightmap.Types.OCEAN_FLOOR, "O");
/*     */           $$0.put(Heightmap.Types.MOTION_BLOCKING, "M");
/*     */           $$0.put(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, "ML");
/*     */         });
/*     */   }
/*     */   private static final int MARGIN_LEFT = 2; private static final int MARGIN_TOP = 2; private static final Map<Heightmap.Types, String> HEIGHTMAP_NAMES;
/*     */   private final Minecraft minecraft;
/*     */   private final AllocationRateCalculator allocationRateCalculator;
/*     */   private final Font font;
/*     */   private HitResult block;
/*     */   private HitResult liquid;
/*     */   @Nullable
/*     */   private ChunkPos lastPos;
/*     */   @Nullable
/*     */   private LevelChunk clientChunk;
/*     */   @Nullable
/*     */   private CompletableFuture<LevelChunk> serverChunk;
/*     */   private boolean renderDebug;
/*     */   private boolean renderProfilerChart;
/*     */   private boolean renderFpsCharts;
/*     */   private boolean renderNetworkCharts;
/* 104 */   private final SampleLogger frameTimeLogger = new SampleLogger();
/* 105 */   private final SampleLogger tickTimeLogger = new SampleLogger();
/* 106 */   private final SampleLogger pingLogger = new SampleLogger();
/* 107 */   private final SampleLogger bandwidthLogger = new SampleLogger();
/*     */   private final FpsDebugChart fpsChart;
/*     */   private final TpsDebugChart tpsChart;
/*     */   private final PingDebugChart pingChart;
/*     */   private final BandwidthDebugChart bandwidthChart;
/*     */   
/*     */   public DebugScreenOverlay(Minecraft $$0) {
/* 114 */     this.minecraft = $$0;
/* 115 */     this.allocationRateCalculator = new AllocationRateCalculator();
/* 116 */     this.font = $$0.font;
/* 117 */     this.fpsChart = new FpsDebugChart(this.font, this.frameTimeLogger);
/* 118 */     this.tpsChart = new TpsDebugChart(this.font, this.tickTimeLogger, () -> Float.valueOf($$0.level.tickRateManager().millisecondsPerTick()));
/* 119 */     this.pingChart = new PingDebugChart(this.font, this.pingLogger);
/* 120 */     this.bandwidthChart = new BandwidthDebugChart(this.font, this.bandwidthLogger);
/*     */   }
/*     */   
/*     */   public void clearChunkCache() {
/* 124 */     this.serverChunk = null;
/* 125 */     this.clientChunk = null;
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0) {
/* 129 */     this.minecraft.getProfiler().push("debug");
/*     */     
/* 131 */     Entity $$1 = this.minecraft.getCameraEntity();
/*     */     
/* 133 */     this.block = $$1.pick(20.0D, 0.0F, false);
/* 134 */     this.liquid = $$1.pick(20.0D, 0.0F, true);
/*     */     
/* 136 */     $$0.drawManaged(() -> {
/*     */           drawGameInformation($$0);
/*     */           
/*     */           drawSystemInformation($$0);
/*     */           
/*     */           if (this.renderFpsCharts) {
/*     */             int $$1 = $$0.guiWidth();
/*     */             
/*     */             int $$2 = $$1 / 2;
/*     */             
/*     */             this.fpsChart.drawChart($$0, 0, this.fpsChart.getWidth($$2));
/*     */             
/*     */             if (this.minecraft.getSingleplayerServer() != null) {
/*     */               int $$3 = this.tpsChart.getWidth($$2);
/*     */               
/*     */               this.tpsChart.drawChart($$0, $$1 - $$3, $$3);
/*     */             } 
/*     */           } 
/*     */           
/*     */           if (this.renderNetworkCharts) {
/*     */             int $$4 = $$0.guiWidth();
/*     */             
/*     */             int $$5 = $$4 / 2;
/*     */             
/*     */             if (!this.minecraft.isLocalServer()) {
/*     */               this.bandwidthChart.drawChart($$0, 0, this.bandwidthChart.getWidth($$5));
/*     */             }
/*     */             
/*     */             int $$6 = this.pingChart.getWidth($$5);
/*     */             this.pingChart.drawChart($$0, $$4 - $$6, $$6);
/*     */           } 
/*     */         });
/* 168 */     this.minecraft.getProfiler().pop();
/*     */   }
/*     */   
/*     */   protected void drawGameInformation(GuiGraphics $$0) {
/* 172 */     List<String> $$1 = getGameInformation();
/*     */     
/* 174 */     $$1.add("");
/* 175 */     boolean $$2 = (this.minecraft.getSingleplayerServer() != null);
/* 176 */     $$1.add("Debug charts: [F3+1] Profiler " + (this.renderProfilerChart ? "visible" : "hidden") + "; [F3+2] " + ($$2 ? "FPS + TPS " : "FPS ") + (this.renderFpsCharts ? "visible" : "hidden") + "; [F3+3] " + (!this.minecraft.isLocalServer() ? "Bandwidth + Ping" : "Ping") + (this.renderNetworkCharts ? " visible" : " hidden"));
/* 177 */     $$1.add("For help: press F3 + Q");
/*     */     
/* 179 */     renderLines($$0, $$1, true);
/*     */   }
/*     */   
/*     */   protected void drawSystemInformation(GuiGraphics $$0) {
/* 183 */     List<String> $$1 = getSystemInformation();
/*     */     
/* 185 */     renderLines($$0, $$1, false);
/*     */   }
/*     */   
/*     */   private void renderLines(GuiGraphics $$0, List<String> $$1, boolean $$2) {
/* 189 */     Objects.requireNonNull(this.font); int $$3 = 9;
/* 190 */     for (int $$4 = 0; $$4 < $$1.size(); $$4++) {
/* 191 */       String $$5 = $$1.get($$4);
/* 192 */       if (!Strings.isNullOrEmpty($$5)) {
/* 193 */         int $$6 = this.font.width($$5);
/* 194 */         int $$7 = $$2 ? 2 : ($$0.guiWidth() - 2 - $$6);
/* 195 */         int $$8 = 2 + $$3 * $$4;
/* 196 */         $$0.fill($$7 - 1, $$8 - 1, $$7 + $$6 + 1, $$8 + $$3 - 1, -1873784752);
/*     */       } 
/*     */     } 
/* 199 */     for (int $$9 = 0; $$9 < $$1.size(); $$9++) {
/* 200 */       String $$10 = $$1.get($$9);
/* 201 */       if (!Strings.isNullOrEmpty($$10)) {
/* 202 */         int $$11 = this.font.width($$10);
/* 203 */         int $$12 = $$2 ? 2 : ($$0.guiWidth() - 2 - $$11);
/* 204 */         int $$13 = 2 + $$3 * $$9;
/* 205 */         $$0.drawString(this.font, $$10, $$12, $$13, 14737632, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<String> getGameInformation() {
/*     */     String $$13, str1, $$17, $$18, $$19, $$20, $$21;
/* 213 */     IntegratedServer $$0 = this.minecraft.getSingleplayerServer();
/* 214 */     ClientPacketListener $$1 = this.minecraft.getConnection();
/* 215 */     Connection $$2 = $$1.getConnection();
/* 216 */     float $$3 = $$2.getAverageSentPackets();
/* 217 */     float $$4 = $$2.getAverageReceivedPackets();
/*     */     
/* 219 */     TickRateManager $$5 = getLevel().tickRateManager();
/* 220 */     if ($$5.isSteppingForward()) {
/* 221 */       str1 = " (frozen - stepping)";
/* 222 */     } else if ($$5.isFrozen()) {
/* 223 */       str1 = " (frozen)";
/*     */     } else {
/* 225 */       str1 = "";
/*     */     } 
/*     */     
/* 228 */     if ($$0 != null) {
/* 229 */       ServerTickRateManager $$9 = $$0.tickRateManager();
/* 230 */       boolean $$10 = $$9.isSprinting();
/* 231 */       if ($$10) {
/* 232 */         str1 = " (sprinting)";
/*     */       }
/* 234 */       String $$11 = $$10 ? "-" : String.format(Locale.ROOT, "%.1f", new Object[] { Float.valueOf($$5.millisecondsPerTick()) });
/* 235 */       String $$12 = String.format(Locale.ROOT, "Integrated server @ %.1f/%s ms%s, %.0f tx, %.0f rx", new Object[] { Float.valueOf($$0.getCurrentSmoothedTickTime()), $$11, str1, Float.valueOf($$3), Float.valueOf($$4) });
/*     */     } else {
/* 237 */       $$13 = String.format(Locale.ROOT, "\"%s\" server%s, %.0f tx, %.0f rx", new Object[] { $$1.serverBrand(), str1, Float.valueOf($$3), Float.valueOf($$4) });
/*     */     } 
/*     */     
/* 240 */     BlockPos $$14 = this.minecraft.getCameraEntity().blockPosition();
/* 241 */     if (this.minecraft.showOnlyReducedInfo()) {
/* 242 */       return Lists.newArrayList((Object[])new String[] {
/* 243 */             "Minecraft " + SharedConstants.getCurrentVersion().getName() + " (" + this.minecraft.getLaunchedVersion() + "/" + ClientBrandRetriever.getClientModName() + ")", this.minecraft.fpsString, $$13, this.minecraft.levelRenderer
/*     */ 
/*     */             
/* 246 */             .getSectionStatistics(), this.minecraft.levelRenderer
/* 247 */             .getEntityStatistics(), "P: " + this.minecraft.particleEngine
/* 248 */             .countParticles() + ". T: " + this.minecraft.level.getEntityCount(), this.minecraft.level
/* 249 */             .gatherChunkSourceStats(), "", 
/*     */             
/* 251 */             String.format(Locale.ROOT, "Chunk-relative: %d %d %d", new Object[] { Integer.valueOf($$14.getX() & 0xF), Integer.valueOf($$14.getY() & 0xF), Integer.valueOf($$14.getZ() & 0xF) })
/*     */           });
/*     */     }
/*     */     
/* 255 */     Entity $$15 = this.minecraft.getCameraEntity();
/* 256 */     Direction $$16 = $$15.getDirection();
/*     */     
/* 258 */     switch ($$16) {
/*     */       case NORTH:
/* 260 */         $$17 = "Towards negative Z";
/*     */         break;
/*     */       case SOUTH:
/* 263 */         $$18 = "Towards positive Z";
/*     */         break;
/*     */       case WEST:
/* 266 */         $$19 = "Towards negative X";
/*     */         break;
/*     */       case EAST:
/* 269 */         $$20 = "Towards positive X";
/*     */         break;
/*     */       default:
/* 272 */         $$21 = "Invalid";
/*     */         break;
/*     */     } 
/*     */     
/* 276 */     ChunkPos $$22 = new ChunkPos($$14);
/* 277 */     if (!Objects.equals(this.lastPos, $$22)) {
/* 278 */       this.lastPos = $$22;
/* 279 */       clearChunkCache();
/*     */     } 
/*     */     
/* 282 */     Level $$23 = getLevel();
/* 283 */     LongSet $$24 = ($$23 instanceof ServerLevel) ? ((ServerLevel)$$23).getForcedChunks() : (LongSet)LongSets.EMPTY_SET;
/* 284 */     List<String> $$25 = Lists.newArrayList((Object[])new String[] {
/* 285 */           "Minecraft " + SharedConstants.getCurrentVersion().getName() + " (" + this.minecraft.getLaunchedVersion() + "/" + ClientBrandRetriever.getClientModName() + ("release".equalsIgnoreCase(this.minecraft.getVersionType()) ? "" : ("/" + this.minecraft.getVersionType())) + ")", this.minecraft.fpsString, $$13, this.minecraft.levelRenderer
/*     */ 
/*     */           
/* 288 */           .getSectionStatistics(), this.minecraft.levelRenderer
/* 289 */           .getEntityStatistics(), "P: " + this.minecraft.particleEngine
/* 290 */           .countParticles() + ". T: " + this.minecraft.level.getEntityCount(), this.minecraft.level
/* 291 */           .gatherChunkSourceStats()
/*     */         });
/*     */     
/* 294 */     String $$26 = getServerChunkStats();
/* 295 */     if ($$26 != null) {
/* 296 */       $$25.add($$26);
/*     */     }
/*     */     
/* 299 */     $$25.add("" + this.minecraft.level.dimension().location() + " FC: " + this.minecraft.level.dimension().location());
/* 300 */     $$25.add("");
/* 301 */     $$25.add(String.format(Locale.ROOT, "XYZ: %.3f / %.5f / %.3f", new Object[] { Double.valueOf(this.minecraft.getCameraEntity().getX()), Double.valueOf(this.minecraft.getCameraEntity().getY()), Double.valueOf(this.minecraft.getCameraEntity().getZ()) }));
/* 302 */     $$25.add(String.format(Locale.ROOT, "Block: %d %d %d [%d %d %d]", new Object[] { Integer.valueOf($$14.getX()), Integer.valueOf($$14.getY()), Integer.valueOf($$14.getZ()), Integer.valueOf($$14.getX() & 0xF), Integer.valueOf($$14.getY() & 0xF), Integer.valueOf($$14.getZ() & 0xF) }));
/* 303 */     $$25.add(String.format(Locale.ROOT, "Chunk: %d %d %d [%d %d in r.%d.%d.mca]", new Object[] { Integer.valueOf($$22.x), Integer.valueOf(SectionPos.blockToSectionCoord($$14.getY())), Integer.valueOf($$22.z), Integer.valueOf($$22.getRegionLocalX()), Integer.valueOf($$22.getRegionLocalZ()), Integer.valueOf($$22.getRegionX()), Integer.valueOf($$22.getRegionZ()) }));
/* 304 */     $$25.add(String.format(Locale.ROOT, "Facing: %s (%s) (%.1f / %.1f)", new Object[] { $$16, $$21, Float.valueOf(Mth.wrapDegrees($$15.getYRot())), Float.valueOf(Mth.wrapDegrees($$15.getXRot())) }));
/*     */     
/* 306 */     LevelChunk $$27 = getClientChunk();
/* 307 */     if ($$27.isEmpty()) {
/* 308 */       $$25.add("Waiting for chunk...");
/*     */     } else {
/* 310 */       int $$28 = this.minecraft.level.getChunkSource().getLightEngine().getRawBrightness($$14, 0);
/* 311 */       int $$29 = this.minecraft.level.getBrightness(LightLayer.SKY, $$14);
/* 312 */       int $$30 = this.minecraft.level.getBrightness(LightLayer.BLOCK, $$14);
/* 313 */       $$25.add("Client Light: " + $$28 + " (" + $$29 + " sky, " + $$30 + " block)");
/* 314 */       LevelChunk $$31 = getServerChunk();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 323 */       StringBuilder $$32 = new StringBuilder("CH");
/* 324 */       for (Heightmap.Types $$33 : Heightmap.Types.values()) {
/* 325 */         if ($$33.sendToClient()) {
/* 326 */           $$32.append(" ").append(HEIGHTMAP_NAMES.get($$33)).append(": ").append($$27.getHeight($$33, $$14.getX(), $$14.getZ()));
/*     */         }
/*     */       } 
/* 329 */       $$25.add($$32.toString());
/*     */       
/* 331 */       $$32.setLength(0);
/* 332 */       $$32.append("SH");
/* 333 */       for (Heightmap.Types $$34 : Heightmap.Types.values()) {
/* 334 */         if ($$34.keepAfterWorldgen()) {
/* 335 */           $$32.append(" ").append(HEIGHTMAP_NAMES.get($$34)).append(": ");
/* 336 */           if ($$31 != null) {
/* 337 */             $$32.append($$31.getHeight($$34, $$14.getX(), $$14.getZ()));
/*     */           } else {
/* 339 */             $$32.append("??");
/*     */           } 
/*     */         } 
/*     */       } 
/* 343 */       $$25.add($$32.toString());
/*     */       
/* 345 */       if ($$14.getY() >= this.minecraft.level.getMinBuildHeight() && $$14.getY() < this.minecraft.level.getMaxBuildHeight()) {
/* 346 */         $$25.add("Biome: " + printBiome(this.minecraft.level.getBiome($$14)));
/*     */ 
/*     */ 
/*     */         
/* 350 */         if ($$31 != null) {
/* 351 */           float $$35 = $$23.getMoonBrightness();
/* 352 */           long $$36 = $$31.getInhabitedTime();
/* 353 */           DifficultyInstance $$37 = new DifficultyInstance($$23.getDifficulty(), $$23.getDayTime(), $$36, $$35);
/* 354 */           $$25.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f (Day %d)", new Object[] { Float.valueOf($$37.getEffectiveDifficulty()), Float.valueOf($$37.getSpecialMultiplier()), Long.valueOf(this.minecraft.level.getDayTime() / 24000L) }));
/*     */         } else {
/* 356 */           $$25.add("Local Difficulty: ??");
/*     */         } 
/*     */       } 
/* 359 */       if ($$31 != null && $$31.isOldNoiseGeneration()) {
/* 360 */         $$25.add("Blending: Old");
/*     */       }
/*     */     } 
/*     */     
/* 364 */     ServerLevel $$38 = getServerLevel();
/* 365 */     if ($$38 != null) {
/* 366 */       ServerChunkCache $$39 = $$38.getChunkSource();
/*     */       
/* 368 */       ChunkGenerator $$40 = $$39.getGenerator();
/* 369 */       RandomState $$41 = $$39.randomState();
/* 370 */       $$40.addDebugScreenInfo($$25, $$41, $$14);
/* 371 */       Climate.Sampler $$42 = $$41.sampler();
/* 372 */       BiomeSource $$43 = $$40.getBiomeSource();
/* 373 */       $$43.addDebugInfo($$25, $$14, $$42);
/*     */       
/* 375 */       NaturalSpawner.SpawnState $$44 = $$39.getLastSpawnState();
/* 376 */       if ($$44 != null) {
/* 377 */         Object2IntMap<MobCategory> $$45 = $$44.getMobCategoryCounts();
/* 378 */         int $$46 = $$44.getSpawnableChunkCount();
/* 379 */         $$25.add("SC: " + $$46 + ", " + (String)Stream.<MobCategory>of(MobCategory.values()).map($$1 -> "" + Character.toUpperCase($$1.getName().charAt(0)) + ": " + Character.toUpperCase($$1.getName().charAt(0))).collect(Collectors.joining(", ")));
/*     */       } else {
/* 381 */         $$25.add("SC: N/A");
/*     */       } 
/*     */     } 
/*     */     
/* 385 */     PostChain $$47 = this.minecraft.gameRenderer.currentEffect();
/* 386 */     if ($$47 != null) {
/* 387 */       $$25.add("Shader: " + $$47.getName());
/*     */     }
/*     */     
/* 390 */     $$25.add(this.minecraft.getSoundManager().getDebugString() + this.minecraft.getSoundManager().getDebugString());
/*     */     
/* 392 */     return $$25;
/*     */   }
/*     */   
/*     */   private static String printBiome(Holder<Biome> $$0) {
/* 396 */     return (String)$$0.unwrap().map($$0 -> $$0.location().toString(), $$0 -> "[unregistered " + $$0 + "]");
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ServerLevel getServerLevel() {
/* 401 */     IntegratedServer $$0 = this.minecraft.getSingleplayerServer();
/* 402 */     if ($$0 != null) {
/* 403 */       return $$0.getLevel(this.minecraft.level.dimension());
/*     */     }
/*     */     
/* 406 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private String getServerChunkStats() {
/* 411 */     ServerLevel $$0 = getServerLevel();
/* 412 */     if ($$0 != null) {
/* 413 */       return $$0.gatherChunkSourceStats();
/*     */     }
/* 415 */     return null;
/*     */   }
/*     */   
/*     */   private Level getLevel() {
/* 419 */     return (Level)DataFixUtils.orElse(
/* 420 */         Optional.<IntegratedServer>ofNullable(this.minecraft.getSingleplayerServer()).flatMap($$0 -> Optional.ofNullable($$0.getLevel(this.minecraft.level.dimension()))), this.minecraft.level);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private LevelChunk getServerChunk() {
/* 427 */     if (this.serverChunk == null) {
/* 428 */       ServerLevel $$0 = getServerLevel();
/* 429 */       if ($$0 == null) {
/* 430 */         return null;
/*     */       }
/* 432 */       this.serverChunk = $$0.getChunkSource().getChunkFuture(this.lastPos.x, this.lastPos.z, ChunkStatus.FULL, false).thenApply($$0 -> (LevelChunk)$$0.map((), ()));
/*     */     } 
/* 434 */     return this.serverChunk.getNow(null);
/*     */   }
/*     */   
/*     */   private LevelChunk getClientChunk() {
/* 438 */     if (this.clientChunk == null) {
/* 439 */       this.clientChunk = this.minecraft.level.getChunk(this.lastPos.x, this.lastPos.z);
/*     */     }
/* 441 */     return this.clientChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<String> getSystemInformation() {
/* 446 */     long $$0 = Runtime.getRuntime().maxMemory();
/* 447 */     long $$1 = Runtime.getRuntime().totalMemory();
/* 448 */     long $$2 = Runtime.getRuntime().freeMemory();
/* 449 */     long $$3 = $$1 - $$2;
/*     */     
/* 451 */     List<String> $$4 = Lists.newArrayList((Object[])new String[] {
/* 452 */           String.format(Locale.ROOT, "Java: %s %dbit", new Object[] { System.getProperty("java.version"), Integer.valueOf(this.minecraft.is64Bit() ? 64 : 32)
/* 453 */             }), String.format(Locale.ROOT, "Mem: % 2d%% %03d/%03dMB", new Object[] { Long.valueOf($$3 * 100L / $$0), Long.valueOf(bytesToMegabytes($$3)), Long.valueOf(bytesToMegabytes($$0))
/* 454 */             }), String.format(Locale.ROOT, "Allocation rate: %03dMB /s", new Object[] { Long.valueOf(bytesToMegabytes(this.allocationRateCalculator.bytesAllocatedPerSecond($$3)))
/* 455 */             }), String.format(Locale.ROOT, "Allocated: % 2d%% %03dMB", new Object[] { Long.valueOf($$1 * 100L / $$0), Long.valueOf(bytesToMegabytes($$1)) }), "", 
/*     */           
/* 457 */           String.format(Locale.ROOT, "CPU: %s", new Object[] { GlUtil.getCpuInfo() }), "", 
/*     */           
/* 459 */           String.format(Locale.ROOT, "Display: %dx%d (%s)", new Object[] { Integer.valueOf(Minecraft.getInstance().getWindow().getWidth()), Integer.valueOf(Minecraft.getInstance().getWindow().getHeight()), GlUtil.getVendor()
/* 460 */             }), GlUtil.getRenderer(), 
/* 461 */           GlUtil.getOpenGLVersion()
/*     */         });
/*     */     
/* 464 */     if (this.minecraft.showOnlyReducedInfo()) {
/* 465 */       return $$4;
/*     */     }
/*     */     
/* 468 */     if (this.block.getType() == HitResult.Type.BLOCK) {
/* 469 */       BlockPos $$5 = ((BlockHitResult)this.block).getBlockPos();
/* 470 */       BlockState $$6 = this.minecraft.level.getBlockState($$5);
/*     */       
/* 472 */       $$4.add("");
/* 473 */       $$4.add("" + ChatFormatting.UNDERLINE + "Targeted Block: " + ChatFormatting.UNDERLINE + ", " + $$5.getX() + ", " + $$5.getY());
/* 474 */       $$4.add(String.valueOf(BuiltInRegistries.BLOCK.getKey($$6.getBlock())));
/*     */       
/* 476 */       for (UnmodifiableIterator<Map.Entry<Property<?>, Comparable<?>>> unmodifiableIterator = $$6.getValues().entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<Property<?>, Comparable<?>> $$7 = unmodifiableIterator.next();
/* 477 */         $$4.add(getPropertyValueString($$7)); }
/*     */ 
/*     */       
/* 480 */       Objects.requireNonNull($$4); $$6.getTags().map($$0 -> "#" + $$0.location()).forEach($$4::add);
/*     */     } 
/*     */     
/* 483 */     if (this.liquid.getType() == HitResult.Type.BLOCK) {
/* 484 */       BlockPos $$8 = ((BlockHitResult)this.liquid).getBlockPos();
/* 485 */       FluidState $$9 = this.minecraft.level.getFluidState($$8);
/* 486 */       $$4.add("");
/* 487 */       $$4.add("" + ChatFormatting.UNDERLINE + "Targeted Fluid: " + ChatFormatting.UNDERLINE + ", " + $$8.getX() + ", " + $$8.getY());
/* 488 */       $$4.add(String.valueOf(BuiltInRegistries.FLUID.getKey($$9.getType())));
/*     */       
/* 490 */       for (UnmodifiableIterator<Map.Entry<Property<?>, Comparable<?>>> unmodifiableIterator = $$9.getValues().entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<Property<?>, Comparable<?>> $$10 = unmodifiableIterator.next();
/* 491 */         $$4.add(getPropertyValueString($$10)); }
/*     */ 
/*     */       
/* 494 */       Objects.requireNonNull($$4); $$9.getTags().map($$0 -> "#" + $$0.location()).forEach($$4::add);
/*     */     } 
/*     */     
/* 497 */     Entity $$11 = this.minecraft.crosshairPickEntity;
/* 498 */     if ($$11 != null) {
/* 499 */       $$4.add("");
/* 500 */       $$4.add("" + ChatFormatting.UNDERLINE + "Targeted Entity");
/* 501 */       $$4.add(String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey($$11.getType())));
/*     */     } 
/*     */     
/* 504 */     return $$4;
/*     */   }
/*     */   
/*     */   private String getPropertyValueString(Map.Entry<Property<?>, Comparable<?>> $$0) {
/* 508 */     Property<?> $$1 = $$0.getKey();
/* 509 */     Comparable<?> $$2 = $$0.getValue();
/* 510 */     String $$3 = Util.getPropertyName($$1, $$2);
/*     */     
/* 512 */     if (Boolean.TRUE.equals($$2)) {
/* 513 */       $$3 = "" + ChatFormatting.GREEN + ChatFormatting.GREEN;
/* 514 */     } else if (Boolean.FALSE.equals($$2)) {
/* 515 */       $$3 = "" + ChatFormatting.RED + ChatFormatting.RED;
/*     */     } 
/*     */     
/* 518 */     return $$1.getName() + ": " + $$1.getName();
/*     */   }
/*     */   
/*     */   private static long bytesToMegabytes(long $$0) {
/* 522 */     return $$0 / 1024L / 1024L;
/*     */   }
/*     */   
/*     */   public boolean showDebugScreen() {
/* 526 */     return (this.renderDebug && !this.minecraft.options.hideGui);
/*     */   }
/*     */   
/*     */   public boolean showProfilerChart() {
/* 530 */     return (showDebugScreen() && this.renderProfilerChart);
/*     */   }
/*     */   
/*     */   public boolean showNetworkCharts() {
/* 534 */     return (showDebugScreen() && this.renderNetworkCharts);
/*     */   }
/*     */   
/*     */   public void toggleOverlay() {
/* 538 */     this.renderDebug = !this.renderDebug;
/*     */   }
/*     */   
/*     */   public void toggleNetworkCharts() {
/* 542 */     this.renderNetworkCharts = (!this.renderDebug || !this.renderNetworkCharts);
/* 543 */     if (this.renderNetworkCharts) {
/* 544 */       this.renderDebug = true;
/* 545 */       this.renderFpsCharts = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void toggleFpsCharts() {
/* 550 */     this.renderFpsCharts = (!this.renderDebug || !this.renderFpsCharts);
/* 551 */     if (this.renderFpsCharts) {
/* 552 */       this.renderDebug = true;
/* 553 */       this.renderNetworkCharts = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void toggleProfilerChart() {
/* 558 */     this.renderProfilerChart = (!this.renderDebug || !this.renderProfilerChart);
/* 559 */     if (this.renderProfilerChart) {
/* 560 */       this.renderDebug = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void logFrameDuration(long $$0) {
/* 565 */     this.frameTimeLogger.logSample($$0);
/*     */   }
/*     */   
/*     */   public void logTickDuration(long $$0) {
/* 569 */     this.tickTimeLogger.logSample($$0);
/*     */   }
/*     */   
/*     */   public SampleLogger getPingLogger() {
/* 573 */     return this.pingLogger;
/*     */   }
/*     */   
/*     */   public SampleLogger getBandwidthLogger() {
/* 577 */     return this.bandwidthLogger;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 581 */     this.renderDebug = false;
/* 582 */     this.tickTimeLogger.reset();
/* 583 */     this.pingLogger.reset();
/* 584 */     this.bandwidthLogger.reset();
/*     */   }
/*     */   
/*     */   private static class AllocationRateCalculator {
/*     */     private static final int UPDATE_INTERVAL_MS = 500;
/* 589 */     private static final List<GarbageCollectorMXBean> GC_MBEANS = ManagementFactory.getGarbageCollectorMXBeans();
/*     */     
/* 591 */     private long lastTime = 0L;
/* 592 */     private long lastHeapUsage = -1L;
/* 593 */     private long lastGcCounts = -1L;
/* 594 */     private long lastRate = 0L;
/*     */     
/*     */     long bytesAllocatedPerSecond(long $$0) {
/* 597 */       long $$1 = System.currentTimeMillis();
/*     */       
/* 599 */       if ($$1 - this.lastTime < 500L) {
/* 600 */         return this.lastRate;
/*     */       }
/*     */       
/* 603 */       long $$2 = gcCounts();
/*     */ 
/*     */ 
/*     */       
/* 607 */       if (this.lastTime != 0L && $$2 == this.lastGcCounts) {
/* 608 */         double $$3 = TimeUnit.SECONDS.toMillis(1L) / ($$1 - this.lastTime);
/* 609 */         long $$4 = $$0 - this.lastHeapUsage;
/* 610 */         this.lastRate = Math.round($$4 * $$3);
/*     */       } 
/*     */       
/* 613 */       this.lastTime = $$1;
/* 614 */       this.lastHeapUsage = $$0;
/* 615 */       this.lastGcCounts = $$2;
/* 616 */       return this.lastRate;
/*     */     }
/*     */     
/*     */     private static long gcCounts() {
/* 620 */       long $$0 = 0L;
/* 621 */       for (GarbageCollectorMXBean $$1 : GC_MBEANS) {
/* 622 */         $$0 += $$1.getCollectionCount();
/*     */       }
/* 624 */       return $$0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\DebugScreenOverlay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */