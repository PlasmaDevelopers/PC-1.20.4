/*    */ package net.minecraft.world;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ContainerHelper
/*    */ {
/*    */   public static ItemStack removeItem(List<ItemStack> $$0, int $$1, int $$2) {
/* 14 */     if ($$1 < 0 || $$1 >= $$0.size() || ((ItemStack)$$0.get($$1)).isEmpty() || $$2 <= 0) {
/* 15 */       return ItemStack.EMPTY;
/*    */     }
/*    */     
/* 18 */     return ((ItemStack)$$0.get($$1)).split($$2);
/*    */   }
/*    */   
/*    */   public static ItemStack takeItem(List<ItemStack> $$0, int $$1) {
/* 22 */     if ($$1 < 0 || $$1 >= $$0.size()) {
/* 23 */       return ItemStack.EMPTY;
/*    */     }
/*    */     
/* 26 */     return $$0.set($$1, ItemStack.EMPTY);
/*    */   }
/*    */   
/*    */   public static CompoundTag saveAllItems(CompoundTag $$0, NonNullList<ItemStack> $$1) {
/* 30 */     return saveAllItems($$0, $$1, true);
/*    */   }
/*    */   
/*    */   public static CompoundTag saveAllItems(CompoundTag $$0, NonNullList<ItemStack> $$1, boolean $$2) {
/* 34 */     ListTag $$3 = new ListTag();
/* 35 */     for (int $$4 = 0; $$4 < $$1.size(); $$4++) {
/* 36 */       ItemStack $$5 = (ItemStack)$$1.get($$4);
/* 37 */       if (!$$5.isEmpty()) {
/* 38 */         CompoundTag $$6 = new CompoundTag();
/* 39 */         $$6.putByte("Slot", (byte)$$4);
/* 40 */         $$5.save($$6);
/* 41 */         $$3.add($$6);
/*    */       } 
/*    */     } 
/* 44 */     if (!$$3.isEmpty() || $$2) {
/* 45 */       $$0.put("Items", (Tag)$$3);
/*    */     }
/* 47 */     return $$0;
/*    */   }
/*    */   
/*    */   public static void loadAllItems(CompoundTag $$0, NonNullList<ItemStack> $$1) {
/* 51 */     ListTag $$2 = $$0.getList("Items", 10);
/* 52 */     for (int $$3 = 0; $$3 < $$2.size(); $$3++) {
/* 53 */       CompoundTag $$4 = $$2.getCompound($$3);
/* 54 */       int $$5 = $$4.getByte("Slot") & 0xFF;
/* 55 */       if ($$5 >= 0 && $$5 < $$1.size()) {
/* 56 */         $$1.set($$5, ItemStack.of($$4));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int clearOrCountMatchingItems(Container $$0, Predicate<ItemStack> $$1, int $$2, boolean $$3) {
/* 62 */     int $$4 = 0;
/* 63 */     for (int $$5 = 0; $$5 < $$0.getContainerSize(); $$5++) {
/* 64 */       ItemStack $$6 = $$0.getItem($$5);
/* 65 */       int $$7 = clearOrCountMatchingItems($$6, $$1, $$2 - $$4, $$3);
/* 66 */       if ($$7 > 0 && !$$3 && $$6.isEmpty()) {
/* 67 */         $$0.setItem($$5, ItemStack.EMPTY);
/*    */       }
/* 69 */       $$4 += $$7;
/*    */     } 
/* 71 */     return $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int clearOrCountMatchingItems(ItemStack $$0, Predicate<ItemStack> $$1, int $$2, boolean $$3) {
/* 76 */     if ($$0.isEmpty() || !$$1.test($$0)) {
/* 77 */       return 0;
/*    */     }
/*    */     
/* 80 */     if ($$3) {
/* 81 */       return $$0.getCount();
/*    */     }
/*    */     
/* 84 */     int $$4 = ($$2 < 0) ? $$0.getCount() : Math.min($$2, $$0.getCount());
/* 85 */     $$0.shrink($$4);
/* 86 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ContainerHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */