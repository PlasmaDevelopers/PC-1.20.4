/*      */ package net.minecraft.world.level.levelgen;
/*      */ import com.mojang.datafixers.kinds.App;
/*      */ import com.mojang.datafixers.kinds.Applicative;
/*      */ import com.mojang.datafixers.util.Either;
/*      */ import com.mojang.datafixers.util.Function3;
/*      */ import com.mojang.serialization.Codec;
/*      */ import com.mojang.serialization.MapCodec;
/*      */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*      */ import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
/*      */ import java.util.Arrays;
/*      */ import java.util.Optional;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Function;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.Registry;
/*      */ import net.minecraft.resources.ResourceKey;
/*      */ import net.minecraft.util.CubicSpline;
/*      */ import net.minecraft.util.KeyDispatchDataCodec;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.StringRepresentable;
/*      */ import net.minecraft.util.ToFloatFunction;
/*      */ import net.minecraft.world.level.dimension.DimensionType;
/*      */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*      */ import net.minecraft.world.level.levelgen.synth.SimplexNoise;
/*      */ 
/*      */ public final class DensityFunctions {
/*      */   private static final Codec<DensityFunction> CODEC;
/*      */   protected static final double MAX_REASONABLE_NOISE_VALUE = 1000000.0D;
/*      */   
/*      */   static {
/*   32 */     CODEC = BuiltInRegistries.DENSITY_FUNCTION_TYPE.byNameCodec().dispatch($$0 -> $$0.codec().codec(), Function.identity());
/*      */   }
/*      */ 
/*      */   
/*   36 */   static final Codec<Double> NOISE_VALUE_CODEC = Codec.doubleRange(-1000000.0D, 1000000.0D);
/*      */   
/*      */   public static final Codec<DensityFunction> DIRECT_CODEC;
/*      */   
/*      */   static {
/*   41 */     DIRECT_CODEC = Codec.either(NOISE_VALUE_CODEC, CODEC).xmap($$0 -> (DensityFunction)$$0.map(DensityFunctions::constant, Function.identity()), $$0 -> {
/*      */           if ($$0 instanceof Constant) {
/*      */             Constant $$1 = (Constant)$$0;
/*      */             return Either.left(Double.valueOf($$1.value()));
/*      */           } 
/*      */           return Either.right($$0);
/*      */         });
/*      */   } public static Codec<? extends DensityFunction> bootstrap(Registry<Codec<? extends DensityFunction>> $$0) {
/*   49 */     register($$0, "blend_alpha", BlendAlpha.CODEC);
/*   50 */     register($$0, "blend_offset", BlendOffset.CODEC);
/*   51 */     register($$0, "beardifier", BeardifierMarker.CODEC);
/*   52 */     register($$0, "old_blended_noise", BlendedNoise.CODEC);
/*   53 */     for (Marker.Type $$1 : Marker.Type.values()) {
/*   54 */       register($$0, $$1.getSerializedName(), (KeyDispatchDataCodec)$$1.codec);
/*      */     }
/*   56 */     register($$0, "noise", (KeyDispatchDataCodec)Noise.CODEC);
/*   57 */     register($$0, "end_islands", (KeyDispatchDataCodec)EndIslandDensityFunction.CODEC);
/*   58 */     register($$0, "weird_scaled_sampler", (KeyDispatchDataCodec)WeirdScaledSampler.CODEC);
/*   59 */     register($$0, "shifted_noise", (KeyDispatchDataCodec)ShiftedNoise.CODEC);
/*   60 */     register($$0, "range_choice", (KeyDispatchDataCodec)RangeChoice.CODEC);
/*   61 */     register($$0, "shift_a", (KeyDispatchDataCodec)ShiftA.CODEC);
/*   62 */     register($$0, "shift_b", (KeyDispatchDataCodec)ShiftB.CODEC);
/*   63 */     register($$0, "shift", (KeyDispatchDataCodec)Shift.CODEC);
/*   64 */     register($$0, "blend_density", (KeyDispatchDataCodec)BlendDensity.CODEC);
/*   65 */     register($$0, "clamp", (KeyDispatchDataCodec)Clamp.CODEC);
/*   66 */     for (Mapped.Type $$2 : Mapped.Type.values()) {
/*   67 */       register($$0, $$2.getSerializedName(), (KeyDispatchDataCodec)$$2.codec);
/*      */     }
/*   69 */     for (TwoArgumentSimpleFunction.Type $$3 : TwoArgumentSimpleFunction.Type.values()) {
/*   70 */       register($$0, $$3.getSerializedName(), (KeyDispatchDataCodec)$$3.codec);
/*      */     }
/*   72 */     register($$0, "spline", (KeyDispatchDataCodec)Spline.CODEC);
/*   73 */     register($$0, "constant", (KeyDispatchDataCodec)Constant.CODEC);
/*   74 */     return register($$0, "y_clamped_gradient", (KeyDispatchDataCodec)YClampedGradient.CODEC);
/*      */   }
/*      */   
/*      */   private static Codec<? extends DensityFunction> register(Registry<Codec<? extends DensityFunction>> $$0, String $$1, KeyDispatchDataCodec<? extends DensityFunction> $$2) {
/*   78 */     return (Codec<? extends DensityFunction>)Registry.register($$0, $$1, $$2.codec());
/*      */   }
/*      */   
/*      */   static <A, O> KeyDispatchDataCodec<O> singleArgumentCodec(Codec<A> $$0, Function<A, O> $$1, Function<O, A> $$2) {
/*   82 */     return KeyDispatchDataCodec.of($$0.fieldOf("argument").xmap($$1, $$2));
/*      */   }
/*      */   
/*      */   static <O> KeyDispatchDataCodec<O> singleFunctionArgumentCodec(Function<DensityFunction, O> $$0, Function<O, DensityFunction> $$1) {
/*   86 */     return singleArgumentCodec(DensityFunction.HOLDER_HELPER_CODEC, $$0, $$1);
/*      */   }
/*      */   
/*      */   static <O> KeyDispatchDataCodec<O> doubleFunctionArgumentCodec(BiFunction<DensityFunction, DensityFunction, O> $$0, Function<O, DensityFunction> $$1, Function<O, DensityFunction> $$2) {
/*   90 */     return KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec($$3 -> $$3.group((App)DensityFunction.HOLDER_HELPER_CODEC.fieldOf("argument1").forGetter($$0), (App)DensityFunction.HOLDER_HELPER_CODEC.fieldOf("argument2").forGetter($$1)).apply((Applicative)$$3, $$2)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static <O> KeyDispatchDataCodec<O> makeCodec(MapCodec<O> $$0) {
/*   97 */     return KeyDispatchDataCodec.of($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DensityFunction interpolated(DensityFunction $$0) {
/*  104 */     return new Marker(Marker.Type.Interpolated, $$0);
/*      */   }
/*      */   
/*      */   public static DensityFunction flatCache(DensityFunction $$0) {
/*  108 */     return new Marker(Marker.Type.FlatCache, $$0);
/*      */   }
/*      */   
/*      */   public static DensityFunction cache2d(DensityFunction $$0) {
/*  112 */     return new Marker(Marker.Type.Cache2D, $$0);
/*      */   }
/*      */   
/*      */   public static DensityFunction cacheOnce(DensityFunction $$0) {
/*  116 */     return new Marker(Marker.Type.CacheOnce, $$0);
/*      */   }
/*      */   
/*      */   public static DensityFunction cacheAllInCell(DensityFunction $$0) {
/*  120 */     return new Marker(Marker.Type.CacheAllInCell, $$0);
/*      */   }
/*      */   
/*      */   public static DensityFunction mappedNoise(Holder<NormalNoise.NoiseParameters> $$0, @Deprecated double $$1, double $$2, double $$3, double $$4) {
/*  124 */     return mapFromUnitTo(new Noise(new DensityFunction.NoiseHolder($$0), $$1, $$2), $$3, $$4);
/*      */   }
/*      */   
/*      */   public static DensityFunction mappedNoise(Holder<NormalNoise.NoiseParameters> $$0, double $$1, double $$2, double $$3) {
/*  128 */     return mappedNoise($$0, 1.0D, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public static DensityFunction mappedNoise(Holder<NormalNoise.NoiseParameters> $$0, double $$1, double $$2) {
/*  132 */     return mappedNoise($$0, 1.0D, 1.0D, $$1, $$2);
/*      */   }
/*      */   
/*      */   public static DensityFunction shiftedNoise2d(DensityFunction $$0, DensityFunction $$1, double $$2, Holder<NormalNoise.NoiseParameters> $$3) {
/*  136 */     return new ShiftedNoise($$0, zero(), $$1, $$2, 0.0D, new DensityFunction.NoiseHolder($$3));
/*      */   }
/*      */   
/*      */   public static DensityFunction noise(Holder<NormalNoise.NoiseParameters> $$0) {
/*  140 */     return noise($$0, 1.0D, 1.0D);
/*      */   }
/*      */   
/*      */   public static DensityFunction noise(Holder<NormalNoise.NoiseParameters> $$0, double $$1, double $$2) {
/*  144 */     return new Noise(new DensityFunction.NoiseHolder($$0), $$1, $$2);
/*      */   }
/*      */   
/*      */   public static DensityFunction noise(Holder<NormalNoise.NoiseParameters> $$0, double $$1) {
/*  148 */     return noise($$0, 1.0D, $$1);
/*      */   }
/*      */   
/*      */   public static DensityFunction rangeChoice(DensityFunction $$0, double $$1, double $$2, DensityFunction $$3, DensityFunction $$4) {
/*  152 */     return new RangeChoice($$0, $$1, $$2, $$3, $$4);
/*      */   }
/*      */   
/*      */   public static DensityFunction shiftA(Holder<NormalNoise.NoiseParameters> $$0) {
/*  156 */     return new ShiftA(new DensityFunction.NoiseHolder($$0));
/*      */   }
/*      */   
/*      */   public static DensityFunction shiftB(Holder<NormalNoise.NoiseParameters> $$0) {
/*  160 */     return new ShiftB(new DensityFunction.NoiseHolder($$0));
/*      */   }
/*      */   
/*      */   public static DensityFunction shift(Holder<NormalNoise.NoiseParameters> $$0) {
/*  164 */     return new Shift(new DensityFunction.NoiseHolder($$0));
/*      */   }
/*      */   
/*      */   public static DensityFunction blendDensity(DensityFunction $$0) {
/*  168 */     return new BlendDensity($$0);
/*      */   }
/*      */   
/*      */   public static DensityFunction endIslands(long $$0) {
/*  172 */     return new EndIslandDensityFunction($$0);
/*      */   }
/*      */   
/*      */   public static DensityFunction weirdScaledSampler(DensityFunction $$0, Holder<NormalNoise.NoiseParameters> $$1, WeirdScaledSampler.RarityValueMapper $$2) {
/*  176 */     return new WeirdScaledSampler($$0, new DensityFunction.NoiseHolder($$1), $$2);
/*      */   }
/*      */   
/*      */   public static DensityFunction add(DensityFunction $$0, DensityFunction $$1) {
/*  180 */     return TwoArgumentSimpleFunction.create(TwoArgumentSimpleFunction.Type.ADD, $$0, $$1);
/*      */   }
/*      */   
/*      */   public static DensityFunction mul(DensityFunction $$0, DensityFunction $$1) {
/*  184 */     return TwoArgumentSimpleFunction.create(TwoArgumentSimpleFunction.Type.MUL, $$0, $$1);
/*      */   }
/*      */   
/*      */   public static DensityFunction min(DensityFunction $$0, DensityFunction $$1) {
/*  188 */     return TwoArgumentSimpleFunction.create(TwoArgumentSimpleFunction.Type.MIN, $$0, $$1);
/*      */   }
/*      */   
/*      */   public static DensityFunction max(DensityFunction $$0, DensityFunction $$1) {
/*  192 */     return TwoArgumentSimpleFunction.create(TwoArgumentSimpleFunction.Type.MAX, $$0, $$1);
/*      */   }
/*      */   
/*      */   public static DensityFunction spline(CubicSpline<Spline.Point, Spline.Coordinate> $$0) {
/*  196 */     return new Spline($$0);
/*      */   }
/*      */   
/*      */   public static DensityFunction zero() {
/*  200 */     return Constant.ZERO;
/*      */   }
/*      */   
/*      */   public static DensityFunction constant(double $$0) {
/*  204 */     return new Constant($$0);
/*      */   }
/*      */   
/*      */   public static DensityFunction yClampedGradient(int $$0, int $$1, double $$2, double $$3) {
/*  208 */     return new YClampedGradient($$0, $$1, $$2, $$3);
/*      */   }
/*      */   
/*      */   public static DensityFunction map(DensityFunction $$0, Mapped.Type $$1) {
/*  212 */     return Mapped.create($$1, $$0);
/*      */   }
/*      */   
/*      */   private static DensityFunction mapFromUnitTo(DensityFunction $$0, double $$1, double $$2) {
/*  216 */     double $$3 = ($$1 + $$2) * 0.5D;
/*  217 */     double $$4 = ($$2 - $$1) * 0.5D;
/*      */     
/*  219 */     return add(constant($$3), mul(constant($$4), $$0));
/*      */   }
/*      */   
/*      */   public static DensityFunction blendAlpha() {
/*  223 */     return BlendAlpha.INSTANCE;
/*      */   }
/*      */   
/*      */   public static DensityFunction blendOffset() {
/*  227 */     return BlendOffset.INSTANCE;
/*      */   }
/*      */   
/*      */   public static DensityFunction lerp(DensityFunction $$0, DensityFunction $$1, DensityFunction $$2) {
/*  231 */     if ($$1 instanceof Constant) { Constant $$3 = (Constant)$$1;
/*  232 */       return lerp($$0, $$3.value, $$2); }
/*      */     
/*  234 */     DensityFunction $$4 = cacheOnce($$0);
/*  235 */     DensityFunction $$5 = add(mul($$4, constant(-1.0D)), constant(1.0D));
/*  236 */     return add(mul($$1, $$5), mul($$2, $$4));
/*      */   }
/*      */ 
/*      */   
/*      */   public static DensityFunction lerp(DensityFunction $$0, double $$1, DensityFunction $$2) {
/*  241 */     return add(mul($$0, add($$2, constant(-$$1))), constant($$1));
/*      */   }
/*      */ 
/*      */   
/*      */   private static interface TransformerWithContext
/*      */     extends DensityFunction
/*      */   {
/*      */     DensityFunction input();
/*      */     
/*      */     default double compute(DensityFunction.FunctionContext $$0) {
/*  251 */       return transform($$0, input().compute($$0));
/*      */     }
/*      */ 
/*      */     
/*      */     default void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  256 */       input().fillArray($$0, $$1);
/*  257 */       for (int $$2 = 0; $$2 < $$0.length; $$2++)
/*  258 */         $$0[$$2] = transform($$1.forIndex($$2), $$0[$$2]); 
/*      */     }
/*      */     
/*      */     double transform(DensityFunction.FunctionContext param1FunctionContext, double param1Double);
/*      */   }
/*      */   
/*      */   private static interface PureTransformer
/*      */     extends DensityFunction
/*      */   {
/*      */     DensityFunction input();
/*      */     
/*      */     default double compute(DensityFunction.FunctionContext $$0) {
/*  270 */       return transform(input().compute($$0));
/*      */     }
/*      */ 
/*      */     
/*      */     default void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  275 */       input().fillArray($$0, $$1);
/*  276 */       for (int $$2 = 0; $$2 < $$0.length; $$2++)
/*  277 */         $$0[$$2] = transform($$0[$$2]); 
/*      */     }
/*      */     
/*      */     double transform(double param1Double);
/*      */   }
/*      */   
/*      */   protected enum BlendAlpha
/*      */     implements DensityFunction.SimpleFunction {
/*  285 */     INSTANCE;
/*  286 */     public static final KeyDispatchDataCodec<DensityFunction> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE)); static {
/*      */     
/*      */     }
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  290 */       return 1.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  295 */       Arrays.fill($$0, 1.0D);
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  300 */       return 1.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  305 */       return 1.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  310 */       return CODEC;
/*      */     }
/*      */   }
/*      */   
/*      */   protected enum BlendOffset implements DensityFunction.SimpleFunction {
/*  315 */     INSTANCE;
/*  316 */     public static final KeyDispatchDataCodec<DensityFunction> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE)); static {
/*      */     
/*      */     }
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  320 */       return 0.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  325 */       Arrays.fill($$0, 0.0D);
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  330 */       return 0.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  335 */       return 0.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  340 */       return CODEC;
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface BeardifierOrMarker extends DensityFunction.SimpleFunction {
/*  345 */     public static final KeyDispatchDataCodec<DensityFunction> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(DensityFunctions.BeardifierMarker.INSTANCE));
/*      */ 
/*      */     
/*      */     default KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  349 */       return CODEC;
/*      */     }
/*      */   }
/*      */   
/*      */   protected enum BeardifierMarker implements BeardifierOrMarker {
/*  354 */     INSTANCE;
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  358 */       return 0.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  363 */       Arrays.fill($$0, 0.0D);
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  368 */       return 0.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  373 */       return 0.0D;
/*      */     } } @VisibleForDebug
/*      */   public static final class HolderHolder extends Record implements DensityFunction { private final Holder<DensityFunction> function; public final String toString() {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$HolderHolder;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #382	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$HolderHolder;
/*      */     } public final int hashCode() {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$HolderHolder;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #382	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$HolderHolder;
/*      */     } public final boolean equals(Object $$0) {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$HolderHolder;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #382	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$HolderHolder;
/*      */       //   0	8	1	$$0	Ljava/lang/Object;
/*      */     }
/*  382 */     public Holder<DensityFunction> function() { return this.function; } public HolderHolder(Holder<DensityFunction> $$0) {
/*  383 */       this.function = $$0;
/*      */     }
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  386 */       return ((DensityFunction)this.function.value()).compute($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  391 */       ((DensityFunction)this.function.value()).fillArray($$0, $$1);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  397 */       return $$0.apply(new HolderHolder((Holder<DensityFunction>)new Holder.Direct(((DensityFunction)this.function.value()).mapAll($$0))));
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  402 */       return this.function.isBound() ? ((DensityFunction)this.function.value()).minValue() : Double.NEGATIVE_INFINITY;
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  407 */       return this.function.isBound() ? ((DensityFunction)this.function.value()).maxValue() : Double.POSITIVE_INFINITY;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  413 */       throw new UnsupportedOperationException("Calling .codec() on HolderHolder");
/*      */     } }
/*      */ 
/*      */   
/*      */   public static interface MarkerOrMarked extends DensityFunction {
/*      */     DensityFunctions.Marker.Type type();
/*      */     
/*      */     DensityFunction wrapped();
/*      */     
/*      */     default KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  423 */       return (KeyDispatchDataCodec)(type()).codec;
/*      */     }
/*      */ 
/*      */     
/*      */     default DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  428 */       return $$0.apply(new DensityFunctions.Marker(type(), wrapped().mapAll($$0)));
/*      */     } }
/*      */   protected static final class Marker extends Record implements MarkerOrMarked { private final Type type; private final DensityFunction wrapped;
/*      */     
/*  432 */     protected Marker(Type $$0, DensityFunction $$1) { this.type = $$0; this.wrapped = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Marker;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #432	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Marker; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Marker;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #432	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Marker; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Marker;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #432	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Marker;
/*  432 */       //   0	8	1	$$0	Ljava/lang/Object; } public Type type() { return this.type; } public DensityFunction wrapped() { return this.wrapped; }
/*      */     
/*  434 */     enum Type implements StringRepresentable { Interpolated("interpolated"),
/*  435 */       FlatCache("flat_cache"),
/*  436 */       Cache2D("cache_2d"),
/*  437 */       CacheOnce("cache_once"),
/*  438 */       CacheAllInCell("cache_all_in_cell");
/*      */       private final String name;
/*      */       final KeyDispatchDataCodec<DensityFunctions.MarkerOrMarked> codec;
/*      */       
/*      */       Type(String $$0) {
/*  443 */         this.codec = DensityFunctions.singleFunctionArgumentCodec($$0 -> new DensityFunctions.Marker(this, $$0), DensityFunctions.MarkerOrMarked::wrapped);
/*      */ 
/*      */         
/*  446 */         this.name = $$0;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getSerializedName() {
/*  451 */         return this.name;
/*      */       } }
/*      */ 
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  457 */       return this.wrapped.compute($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  462 */       this.wrapped.fillArray($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  467 */       return this.wrapped.minValue();
/*      */     }
/*      */     
/*      */     public double maxValue()
/*      */     {
/*  472 */       return this.wrapped.maxValue();
/*      */     } }
/*      */    enum Type implements StringRepresentable { Interpolated("interpolated"), FlatCache("flat_cache"), Cache2D("cache_2d"), CacheOnce("cache_once"), CacheAllInCell("cache_all_in_cell"); private final String name; final KeyDispatchDataCodec<DensityFunctions.MarkerOrMarked> codec; Type(String $$0) { this.codec = DensityFunctions.singleFunctionArgumentCodec($$0 -> new DensityFunctions.Marker(this, $$0), DensityFunctions.MarkerOrMarked::wrapped);
/*      */       this.name = $$0; } public String getSerializedName() { return this.name; } } protected static final class Noise extends Record implements DensityFunction { private final DensityFunction.NoiseHolder noise; @Deprecated private final double xzScale; private final double yScale; public static final MapCodec<Noise> DATA_CODEC;
/*  476 */     protected Noise(DensityFunction.NoiseHolder $$0, @Deprecated double $$1, double $$2) { this.noise = $$0; this.xzScale = $$1; this.yScale = $$2; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Noise;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #476	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Noise; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Noise;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #476	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Noise; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Noise;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #476	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Noise;
/*  476 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction.NoiseHolder noise() { return this.noise; } @Deprecated public double xzScale() { return this.xzScale; } public double yScale() { return this.yScale; } static {
/*  477 */       DATA_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DensityFunction.NoiseHolder.CODEC.fieldOf("noise").forGetter(Noise::noise), (App)Codec.DOUBLE.fieldOf("xz_scale").forGetter(Noise::xzScale), (App)Codec.DOUBLE.fieldOf("y_scale").forGetter(Noise::yScale)).apply((Applicative)$$0, Noise::new));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  483 */     public static final KeyDispatchDataCodec<Noise> CODEC = DensityFunctions.makeCodec(DATA_CODEC);
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  487 */       return this.noise.getValue($$0.blockX() * this.xzScale, $$0.blockY() * this.yScale, $$0.blockZ() * this.xzScale);
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  492 */       $$1.fillAllDirectly($$0, this);
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  497 */       return $$0.apply(new Noise($$0.visitNoise(this.noise), this.xzScale, this.yScale));
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  502 */       return -maxValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  507 */       return this.noise.maxValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  512 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */ 
/*      */   
/*      */   protected static final class EndIslandDensityFunction
/*      */     implements DensityFunction.SimpleFunction {
/*  518 */     public static final KeyDispatchDataCodec<EndIslandDensityFunction> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(new EndIslandDensityFunction(0L)));
/*      */     
/*      */     private static final float ISLAND_THRESHOLD = -0.9F;
/*      */     private final SimplexNoise islandNoise;
/*      */     
/*      */     public EndIslandDensityFunction(long $$0) {
/*  524 */       RandomSource $$1 = new LegacyRandomSource($$0);
/*      */       
/*  526 */       $$1.consumeCount(17292);
/*  527 */       this.islandNoise = new SimplexNoise($$1);
/*      */     }
/*      */     
/*      */     private static float getHeightValue(SimplexNoise $$0, int $$1, int $$2) {
/*  531 */       int $$3 = $$1 / 2;
/*  532 */       int $$4 = $$2 / 2;
/*  533 */       int $$5 = $$1 % 2;
/*  534 */       int $$6 = $$2 % 2;
/*      */ 
/*      */       
/*  537 */       float $$7 = 100.0F - Mth.sqrt(($$1 * $$1 + $$2 * $$2)) * 8.0F;
/*  538 */       $$7 = Mth.clamp($$7, -100.0F, 80.0F);
/*      */ 
/*      */       
/*  541 */       for (int $$8 = -12; $$8 <= 12; $$8++) {
/*  542 */         for (int $$9 = -12; $$9 <= 12; $$9++) {
/*  543 */           long $$10 = ($$3 + $$8);
/*  544 */           long $$11 = ($$4 + $$9);
/*  545 */           if ($$10 * $$10 + $$11 * $$11 > 4096L && $$0.getValue($$10, $$11) < -0.8999999761581421D) {
/*  546 */             float $$12 = (Mth.abs((float)$$10) * 3439.0F + Mth.abs((float)$$11) * 147.0F) % 13.0F + 9.0F;
/*  547 */             float $$13 = ($$5 - $$8 * 2);
/*  548 */             float $$14 = ($$6 - $$9 * 2);
/*  549 */             float $$15 = 100.0F - Mth.sqrt($$13 * $$13 + $$14 * $$14) * $$12;
/*  550 */             $$15 = Mth.clamp($$15, -100.0F, 80.0F);
/*  551 */             $$7 = Math.max($$7, $$15);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  556 */       return $$7;
/*      */     }
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  561 */       return (getHeightValue(this.islandNoise, $$0.blockX() / 8, $$0.blockZ() / 8) - 8.0D) / 128.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  566 */       return -0.84375D;
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  571 */       return 0.5625D;
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  576 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */   protected static final class WeirdScaledSampler extends Record implements TransformerWithContext { private final DensityFunction input; private final DensityFunction.NoiseHolder noise; private final RarityValueMapper rarityValueMapper; private static final MapCodec<WeirdScaledSampler> DATA_CODEC;
/*      */     
/*  580 */     protected WeirdScaledSampler(DensityFunction $$0, DensityFunction.NoiseHolder $$1, RarityValueMapper $$2) { this.input = $$0; this.noise = $$1; this.rarityValueMapper = $$2; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$WeirdScaledSampler;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #580	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$WeirdScaledSampler; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$WeirdScaledSampler;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #580	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$WeirdScaledSampler; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$WeirdScaledSampler;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #580	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$WeirdScaledSampler;
/*  580 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction input() { return this.input; } public DensityFunction.NoiseHolder noise() { return this.noise; } public RarityValueMapper rarityValueMapper() { return this.rarityValueMapper; } static {
/*  581 */       DATA_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(WeirdScaledSampler::input), (App)DensityFunction.NoiseHolder.CODEC.fieldOf("noise").forGetter(WeirdScaledSampler::noise), (App)RarityValueMapper.CODEC.fieldOf("rarity_value_mapper").forGetter(WeirdScaledSampler::rarityValueMapper)).apply((Applicative)$$0, WeirdScaledSampler::new));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  587 */     public static final KeyDispatchDataCodec<WeirdScaledSampler> CODEC = DensityFunctions.makeCodec(DATA_CODEC);
/*      */ 
/*      */     
/*      */     public enum RarityValueMapper
/*      */       implements StringRepresentable
/*      */     {
/*  593 */       TYPE1("type_1", NoiseRouterData.QuantizedSpaghettiRarity::getSpaghettiRarity3D, 2.0D),
/*  594 */       TYPE2("type_2", NoiseRouterData.QuantizedSpaghettiRarity::getSphaghettiRarity2D, 3.0D);
/*      */ 
/*      */       
/*  597 */       public static final Codec<RarityValueMapper> CODEC = (Codec<RarityValueMapper>)StringRepresentable.fromEnum(RarityValueMapper::values); private final String name; final Double2DoubleFunction mapper; final double maxRarity;
/*      */       
/*      */       static {
/*      */       
/*      */       }
/*      */       
/*      */       RarityValueMapper(String $$0, Double2DoubleFunction $$1, double $$2) {
/*  604 */         this.name = $$0;
/*  605 */         this.mapper = $$1;
/*  606 */         this.maxRarity = $$2;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getSerializedName() {
/*  611 */         return this.name;
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public double transform(DensityFunction.FunctionContext $$0, double $$1) {
/*  617 */       double $$2 = this.rarityValueMapper.mapper.get($$1);
/*  618 */       return $$2 * Math.abs(this.noise.getValue($$0
/*  619 */             .blockX() / $$2, $$0
/*  620 */             .blockY() / $$2, $$0
/*  621 */             .blockZ() / $$2));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  627 */       return $$0.apply(new WeirdScaledSampler(this.input.mapAll($$0), $$0.visitNoise(this.noise), this.rarityValueMapper));
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  632 */       return 0.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  637 */       return this.rarityValueMapper.maxRarity * this.noise.maxValue();
/*      */     }
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec()
/*      */     {
/*  642 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */    public enum RarityValueMapper implements StringRepresentable { TYPE1("type_1", NoiseRouterData.QuantizedSpaghettiRarity::getSpaghettiRarity3D, 2.0D), TYPE2("type_2", NoiseRouterData.QuantizedSpaghettiRarity::getSphaghettiRarity2D, 3.0D); public static final Codec<RarityValueMapper> CODEC = (Codec<RarityValueMapper>)StringRepresentable.fromEnum(RarityValueMapper::values); private final String name; final Double2DoubleFunction mapper; final double maxRarity; static {  } RarityValueMapper(String $$0, Double2DoubleFunction $$1, double $$2) { this.name = $$0; this.mapper = $$1; this.maxRarity = $$2; } public String getSerializedName() { return this.name; } }
/*      */   protected static final class ShiftedNoise extends Record implements DensityFunction { private final DensityFunction shiftX; private final DensityFunction shiftY; private final DensityFunction shiftZ; private final double xzScale; private final double yScale; private final DensityFunction.NoiseHolder noise; private static final MapCodec<ShiftedNoise> DATA_CODEC;
/*  646 */     protected ShiftedNoise(DensityFunction $$0, DensityFunction $$1, DensityFunction $$2, double $$3, double $$4, DensityFunction.NoiseHolder $$5) { this.shiftX = $$0; this.shiftY = $$1; this.shiftZ = $$2; this.xzScale = $$3; this.yScale = $$4; this.noise = $$5; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftedNoise;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #646	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftedNoise; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftedNoise;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #646	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftedNoise; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftedNoise;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #646	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftedNoise;
/*  646 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction shiftX() { return this.shiftX; } public DensityFunction shiftY() { return this.shiftY; } public DensityFunction shiftZ() { return this.shiftZ; } public double xzScale() { return this.xzScale; } public double yScale() { return this.yScale; } public DensityFunction.NoiseHolder noise() { return this.noise; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*  654 */       DATA_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shift_x").forGetter(ShiftedNoise::shiftX), (App)DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shift_y").forGetter(ShiftedNoise::shiftY), (App)DensityFunction.HOLDER_HELPER_CODEC.fieldOf("shift_z").forGetter(ShiftedNoise::shiftZ), (App)Codec.DOUBLE.fieldOf("xz_scale").forGetter(ShiftedNoise::xzScale), (App)Codec.DOUBLE.fieldOf("y_scale").forGetter(ShiftedNoise::yScale), (App)DensityFunction.NoiseHolder.CODEC.fieldOf("noise").forGetter(ShiftedNoise::noise)).apply((Applicative)$$0, ShiftedNoise::new));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  663 */     public static final KeyDispatchDataCodec<ShiftedNoise> CODEC = DensityFunctions.makeCodec(DATA_CODEC);
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  667 */       double $$1 = $$0.blockX() * this.xzScale + this.shiftX.compute($$0);
/*  668 */       double $$2 = $$0.blockY() * this.yScale + this.shiftY.compute($$0);
/*  669 */       double $$3 = $$0.blockZ() * this.xzScale + this.shiftZ.compute($$0);
/*  670 */       return this.noise.getValue($$1, $$2, $$3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  675 */       $$1.fillAllDirectly($$0, this);
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  680 */       return $$0.apply(new ShiftedNoise(this.shiftX
/*  681 */             .mapAll($$0), this.shiftY
/*  682 */             .mapAll($$0), this.shiftZ
/*  683 */             .mapAll($$0), this.xzScale, this.yScale, $$0
/*      */ 
/*      */             
/*  686 */             .visitNoise(this.noise)));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  692 */       return -maxValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  697 */       return this.noise.maxValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  702 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */   private static final class RangeChoice extends Record implements DensityFunction { private final DensityFunction input; private final double minInclusive; private final double maxExclusive; private final DensityFunction whenInRange; private final DensityFunction whenOutOfRange; public static final MapCodec<RangeChoice> DATA_CODEC;
/*      */     
/*  706 */     RangeChoice(DensityFunction $$0, double $$1, double $$2, DensityFunction $$3, DensityFunction $$4) { this.input = $$0; this.minInclusive = $$1; this.maxExclusive = $$2; this.whenInRange = $$3; this.whenOutOfRange = $$4; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$RangeChoice;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #706	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$RangeChoice; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$RangeChoice;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #706	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$RangeChoice; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$RangeChoice;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #706	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$RangeChoice;
/*  706 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction input() { return this.input; } public double minInclusive() { return this.minInclusive; } public double maxExclusive() { return this.maxExclusive; } public DensityFunction whenInRange() { return this.whenInRange; } public DensityFunction whenOutOfRange() { return this.whenOutOfRange; } static {
/*  707 */       DATA_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(RangeChoice::input), (App)DensityFunctions.NOISE_VALUE_CODEC.fieldOf("min_inclusive").forGetter(RangeChoice::minInclusive), (App)DensityFunctions.NOISE_VALUE_CODEC.fieldOf("max_exclusive").forGetter(RangeChoice::maxExclusive), (App)DensityFunction.HOLDER_HELPER_CODEC.fieldOf("when_in_range").forGetter(RangeChoice::whenInRange), (App)DensityFunction.HOLDER_HELPER_CODEC.fieldOf("when_out_of_range").forGetter(RangeChoice::whenOutOfRange)).apply((Applicative)$$0, RangeChoice::new));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  715 */     public static final KeyDispatchDataCodec<RangeChoice> CODEC = DensityFunctions.makeCodec(DATA_CODEC);
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  719 */       double $$1 = this.input.compute($$0);
/*  720 */       if ($$1 >= this.minInclusive && $$1 < this.maxExclusive) {
/*  721 */         return this.whenInRange.compute($$0);
/*      */       }
/*  723 */       return this.whenOutOfRange.compute($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  728 */       this.input.fillArray($$0, $$1);
/*  729 */       for (int $$2 = 0; $$2 < $$0.length; $$2++) {
/*  730 */         double $$3 = $$0[$$2];
/*  731 */         if ($$3 >= this.minInclusive && $$3 < this.maxExclusive) {
/*  732 */           $$0[$$2] = this.whenInRange.compute($$1.forIndex($$2));
/*      */         } else {
/*  734 */           $$0[$$2] = this.whenOutOfRange.compute($$1.forIndex($$2));
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  741 */       return $$0.apply(new RangeChoice(this.input.mapAll($$0), this.minInclusive, this.maxExclusive, this.whenInRange.mapAll($$0), this.whenOutOfRange.mapAll($$0)));
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  746 */       return Math.min(this.whenInRange.minValue(), this.whenOutOfRange.minValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  751 */       return Math.max(this.whenInRange.maxValue(), this.whenOutOfRange.maxValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  756 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */ 
/*      */   
/*      */   static interface ShiftNoise
/*      */     extends DensityFunction {
/*      */     DensityFunction.NoiseHolder offsetNoise();
/*      */     
/*      */     default double minValue() {
/*  765 */       return -maxValue();
/*      */     }
/*      */ 
/*      */     
/*      */     default double maxValue() {
/*  770 */       return offsetNoise().maxValue() * 4.0D;
/*      */     }
/*      */     
/*      */     default double compute(double $$0, double $$1, double $$2) {
/*  774 */       return offsetNoise().getValue($$0 * 0.25D, $$1 * 0.25D, $$2 * 0.25D) * 4.0D;
/*      */     }
/*      */ 
/*      */     
/*      */     default void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*  779 */       $$1.fillAllDirectly($$0, this);
/*      */     } }
/*      */   protected static final class ShiftA extends Record implements ShiftNoise { private final DensityFunction.NoiseHolder offsetNoise;
/*      */     
/*  783 */     protected ShiftA(DensityFunction.NoiseHolder $$0) { this.offsetNoise = $$0; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftA;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #783	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftA; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftA;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #783	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftA; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftA;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #783	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftA;
/*  783 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction.NoiseHolder offsetNoise() { return this.offsetNoise; }
/*  784 */      static final KeyDispatchDataCodec<ShiftA> CODEC = DensityFunctions.singleArgumentCodec(DensityFunction.NoiseHolder.CODEC, ShiftA::new, ShiftA::offsetNoise);
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  788 */       return compute($$0.blockX(), 0.0D, $$0.blockZ());
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  793 */       return $$0.apply(new ShiftA($$0.visitNoise(this.offsetNoise)));
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  798 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */   protected static final class ShiftB extends Record implements ShiftNoise { private final DensityFunction.NoiseHolder offsetNoise;
/*      */     
/*  802 */     protected ShiftB(DensityFunction.NoiseHolder $$0) { this.offsetNoise = $$0; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftB;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #802	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftB; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftB;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #802	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftB; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftB;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #802	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$ShiftB;
/*  802 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction.NoiseHolder offsetNoise() { return this.offsetNoise; }
/*  803 */      static final KeyDispatchDataCodec<ShiftB> CODEC = DensityFunctions.singleArgumentCodec(DensityFunction.NoiseHolder.CODEC, ShiftB::new, ShiftB::offsetNoise);
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  807 */       return compute($$0.blockZ(), $$0.blockX(), 0.0D);
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  812 */       return $$0.apply(new ShiftB($$0.visitNoise(this.offsetNoise)));
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  817 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */   protected static final class Shift extends Record implements ShiftNoise { private final DensityFunction.NoiseHolder offsetNoise;
/*      */     
/*  821 */     protected Shift(DensityFunction.NoiseHolder $$0) { this.offsetNoise = $$0; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Shift;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #821	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Shift; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Shift;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #821	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Shift; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Shift;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #821	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Shift;
/*  821 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction.NoiseHolder offsetNoise() { return this.offsetNoise; }
/*  822 */      static final KeyDispatchDataCodec<Shift> CODEC = DensityFunctions.singleArgumentCodec(DensityFunction.NoiseHolder.CODEC, Shift::new, Shift::offsetNoise);
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/*  826 */       return compute($$0.blockX(), $$0.blockY(), $$0.blockZ());
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  831 */       return $$0.apply(new Shift($$0.visitNoise(this.offsetNoise)));
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  836 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */   private static final class BlendDensity extends Record implements TransformerWithContext { private final DensityFunction input;
/*      */     
/*  840 */     BlendDensity(DensityFunction $$0) { this.input = $$0; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$BlendDensity;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #840	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$BlendDensity; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$BlendDensity;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #840	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$BlendDensity; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$BlendDensity;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #840	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$BlendDensity;
/*  840 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction input() { return this.input; }
/*  841 */      static final KeyDispatchDataCodec<BlendDensity> CODEC = DensityFunctions.singleFunctionArgumentCodec(BlendDensity::new, BlendDensity::input);
/*      */ 
/*      */     
/*      */     public double transform(DensityFunction.FunctionContext $$0, double $$1) {
/*  845 */       return $$0.getBlender().blendDensity($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  850 */       return $$0.apply(new BlendDensity(this.input.mapAll($$0)));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public double minValue() {
/*  856 */       return Double.NEGATIVE_INFINITY;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public double maxValue() {
/*  862 */       return Double.POSITIVE_INFINITY;
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  867 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */   protected static final class Clamp extends Record implements PureTransformer { private final DensityFunction input; private final double minValue; private final double maxValue; private static final MapCodec<Clamp> DATA_CODEC;
/*      */     
/*  871 */     protected Clamp(DensityFunction $$0, double $$1, double $$2) { this.input = $$0; this.minValue = $$1; this.maxValue = $$2; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Clamp;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #871	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Clamp; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Clamp;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #871	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Clamp; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Clamp;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #871	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Clamp;
/*  871 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction input() { return this.input; } public double minValue() { return this.minValue; } public double maxValue() { return this.maxValue; } static {
/*  872 */       DATA_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DensityFunction.DIRECT_CODEC.fieldOf("input").forGetter(Clamp::input), (App)DensityFunctions.NOISE_VALUE_CODEC.fieldOf("min").forGetter(Clamp::minValue), (App)DensityFunctions.NOISE_VALUE_CODEC.fieldOf("max").forGetter(Clamp::maxValue)).apply((Applicative)$$0, Clamp::new));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  878 */     public static final KeyDispatchDataCodec<Clamp> CODEC = DensityFunctions.makeCodec(DATA_CODEC);
/*      */ 
/*      */     
/*      */     public double transform(double $$0) {
/*  882 */       return Mth.clamp($$0, this.minValue, this.maxValue);
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*  887 */       return new Clamp(this.input.mapAll($$0), this.minValue, this.maxValue);
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/*  892 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */   protected static final class Mapped extends Record implements PureTransformer { private final Type type; private final DensityFunction input; private final double minValue; private final double maxValue;
/*      */     
/*  896 */     protected Mapped(Type $$0, DensityFunction $$1, double $$2, double $$3) { this.type = $$0; this.input = $$1; this.minValue = $$2; this.maxValue = $$3; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Mapped;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #896	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Mapped; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Mapped;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #896	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Mapped; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Mapped;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #896	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Mapped;
/*  896 */       //   0	8	1	$$0	Ljava/lang/Object; } public Type type() { return this.type; } public DensityFunction input() { return this.input; } public double minValue() { return this.minValue; } public double maxValue() { return this.maxValue; }
/*      */      public static Mapped create(Type $$0, DensityFunction $$1) {
/*  898 */       double $$2 = $$1.minValue();
/*  899 */       double $$3 = transform($$0, $$2);
/*  900 */       double $$4 = transform($$0, $$1.maxValue());
/*  901 */       if ($$0 == Type.ABS || $$0 == Type.SQUARE)
/*      */       {
/*  903 */         return new Mapped($$0, $$1, Math.max(0.0D, $$2), Math.max($$3, $$4));
/*      */       }
/*      */       
/*  906 */       return new Mapped($$0, $$1, $$3, $$4);
/*      */     }
/*      */     
/*      */     enum Type implements StringRepresentable {
/*  910 */       ABS("abs"),
/*  911 */       SQUARE("square"),
/*  912 */       CUBE("cube"),
/*  913 */       HALF_NEGATIVE("half_negative"),
/*  914 */       QUARTER_NEGATIVE("quarter_negative"),
/*  915 */       SQUEEZE("squeeze");
/*      */       private final String name;
/*      */       final KeyDispatchDataCodec<DensityFunctions.Mapped> codec;
/*      */       
/*      */       Type(String $$0) {
/*  920 */         this.codec = DensityFunctions.singleFunctionArgumentCodec($$0 -> DensityFunctions.Mapped.create(this, $$0), DensityFunctions.Mapped::input);
/*      */ 
/*      */         
/*  923 */         this.name = $$0;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getSerializedName() {
/*  928 */         return this.name;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static double transform(Type $$0, double $$1) {
/*      */       // Byte code:
/*      */       //   0: getstatic net/minecraft/world/level/levelgen/DensityFunctions$1.$SwitchMap$net$minecraft$world$level$levelgen$DensityFunctions$Mapped$Type : [I
/*      */       //   3: aload_0
/*      */       //   4: invokevirtual ordinal : ()I
/*      */       //   7: iaload
/*      */       //   8: tableswitch default -> 48, 1 -> 56, 2 -> 63, 3 -> 69, 4 -> 77, 5 -> 95, 6 -> 113
/*      */       //   48: new java/lang/IncompatibleClassChangeError
/*      */       //   51: dup
/*      */       //   52: invokespecial <init> : ()V
/*      */       //   55: athrow
/*      */       //   56: dload_1
/*      */       //   57: invokestatic abs : (D)D
/*      */       //   60: goto -> 140
/*      */       //   63: dload_1
/*      */       //   64: dload_1
/*      */       //   65: dmul
/*      */       //   66: goto -> 140
/*      */       //   69: dload_1
/*      */       //   70: dload_1
/*      */       //   71: dmul
/*      */       //   72: dload_1
/*      */       //   73: dmul
/*      */       //   74: goto -> 140
/*      */       //   77: dload_1
/*      */       //   78: dconst_0
/*      */       //   79: dcmpl
/*      */       //   80: ifle -> 87
/*      */       //   83: dload_1
/*      */       //   84: goto -> 140
/*      */       //   87: dload_1
/*      */       //   88: ldc2_w 0.5
/*      */       //   91: dmul
/*      */       //   92: goto -> 140
/*      */       //   95: dload_1
/*      */       //   96: dconst_0
/*      */       //   97: dcmpl
/*      */       //   98: ifle -> 105
/*      */       //   101: dload_1
/*      */       //   102: goto -> 140
/*      */       //   105: dload_1
/*      */       //   106: ldc2_w 0.25
/*      */       //   109: dmul
/*      */       //   110: goto -> 140
/*      */       //   113: dload_1
/*      */       //   114: ldc2_w -1.0
/*      */       //   117: dconst_1
/*      */       //   118: invokestatic clamp : (DDD)D
/*      */       //   121: dstore_3
/*      */       //   122: dload_3
/*      */       //   123: ldc2_w 2.0
/*      */       //   126: ddiv
/*      */       //   127: dload_3
/*      */       //   128: dload_3
/*      */       //   129: dmul
/*      */       //   130: dload_3
/*      */       //   131: dmul
/*      */       //   132: ldc2_w 24.0
/*      */       //   135: ddiv
/*      */       //   136: dsub
/*      */       //   137: goto -> 140
/*      */       //   140: dreturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #933	-> 0
/*      */       //   #934	-> 56
/*      */       //   #935	-> 63
/*      */       //   #936	-> 69
/*      */       //   #937	-> 77
/*      */       //   #938	-> 95
/*      */       //   #941	-> 113
/*      */       //   #942	-> 122
/*      */       //   #933	-> 140
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	141	0	$$0	Lnet/minecraft/world/level/levelgen/DensityFunctions$Mapped$Type;
/*      */       //   0	141	1	$$1	D
/*      */       //   122	18	3	$$2	D
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double transform(double $$0) {
/*  949 */       return transform(this.type, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public Mapped mapAll(DensityFunction.Visitor $$0) {
/*  954 */       return create(this.type, this.input.mapAll($$0));
/*      */     }
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec()
/*      */     {
/*  959 */       return (KeyDispatchDataCodec)this.type.codec;
/*      */     } }
/*      */    enum Type implements StringRepresentable { ABS("abs"), SQUARE("square"), CUBE("cube"), HALF_NEGATIVE("half_negative"), QUARTER_NEGATIVE("quarter_negative"),
/*      */     SQUEEZE("squeeze"); private final String name; final KeyDispatchDataCodec<DensityFunctions.Mapped> codec; Type(String $$0) { this.codec = DensityFunctions.singleFunctionArgumentCodec($$0 -> DensityFunctions.Mapped.create(this, $$0), DensityFunctions.Mapped::input);
/*      */       this.name = $$0; } public String getSerializedName() { return this.name; } }
/*  964 */   static interface TwoArgumentSimpleFunction extends DensityFunction { public static final Logger LOGGER = LogUtils.getLogger();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static TwoArgumentSimpleFunction create(Type $$0, DensityFunction $$1, DensityFunction $$2) {
/*      */       // Byte code:
/*      */       //   0: aload_1
/*      */       //   1: invokeinterface minValue : ()D
/*      */       //   6: dstore_3
/*      */       //   7: aload_2
/*      */       //   8: invokeinterface minValue : ()D
/*      */       //   13: dstore #5
/*      */       //   15: aload_1
/*      */       //   16: invokeinterface maxValue : ()D
/*      */       //   21: dstore #7
/*      */       //   23: aload_2
/*      */       //   24: invokeinterface maxValue : ()D
/*      */       //   29: dstore #9
/*      */       //   31: aload_0
/*      */       //   32: getstatic net/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type.MIN : Lnet/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type;
/*      */       //   35: if_acmpeq -> 45
/*      */       //   38: aload_0
/*      */       //   39: getstatic net/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type.MAX : Lnet/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type;
/*      */       //   42: if_acmpne -> 100
/*      */       //   45: dload_3
/*      */       //   46: dload #9
/*      */       //   48: dcmpl
/*      */       //   49: iflt -> 56
/*      */       //   52: iconst_1
/*      */       //   53: goto -> 57
/*      */       //   56: iconst_0
/*      */       //   57: istore #11
/*      */       //   59: dload #5
/*      */       //   61: dload #7
/*      */       //   63: dcmpl
/*      */       //   64: iflt -> 71
/*      */       //   67: iconst_1
/*      */       //   68: goto -> 72
/*      */       //   71: iconst_0
/*      */       //   72: istore #12
/*      */       //   74: iload #11
/*      */       //   76: ifne -> 84
/*      */       //   79: iload #12
/*      */       //   81: ifeq -> 100
/*      */       //   84: getstatic net/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction.LOGGER : Lorg/slf4j/Logger;
/*      */       //   87: aload_0
/*      */       //   88: aload_1
/*      */       //   89: aload_2
/*      */       //   90: <illegal opcode> makeConcatWithConstants : (Lnet/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type;Lnet/minecraft/world/level/levelgen/DensityFunction;Lnet/minecraft/world/level/levelgen/DensityFunction;)Ljava/lang/String;
/*      */       //   95: invokeinterface warn : (Ljava/lang/String;)V
/*      */       //   100: getstatic net/minecraft/world/level/levelgen/DensityFunctions$1.$SwitchMap$net$minecraft$world$level$levelgen$DensityFunctions$TwoArgumentSimpleFunction$Type : [I
/*      */       //   103: aload_0
/*      */       //   104: invokevirtual ordinal : ()I
/*      */       //   107: iaload
/*      */       //   108: tableswitch default -> 140, 1 -> 148, 2 -> 155, 3 -> 164, 4 -> 173
/*      */       //   140: new java/lang/IncompatibleClassChangeError
/*      */       //   143: dup
/*      */       //   144: invokespecial <init> : ()V
/*      */       //   147: athrow
/*      */       //   148: dload_3
/*      */       //   149: dload #5
/*      */       //   151: dadd
/*      */       //   152: goto -> 227
/*      */       //   155: dload_3
/*      */       //   156: dload #5
/*      */       //   158: invokestatic max : (DD)D
/*      */       //   161: goto -> 227
/*      */       //   164: dload_3
/*      */       //   165: dload #5
/*      */       //   167: invokestatic min : (DD)D
/*      */       //   170: goto -> 227
/*      */       //   173: dload_3
/*      */       //   174: dconst_0
/*      */       //   175: dcmpl
/*      */       //   176: ifle -> 193
/*      */       //   179: dload #5
/*      */       //   181: dconst_0
/*      */       //   182: dcmpl
/*      */       //   183: ifle -> 193
/*      */       //   186: dload_3
/*      */       //   187: dload #5
/*      */       //   189: dmul
/*      */       //   190: goto -> 227
/*      */       //   193: dload #7
/*      */       //   195: dconst_0
/*      */       //   196: dcmpg
/*      */       //   197: ifge -> 215
/*      */       //   200: dload #9
/*      */       //   202: dconst_0
/*      */       //   203: dcmpg
/*      */       //   204: ifge -> 215
/*      */       //   207: dload #7
/*      */       //   209: dload #9
/*      */       //   211: dmul
/*      */       //   212: goto -> 227
/*      */       //   215: dload_3
/*      */       //   216: dload #9
/*      */       //   218: dmul
/*      */       //   219: dload #7
/*      */       //   221: dload #5
/*      */       //   223: dmul
/*      */       //   224: invokestatic min : (DD)D
/*      */       //   227: dstore #11
/*      */       //   229: getstatic net/minecraft/world/level/levelgen/DensityFunctions$1.$SwitchMap$net$minecraft$world$level$levelgen$DensityFunctions$TwoArgumentSimpleFunction$Type : [I
/*      */       //   232: aload_0
/*      */       //   233: invokevirtual ordinal : ()I
/*      */       //   236: iaload
/*      */       //   237: tableswitch default -> 268, 1 -> 276, 2 -> 284, 3 -> 294, 4 -> 304
/*      */       //   268: new java/lang/IncompatibleClassChangeError
/*      */       //   271: dup
/*      */       //   272: invokespecial <init> : ()V
/*      */       //   275: athrow
/*      */       //   276: dload #7
/*      */       //   278: dload #9
/*      */       //   280: dadd
/*      */       //   281: goto -> 358
/*      */       //   284: dload #7
/*      */       //   286: dload #9
/*      */       //   288: invokestatic max : (DD)D
/*      */       //   291: goto -> 358
/*      */       //   294: dload #7
/*      */       //   296: dload #9
/*      */       //   298: invokestatic min : (DD)D
/*      */       //   301: goto -> 358
/*      */       //   304: dload_3
/*      */       //   305: dconst_0
/*      */       //   306: dcmpl
/*      */       //   307: ifle -> 325
/*      */       //   310: dload #5
/*      */       //   312: dconst_0
/*      */       //   313: dcmpl
/*      */       //   314: ifle -> 325
/*      */       //   317: dload #7
/*      */       //   319: dload #9
/*      */       //   321: dmul
/*      */       //   322: goto -> 358
/*      */       //   325: dload #7
/*      */       //   327: dconst_0
/*      */       //   328: dcmpg
/*      */       //   329: ifge -> 346
/*      */       //   332: dload #9
/*      */       //   334: dconst_0
/*      */       //   335: dcmpg
/*      */       //   336: ifge -> 346
/*      */       //   339: dload_3
/*      */       //   340: dload #5
/*      */       //   342: dmul
/*      */       //   343: goto -> 358
/*      */       //   346: dload_3
/*      */       //   347: dload #5
/*      */       //   349: dmul
/*      */       //   350: dload #7
/*      */       //   352: dload #9
/*      */       //   354: dmul
/*      */       //   355: invokestatic max : (DD)D
/*      */       //   358: dstore #13
/*      */       //   360: aload_0
/*      */       //   361: getstatic net/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type.MUL : Lnet/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type;
/*      */       //   364: if_acmpeq -> 374
/*      */       //   367: aload_0
/*      */       //   368: getstatic net/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type.ADD : Lnet/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type;
/*      */       //   371: if_acmpne -> 468
/*      */       //   374: aload_1
/*      */       //   375: instanceof net/minecraft/world/level/levelgen/DensityFunctions$Constant
/*      */       //   378: ifeq -> 421
/*      */       //   381: aload_1
/*      */       //   382: checkcast net/minecraft/world/level/levelgen/DensityFunctions$Constant
/*      */       //   385: astore #15
/*      */       //   387: new net/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd
/*      */       //   390: dup
/*      */       //   391: aload_0
/*      */       //   392: getstatic net/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type.ADD : Lnet/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type;
/*      */       //   395: if_acmpne -> 404
/*      */       //   398: getstatic net/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type.ADD : Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type;
/*      */       //   401: goto -> 407
/*      */       //   404: getstatic net/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type.MUL : Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type;
/*      */       //   407: aload_2
/*      */       //   408: dload #11
/*      */       //   410: dload #13
/*      */       //   412: aload #15
/*      */       //   414: getfield value : D
/*      */       //   417: invokespecial <init> : (Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type;Lnet/minecraft/world/level/levelgen/DensityFunction;DDD)V
/*      */       //   420: areturn
/*      */       //   421: aload_2
/*      */       //   422: instanceof net/minecraft/world/level/levelgen/DensityFunctions$Constant
/*      */       //   425: ifeq -> 468
/*      */       //   428: aload_2
/*      */       //   429: checkcast net/minecraft/world/level/levelgen/DensityFunctions$Constant
/*      */       //   432: astore #15
/*      */       //   434: new net/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd
/*      */       //   437: dup
/*      */       //   438: aload_0
/*      */       //   439: getstatic net/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type.ADD : Lnet/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type;
/*      */       //   442: if_acmpne -> 451
/*      */       //   445: getstatic net/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type.ADD : Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type;
/*      */       //   448: goto -> 454
/*      */       //   451: getstatic net/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type.MUL : Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type;
/*      */       //   454: aload_1
/*      */       //   455: dload #11
/*      */       //   457: dload #13
/*      */       //   459: aload #15
/*      */       //   461: getfield value : D
/*      */       //   464: invokespecial <init> : (Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd$Type;Lnet/minecraft/world/level/levelgen/DensityFunction;DDD)V
/*      */       //   467: areturn
/*      */       //   468: new net/minecraft/world/level/levelgen/DensityFunctions$Ap2
/*      */       //   471: dup
/*      */       //   472: aload_0
/*      */       //   473: aload_1
/*      */       //   474: aload_2
/*      */       //   475: dload #11
/*      */       //   477: dload #13
/*      */       //   479: invokespecial <init> : (Lnet/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type;Lnet/minecraft/world/level/levelgen/DensityFunction;Lnet/minecraft/world/level/levelgen/DensityFunction;DD)V
/*      */       //   482: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #967	-> 0
/*      */       //   #968	-> 7
/*      */       //   #970	-> 15
/*      */       //   #971	-> 23
/*      */       //   #973	-> 31
/*      */       //   #974	-> 45
/*      */       //   #975	-> 59
/*      */       //   #976	-> 74
/*      */       //   #977	-> 84
/*      */       //   #987	-> 100
/*      */       //   #988	-> 148
/*      */       //   #989	-> 155
/*      */       //   #990	-> 164
/*      */       //   #992	-> 173
/*      */       //   #993	-> 186
/*      */       //   #995	-> 193
/*      */       //   #996	-> 207
/*      */       //   #998	-> 215
/*      */       //   #1002	-> 229
/*      */       //   #1003	-> 276
/*      */       //   #1004	-> 284
/*      */       //   #1005	-> 294
/*      */       //   #1007	-> 304
/*      */       //   #1008	-> 317
/*      */       //   #1010	-> 325
/*      */       //   #1011	-> 339
/*      */       //   #1013	-> 346
/*      */       //   #1017	-> 360
/*      */       //   #1018	-> 374
/*      */       //   #1019	-> 387
/*      */       //   #1021	-> 421
/*      */       //   #1022	-> 434
/*      */       //   #1026	-> 468
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	483	0	$$0	Lnet/minecraft/world/level/levelgen/DensityFunctions$TwoArgumentSimpleFunction$Type;
/*      */       //   0	483	1	$$1	Lnet/minecraft/world/level/levelgen/DensityFunction;
/*      */       //   0	483	2	$$2	Lnet/minecraft/world/level/levelgen/DensityFunction;
/*      */       //   7	476	3	$$3	D
/*      */       //   15	468	5	$$4	D
/*      */       //   23	460	7	$$5	D
/*      */       //   31	452	9	$$6	D
/*      */       //   59	41	11	$$7	Z
/*      */       //   74	26	12	$$8	Z
/*      */       //   229	254	11	$$9	D
/*      */       //   360	123	13	$$10	D
/*      */       //   387	34	15	$$11	Lnet/minecraft/world/level/levelgen/DensityFunctions$Constant;
/*      */       //   434	34	15	$$12	Lnet/minecraft/world/level/levelgen/DensityFunctions$Constant;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Type type();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DensityFunction argument1();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DensityFunction argument2();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public enum Type
/*      */       implements StringRepresentable
/*      */     {
/* 1030 */       ADD("add"),
/* 1031 */       MUL("mul"),
/* 1032 */       MIN("min"),
/* 1033 */       MAX("max"); final KeyDispatchDataCodec<DensityFunctions.TwoArgumentSimpleFunction> codec; private final String name;
/*      */       
/*      */       Type(String $$0) {
/* 1036 */         this.codec = DensityFunctions.doubleFunctionArgumentCodec(($$0, $$1) -> DensityFunctions.TwoArgumentSimpleFunction.create(this, $$0, $$1), DensityFunctions.TwoArgumentSimpleFunction::argument1, DensityFunctions.TwoArgumentSimpleFunction::argument2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1045 */         this.name = $$0;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getSerializedName() {
/* 1050 */         return this.name;
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     default KeyDispatchDataCodec<? extends DensityFunction> codec() {
/* 1062 */       return (KeyDispatchDataCodec)(type()).codec;
/*      */     } } public enum Type implements StringRepresentable { ADD("add"), MUL("mul"), MIN("min"), MAX("max"); final KeyDispatchDataCodec<DensityFunctions.TwoArgumentSimpleFunction> codec; private final String name; Type(String $$0) { this.codec = DensityFunctions.doubleFunctionArgumentCodec(($$0, $$1) -> DensityFunctions.TwoArgumentSimpleFunction.create(this, $$0, $$1), DensityFunctions.TwoArgumentSimpleFunction::argument1, DensityFunctions.TwoArgumentSimpleFunction::argument2);
/*      */       this.name = $$0; } public String getSerializedName() { return this.name; } }
/*      */   private static final class MulOrAdd extends Record implements PureTransformer, TwoArgumentSimpleFunction { private final Type specificType; private final DensityFunction input; private final double minValue; private final double maxValue; private final double argument;
/* 1066 */     MulOrAdd(Type $$0, DensityFunction $$1, double $$2, double $$3, double $$4) { this.specificType = $$0; this.input = $$1; this.minValue = $$2; this.maxValue = $$3; this.argument = $$4; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1066	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1066	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1066	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$MulOrAdd;
/* 1066 */       //   0	8	1	$$0	Ljava/lang/Object; } public Type specificType() { return this.specificType; } public DensityFunction input() { return this.input; } public double minValue() { return this.minValue; } public double maxValue() { return this.maxValue; } public double argument() { return this.argument; }
/*      */     
/* 1068 */     enum Type { MUL,
/* 1069 */       ADD; }
/*      */ 
/*      */ 
/*      */     
/*      */     public DensityFunctions.TwoArgumentSimpleFunction.Type type() {
/* 1074 */       return (this.specificType == Type.MUL) ? DensityFunctions.TwoArgumentSimpleFunction.Type.MUL : DensityFunctions.TwoArgumentSimpleFunction.Type.ADD;
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction argument1() {
/* 1079 */       return DensityFunctions.constant(this.argument);
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction argument2() {
/* 1084 */       return this.input;
/*      */     }
/*      */ 
/*      */     
/*      */     public double transform(double $$0) {
/* 1089 */       switch (this.specificType) { default: throw new IncompatibleClassChangeError();case MUL: case ADD: break; }  return 
/*      */         
/* 1091 */         $$0 + this.argument;
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/*      */       double $$8, $$9;
/* 1097 */       DensityFunction $$1 = this.input.mapAll($$0);
/* 1098 */       double $$2 = $$1.minValue();
/* 1099 */       double $$3 = $$1.maxValue();
/*      */ 
/*      */       
/* 1102 */       if (this.specificType == Type.ADD) {
/* 1103 */         double $$4 = $$2 + this.argument;
/* 1104 */         double $$5 = $$3 + this.argument;
/* 1105 */       } else if (this.argument >= 0.0D) {
/* 1106 */         double $$6 = $$2 * this.argument;
/* 1107 */         double $$7 = $$3 * this.argument;
/*      */       } else {
/* 1109 */         $$8 = $$3 * this.argument;
/* 1110 */         $$9 = $$2 * this.argument;
/*      */       } 
/* 1112 */       return new MulOrAdd(this.specificType, $$1, $$8, $$9, this.argument);
/*      */     } }
/*      */   enum Type { MUL, ADD; }
/*      */   private static final class Ap2 extends Record implements TwoArgumentSimpleFunction { private final DensityFunctions.TwoArgumentSimpleFunction.Type type; private final DensityFunction argument1; private final DensityFunction argument2; private final double minValue; private final double maxValue;
/* 1116 */     Ap2(DensityFunctions.TwoArgumentSimpleFunction.Type $$0, DensityFunction $$1, DensityFunction $$2, double $$3, double $$4) { this.type = $$0; this.argument1 = $$1; this.argument2 = $$2; this.minValue = $$3; this.maxValue = $$4; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Ap2;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1116	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Ap2; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Ap2;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1116	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Ap2; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Ap2;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1116	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Ap2;
/* 1116 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunctions.TwoArgumentSimpleFunction.Type type() { return this.type; } public DensityFunction argument1() { return this.argument1; } public DensityFunction argument2() { return this.argument2; }
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/* 1119 */       double $$1 = this.argument1.compute($$0);
/* 1120 */       switch (this.type) { default: throw new IncompatibleClassChangeError();case MUL: case null: return 
/*      */ 
/*      */             
/* 1123 */             ($$1 == 0.0D) ? 
/* 1124 */             0.0D : (
/*      */             
/* 1126 */             $$1 * this.argument2.compute($$0));
/*      */         
/*      */         case null:
/* 1129 */           return ($$1 < this.argument2.minValue()) ? 
/* 1130 */             $$1 : 
/*      */             
/* 1132 */             Math.min($$1, this.argument2.compute($$0));
/*      */         case ADD:
/*      */           break; }
/* 1135 */        return ($$1 > this.argument2.maxValue()) ? 
/* 1136 */         $$1 : 
/*      */         
/* 1138 */         Math.max($$1, this.argument2.compute($$0));
/*      */     }
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/*      */       double[] $$2;
/*      */       int $$4;
/*      */       double $$6, $$9;
/*      */       int $$3, $$7, $$10;
/* 1145 */       this.argument1.fillArray($$0, $$1);
/* 1146 */       switch (this.type) {
/*      */         case MUL:
/* 1148 */           $$2 = new double[$$0.length];
/* 1149 */           this.argument2.fillArray($$2, $$1);
/* 1150 */           for ($$3 = 0; $$3 < $$0.length; $$3++) {
/* 1151 */             $$0[$$3] = $$0[$$3] + $$2[$$3];
/*      */           }
/*      */           break;
/*      */         case null:
/* 1155 */           for ($$4 = 0; $$4 < $$0.length; $$4++) {
/* 1156 */             double $$5 = $$0[$$4];
/* 1157 */             $$0[$$4] = ($$5 == 0.0D) ? 0.0D : ($$5 * this.argument2.compute($$1.forIndex($$4)));
/*      */           } 
/*      */           break;
/*      */         case null:
/* 1161 */           $$6 = this.argument2.minValue();
/* 1162 */           for ($$7 = 0; $$7 < $$0.length; $$7++) {
/* 1163 */             double $$8 = $$0[$$7];
/* 1164 */             $$0[$$7] = ($$8 < $$6) ? $$8 : Math.min($$8, this.argument2.compute($$1.forIndex($$7)));
/*      */           } 
/*      */           break;
/*      */         case ADD:
/* 1168 */           $$9 = this.argument2.maxValue();
/* 1169 */           for ($$10 = 0; $$10 < $$0.length; $$10++) {
/* 1170 */             double $$11 = $$0[$$10];
/* 1171 */             $$0[$$10] = ($$11 > $$9) ? $$11 : Math.max($$11, this.argument2.compute($$1.forIndex($$10)));
/*      */           } 
/*      */           break;
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/* 1179 */       return $$0.apply(DensityFunctions.TwoArgumentSimpleFunction.create(this.type, this.argument1.mapAll($$0), this.argument2.mapAll($$0)));
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/* 1184 */       return this.minValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/* 1189 */       return this.maxValue;
/*      */     } }
/*      */   public static final class Spline extends Record implements DensityFunction { private final CubicSpline<Point, Coordinate> spline;
/*      */     
/* 1193 */     public Spline(CubicSpline<Point, Coordinate> $$0) { this.spline = $$0; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1193	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1193	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1193	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline;
/* 1193 */       //   0	8	1	$$0	Ljava/lang/Object; } public CubicSpline<Point, Coordinate> spline() { return this.spline; }
/* 1194 */      private static final Codec<CubicSpline<Point, Coordinate>> SPLINE_CODEC = CubicSpline.codec(Coordinate.CODEC);
/* 1195 */     private static final MapCodec<Spline> DATA_CODEC = SPLINE_CODEC.fieldOf("spline").xmap(Spline::new, Spline::spline);
/*      */     
/* 1197 */     public static final KeyDispatchDataCodec<Spline> CODEC = DensityFunctions.makeCodec(DATA_CODEC);
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/* 1201 */       return this.spline.apply(new Point($$0));
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/* 1206 */       return this.spline.minValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/* 1211 */       return this.spline.maxValue();
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 1216 */       $$1.fillAllDirectly($$0, this);
/*      */     }
/*      */ 
/*      */     
/*      */     public DensityFunction mapAll(DensityFunction.Visitor $$0) {
/* 1221 */       return $$0.apply(new Spline(this.spline.mapAll($$1 -> $$1.mapAll($$0))));
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/* 1226 */       return (KeyDispatchDataCodec)CODEC;
/*      */     }
/*      */     public static final class Coordinate extends Record implements ToFloatFunction<Point> { private final Holder<DensityFunction> function;
/* 1229 */       public Coordinate(Holder<DensityFunction> $$0) { this.function = $$0; } public final int hashCode() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Coordinate;)I
/*      */         //   6: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #1229	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Coordinate; } public final boolean equals(Object $$0) { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: aload_1
/*      */         //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Coordinate;Ljava/lang/Object;)Z
/*      */         //   7: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #1229	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Coordinate;
/* 1229 */         //   0	8	1	$$0	Ljava/lang/Object; } public Holder<DensityFunction> function() { return this.function; }
/* 1230 */        public static final Codec<Coordinate> CODEC = DensityFunction.CODEC.xmap(Coordinate::new, Coordinate::function);
/*      */ 
/*      */ 
/*      */       
/*      */       public String toString() {
/* 1235 */         Optional<ResourceKey<DensityFunction>> $$0 = this.function.unwrapKey();
/* 1236 */         if ($$0.isPresent()) {
/* 1237 */           ResourceKey<DensityFunction> $$1 = $$0.get();
/* 1238 */           if ($$1 == NoiseRouterData.CONTINENTS) {
/* 1239 */             return "continents";
/*      */           }
/* 1241 */           if ($$1 == NoiseRouterData.EROSION) {
/* 1242 */             return "erosion";
/*      */           }
/* 1244 */           if ($$1 == NoiseRouterData.RIDGES) {
/* 1245 */             return "weirdness";
/*      */           }
/* 1247 */           if ($$1 == NoiseRouterData.RIDGES_FOLDED) {
/* 1248 */             return "ridges";
/*      */           }
/*      */         } 
/* 1251 */         return "Coordinate[" + this.function + "]";
/*      */       }
/*      */ 
/*      */       
/*      */       public float apply(DensityFunctions.Spline.Point $$0) {
/* 1256 */         return (float)((DensityFunction)this.function.value()).compute($$0.context());
/*      */       }
/*      */ 
/*      */       
/*      */       public float minValue() {
/* 1261 */         return this.function.isBound() ? (float)((DensityFunction)this.function.value()).minValue() : Float.NEGATIVE_INFINITY;
/*      */       }
/*      */ 
/*      */       
/*      */       public float maxValue() {
/* 1266 */         return this.function.isBound() ? (float)((DensityFunction)this.function.value()).maxValue() : Float.POSITIVE_INFINITY;
/*      */       }
/*      */       
/*      */       public Coordinate mapAll(DensityFunction.Visitor $$0) {
/* 1270 */         return new Coordinate((Holder<DensityFunction>)new Holder.Direct(((DensityFunction)this.function.value()).mapAll($$0)));
/*      */       } }
/*      */     public static final class Point extends Record { private final DensityFunction.FunctionContext context;
/*      */       
/* 1274 */       public Point(DensityFunction.FunctionContext $$0) { this.context = $$0; } public final String toString() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point;)Ljava/lang/String;
/*      */         //   6: areturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #1274	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point; } public final int hashCode() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point;)I
/*      */         //   6: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #1274	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point; } public final boolean equals(Object $$0) { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: aload_1
/*      */         //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point;Ljava/lang/Object;)Z
/*      */         //   7: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #1274	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point;
/* 1274 */         //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction.FunctionContext context() { return this.context; } } } public static final class Coordinate extends Record implements ToFloatFunction<Spline.Point> { private final Holder<DensityFunction> function; public Coordinate(Holder<DensityFunction> $$0) { this.function = $$0; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Coordinate;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1229	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Coordinate; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Coordinate;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1229	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Coordinate;
/* 1274 */       //   0	8	1	$$0	Ljava/lang/Object; } public Holder<DensityFunction> function() { return this.function; } public static final Codec<Coordinate> CODEC = DensityFunction.CODEC.xmap(Coordinate::new, Coordinate::function); public String toString() { Optional<ResourceKey<DensityFunction>> $$0 = this.function.unwrapKey(); if ($$0.isPresent()) { ResourceKey<DensityFunction> $$1 = $$0.get(); if ($$1 == NoiseRouterData.CONTINENTS) return "continents";  if ($$1 == NoiseRouterData.EROSION) return "erosion";  if ($$1 == NoiseRouterData.RIDGES) return "weirdness";  if ($$1 == NoiseRouterData.RIDGES_FOLDED) return "ridges";  }  return "Coordinate[" + this.function + "]"; } public float apply(DensityFunctions.Spline.Point $$0) { return (float)((DensityFunction)this.function.value()).compute($$0.context()); } public float minValue() { return this.function.isBound() ? (float)((DensityFunction)this.function.value()).minValue() : Float.NEGATIVE_INFINITY; } public float maxValue() { return this.function.isBound() ? (float)((DensityFunction)this.function.value()).maxValue() : Float.POSITIVE_INFINITY; } public Coordinate mapAll(DensityFunction.Visitor $$0) { return new Coordinate((Holder<DensityFunction>)new Holder.Direct(((DensityFunction)this.function.value()).mapAll($$0))); } } public static final class Point extends Record { public Point(DensityFunction.FunctionContext $$0) { this.context = $$0; } private final DensityFunction.FunctionContext context; public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1274	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1274	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1274	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Spline$Point;
/* 1274 */       //   0	8	1	$$0	Ljava/lang/Object; } public DensityFunction.FunctionContext context() { return this.context; }
/*      */      }
/*      */   private static final class Constant extends Record implements DensityFunction.SimpleFunction { final double value;
/* 1277 */     Constant(double $$0) { this.value = $$0; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Constant;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1277	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Constant; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Constant;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1277	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Constant; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$Constant;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1277	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$Constant;
/* 1277 */       //   0	8	1	$$0	Ljava/lang/Object; } public double value() { return this.value; }
/* 1278 */      static final KeyDispatchDataCodec<Constant> CODEC = DensityFunctions.singleArgumentCodec(DensityFunctions.NOISE_VALUE_CODEC, Constant::new, Constant::value);
/* 1279 */     static final Constant ZERO = new Constant(0.0D);
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/* 1283 */       return this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public void fillArray(double[] $$0, DensityFunction.ContextProvider $$1) {
/* 1288 */       Arrays.fill($$0, this.value);
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/* 1293 */       return this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/* 1298 */       return this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/* 1303 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */   private static final class YClampedGradient extends Record implements DensityFunction.SimpleFunction { private final int fromY; private final int toY; private final double fromValue; private final double toValue; private static final MapCodec<YClampedGradient> DATA_CODEC;
/*      */     
/* 1307 */     YClampedGradient(int $$0, int $$1, double $$2, double $$3) { this.fromY = $$0; this.toY = $$1; this.fromValue = $$2; this.toValue = $$3; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/DensityFunctions$YClampedGradient;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1307	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$YClampedGradient; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/DensityFunctions$YClampedGradient;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1307	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$YClampedGradient; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/DensityFunctions$YClampedGradient;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1307	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/DensityFunctions$YClampedGradient;
/* 1307 */       //   0	8	1	$$0	Ljava/lang/Object; } public int fromY() { return this.fromY; } public int toY() { return this.toY; } public double fromValue() { return this.fromValue; } public double toValue() { return this.toValue; } static {
/* 1308 */       DATA_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.intRange(DimensionType.MIN_Y * 2, DimensionType.MAX_Y * 2).fieldOf("from_y").forGetter(YClampedGradient::fromY), (App)Codec.intRange(DimensionType.MIN_Y * 2, DimensionType.MAX_Y * 2).fieldOf("to_y").forGetter(YClampedGradient::toY), (App)DensityFunctions.NOISE_VALUE_CODEC.fieldOf("from_value").forGetter(YClampedGradient::fromValue), (App)DensityFunctions.NOISE_VALUE_CODEC.fieldOf("to_value").forGetter(YClampedGradient::toValue)).apply((Applicative)$$0, YClampedGradient::new));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1315 */     public static final KeyDispatchDataCodec<YClampedGradient> CODEC = DensityFunctions.makeCodec(DATA_CODEC);
/*      */ 
/*      */     
/*      */     public double compute(DensityFunction.FunctionContext $$0) {
/* 1319 */       return Mth.clampedMap($$0.blockY(), this.fromY, this.toY, this.fromValue, this.toValue);
/*      */     }
/*      */ 
/*      */     
/*      */     public double minValue() {
/* 1324 */       return Math.min(this.fromValue, this.toValue);
/*      */     }
/*      */ 
/*      */     
/*      */     public double maxValue() {
/* 1329 */       return Math.max(this.fromValue, this.toValue);
/*      */     }
/*      */ 
/*      */     
/*      */     public KeyDispatchDataCodec<? extends DensityFunction> codec() {
/* 1334 */       return (KeyDispatchDataCodec)CODEC;
/*      */     } }
/*      */ 
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\DensityFunctions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */