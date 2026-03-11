/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ 
/*    */ public final class AdvancementHolder extends Record {
/*    */   private final ResourceLocation id;
/*    */   
/*  7 */   public AdvancementHolder(ResourceLocation $$0, Advancement $$1) { this.id = $$0; this.value = $$1; } private final Advancement value; public ResourceLocation id() { return this.id; } public Advancement value() { return this.value; }
/*    */    public void write(FriendlyByteBuf $$0) {
/*  9 */     $$0.writeResourceLocation(this.id);
/* 10 */     this.value.write($$0);
/*    */   }
/*    */   
/*    */   public static AdvancementHolder read(FriendlyByteBuf $$0) {
/* 14 */     return new AdvancementHolder($$0.readResourceLocation(), Advancement.read($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 19 */     if (this == $$0) {
/* 20 */       return true;
/*    */     }
/* 22 */     if ($$0 instanceof AdvancementHolder) { AdvancementHolder $$1 = (AdvancementHolder)$$0; if (this.id.equals($$1.id)); }  return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 27 */     return this.id.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return this.id.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\AdvancementHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */