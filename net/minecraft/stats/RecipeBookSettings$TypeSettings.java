/*    */ package net.minecraft.stats;
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
/*    */ final class TypeSettings
/*    */ {
/*    */   boolean open;
/*    */   boolean filtering;
/*    */   
/*    */   public TypeSettings(boolean $$0, boolean $$1) {
/* 26 */     this.open = $$0;
/* 27 */     this.filtering = $$1;
/*    */   }
/*    */   
/*    */   public TypeSettings copy() {
/* 31 */     return new TypeSettings(this.open, this.filtering);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 36 */     if (this == $$0) {
/* 37 */       return true;
/*    */     }
/*    */     
/* 40 */     if ($$0 instanceof TypeSettings) { TypeSettings $$1 = (TypeSettings)$$0;
/* 41 */       return (this.open == $$1.open && this.filtering == $$1.filtering); }
/*    */     
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 48 */     int $$0 = this.open ? 1 : 0;
/* 49 */     $$0 = 31 * $$0 + (this.filtering ? 1 : 0);
/* 50 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     return "[open=" + this.open + ", filtering=" + this.filtering + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\stats\RecipeBookSettings$TypeSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */