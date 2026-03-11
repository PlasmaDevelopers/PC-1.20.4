/*     */ package net.minecraft.world.level.chunk.storage;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.IntArrayTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.util.thread.ProcessorMailbox;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.entity.ChunkEntities;
/*     */ import net.minecraft.world.level.entity.EntityPersistentStorage;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class EntityStorage implements EntityPersistentStorage<Entity> {
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final String ENTITIES_TAG = "Entities";
/*     */   private static final String POSITION_TAG = "Position";
/*     */   private final ServerLevel level;
/*     */   private final IOWorker worker;
/*  37 */   private final LongSet emptyChunks = (LongSet)new LongOpenHashSet();
/*     */   
/*     */   private final ProcessorMailbox<Runnable> entityDeserializerQueue;
/*     */   protected final DataFixer fixerUpper;
/*     */   
/*     */   public EntityStorage(ServerLevel $$0, Path $$1, DataFixer $$2, boolean $$3, Executor $$4) {
/*  43 */     this.level = $$0;
/*  44 */     this.fixerUpper = $$2;
/*  45 */     this.entityDeserializerQueue = ProcessorMailbox.create($$4, "entity-deserializer");
/*  46 */     this.worker = new IOWorker($$1, $$3, "entities");
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<ChunkEntities<Entity>> loadEntities(ChunkPos $$0) {
/*  51 */     if (this.emptyChunks.contains($$0.toLong())) {
/*  52 */       return CompletableFuture.completedFuture(emptyChunk($$0));
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     Objects.requireNonNull(this.entityDeserializerQueue); return this.worker.loadAsync($$0).thenApplyAsync($$1 -> { if ($$1.isEmpty()) { this.emptyChunks.add($$0.toLong()); return emptyChunk($$0); }  try { ChunkPos $$2 = readChunkPos($$1.get()); if (!Objects.equals($$0, $$2)) LOGGER.error("Chunk file at {} is in the wrong location. (Expected {}, got {})", new Object[] { $$0, $$0, $$2 });  } catch (Exception $$3) { LOGGER.warn("Failed to parse chunk {} position info", $$0, $$3); }  CompoundTag $$4 = upgradeChunkTag($$1.get()); ListTag $$5 = $$4.getList("Entities", 10); List<Entity> $$6 = (List<Entity>)EntityType.loadEntitiesRecursive((List)$$5, (Level)this.level).collect(ImmutableList.toImmutableList()); return new ChunkEntities($$0, $$6); }this.entityDeserializerQueue::tell);
/*     */   }
/*     */   
/*     */   private static ChunkPos readChunkPos(CompoundTag $$0) {
/*  83 */     int[] $$1 = $$0.getIntArray("Position");
/*  84 */     return new ChunkPos($$1[0], $$1[1]);
/*     */   }
/*     */   
/*     */   private static void writeChunkPos(CompoundTag $$0, ChunkPos $$1) {
/*  88 */     $$0.put("Position", (Tag)new IntArrayTag(new int[] { $$1.x, $$1.z }));
/*     */   }
/*     */   
/*     */   private static ChunkEntities<Entity> emptyChunk(ChunkPos $$0) {
/*  92 */     return new ChunkEntities($$0, (List)ImmutableList.of());
/*     */   }
/*     */ 
/*     */   
/*     */   public void storeEntities(ChunkEntities<Entity> $$0) {
/*  97 */     ChunkPos $$1 = $$0.getPos();
/*  98 */     if ($$0.isEmpty()) {
/*  99 */       if (this.emptyChunks.add($$1.toLong())) {
/* 100 */         this.worker.store($$1, null);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 105 */     ListTag $$2 = new ListTag();
/* 106 */     $$0.getEntities().forEach($$1 -> {
/*     */           CompoundTag $$2 = new CompoundTag();
/*     */           
/*     */           if ($$1.save($$2)) {
/*     */             $$0.add($$2);
/*     */           }
/*     */         });
/* 113 */     CompoundTag $$3 = NbtUtils.addCurrentDataVersion(new CompoundTag());
/* 114 */     $$3.put("Entities", (Tag)$$2);
/* 115 */     writeChunkPos($$3, $$1);
/* 116 */     this.worker.store($$1, $$3).exceptionally($$1 -> {
/*     */           LOGGER.error("Failed to store chunk {}", $$0, $$1);
/*     */           return null;
/*     */         });
/* 120 */     this.emptyChunks.remove($$1.toLong());
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(boolean $$0) {
/* 125 */     this.worker.synchronize($$0).join();
/* 126 */     this.entityDeserializerQueue.runAll();
/*     */   }
/*     */   
/*     */   private CompoundTag upgradeChunkTag(CompoundTag $$0) {
/* 130 */     int $$1 = NbtUtils.getDataVersion($$0, -1);
/* 131 */     return DataFixTypes.ENTITY_CHUNK.updateToCurrentVersion(this.fixerUpper, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 136 */     this.worker.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\EntityStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */