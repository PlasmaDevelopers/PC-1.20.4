/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.client.model.RabbitModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Rabbit;
/*    */ 
/*    */ public class RabbitRenderer extends MobRenderer<Rabbit, RabbitModel<Rabbit>> {
/* 10 */   private static final ResourceLocation RABBIT_BROWN_LOCATION = new ResourceLocation("textures/entity/rabbit/brown.png");
/* 11 */   private static final ResourceLocation RABBIT_WHITE_LOCATION = new ResourceLocation("textures/entity/rabbit/white.png");
/* 12 */   private static final ResourceLocation RABBIT_BLACK_LOCATION = new ResourceLocation("textures/entity/rabbit/black.png");
/* 13 */   private static final ResourceLocation RABBIT_GOLD_LOCATION = new ResourceLocation("textures/entity/rabbit/gold.png");
/* 14 */   private static final ResourceLocation RABBIT_SALT_LOCATION = new ResourceLocation("textures/entity/rabbit/salt.png");
/* 15 */   private static final ResourceLocation RABBIT_WHITE_SPLOTCHED_LOCATION = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
/* 16 */   private static final ResourceLocation RABBIT_TOAST_LOCATION = new ResourceLocation("textures/entity/rabbit/toast.png");
/* 17 */   private static final ResourceLocation RABBIT_EVIL_LOCATION = new ResourceLocation("textures/entity/rabbit/caerbannog.png");
/*    */   
/*    */   public RabbitRenderer(EntityRendererProvider.Context $$0) {
/* 20 */     super($$0, new RabbitModel($$0.bakeLayer(ModelLayers.RABBIT)), 0.3F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Rabbit $$0) {
/* 25 */     String $$1 = ChatFormatting.stripFormatting($$0.getName().getString());
/* 26 */     if ("Toast".equals($$1)) {
/* 27 */       return RABBIT_TOAST_LOCATION;
/*    */     }
/*    */     
/* 30 */     switch ($$0.getVariant()) { default: throw new IncompatibleClassChangeError();case BROWN: case WHITE: case BLACK: case GOLD: case SALT: case WHITE_SPLOTCHED: case EVIL: break; }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 37 */       RABBIT_EVIL_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\RabbitRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */