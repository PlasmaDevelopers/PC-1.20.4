/*    */ package net.minecraft.world.entity.projectile;
/*    */ 
/*    */ import net.minecraft.core.particles.ItemParticleOption;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.EntityHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ 
/*    */ public class Snowball
/*    */   extends ThrowableItemProjectile
/*    */ {
/*    */   public Snowball(EntityType<? extends Snowball> $$0, Level $$1) {
/* 20 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public Snowball(Level $$0, LivingEntity $$1) {
/* 24 */     super(EntityType.SNOWBALL, $$1, $$0);
/*    */   }
/*    */   
/*    */   public Snowball(Level $$0, double $$1, double $$2, double $$3) {
/* 28 */     super(EntityType.SNOWBALL, $$1, $$2, $$3, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getDefaultItem() {
/* 33 */     return Items.SNOWBALL;
/*    */   }
/*    */   
/*    */   private ParticleOptions getParticle() {
/* 37 */     ItemStack $$0 = getItemRaw();
/* 38 */     return $$0.isEmpty() ? (ParticleOptions)ParticleTypes.ITEM_SNOWBALL : (ParticleOptions)new ItemParticleOption(ParticleTypes.ITEM, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void handleEntityEvent(byte $$0) {
/* 43 */     if ($$0 == 3) {
/* 44 */       ParticleOptions $$1 = getParticle();
/* 45 */       for (int $$2 = 0; $$2 < 8; $$2++) {
/* 46 */         level().addParticle($$1, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHitEntity(EntityHitResult $$0) {
/* 53 */     super.onHitEntity($$0);
/* 54 */     Entity $$1 = $$0.getEntity();
/* 55 */     int $$2 = ($$1 instanceof net.minecraft.world.entity.monster.Blaze) ? 3 : 0;
/*    */     
/* 57 */     $$1.hurt(damageSources().thrown(this, getOwner()), $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHit(HitResult $$0) {
/* 62 */     super.onHit($$0);
/*    */     
/* 64 */     if (!(level()).isClientSide) {
/* 65 */       level().broadcastEntityEvent(this, (byte)3);
/* 66 */       discard();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\Snowball.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */