/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class SwampHutStructure extends Structure {
/* 12 */   public static final Codec<SwampHutStructure> CODEC = simpleCodec(SwampHutStructure::new);
/*    */   
/*    */   public SwampHutStructure(Structure.StructureSettings $$0) {
/* 15 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 20 */     return onTopOfChunkCenter($$0, Heightmap.Types.WORLD_SURFACE_WG, $$1 -> generatePieces($$1, $$0));
/*    */   }
/*    */   
/*    */   private static void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
/* 24 */     $$0.addPiece((StructurePiece)new SwampHutPiece((RandomSource)$$1.random(), $$1.chunkPos().getMinBlockX(), $$1.chunkPos().getMinBlockZ()));
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 29 */     return StructureType.SWAMP_HUT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\SwampHutStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */