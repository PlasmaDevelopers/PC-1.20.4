/*    */ package net.minecraft.world;
/*    */ 
/*    */ import java.util.Set;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
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
/*    */ public interface Container
/*    */   extends Clearable
/*    */ {
/*    */   public static final int LARGE_MAX_STACK_SIZE = 64;
/*    */   public static final int DEFAULT_DISTANCE_LIMIT = 8;
/*    */   
/*    */   default int getMaxStackSize() {
/* 30 */     return 64;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   default void startOpen(Player $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   default void stopOpen(Player $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean canPlaceItem(int $$0, ItemStack $$1) {
/* 44 */     return true;
/*    */   }
/*    */   
/*    */   default boolean canTakeItem(Container $$0, int $$1, ItemStack $$2) {
/* 48 */     return true;
/*    */   }
/*    */   
/*    */   default int countItem(Item $$0) {
/* 52 */     int $$1 = 0;
/* 53 */     for (int $$2 = 0; $$2 < getContainerSize(); $$2++) {
/* 54 */       ItemStack $$3 = getItem($$2);
/* 55 */       if ($$3.getItem().equals($$0)) {
/* 56 */         $$1 += $$3.getCount();
/*    */       }
/*    */     } 
/* 59 */     return $$1;
/*    */   }
/*    */   
/*    */   default boolean hasAnyOf(Set<Item> $$0) {
/* 63 */     return hasAnyMatching($$1 -> (!$$1.isEmpty() && $$0.contains($$1.getItem())));
/*    */   }
/*    */   
/*    */   default boolean hasAnyMatching(Predicate<ItemStack> $$0) {
/* 67 */     for (int $$1 = 0; $$1 < getContainerSize(); $$1++) {
/* 68 */       ItemStack $$2 = getItem($$1);
/* 69 */       if ($$0.test($$2)) {
/* 70 */         return true;
/*    */       }
/*    */     } 
/* 73 */     return false;
/*    */   }
/*    */   
/*    */   static boolean stillValidBlockEntity(BlockEntity $$0, Player $$1) {
/* 77 */     return stillValidBlockEntity($$0, $$1, 8);
/*    */   }
/*    */   
/*    */   static boolean stillValidBlockEntity(BlockEntity $$0, Player $$1, int $$2) {
/* 81 */     Level $$3 = $$0.getLevel();
/* 82 */     BlockPos $$4 = $$0.getBlockPos();
/*    */     
/* 84 */     if ($$3 == null) {
/* 85 */       return false;
/*    */     }
/* 87 */     if ($$3.getBlockEntity($$4) != $$0) {
/* 88 */       return false;
/*    */     }
/*    */     
/* 91 */     return ($$1.distanceToSqr($$4.getX() + 0.5D, $$4.getY() + 0.5D, $$4.getZ() + 0.5D) <= ($$2 * $$2));
/*    */   }
/*    */   
/*    */   int getContainerSize();
/*    */   
/*    */   boolean isEmpty();
/*    */   
/*    */   ItemStack getItem(int paramInt);
/*    */   
/*    */   ItemStack removeItem(int paramInt1, int paramInt2);
/*    */   
/*    */   ItemStack removeItemNoUpdate(int paramInt);
/*    */   
/*    */   void setItem(int paramInt, ItemStack paramItemStack);
/*    */   
/*    */   void setChanged();
/*    */   
/*    */   boolean stillValid(Player paramPlayer);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\Container.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */