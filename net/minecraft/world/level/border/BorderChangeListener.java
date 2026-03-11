/*    */ package net.minecraft.world.level.border;
/*    */ 
/*    */ public interface BorderChangeListener {
/*    */   void onBorderSizeSet(WorldBorder paramWorldBorder, double paramDouble);
/*    */   
/*    */   void onBorderSizeLerping(WorldBorder paramWorldBorder, double paramDouble1, double paramDouble2, long paramLong);
/*    */   
/*    */   void onBorderCenterSet(WorldBorder paramWorldBorder, double paramDouble1, double paramDouble2);
/*    */   
/*    */   void onBorderSetWarningTime(WorldBorder paramWorldBorder, int paramInt);
/*    */   
/*    */   void onBorderSetWarningBlocks(WorldBorder paramWorldBorder, int paramInt);
/*    */   
/*    */   void onBorderSetDamagePerBlock(WorldBorder paramWorldBorder, double paramDouble);
/*    */   
/*    */   void onBorderSetDamageSafeZOne(WorldBorder paramWorldBorder, double paramDouble);
/*    */   
/*    */   public static class DelegateBorderChangeListener implements BorderChangeListener {
/*    */     private final WorldBorder worldBorder;
/*    */     
/*    */     public DelegateBorderChangeListener(WorldBorder $$0) {
/* 22 */       this.worldBorder = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public void onBorderSizeSet(WorldBorder $$0, double $$1) {
/* 27 */       this.worldBorder.setSize($$1);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onBorderSizeLerping(WorldBorder $$0, double $$1, double $$2, long $$3) {
/* 32 */       this.worldBorder.lerpSizeBetween($$1, $$2, $$3);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onBorderCenterSet(WorldBorder $$0, double $$1, double $$2) {
/* 37 */       this.worldBorder.setCenter($$1, $$2);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onBorderSetWarningTime(WorldBorder $$0, int $$1) {
/* 42 */       this.worldBorder.setWarningTime($$1);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onBorderSetWarningBlocks(WorldBorder $$0, int $$1) {
/* 47 */       this.worldBorder.setWarningBlocks($$1);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onBorderSetDamagePerBlock(WorldBorder $$0, double $$1) {
/* 52 */       this.worldBorder.setDamagePerBlock($$1);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onBorderSetDamageSafeZOne(WorldBorder $$0, double $$1) {
/* 57 */       this.worldBorder.setDamageSafeZone($$1);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\border\BorderChangeListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */