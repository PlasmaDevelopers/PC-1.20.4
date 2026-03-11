/*     */ package net.minecraft.util;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SortedArraySet<T>
/*     */   extends AbstractSet<T>
/*     */ {
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 10;
/*     */   private final Comparator<T> comparator;
/*     */   T[] contents;
/*     */   int size;
/*     */   
/*     */   private SortedArraySet(int $$0, Comparator<T> $$1) {
/*  26 */     this.comparator = $$1;
/*     */     
/*  28 */     if ($$0 < 0) {
/*  29 */       throw new IllegalArgumentException("Initial capacity (" + $$0 + ") is negative");
/*     */     }
/*  31 */     this.contents = castRawArray(new Object[$$0]);
/*     */   }
/*     */   
/*     */   public static <T extends Comparable<T>> SortedArraySet<T> create() {
/*  35 */     return create(10);
/*     */   }
/*     */   
/*     */   public static <T extends Comparable<T>> SortedArraySet<T> create(int $$0) {
/*  39 */     return new SortedArraySet<>($$0, (Comparator)Comparator.naturalOrder());
/*     */   }
/*     */   
/*     */   public static <T> SortedArraySet<T> create(Comparator<T> $$0) {
/*  43 */     return create($$0, 10);
/*     */   }
/*     */   
/*     */   public static <T> SortedArraySet<T> create(Comparator<T> $$0, int $$1) {
/*  47 */     return new SortedArraySet<>($$1, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T[] castRawArray(Object[] $$0) {
/*  52 */     return (T[])$$0;
/*     */   }
/*     */   
/*     */   private int findIndex(T $$0) {
/*  56 */     return Arrays.binarySearch(this.contents, 0, this.size, $$0, this.comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getInsertionPosition(int $$0) {
/*  65 */     return -$$0 - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(T $$0) {
/*  70 */     int $$1 = findIndex($$0);
/*  71 */     if ($$1 >= 0) {
/*  72 */       return false;
/*     */     }
/*     */     
/*  75 */     int $$2 = getInsertionPosition($$1);
/*  76 */     addInternal($$0, $$2);
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   private void grow(int $$0) {
/*  81 */     if ($$0 <= this.contents.length) {
/*     */       return;
/*     */     }
/*  84 */     if (this.contents != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
/*  85 */       $$0 = (int)Math.max(Math.min(this.contents.length + (this.contents.length >> 1), 2147483639L), $$0);
/*  86 */     } else if ($$0 < 10) {
/*  87 */       $$0 = 10;
/*     */     } 
/*     */     
/*  90 */     Object[] $$1 = new Object[$$0];
/*  91 */     System.arraycopy(this.contents, 0, $$1, 0, this.size);
/*  92 */     this.contents = castRawArray($$1);
/*     */   }
/*     */   
/*     */   private void addInternal(T $$0, int $$1) {
/*  96 */     grow(this.size + 1);
/*  97 */     if ($$1 != this.size) {
/*  98 */       System.arraycopy(this.contents, $$1, this.contents, $$1 + 1, this.size - $$1);
/*     */     }
/* 100 */     this.contents[$$1] = $$0;
/* 101 */     this.size++;
/*     */   }
/*     */   
/*     */   void removeInternal(int $$0) {
/* 105 */     this.size--;
/* 106 */     if ($$0 != this.size) {
/* 107 */       System.arraycopy(this.contents, $$0 + 1, this.contents, $$0, this.size - $$0);
/*     */     }
/* 109 */     this.contents[this.size] = null;
/*     */   }
/*     */   
/*     */   private T getInternal(int $$0) {
/* 113 */     return this.contents[$$0];
/*     */   }
/*     */   
/*     */   public T addOrGet(T $$0) {
/* 117 */     int $$1 = findIndex($$0);
/* 118 */     if ($$1 >= 0) {
/* 119 */       return getInternal($$1);
/*     */     }
/*     */     
/* 122 */     addInternal($$0, getInsertionPosition($$1));
/* 123 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Object $$0) {
/* 129 */     int $$1 = findIndex((T)$$0);
/* 130 */     if ($$1 >= 0) {
/* 131 */       removeInternal($$1);
/* 132 */       return true;
/*     */     } 
/* 134 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public T get(T $$0) {
/* 139 */     int $$1 = findIndex($$0);
/* 140 */     if ($$1 >= 0) {
/* 141 */       return getInternal($$1);
/*     */     }
/* 143 */     return null;
/*     */   }
/*     */   
/*     */   public T first() {
/* 147 */     return getInternal(0);
/*     */   }
/*     */   
/*     */   public T last() {
/* 151 */     return getInternal(this.size - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Object $$0) {
/* 157 */     int $$1 = findIndex((T)$$0);
/* 158 */     return ($$1 >= 0);
/*     */   }
/*     */   
/*     */   private class ArrayIterator implements Iterator<T> {
/*     */     private int index;
/* 163 */     private int last = -1;
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 167 */       return (this.index < SortedArraySet.this.size);
/*     */     }
/*     */ 
/*     */     
/*     */     public T next() {
/* 172 */       if (this.index >= SortedArraySet.this.size) {
/* 173 */         throw new NoSuchElementException();
/*     */       }
/* 175 */       this.last = this.index++;
/* 176 */       return SortedArraySet.this.contents[this.last];
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 181 */       if (this.last == -1) {
/* 182 */         throw new IllegalStateException();
/*     */       }
/* 184 */       SortedArraySet.this.removeInternal(this.last);
/* 185 */       this.index--;
/* 186 */       this.last = -1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/* 192 */     return new ArrayIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 197 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 202 */     return Arrays.copyOf(this.contents, this.size, Object[].class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <U> U[] toArray(U[] $$0) {
/* 208 */     if ($$0.length < this.size) {
/* 209 */       return Arrays.copyOf(this.contents, this.size, (Class)$$0.getClass());
/*     */     }
/* 211 */     System.arraycopy(this.contents, 0, $$0, 0, this.size);
/* 212 */     if ($$0.length > this.size) {
/* 213 */       $$0[this.size] = null;
/*     */     }
/* 215 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 220 */     Arrays.fill((Object[])this.contents, 0, this.size, (Object)null);
/* 221 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 226 */     if (this == $$0) {
/* 227 */       return true;
/*     */     }
/* 229 */     if ($$0 instanceof SortedArraySet) { SortedArraySet<?> $$1 = (SortedArraySet)$$0;
/* 230 */       if (this.comparator.equals($$1.comparator)) {
/* 231 */         return (this.size == $$1.size && Arrays.equals((Object[])this.contents, (Object[])$$1.contents));
/*     */       } }
/*     */ 
/*     */     
/* 235 */     return super.equals($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SortedArraySet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */