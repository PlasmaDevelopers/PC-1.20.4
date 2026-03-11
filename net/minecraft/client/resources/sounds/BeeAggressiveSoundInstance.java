/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.animal.Bee;
/*    */ 
/*    */ public class BeeAggressiveSoundInstance extends BeeSoundInstance {
/*    */   public BeeAggressiveSoundInstance(Bee $$0) {
/*  9 */     super($$0, SoundEvents.BEE_LOOP_AGGRESSIVE, SoundSource.NEUTRAL);
/* 10 */     this.delay = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractTickableSoundInstance getAlternativeSoundInstance() {
/* 15 */     return new BeeFlyingSoundInstance(this.bee);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSwitchSounds() {
/* 20 */     return !this.bee.isAngry();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\BeeAggressiveSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */