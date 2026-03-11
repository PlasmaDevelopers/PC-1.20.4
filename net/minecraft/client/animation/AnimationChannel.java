/*    */ package net.minecraft.client.animation;
/*    */ 
/*    */ public final class AnimationChannel extends Record {
/*    */   private final Target target;
/*    */   private final Keyframe[] keyframes;
/*    */   
/*  7 */   public AnimationChannel(Target $$0, Keyframe... $$1) { this.target = $$0; this.keyframes = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/animation/AnimationChannel;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/client/animation/AnimationChannel; } public Target target() { return this.target; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/animation/AnimationChannel;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/animation/AnimationChannel; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/animation/AnimationChannel;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/animation/AnimationChannel;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public Keyframe[] keyframes() { return this.keyframes; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static interface Interpolation
/*    */   {
/*    */     Vector3f apply(Vector3f param1Vector3f, float param1Float1, Keyframe[] param1ArrayOfKeyframe, int param1Int1, int param1Int2, float param1Float2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Targets
/*    */   {
/* 21 */     public static final AnimationChannel.Target POSITION = ModelPart::offsetPos;
/* 22 */     public static final AnimationChannel.Target ROTATION = ModelPart::offsetRotation;
/* 23 */     public static final AnimationChannel.Target SCALE = ModelPart::offsetScale; }
/*    */   public static class Interpolations { public static final AnimationChannel.Interpolation LINEAR; public static final AnimationChannel.Interpolation CATMULLROM;
/*    */     
/*    */     static {
/* 27 */       LINEAR = (($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*    */           Vector3f $$6 = $$2[$$3].target();
/*    */           
/*    */           Vector3f $$7 = $$2[$$4].target();
/*    */           
/*    */           return $$6.lerp((Vector3fc)$$7, $$1, $$0).mul($$5);
/*    */         });
/* 34 */       CATMULLROM = (($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*    */           Vector3f $$6 = $$2[Math.max(0, $$3 - 1)].target();
/*    */           Vector3f $$7 = $$2[$$3].target();
/*    */           Vector3f $$8 = $$2[$$4].target();
/*    */           Vector3f $$9 = $$2[Math.min($$2.length - 1, $$4 + 1)].target();
/*    */           $$0.set(Mth.catmullrom($$1, $$6.x(), $$7.x(), $$8.x(), $$9.x()) * $$5, Mth.catmullrom($$1, $$6.y(), $$7.y(), $$8.y(), $$9.y()) * $$5, Mth.catmullrom($$1, $$6.z(), $$7.z(), $$8.z(), $$9.z()) * $$5);
/*    */           return $$0;
/*    */         });
/*    */     } }
/*    */ 
/*    */   
/*    */   public static interface Target {
/*    */     void apply(ModelPart param1ModelPart, Vector3f param1Vector3f);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\AnimationChannel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */