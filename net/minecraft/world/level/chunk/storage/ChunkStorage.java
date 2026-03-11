/*     */ package net.minecraft.world.level.chunk.storage;
/*     */ 
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.LegacyStructureDataHandler;
/*     */ import net.minecraft.world.level.storage.DimensionDataStorage;
/*     */ 
/*     */ 
/*     */ public class ChunkStorage
/*     */   implements AutoCloseable
/*     */ {
/*     */   public static final int LAST_MONOLYTH_STRUCTURE_DATA_VERSION = 1493;
/*     */   private final IOWorker worker;
/*     */   protected final DataFixer fixerUpper;
/*     */   @Nullable
/*     */   private volatile LegacyStructureDataHandler legacyStructureHandler;
/*     */   
/*     */   public ChunkStorage(Path $$0, DataFixer $$1, boolean $$2) {
/*  34 */     this.fixerUpper = $$1;
/*  35 */     this.worker = new IOWorker($$0, $$2, "chunk");
/*     */   }
/*     */   
/*     */   public boolean isOldChunkAround(ChunkPos $$0, int $$1) {
/*  39 */     return this.worker.isOldChunkAround($$0, $$1);
/*     */   }
/*     */   
/*     */   public CompoundTag upgradeChunkTag(ResourceKey<Level> $$0, Supplier<DimensionDataStorage> $$1, CompoundTag $$2, Optional<ResourceKey<Codec<? extends ChunkGenerator>>> $$3) {
/*  43 */     int $$4 = getVersion($$2);
/*     */     
/*  45 */     if ($$4 < 1493) {
/*  46 */       $$2 = DataFixTypes.CHUNK.update(this.fixerUpper, $$2, $$4, 1493);
/*     */       
/*  48 */       if ($$2.getCompound("Level").getBoolean("hasLegacyStructureData")) {
/*  49 */         LegacyStructureDataHandler $$5 = getLegacyStructureHandler($$0, $$1);
/*  50 */         $$2 = $$5.updateFromLegacy($$2);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  55 */     injectDatafixingContext($$2, $$0, $$3);
/*     */     
/*  57 */     $$2 = DataFixTypes.CHUNK.updateToCurrentVersion(this.fixerUpper, $$2, Math.max(1493, $$4));
/*     */ 
/*     */     
/*  60 */     if ($$4 < SharedConstants.getCurrentVersion().getDataVersion().getVersion()) {
/*  61 */       NbtUtils.addCurrentDataVersion($$2);
/*     */     }
/*     */     
/*  64 */     $$2.remove("__context");
/*     */     
/*  66 */     return $$2;
/*     */   }
/*     */   
/*     */   private LegacyStructureDataHandler getLegacyStructureHandler(ResourceKey<Level> $$0, Supplier<DimensionDataStorage> $$1) {
/*  70 */     LegacyStructureDataHandler $$2 = this.legacyStructureHandler;
/*  71 */     if ($$2 == null) {
/*  72 */       synchronized (this) {
/*  73 */         $$2 = this.legacyStructureHandler;
/*  74 */         if ($$2 == null) {
/*  75 */           this.legacyStructureHandler = $$2 = LegacyStructureDataHandler.getLegacyStructureHandler($$0, $$1.get());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  80 */     return $$2;
/*     */   }
/*     */   
/*     */   public static void injectDatafixingContext(CompoundTag $$0, ResourceKey<Level> $$1, Optional<ResourceKey<Codec<? extends ChunkGenerator>>> $$2) {
/*  84 */     CompoundTag $$3 = new CompoundTag();
/*  85 */     $$3.putString("dimension", $$1.location().toString());
/*  86 */     $$2.ifPresent($$1 -> $$0.putString("generator", $$1.location().toString()));
/*  87 */     $$0.put("__context", (Tag)$$3);
/*     */   }
/*     */   
/*     */   public static int getVersion(CompoundTag $$0) {
/*  91 */     return NbtUtils.getDataVersion($$0, -1);
/*     */   }
/*     */   
/*     */   public CompletableFuture<Optional<CompoundTag>> read(ChunkPos $$0) {
/*  95 */     return this.worker.loadAsync($$0);
/*     */   }
/*     */   
/*     */   public void write(ChunkPos $$0, CompoundTag $$1) {
/*  99 */     this.worker.store($$0, $$1);
/*     */     
/* 101 */     if (this.legacyStructureHandler != null) {
/* 102 */       this.legacyStructureHandler.removeIndex($$0.toLong());
/*     */     }
/*     */   }
/*     */   
/*     */   public void flushWorker() {
/* 107 */     this.worker.synchronize(true).join();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 112 */     this.worker.close();
/*     */   }
/*     */   
/*     */   public ChunkScanAccess chunkScanner() {
/* 116 */     return this.worker;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\ChunkStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */