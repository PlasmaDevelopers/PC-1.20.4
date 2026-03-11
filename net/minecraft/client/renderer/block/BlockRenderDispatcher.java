/*     */ package net.minecraft.client.renderer.block;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.client.color.block.BlockColors;
/*     */ import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
/*     */ import net.minecraft.client.renderer.ItemBlockRenderTypes;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.ItemDisplayContext;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ 
/*     */ public class BlockRenderDispatcher implements ResourceManagerReloadListener {
/*     */   private final BlockModelShaper blockModelShaper;
/*     */   private final ModelBlockRenderer modelRenderer;
/*     */   private final BlockEntityWithoutLevelRenderer blockEntityRenderer;
/*     */   private final LiquidBlockRenderer liquidBlockRenderer;
/*  31 */   private final RandomSource random = RandomSource.create();
/*     */   private final BlockColors blockColors;
/*     */   
/*     */   public BlockRenderDispatcher(BlockModelShaper $$0, BlockEntityWithoutLevelRenderer $$1, BlockColors $$2) {
/*  35 */     this.blockModelShaper = $$0;
/*  36 */     this.blockEntityRenderer = $$1;
/*  37 */     this.blockColors = $$2;
/*  38 */     this.modelRenderer = new ModelBlockRenderer(this.blockColors);
/*  39 */     this.liquidBlockRenderer = new LiquidBlockRenderer();
/*     */   }
/*     */   
/*     */   public BlockModelShaper getBlockModelShaper() {
/*  43 */     return this.blockModelShaper;
/*     */   }
/*     */   
/*     */   public void renderBreakingTexture(BlockState $$0, BlockPos $$1, BlockAndTintGetter $$2, PoseStack $$3, VertexConsumer $$4) {
/*  47 */     if ($$0.getRenderShape() != RenderShape.MODEL) {
/*     */       return;
/*     */     }
/*     */     
/*  51 */     BakedModel $$5 = this.blockModelShaper.getBlockModel($$0);
/*  52 */     long $$6 = $$0.getSeed($$1);
/*  53 */     this.modelRenderer.tesselateBlock($$2, $$5, $$0, $$1, $$3, $$4, true, this.random, $$6, OverlayTexture.NO_OVERLAY);
/*     */   }
/*     */   
/*     */   public void renderBatched(BlockState $$0, BlockPos $$1, BlockAndTintGetter $$2, PoseStack $$3, VertexConsumer $$4, boolean $$5, RandomSource $$6) {
/*     */     try {
/*  58 */       RenderShape $$7 = $$0.getRenderShape();
/*  59 */       if ($$7 == RenderShape.MODEL) {
/*  60 */         this.modelRenderer.tesselateBlock($$2, getBlockModel($$0), $$0, $$1, $$3, $$4, $$5, $$6, $$0.getSeed($$1), OverlayTexture.NO_OVERLAY);
/*     */       }
/*  62 */     } catch (Throwable $$8) {
/*  63 */       CrashReport $$9 = CrashReport.forThrowable($$8, "Tesselating block in world");
/*  64 */       CrashReportCategory $$10 = $$9.addCategory("Block being tesselated");
/*     */       
/*  66 */       CrashReportCategory.populateBlockDetails($$10, (LevelHeightAccessor)$$2, $$1, $$0);
/*     */       
/*  68 */       throw new ReportedException($$9);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderLiquid(BlockPos $$0, BlockAndTintGetter $$1, VertexConsumer $$2, BlockState $$3, FluidState $$4) {
/*     */     try {
/*  74 */       this.liquidBlockRenderer.tesselate($$1, $$0, $$2, $$3, $$4);
/*  75 */     } catch (Throwable $$5) {
/*  76 */       CrashReport $$6 = CrashReport.forThrowable($$5, "Tesselating liquid in world");
/*  77 */       CrashReportCategory $$7 = $$6.addCategory("Block being tesselated");
/*     */       
/*  79 */       CrashReportCategory.populateBlockDetails($$7, (LevelHeightAccessor)$$1, $$0, null);
/*     */       
/*  81 */       throw new ReportedException($$6);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ModelBlockRenderer getModelRenderer() {
/*  86 */     return this.modelRenderer;
/*     */   }
/*     */   
/*     */   public BakedModel getBlockModel(BlockState $$0) {
/*  90 */     return this.blockModelShaper.getBlockModel($$0);
/*     */   } public void renderSingleBlock(BlockState $$0, PoseStack $$1, MultiBufferSource $$2, int $$3, int $$4) {
/*     */     BakedModel $$6;
/*     */     int $$7;
/*     */     float $$8, $$9, $$10;
/*  95 */     RenderShape $$5 = $$0.getRenderShape();
/*  96 */     if ($$5 == RenderShape.INVISIBLE) {
/*     */       return;
/*     */     }
/*     */     
/* 100 */     switch ($$5) {
/*     */       case MODEL:
/* 102 */         $$6 = getBlockModel($$0);
/*     */         
/* 104 */         $$7 = this.blockColors.getColor($$0, null, null, 0);
/* 105 */         $$8 = ($$7 >> 16 & 0xFF) / 255.0F;
/* 106 */         $$9 = ($$7 >> 8 & 0xFF) / 255.0F;
/* 107 */         $$10 = ($$7 & 0xFF) / 255.0F;
/*     */         
/* 109 */         this.modelRenderer.renderModel($$1.last(), $$2.getBuffer(ItemBlockRenderTypes.getRenderType($$0, false)), $$0, $$6, $$8, $$9, $$10, $$3, $$4);
/*     */         break;
/*     */       case ENTITYBLOCK_ANIMATED:
/* 112 */         this.blockEntityRenderer.renderByItem(new ItemStack((ItemLike)$$0.getBlock()), ItemDisplayContext.NONE, $$1, $$2, $$3, $$4);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(ResourceManager $$0) {
/* 119 */     this.liquidBlockRenderer.setupSprites();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\BlockRenderDispatcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */