/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ChickenModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Chicken;
/*    */ 
/*    */ public class ChickenRenderer extends MobRenderer<Chicken, ChickenModel<Chicken>> {
/* 10 */   private static final ResourceLocation CHICKEN_LOCATION = new ResourceLocation("textures/entity/chicken.png");
/*    */   
/*    */   public ChickenRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, new ChickenModel($$0.bakeLayer(ModelLayers.CHICKEN)), 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Chicken $$0) {
/* 18 */     return CHICKEN_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getBob(Chicken $$0, float $$1) {
/* 23 */     float $$2 = Mth.lerp($$1, $$0.oFlap, $$0.flap);
/* 24 */     float $$3 = Mth.lerp($$1, $$0.oFlapSpeed, $$0.flapSpeed);
/*    */     
/* 26 */     return (Mth.sin($$2) + 1.0F) * $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ChickenRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */