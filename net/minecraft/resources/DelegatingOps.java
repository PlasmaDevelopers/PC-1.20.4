/*     */ package net.minecraft.resources;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.ListBuilder;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.RecordBuilder;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.LongStream;
/*     */ import java.util.stream.Stream;
/*     */ 
/*     */ public abstract class DelegatingOps<T>
/*     */   implements DynamicOps<T> {
/*     */   protected final DynamicOps<T> delegate;
/*     */   
/*     */   protected DelegatingOps(DynamicOps<T> $$0) {
/*  22 */     this.delegate = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public T empty() {
/*  27 */     return (T)this.delegate.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public <U> U convertTo(DynamicOps<U> $$0, T $$1) {
/*  32 */     return (U)this.delegate.convertTo($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Number> getNumberValue(T $$0) {
/*  37 */     return this.delegate.getNumberValue($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createNumeric(Number $$0) {
/*  42 */     return (T)this.delegate.createNumeric($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createByte(byte $$0) {
/*  47 */     return (T)this.delegate.createByte($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createShort(short $$0) {
/*  52 */     return (T)this.delegate.createShort($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createInt(int $$0) {
/*  57 */     return (T)this.delegate.createInt($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createLong(long $$0) {
/*  62 */     return (T)this.delegate.createLong($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createFloat(float $$0) {
/*  67 */     return (T)this.delegate.createFloat($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createDouble(double $$0) {
/*  72 */     return (T)this.delegate.createDouble($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Boolean> getBooleanValue(T $$0) {
/*  77 */     return this.delegate.getBooleanValue($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createBoolean(boolean $$0) {
/*  82 */     return (T)this.delegate.createBoolean($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<String> getStringValue(T $$0) {
/*  87 */     return this.delegate.getStringValue($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createString(String $$0) {
/*  92 */     return (T)this.delegate.createString($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<T> mergeToList(T $$0, T $$1) {
/*  97 */     return this.delegate.mergeToList($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<T> mergeToList(T $$0, List<T> $$1) {
/* 102 */     return this.delegate.mergeToList($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<T> mergeToMap(T $$0, T $$1, T $$2) {
/* 107 */     return this.delegate.mergeToMap($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<T> mergeToMap(T $$0, MapLike<T> $$1) {
/* 112 */     return this.delegate.mergeToMap($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Stream<Pair<T, T>>> getMapValues(T $$0) {
/* 117 */     return this.delegate.getMapValues($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(T $$0) {
/* 122 */     return this.delegate.getMapEntries($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createMap(Stream<Pair<T, T>> $$0) {
/* 127 */     return (T)this.delegate.createMap($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<MapLike<T>> getMap(T $$0) {
/* 132 */     return this.delegate.getMap($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Stream<T>> getStream(T $$0) {
/* 137 */     return this.delegate.getStream($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Consumer<Consumer<T>>> getList(T $$0) {
/* 142 */     return this.delegate.getList($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createList(Stream<T> $$0) {
/* 147 */     return (T)this.delegate.createList($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<ByteBuffer> getByteBuffer(T $$0) {
/* 152 */     return this.delegate.getByteBuffer($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createByteList(ByteBuffer $$0) {
/* 157 */     return (T)this.delegate.createByteList($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<IntStream> getIntStream(T $$0) {
/* 162 */     return this.delegate.getIntStream($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createIntList(IntStream $$0) {
/* 167 */     return (T)this.delegate.createIntList($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<LongStream> getLongStream(T $$0) {
/* 172 */     return this.delegate.getLongStream($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T createLongList(LongStream $$0) {
/* 177 */     return (T)this.delegate.createLongList($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T remove(T $$0, String $$1) {
/* 182 */     return (T)this.delegate.remove($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean compressMaps() {
/* 187 */     return this.delegate.compressMaps();
/*     */   }
/*     */ 
/*     */   
/*     */   public ListBuilder<T> listBuilder() {
/* 192 */     return (ListBuilder<T>)new ListBuilder.Builder(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecordBuilder<T> mapBuilder() {
/* 197 */     return (RecordBuilder<T>)new RecordBuilder.MapBuilder(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\DelegatingOps.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */