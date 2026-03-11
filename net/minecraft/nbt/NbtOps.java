/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.RecordBuilder;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.longs.LongArrayList;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.LongStream;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class NbtOps
/*     */   implements DynamicOps<Tag> {
/*  29 */   public static final NbtOps INSTANCE = new NbtOps();
/*     */ 
/*     */   
/*     */   private static final String WRAPPER_MARKER = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public Tag empty() {
/*  37 */     return EndTag.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public <U> U convertTo(DynamicOps<U> $$0, Tag $$1) {
/*  42 */     switch ($$1.getId()) {
/*     */       case 0:
/*  44 */         return (U)$$0.empty();
/*     */       case 1:
/*  46 */         return (U)$$0.createByte(((NumericTag)$$1).getAsByte());
/*     */       case 2:
/*  48 */         return (U)$$0.createShort(((NumericTag)$$1).getAsShort());
/*     */       case 3:
/*  50 */         return (U)$$0.createInt(((NumericTag)$$1).getAsInt());
/*     */       case 4:
/*  52 */         return (U)$$0.createLong(((NumericTag)$$1).getAsLong());
/*     */       case 5:
/*  54 */         return (U)$$0.createFloat(((NumericTag)$$1).getAsFloat());
/*     */       case 6:
/*  56 */         return (U)$$0.createDouble(((NumericTag)$$1).getAsDouble());
/*     */       case 7:
/*  58 */         return (U)$$0.createByteList(ByteBuffer.wrap(((ByteArrayTag)$$1).getAsByteArray()));
/*     */       case 8:
/*  60 */         return (U)$$0.createString($$1.getAsString());
/*     */       case 9:
/*  62 */         return (U)convertList($$0, $$1);
/*     */       case 10:
/*  64 */         return (U)convertMap($$0, $$1);
/*     */       case 11:
/*  66 */         return (U)$$0.createIntList(Arrays.stream(((IntArrayTag)$$1).getAsIntArray()));
/*     */       case 12:
/*  68 */         return (U)$$0.createLongList(Arrays.stream(((LongArrayTag)$$1).getAsLongArray()));
/*     */     } 
/*  70 */     throw new IllegalStateException("Unknown tag type: " + $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DataResult<Number> getNumberValue(Tag $$0) {
/*  76 */     if ($$0 instanceof NumericTag) { NumericTag $$1 = (NumericTag)$$0;
/*  77 */       return DataResult.success($$1.getAsNumber()); }
/*     */     
/*  79 */     return DataResult.error(() -> "Not a number");
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createNumeric(Number $$0) {
/*  84 */     return DoubleTag.valueOf($$0.doubleValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createByte(byte $$0) {
/*  89 */     return ByteTag.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createShort(short $$0) {
/*  94 */     return ShortTag.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createInt(int $$0) {
/*  99 */     return IntTag.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createLong(long $$0) {
/* 104 */     return LongTag.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createFloat(float $$0) {
/* 109 */     return FloatTag.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createDouble(double $$0) {
/* 114 */     return DoubleTag.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createBoolean(boolean $$0) {
/* 119 */     return ByteTag.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<String> getStringValue(Tag $$0) {
/* 124 */     if ($$0 instanceof StringTag) { StringTag $$1 = (StringTag)$$0;
/* 125 */       return DataResult.success($$1.getAsString()); }
/*     */     
/* 127 */     return DataResult.error(() -> "Not a string");
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createString(String $$0) {
/* 132 */     return StringTag.valueOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Tag> mergeToList(Tag $$0, Tag $$1) {
/* 137 */     return createCollector($$0)
/* 138 */       .<DataResult<Tag>>map($$1 -> DataResult.success($$1.accept($$0).result()))
/* 139 */       .orElseGet(() -> DataResult.error((), $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Tag> mergeToList(Tag $$0, List<Tag> $$1) {
/* 144 */     return createCollector($$0)
/* 145 */       .<DataResult<Tag>>map($$1 -> DataResult.success($$1.acceptAll($$0).result()))
/* 146 */       .orElseGet(() -> DataResult.error((), $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Tag> mergeToMap(Tag $$0, Tag $$1, Tag $$2) {
/* 151 */     if (!($$0 instanceof CompoundTag) && !($$0 instanceof EndTag)) {
/* 152 */       return DataResult.error(() -> "mergeToMap called with not a map: " + $$0, $$0);
/*     */     }
/* 154 */     if (!($$1 instanceof StringTag)) {
/* 155 */       return DataResult.error(() -> "key is not a string: " + $$0, $$0);
/*     */     }
/*     */     
/* 158 */     CompoundTag $$3 = new CompoundTag();
/* 159 */     if ($$0 instanceof CompoundTag) { CompoundTag $$4 = (CompoundTag)$$0;
/* 160 */       $$4.getAllKeys().forEach($$2 -> $$0.put($$2, $$1.get($$2))); }
/*     */     
/* 162 */     $$3.put($$1.getAsString(), $$2);
/* 163 */     return DataResult.success($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Tag> mergeToMap(Tag $$0, MapLike<Tag> $$1) {
/* 168 */     if (!($$0 instanceof CompoundTag) && !($$0 instanceof EndTag)) {
/* 169 */       return DataResult.error(() -> "mergeToMap called with not a map: " + $$0, $$0);
/*     */     }
/*     */     
/* 172 */     CompoundTag $$2 = new CompoundTag();
/* 173 */     if ($$0 instanceof CompoundTag) { CompoundTag $$3 = (CompoundTag)$$0;
/* 174 */       $$3.getAllKeys().forEach($$2 -> $$0.put($$2, $$1.get($$2))); }
/*     */ 
/*     */     
/* 177 */     List<Tag> $$4 = Lists.newArrayList();
/*     */     
/* 179 */     $$1.entries().forEach($$2 -> {
/*     */           Tag $$3 = (Tag)$$2.getFirst();
/*     */           
/*     */           if (!($$3 instanceof StringTag)) {
/*     */             $$0.add($$3);
/*     */             
/*     */             return;
/*     */           } 
/*     */           $$1.put($$3.getAsString(), (Tag)$$2.getSecond());
/*     */         });
/* 189 */     if (!$$4.isEmpty()) {
/* 190 */       return DataResult.error(() -> "some keys are not strings: " + $$0, $$2);
/*     */     }
/*     */     
/* 193 */     return DataResult.success($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Stream<Pair<Tag, Tag>>> getMapValues(Tag $$0) {
/* 198 */     if ($$0 instanceof CompoundTag) { CompoundTag $$1 = (CompoundTag)$$0;
/* 199 */       return DataResult.success($$1.getAllKeys().stream().map($$1 -> Pair.of(createString($$1), $$0.get($$1)))); }
/*     */     
/* 201 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Consumer<BiConsumer<Tag, Tag>>> getMapEntries(Tag $$0) {
/* 206 */     if ($$0 instanceof CompoundTag) { CompoundTag $$1 = (CompoundTag)$$0;
/* 207 */       return DataResult.success($$1 -> $$0.getAllKeys().forEach(())); }
/*     */     
/* 209 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<MapLike<Tag>> getMap(Tag $$0) {
/* 214 */     if ($$0 instanceof CompoundTag) { final CompoundTag tag = (CompoundTag)$$0;
/* 215 */       return DataResult.success(new MapLike<Tag>()
/*     */           {
/*     */             @Nullable
/*     */             public Tag get(Tag $$0) {
/* 219 */               return tag.get($$0.getAsString());
/*     */             }
/*     */ 
/*     */             
/*     */             @Nullable
/*     */             public Tag get(String $$0) {
/* 225 */               return tag.get($$0);
/*     */             }
/*     */ 
/*     */             
/*     */             public Stream<Pair<Tag, Tag>> entries() {
/* 230 */               return tag.getAllKeys().stream().map($$1 -> Pair.of(NbtOps.this.createString($$1), $$0.get($$1)));
/*     */             }
/*     */ 
/*     */             
/*     */             public String toString() {
/* 235 */               return "MapLike[" + tag + "]";
/*     */             }
/*     */           }); }
/*     */     
/* 239 */     return DataResult.error(() -> "Not a map: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createMap(Stream<Pair<Tag, Tag>> $$0) {
/* 244 */     CompoundTag $$1 = new CompoundTag();
/* 245 */     $$0.forEach($$1 -> $$0.put(((Tag)$$1.getFirst()).getAsString(), (Tag)$$1.getSecond()));
/*     */ 
/*     */     
/* 248 */     return $$1;
/*     */   }
/*     */   
/*     */   private static Tag tryUnwrap(CompoundTag $$0) {
/* 252 */     if ($$0.size() == 1) {
/* 253 */       Tag $$1 = $$0.get("");
/* 254 */       if ($$1 != null) {
/* 255 */         return $$1;
/*     */       }
/*     */     } 
/* 258 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Stream<Tag>> getStream(Tag $$0) {
/* 263 */     if ($$0 instanceof ListTag) { ListTag $$1 = (ListTag)$$0;
/* 264 */       if ($$1.getElementType() == 10) {
/* 265 */         return DataResult.success($$1.stream().map($$0 -> tryUnwrap((CompoundTag)$$0)));
/*     */       }
/* 267 */       return DataResult.success($$1.stream()); }
/*     */ 
/*     */     
/* 270 */     if ($$0 instanceof CollectionTag) { CollectionTag<?> $$2 = (CollectionTag)$$0;
/* 271 */       return DataResult.success($$2.stream().map($$0 -> $$0)); }
/*     */     
/* 273 */     return DataResult.error(() -> "Not a list");
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<Consumer<Consumer<Tag>>> getList(Tag $$0) {
/* 278 */     if ($$0 instanceof ListTag) { ListTag $$1 = (ListTag)$$0;
/* 279 */       if ($$1.getElementType() == 10) {
/* 280 */         return DataResult.success($$1 -> $$0.forEach(()));
/*     */       }
/* 282 */       Objects.requireNonNull($$1); return DataResult.success($$1::forEach); }
/*     */ 
/*     */     
/* 285 */     if ($$0 instanceof CollectionTag) { CollectionTag<?> $$2 = (CollectionTag)$$0;
/* 286 */       Objects.requireNonNull($$2); return DataResult.success($$2::forEach); }
/*     */     
/* 288 */     return DataResult.error(() -> "Not a list: " + $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<ByteBuffer> getByteBuffer(Tag $$0) {
/* 293 */     if ($$0 instanceof ByteArrayTag) { ByteArrayTag $$1 = (ByteArrayTag)$$0;
/* 294 */       return DataResult.success(ByteBuffer.wrap($$1.getAsByteArray())); }
/*     */     
/* 296 */     return super.getByteBuffer($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Tag createByteList(ByteBuffer $$0) {
/* 302 */     ByteBuffer $$1 = $$0.duplicate().clear();
/*     */     
/* 304 */     byte[] $$2 = new byte[$$0.capacity()];
/* 305 */     $$1.get(0, $$2, 0, $$2.length);
/* 306 */     return new ByteArrayTag($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<IntStream> getIntStream(Tag $$0) {
/* 311 */     if ($$0 instanceof IntArrayTag) { IntArrayTag $$1 = (IntArrayTag)$$0;
/* 312 */       return DataResult.success(Arrays.stream($$1.getAsIntArray())); }
/*     */     
/* 314 */     return super.getIntStream($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createIntList(IntStream $$0) {
/* 319 */     return new IntArrayTag($$0.toArray());
/*     */   }
/*     */ 
/*     */   
/*     */   public DataResult<LongStream> getLongStream(Tag $$0) {
/* 324 */     if ($$0 instanceof LongArrayTag) { LongArrayTag $$1 = (LongArrayTag)$$0;
/* 325 */       return DataResult.success(Arrays.stream($$1.getAsLongArray())); }
/*     */     
/* 327 */     return super.getLongStream($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createLongList(LongStream $$0) {
/* 332 */     return new LongArrayTag($$0.toArray());
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag createList(Stream<Tag> $$0) {
/* 337 */     return InitialListCollector.INSTANCE.acceptAll($$0).result();
/*     */   }
/*     */ 
/*     */   
/*     */   public Tag remove(Tag $$0, String $$1) {
/* 342 */     if ($$0 instanceof CompoundTag) { CompoundTag $$2 = (CompoundTag)$$0;
/* 343 */       CompoundTag $$3 = new CompoundTag();
/* 344 */       $$2.getAllKeys().stream().filter($$1 -> !Objects.equals($$1, $$0)).forEach($$2 -> $$0.put($$2, $$1.get($$2)));
/* 345 */       return $$3; }
/*     */     
/* 347 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 352 */     return "NBT";
/*     */   }
/*     */ 
/*     */   
/*     */   public RecordBuilder<Tag> mapBuilder() {
/* 357 */     return (RecordBuilder<Tag>)new NbtRecordBuilder();
/*     */   }
/*     */   
/*     */   private class NbtRecordBuilder extends RecordBuilder.AbstractStringBuilder<Tag, CompoundTag> {
/*     */     protected NbtRecordBuilder() {
/* 362 */       super(NbtOps.this);
/*     */     }
/*     */ 
/*     */     
/*     */     protected CompoundTag initBuilder() {
/* 367 */       return new CompoundTag();
/*     */     }
/*     */ 
/*     */     
/*     */     protected CompoundTag append(String $$0, Tag $$1, CompoundTag $$2) {
/* 372 */       $$2.put($$0, $$1);
/* 373 */       return $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     protected DataResult<Tag> build(CompoundTag $$0, Tag $$1) {
/* 378 */       if ($$1 == null || $$1 == EndTag.INSTANCE) {
/* 379 */         return DataResult.success($$0);
/*     */       }
/* 381 */       if ($$1 instanceof CompoundTag) { CompoundTag $$2 = (CompoundTag)$$1;
/* 382 */         CompoundTag $$3 = new CompoundTag(Maps.newHashMap($$2.entries()));
/* 383 */         for (Map.Entry<String, Tag> $$4 : $$0.entries().entrySet()) {
/* 384 */           $$3.put($$4.getKey(), $$4.getValue());
/*     */         }
/* 386 */         return DataResult.success($$3); }
/*     */       
/* 388 */       return DataResult.error(() -> "mergeToMap called with not a map: " + $$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private static interface ListCollector {
/*     */     ListCollector accept(Tag param1Tag);
/*     */     
/*     */     default ListCollector acceptAll(Iterable<Tag> $$0) {
/* 396 */       ListCollector $$1 = this;
/* 397 */       for (Tag $$2 : $$0) {
/* 398 */         $$1 = $$1.accept($$2);
/*     */       }
/* 400 */       return $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     default ListCollector acceptAll(Stream<Tag> $$0) {
/* 405 */       Objects.requireNonNull($$0); return acceptAll($$0::iterator);
/*     */     }
/*     */     
/*     */     Tag result();
/*     */   }
/*     */   
/*     */   private static Optional<ListCollector> createCollector(Tag $$0) {
/* 412 */     if ($$0 instanceof EndTag) {
/* 413 */       return Optional.of(InitialListCollector.INSTANCE);
/*     */     }
/* 415 */     if ($$0 instanceof CollectionTag) { CollectionTag<?> $$1 = (CollectionTag)$$0;
/* 416 */       if ($$1.isEmpty()) {
/* 417 */         return Optional.of(InitialListCollector.INSTANCE);
/*     */       }
/* 419 */       if ($$1 instanceof ListTag) { ListTag $$2 = (ListTag)$$1;
/* 420 */         switch ($$2.getElementType()) { case 0: case 10:  }  return 
/*     */ 
/*     */           
/* 423 */           Optional.of(new HomogenousListCollector($$2)); }
/*     */ 
/*     */       
/* 426 */       if ($$1 instanceof ByteArrayTag) { ByteArrayTag $$3 = (ByteArrayTag)$$1;
/* 427 */         return Optional.of(new ByteListCollector($$3.getAsByteArray())); }
/*     */       
/* 429 */       if ($$1 instanceof IntArrayTag) { IntArrayTag $$4 = (IntArrayTag)$$1;
/* 430 */         return Optional.of(new IntListCollector($$4.getAsIntArray())); }
/*     */       
/* 432 */       if ($$1 instanceof LongArrayTag) { LongArrayTag $$5 = (LongArrayTag)$$1;
/* 433 */         return Optional.of(new LongListCollector($$5.getAsLongArray())); }
/*     */        }
/*     */     
/* 436 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   private static class InitialListCollector implements ListCollector {
/* 440 */     public static final InitialListCollector INSTANCE = new InitialListCollector();
/*     */ 
/*     */     
/*     */     public NbtOps.ListCollector accept(Tag $$0) {
/* 444 */       if ($$0 instanceof CompoundTag) { CompoundTag $$1 = (CompoundTag)$$0;
/*     */         
/* 446 */         return (new NbtOps.HeterogenousListCollector()).accept($$1); }
/*     */       
/* 448 */       if ($$0 instanceof ByteTag) { ByteTag $$2 = (ByteTag)$$0;
/* 449 */         return new NbtOps.ByteListCollector($$2.getAsByte()); }
/*     */       
/* 451 */       if ($$0 instanceof IntTag) { IntTag $$3 = (IntTag)$$0;
/* 452 */         return new NbtOps.IntListCollector($$3.getAsInt()); }
/*     */       
/* 454 */       if ($$0 instanceof LongTag) { LongTag $$4 = (LongTag)$$0;
/* 455 */         return new NbtOps.LongListCollector($$4.getAsLong()); }
/*     */       
/* 457 */       return new NbtOps.HomogenousListCollector($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag result() {
/* 462 */       return new ListTag();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class HomogenousListCollector implements ListCollector {
/* 467 */     private final ListTag result = new ListTag();
/*     */     
/*     */     HomogenousListCollector(Tag $$0) {
/* 470 */       this.result.add($$0);
/*     */     }
/*     */     
/*     */     HomogenousListCollector(ListTag $$0) {
/* 474 */       this.result.addAll($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public NbtOps.ListCollector accept(Tag $$0) {
/* 479 */       if ($$0.getId() != this.result.getElementType()) {
/* 480 */         return (new NbtOps.HeterogenousListCollector()).acceptAll(this.result).accept($$0);
/*     */       }
/* 482 */       this.result.add($$0);
/* 483 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag result() {
/* 488 */       return this.result;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class HeterogenousListCollector implements ListCollector {
/* 493 */     private final ListTag result = new ListTag();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public HeterogenousListCollector(Collection<Tag> $$0) {
/* 499 */       this.result.addAll($$0);
/*     */     }
/*     */     
/*     */     public HeterogenousListCollector(IntArrayList $$0) {
/* 503 */       $$0.forEach($$0 -> this.result.add(wrapElement(IntTag.valueOf($$0))));
/*     */     }
/*     */     
/*     */     public HeterogenousListCollector(ByteArrayList $$0) {
/* 507 */       $$0.forEach($$0 -> this.result.add(wrapElement(ByteTag.valueOf($$0))));
/*     */     }
/*     */     
/*     */     public HeterogenousListCollector(LongArrayList $$0) {
/* 511 */       $$0.forEach($$0 -> this.result.add(wrapElement(LongTag.valueOf($$0))));
/*     */     }
/*     */     
/*     */     private static boolean isWrapper(CompoundTag $$0) {
/* 515 */       return ($$0.size() == 1 && $$0.contains(""));
/*     */     }
/*     */     
/*     */     private static Tag wrapIfNeeded(Tag $$0) {
/* 519 */       if ($$0 instanceof CompoundTag) { CompoundTag $$1 = (CompoundTag)$$0; if (!isWrapper($$1))
/* 520 */           return $$1;  }
/*     */       
/* 522 */       return wrapElement($$0);
/*     */     }
/*     */     
/*     */     private static CompoundTag wrapElement(Tag $$0) {
/* 526 */       CompoundTag $$1 = new CompoundTag();
/* 527 */       $$1.put("", $$0);
/* 528 */       return $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public NbtOps.ListCollector accept(Tag $$0) {
/* 533 */       this.result.add(wrapIfNeeded($$0));
/* 534 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag result() {
/* 539 */       return this.result;
/*     */     }
/*     */     
/*     */     public HeterogenousListCollector() {} }
/*     */   
/* 544 */   private static class IntListCollector implements ListCollector { private final IntArrayList values = new IntArrayList();
/*     */     
/*     */     public IntListCollector(int $$0) {
/* 547 */       this.values.add($$0);
/*     */     }
/*     */     
/*     */     public IntListCollector(int[] $$0) {
/* 551 */       this.values.addElements(0, $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public NbtOps.ListCollector accept(Tag $$0) {
/* 556 */       if ($$0 instanceof IntTag) { IntTag $$1 = (IntTag)$$0;
/* 557 */         this.values.add($$1.getAsInt());
/* 558 */         return this; }
/*     */ 
/*     */       
/* 561 */       return (new NbtOps.HeterogenousListCollector(this.values)).accept($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag result() {
/* 566 */       return new IntArrayTag(this.values.toIntArray());
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class ByteListCollector implements ListCollector {
/* 571 */     private final ByteArrayList values = new ByteArrayList();
/*     */     
/*     */     public ByteListCollector(byte $$0) {
/* 574 */       this.values.add($$0);
/*     */     }
/*     */     
/*     */     public ByteListCollector(byte[] $$0) {
/* 578 */       this.values.addElements(0, $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public NbtOps.ListCollector accept(Tag $$0) {
/* 583 */       if ($$0 instanceof ByteTag) { ByteTag $$1 = (ByteTag)$$0;
/* 584 */         this.values.add($$1.getAsByte());
/* 585 */         return this; }
/*     */ 
/*     */       
/* 588 */       return (new NbtOps.HeterogenousListCollector(this.values)).accept($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag result() {
/* 593 */       return new ByteArrayTag(this.values.toByteArray());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class LongListCollector implements ListCollector {
/* 598 */     private final LongArrayList values = new LongArrayList();
/*     */     
/*     */     public LongListCollector(long $$0) {
/* 601 */       this.values.add($$0);
/*     */     }
/*     */     
/*     */     public LongListCollector(long[] $$0) {
/* 605 */       this.values.addElements(0, $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public NbtOps.ListCollector accept(Tag $$0) {
/* 610 */       if ($$0 instanceof LongTag) { LongTag $$1 = (LongTag)$$0;
/* 611 */         this.values.add($$1.getAsLong());
/* 612 */         return this; }
/*     */ 
/*     */       
/* 615 */       return (new NbtOps.HeterogenousListCollector(this.values)).accept($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Tag result() {
/* 620 */       return new LongArrayTag(this.values.toLongArray());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\NbtOps.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */