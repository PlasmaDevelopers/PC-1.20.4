/*    */ package net.minecraft.core;
/*    */ 
/*    */ import net.minecraft.nbt.FloatTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class Rotations {
/*    */   protected final float x;
/*    */   protected final float y;
/*    */   protected final float z;
/*    */   
/*    */   public Rotations(float $$0, float $$1, float $$2) {
/* 13 */     this.x = (Float.isInfinite($$0) || Float.isNaN($$0)) ? 0.0F : ($$0 % 360.0F);
/* 14 */     this.y = (Float.isInfinite($$1) || Float.isNaN($$1)) ? 0.0F : ($$1 % 360.0F);
/* 15 */     this.z = (Float.isInfinite($$2) || Float.isNaN($$2)) ? 0.0F : ($$2 % 360.0F);
/*    */   }
/*    */   
/*    */   public Rotations(ListTag $$0) {
/* 19 */     this($$0.getFloat(0), $$0.getFloat(1), $$0.getFloat(2));
/*    */   }
/*    */   
/*    */   public ListTag save() {
/* 23 */     ListTag $$0 = new ListTag();
/* 24 */     $$0.add(FloatTag.valueOf(this.x));
/* 25 */     $$0.add(FloatTag.valueOf(this.y));
/* 26 */     $$0.add(FloatTag.valueOf(this.z));
/* 27 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 32 */     if (!($$0 instanceof Rotations)) {
/* 33 */       return false;
/*    */     }
/* 35 */     Rotations $$1 = (Rotations)$$0;
/* 36 */     return (this.x == $$1.x && this.y == $$1.y && this.z == $$1.z);
/*    */   }
/*    */   
/*    */   public float getX() {
/* 40 */     return this.x;
/*    */   }
/*    */   
/*    */   public float getY() {
/* 44 */     return this.y;
/*    */   }
/*    */   
/*    */   public float getZ() {
/* 48 */     return this.z;
/*    */   }
/*    */   
/*    */   public float getWrappedX() {
/* 52 */     return Mth.wrapDegrees(this.x);
/*    */   }
/*    */   
/*    */   public float getWrappedY() {
/* 56 */     return Mth.wrapDegrees(this.y);
/*    */   }
/*    */   
/*    */   public float getWrappedZ() {
/* 60 */     return Mth.wrapDegrees(this.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Rotations.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */