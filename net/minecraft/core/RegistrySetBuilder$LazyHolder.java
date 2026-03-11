/*    */ package net.minecraft.core;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class LazyHolder<T>
/*    */   extends Holder.Reference<T>
/*    */ {
/*    */   @Nullable
/*    */   Supplier<T> supplier;
/*    */   
/*    */   protected LazyHolder(HolderOwner<T> $$0, @Nullable ResourceKey<T> $$1) {
/* 35 */     super(Holder.Reference.Type.STAND_ALONE, $$0, $$1, null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void bindValue(T $$0) {
/* 40 */     super.bindValue($$0);
/* 41 */     this.supplier = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public T value() {
/* 46 */     if (this.supplier != null) {
/* 47 */       bindValue(this.supplier.get());
/*    */     }
/* 49 */     return super.value();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySetBuilder$LazyHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */