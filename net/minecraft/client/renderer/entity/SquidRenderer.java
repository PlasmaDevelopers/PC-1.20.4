/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.SquidModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Squid;
/*    */ 
/*    */ public class SquidRenderer<T extends Squid> extends MobRenderer<T, SquidModel<T>> {
/* 11 */   private static final ResourceLocation SQUID_LOCATION = new ResourceLocation("textures/entity/squid/squid.png");
/*    */   
/*    */   public SquidRenderer(EntityRendererProvider.Context $$0, SquidModel<T> $$1) {
/* 14 */     super($$0, $$1, 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(T $$0) {
/* 19 */     return SQUID_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(T $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 24 */     float $$5 = Mth.lerp($$4, ((Squid)$$0).xBodyRotO, ((Squid)$$0).xBodyRot);
/* 25 */     float $$6 = Mth.lerp($$4, ((Squid)$$0).zBodyRotO, ((Squid)$$0).zBodyRot);
/*    */     
/* 27 */     $$1.translate(0.0F, 0.5F, 0.0F);
/* 28 */     $$1.mulPose(Axis.YP.rotationDegrees(180.0F - $$3));
/* 29 */     $$1.mulPose(Axis.XP.rotationDegrees($$5));
/* 30 */     $$1.mulPose(Axis.YP.rotationDegrees($$6));
/* 31 */     $$1.translate(0.0F, -1.2F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getBob(T $$0, float $$1) {
/* 36 */     return Mth.lerp($$1, ((Squid)$$0).oldTentacleAngle, ((Squid)$$0).tentacleAngle);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SquidRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */