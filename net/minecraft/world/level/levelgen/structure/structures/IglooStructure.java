/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class IglooStructure extends Structure {
/* 16 */   public static final Codec<IglooStructure> CODEC = simpleCodec(IglooStructure::new);
/*    */   
/*    */   public IglooStructure(Structure.StructureSettings $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 24 */     return onTopOfChunkCenter($$0, Heightmap.Types.WORLD_SURFACE_WG, $$1 -> generatePieces($$1, $$0));
/*    */   }
/*    */   
/*    */   private void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
/* 28 */     ChunkPos $$2 = $$1.chunkPos();
/* 29 */     WorldgenRandom $$3 = $$1.random();
/*    */     
/* 31 */     BlockPos $$4 = new BlockPos($$2.getMinBlockX(), 90, $$2.getMinBlockZ());
/* 32 */     Rotation $$5 = Rotation.getRandom((RandomSource)$$3);
/* 33 */     IglooPieces.addPieces($$1.structureTemplateManager(), $$4, $$5, (StructurePieceAccessor)$$0, (RandomSource)$$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 38 */     return StructureType.IGLOO;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\IglooStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */