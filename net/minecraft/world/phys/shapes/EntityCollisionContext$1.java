/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends EntityCollisionContext
/*    */ {
/*    */   null(boolean $$0, double $$1, ItemStack $$2, Predicate<FluidState> $$3, Entity $$4) {
/* 16 */     super($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */   public boolean isAbove(VoxelShape $$0, BlockPos $$1, boolean $$2) {
/* 19 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\EntityCollisionContext$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */