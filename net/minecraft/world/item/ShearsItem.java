/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.GrowingPlantHeadBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class ShearsItem extends Item {
/*    */   public ShearsItem(Item.Properties $$0) {
/* 23 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mineBlock(ItemStack $$0, Level $$1, BlockState $$2, BlockPos $$3, LivingEntity $$4) {
/* 28 */     if (!$$1.isClientSide && !$$2.is(BlockTags.FIRE)) {
/* 29 */       $$0.hurtAndBreak(1, $$4, $$0 -> $$0.broadcastBreakEvent(EquipmentSlot.MAINHAND));
/*    */     }
/*    */     
/* 32 */     if ($$2.is(BlockTags.LEAVES) || $$2
/* 33 */       .is(Blocks.COBWEB) || $$2
/* 34 */       .is(Blocks.SHORT_GRASS) || $$2
/* 35 */       .is(Blocks.FERN) || $$2
/* 36 */       .is(Blocks.DEAD_BUSH) || $$2
/* 37 */       .is(Blocks.HANGING_ROOTS) || $$2
/* 38 */       .is(Blocks.VINE) || $$2
/* 39 */       .is(Blocks.TRIPWIRE) || $$2
/* 40 */       .is(BlockTags.WOOL))
/*    */     {
/* 42 */       return true;
/*    */     }
/* 44 */     return super.mineBlock($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCorrectToolForDrops(BlockState $$0) {
/* 49 */     return ($$0.is(Blocks.COBWEB) || $$0.is(Blocks.REDSTONE_WIRE) || $$0.is(Blocks.TRIPWIRE));
/*    */   }
/*    */ 
/*    */   
/*    */   public float getDestroySpeed(ItemStack $$0, BlockState $$1) {
/* 54 */     if ($$1.is(Blocks.COBWEB) || $$1.is(BlockTags.LEAVES)) {
/* 55 */       return 15.0F;
/*    */     }
/* 57 */     if ($$1.is(BlockTags.WOOL)) {
/* 58 */       return 5.0F;
/*    */     }
/* 60 */     if ($$1.is(Blocks.VINE) || $$1.is(Blocks.GLOW_LICHEN)) {
/* 61 */       return 2.0F;
/*    */     }
/* 63 */     return super.getDestroySpeed($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 68 */     Level $$1 = $$0.getLevel();
/* 69 */     BlockPos $$2 = $$0.getClickedPos();
/* 70 */     BlockState $$3 = $$1.getBlockState($$2);
/* 71 */     Block $$4 = $$3.getBlock();
/* 72 */     if ($$4 instanceof GrowingPlantHeadBlock) { GrowingPlantHeadBlock $$5 = (GrowingPlantHeadBlock)$$4;
/* 73 */       if (!$$5.isMaxAge($$3)) {
/* 74 */         Player $$6 = $$0.getPlayer();
/* 75 */         ItemStack $$7 = $$0.getItemInHand();
/* 76 */         if ($$6 instanceof ServerPlayer) {
/* 77 */           CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)$$6, $$2, $$7);
/*    */         }
/* 79 */         $$1.playSound($$6, $$2, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 80 */         BlockState $$8 = $$5.getMaxAgeState($$3);
/* 81 */         $$1.setBlockAndUpdate($$2, $$8);
/* 82 */         $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of((Entity)$$0.getPlayer(), $$8));
/* 83 */         if ($$6 != null) {
/* 84 */           $$7.hurtAndBreak(1, $$6, $$1 -> $$1.broadcastBreakEvent($$0.getHand()));
/*    */         }
/*    */         
/* 87 */         return InteractionResult.sidedSuccess($$1.isClientSide);
/*    */       }  }
/*    */     
/* 90 */     return super.useOn($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ShearsItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */