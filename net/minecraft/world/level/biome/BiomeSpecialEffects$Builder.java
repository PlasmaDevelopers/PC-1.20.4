/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.sounds.Music;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/* 107 */   private OptionalInt fogColor = OptionalInt.empty();
/* 108 */   private OptionalInt waterColor = OptionalInt.empty();
/* 109 */   private OptionalInt waterFogColor = OptionalInt.empty();
/* 110 */   private OptionalInt skyColor = OptionalInt.empty();
/* 111 */   private Optional<Integer> foliageColorOverride = Optional.empty();
/* 112 */   private Optional<Integer> grassColorOverride = Optional.empty();
/* 113 */   private BiomeSpecialEffects.GrassColorModifier grassColorModifier = BiomeSpecialEffects.GrassColorModifier.NONE;
/* 114 */   private Optional<AmbientParticleSettings> ambientParticle = Optional.empty();
/* 115 */   private Optional<Holder<SoundEvent>> ambientLoopSoundEvent = Optional.empty();
/* 116 */   private Optional<AmbientMoodSettings> ambientMoodSettings = Optional.empty();
/* 117 */   private Optional<AmbientAdditionsSettings> ambientAdditionsSettings = Optional.empty();
/* 118 */   private Optional<Music> backgroundMusic = Optional.empty();
/*     */   
/*     */   public Builder fogColor(int $$0) {
/* 121 */     this.fogColor = OptionalInt.of($$0);
/* 122 */     return this;
/*     */   }
/*     */   
/*     */   public Builder waterColor(int $$0) {
/* 126 */     this.waterColor = OptionalInt.of($$0);
/* 127 */     return this;
/*     */   }
/*     */   
/*     */   public Builder waterFogColor(int $$0) {
/* 131 */     this.waterFogColor = OptionalInt.of($$0);
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   public Builder skyColor(int $$0) {
/* 136 */     this.skyColor = OptionalInt.of($$0);
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public Builder foliageColorOverride(int $$0) {
/* 141 */     this.foliageColorOverride = Optional.of(Integer.valueOf($$0));
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public Builder grassColorOverride(int $$0) {
/* 146 */     this.grassColorOverride = Optional.of(Integer.valueOf($$0));
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public Builder grassColorModifier(BiomeSpecialEffects.GrassColorModifier $$0) {
/* 151 */     this.grassColorModifier = $$0;
/* 152 */     return this;
/*     */   }
/*     */   
/*     */   public Builder ambientParticle(AmbientParticleSettings $$0) {
/* 156 */     this.ambientParticle = Optional.of($$0);
/* 157 */     return this;
/*     */   }
/*     */   
/*     */   public Builder ambientLoopSound(Holder<SoundEvent> $$0) {
/* 161 */     this.ambientLoopSoundEvent = Optional.of($$0);
/* 162 */     return this;
/*     */   }
/*     */   
/*     */   public Builder ambientMoodSound(AmbientMoodSettings $$0) {
/* 166 */     this.ambientMoodSettings = Optional.of($$0);
/* 167 */     return this;
/*     */   }
/*     */   
/*     */   public Builder ambientAdditionsSound(AmbientAdditionsSettings $$0) {
/* 171 */     this.ambientAdditionsSettings = Optional.of($$0);
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public Builder backgroundMusic(@Nullable Music $$0) {
/* 176 */     this.backgroundMusic = Optional.ofNullable($$0);
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   public BiomeSpecialEffects build() {
/* 181 */     return new BiomeSpecialEffects(this.fogColor
/* 182 */         .orElseThrow(() -> new IllegalStateException("Missing 'fog' color.")), this.waterColor
/* 183 */         .orElseThrow(() -> new IllegalStateException("Missing 'water' color.")), this.waterFogColor
/* 184 */         .orElseThrow(() -> new IllegalStateException("Missing 'water fog' color.")), this.skyColor
/* 185 */         .orElseThrow(() -> new IllegalStateException("Missing 'sky' color.")), this.foliageColorOverride, this.grassColorOverride, this.grassColorModifier, this.ambientParticle, this.ambientLoopSoundEvent, this.ambientMoodSettings, this.ambientAdditionsSettings, this.backgroundMusic);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\BiomeSpecialEffects$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */