/*    */ package net.minecraft.core;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.AbstractList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class NonNullList<E> extends AbstractList<E> {
/*    */   private final List<E> list;
/*    */   
/*    */   public static <E> NonNullList<E> create() {
/* 14 */     return new NonNullList<>(Lists.newArrayList(), null);
/*    */   } @Nullable
/*    */   private final E defaultValue;
/*    */   public static <E> NonNullList<E> createWithCapacity(int $$0) {
/* 18 */     return new NonNullList<>(Lists.newArrayListWithCapacity($$0), null);
/*    */   }
/*    */ 
/*    */   
/*    */   public static <E> NonNullList<E> withSize(int $$0, E $$1) {
/* 23 */     Validate.notNull($$1);
/*    */     
/* 25 */     Object[] $$2 = new Object[$$0];
/* 26 */     Arrays.fill($$2, $$1);
/* 27 */     return new NonNullList<>(Arrays.asList((E[])$$2), $$1);
/*    */   }
/*    */   
/*    */   @SafeVarargs
/*    */   public static <E> NonNullList<E> of(E $$0, E... $$1) {
/* 32 */     return new NonNullList<>(Arrays.asList($$1), $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected NonNullList(List<E> $$0, @Nullable E $$1) {
/* 40 */     this.list = $$0;
/* 41 */     this.defaultValue = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public E get(int $$0) {
/* 47 */     return this.list.get($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public E set(int $$0, E $$1) {
/* 52 */     Validate.notNull($$1);
/*    */     
/* 54 */     return this.list.set($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(int $$0, E $$1) {
/* 59 */     Validate.notNull($$1);
/*    */     
/* 61 */     this.list.add($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public E remove(int $$0) {
/* 66 */     return this.list.remove($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 71 */     return this.list.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 76 */     if (this.defaultValue == null) {
/* 77 */       super.clear();
/*    */     } else {
/* 79 */       for (int $$0 = 0; $$0 < size(); $$0++)
/* 80 */         set($$0, this.defaultValue); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\NonNullList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */