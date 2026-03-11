/*    */ package net.minecraft.world.phys;
/*    */ 
/*    */ public abstract class HitResult {
/*    */   protected final Vec3 location;
/*    */   
/*    */   public enum Type {
/*  7 */     MISS, BLOCK, ENTITY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected HitResult(Vec3 $$0) {
/* 13 */     this.location = $$0;
/*    */   }
/*    */   
/*    */   public double distanceTo(Entity $$0) {
/* 17 */     double $$1 = this.location.x - $$0.getX();
/* 18 */     double $$2 = this.location.y - $$0.getY();
/* 19 */     double $$3 = this.location.z - $$0.getZ();
/* 20 */     return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
/*    */   }
/*    */   
/*    */   public abstract Type getType();
/*    */   
/*    */   public Vec3 getLocation() {
/* 26 */     return this.location;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\HitResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */