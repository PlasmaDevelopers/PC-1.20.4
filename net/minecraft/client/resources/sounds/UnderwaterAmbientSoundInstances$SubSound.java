/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ 
/*    */ 
/*    */ public class SubSound
/*    */   extends AbstractTickableSoundInstance
/*    */ {
/*    */   private final LocalPlayer player;
/*    */   
/*    */   protected SubSound(LocalPlayer $$0, SoundEvent $$1) {
/* 14 */     super($$1, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
/*    */     
/* 16 */     this.player = $$0;
/* 17 */     this.looping = false;
/* 18 */     this.delay = 0;
/* 19 */     this.volume = 1.0F;
/* 20 */     this.relative = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 25 */     if (this.player.isRemoved() || !this.player.isUnderWater())
/* 26 */       stop(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\UnderwaterAmbientSoundInstances$SubSound.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */