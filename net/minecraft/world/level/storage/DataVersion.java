/*    */ package net.minecraft.world.level.storage;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DataVersion
/*    */ {
/*    */   private final int version;
/*    */   private final String series;
/*  9 */   public static String MAIN_SERIES = "main";
/*    */   
/*    */   public DataVersion(int $$0) {
/* 12 */     this($$0, MAIN_SERIES);
/*    */   }
/*    */   
/*    */   public DataVersion(int $$0, String $$1) {
/* 16 */     this.version = $$0;
/* 17 */     this.series = $$1;
/*    */   }
/*    */   
/*    */   public boolean isSideSeries() {
/* 21 */     return !this.series.equals(MAIN_SERIES);
/*    */   }
/*    */   
/*    */   public String getSeries() {
/* 25 */     return this.series;
/*    */   }
/*    */   
/*    */   public int getVersion() {
/* 29 */     return this.version;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isCompatible(DataVersion $$0) {
/* 36 */     return getSeries().equals($$0.getSeries());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\DataVersion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */