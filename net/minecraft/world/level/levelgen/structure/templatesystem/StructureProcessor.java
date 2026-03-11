/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class StructureProcessor
/*    */ {
/*    */   @Nullable
/*    */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/* 21 */     return $$4;
/*    */   }
/*    */   
/*    */   protected abstract StructureProcessorType<?> getType();
/*    */   
/*    */   public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor $$0, BlockPos $$1, BlockPos $$2, List<StructureTemplate.StructureBlockInfo> $$3, List<StructureTemplate.StructureBlockInfo> $$4, StructurePlaceSettings $$5) {
/* 27 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\StructureProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */