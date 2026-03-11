/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ParrotModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Parrot;
/*    */ 
/*    */ public class ParrotRenderer extends MobRenderer<Parrot, ParrotModel> {
/* 10 */   private static final ResourceLocation RED_BLUE = new ResourceLocation("textures/entity/parrot/parrot_red_blue.png");
/* 11 */   private static final ResourceLocation BLUE = new ResourceLocation("textures/entity/parrot/parrot_blue.png");
/* 12 */   private static final ResourceLocation GREEN = new ResourceLocation("textures/entity/parrot/parrot_green.png");
/* 13 */   private static final ResourceLocation YELLOW_BLUE = new ResourceLocation("textures/entity/parrot/parrot_yellow_blue.png");
/* 14 */   private static final ResourceLocation GREY = new ResourceLocation("textures/entity/parrot/parrot_grey.png");
/*    */   
/*    */   public ParrotRenderer(EntityRendererProvider.Context $$0) {
/* 17 */     super($$0, new ParrotModel($$0.bakeLayer(ModelLayers.PARROT)), 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Parrot $$0) {
/* 22 */     return getVariantTexture($$0.getVariant());
/*    */   }
/*    */   
/*    */   public static ResourceLocation getVariantTexture(Parrot.Variant $$0) {
/* 26 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case RED_BLUE: case BLUE: case GREEN: case YELLOW_BLUE: case GRAY: break; }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 31 */       GREY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getBob(Parrot $$0, float $$1) {
/* 37 */     float $$2 = Mth.lerp($$1, $$0.oFlap, $$0.flap);
/* 38 */     float $$3 = Mth.lerp($$1, $$0.oFlapSpeed, $$0.flapSpeed);
/*    */     
/* 40 */     return (Mth.sin($$2) + 1.0F) * $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ParrotRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */