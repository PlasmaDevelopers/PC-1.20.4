/*    */ package net.minecraft.core;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Cursor3D
/*    */ {
/*    */   public static final int TYPE_INSIDE = 0;
/*    */   public static final int TYPE_FACE = 1;
/*    */   public static final int TYPE_EDGE = 2;
/*    */   public static final int TYPE_CORNER = 3;
/*    */   private final int originX;
/*    */   private final int originY;
/*    */   private final int originZ;
/*    */   private final int width;
/*    */   private final int height;
/*    */   private final int depth;
/*    */   private final int end;
/*    */   private int index;
/*    */   private int x;
/*    */   private int y;
/*    */   private int z;
/*    */   
/*    */   public Cursor3D(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 24 */     this.originX = $$0;
/* 25 */     this.originY = $$1;
/* 26 */     this.originZ = $$2;
/*    */     
/* 28 */     this.width = $$3 - $$0 + 1;
/* 29 */     this.height = $$4 - $$1 + 1;
/* 30 */     this.depth = $$5 - $$2 + 1;
/* 31 */     this.end = this.width * this.height * this.depth;
/*    */   }
/*    */   
/*    */   public boolean advance() {
/* 35 */     if (this.index == this.end) {
/* 36 */       return false;
/*    */     }
/*    */     
/* 39 */     this.x = this.index % this.width;
/* 40 */     int $$0 = this.index / this.width;
/* 41 */     this.y = $$0 % this.height;
/* 42 */     this.z = $$0 / this.height;
/*    */     
/* 44 */     this.index++;
/* 45 */     return true;
/*    */   }
/*    */   
/*    */   public int nextX() {
/* 49 */     return this.originX + this.x;
/*    */   }
/*    */   
/*    */   public int nextY() {
/* 53 */     return this.originY + this.y;
/*    */   }
/*    */   
/*    */   public int nextZ() {
/* 57 */     return this.originZ + this.z;
/*    */   }
/*    */   
/*    */   public int getNextType() {
/* 61 */     int $$0 = 0;
/* 62 */     if (this.x == 0 || this.x == this.width - 1) {
/* 63 */       $$0++;
/*    */     }
/* 65 */     if (this.y == 0 || this.y == this.height - 1) {
/* 66 */       $$0++;
/*    */     }
/* 68 */     if (this.z == 0 || this.z == this.depth - 1) {
/* 69 */       $$0++;
/*    */     }
/* 71 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Cursor3D.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */