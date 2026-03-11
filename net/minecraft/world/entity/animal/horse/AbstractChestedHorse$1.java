/*     */ package net.minecraft.world.entity.animal.horse;
/*     */ 
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
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
/*     */   implements SlotAccess
/*     */ {
/*     */   public ItemStack get() {
/* 139 */     return AbstractChestedHorse.this.hasChest() ? new ItemStack((ItemLike)Items.CHEST) : ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean set(ItemStack $$0) {
/* 144 */     if ($$0.isEmpty()) {
/* 145 */       if (AbstractChestedHorse.this.hasChest()) {
/* 146 */         AbstractChestedHorse.this.setChest(false);
/* 147 */         AbstractChestedHorse.this.createInventory();
/*     */       } 
/* 149 */       return true;
/*     */     } 
/* 151 */     if ($$0.is(Items.CHEST)) {
/* 152 */       if (!AbstractChestedHorse.this.hasChest()) {
/* 153 */         AbstractChestedHorse.this.setChest(true);
/* 154 */         AbstractChestedHorse.this.createInventory();
/*     */       } 
/* 156 */       return true;
/*     */     } 
/* 158 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\AbstractChestedHorse$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */