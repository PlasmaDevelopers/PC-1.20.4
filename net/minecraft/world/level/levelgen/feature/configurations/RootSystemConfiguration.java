/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class RootSystemConfiguration implements FeatureConfiguration {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)PlacedFeature.CODEC.fieldOf("feature").forGetter(()), (App)Codec.intRange(1, 64).fieldOf("required_vertical_space_for_tree").forGetter(()), (App)Codec.intRange(1, 64).fieldOf("root_radius").forGetter(()), (App)TagKey.hashedCodec(Registries.BLOCK).fieldOf("root_replaceable").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("root_state_provider").forGetter(()), (App)Codec.intRange(1, 256).fieldOf("root_placement_attempts").forGetter(()), (App)Codec.intRange(1, 4096).fieldOf("root_column_max_height").forGetter(()), (App)Codec.intRange(1, 64).fieldOf("hanging_root_radius").forGetter(()), (App)Codec.intRange(0, 16).fieldOf("hanging_roots_vertical_span").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("hanging_root_state_provider").forGetter(()), (App)Codec.intRange(1, 256).fieldOf("hanging_root_placement_attempts").forGetter(()), (App)Codec.intRange(1, 64).fieldOf("allowed_vertical_water_for_tree").forGetter(()), (App)BlockPredicate.CODEC.fieldOf("allowed_tree_position").forGetter(())).apply((Applicative)$$0, RootSystemConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<RootSystemConfiguration> CODEC;
/*    */   
/*    */   public final Holder<PlacedFeature> treeFeature;
/*    */   
/*    */   public final int requiredVerticalSpaceForTree;
/*    */   
/*    */   public final int rootRadius;
/*    */   
/*    */   public final TagKey<Block> rootReplaceable;
/*    */   
/*    */   public final BlockStateProvider rootStateProvider;
/*    */   
/*    */   public final int rootPlacementAttempts;
/*    */   
/*    */   public final int rootColumnMaxHeight;
/*    */   
/*    */   public final int hangingRootRadius;
/*    */   
/*    */   public final int hangingRootsVerticalSpan;
/*    */   
/*    */   public final BlockStateProvider hangingRootStateProvider;
/*    */   
/*    */   public final int hangingRootPlacementAttempts;
/*    */   public final int allowedVerticalWaterForTree;
/*    */   public final BlockPredicate allowedTreePosition;
/*    */   
/*    */   public RootSystemConfiguration(Holder<PlacedFeature> $$0, int $$1, int $$2, TagKey<Block> $$3, BlockStateProvider $$4, int $$5, int $$6, int $$7, int $$8, BlockStateProvider $$9, int $$10, int $$11, BlockPredicate $$12) {
/* 45 */     this.treeFeature = $$0;
/* 46 */     this.requiredVerticalSpaceForTree = $$1;
/* 47 */     this.rootRadius = $$2;
/* 48 */     this.rootReplaceable = $$3;
/* 49 */     this.rootStateProvider = $$4;
/* 50 */     this.rootPlacementAttempts = $$5;
/* 51 */     this.rootColumnMaxHeight = $$6;
/* 52 */     this.hangingRootRadius = $$7;
/* 53 */     this.hangingRootsVerticalSpan = $$8;
/* 54 */     this.hangingRootStateProvider = $$9;
/* 55 */     this.hangingRootPlacementAttempts = $$10;
/* 56 */     this.allowedVerticalWaterForTree = $$11;
/* 57 */     this.allowedTreePosition = $$12;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\RootSystemConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */