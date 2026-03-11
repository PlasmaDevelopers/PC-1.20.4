/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.GhastModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Ghast;
/*    */ 
/*    */ public class GhastRenderer extends MobRenderer<Ghast, GhastModel<Ghast>> {
/* 10 */   private static final ResourceLocation GHAST_LOCATION = new ResourceLocation("textures/entity/ghast/ghast.png");
/* 11 */   private static final ResourceLocation GHAST_SHOOTING_LOCATION = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
/*    */   
/*    */   public GhastRenderer(EntityRendererProvider.Context $$0) {
/* 14 */     super($$0, new GhastModel($$0.bakeLayer(ModelLayers.GHAST)), 1.5F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Ghast $$0) {
/* 19 */     if ($$0.isCharging()) {
/* 20 */       return GHAST_SHOOTING_LOCATION;
/*    */     }
/*    */     
/* 23 */     return GHAST_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(Ghast $$0, PoseStack $$1, float $$2) {
/* 28 */     float $$3 = 1.0F;
/* 29 */     float $$4 = 4.5F;
/* 30 */     float $$5 = 4.5F;
/* 31 */     $$1.scale(4.5F, 4.5F, 4.5F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\GhastRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */