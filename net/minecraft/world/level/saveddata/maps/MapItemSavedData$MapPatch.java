/*    */ package net.minecraft.world.level.saveddata.maps;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapPatch
/*    */ {
/*    */   public final int startX;
/*    */   public final int startY;
/*    */   public final int width;
/*    */   public final int height;
/*    */   public final byte[] mapColors;
/*    */   
/*    */   public MapPatch(int $$0, int $$1, int $$2, int $$3, byte[] $$4) {
/* 52 */     this.startX = $$0;
/* 53 */     this.startY = $$1;
/* 54 */     this.width = $$2;
/* 55 */     this.height = $$3;
/* 56 */     this.mapColors = $$4;
/*    */   }
/*    */   
/*    */   public void applyToMap(MapItemSavedData $$0) {
/* 60 */     for (int $$1 = 0; $$1 < this.width; $$1++) {
/* 61 */       for (int $$2 = 0; $$2 < this.height; $$2++)
/* 62 */         $$0.setColor(this.startX + $$1, this.startY + $$2, this.mapColors[$$1 + $$2 * this.width]); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\saveddata\maps\MapItemSavedData$MapPatch.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */