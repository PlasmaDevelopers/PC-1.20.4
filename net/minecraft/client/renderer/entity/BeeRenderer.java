/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.BeeModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Bee;
/*    */ 
/*    */ public class BeeRenderer extends MobRenderer<Bee, BeeModel<Bee>> {
/*  9 */   private static final ResourceLocation ANGRY_BEE_TEXTURE = new ResourceLocation("textures/entity/bee/bee_angry.png");
/* 10 */   private static final ResourceLocation ANGRY_NECTAR_BEE_TEXTURE = new ResourceLocation("textures/entity/bee/bee_angry_nectar.png");
/* 11 */   private static final ResourceLocation BEE_TEXTURE = new ResourceLocation("textures/entity/bee/bee.png");
/* 12 */   private static final ResourceLocation NECTAR_BEE_TEXTURE = new ResourceLocation("textures/entity/bee/bee_nectar.png");
/*    */   
/*    */   public BeeRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new BeeModel($$0.bakeLayer(ModelLayers.BEE)), 0.4F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Bee $$0) {
/* 20 */     if ($$0.isAngry()) {
/* 21 */       if ($$0.hasNectar()) {
/* 22 */         return ANGRY_NECTAR_BEE_TEXTURE;
/*    */       }
/* 24 */       return ANGRY_BEE_TEXTURE;
/* 25 */     }  if ($$0.hasNectar()) {
/* 26 */       return NECTAR_BEE_TEXTURE;
/*    */     }
/* 28 */     return BEE_TEXTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\BeeRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */