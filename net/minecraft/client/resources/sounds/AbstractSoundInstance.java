/*     */ package net.minecraft.client.resources.sounds;
/*     */ 
/*     */ import net.minecraft.client.sounds.SoundManager;
/*     */ import net.minecraft.client.sounds.WeighedSoundEvents;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ public abstract class AbstractSoundInstance implements SoundInstance {
/*     */   protected Sound sound;
/*     */   protected final SoundSource source;
/*     */   protected final ResourceLocation location;
/*  14 */   protected float volume = 1.0F;
/*  15 */   protected float pitch = 1.0F;
/*     */   protected double x;
/*     */   protected double y;
/*     */   protected double z;
/*     */   protected boolean looping;
/*     */   protected int delay;
/*  21 */   protected SoundInstance.Attenuation attenuation = SoundInstance.Attenuation.LINEAR;
/*     */   protected boolean relative;
/*     */   protected RandomSource random;
/*     */   
/*     */   protected AbstractSoundInstance(SoundEvent $$0, SoundSource $$1, RandomSource $$2) {
/*  26 */     this($$0.getLocation(), $$1, $$2);
/*     */   }
/*     */   
/*     */   protected AbstractSoundInstance(ResourceLocation $$0, SoundSource $$1, RandomSource $$2) {
/*  30 */     this.location = $$0;
/*  31 */     this.source = $$1;
/*  32 */     this.random = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocation() {
/*  37 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public WeighedSoundEvents resolve(SoundManager $$0) {
/*  42 */     if (this.location.equals(SoundManager.INTENTIONALLY_EMPTY_SOUND_LOCATION)) {
/*  43 */       this.sound = SoundManager.INTENTIONALLY_EMPTY_SOUND;
/*  44 */       return SoundManager.INTENTIONALLY_EMPTY_SOUND_EVENT;
/*     */     } 
/*  46 */     WeighedSoundEvents $$1 = $$0.getSoundEvent(this.location);
/*  47 */     if ($$1 == null) {
/*  48 */       this.sound = SoundManager.EMPTY_SOUND;
/*     */     } else {
/*  50 */       this.sound = $$1.getSound(this.random);
/*     */     } 
/*     */     
/*  53 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Sound getSound() {
/*  58 */     return this.sound;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSource() {
/*  63 */     return this.source;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLooping() {
/*  68 */     return this.looping;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDelay() {
/*  73 */     return this.delay;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVolume() {
/*  78 */     return this.volume * this.sound.getVolume().sample(this.random);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getPitch() {
/*  83 */     return this.pitch * this.sound.getPitch().sample(this.random);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getX() {
/*  88 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getY() {
/*  93 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getZ() {
/*  98 */     return this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundInstance.Attenuation getAttenuation() {
/* 103 */     return this.attenuation;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRelative() {
/* 108 */     return this.relative;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 113 */     return "SoundInstance[" + this.location + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\AbstractSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */