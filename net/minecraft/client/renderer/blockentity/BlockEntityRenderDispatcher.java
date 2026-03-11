/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.model.geom.EntityModelSet;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*     */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ 
/*     */ public class BlockEntityRenderDispatcher
/*     */   implements ResourceManagerReloadListener {
/*  30 */   private Map<BlockEntityType<?>, BlockEntityRenderer<?>> renderers = (Map<BlockEntityType<?>, BlockEntityRenderer<?>>)ImmutableMap.of();
/*     */   
/*     */   private final Font font;
/*     */   
/*     */   private final EntityModelSet entityModelSet;
/*     */   public Level level;
/*     */   public Camera camera;
/*     */   public HitResult cameraHitResult;
/*     */   private final Supplier<BlockRenderDispatcher> blockRenderDispatcher;
/*     */   private final Supplier<ItemRenderer> itemRenderer;
/*     */   private final Supplier<EntityRenderDispatcher> entityRenderer;
/*     */   
/*     */   public BlockEntityRenderDispatcher(Font $$0, EntityModelSet $$1, Supplier<BlockRenderDispatcher> $$2, Supplier<ItemRenderer> $$3, Supplier<EntityRenderDispatcher> $$4) {
/*  43 */     this.itemRenderer = $$3;
/*  44 */     this.entityRenderer = $$4;
/*  45 */     this.font = $$0;
/*  46 */     this.entityModelSet = $$1;
/*  47 */     this.blockRenderDispatcher = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <E extends BlockEntity> BlockEntityRenderer<E> getRenderer(E $$0) {
/*  53 */     return (BlockEntityRenderer<E>)this.renderers.get($$0.getType());
/*     */   }
/*     */   
/*     */   public void prepare(Level $$0, Camera $$1, HitResult $$2) {
/*  57 */     if (this.level != $$0) {
/*  58 */       setLevel($$0);
/*     */     }
/*  60 */     this.camera = $$1;
/*  61 */     this.cameraHitResult = $$2;
/*     */   }
/*     */   
/*     */   public <E extends BlockEntity> void render(E $$0, float $$1, PoseStack $$2, MultiBufferSource $$3) {
/*  65 */     BlockEntityRenderer<E> $$4 = getRenderer($$0);
/*  66 */     if ($$4 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (!$$0.hasLevel() || !$$0.getType().isValid($$0.getBlockState())) {
/*     */       return;
/*     */     }
/*     */     
/*  75 */     if (!$$4.shouldRender($$0, this.camera.getPosition())) {
/*     */       return;
/*     */     }
/*     */     
/*  79 */     tryRender((BlockEntity)$$0, () -> setupAndRender($$0, $$1, $$2, $$3, $$4));
/*     */   }
/*     */   
/*     */   private static <T extends BlockEntity> void setupAndRender(BlockEntityRenderer<T> $$0, T $$1, float $$2, PoseStack $$3, MultiBufferSource $$4) {
/*     */     int $$7;
/*  84 */     Level $$5 = $$1.getLevel();
/*  85 */     if ($$5 != null) {
/*  86 */       int $$6 = LevelRenderer.getLightColor((BlockAndTintGetter)$$5, $$1.getBlockPos());
/*     */     } else {
/*  88 */       $$7 = 15728880;
/*     */     } 
/*  90 */     $$0.render($$1, $$2, $$3, $$4, $$7, OverlayTexture.NO_OVERLAY);
/*     */   }
/*     */   
/*     */   public <E extends BlockEntity> boolean renderItem(E $$0, PoseStack $$1, MultiBufferSource $$2, int $$3, int $$4) {
/*  94 */     BlockEntityRenderer<E> $$5 = getRenderer($$0);
/*  95 */     if ($$5 == null) {
/*  96 */       return true;
/*     */     }
/*  98 */     tryRender((BlockEntity)$$0, () -> $$0.render($$1, 0.0F, $$2, $$3, $$4, $$5));
/*  99 */     return false;
/*     */   }
/*     */   
/*     */   private static void tryRender(BlockEntity $$0, Runnable $$1) {
/*     */     try {
/* 104 */       $$1.run();
/* 105 */     } catch (Throwable $$2) {
/* 106 */       CrashReport $$3 = CrashReport.forThrowable($$2, "Rendering Block Entity");
/* 107 */       CrashReportCategory $$4 = $$3.addCategory("Block Entity Details");
/*     */       
/* 109 */       $$0.fillCrashReportCategory($$4);
/*     */       
/* 111 */       throw new ReportedException($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setLevel(@Nullable Level $$0) {
/* 116 */     this.level = $$0;
/* 117 */     if ($$0 == null) {
/* 118 */       this.camera = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(ResourceManager $$0) {
/* 124 */     BlockEntityRendererProvider.Context $$1 = new BlockEntityRendererProvider.Context(this, this.blockRenderDispatcher.get(), this.itemRenderer.get(), this.entityRenderer.get(), this.entityModelSet, this.font);
/* 125 */     this.renderers = BlockEntityRenderers.createEntityRenderers($$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BlockEntityRenderDispatcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */