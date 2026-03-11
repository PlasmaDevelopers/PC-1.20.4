/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ 
/*    */ public final class BlockColumnConfiguration extends Record implements FeatureConfiguration {
/*    */   private final List<Layer> layers;
/*    */   private final Direction direction;
/*    */   private final BlockPredicate allowedPlacement;
/*    */   private final boolean prioritizeTip;
/*    */   public static final Codec<BlockColumnConfiguration> CODEC;
/*    */   
/* 12 */   public BlockColumnConfiguration(List<Layer> $$0, Direction $$1, BlockPredicate $$2, boolean $$3) { this.layers = $$0; this.direction = $$1; this.allowedPlacement = $$2; this.prioritizeTip = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration; } public List<Layer> layers() { return this.layers; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public Direction direction() { return this.direction; } public BlockPredicate allowedPlacement() { return this.allowedPlacement; } public boolean prioritizeTip() { return this.prioritizeTip; }
/*    */    static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Layer.CODEC.listOf().fieldOf("layers").forGetter(BlockColumnConfiguration::layers), (App)Direction.CODEC.fieldOf("direction").forGetter(BlockColumnConfiguration::direction), (App)BlockPredicate.CODEC.fieldOf("allowed_placement").forGetter(BlockColumnConfiguration::allowedPlacement), (App)Codec.BOOL.fieldOf("prioritize_tip").forGetter(BlockColumnConfiguration::prioritizeTip)).apply((Applicative)$$0, BlockColumnConfiguration::new));
/*    */   }
/*    */   
/*    */   public static final class Layer extends Record { private final IntProvider height;
/*    */     private final BlockStateProvider state;
/*    */     public static final Codec<Layer> CODEC;
/*    */     
/* 21 */     public Layer(IntProvider $$0, BlockStateProvider $$1) { this.height = $$0; this.state = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration$Layer;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #21	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration$Layer; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration$Layer;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #21	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration$Layer; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration$Layer;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #21	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/configurations/BlockColumnConfiguration$Layer;
/* 21 */       //   0	8	1	$$0	Ljava/lang/Object; } public IntProvider height() { return this.height; } public BlockStateProvider state() { return this.state; } static {
/* 22 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)IntProvider.NON_NEGATIVE_CODEC.fieldOf("height").forGetter(Layer::height), (App)BlockStateProvider.CODEC.fieldOf("provider").forGetter(Layer::state)).apply((Applicative)$$0, Layer::new));
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Layer layer(IntProvider $$0, BlockStateProvider $$1) {
/* 29 */     return new Layer($$0, $$1);
/*    */   }
/*    */   
/*    */   public static BlockColumnConfiguration simple(IntProvider $$0, BlockStateProvider $$1) {
/* 33 */     return new BlockColumnConfiguration(List.of(layer($$0, $$1)), Direction.UP, BlockPredicate.ONLY_IN_AIR_PREDICATE, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\BlockColumnConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */