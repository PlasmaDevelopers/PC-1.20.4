/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.common.primitives.Doubles;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexBuffer;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import com.mojang.blaze3d.vertex.VertexSorting;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ItemBlockRenderTypes;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.SectionBufferBuilderPack;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.block.ModelBlockRenderer;
/*     */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
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
/*     */ public class RenderSection
/*     */ {
/*     */   public static final int SIZE = 16;
/*     */   public final int index;
/* 253 */   public final AtomicReference<SectionRenderDispatcher.CompiledSection> compiled = new AtomicReference<>(SectionRenderDispatcher.CompiledSection.UNCOMPILED);
/* 254 */   final AtomicInteger initialCompilationCancelCount = new AtomicInteger(0);
/*     */   @Nullable
/*     */   private RebuildTask lastRebuildTask;
/*     */   @Nullable
/*     */   private ResortTransparencyTask lastResortTransparencyTask;
/* 259 */   private final Set<BlockEntity> globalBlockEntities = Sets.newHashSet(); private final Map<RenderType, VertexBuffer> buffers; private AABB bb; private boolean dirty;
/*     */   public RenderSection(int $$1, int $$2, int $$3, int $$4) {
/* 261 */     this.buffers = (Map<RenderType, VertexBuffer>)RenderType.chunkBufferLayers().stream().collect(Collectors.toMap($$0 -> $$0, $$0 -> new VertexBuffer(VertexBuffer.Usage.STATIC)));
/*     */ 
/*     */ 
/*     */     
/* 265 */     this.dirty = true;
/*     */     
/* 267 */     this.origin = new BlockPos.MutableBlockPos(-1, -1, -1);
/* 268 */     this.relativeOrigins = (BlockPos.MutableBlockPos[])Util.make(new BlockPos.MutableBlockPos[6], $$0 -> {
/*     */           for (int $$1 = 0; $$1 < $$0.length; $$1++) {
/*     */             $$0[$$1] = new BlockPos.MutableBlockPos();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     this.index = $$1;
/* 278 */     setOrigin($$2, $$3, $$4);
/*     */   }
/*     */   final BlockPos.MutableBlockPos origin; private final BlockPos.MutableBlockPos[] relativeOrigins; private boolean playerChanged;
/*     */   private boolean doesChunkExistAt(BlockPos $$0) {
/* 282 */     return (SectionRenderDispatcher.this.level.getChunk(SectionPos.blockToSectionCoord($$0.getX()), SectionPos.blockToSectionCoord($$0.getZ()), ChunkStatus.FULL, false) != null);
/*     */   }
/*     */   
/*     */   public boolean hasAllNeighbors() {
/* 286 */     int $$0 = 24;
/*     */     
/* 288 */     if (getDistToPlayerSqr() > 576.0D) {
/* 289 */       return (doesChunkExistAt((BlockPos)this.relativeOrigins[Direction.WEST.ordinal()]) && 
/* 290 */         doesChunkExistAt((BlockPos)this.relativeOrigins[Direction.NORTH.ordinal()]) && 
/* 291 */         doesChunkExistAt((BlockPos)this.relativeOrigins[Direction.EAST.ordinal()]) && 
/* 292 */         doesChunkExistAt((BlockPos)this.relativeOrigins[Direction.SOUTH.ordinal()]));
/*     */     }
/* 294 */     return true;
/*     */   }
/*     */   
/*     */   public AABB getBoundingBox() {
/* 298 */     return this.bb;
/*     */   }
/*     */   
/*     */   public VertexBuffer getBuffer(RenderType $$0) {
/* 302 */     return this.buffers.get($$0);
/*     */   }
/*     */   
/*     */   public void setOrigin(int $$0, int $$1, int $$2) {
/* 306 */     reset();
/* 307 */     this.origin.set($$0, $$1, $$2);
/* 308 */     this.bb = new AABB($$0, $$1, $$2, ($$0 + 16), ($$1 + 16), ($$2 + 16));
/*     */     
/* 310 */     for (Direction $$3 : Direction.values()) {
/* 311 */       this.relativeOrigins[$$3.ordinal()].set((Vec3i)this.origin).move($$3, 16);
/*     */     }
/*     */   }
/*     */   
/*     */   protected double getDistToPlayerSqr() {
/* 316 */     Camera $$0 = (Minecraft.getInstance()).gameRenderer.getMainCamera();
/* 317 */     double $$1 = this.bb.minX + 8.0D - ($$0.getPosition()).x;
/* 318 */     double $$2 = this.bb.minY + 8.0D - ($$0.getPosition()).y;
/* 319 */     double $$3 = this.bb.minZ + 8.0D - ($$0.getPosition()).z;
/* 320 */     return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
/*     */   }
/*     */   
/*     */   void beginLayer(BufferBuilder $$0) {
/* 324 */     $$0.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
/*     */   }
/*     */   
/*     */   public SectionRenderDispatcher.CompiledSection getCompiled() {
/* 328 */     return this.compiled.get();
/*     */   }
/*     */   
/*     */   private void reset() {
/* 332 */     cancelTasks();
/* 333 */     this.compiled.set(SectionRenderDispatcher.CompiledSection.UNCOMPILED);
/* 334 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public void releaseBuffers() {
/* 338 */     reset();
/* 339 */     this.buffers.values().forEach(VertexBuffer::close);
/*     */   }
/*     */   
/*     */   public BlockPos getOrigin() {
/* 343 */     return (BlockPos)this.origin;
/*     */   }
/*     */   
/*     */   public void setDirty(boolean $$0) {
/* 347 */     boolean $$1 = this.dirty;
/* 348 */     this.dirty = true;
/* 349 */     this.playerChanged = $$0 | (($$1 && this.playerChanged));
/*     */   }
/*     */   
/*     */   public void setNotDirty() {
/* 353 */     this.dirty = false;
/* 354 */     this.playerChanged = false;
/*     */   }
/*     */   
/*     */   public boolean isDirty() {
/* 358 */     return this.dirty;
/*     */   }
/*     */   
/*     */   public boolean isDirtyFromPlayer() {
/* 362 */     return (this.dirty && this.playerChanged);
/*     */   }
/*     */   
/*     */   public BlockPos getRelativeOrigin(Direction $$0) {
/* 366 */     return (BlockPos)this.relativeOrigins[$$0.ordinal()];
/*     */   }
/*     */   
/*     */   public boolean resortTransparency(RenderType $$0, SectionRenderDispatcher $$1) {
/* 370 */     SectionRenderDispatcher.CompiledSection $$2 = getCompiled();
/* 371 */     if (this.lastResortTransparencyTask != null) {
/* 372 */       this.lastResortTransparencyTask.cancel();
/*     */     }
/* 374 */     if (!$$2.hasBlocks.contains($$0)) {
/* 375 */       return false;
/*     */     }
/* 377 */     this.lastResortTransparencyTask = new ResortTransparencyTask(getDistToPlayerSqr(), $$2);
/* 378 */     $$1.schedule(this.lastResortTransparencyTask);
/* 379 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean cancelTasks() {
/* 383 */     boolean $$0 = false;
/* 384 */     if (this.lastRebuildTask != null) {
/* 385 */       this.lastRebuildTask.cancel();
/* 386 */       this.lastRebuildTask = null;
/* 387 */       $$0 = true;
/*     */     } 
/* 389 */     if (this.lastResortTransparencyTask != null) {
/* 390 */       this.lastResortTransparencyTask.cancel();
/* 391 */       this.lastResortTransparencyTask = null;
/*     */     } 
/* 393 */     return $$0;
/*     */   }
/*     */   
/*     */   public CompileTask createCompileTask(RenderRegionCache $$0) {
/* 397 */     boolean $$1 = cancelTasks();
/* 398 */     BlockPos $$2 = this.origin.immutable();
/* 399 */     int $$3 = 1;
/* 400 */     RenderChunkRegion $$4 = $$0.createRegion((Level)SectionRenderDispatcher.this.level, $$2.offset(-1, -1, -1), $$2.offset(16, 16, 16), 1);
/* 401 */     boolean $$5 = (this.compiled.get() == SectionRenderDispatcher.CompiledSection.UNCOMPILED);
/* 402 */     if ($$5 && $$1) {
/* 403 */       this.initialCompilationCancelCount.incrementAndGet();
/*     */     }
/* 405 */     this.lastRebuildTask = new RebuildTask(getDistToPlayerSqr(), $$4, (!$$5 || this.initialCompilationCancelCount.get() > 2));
/* 406 */     return this.lastRebuildTask;
/*     */   }
/*     */   
/*     */   public void rebuildSectionAsync(SectionRenderDispatcher $$0, RenderRegionCache $$1) {
/* 410 */     CompileTask $$2 = createCompileTask($$1);
/* 411 */     $$0.schedule($$2);
/*     */   }
/*     */   
/*     */   void updateGlobalBlockEntities(Collection<BlockEntity> $$0) {
/* 415 */     Set<BlockEntity> $$2, $$1 = Sets.newHashSet($$0);
/*     */     
/* 417 */     synchronized (this.globalBlockEntities) {
/* 418 */       $$2 = Sets.newHashSet(this.globalBlockEntities);
/* 419 */       $$1.removeAll(this.globalBlockEntities);
/* 420 */       $$2.removeAll($$0);
/* 421 */       this.globalBlockEntities.clear();
/* 422 */       this.globalBlockEntities.addAll($$0);
/*     */     } 
/* 424 */     SectionRenderDispatcher.this.renderer.updateGlobalBlockEntities($$2, $$1);
/*     */   }
/*     */   
/*     */   public void compileSync(RenderRegionCache $$0) {
/* 428 */     CompileTask $$1 = createCompileTask($$0);
/*     */ 
/*     */     
/* 431 */     $$1.doTask(SectionRenderDispatcher.this.fixedBuffers);
/*     */   }
/*     */   
/*     */   public boolean isAxisAlignedWith(int $$0, int $$1, int $$2) {
/* 435 */     BlockPos $$3 = getOrigin();
/* 436 */     return ($$0 == SectionPos.blockToSectionCoord($$3.getX()) || $$2 == 
/* 437 */       SectionPos.blockToSectionCoord($$3.getZ()) || $$1 == 
/* 438 */       SectionPos.blockToSectionCoord($$3.getY()));
/*     */   }
/*     */   
/*     */   private class RebuildTask extends CompileTask {
/*     */     @Nullable
/*     */     protected RenderChunkRegion region;
/*     */     
/*     */     public RebuildTask(@Nullable double $$0, RenderChunkRegion $$1, boolean $$2) {
/* 446 */       super($$0, $$2);
/* 447 */       this.region = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String name() {
/* 452 */       return "rend_chk_rebuild";
/*     */     }
/*     */ 
/*     */     
/*     */     public CompletableFuture<SectionRenderDispatcher.SectionTaskResult> doTask(SectionBufferBuilderPack $$0) {
/* 457 */       if (this.isCancelled.get()) {
/* 458 */         return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       }
/*     */       
/* 461 */       if (!SectionRenderDispatcher.RenderSection.this.hasAllNeighbors()) {
/* 462 */         this.region = null;
/* 463 */         SectionRenderDispatcher.RenderSection.this.setDirty(false);
/* 464 */         this.isCancelled.set(true);
/* 465 */         return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       } 
/*     */       
/* 468 */       if (this.isCancelled.get()) {
/* 469 */         return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       }
/*     */       
/* 472 */       Vec3 $$1 = this.this$1.this$0.getCameraPosition();
/* 473 */       float $$2 = (float)$$1.x;
/* 474 */       float $$3 = (float)$$1.y;
/* 475 */       float $$4 = (float)$$1.z;
/*     */       
/* 477 */       CompileResults $$5 = compile($$2, $$3, $$4, $$0);
/* 478 */       SectionRenderDispatcher.RenderSection.this.updateGlobalBlockEntities($$5.globalBlockEntities);
/*     */       
/* 480 */       if (this.isCancelled.get()) {
/* 481 */         $$5.renderedLayers.values().forEach(BufferBuilder.RenderedBuffer::release);
/* 482 */         return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       } 
/*     */       
/* 485 */       SectionRenderDispatcher.CompiledSection $$6 = new SectionRenderDispatcher.CompiledSection();
/* 486 */       $$6.visibilitySet = $$5.visibilitySet;
/* 487 */       $$6.renderableBlockEntities.addAll($$5.blockEntities);
/* 488 */       $$6.transparencyState = $$5.transparencyState;
/*     */       
/* 490 */       List<CompletableFuture<Void>> $$7 = Lists.newArrayList();
/*     */       
/* 492 */       $$5.renderedLayers.forEach(($$2, $$3) -> {
/*     */             $$0.add(this.this$1.this$0.uploadSectionLayer($$3, SectionRenderDispatcher.RenderSection.this.getBuffer($$2)));
/*     */             
/*     */             $$1.hasBlocks.add($$2);
/*     */           });
/* 497 */       return Util.sequenceFailFast($$7).handle(($$1, $$2) -> {
/*     */             if ($$2 != null && !($$2 instanceof java.util.concurrent.CancellationException) && !($$2 instanceof InterruptedException)) {
/*     */               Minecraft.getInstance().delayCrash(CrashReport.forThrowable($$2, "Rendering section"));
/*     */             }
/*     */             if (this.isCancelled.get()) {
/*     */               return SectionRenderDispatcher.SectionTaskResult.CANCELLED;
/*     */             }
/*     */             SectionRenderDispatcher.RenderSection.this.compiled.set($$0);
/*     */             SectionRenderDispatcher.RenderSection.this.initialCompilationCancelCount.set(0);
/*     */             this.this$1.this$0.renderer.addRecentlyCompiledSection(SectionRenderDispatcher.RenderSection.this);
/*     */             return SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL;
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     private CompileResults compile(float $$0, float $$1, float $$2, SectionBufferBuilderPack $$3) {
/* 513 */       CompileResults $$4 = new CompileResults();
/*     */       
/* 515 */       int $$5 = 1;
/*     */       
/* 517 */       BlockPos $$6 = SectionRenderDispatcher.RenderSection.this.origin.immutable();
/* 518 */       BlockPos $$7 = $$6.offset(15, 15, 15);
/*     */       
/* 520 */       VisGraph $$8 = new VisGraph();
/* 521 */       RenderChunkRegion $$9 = this.region;
/* 522 */       this.region = null;
/* 523 */       PoseStack $$10 = new PoseStack();
/*     */       
/* 525 */       if ($$9 != null) {
/* 526 */         ModelBlockRenderer.enableCaching();
/*     */         
/* 528 */         ReferenceArraySet<RenderType> referenceArraySet = new ReferenceArraySet(RenderType.chunkBufferLayers().size());
/*     */         
/* 530 */         RandomSource $$12 = RandomSource.create();
/* 531 */         BlockRenderDispatcher $$13 = Minecraft.getInstance().getBlockRenderer();
/* 532 */         for (BlockPos $$14 : BlockPos.betweenClosed($$6, $$7)) {
/* 533 */           BlockState $$15 = $$9.getBlockState($$14);
/* 534 */           if ($$15.isSolidRender((BlockGetter)$$9, $$14)) {
/* 535 */             $$8.setOpaque($$14);
/*     */           }
/*     */           
/* 538 */           if ($$15.hasBlockEntity()) {
/* 539 */             BlockEntity $$16 = $$9.getBlockEntity($$14);
/* 540 */             if ($$16 != null) {
/* 541 */               handleBlockEntity($$4, $$16);
/*     */             }
/*     */           } 
/*     */           
/* 545 */           FluidState $$17 = $$15.getFluidState();
/*     */           
/* 547 */           if (!$$17.isEmpty()) {
/* 548 */             RenderType $$18 = ItemBlockRenderTypes.getRenderLayer($$17);
/* 549 */             BufferBuilder $$19 = $$3.builder($$18);
/* 550 */             if (referenceArraySet.add($$18)) {
/* 551 */               SectionRenderDispatcher.RenderSection.this.beginLayer($$19);
/*     */             }
/* 553 */             $$13.renderLiquid($$14, $$9, (VertexConsumer)$$19, $$15, $$17);
/*     */           } 
/*     */           
/* 556 */           if ($$15.getRenderShape() != RenderShape.INVISIBLE) {
/* 557 */             RenderType $$20 = ItemBlockRenderTypes.getChunkRenderType($$15);
/* 558 */             BufferBuilder $$21 = $$3.builder($$20);
/* 559 */             if (referenceArraySet.add($$20)) {
/* 560 */               SectionRenderDispatcher.RenderSection.this.beginLayer($$21);
/*     */             }
/*     */             
/* 563 */             $$10.pushPose();
/* 564 */             $$10.translate(($$14.getX() & 0xF), ($$14.getY() & 0xF), ($$14.getZ() & 0xF));
/* 565 */             $$13.renderBatched($$15, $$14, $$9, $$10, (VertexConsumer)$$21, true, $$12);
/* 566 */             $$10.popPose();
/*     */           } 
/*     */         } 
/*     */         
/* 570 */         if (referenceArraySet.contains(RenderType.translucent())) {
/* 571 */           BufferBuilder $$22 = $$3.builder(RenderType.translucent());
/* 572 */           if (!$$22.isCurrentBatchEmpty()) {
/* 573 */             $$22.setQuadSorting(VertexSorting.byDistance($$0 - $$6.getX(), $$1 - $$6.getY(), $$2 - $$6.getZ()));
/* 574 */             $$4.transparencyState = $$22.getSortState();
/*     */           } 
/*     */         } 
/*     */         
/* 578 */         for (RenderType $$23 : referenceArraySet) {
/* 579 */           BufferBuilder.RenderedBuffer $$24 = $$3.builder($$23).endOrDiscardIfEmpty();
/* 580 */           if ($$24 != null) {
/* 581 */             $$4.renderedLayers.put($$23, $$24);
/*     */           }
/*     */         } 
/*     */         
/* 585 */         ModelBlockRenderer.clearCache();
/*     */       } 
/*     */       
/* 588 */       $$4.visibilitySet = $$8.resolve();
/*     */       
/* 590 */       return $$4;
/*     */     }
/*     */     
/*     */     private <E extends BlockEntity> void handleBlockEntity(CompileResults $$0, E $$1) {
/* 594 */       BlockEntityRenderer<E> $$2 = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer((BlockEntity)$$1);
/* 595 */       if ($$2 != null) {
/* 596 */         $$0.blockEntities.add((BlockEntity)$$1);
/* 597 */         if ($$2.shouldRenderOffScreen((BlockEntity)$$1)) {
/* 598 */           $$0.globalBlockEntities.add((BlockEntity)$$1);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void cancel() {
/* 605 */       this.region = null;
/* 606 */       if (this.isCancelled.compareAndSet(false, true))
/* 607 */         SectionRenderDispatcher.RenderSection.this.setDirty(false); 
/*     */     }
/*     */     
/*     */     private static final class CompileResults
/*     */     {
/* 612 */       public final List<BlockEntity> globalBlockEntities = new ArrayList<>();
/* 613 */       public final List<BlockEntity> blockEntities = new ArrayList<>();
/* 614 */       public final Map<RenderType, BufferBuilder.RenderedBuffer> renderedLayers = (Map<RenderType, BufferBuilder.RenderedBuffer>)new Reference2ObjectArrayMap();
/* 615 */       public VisibilitySet visibilitySet = new VisibilitySet();
/*     */       @Nullable
/*     */       public BufferBuilder.SortState transparencyState;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ResortTransparencyTask extends CompileTask {
/*     */     private final SectionRenderDispatcher.CompiledSection compiledSection;
/*     */     
/*     */     public ResortTransparencyTask(double $$0, SectionRenderDispatcher.CompiledSection $$1) {
/* 625 */       super($$0, true);
/* 626 */       this.compiledSection = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String name() {
/* 631 */       return "rend_chk_sort";
/*     */     }
/*     */ 
/*     */     
/*     */     public CompletableFuture<SectionRenderDispatcher.SectionTaskResult> doTask(SectionBufferBuilderPack $$0) {
/* 636 */       if (this.isCancelled.get()) {
/* 637 */         return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       }
/*     */       
/* 640 */       if (!SectionRenderDispatcher.RenderSection.this.hasAllNeighbors()) {
/* 641 */         this.isCancelled.set(true);
/* 642 */         return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       } 
/*     */       
/* 645 */       if (this.isCancelled.get()) {
/* 646 */         return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       }
/*     */       
/* 649 */       Vec3 $$1 = this.this$1.this$0.getCameraPosition();
/* 650 */       float $$2 = (float)$$1.x;
/* 651 */       float $$3 = (float)$$1.y;
/* 652 */       float $$4 = (float)$$1.z;
/*     */       
/* 654 */       BufferBuilder.SortState $$5 = this.compiledSection.transparencyState;
/* 655 */       if ($$5 == null || this.compiledSection.isEmpty(RenderType.translucent())) {
/* 656 */         return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       }
/*     */       
/* 659 */       BufferBuilder $$6 = $$0.builder(RenderType.translucent());
/* 660 */       SectionRenderDispatcher.RenderSection.this.beginLayer($$6);
/* 661 */       $$6.restoreSortState($$5);
/* 662 */       $$6.setQuadSorting(VertexSorting.byDistance($$2 - SectionRenderDispatcher.RenderSection.this.origin.getX(), $$3 - SectionRenderDispatcher.RenderSection.this.origin.getY(), $$4 - SectionRenderDispatcher.RenderSection.this.origin.getZ()));
/* 663 */       this.compiledSection.transparencyState = $$6.getSortState();
/* 664 */       BufferBuilder.RenderedBuffer $$7 = $$6.end();
/*     */       
/* 666 */       if (this.isCancelled.get()) {
/* 667 */         $$7.release();
/* 668 */         return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       } 
/*     */       
/* 671 */       CompletableFuture<SectionRenderDispatcher.SectionTaskResult> $$8 = this.this$1.this$0.uploadSectionLayer($$7, SectionRenderDispatcher.RenderSection.this.getBuffer(RenderType.translucent())).thenApply($$0 -> SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */       
/* 673 */       return $$8.handle(($$0, $$1) -> {
/*     */             if ($$1 != null && !($$1 instanceof java.util.concurrent.CancellationException) && !($$1 instanceof InterruptedException)) {
/*     */               Minecraft.getInstance().delayCrash(CrashReport.forThrowable($$1, "Rendering section"));
/*     */             }
/*     */             return this.isCancelled.get() ? SectionRenderDispatcher.SectionTaskResult.CANCELLED : SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL;
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public void cancel() {
/* 683 */       this.isCancelled.set(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private abstract class CompileTask implements Comparable<CompileTask> {
/*     */     protected final double distAtCreation;
/* 689 */     protected final AtomicBoolean isCancelled = new AtomicBoolean(false);
/*     */     protected final boolean isHighPriority;
/*     */     
/*     */     public CompileTask(double $$0, boolean $$1) {
/* 693 */       this.distAtCreation = $$0;
/* 694 */       this.isHighPriority = $$1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(CompileTask $$0) {
/* 705 */       return Doubles.compare(this.distAtCreation, $$0.distAtCreation);
/*     */     }
/*     */     
/*     */     public abstract CompletableFuture<SectionRenderDispatcher.SectionTaskResult> doTask(SectionBufferBuilderPack param2SectionBufferBuilderPack);
/*     */     
/*     */     public abstract void cancel();
/*     */     
/*     */     protected abstract String name();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\chunk\SectionRenderDispatcher$RenderSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */