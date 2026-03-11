/*    */ package net.minecraft.world.level.block;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class PiglinWallSkullBlock extends WallSkullBlock {
/* 15 */   public static final MapCodec<PiglinWallSkullBlock> CODEC = simpleCodec(PiglinWallSkullBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<PiglinWallSkullBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */   
/* 22 */   private static final Map<Direction, VoxelShape> AABBS = (Map<Direction, VoxelShape>)Maps.immutableEnumMap(Map.of(Direction.NORTH, 
/* 23 */         Block.box(3.0D, 4.0D, 8.0D, 13.0D, 12.0D, 16.0D), Direction.SOUTH, 
/* 24 */         Block.box(3.0D, 4.0D, 0.0D, 13.0D, 12.0D, 8.0D), Direction.EAST, 
/* 25 */         Block.box(0.0D, 4.0D, 3.0D, 8.0D, 12.0D, 13.0D), Direction.WEST, 
/* 26 */         Block.box(8.0D, 4.0D, 3.0D, 16.0D, 12.0D, 13.0D)));
/*    */ 
/*    */   
/*    */   public PiglinWallSkullBlock(BlockBehaviour.Properties $$0) {
/* 30 */     super(SkullBlock.Types.PIGLIN, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 35 */     return AABBS.get($$0.getValue((Property)FACING));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PiglinWallSkullBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */