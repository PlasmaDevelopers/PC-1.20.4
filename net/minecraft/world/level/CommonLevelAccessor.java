/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ 
/*    */ public interface CommonLevelAccessor
/*    */   extends EntityGetter, LevelReader, LevelSimulatedRW
/*    */ {
/*    */   default <T extends net.minecraft.world.level.block.entity.BlockEntity> Optional<T> getBlockEntity(BlockPos $$0, BlockEntityType<T> $$1) {
/* 18 */     return super.getBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   default List<VoxelShape> getEntityCollisions(@Nullable Entity $$0, AABB $$1) {
/* 23 */     return super.getEntityCollisions($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   default boolean isUnobstructed(@Nullable Entity $$0, VoxelShape $$1) {
/* 28 */     return super.isUnobstructed($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   default BlockPos getHeightmapPos(Heightmap.Types $$0, BlockPos $$1) {
/* 33 */     return super.getHeightmapPos($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\CommonLevelAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */