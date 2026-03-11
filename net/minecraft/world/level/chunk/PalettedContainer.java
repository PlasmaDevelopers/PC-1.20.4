/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArraySet;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.LongStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.VarInt;
/*     */ import net.minecraft.util.BitStorage;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.SimpleBitStorage;
/*     */ import net.minecraft.util.ThreadingDetector;
/*     */ import net.minecraft.util.ZeroBitStorage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PalettedContainer<T>
/*     */   implements PaletteResize<T>, PalettedContainerRO<T>
/*     */ {
/*     */   private static final int MIN_PALETTE_BITS = 0;
/*     */   private final PaletteResize<T> dummyPaletteResize = ($$0, $$1) -> 0;
/*     */   private final IdMap<T> registry;
/*     */   private volatile Data<T> data;
/*     */   private final Strategy strategy;
/*  42 */   private final ThreadingDetector threadingDetector = new ThreadingDetector("PalettedContainer");
/*     */   
/*     */   public void acquire() {
/*  45 */     this.threadingDetector.checkAndLock();
/*     */   }
/*     */   
/*     */   public void release() {
/*  49 */     this.threadingDetector.checkAndUnlock();
/*     */   }
/*     */   
/*     */   public static <T> Codec<PalettedContainer<T>> codecRW(IdMap<T> $$0, Codec<T> $$1, Strategy $$2, T $$3) {
/*  53 */     PalettedContainerRO.Unpacker<T, PalettedContainer<T>> $$4 = PalettedContainer::unpack;
/*  54 */     return codec($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static <T> Codec<PalettedContainerRO<T>> codecRO(IdMap<T> $$0, Codec<T> $$1, Strategy $$2, T $$3) {
/*  58 */     PalettedContainerRO.Unpacker<T, PalettedContainerRO<T>> $$4 = ($$0, $$1, $$2) -> unpack($$0, $$1, $$2).map(());
/*  59 */     return codec($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private static <T, C extends PalettedContainerRO<T>> Codec<C> codec(IdMap<T> $$0, Codec<T> $$1, Strategy $$2, T $$3, PalettedContainerRO.Unpacker<T, C> $$4) {
/*  63 */     return RecordCodecBuilder.create($$2 -> $$2.group((App)$$0.mapResult(ExtraCodecs.orElsePartial($$1)).listOf().fieldOf("palette").forGetter(PalettedContainerRO.PackedData::paletteEntries), (App)Codec.LONG_STREAM.optionalFieldOf("data").forGetter(PalettedContainerRO.PackedData::storage)).apply((Applicative)$$2, PackedData::new))
/*     */ 
/*     */       
/*  66 */       .comapFlatMap($$3 -> $$0.read($$1, $$2, $$3), $$2 -> $$2.pack($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PalettedContainer(IdMap<T> $$0, Strategy $$1, Configuration<T> $$2, BitStorage $$3, List<T> $$4) {
/*  73 */     this.registry = $$0;
/*  74 */     this.strategy = $$1;
/*     */     
/*  76 */     this.data = new Data<>($$2, $$3, $$2.factory().create($$2.bits(), $$0, this, $$4));
/*     */   }
/*     */   
/*     */   private PalettedContainer(IdMap<T> $$0, Strategy $$1, Data<T> $$2) {
/*  80 */     this.registry = $$0;
/*  81 */     this.strategy = $$1;
/*  82 */     this.data = $$2;
/*     */   }
/*     */   
/*     */   public PalettedContainer(IdMap<T> $$0, T $$1, Strategy $$2) {
/*  86 */     this.strategy = $$2;
/*  87 */     this.registry = $$0;
/*  88 */     this.data = createOrReuseData(null, 0);
/*     */     
/*  90 */     this.data.palette.idFor($$1);
/*     */   }
/*     */   
/*     */   private Data<T> createOrReuseData(@Nullable Data<T> $$0, int $$1) {
/*  94 */     Configuration<T> $$2 = this.strategy.getConfiguration(this.registry, $$1);
/*  95 */     if ($$0 != null && $$2.equals($$0.configuration())) {
/*  96 */       return $$0;
/*     */     }
/*  98 */     return $$2.createData(this.registry, this, this.strategy.size());
/*     */   }
/*     */ 
/*     */   
/*     */   public int onResize(int $$0, T $$1) {
/* 103 */     Data<T> $$2 = this.data;
/* 104 */     Data<T> $$3 = createOrReuseData($$2, $$0);
/*     */     
/* 106 */     $$3.copyFrom($$2.palette, $$2.storage);
/*     */     
/* 108 */     this.data = $$3;
/* 109 */     return $$3.palette.idFor($$1);
/*     */   }
/*     */   
/*     */   public T getAndSet(int $$0, int $$1, int $$2, T $$3) {
/* 113 */     acquire();
/*     */     try {
/* 115 */       return getAndSet(this.strategy.getIndex($$0, $$1, $$2), $$3);
/*     */     } finally {
/* 117 */       release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getAndSetUnchecked(int $$0, int $$1, int $$2, T $$3) {
/* 126 */     return getAndSet(this.strategy.getIndex($$0, $$1, $$2), $$3);
/*     */   }
/*     */   
/*     */   private T getAndSet(int $$0, T $$1) {
/* 130 */     int $$2 = this.data.palette.idFor($$1);
/* 131 */     int $$3 = this.data.storage.getAndSet($$0, $$2);
/* 132 */     return this.data.palette.valueFor($$3);
/*     */   }
/*     */   
/*     */   public void set(int $$0, int $$1, int $$2, T $$3) {
/* 136 */     acquire();
/*     */     try {
/* 138 */       set(this.strategy.getIndex($$0, $$1, $$2), $$3);
/*     */     } finally {
/* 140 */       release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void set(int $$0, T $$1) {
/* 145 */     int $$2 = this.data.palette.idFor($$1);
/*     */     
/* 147 */     this.data.storage.set($$0, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(int $$0, int $$1, int $$2) {
/* 152 */     return get(this.strategy.getIndex($$0, $$1, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected T get(int $$0) {
/* 157 */     Data<T> $$1 = this.data;
/* 158 */     return $$1.palette.valueFor($$1.storage.get($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void getAll(Consumer<T> $$0) {
/* 163 */     Palette<T> $$1 = this.data.palette();
/* 164 */     IntArraySet intArraySet = new IntArraySet();
/* 165 */     Objects.requireNonNull(intArraySet); this.data.storage.getAll(intArraySet::add);
/* 166 */     intArraySet.forEach($$2 -> $$0.accept($$1.valueFor($$2)));
/*     */   }
/*     */   
/*     */   public void read(FriendlyByteBuf $$0) {
/* 170 */     acquire();
/*     */     try {
/* 172 */       int $$1 = $$0.readByte();
/*     */       
/* 174 */       Data<T> $$2 = createOrReuseData(this.data, $$1);
/* 175 */       $$2.palette.read($$0);
/* 176 */       $$0.readLongArray($$2.storage.getRaw());
/*     */       
/* 178 */       this.data = $$2;
/*     */     } finally {
/* 180 */       release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 186 */     acquire();
/*     */     try {
/* 188 */       this.data.write($$0);
/*     */     } finally {
/* 190 */       release();
/*     */     } 
/*     */   }
/*     */   private static <T> DataResult<PalettedContainer<T>> unpack(IdMap<T> $$0, Strategy $$1, PalettedContainerRO.PackedData<T> $$2) {
/*     */     SimpleBitStorage simpleBitStorage;
/* 195 */     List<T> $$3 = $$2.paletteEntries();
/* 196 */     int $$4 = $$1.size();
/*     */     
/* 198 */     int $$5 = $$1.calculateBitsForSerialization($$0, $$3.size());
/* 199 */     Configuration<T> $$6 = $$1.getConfiguration($$0, $$5);
/*     */ 
/*     */     
/* 202 */     if ($$5 == 0) {
/* 203 */       ZeroBitStorage zeroBitStorage = new ZeroBitStorage($$4);
/*     */     } else {
/* 205 */       Optional<LongStream> $$8 = $$2.storage();
/* 206 */       if ($$8.isEmpty()) {
/* 207 */         return DataResult.error(() -> "Missing values for non-zero storage");
/*     */       }
/* 209 */       long[] $$9 = ((LongStream)$$8.get()).toArray();
/*     */       try {
/* 211 */         if ($$6.factory() == Strategy.GLOBAL_PALETTE_FACTORY) {
/* 212 */           Palette<T> $$10 = new HashMapPalette<>($$0, $$5, ($$0, $$1) -> 0, $$3);
/* 213 */           SimpleBitStorage $$11 = new SimpleBitStorage($$5, $$4, $$9);
/*     */           
/* 215 */           int[] $$12 = new int[$$4];
/* 216 */           $$11.unpack($$12);
/*     */           
/* 218 */           swapPalette($$12, $$2 -> $$0.getId($$1.valueFor($$2)));
/*     */           
/* 220 */           simpleBitStorage = new SimpleBitStorage($$6.bits(), $$4, $$12);
/*     */         } else {
/* 222 */           simpleBitStorage = new SimpleBitStorage($$6.bits(), $$4, $$9);
/*     */         } 
/* 224 */       } catch (net.minecraft.util.SimpleBitStorage.InitializationException $$15) {
/* 225 */         return DataResult.error(() -> "Failed to read PalettedContainer: " + $$0.getMessage());
/*     */       } 
/*     */     } 
/*     */     
/* 229 */     return DataResult.success(new PalettedContainer<>($$0, $$1, $$6, (BitStorage)simpleBitStorage, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public PalettedContainerRO.PackedData<T> pack(IdMap<T> $$0, Strategy $$1) {
/* 234 */     acquire();
/*     */     try {
/*     */       Optional<LongStream> $$8;
/* 237 */       HashMapPalette<T> $$2 = new HashMapPalette<>($$0, this.data.storage.getBits(), this.dummyPaletteResize);
/* 238 */       int $$3 = $$1.size();
/* 239 */       int[] $$4 = new int[$$3];
/*     */       
/* 241 */       this.data.storage.unpack($$4);
/*     */       
/* 243 */       swapPalette($$4, $$1 -> $$0.idFor(this.data.palette.valueFor($$1)));
/*     */       
/* 245 */       int $$5 = $$1.calculateBitsForSerialization($$0, $$2.getSize());
/*     */ 
/*     */       
/* 248 */       if ($$5 != 0) {
/* 249 */         SimpleBitStorage $$6 = new SimpleBitStorage($$5, $$3, $$4);
/* 250 */         Optional<LongStream> $$7 = Optional.of(Arrays.stream($$6.getRaw()));
/*     */       } else {
/* 252 */         $$8 = Optional.empty();
/*     */       } 
/* 254 */       return new PalettedContainerRO.PackedData($$2.getEntries(), $$8);
/*     */     } finally {
/* 256 */       release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static <T> void swapPalette(int[] $$0, IntUnaryOperator $$1) {
/* 261 */     int $$2 = -1;
/* 262 */     int $$3 = -1;
/* 263 */     for (int $$4 = 0; $$4 < $$0.length; $$4++) {
/* 264 */       int $$5 = $$0[$$4];
/* 265 */       if ($$5 != $$2) {
/* 266 */         $$2 = $$5;
/* 267 */         $$3 = $$1.applyAsInt($$5);
/*     */       } 
/* 269 */       $$0[$$4] = $$3;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/* 275 */     return this.data.getSerializedSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean maybeHas(Predicate<T> $$0) {
/* 280 */     return this.data.palette.maybeHas($$0);
/*     */   }
/*     */   
/*     */   public PalettedContainer<T> copy() {
/* 284 */     return new PalettedContainer(this.registry, this.strategy, this.data.copy());
/*     */   }
/*     */ 
/*     */   
/*     */   public PalettedContainer<T> recreate() {
/* 289 */     return new PalettedContainer(this.registry, this.data.palette.valueFor(0), this.strategy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void count(CountConsumer<T> $$0) {
/* 299 */     if (this.data.palette.getSize() == 1) {
/* 300 */       $$0.accept(this.data.palette.valueFor(0), this.data.storage.getSize());
/*     */       return;
/*     */     } 
/* 303 */     Int2IntOpenHashMap $$1 = new Int2IntOpenHashMap();
/* 304 */     this.data.storage.getAll($$1 -> $$0.addTo($$1, 1));
/* 305 */     $$1.int2IntEntrySet().forEach($$1 -> $$0.accept(this.data.palette.valueFor($$1.getIntKey()), $$1.getIntValue()));
/*     */   }
/*     */   private static final class Data<T> extends Record { private final PalettedContainer.Configuration<T> configuration; final BitStorage storage; final Palette<T> palette;
/* 308 */     Data(PalettedContainer.Configuration<T> $$0, BitStorage $$1, Palette<T> $$2) { this.configuration = $$0; this.storage = $$1; this.palette = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/chunk/PalettedContainer$Data;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #308	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Data;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 308 */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Data<TT;>; } public PalettedContainer.Configuration<T> configuration() { return this.configuration; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/chunk/PalettedContainer$Data;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #308	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Data;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Data<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/chunk/PalettedContainer$Data;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #308	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Data;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 308 */       //   0	8	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Data<TT;>; } public BitStorage storage() { return this.storage; } public Palette<T> palette() { return this.palette; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void copyFrom(Palette<T> $$0, BitStorage $$1) {
/* 314 */       for (int $$2 = 0; $$2 < $$1.getSize(); $$2++) {
/* 315 */         T $$3 = $$0.valueFor($$1.get($$2));
/* 316 */         this.storage.set($$2, this.palette.idFor($$3));
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getSerializedSize() {
/* 321 */       return 1 + this.palette.getSerializedSize() + VarInt.getByteSize((this.storage.getRaw()).length) + (this.storage.getRaw()).length * 8;
/*     */     }
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 325 */       $$0.writeByte(this.storage.getBits());
/* 326 */       this.palette.write($$0);
/* 327 */       $$0.writeLongArray(this.storage.getRaw());
/*     */     }
/*     */     
/*     */     public Data<T> copy() {
/* 331 */       return new Data(this.configuration, this.storage.copy(), this.palette.copy());
/*     */     } }
/*     */   private static final class Configuration<T> extends Record { private final Palette.Factory factory; private final int bits;
/*     */     
/* 335 */     Configuration(Palette.Factory $$0, int $$1) { this.factory = $$0; this.bits = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/chunk/PalettedContainer$Configuration;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #335	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Configuration;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Configuration<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/chunk/PalettedContainer$Configuration;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #335	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Configuration;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Configuration<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/chunk/PalettedContainer$Configuration;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #335	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Configuration;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 335 */       //   0	8	0	this	Lnet/minecraft/world/level/chunk/PalettedContainer$Configuration<TT;>; } public Palette.Factory factory() { return this.factory; } public int bits() { return this.bits; }
/*     */ 
/*     */ 
/*     */     
/*     */     public PalettedContainer.Data<T> createData(IdMap<T> $$0, PaletteResize<T> $$1, int $$2) {
/* 340 */       BitStorage $$3 = (this.bits == 0) ? (BitStorage)new ZeroBitStorage($$2) : (BitStorage)new SimpleBitStorage(this.bits, $$2);
/* 341 */       Palette<T> $$4 = this.factory.create(this.bits, $$0, $$1, List.of());
/*     */       
/* 343 */       return new PalettedContainer.Data<>(this, $$3, $$4);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static abstract class Strategy {
/* 348 */     public static final Palette.Factory SINGLE_VALUE_PALETTE_FACTORY = SingleValuePalette::create;
/* 349 */     public static final Palette.Factory LINEAR_PALETTE_FACTORY = LinearPalette::create;
/* 350 */     public static final Palette.Factory HASHMAP_PALETTE_FACTORY = HashMapPalette::create;
/* 351 */     static final Palette.Factory GLOBAL_PALETTE_FACTORY = GlobalPalette::create;
/*     */     
/* 353 */     public static final Strategy SECTION_STATES = new Strategy(4)
/*     */       {
/*     */         public <A> PalettedContainer.Configuration<A> getConfiguration(IdMap<A> $$0, int $$1) {
/* 356 */           switch ($$1) { case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8:  }  return 
/*     */ 
/*     */ 
/*     */             
/* 360 */             new PalettedContainer.Configuration<>(PalettedContainer.Strategy.GLOBAL_PALETTE_FACTORY, Mth.ceillog2($$0.size()));
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 365 */     public static final Strategy SECTION_BIOMES = new Strategy(2)
/*     */       {
/*     */         public <A> PalettedContainer.Configuration<A> getConfiguration(IdMap<A> $$0, int $$1) {
/* 368 */           switch ($$1) { case 0: case 1: case 2: case 3:  }  return 
/*     */ 
/*     */             
/* 371 */             new PalettedContainer.Configuration<>(PalettedContainer.Strategy.GLOBAL_PALETTE_FACTORY, Mth.ceillog2($$0.size()));
/*     */         }
/*     */       };
/*     */ 
/*     */     
/*     */     private final int sizeBits;
/*     */     
/*     */     Strategy(int $$0) {
/* 379 */       this.sizeBits = $$0;
/*     */     }
/*     */     
/*     */     public int size() {
/* 383 */       return 1 << this.sizeBits * 3;
/*     */     }
/*     */     
/*     */     public int getIndex(int $$0, int $$1, int $$2) {
/* 387 */       return ($$1 << this.sizeBits | $$2) << this.sizeBits | $$0;
/*     */     }
/*     */     
/*     */     public abstract <A> PalettedContainer.Configuration<A> getConfiguration(IdMap<A> param1IdMap, int param1Int);
/*     */     
/*     */     <A> int calculateBitsForSerialization(IdMap<A> $$0, int $$1) {
/* 393 */       int $$2 = Mth.ceillog2($$1);
/* 394 */       PalettedContainer.Configuration<A> $$3 = getConfiguration($$0, $$2);
/*     */       
/* 396 */       return ($$3.factory() == GLOBAL_PALETTE_FACTORY) ? $$2 : $$3.bits();
/*     */     }
/*     */   }
/*     */   
/*     */   class null extends Strategy {
/*     */     null(int $$0) {
/*     */       super($$0);
/*     */     }
/*     */     
/*     */     public <A> PalettedContainer.Configuration<A> getConfiguration(IdMap<A> $$0, int $$1) {
/*     */       switch ($$1) {
/*     */         case 0:
/*     */         
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         case 4:
/*     */         
/*     */         case 5:
/*     */         case 6:
/*     */         case 7:
/*     */         case 8:
/*     */         
/*     */       } 
/*     */       return new PalettedContainer.Configuration<>(PalettedContainer.Strategy.GLOBAL_PALETTE_FACTORY, Mth.ceillog2($$0.size()));
/*     */     }
/*     */   }
/*     */   
/*     */   class null extends Strategy {
/*     */     null(int $$0) {
/*     */       super($$0);
/*     */     }
/*     */     
/*     */     public <A> PalettedContainer.Configuration<A> getConfiguration(IdMap<A> $$0, int $$1) {
/*     */       switch ($$1) {
/*     */         case 0:
/*     */         
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         
/*     */       } 
/*     */       return new PalettedContainer.Configuration<>(PalettedContainer.Strategy.GLOBAL_PALETTE_FACTORY, Mth.ceillog2($$0.size()));
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface CountConsumer<T> {
/*     */     void accept(T param1T, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\PalettedContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */