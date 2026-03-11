/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ 
/*    */ public abstract class RisingParticle extends TextureSheetParticle {
/*    */   protected RisingParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*  7 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*  8 */     this.friction = 0.96F;
/*  9 */     this.xd = this.xd * 0.009999999776482582D + $$4;
/* 10 */     this.yd = this.yd * 0.009999999776482582D + $$5;
/* 11 */     this.zd = this.zd * 0.009999999776482582D + $$6;
/* 12 */     this.x += ((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
/* 13 */     this.y += ((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
/* 14 */     this.z += ((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
/*    */     
/* 16 */     this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\RisingParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */