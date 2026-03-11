/*    */ package net.minecraft.world.level.levelgen.material;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.NoiseChunk;
/*    */ 
/*    */ public final class MaterialRuleList extends Record implements NoiseChunk.BlockStateFiller {
/*    */   private final List<NoiseChunk.BlockStateFiller> materialRuleList;
/*    */   
/* 10 */   public MaterialRuleList(List<NoiseChunk.BlockStateFiller> $$0) { this.materialRuleList = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/material/MaterialRuleList;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 10 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/material/MaterialRuleList; } public List<NoiseChunk.BlockStateFiller> materialRuleList() { return this.materialRuleList; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/material/MaterialRuleList;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/material/MaterialRuleList; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/material/MaterialRuleList;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/material/MaterialRuleList;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } @Nullable
/*    */   public BlockState calculate(DensityFunction.FunctionContext $$0) {
/* 14 */     for (NoiseChunk.BlockStateFiller $$1 : this.materialRuleList) {
/* 15 */       BlockState $$2 = $$1.calculate($$0);
/* 16 */       if ($$2 != null) {
/* 17 */         return $$2;
/*    */       }
/*    */     } 
/* 20 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\material\MaterialRuleList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */