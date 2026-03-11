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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RegistryFileCodec<E>
/*    */   implements Codec<Holder<E>>
/*    */ {
/*    */   private final ResourceKey<? extends Registry<E>> registryKey;
/*    */   private final Codec<E> elementCodec;
/*    */   private final boolean allowInline;
/*    */   
/*    */   public static <E> RegistryFileCodec<E> create(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1) {
/* 26 */     return create($$0, $$1, true);
/*    */   }
/*    */   
/*    */   public static <E> RegistryFileCodec<E> create(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1, boolean $$2) {
/* 30 */     return new RegistryFileCodec<>($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   private RegistryFileCodec(ResourceKey<? extends Registry<E>> $$0, Codec<E> $$1, boolean $$2) {
/* 34 */     this.registryKey = $$0;
/* 35 */     this.elementCodec = $$1;
/* 36 */     this.allowInline = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> DataResult<T> encode(Holder<E> $$0, DynamicOps<T> $$1, T $$2) {
/* 41 */     if ($$1 instanceof RegistryOps) { RegistryOps<?> $$3 = (RegistryOps)$$1;
/* 42 */       Optional<HolderOwner<E>> $$4 = $$3.owner(this.registryKey);
/* 43 */       if ($$4.isPresent()) {
/* 44 */         if (!$$0.canSerializeIn($$4.get())) {
/* 45 */           return DataResult.error(() -> "Element " + $$0 + " is not valid in current registry set");
/*    */         }
/* 47 */         return (DataResult<T>)$$0.unwrap().map($$2 -> ResourceLocation.CODEC.encode($$2.location(), $$0, $$1), $$2 -> this.elementCodec.encode($$2, $$0, $$1));
/*    */       }  }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     return this.elementCodec.encode($$0.value(), $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> $$0, T $$1) {
/* 59 */     if ($$0 instanceof RegistryOps) { RegistryOps<?> $$2 = (RegistryOps)$$0;
/* 60 */       Optional<HolderGetter<E>> $$3 = $$2.getter(this.registryKey);
/* 61 */       if ($$3.isEmpty()) {
/* 62 */         return DataResult.error(() -> "Registry does not exist: " + this.registryKey);
/*    */       }
/*    */       
/* 65 */       HolderGetter<E> $$4 = $$3.get();
/*    */       
/* 67 */       DataResult<Pair<ResourceLocation, T>> $$5 = ResourceLocation.CODEC.decode($$0, $$1);
/* 68 */       if ($$5.result().isEmpty()) {
/* 69 */         if (!this.allowInline) {
/* 70 */           return DataResult.error(() -> "Inline definitions not allowed here");
/*    */         }
/* 72 */         return this.elementCodec.decode($$0, $$1).map($$0 -> $$0.mapFirst(Holder::direct));
/*    */       } 
/*    */       
/* 75 */       Pair<ResourceLocation, T> $$6 = $$5.result().get();
/* 76 */       ResourceKey<E> $$7 = ResourceKey.create(this.registryKey, (ResourceLocation)$$6.getFirst());
/* 77 */       return ((DataResult)$$4.get($$7)
/* 78 */         .map(DataResult::success).orElseGet(() -> DataResult.error(())))
/* 79 */         .map($$1 -> Pair.of($$1, $$0.getSecond())).setLifecycle(Lifecycle.stable()); }
/*    */     
/* 81 */     return this.elementCodec.decode($$0, $$1).map($$0 -> $$0.mapFirst(Holder::direct));
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 86 */     return "RegistryFileCodec[" + this.registryKey + " " + this.elementCodec + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\RegistryFileCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */