/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ 
/*    */ public class UnderwaterAmbientSoundInstances {
/*    */   public static class SubSound
/*    */     extends AbstractTickableSoundInstance {
/*    */     private final LocalPlayer player;
/*    */     
/*    */     protected SubSound(LocalPlayer $$0, SoundEvent $$1) {
/* 14 */       super($$1, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
/*    */       
/* 16 */       this.player = $$0;
/* 17 */       this.looping = false;
/* 18 */       this.delay = 0;
/* 19 */       this.volume = 1.0F;
/* 20 */       this.relative = true;
/*    */     }
/*    */ 
/*    */     
/*    */     public void tick() {
/* 25 */       if (this.player.isRemoved() || !this.player.isUnderWater())
/* 26 */         stop(); 
/*    */     }
/*    */   }
/*    */   
/*    */   public static class UnderwaterAmbientSoundInstance
/*    */     extends AbstractTickableSoundInstance
/*    */   {
/*    */     public static final int FADE_DURATION = 40;
/*    */     private final LocalPlayer player;
/*    */     private int fade;
/*    */     
/*    */     public UnderwaterAmbientSoundInstance(LocalPlayer $$0) {
/* 38 */       super(SoundEvents.AMBIENT_UNDERWATER_LOOP, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
/*    */       
/* 40 */       this.player = $$0;
/* 41 */       this.looping = true;
/* 42 */       this.delay = 0;
/* 43 */       this.volume = 1.0F;
/* 44 */       this.relative = true;
/*    */     }
/*    */ 
/*    */     
/*    */     public void tick() {
/* 49 */       if (this.player.isRemoved() || this.fade < 0) {
/* 50 */         stop();
/*    */         
/*    */         return;
/*    */       } 
/* 54 */       if (this.player.isUnderWater()) {
/* 55 */         this.fade++;
/*    */       } else {
/* 57 */         this.fade -= 2;
/*    */       } 
/*    */       
/* 60 */       this.fade = Math.min(this.fade, 40);
/* 61 */       this.volume = Math.max(0.0F, Math.min(this.fade / 40.0F, 1.0F));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\UnderwaterAmbientSoundInstances.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */