/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class EmptyMapItem extends ComplexItem {
/*    */   public EmptyMapItem(Item.Properties $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 17 */     ItemStack $$3 = $$1.getItemInHand($$2);
/*    */     
/* 19 */     if ($$0.isClientSide) {
/* 20 */       return InteractionResultHolder.success($$3);
/*    */     }
/*    */     
/* 23 */     if (!($$1.getAbilities()).instabuild) {
/* 24 */       $$3.shrink(1);
/*    */     }
/*    */     
/* 27 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 28 */     $$1.level().playSound(null, (Entity)$$1, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, $$1.getSoundSource(), 1.0F, 1.0F);
/*    */     
/* 30 */     ItemStack $$4 = MapItem.create($$0, $$1.getBlockX(), $$1.getBlockZ(), (byte)0, true, false);
/* 31 */     if ($$3.isEmpty()) {
/* 32 */       return InteractionResultHolder.consume($$4);
/*    */     }
/* 34 */     if (!$$1.getInventory().add($$4.copy())) {
/* 35 */       $$1.drop($$4, false);
/*    */     }
/*    */     
/* 38 */     return InteractionResultHolder.consume($$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\EmptyMapItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */