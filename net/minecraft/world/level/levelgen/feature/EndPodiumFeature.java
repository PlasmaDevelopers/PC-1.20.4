/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.level.LevelWriter;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.WallTorchBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
/*    */ 
/*    */ public class EndPodiumFeature extends Feature<NoneFeatureConfiguration> {
/*    */   public static final int PODIUM_RADIUS = 4;
/*    */   public static final int PODIUM_PILLAR_HEIGHT = 4;
/* 16 */   private static final BlockPos END_PODIUM_LOCATION = BlockPos.ZERO; public static final int RIM_RADIUS = 1; public static final float CORNER_ROUNDING = 0.5F; private final boolean active;
/*    */   
/*    */   public static BlockPos getLocation(BlockPos $$0) {
/* 19 */     return END_PODIUM_LOCATION.offset((Vec3i)$$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EndPodiumFeature(boolean $$0) {
/* 25 */     super(NoneFeatureConfiguration.CODEC);
/* 26 */     this.active = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> $$0) {
/* 31 */     BlockPos $$1 = $$0.origin();
/* 32 */     WorldGenLevel $$2 = $$0.level();
/* 33 */     for (BlockPos $$3 : BlockPos.betweenClosed(new BlockPos($$1.getX() - 4, $$1.getY() - 1, $$1.getZ() - 4), new BlockPos($$1.getX() + 4, $$1.getY() + 32, $$1.getZ() + 4))) {
/* 34 */       boolean $$4 = $$3.closerThan((Vec3i)$$1, 2.5D);
/*    */       
/* 36 */       if ($$4 || $$3.closerThan((Vec3i)$$1, 3.5D)) {
/* 37 */         if ($$3.getY() < $$1.getY()) {
/* 38 */           if ($$4) {
/*    */             
/* 40 */             setBlock((LevelWriter)$$2, $$3, Blocks.BEDROCK.defaultBlockState()); continue;
/* 41 */           }  if ($$3.getY() < $$1.getY())
/*    */           {
/* 43 */             setBlock((LevelWriter)$$2, $$3, Blocks.END_STONE.defaultBlockState()); }  continue;
/*    */         } 
/* 45 */         if ($$3.getY() > $$1.getY()) {
/*    */           
/* 47 */           setBlock((LevelWriter)$$2, $$3, Blocks.AIR.defaultBlockState()); continue;
/* 48 */         }  if (!$$4) {
/*    */           
/* 50 */           setBlock((LevelWriter)$$2, $$3, Blocks.BEDROCK.defaultBlockState()); continue;
/* 51 */         }  if (this.active) {
/*    */           
/* 53 */           setBlock((LevelWriter)$$2, new BlockPos((Vec3i)$$3), Blocks.END_PORTAL.defaultBlockState()); continue;
/*    */         } 
/* 55 */         setBlock((LevelWriter)$$2, new BlockPos((Vec3i)$$3), Blocks.AIR.defaultBlockState());
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 61 */     for (int $$5 = 0; $$5 < 4; $$5++) {
/* 62 */       setBlock((LevelWriter)$$2, $$1.above($$5), Blocks.BEDROCK.defaultBlockState());
/*    */     }
/*    */     
/* 65 */     BlockPos $$6 = $$1.above(2);
/* 66 */     for (Direction $$7 : Direction.Plane.HORIZONTAL) {
/* 67 */       setBlock((LevelWriter)$$2, $$6.relative($$7), (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)$$7));
/*    */     }
/*    */     
/* 70 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\EndPodiumFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */