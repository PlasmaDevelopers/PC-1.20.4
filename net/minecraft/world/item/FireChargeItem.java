/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.BaseFireBlock;
/*    */ import net.minecraft.world.level.block.CampfireBlock;
/*    */ import net.minecraft.world.level.block.CandleCakeBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class FireChargeItem extends Item {
/*    */   public FireChargeItem(Item.Properties $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 25 */     Level $$1 = $$0.getLevel();
/* 26 */     BlockPos $$2 = $$0.getClickedPos();
/* 27 */     BlockState $$3 = $$1.getBlockState($$2);
/* 28 */     boolean $$4 = false;
/*    */     
/* 30 */     if (CampfireBlock.canLight($$3) || CandleBlock.canLight($$3) || CandleCakeBlock.canLight($$3)) {
/* 31 */       playSound($$1, $$2);
/* 32 */       $$1.setBlockAndUpdate($$2, (BlockState)$$3.setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(true)));
/* 33 */       $$1.gameEvent((Entity)$$0.getPlayer(), GameEvent.BLOCK_CHANGE, $$2);
/* 34 */       $$4 = true;
/*    */     } else {
/* 36 */       $$2 = $$2.relative($$0.getClickedFace());
/* 37 */       if (BaseFireBlock.canBePlacedAt($$1, $$2, $$0.getHorizontalDirection())) {
/* 38 */         playSound($$1, $$2);
/* 39 */         $$1.setBlockAndUpdate($$2, BaseFireBlock.getState((BlockGetter)$$1, $$2));
/* 40 */         $$1.gameEvent((Entity)$$0.getPlayer(), GameEvent.BLOCK_PLACE, $$2);
/* 41 */         $$4 = true;
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     if ($$4) {
/* 46 */       $$0.getItemInHand().shrink(1);
/* 47 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*    */     } 
/*    */     
/* 50 */     return InteractionResult.FAIL;
/*    */   }
/*    */   
/*    */   private void playSound(Level $$0, BlockPos $$1) {
/* 54 */     RandomSource $$2 = $$0.getRandom();
/* 55 */     $$0.playSound(null, $$1, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, ($$2.nextFloat() - $$2.nextFloat()) * 0.2F + 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\FireChargeItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */