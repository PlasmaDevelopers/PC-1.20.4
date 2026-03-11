/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.HumanoidModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Zombie;
/*    */ 
/*    */ public abstract class AbstractZombieRenderer<T extends Zombie, M extends ZombieModel<T>> extends HumanoidMobRenderer<T, M> {
/*  9 */   private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation("textures/entity/zombie/zombie.png");
/*    */   
/*    */   protected AbstractZombieRenderer(EntityRendererProvider.Context $$0, M $$1, M $$2, M $$3) {
/* 12 */     super($$0, $$1, 0.5F);
/*    */     
/* 14 */     addLayer((RenderLayer<T, M>)new HumanoidArmorLayer(this, (HumanoidModel)$$2, (HumanoidModel)$$3, $$0.getModelManager()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Zombie $$0) {
/* 19 */     return ZOMBIE_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isShaking(T $$0) {
/* 24 */     return (super.isShaking($$0) || $$0.isUnderWaterConverting());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\AbstractZombieRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */