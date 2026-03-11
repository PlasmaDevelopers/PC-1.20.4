/*    */ package net.minecraft.core;
/*    */ 
/*    */ import java.util.Optional;
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
/*    */ abstract class EmptyTagLookup<T>
/*    */   implements HolderGetter<T>
/*    */ {
/*    */   protected final HolderOwner<T> owner;
/*    */   
/*    */   protected EmptyTagLookup(HolderOwner<T> $$0) {
/* 57 */     this.owner = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/* 62 */     return Optional.of(HolderSet.emptyNamed(this.owner, $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySetBuilder$EmptyTagLookup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */