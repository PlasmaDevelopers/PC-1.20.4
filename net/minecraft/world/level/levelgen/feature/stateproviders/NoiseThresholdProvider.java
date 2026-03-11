/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function8;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.synth.NormalNoise;
/*    */ 
/*    */ public class NoiseThresholdProvider extends NoiseBasedStateProvider {
/*    */   public static final Codec<NoiseThresholdProvider> CODEC;
/*    */   private final float threshold;
/*    */   private final float highChance;
/*    */   private final BlockState defaultState;
/*    */   private final List<BlockState> lowStates;
/*    */   private final List<BlockState> highStates;
/*    */   
/*    */   static {
/* 24 */     CODEC = RecordCodecBuilder.create($$0 -> noiseCodec($$0).and($$0.group((App)Codec.floatRange(-1.0F, 1.0F).fieldOf("threshold").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("high_chance").forGetter(()), (App)BlockState.CODEC.fieldOf("default_state").forGetter(()), (App)Codec.list(BlockState.CODEC).fieldOf("low_states").forGetter(()), (App)Codec.list(BlockState.CODEC).fieldOf("high_states").forGetter(()))).apply((Applicative)$$0, NoiseThresholdProvider::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NoiseThresholdProvider(long $$0, NormalNoise.NoiseParameters $$1, float $$2, float $$3, float $$4, BlockState $$5, List<BlockState> $$6, List<BlockState> $$7) {
/* 41 */     super($$0, $$1, $$2);
/* 42 */     this.threshold = $$3;
/* 43 */     this.highChance = $$4;
/* 44 */     this.defaultState = $$5;
/* 45 */     this.lowStates = $$6;
/* 46 */     this.highStates = $$7;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateProviderType<?> type() {
/* 51 */     return BlockStateProviderType.NOISE_THRESHOLD_PROVIDER;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockState getState(RandomSource $$0, BlockPos $$1) {
/* 57 */     double $$2 = getNoiseValue($$1, this.scale);
/* 58 */     if ($$2 < this.threshold) {
/* 59 */       return (BlockState)Util.getRandom(this.lowStates, $$0);
/*    */     }
/*    */     
/* 62 */     if ($$0.nextFloat() < this.highChance) {
/* 63 */       return (BlockState)Util.getRandom(this.highStates, $$0);
/*    */     }
/*    */     
/* 66 */     return this.defaultState;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\NoiseThresholdProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */