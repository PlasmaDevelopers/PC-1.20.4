/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.RandomAccess;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.UnaryOperator;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class ArrayListDeque<T>
/*     */   extends AbstractList<T> implements Serializable, Cloneable, Deque<T>, RandomAccess {
/*     */   private static final int MIN_GROWTH = 1;
/*     */   private Object[] contents;
/*     */   private int head;
/*     */   private int size;
/*     */   
/*     */   public ArrayListDeque() {
/*  24 */     this(1);
/*     */   }
/*     */   
/*     */   public ArrayListDeque(int $$0) {
/*  28 */     this.contents = new Object[$$0];
/*  29 */     this.head = 0;
/*  30 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  35 */     return this.size;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public int capacity() {
/*  40 */     return this.contents.length;
/*     */   }
/*     */   
/*     */   private int getIndex(int $$0) {
/*  44 */     return ($$0 + this.head) % this.contents.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(int $$0) {
/*  49 */     verifyIndexInRange($$0);
/*  50 */     return getInner(getIndex($$0));
/*     */   }
/*     */   
/*     */   private static void verifyIndexInRange(int $$0, int $$1) {
/*  54 */     if ($$0 < 0 || $$0 >= $$1) {
/*  55 */       throw new IndexOutOfBoundsException($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void verifyIndexInRange(int $$0) {
/*  60 */     verifyIndexInRange($$0, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   private T getInner(int $$0) {
/*  65 */     return (T)this.contents[$$0];
/*     */   }
/*     */ 
/*     */   
/*     */   public T set(int $$0, T $$1) {
/*  70 */     verifyIndexInRange($$0);
/*  71 */     Objects.requireNonNull($$1);
/*  72 */     int $$2 = getIndex($$0);
/*  73 */     T $$3 = getInner($$2);
/*  74 */     this.contents[$$2] = $$1;
/*  75 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(int $$0, T $$1) {
/*  80 */     verifyIndexInRange($$0, this.size + 1);
/*  81 */     Objects.requireNonNull($$1);
/*  82 */     if (this.size == this.contents.length) {
/*  83 */       grow();
/*     */     }
/*  85 */     int $$2 = getIndex($$0);
/*  86 */     if ($$0 == this.size) {
/*  87 */       this.contents[$$2] = $$1;
/*  88 */     } else if ($$0 == 0) {
/*  89 */       this.head--;
/*  90 */       if (this.head < 0) {
/*  91 */         this.head += this.contents.length;
/*     */       }
/*  93 */       this.contents[getIndex(0)] = $$1;
/*     */     } else {
/*  95 */       for (int $$3 = this.size - 1; $$3 >= $$0; $$3--) {
/*  96 */         this.contents[getIndex($$3 + 1)] = this.contents[getIndex($$3)];
/*     */       }
/*  98 */       this.contents[$$2] = $$1;
/*     */     } 
/* 100 */     this.modCount++;
/* 101 */     this.size++;
/*     */   }
/*     */ 
/*     */   
/*     */   private void grow() {
/* 106 */     int $$0 = this.contents.length + Math.max(this.contents.length >> 1, 1);
/* 107 */     Object[] $$1 = new Object[$$0];
/* 108 */     copyCount($$1, this.size);
/* 109 */     this.head = 0;
/* 110 */     this.contents = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public T remove(int $$0) {
/* 115 */     verifyIndexInRange($$0);
/* 116 */     int $$1 = getIndex($$0);
/* 117 */     T $$2 = getInner($$1);
/* 118 */     if ($$0 == 0) {
/* 119 */       this.contents[$$1] = null;
/* 120 */       this.head++;
/* 121 */     } else if ($$0 == this.size - 1) {
/* 122 */       this.contents[$$1] = null;
/*     */     } else {
/* 124 */       for (int $$3 = $$0 + 1; $$3 < this.size; $$3++) {
/* 125 */         this.contents[getIndex($$3 - 1)] = get($$3);
/*     */       }
/* 127 */       this.contents[getIndex(this.size - 1)] = null;
/*     */     } 
/* 129 */     this.modCount++;
/* 130 */     this.size--;
/* 131 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeIf(Predicate<? super T> $$0) {
/* 136 */     int $$1 = 0;
/* 137 */     for (int $$2 = 0; $$2 < this.size; $$2++) {
/* 138 */       T $$3 = get($$2);
/* 139 */       if ($$0.test($$3)) {
/* 140 */         $$1++;
/* 141 */       } else if ($$1 != 0) {
/* 142 */         this.contents[getIndex($$2 - $$1)] = $$3;
/* 143 */         this.contents[getIndex($$2)] = null;
/*     */       } 
/*     */     } 
/* 146 */     this.modCount += $$1;
/* 147 */     this.size -= $$1;
/* 148 */     return ($$1 != 0);
/*     */   }
/*     */   
/*     */   private void copyCount(Object[] $$0, int $$1) {
/* 152 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 153 */       $$0[$$2] = get($$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void replaceAll(UnaryOperator<T> $$0) {
/* 159 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/* 160 */       int $$2 = getIndex($$1);
/* 161 */       this.contents[$$2] = Objects.requireNonNull($$0.apply(getInner($$1)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super T> $$0) {
/* 167 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/* 168 */       $$0.accept(get($$1));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFirst(T $$0) {
/* 174 */     add(0, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addLast(T $$0) {
/* 179 */     add(this.size, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean offerFirst(T $$0) {
/* 184 */     addFirst($$0);
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean offerLast(T $$0) {
/* 190 */     addLast($$0);
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public T removeFirst() {
/* 196 */     if (this.size == 0) {
/* 197 */       throw new NoSuchElementException();
/*     */     }
/* 199 */     return remove(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T removeLast() {
/* 204 */     if (this.size == 0) {
/* 205 */       throw new NoSuchElementException();
/*     */     }
/* 207 */     return remove(this.size - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T pollFirst() {
/* 213 */     if (this.size == 0) {
/* 214 */       return null;
/*     */     }
/* 216 */     return removeFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T pollLast() {
/* 222 */     if (this.size == 0) {
/* 223 */       return null;
/*     */     }
/* 225 */     return removeLast();
/*     */   }
/*     */ 
/*     */   
/*     */   public T getFirst() {
/* 230 */     if (this.size == 0) {
/* 231 */       throw new NoSuchElementException();
/*     */     }
/* 233 */     return get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T getLast() {
/* 238 */     if (this.size == 0) {
/* 239 */       throw new NoSuchElementException();
/*     */     }
/* 241 */     return get(this.size - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T peekFirst() {
/* 247 */     if (this.size == 0) {
/* 248 */       return null;
/*     */     }
/* 250 */     return getFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T peekLast() {
/* 256 */     if (this.size == 0) {
/* 257 */       return null;
/*     */     }
/* 259 */     return getLast();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeFirstOccurrence(Object $$0) {
/* 264 */     for (int $$1 = 0; $$1 < this.size; $$1++) {
/* 265 */       T $$2 = get($$1);
/* 266 */       if (Objects.equals($$0, $$2)) {
/* 267 */         remove($$1);
/* 268 */         return true;
/*     */       } 
/*     */     } 
/* 271 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeLastOccurrence(Object $$0) {
/* 276 */     for (int $$1 = this.size - 1; $$1 >= 0; $$1--) {
/* 277 */       T $$2 = get($$1);
/* 278 */       if (Objects.equals($$0, $$2)) {
/* 279 */         remove($$1);
/* 280 */         return true;
/*     */       } 
/*     */     } 
/* 283 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean offer(T $$0) {
/* 288 */     return offerLast($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T remove() {
/* 293 */     return removeFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T poll() {
/* 299 */     return pollFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public T element() {
/* 304 */     return getFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public T peek() {
/* 310 */     return peekFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(T $$0) {
/* 315 */     addFirst($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public T pop() {
/* 320 */     return removeFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<T> descendingIterator() {
/* 325 */     return new DescendingIterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private class DescendingIterator
/*     */     implements Iterator<T>
/*     */   {
/* 332 */     private int index = ArrayListDeque.this.size() - 1;
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/* 337 */       return (this.index >= 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public T next() {
/* 342 */       return ArrayListDeque.this.get(this.index--);
/*     */     }
/*     */ 
/*     */     
/*     */     public void remove() {
/* 347 */       ArrayListDeque.this.remove(this.index + 1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ArrayListDeque.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */