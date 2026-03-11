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
/*    */ public class NoiseBasedCountPlacement
/*    */   extends RepeatingPlacement {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("noise_to_count_ratio").forGetter(()), (App)Codec.DOUBLE.fieldOf("noise_factor").forGetter(()), (App)Codec.DOUBLE.fieldOf("noise_offset").orElse(Double.valueOf(0.0D)).forGetter(())).apply((Applicative)$$0, NoiseBasedCountPlacement::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<NoiseBasedCountPlacement> CODEC;
/*    */   
/*    */   private final int noiseToCountRatio;
/*    */   
/*    */   private final double noiseFactor;
/*    */   
/*    */   private final double noiseOffset;
/*    */   
/*    */   private NoiseBasedCountPlacement(int $$0, double $$1, double $$2) {
/* 28 */     this.noiseToCountRatio = $$0;
/* 29 */     this.noiseFactor = $$1;
/* 30 */     this.noiseOffset = $$2;
/*    */   }
/*    */   
/*    */   public static NoiseBasedCountPlacement of(int $$0, double $$1, double $$2) {
/* 34 */     return new NoiseBasedCountPlacement($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int count(RandomSource $$0, BlockPos $$1) {
/* 39 */     double $$2 = Biome.BIOME_INFO_NOISE.getValue($$1.getX() / this.noiseFactor, $$1.getZ() / this.noiseFactor, false);
/* 40 */     return (int)Math.ceil(($$2 + this.noiseOffset) * this.noiseToCountRatio);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 45 */     return PlacementModifierType.NOISE_BASED_COUNT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\NoiseBasedCountPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */