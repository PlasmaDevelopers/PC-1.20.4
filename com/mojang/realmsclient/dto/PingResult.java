/*    */ package com.mojang.realmsclient.dto;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PingResult
/*    */   extends ValueObject implements ReflectionBasedSerialization {
/*    */   @SerializedName("pingResults")
/* 10 */   public List<RegionPingResult> pingResults = Lists.newArrayList();
/*    */   
/*    */   @SerializedName("worldIds")
/* 13 */   public List<Long> worldIds = Lists.newArrayList();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\dto\PingResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */