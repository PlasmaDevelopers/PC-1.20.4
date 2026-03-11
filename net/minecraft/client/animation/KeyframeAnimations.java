/*    */ package net.minecraft.client.animation;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.client.model.HierarchicalModel;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.util.Mth;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class KeyframeAnimations
/*    */ {
/*    */   public static void animate(HierarchicalModel<?> $$0, AnimationDefinition $$1, long $$2, float $$3, Vector3f $$4) {
/* 14 */     float $$5 = getElapsedSeconds($$1, $$2);
/*    */     
/* 16 */     for (Map.Entry<String, List<AnimationChannel>> $$6 : $$1.boneAnimations().entrySet()) {
/* 17 */       Optional<ModelPart> $$7 = $$0.getAnyDescendantWithName($$6.getKey());
/* 18 */       List<AnimationChannel> $$8 = $$6.getValue();
/*    */       
/* 20 */       $$7.ifPresent($$4 -> $$0.forEach(()));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static float getElapsedSeconds(AnimationDefinition $$0, long $$1) {
/* 45 */     float $$2 = (float)$$1 / 1000.0F;
/* 46 */     return $$0.looping() ? ($$2 % $$0.lengthInSeconds()) : $$2;
/*    */   }
/*    */   
/*    */   public static Vector3f posVec(float $$0, float $$1, float $$2) {
/* 50 */     return new Vector3f($$0, -$$1, $$2);
/*    */   }
/*    */   
/*    */   public static Vector3f degreeVec(float $$0, float $$1, float $$2) {
/* 54 */     return new Vector3f($$0 * 0.017453292F, $$1 * 0.017453292F, $$2 * 0.017453292F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Vector3f scaleVec(double $$0, double $$1, double $$2) {
/* 65 */     return new Vector3f((float)($$0 - 1.0D), (float)($$1 - 1.0D), (float)($$2 - 1.0D));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\KeyframeAnimations.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */