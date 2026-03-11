/*    */ package net.minecraft.world.level.material;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class EmptyFluid
/*    */   extends Fluid {
/*    */   public Item getBucket() {
/* 18 */     return Items.AIR;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeReplacedWith(FluidState $$0, BlockGetter $$1, BlockPos $$2, Fluid $$3, Direction $$4) {
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3 getFlow(BlockGetter $$0, BlockPos $$1, FluidState $$2) {
/* 28 */     return Vec3.ZERO;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTickDelay(LevelReader $$0) {
/* 33 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isEmpty() {
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float getExplosionResistance() {
/* 43 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getHeight(FluidState $$0, BlockGetter $$1, BlockPos $$2) {
/* 48 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getOwnHeight(FluidState $$0) {
/* 53 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockState createLegacyBlock(FluidState $$0) {
/* 58 */     return Blocks.AIR.defaultBlockState();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSource(FluidState $$0) {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount(FluidState $$0) {
/* 68 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(FluidState $$0, BlockGetter $$1, BlockPos $$2) {
/* 73 */     return Shapes.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\material\EmptyFluid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */