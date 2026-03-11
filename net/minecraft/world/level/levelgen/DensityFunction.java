/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.RegistryFileCodec;
/*     */ import net.minecraft.util.KeyDispatchDataCodec;
/*     */ import net.minecraft.world.level.levelgen.blending.Blender;
/*     */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*     */ 
/*     */ public interface DensityFunction {
/*  14 */   public static final Codec<DensityFunction> DIRECT_CODEC = DensityFunctions.DIRECT_CODEC;
/*  15 */   public static final Codec<Holder<DensityFunction>> CODEC = (Codec<Holder<DensityFunction>>)RegistryFileCodec.create(Registries.DENSITY_FUNCTION, DIRECT_CODEC); public static final Codec<DensityFunction> HOLDER_HELPER_CODEC;
/*     */   static {
/*  17 */     HOLDER_HELPER_CODEC = CODEC.xmap(HolderHolder::new, $$0 -> {
/*     */           if ($$0 instanceof DensityFunctions.HolderHolder) {
/*     */             DensityFunctions.HolderHolder $$1 = (DensityFunctions.HolderHolder)$$0;
/*     */             return $$1.function();
/*     */           } 
/*     */           return (Holder)new Holder.Direct($$0);
/*     */         });
/*     */   }
/*     */   public static interface ContextProvider {
/*     */     DensityFunction.FunctionContext forIndex(int param1Int);
/*     */     void fillAllDirectly(double[] param1ArrayOfdouble, DensityFunction param1DensityFunction); }
/*     */   
/*     */   public static final class NoiseHolder extends Record { private final Holder<NormalNoise.NoiseParameters> noiseData;
/*     */     @Nullable
/*     */     private final NormalNoise noise;
/*     */     public static final Codec<NoiseHolder> CODEC;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #42	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #42	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;
/*     */     }
/*     */     
/*  42 */     public NoiseHolder(Holder<NormalNoise.NoiseParameters> $$0, @Nullable NormalNoise $$1) { this.noiseData = $$0; this.noise = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #42	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunction$NoiseHolder;
/*  42 */       //   0	8	1	$$0	Ljava/lang/Object; } public Holder<NormalNoise.NoiseParameters> noiseData() { return this.noiseData; } @Nullable public NormalNoise noise() { return this.noise; }
/*     */ 
/*     */ 
/*     */     
/*     */     public NoiseHolder(Holder<NormalNoise.NoiseParameters> $$0) {
/*  47 */       this($$0, null);
/*     */     }
/*     */     static {
/*  50 */       CODEC = NormalNoise.NoiseParameters.CODEC.xmap($$0 -> new NoiseHolder($$0, null), NoiseHolder::noiseData);
/*     */     }
/*     */     public double getValue(double $$0, double $$1, double $$2) {
/*  53 */       return (this.noise == null) ? 0.0D : this.noise.getValue($$0, $$1, $$2);
/*     */     }
/*     */     
/*     */     public double maxValue() {
/*  57 */       return (this.noise == null) ? 2.0D : this.noise.maxValue();
/*     */     } }
/*     */ 
/*     */   
/*     */   public static interface Visitor {
/*     */     DensityFunction apply(DensityFunction param1DensityFunction);
/*     */     
/*     */     default DensityFunction.NoiseHolder visitNoise(DensityFunction.NoiseHolder $$0) {
/*  65 */       return $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface SimpleFunction
/*     */     extends DensityFunction {
/*     */     default void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  72 */       $$1.fillAllDirectly($$0, this);
/*     */     }
/*     */ 
/*     */     
/*     */     default DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  77 */       return $$0.apply(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface FunctionContext {
/*     */     int blockX();
/*     */     
/*     */     int blockY();
/*     */     
/*     */     int blockZ();
/*     */     
/*     */     default Blender getBlender() {
/*  89 */       return Blender.empty();
/*     */     } }
/*     */   public static final class SinglePointContext extends Record implements FunctionContext { private final int blockX; private final int blockY; private final int blockZ;
/*     */     
/*  93 */     public SinglePointContext(int $$0, int $$1, int $$2) { this.blockX = $$0; this.blockY = $$1; this.blockZ = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #93	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  93 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext; } public int blockX() { return this.blockX; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #93	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #93	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunction$SinglePointContext;
/*  93 */       //   0	8	1	$$0	Ljava/lang/Object; } public int blockY() { return this.blockY; } public int blockZ() { return this.blockZ; }
/*     */      }
/*     */   default DensityFunction clamp(double $$0, double $$1) {
/*  96 */     return new DensityFunctions.Clamp(this, $$0, $$1);
/*     */   }
/*     */   
/*     */   default DensityFunction abs() {
/* 100 */     return DensityFunctions.map(this, DensityFunctions.Mapped.Type.ABS);
/*     */   }
/*     */   
/*     */   default DensityFunction square() {
/* 104 */     return DensityFunctions.map(this, DensityFunctions.Mapped.Type.SQUARE);
/*     */   }
/*     */   
/*     */   default DensityFunction cube() {
/* 108 */     return DensityFunctions.map(this, DensityFunctions.Mapped.Type.CUBE);
/*     */   }
/*     */   
/*     */   default DensityFunction halfNegative() {
/* 112 */     return DensityFunctions.map(this, DensityFunctions.Mapped.Type.HALF_NEGATIVE);
/*     */   }
/*     */   
/*     */   default DensityFunction quarterNegative() {
/* 116 */     return DensityFunctions.map(this, DensityFunctions.Mapped.Type.QUARTER_NEGATIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default DensityFunction squeeze() {
/* 123 */     return DensityFunctions.map(this, DensityFunctions.Mapped.Type.SQUEEZE);
/*     */   }
/*     */   
/*     */   double compute(FunctionContext paramFunctionContext);
/*     */   
/*     */   void fillArray(double[] paramArrayOfdouble, ContextProvider paramContextProvider);
/*     */   
/*     */   DensityFunction mapAll(Visitor paramVisitor);
/*     */   
/*     */   double minValue();
/*     */   
/*     */   double maxValue();
/*     */   
/*     */   KeyDispatchDataCodec<? extends DensityFunction> codec();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\DensityFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */