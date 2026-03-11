/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ 
/*    */ public class RealmsDescriptionDto
/*    */   extends ValueObject implements ReflectionBasedSerialization {
/*    */   @SerializedName("name")
/*    */   public String name;
/*    */   @SerializedName("description")
/*    */   public String description;
/*    */   
/*    */   public RealmsDescriptionDto(String $$0, String $$1) {
/* 13 */     this.name = $$0;
/* 14 */     this.description = $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsDescriptionDto.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */