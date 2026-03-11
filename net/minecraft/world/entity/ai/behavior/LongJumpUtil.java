/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityDimensions;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LongJumpUtil
/*    */ {
/*    */   public static Optional<Vec3> calculateJumpVectorForAngle(Mob $$0, Vec3 $$1, float $$2, int $$3, boolean $$4) {
/* 21 */     Vec3 $$5 = $$0.position();
/*    */ 
/*    */     
/* 24 */     Vec3 $$6 = (new Vec3($$1.x - $$5.x, 0.0D, $$1.z - $$5.z)).normalize().scale(0.5D);
/* 25 */     Vec3 $$7 = $$1.subtract($$6);
/*    */     
/* 27 */     Vec3 $$8 = $$7.subtract($$5);
/* 28 */     float $$9 = $$3 * 3.1415927F / 180.0F;
/* 29 */     double $$10 = Math.atan2($$8.z, $$8.x);
/* 30 */     double $$11 = $$8.subtract(0.0D, $$8.y, 0.0D).lengthSqr();
/* 31 */     double $$12 = Math.sqrt($$11);
/* 32 */     double $$13 = $$8.y;
/* 33 */     double $$14 = 0.08D;
/*    */     
/* 35 */     double $$15 = Math.sin((2.0F * $$9));
/* 36 */     double $$16 = Math.pow(Math.cos($$9), 2.0D);
/* 37 */     double $$17 = Math.sin($$9);
/* 38 */     double $$18 = Math.cos($$9);
/* 39 */     double $$19 = Math.sin($$10);
/* 40 */     double $$20 = Math.cos($$10);
/*    */     
/* 42 */     double $$21 = $$11 * 0.08D / ($$12 * $$15 - 2.0D * $$13 * $$16);
/* 43 */     if ($$21 < 0.0D) {
/* 44 */       return Optional.empty();
/*    */     }
/*    */     
/* 47 */     double $$22 = Math.sqrt($$21);
/* 48 */     if ($$22 > $$2) {
/* 49 */       return Optional.empty();
/*    */     }
/*    */     
/* 52 */     double $$23 = $$22 * $$18;
/* 53 */     double $$24 = $$22 * $$17;
/*    */     
/* 55 */     if ($$4) {
/*    */       
/* 57 */       int $$25 = Mth.ceil($$12 / $$23) * 2;
/* 58 */       double $$26 = 0.0D;
/* 59 */       Vec3 $$27 = null;
/*    */       
/* 61 */       EntityDimensions $$28 = $$0.getDimensions(Pose.LONG_JUMPING);
/* 62 */       for (int $$29 = 0; $$29 < $$25 - 1; $$29++) {
/* 63 */         $$26 += $$12 / $$25;
/* 64 */         double $$30 = $$17 / $$18 * $$26 - Math.pow($$26, 2.0D) * 0.08D / 2.0D * $$21 * Math.pow($$18, 2.0D);
/* 65 */         double $$31 = $$26 * $$20;
/* 66 */         double $$32 = $$26 * $$19;
/*    */         
/* 68 */         Vec3 $$33 = new Vec3($$5.x + $$31, $$5.y + $$30, $$5.z + $$32);
/* 69 */         if ($$27 != null && !isClearTransition($$0, $$28, $$27, $$33)) {
/* 70 */           return Optional.empty();
/*    */         }
/*    */         
/* 73 */         $$27 = $$33;
/*    */       } 
/*    */     } 
/*    */     
/* 77 */     return Optional.of((new Vec3($$23 * $$20, $$24, $$23 * $$19)).scale(0.949999988079071D));
/*    */   }
/*    */   
/*    */   private static boolean isClearTransition(Mob $$0, EntityDimensions $$1, Vec3 $$2, Vec3 $$3) {
/* 81 */     Vec3 $$4 = $$3.subtract($$2);
/*    */     
/* 83 */     double $$5 = Math.min($$1.width, $$1.height);
/* 84 */     int $$6 = Mth.ceil($$4.length() / $$5);
/*    */     
/* 86 */     Vec3 $$7 = $$4.normalize();
/* 87 */     Vec3 $$8 = $$2;
/* 88 */     for (int $$9 = 0; $$9 < $$6; $$9++) {
/* 89 */       $$8 = ($$9 == $$6 - 1) ? $$3 : $$8.add($$7.scale($$5 * 0.8999999761581421D));
/* 90 */       if (!$$0.level().noCollision((Entity)$$0, $$1.makeBoundingBox($$8))) {
/* 91 */         return false;
/*    */       }
/*    */     } 
/* 94 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\LongJumpUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */