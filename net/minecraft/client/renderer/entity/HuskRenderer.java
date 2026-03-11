/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Zombie;
/*    */ 
/*    */ public class HuskRenderer extends ZombieRenderer {
/*  9 */   private static final ResourceLocation HUSK_LOCATION = new ResourceLocation("textures/entity/zombie/husk.png");
/*    */   
/*    */   public HuskRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, ModelLayers.HUSK, ModelLayers.HUSK_INNER_ARMOR, ModelLayers.HUSK_OUTER_ARMOR);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(Zombie $$0, PoseStack $$1, float $$2) {
/* 17 */     float $$3 = 1.0625F;
/* 18 */     $$1.scale(1.0625F, 1.0625F, 1.0625F);
/* 19 */     super.scale($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Zombie $$0) {
/* 24 */     return HUSK_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\HuskRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */