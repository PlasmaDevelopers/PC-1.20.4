/*     */ package net.minecraft.world.level.dimension;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.nio.file.Path;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.valueproviders.IntProvider;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ public final class DimensionType extends Record {
/*     */   private final OptionalLong fixedTime;
/*     */   private final boolean hasSkyLight;
/*     */   private final boolean hasCeiling;
/*     */   private final boolean ultraWarm;
/*     */   private final boolean natural;
/*     */   private final double coordinateScale;
/*     */   private final boolean bedWorks;
/*     */   private final boolean respawnAnchorWorks;
/*     */   private final int minY;
/*     */   
/*  27 */   public OptionalLong fixedTime() { return this.fixedTime; } private final int height; private final int logicalHeight; private final TagKey<Block> infiniburn; private final ResourceLocation effectsLocation; private final float ambientLight; private final MonsterSettings monsterSettings; public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/dimension/DimensionType;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #27	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/dimension/DimensionType; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/dimension/DimensionType;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #27	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/dimension/DimensionType; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/dimension/DimensionType;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #27	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/level/dimension/DimensionType;
/*  27 */     //   0	8	1	$$0	Ljava/lang/Object; } public boolean hasSkyLight() { return this.hasSkyLight; } public boolean hasCeiling() { return this.hasCeiling; } public boolean ultraWarm() { return this.ultraWarm; } public boolean natural() { return this.natural; } public double coordinateScale() { return this.coordinateScale; } public boolean bedWorks() { return this.bedWorks; } public boolean respawnAnchorWorks() { return this.respawnAnchorWorks; } public int minY() { return this.minY; } public int height() { return this.height; } public int logicalHeight() { return this.logicalHeight; } public TagKey<Block> infiniburn() { return this.infiniburn; } public ResourceLocation effectsLocation() { return this.effectsLocation; } public float ambientLight() { return this.ambientLight; } public MonsterSettings monsterSettings() { return this.monsterSettings; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   public static final int BITS_FOR_Y = BlockPos.PACKED_Y_LENGTH;
/*     */   
/*     */   public static final int MIN_HEIGHT = 16;
/*  47 */   public static final int Y_SIZE = (1 << BITS_FOR_Y) - 32;
/*     */   
/*  49 */   public static final int MAX_Y = (Y_SIZE >> 1) - 1;
/*     */   
/*  51 */   public static final int MIN_Y = MAX_Y - Y_SIZE + 1;
/*     */ 
/*     */   
/*  54 */   public static final int WAY_ABOVE_MAX_Y = MAX_Y << 4;
/*     */   
/*  56 */   public static final int WAY_BELOW_MIN_Y = MIN_Y << 4; public static final Codec<DimensionType> DIRECT_CODEC; private static final int MOON_PHASES = 8;
/*     */   public static final class MonsterSettings extends Record { private final boolean piglinSafe; private final boolean hasRaids; private final IntProvider monsterSpawnLightTest; private final int monsterSpawnBlockLightLimit; public static final MapCodec<MonsterSettings> CODEC;
/*  58 */     public MonsterSettings(boolean $$0, boolean $$1, IntProvider $$2, int $$3) { this.piglinSafe = $$0; this.hasRaids = $$1; this.monsterSpawnLightTest = $$2; this.monsterSpawnBlockLightLimit = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #58	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #58	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #58	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/dimension/DimensionType$MonsterSettings;
/*  58 */       //   0	8	1	$$0	Ljava/lang/Object; } public boolean piglinSafe() { return this.piglinSafe; } public boolean hasRaids() { return this.hasRaids; } public IntProvider monsterSpawnLightTest() { return this.monsterSpawnLightTest; } public int monsterSpawnBlockLightLimit() { return this.monsterSpawnBlockLightLimit; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  64 */       CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.BOOL.fieldOf("piglin_safe").forGetter(MonsterSettings::piglinSafe), (App)Codec.BOOL.fieldOf("has_raids").forGetter(MonsterSettings::hasRaids), (App)IntProvider.codec(0, 15).fieldOf("monster_spawn_light_level").forGetter(MonsterSettings::monsterSpawnLightTest), (App)Codec.intRange(0, 15).fieldOf("monster_spawn_block_light_limit").forGetter(MonsterSettings::monsterSpawnBlockLightLimit)).apply((Applicative)$$0, MonsterSettings::new));
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  72 */     DIRECT_CODEC = ExtraCodecs.catchDecoderException(RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.asOptionalLong(Codec.LONG.optionalFieldOf("fixed_time")).forGetter(DimensionType::fixedTime), (App)Codec.BOOL.fieldOf("has_skylight").forGetter(DimensionType::hasSkyLight), (App)Codec.BOOL.fieldOf("has_ceiling").forGetter(DimensionType::hasCeiling), (App)Codec.BOOL.fieldOf("ultrawarm").forGetter(DimensionType::ultraWarm), (App)Codec.BOOL.fieldOf("natural").forGetter(DimensionType::natural), (App)Codec.doubleRange(9.999999747378752E-6D, 3.0E7D).fieldOf("coordinate_scale").forGetter(DimensionType::coordinateScale), (App)Codec.BOOL.fieldOf("bed_works").forGetter(DimensionType::bedWorks), (App)Codec.BOOL.fieldOf("respawn_anchor_works").forGetter(DimensionType::respawnAnchorWorks), (App)Codec.intRange(MIN_Y, MAX_Y).fieldOf("min_y").forGetter(DimensionType::minY), (App)Codec.intRange(16, Y_SIZE).fieldOf("height").forGetter(DimensionType::height), (App)Codec.intRange(0, Y_SIZE).fieldOf("logical_height").forGetter(DimensionType::logicalHeight), (App)TagKey.hashedCodec(Registries.BLOCK).fieldOf("infiniburn").forGetter(DimensionType::infiniburn), (App)ResourceLocation.CODEC.fieldOf("effects").orElse(BuiltinDimensionTypes.OVERWORLD_EFFECTS).forGetter(DimensionType::effectsLocation), (App)Codec.FLOAT.fieldOf("ambient_light").forGetter(DimensionType::ambientLight), (App)MonsterSettings.CODEC.forGetter(DimensionType::monsterSettings)).apply((Applicative)$$0, DimensionType::new)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DimensionType(OptionalLong $$0, boolean $$1, boolean $$2, boolean $$3, boolean $$4, double $$5, boolean $$6, boolean $$7, int $$8, int $$9, int $$10, TagKey<Block> $$11, ResourceLocation $$12, float $$13, MonsterSettings $$14)
/*     */   {
/*  91 */     if ($$9 < 16) {
/*  92 */       throw new IllegalStateException("height has to be at least 16");
/*     */     }
/*     */     
/*  95 */     if ($$8 + $$9 > MAX_Y + 1) {
/*  96 */       throw new IllegalStateException("min_y + height cannot be higher than: " + MAX_Y + 1);
/*     */     }
/*     */     
/*  99 */     if ($$10 > $$9) {
/* 100 */       throw new IllegalStateException("logical_height cannot be higher than height");
/*     */     }
/*     */     
/* 103 */     if ($$9 % 16 != 0) {
/* 104 */       throw new IllegalStateException("height has to be multiple of 16");
/*     */     }
/*     */     
/* 107 */     if ($$8 % 16 != 0)
/* 108 */       throw new IllegalStateException("min_y has to be a multiple of 16");  this.fixedTime = $$0; this.hasSkyLight = $$1; this.hasCeiling = $$2; this.ultraWarm = $$3; this.natural = $$4; this.coordinateScale = $$5; this.bedWorks = $$6; this.respawnAnchorWorks = $$7; this.minY = $$8; this.height = $$9;
/*     */     this.logicalHeight = $$10;
/*     */     this.infiniburn = $$11;
/*     */     this.effectsLocation = $$12;
/*     */     this.ambientLight = $$13;
/* 113 */     this.monsterSettings = $$14; } public static final float[] MOON_BRIGHTNESS_PER_PHASE = new float[] { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static final Codec<Holder<DimensionType>> CODEC = (Codec<Holder<DimensionType>>)RegistryFileCodec.create(Registries.DIMENSION_TYPE, DIRECT_CODEC);
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static DataResult<ResourceKey<Level>> parseLegacy(Dynamic<?> $$0) {
/* 122 */     Optional<Number> $$1 = $$0.asNumber().result();
/* 123 */     if ($$1.isPresent()) {
/* 124 */       int $$2 = ((Number)$$1.get()).intValue();
/* 125 */       if ($$2 == -1)
/* 126 */         return DataResult.success(Level.NETHER); 
/* 127 */       if ($$2 == 0)
/* 128 */         return DataResult.success(Level.OVERWORLD); 
/* 129 */       if ($$2 == 1) {
/* 130 */         return DataResult.success(Level.END);
/*     */       }
/*     */     } 
/*     */     
/* 134 */     return Level.RESOURCE_KEY_CODEC.parse($$0);
/*     */   }
/*     */   
/*     */   public static double getTeleportationScale(DimensionType $$0, DimensionType $$1) {
/* 138 */     double $$2 = $$0.coordinateScale();
/* 139 */     double $$3 = $$1.coordinateScale();
/*     */     
/* 141 */     return $$2 / $$3;
/*     */   }
/*     */   
/*     */   public static Path getStorageFolder(ResourceKey<Level> $$0, Path $$1) {
/* 145 */     if ($$0 == Level.OVERWORLD) {
/* 146 */       return $$1;
/*     */     }
/* 148 */     if ($$0 == Level.END) {
/* 149 */       return $$1.resolve("DIM1");
/*     */     }
/* 151 */     if ($$0 == Level.NETHER) {
/* 152 */       return $$1.resolve("DIM-1");
/*     */     }
/* 154 */     return $$1.resolve("dimensions").resolve($$0.location().getNamespace()).resolve($$0.location().getPath());
/*     */   }
/*     */   
/*     */   public boolean hasFixedTime() {
/* 158 */     return this.fixedTime.isPresent();
/*     */   }
/*     */ 
/*     */   
/*     */   public float timeOfDay(long $$0) {
/* 163 */     double $$1 = Mth.frac(this.fixedTime.orElse($$0) / 24000.0D - 0.25D);
/*     */ 
/*     */     
/* 166 */     double $$2 = 0.5D - Math.cos($$1 * Math.PI) / 2.0D;
/*     */     
/* 168 */     return (float)($$1 * 2.0D + $$2) / 3.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int moonPhase(long $$0) {
/* 173 */     return (int)($$0 / 24000L % 8L + 8L) % 8;
/*     */   }
/*     */   
/*     */   public boolean piglinSafe() {
/* 177 */     return this.monsterSettings.piglinSafe();
/*     */   }
/*     */   
/*     */   public boolean hasRaids() {
/* 181 */     return this.monsterSettings.hasRaids();
/*     */   }
/*     */   
/*     */   public IntProvider monsterSpawnLightTest() {
/* 185 */     return this.monsterSettings.monsterSpawnLightTest();
/*     */   }
/*     */   
/*     */   public int monsterSpawnBlockLightLimit() {
/* 189 */     return this.monsterSettings.monsterSpawnBlockLightLimit();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\DimensionType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */