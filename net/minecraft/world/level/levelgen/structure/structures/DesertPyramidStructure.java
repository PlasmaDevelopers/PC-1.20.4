/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.mojang.serialization.Codec;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.SortedArraySet;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.BrushableBlockEntity;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
/*    */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*    */ 
/*    */ public class DesertPyramidStructure extends SinglePieceStructure {
/* 27 */   public static final Codec<DesertPyramidStructure> CODEC = simpleCodec(DesertPyramidStructure::new);
/*    */   
/*    */   public DesertPyramidStructure(Structure.StructureSettings $$0) {
/* 30 */     super(DesertPyramidPiece::new, 21, 21, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void afterPlace(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, PiecesContainer $$6) {
/* 35 */     SortedArraySet<BlockPos> sortedArraySet = SortedArraySet.create(Vec3i::compareTo);
/* 36 */     for (StructurePiece $$8 : $$6.pieces()) {
/* 37 */       if ($$8 instanceof DesertPyramidPiece) { DesertPyramidPiece $$9 = (DesertPyramidPiece)$$8;
/* 38 */         sortedArraySet.addAll($$9.getPotentialSuspiciousSandWorldPositions());
/*    */         
/* 40 */         placeSuspiciousSand($$4, $$0, $$9.getRandomCollapsedRoofPos()); }
/*    */     
/*    */     } 
/*    */     
/* 44 */     ObjectArrayList<BlockPos> $$10 = new ObjectArrayList(sortedArraySet.stream().toList());
/* 45 */     RandomSource $$11 = RandomSource.create($$0.getSeed()).forkPositional().at($$6.calculateBoundingBox().getCenter());
/* 46 */     Util.shuffle((List)$$10, $$11);
/* 47 */     int $$12 = Math.min(sortedArraySet.size(), $$11.nextInt(5, 8));
/* 48 */     for (ObjectListIterator<BlockPos> objectListIterator = $$10.iterator(); objectListIterator.hasNext(); ) { BlockPos $$13 = objectListIterator.next();
/* 49 */       if ($$12 > 0) {
/* 50 */         $$12--;
/* 51 */         placeSuspiciousSand($$4, $$0, $$13); continue;
/* 52 */       }  if ($$4.isInside((Vec3i)$$13)) {
/* 53 */         $$0.setBlock($$13, Blocks.SAND.defaultBlockState(), 2);
/*    */       } }
/*    */   
/*    */   }
/*    */   
/*    */   private static void placeSuspiciousSand(BoundingBox $$0, WorldGenLevel $$1, BlockPos $$2) {
/* 59 */     if ($$0.isInside((Vec3i)$$2)) {
/* 60 */       $$1.setBlock($$2, Blocks.SUSPICIOUS_SAND.defaultBlockState(), 2);
/* 61 */       $$1.getBlockEntity($$2, BlockEntityType.BRUSHABLE_BLOCK).ifPresent($$1 -> $$1.setLootTable(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY, $$0.asLong()));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 67 */     return StructureType.DESERT_PYRAMID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\DesertPyramidStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */