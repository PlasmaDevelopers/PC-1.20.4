/*    */ package com.mojang.realmsclient.client;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.realmsclient.dto.RegionPingResult;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Socket;
/*    */ import java.net.SocketAddress;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import net.minecraft.Util;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ 
/*    */ public class Ping
/*    */ {
/*    */   public static List<RegionPingResult> ping(Region... $$0) {
/* 17 */     for (Region $$1 : $$0) {
/* 18 */       ping($$1.endpoint);
/*    */     }
/*    */ 
/*    */     
/* 22 */     List<RegionPingResult> $$2 = Lists.newArrayList();
/* 23 */     for (Region $$3 : $$0) {
/* 24 */       $$2.add(new RegionPingResult($$3.name, ping($$3.endpoint)));
/*    */     }
/*    */ 
/*    */     
/* 28 */     $$2.sort(Comparator.comparingInt(RegionPingResult::ping));
/* 29 */     return $$2;
/*    */   }
/*    */   
/*    */   private static int ping(String $$0) {
/* 33 */     int $$1 = 700;
/* 34 */     long $$2 = 0L;
/* 35 */     Socket $$3 = null;
/* 36 */     for (int $$4 = 0; $$4 < 5; $$4++) {
/*    */       
/* 38 */       try { SocketAddress $$5 = new InetSocketAddress($$0, 80);
/* 39 */         $$3 = new Socket();
/* 40 */         long $$6 = now();
/* 41 */         $$3.connect($$5, 700);
/* 42 */         $$2 += now() - $$6;
/*    */ 
/*    */ 
/*    */         
/* 46 */         IOUtils.closeQuietly($$3); } catch (Exception $$7) { $$2 += 700L; } finally { IOUtils.closeQuietly($$3); }
/*    */     
/*    */     } 
/* 49 */     return (int)($$2 / 5.0D);
/*    */   }
/*    */   
/*    */   private static long now() {
/* 53 */     return Util.getMillis();
/*    */   }
/*    */   
/*    */   public static List<RegionPingResult> pingAllRegions() {
/* 57 */     return ping(Region.values());
/*    */   }
/*    */   
/*    */   enum Region {
/* 61 */     US_EAST_1("us-east-1", "ec2.us-east-1.amazonaws.com"),
/* 62 */     US_WEST_2("us-west-2", "ec2.us-west-2.amazonaws.com"),
/* 63 */     US_WEST_1("us-west-1", "ec2.us-west-1.amazonaws.com"),
/* 64 */     EU_WEST_1("eu-west-1", "ec2.eu-west-1.amazonaws.com"),
/* 65 */     AP_SOUTHEAST_1("ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com"),
/* 66 */     AP_SOUTHEAST_2("ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com"),
/* 67 */     AP_NORTHEAST_1("ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com"),
/* 68 */     SA_EAST_1("sa-east-1", "ec2.sa-east-1.amazonaws.com"); final String name; final String endpoint;
/*    */     
/*    */     Region(String $$0, String $$1) {
/* 71 */       this.name = $$0;
/* 72 */       this.endpoint = $$1;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\client\Ping.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */