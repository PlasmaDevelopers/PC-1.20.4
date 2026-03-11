/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Codec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.arguments.blocks.BlockStateParser;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ public class JigsawReplacementProcessor
/*    */   extends StructureProcessor
/*    */ {
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 20 */   public static final Codec<JigsawReplacementProcessor> CODEC = Codec.unit(() -> INSTANCE);
/*    */   
/* 22 */   public static final JigsawReplacementProcessor INSTANCE = new JigsawReplacementProcessor();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/* 30 */     BlockState $$6 = $$4.state();
/* 31 */     if ($$6.is(Blocks.JIGSAW)) {
/*    */       BlockState $$9;
/*    */ 
/*    */       
/* 35 */       if ($$4.nbt() == null) {
/* 36 */         LOGGER.warn("Jigsaw block at {} is missing nbt, will not replace", $$1);
/* 37 */         return $$4;
/*    */       } 
/*    */       
/* 40 */       String $$7 = $$4.nbt().getString("final_state");
/*    */       
/*    */       try {
/* 43 */         BlockStateParser.BlockResult $$8 = BlockStateParser.parseForBlock($$0.holderLookup(Registries.BLOCK), $$7, true);
/* 44 */         $$9 = $$8.blockState();
/* 45 */       } catch (CommandSyntaxException $$10) {
/* 46 */         throw new RuntimeException($$10);
/*    */       } 
/* 48 */       if ($$9.is(Blocks.STRUCTURE_VOID)) {
/* 49 */         return null;
/*    */       }
/* 51 */       return new StructureTemplate.StructureBlockInfo($$4.pos(), $$9, null);
/*    */     } 
/*    */     return $$4;
/*    */   }
/*    */   protected StructureProcessorType<?> getType() {
/* 56 */     return StructureProcessorType.JIGSAW_REPLACEMENT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\JigsawReplacementProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */