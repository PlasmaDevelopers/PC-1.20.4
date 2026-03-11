/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ 
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*    */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface BlockEntityRendererProvider<T extends net.minecraft.world.level.block.entity.BlockEntity>
/*    */ {
/*    */   BlockEntityRenderer<T> create(Context paramContext);
/*    */   
/*    */   public static class Context
/*    */   {
/*    */     private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
/*    */     private final BlockRenderDispatcher blockRenderDispatcher;
/*    */     private final ItemRenderer itemRenderer;
/*    */     
/*    */     public Context(BlockEntityRenderDispatcher $$0, BlockRenderDispatcher $$1, ItemRenderer $$2, EntityRenderDispatcher $$3, EntityModelSet $$4, Font $$5) {
/* 23 */       this.blockEntityRenderDispatcher = $$0;
/* 24 */       this.blockRenderDispatcher = $$1;
/* 25 */       this.itemRenderer = $$2;
/* 26 */       this.entityRenderer = $$3;
/* 27 */       this.modelSet = $$4;
/* 28 */       this.font = $$5;
/*    */     }
/*    */     private final EntityRenderDispatcher entityRenderer; private final EntityModelSet modelSet; private final Font font;
/*    */     public BlockEntityRenderDispatcher getBlockEntityRenderDispatcher() {
/* 32 */       return this.blockEntityRenderDispatcher;
/*    */     }
/*    */     
/*    */     public BlockRenderDispatcher getBlockRenderDispatcher() {
/* 36 */       return this.blockRenderDispatcher;
/*    */     }
/*    */     
/*    */     public EntityRenderDispatcher getEntityRenderer() {
/* 40 */       return this.entityRenderer;
/*    */     }
/*    */     
/*    */     public ItemRenderer getItemRenderer() {
/* 44 */       return this.itemRenderer;
/*    */     }
/*    */     
/*    */     public EntityModelSet getModelSet() {
/* 48 */       return this.modelSet;
/*    */     }
/*    */     
/*    */     public ModelPart bakeLayer(ModelLayerLocation $$0) {
/* 52 */       return this.modelSet.bakeLayer($$0);
/*    */     }
/*    */     
/*    */     public Font getFont() {
/* 56 */       return this.font;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BlockEntityRendererProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */