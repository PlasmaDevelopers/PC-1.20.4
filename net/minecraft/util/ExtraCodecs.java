/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Decoder;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.Encoder;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.RecordBuilder;
/*     */ import com.mojang.serialization.codecs.BaseMapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.floats.FloatArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
/*     */ import java.time.Instant;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.temporal.TemporalAccessor;
/*     */ import java.util.Arrays;
/*     */ import java.util.Base64;
/*     */ import java.util.BitSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntFunction;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.ToIntFunction;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import java.util.stream.LongStream;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.apache.commons.lang3.StringEscapeUtils;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ import org.joml.AxisAngle4f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class ExtraCodecs {
/*     */   public static <T> Codec<T> converter(DynamicOps<T> $$0) {
/*  67 */     return Codec.PASSTHROUGH.xmap($$1 -> $$1.convert($$0).getValue(), $$1 -> new Dynamic($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final Codec<JsonElement> JSON = converter((DynamicOps<JsonElement>)JsonOps.INSTANCE);
/*  74 */   public static final Codec<Object> JAVA = converter(JavaOps.INSTANCE); public static final Codec<JsonElement> FLAT_JSON; public static final Codec<Vector3f> VECTOR3F; public static final Codec<Quaternionf> QUATERNIONF_COMPONENTS; public static final Codec<AxisAngle4f> AXISANGLE4F;
/*     */   static {
/*  76 */     FLAT_JSON = Codec.STRING.flatXmap($$0 -> {
/*     */           
/*     */           try {
/*     */             return DataResult.success(JsonParser.parseString($$0));
/*  80 */           } catch (JsonParseException $$1) {
/*     */             Objects.requireNonNull($$1);
/*     */             return DataResult.error($$1::getMessage);
/*     */           } 
/*     */         }$$0 -> {
/*     */           try {
/*     */             return DataResult.success(GsonHelper.toStableString($$0));
/*  87 */           } catch (IllegalArgumentException $$1) {
/*     */             Objects.requireNonNull($$1);
/*     */             
/*     */             return DataResult.error($$1::getMessage);
/*     */           } 
/*     */         });
/*  93 */     VECTOR3F = Codec.FLOAT.listOf().comapFlatMap($$0 -> Util.fixedSize($$0, 3).map(()), $$0 -> List.of(Float.valueOf($$0.x()), Float.valueOf($$0.y()), Float.valueOf($$0.z())));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     QUATERNIONF_COMPONENTS = Codec.FLOAT.listOf().comapFlatMap($$0 -> Util.fixedSize($$0, 4).map(()), $$0 -> List.of(Float.valueOf($$0.x), Float.valueOf($$0.y), Float.valueOf($$0.z), Float.valueOf($$0.w)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     AXISANGLE4F = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("angle").forGetter(()), (App)VECTOR3F.fieldOf("axis").forGetter(())).apply((Applicative)$$0, AxisAngle4f::new));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public static final Codec<Quaternionf> QUATERNIONF = withAlternative(QUATERNIONF_COMPONENTS, AXISANGLE4F
/*     */       
/* 110 */       .xmap(Quaternionf::new, AxisAngle4f::new)); public static Codec<Matrix4f> MATRIX4F; public static final Codec<Integer> NON_NEGATIVE_INT; public static final Codec<Integer> POSITIVE_INT; public static final Codec<Float> POSITIVE_FLOAT; public static final Codec<Pattern> PATTERN; public static <F, S> Codec<Either<F, S>> xor(Codec<F> $$0, Codec<S> $$1) { return new XorCodec<>($$0, $$1); } public static <P, I> Codec<I> intervalCodec(Codec<P> $$0, String $$1, String $$2, BiFunction<P, P, DataResult<I>> $$3, Function<I, P> $$4, Function<I, P> $$5) { Codec<I> $$6 = Codec.list($$0).comapFlatMap($$1 -> Util.fixedSize($$1, 2).flatMap(()), $$2 -> ImmutableList.of($$0.apply($$2), $$1.apply($$2))); Codec<I> $$7 = RecordCodecBuilder.create($$3 -> $$3.group((App)$$0.fieldOf($$1).forGetter(Pair::getFirst), (App)$$0.fieldOf($$2).forGetter(Pair::getSecond)).apply((Applicative)$$3, Pair::of)).comapFlatMap($$1 -> (DataResult)$$0.apply($$1.getFirst(), $$1.getSecond()), $$2 -> Pair.of($$0.apply($$2), $$1.apply($$2))); Codec<I> $$8 = withAlternative($$6, $$7); return Codec.either($$0, $$8).comapFlatMap($$1 -> (DataResult)$$1.map((), DataResult::success), $$2 -> { P $$3 = $$0.apply($$2); P $$4 = $$1.apply($$2); return Objects.equals($$3, $$4) ? Either.left($$3) : Either.right($$2); }); } public static <A> Codec.ResultFunction<A> orElsePartial(final A value) { return new Codec.ResultFunction<A>() { public <T> DataResult<Pair<A, T>> apply(DynamicOps<T> $$0, T $$1, DataResult<Pair<A, T>> $$2) { MutableObject<String> $$3 = new MutableObject(); Objects.requireNonNull($$3); Optional<Pair<A, T>> $$4 = $$2.resultOrPartial($$3::setValue); if ($$4.isPresent()) return $$2;  return DataResult.error(() -> "(" + (String)$$0.getValue() + " -> using default)", Pair.of(value, $$1)); } public <T> DataResult<T> coApply(DynamicOps<T> $$0, A $$1, DataResult<T> $$2) { return $$2; } public String toString() { return "OrElsePartial[" + value + "]"; } }
/*     */       ; } public static <E> Codec<E> idResolverCodec(ToIntFunction<E> $$0, IntFunction<E> $$1, int $$2) { return Codec.INT.flatXmap($$1 -> (DataResult)Optional.ofNullable($$0.apply($$1.intValue())).map(DataResult::success).orElseGet(()), $$2 -> { int $$3 = $$0.applyAsInt($$2); return ($$3 == $$1) ? DataResult.error(()) : DataResult.success(Integer.valueOf($$3)); }); } public static <E> Codec<E> stringResolverCodec(Function<E, String> $$0, Function<String, E> $$1) { return Codec.STRING.flatXmap($$1 -> (DataResult)Optional.ofNullable($$0.apply($$1)).map(DataResult::success).orElseGet(()), $$1 -> (DataResult)Optional.<String>ofNullable($$0.apply($$1)).map(DataResult::success).orElseGet(())); } public static <E> Codec<E> orCompressed(final Codec<E> normal, final Codec<E> compressed) { return new Codec<E>() { public <T> DataResult<T> encode(E $$0, DynamicOps<T> $$1, T $$2) { if ($$1.compressMaps()) return compressed.encode($$0, $$1, $$2);  return normal.encode($$0, $$1, $$2); } public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> $$0, T $$1) { if ($$0.compressMaps()) return compressed.decode($$0, $$1);  return normal.decode($$0, $$1); } public String toString() { return "" + normal + " orCompressed " + normal; } }
/*     */       ; } public static <E> MapCodec<E> orCompressed(final MapCodec<E> normal, final MapCodec<E> compressed) { return new MapCodec<E>() { public <T> RecordBuilder<T> encode(E $$0, DynamicOps<T> $$1, RecordBuilder<T> $$2) { if ($$1.compressMaps()) return compressed.encode($$0, $$1, $$2);  return normal.encode($$0, $$1, $$2); } public <T> DataResult<E> decode(DynamicOps<T> $$0, MapLike<T> $$1) { if ($$0.compressMaps()) return compressed.decode($$0, $$1);  return normal.decode($$0, $$1); } public <T> Stream<T> keys(DynamicOps<T> $$0) { return compressed.keys($$0); } public String toString() { return "" + normal + " orCompressed " + normal; } }
/* 113 */       ; } static { MATRIX4F = Codec.FLOAT.listOf().comapFlatMap($$0 -> Util.fixedSize($$0, 16).map(()), $$0 -> {
/*     */           FloatArrayList floatArrayList = new FloatArrayList(16);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           for (int $$2 = 0; $$2 < 16; $$2++) {
/*     */             floatArrayList.add($$0.getRowColumn($$2 >> 2, $$2 & 0x3));
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return (List)floatArrayList;
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 461 */     NON_NEGATIVE_INT = intRangeWithMessage(0, 2147483647, $$0 -> "Value must be non-negative: " + $$0);
/* 462 */     POSITIVE_INT = intRangeWithMessage(1, 2147483647, $$0 -> "Value must be positive: " + $$0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 477 */     POSITIVE_FLOAT = floatRangeMinExclusiveWithMessage(0.0F, Float.MAX_VALUE, $$0 -> "Value must be positive: " + $$0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 634 */     PATTERN = Codec.STRING.comapFlatMap($$0 -> {
/*     */           
/*     */           try { return DataResult.success(Pattern.compile($$0)); }
/* 637 */           catch (PatternSyntaxException $$1) { return DataResult.error(()); }  }Pattern::pattern); }
/*     */   public static <E> Codec<E> overrideLifecycle(Codec<E> $$0, final Function<E, Lifecycle> decodeLifecycle, final Function<E, Lifecycle> encodeLifecycle) { return $$0.mapResult(new Codec.ResultFunction<E>() { public <T> DataResult<Pair<E, T>> apply(DynamicOps<T> $$0, T $$1, DataResult<Pair<E, T>> $$2) { return $$2.result().map($$2 -> $$0.setLifecycle($$1.apply($$2.getFirst()))).orElse($$2); } public <T> DataResult<T> coApply(DynamicOps<T> $$0, E $$1, DataResult<T> $$2) { return $$2.setLifecycle(encodeLifecycle.apply($$1)); } public String toString() { return "WithLifecycle[" + decodeLifecycle + " " + encodeLifecycle + "]"; } }
/*     */       ); } public static <F, S> EitherCodec<F, S> either(Codec<F> $$0, Codec<S> $$1) { return new EitherCodec<>($$0, $$1); } public static final class EitherCodec<F, S> implements Codec<Either<F, S>> {
/*     */     private final Codec<F> first; private final Codec<S> second; public EitherCodec(Codec<F> $$0, Codec<S> $$1) { this.first = $$0; this.second = $$1; } public <T> DataResult<Pair<Either<F, S>, T>> decode(DynamicOps<T> $$0, T $$1) { DataResult<Pair<Either<F, S>, T>> $$2 = this.first.decode($$0, $$1).map($$0 -> $$0.mapFirst(Either::left)); if ($$2.error().isEmpty()) return $$2;  DataResult<Pair<Either<F, S>, T>> $$3 = this.second.decode($$0, $$1).map($$0 -> $$0.mapFirst(Either::right)); if ($$3.error().isEmpty()) return $$3;  return $$2.apply2(($$0, $$1) -> $$1, $$3); } public <T> DataResult<T> encode(Either<F, S> $$0, DynamicOps<T> $$1, T $$2) { return (DataResult<T>)$$0.map($$2 -> this.first.encode($$2, $$0, $$1), $$2 -> this.second.encode($$2, $$0, $$1)); } public boolean equals(Object $$0) { if (this == $$0) return true;  if ($$0 == null || getClass() != $$0.getClass()) return false;  EitherCodec<?, ?> $$1 = (EitherCodec<?, ?>)$$0; return (Objects.equals(this.first, $$1.first) && Objects.equals(this.second, $$1.second)); } public int hashCode() { return Objects.hash(new Object[] { this.first, this.second }); } public String toString() { return "EitherCodec[" + this.first + ", " + this.second + "]"; } } public static <K, V> StrictUnboundedMapCodec<K, V> strictUnboundedMap(Codec<K> $$0, Codec<V> $$1) { return new StrictUnboundedMapCodec<>($$0, $$1); } public static final class StrictUnboundedMapCodec<K, V> extends Record implements Codec<Map<K, V>>, BaseMapCodec<K, V> {
/*     */     private final Codec<K> keyCodec; private final Codec<V> elementCodec; public StrictUnboundedMapCodec(Codec<K> $$0, Codec<V> $$1) { this.keyCodec = $$0; this.elementCodec = $$1; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #358	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec<TK;TV;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #358	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	8	0	this	Lnet/minecraft/util/ExtraCodecs$StrictUnboundedMapCodec<TK;TV;>; } public Codec<K> keyCodec() { return this.keyCodec; } public Codec<V> elementCodec() { return this.elementCodec; } public <T> DataResult<Map<K, V>> decode(DynamicOps<T> $$0, MapLike<T> $$1) { ImmutableMap.Builder<K, V> $$2 = ImmutableMap.builder(); for (Pair<T, T> $$3 : (Iterable<Pair<T, T>>)$$1.entries().toList()) { DataResult<K> $$4 = keyCodec().parse($$0, $$3.getFirst()); DataResult<V> $$5 = elementCodec().parse($$0, $$3.getSecond()); DataResult<Pair<K, V>> $$6 = $$4.apply2stable(Pair::of, $$5); if ($$6.error().isPresent()) return DataResult.error(() -> { String $$4; DataResult.PartialResult<Pair<K, V>> $$2 = $$0.error().get(); if ($$1.result().isPresent()) { String $$3 = "Map entry '" + $$1.result().get() + "' : " + $$2.message(); } else { $$4 = $$2.message(); }  return $$4; });  if ($$6.result().isPresent()) { Pair<K, V> $$7 = $$6.result().get(); $$2.put($$7.getFirst(), $$7.getSecond()); continue; }  return DataResult.error(() -> "Empty or invalid map contents are not allowed"); }  ImmutableMap immutableMap = $$2.build(); return DataResult.success(immutableMap); } public <T> DataResult<Pair<Map<K, V>, T>> decode(DynamicOps<T> $$0, T $$1) { return $$0.getMap($$1).setLifecycle(Lifecycle.stable()).flatMap($$1 -> decode($$0, $$1)).map($$1 -> Pair.of($$1, $$0)); } public <T> DataResult<T> encode(Map<K, V> $$0, DynamicOps<T> $$1, T $$2) { return encode($$0, $$1, $$1.mapBuilder()).build($$2); } public String toString() { return "StrictUnboundedMapCodec[" + this.keyCodec + " -> " + this.elementCodec + "]"; } } private static final class XorCodec<F, S> extends Record implements Codec<Either<F, S>> {
/*     */     private final Codec<F> first; private final Codec<S> second; XorCodec(Codec<F> $$0, Codec<S> $$1) { this.first = $$0; this.second = $$1; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/ExtraCodecs$XorCodec;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #406	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/util/ExtraCodecs$XorCodec;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/util/ExtraCodecs$XorCodec<TF;TS;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/ExtraCodecs$XorCodec;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #406	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/util/ExtraCodecs$XorCodec;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	8	0	this	Lnet/minecraft/util/ExtraCodecs$XorCodec<TF;TS;>; } public Codec<F> first() { return this.first; } public Codec<S> second() { return this.second; } public <T> DataResult<Pair<Either<F, S>, T>> decode(DynamicOps<T> $$0, T $$1) { DataResult<Pair<Either<F, S>, T>> $$2 = this.first.decode($$0, $$1).map($$0 -> $$0.mapFirst(Either::left)); DataResult<Pair<Either<F, S>, T>> $$3 = this.second.decode($$0, $$1).map($$0 -> $$0.mapFirst(Either::right)); Optional<Pair<Either<F, S>, T>> $$4 = $$2.result(); Optional<Pair<Either<F, S>, T>> $$5 = $$3.result(); if ($$4.isPresent() && $$5.isPresent()) return DataResult.error(() -> "Both alternatives read successfully, can not pick the correct one; first: " + $$0.get() + " second: " + $$1.get(), $$4.get());  if ($$4.isPresent()) return $$2;  if ($$5.isPresent()) return $$3;  return $$2.apply2(($$0, $$1) -> $$1, $$3); } public <T> DataResult<T> encode(Either<F, S> $$0, DynamicOps<T> $$1, T $$2) { return (DataResult<T>)$$0.map($$2 -> this.first.encode($$2, $$0, $$1), $$2 -> this.second.encode($$2, $$0, $$1)); } public String toString() { return "XorCodec[" + this.first + ", " + this.second + "]"; }
/* 643 */   } public static <T> Codec<T> validate(Codec<T> $$0, Function<T, DataResult<T>> $$1) { if ($$0 instanceof MapCodec.MapCodecCodec) { MapCodec.MapCodecCodec<T> $$2 = (MapCodec.MapCodecCodec<T>)$$0; return validate($$2.codec(), $$1).codec(); }  return $$0.flatXmap($$1, $$1); } public static <A> Codec<A> catchDecoderException(final Codec<A> codec) { return Codec.of((Encoder)codec, new Decoder<A>()
/*     */         {
/*     */           public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> $$0, T $$1) {
/*     */             
/* 647 */             try { return codec.decode($$0, $$1); }
/* 648 */             catch (Exception $$2)
/* 649 */             { return DataResult.error(() -> "Caught exception decoding " + $$0 + ": " + $$1.getMessage()); } 
/*     */           }
/*     */         }); } public static <T> MapCodec<T> validate(MapCodec<T> $$0, Function<T, DataResult<T>> $$1) { return $$0.flatXmap($$1, $$1); } private static Codec<Integer> intRangeWithMessage(int $$0, int $$1, Function<Integer, String> $$2) { return validate((Codec<Integer>)Codec.INT, $$3 -> ($$3.compareTo(Integer.valueOf($$0)) >= 0 && $$3.compareTo(Integer.valueOf($$1)) <= 0) ? DataResult.success($$3) : DataResult.error(())); } public static Codec<Integer> intRange(int $$0, int $$1) { return intRangeWithMessage($$0, $$1, $$2 -> "Value must be within range [" + $$0 + ";" + $$1 + "]: " + $$2); } private static Codec<Float> floatRangeMinExclusiveWithMessage(float $$0, float $$1, Function<Float, String> $$2) { return validate((Codec<Float>)Codec.FLOAT, $$3 -> ($$3.compareTo(Float.valueOf($$0)) > 0 && $$3.compareTo(Float.valueOf($$1)) <= 0) ? DataResult.success($$3) : DataResult.error(())); } public static <T> Codec<List<T>> nonEmptyList(Codec<List<T>> $$0) { return validate($$0, $$0 -> $$0.isEmpty() ? DataResult.error(()) : DataResult.success($$0)); } public static <T> Codec<HolderSet<T>> nonEmptyHolderSet(Codec<HolderSet<T>> $$0) { return validate($$0, $$0 -> $$0.unwrap().right().filter(List::isEmpty).isPresent() ? DataResult.error(()) : DataResult.success($$0)); } public static <T> Codec<T> recursive(String $$0, Function<Codec<T>, Codec<T>> $$1) { return new RecursiveCodec<>($$0, $$1); } public static <A> Codec<A> lazyInitializedCodec(Supplier<Codec<A>> $$0) { return new RecursiveCodec<>($$0.toString(), $$1 -> (Codec)$$0.get()); } private static class RecursiveCodec<T> implements Codec<T> {
/*     */     private final String name; private final Supplier<Codec<T>> wrapped; RecursiveCodec(String $$0, Function<Codec<T>, Codec<T>> $$1) { this.name = $$0; this.wrapped = (Supplier<Codec<T>>)Suppliers.memoize(() -> (Codec)$$0.apply(this)); } public <S> DataResult<Pair<T, S>> decode(DynamicOps<S> $$0, S $$1) { return ((Codec)this.wrapped.get()).decode($$0, $$1); } public <S> DataResult<S> encode(T $$0, DynamicOps<S> $$1, S $$2) { return ((Codec)this.wrapped.get()).encode($$0, $$1, $$2); } public String toString() { return "RecursiveCodec[" + this.name + "]"; } } public static <A> MapCodec<Optional<A>> strictOptionalField(Codec<A> $$0, String $$1) { return new StrictOptionalFieldCodec<>($$1, $$0); } public static <A> MapCodec<A> strictOptionalField(Codec<A> $$0, String $$1, A $$2) { return strictOptionalField($$0, $$1).xmap($$1 -> $$1.orElse($$0), $$1 -> Objects.equals($$1, $$0) ? Optional.empty() : Optional.<Object>of($$1)); } private static final class StrictOptionalFieldCodec<A> extends MapCodec<Optional<A>> {
/*     */     private final String name; private final Codec<A> elementCodec; public StrictOptionalFieldCodec(String $$0, Codec<A> $$1) { this.name = $$0; this.elementCodec = $$1; } public <T> DataResult<Optional<A>> decode(DynamicOps<T> $$0, MapLike<T> $$1) { T $$2 = (T)$$1.get(this.name); if ($$2 == null) return DataResult.success(Optional.empty());  return this.elementCodec.parse($$0, $$2).map(Optional::of); } public <T> RecordBuilder<T> encode(Optional<A> $$0, DynamicOps<T> $$1, RecordBuilder<T> $$2) { if ($$0.isPresent()) return $$2.add(this.name, this.elementCodec.encodeStart($$1, $$0.get()));  return $$2; } public <T> Stream<T> keys(DynamicOps<T> $$0) { return Stream.of((T)$$0.createString(this.name)); } public boolean equals(Object $$0) { if (this == $$0) return true;  if ($$0 instanceof StrictOptionalFieldCodec) { StrictOptionalFieldCodec<?> $$1 = (StrictOptionalFieldCodec)$$0; return (Objects.equals(this.name, $$1.name) && Objects.equals(this.elementCodec, $$1.elementCodec)); }  return false; }
/*     */     public int hashCode() { return Objects.hash(new Object[] { this.name, this.elementCodec }); }
/*     */     public String toString() { return "StrictOptionalFieldCodec[" + this.name + ": " + this.elementCodec + "]"; } }
/*     */   public static <E> MapCodec<E> retrieveContext(final Function<DynamicOps<?>, DataResult<E>> getter) { class ContextRetrievalCodec extends MapCodec<E> {
/*     */       public <T> RecordBuilder<T> encode(E $$0, DynamicOps<T> $$1, RecordBuilder<T> $$2) { return $$2; }
/*     */       public <T> DataResult<E> decode(DynamicOps<T> $$0, MapLike<T> $$1) { return getter.apply($$0); }
/*     */       public String toString() { return "ContextRetrievalCodec[" + getter + "]"; }
/*     */       public <T> Stream<T> keys(DynamicOps<T> $$0) { return Stream.empty(); } }; return new ContextRetrievalCodec(); }
/*     */   public static <E, L extends Collection<E>, T> Function<L, DataResult<L>> ensureHomogenous(Function<E, T> $$0) { return $$1 -> { Iterator<E> $$2 = $$1.iterator(); if ($$2.hasNext()) { T $$3 = (T)$$0.apply($$2.next()); while ($$2.hasNext()) { E $$4 = $$2.next(); T $$5 = $$0.apply($$4); if ($$5 != $$3) return DataResult.error(());  }  }  return DataResult.success($$1, Lifecycle.stable()); }; }
/* 662 */   public static Codec<TemporalAccessor> temporalCodec(DateTimeFormatter $$0) { Objects.requireNonNull($$0); return Codec.STRING.comapFlatMap($$1 -> { try { return DataResult.success($$0.parse($$1)); } catch (Exception $$2) { Objects.requireNonNull($$2); return DataResult.error($$2::getMessage); }  }$$0::format); }
/*     */ 
/*     */   
/* 665 */   public static final Codec<Instant> INSTANT_ISO8601 = temporalCodec(DateTimeFormatter.ISO_INSTANT).xmap(Instant::from, Function.identity()); public static final Codec<byte[]> BASE64_STRING; public static final Codec<String> ESCAPED_STRING; public static final Codec<TagOrElementLocation> TAG_OR_ELEMENT_ID; public static final Function<Optional<Long>, OptionalLong> toOptionalLong; public static final Function<OptionalLong, Optional<Long>> fromOptionalLong; public static final Codec<BitSet> BIT_SET; private static final Codec<Property> PROPERTY; @VisibleForTesting
/*     */   public static final Codec<PropertyMap> PROPERTY_MAP; private static final MapCodec<GameProfile> GAME_PROFILE_WITHOUT_PROPERTIES; public static final Codec<GameProfile> GAME_PROFILE; public static final Codec<String> NON_EMPTY_STRING; public static final Codec<Integer> CODEPOINT; public static Codec<String> RESOURCE_PATH_CODEC;
/* 667 */   static { BASE64_STRING = Codec.STRING.comapFlatMap($$0 -> {
/*     */           
/*     */           try {
/*     */             return DataResult.success(Base64.getDecoder().decode($$0));
/* 671 */           } catch (IllegalArgumentException $$1) {
/*     */             return DataResult.error(());
/*     */           } 
/*     */         }$$0 -> Base64.getEncoder().encodeToString($$0));
/*     */ 
/*     */ 
/*     */     
/* 678 */     ESCAPED_STRING = Codec.STRING.comapFlatMap($$0 -> DataResult.success(StringEscapeUtils.unescapeJava($$0)), StringEscapeUtils::escapeJava);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 691 */     TAG_OR_ELEMENT_ID = Codec.STRING.comapFlatMap($$0 -> $$0.startsWith("#") ? ResourceLocation.read($$0.substring(1)).map(()) : ResourceLocation.read($$0).map(()), TagOrElementLocation::decoratedId);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 698 */     toOptionalLong = ($$0 -> (OptionalLong)$$0.map(OptionalLong::of).orElseGet(OptionalLong::empty));
/* 699 */     fromOptionalLong = ($$0 -> $$0.isPresent() ? Optional.<Long>of(Long.valueOf($$0.getAsLong())) : Optional.empty());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 705 */     BIT_SET = Codec.LONG_STREAM.xmap($$0 -> BitSet.valueOf($$0.toArray()), $$0 -> Arrays.stream($$0.toLongArray()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 710 */     PROPERTY = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.STRING.fieldOf("name").forGetter(Property::name), (App)Codec.STRING.fieldOf("value").forGetter(Property::value), (App)Codec.STRING.optionalFieldOf("signature").forGetter(())).apply((Applicative)$$0, ()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 720 */     PROPERTY_MAP = Codec.either((Codec)Codec.unboundedMap((Codec)Codec.STRING, Codec.STRING.listOf()), PROPERTY.listOf()).xmap($$0 -> {
/*     */           PropertyMap $$1 = new PropertyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           $$0.ifLeft(()).ifRight(());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return $$1;
/*     */         }$$0 -> Either.right($$0.values().stream().toList()));
/*     */ 
/*     */ 
/*     */     
/* 736 */     GAME_PROFILE_WITHOUT_PROPERTIES = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)UUIDUtil.AUTHLIB_CODEC.fieldOf("id").forGetter(GameProfile::getId), (App)Codec.STRING.fieldOf("name").forGetter(GameProfile::getName)).apply((Applicative)$$0, GameProfile::new));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 741 */     GAME_PROFILE = RecordCodecBuilder.create($$0 -> $$0.group((App)GAME_PROFILE_WITHOUT_PROPERTIES.forGetter(Function.identity()), (App)PROPERTY_MAP.optionalFieldOf("properties", new PropertyMap()).forGetter(GameProfile::getProperties)).apply((Applicative)$$0, ()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 749 */     NON_EMPTY_STRING = validate((Codec<String>)Codec.STRING, $$0 -> $$0.isEmpty() ? DataResult.error(()) : DataResult.success($$0));
/*     */     
/* 751 */     CODEPOINT = Codec.STRING.comapFlatMap($$0 -> { int[] $$1 = $$0.codePoints().toArray(); return ($$1.length != 1) ? DataResult.error(()) : DataResult.success(Integer.valueOf($$1[0])); }Character::toString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 773 */     RESOURCE_PATH_CODEC = validate((Codec<String>)Codec.STRING, $$0 -> !ResourceLocation.isValidPath($$0) ? DataResult.error(()) : DataResult.success($$0)); } public static final class TagOrElementLocation extends Record {
/*     */     private final ResourceLocation id; private final boolean tag; public TagOrElementLocation(ResourceLocation $$0, boolean $$1) { this.id = $$0; this.tag = $$1; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/ExtraCodecs$TagOrElementLocation;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #680	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/util/ExtraCodecs$TagOrElementLocation; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/util/ExtraCodecs$TagOrElementLocation;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #680	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/util/ExtraCodecs$TagOrElementLocation;
/*     */       //   0	8	1	$$0	Ljava/lang/Object; }
/*     */     public ResourceLocation id() { return this.id; }
/*     */     public boolean tag() { return this.tag; }
/*     */     public String toString() { return decoratedId(); }
/*     */     private String decoratedId() { return this.tag ? ("#" + this.id) : this.id.toString(); } }
/*     */   public static MapCodec<OptionalLong> asOptionalLong(MapCodec<Optional<Long>> $$0) { return $$0.xmap(toOptionalLong, fromOptionalLong); }
/*     */   public static Codec<String> sizeLimitedString(int $$0, int $$1) { return validate((Codec<String>)Codec.STRING, $$2 -> { int $$3 = $$2.length(); return ($$3 < $$0) ? DataResult.error(()) : (($$3 > $$1) ? DataResult.error(()) : DataResult.success($$2)); }); }
/* 781 */   public static <T> Codec<T> withAlternative(Codec<T> $$0, Codec<? extends T> $$1) { return Codec.either($$0, $$1)
/*     */ 
/*     */       
/* 784 */       .xmap($$0 -> $$0.map((), ()), Either::left); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T, U> Codec<T> withAlternative(Codec<T> $$0, Codec<U> $$1, Function<U, T> $$2) {
/* 791 */     return Codec.either($$0, $$1)
/*     */ 
/*     */       
/* 794 */       .xmap($$1 -> $$1.map((), $$0), Either::left);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Codec<Object2BooleanMap<T>> object2BooleanMap(Codec<T> $$0) {
/* 801 */     return Codec.unboundedMap($$0, (Codec)Codec.BOOL).xmap(it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap::new, it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap::new);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static <K, V> MapCodec<V> dispatchOptionalValue(final String typeKey, final String valueKey, final Codec<K> typeCodec, final Function<? super V, ? extends K> typeGetter, final Function<? super K, ? extends Codec<? extends V>> valueCodec) {
/* 807 */     return new MapCodec<V>()
/*     */       {
/*     */         public <T> Stream<T> keys(DynamicOps<T> $$0) {
/* 810 */           return Stream.of((T[])new Object[] { $$0.createString(this.val$typeKey), $$0.createString(this.val$valueKey) });
/*     */         }
/*     */ 
/*     */         
/*     */         public <T> DataResult<V> decode(DynamicOps<T> $$0, MapLike<T> $$1) {
/* 815 */           T $$2 = (T)$$1.get(typeKey);
/* 816 */           if ($$2 == null) {
/* 817 */             return DataResult.error(() -> "Missing \"" + $$0 + "\" in: " + $$1);
/*     */           }
/* 819 */           return typeCodec.decode($$0, $$2).flatMap($$4 -> {
/*     */                 Objects.requireNonNull($$2);
/*     */                 T $$5 = Objects.requireNonNullElseGet((T)$$0.get($$1), $$2::emptyMap);
/*     */                 return ((Codec)$$3.apply($$4.getFirst())).decode($$2, $$5).map(Pair::getFirst);
/*     */               });
/*     */         }
/*     */         
/*     */         public <T> RecordBuilder<T> encode(V $$0, DynamicOps<T> $$1, RecordBuilder<T> $$2) {
/* 827 */           K $$3 = typeGetter.apply($$0);
/* 828 */           $$2.add(typeKey, typeCodec.encodeStart($$1, $$3));
/* 829 */           DataResult<T> $$4 = encode(valueCodec.apply($$3), $$0, $$1);
/* 830 */           if ($$4.result().isEmpty() || !Objects.equals($$4.result().get(), $$1.emptyMap())) {
/* 831 */             $$2.add(valueKey, $$4);
/*     */           }
/* 833 */           return $$2;
/*     */         }
/*     */ 
/*     */         
/*     */         private <T, V2 extends V> DataResult<T> encode(Codec<V2> $$0, V $$1, DynamicOps<T> $$2) {
/* 838 */           return $$0.encodeStart($$2, $$1);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ExtraCodecs.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */