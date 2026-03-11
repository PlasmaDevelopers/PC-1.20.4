/*   */ package com.mojang.blaze3d.audio;public final class ListenerTransform extends Record { private final Vec3 position;
/*   */   private final Vec3 forward;
/*   */   private final Vec3 up;
/*   */   
/* 5 */   public ListenerTransform(Vec3 $$0, Vec3 $$1, Vec3 $$2) { this.position = $$0; this.forward = $$1; this.up = $$2; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lcom/mojang/blaze3d/audio/ListenerTransform;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 5 */     //   0	7	0	this	Lcom/mojang/blaze3d/audio/ListenerTransform; } public Vec3 position() { return this.position; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lcom/mojang/blaze3d/audio/ListenerTransform;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lcom/mojang/blaze3d/audio/ListenerTransform; } public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lcom/mojang/blaze3d/audio/ListenerTransform;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #5	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lcom/mojang/blaze3d/audio/ListenerTransform;
/* 5 */     //   0	8	1	$$0	Ljava/lang/Object; } public Vec3 forward() { return this.forward; } public Vec3 up() { return this.up; }
/* 6 */    public static final ListenerTransform INITIAL = new ListenerTransform(Vec3.ZERO, new Vec3(0.0D, 0.0D, -1.0D), new Vec3(0.0D, 1.0D, 0.0D));
/*   */   
/*   */   public Vec3 right() {
/* 9 */     return this.forward.cross(this.up);
/*   */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\ListenerTransform.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */