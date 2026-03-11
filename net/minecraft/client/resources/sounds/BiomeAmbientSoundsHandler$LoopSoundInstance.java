/*     */ package net.minecraft.client.resources.sounds;
/*     */ 
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoopSoundInstance
/*     */   extends AbstractTickableSoundInstance
/*     */ {
/*     */   private int fadeDirection;
/*     */   private int fade;
/*     */   
/*     */   public LoopSoundInstance(SoundEvent $$0) {
/* 130 */     super($$0, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
/*     */     
/* 132 */     this.looping = true;
/* 133 */     this.delay = 0;
/* 134 */     this.volume = 1.0F;
/* 135 */     this.relative = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 140 */     if (this.fade < 0) {
/* 141 */       stop();
/*     */     }
/*     */     
/* 144 */     this.fade += this.fadeDirection;
/* 145 */     this.volume = Mth.clamp(this.fade / 40.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void fadeOut() {
/* 149 */     this.fade = Math.min(this.fade, 40);
/* 150 */     this.fadeDirection = -1;
/*     */   }
/*     */   
/*     */   public void fadeIn() {
/* 154 */     this.fade = Math.max(0, this.fade);
/* 155 */     this.fadeDirection = 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\BiomeAmbientSoundsHandler$LoopSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */