/*    */ package net.minecraft.core.dispenser;
/*    */ 
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.DispenserBlock;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class DefaultDispenseItemBehavior
/*    */   implements DispenseItemBehavior {
/*    */   public final ItemStack dispense(BlockSource $$0, ItemStack $$1) {
/* 15 */     ItemStack $$2 = execute($$0, $$1);
/*    */     
/* 17 */     playSound($$0);
/* 18 */     playAnimation($$0, (Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/*    */     
/* 20 */     return $$2;
/*    */   }
/*    */   
/*    */   protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 24 */     Direction $$2 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/* 25 */     Position $$3 = DispenserBlock.getDispensePosition($$0);
/*    */     
/* 27 */     ItemStack $$4 = $$1.split(1);
/*    */     
/* 29 */     spawnItem((Level)$$0.level(), $$4, 6, $$2, $$3);
/*    */     
/* 31 */     return $$1;
/*    */   }
/*    */   
/*    */   public static void spawnItem(Level $$0, ItemStack $$1, int $$2, Direction $$3, Position $$4) {
/* 35 */     double $$5 = $$4.x();
/* 36 */     double $$6 = $$4.y();
/* 37 */     double $$7 = $$4.z();
/*    */     
/* 39 */     if ($$3.getAxis() == Direction.Axis.Y) {
/*    */       
/* 41 */       $$6 -= 0.125D;
/*    */     } else {
/*    */       
/* 44 */       $$6 -= 0.15625D;
/*    */     } 
/*    */     
/* 47 */     ItemEntity $$8 = new ItemEntity($$0, $$5, $$6, $$7, $$1);
/*    */     
/* 49 */     double $$9 = $$0.random.nextDouble() * 0.1D + 0.2D;
/* 50 */     $$8.setDeltaMovement($$0.random
/* 51 */         .triangle($$3.getStepX() * $$9, 0.0172275D * $$2), $$0.random
/* 52 */         .triangle(0.2D, 0.0172275D * $$2), $$0.random
/* 53 */         .triangle($$3.getStepZ() * $$9, 0.0172275D * $$2));
/*    */ 
/*    */     
/* 56 */     $$0.addFreshEntity((Entity)$$8);
/*    */   }
/*    */   
/*    */   protected void playSound(BlockSource $$0) {
/* 60 */     $$0.level().levelEvent(1000, $$0.pos(), 0);
/*    */   }
/*    */   
/*    */   protected void playAnimation(BlockSource $$0, Direction $$1) {
/* 64 */     $$0.level().levelEvent(2000, $$0.pos(), $$1.get3DDataValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\dispenser\DefaultDispenseItemBehavior.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */