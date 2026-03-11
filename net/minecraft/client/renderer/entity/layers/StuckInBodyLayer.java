/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.PlayerModel;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public abstract class StuckInBodyLayer<T extends LivingEntity, M extends PlayerModel<T>> extends RenderLayer<T, M> {
/*    */   public StuckInBodyLayer(LivingEntityRenderer<T, M> $$0) {
/* 15 */     super((RenderLayerParent<T, M>)$$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 24 */     int $$10 = numStuck($$3);
/* 25 */     RandomSource $$11 = RandomSource.create($$3.getId());
/*    */     
/* 27 */     if ($$10 <= 0) {
/*    */       return;
/*    */     }
/*    */     
/* 31 */     for (int $$12 = 0; $$12 < $$10; $$12++) {
/* 32 */       $$0.pushPose();
/* 33 */       ModelPart $$13 = ((PlayerModel)getParentModel()).getRandomModelPart($$11);
/* 34 */       ModelPart.Cube $$14 = $$13.getRandomCube($$11);
/* 35 */       $$13.translateAndRotate($$0);
/* 36 */       float $$15 = $$11.nextFloat();
/* 37 */       float $$16 = $$11.nextFloat();
/* 38 */       float $$17 = $$11.nextFloat();
/* 39 */       float $$18 = Mth.lerp($$15, $$14.minX, $$14.maxX) / 16.0F;
/* 40 */       float $$19 = Mth.lerp($$16, $$14.minY, $$14.maxY) / 16.0F;
/* 41 */       float $$20 = Mth.lerp($$17, $$14.minZ, $$14.maxZ) / 16.0F;
/* 42 */       $$0.translate($$18, $$19, $$20);
/* 43 */       $$15 = -1.0F * ($$15 * 2.0F - 1.0F);
/* 44 */       $$16 = -1.0F * ($$16 * 2.0F - 1.0F);
/* 45 */       $$17 = -1.0F * ($$17 * 2.0F - 1.0F);
/*    */       
/* 47 */       renderStuckItem($$0, $$1, $$2, (Entity)$$3, $$15, $$16, $$17, $$6);
/*    */       
/* 49 */       $$0.popPose();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract int numStuck(T paramT);
/*    */   
/*    */   protected abstract void renderStuckItem(PoseStack paramPoseStack, MultiBufferSource paramMultiBufferSource, int paramInt, Entity paramEntity, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\StuckInBodyLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */