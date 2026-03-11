/*     */ package net.minecraft.world.level.biome;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.RegistryFileCodec;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.FoliageColor;
/*     */ import net.minecraft.world.level.GrassColor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ 
/*     */ public final class Biome {
/*     */   public static final Codec<Biome> DIRECT_CODEC;
/*     */   public static final Codec<Biome> NETWORK_CODEC;
/*     */   
/*     */   static {
/*  37 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ClimateSettings.CODEC.forGetter(()), (App)BiomeSpecialEffects.CODEC.fieldOf("effects").forGetter(()), (App)BiomeGenerationSettings.CODEC.forGetter(()), (App)MobSpawnSettings.CODEC.forGetter(())).apply((Applicative)$$0, Biome::new));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  44 */     NETWORK_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ClimateSettings.CODEC.forGetter(()), (App)BiomeSpecialEffects.CODEC.fieldOf("effects").forGetter(())).apply((Applicative)$$0, ()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static final Codec<Holder<Biome>> CODEC = (Codec<Holder<Biome>>)RegistryFileCodec.create(Registries.BIOME, DIRECT_CODEC);
/*  50 */   public static final Codec<HolderSet<Biome>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.BIOME, DIRECT_CODEC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final PerlinSimplexNoise TEMPERATURE_NOISE = new PerlinSimplexNoise((RandomSource)new WorldgenRandom((RandomSource)new LegacyRandomSource(1234L)), (List)ImmutableList.of(Integer.valueOf(0)));
/*  57 */   static final PerlinSimplexNoise FROZEN_TEMPERATURE_NOISE = new PerlinSimplexNoise((RandomSource)new WorldgenRandom((RandomSource)new LegacyRandomSource(3456L)), (List)ImmutableList.of(Integer.valueOf(-2), Integer.valueOf(-1), Integer.valueOf(0)));
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*  60 */   public static final PerlinSimplexNoise BIOME_INFO_NOISE = new PerlinSimplexNoise((RandomSource)new WorldgenRandom((RandomSource)new LegacyRandomSource(2345L)), (List)ImmutableList.of(Integer.valueOf(0)));
/*     */   private static final int TEMPERATURE_CACHE_SIZE = 1024;
/*     */   private final ClimateSettings climateSettings;
/*     */   private final BiomeGenerationSettings generationSettings;
/*     */   private final MobSpawnSettings mobSettings;
/*     */   private final BiomeSpecialEffects specialEffects;
/*     */   
/*  67 */   public enum Precipitation implements StringRepresentable { NONE("none"),
/*  68 */     RAIN("rain"),
/*  69 */     SNOW("snow");
/*     */ 
/*     */     
/*  72 */     public static final Codec<Precipitation> CODEC = (Codec<Precipitation>)StringRepresentable.fromEnum(Precipitation::values); private final String name;
/*     */     static {
/*     */     
/*     */     }
/*     */     Precipitation(String $$0) {
/*  77 */       this.name = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/*  82 */       return this.name;
/*     */     } }
/*     */ 
/*     */   
/*     */   public enum TemperatureModifier implements StringRepresentable {
/*  87 */     NONE("none")
/*     */     {
/*     */       public float modifyTemperature(BlockPos $$0, float $$1) {
/*  90 */         return $$1;
/*     */       }
/*     */     },
/*  93 */     FROZEN("frozen")
/*     */     {
/*     */       public float modifyTemperature(BlockPos $$0, float $$1) {
/*  96 */         double $$2 = Biome.FROZEN_TEMPERATURE_NOISE.getValue($$0.getX() * 0.05D, $$0.getZ() * 0.05D, false) * 7.0D;
/*  97 */         double $$3 = Biome.BIOME_INFO_NOISE.getValue($$0.getX() * 0.2D, $$0.getZ() * 0.2D, false);
/*  98 */         double $$4 = $$2 + $$3;
/*  99 */         if ($$4 < 0.3D) {
/* 100 */           double $$5 = Biome.BIOME_INFO_NOISE.getValue($$0.getX() * 0.09D, $$0.getZ() * 0.09D, false);
/* 101 */           if ($$5 < 0.8D) {
/* 102 */             return 0.2F;
/*     */           }
/*     */         } 
/*     */         
/* 106 */         return $$1;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */     
/* 118 */     public static final Codec<TemperatureModifier> CODEC = (Codec<TemperatureModifier>)StringRepresentable.fromEnum(TemperatureModifier::values); TemperatureModifier(String $$0) { this.name = $$0; }
/*     */     static {  }
/*     */     public String getName() {
/* 121 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getSerializedName()
/*     */     {
/* 126 */       return this.name;
/*     */     }
/*     */     public abstract float modifyTemperature(BlockPos param1BlockPos, float param1Float); }
/*     */   enum null { public float modifyTemperature(BlockPos $$0, float $$1) {
/*     */       return $$1;
/*     */     } }
/* 132 */   private final ThreadLocal<Long2FloatLinkedOpenHashMap> temperatureCache = ThreadLocal.withInitial(() -> (Long2FloatLinkedOpenHashMap)Util.make(()));
/*     */   
/*     */   enum null {
/*     */     public float modifyTemperature(BlockPos $$0, float $$1) {
/*     */       double $$2 = Biome.FROZEN_TEMPERATURE_NOISE.getValue($$0.getX() * 0.05D, $$0.getZ() * 0.05D, false) * 7.0D;
/*     */       double $$3 = Biome.BIOME_INFO_NOISE.getValue($$0.getX() * 0.2D, $$0.getZ() * 0.2D, false);
/*     */       double $$4 = $$2 + $$3;
/*     */       if ($$4 < 0.3D) {
/*     */         double $$5 = Biome.BIOME_INFO_NOISE.getValue($$0.getX() * 0.09D, $$0.getZ() * 0.09D, false);
/*     */         if ($$5 < 0.8D)
/*     */           return 0.2F; 
/*     */       } 
/*     */       return $$1;
/*     */     } }
/*     */   
/*     */   Biome(ClimateSettings $$0, BiomeSpecialEffects $$1, BiomeGenerationSettings $$2, MobSpawnSettings $$3) {
/* 148 */     this.climateSettings = $$0;
/* 149 */     this.generationSettings = $$2;
/* 150 */     this.mobSettings = $$3;
/*     */     
/* 152 */     this.specialEffects = $$1;
/*     */   }
/*     */   
/*     */   public int getSkyColor() {
/* 156 */     return this.specialEffects.getSkyColor();
/*     */   }
/*     */   
/*     */   public MobSpawnSettings getMobSettings() {
/* 160 */     return this.mobSettings;
/*     */   }
/*     */   
/*     */   public boolean hasPrecipitation() {
/* 164 */     return this.climateSettings.hasPrecipitation();
/*     */   }
/*     */   
/*     */   public Precipitation getPrecipitationAt(BlockPos $$0) {
/* 168 */     if (!hasPrecipitation()) {
/* 169 */       return Precipitation.NONE;
/*     */     }
/* 171 */     return coldEnoughToSnow($$0) ? Precipitation.SNOW : Precipitation.RAIN;
/*     */   }
/*     */   
/*     */   private float getHeightAdjustedTemperature(BlockPos $$0) {
/* 175 */     float $$1 = this.climateSettings.temperatureModifier.modifyTemperature($$0, getBaseTemperature());
/*     */     
/* 177 */     if ($$0.getY() > 80) {
/*     */       
/* 179 */       float $$2 = (float)(TEMPERATURE_NOISE.getValue(($$0.getX() / 8.0F), ($$0.getZ() / 8.0F), false) * 8.0D);
/* 180 */       return $$1 - ($$2 + $$0.getY() - 80.0F) * 0.05F / 40.0F;
/*     */     } 
/* 182 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   private float getTemperature(BlockPos $$0) {
/* 188 */     long $$1 = $$0.asLong();
/* 189 */     Long2FloatLinkedOpenHashMap $$2 = this.temperatureCache.get();
/* 190 */     float $$3 = $$2.get($$1);
/* 191 */     if (!Float.isNaN($$3)) {
/* 192 */       return $$3;
/*     */     }
/* 194 */     float $$4 = getHeightAdjustedTemperature($$0);
/* 195 */     if ($$2.size() == 1024) {
/* 196 */       $$2.removeFirstFloat();
/*     */     }
/* 198 */     $$2.put($$1, $$4);
/* 199 */     return $$4;
/*     */   }
/*     */   
/*     */   public boolean shouldFreeze(LevelReader $$0, BlockPos $$1) {
/* 203 */     return shouldFreeze($$0, $$1, true);
/*     */   }
/*     */   
/*     */   public boolean shouldFreeze(LevelReader $$0, BlockPos $$1, boolean $$2) {
/* 207 */     if (warmEnoughToRain($$1)) {
/* 208 */       return false;
/*     */     }
/*     */     
/* 211 */     if ($$1.getY() >= $$0.getMinBuildHeight() && $$1.getY() < $$0.getMaxBuildHeight() && $$0.getBrightness(LightLayer.BLOCK, $$1) < 10) {
/* 212 */       BlockState $$3 = $$0.getBlockState($$1);
/* 213 */       FluidState $$4 = $$0.getFluidState($$1);
/* 214 */       if ($$4.getType() == Fluids.WATER && $$3.getBlock() instanceof net.minecraft.world.level.block.LiquidBlock) {
/* 215 */         if (!$$2) {
/* 216 */           return true;
/*     */         }
/*     */         
/* 219 */         boolean $$5 = ($$0.isWaterAt($$1.west()) && $$0.isWaterAt($$1.east()) && $$0.isWaterAt($$1.north()) && $$0.isWaterAt($$1.south()));
/* 220 */         if (!$$5) {
/* 221 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 225 */     return false;
/*     */   }
/*     */   
/*     */   public boolean coldEnoughToSnow(BlockPos $$0) {
/* 229 */     return !warmEnoughToRain($$0);
/*     */   }
/*     */   
/*     */   public boolean warmEnoughToRain(BlockPos $$0) {
/* 233 */     return (getTemperature($$0) >= 0.15F);
/*     */   }
/*     */   
/*     */   public boolean shouldMeltFrozenOceanIcebergSlightly(BlockPos $$0) {
/* 237 */     return (getTemperature($$0) > 0.1F);
/*     */   }
/*     */   
/*     */   public boolean shouldSnow(LevelReader $$0, BlockPos $$1) {
/* 241 */     if (warmEnoughToRain($$1)) {
/* 242 */       return false;
/*     */     }
/*     */     
/* 245 */     if ($$1.getY() >= $$0.getMinBuildHeight() && $$1.getY() < $$0.getMaxBuildHeight() && $$0.getBrightness(LightLayer.BLOCK, $$1) < 10) {
/* 246 */       BlockState $$2 = $$0.getBlockState($$1);
/*     */ 
/*     */       
/* 249 */       if (($$2.isAir() || $$2.is(Blocks.SNOW)) && Blocks.SNOW.defaultBlockState().canSurvive($$0, $$1)) {
/* 250 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BiomeGenerationSettings getGenerationSettings() {
/* 261 */     return this.generationSettings;
/*     */   }
/*     */   
/*     */   public int getFogColor() {
/* 265 */     return this.specialEffects.getFogColor();
/*     */   }
/*     */   
/*     */   public int getGrassColor(double $$0, double $$1) {
/* 269 */     int $$2 = ((Integer)this.specialEffects.getGrassColorOverride().orElseGet(this::getGrassColorFromTexture)).intValue();
/* 270 */     return this.specialEffects.getGrassColorModifier().modifyColor($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private int getGrassColorFromTexture() {
/* 274 */     double $$0 = Mth.clamp(this.climateSettings.temperature, 0.0F, 1.0F);
/* 275 */     double $$1 = Mth.clamp(this.climateSettings.downfall, 0.0F, 1.0F);
/*     */     
/* 277 */     return GrassColor.get($$0, $$1);
/*     */   }
/*     */   
/*     */   public int getFoliageColor() {
/* 281 */     return ((Integer)this.specialEffects.getFoliageColorOverride().orElseGet(this::getFoliageColorFromTexture)).intValue();
/*     */   }
/*     */   
/*     */   private int getFoliageColorFromTexture() {
/* 285 */     double $$0 = Mth.clamp(this.climateSettings.temperature, 0.0F, 1.0F);
/* 286 */     double $$1 = Mth.clamp(this.climateSettings.downfall, 0.0F, 1.0F);
/* 287 */     return FoliageColor.get($$0, $$1);
/*     */   }
/*     */   
/*     */   public float getBaseTemperature() {
/* 291 */     return this.climateSettings.temperature;
/*     */   }
/*     */   
/*     */   public BiomeSpecialEffects getSpecialEffects() {
/* 295 */     return this.specialEffects;
/*     */   }
/*     */   
/*     */   public int getWaterColor() {
/* 299 */     return this.specialEffects.getWaterColor();
/*     */   }
/*     */   
/*     */   public int getWaterFogColor() {
/* 303 */     return this.specialEffects.getWaterFogColor();
/*     */   }
/*     */   
/*     */   public Optional<AmbientParticleSettings> getAmbientParticle() {
/* 307 */     return this.specialEffects.getAmbientParticleSettings();
/*     */   }
/*     */   
/*     */   public Optional<Holder<SoundEvent>> getAmbientLoop() {
/* 311 */     return this.specialEffects.getAmbientLoopSoundEvent();
/*     */   }
/*     */   
/*     */   public Optional<AmbientMoodSettings> getAmbientMood() {
/* 315 */     return this.specialEffects.getAmbientMoodSettings();
/*     */   }
/*     */   
/*     */   public Optional<AmbientAdditionsSettings> getAmbientAdditions() {
/* 319 */     return this.specialEffects.getAmbientAdditionsSettings();
/*     */   }
/*     */   
/*     */   public Optional<Music> getBackgroundMusic() {
/* 323 */     return this.specialEffects.getBackgroundMusic(); } public static class BiomeBuilder { private boolean hasPrecipitation; @Nullable private Float temperature; private Biome.TemperatureModifier temperatureModifier; @Nullable
/*     */     private Float downfall; @Nullable
/*     */     private BiomeSpecialEffects specialEffects; @Nullable
/*     */     private MobSpawnSettings mobSpawnSettings; @Nullable
/* 327 */     private BiomeGenerationSettings generationSettings; public BiomeBuilder() { this.hasPrecipitation = true;
/*     */ 
/*     */       
/* 330 */       this.temperatureModifier = Biome.TemperatureModifier.NONE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BiomeBuilder hasPrecipitation(boolean $$0) {
/* 341 */       this.hasPrecipitation = $$0;
/* 342 */       return this;
/*     */     }
/*     */     
/*     */     public BiomeBuilder temperature(float $$0) {
/* 346 */       this.temperature = Float.valueOf($$0);
/* 347 */       return this;
/*     */     }
/*     */     
/*     */     public BiomeBuilder downfall(float $$0) {
/* 351 */       this.downfall = Float.valueOf($$0);
/* 352 */       return this;
/*     */     }
/*     */     
/*     */     public BiomeBuilder specialEffects(BiomeSpecialEffects $$0) {
/* 356 */       this.specialEffects = $$0;
/* 357 */       return this;
/*     */     }
/*     */     
/*     */     public BiomeBuilder mobSpawnSettings(MobSpawnSettings $$0) {
/* 361 */       this.mobSpawnSettings = $$0;
/* 362 */       return this;
/*     */     }
/*     */     
/*     */     public BiomeBuilder generationSettings(BiomeGenerationSettings $$0) {
/* 366 */       this.generationSettings = $$0;
/* 367 */       return this;
/*     */     }
/*     */     
/*     */     public BiomeBuilder temperatureAdjustment(Biome.TemperatureModifier $$0) {
/* 371 */       this.temperatureModifier = $$0;
/* 372 */       return this;
/*     */     }
/*     */     
/*     */     public Biome build() {
/* 376 */       if (this.temperature == null || this.downfall == null || this.specialEffects == null || this.mobSpawnSettings == null || this.generationSettings == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 382 */         throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
/*     */       }
/*     */       
/* 385 */       return new Biome(new Biome.ClimateSettings(this.hasPrecipitation, this.temperature
/* 386 */             .floatValue(), this.temperatureModifier, this.downfall.floatValue()), this.specialEffects, this.generationSettings, this.mobSpawnSettings);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 395 */       return "BiomeBuilder{\nhasPrecipitation=" + this.hasPrecipitation + ",\ntemperature=" + this.temperature + ",\ntemperatureModifier=" + this.temperatureModifier + ",\ndownfall=" + this.downfall + ",\nspecialEffects=" + this.specialEffects + ",\nmobSpawnSettings=" + this.mobSpawnSettings + ",\ngenerationSettings=" + this.generationSettings + ",\n}";
/*     */     } }
/*     */   
/*     */   private static final class ClimateSettings extends Record { private final boolean hasPrecipitation; final float temperature; final Biome.TemperatureModifier temperatureModifier; final float downfall; public static final MapCodec<ClimateSettings> CODEC;
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/Biome$ClimateSettings;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #407	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Biome$ClimateSettings;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/Biome$ClimateSettings;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #407	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/Biome$ClimateSettings;
/*     */     }
/*     */     
/* 407 */     ClimateSettings(boolean $$0, float $$1, Biome.TemperatureModifier $$2, float $$3) { this.hasPrecipitation = $$0; this.temperature = $$1; this.temperatureModifier = $$2; this.downfall = $$3; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/Biome$ClimateSettings;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #407	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/biome/Biome$ClimateSettings;
/* 407 */       //   0	8	1	$$0	Ljava/lang/Object; } public boolean hasPrecipitation() { return this.hasPrecipitation; } public float temperature() { return this.temperature; } public Biome.TemperatureModifier temperatureModifier() { return this.temperatureModifier; } public float downfall() { return this.downfall; } static {
/* 408 */       CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.BOOL.fieldOf("has_precipitation").forGetter(()), (App)Codec.FLOAT.fieldOf("temperature").forGetter(()), (App)Biome.TemperatureModifier.CODEC.optionalFieldOf("temperature_modifier", Biome.TemperatureModifier.NONE).forGetter(()), (App)Codec.FLOAT.fieldOf("downfall").forGetter(())).apply((Applicative)$$0, ClimateSettings::new));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Biome.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */