/*    */ package net.minecraft.server.rcon;
/*    */ 
/*    */ import java.nio.charset.StandardCharsets;
/*    */ 
/*    */ public class PktUtils {
/*    */   public static final int MAX_PACKET_SIZE = 1460;
/*  7 */   public static final char[] HEX_CHAR = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*    */ 
/*    */ 
/*    */   
/*    */   public static String stringFromByteArray(byte[] $$0, int $$1, int $$2) {
/* 12 */     int $$3 = $$2 - 1;
/* 13 */     int $$4 = ($$1 > $$3) ? $$3 : $$1;
/* 14 */     while (0 != $$0[$$4] && $$4 < $$3) {
/* 15 */       $$4++;
/*    */     }
/*    */     
/* 18 */     return new String($$0, $$1, $$4 - $$1, StandardCharsets.UTF_8);
/*    */   }
/*    */   
/*    */   public static int intFromByteArray(byte[] $$0, int $$1) {
/* 22 */     return intFromByteArray($$0, $$1, $$0.length);
/*    */   }
/*    */   
/*    */   public static int intFromByteArray(byte[] $$0, int $$1, int $$2) {
/* 26 */     if (0 > $$2 - $$1 - 4)
/*    */     {
/*    */       
/* 29 */       return 0;
/*    */     }
/* 31 */     return $$0[$$1 + 3] << 24 | ($$0[$$1 + 2] & 0xFF) << 16 | ($$0[$$1 + 1] & 0xFF) << 8 | $$0[$$1] & 0xFF;
/*    */   }
/*    */   
/*    */   public static int intFromNetworkByteArray(byte[] $$0, int $$1, int $$2) {
/* 35 */     if (0 > $$2 - $$1 - 4)
/*    */     {
/*    */       
/* 38 */       return 0;
/*    */     }
/* 40 */     return $$0[$$1] << 24 | ($$0[$$1 + 1] & 0xFF) << 16 | ($$0[$$1 + 2] & 0xFF) << 8 | $$0[$$1 + 3] & 0xFF;
/*    */   }
/*    */   
/*    */   public static String toHexString(byte $$0) {
/* 44 */     return "" + HEX_CHAR[($$0 & 0xF0) >>> 4] + HEX_CHAR[($$0 & 0xF0) >>> 4];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\rcon\PktUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */