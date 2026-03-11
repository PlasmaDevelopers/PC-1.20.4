/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.inventory.ChestMenu;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends ContainerOpenersCounter
/*    */ {
/*    */   protected void onOpen(Level $$0, BlockPos $$1, BlockState $$2) {
/* 28 */     BarrelBlockEntity.this.playSound($$2, SoundEvents.BARREL_OPEN);
/* 29 */     BarrelBlockEntity.this.updateBlockState($$2, true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onClose(Level $$0, BlockPos $$1, BlockState $$2) {
/* 34 */     BarrelBlockEntity.this.playSound($$2, SoundEvents.BARREL_CLOSE);
/* 35 */     BarrelBlockEntity.this.updateBlockState($$2, false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void openerCountChanged(Level $$0, BlockPos $$1, BlockState $$2, int $$3, int $$4) {}
/*    */ 
/*    */   
/*    */   protected boolean isOwnContainer(Player $$0) {
/* 44 */     if ($$0.containerMenu instanceof ChestMenu) {
/* 45 */       Container $$1 = ((ChestMenu)$$0.containerMenu).getContainer();
/* 46 */       return ($$1 == BarrelBlockEntity.this);
/*    */     } 
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BarrelBlockEntity$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */