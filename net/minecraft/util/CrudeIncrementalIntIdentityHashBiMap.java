/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.IdMap;
/*     */ 
/*     */ public class CrudeIncrementalIntIdentityHashBiMap<K>
/*     */   implements IdMap<K> {
/*     */   private static final int NOT_FOUND = -1;
/*  13 */   private static final Object EMPTY_SLOT = null;
/*     */   
/*     */   private static final float LOADFACTOR = 0.8F;
/*     */   
/*     */   private K[] keys;
/*     */   
/*     */   private int[] values;
/*     */   private K[] byId;
/*     */   private int nextId;
/*     */   private int size;
/*     */   
/*     */   private CrudeIncrementalIntIdentityHashBiMap(int $$0) {
/*  25 */     this.keys = (K[])new Object[$$0];
/*  26 */     this.values = new int[$$0];
/*  27 */     this.byId = (K[])new Object[$$0];
/*     */   }
/*     */   
/*     */   private CrudeIncrementalIntIdentityHashBiMap(K[] $$0, int[] $$1, K[] $$2, int $$3, int $$4) {
/*  31 */     this.keys = $$0;
/*  32 */     this.values = $$1;
/*  33 */     this.byId = $$2;
/*  34 */     this.nextId = $$3;
/*  35 */     this.size = $$4;
/*     */   }
/*     */   
/*     */   public static <A> CrudeIncrementalIntIdentityHashBiMap<A> create(int $$0) {
/*  39 */     return new CrudeIncrementalIntIdentityHashBiMap<>((int)($$0 / 0.8F));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId(@Nullable K $$0) {
/*  44 */     return getValue(indexOf($$0, hash($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public K byId(int $$0) {
/*  50 */     if ($$0 < 0 || $$0 >= this.byId.length) {
/*  51 */       return null;
/*     */     }
/*     */     
/*  54 */     return this.byId[$$0];
/*     */   }
/*     */   
/*     */   private int getValue(int $$0) {
/*  58 */     if ($$0 == -1) {
/*  59 */       return -1;
/*     */     }
/*  61 */     return this.values[$$0];
/*     */   }
/*     */   
/*     */   public boolean contains(K $$0) {
/*  65 */     return (getId($$0) != -1);
/*     */   }
/*     */   
/*     */   public boolean contains(int $$0) {
/*  69 */     return (byId($$0) != null);
/*     */   }
/*     */   
/*     */   public int add(K $$0) {
/*  73 */     int $$1 = nextId();
/*     */     
/*  75 */     addMapping($$0, $$1);
/*     */     
/*  77 */     return $$1;
/*     */   }
/*     */   
/*     */   private int nextId() {
/*  81 */     while (this.nextId < this.byId.length && this.byId[this.nextId] != null) {
/*  82 */       this.nextId++;
/*     */     }
/*  84 */     return this.nextId;
/*     */   }
/*     */ 
/*     */   
/*     */   private void grow(int $$0) {
/*  89 */     K[] $$1 = this.keys;
/*  90 */     int[] $$2 = this.values;
/*     */     
/*  92 */     CrudeIncrementalIntIdentityHashBiMap<K> $$3 = new CrudeIncrementalIntIdentityHashBiMap($$0);
/*  93 */     for (int $$4 = 0; $$4 < $$1.length; $$4++) {
/*  94 */       if ($$1[$$4] != null) {
/*  95 */         $$3.addMapping($$1[$$4], $$2[$$4]);
/*     */       }
/*     */     } 
/*     */     
/*  99 */     this.keys = $$3.keys;
/* 100 */     this.values = $$3.values;
/* 101 */     this.byId = $$3.byId;
/* 102 */     this.nextId = $$3.nextId;
/* 103 */     this.size = $$3.size;
/*     */   }
/*     */   
/*     */   public void addMapping(K $$0, int $$1) {
/* 107 */     int $$2 = Math.max($$1, this.size + 1);
/* 108 */     if ($$2 >= this.keys.length * 0.8F) {
/* 109 */       int $$3 = this.keys.length << 1;
/* 110 */       while ($$3 < $$1) {
/* 111 */         $$3 <<= 1;
/*     */       }
/* 113 */       grow($$3);
/*     */     } 
/*     */     
/* 116 */     int $$4 = findEmpty(hash($$0));
/* 117 */     this.keys[$$4] = $$0;
/* 118 */     this.values[$$4] = $$1;
/* 119 */     this.byId[$$1] = $$0;
/* 120 */     this.size++;
/*     */     
/* 122 */     if ($$1 == this.nextId) {
/* 123 */       this.nextId++;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int hash(@Nullable K $$0) {
/* 139 */     return (Mth.murmurHash3Mixer(System.identityHashCode($$0)) & Integer.MAX_VALUE) % this.keys.length;
/*     */   }
/*     */   
/*     */   private int indexOf(@Nullable K $$0, int $$1) {
/* 143 */     for (int $$2 = $$1; $$2 < this.keys.length; $$2++) {
/* 144 */       if (this.keys[$$2] == $$0) {
/* 145 */         return $$2;
/*     */       }
/* 147 */       if (this.keys[$$2] == EMPTY_SLOT) {
/* 148 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/* 152 */     for (int $$3 = 0; $$3 < $$1; $$3++) {
/* 153 */       if (this.keys[$$3] == $$0) {
/* 154 */         return $$3;
/*     */       }
/* 156 */       if (this.keys[$$3] == EMPTY_SLOT) {
/* 157 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/* 161 */     return -1;
/*     */   }
/*     */   
/*     */   private int findEmpty(int $$0) {
/* 165 */     for (int $$1 = $$0; $$1 < this.keys.length; $$1++) {
/* 166 */       if (this.keys[$$1] == EMPTY_SLOT) {
/* 167 */         return $$1;
/*     */       }
/*     */     } 
/*     */     
/* 171 */     for (int $$2 = 0; $$2 < $$0; $$2++) {
/* 172 */       if (this.keys[$$2] == EMPTY_SLOT) {
/* 173 */         return $$2;
/*     */       }
/*     */     } 
/*     */     
/* 177 */     throw new RuntimeException("Overflowed :(");
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<K> iterator() {
/* 182 */     return (Iterator<K>)Iterators.filter((Iterator)Iterators.forArray((Object[])this.byId), Predicates.notNull());
/*     */   }
/*     */   
/*     */   public void clear() {
/* 186 */     Arrays.fill((Object[])this.keys, (Object)null);
/* 187 */     Arrays.fill((Object[])this.byId, (Object)null);
/* 188 */     this.nextId = 0;
/* 189 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 194 */     return this.size;
/*     */   }
/*     */   
/*     */   public CrudeIncrementalIntIdentityHashBiMap<K> copy() {
/* 198 */     return new CrudeIncrementalIntIdentityHashBiMap((K[])this.keys
/* 199 */         .clone(), (int[])this.values
/* 200 */         .clone(), (K[])this.byId
/* 201 */         .clone(), this.nextId, this.size);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\CrudeIncrementalIntIdentityHashBiMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */