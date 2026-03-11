/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*    */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*    */ 
/*    */ public class BuriedTreasurePiece
/*    */   extends StructurePiece {
/*    */   public BuriedTreasurePiece(BlockPos $$0) {
/* 24 */     super(StructurePieceType.BURIED_TREASURE_PIECE, 0, new BoundingBox($$0));
/*    */   }
/*    */   
/*    */   public BuriedTreasurePiece(CompoundTag $$0) {
/* 28 */     super(StructurePieceType.BURIED_TREASURE_PIECE, $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {}
/*    */ 
/*    */   
/*    */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 37 */     int $$7 = $$0.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, this.boundingBox.minX(), this.boundingBox.minZ());
/* 38 */     BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos(this.boundingBox.minX(), $$7, this.boundingBox.minZ());
/*    */     
/* 40 */     while ($$8.getY() > $$0.getMinBuildHeight()) {
/* 41 */       BlockState $$9 = $$0.getBlockState((BlockPos)$$8);
/* 42 */       BlockState $$10 = $$0.getBlockState($$8.below());
/*    */       
/* 44 */       if ($$10 == Blocks.SANDSTONE.defaultBlockState() || $$10 == Blocks.STONE
/* 45 */         .defaultBlockState() || $$10 == Blocks.ANDESITE
/* 46 */         .defaultBlockState() || $$10 == Blocks.GRANITE
/* 47 */         .defaultBlockState() || $$10 == Blocks.DIORITE
/* 48 */         .defaultBlockState()) {
/*    */         
/* 50 */         BlockState $$11 = ($$9.isAir() || isLiquid($$9)) ? Blocks.SAND.defaultBlockState() : $$9;
/*    */         
/* 52 */         for (Direction $$12 : Direction.values()) {
/* 53 */           BlockPos $$13 = $$8.relative($$12);
/* 54 */           BlockState $$14 = $$0.getBlockState($$13);
/*    */           
/* 56 */           if ($$14.isAir() || isLiquid($$14)) {
/* 57 */             BlockPos $$15 = $$13.below();
/* 58 */             BlockState $$16 = $$0.getBlockState($$15);
/*    */             
/* 60 */             if (($$16.isAir() || isLiquid($$16)) && $$12 != Direction.UP) {
/* 61 */               $$0.setBlock($$13, $$10, 3);
/*    */             } else {
/* 63 */               $$0.setBlock($$13, $$11, 3);
/*    */             } 
/*    */           } 
/*    */         } 
/* 67 */         this.boundingBox = new BoundingBox((BlockPos)$$8);
/* 68 */         createChest((ServerLevelAccessor)$$0, $$4, $$3, (BlockPos)$$8, BuiltInLootTables.BURIED_TREASURE, null);
/*    */         
/*    */         return;
/*    */       } 
/* 72 */       $$8.move(0, -1, 0);
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean isLiquid(BlockState $$0) {
/* 77 */     return ($$0 == Blocks.WATER.defaultBlockState() || $$0 == Blocks.LAVA
/* 78 */       .defaultBlockState());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\BuriedTreasurePieces$BuriedTreasurePiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */