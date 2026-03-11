/*     */ package net.minecraft.util.worldupdate;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.chunk.storage.ChunkStorage;
/*     */ import net.minecraft.world.level.chunk.storage.RegionFile;
/*     */ import net.minecraft.world.level.dimension.LevelStem;
/*     */ import net.minecraft.world.level.storage.DimensionDataStorage;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldUpgrader
/*     */ {
/*  47 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  48 */   private static final ThreadFactory THREAD_FACTORY = (new ThreadFactoryBuilder()).setDaemon(true).build();
/*     */   
/*     */   private final Registry<LevelStem> dimensions;
/*     */   
/*     */   private final Set<ResourceKey<Level>> levels;
/*     */   private final boolean eraseCache;
/*     */   private final LevelStorageSource.LevelStorageAccess levelStorage;
/*     */   private final Thread thread;
/*     */   private final DataFixer dataFixer;
/*     */   private volatile boolean running = true;
/*     */   private volatile boolean finished;
/*     */   private volatile float progress;
/*     */   private volatile int totalChunks;
/*     */   private volatile int converted;
/*     */   private volatile int skipped;
/*  63 */   private final Reference2FloatMap<ResourceKey<Level>> progressMap = Reference2FloatMaps.synchronize((Reference2FloatMap)new Reference2FloatOpenHashMap());
/*     */   
/*  65 */   private volatile Component status = (Component)Component.translatable("optimizeWorld.stage.counting");
/*     */   
/*  67 */   private static final Pattern REGEX = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
/*     */   private final DimensionDataStorage overworldDataStorage;
/*     */   
/*     */   public WorldUpgrader(LevelStorageSource.LevelStorageAccess $$0, DataFixer $$1, Registry<LevelStem> $$2, boolean $$3) {
/*  71 */     this.dimensions = $$2;
/*  72 */     this.levels = (Set<ResourceKey<Level>>)$$2.registryKeySet().stream().map(Registries::levelStemToLevel).collect(Collectors.toUnmodifiableSet());
/*  73 */     this.eraseCache = $$3;
/*  74 */     this.dataFixer = $$1;
/*  75 */     this.levelStorage = $$0;
/*  76 */     this.overworldDataStorage = new DimensionDataStorage(this.levelStorage.getDimensionPath(Level.OVERWORLD).resolve("data").toFile(), $$1);
/*     */     
/*  78 */     this.thread = THREAD_FACTORY.newThread(this::work);
/*  79 */     this.thread.setUncaughtExceptionHandler(($$0, $$1) -> {
/*     */           LOGGER.error("Error upgrading world", $$1);
/*     */           this.status = (Component)Component.translatable("optimizeWorld.stage.failed");
/*     */           this.finished = true;
/*     */         });
/*  84 */     this.thread.start();
/*     */   }
/*     */   
/*     */   public void cancel() {
/*  88 */     this.running = false;
/*     */     try {
/*  90 */       this.thread.join();
/*  91 */     } catch (InterruptedException interruptedException) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private void work() {
/*  96 */     this.totalChunks = 0;
/*  97 */     ImmutableMap.Builder<ResourceKey<Level>, ListIterator<ChunkPos>> $$0 = ImmutableMap.builder();
/*  98 */     for (ResourceKey<Level> $$1 : this.levels) {
/*  99 */       List<ChunkPos> $$2 = getAllChunkPos($$1);
/* 100 */       $$0.put($$1, $$2.listIterator());
/* 101 */       this.totalChunks += $$2.size();
/*     */     } 
/* 103 */     if (this.totalChunks == 0) {
/* 104 */       this.finished = true;
/*     */       return;
/*     */     } 
/* 107 */     float $$3 = this.totalChunks;
/* 108 */     ImmutableMap<ResourceKey<Level>, ListIterator<ChunkPos>> $$4 = $$0.build();
/*     */     
/* 110 */     ImmutableMap.Builder<ResourceKey<Level>, ChunkStorage> $$5 = ImmutableMap.builder();
/* 111 */     for (ResourceKey<Level> $$6 : this.levels) {
/* 112 */       Path $$7 = this.levelStorage.getDimensionPath($$6);
/* 113 */       $$5.put($$6, new ChunkStorage($$7.resolve("region"), this.dataFixer, true));
/*     */     } 
/* 115 */     ImmutableMap<ResourceKey<Level>, ChunkStorage> $$8 = $$5.build();
/* 116 */     long $$9 = Util.getMillis();
/*     */     
/* 118 */     this.status = (Component)Component.translatable("optimizeWorld.stage.upgrading");
/*     */     
/* 120 */     while (this.running) {
/* 121 */       boolean $$10 = false;
/*     */       
/* 123 */       float $$11 = 0.0F;
/* 124 */       for (ResourceKey<Level> $$12 : this.levels) {
/* 125 */         ListIterator<ChunkPos> $$13 = (ListIterator<ChunkPos>)$$4.get($$12);
/* 126 */         ChunkStorage $$14 = (ChunkStorage)$$8.get($$12);
/* 127 */         if ($$13.hasNext()) {
/* 128 */           ChunkPos $$15 = $$13.next();
/* 129 */           boolean $$16 = false;
/*     */           try {
/* 131 */             CompoundTag $$17 = ((Optional<CompoundTag>)$$14.read($$15).join()).orElse(null);
/* 132 */             if ($$17 != null) {
/* 133 */               int $$18 = ChunkStorage.getVersion($$17);
/*     */               
/* 135 */               ChunkGenerator $$19 = ((LevelStem)this.dimensions.getOrThrow(Registries.levelToLevelStem($$12))).generator();
/* 136 */               CompoundTag $$20 = $$14.upgradeChunkTag($$12, () -> this.overworldDataStorage, $$17, $$19.getTypeNameForDataFixer());
/*     */               
/* 138 */               ChunkPos $$21 = new ChunkPos($$20.getInt("xPos"), $$20.getInt("zPos"));
/* 139 */               if (!$$21.equals($$15)) {
/* 140 */                 LOGGER.warn("Chunk {} has invalid position {}", $$15, $$21);
/*     */               }
/*     */               
/* 143 */               boolean $$22 = ($$18 < SharedConstants.getCurrentVersion().getDataVersion().getVersion());
/* 144 */               if (this.eraseCache) {
/* 145 */                 $$22 = ($$22 || $$20.contains("Heightmaps"));
/* 146 */                 $$20.remove("Heightmaps");
/* 147 */                 $$22 = ($$22 || $$20.contains("isLightOn"));
/* 148 */                 $$20.remove("isLightOn");
/*     */                 
/* 150 */                 ListTag $$23 = $$20.getList("sections", 10);
/* 151 */                 for (int $$24 = 0; $$24 < $$23.size(); $$24++) {
/* 152 */                   CompoundTag $$25 = $$23.getCompound($$24);
/* 153 */                   $$22 = ($$22 || $$25.contains("BlockLight"));
/* 154 */                   $$25.remove("BlockLight");
/* 155 */                   $$22 = ($$22 || $$25.contains("SkyLight"));
/* 156 */                   $$25.remove("SkyLight");
/*     */                 } 
/*     */               } 
/*     */               
/* 160 */               if ($$22) {
/* 161 */                 $$14.write($$15, $$20);
/* 162 */                 $$16 = true;
/*     */               } 
/*     */             } 
/* 165 */           } catch (ReportedException|java.util.concurrent.CompletionException $$26) {
/* 166 */             Throwable $$27 = $$26.getCause();
/* 167 */             if ($$27 instanceof IOException) {
/* 168 */               LOGGER.error("Error upgrading chunk {}", $$15, $$27);
/*     */             } else {
/* 170 */               throw $$26;
/*     */             } 
/*     */           } 
/* 173 */           if ($$16) {
/* 174 */             this.converted++;
/*     */           } else {
/* 176 */             this.skipped++;
/*     */           } 
/* 178 */           $$10 = true;
/*     */         } 
/* 180 */         float $$28 = $$13.nextIndex() / $$3;
/* 181 */         this.progressMap.put($$12, $$28);
/* 182 */         $$11 += $$28;
/*     */       } 
/*     */       
/* 185 */       this.progress = $$11;
/*     */       
/* 187 */       if (!$$10) {
/* 188 */         this.running = false;
/*     */       }
/*     */     } 
/*     */     
/* 192 */     this.status = (Component)Component.translatable("optimizeWorld.stage.finished");
/*     */     
/* 194 */     for (UnmodifiableIterator<ChunkStorage> unmodifiableIterator = $$8.values().iterator(); unmodifiableIterator.hasNext(); ) { ChunkStorage $$29 = unmodifiableIterator.next();
/*     */       try {
/* 196 */         $$29.close();
/* 197 */       } catch (IOException $$30) {
/* 198 */         LOGGER.error("Error upgrading chunk", $$30);
/*     */       }  }
/*     */     
/* 201 */     this.overworldDataStorage.save();
/* 202 */     $$9 = Util.getMillis() - $$9;
/* 203 */     LOGGER.info("World optimizaton finished after {} ms", Long.valueOf($$9));
/* 204 */     this.finished = true;
/*     */   }
/*     */   
/*     */   private List<ChunkPos> getAllChunkPos(ResourceKey<Level> $$0) {
/* 208 */     File $$1 = this.levelStorage.getDimensionPath($$0).toFile();
/*     */     
/* 210 */     File $$2 = new File($$1, "region");
/* 211 */     File[] $$3 = $$2.listFiles(($$0, $$1) -> $$1.endsWith(".mca"));
/*     */     
/* 213 */     if ($$3 == null) {
/* 214 */       return (List<ChunkPos>)ImmutableList.of();
/*     */     }
/*     */     
/* 217 */     List<ChunkPos> $$4 = Lists.newArrayList();
/* 218 */     for (File $$5 : $$3) {
/* 219 */       Matcher $$6 = REGEX.matcher($$5.getName());
/* 220 */       if ($$6.matches()) {
/*     */ 
/*     */ 
/*     */         
/* 224 */         int $$7 = Integer.parseInt($$6.group(1)) << 5;
/* 225 */         int $$8 = Integer.parseInt($$6.group(2)) << 5;
/*     */         
/* 227 */         try { RegionFile $$9 = new RegionFile($$5.toPath(), $$2.toPath(), true); 
/* 228 */           try { for (int $$10 = 0; $$10 < 32; $$10++) {
/* 229 */               for (int $$11 = 0; $$11 < 32; $$11++) {
/* 230 */                 ChunkPos $$12 = new ChunkPos($$10 + $$7, $$11 + $$8);
/* 231 */                 if ($$9.doesChunkExist($$12)) {
/* 232 */                   $$4.add($$12);
/*     */                 }
/*     */               } 
/*     */             } 
/* 236 */             $$9.close(); } catch (Throwable throwable) { try { $$9.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 241 */     return $$4;
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/* 245 */     return this.finished;
/*     */   }
/*     */   
/*     */   public Set<ResourceKey<Level>> levels() {
/* 249 */     return this.levels;
/*     */   }
/*     */   
/*     */   public float dimensionProgress(ResourceKey<Level> $$0) {
/* 253 */     return this.progressMap.getFloat($$0);
/*     */   }
/*     */   
/*     */   public float getProgress() {
/* 257 */     return this.progress;
/*     */   }
/*     */   
/*     */   public int getTotalChunks() {
/* 261 */     return this.totalChunks;
/*     */   }
/*     */   
/*     */   public int getConverted() {
/* 265 */     return this.converted;
/*     */   }
/*     */   
/*     */   public int getSkipped() {
/* 269 */     return this.skipped;
/*     */   }
/*     */   
/*     */   public Component getStatus() {
/* 273 */     return this.status;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\worldupdate\WorldUpgrader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */