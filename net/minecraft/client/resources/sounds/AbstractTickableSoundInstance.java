/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public abstract class AbstractTickableSoundInstance extends AbstractSoundInstance implements TickableSoundInstance {
/*    */   private boolean stopped;
/*    */   
/*    */   protected AbstractTickableSoundInstance(SoundEvent $$0, SoundSource $$1, RandomSource $$2) {
/* 11 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isStopped() {
/* 16 */     return this.stopped;
/*    */   }
/*    */   
/*    */   protected final void stop() {
/* 20 */     this.stopped = true;
/*    */     
/* 22 */     this.looping = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\AbstractTickableSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */