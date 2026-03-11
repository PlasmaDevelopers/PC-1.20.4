/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.SlabBlock;
/*    */ import net.minecraft.world.level.block.StairBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class BlackstoneReplaceProcessor
/*    */   extends StructureProcessor
/*    */ {
/* 20 */   public static final Codec<BlackstoneReplaceProcessor> CODEC = Codec.unit(() -> INSTANCE);
/*    */   
/* 22 */   public static final BlackstoneReplaceProcessor INSTANCE = new BlackstoneReplaceProcessor();
/*    */   
/*    */   private final Map<Block, Block> replacements;
/*    */   
/*    */   private BlackstoneReplaceProcessor() {
/* 27 */     this.replacements = (Map<Block, Block>)Util.make(Maps.newHashMap(), $$0 -> {
/*    */           $$0.put(Blocks.COBBLESTONE, Blocks.BLACKSTONE);
/*    */           $$0.put(Blocks.MOSSY_COBBLESTONE, Blocks.BLACKSTONE);
/*    */           $$0.put(Blocks.STONE, Blocks.POLISHED_BLACKSTONE);
/*    */           $$0.put(Blocks.STONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS);
/*    */           $$0.put(Blocks.MOSSY_STONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS);
/*    */           $$0.put(Blocks.COBBLESTONE_STAIRS, Blocks.BLACKSTONE_STAIRS);
/*    */           $$0.put(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.BLACKSTONE_STAIRS);
/*    */           $$0.put(Blocks.STONE_STAIRS, Blocks.POLISHED_BLACKSTONE_STAIRS);
/*    */           $$0.put(Blocks.STONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
/*    */           $$0.put(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
/*    */           $$0.put(Blocks.COBBLESTONE_SLAB, Blocks.BLACKSTONE_SLAB);
/*    */           $$0.put(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.BLACKSTONE_SLAB);
/*    */           $$0.put(Blocks.SMOOTH_STONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB);
/*    */           $$0.put(Blocks.STONE_SLAB, Blocks.POLISHED_BLACKSTONE_SLAB);
/*    */           $$0.put(Blocks.STONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
/*    */           $$0.put(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
/*    */           $$0.put(Blocks.STONE_BRICK_WALL, Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
/*    */           $$0.put(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
/*    */           $$0.put(Blocks.COBBLESTONE_WALL, Blocks.BLACKSTONE_WALL);
/*    */           $$0.put(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.BLACKSTONE_WALL);
/*    */           $$0.put(Blocks.CHISELED_STONE_BRICKS, Blocks.CHISELED_POLISHED_BLACKSTONE);
/*    */           $$0.put(Blocks.CRACKED_STONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
/*    */           $$0.put(Blocks.IRON_BARS, Blocks.CHAIN);
/*    */         });
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
/*    */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/* 67 */     Block $$6 = this.replacements.get($$4.state().getBlock());
/* 68 */     if ($$6 == null) {
/* 69 */       return $$4;
/*    */     }
/* 71 */     BlockState $$7 = $$4.state();
/* 72 */     BlockState $$8 = $$6.defaultBlockState();
/* 73 */     if ($$7.hasProperty((Property)StairBlock.FACING)) {
/* 74 */       $$8 = (BlockState)$$8.setValue((Property)StairBlock.FACING, $$7.getValue((Property)StairBlock.FACING));
/*    */     }
/* 76 */     if ($$7.hasProperty((Property)StairBlock.HALF)) {
/* 77 */       $$8 = (BlockState)$$8.setValue((Property)StairBlock.HALF, $$7.getValue((Property)StairBlock.HALF));
/*    */     }
/* 79 */     if ($$7.hasProperty((Property)SlabBlock.TYPE)) {
/* 80 */       $$8 = (BlockState)$$8.setValue((Property)SlabBlock.TYPE, $$7.getValue((Property)SlabBlock.TYPE));
/*    */     }
/* 82 */     return new StructureTemplate.StructureBlockInfo($$4.pos(), $$8, $$4.nbt());
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureProcessorType<?> getType() {
/* 87 */     return StructureProcessorType.BLACKSTONE_REPLACE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\BlackstoneReplaceProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */