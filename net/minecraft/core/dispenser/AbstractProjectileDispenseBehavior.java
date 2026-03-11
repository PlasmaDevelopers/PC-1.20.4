/*    */ package net.minecraft.core.dispenser;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.Projectile;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.DispenserBlock;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public abstract class AbstractProjectileDispenseBehavior extends DefaultDispenseItemBehavior {
/*    */   public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 14 */     ServerLevel serverLevel = $$0.level();
/* 15 */     Position $$3 = DispenserBlock.getDispensePosition($$0);
/* 16 */     Direction $$4 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/*    */     
/* 18 */     Projectile $$5 = getProjectile((Level)serverLevel, $$3, $$1);
/* 19 */     $$5.shoot($$4.getStepX(), ($$4.getStepY() + 0.1F), $$4.getStepZ(), getPower(), getUncertainty());
/* 20 */     serverLevel.addFreshEntity((Entity)$$5);
/*    */     
/* 22 */     $$1.shrink(1);
/*    */     
/* 24 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void playSound(BlockSource $$0) {
/* 29 */     $$0.level().levelEvent(1002, $$0.pos(), 0);
/*    */   }
/*    */   
/*    */   protected abstract Projectile getProjectile(Level paramLevel, Position paramPosition, ItemStack paramItemStack);
/*    */   
/*    */   protected float getUncertainty() {
/* 35 */     return 6.0F;
/*    */   }
/*    */   
/*    */   protected float getPower() {
/* 39 */     return 1.1F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\dispenser\AbstractProjectileDispenseBehavior.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */