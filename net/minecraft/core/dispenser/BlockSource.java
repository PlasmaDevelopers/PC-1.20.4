/*    */ package net.minecraft.core.dispenser;
/*    */ 
/*    */ public final class BlockSource extends Record {
/*    */   private final ServerLevel level;
/*    */   private final BlockPos pos;
/*    */   private final BlockState state;
/*    */   private final DispenserBlockEntity blockEntity;
/*    */   
/*  9 */   public BlockSource(ServerLevel $$0, BlockPos $$1, BlockState $$2, DispenserBlockEntity $$3) { this.level = $$0; this.pos = $$1; this.state = $$2; this.blockEntity = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/core/dispenser/BlockSource;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/core/dispenser/BlockSource; } public ServerLevel level() { return this.level; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/dispenser/BlockSource;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/core/dispenser/BlockSource; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/core/dispenser/BlockSource;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/core/dispenser/BlockSource;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public BlockPos pos() { return this.pos; } public BlockState state() { return this.state; } public DispenserBlockEntity blockEntity() { return this.blockEntity; }
/*    */    public Vec3 center() {
/* 11 */     return this.pos.getCenter();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\dispenser\BlockSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */