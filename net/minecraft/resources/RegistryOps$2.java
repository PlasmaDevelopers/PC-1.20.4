/*    */ package net.minecraft.resources;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.HolderOwner;
/*    */ import net.minecraft.core.Registry;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements RegistryOps.RegistryInfoLookup
/*    */ {
/*    */   public <E> Optional<RegistryOps.RegistryInfo<E>> lookup(ResourceKey<? extends Registry<? extends E>> $$0) {
/* 43 */     return lookupProvider.lookup($$0).map($$0 -> new RegistryOps.RegistryInfo((HolderOwner<?>)$$0, (HolderGetter<?>)$$0, $$0.registryLifecycle()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\RegistryOps$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */