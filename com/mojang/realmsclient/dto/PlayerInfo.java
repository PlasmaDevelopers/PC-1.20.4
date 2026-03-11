/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerInfo
/*    */   extends ValueObject
/*    */   implements ReflectionBasedSerialization
/*    */ {
/*    */   @SerializedName("name")
/*    */   private String name;
/*    */   @SerializedName("uuid")
/*    */   private UUID uuid;
/*    */   @SerializedName("operator")
/*    */   private boolean operator;
/*    */   @SerializedName("accepted")
/*    */   private boolean accepted;
/*    */   @SerializedName("online")
/*    */   private boolean online;
/*    */   
/*    */   public String getName() {
/* 24 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String $$0) {
/* 28 */     this.name = $$0;
/*    */   }
/*    */   
/*    */   public UUID getUuid() {
/* 32 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public void setUuid(UUID $$0) {
/* 36 */     this.uuid = $$0;
/*    */   }
/*    */   
/*    */   public boolean isOperator() {
/* 40 */     return this.operator;
/*    */   }
/*    */   
/*    */   public void setOperator(boolean $$0) {
/* 44 */     this.operator = $$0;
/*    */   }
/*    */   
/*    */   public boolean getAccepted() {
/* 48 */     return this.accepted;
/*    */   }
/*    */   
/*    */   public void setAccepted(boolean $$0) {
/* 52 */     this.accepted = $$0;
/*    */   }
/*    */   
/*    */   public boolean getOnline() {
/* 56 */     return this.online;
/*    */   }
/*    */   
/*    */   public void setOnline(boolean $$0) {
/* 60 */     this.online = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\PlayerInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */