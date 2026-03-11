/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.TurtleModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Turtle;
/*    */ 
/*    */ public class TurtleRenderer extends MobRenderer<Turtle, TurtleModel<Turtle>> {
/* 11 */   private static final ResourceLocation TURTLE_LOCATION = new ResourceLocation("textures/entity/turtle/big_sea_turtle.png");
/*    */   
/*    */   public TurtleRenderer(EntityRendererProvider.Context $$0) {
/* 14 */     super($$0, new TurtleModel($$0.bakeLayer(ModelLayers.TURTLE)), 0.7F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Turtle $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 20 */     if ($$0.isBaby()) {
/* 21 */       this.shadowRadius *= 0.5F;
/*    */     }
/* 23 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Turtle $$0) {
/* 28 */     return TURTLE_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\TurtleRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */