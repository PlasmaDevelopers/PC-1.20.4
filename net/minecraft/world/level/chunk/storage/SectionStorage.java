/*     */ package net.minecraft.world.level.chunk.storage;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.OptionalDynamic;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionException;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SectionStorage<R> implements AutoCloseable {
/*  39 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */   
/*     */   private static final String SECTIONS_TAG = "Sections";
/*     */   
/*     */   private final IOWorker worker;
/*     */   
/*  46 */   private final Long2ObjectMap<Optional<R>> storage = (Long2ObjectMap<Optional<R>>)new Long2ObjectOpenHashMap();
/*  47 */   private final LongLinkedOpenHashSet dirty = new LongLinkedOpenHashSet();
/*     */   
/*     */   private final Function<Runnable, Codec<R>> codec;
/*     */   private final Function<Runnable, R> factory;
/*     */   private final DataFixer fixerUpper;
/*     */   private final DataFixTypes type;
/*     */   private final RegistryAccess registryAccess;
/*     */   protected final LevelHeightAccessor levelHeightAccessor;
/*     */   
/*     */   public SectionStorage(Path $$0, Function<Runnable, Codec<R>> $$1, Function<Runnable, R> $$2, DataFixer $$3, DataFixTypes $$4, boolean $$5, RegistryAccess $$6, LevelHeightAccessor $$7) {
/*  57 */     this.codec = $$1;
/*  58 */     this.factory = $$2;
/*  59 */     this.fixerUpper = $$3;
/*  60 */     this.type = $$4;
/*  61 */     this.registryAccess = $$6;
/*  62 */     this.levelHeightAccessor = $$7;
/*  63 */     this.worker = new IOWorker($$0, $$5, $$0.getFileName().toString());
/*     */   }
/*     */   
/*     */   protected void tick(BooleanSupplier $$0) {
/*  67 */     while (hasWork() && $$0.getAsBoolean()) {
/*  68 */       ChunkPos $$1 = SectionPos.of(this.dirty.firstLong()).chunk();
/*  69 */       writeColumn($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasWork() {
/*  74 */     return !this.dirty.isEmpty();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected Optional<R> get(long $$0) {
/*  79 */     return (Optional<R>)this.storage.get($$0);
/*     */   }
/*     */   
/*     */   protected Optional<R> getOrLoad(long $$0) {
/*  83 */     if (outsideStoredRange($$0)) {
/*  84 */       return Optional.empty();
/*     */     }
/*  86 */     Optional<R> $$1 = get($$0);
/*  87 */     if ($$1 != null) {
/*  88 */       return $$1;
/*     */     }
/*  90 */     readColumn(SectionPos.of($$0).chunk());
/*     */     
/*  92 */     $$1 = get($$0);
/*  93 */     if ($$1 == null) {
/*  94 */       throw (IllegalStateException)Util.pauseInIde(new IllegalStateException());
/*     */     }
/*  96 */     return $$1;
/*     */   }
/*     */   
/*     */   protected boolean outsideStoredRange(long $$0) {
/* 100 */     int $$1 = SectionPos.sectionToBlockCoord(SectionPos.y($$0));
/* 101 */     return this.levelHeightAccessor.isOutsideBuildHeight($$1);
/*     */   }
/*     */   
/*     */   protected R getOrCreate(long $$0) {
/* 105 */     if (outsideStoredRange($$0)) {
/* 106 */       throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("sectionPos out of bounds"));
/*     */     }
/* 108 */     Optional<R> $$1 = getOrLoad($$0);
/* 109 */     if ($$1.isPresent()) {
/* 110 */       return $$1.get();
/*     */     }
/* 112 */     R $$2 = this.factory.apply(() -> setDirty($$0));
/* 113 */     this.storage.put($$0, Optional.of($$2));
/* 114 */     return $$2;
/*     */   }
/*     */   
/*     */   private void readColumn(ChunkPos $$0) {
/* 118 */     Optional<CompoundTag> $$1 = tryRead($$0).join();
/* 119 */     RegistryOps<Tag> $$2 = RegistryOps.create((DynamicOps)NbtOps.INSTANCE, (HolderLookup.Provider)this.registryAccess);
/* 120 */     readColumn($$0, (DynamicOps<Tag>)$$2, (Tag)$$1.orElse(null));
/*     */   }
/*     */   
/*     */   private CompletableFuture<Optional<CompoundTag>> tryRead(ChunkPos $$0) {
/* 124 */     return this.worker.loadAsync($$0)
/* 125 */       .exceptionally($$1 -> {
/*     */           if ($$1 instanceof IOException) {
/*     */             IOException $$2 = (IOException)$$1;
/*     */             LOGGER.error("Error reading chunk {} data from disk", $$0, $$2);
/*     */             return Optional.empty();
/*     */           } 
/*     */           throw new CompletionException($$1);
/*     */         });
/*     */   }
/*     */   
/*     */   private <T> void readColumn(ChunkPos $$0, DynamicOps<T> $$1, @Nullable T $$2) {
/* 136 */     if ($$2 == null) {
/* 137 */       for (int $$3 = this.levelHeightAccessor.getMinSection(); $$3 < this.levelHeightAccessor.getMaxSection(); $$3++) {
/* 138 */         this.storage.put(getKey($$0, $$3), Optional.empty());
/*     */       }
/*     */     } else {
/* 141 */       Dynamic<T> $$4 = new Dynamic($$1, $$2);
/* 142 */       int $$5 = getVersion($$4);
/* 143 */       int $$6 = SharedConstants.getCurrentVersion().getDataVersion().getVersion();
/* 144 */       boolean $$7 = ($$5 != $$6);
/* 145 */       Dynamic<T> $$8 = this.type.update(this.fixerUpper, $$4, $$5, $$6);
/* 146 */       OptionalDynamic<T> $$9 = $$8.get("Sections");
/* 147 */       for (int $$10 = this.levelHeightAccessor.getMinSection(); $$10 < this.levelHeightAccessor.getMaxSection(); $$10++) {
/* 148 */         long $$11 = getKey($$0, $$10);
/* 149 */         Optional<R> $$12 = $$9.get(Integer.toString($$10)).result().flatMap($$1 -> {
/*     */               Objects.requireNonNull(LOGGER); return ((Codec)this.codec.apply(())).parse($$1).resultOrPartial(LOGGER::error);
/*     */             });
/* 152 */         this.storage.put($$11, $$12);
/* 153 */         $$12.ifPresent($$2 -> {
/*     */               onSectionLoad($$0);
/*     */               if ($$1) {
/*     */                 setDirty($$0);
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeColumn(ChunkPos $$0) {
/* 164 */     RegistryOps<Tag> $$1 = RegistryOps.create((DynamicOps)NbtOps.INSTANCE, (HolderLookup.Provider)this.registryAccess);
/* 165 */     Dynamic<Tag> $$2 = writeColumn($$0, (DynamicOps<Tag>)$$1);
/* 166 */     Tag $$3 = (Tag)$$2.getValue();
/* 167 */     if ($$3 instanceof CompoundTag) {
/* 168 */       this.worker.store($$0, (CompoundTag)$$3);
/*     */     } else {
/* 170 */       LOGGER.error("Expected compound tag, got {}", $$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T> Dynamic<T> writeColumn(ChunkPos $$0, DynamicOps<T> $$1) {
/* 175 */     Map<T, T> $$2 = Maps.newHashMap();
/* 176 */     for (int $$3 = this.levelHeightAccessor.getMinSection(); $$3 < this.levelHeightAccessor.getMaxSection(); $$3++) {
/* 177 */       long $$4 = getKey($$0, $$3);
/* 178 */       this.dirty.remove($$4);
/* 179 */       Optional<R> $$5 = (Optional<R>)this.storage.get($$4);
/* 180 */       if ($$5 != null && !$$5.isEmpty()) {
/*     */ 
/*     */         
/* 183 */         DataResult<T> $$6 = ((Codec)this.codec.apply(() -> setDirty($$0))).encodeStart($$1, $$5.get());
/* 184 */         String $$7 = Integer.toString($$3);
/* 185 */         Objects.requireNonNull(LOGGER); $$6.resultOrPartial(LOGGER::error).ifPresent($$3 -> $$0.put($$1.createString($$2), $$3));
/*     */       } 
/*     */     } 
/* 188 */     return new Dynamic($$1, $$1.createMap((Map)ImmutableMap.of($$1
/* 189 */             .createString("Sections"), $$1.createMap($$2), $$1
/* 190 */             .createString("DataVersion"), $$1.createInt(SharedConstants.getCurrentVersion().getDataVersion().getVersion()))));
/*     */   }
/*     */   
/*     */   private static long getKey(ChunkPos $$0, int $$1) {
/* 194 */     return SectionPos.asLong($$0.x, $$1, $$0.z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onSectionLoad(long $$0) {}
/*     */   
/*     */   protected void setDirty(long $$0) {
/* 201 */     Optional<R> $$1 = (Optional<R>)this.storage.get($$0);
/* 202 */     if ($$1 == null || $$1.isEmpty()) {
/* 203 */       LOGGER.warn("No data for position: {}", SectionPos.of($$0));
/*     */       return;
/*     */     } 
/* 206 */     this.dirty.add($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getVersion(Dynamic<?> $$0) {
/* 211 */     return $$0.get("DataVersion").asInt(1945);
/*     */   }
/*     */   
/*     */   public void flush(ChunkPos $$0) {
/* 215 */     if (hasWork()) {
/* 216 */       for (int $$1 = this.levelHeightAccessor.getMinSection(); $$1 < this.levelHeightAccessor.getMaxSection(); $$1++) {
/* 217 */         long $$2 = getKey($$0, $$1);
/* 218 */         if (this.dirty.contains($$2)) {
/* 219 */           writeColumn($$0);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 228 */     this.worker.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\SectionStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */