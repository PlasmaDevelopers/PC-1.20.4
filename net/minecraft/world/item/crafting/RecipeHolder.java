/*    */ package net.minecraft.world.item.crafting;
/*    */ 
/*    */ public final class RecipeHolder<T extends Recipe<?>> extends Record {
/*    */   private final ResourceLocation id;
/*    */   
/*  6 */   public RecipeHolder(ResourceLocation $$0, T $$1) { this.id = $$0; this.value = $$1; } private final T value; public ResourceLocation id() { return this.id; } public T value() { return this.value; }
/*    */   
/*    */   public boolean equals(Object $$0) {
/*  9 */     if (this == $$0) {
/* 10 */       return true;
/*    */     }
/* 12 */     if ($$0 instanceof RecipeHolder) { RecipeHolder<?> $$1 = (RecipeHolder)$$0; if (this.id.equals($$1.id)); }  return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 17 */     return this.id.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return this.id.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\RecipeHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */