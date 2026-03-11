/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ 
/*    */ public class NoiseThresholdCountPlacement
/*    */   extends RepeatingPlacement {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.DOUBLE.fieldOf("noise_level").forGetter(()), (App)Codec.INT.fieldOf("below_noise").forGetter(()), (App)Codec.INT.fieldOf("above_noise").forGetter(())).apply((Applicative)$$0, NoiseThresholdCountPlacement::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<NoiseThresholdCountPlacement> CODEC;
/*    */   
/*    */   private final double noiseLevel;
/*    */   
/*    */   private final int belowNoise;
/*    */   private final int aboveNoise;
/*    */   
/*    */   private NoiseThresholdCountPlacement(double $$0, int $$1, int $$2) {
/* 27 */     this.noiseLevel = $$0;
/* 28 */     this.belowNoise = $$1;
/* 29 */     this.aboveNoise = $$2;
/*    */   }
/*    */   
/*    */   public static NoiseThresholdCountPlacement of(double $$0, int $$1, int $$2) {
/* 33 */     return new NoiseThresholdCountPlacement($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int count(RandomSource $$0, BlockPos $$1) {
/* 39 */     double $$2 = Biome.BIOME_INFO_NOISE.getValue($$1.getX() / 200.0D, $$1.getZ() / 200.0D, false);
/* 40 */     return ($$2 < this.noiseLevel) ? this.belowNoise : this.aboveNoise;
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 45 */     return PlacementModifierType.NOISE_THRESHOLD_COUNT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\NoiseThresholdCountPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */