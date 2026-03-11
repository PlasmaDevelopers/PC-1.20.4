/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemUtils
/*    */ {
/*    */   public static InteractionResultHolder<ItemStack> startUsingInstantly(Level $$0, Player $$1, InteractionHand $$2) {
/* 19 */     $$1.startUsingItem($$2);
/* 20 */     return InteractionResultHolder.consume($$1.getItemInHand($$2));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ItemStack createFilledResult(ItemStack $$0, Player $$1, ItemStack $$2, boolean $$3) {
/* 29 */     boolean $$4 = ($$1.getAbilities()).instabuild;
/* 30 */     if ($$3 && $$4) {
/* 31 */       if (!$$1.getInventory().contains($$2)) {
/* 32 */         $$1.getInventory().add($$2);
/*    */       }
/* 34 */       return $$0;
/*    */     } 
/*    */     
/* 37 */     if (!$$4) {
/* 38 */       $$0.shrink(1);
/*    */     }
/* 40 */     if ($$0.isEmpty()) {
/* 41 */       return $$2;
/*    */     }
/* 43 */     if (!$$1.getInventory().add($$2)) {
/* 44 */       $$1.drop($$2, false);
/*    */     }
/* 46 */     return $$0;
/*    */   }
/*    */   
/*    */   public static ItemStack createFilledResult(ItemStack $$0, Player $$1, ItemStack $$2) {
/* 50 */     return createFilledResult($$0, $$1, $$2, true);
/*    */   }
/*    */   
/*    */   public static void onContainerDestroyed(ItemEntity $$0, Stream<ItemStack> $$1) {
/* 54 */     Level $$2 = $$0.level();
/* 55 */     if ($$2.isClientSide) {
/*    */       return;
/*    */     }
/*    */     
/* 59 */     $$1.forEach($$2 -> $$0.addFreshEntity((Entity)new ItemEntity($$0, $$1.getX(), $$1.getY(), $$1.getZ(), $$2)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ItemUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */