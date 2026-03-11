/*     */ package net.minecraft.client.gui.font;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class CodepointMap<T>
/*     */ {
/*     */   private static final int BLOCK_BITS = 8;
/*     */   private static final int BLOCK_SIZE = 256;
/*     */   private static final int IN_BLOCK_MASK = 255;
/*     */   private static final int MAX_BLOCK = 4351;
/*     */   private static final int BLOCK_COUNT = 4352;
/*     */   private final T[] empty;
/*     */   private final T[][] blockMap;
/*     */   private final IntFunction<T[]> blockConstructor;
/*     */   
/*     */   public CodepointMap(IntFunction<T[]> $$0, IntFunction<T[][]> $$1) {
/*  22 */     this.empty = $$0.apply(256);
/*  23 */     this.blockMap = $$1.apply(4352);
/*  24 */     Arrays.fill((Object[])this.blockMap, this.empty);
/*  25 */     this.blockConstructor = $$0;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  29 */     Arrays.fill((Object[])this.blockMap, this.empty);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T get(int $$0) {
/*  34 */     int $$1 = $$0 >> 8;
/*  35 */     int $$2 = $$0 & 0xFF;
/*  36 */     return this.blockMap[$$1][$$2];
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T put(int $$0, T $$1) {
/*  41 */     int $$2 = $$0 >> 8;
/*  42 */     int $$3 = $$0 & 0xFF;
/*     */     
/*  44 */     T[] $$4 = this.blockMap[$$2];
/*  45 */     if ($$4 == this.empty) {
/*  46 */       $$4 = this.blockConstructor.apply(256);
/*  47 */       this.blockMap[$$2] = $$4;
/*  48 */       $$4[$$3] = $$1;
/*  49 */       return null;
/*     */     } 
/*  51 */     T $$5 = $$4[$$3];
/*  52 */     $$4[$$3] = $$1;
/*  53 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public T computeIfAbsent(int $$0, IntFunction<T> $$1) {
/*  58 */     int $$2 = $$0 >> 8;
/*  59 */     int $$3 = $$0 & 0xFF;
/*     */     
/*  61 */     T[] $$4 = this.blockMap[$$2];
/*  62 */     T $$5 = $$4[$$3];
/*  63 */     if ($$5 != null) {
/*  64 */       return $$5;
/*     */     }
/*     */     
/*  67 */     if ($$4 == this.empty) {
/*  68 */       $$4 = this.blockConstructor.apply(256);
/*  69 */       this.blockMap[$$2] = $$4;
/*     */     } 
/*     */     
/*  72 */     T $$6 = $$1.apply($$0);
/*  73 */     $$4[$$3] = $$6;
/*  74 */     return $$6;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T remove(int $$0) {
/*  79 */     int $$1 = $$0 >> 8;
/*  80 */     int $$2 = $$0 & 0xFF;
/*     */     
/*  82 */     T[] $$3 = this.blockMap[$$1];
/*  83 */     if ($$3 == this.empty) {
/*  84 */       return null;
/*     */     }
/*     */     
/*  87 */     T $$4 = $$3[$$2];
/*  88 */     $$3[$$2] = null;
/*  89 */     return $$4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void forEach(Output<T> $$0) {
/*  98 */     for (int $$1 = 0; $$1 < this.blockMap.length; $$1++) {
/*  99 */       T[] $$2 = this.blockMap[$$1];
/* 100 */       if ($$2 != this.empty) {
/* 101 */         for (int $$3 = 0; $$3 < $$2.length; $$3++) {
/* 102 */           T $$4 = $$2[$$3];
/* 103 */           if ($$4 != null) {
/* 104 */             int $$5 = $$1 << 8 | $$3;
/* 105 */             $$0.accept($$5, $$4);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public IntSet keySet() {
/* 113 */     IntOpenHashSet $$0 = new IntOpenHashSet();
/* 114 */     forEach(($$1, $$2) -> $$0.add($$1));
/* 115 */     return (IntSet)$$0;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Output<T> {
/*     */     void accept(int param1Int, T param1T);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\CodepointMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */