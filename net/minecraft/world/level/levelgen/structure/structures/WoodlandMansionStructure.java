/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class WoodlandMansionStructure extends Structure {
/* 24 */   public static final Codec<WoodlandMansionStructure> CODEC = simpleCodec(WoodlandMansionStructure::new);
/*    */   
/*    */   public WoodlandMansionStructure(Structure.StructureSettings $$0) {
/* 27 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 32 */     Rotation $$1 = Rotation.getRandom((RandomSource)$$0.random());
/*    */ 
/*    */     
/* 35 */     BlockPos $$2 = getLowestYIn5by5BoxOffset7Blocks($$0, $$1);
/*    */ 
/*    */     
/* 38 */     if ($$2.getY() < 60) {
/* 39 */       return Optional.empty();
/*    */     }
/*    */     
/* 42 */     return Optional.of(new Structure.GenerationStub($$2, $$3 -> generatePieces($$3, $$0, $$1, $$2)));
/*    */   }
/*    */   
/*    */   private void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1, BlockPos $$2, Rotation $$3) {
/* 46 */     List<WoodlandMansionPieces.WoodlandMansionPiece> $$4 = Lists.newLinkedList();
/* 47 */     WoodlandMansionPieces.generateMansion($$1.structureTemplateManager(), $$2, $$3, $$4, (RandomSource)$$1.random());
/* 48 */     Objects.requireNonNull($$0); $$4.forEach($$0::addPiece);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void afterPlace(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, PiecesContainer $$6) {
/* 54 */     BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
/* 55 */     int $$8 = $$0.getMinBuildHeight();
/* 56 */     BoundingBox $$9 = $$6.calculateBoundingBox();
/*    */     
/* 58 */     int $$10 = $$9.minY();
/* 59 */     for (int $$11 = $$4.minX(); $$11 <= $$4.maxX(); $$11++) {
/* 60 */       for (int $$12 = $$4.minZ(); $$12 <= $$4.maxZ(); $$12++) {
/* 61 */         $$7.set($$11, $$10, $$12);
/*    */         
/* 63 */         if (!$$0.isEmptyBlock((BlockPos)$$7) && $$9.isInside((Vec3i)$$7) && $$6.isInsidePiece((BlockPos)$$7)) {
/* 64 */           for (int $$13 = $$10 - 1; $$13 > $$8; ) {
/* 65 */             $$7.setY($$13);
/* 66 */             if ($$0.isEmptyBlock((BlockPos)$$7) || $$0.getBlockState((BlockPos)$$7).liquid()) {
/* 67 */               $$0.setBlock((BlockPos)$$7, Blocks.COBBLESTONE.defaultBlockState(), 2);
/*    */               $$13--;
/*    */             } 
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 79 */     return StructureType.WOODLAND_MANSION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\WoodlandMansionStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */