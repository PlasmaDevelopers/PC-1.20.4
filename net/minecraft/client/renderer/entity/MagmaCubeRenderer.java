/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.LavaSlimeModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.monster.MagmaCube;
/*    */ 
/*    */ public class MagmaCubeRenderer extends MobRenderer<MagmaCube, LavaSlimeModel<MagmaCube>> {
/* 14 */   private static final ResourceLocation MAGMACUBE_LOCATION = new ResourceLocation("textures/entity/slime/magmacube.png");
/*    */   
/*    */   public MagmaCubeRenderer(EntityRendererProvider.Context $$0) {
/* 17 */     super($$0, new LavaSlimeModel($$0.bakeLayer(ModelLayers.MAGMA_CUBE)), 0.25F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(MagmaCube $$0, BlockPos $$1) {
/* 22 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(MagmaCube $$0) {
/* 27 */     return MAGMACUBE_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(MagmaCube $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 32 */     this.shadowRadius = 0.25F * $$0.getSize();
/* 33 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(MagmaCube $$0, PoseStack $$1, float $$2) {
/* 38 */     int $$3 = $$0.getSize();
/* 39 */     float $$4 = Mth.lerp($$2, $$0.oSquish, $$0.squish) / ($$3 * 0.5F + 1.0F);
/* 40 */     float $$5 = 1.0F / ($$4 + 1.0F);
/* 41 */     $$1.scale($$5 * $$3, 1.0F / $$5 * $$3, $$5 * $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\MagmaCubeRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */