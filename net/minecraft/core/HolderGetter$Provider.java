/*    */ package net.minecraft.core;
/*    */ 
/*    */ import java.util.Optional;
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
/*    */ public interface Provider
/*    */ {
/*    */   <T> Optional<HolderGetter<T>> lookup(ResourceKey<? extends Registry<? extends T>> paramResourceKey);
/*    */   
/*    */   default <T> HolderGetter<T> lookupOrThrow(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 28 */     return (HolderGetter<T>)lookup($$0).orElseThrow(() -> new IllegalStateException("Registry " + $$0.location() + " not found"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderGetter$Provider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */