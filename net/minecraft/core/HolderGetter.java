/*    */ package net.minecraft.core;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.TagKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface HolderGetter<T>
/*    */ {
/*    */   default Holder.Reference<T> getOrThrow(ResourceKey<T> $$0) {
/* 15 */     return get($$0).<Throwable>orElseThrow(() -> new IllegalStateException("Missing element " + $$0));
/*    */   }
/*    */   
/*    */   Optional<Holder.Reference<T>> get(ResourceKey<T> paramResourceKey);
/*    */   
/*    */   default HolderSet.Named<T> getOrThrow(TagKey<T> $$0) {
/* 21 */     return get($$0).<Throwable>orElseThrow(() -> new IllegalStateException("Missing tag " + $$0));
/*    */   }
/*    */   
/*    */   Optional<HolderSet.Named<T>> get(TagKey<T> paramTagKey);
/*    */   
/*    */   public static interface Provider {
/*    */     default <T> HolderGetter<T> lookupOrThrow(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 28 */       return (HolderGetter<T>)lookup($$0).orElseThrow(() -> new IllegalStateException("Registry " + $$0.location() + " not found"));
/*    */     }
/*    */     
/*    */     <T> Optional<HolderGetter<T>> lookup(ResourceKey<? extends Registry<? extends T>> param1ResourceKey);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */