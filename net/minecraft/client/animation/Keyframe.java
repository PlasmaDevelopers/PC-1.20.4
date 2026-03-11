/*   */ package net.minecraft.client.animation;public final class Keyframe extends Record { private final float timestamp;
/*   */   private final Vector3f target;
/*   */   private final AnimationChannel.Interpolation interpolation;
/*   */   
/* 5 */   public Keyframe(float $$0, Vector3f $$1, AnimationChannel.Interpolation $$2) { this.timestamp = $$0; this.target = $$1; this.interpolation = $$2; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/animation/Keyframe;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 5 */     //   0	7	0	this	Lnet/minecraft/client/animation/Keyframe; } public float timestamp() { return this.timestamp; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/animation/Keyframe;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/client/animation/Keyframe; } public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/animation/Keyframe;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/client/animation/Keyframe;
/* 5 */     //   0	8	1	$$0	Ljava/lang/Object; } public Vector3f target() { return this.target; } public AnimationChannel.Interpolation interpolation() { return this.interpolation; }
/*   */    }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\Keyframe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */