/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ public class Rect2i {
/*    */   private int xPos;
/*    */   private int yPos;
/*    */   private int width;
/*    */   private int height;
/*    */   
/*    */   public Rect2i(int $$0, int $$1, int $$2, int $$3) {
/* 10 */     this.xPos = $$0;
/* 11 */     this.yPos = $$1;
/* 12 */     this.width = $$2;
/* 13 */     this.height = $$3;
/*    */   }
/*    */   
/*    */   public Rect2i intersect(Rect2i $$0) {
/* 17 */     int $$1 = this.xPos;
/* 18 */     int $$2 = this.yPos;
/* 19 */     int $$3 = this.xPos + this.width;
/* 20 */     int $$4 = this.yPos + this.height;
/*    */     
/* 22 */     int $$5 = $$0.getX();
/* 23 */     int $$6 = $$0.getY();
/* 24 */     int $$7 = $$5 + $$0.getWidth();
/* 25 */     int $$8 = $$6 + $$0.getHeight();
/*    */     
/* 27 */     this.xPos = Math.max($$1, $$5);
/* 28 */     this.yPos = Math.max($$2, $$6);
/* 29 */     this.width = Math.max(0, Math.min($$3, $$7) - this.xPos);
/* 30 */     this.height = Math.max(0, Math.min($$4, $$8) - this.yPos);
/*    */     
/* 32 */     return this;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 36 */     return this.xPos;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 40 */     return this.yPos;
/*    */   }
/*    */   
/*    */   public void setX(int $$0) {
/* 44 */     this.xPos = $$0;
/*    */   }
/*    */   
/*    */   public void setY(int $$0) {
/* 48 */     this.yPos = $$0;
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 52 */     return this.width;
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 56 */     return this.height;
/*    */   }
/*    */   
/*    */   public void setWidth(int $$0) {
/* 60 */     this.width = $$0;
/*    */   }
/*    */   
/*    */   public void setHeight(int $$0) {
/* 64 */     this.height = $$0;
/*    */   }
/*    */   
/*    */   public void setPosition(int $$0, int $$1) {
/* 68 */     this.xPos = $$0;
/* 69 */     this.yPos = $$1;
/*    */   }
/*    */   
/*    */   public boolean contains(int $$0, int $$1) {
/* 73 */     return ($$0 >= this.xPos && $$0 <= this.xPos + this.width && $$1 >= this.yPos && $$1 <= this.yPos + this.height);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\Rect2i.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */