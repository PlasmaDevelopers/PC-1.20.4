/*    */ package net.minecraft.world.level.levelgen.structure.pools;
/*    */ 
/*    */ import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ import org.apache.commons.lang3.mutable.MutableObject;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class PieceState
/*    */   extends Record
/*    */ {
/*    */   final PoolElementStructurePiece piece;
/*    */   final MutableObject<VoxelShape> free;
/*    */   final int depth;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$PieceState;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$PieceState;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$PieceState;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$PieceState;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$PieceState;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$PieceState;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   PieceState(PoolElementStructurePiece $$0, MutableObject<VoxelShape> $$1, int $$2) {
/* 52 */     this.piece = $$0; this.free = $$1; this.depth = $$2; } public PoolElementStructurePiece piece() { return this.piece; } public MutableObject<VoxelShape> free() { return this.free; } public int depth() { return this.depth; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\JigsawPlacement$PieceState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */