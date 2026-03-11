/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.BaseFireBlock;
/*    */ import net.minecraft.world.level.block.CampfireBlock;
/*    */ import net.minecraft.world.level.block.CandleBlock;
/*    */ import net.minecraft.world.level.block.CandleCakeBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class FlintAndSteelItem extends Item {
/*    */   public FlintAndSteelItem(Item.Properties $$0) {
/* 24 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 29 */     Player $$1 = $$0.getPlayer();
/* 30 */     Level $$2 = $$0.getLevel();
/* 31 */     BlockPos $$3 = $$0.getClickedPos();
/*    */     
/* 33 */     BlockState $$4 = $$2.getBlockState($$3);
/* 34 */     if (CampfireBlock.canLight($$4) || CandleBlock.canLight($$4) || CandleCakeBlock.canLight($$4)) {
/* 35 */       $$2.playSound($$1, $$3, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, $$2.getRandom().nextFloat() * 0.4F + 0.8F);
/* 36 */       $$2.setBlock($$3, (BlockState)$$4.setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
/* 37 */       $$2.gameEvent((Entity)$$1, GameEvent.BLOCK_CHANGE, $$3);
/* 38 */       if ($$1 != null) {
/* 39 */         $$0.getItemInHand().hurtAndBreak(1, $$1, $$1 -> $$1.broadcastBreakEvent($$0.getHand()));
/*    */       }
/* 41 */       return InteractionResult.sidedSuccess($$2.isClientSide());
/*    */     } 
/*    */     
/* 44 */     BlockPos $$5 = $$3.relative($$0.getClickedFace());
/* 45 */     if (BaseFireBlock.canBePlacedAt($$2, $$5, $$0.getHorizontalDirection())) {
/* 46 */       $$2.playSound($$1, $$5, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, $$2.getRandom().nextFloat() * 0.4F + 0.8F);
/*    */       
/* 48 */       BlockState $$6 = BaseFireBlock.getState((BlockGetter)$$2, $$5);
/* 49 */       $$2.setBlock($$5, $$6, 11);
/* 50 */       $$2.gameEvent((Entity)$$1, GameEvent.BLOCK_PLACE, $$3);
/*    */       
/* 52 */       ItemStack $$7 = $$0.getItemInHand();
/* 53 */       if ($$1 instanceof ServerPlayer) {
/* 54 */         CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)$$1, $$5, $$7);
/* 55 */         $$7.hurtAndBreak(1, $$1, $$1 -> $$1.broadcastBreakEvent($$0.getHand()));
/*    */       } 
/*    */       
/* 58 */       return InteractionResult.sidedSuccess($$2.isClientSide());
/*    */     } 
/*    */     
/* 61 */     return InteractionResult.FAIL;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\FlintAndSteelItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */