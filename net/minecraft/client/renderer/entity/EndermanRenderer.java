/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.EndermanModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.monster.EnderMan;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class EndermanRenderer extends MobRenderer<EnderMan, EndermanModel<EnderMan>> {
/* 16 */   private static final ResourceLocation ENDERMAN_LOCATION = new ResourceLocation("textures/entity/enderman/enderman.png");
/*    */   
/* 18 */   private final RandomSource random = RandomSource.create();
/*    */   
/*    */   public EndermanRenderer(EntityRendererProvider.Context $$0) {
/* 21 */     super($$0, new EndermanModel($$0.bakeLayer(ModelLayers.ENDERMAN)), 0.5F);
/*    */     
/* 23 */     addLayer((RenderLayer<EnderMan, EndermanModel<EnderMan>>)new EnderEyesLayer(this));
/* 24 */     addLayer((RenderLayer<EnderMan, EndermanModel<EnderMan>>)new CarriedBlockLayer(this, $$0.getBlockRenderDispatcher()));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(EnderMan $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 30 */     BlockState $$6 = $$0.getCarriedBlock();
/* 31 */     EndermanModel<EnderMan> $$7 = getModel();
/* 32 */     $$7.carrying = ($$6 != null);
/* 33 */     $$7.creepy = $$0.isCreepy();
/*    */     
/* 35 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3 getRenderOffset(EnderMan $$0, float $$1) {
/* 40 */     if ($$0.isCreepy()) {
/* 41 */       double $$2 = 0.02D;
/* 42 */       return new Vec3(this.random.nextGaussian() * 0.02D, 0.0D, this.random.nextGaussian() * 0.02D);
/*    */     } 
/*    */     
/* 45 */     return super.getRenderOffset($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(EnderMan $$0) {
/* 50 */     return ENDERMAN_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EndermanRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */