/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.HorseModel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*    */ 
/*    */ public abstract class AbstractHorseRenderer<T extends AbstractHorse, M extends HorseModel<T>>
/*    */   extends MobRenderer<T, M> {
/*    */   public AbstractHorseRenderer(EntityRendererProvider.Context $$0, M $$1, float $$2) {
/* 11 */     super($$0, $$1, 0.75F);
/* 12 */     this.scale = $$2;
/*    */   }
/*    */   private final float scale;
/*    */   
/*    */   protected void scale(T $$0, PoseStack $$1, float $$2) {
/* 17 */     $$1.scale(this.scale, this.scale, this.scale);
/* 18 */     super.scale($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\AbstractHorseRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */