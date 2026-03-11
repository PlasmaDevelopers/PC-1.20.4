/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
/*    */ 
/*    */ public class OreConfiguration implements FeatureConfiguration {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.list(TargetBlockState.CODEC).fieldOf("targets").forGetter(()), (App)Codec.intRange(0, 64).fieldOf("size").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("discard_chance_on_air_exposure").forGetter(())).apply((Applicative)$$0, OreConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<OreConfiguration> CODEC;
/*    */   
/*    */   public final List<TargetBlockState> targetStates;
/*    */   public final int size;
/*    */   public final float discardChanceOnAirExposure;
/*    */   
/*    */   public OreConfiguration(List<TargetBlockState> $$0, int $$1, float $$2) {
/* 23 */     this.size = $$1;
/* 24 */     this.targetStates = $$0;
/* 25 */     this.discardChanceOnAirExposure = $$2;
/*    */   }
/*    */   
/*    */   public OreConfiguration(List<TargetBlockState> $$0, int $$1) {
/* 29 */     this($$0, $$1, 0.0F);
/*    */   }
/*    */   
/*    */   public OreConfiguration(RuleTest $$0, BlockState $$1, int $$2, float $$3) {
/* 33 */     this((List<TargetBlockState>)ImmutableList.of(new TargetBlockState($$0, $$1)), $$2, $$3);
/*    */   }
/*    */   
/*    */   public OreConfiguration(RuleTest $$0, BlockState $$1, int $$2) {
/* 37 */     this((List<TargetBlockState>)ImmutableList.of(new TargetBlockState($$0, $$1)), $$2, 0.0F);
/*    */   }
/*    */   
/*    */   public static TargetBlockState target(RuleTest $$0, BlockState $$1) {
/* 41 */     return new TargetBlockState($$0, $$1);
/*    */   }
/*    */   public static class TargetBlockState { public static final Codec<TargetBlockState> CODEC;
/*    */     static {
/* 45 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RuleTest.CODEC.fieldOf("target").forGetter(()), (App)BlockState.CODEC.fieldOf("state").forGetter(())).apply((Applicative)$$0, TargetBlockState::new));
/*    */     }
/*    */ 
/*    */     
/*    */     public final RuleTest target;
/*    */     
/*    */     public final BlockState state;
/*    */     
/*    */     TargetBlockState(RuleTest $$0, BlockState $$1) {
/* 54 */       this.target = $$0;
/* 55 */       this.state = $$1;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\OreConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */