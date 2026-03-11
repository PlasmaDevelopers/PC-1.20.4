/*   */ package net.minecraft.client.renderer.block.model;public final class BlockElementRotation extends Record { private final Vector3f origin;
/*   */   private final Direction.Axis axis;
/*   */   private final float angle;
/*   */   private final boolean rescale;
/*   */   
/* 6 */   public BlockElementRotation(Vector3f $$0, Direction.Axis $$1, float $$2, boolean $$3) { this.origin = $$0; this.axis = $$1; this.angle = $$2; this.rescale = $$3; } public final String toString() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/block/model/BlockElementRotation;)Ljava/lang/String;
/*   */     //   6: areturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/* 6 */     //   0	7	0	this	Lnet/minecraft/client/renderer/block/model/BlockElementRotation; } public Vector3f origin() { return this.origin; } public final int hashCode() { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/block/model/BlockElementRotation;)I
/*   */     //   6: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	7	0	this	Lnet/minecraft/client/renderer/block/model/BlockElementRotation; } public final boolean equals(Object $$0) { // Byte code:
/*   */     //   0: aload_0
/*   */     //   1: aload_1
/*   */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/block/model/BlockElementRotation;Ljava/lang/Object;)Z
/*   */     //   7: ireturn
/*   */     // Line number table:
/*   */     //   Java source line number -> byte code offset
/*   */     //   #6	-> 0
/*   */     // Local variable table:
/*   */     //   start	length	slot	name	descriptor
/*   */     //   0	8	0	this	Lnet/minecraft/client/renderer/block/model/BlockElementRotation;
/* 6 */     //   0	8	1	$$0	Ljava/lang/Object; } public Direction.Axis axis() { return this.axis; } public float angle() { return this.angle; } public boolean rescale() { return this.rescale; }
/*   */    }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BlockElementRotation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */