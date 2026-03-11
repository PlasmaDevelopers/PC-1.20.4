/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.monster.Guardian;
/*    */ 
/*    */ public class GuardianAttackSoundInstance
/*    */   extends AbstractTickableSoundInstance {
/*    */   private static final float VOLUME_MIN = 0.0F;
/*    */   private static final float VOLUME_SCALE = 1.0F;
/*    */   private static final float PITCH_MIN = 0.7F;
/*    */   private static final float PITCH_SCALE = 0.5F;
/*    */   private final Guardian guardian;
/*    */   
/*    */   public GuardianAttackSoundInstance(Guardian $$0) {
/* 16 */     super(SoundEvents.GUARDIAN_ATTACK, SoundSource.HOSTILE, SoundInstance.createUnseededRandom());
/*    */     
/* 18 */     this.guardian = $$0;
/*    */     
/* 20 */     this.attenuation = SoundInstance.Attenuation.NONE;
/* 21 */     this.looping = true;
/* 22 */     this.delay = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaySound() {
/* 27 */     return !this.guardian.isSilent();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 32 */     if (this.guardian.isRemoved() || this.guardian.getTarget() != null) {
/* 33 */       stop();
/*    */       
/*    */       return;
/*    */     } 
/* 37 */     this.x = (float)this.guardian.getX();
/* 38 */     this.y = (float)this.guardian.getY();
/* 39 */     this.z = (float)this.guardian.getZ();
/*    */     
/* 41 */     float $$0 = this.guardian.getAttackAnimationScale(0.0F);
/* 42 */     this.volume = 0.0F + 1.0F * $$0 * $$0;
/* 43 */     this.pitch = 0.7F + 0.5F * $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\GuardianAttackSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */