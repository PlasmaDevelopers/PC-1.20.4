/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class BuriedTreasureStructure extends Structure {
/* 13 */   public static final Codec<BuriedTreasureStructure> CODEC = simpleCodec(BuriedTreasureStructure::new);
/*    */   
/*    */   public BuriedTreasureStructure(Structure.StructureSettings $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 21 */     return onTopOfChunkCenter($$0, Heightmap.Types.OCEAN_FLOOR_WG, $$1 -> generatePieces($$1, $$0));
/*    */   }
/*    */   
/*    */   private static void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
/* 25 */     BlockPos $$2 = new BlockPos($$1.chunkPos().getBlockX(9), 90, $$1.chunkPos().getBlockZ(9));
/* 26 */     $$0.addPiece(new BuriedTreasurePieces.BuriedTreasurePiece($$2));
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 31 */     return StructureType.BURIED_TREASURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\BuriedTreasureStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */