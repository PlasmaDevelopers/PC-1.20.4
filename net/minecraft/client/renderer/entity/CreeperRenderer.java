/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.CreeperModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Creeper;
/*    */ 
/*    */ public class CreeperRenderer extends MobRenderer<Creeper, CreeperModel<Creeper>> {
/* 12 */   private static final ResourceLocation CREEPER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper.png");
/*    */   
/*    */   public CreeperRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new CreeperModel($$0.bakeLayer(ModelLayers.CREEPER)), 0.5F);
/*    */     
/* 17 */     addLayer((RenderLayer<Creeper, CreeperModel<Creeper>>)new CreeperPowerLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(Creeper $$0, PoseStack $$1, float $$2) {
/* 22 */     float $$3 = $$0.getSwelling($$2);
/*    */     
/* 24 */     float $$4 = 1.0F + Mth.sin($$3 * 100.0F) * $$3 * 0.01F;
/* 25 */     $$3 = Mth.clamp($$3, 0.0F, 1.0F);
/* 26 */     $$3 *= $$3;
/* 27 */     $$3 *= $$3;
/* 28 */     float $$5 = (1.0F + $$3 * 0.4F) * $$4;
/* 29 */     float $$6 = (1.0F + $$3 * 0.1F) / $$4;
/* 30 */     $$1.scale($$5, $$6, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getWhiteOverlayProgress(Creeper $$0, float $$1) {
/* 35 */     float $$2 = $$0.getSwelling($$1);
/*    */     
/* 37 */     if ((int)($$2 * 10.0F) % 2 == 0) {
/* 38 */       return 0.0F;
/*    */     }
/*    */     
/* 41 */     return Mth.clamp($$2, 0.5F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Creeper $$0) {
/* 46 */     return CREEPER_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\CreeperRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */