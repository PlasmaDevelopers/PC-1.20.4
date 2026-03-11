/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ 
/*    */ public class BlockPileConfiguration implements FeatureConfiguration {
/*    */   public static final Codec<BlockPileConfiguration> CODEC;
/*    */   
/*    */   static {
/*  7 */     CODEC = BlockStateProvider.CODEC.fieldOf("state_provider").xmap(BlockPileConfiguration::new, $$0 -> $$0.stateProvider).codec();
/*    */   }
/*    */   public final BlockStateProvider stateProvider;
/*    */   
/*    */   public BlockPileConfiguration(BlockStateProvider $$0) {
/* 12 */     this.stateProvider = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\BlockPileConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */