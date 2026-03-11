/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ChorusFruitItem
/*    */   extends Item {
/*    */   public ChorusFruitItem(Item.Properties $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack finishUsingItem(ItemStack $$0, Level $$1, LivingEntity $$2) {
/* 23 */     ItemStack $$3 = super.finishUsingItem($$0, $$1, $$2);
/*    */     
/* 25 */     if (!$$1.isClientSide) {
/* 26 */       for (int $$4 = 0; $$4 < 16; $$4++) {
/* 27 */         double $$5 = $$2.getX() + ($$2.getRandom().nextDouble() - 0.5D) * 16.0D;
/* 28 */         double $$6 = Mth.clamp($$2.getY() + ($$2.getRandom().nextInt(16) - 8), $$1.getMinBuildHeight(), ($$1.getMinBuildHeight() + ((ServerLevel)$$1).getLogicalHeight() - 1));
/* 29 */         double $$7 = $$2.getZ() + ($$2.getRandom().nextDouble() - 0.5D) * 16.0D;
/* 30 */         if ($$2.isPassenger()) {
/* 31 */           $$2.stopRiding();
/*    */         }
/* 33 */         Vec3 $$8 = $$2.position();
/* 34 */         if ($$2.randomTeleport($$5, $$6, $$7, true)) {
/* 35 */           SoundSource $$12; SoundEvent $$11; $$1.gameEvent(GameEvent.TELEPORT, $$8, GameEvent.Context.of((Entity)$$2));
/*    */ 
/*    */ 
/*    */           
/* 39 */           if ($$2 instanceof net.minecraft.world.entity.animal.Fox) {
/* 40 */             SoundEvent $$9 = SoundEvents.FOX_TELEPORT;
/* 41 */             SoundSource $$10 = SoundSource.NEUTRAL;
/*    */           } else {
/* 43 */             $$11 = SoundEvents.CHORUS_FRUIT_TELEPORT;
/* 44 */             $$12 = SoundSource.PLAYERS;
/*    */           } 
/* 46 */           $$1.playSound(null, $$2.getX(), $$2.getY(), $$2.getZ(), $$11, $$12);
/* 47 */           $$2.resetFallDistance();
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 52 */       if ($$2 instanceof Player) { Player $$13 = (Player)$$2;
/* 53 */         $$13.getCooldowns().addCooldown(this, 20); }
/*    */     
/*    */     } 
/*    */     
/* 57 */     return $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ChorusFruitItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */