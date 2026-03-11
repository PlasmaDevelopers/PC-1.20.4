/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import org.jetbrains.annotations.VisibleForTesting;
/*    */ 
/*    */ public class VecDeltaCodec
/*    */ {
/*    */   private static final double TRUNCATION_STEPS = 4096.0D;
/*  9 */   private Vec3 base = Vec3.ZERO;
/*    */   
/*    */   @VisibleForTesting
/*    */   static long encode(double $$0) {
/* 13 */     return Math.round($$0 * 4096.0D);
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/*    */   static double decode(long $$0) {
/* 18 */     return $$0 / 4096.0D;
/*    */   }
/*    */   
/*    */   public Vec3 decode(long $$0, long $$1, long $$2) {
/* 22 */     if ($$0 == 0L && $$1 == 0L && $$2 == 0L) {
/* 23 */       return this.base;
/*    */     }
/* 25 */     double $$3 = ($$0 == 0L) ? this.base.x : decode(encode(this.base.x) + $$0);
/* 26 */     double $$4 = ($$1 == 0L) ? this.base.y : decode(encode(this.base.y) + $$1);
/* 27 */     double $$5 = ($$2 == 0L) ? this.base.z : decode(encode(this.base.z) + $$2);
/* 28 */     return new Vec3($$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   public long encodeX(Vec3 $$0) {
/* 32 */     return encode($$0.x) - encode(this.base.x);
/*    */   }
/*    */   
/*    */   public long encodeY(Vec3 $$0) {
/* 36 */     return encode($$0.y) - encode(this.base.y);
/*    */   }
/*    */   
/*    */   public long encodeZ(Vec3 $$0) {
/* 40 */     return encode($$0.z) - encode(this.base.z);
/*    */   }
/*    */   
/*    */   public Vec3 delta(Vec3 $$0) {
/* 44 */     return $$0.subtract(this.base);
/*    */   }
/*    */   
/*    */   public void setBase(Vec3 $$0) {
/* 48 */     this.base = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\VecDeltaCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */