/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.DecimalFormatSymbols;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public interface StatFormatter {
/*    */   public static final DecimalFormat DECIMAL_FORMAT;
/*    */   
/* 11 */   static { DECIMAL_FORMAT = (DecimalFormat)Util.make(new DecimalFormat("########0.00"), $$0 -> $$0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
/*    */     
/* 13 */     Objects.requireNonNull(NumberFormat.getIntegerInstance(Locale.US)); } public static final StatFormatter DEFAULT = NumberFormat.getIntegerInstance(Locale.US)::format; public static final StatFormatter DIVIDE_BY_TEN; static {
/* 14 */     DIVIDE_BY_TEN = ($$0 -> DECIMAL_FORMAT.format($$0 * 0.1D));
/* 15 */     DISTANCE = ($$0 -> {
/*    */         double $$1 = $$0 / 100.0D;
/*    */ 
/*    */         
/*    */         double $$2 = $$1 / 1000.0D;
/*    */ 
/*    */         
/*    */         return ($$2 > 0.5D) ? (DECIMAL_FORMAT.format($$2) + " km") : (($$1 > 0.5D) ? (DECIMAL_FORMAT.format($$1) + " m") : ("" + $$0 + " cm"));
/*    */       });
/*    */ 
/*    */     
/* 26 */     TIME = ($$0 -> {
/*    */         double $$1 = $$0 / 20.0D;
/*    */         double $$2 = $$1 / 60.0D;
/*    */         double $$3 = $$2 / 60.0D;
/*    */         double $$4 = $$3 / 24.0D;
/*    */         double $$5 = $$4 / 365.0D;
/*    */         return ($$5 > 0.5D) ? (DECIMAL_FORMAT.format($$5) + " y") : (($$4 > 0.5D) ? (DECIMAL_FORMAT.format($$4) + " d") : (($$3 > 0.5D) ? (DECIMAL_FORMAT.format($$3) + " h") : (($$2 > 0.5D) ? (DECIMAL_FORMAT.format($$2) + " m") : ("" + $$1 + " s"))));
/*    */       });
/*    */   }
/*    */   
/*    */   public static final StatFormatter DISTANCE;
/*    */   public static final StatFormatter TIME;
/*    */   
/*    */   String format(int paramInt);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\stats\StatFormatter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */