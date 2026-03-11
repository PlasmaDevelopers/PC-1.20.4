/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Lifecycle;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.TagKey;
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
/*    */   implements HolderLookup.RegistryLookup<T>
/*    */ {
/*    */   public ResourceKey<? extends Registry<? extends T>> key() {
/* 67 */     return MappedRegistry.this.key;
/*    */   }
/*    */ 
/*    */   
/*    */   public Lifecycle registryLifecycle() {
/* 72 */     return MappedRegistry.this.registryLifecycle();
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/* 77 */     return MappedRegistry.this.getHolder($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<Holder.Reference<T>> listElements() {
/* 82 */     return MappedRegistry.this.holders();
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/* 87 */     return MappedRegistry.this.getTag($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<HolderSet.Named<T>> listTags() {
/* 92 */     return MappedRegistry.this.getTags().map(Pair::getSecond);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\MappedRegistry$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */