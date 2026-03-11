/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.world.SimpleContainer;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
/*    */ 
/*    */ public class PlayerEnderChestContainer
/*    */   extends SimpleContainer {
/*    */   @Nullable
/*    */   private EnderChestBlockEntity activeChest;
/*    */   
/*    */   public PlayerEnderChestContainer() {
/* 17 */     super(27);
/*    */   }
/*    */   
/*    */   public void setActiveChest(EnderChestBlockEntity $$0) {
/* 21 */     this.activeChest = $$0;
/*    */   }
/*    */   
/*    */   public boolean isActiveChest(EnderChestBlockEntity $$0) {
/* 25 */     return (this.activeChest == $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void fromTag(ListTag $$0) {
/* 30 */     for (int $$1 = 0; $$1 < getContainerSize(); $$1++) {
/* 31 */       setItem($$1, ItemStack.EMPTY);
/*    */     }
/* 33 */     for (int $$2 = 0; $$2 < $$0.size(); $$2++) {
/* 34 */       CompoundTag $$3 = $$0.getCompound($$2);
/* 35 */       int $$4 = $$3.getByte("Slot") & 0xFF;
/* 36 */       if ($$4 >= 0 && $$4 < getContainerSize()) {
/* 37 */         setItem($$4, ItemStack.of($$3));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ListTag createTag() {
/* 44 */     ListTag $$0 = new ListTag();
/* 45 */     for (int $$1 = 0; $$1 < getContainerSize(); $$1++) {
/* 46 */       ItemStack $$2 = getItem($$1);
/* 47 */       if (!$$2.isEmpty()) {
/* 48 */         CompoundTag $$3 = new CompoundTag();
/* 49 */         $$3.putByte("Slot", (byte)$$1);
/* 50 */         $$2.save($$3);
/* 51 */         $$0.add($$3);
/*    */       } 
/*    */     } 
/* 54 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean stillValid(Player $$0) {
/* 59 */     if (this.activeChest != null && !this.activeChest.stillValid($$0)) {
/* 60 */       return false;
/*    */     }
/* 62 */     return super.stillValid($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void startOpen(Player $$0) {
/* 67 */     if (this.activeChest != null) {
/* 68 */       this.activeChest.startOpen($$0);
/*    */     }
/* 70 */     super.startOpen($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stopOpen(Player $$0) {
/* 75 */     if (this.activeChest != null) {
/* 76 */       this.activeChest.stopOpen($$0);
/*    */     }
/* 78 */     super.stopOpen($$0);
/* 79 */     this.activeChest = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\PlayerEnderChestContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */