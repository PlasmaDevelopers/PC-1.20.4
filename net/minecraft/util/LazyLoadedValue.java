/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.base.Suppliers;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class LazyLoadedValue<T>
/*    */ {
/*    */   private final Supplier<T> factory;
/*    */   
/*    */   public LazyLoadedValue(Supplier<T> $$0) {
/* 15 */     Objects.requireNonNull($$0); this.factory = (Supplier<T>)Suppliers.memoize($$0::get);
/*    */   }
/*    */   
/*    */   public T get() {
/* 19 */     return this.factory.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\LazyLoadedValue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */