/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function8;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ 
/*    */ public class GeodeBlockSettings
/*    */ {
/*    */   public final BlockStateProvider fillingProvider;
/*    */   public final BlockStateProvider innerLayerProvider;
/*    */   public final BlockStateProvider alternateInnerLayerProvider;
/*    */   public final BlockStateProvider middleLayerProvider;
/*    */   
/*    */   static {
/* 24 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockStateProvider.CODEC.fieldOf("filling_provider").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("inner_layer_provider").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("alternate_inner_layer_provider").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("middle_layer_provider").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("outer_layer_provider").forGetter(()), (App)ExtraCodecs.nonEmptyList(BlockState.CODEC.listOf()).fieldOf("inner_placements").forGetter(()), (App)TagKey.hashedCodec(Registries.BLOCK).fieldOf("cannot_replace").forGetter(()), (App)TagKey.hashedCodec(Registries.BLOCK).fieldOf("invalid_blocks").forGetter(())).apply((Applicative)$$0, GeodeBlockSettings::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public final BlockStateProvider outerLayerProvider;
/*    */   
/*    */   public final List<BlockState> innerPlacements;
/*    */   
/*    */   public final TagKey<Block> cannotReplace;
/*    */   public final TagKey<Block> invalidBlocks;
/*    */   public static final Codec<GeodeBlockSettings> CODEC;
/*    */   
/*    */   public GeodeBlockSettings(BlockStateProvider $$0, BlockStateProvider $$1, BlockStateProvider $$2, BlockStateProvider $$3, BlockStateProvider $$4, List<BlockState> $$5, TagKey<Block> $$6, TagKey<Block> $$7) {
/* 37 */     this.fillingProvider = $$0;
/* 38 */     this.innerLayerProvider = $$1;
/* 39 */     this.alternateInnerLayerProvider = $$2;
/* 40 */     this.middleLayerProvider = $$3;
/* 41 */     this.outerLayerProvider = $$4;
/* 42 */     this.innerPlacements = $$5;
/* 43 */     this.cannotReplace = $$6;
/* 44 */     this.invalidBlocks = $$7;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\GeodeBlockSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */