/*    */ package net.minecraft.world.entity.ai.attributes;
/*    */ 
/*    */ public class Attribute {
/*    */   public static final int MAX_NAME_LENGTH = 64;
/*    */   private final double defaultValue;
/*    */   private boolean syncable;
/*    */   private final String descriptionId;
/*    */   
/*    */   protected Attribute(String $$0, double $$1) {
/* 10 */     this.defaultValue = $$1;
/* 11 */     this.descriptionId = $$0;
/*    */   }
/*    */   
/*    */   public double getDefaultValue() {
/* 15 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isClientSyncable() {
/* 20 */     return this.syncable;
/*    */   }
/*    */   
/*    */   public Attribute setSyncable(boolean $$0) {
/* 24 */     this.syncable = $$0;
/* 25 */     return this;
/*    */   }
/*    */   
/*    */   public double sanitizeValue(double $$0) {
/* 29 */     return $$0;
/*    */   }
/*    */   
/*    */   public String getDescriptionId() {
/* 33 */     return this.descriptionId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\attributes\Attribute.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */