/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ public class StringUtil
/*    */ {
/* 12 */   private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
/* 13 */   private static final Pattern LINE_PATTERN = Pattern.compile("\\r\\n|\\v");
/* 14 */   private static final Pattern LINE_END_PATTERN = Pattern.compile("(?:\\r\\n|\\v)$");
/*    */   
/*    */   public static String formatTickDuration(int $$0, float $$1) {
/* 17 */     int $$2 = Mth.floor($$0 / $$1);
/* 18 */     int $$3 = $$2 / 60;
/* 19 */     $$2 %= 60;
/* 20 */     int $$4 = $$3 / 60;
/* 21 */     $$3 %= 60;
/*    */     
/* 23 */     if ($$4 > 0) {
/* 24 */       return String.format(Locale.ROOT, "%02d:%02d:%02d", new Object[] { Integer.valueOf($$4), Integer.valueOf($$3), Integer.valueOf($$2) });
/*    */     }
/* 26 */     return String.format(Locale.ROOT, "%02d:%02d", new Object[] { Integer.valueOf($$3), Integer.valueOf($$2) });
/*    */   }
/*    */   
/*    */   public static String stripColor(String $$0) {
/* 30 */     return STRIP_COLOR_PATTERN.matcher($$0).replaceAll("");
/*    */   }
/*    */   
/*    */   public static boolean isNullOrEmpty(@Nullable String $$0) {
/* 34 */     return StringUtils.isEmpty($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String truncateStringIfNecessary(String $$0, int $$1, boolean $$2) {
/* 43 */     if ($$0.length() <= $$1) {
/* 44 */       return $$0;
/*    */     }
/*    */     
/* 47 */     if ($$2 && $$1 > 3) {
/* 48 */       return $$0.substring(0, $$1 - 3) + "...";
/*    */     }
/* 50 */     return $$0.substring(0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public static int lineCount(String $$0) {
/* 55 */     if ($$0.isEmpty()) {
/* 56 */       return 0;
/*    */     }
/*    */     
/* 59 */     Matcher $$1 = LINE_PATTERN.matcher($$0);
/*    */     
/* 61 */     int $$2 = 1;
/* 62 */     while ($$1.find()) {
/* 63 */       $$2++;
/*    */     }
/* 65 */     return $$2;
/*    */   }
/*    */   
/*    */   public static boolean endsWithNewLine(String $$0) {
/* 69 */     return LINE_END_PATTERN.matcher($$0).find();
/*    */   }
/*    */   
/*    */   public static String trimChatMessage(String $$0) {
/* 73 */     return truncateStringIfNecessary($$0, 256, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\StringUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */