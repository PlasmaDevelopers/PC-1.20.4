/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ import net.minecraft.world.level.levelgen.placement.CaveSurface;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class VegetationPatchConfiguration implements FeatureConfiguration {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)TagKey.hashedCodec(Registries.BLOCK).fieldOf("replaceable").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("ground_state").forGetter(()), (App)PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(()), (App)CaveSurface.CODEC.fieldOf("surface").forGetter(()), (App)IntProvider.codec(1, 128).fieldOf("depth").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter(()), (App)Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(()), (App)IntProvider.CODEC.fieldOf("xz_radius").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter(())).apply((Applicative)$$0, VegetationPatchConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<VegetationPatchConfiguration> CODEC;
/*    */   
/*    */   public final TagKey<Block> replaceable;
/*    */   
/*    */   public final BlockStateProvider groundState;
/*    */   
/*    */   public final Holder<PlacedFeature> vegetationFeature;
/*    */   
/*    */   public final CaveSurface surface;
/*    */   
/*    */   public final IntProvider depth;
/*    */   
/*    */   public final float extraBottomBlockChance;
/*    */   
/*    */   public final int verticalRange;
/*    */   
/*    */   public final float vegetationChance;
/*    */   
/*    */   public final IntProvider xzRadius;
/*    */   public final float extraEdgeColumnChance;
/*    */   
/*    */   public VegetationPatchConfiguration(TagKey<Block> $$0, BlockStateProvider $$1, Holder<PlacedFeature> $$2, CaveSurface $$3, IntProvider $$4, float $$5, int $$6, float $$7, IntProvider $$8, float $$9) {
/* 42 */     this.replaceable = $$0;
/* 43 */     this.groundState = $$1;
/* 44 */     this.vegetationFeature = $$2;
/* 45 */     this.surface = $$3;
/* 46 */     this.depth = $$4;
/* 47 */     this.extraBottomBlockChance = $$5;
/* 48 */     this.verticalRange = $$6;
/* 49 */     this.vegetationChance = $$7;
/* 50 */     this.xzRadius = $$8;
/* 51 */     this.extraEdgeColumnChance = $$9;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\VegetationPatchConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */