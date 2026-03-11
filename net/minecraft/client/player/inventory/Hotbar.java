/*    */ package net.minecraft.client.player.inventory;
/*    */ 
/*    */ import com.google.common.collect.ForwardingList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class Hotbar extends ForwardingList<ItemStack> {
/* 13 */   private final NonNullList<ItemStack> items = NonNullList.withSize(Inventory.getSelectionSize(), ItemStack.EMPTY);
/*    */ 
/*    */   
/*    */   protected List<ItemStack> delegate() {
/* 17 */     return (List<ItemStack>)this.items;
/*    */   }
/*    */   
/*    */   public ListTag createTag() {
/* 21 */     ListTag $$0 = new ListTag();
/* 22 */     for (ItemStack $$1 : delegate()) {
/* 23 */       $$0.add($$1.save(new CompoundTag()));
/*    */     }
/* 25 */     return $$0;
/*    */   }
/*    */   
/*    */   public void fromTag(ListTag $$0) {
/* 29 */     List<ItemStack> $$1 = delegate();
/* 30 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/* 31 */       $$1.set($$2, ItemStack.of($$0.getCompound($$2)));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 37 */     for (ItemStack $$0 : delegate()) {
/* 38 */       if (!$$0.isEmpty()) {
/* 39 */         return false;
/*    */       }
/*    */     } 
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\player\inventory\Hotbar.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */