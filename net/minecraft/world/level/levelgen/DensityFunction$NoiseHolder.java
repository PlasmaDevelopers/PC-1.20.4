/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
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
/*    */ public final class NoiseHolder
/*    */   extends Record
/*    */ {
/*    */   private final Holder<NormalNoise.NoiseParameters> noiseData;
/*    */   @Nullable
/*    */   private final NormalNoise noise;
/*    */   public static final Codec<NoiseHolder> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #42	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #42	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #42	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public NoiseHolder(Holder<NormalNoise.NoiseParameters> $$0, @Nullable NormalNoise $$1) {
/* 42 */     this.noiseData = $$0; this.noise = $$1; } public Holder<NormalNoise.NoiseParameters> noiseData() { return this.noiseData; } @Nullable public NormalNoise noise() { return this.noise; }
/*    */ 
/*    */ 
/*    */   
/*    */   public NoiseHolder(Holder<NormalNoise.NoiseParameters> $$0) {
/* 47 */     this($$0, null);
/*    */   }
/*    */   static {
/* 50 */     CODEC = NormalNoise.NoiseParameters.CODEC.xmap($$0 -> new NoiseHolder($$0, null), NoiseHolder::noiseData);
/*    */   }
/*    */   public double getValue(double $$0, double $$1, double $$2) {
/* 53 */     return (this.noise == null) ? 0.0D : this.noise.getValue($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public double maxValue() {
/* 57 */     return (this.noise == null) ? 2.0D : this.noise.maxValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\DensityFunction$NoiseHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */