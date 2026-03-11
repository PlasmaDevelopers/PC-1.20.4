/*    */ package net.minecraft.resources;
/*    */ 
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.Lifecycle;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.HolderOwner;
/*    */ import net.minecraft.core.Registry;
/*    */ 
/*    */ public final class RegistryFixedCodec<E>
/*    */   implements Codec<Holder<E>> {
/*    */   private final ResourceKey<? extends Registry<E>> registryKey;
/*    */   
/*    */   public static <E> RegistryFixedCodec<E> create(ResourceKey<? extends Registry<E>> $$0) {
/* 19 */     return new RegistryFixedCodec<>($$0);
/*    */   }
/*    */   
/*    */   private RegistryFixedCodec(ResourceKey<? extends Registry<E>> $$0) {
/* 23 */     this.registryKey = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> DataResult<T> encode(Holder<E> $$0, DynamicOps<T> $$1, T $$2) {
/* 28 */     if ($$1 instanceof RegistryOps) { RegistryOps<?> $$3 = (RegistryOps)$$1;
/* 29 */       Optional<HolderOwner<E>> $$4 = $$3.owner(this.registryKey);
/* 30 */       if ($$4.isPresent()) {
/* 31 */         if (!$$0.canSerializeIn($$4.get())) {
/* 32 */           return DataResult.error(() -> "Element " + $$0 + " is not valid in current registry set");
/*    */         }
/* 34 */         return (DataResult<T>)$$0.unwrap().map($$2 -> ResourceLocation.CODEC.encode($$2.location(), $$0, $$1), $$0 -> DataResult.error(()));
/*    */       }  }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     return DataResult.error(() -> "Can't access registry " + this.registryKey);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> $$0, T $$1) {
/* 46 */     if ($$0 instanceof RegistryOps) { RegistryOps<?> $$2 = (RegistryOps)$$0;
/* 47 */       Optional<HolderGetter<E>> $$3 = $$2.getter(this.registryKey);
/* 48 */       if ($$3.isPresent()) {
/* 49 */         return ResourceLocation.CODEC.decode($$0, $$1).flatMap($$1 -> {
/*    */               ResourceLocation $$2 = (ResourceLocation)$$1.getFirst();
/*    */               
/*    */               return ((DataResult)((HolderGetter)$$0.get()).get(ResourceKey.create(this.registryKey, $$2)).map(DataResult::success).orElseGet(())).map(()).setLifecycle(Lifecycle.stable());
/*    */             });
/*    */       } }
/*    */ 
/*    */     
/* 57 */     return DataResult.error(() -> "Can't access registry " + this.registryKey);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 62 */     return "RegistryFixedCodec[" + this.registryKey + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\RegistryFixedCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */