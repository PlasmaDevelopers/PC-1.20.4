/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import net.minecraft.world.phys.AABB;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class EntityDimensions {
/*    */   public final float width;
/*    */   public final float height;
/*    */   public final boolean fixed;
/*    */   
/*    */   public EntityDimensions(float $$0, float $$1, boolean $$2) {
/* 12 */     this.width = $$0;
/* 13 */     this.height = $$1;
/* 14 */     this.fixed = $$2;
/*    */   }
/*    */   
/*    */   public AABB makeBoundingBox(Vec3 $$0) {
/* 18 */     return makeBoundingBox($$0.x, $$0.y, $$0.z);
/*    */   }
/*    */   
/*    */   public AABB makeBoundingBox(double $$0, double $$1, double $$2) {
/* 22 */     float $$3 = this.width / 2.0F;
/* 23 */     float $$4 = this.height;
/* 24 */     return new AABB($$0 - $$3, $$1, $$2 - $$3, $$0 + $$3, $$1 + $$4, $$2 + $$3);
/*    */   }
/*    */   
/*    */   public EntityDimensions scale(float $$0) {
/* 28 */     return scale($$0, $$0);
/*    */   }
/*    */   
/*    */   public EntityDimensions scale(float $$0, float $$1) {
/* 32 */     if (this.fixed || ($$0 == 1.0F && $$1 == 1.0F)) {
/* 33 */       return this;
/*    */     }
/* 35 */     return scalable(this.width * $$0, this.height * $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public static EntityDimensions scalable(float $$0, float $$1) {
/* 40 */     return new EntityDimensions($$0, $$1, false);
/*    */   }
/*    */   
/*    */   public static EntityDimensions fixed(float $$0, float $$1) {
/* 44 */     return new EntityDimensions($$0, $$1, true);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return "EntityDimensions w=" + this.width + ", h=" + this.height + ", fixed=" + this.fixed;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\EntityDimensions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */