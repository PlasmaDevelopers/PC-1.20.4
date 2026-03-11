/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class NameTagItem extends Item {
/*    */   public NameTagItem(Item.Properties $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult interactLivingEntity(ItemStack $$0, Player $$1, LivingEntity $$2, InteractionHand $$3) {
/* 16 */     if ($$0.hasCustomHoverName() && !($$2 instanceof Player)) {
/* 17 */       if (!($$1.level()).isClientSide && $$2.isAlive()) {
/* 18 */         $$2.setCustomName($$0.getHoverName());
/* 19 */         if ($$2 instanceof Mob) {
/* 20 */           ((Mob)$$2).setPersistenceRequired();
/*    */         }
/*    */         
/* 23 */         $$0.shrink(1);
/*    */       } 
/*    */       
/* 26 */       return InteractionResult.sidedSuccess(($$1.level()).isClientSide);
/*    */     } 
/* 28 */     return InteractionResult.PASS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\NameTagItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */