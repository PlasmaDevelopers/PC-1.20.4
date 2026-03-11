/*    */ package net.minecraft.world.entity.projectile;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.BaseFireBlock;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.EntityHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ 
/*    */ public class SmallFireball extends Fireball {
/*    */   public SmallFireball(EntityType<? extends SmallFireball> $$0, Level $$1) {
/* 18 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public SmallFireball(Level $$0, LivingEntity $$1, double $$2, double $$3, double $$4) {
/* 22 */     super(EntityType.SMALL_FIREBALL, $$1, $$2, $$3, $$4, $$0);
/*    */   }
/*    */   
/*    */   public SmallFireball(Level $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/* 26 */     super(EntityType.SMALL_FIREBALL, $$1, $$2, $$3, $$4, $$5, $$6, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHitEntity(EntityHitResult $$0) {
/* 31 */     super.onHitEntity($$0);
/* 32 */     if ((level()).isClientSide) {
/*    */       return;
/*    */     }
/* 35 */     Entity $$1 = $$0.getEntity();
/* 36 */     Entity $$2 = getOwner();
/* 37 */     int $$3 = $$1.getRemainingFireTicks();
/* 38 */     $$1.setSecondsOnFire(5);
/* 39 */     if (!$$1.hurt(damageSources().fireball(this, $$2), 5.0F)) {
/*    */ 
/*    */       
/* 42 */       $$1.setRemainingFireTicks($$3);
/* 43 */     } else if ($$2 instanceof LivingEntity) {
/* 44 */       doEnchantDamageEffects((LivingEntity)$$2, $$1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHitBlock(BlockHitResult $$0) {
/* 50 */     super.onHitBlock($$0);
/* 51 */     if ((level()).isClientSide) {
/*    */       return;
/*    */     }
/* 54 */     Entity $$1 = getOwner();
/* 55 */     if (!($$1 instanceof net.minecraft.world.entity.Mob) || level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 56 */       BlockPos $$2 = $$0.getBlockPos().relative($$0.getDirection());
/* 57 */       if (level().isEmptyBlock($$2)) {
/* 58 */         level().setBlockAndUpdate($$2, BaseFireBlock.getState((BlockGetter)level(), $$2));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHit(HitResult $$0) {
/* 65 */     super.onHit($$0);
/* 66 */     if (!(level()).isClientSide) {
/* 67 */       discard();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPickable() {
/* 73 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hurt(DamageSource $$0, float $$1) {
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\SmallFireball.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */