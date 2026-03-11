/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class ReplaceBlockConfiguration implements FeatureConfiguration {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.list(OreConfiguration.TargetBlockState.CODEC).fieldOf("targets").forGetter(())).apply((Applicative)$$0, ReplaceBlockConfiguration::new));
/*    */   }
/*    */   
/*    */   public static final Codec<ReplaceBlockConfiguration> CODEC;
/*    */   public final List<OreConfiguration.TargetBlockState> targetStates;
/*    */   
/*    */   public ReplaceBlockConfiguration(BlockState $$0, BlockState $$1) {
/* 19 */     this((List<OreConfiguration.TargetBlockState>)ImmutableList.of(OreConfiguration.target((RuleTest)new BlockStateMatchTest($$0), $$1)));
/*    */   }
/*    */   
/*    */   public ReplaceBlockConfiguration(List<OreConfiguration.TargetBlockState> $$0) {
/* 23 */     this.targetStates = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\ReplaceBlockConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */