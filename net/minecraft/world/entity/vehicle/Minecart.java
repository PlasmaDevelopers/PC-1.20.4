/*    */ package net.minecraft.world.entity.vehicle;
/*    */ 
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class Minecart extends AbstractMinecart {
/*    */   public Minecart(EntityType<?> $$0, Level $$1) {
/* 13 */     super($$0, $$1);
/*    */   }
/*    */   
/*    */   public Minecart(Level $$0, double $$1, double $$2, double $$3) {
/* 17 */     super(EntityType.MINECART, $$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/* 22 */     if ($$0.isSecondaryUseActive()) {
/* 23 */       return InteractionResult.PASS;
/*    */     }
/*    */     
/* 26 */     if (isVehicle()) {
/* 27 */       return InteractionResult.PASS;
/*    */     }
/* 29 */     if (!(level()).isClientSide) {
/* 30 */       return $$0.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
/*    */     }
/*    */     
/* 33 */     return InteractionResult.SUCCESS;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getDropItem() {
/* 38 */     return Items.MINECART;
/*    */   }
/*    */ 
/*    */   
/*    */   public void activateMinecart(int $$0, int $$1, int $$2, boolean $$3) {
/* 43 */     if ($$3) {
/* 44 */       if (isVehicle()) {
/* 45 */         ejectPassengers();
/*    */       }
/* 47 */       if (getHurtTime() == 0) {
/* 48 */         setHurtDir(-getHurtDir());
/* 49 */         setHurtTime(10);
/* 50 */         setDamage(50.0F);
/* 51 */         markHurt();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public AbstractMinecart.Type getMinecartType() {
/* 58 */     return AbstractMinecart.Type.RIDEABLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\Minecart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */