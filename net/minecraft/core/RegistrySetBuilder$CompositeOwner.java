/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CompositeOwner
/*    */   implements HolderOwner<Object>
/*    */ {
/* 69 */   private final Set<HolderOwner<?>> owners = Sets.newIdentityHashSet();
/*    */ 
/*    */   
/*    */   public boolean canSerializeIn(HolderOwner<Object> $$0) {
/* 73 */     return this.owners.contains($$0);
/*    */   }
/*    */   
/*    */   public void add(HolderOwner<?> $$0) {
/* 77 */     this.owners.add($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> HolderOwner<T> cast() {
/* 82 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySetBuilder$CompositeOwner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */