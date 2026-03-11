/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.VarInt;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ public class LinearPalette<T>
/*     */   implements Palette<T>
/*     */ {
/*     */   private final IdMap<T> registry;
/*     */   private final T[] values;
/*     */   private final PaletteResize<T> resizeHandler;
/*     */   private final int bits;
/*     */   private int size;
/*     */   
/*     */   private LinearPalette(IdMap<T> $$0, int $$1, PaletteResize<T> $$2, List<T> $$3) {
/*  20 */     this.registry = $$0;
/*  21 */     this.values = (T[])new Object[1 << $$1];
/*  22 */     this.bits = $$1;
/*  23 */     this.resizeHandler = $$2;
/*  24 */     Validate.isTrue(($$3.size() <= this.values.length), "Can't initialize LinearPalette of size %d with %d entries", new Object[] { Integer.valueOf(this.values.length), Integer.valueOf($$3.size()) });
/*  25 */     for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
/*  26 */       this.values[$$4] = $$3.get($$4);
/*     */     }
/*  28 */     this.size = $$3.size();
/*     */   }
/*     */   
/*     */   private LinearPalette(IdMap<T> $$0, T[] $$1, PaletteResize<T> $$2, int $$3, int $$4) {
/*  32 */     this.registry = $$0;
/*  33 */     this.values = $$1;
/*  34 */     this.resizeHandler = $$2;
/*  35 */     this.bits = $$3;
/*  36 */     this.size = $$4;
/*     */   }
/*     */   
/*     */   public static <A> Palette<A> create(int $$0, IdMap<A> $$1, PaletteResize<A> $$2, List<A> $$3) {
/*  40 */     return new LinearPalette<>($$1, $$0, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int idFor(T $$0) {
/*  45 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/*  46 */       if (this.values[$$1] == $$0) {
/*  47 */         return $$1;
/*     */       }
/*     */     } 
/*     */     
/*  51 */     int $$2 = this.size;
/*  52 */     if ($$2 < this.values.length) {
/*  53 */       this.values[$$2] = $$0;
/*  54 */       this.size++;
/*  55 */       return $$2;
/*     */     } 
/*     */     
/*  58 */     return this.resizeHandler.onResize(this.bits + 1, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean maybeHas(Predicate<T> $$0) {
/*  63 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/*  64 */       if ($$0.test(this.values[$$1])) {
/*  65 */         return true;
/*     */       }
/*     */     } 
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public T valueFor(int $$0) {
/*  73 */     if ($$0 >= 0 && $$0 < this.size) {
/*  74 */       return this.values[$$0];
/*     */     }
/*  76 */     throw new MissingPaletteEntryException($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(FriendlyByteBuf $$0) {
/*  81 */     this.size = $$0.readVarInt();
/*  82 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/*  83 */       this.values[$$1] = (T)this.registry.byIdOrThrow($$0.readVarInt());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  89 */     $$0.writeVarInt(this.size);
/*  90 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/*  91 */       $$0.writeVarInt(this.registry.getId(this.values[$$1]));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  97 */     int $$0 = VarInt.getByteSize(getSize());
/*     */     
/*  99 */     for (int $$1 = 0; $$1 < getSize(); $$1++) {
/* 100 */       $$0 += VarInt.getByteSize(this.registry.getId(this.values[$$1]));
/*     */     }
/*     */     
/* 103 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 108 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Palette<T> copy() {
/* 113 */     return new LinearPalette(this.registry, (T[])this.values.clone(), this.resizeHandler, this.bits, this.size);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\LinearPalette.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */