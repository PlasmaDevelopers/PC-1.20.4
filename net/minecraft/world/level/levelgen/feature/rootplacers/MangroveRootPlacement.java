/*    */ package net.minecraft.world.level.levelgen.feature.rootplacers;
/*    */ public final class MangroveRootPlacement extends Record {
/*    */   private final HolderSet<Block> canGrowThrough;
/*    */   private final HolderSet<Block> muddyRootsIn;
/*    */   private final BlockStateProvider muddyRootsProvider;
/*    */   private final int maxRootWidth;
/*    */   private final int maxRootLength;
/*    */   private final float randomSkewChance;
/*    */   public static final Codec<MangroveRootPlacement> CODEC;
/*    */   
/* 11 */   public MangroveRootPlacement(HolderSet<Block> $$0, HolderSet<Block> $$1, BlockStateProvider $$2, int $$3, int $$4, float $$5) { this.canGrowThrough = $$0; this.muddyRootsIn = $$1; this.muddyRootsProvider = $$2; this.maxRootWidth = $$3; this.maxRootLength = $$4; this.randomSkewChance = $$5; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/rootplacers/MangroveRootPlacement;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/rootplacers/MangroveRootPlacement; } public HolderSet<Block> canGrowThrough() { return this.canGrowThrough; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/rootplacers/MangroveRootPlacement;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/rootplacers/MangroveRootPlacement; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/rootplacers/MangroveRootPlacement;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/rootplacers/MangroveRootPlacement;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public HolderSet<Block> muddyRootsIn() { return this.muddyRootsIn; } public BlockStateProvider muddyRootsProvider() { return this.muddyRootsProvider; } public int maxRootWidth() { return this.maxRootWidth; } public int maxRootLength() { return this.maxRootLength; } public float randomSkewChance() { return this.randomSkewChance; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_grow_through").forGetter(()), (App)RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("muddy_roots_in").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("muddy_roots_provider").forGetter(()), (App)Codec.intRange(1, 12).fieldOf("max_root_width").forGetter(()), (App)Codec.intRange(1, 64).fieldOf("max_root_length").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("random_skew_chance").forGetter(())).apply((Applicative)$$0, MangroveRootPlacement::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\rootplacers\MangroveRootPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */