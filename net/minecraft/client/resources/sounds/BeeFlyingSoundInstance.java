/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.animal.Bee;
/*    */ 
/*    */ public class BeeFlyingSoundInstance extends BeeSoundInstance {
/*    */   public BeeFlyingSoundInstance(Bee $$0) {
/*  9 */     super($$0, SoundEvents.BEE_LOOP, SoundSource.NEUTRAL);
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractTickableSoundInstance getAlternativeSoundInstance() {
/* 14 */     return new BeeAggressiveSoundInstance(this.bee);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSwitchSounds() {
/* 19 */     return this.bee.isAngry();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\BeeFlyingSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */