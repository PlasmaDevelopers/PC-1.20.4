/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.WitherBossModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.WitherArmorLayer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.boss.wither.WitherBoss;
/*    */ 
/*    */ public class WitherBossRenderer extends MobRenderer<WitherBoss, WitherBossModel<WitherBoss>> {
/* 14 */   private static final ResourceLocation WITHER_INVULNERABLE_LOCATION = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
/* 15 */   private static final ResourceLocation WITHER_LOCATION = new ResourceLocation("textures/entity/wither/wither.png");
/*    */   
/*    */   public WitherBossRenderer(EntityRendererProvider.Context $$0) {
/* 18 */     super($$0, new WitherBossModel($$0.bakeLayer(ModelLayers.WITHER)), 1.0F);
/*    */     
/* 20 */     addLayer((RenderLayer<WitherBoss, WitherBossModel<WitherBoss>>)new WitherArmorLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(WitherBoss $$0, BlockPos $$1) {
/* 25 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(WitherBoss $$0) {
/* 30 */     int $$1 = $$0.getInvulnerableTicks();
/* 31 */     if ($$1 <= 0 || ($$1 <= 80 && $$1 / 5 % 2 == 1)) {
/* 32 */       return WITHER_LOCATION;
/*    */     }
/* 34 */     return WITHER_INVULNERABLE_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(WitherBoss $$0, PoseStack $$1, float $$2) {
/* 39 */     float $$3 = 2.0F;
/*    */     
/* 41 */     int $$4 = $$0.getInvulnerableTicks();
/* 42 */     if ($$4 > 0) {
/* 43 */       $$3 -= ($$4 - $$2) / 220.0F * 0.5F;
/*    */     }
/*    */     
/* 46 */     $$1.scale($$3, $$3, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\WitherBossRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */