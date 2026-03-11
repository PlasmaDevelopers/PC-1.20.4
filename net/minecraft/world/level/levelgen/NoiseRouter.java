/*    */ package net.minecraft.world.level.levelgen;
/*    */ public final class NoiseRouter extends Record { private final DensityFunction barrierNoise;
/*    */   private final DensityFunction fluidLevelFloodednessNoise;
/*    */   private final DensityFunction fluidLevelSpreadNoise;
/*    */   private final DensityFunction lavaNoise;
/*    */   private final DensityFunction temperature;
/*    */   private final DensityFunction vegetation;
/*    */   private final DensityFunction continents;
/*    */   private final DensityFunction erosion;
/*    */   
/* 11 */   public NoiseRouter(DensityFunction $$0, DensityFunction $$1, DensityFunction $$2, DensityFunction $$3, DensityFunction $$4, DensityFunction $$5, DensityFunction $$6, DensityFunction $$7, DensityFunction $$8, DensityFunction $$9, DensityFunction $$10, DensityFunction $$11, DensityFunction $$12, DensityFunction $$13, DensityFunction $$14) { this.barrierNoise = $$0; this.fluidLevelFloodednessNoise = $$1; this.fluidLevelSpreadNoise = $$2; this.lavaNoise = $$3; this.temperature = $$4; this.vegetation = $$5; this.continents = $$6; this.erosion = $$7; this.depth = $$8; this.ridges = $$9; this.initialDensityWithoutJaggedness = $$10; this.finalDensity = $$11; this.veinToggle = $$12; this.veinRidged = $$13; this.veinGap = $$14; } private final DensityFunction depth; private final DensityFunction ridges; private final DensityFunction initialDensityWithoutJaggedness; private final DensityFunction finalDensity; private final DensityFunction veinToggle; private final DensityFunction veinRidged; private final DensityFunction veinGap; public static final Codec<NoiseRouter> CODEC; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/NoiseRouter;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/NoiseRouter; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/NoiseRouter;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/NoiseRouter; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/NoiseRouter;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/NoiseRouter;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction barrierNoise() { return this.barrierNoise; } public DensityFunction fluidLevelFloodednessNoise() { return this.fluidLevelFloodednessNoise; } public DensityFunction fluidLevelSpreadNoise() { return this.fluidLevelSpreadNoise; } public DensityFunction lavaNoise() { return this.lavaNoise; } public DensityFunction temperature() { return this.temperature; } public DensityFunction vegetation() { return this.vegetation; } public DensityFunction continents() { return this.continents; } public DensityFunction erosion() { return this.erosion; } public DensityFunction depth() { return this.depth; } public DensityFunction ridges() { return this.ridges; } public DensityFunction initialDensityWithoutJaggedness() { return this.initialDensityWithoutJaggedness; } public DensityFunction finalDensity() { return this.finalDensity; } public DensityFunction veinToggle() { return this.veinToggle; } public DensityFunction veinRidged() { return this.veinRidged; } public DensityFunction veinGap() { return this.veinGap; }
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
/*    */   private static RecordCodecBuilder<NoiseRouter, DensityFunction> field(String $$0, Function<NoiseRouter, DensityFunction> $$1) {
/* 38 */     return DensityFunction.HOLDER_HELPER_CODEC.fieldOf($$0).forGetter($$1);
/*    */   }
/*    */   static {
/* 41 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)field("barrier", NoiseRouter::barrierNoise), (App)field("fluid_level_floodedness", NoiseRouter::fluidLevelFloodednessNoise), (App)field("fluid_level_spread", NoiseRouter::fluidLevelSpreadNoise), (App)field("lava", NoiseRouter::lavaNoise), (App)field("temperature", NoiseRouter::temperature), (App)field("vegetation", NoiseRouter::vegetation), (App)field("continents", NoiseRouter::continents), (App)field("erosion", NoiseRouter::erosion), (App)field("depth", NoiseRouter::depth), (App)field("ridges", NoiseRouter::ridges), (App)field("initial_density_without_jaggedness", NoiseRouter::initialDensityWithoutJaggedness), (App)field("final_density", NoiseRouter::finalDensity), (App)field("vein_toggle", NoiseRouter::veinToggle), (App)field("vein_ridged", NoiseRouter::veinRidged), (App)field("vein_gap", NoiseRouter::veinGap)).apply((Applicative)$$0, NoiseRouter::new));
/*    */   }
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
/*    */   public NoiseRouter mapAll(DensityFunction.Visitor $$0) {
/* 63 */     return new NoiseRouter(this.barrierNoise
/* 64 */         .mapAll($$0), this.fluidLevelFloodednessNoise
/* 65 */         .mapAll($$0), this.fluidLevelSpreadNoise
/* 66 */         .mapAll($$0), this.lavaNoise
/* 67 */         .mapAll($$0), this.temperature
/* 68 */         .mapAll($$0), this.vegetation
/* 69 */         .mapAll($$0), this.continents
/* 70 */         .mapAll($$0), this.erosion
/* 71 */         .mapAll($$0), this.depth
/* 72 */         .mapAll($$0), this.ridges
/* 73 */         .mapAll($$0), this.initialDensityWithoutJaggedness
/* 74 */         .mapAll($$0), this.finalDensity
/* 75 */         .mapAll($$0), this.veinToggle
/* 76 */         .mapAll($$0), this.veinRidged
/* 77 */         .mapAll($$0), this.veinGap
/* 78 */         .mapAll($$0));
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\NoiseRouter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */