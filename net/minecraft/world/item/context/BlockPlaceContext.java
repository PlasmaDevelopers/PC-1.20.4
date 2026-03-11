/*    */ package net.minecraft.world.item.context;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class BlockPlaceContext
/*    */   extends UseOnContext
/*    */ {
/*    */   private final BlockPos relativePos;
/*    */   protected boolean replaceClicked = true;
/*    */   
/*    */   public BlockPlaceContext(Player $$0, InteractionHand $$1, ItemStack $$2, BlockHitResult $$3) {
/* 21 */     this($$0.level(), $$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public BlockPlaceContext(UseOnContext $$0) {
/* 25 */     this($$0.getLevel(), $$0.getPlayer(), $$0.getHand(), $$0.getItemInHand(), $$0.getHitResult());
/*    */   }
/*    */   
/*    */   protected BlockPlaceContext(Level $$0, @Nullable Player $$1, InteractionHand $$2, ItemStack $$3, BlockHitResult $$4) {
/* 29 */     super($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 31 */     this.relativePos = $$4.getBlockPos().relative($$4.getDirection());
/* 32 */     this.replaceClicked = $$0.getBlockState($$4.getBlockPos()).canBeReplaced(this);
/*    */   }
/*    */   
/*    */   public static BlockPlaceContext at(BlockPlaceContext $$0, BlockPos $$1, Direction $$2) {
/* 36 */     return new BlockPlaceContext($$0
/* 37 */         .getLevel(), $$0
/* 38 */         .getPlayer(), $$0
/* 39 */         .getHand(), $$0
/* 40 */         .getItemInHand(), new BlockHitResult(new Vec3($$1
/*    */ 
/*    */             
/* 43 */             .getX() + 0.5D + $$2.getStepX() * 0.5D, $$1
/* 44 */             .getY() + 0.5D + $$2.getStepY() * 0.5D, $$1
/* 45 */             .getZ() + 0.5D + $$2.getStepZ() * 0.5D), $$2, $$1, false));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlockPos getClickedPos() {
/* 56 */     return this.replaceClicked ? super.getClickedPos() : this.relativePos;
/*    */   }
/*    */   
/*    */   public boolean canPlace() {
/* 60 */     return (this.replaceClicked || getLevel().getBlockState(getClickedPos()).canBeReplaced(this));
/*    */   }
/*    */   
/*    */   public boolean replacingClickedOnBlock() {
/* 64 */     return this.replaceClicked;
/*    */   }
/*    */   
/*    */   public Direction getNearestLookingDirection() {
/* 68 */     return Direction.orderedByNearest((Entity)getPlayer())[0];
/*    */   }
/*    */   
/*    */   public Direction getNearestLookingVerticalDirection() {
/* 72 */     return Direction.getFacingAxis((Entity)getPlayer(), Direction.Axis.Y);
/*    */   }
/*    */   
/*    */   public Direction[] getNearestLookingDirections() {
/* 76 */     Direction[] $$0 = Direction.orderedByNearest((Entity)getPlayer());
/*    */     
/* 78 */     if (this.replaceClicked) {
/* 79 */       return $$0;
/*    */     }
/*    */     
/* 82 */     Direction $$1 = getClickedFace();
/*    */ 
/*    */     
/* 85 */     int $$2 = 0;
/* 86 */     for (; $$2 < $$0.length && 
/* 87 */       $$0[$$2] != $$1.getOpposite(); $$2++);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 92 */     if ($$2 > 0) {
/* 93 */       System.arraycopy($$0, 0, $$0, 1, $$2);
/* 94 */       $$0[0] = $$1.getOpposite();
/*    */     } 
/* 96 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\context\BlockPlaceContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */