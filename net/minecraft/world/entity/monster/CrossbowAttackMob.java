/*    */ package net.minecraft.world.entity.monster;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.projectile.Projectile;
/*    */ import net.minecraft.world.entity.projectile.ProjectileUtil;
/*    */ import net.minecraft.world.item.CrossbowItem;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import org.joml.Vector3f;
/*    */ import org.joml.Vector3fc;
/*    */ 
/*    */ public interface CrossbowAttackMob
/*    */   extends RangedAttackMob {
/*    */   void setChargingCrossbow(boolean paramBoolean);
/*    */   
/*    */   void shootCrossbowProjectile(LivingEntity paramLivingEntity, ItemStack paramItemStack, Projectile paramProjectile, float paramFloat);
/*    */   
/*    */   @Nullable
/*    */   LivingEntity getTarget();
/*    */   
/*    */   void onCrossbowAttackPerformed();
/*    */   
/*    */   default void performCrossbowAttack(LivingEntity $$0, float $$1) {
/* 28 */     InteractionHand $$2 = ProjectileUtil.getWeaponHoldingHand($$0, Items.CROSSBOW);
/* 29 */     ItemStack $$3 = $$0.getItemInHand($$2);
/* 30 */     if ($$0.isHolding(Items.CROSSBOW)) {
/* 31 */       CrossbowItem.performShooting($$0.level(), $$0, $$2, $$3, $$1, (14 - $$0.level().getDifficulty().getId() * 4));
/*    */     }
/* 33 */     onCrossbowAttackPerformed();
/*    */   }
/*    */   
/*    */   default void shootCrossbowProjectile(LivingEntity $$0, LivingEntity $$1, Projectile $$2, float $$3, float $$4) {
/* 37 */     double $$5 = $$1.getX() - $$0.getX();
/* 38 */     double $$6 = $$1.getZ() - $$0.getZ();
/* 39 */     double $$7 = Math.sqrt($$5 * $$5 + $$6 * $$6);
/* 40 */     double $$8 = $$1.getY(0.3333333333333333D) - $$2.getY() + $$7 * 0.20000000298023224D;
/*    */     
/* 42 */     Vector3f $$9 = getProjectileShotVector($$0, new Vec3($$5, $$8, $$6), $$3);
/* 43 */     $$2.shoot($$9.x(), $$9.y(), $$9.z(), $$4, (14 - $$0.level().getDifficulty().getId() * 4));
/* 44 */     $$0.playSound(SoundEvents.CROSSBOW_SHOOT, 1.0F, 1.0F / ($$0.getRandom().nextFloat() * 0.4F + 0.8F));
/*    */   }
/*    */   
/*    */   default Vector3f getProjectileShotVector(LivingEntity $$0, Vec3 $$1, float $$2) {
/* 48 */     Vector3f $$3 = $$1.toVector3f().normalize();
/* 49 */     Vector3f $$4 = (new Vector3f((Vector3fc)$$3)).cross((Vector3fc)new Vector3f(0.0F, 1.0F, 0.0F));
/* 50 */     if ($$4.lengthSquared() <= 1.0E-7D) {
/* 51 */       Vec3 $$5 = $$0.getUpVector(1.0F);
/* 52 */       $$4 = (new Vector3f((Vector3fc)$$3)).cross((Vector3fc)$$5.toVector3f());
/*    */     } 
/*    */     
/* 55 */     Vector3f $$6 = (new Vector3f((Vector3fc)$$3)).rotateAxis(1.5707964F, $$4.x, $$4.y, $$4.z);
/* 56 */     return (new Vector3f((Vector3fc)$$3)).rotateAxis($$2 * 0.017453292F, $$6.x, $$6.y, $$6.z);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\CrossbowAttackMob.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */