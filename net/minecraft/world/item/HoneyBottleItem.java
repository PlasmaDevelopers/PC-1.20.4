/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.effect.MobEffects;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class HoneyBottleItem
/*    */   extends Item {
/*    */   private static final int DRINK_DURATION = 40;
/*    */   
/*    */   public HoneyBottleItem(Item.Properties $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack finishUsingItem(ItemStack $$0, Level $$1, LivingEntity $$2) {
/* 25 */     super.finishUsingItem($$0, $$1, $$2);
/* 26 */     if ($$2 instanceof ServerPlayer) {
/* 27 */       ServerPlayer $$3 = (ServerPlayer)$$2;
/* 28 */       CriteriaTriggers.CONSUME_ITEM.trigger($$3, $$0);
/* 29 */       $$3.awardStat(Stats.ITEM_USED.get(this));
/*    */     } 
/*    */ 
/*    */     
/* 33 */     if (!$$1.isClientSide) {
/* 34 */       $$2.removeEffect(MobEffects.POISON);
/*    */     }
/*    */     
/* 37 */     if ($$0.isEmpty())
/* 38 */       return new ItemStack(Items.GLASS_BOTTLE); 
/* 39 */     if ($$2 instanceof Player) { Player $$4 = (Player)$$2; if (!($$4.getAbilities()).instabuild) {
/* 40 */         ItemStack $$5 = new ItemStack(Items.GLASS_BOTTLE);
/* 41 */         if (!$$4.getInventory().add($$5)) {
/* 42 */           $$4.drop($$5, false);
/*    */         }
/*    */       }  }
/*    */     
/* 46 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getUseDuration(ItemStack $$0) {
/* 51 */     return 40;
/*    */   }
/*    */ 
/*    */   
/*    */   public UseAnim getUseAnimation(ItemStack $$0) {
/* 56 */     return UseAnim.DRINK;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getDrinkingSound() {
/* 61 */     return SoundEvents.HONEY_DRINK;
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getEatingSound() {
/* 66 */     return SoundEvents.HONEY_DRINK;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 71 */     return ItemUtils.startUsingInstantly($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\HoneyBottleItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */