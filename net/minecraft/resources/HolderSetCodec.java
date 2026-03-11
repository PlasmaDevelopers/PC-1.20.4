/*     */ package net.minecraft.resources;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.HolderOwner;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ 
/*     */ public class HolderSetCodec<E> implements Codec<HolderSet<E>> {
/*     */   private final ResourceKey<? extends Registry<E>> registryKey;
/*     */   private final Codec<Holder<E>> elementCodec;
/*     */   private final Codec<List<Holder<E>>> homogenousListCodec;
/*     */   private final Codec<Either<TagKey<E>, List<Holder<E>>>> registryAwareCodec;
/*     */   
/*     */   private static <E> Codec<List<Holder<E>>> homogenousList(Codec<Holder<E>> $$0, boolean $$1) {
/*  27 */     Codec<List<Holder<E>>> $$2 = ExtraCodecs.validate($$0.listOf(), ExtraCodecs.ensureHomogenous(Holder::kind));
/*     */     
/*  29 */     if ($$1) {
/*  30 */       return $$2;
/*     */     }
/*     */     
/*  33 */     return Codec.either($$2, $$0)
/*     */ 
/*     */       
/*  36 */       .xmap($$0 -> (List)$$0.map((), List::of), $$0 -> ($$0.size() == 1) ? Either.right($$0.get(0)) : Either.left($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <E> Codec<HolderSet<E>> create(ResourceKey<? extends Registry<E>> $$0, Codec<Holder<E>> $$1, boolean $$2) {
/*  43 */     return new HolderSetCodec<>($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private HolderSetCodec(ResourceKey<? extends Registry<E>> $$0, Codec<Holder<E>> $$1, boolean $$2) {
/*  47 */     this.registryKey = $$0;
/*  48 */     this.elementCodec = $$1;
/*  49 */     this.homogenousListCodec = homogenousList($$1, $$2);
/*  50 */     this.registryAwareCodec = Codec.either(
/*  51 */         TagKey.hashedCodec($$0), this.homogenousListCodec);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> DataResult<Pair<HolderSet<E>, T>> decode(DynamicOps<T> $$0, T $$1) {
/*  58 */     if ($$0 instanceof RegistryOps) { RegistryOps<T> $$2 = (RegistryOps<T>)$$0;
/*  59 */       Optional<HolderGetter<E>> $$3 = $$2.getter(this.registryKey);
/*  60 */       if ($$3.isPresent()) {
/*  61 */         HolderGetter<E> $$4 = $$3.get();
/*  62 */         return this.registryAwareCodec
/*  63 */           .decode($$0, $$1)
/*  64 */           .map($$1 -> $$1.mapFirst(()));
/*     */       }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     return decodeWithoutRegistry($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> DataResult<T> encode(HolderSet<E> $$0, DynamicOps<T> $$1, T $$2) {
/*  79 */     if ($$1 instanceof RegistryOps) { RegistryOps<T> $$3 = (RegistryOps<T>)$$1;
/*  80 */       Optional<HolderOwner<E>> $$4 = $$3.owner(this.registryKey);
/*  81 */       if ($$4.isPresent()) {
/*  82 */         if (!$$0.canSerializeIn($$4.get())) {
/*  83 */           return DataResult.error(() -> "HolderSet " + $$0 + " is not valid in current registry set");
/*     */         }
/*  85 */         return this.registryAwareCodec.encode($$0.unwrap().mapRight(List::copyOf), $$1, $$2);
/*     */       }  }
/*     */ 
/*     */ 
/*     */     
/*  90 */     return encodeWithoutRegistry($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private <T> DataResult<Pair<HolderSet<E>, T>> decodeWithoutRegistry(DynamicOps<T> $$0, T $$1) {
/*  94 */     return this.elementCodec.listOf().decode($$0, $$1).flatMap($$0 -> {
/*     */           List<Holder.Direct<E>> $$1 = new ArrayList<>();
/*     */           for (Holder<E> $$2 : (Iterable<Holder<E>>)$$0.getFirst()) {
/*     */             if ($$2 instanceof Holder.Direct) {
/*     */               Holder.Direct<E> $$3 = (Holder.Direct<E>)$$2;
/*     */               $$1.add($$3);
/*     */               continue;
/*     */             } 
/*     */             return DataResult.error(());
/*     */           } 
/*     */           return DataResult.success(new Pair(HolderSet.direct($$1), $$0.getSecond()));
/*     */         });
/*     */   }
/*     */   private <T> DataResult<T> encodeWithoutRegistry(HolderSet<E> $$0, DynamicOps<T> $$1, T $$2) {
/* 108 */     return this.homogenousListCodec.encode($$0.stream().toList(), $$1, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\HolderSetCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */