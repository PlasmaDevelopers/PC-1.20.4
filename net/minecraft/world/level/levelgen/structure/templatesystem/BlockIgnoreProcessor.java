/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BlockIgnoreProcessor
/*    */   extends StructureProcessor
/*    */ {
/*    */   public static final Codec<BlockIgnoreProcessor> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = BlockState.CODEC.xmap(BlockBehaviour.BlockStateBase::getBlock, Block::defaultBlockState).listOf().fieldOf("blocks").xmap(BlockIgnoreProcessor::new, $$0 -> $$0.toIgnore).codec();
/*    */   }
/* 23 */   public static final BlockIgnoreProcessor STRUCTURE_BLOCK = new BlockIgnoreProcessor((List<Block>)ImmutableList.of(Blocks.STRUCTURE_BLOCK));
/* 24 */   public static final BlockIgnoreProcessor AIR = new BlockIgnoreProcessor((List<Block>)ImmutableList.of(Blocks.AIR));
/* 25 */   public static final BlockIgnoreProcessor STRUCTURE_AND_AIR = new BlockIgnoreProcessor((List<Block>)ImmutableList.of(Blocks.AIR, Blocks.STRUCTURE_BLOCK));
/*    */   
/*    */   private final ImmutableList<Block> toIgnore;
/*    */   
/*    */   public BlockIgnoreProcessor(List<Block> $$0) {
/* 30 */     this.toIgnore = ImmutableList.copyOf($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/* 36 */     if (this.toIgnore.contains($$4.state().getBlock())) {
/* 37 */       return null;
/*    */     }
/* 39 */     return $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureProcessorType<?> getType() {
/* 44 */     return StructureProcessorType.BLOCK_IGNORE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\BlockIgnoreProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */