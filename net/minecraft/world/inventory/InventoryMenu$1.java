/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   extends Slot
/*     */ {
/*     */   null(Container $$1, int $$2, int $$3, int $$4) {
/*  72 */     super($$1, $$2, $$3, $$4);
/*     */   }
/*     */   public void setByPlayer(ItemStack $$0, ItemStack $$1) {
/*  75 */     InventoryMenu.onEquipItem(owner, slot, $$0, $$1);
/*  76 */     super.setByPlayer($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/*  81 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mayPlace(ItemStack $$0) {
/*  86 */     return (slot == Mob.getEquipmentSlotForItem($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mayPickup(Player $$0) {
/*  91 */     ItemStack $$1 = getItem();
/*  92 */     if (!$$1.isEmpty() && !$$0.isCreative() && EnchantmentHelper.hasBindingCurse($$1)) {
/*  93 */       return false;
/*     */     }
/*  95 */     return super.mayPickup($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
/* 100 */     return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.TEXTURE_EMPTY_SLOTS[slot.getIndex()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\InventoryMenu$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */