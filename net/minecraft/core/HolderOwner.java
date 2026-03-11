/*   */ package net.minecraft.core;
/*   */ 
/*   */ public interface HolderOwner<T> {
/*   */   default boolean canSerializeIn(HolderOwner<T> $$0) {
/* 5 */     return ($$0 == this);
/*   */   }
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderOwner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */