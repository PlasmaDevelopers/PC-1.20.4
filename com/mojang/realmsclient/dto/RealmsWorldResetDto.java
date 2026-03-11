/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RealmsWorldResetDto
/*    */   extends ValueObject
/*    */   implements ReflectionBasedSerialization
/*    */ {
/*    */   @SerializedName("seed")
/*    */   private final String seed;
/*    */   @SerializedName("worldTemplateId")
/*    */   private final long worldTemplateId;
/*    */   @SerializedName("levelType")
/*    */   private final int levelType;
/*    */   @SerializedName("generateStructures")
/*    */   private final boolean generateStructures;
/*    */   @SerializedName("experiments")
/*    */   private final Set<String> experiments;
/*    */   
/*    */   public RealmsWorldResetDto(String $$0, long $$1, int $$2, boolean $$3, Set<String> $$4) {
/* 24 */     this.seed = $$0;
/* 25 */     this.worldTemplateId = $$1;
/* 26 */     this.levelType = $$2;
/* 27 */     this.generateStructures = $$3;
/* 28 */     this.experiments = $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RealmsWorldResetDto.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */