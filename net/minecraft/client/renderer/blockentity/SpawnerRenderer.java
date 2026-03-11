/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.BaseSpawner;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
/*    */ 
/*    */ public class SpawnerRenderer
/*    */   implements BlockEntityRenderer<SpawnerBlockEntity> {
/*    */   public SpawnerRenderer(BlockEntityRendererProvider.Context $$0) {
/* 17 */     this.entityRenderer = $$0.getEntityRenderer();
/*    */   }
/*    */   private final EntityRenderDispatcher entityRenderer;
/*    */   
/*    */   public void render(SpawnerBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 22 */     Level $$6 = $$0.getLevel();
/* 23 */     if ($$6 == null) {
/*    */       return;
/*    */     }
/* 26 */     BaseSpawner $$7 = $$0.getSpawner();
/* 27 */     Entity $$8 = $$7.getOrCreateDisplayEntity($$6, $$0.getBlockPos());
/* 28 */     if ($$8 != null) {
/* 29 */       renderEntityInSpawner($$1, $$2, $$3, $$4, $$8, this.entityRenderer, $$7.getoSpin(), $$7.getSpin());
/*    */     }
/*    */   }
/*    */   
/*    */   public static void renderEntityInSpawner(float $$0, PoseStack $$1, MultiBufferSource $$2, int $$3, Entity $$4, EntityRenderDispatcher $$5, double $$6, double $$7) {
/* 34 */     $$1.pushPose();
/* 35 */     $$1.translate(0.5F, 0.0F, 0.5F);
/* 36 */     float $$8 = 0.53125F;
/* 37 */     float $$9 = Math.max($$4.getBbWidth(), $$4.getBbHeight());
/* 38 */     if ($$9 > 1.0D) {
/* 39 */       $$8 /= $$9;
/*    */     }
/*    */     
/* 42 */     $$1.translate(0.0F, 0.4F, 0.0F);
/* 43 */     $$1.mulPose(Axis.YP.rotationDegrees((float)Mth.lerp($$0, $$6, $$7) * 10.0F));
/* 44 */     $$1.translate(0.0F, -0.2F, 0.0F);
/* 45 */     $$1.mulPose(Axis.XP.rotationDegrees(-30.0F));
/* 46 */     $$1.scale($$8, $$8, $$8);
/* 47 */     $$5.render($$4, 0.0D, 0.0D, 0.0D, 0.0F, $$0, $$1, $$2, $$3);
/* 48 */     $$1.popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\SpawnerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */