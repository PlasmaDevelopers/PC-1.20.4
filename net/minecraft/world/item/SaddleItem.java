/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Saddleable;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class SaddleItem extends Item {
/*    */   public SaddleItem(Item.Properties $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult interactLivingEntity(ItemStack $$0, Player $$1, LivingEntity $$2, InteractionHand $$3) {
/* 18 */     if ($$2 instanceof Saddleable) { Saddleable $$4 = (Saddleable)$$2; if ($$2.isAlive() && 
/* 19 */         !$$4.isSaddled() && $$4.isSaddleable()) {
/* 20 */         if (!($$1.level()).isClientSide) {
/* 21 */           $$4.equipSaddle(SoundSource.NEUTRAL);
/* 22 */           $$2.level().gameEvent((Entity)$$2, GameEvent.EQUIP, $$2.position());
/* 23 */           $$0.shrink(1);
/*    */         } 
/* 25 */         return InteractionResult.sidedSuccess(($$1.level()).isClientSide);
/*    */       }  }
/*    */     
/* 28 */     return InteractionResult.PASS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SaddleItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */