/*     */ package net.minecraft.client.sounds;
/*     */ 
/*     */ import net.minecraft.client.resources.sounds.Sound;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.MultipliedFloats;
/*     */ import net.minecraft.util.valueproviders.SampledFloat;
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
/*     */ class null
/*     */   implements Weighted<Sound>
/*     */ {
/*     */   public int getWeight() {
/* 174 */     WeighedSoundEvents $$0 = SoundManager.Preparations.this.registry.get(soundLocation);
/* 175 */     return ($$0 == null) ? 0 : $$0.getWeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public Sound getSound(RandomSource $$0) {
/* 180 */     WeighedSoundEvents $$1 = SoundManager.Preparations.this.registry.get(soundLocation);
/* 181 */     if ($$1 == null) {
/* 182 */       return SoundManager.EMPTY_SOUND;
/*     */     }
/*     */ 
/*     */     
/* 186 */     Sound $$2 = $$1.getSound($$0);
/* 187 */     return new Sound($$2
/* 188 */         .getLocation().toString(), (SampledFloat)new MultipliedFloats(new SampledFloat[] { $$2
/* 189 */             .getVolume(), this.val$sound.getVolume() }, ), (SampledFloat)new MultipliedFloats(new SampledFloat[] { $$2
/* 190 */             .getPitch(), this.val$sound.getPitch() }, ), sound
/* 191 */         .getWeight(), Sound.Type.FILE, ($$2
/*     */         
/* 193 */         .shouldStream() || sound.shouldStream()), $$2
/* 194 */         .shouldPreload(), $$2
/* 195 */         .getAttenuationDistance());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preloadIfRequired(SoundEngine $$0) {
/* 201 */     WeighedSoundEvents $$1 = SoundManager.Preparations.this.registry.get(soundLocation);
/* 202 */     if ($$1 == null) {
/*     */       return;
/*     */     }
/* 205 */     $$1.preloadIfRequired($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\SoundManager$Preparations$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */