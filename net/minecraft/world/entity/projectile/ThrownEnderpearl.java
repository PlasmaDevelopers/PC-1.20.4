/*    */ package net.minecraft.world.entity.projectile;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.Endermite;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.phys.EntityHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ 
/*    */ public class ThrownEnderpearl extends ThrowableItemProjectile {
/*    */   public ThrownEnderpearl(EntityType<? extends ThrownEnderpearl> $$0, Level $$1) {
/* 23 */     super((EntityType)$$0, $$1);
/*    */   }
/*    */   
/*    */   public ThrownEnderpearl(Level $$0, LivingEntity $$1) {
/* 27 */     super(EntityType.ENDER_PEARL, $$1, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Item getDefaultItem() {
/* 32 */     return Items.ENDER_PEARL;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHitEntity(EntityHitResult $$0) {
/* 37 */     super.onHitEntity($$0);
/* 38 */     $$0.getEntity().hurt(damageSources().thrown(this, getOwner()), 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onHit(HitResult $$0) {
/* 43 */     super.onHit($$0);
/*    */     
/* 45 */     for (int $$1 = 0; $$1 < 32; $$1++) {
/* 46 */       level().addParticle((ParticleOptions)ParticleTypes.PORTAL, getX(), getY() + this.random.nextDouble() * 2.0D, getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
/*    */     }
/*    */     
/* 49 */     if (!(level()).isClientSide && !isRemoved()) {
/* 50 */       Entity $$2 = getOwner();
/* 51 */       if ($$2 instanceof ServerPlayer) { ServerPlayer $$3 = (ServerPlayer)$$2;
/* 52 */         if ($$3.connection.isAcceptingMessages() && $$3.level() == level() && !$$3.isSleeping()) {
/* 53 */           if (this.random.nextFloat() < 0.05F && level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
/* 54 */             Endermite $$4 = (Endermite)EntityType.ENDERMITE.create(level());
/* 55 */             if ($$4 != null) {
/* 56 */               $$4.moveTo($$2.getX(), $$2.getY(), $$2.getZ(), $$2.getYRot(), $$2.getXRot());
/* 57 */               level().addFreshEntity((Entity)$$4);
/*    */             } 
/*    */           } 
/*    */           
/* 61 */           if ($$2.isPassenger()) {
/* 62 */             $$3.dismountTo(getX(), getY(), getZ());
/*    */           } else {
/* 64 */             $$2.teleportTo(getX(), getY(), getZ());
/*    */           } 
/* 66 */           $$2.resetFallDistance();
/* 67 */           $$2.hurt(damageSources().fall(), 5.0F);
/* 68 */           level().playSound(null, getX(), getY(), getZ(), SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS);
/*    */         }  }
/* 70 */       else if ($$2 != null)
/* 71 */       { $$2.teleportTo(getX(), getY(), getZ());
/* 72 */         $$2.resetFallDistance(); }
/*    */       
/* 74 */       discard();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 80 */     Entity $$0 = getOwner();
/* 81 */     if ($$0 instanceof ServerPlayer && !$$0.isAlive() && level().getGameRules().getBoolean(GameRules.RULE_ENDER_PEARLS_VANISH_ON_DEATH)) {
/* 82 */       discard();
/*    */     } else {
/* 84 */       super.tick();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Entity changeDimension(ServerLevel $$0) {
/* 91 */     Entity $$1 = getOwner();
/* 92 */     if ($$1 != null && $$1.level().dimension() != $$0.dimension()) {
/* 93 */       setOwner((Entity)null);
/*    */     }
/* 95 */     return super.changeDimension($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\ThrownEnderpearl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */