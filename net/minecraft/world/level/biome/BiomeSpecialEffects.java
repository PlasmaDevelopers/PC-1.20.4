/*     */ package net.minecraft.world.level.biome;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.util.Function12;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.sounds.Music;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ public class BiomeSpecialEffects {
/*     */   static {
/*  15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("fog_color").forGetter(()), (App)Codec.INT.fieldOf("water_color").forGetter(()), (App)Codec.INT.fieldOf("water_fog_color").forGetter(()), (App)Codec.INT.fieldOf("sky_color").forGetter(()), (App)Codec.INT.optionalFieldOf("foliage_color").forGetter(()), (App)Codec.INT.optionalFieldOf("grass_color").forGetter(()), (App)GrassColorModifier.CODEC.optionalFieldOf("grass_color_modifier", GrassColorModifier.NONE).forGetter(()), (App)AmbientParticleSettings.CODEC.optionalFieldOf("particle").forGetter(()), (App)SoundEvent.CODEC.optionalFieldOf("ambient_sound").forGetter(()), (App)AmbientMoodSettings.CODEC.optionalFieldOf("mood_sound").forGetter(()), (App)AmbientAdditionsSettings.CODEC.optionalFieldOf("additions_sound").forGetter(()), (App)Music.CODEC.optionalFieldOf("music").forGetter(())).apply((Applicative)$$0, BiomeSpecialEffects::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<BiomeSpecialEffects> CODEC;
/*     */   
/*     */   private final int fogColor;
/*     */   
/*     */   private final int waterColor;
/*     */   
/*     */   private final int waterFogColor;
/*     */   
/*     */   private final int skyColor;
/*     */   
/*     */   private final Optional<Integer> foliageColorOverride;
/*     */   
/*     */   private final Optional<Integer> grassColorOverride;
/*     */   
/*     */   private final GrassColorModifier grassColorModifier;
/*     */   
/*     */   private final Optional<AmbientParticleSettings> ambientParticleSettings;
/*     */   
/*     */   private final Optional<Holder<SoundEvent>> ambientLoopSoundEvent;
/*     */   
/*     */   private final Optional<AmbientMoodSettings> ambientMoodSettings;
/*     */   private final Optional<AmbientAdditionsSettings> ambientAdditionsSettings;
/*     */   private final Optional<Music> backgroundMusic;
/*     */   
/*     */   BiomeSpecialEffects(int $$0, int $$1, int $$2, int $$3, Optional<Integer> $$4, Optional<Integer> $$5, GrassColorModifier $$6, Optional<AmbientParticleSettings> $$7, Optional<Holder<SoundEvent>> $$8, Optional<AmbientMoodSettings> $$9, Optional<AmbientAdditionsSettings> $$10, Optional<Music> $$11) {
/*  44 */     this.fogColor = $$0;
/*  45 */     this.waterColor = $$1;
/*  46 */     this.waterFogColor = $$2;
/*  47 */     this.skyColor = $$3;
/*  48 */     this.foliageColorOverride = $$4;
/*  49 */     this.grassColorOverride = $$5;
/*  50 */     this.grassColorModifier = $$6;
/*  51 */     this.ambientParticleSettings = $$7;
/*  52 */     this.ambientLoopSoundEvent = $$8;
/*  53 */     this.ambientMoodSettings = $$9;
/*  54 */     this.ambientAdditionsSettings = $$10;
/*  55 */     this.backgroundMusic = $$11;
/*     */   }
/*     */   
/*     */   public int getFogColor() {
/*  59 */     return this.fogColor;
/*     */   }
/*     */   
/*     */   public int getWaterColor() {
/*  63 */     return this.waterColor;
/*     */   }
/*     */   
/*     */   public int getWaterFogColor() {
/*  67 */     return this.waterFogColor;
/*     */   }
/*     */   
/*     */   public int getSkyColor() {
/*  71 */     return this.skyColor;
/*     */   }
/*     */   
/*     */   public Optional<Integer> getFoliageColorOverride() {
/*  75 */     return this.foliageColorOverride;
/*     */   }
/*     */   
/*     */   public Optional<Integer> getGrassColorOverride() {
/*  79 */     return this.grassColorOverride;
/*     */   }
/*     */   
/*     */   public GrassColorModifier getGrassColorModifier() {
/*  83 */     return this.grassColorModifier;
/*     */   }
/*     */   
/*     */   public Optional<AmbientParticleSettings> getAmbientParticleSettings() {
/*  87 */     return this.ambientParticleSettings;
/*     */   }
/*     */   
/*     */   public Optional<Holder<SoundEvent>> getAmbientLoopSoundEvent() {
/*  91 */     return this.ambientLoopSoundEvent;
/*     */   }
/*     */   
/*     */   public Optional<AmbientMoodSettings> getAmbientMoodSettings() {
/*  95 */     return this.ambientMoodSettings;
/*     */   }
/*     */   
/*     */   public Optional<AmbientAdditionsSettings> getAmbientAdditionsSettings() {
/*  99 */     return this.ambientAdditionsSettings;
/*     */   }
/*     */   
/*     */   public Optional<Music> getBackgroundMusic() {
/* 103 */     return this.backgroundMusic;
/*     */   }
/*     */   
/*     */   public static class Builder {
/* 107 */     private OptionalInt fogColor = OptionalInt.empty();
/* 108 */     private OptionalInt waterColor = OptionalInt.empty();
/* 109 */     private OptionalInt waterFogColor = OptionalInt.empty();
/* 110 */     private OptionalInt skyColor = OptionalInt.empty();
/* 111 */     private Optional<Integer> foliageColorOverride = Optional.empty();
/* 112 */     private Optional<Integer> grassColorOverride = Optional.empty();
/* 113 */     private BiomeSpecialEffects.GrassColorModifier grassColorModifier = BiomeSpecialEffects.GrassColorModifier.NONE;
/* 114 */     private Optional<AmbientParticleSettings> ambientParticle = Optional.empty();
/* 115 */     private Optional<Holder<SoundEvent>> ambientLoopSoundEvent = Optional.empty();
/* 116 */     private Optional<AmbientMoodSettings> ambientMoodSettings = Optional.empty();
/* 117 */     private Optional<AmbientAdditionsSettings> ambientAdditionsSettings = Optional.empty();
/* 118 */     private Optional<Music> backgroundMusic = Optional.empty();
/*     */     
/*     */     public Builder fogColor(int $$0) {
/* 121 */       this.fogColor = OptionalInt.of($$0);
/* 122 */       return this;
/*     */     }
/*     */     
/*     */     public Builder waterColor(int $$0) {
/* 126 */       this.waterColor = OptionalInt.of($$0);
/* 127 */       return this;
/*     */     }
/*     */     
/*     */     public Builder waterFogColor(int $$0) {
/* 131 */       this.waterFogColor = OptionalInt.of($$0);
/* 132 */       return this;
/*     */     }
/*     */     
/*     */     public Builder skyColor(int $$0) {
/* 136 */       this.skyColor = OptionalInt.of($$0);
/* 137 */       return this;
/*     */     }
/*     */     
/*     */     public Builder foliageColorOverride(int $$0) {
/* 141 */       this.foliageColorOverride = Optional.of(Integer.valueOf($$0));
/* 142 */       return this;
/*     */     }
/*     */     
/*     */     public Builder grassColorOverride(int $$0) {
/* 146 */       this.grassColorOverride = Optional.of(Integer.valueOf($$0));
/* 147 */       return this;
/*     */     }
/*     */     
/*     */     public Builder grassColorModifier(BiomeSpecialEffects.GrassColorModifier $$0) {
/* 151 */       this.grassColorModifier = $$0;
/* 152 */       return this;
/*     */     }
/*     */     
/*     */     public Builder ambientParticle(AmbientParticleSettings $$0) {
/* 156 */       this.ambientParticle = Optional.of($$0);
/* 157 */       return this;
/*     */     }
/*     */     
/*     */     public Builder ambientLoopSound(Holder<SoundEvent> $$0) {
/* 161 */       this.ambientLoopSoundEvent = Optional.of($$0);
/* 162 */       return this;
/*     */     }
/*     */     
/*     */     public Builder ambientMoodSound(AmbientMoodSettings $$0) {
/* 166 */       this.ambientMoodSettings = Optional.of($$0);
/* 167 */       return this;
/*     */     }
/*     */     
/*     */     public Builder ambientAdditionsSound(AmbientAdditionsSettings $$0) {
/* 171 */       this.ambientAdditionsSettings = Optional.of($$0);
/* 172 */       return this;
/*     */     }
/*     */     
/*     */     public Builder backgroundMusic(@Nullable Music $$0) {
/* 176 */       this.backgroundMusic = Optional.ofNullable($$0);
/* 177 */       return this;
/*     */     }
/*     */     
/*     */     public BiomeSpecialEffects build() {
/* 181 */       return new BiomeSpecialEffects(this.fogColor
/* 182 */           .orElseThrow(() -> new IllegalStateException("Missing 'fog' color.")), this.waterColor
/* 183 */           .orElseThrow(() -> new IllegalStateException("Missing 'water' color.")), this.waterFogColor
/* 184 */           .orElseThrow(() -> new IllegalStateException("Missing 'water fog' color.")), this.skyColor
/* 185 */           .orElseThrow(() -> new IllegalStateException("Missing 'sky' color.")), this.foliageColorOverride, this.grassColorOverride, this.grassColorModifier, this.ambientParticle, this.ambientLoopSoundEvent, this.ambientMoodSettings, this.ambientAdditionsSettings, this.backgroundMusic);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum GrassColorModifier
/*     */     implements StringRepresentable
/*     */   {
/* 197 */     NONE("none")
/*     */     {
/*     */       public int modifyColor(double $$0, double $$1, int $$2) {
/* 200 */         return $$2;
/*     */       }
/*     */     },
/* 203 */     DARK_FOREST("dark_forest")
/*     */     {
/*     */       public int modifyColor(double $$0, double $$1, int $$2) {
/* 206 */         return ($$2 & 0xFEFEFE) + 2634762 >> 1;
/*     */       }
/*     */     },
/* 209 */     SWAMP("swamp")
/*     */     {
/*     */       public int modifyColor(double $$0, double $$1, int $$2) {
/* 212 */         double $$3 = Biome.BIOME_INFO_NOISE.getValue($$0 * 0.0225D, $$1 * 0.0225D, false);
/* 213 */         if ($$3 < -0.1D) {
/* 214 */           return 5011004;
/*     */         }
/* 216 */         return 6975545;
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     GrassColorModifier(String $$0) {
/*     */       this.name = $$0;
/*     */     }
/*     */     
/* 228 */     public static final Codec<GrassColorModifier> CODEC = (Codec<GrassColorModifier>)StringRepresentable.fromEnum(GrassColorModifier::values);
/*     */     
/*     */     public String getName() {
/* 231 */       return this.name;
/*     */     } static {
/*     */     
/*     */     }
/*     */     public String getSerializedName() {
/* 236 */       return this.name;
/*     */     }
/*     */     
/*     */     public abstract int modifyColor(double param1Double1, double param1Double2, int param1Int);
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public int modifyColor(double $$0, double $$1, int $$2) {
/*     */       return $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public int modifyColor(double $$0, double $$1, int $$2) {
/*     */       return ($$2 & 0xFEFEFE) + 2634762 >> 1;
/*     */     }
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public int modifyColor(double $$0, double $$1, int $$2) {
/*     */       double $$3 = Biome.BIOME_INFO_NOISE.getValue($$0 * 0.0225D, $$1 * 0.0225D, false);
/*     */       if ($$3 < -0.1D)
/*     */         return 5011004; 
/*     */       return 6975545;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\BiomeSpecialEffects.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */