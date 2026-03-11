/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class TheEndPortalBlockEntity extends BlockEntity {
/*    */   protected TheEndPortalBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
/*  9 */     super($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public TheEndPortalBlockEntity(BlockPos $$0, BlockState $$1) {
/* 13 */     this(BlockEntityType.END_PORTAL, $$0, $$1);
/*    */   }
/*    */   
/*    */   public boolean shouldRenderFace(Direction $$0) {
/* 17 */     return ($$0.getAxis() == Direction.Axis.Y);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\TheEndPortalBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */