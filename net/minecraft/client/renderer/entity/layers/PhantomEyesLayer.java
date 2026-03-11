/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.model.PhantomModel;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.monster.Phantom;
/*    */ 
/*    */ public class PhantomEyesLayer<T extends Phantom> extends EyesLayer<T, PhantomModel<T>> {
/* 10 */   private static final RenderType PHANTOM_EYES = RenderType.eyes(new ResourceLocation("textures/entity/phantom_eyes.png"));
/*    */   
/*    */   public PhantomEyesLayer(RenderLayerParent<T, PhantomModel<T>> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderType renderType() {
/* 18 */     return PHANTOM_EYES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\PhantomEyesLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */