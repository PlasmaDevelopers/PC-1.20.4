/*    */ package net.minecraft.client;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.OptionEnum;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum CloudStatus
/*    */   implements OptionEnum, StringRepresentable {
/*  9 */   OFF(0, "false", "options.off"),
/* 10 */   FAST(1, "fast", "options.clouds.fast"),
/* 11 */   FANCY(2, "true", "options.clouds.fancy"); public static final Codec<CloudStatus> CODEC;
/*    */   
/*    */   static {
/* 14 */     CODEC = (Codec<CloudStatus>)StringRepresentable.fromEnum(CloudStatus::values);
/*    */   }
/*    */   private final int id;
/*    */   private final String legacyName;
/*    */   private final String key;
/*    */   
/*    */   CloudStatus(int $$0, String $$1, String $$2) {
/* 21 */     this.id = $$0;
/* 22 */     this.legacyName = $$1;
/* 23 */     this.key = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 28 */     return this.legacyName;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 33 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 38 */     return this.key;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\CloudStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */