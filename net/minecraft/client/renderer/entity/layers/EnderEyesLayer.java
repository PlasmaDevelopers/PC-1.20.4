/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.model.EndermanModel;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public class EnderEyesLayer<T extends LivingEntity> extends EyesLayer<T, EndermanModel<T>> {
/* 10 */   private static final RenderType ENDERMAN_EYES = RenderType.eyes(new ResourceLocation("textures/entity/enderman/enderman_eyes.png"));
/*    */   
/*    */   public EnderEyesLayer(RenderLayerParent<T, EndermanModel<T>> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderType renderType() {
/* 18 */     return ENDERMAN_EYES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\EnderEyesLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */