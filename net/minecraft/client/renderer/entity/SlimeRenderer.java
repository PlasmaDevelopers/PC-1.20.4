/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.SlimeModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.monster.Slime;
/*    */ 
/*    */ public class SlimeRenderer extends MobRenderer<Slime, SlimeModel<Slime>> {
/* 13 */   private static final ResourceLocation SLIME_LOCATION = new ResourceLocation("textures/entity/slime/slime.png");
/*    */   
/*    */   public SlimeRenderer(EntityRendererProvider.Context $$0) {
/* 16 */     super($$0, new SlimeModel($$0.bakeLayer(ModelLayers.SLIME)), 0.25F);
/*    */     
/* 18 */     addLayer((RenderLayer<Slime, SlimeModel<Slime>>)new SlimeOuterLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Slime $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 23 */     this.shadowRadius = 0.25F * $$0.getSize();
/* 24 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void scale(Slime $$0, PoseStack $$1, float $$2) {
/* 30 */     float $$3 = 0.999F;
/* 31 */     $$1.scale(0.999F, 0.999F, 0.999F);
/* 32 */     $$1.translate(0.0F, 0.001F, 0.0F);
/*    */     
/* 34 */     float $$4 = $$0.getSize();
/* 35 */     float $$5 = Mth.lerp($$2, $$0.oSquish, $$0.squish) / ($$4 * 0.5F + 1.0F);
/* 36 */     float $$6 = 1.0F / ($$5 + 1.0F);
/* 37 */     $$1.scale($$6 * $$4, 1.0F / $$6 * $$4, $$6 * $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Slime $$0) {
/* 42 */     return SLIME_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SlimeRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */