/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SculkChargeParticleOptions;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Provider
/*    */   extends Record
/*    */   implements ParticleProvider<SculkChargeParticleOptions>
/*    */ {
/*    */   private final SpriteSet sprite;
/*    */   
/*    */   public Provider(SpriteSet $$0) {
/* 37 */     this.sprite = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/particle/SculkChargeParticle$Provider;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #37	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 37 */     //   0	7	0	this	Lnet/minecraft/client/particle/SculkChargeParticle$Provider; } public SpriteSet sprite() { return this.sprite; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/particle/SculkChargeParticle$Provider;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #37	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/particle/SculkChargeParticle$Provider; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/particle/SculkChargeParticle$Provider;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #37	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/particle/SculkChargeParticle$Provider;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } public Particle createParticle(SculkChargeParticleOptions $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 40 */     SculkChargeParticle $$8 = new SculkChargeParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, this.sprite);
/* 41 */     $$8.setAlpha(1.0F);
/* 42 */     $$8.setParticleSpeed($$5, $$6, $$7);
/* 43 */     $$8.oRoll = $$0.roll();
/* 44 */     $$8.roll = $$0.roll();
/* 45 */     $$8.setLifetime($$1.random.nextInt(12) + 8);
/* 46 */     return $$8;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\SculkChargeParticle$Provider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */