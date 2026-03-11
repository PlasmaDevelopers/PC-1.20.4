/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
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
/*    */ public class UnderwaterAmbientSoundInstance
/*    */   extends AbstractTickableSoundInstance
/*    */ {
/*    */   public static final int FADE_DURATION = 40;
/*    */   private final LocalPlayer player;
/*    */   private int fade;
/*    */   
/*    */   public UnderwaterAmbientSoundInstance(LocalPlayer $$0) {
/* 38 */     super(SoundEvents.AMBIENT_UNDERWATER_LOOP, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
/*    */     
/* 40 */     this.player = $$0;
/* 41 */     this.looping = true;
/* 42 */     this.delay = 0;
/* 43 */     this.volume = 1.0F;
/* 44 */     this.relative = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 49 */     if (this.player.isRemoved() || this.fade < 0) {
/* 50 */       stop();
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     if (this.player.isUnderWater()) {
/* 55 */       this.fade++;
/*    */     } else {
/* 57 */       this.fade -= 2;
/*    */     } 
/*    */     
/* 60 */     this.fade = Math.min(this.fade, 40);
/* 61 */     this.volume = Math.max(0.0F, Math.min(this.fade / 40.0F, 1.0F));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\UnderwaterAmbientSoundInstances$UnderwaterAmbientSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */