/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.SharedConstants;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.LevelHeightAccessor;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.chunk.LevelChunk;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ 
/*    */ public class PlayerRespawnLogic {
/*    */   @Nullable
/*    */   protected static BlockPos getOverworldRespawnPos(ServerLevel $$0, int $$1, int $$2) {
/* 19 */     boolean $$3 = $$0.dimensionType().hasCeiling();
/*    */ 
/*    */ 
/*    */     
/* 23 */     LevelChunk $$4 = $$0.getChunk(SectionPos.blockToSectionCoord($$1), SectionPos.blockToSectionCoord($$2));
/* 24 */     int $$5 = $$3 ? $$0.getChunkSource().getGenerator().getSpawnHeight((LevelHeightAccessor)$$0) : $$4.getHeight(Heightmap.Types.MOTION_BLOCKING, $$1 & 0xF, $$2 & 0xF);
/*    */ 
/*    */     
/* 27 */     if ($$5 < $$0.getMinBuildHeight()) {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     int $$6 = $$4.getHeight(Heightmap.Types.WORLD_SURFACE, $$1 & 0xF, $$2 & 0xF);
/* 33 */     if ($$6 <= $$5 && $$6 > $$4.getHeight(Heightmap.Types.OCEAN_FLOOR, $$1 & 0xF, $$2 & 0xF)) {
/* 34 */       return null;
/*    */     }
/*    */     
/* 37 */     BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
/*    */     
/* 39 */     for (int $$8 = $$5 + 1; $$8 >= $$0.getMinBuildHeight(); $$8--) {
/* 40 */       $$7.set($$1, $$8, $$2);
/* 41 */       BlockState $$9 = $$0.getBlockState((BlockPos)$$7);
/*    */ 
/*    */       
/* 44 */       if (!$$9.getFluidState().isEmpty()) {
/*    */         break;
/*    */       }
/*    */ 
/*    */       
/* 49 */       if (Block.isFaceFull($$9.getCollisionShape((BlockGetter)$$0, (BlockPos)$$7), Direction.UP)) {
/* 50 */         return $$7.above().immutable();
/*    */       }
/*    */     } 
/* 53 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static BlockPos getSpawnPosInChunk(ServerLevel $$0, ChunkPos $$1) {
/* 58 */     if (SharedConstants.debugVoidTerrain($$1)) {
/* 59 */       return null;
/*    */     }
/*    */     
/* 62 */     for (int $$2 = $$1.getMinBlockX(); $$2 <= $$1.getMaxBlockX(); $$2++) {
/* 63 */       for (int $$3 = $$1.getMinBlockZ(); $$3 <= $$1.getMaxBlockZ(); $$3++) {
/* 64 */         BlockPos $$4 = getOverworldRespawnPos($$0, $$2, $$3);
/* 65 */         if ($$4 != null) {
/* 66 */           return $$4;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 71 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\PlayerRespawnLogic.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */