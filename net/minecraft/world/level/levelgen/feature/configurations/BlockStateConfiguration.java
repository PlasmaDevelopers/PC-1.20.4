/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ 
/*    */ public class BlockStateConfiguration implements FeatureConfiguration {
/*    */   public static final Codec<BlockStateConfiguration> CODEC;
/*    */   
/*    */   static {
/*  7 */     CODEC = BlockState.CODEC.fieldOf("state").xmap(BlockStateConfiguration::new, $$0 -> $$0.state).codec();
/*    */   }
/*    */   public final BlockState state;
/*    */   
/*    */   public BlockStateConfiguration(BlockState $$0) {
/* 12 */     this.state = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\BlockStateConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */