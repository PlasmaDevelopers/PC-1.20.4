/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
/*    */ 
/*    */ public class FurnaceResultSlot extends Slot {
/*    */   private final Player player;
/*    */   private int removeCount;
/*    */   
/*    */   public FurnaceResultSlot(Player $$0, Container $$1, int $$2, int $$3, int $$4) {
/* 14 */     super($$1, $$2, $$3, $$4);
/* 15 */     this.player = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mayPlace(ItemStack $$0) {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack remove(int $$0) {
/* 25 */     if (hasItem()) {
/* 26 */       this.removeCount += Math.min($$0, getItem().getCount());
/*    */     }
/* 28 */     return super.remove($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTake(Player $$0, ItemStack $$1) {
/* 33 */     checkTakeAchievements($$1);
/* 34 */     super.onTake($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onQuickCraft(ItemStack $$0, int $$1) {
/* 39 */     this.removeCount += $$1;
/* 40 */     checkTakeAchievements($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void checkTakeAchievements(ItemStack $$0) {
/* 45 */     $$0.onCraftedBy(this.player.level(), this.player, this.removeCount);
/* 46 */     Player player = this.player; if (player instanceof ServerPlayer) { ServerPlayer $$1 = (ServerPlayer)player;
/* 47 */       Container container = this.container; if (container instanceof AbstractFurnaceBlockEntity) { AbstractFurnaceBlockEntity $$2 = (AbstractFurnaceBlockEntity)container;
/* 48 */         $$2.awardUsedRecipesAndPopExperience($$1); }
/*    */        }
/*    */     
/* 51 */     this.removeCount = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\FurnaceResultSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */