/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potion;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
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
/*     */ class PotionSlot
/*     */   extends Slot
/*     */ {
/*     */   public PotionSlot(Container $$0, int $$1, int $$2, int $$3) {
/* 130 */     super($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mayPlace(ItemStack $$0) {
/* 135 */     return mayPlaceItem($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 140 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTake(Player $$0, ItemStack $$1) {
/* 145 */     Potion $$2 = PotionUtils.getPotion($$1);
/* 146 */     if ($$0 instanceof ServerPlayer) {
/* 147 */       CriteriaTriggers.BREWED_POTION.trigger((ServerPlayer)$$0, (Holder)$$2.builtInRegistryHolder());
/*     */     }
/* 149 */     super.onTake($$0, $$1);
/*     */   }
/*     */   
/*     */   public static boolean mayPlaceItem(ItemStack $$0) {
/* 153 */     return ($$0.is(Items.POTION) || $$0.is(Items.SPLASH_POTION) || $$0.is(Items.LINGERING_POTION) || $$0.is(Items.GLASS_BOTTLE));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\BrewingStandMenu$PotionSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */