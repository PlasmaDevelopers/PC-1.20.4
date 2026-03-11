/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public interface CollisionContext {
/*    */   static CollisionContext empty() {
/* 10 */     return EntityCollisionContext.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   static CollisionContext of(Entity $$0) {
/* 15 */     return new EntityCollisionContext($$0);
/*    */   }
/*    */   
/*    */   boolean isDescending();
/*    */   
/*    */   boolean isAbove(VoxelShape paramVoxelShape, BlockPos paramBlockPos, boolean paramBoolean);
/*    */   
/*    */   boolean isHoldingItem(Item paramItem);
/*    */   
/*    */   boolean canStandOnFluid(FluidState paramFluidState1, FluidState paramFluidState2);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\CollisionContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */