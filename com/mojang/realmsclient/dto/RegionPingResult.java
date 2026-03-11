/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class RegionPingResult
/*    */   extends ValueObject
/*    */   implements ReflectionBasedSerialization {
/*    */   @SerializedName("regionName")
/*    */   private final String regionName;
/*    */   @SerializedName("ping")
/*    */   private final int ping;
/*    */   
/*    */   public RegionPingResult(String $$0, int $$1) {
/* 15 */     this.regionName = $$0;
/* 16 */     this.ping = $$1;
/*    */   }
/*    */   
/*    */   public int ping() {
/* 20 */     return this.ping;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 25 */     return String.format(Locale.ROOT, "%s --> %.2f ms", new Object[] { this.regionName, Float.valueOf(this.ping) });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\RegionPingResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */