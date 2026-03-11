/*    */ package net.minecraft.resources;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
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
/*    */ class null
/*    */   implements RegistryOps.RegistryInfoLookup
/*    */ {
/* 27 */   private final Map<ResourceKey<? extends Registry<?>>, Optional<? extends RegistryOps.RegistryInfo<?>>> lookups = new HashMap<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> Optional<RegistryOps.RegistryInfo<T>> lookup(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 32 */     Objects.requireNonNull(original); return (Optional<RegistryOps.RegistryInfo<T>>)this.lookups.computeIfAbsent($$0, original::lookup);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\RegistryOps$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */