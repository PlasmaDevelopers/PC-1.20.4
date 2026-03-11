/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.resources.RegistryOps;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.util.JavaOps;
/*    */ 
/*    */ public class Cloner<T>
/*    */ {
/*    */   private final Codec<T> directCodec;
/*    */   
/*    */   Cloner(Codec<T> $$0) {
/* 18 */     this.directCodec = $$0;
/*    */   }
/*    */   
/*    */   public T clone(T $$0, HolderLookup.Provider $$1, HolderLookup.Provider $$2) {
/* 22 */     RegistryOps registryOps1 = RegistryOps.create((DynamicOps)JavaOps.INSTANCE, $$1);
/* 23 */     RegistryOps registryOps2 = RegistryOps.create((DynamicOps)JavaOps.INSTANCE, $$2);
/*    */     
/* 25 */     Object $$5 = Util.getOrThrow(this.directCodec.encodeStart((DynamicOps)registryOps1, $$0), $$0 -> new IllegalStateException("Failed to encode: " + $$0));
/* 26 */     return (T)Util.getOrThrow(this.directCodec.parse((DynamicOps)registryOps2, $$5), $$0 -> new IllegalStateException("Failed to decode: " + $$0));
/*    */   }
/*    */   
/*    */   public static class Factory {
/* 30 */     private final Map<ResourceKey<? extends Registry<?>>, Cloner<?>> codecs = new HashMap<>();
/*    */     
/*    */     public <T> Factory addCodec(ResourceKey<? extends Registry<? extends T>> $$0, Codec<T> $$1) {
/* 33 */       this.codecs.put($$0, new Cloner($$1));
/* 34 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     @Nullable
/*    */     public <T> Cloner<T> cloner(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 40 */       return (Cloner<T>)this.codecs.get($$0);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Cloner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */