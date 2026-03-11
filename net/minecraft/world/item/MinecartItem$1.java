/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.dispenser.BlockSource;
/*    */ import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*    */ import net.minecraft.world.level.block.BaseRailBlock;
/*    */ import net.minecraft.world.level.block.DispenserBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.block.state.properties.RailShape;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends DefaultDispenseItemBehavior
/*    */ {
/* 24 */   private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
/*    */   
/*    */   public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/*    */     double $$16;
/* 28 */     Direction $$2 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/* 29 */     ServerLevel $$3 = $$0.level();
/* 30 */     Vec3 $$4 = $$0.center();
/*    */ 
/*    */ 
/*    */     
/* 34 */     double $$5 = $$4.x() + $$2.getStepX() * 1.125D;
/* 35 */     double $$6 = Math.floor($$4.y()) + $$2.getStepY();
/* 36 */     double $$7 = $$4.z() + $$2.getStepZ() * 1.125D;
/*    */     
/* 38 */     BlockPos $$8 = $$0.pos().relative($$2);
/* 39 */     BlockState $$9 = $$3.getBlockState($$8);
/* 40 */     RailShape $$10 = ($$9.getBlock() instanceof BaseRailBlock) ? (RailShape)$$9.getValue(((BaseRailBlock)$$9.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
/*    */ 
/*    */     
/* 43 */     if ($$9.is(BlockTags.RAILS)) {
/* 44 */       if ($$10.isAscending()) {
/* 45 */         double $$11 = 0.6D;
/*    */       } else {
/* 47 */         double $$12 = 0.1D;
/*    */       } 
/* 49 */     } else if ($$9.isAir() && $$3.getBlockState($$8.below()).is(BlockTags.RAILS)) {
/* 50 */       BlockState $$13 = $$3.getBlockState($$8.below());
/* 51 */       RailShape $$14 = ($$13.getBlock() instanceof BaseRailBlock) ? (RailShape)$$13.getValue(((BaseRailBlock)$$13.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
/* 52 */       if ($$2 == Direction.DOWN || !$$14.isAscending()) {
/* 53 */         double $$15 = -0.9D;
/*    */       } else {
/* 55 */         $$16 = -0.4D;
/*    */       } 
/*    */     } else {
/* 58 */       return this.defaultDispenseItemBehavior.dispense($$0, $$1);
/*    */     } 
/*    */     
/* 61 */     AbstractMinecart $$18 = AbstractMinecart.createMinecart($$3, $$5, $$6 + $$16, $$7, ((MinecartItem)$$1.getItem()).type, $$1, null);
/* 62 */     $$3.addFreshEntity((Entity)$$18);
/*    */     
/* 64 */     $$1.shrink(1);
/* 65 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void playSound(BlockSource $$0) {
/* 70 */     $$0.level().levelEvent(1000, $$0.pos(), 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\MinecartItem$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */