/*     */ package net.minecraft.client.resources.sounds;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.sounds.SoundManager;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.biome.AmbientAdditionsSettings;
/*     */ import net.minecraft.world.level.biome.AmbientMoodSettings;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeManager;
/*     */ 
/*     */ 
/*     */ public class BiomeAmbientSoundsHandler
/*     */   implements AmbientSoundHandler
/*     */ {
/*     */   private static final int LOOP_SOUND_CROSS_FADE_TIME = 40;
/*     */   private static final float SKY_MOOD_RECOVERY_RATE = 0.001F;
/*     */   private final LocalPlayer player;
/*     */   private final SoundManager soundManager;
/*     */   private final BiomeManager biomeManager;
/*     */   private final RandomSource random;
/*  31 */   private final Object2ObjectArrayMap<Biome, LoopSoundInstance> loopSounds = new Object2ObjectArrayMap();
/*  32 */   private Optional<AmbientMoodSettings> moodSettings = Optional.empty();
/*  33 */   private Optional<AmbientAdditionsSettings> additionsSettings = Optional.empty();
/*     */   
/*     */   private float moodiness;
/*     */   @Nullable
/*     */   private Biome previousBiome;
/*     */   
/*     */   public BiomeAmbientSoundsHandler(LocalPlayer $$0, SoundManager $$1, BiomeManager $$2) {
/*  40 */     this.random = $$0.level().getRandom();
/*     */     
/*  42 */     this.player = $$0;
/*  43 */     this.soundManager = $$1;
/*  44 */     this.biomeManager = $$2;
/*     */   }
/*     */   
/*     */   public float getMoodiness() {
/*  48 */     return this.moodiness;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  53 */     this.loopSounds.values().removeIf(AbstractTickableSoundInstance::isStopped);
/*     */     
/*  55 */     Biome $$0 = (Biome)this.biomeManager.getNoiseBiomeAtPosition(this.player.getX(), this.player.getY(), this.player.getZ()).value();
/*  56 */     if ($$0 != this.previousBiome) {
/*  57 */       this.previousBiome = $$0;
/*     */       
/*  59 */       this.moodSettings = $$0.getAmbientMood();
/*  60 */       this.additionsSettings = $$0.getAmbientAdditions();
/*     */       
/*  62 */       this.loopSounds.values().forEach(LoopSoundInstance::fadeOut);
/*     */       
/*  64 */       $$0.getAmbientLoop().ifPresent($$1 -> this.loopSounds.compute($$0, ()));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     this.additionsSettings.ifPresent($$0 -> {
/*     */           if (this.random.nextDouble() < $$0.getTickChance()) {
/*     */             this.soundManager.play(SimpleSoundInstance.forAmbientAddition((SoundEvent)$$0.getSoundEvent().value()));
/*     */           }
/*     */         });
/*     */     
/*  80 */     this.moodSettings.ifPresent($$0 -> {
/*     */           Level $$1 = this.player.level();
/*     */           int $$2 = $$0.getBlockSearchExtent() * 2 + 1;
/*     */           BlockPos $$3 = BlockPos.containing(this.player.getX() + this.random.nextInt($$2) - $$0.getBlockSearchExtent(), this.player.getEyeY() + this.random.nextInt($$2) - $$0.getBlockSearchExtent(), this.player.getZ() + this.random.nextInt($$2) - $$0.getBlockSearchExtent());
/*     */           int $$4 = $$1.getBrightness(LightLayer.SKY, $$3);
/*     */           if ($$4 > 0) {
/*     */             this.moodiness -= $$4 / $$1.getMaxLightLevel() * 0.001F;
/*     */           } else {
/*     */             this.moodiness -= ($$1.getBrightness(LightLayer.BLOCK, $$3) - 1) / $$0.getTickDelay();
/*     */           } 
/*     */           if (this.moodiness >= 1.0F) {
/*     */             double $$5 = $$3.getX() + 0.5D;
/*     */             double $$6 = $$3.getY() + 0.5D;
/*     */             double $$7 = $$3.getZ() + 0.5D;
/*     */             double $$8 = $$5 - this.player.getX();
/*     */             double $$9 = $$6 - this.player.getEyeY();
/*     */             double $$10 = $$7 - this.player.getZ();
/*     */             double $$11 = Math.sqrt($$8 * $$8 + $$9 * $$9 + $$10 * $$10);
/*     */             double $$12 = $$11 + $$0.getSoundPositionOffset();
/*     */             SimpleSoundInstance $$13 = SimpleSoundInstance.forAmbientMood((SoundEvent)$$0.getSoundEvent().value(), this.random, this.player.getX() + $$8 / $$11 * $$12, this.player.getEyeY() + $$9 / $$11 * $$12, this.player.getZ() + $$10 / $$11 * $$12);
/*     */             this.soundManager.play($$13);
/*     */             this.moodiness = 0.0F;
/*     */           } else {
/*     */             this.moodiness = Math.max(this.moodiness, 0.0F);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class LoopSoundInstance
/*     */     extends AbstractTickableSoundInstance
/*     */   {
/*     */     private int fadeDirection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int fade;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LoopSoundInstance(SoundEvent $$0) {
/* 130 */       super($$0, SoundSource.AMBIENT, SoundInstance.createUnseededRandom());
/*     */       
/* 132 */       this.looping = true;
/* 133 */       this.delay = 0;
/* 134 */       this.volume = 1.0F;
/* 135 */       this.relative = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 140 */       if (this.fade < 0) {
/* 141 */         stop();
/*     */       }
/*     */       
/* 144 */       this.fade += this.fadeDirection;
/* 145 */       this.volume = Mth.clamp(this.fade / 40.0F, 0.0F, 1.0F);
/*     */     }
/*     */     
/*     */     public void fadeOut() {
/* 149 */       this.fade = Math.min(this.fade, 40);
/* 150 */       this.fadeDirection = -1;
/*     */     }
/*     */     
/*     */     public void fadeIn() {
/* 154 */       this.fade = Math.max(0, this.fade);
/* 155 */       this.fadeDirection = 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\BiomeAmbientSoundsHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */