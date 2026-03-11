/*    */ package net.minecraft.world.entity.projectile;
/*    */ 
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.EntityHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ 
/*    */ public class LargeFireball
/*    */   extends Fireball {
/* 14 */   private int explosionPower = 1;
/*    */   
/*    */   public LargeFireball(EntityType<? extends LargeFireball> $$0, Level $$1) {
/* 17 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public LargeFireball(Level $$0, LivingEntity $$1, double $$2, double $$3, double $$4, int $$5) {
/* 21 */     super(EntityType.FIREBALL, $$1, $$2, $$3, $$4, $$0);
/* 22 */     this.explosionPower = $$5;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHit(HitResult $$0) {
/* 27 */     super.onHit($$0);
/* 28 */     if (!(level()).isClientSide) {
/* 29 */       boolean $$1 = level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
/* 30 */       level().explode(this, getX(), getY(), getZ(), this.explosionPower, $$1, Level.ExplosionInteraction.MOB);
/* 31 */       discard();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHitEntity(EntityHitResult $$0) {
/* 37 */     super.onHitEntity($$0);
/* 38 */     if ((level()).isClientSide) {
/*    */       return;
/*    */     }
/* 41 */     Entity $$1 = $$0.getEntity();
/* 42 */     Entity $$2 = getOwner();
/* 43 */     $$1.hurt(damageSources().fireball(this, $$2), 6.0F);
/* 44 */     if ($$2 instanceof LivingEntity) {
/* 45 */       doEnchantDamageEffects((LivingEntity)$$2, $$1);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 51 */     super.addAdditionalSaveData($$0);
/* 52 */     $$0.putByte("ExplosionPower", (byte)this.explosionPower);
/*    */   }
/*    */ 
/*    */   
/*    */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 57 */     super.readAdditionalSaveData($$0);
/* 58 */     if ($$0.contains("ExplosionPower", 99))
/* 59 */       this.explosionPower = $$0.getByte("ExplosionPower"); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\LargeFireball.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */