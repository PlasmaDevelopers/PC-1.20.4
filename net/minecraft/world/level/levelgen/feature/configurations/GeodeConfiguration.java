/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.util.valueproviders.UniformInt;
/*    */ import net.minecraft.world.level.levelgen.GeodeBlockSettings;
/*    */ import net.minecraft.world.level.levelgen.GeodeCrackSettings;
/*    */ import net.minecraft.world.level.levelgen.GeodeLayerSettings;
/*    */ 
/*    */ public class GeodeConfiguration implements FeatureConfiguration {
/* 12 */   public static final Codec<Double> CHANCE_RANGE = Codec.doubleRange(0.0D, 1.0D); public static final Codec<GeodeConfiguration> CODEC;
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)GeodeBlockSettings.CODEC.fieldOf("blocks").forGetter(()), (App)GeodeLayerSettings.CODEC.fieldOf("layers").forGetter(()), (App)GeodeCrackSettings.CODEC.fieldOf("crack").forGetter(()), (App)CHANCE_RANGE.fieldOf("use_potential_placements_chance").orElse(Double.valueOf(0.35D)).forGetter(()), (App)CHANCE_RANGE.fieldOf("use_alternate_layer0_chance").orElse(Double.valueOf(0.0D)).forGetter(()), (App)Codec.BOOL.fieldOf("placements_require_layer0_alternate").orElse(Boolean.valueOf(true)).forGetter(()), (App)IntProvider.codec(1, 20).fieldOf("outer_wall_distance").orElse(UniformInt.of(4, 5)).forGetter(()), (App)IntProvider.codec(1, 20).fieldOf("distribution_points").orElse(UniformInt.of(3, 4)).forGetter(()), (App)IntProvider.codec(0, 10).fieldOf("point_offset").orElse(UniformInt.of(1, 2)).forGetter(()), (App)Codec.INT.fieldOf("min_gen_offset").orElse(Integer.valueOf(-16)).forGetter(()), (App)Codec.INT.fieldOf("max_gen_offset").orElse(Integer.valueOf(16)).forGetter(()), (App)CHANCE_RANGE.fieldOf("noise_multiplier").orElse(Double.valueOf(0.05D)).forGetter(()), (App)Codec.INT.fieldOf("invalid_blocks_threshold").forGetter(())).apply((Applicative)$$0, GeodeConfiguration::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final GeodeBlockSettings geodeBlockSettings;
/*    */ 
/*    */   
/*    */   public final GeodeLayerSettings geodeLayerSettings;
/*    */ 
/*    */   
/*    */   public final GeodeCrackSettings geodeCrackSettings;
/*    */ 
/*    */   
/*    */   public final double usePotentialPlacementsChance;
/*    */ 
/*    */   
/*    */   public final double useAlternateLayer0Chance;
/*    */ 
/*    */   
/*    */   public final boolean placementsRequireLayer0Alternate;
/*    */ 
/*    */   
/*    */   public final IntProvider outerWallDistance;
/*    */ 
/*    */   
/*    */   public final IntProvider distributionPoints;
/*    */ 
/*    */   
/*    */   public final IntProvider pointOffset;
/*    */   
/*    */   public final int minGenOffset;
/*    */   
/*    */   public final int maxGenOffset;
/*    */   
/*    */   public final double noiseMultiplier;
/*    */   
/*    */   public final int invalidBlocksThreshold;
/*    */ 
/*    */   
/*    */   public GeodeConfiguration(GeodeBlockSettings $$0, GeodeLayerSettings $$1, GeodeCrackSettings $$2, double $$3, double $$4, boolean $$5, IntProvider $$6, IntProvider $$7, IntProvider $$8, int $$9, int $$10, double $$11, int $$12) {
/* 55 */     this.geodeBlockSettings = $$0;
/* 56 */     this.geodeLayerSettings = $$1;
/* 57 */     this.geodeCrackSettings = $$2;
/* 58 */     this.usePotentialPlacementsChance = $$3;
/* 59 */     this.useAlternateLayer0Chance = $$4;
/* 60 */     this.placementsRequireLayer0Alternate = $$5;
/* 61 */     this.outerWallDistance = $$6;
/* 62 */     this.distributionPoints = $$7;
/* 63 */     this.pointOffset = $$8;
/* 64 */     this.minGenOffset = $$9;
/* 65 */     this.maxGenOffset = $$10;
/* 66 */     this.noiseMultiplier = $$11;
/* 67 */     this.invalidBlocksThreshold = $$12;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\GeodeConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */