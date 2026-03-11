/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.levelgen.feature.Feature;
/*    */ 
/*    */ 
/*    */ public class ProtectedBlockProcessor
/*    */   extends StructureProcessor
/*    */ {
/*    */   public final TagKey<Block> cannotReplace;
/*    */   public static final Codec<ProtectedBlockProcessor> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = TagKey.hashedCodec(Registries.BLOCK).xmap(ProtectedBlockProcessor::new, $$0 -> $$0.cannotReplace);
/*    */   }
/*    */   public ProtectedBlockProcessor(TagKey<Block> $$0) {
/* 24 */     this.cannotReplace = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/* 30 */     if (Feature.isReplaceable(this.cannotReplace).test($$0.getBlockState($$4.pos()))) {
/* 31 */       return $$4;
/*    */     }
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureProcessorType<?> getType() {
/* 38 */     return StructureProcessorType.PROTECTED_BLOCKS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\ProtectedBlockProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */