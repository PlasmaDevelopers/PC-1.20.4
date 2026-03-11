/*    */ package net.minecraft.client.particle;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
/*    */ 
/*    */ public class SculkChargePopParticle extends TextureSheetParticle {
/*    */   private final SpriteSet sprites;
/*    */   
/*    */   SculkChargePopParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, SpriteSet $$7) {
/* 10 */     super($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/* 11 */     this.friction = 0.96F;
/*    */     
/* 13 */     this.sprites = $$7;
/* 14 */     scale(1.0F);
/*    */     
/* 16 */     this.hasPhysics = false;
/*    */     
/* 18 */     setSpriteFromAge($$7);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightColor(float $$0) {
/* 23 */     return 240;
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleRenderType getRenderType() {
/* 28 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 33 */     super.tick();
/* 34 */     setSpriteFromAge(this.sprites);
/*    */   }
/*    */   public static final class Provider extends Record implements ParticleProvider<SimpleParticleType> { private final SpriteSet sprite;
/* 37 */     public Provider(SpriteSet $$0) { this.sprite = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/particle/SculkChargePopParticle$Provider;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #37	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 37 */       //   0	7	0	this	Lnet/minecraft/client/particle/SculkChargePopParticle$Provider; } public SpriteSet sprite() { return this.sprite; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/particle/SculkChargePopParticle$Provider;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #37	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/particle/SculkChargePopParticle$Provider; }
/*    */     public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/particle/SculkChargePopParticle$Provider;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #37	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/particle/SculkChargePopParticle$Provider;
/*    */       //   0	8	1	$$0	Ljava/lang/Object; } public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 40 */       SculkChargePopParticle $$8 = new SculkChargePopParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprite);
/* 41 */       $$8.setAlpha(1.0F);
/* 42 */       $$8.setParticleSpeed($$5, $$6, $$7);
/* 43 */       $$8.setLifetime($$1.random.nextInt(4) + 6);
/* 44 */       return $$8;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SculkChargePopParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */