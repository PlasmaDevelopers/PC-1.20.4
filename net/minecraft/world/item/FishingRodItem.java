/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.entity.projectile.FishingHook;
/*    */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class FishingRodItem extends Item implements Vanishable {
/*    */   public FishingRodItem(Item.Properties $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 21 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 22 */     if ($$1.fishing != null) {
/* 23 */       if (!$$0.isClientSide) {
/* 24 */         int $$4 = $$1.fishing.retrieve($$3);
/* 25 */         $$3.hurtAndBreak($$4, $$1, $$1 -> $$1.broadcastBreakEvent($$0));
/*    */       } 
/* 27 */       $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / ($$0.getRandom().nextFloat() * 0.4F + 0.8F));
/*    */       
/* 29 */       $$1.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
/*    */     } else {
/* 31 */       $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / ($$0.getRandom().nextFloat() * 0.4F + 0.8F));
/*    */       
/* 33 */       if (!$$0.isClientSide) {
/* 34 */         int $$5 = EnchantmentHelper.getFishingSpeedBonus($$3);
/* 35 */         int $$6 = EnchantmentHelper.getFishingLuckBonus($$3);
/* 36 */         $$0.addFreshEntity((Entity)new FishingHook($$1, $$0, $$6, $$5));
/*    */       } 
/* 38 */       $$1.awardStat(Stats.ITEM_USED.get(this));
/*    */       
/* 40 */       $$1.gameEvent(GameEvent.ITEM_INTERACT_START);
/*    */     } 
/* 42 */     return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEnchantmentValue() {
/* 47 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\FishingRodItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */