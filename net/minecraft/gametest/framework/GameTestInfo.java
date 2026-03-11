/*     */ package net.minecraft.gametest.framework;
/*     */ 
/*     */ import com.google.common.base.Stopwatch;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GameTestInfo
/*     */ {
/*     */   private final TestFunction testFunction;
/*     */   @Nullable
/*     */   private BlockPos structureBlockPos;
/*     */   private final ServerLevel level;
/*  35 */   private final Collection<GameTestListener> listeners = Lists.newArrayList();
/*     */   
/*     */   private final int timeoutTicks;
/*     */   
/*  39 */   private final Collection<GameTestSequence> sequences = Lists.newCopyOnWriteArrayList();
/*  40 */   private final Object2LongMap<Runnable> runAtTickTimeMap = (Object2LongMap<Runnable>)new Object2LongOpenHashMap();
/*     */   
/*     */   private long startTick;
/*  43 */   private int ticksToWaitForChunkLoading = 20;
/*     */   private boolean placedStructure;
/*     */   private boolean chunksLoaded;
/*     */   private long tickCount;
/*     */   private boolean started;
/*     */   private boolean rerunUntilFailed;
/*  49 */   private final Stopwatch timer = Stopwatch.createUnstarted();
/*     */   
/*     */   private boolean done;
/*     */   
/*     */   private final Rotation rotation;
/*     */   @Nullable
/*     */   private Throwable error;
/*     */   @Nullable
/*     */   private StructureBlockEntity structureBlockEntity;
/*     */   
/*     */   public GameTestInfo(TestFunction $$0, Rotation $$1, ServerLevel $$2) {
/*  60 */     this.testFunction = $$0;
/*  61 */     this.level = $$2;
/*  62 */     this.timeoutTicks = $$0.getMaxTicks();
/*  63 */     this.rotation = $$0.getRotation().getRotated($$1);
/*     */   }
/*     */   
/*     */   void setStructureBlockPos(BlockPos $$0) {
/*  67 */     this.structureBlockPos = $$0;
/*     */   }
/*     */   
/*     */   void startExecution() {
/*  71 */     this.startTick = this.level.getGameTime() + this.testFunction.getSetupTicks();
/*  72 */     this.timer.start();
/*     */   }
/*     */   
/*     */   public void tick() {
/*  76 */     if (isDone()) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     if (this.structureBlockEntity == null) {
/*  81 */       fail(new IllegalStateException("Running test without structure block entity"));
/*     */     }
/*     */ 
/*     */     
/*  85 */     if (!this.chunksLoaded && !StructureUtils.getStructureBoundingBox(this.structureBlockEntity).intersectingChunks().allMatch($$0 -> this.level.isPositionEntityTicking($$0.getWorldPosition()))) {
/*     */       return;
/*     */     }
/*  88 */     this.chunksLoaded = true;
/*     */ 
/*     */     
/*  91 */     if (this.ticksToWaitForChunkLoading > 0) {
/*  92 */       this.ticksToWaitForChunkLoading--;
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     if (!this.placedStructure) {
/*  97 */       this.placedStructure = true;
/*  98 */       this.structureBlockEntity.placeStructure(this.level);
/*  99 */       BoundingBox $$0 = StructureUtils.getStructureBoundingBox(this.structureBlockEntity);
/* 100 */       this.level.getBlockTicks().clearArea($$0);
/* 101 */       this.level.clearBlockEvents($$0);
/* 102 */       startExecution();
/*     */     } 
/*     */     
/* 105 */     tickInternal();
/*     */     
/* 107 */     if (isDone()) {
/* 108 */       if (this.error != null) {
/* 109 */         this.listeners.forEach($$0 -> $$0.testFailed(this));
/*     */       } else {
/* 111 */         this.listeners.forEach($$0 -> $$0.testPassed(this));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void tickInternal() {
/* 117 */     this.tickCount = this.level.getGameTime() - this.startTick;
/* 118 */     if (this.tickCount < 0L) {
/*     */       return;
/*     */     }
/* 121 */     if (this.tickCount == 0L) {
/* 122 */       startTest();
/*     */     }
/* 124 */     ObjectIterator<Object2LongMap.Entry<Runnable>> $$0 = this.runAtTickTimeMap.object2LongEntrySet().iterator();
/* 125 */     while ($$0.hasNext()) {
/* 126 */       Object2LongMap.Entry<Runnable> $$1 = (Object2LongMap.Entry<Runnable>)$$0.next();
/* 127 */       if ($$1.getLongValue() <= this.tickCount) {
/*     */         try {
/* 129 */           ((Runnable)$$1.getKey()).run();
/* 130 */         } catch (Exception $$2) {
/* 131 */           fail($$2);
/*     */         } 
/* 133 */         $$0.remove();
/*     */       } 
/*     */     } 
/* 136 */     if (this.tickCount > this.timeoutTicks) {
/*     */       
/* 138 */       if (this.sequences.isEmpty()) {
/* 139 */         fail(new GameTestTimeoutException("Didn't succeed or fail within " + this.testFunction.getMaxTicks() + " ticks"));
/*     */       } else {
/* 141 */         this.sequences.forEach($$0 -> $$0.tickAndFailIfNotComplete(this.tickCount));
/* 142 */         if (this.error == null) {
/* 143 */           fail(new GameTestTimeoutException("No sequences finished"));
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 148 */       this.sequences.forEach($$0 -> $$0.tickAndContinue(this.tickCount));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void startTest() {
/* 153 */     if (this.started) {
/* 154 */       throw new IllegalStateException("Test already started");
/*     */     }
/* 156 */     this.started = true;
/*     */     try {
/* 158 */       this.testFunction.run(new GameTestHelper(this));
/* 159 */     } catch (Exception $$0) {
/* 160 */       fail($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setRunAtTickTime(long $$0, Runnable $$1) {
/* 165 */     this.runAtTickTimeMap.put($$1, $$0);
/*     */   }
/*     */   
/*     */   public String getTestName() {
/* 169 */     return this.testFunction.getTestName();
/*     */   }
/*     */   
/*     */   public BlockPos getStructureBlockPos() {
/* 173 */     return this.structureBlockPos;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getStructureOrigin() {
/* 178 */     StructureBlockEntity $$0 = getStructureBlockEntity();
/* 179 */     if ($$0 == null) {
/* 180 */       return null;
/*     */     }
/* 182 */     return StructureUtils.getStructureOrigin($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Vec3i getStructureSize() {
/* 187 */     StructureBlockEntity $$0 = getStructureBlockEntity();
/* 188 */     if ($$0 == null) {
/* 189 */       return null;
/*     */     }
/*     */     
/* 192 */     return $$0.getStructureSize();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AABB getStructureBounds() {
/* 197 */     StructureBlockEntity $$0 = getStructureBlockEntity();
/* 198 */     if ($$0 == null) {
/* 199 */       return null;
/*     */     }
/* 201 */     return StructureUtils.getStructureBounds($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private StructureBlockEntity getStructureBlockEntity() {
/* 206 */     return (StructureBlockEntity)this.level.getBlockEntity(this.structureBlockPos);
/*     */   }
/*     */   
/*     */   public ServerLevel getLevel() {
/* 210 */     return this.level;
/*     */   }
/*     */   
/*     */   public boolean hasSucceeded() {
/* 214 */     return (this.done && this.error == null);
/*     */   }
/*     */   
/*     */   public boolean hasFailed() {
/* 218 */     return (this.error != null);
/*     */   }
/*     */   
/*     */   public boolean hasStarted() {
/* 222 */     return this.started;
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/* 226 */     return this.done;
/*     */   }
/*     */   
/*     */   public long getRunTime() {
/* 230 */     return this.timer.elapsed(TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */   private void finish() {
/* 234 */     if (!this.done) {
/* 235 */       this.done = true;
/* 236 */       if (this.timer.isRunning()) {
/* 237 */         this.timer.stop();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void succeed() {
/* 244 */     if (this.error == null) {
/* 245 */       finish();
/* 246 */       AABB $$0 = getStructureBounds();
/* 247 */       List<Entity> $$1 = getLevel().getEntitiesOfClass(Entity.class, $$0.inflate(1.0D), $$0 -> !($$0 instanceof net.minecraft.world.entity.player.Player));
/* 248 */       $$1.forEach($$0 -> $$0.remove(Entity.RemovalReason.DISCARDED));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fail(Throwable $$0) {
/* 253 */     this.error = $$0;
/* 254 */     finish();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Throwable getError() {
/* 262 */     return this.error;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 267 */     return getTestName();
/*     */   }
/*     */   
/*     */   public void addListener(GameTestListener $$0) {
/* 271 */     this.listeners.add($$0);
/*     */   }
/*     */   
/*     */   public void prepareTestStructure(BlockPos $$0) {
/* 275 */     this.structureBlockEntity = StructureUtils.prepareTestStructure(this, $$0, getRotation(), this.level);
/* 276 */     this.structureBlockPos = this.structureBlockEntity.getBlockPos();
/*     */     
/* 278 */     StructureUtils.addCommandBlockAndButtonToStartTest(this.structureBlockPos, new BlockPos(1, 0, -1), getRotation(), this.level);
/*     */     
/* 280 */     this.listeners.forEach($$0 -> $$0.testStructureLoaded(this));
/*     */   }
/*     */   
/*     */   public void clearStructure() {
/* 284 */     if (this.structureBlockEntity == null) {
/* 285 */       throw new IllegalStateException("Expected structure to be initialized, but it was null");
/*     */     }
/*     */     
/* 288 */     BoundingBox $$0 = StructureUtils.getStructureBoundingBox(this.structureBlockEntity);
/* 289 */     StructureUtils.clearSpaceForStructure($$0, this.level);
/*     */   }
/*     */   
/*     */   long getTick() {
/* 293 */     return this.tickCount;
/*     */   }
/*     */   
/*     */   GameTestSequence createSequence() {
/* 297 */     GameTestSequence $$0 = new GameTestSequence(this);
/* 298 */     this.sequences.add($$0);
/* 299 */     return $$0;
/*     */   }
/*     */   
/*     */   public boolean isRequired() {
/* 303 */     return this.testFunction.isRequired();
/*     */   }
/*     */   
/*     */   public boolean isOptional() {
/* 307 */     return !this.testFunction.isRequired();
/*     */   }
/*     */   
/*     */   public String getStructureName() {
/* 311 */     return this.testFunction.getStructureName();
/*     */   }
/*     */   
/*     */   public Rotation getRotation() {
/* 315 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public TestFunction getTestFunction() {
/* 319 */     return this.testFunction;
/*     */   }
/*     */   
/*     */   public int getTimeoutTicks() {
/* 323 */     return this.timeoutTicks;
/*     */   }
/*     */   
/*     */   public boolean isFlaky() {
/* 327 */     return this.testFunction.isFlaky();
/*     */   }
/*     */   
/*     */   public int maxAttempts() {
/* 331 */     return this.testFunction.getMaxAttempts();
/*     */   }
/*     */   
/*     */   public int requiredSuccesses() {
/* 335 */     return this.testFunction.getRequiredSuccesses();
/*     */   }
/*     */   
/*     */   public void setRerunUntilFailed(boolean $$0) {
/* 339 */     this.rerunUntilFailed = $$0;
/*     */   }
/*     */   
/*     */   public boolean rerunUntilFailed() {
/* 343 */     return this.rerunUntilFailed;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */