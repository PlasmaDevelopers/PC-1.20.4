/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*    */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*    */ import net.minecraft.client.resources.model.ModelManager;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface EntityRendererProvider<T extends net.minecraft.world.entity.Entity>
/*    */ {
/*    */   EntityRenderer<T> create(Context paramContext);
/*    */   
/*    */   public static class Context
/*    */   {
/*    */     private final EntityRenderDispatcher entityRenderDispatcher;
/*    */     private final ItemRenderer itemRenderer;
/*    */     private final BlockRenderDispatcher blockRenderDispatcher;
/*    */     
/*    */     public Context(EntityRenderDispatcher $$0, ItemRenderer $$1, BlockRenderDispatcher $$2, ItemInHandRenderer $$3, ResourceManager $$4, EntityModelSet $$5, Font $$6) {
/* 25 */       this.entityRenderDispatcher = $$0;
/* 26 */       this.itemRenderer = $$1;
/* 27 */       this.blockRenderDispatcher = $$2;
/* 28 */       this.itemInHandRenderer = $$3;
/* 29 */       this.resourceManager = $$4;
/* 30 */       this.modelSet = $$5;
/* 31 */       this.font = $$6;
/*    */     }
/*    */     private final ItemInHandRenderer itemInHandRenderer; private final ResourceManager resourceManager; private final EntityModelSet modelSet; private final Font font;
/*    */     public EntityRenderDispatcher getEntityRenderDispatcher() {
/* 35 */       return this.entityRenderDispatcher;
/*    */     }
/*    */     
/*    */     public ItemRenderer getItemRenderer() {
/* 39 */       return this.itemRenderer;
/*    */     }
/*    */     
/*    */     public BlockRenderDispatcher getBlockRenderDispatcher() {
/* 43 */       return this.blockRenderDispatcher;
/*    */     }
/*    */     
/*    */     public ItemInHandRenderer getItemInHandRenderer() {
/* 47 */       return this.itemInHandRenderer;
/*    */     }
/*    */     
/*    */     public ResourceManager getResourceManager() {
/* 51 */       return this.resourceManager;
/*    */     }
/*    */     
/*    */     public EntityModelSet getModelSet() {
/* 55 */       return this.modelSet;
/*    */     }
/*    */     
/*    */     public ModelManager getModelManager() {
/* 59 */       return this.blockRenderDispatcher.getBlockModelShaper().getModelManager();
/*    */     }
/*    */     
/*    */     public ModelPart bakeLayer(ModelLayerLocation $$0) {
/* 63 */       return this.modelSet.bakeLayer($$0);
/*    */     }
/*    */     
/*    */     public Font getFont() {
/* 67 */       return this.font;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EntityRendererProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */