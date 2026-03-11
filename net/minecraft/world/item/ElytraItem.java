/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.DispenserBlock;
/*    */ 
/*    */ public class ElytraItem extends Item implements Equipable {
/*    */   public ElytraItem(Item.Properties $$0) {
/* 14 */     super($$0);
/*    */     
/* 16 */     DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
/*    */   }
/*    */   
/*    */   public static boolean isFlyEnabled(ItemStack $$0) {
/* 20 */     return ($$0.getDamageValue() < $$0.getMaxDamage() - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidRepairItem(ItemStack $$0, ItemStack $$1) {
/* 25 */     return $$1.is(Items.PHANTOM_MEMBRANE);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 30 */     return swapWithEquipmentSlot(this, $$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundEvent getEquipSound() {
/* 35 */     return SoundEvents.ARMOR_EQUIP_ELYTRA;
/*    */   }
/*    */ 
/*    */   
/*    */   public EquipmentSlot getEquipmentSlot() {
/* 40 */     return EquipmentSlot.CHEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ElytraItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */