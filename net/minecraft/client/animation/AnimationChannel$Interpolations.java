/*    */ package net.minecraft.client.animation;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ import org.joml.Vector3f;
/*    */ import org.joml.Vector3fc;
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
/*    */ public class Interpolations
/*    */ {
/*    */   public static final AnimationChannel.Interpolation LINEAR;
/*    */   public static final AnimationChannel.Interpolation CATMULLROM;
/*    */   
/*    */   static {
/* 27 */     LINEAR = (($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*    */         Vector3f $$6 = $$2[$$3].target();
/*    */         
/*    */         Vector3f $$7 = $$2[$$4].target();
/*    */         
/*    */         return $$6.lerp((Vector3fc)$$7, $$1, $$0).mul($$5);
/*    */       });
/* 34 */     CATMULLROM = (($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*    */         Vector3f $$6 = $$2[Math.max(0, $$3 - 1)].target();
/*    */         Vector3f $$7 = $$2[$$3].target();
/*    */         Vector3f $$8 = $$2[$$4].target();
/*    */         Vector3f $$9 = $$2[Math.min($$2.length - 1, $$4 + 1)].target();
/*    */         $$0.set(Mth.catmullrom($$1, $$6.x(), $$7.x(), $$8.x(), $$9.x()) * $$5, Mth.catmullrom($$1, $$6.y(), $$7.y(), $$8.y(), $$9.y()) * $$5, Mth.catmullrom($$1, $$6.z(), $$7.z(), $$8.z(), $$9.z()) * $$5);
/*    */         return $$0;
/*    */       });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\AnimationChannel$Interpolations.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */