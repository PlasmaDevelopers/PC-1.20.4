/*    */ package net.minecraft.data;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PackGenerator
/*    */ {
/*    */   private final boolean toRun;
/*    */   private final String providerPrefix;
/*    */   private final PackOutput output;
/*    */   
/*    */   PackGenerator(boolean $$1, String $$2, PackOutput $$3) {
/* 79 */     this.toRun = $$1;
/* 80 */     this.providerPrefix = $$2;
/* 81 */     this.output = $$3;
/*    */   }
/*    */   
/*    */   public <T extends DataProvider> T addProvider(DataProvider.Factory<T> $$0) {
/* 85 */     T $$1 = $$0.create(this.output);
/* 86 */     String $$2 = this.providerPrefix + "/" + this.providerPrefix;
/* 87 */     if (!DataGenerator.this.allProviderIds.add($$2)) {
/* 88 */       throw new IllegalStateException("Duplicate provider: " + $$2);
/*    */     }
/* 90 */     if (this.toRun) {
/* 91 */       DataGenerator.this.providersToRun.put($$2, (DataProvider)$$1);
/*    */     }
/* 93 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\DataGenerator$PackGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */