/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.SimpleParticleType;
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
/*    */ public class AngryVillagerProvider
/*    */   implements ParticleProvider<SimpleParticleType>
/*    */ {
/*    */   private final SpriteSet sprite;
/*    */   
/*    */   public AngryVillagerProvider(SpriteSet $$0) {
/* 51 */     this.sprite = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 56 */     HeartParticle $$8 = new HeartParticle($$1, $$2, $$3 + 0.5D, $$4);
/* 57 */     $$8.pickSprite(this.sprite);
/* 58 */     $$8.setColor(1.0F, 1.0F, 1.0F);
/* 59 */     return $$8;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\HeartParticle$AngryVillagerProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */