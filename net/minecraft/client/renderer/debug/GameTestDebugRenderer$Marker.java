/*    */ package net.minecraft.client.renderer.debug;
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
/*    */ class Marker
/*    */ {
/*    */   public int color;
/*    */   public String text;
/*    */   public long removeAtTime;
/*    */   
/*    */   public Marker(int $$0, String $$1, long $$2) {
/* 24 */     this.color = $$0;
/* 25 */     this.text = $$1;
/* 26 */     this.removeAtTime = $$2;
/*    */   }
/*    */   
/*    */   public float getR() {
/* 30 */     return (this.color >> 16 & 0xFF) / 255.0F;
/*    */   }
/*    */   
/*    */   public float getG() {
/* 34 */     return (this.color >> 8 & 0xFF) / 255.0F;
/*    */   }
/*    */   
/*    */   public float getB() {
/* 38 */     return (this.color & 0xFF) / 255.0F;
/*    */   }
/*    */   
/*    */   public float getA() {
/* 42 */     return (this.color >> 24 & 0xFF) / 255.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\GameTestDebugRenderer$Marker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */