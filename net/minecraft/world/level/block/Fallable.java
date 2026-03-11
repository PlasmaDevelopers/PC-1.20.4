/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.item.FallingBlockEntity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Fallable
/*    */ {
/*    */   default void onLand(Level $$0, BlockPos $$1, BlockState $$2, BlockState $$3, FallingBlockEntity $$4) {}
/*    */   
/*    */   default void onBrokenAfterFall(Level $$0, BlockPos $$1, FallingBlockEntity $$2) {}
/*    */   
/*    */   default DamageSource getFallDamageSource(Entity $$0) {
/* 19 */     return $$0.damageSources().fallingBlock($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\Fallable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */