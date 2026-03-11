/*    */ package net.minecraft.core.dispenser;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.vehicle.Boat;
/*    */ import net.minecraft.world.entity.vehicle.ChestBoat;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.DispenserBlock;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class BoatDispenseItemBehavior extends DefaultDispenseItemBehavior {
/* 16 */   private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
/*    */   private final Boat.Type type;
/*    */   private final boolean isChestBoat;
/*    */   
/*    */   public BoatDispenseItemBehavior(Boat.Type $$0) {
/* 21 */     this($$0, false);
/*    */   }
/*    */   
/*    */   public BoatDispenseItemBehavior(Boat.Type $$0, boolean $$1) {
/* 25 */     this.type = $$0;
/* 26 */     this.isChestBoat = $$1;
/*    */   }
/*    */   
/*    */   public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/*    */     double $$11;
/* 31 */     Direction $$2 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/* 32 */     ServerLevel $$3 = $$0.level();
/* 33 */     Vec3 $$4 = $$0.center();
/*    */     
/* 35 */     double $$5 = 0.5625D + EntityType.BOAT.getWidth() / 2.0D;
/* 36 */     double $$6 = $$4.x() + $$2.getStepX() * $$5;
/* 37 */     double $$7 = $$4.y() + ($$2.getStepY() * 1.125F);
/* 38 */     double $$8 = $$4.z() + $$2.getStepZ() * $$5;
/*    */     
/* 40 */     BlockPos $$9 = $$0.pos().relative($$2);
/*    */ 
/*    */     
/* 43 */     if ($$3.getFluidState($$9).is(FluidTags.WATER)) {
/* 44 */       double $$10 = 1.0D;
/* 45 */     } else if ($$3.getBlockState($$9).isAir() && $$3.getFluidState($$9.below()).is(FluidTags.WATER)) {
/* 46 */       $$11 = 0.0D;
/*    */     } else {
/* 48 */       return this.defaultDispenseItemBehavior.dispense($$0, $$1);
/*    */     } 
/*    */ 
/*    */     
/* 52 */     Boat $$13 = this.isChestBoat ? (Boat)new ChestBoat((Level)$$3, $$6, $$7 + $$11, $$8) : new Boat((Level)$$3, $$6, $$7 + $$11, $$8);
/* 53 */     EntityType.createDefaultStackConfig($$3, $$1, null)
/* 54 */       .accept($$13);
/* 55 */     $$13.setVariant(this.type);
/* 56 */     $$13.setYRot($$2.toYRot());
/* 57 */     $$3.addFreshEntity((Entity)$$13);
/*    */     
/* 59 */     $$1.shrink(1);
/* 60 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void playSound(BlockSource $$0) {
/* 65 */     $$0.level().levelEvent(1000, $$0.pos(), 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\dispenser\BoatDispenseItemBehavior.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */