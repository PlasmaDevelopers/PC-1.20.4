/*    */ package net.minecraft.world.phys;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ public class BlockHitResult extends HitResult {
/*    */   private final Direction direction;
/*    */   private final BlockPos blockPos;
/*    */   private final boolean miss;
/*    */   private final boolean inside;
/*    */   
/*    */   public static BlockHitResult miss(Vec3 $$0, Direction $$1, BlockPos $$2) {
/* 13 */     return new BlockHitResult(true, $$0, $$1, $$2, false);
/*    */   }
/*    */   
/*    */   public BlockHitResult(Vec3 $$0, Direction $$1, BlockPos $$2, boolean $$3) {
/* 17 */     this(false, $$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   private BlockHitResult(boolean $$0, Vec3 $$1, Direction $$2, BlockPos $$3, boolean $$4) {
/* 21 */     super($$1);
/*    */     
/* 23 */     this.miss = $$0;
/* 24 */     this.direction = $$2;
/* 25 */     this.blockPos = $$3;
/* 26 */     this.inside = $$4;
/*    */   }
/*    */   
/*    */   public BlockHitResult withDirection(Direction $$0) {
/* 30 */     return new BlockHitResult(this.miss, this.location, $$0, this.blockPos, this.inside);
/*    */   }
/*    */   
/*    */   public BlockHitResult withPosition(BlockPos $$0) {
/* 34 */     return new BlockHitResult(this.miss, this.location, this.direction, $$0, this.inside);
/*    */   }
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 38 */     return this.blockPos;
/*    */   }
/*    */   
/*    */   public Direction getDirection() {
/* 42 */     return this.direction;
/*    */   }
/*    */ 
/*    */   
/*    */   public HitResult.Type getType() {
/* 47 */     return this.miss ? HitResult.Type.MISS : HitResult.Type.BLOCK;
/*    */   }
/*    */   
/*    */   public boolean isInside() {
/* 51 */     return this.inside;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\BlockHitResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */