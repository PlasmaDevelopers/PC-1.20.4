/*    */ package com.mojang.blaze3d.audio;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import javax.sound.sampled.AudioFormat;
/*    */ import org.lwjgl.openal.AL10;
/*    */ import org.lwjgl.openal.ALC10;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class OpenAlUtil
/*    */ {
/* 11 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private static String alErrorToString(int $$0) {
/* 14 */     switch ($$0) {
/*    */       case 40961:
/* 16 */         return "Invalid name parameter.";
/*    */       case 40962:
/* 18 */         return "Invalid enumerated parameter value.";
/*    */       case 40963:
/* 20 */         return "Invalid parameter parameter value.";
/*    */       case 40964:
/* 22 */         return "Invalid operation.";
/*    */       case 40965:
/* 24 */         return "Unable to allocate memory.";
/*    */     } 
/* 26 */     return "An unrecognized error occurred.";
/*    */   }
/*    */ 
/*    */   
/*    */   static boolean checkALError(String $$0) {
/* 31 */     int $$1 = AL10.alGetError();
/* 32 */     if ($$1 != 0) {
/* 33 */       LOGGER.error("{}: {}", $$0, alErrorToString($$1));
/* 34 */       return true;
/*    */     } 
/* 36 */     return false;
/*    */   }
/*    */   
/*    */   private static String alcErrorToString(int $$0) {
/* 40 */     switch ($$0) {
/*    */       case 40961:
/* 42 */         return "Invalid device.";
/*    */       case 40962:
/* 44 */         return "Invalid context.";
/*    */       case 40964:
/* 46 */         return "Invalid value.";
/*    */       case 40963:
/* 48 */         return "Illegal enum.";
/*    */       case 40965:
/* 50 */         return "Unable to allocate memory.";
/*    */     } 
/* 52 */     return "An unrecognized error occurred.";
/*    */   }
/*    */ 
/*    */   
/*    */   static boolean checkALCError(long $$0, String $$1) {
/* 57 */     int $$2 = ALC10.alcGetError($$0);
/* 58 */     if ($$2 != 0) {
/* 59 */       LOGGER.error("{} ({}): {}", new Object[] { $$1, Long.valueOf($$0), alcErrorToString($$2) });
/* 60 */       return true;
/*    */     } 
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   static int audioFormatToOpenAl(AudioFormat $$0) {
/* 66 */     AudioFormat.Encoding $$1 = $$0.getEncoding();
/* 67 */     int $$2 = $$0.getChannels();
/* 68 */     int $$3 = $$0.getSampleSizeInBits();
/*    */     
/* 70 */     if ($$1.equals(AudioFormat.Encoding.PCM_UNSIGNED) || $$1.equals(AudioFormat.Encoding.PCM_SIGNED)) {
/* 71 */       if ($$2 == 1) {
/* 72 */         if ($$3 == 8)
/* 73 */           return 4352; 
/* 74 */         if ($$3 == 16) {
/* 75 */           return 4353;
/*    */         }
/* 77 */       } else if ($$2 == 2) {
/* 78 */         if ($$3 == 8)
/* 79 */           return 4354; 
/* 80 */         if ($$3 == 16) {
/* 81 */           return 4355;
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 86 */     throw new IllegalArgumentException("Invalid audio format: " + $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\OpenAlUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */