/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
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
/*    */ public class VibrationUser
/*    */   extends SculkSensorBlockEntity.VibrationUser
/*    */ {
/*    */   public VibrationUser(BlockPos $$1) {
/* 27 */     super($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getListenerRadius() {
/* 32 */     return 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable GameEvent.Context $$3) {
/* 37 */     int $$4 = getBackSignal((Level)$$0, this.blockPos, CalibratedSculkSensorBlockEntity.this.getBlockState());
/*    */     
/* 39 */     if ($$4 != 0 && VibrationSystem.getGameEventFrequency($$2) != $$4) {
/* 40 */       return false;
/*    */     }
/*    */     
/* 43 */     return super.canReceiveVibration($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   private int getBackSignal(Level $$0, BlockPos $$1, BlockState $$2) {
/* 47 */     Direction $$3 = ((Direction)$$2.getValue((Property)CalibratedSculkSensorBlock.FACING)).getOpposite();
/* 48 */     return $$0.getSignal($$1.relative($$3), $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\CalibratedSculkSensorBlockEntity$VibrationUser.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */