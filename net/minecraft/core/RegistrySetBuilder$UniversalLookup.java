/*     */ package net.minecraft.core;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
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
/*     */ class UniversalLookup
/*     */   extends RegistrySetBuilder.EmptyTagLookup<Object>
/*     */ {
/*  87 */   final Map<ResourceKey<Object>, Holder.Reference<Object>> holders = new HashMap<>();
/*     */   
/*     */   public UniversalLookup(HolderOwner<Object> $$0) {
/*  90 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<Holder.Reference<Object>> get(ResourceKey<Object> $$0) {
/*  95 */     return Optional.of(getOrCreate($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   <T> Holder.Reference<T> getOrCreate(ResourceKey<T> $$0) {
/* 100 */     return (Holder.Reference<T>)this.holders.computeIfAbsent($$0, $$0 -> Holder.Reference.createStandAlone(this.owner, $$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySetBuilder$UniversalLookup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */