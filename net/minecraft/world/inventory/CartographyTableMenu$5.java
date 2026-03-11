/*    */ package net.minecraft.world.inventory;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends Slot
/*    */ {
/*    */   null(Container $$1, int $$2, int $$3, int $$4) {
/* 66 */     super($$1, $$2, $$3, $$4);
/*    */   }
/*    */   public boolean mayPlace(ItemStack $$0) {
/* 69 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTake(Player $$0, ItemStack $$1) {
/* 74 */     ((Slot)CartographyTableMenu.this.slots.get(0)).remove(1);
/* 75 */     ((Slot)CartographyTableMenu.this.slots.get(1)).remove(1);
/*    */     
/* 77 */     $$1.getItem().onCraftedBy($$1, $$0.level(), $$0);
/*    */     
/* 79 */     access.execute(($$0, $$1) -> {
/*    */           long $$2 = $$0.getGameTime();
/*    */           
/*    */           if (CartographyTableMenu.this.lastSoundTime != $$2) {
/*    */             $$0.playSound(null, $$1, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
/*    */             
/*    */             CartographyTableMenu.this.lastSoundTime = $$2;
/*    */           } 
/*    */         });
/* 88 */     super.onTake($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\CartographyTableMenu$5.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */