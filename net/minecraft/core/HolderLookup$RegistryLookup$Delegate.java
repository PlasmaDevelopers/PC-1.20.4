/*    */ package net.minecraft.core;
/*    */ 
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
/*    */ public abstract class Delegate<T>
/*    */   implements HolderLookup.RegistryLookup<T>
/*    */ {
/*    */   protected abstract HolderLookup.RegistryLookup<T> parent();
/*    */   
/*    */   public ResourceKey<? extends Registry<? extends T>> key() {
/* 50 */     return parent().key();
/*    */   }
/*    */ 
/*    */   
/*    */   public Lifecycle registryLifecycle() {
/* 55 */     return parent().registryLifecycle();
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/* 60 */     return parent().get($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<Holder.Reference<T>> listElements() {
/* 65 */     return parent().listElements();
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/* 70 */     return parent().get($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<HolderSet.Named<T>> listTags() {
/* 75 */     return parent().listTags();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderLookup$RegistryLookup$Delegate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */