/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public interface Equipable extends Vanishable {
/*    */   EquipmentSlot getEquipmentSlot();
/*    */   
/*    */   default SoundEvent getEquipSound() {
/* 20 */     return SoundEvents.ARMOR_EQUIP_GENERIC;
/*    */   }
/*    */   
/*    */   default InteractionResultHolder<ItemStack> swapWithEquipmentSlot(Item $$0, Level $$1, Player $$2, InteractionHand $$3) {
/* 24 */     ItemStack $$4 = $$2.getItemInHand($$3);
/* 25 */     EquipmentSlot $$5 = Mob.getEquipmentSlotForItem($$4);
/* 26 */     ItemStack $$6 = $$2.getItemBySlot($$5);
/*    */     
/* 28 */     if ((EnchantmentHelper.hasBindingCurse($$6) && !$$2.isCreative()) || ItemStack.matches($$4, $$6)) {
/* 29 */       return InteractionResultHolder.fail($$4);
/*    */     }
/*    */     
/* 32 */     if (!$$1.isClientSide()) {
/* 33 */       $$2.awardStat(Stats.ITEM_USED.get($$0));
/*    */     }
/*    */ 
/*    */     
/* 37 */     ItemStack $$7 = $$6.isEmpty() ? $$4 : $$6.copyAndClear();
/* 38 */     ItemStack $$8 = $$4.copyAndClear();
/* 39 */     $$2.setItemSlot($$5, $$8);
/*    */     
/* 41 */     return InteractionResultHolder.sidedSuccess($$7, $$1.isClientSide());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   static Equipable get(ItemStack $$0) {
/* 46 */     Item item1 = $$0.getItem(); if (item1 instanceof Equipable) { Equipable $$1 = (Equipable)item1;
/* 47 */       return $$1; }
/*    */ 
/*    */     
/* 50 */     Item item2 = $$0.getItem(); if (item2 instanceof BlockItem) { BlockItem $$2 = (BlockItem)item2; Block block = $$2.getBlock(); if (block instanceof Equipable) { Equipable $$3 = (Equipable)block;
/* 51 */         return $$3; }
/*    */        }
/*    */     
/* 54 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\Equipable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */