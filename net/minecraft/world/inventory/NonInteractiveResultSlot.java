/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class NonInteractiveResultSlot
/*    */   extends Slot
/*    */ {
/*    */   public NonInteractiveResultSlot(Container $$0, int $$1, int $$2, int $$3) {
/* 12 */     super($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onQuickCraft(ItemStack $$0, ItemStack $$1) {}
/*    */ 
/*    */   
/*    */   public boolean mayPickup(Player $$0) {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<ItemStack> tryRemove(int $$0, int $$1, Player $$2) {
/* 26 */     return Optional.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack safeTake(int $$0, int $$1, Player $$2) {
/* 31 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack safeInsert(ItemStack $$0) {
/* 36 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack safeInsert(ItemStack $$0, int $$1) {
/* 41 */     return safeInsert($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean allowModification(Player $$0) {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack $$0) {
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack remove(int $$0) {
/* 56 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTake(Player $$0, ItemStack $$1) {}
/*    */ 
/*    */   
/*    */   public boolean isHighlightable() {
/* 65 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFake() {
/* 70 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\NonInteractiveResultSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */