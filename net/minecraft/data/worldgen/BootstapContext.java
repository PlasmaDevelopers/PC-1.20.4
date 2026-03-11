/*    */ package net.minecraft.data.worldgen;
/*    */ 
/*    */ import com.mojang.serialization.Lifecycle;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ public interface BootstapContext<T> {
/*    */   Holder.Reference<T> register(ResourceKey<T> paramResourceKey, T paramT, Lifecycle paramLifecycle);
/*    */   
/*    */   default Holder.Reference<T> register(ResourceKey<T> $$0, T $$1) {
/* 13 */     return register($$0, $$1, Lifecycle.stable());
/*    */   }
/*    */   
/*    */   <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> paramResourceKey);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\BootstapContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */