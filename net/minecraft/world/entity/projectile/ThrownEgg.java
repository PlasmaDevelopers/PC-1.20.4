/*    */ package net.minecraft.world.entity.projectile;
/*    */ import net.minecraft.core.particles.ItemParticleOption;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Chicken;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.EntityHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ 
/*    */ public class ThrownEgg extends ThrowableItemProjectile {
/*    */   public ThrownEgg(EntityType<? extends ThrownEgg> $$0, Level $$1) {
/* 17 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public ThrownEgg(Level $$0, LivingEntity $$1) {
/* 21 */     super(EntityType.EGG, $$1, $$0);
/*    */   }
/*    */   
/*    */   public ThrownEgg(Level $$0, double $$1, double $$2, double $$3) {
/* 25 */     super(EntityType.EGG, $$1, $$2, $$3, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleEntityEvent(byte $$0) {
/* 30 */     if ($$0 == 3) {
/* 31 */       double $$1 = 0.08D;
/* 32 */       for (int $$2 = 0; $$2 < 8; $$2++) {
/* 33 */         level().addParticle((ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, getItem()), getX(), getY(), getZ(), (this.random.nextFloat() - 0.5D) * 0.08D, (this.random.nextFloat() - 0.5D) * 0.08D, (this.random.nextFloat() - 0.5D) * 0.08D);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHitEntity(EntityHitResult $$0) {
/* 40 */     super.onHitEntity($$0);
/* 41 */     $$0.getEntity().hurt(damageSources().thrown(this, getOwner()), 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHit(HitResult $$0) {
/* 46 */     super.onHit($$0);
/*    */     
/* 48 */     if (!(level()).isClientSide) {
/* 49 */       if (this.random.nextInt(8) == 0) {
/* 50 */         int $$1 = 1;
/* 51 */         if (this.random.nextInt(32) == 0) {
/* 52 */           $$1 = 4;
/*    */         }
/* 54 */         for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 55 */           Chicken $$3 = (Chicken)EntityType.CHICKEN.create(level());
/* 56 */           if ($$3 != null) {
/* 57 */             $$3.setAge(-24000);
/* 58 */             $$3.moveTo(getX(), getY(), getZ(), getYRot(), 0.0F);
/* 59 */             level().addFreshEntity((Entity)$$3);
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 64 */       level().broadcastEntityEvent(this, (byte)3);
/* 65 */       discard();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getDefaultItem() {
/* 71 */     return Items.EGG;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\ThrownEgg.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */