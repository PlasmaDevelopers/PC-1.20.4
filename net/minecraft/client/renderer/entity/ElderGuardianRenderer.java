/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.ElderGuardian;
/*    */ import net.minecraft.world.entity.monster.Guardian;
/*    */ 
/*    */ public class ElderGuardianRenderer extends GuardianRenderer {
/* 10 */   public static final ResourceLocation GUARDIAN_ELDER_LOCATION = new ResourceLocation("textures/entity/guardian_elder.png");
/*    */   
/*    */   public ElderGuardianRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, 1.2F, ModelLayers.ELDER_GUARDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(Guardian $$0, PoseStack $$1, float $$2) {
/* 18 */     $$1.scale(ElderGuardian.ELDER_SIZE_SCALE, ElderGuardian.ELDER_SIZE_SCALE, ElderGuardian.ELDER_SIZE_SCALE);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Guardian $$0) {
/* 23 */     return GUARDIAN_ELDER_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ElderGuardianRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */