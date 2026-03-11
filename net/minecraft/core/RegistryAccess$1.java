/*     */ package net.minecraft.core;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements RegistryAccess.Frozen
/*     */ {
/*     */   public <T> Optional<Registry<T>> registry(ResourceKey<? extends Registry<? extends T>> $$0) {
/*  89 */     Registry<Registry<T>> $$1 = registries;
/*  90 */     return (Optional)$$1.getOptional($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<RegistryAccess.RegistryEntry<?>> registries() {
/*  95 */     return registries.entrySet().stream().map(RegistryAccess.RegistryEntry::fromMapEntry);
/*     */   }
/*     */ 
/*     */   
/*     */   public RegistryAccess.Frozen freeze() {
/* 100 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistryAccess$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */