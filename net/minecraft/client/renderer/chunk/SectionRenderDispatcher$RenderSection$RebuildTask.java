/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.blaze3d.vertex.VertexSorting;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.ItemBlockRenderTypes;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.SectionBufferBuilderPack;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.block.ModelBlockRenderer;
/*     */ import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RebuildTask
/*     */   extends SectionRenderDispatcher.RenderSection.CompileTask
/*     */ {
/*     */   @Nullable
/*     */   protected RenderChunkRegion region;
/*     */   
/*     */   public RebuildTask(@Nullable double $$0, RenderChunkRegion $$1, boolean $$2) {
/* 446 */     super($$0, $$2);
/* 447 */     this.region = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String name() {
/* 452 */     return "rend_chk_rebuild";
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<SectionRenderDispatcher.SectionTaskResult> doTask(SectionBufferBuilderPack $$0) {
/* 457 */     if (this.isCancelled.get()) {
/* 458 */       return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */     }
/*     */     
/* 461 */     if (!SectionRenderDispatcher.RenderSection.this.hasAllNeighbors()) {
/* 462 */       this.region = null;
/* 463 */       SectionRenderDispatcher.RenderSection.this.setDirty(false);
/* 464 */       this.isCancelled.set(true);
/* 465 */       return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */     } 
/*     */     
/* 468 */     if (this.isCancelled.get()) {
/* 469 */       return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */     }
/*     */     
/* 472 */     Vec3 $$1 = this.this$1.this$0.getCameraPosition();
/* 473 */     float $$2 = (float)$$1.x;
/* 474 */     float $$3 = (float)$$1.y;
/* 475 */     float $$4 = (float)$$1.z;
/*     */     
/* 477 */     CompileResults $$5 = compile($$2, $$3, $$4, $$0);
/* 478 */     SectionRenderDispatcher.RenderSection.this.updateGlobalBlockEntities($$5.globalBlockEntities);
/*     */     
/* 480 */     if (this.isCancelled.get()) {
/* 481 */       $$5.renderedLayers.values().forEach(BufferBuilder.RenderedBuffer::release);
/* 482 */       return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
/*     */     } 
/*     */     
/* 485 */     SectionRenderDispatcher.CompiledSection $$6 = new SectionRenderDispatcher.CompiledSection();
/* 486 */     $$6.visibilitySet = $$5.visibilitySet;
/* 487 */     $$6.renderableBlockEntities.addAll($$5.blockEntities);
/* 488 */     $$6.transparencyState = $$5.transparencyState;
/*     */     
/* 490 */     List<CompletableFuture<Void>> $$7 = Lists.newArrayList();
/*     */     
/* 492 */     $$5.renderedLayers.forEach(($$2, $$3) -> {
/*     */           $$0.add(this.this$1.this$0.uploadSectionLayer($$3, SectionRenderDispatcher.RenderSection.this.getBuffer($$2)));
/*     */           
/*     */           $$1.hasBlocks.add($$2);
/*     */         });
/* 497 */     return Util.sequenceFailFast($$7).handle(($$1, $$2) -> {
/*     */           if ($$2 != null && !($$2 instanceof java.util.concurrent.CancellationException) && !($$2 instanceof InterruptedException)) {
/*     */             Minecraft.getInstance().delayCrash(CrashReport.forThrowable($$2, "Rendering section"));
/*     */           }
/*     */           if (this.isCancelled.get()) {
/*     */             return SectionRenderDispatcher.SectionTaskResult.CANCELLED;
/*     */           }
/*     */           SectionRenderDispatcher.RenderSection.this.compiled.set($$0);
/*     */           SectionRenderDispatcher.RenderSection.this.initialCompilationCancelCount.set(0);
/*     */           this.this$1.this$0.renderer.addRecentlyCompiledSection(SectionRenderDispatcher.RenderSection.this);
/*     */           return SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL;
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private CompileResults compile(float $$0, float $$1, float $$2, SectionBufferBuilderPack $$3) {
/* 513 */     CompileResults $$4 = new CompileResults();
/*     */     
/* 515 */     int $$5 = 1;
/*     */     
/* 517 */     BlockPos $$6 = SectionRenderDispatcher.RenderSection.this.origin.immutable();
/* 518 */     BlockPos $$7 = $$6.offset(15, 15, 15);
/*     */     
/* 520 */     VisGraph $$8 = new VisGraph();
/* 521 */     RenderChunkRegion $$9 = this.region;
/* 522 */     this.region = null;
/* 523 */     PoseStack $$10 = new PoseStack();
/*     */     
/* 525 */     if ($$9 != null) {
/* 526 */       ModelBlockRenderer.enableCaching();
/*     */       
/* 528 */       ReferenceArraySet<RenderType> referenceArraySet = new ReferenceArraySet(RenderType.chunkBufferLayers().size());
/*     */       
/* 530 */       RandomSource $$12 = RandomSource.create();
/* 531 */       BlockRenderDispatcher $$13 = Minecraft.getInstance().getBlockRenderer();
/* 532 */       for (BlockPos $$14 : BlockPos.betweenClosed($$6, $$7)) {
/* 533 */         BlockState $$15 = $$9.getBlockState($$14);
/* 534 */         if ($$15.isSolidRender((BlockGetter)$$9, $$14)) {
/* 535 */           $$8.setOpaque($$14);
/*     */         }
/*     */         
/* 538 */         if ($$15.hasBlockEntity()) {
/* 539 */           BlockEntity $$16 = $$9.getBlockEntity($$14);
/* 540 */           if ($$16 != null) {
/* 541 */             handleBlockEntity($$4, $$16);
/*     */           }
/*     */         } 
/*     */         
/* 545 */         FluidState $$17 = $$15.getFluidState();
/*     */         
/* 547 */         if (!$$17.isEmpty()) {
/* 548 */           RenderType $$18 = ItemBlockRenderTypes.getRenderLayer($$17);
/* 549 */           BufferBuilder $$19 = $$3.builder($$18);
/* 550 */           if (referenceArraySet.add($$18)) {
/* 551 */             SectionRenderDispatcher.RenderSection.this.beginLayer($$19);
/*     */           }
/* 553 */           $$13.renderLiquid($$14, $$9, (VertexConsumer)$$19, $$15, $$17);
/*     */         } 
/*     */         
/* 556 */         if ($$15.getRenderShape() != RenderShape.INVISIBLE) {
/* 557 */           RenderType $$20 = ItemBlockRenderTypes.getChunkRenderType($$15);
/* 558 */           BufferBuilder $$21 = $$3.builder($$20);
/* 559 */           if (referenceArraySet.add($$20)) {
/* 560 */             SectionRenderDispatcher.RenderSection.this.beginLayer($$21);
/*     */           }
/*     */           
/* 563 */           $$10.pushPose();
/* 564 */           $$10.translate(($$14.getX() & 0xF), ($$14.getY() & 0xF), ($$14.getZ() & 0xF));
/* 565 */           $$13.renderBatched($$15, $$14, $$9, $$10, (VertexConsumer)$$21, true, $$12);
/* 566 */           $$10.popPose();
/*     */         } 
/*     */       } 
/*     */       
/* 570 */       if (referenceArraySet.contains(RenderType.translucent())) {
/* 571 */         BufferBuilder $$22 = $$3.builder(RenderType.translucent());
/* 572 */         if (!$$22.isCurrentBatchEmpty()) {
/* 573 */           $$22.setQuadSorting(VertexSorting.byDistance($$0 - $$6.getX(), $$1 - $$6.getY(), $$2 - $$6.getZ()));
/* 574 */           $$4.transparencyState = $$22.getSortState();
/*     */         } 
/*     */       } 
/*     */       
/* 578 */       for (RenderType $$23 : referenceArraySet) {
/* 579 */         BufferBuilder.RenderedBuffer $$24 = $$3.builder($$23).endOrDiscardIfEmpty();
/* 580 */         if ($$24 != null) {
/* 581 */           $$4.renderedLayers.put($$23, $$24);
/*     */         }
/*     */       } 
/*     */       
/* 585 */       ModelBlockRenderer.clearCache();
/*     */     } 
/*     */     
/* 588 */     $$4.visibilitySet = $$8.resolve();
/*     */     
/* 590 */     return $$4;
/*     */   }
/*     */   
/*     */   private <E extends BlockEntity> void handleBlockEntity(CompileResults $$0, E $$1) {
/* 594 */     BlockEntityRenderer<E> $$2 = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer((BlockEntity)$$1);
/* 595 */     if ($$2 != null) {
/* 596 */       $$0.blockEntities.add((BlockEntity)$$1);
/* 597 */       if ($$2.shouldRenderOffScreen((BlockEntity)$$1)) {
/* 598 */         $$0.globalBlockEntities.add((BlockEntity)$$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 605 */     this.region = null;
/* 606 */     if (this.isCancelled.compareAndSet(false, true))
/* 607 */       SectionRenderDispatcher.RenderSection.this.setDirty(false); 
/*     */   }
/*     */   
/*     */   private static final class CompileResults
/*     */   {
/* 612 */     public final List<BlockEntity> globalBlockEntities = new ArrayList<>();
/* 613 */     public final List<BlockEntity> blockEntities = new ArrayList<>();
/* 614 */     public final Map<RenderType, BufferBuilder.RenderedBuffer> renderedLayers = (Map<RenderType, BufferBuilder.RenderedBuffer>)new Reference2ObjectArrayMap();
/* 615 */     public VisibilitySet visibilitySet = new VisibilitySet();
/*     */     @Nullable
/*     */     public BufferBuilder.SortState transparencyState;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\chunk\SectionRenderDispatcher$RenderSection$RebuildTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */