/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.entity.EntityTypeTest;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ContainerOpenersCounter
/*    */ {
/*    */   private static final int CHECK_TICK_DELAY = 5;
/*    */   private int openCount;
/*    */   
/*    */   protected abstract void onOpen(Level paramLevel, BlockPos paramBlockPos, BlockState paramBlockState);
/*    */   
/*    */   public void incrementOpeners(Player $$0, Level $$1, BlockPos $$2, BlockState $$3) {
/* 24 */     int $$4 = this.openCount++;
/* 25 */     if ($$4 == 0) {
/* 26 */       onOpen($$1, $$2, $$3);
/* 27 */       $$1.gameEvent((Entity)$$0, GameEvent.CONTAINER_OPEN, $$2);
/* 28 */       scheduleRecheck($$1, $$2, $$3);
/*    */     } 
/* 30 */     openerCountChanged($$1, $$2, $$3, $$4, this.openCount);
/*    */   } protected abstract void onClose(Level paramLevel, BlockPos paramBlockPos, BlockState paramBlockState); protected abstract void openerCountChanged(Level paramLevel, BlockPos paramBlockPos, BlockState paramBlockState, int paramInt1, int paramInt2);
/*    */   protected abstract boolean isOwnContainer(Player paramPlayer);
/*    */   public void decrementOpeners(Player $$0, Level $$1, BlockPos $$2, BlockState $$3) {
/* 34 */     int $$4 = this.openCount--;
/* 35 */     if (this.openCount == 0) {
/* 36 */       onClose($$1, $$2, $$3);
/* 37 */       $$1.gameEvent((Entity)$$0, GameEvent.CONTAINER_CLOSE, $$2);
/*    */     } 
/* 39 */     openerCountChanged($$1, $$2, $$3, $$4, this.openCount);
/*    */   }
/*    */   
/*    */   private int getOpenCount(Level $$0, BlockPos $$1) {
/* 43 */     int $$2 = $$1.getX();
/* 44 */     int $$3 = $$1.getY();
/* 45 */     int $$4 = $$1.getZ();
/*    */     
/* 47 */     float $$5 = 5.0F;
/* 48 */     AABB $$6 = new AABB(($$2 - 5.0F), ($$3 - 5.0F), ($$4 - 5.0F), (($$2 + 1) + 5.0F), (($$3 + 1) + 5.0F), (($$4 + 1) + 5.0F));
/* 49 */     return $$0.getEntities(EntityTypeTest.forClass(Player.class), $$6, this::isOwnContainer).size();
/*    */   }
/*    */   
/*    */   public void recheckOpeners(Level $$0, BlockPos $$1, BlockState $$2) {
/* 53 */     int $$3 = getOpenCount($$0, $$1);
/* 54 */     int $$4 = this.openCount;
/* 55 */     if ($$4 != $$3) {
/* 56 */       boolean $$5 = ($$3 != 0);
/* 57 */       boolean $$6 = ($$4 != 0);
/* 58 */       if ($$5 && !$$6) {
/* 59 */         onOpen($$0, $$1, $$2);
/* 60 */         $$0.gameEvent(null, GameEvent.CONTAINER_OPEN, $$1);
/* 61 */       } else if (!$$5) {
/* 62 */         onClose($$0, $$1, $$2);
/* 63 */         $$0.gameEvent(null, GameEvent.CONTAINER_CLOSE, $$1);
/*    */       } 
/* 65 */       this.openCount = $$3;
/*    */     } 
/* 67 */     openerCountChanged($$0, $$1, $$2, $$4, $$3);
/* 68 */     if ($$3 > 0) {
/* 69 */       scheduleRecheck($$0, $$1, $$2);
/*    */     }
/*    */   }
/*    */   
/*    */   public int getOpenerCount() {
/* 74 */     return this.openCount;
/*    */   }
/*    */   
/*    */   private static void scheduleRecheck(Level $$0, BlockPos $$1, BlockState $$2) {
/* 78 */     $$0.scheduleTick($$1, $$2.getBlock(), 5);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\ContainerOpenersCounter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */