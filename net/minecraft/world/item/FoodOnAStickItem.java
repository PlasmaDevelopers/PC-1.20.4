/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.ItemSteerable;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class FoodOnAStickItem<T extends Entity & ItemSteerable> extends Item {
/*    */   private final EntityType<T> canInteractWith;
/*    */   private final int consumeItemDamage;
/*    */   
/*    */   public FoodOnAStickItem(Item.Properties $$0, EntityType<T> $$1, int $$2) {
/* 17 */     super($$0);
/*    */     
/* 19 */     this.canInteractWith = $$1;
/* 20 */     this.consumeItemDamage = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 25 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 26 */     if ($$0.isClientSide) {
/* 27 */       return InteractionResultHolder.pass($$3);
/*    */     }
/*    */     
/* 30 */     Entity $$4 = $$1.getControlledVehicle();
/*    */     
/* 32 */     if ($$1.isPassenger() && $$4 instanceof ItemSteerable) { ItemSteerable $$5 = (ItemSteerable)$$4; if ($$4.getType() == this.canInteractWith && 
/* 33 */         $$5.boost()) {
/* 34 */         $$3.hurtAndBreak(this.consumeItemDamage, $$1, $$1 -> $$1.broadcastBreakEvent($$0));
/* 35 */         if ($$3.isEmpty()) {
/* 36 */           ItemStack $$6 = new ItemStack(Items.FISHING_ROD);
/* 37 */           $$6.setTag($$3.getTag());
/* 38 */           return InteractionResultHolder.success($$6);
/*    */         } 
/* 40 */         return InteractionResultHolder.success($$3);
/*    */       }  }
/*    */ 
/*    */     
/* 44 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/*    */     
/* 46 */     return InteractionResultHolder.pass($$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\FoodOnAStickItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */