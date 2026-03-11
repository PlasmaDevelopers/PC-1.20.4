/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.RecordBuilder;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrayList;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteList;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrayList;
/*     */ import it.unimi.dsi.fastutil.longs.LongList;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.LongStream;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JavaOps
/*     */   implements DynamicOps<Object>
/*     */ {
/*  33 */   public static final JavaOps INSTANCE = new JavaOps();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object empty() {
/*  40 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object emptyMap() {
/*  45 */     return Map.of();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object emptyList() {
/*  50 */     return List.of();
/*     */   }
/*     */ 
/*     */   
/*     */   public <U> U convertTo(DynamicOps<U> $$0, Object $$1) {
/*  55 */     if ($$1 == null) {
/*  56 */       return (U)$$0.empty();
/*     */     }
/*  58 */     if ($$1 instanceof Map) {
/*  59 */       return (U)convertMap($$0, $$1);
/*     */     }
/*  61 */     if ($$1 instanceof ByteList) { ByteList $$2 = (ByteList)$$1;
/*  62 */       return (U)$$0.createByteList(ByteBuffer.wrap($$2.toByteArray())); }
/*     */     
/*  64 */     if ($$1 instanceof IntList) { IntList $$3 = (IntList)$$1;
/*  65 */       return (U)$$0.createIntList($$3.intStream()); }
/*     */     
/*  67 */     if ($$1 instanceof LongList) { LongList $$4 = (LongList)$$1;
/*  68 */       return (U)$$0.createLongList($$4.longStream()); }
/*     */     
/*  70 */     if ($$1 instanceof List) {
/*  71 */       return (U)convertList($$0, $$1);
/*     */     }
/*  73 */     if ($$1 instanceof String) { String $$5 = (String)$$1;
/*  74 */       return (U)$$0.createString($$5); }
/*     */     
/*  76 */     if ($$1 instanceof Boolean) { Boolean $$6 = (Boolean)$$1;
/*  77 */       return (U)$$0.createBoolean($$6.booleanValue()); }
/*     */     
/*  79 */     if ($$1 instanceof Byte) { Byte $$7 = (Byte)$$1;
/*  80 */       return (U)$$0.createByte($$7.byteValue()); }
/*     */     
/*  82 */     if ($$1 instanceof Short) { Short $$8 = (Short)$$1;
/*  83 */       return (U)$$0.createShort($$8.shortValue()); }
/*     */     
/*  85 */     if ($$1 instanceof Integer) { Integer $$9 = (Integer)$$1;
/*  86 */       return (U)$$0.createInt($$9.intValue()); }
/*     */     
/*  88 */     if ($$1 instanceof Long) { Long $$10 = (Long)$$1;
/*  89 */       return (U)$$0.createLong($$10.longValue()); }
/*     */     
/*  91 */     if ($$1 instanceof Float) { Float $$11 = (Float)$$1;
/*  92 */       return (U)$$0.createFloat($$11.floatValue()); }
/*     */     
/*  94 */     if ($$1 instanceof Double) { Double $$12 = (Double)$$1;
/*  95 */       return (U)$$0.createDouble($$12.doubleValue()); }
/*     */     
/*  97 */     if ($$1 instanceof Number) { Number $$13 = (Number)$$1;
/*  98 */       return (U)$$0.createNumeric($$13); }
/*     */     
/* 100 */     throw new IllegalStateException("Don't know how to convert " + $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Number> getNumberValue(Object $$0) {
/* 105 */     if ($$0 instanceof Number) { Number $$1 = (Number)$$0;
/* 106 */       return DataResult.success($$1); }
/*     */     
/* 108 */     return DataResult.error(() -> "Not a number: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createNumeric(Number $$0) {
/* 113 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createByte(byte $$0) {
/* 118 */     return Byte.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createShort(short $$0) {
/* 123 */     return Short.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createInt(int $$0) {
/* 128 */     return Integer.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createLong(long $$0) {
/* 133 */     return Long.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createFloat(float $$0) {
/* 138 */     return Float.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createDouble(double $$0) {
/* 143 */     return Double.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Boolean> getBooleanValue(Object $$0) {
/* 148 */     if ($$0 instanceof Boolean) { Boolean $$1 = (Boolean)$$0;
/* 149 */       return DataResult.success($$1); }
/*     */     
/* 151 */     return DataResult.error(() -> "Not a boolean: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createBoolean(boolean $$0) {
/* 156 */     return Boolean.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<String> getStringValue(Object $$0) {
/* 161 */     if ($$0 instanceof String) { String $$1 = (String)$$0;
/* 162 */       return DataResult.success($$1); }
/*     */     
/* 164 */     return DataResult.error(() -> "Not a string: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createString(String $$0) {
/* 169 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Object> mergeToList(Object $$0, Object $$1) {
/* 174 */     if ($$0 == empty()) {
/* 175 */       return DataResult.success(List.of($$1));
/*     */     }
/* 177 */     if ($$0 instanceof List) { List<?> $$2 = (List)$$0;
/* 178 */       if ($$2.isEmpty()) {
/* 179 */         return DataResult.success(List.of($$1));
/*     */       }
/* 181 */       return DataResult.success(ImmutableList.builder().addAll($$2).add($$1).build()); }
/*     */     
/* 183 */     return DataResult.error(() -> "Not a list: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Object> mergeToList(Object $$0, List<Object> $$1) {
/* 188 */     if ($$0 == empty()) {
/* 189 */       return DataResult.success($$1);
/*     */     }
/* 191 */     if ($$0 instanceof List) { List<?> $$2 = (List)$$0;
/* 192 */       if ($$2.isEmpty()) {
/* 193 */         return DataResult.success($$1);
/*     */       }
/* 195 */       return DataResult.success(ImmutableList.builder().addAll($$2).addAll($$1).build()); }
/*     */     
/* 197 */     return DataResult.error(() -> "Not a list: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Object> mergeToMap(Object $$0, Object $$1, Object $$2) {
/* 202 */     if ($$0 == empty()) {
/* 203 */       return DataResult.success(Map.of($$1, $$2));
/*     */     }
/* 205 */     if ($$0 instanceof Map) { Map<?, ?> $$3 = (Map<?, ?>)$$0;
/* 206 */       if ($$3.isEmpty()) {
/* 207 */         return DataResult.success(Map.of($$1, $$2));
/*     */       }
/* 209 */       ImmutableMap.Builder<Object, Object> $$4 = ImmutableMap.builderWithExpectedSize($$3.size() + 1);
/* 210 */       $$4.putAll($$3);
/* 211 */       $$4.put($$1, $$2);
/* 212 */       return DataResult.success($$4.buildKeepingLast()); }
/*     */     
/* 214 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Object> mergeToMap(Object $$0, Map<Object, Object> $$1) {
/* 219 */     if ($$0 == empty()) {
/* 220 */       return DataResult.success($$1);
/*     */     }
/* 222 */     if ($$0 instanceof Map) { Map<?, ?> $$2 = (Map<?, ?>)$$0;
/* 223 */       if ($$2.isEmpty()) {
/* 224 */         return DataResult.success($$1);
/*     */       }
/* 226 */       ImmutableMap.Builder<Object, Object> $$3 = ImmutableMap.builderWithExpectedSize($$2.size() + $$1.size());
/* 227 */       $$3.putAll($$2);
/* 228 */       $$3.putAll($$1);
/* 229 */       return DataResult.success($$3.buildKeepingLast()); }
/*     */     
/* 231 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */   
/*     */   private static Map<Object, Object> mapLikeToMap(MapLike<Object> $$0) {
/* 235 */     return (Map<Object, Object>)$$0.entries().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Object> mergeToMap(Object $$0, MapLike<Object> $$1) {
/* 240 */     if ($$0 == empty()) {
/* 241 */       return DataResult.success(mapLikeToMap($$1));
/*     */     }
/* 243 */     if ($$0 instanceof Map) { Map<?, ?> $$2 = (Map<?, ?>)$$0;
/* 244 */       if ($$2.isEmpty()) {
/* 245 */         return DataResult.success(mapLikeToMap($$1));
/*     */       }
/*     */       
/* 248 */       ImmutableMap.Builder<Object, Object> $$3 = ImmutableMap.builderWithExpectedSize($$2.size());
/* 249 */       $$3.putAll($$2);
/* 250 */       $$1.entries().forEach($$1 -> $$0.put($$1.getFirst(), $$1.getSecond()));
/* 251 */       return DataResult.success($$3.buildKeepingLast()); }
/*     */     
/* 253 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */   
/*     */   static Stream<Pair<Object, Object>> getMapEntries(Map<?, ?> $$0) {
/* 257 */     return $$0.entrySet().stream().map($$0 -> Pair.of($$0.getKey(), $$0.getValue()));
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Stream<Pair<Object, Object>>> getMapValues(Object $$0) {
/* 262 */     if ($$0 instanceof Map) { Map<?, ?> $$1 = (Map<?, ?>)$$0;
/* 263 */       return DataResult.success(getMapEntries($$1)); }
/*     */     
/* 265 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Consumer<BiConsumer<Object, Object>>> getMapEntries(Object $$0) {
/* 270 */     if ($$0 instanceof Map) { Map<?, ?> $$1 = (Map<?, ?>)$$0;
/* 271 */       Objects.requireNonNull($$1); return DataResult.success($$1::forEach); }
/*     */     
/* 273 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createMap(Stream<Pair<Object, Object>> $$0) {
/* 278 */     return $$0.collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<MapLike<Object>> getMap(Object $$0) {
/* 283 */     if ($$0 instanceof Map) { final Map<?, ?> map = (Map<?, ?>)$$0;
/* 284 */       return DataResult.success(new MapLike<Object>()
/*     */           {
/*     */             @Nullable
/*     */             public Object get(Object $$0)
/*     */             {
/* 289 */               return map.get($$0);
/*     */             }
/*     */ 
/*     */             
/*     */             @Nullable
/*     */             public Object get(String $$0) {
/* 295 */               return map.get($$0);
/*     */             }
/*     */ 
/*     */             
/*     */             public Stream<Pair<Object, Object>> entries() {
/* 300 */               return JavaOps.getMapEntries(map);
/*     */             }
/*     */ 
/*     */             
/*     */             public String toString() {
/* 305 */               return "MapLike[" + map + "]";
/*     */             }
/*     */           }); }
/*     */ 
/*     */     
/* 310 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createMap(Map<Object, Object> $$0) {
/* 315 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Stream<Object>> getStream(Object $$0) {
/* 320 */     if ($$0 instanceof List) { List<?> $$1 = (List)$$0;
/* 321 */       return DataResult.success($$1.stream().map($$0 -> $$0)); }
/*     */     
/* 323 */     return DataResult.error(() -> "Not an list: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Consumer<Consumer<Object>>> getList(Object $$0) {
/* 328 */     if ($$0 instanceof List) { List<?> $$1 = (List)$$0;
/* 329 */       Objects.requireNonNull($$1); return DataResult.success($$1::forEach); }
/*     */     
/* 331 */     return DataResult.error(() -> "Not an list: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createList(Stream<Object> $$0) {
/* 336 */     return $$0.toList();
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<ByteBuffer> getByteBuffer(Object $$0) {
/* 341 */     if ($$0 instanceof ByteList) { ByteList $$1 = (ByteList)$$0;
/* 342 */       return DataResult.success(ByteBuffer.wrap($$1.toByteArray())); }
/*     */     
/* 344 */     return DataResult.error(() -> "Not a byte list: " + $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object createByteList(ByteBuffer $$0) {
/* 350 */     ByteBuffer $$1 = $$0.duplicate().clear();
/* 351 */     ByteArrayList $$2 = new ByteArrayList();
/* 352 */     $$2.size($$1.capacity());
/* 353 */     $$1.get(0, $$2.elements(), 0, $$2.size());
/* 354 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<IntStream> getIntStream(Object $$0) {
/* 359 */     if ($$0 instanceof IntList) { IntList $$1 = (IntList)$$0;
/* 360 */       return DataResult.success($$1.intStream()); }
/*     */     
/* 362 */     return DataResult.error(() -> "Not an int list: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createIntList(IntStream $$0) {
/* 367 */     return IntArrayList.toList($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<LongStream> getLongStream(Object $$0) {
/* 372 */     if ($$0 instanceof LongList) { LongList $$1 = (LongList)$$0;
/* 373 */       return DataResult.success($$1.longStream()); }
/*     */     
/* 375 */     return DataResult.error(() -> "Not a long list: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object createLongList(LongStream $$0) {
/* 380 */     return LongArrayList.toList($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object remove(Object $$0, String $$1) {
/* 385 */     if ($$0 instanceof Map) { Map<?, ?> $$2 = (Map<?, ?>)$$0;
/* 386 */       Map<Object, Object> $$3 = new LinkedHashMap<>($$2);
/* 387 */       $$3.remove($$1);
/* 388 */       return DataResult.success(Map.copyOf($$3)); }
/*     */     
/* 390 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecordBuilder<Object> mapBuilder() {
/* 395 */     return (RecordBuilder<Object>)new FixedMapBuilder(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 400 */     return "Java";
/*     */   }
/*     */   
/*     */   private static final class FixedMapBuilder<T> extends RecordBuilder.AbstractUniversalBuilder<T, ImmutableMap.Builder<T, T>> {
/*     */     public FixedMapBuilder(DynamicOps<T> $$0) {
/* 405 */       super($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected ImmutableMap.Builder<T, T> initBuilder() {
/* 410 */       return ImmutableMap.builder();
/*     */     }
/*     */ 
/*     */     
/*     */     protected ImmutableMap.Builder<T, T> append(T $$0, T $$1, ImmutableMap.Builder<T, T> $$2) {
/* 415 */       return $$2.put($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     protected DataResult<T> build(ImmutableMap.Builder<T, T> $$0, T $$1) {
/*     */       ImmutableMap<T, T> $$2;
/*     */       try {
/* 422 */         $$2 = $$0.buildOrThrow();
/* 423 */       } catch (IllegalArgumentException $$3) {
/* 424 */         return DataResult.error(() -> "Can't build map: " + $$0.getMessage());
/*     */       } 
/* 426 */       return ops().mergeToMap($$1, (Map)$$2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\JavaOps.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */