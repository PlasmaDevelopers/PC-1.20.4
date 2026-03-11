/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.VarInt;
/*     */ import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
/*     */ 
/*     */ public class HashMapPalette<T> implements Palette<T> {
/*     */   private final IdMap<T> registry;
/*     */   private final CrudeIncrementalIntIdentityHashBiMap<T> values;
/*     */   private final PaletteResize<T> resizeHandler;
/*     */   private final int bits;
/*     */   
/*     */   public HashMapPalette(IdMap<T> $$0, int $$1, PaletteResize<T> $$2, List<T> $$3) {
/*  19 */     this($$0, $$1, $$2);
/*  20 */     Objects.requireNonNull(this.values); $$3.forEach(this.values::add);
/*     */   }
/*     */   
/*     */   public HashMapPalette(IdMap<T> $$0, int $$1, PaletteResize<T> $$2) {
/*  24 */     this($$0, $$1, $$2, CrudeIncrementalIntIdentityHashBiMap.create(1 << $$1));
/*     */   }
/*     */   
/*     */   private HashMapPalette(IdMap<T> $$0, int $$1, PaletteResize<T> $$2, CrudeIncrementalIntIdentityHashBiMap<T> $$3) {
/*  28 */     this.registry = $$0;
/*  29 */     this.bits = $$1;
/*  30 */     this.resizeHandler = $$2;
/*  31 */     this.values = $$3;
/*     */   }
/*     */   
/*     */   public static <A> Palette<A> create(int $$0, IdMap<A> $$1, PaletteResize<A> $$2, List<A> $$3) {
/*  35 */     return new HashMapPalette<>($$1, $$0, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int idFor(T $$0) {
/*  40 */     int $$1 = this.values.getId($$0);
/*  41 */     if ($$1 == -1) {
/*  42 */       $$1 = this.values.add($$0);
/*     */       
/*  44 */       if ($$1 >= 1 << this.bits) {
/*  45 */         $$1 = this.resizeHandler.onResize(this.bits + 1, $$0);
/*     */       }
/*     */     } 
/*  48 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean maybeHas(Predicate<T> $$0) {
/*  53 */     for (int $$1 = 0; $$1 < getSize(); $$1++) {
/*  54 */       if ($$0.test((T)this.values.byId($$1))) {
/*  55 */         return true;
/*     */       }
/*     */     } 
/*  58 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public T valueFor(int $$0) {
/*  63 */     T $$1 = (T)this.values.byId($$0);
/*  64 */     if ($$1 == null) {
/*  65 */       throw new MissingPaletteEntryException($$0);
/*     */     }
/*  67 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(FriendlyByteBuf $$0) {
/*  72 */     this.values.clear();
/*  73 */     int $$1 = $$0.readVarInt();
/*  74 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/*  75 */       this.values.add(this.registry.byIdOrThrow($$0.readVarInt()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  81 */     int $$1 = getSize();
/*  82 */     $$0.writeVarInt($$1);
/*     */     
/*  84 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/*  85 */       $$0.writeVarInt(this.registry.getId(this.values.byId($$2)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  91 */     int $$0 = VarInt.getByteSize(getSize());
/*     */     
/*  93 */     for (int $$1 = 0; $$1 < getSize(); $$1++) {
/*  94 */       $$0 += VarInt.getByteSize(this.registry.getId(this.values.byId($$1)));
/*     */     }
/*     */     
/*  97 */     return $$0;
/*     */   }
/*     */   
/*     */   public List<T> getEntries() {
/* 101 */     ArrayList<T> $$0 = new ArrayList<>();
/* 102 */     Objects.requireNonNull($$0); this.values.iterator().forEachRemaining($$0::add);
/* 103 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 108 */     return this.values.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Palette<T> copy() {
/* 113 */     return new HashMapPalette(this.registry, this.bits, this.resizeHandler, this.values.copy());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\HashMapPalette.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */