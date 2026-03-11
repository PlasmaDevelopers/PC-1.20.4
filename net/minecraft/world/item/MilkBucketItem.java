/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class MilkBucketItem extends Item {
/*    */   private static final int DRINK_DURATION = 32;
/*    */   
/*    */   public MilkBucketItem(Item.Properties $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack finishUsingItem(ItemStack $$0, Level $$1, LivingEntity $$2) {
/* 21 */     if ($$2 instanceof ServerPlayer) { ServerPlayer $$3 = (ServerPlayer)$$2;
/* 22 */       CriteriaTriggers.CONSUME_ITEM.trigger($$3, $$0);
/* 23 */       $$3.awardStat(Stats.ITEM_USED.get(this)); }
/*    */ 
/*    */     
/* 26 */     if ($$2 instanceof Player && !(((Player)$$2).getAbilities()).instabuild) {
/* 27 */       $$0.shrink(1);
/*    */     }
/*    */     
/* 30 */     if (!$$1.isClientSide) {
/* 31 */       $$2.removeAllEffects();
/*    */     }
/*    */     
/* 34 */     if ($$0.isEmpty()) {
/* 35 */       return new ItemStack(Items.BUCKET);
/*    */     }
/* 37 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getUseDuration(ItemStack $$0) {
/* 42 */     return 32;
/*    */   }
/*    */ 
/*    */   
/*    */   public UseAnim getUseAnimation(ItemStack $$0) {
/* 47 */     return UseAnim.DRINK;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 52 */     return ItemUtils.startUsingInstantly($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\MilkBucketItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */