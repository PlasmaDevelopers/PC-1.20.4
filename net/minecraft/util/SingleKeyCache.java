/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SingleKeyCache<K, V>
/*    */ {
/*    */   private final Function<K, V> computeValue;
/*    */   @Nullable
/* 14 */   private K cacheKey = null;
/*    */   
/*    */   @Nullable
/*    */   private V cachedValue;
/*    */   
/*    */   public SingleKeyCache(Function<K, V> $$0) {
/* 20 */     this.computeValue = $$0;
/*    */   }
/*    */   
/*    */   public V getValue(K $$0) {
/* 24 */     if (this.cachedValue == null || !Objects.equals(this.cacheKey, $$0)) {
/* 25 */       this.cachedValue = this.computeValue.apply($$0);
/* 26 */       this.cacheKey = $$0;
/*    */     } 
/* 28 */     return this.cachedValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SingleKeyCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */