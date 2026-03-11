/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.StriderModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Strider;
/*    */ 
/*    */ public class StriderRenderer extends MobRenderer<Strider, StriderModel<Strider>> {
/* 11 */   private static final ResourceLocation STRIDER_LOCATION = new ResourceLocation("textures/entity/strider/strider.png");
/* 12 */   private static final ResourceLocation COLD_LOCATION = new ResourceLocation("textures/entity/strider/strider_cold.png");
/*    */   
/*    */   public StriderRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new StriderModel($$0.bakeLayer(ModelLayers.STRIDER)), 0.5F);
/*    */     
/* 17 */     addLayer((RenderLayer<Strider, StriderModel<Strider>>)new SaddleLayer(this, (EntityModel)new StriderModel($$0.bakeLayer(ModelLayers.STRIDER_SADDLE)), new ResourceLocation("textures/entity/strider/strider_saddle.png")));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Strider $$0) {
/* 22 */     return $$0.isSuffocating() ? COLD_LOCATION : STRIDER_LOCATION;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void scale(Strider $$0, PoseStack $$1, float $$2) {
/* 28 */     if ($$0.isBaby()) {
/* 29 */       $$1.scale(0.5F, 0.5F, 0.5F);
/* 30 */       this.shadowRadius = 0.25F;
/*    */     } else {
/* 32 */       this.shadowRadius = 0.5F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isShaking(Strider $$0) {
/* 38 */     return (super.isShaking($$0) || $$0.isSuffocating());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\StriderRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */