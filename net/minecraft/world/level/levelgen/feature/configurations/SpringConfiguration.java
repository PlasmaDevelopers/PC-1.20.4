/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function5;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public class SpringConfiguration implements FeatureConfiguration {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)FluidState.CODEC.fieldOf("state").forGetter(()), (App)Codec.BOOL.fieldOf("requires_block_below").orElse(Boolean.valueOf(true)).forGetter(()), (App)Codec.INT.fieldOf("rock_count").orElse(Integer.valueOf(4)).forGetter(()), (App)Codec.INT.fieldOf("hole_count").orElse(Integer.valueOf(1)).forGetter(()), (App)RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_blocks").forGetter(())).apply((Applicative)$$0, SpringConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<SpringConfiguration> CODEC;
/*    */   
/*    */   public final FluidState state;
/*    */   
/*    */   public final boolean requiresBlockBelow;
/*    */   
/*    */   public final int rockCount;
/*    */   public final int holeCount;
/*    */   public final HolderSet<Block> validBlocks;
/*    */   
/*    */   public SpringConfiguration(FluidState $$0, boolean $$1, int $$2, int $$3, HolderSet<Block> $$4) {
/* 27 */     this.state = $$0;
/* 28 */     this.requiresBlockBelow = $$1;
/* 29 */     this.rockCount = $$2;
/* 30 */     this.holeCount = $$3;
/* 31 */     this.validBlocks = $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\SpringConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */