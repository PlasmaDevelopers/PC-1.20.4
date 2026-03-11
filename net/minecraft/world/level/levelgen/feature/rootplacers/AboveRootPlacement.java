/*    */ package net.minecraft.world.level.levelgen.feature.rootplacers;
/*    */ public final class AboveRootPlacement extends Record {
/*    */   private final BlockStateProvider aboveRootProvider;
/*    */   private final float aboveRootPlacementChance;
/*    */   public static final Codec<AboveRootPlacement> CODEC;
/*    */   
/*  7 */   public AboveRootPlacement(BlockStateProvider $$0, float $$1) { this.aboveRootProvider = $$0; this.aboveRootPlacementChance = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  7 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement; } public BlockStateProvider aboveRootProvider() { return this.aboveRootProvider; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/rootplacers/AboveRootPlacement;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public float aboveRootPlacementChance() { return this.aboveRootPlacementChance; }
/*    */ 
/*    */   
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockStateProvider.CODEC.fieldOf("above_root_provider").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("above_root_placement_chance").forGetter(())).apply((Applicative)$$0, AboveRootPlacement::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\rootplacers\AboveRootPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */