/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends Slot
/*    */ {
/*    */   null(Container $$1, int $$2, int $$3, int $$4) {
/* 63 */     super($$1, $$2, $$3, $$4);
/*    */   }
/*    */   public boolean mayPlace(ItemStack $$0) {
/* 66 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTake(Player $$0, ItemStack $$1) {
/* 71 */     $$1.onCraftedBy($$0.level(), $$0, $$1.getCount());
/* 72 */     StonecutterMenu.this.resultContainer.awardUsedRecipes($$0, getRelevantItems());
/*    */ 
/*    */     
/* 75 */     ItemStack $$2 = StonecutterMenu.this.inputSlot.remove(1);
/* 76 */     if (!$$2.isEmpty()) {
/* 77 */       StonecutterMenu.this.setupResultSlot();
/*    */     }
/*    */     
/* 80 */     access.execute(($$0, $$1) -> {
/*    */           long $$2 = $$0.getGameTime();
/*    */           
/*    */           if (StonecutterMenu.this.lastSoundTime != $$2) {
/*    */             $$0.playSound(null, $$1, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
/*    */             
/*    */             StonecutterMenu.this.lastSoundTime = $$2;
/*    */           } 
/*    */         });
/* 89 */     super.onTake($$0, $$1);
/*    */   }
/*    */   
/*    */   private List<ItemStack> getRelevantItems() {
/* 93 */     return List.of(StonecutterMenu.this.inputSlot
/* 94 */         .getItem());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\StonecutterMenu$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */