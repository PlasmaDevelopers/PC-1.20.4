/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.IntSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.thread.ProcessorHandle;
/*     */ import net.minecraft.util.thread.ProcessorMailbox;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.DataLayer;
/*     */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*     */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ThreadedLevelLightEngine
/*     */   extends LevelLightEngine
/*     */   implements AutoCloseable
/*     */ {
/*     */   public static final int DEFAULT_BATCH_SIZE = 1000;
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private final ProcessorMailbox<Runnable> taskMailbox;
/*  33 */   private final ObjectList<Pair<TaskType, Runnable>> lightTasks = (ObjectList<Pair<TaskType, Runnable>>)new ObjectArrayList();
/*     */   private final ChunkMap chunkMap;
/*     */   private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> sorterMailbox;
/*  36 */   private final int taskPerBatch = 1000;
/*  37 */   private final AtomicBoolean scheduled = new AtomicBoolean();
/*     */   
/*     */   public ThreadedLevelLightEngine(LightChunkGetter $$0, ChunkMap $$1, boolean $$2, ProcessorMailbox<Runnable> $$3, ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> $$4) {
/*  40 */     super($$0, true, $$2);
/*  41 */     this.chunkMap = $$1;
/*  42 */     this.sorterMailbox = $$4;
/*  43 */     this.taskMailbox = $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */   
/*     */   public int runLightUpdates() {
/*  52 */     throw (UnsupportedOperationException)Util.pauseInIde(new UnsupportedOperationException("Ran automatically on a different thread!"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkBlock(BlockPos $$0) {
/*  57 */     BlockPos $$1 = $$0.immutable();
/*  58 */     addTask(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()), TaskType.PRE_UPDATE, Util.name(() -> super.checkBlock($$0), () -> "checkBlock " + $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateChunkStatus(ChunkPos $$0) {
/*  63 */     addTask($$0.x, $$0.z, () -> 0, TaskType.PRE_UPDATE, Util.name(() -> {
/*     */             super.retainData($$0, false);
/*     */             super.setLightEnabled($$0, false);
/*     */             for (int $$1 = getMinLightSection(); $$1 < getMaxLightSection(); $$1++) {
/*     */               super.queueSectionData(LightLayer.BLOCK, SectionPos.of($$0, $$1), null);
/*     */               super.queueSectionData(LightLayer.SKY, SectionPos.of($$0, $$1), null);
/*     */             } 
/*     */             for (int $$2 = this.levelHeightAccessor.getMinSection(); $$2 < this.levelHeightAccessor.getMaxSection(); $$2++) {
/*     */               super.updateSectionStatus(SectionPos.of($$0, $$2), true);
/*     */             }
/*     */           }() -> "updateChunkStatus " + $$0 + " true"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateSectionStatus(SectionPos $$0, boolean $$1) {
/*  82 */     addTask($$0.x(), $$0.z(), () -> 0, TaskType.PRE_UPDATE, Util.name(() -> super.updateSectionStatus($$0, $$1), () -> "updateSectionStatus " + $$0 + " " + $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void propagateLightSources(ChunkPos $$0) {
/*  87 */     addTask($$0.x, $$0.z, TaskType.PRE_UPDATE, Util.name(() -> super.propagateLightSources($$0), () -> "propagateLight " + $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLightEnabled(ChunkPos $$0, boolean $$1) {
/*  92 */     addTask($$0.x, $$0.z, TaskType.PRE_UPDATE, Util.name(() -> super.setLightEnabled($$0, $$1), () -> "enableLight " + $$0 + " " + $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void queueSectionData(LightLayer $$0, SectionPos $$1, @Nullable DataLayer $$2) {
/*  98 */     addTask($$1.x(), $$1.z(), () -> 0, TaskType.PRE_UPDATE, Util.name(() -> super.queueSectionData($$0, $$1, $$2), () -> "queueData " + $$0));
/*     */   }
/*     */   
/*     */   private void addTask(int $$0, int $$1, TaskType $$2, Runnable $$3) {
/* 102 */     addTask($$0, $$1, this.chunkMap.getChunkQueueLevel(ChunkPos.asLong($$0, $$1)), $$2, $$3);
/*     */   }
/*     */   
/*     */   private void addTask(int $$0, int $$1, IntSupplier $$2, TaskType $$3, Runnable $$4) {
/* 106 */     this.sorterMailbox.tell(ChunkTaskPriorityQueueSorter.message(() -> {
/*     */             this.lightTasks.add(Pair.of($$0, $$1));
/*     */             if (this.lightTasks.size() >= 1000) {
/*     */               runUpdate();
/*     */             }
/* 111 */           }ChunkPos.asLong($$0, $$1), $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void retainData(ChunkPos $$0, boolean $$1) {
/* 116 */     addTask($$0.x, $$0.z, () -> 0, TaskType.PRE_UPDATE, Util.name(() -> super.retainData($$0, $$1), () -> "retainData " + $$0));
/*     */   }
/*     */   
/*     */   public CompletableFuture<ChunkAccess> initializeLight(ChunkAccess $$0, boolean $$1) {
/* 120 */     ChunkPos $$2 = $$0.getPos();
/* 121 */     addTask($$2.x, $$2.z, TaskType.PRE_UPDATE, Util.name(() -> {
/*     */             LevelChunkSection[] $$2 = $$0.getSections();
/*     */             for (int $$3 = 0; $$3 < $$0.getSectionsCount(); $$3++) {
/*     */               LevelChunkSection $$4 = $$2[$$3];
/*     */               if (!$$4.hasOnlyAir()) {
/*     */                 int $$5 = this.levelHeightAccessor.getSectionYFromSectionIndex($$3);
/*     */                 super.updateSectionStatus(SectionPos.of($$1, $$5), false);
/*     */               } 
/*     */             } 
/*     */           }() -> "initializeLight: " + $$0));
/* 131 */     return CompletableFuture.supplyAsync(() -> {
/*     */           super.setLightEnabled($$0, $$1);
/*     */           super.retainData($$0, false);
/*     */           return $$2;
/*     */         }$$1 -> addTask($$0.x, $$0.z, TaskType.POST_UPDATE, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompletableFuture<ChunkAccess> lightChunk(ChunkAccess $$0, boolean $$1) {
/* 142 */     ChunkPos $$2 = $$0.getPos();
/* 143 */     $$0.setLightCorrect(false);
/* 144 */     addTask($$2.x, $$2.z, TaskType.PRE_UPDATE, Util.name(() -> {
/*     */             if (!$$0) {
/*     */               super.propagateLightSources($$1);
/*     */             }
/*     */           }() -> "lightChunk " + $$0 + " " + $$1));
/*     */ 
/*     */ 
/*     */     
/* 152 */     return CompletableFuture.supplyAsync(() -> {
/*     */           $$0.setLightCorrect(true);
/*     */           this.chunkMap.releaseLightTicket($$1);
/*     */           return $$0;
/*     */         }$$1 -> addTask($$0.x, $$0.z, TaskType.POST_UPDATE, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tryScheduleUpdate() {
/* 164 */     if ((!this.lightTasks.isEmpty() || hasLightWork()) && this.scheduled.compareAndSet(false, true)) {
/* 165 */       this.taskMailbox.tell(() -> {
/*     */             runUpdate();
/*     */             this.scheduled.set(false);
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   private void runUpdate() {
/* 173 */     int $$0 = Math.min(this.lightTasks.size(), 1000);
/*     */     
/* 175 */     ObjectListIterator<Pair<TaskType, Runnable>> $$1 = this.lightTasks.iterator();
/*     */     
/* 177 */     int $$2 = 0;
/* 178 */     while ($$1.hasNext() && $$2 < $$0) {
/* 179 */       Pair<TaskType, Runnable> $$3 = (Pair<TaskType, Runnable>)$$1.next();
/* 180 */       if ($$3.getFirst() == TaskType.PRE_UPDATE) {
/* 181 */         ((Runnable)$$3.getSecond()).run();
/*     */       }
/* 183 */       $$2++;
/*     */     } 
/* 185 */     $$1.back($$2);
/*     */     
/* 187 */     super.runLightUpdates();
/*     */     
/* 189 */     $$2 = 0;
/* 190 */     while ($$1.hasNext() && $$2 < $$0) {
/* 191 */       Pair<TaskType, Runnable> $$4 = (Pair<TaskType, Runnable>)$$1.next();
/* 192 */       if ($$4.getFirst() == TaskType.POST_UPDATE) {
/* 193 */         ((Runnable)$$4.getSecond()).run();
/*     */       }
/* 195 */       $$1.remove();
/* 196 */       $$2++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public CompletableFuture<?> waitForPendingTasks(int $$0, int $$1) {
/* 201 */     return CompletableFuture.runAsync(() -> {
/*     */         
/*     */         }$$2 -> addTask($$0, $$1, TaskType.POST_UPDATE, $$2));
/*     */   }
/*     */   
/*     */   private enum TaskType
/*     */   {
/* 208 */     PRE_UPDATE, POST_UPDATE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ThreadedLevelLightEngine.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */