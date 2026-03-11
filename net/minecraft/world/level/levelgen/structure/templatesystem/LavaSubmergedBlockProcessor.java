/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LavaSubmergedBlockProcessor
/*    */   extends StructureProcessor
/*    */ {
/* 16 */   public static final Codec<LavaSubmergedBlockProcessor> CODEC = Codec.unit(() -> INSTANCE);
/* 17 */   public static final LavaSubmergedBlockProcessor INSTANCE = new LavaSubmergedBlockProcessor();
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/* 22 */     BlockPos $$6 = $$4.pos();
/* 23 */     boolean $$7 = $$0.getBlockState($$6).is(Blocks.LAVA);
/* 24 */     if ($$7 && !Block.isShapeFullBlock($$4.state().getShape((BlockGetter)$$0, $$6))) {
/* 25 */       return new StructureTemplate.StructureBlockInfo($$6, Blocks.LAVA.defaultBlockState(), $$4.nbt());
/*    */     }
/* 27 */     return $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureProcessorType<?> getType() {
/* 32 */     return StructureProcessorType.LAVA_SUBMERGED_BLOCK;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\LavaSubmergedBlockProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */