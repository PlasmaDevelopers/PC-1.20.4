/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ import com.mojang.datafixers.Products;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.LegacyRandomSource;
/*    */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*    */ 
/*    */ public abstract class NoiseBasedStateProvider extends BlockStateProvider {
/*    */   protected static <P extends NoiseBasedStateProvider> Products.P3<RecordCodecBuilder.Mu<P>, Long, NormalNoise.NoiseParameters, Float> noiseCodec(RecordCodecBuilder.Instance<P> $$0) {
/* 14 */     return $$0.group((App)Codec.LONG
/* 15 */         .fieldOf("seed").forGetter($$0 -> Long.valueOf($$0.seed)), (App)NormalNoise.NoiseParameters.DIRECT_CODEC
/* 16 */         .fieldOf("noise").forGetter($$0 -> $$0.parameters), (App)ExtraCodecs.POSITIVE_FLOAT
/* 17 */         .fieldOf("scale").forGetter($$0 -> Float.valueOf($$0.scale)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected final long seed;
/*    */   protected final NormalNoise.NoiseParameters parameters;
/*    */   protected final float scale;
/*    */   protected final NormalNoise noise;
/*    */   
/*    */   protected NoiseBasedStateProvider(long $$0, NormalNoise.NoiseParameters $$1, float $$2) {
/* 27 */     this.seed = $$0;
/* 28 */     this.parameters = $$1;
/* 29 */     this.scale = $$2;
/* 30 */     this.noise = NormalNoise.create((RandomSource)new WorldgenRandom((RandomSource)new LegacyRandomSource($$0)), $$1);
/*    */   }
/*    */   
/*    */   protected double getNoiseValue(BlockPos $$0, double $$1) {
/* 34 */     return this.noise.getValue($$0.getX() * $$1, $$0.getY() * $$1, $$0.getZ() * $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\NoiseBasedStateProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */