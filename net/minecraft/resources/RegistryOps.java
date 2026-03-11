/*    */ package net.minecraft.resources;
/*    */ 
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.HolderOwner;
/*    */ import net.minecraft.core.Registry;
/*    */ 
/*    */ public class RegistryOps<T> extends DelegatingOps<T> {
/*    */   private final RegistryInfoLookup lookupProvider;
/*    */   
/*    */   public static final class RegistryInfo<T> extends Record {
/*    */     private final HolderOwner<T> owner;
/*    */     private final HolderGetter<T> getter;
/*    */     private final Lifecycle elementsLifecycle;
/*    */     
/* 19 */     public RegistryInfo(HolderOwner<T> $$0, HolderGetter<T> $$1, Lifecycle $$2) { this.owner = $$0; this.getter = $$1; this.elementsLifecycle = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/resources/RegistryOps$RegistryInfo;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #19	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 19 */       //   0	7	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo<TT;>; } public HolderOwner<T> owner() { return this.owner; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/resources/RegistryOps$RegistryInfo;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #19	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/resources/RegistryOps$RegistryInfo;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #19	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 19 */       //   0	8	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo<TT;>; } public HolderGetter<T> getter() { return this.getter; } public Lifecycle elementsLifecycle() { return this.elementsLifecycle; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static RegistryInfoLookup memoizeLookup(final RegistryInfoLookup original) {
/* 26 */     return new RegistryInfoLookup() {
/* 27 */         private final Map<ResourceKey<? extends Registry<?>>, Optional<? extends RegistryOps.RegistryInfo<?>>> lookups = new HashMap<>();
/*    */ 
/*    */ 
/*    */         
/*    */         public <T> Optional<RegistryOps.RegistryInfo<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 32 */           Objects.requireNonNull(original); return (Optional<RegistryOps.RegistryInfo<T>>)this.lookups.computeIfAbsent($$0, original::lookup);
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> RegistryOps<T> create(DynamicOps<T> $$0, final HolderLookup.Provider lookupProvider) {
/* 40 */     return create($$0, memoizeLookup(new RegistryInfoLookup()
/*    */           {
/*    */             public <E> Optional<RegistryOps.RegistryInfo<E>> lookup(ResourceKey<? extends Registry<? extends E>> $$0) {
/* 43 */               return lookupProvider.lookup($$0).map($$0 -> new RegistryOps.RegistryInfo((HolderOwner<?>)$$0, (HolderGetter<?>)$$0, $$0.registryLifecycle()));
/*    */             }
/*    */           }));
/*    */   }
/*    */   
/*    */   public static <T> RegistryOps<T> create(DynamicOps<T> $$0, RegistryInfoLookup $$1) {
/* 49 */     return new RegistryOps<>($$0, $$1);
/*    */   }
/*    */   
/*    */   private RegistryOps(DynamicOps<T> $$0, RegistryInfoLookup $$1) {
/* 53 */     super($$0);
/* 54 */     this.lookupProvider = $$1;
/*    */   }
/*    */   
/*    */   public <E> Optional<HolderOwner<E>> owner(ResourceKey<? extends Registry<? extends E>> $$0) {
/* 58 */     return this.lookupProvider.<E>lookup($$0).map(RegistryInfo::owner);
/*    */   }
/*    */   
/*    */   public <E> Optional<HolderGetter<E>> getter(ResourceKey<? extends Registry<? extends E>> $$0) {
/* 62 */     return this.lookupProvider.<E>lookup($$0).map(RegistryInfo::getter);
/*    */   }
/*    */   
/*    */   public static <E, O> RecordCodecBuilder<O, HolderGetter<E>> retrieveGetter(ResourceKey<? extends Registry<? extends E>> $$0) {
/* 66 */     return ExtraCodecs.retrieveContext($$1 -> {
/*    */           if ($$1 instanceof RegistryOps) {
/*    */             RegistryOps<?> $$2 = (RegistryOps)$$1;
/*    */             
/*    */             return $$2.lookupProvider.lookup($$0).map(()).orElseGet(());
/*    */           } 
/*    */           return DataResult.error(());
/* 73 */         }).forGetter($$0 -> null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <E, O> RecordCodecBuilder<O, Holder.Reference<E>> retrieveElement(ResourceKey<E> $$0) {
/* 81 */     ResourceKey<? extends Registry<E>> $$1 = (ResourceKey)ResourceKey.createRegistryKey($$0.registry());
/* 82 */     return ExtraCodecs.retrieveContext($$2 -> {
/*    */           if ($$2 instanceof RegistryOps) {
/*    */             RegistryOps<?> $$3 = (RegistryOps)$$2;
/*    */             
/*    */             return $$3.lookupProvider.lookup($$0).flatMap(()).map(DataResult::success).orElseGet(());
/*    */           } 
/*    */           
/*    */           return DataResult.error(());
/* 90 */         }).forGetter($$0 -> null);
/*    */   }
/*    */   
/*    */   public static interface RegistryInfoLookup {
/*    */     <T> Optional<RegistryOps.RegistryInfo<T>> lookup(ResourceKey<? extends Registry<? extends T>> param1ResourceKey);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\RegistryOps.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */