/*    */ package net.minecraft.world.entity.vehicle;
/*    */ 
/*    */ import net.minecraft.network.syncher.EntityDataAccessor;
/*    */ import net.minecraft.network.syncher.EntityDataSerializers;
/*    */ import net.minecraft.network.syncher.SynchedEntityData;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.GameRules;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public abstract class VehicleEntity extends Entity {
/* 18 */   protected static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(VehicleEntity.class, EntityDataSerializers.INT);
/* 19 */   protected static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(VehicleEntity.class, EntityDataSerializers.INT);
/* 20 */   protected static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(VehicleEntity.class, EntityDataSerializers.FLOAT);
/*    */   
/*    */   public VehicleEntity(EntityType<?> $$0, Level $$1) {
/* 23 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hurt(DamageSource $$0, float $$1) {
/* 28 */     if ((level()).isClientSide || isRemoved()) {
/* 29 */       return true;
/*    */     }
/* 31 */     if (isInvulnerableTo($$0)) {
/* 32 */       return false;
/*    */     }
/* 34 */     setHurtDir(-getHurtDir());
/* 35 */     setHurtTime(10);
/* 36 */     markHurt();
/* 37 */     setDamage(getDamage() + $$1 * 10.0F);
/* 38 */     gameEvent(GameEvent.ENTITY_DAMAGE, $$0.getEntity());
/* 39 */     boolean $$2 = ($$0.getEntity() instanceof Player && (((Player)$$0.getEntity()).getAbilities()).instabuild);
/*    */     
/* 41 */     if ((!$$2 && getDamage() > 40.0F) || shouldSourceDestroy($$0)) {
/* 42 */       destroy($$0);
/* 43 */     } else if ($$2) {
/* 44 */       discard();
/*    */     } 
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   boolean shouldSourceDestroy(DamageSource $$0) {
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public void destroy(Item $$0) {
/* 54 */     kill();
/*    */     
/* 56 */     if (!level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
/*    */       return;
/*    */     }
/*    */     
/* 60 */     ItemStack $$1 = new ItemStack((ItemLike)$$0);
/* 61 */     if (hasCustomName()) {
/* 62 */       $$1.setHoverName(getCustomName());
/*    */     }
/* 64 */     spawnAtLocation($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void defineSynchedData() {
/* 69 */     this.entityData.define(DATA_ID_HURT, Integer.valueOf(0));
/* 70 */     this.entityData.define(DATA_ID_HURTDIR, Integer.valueOf(1));
/* 71 */     this.entityData.define(DATA_ID_DAMAGE, Float.valueOf(0.0F));
/*    */   }
/*    */   
/*    */   public void setHurtTime(int $$0) {
/* 75 */     this.entityData.set(DATA_ID_HURT, Integer.valueOf($$0));
/*    */   }
/*    */   
/*    */   public void setHurtDir(int $$0) {
/* 79 */     this.entityData.set(DATA_ID_HURTDIR, Integer.valueOf($$0));
/*    */   }
/*    */   
/*    */   public void setDamage(float $$0) {
/* 83 */     this.entityData.set(DATA_ID_DAMAGE, Float.valueOf($$0));
/*    */   }
/*    */   
/*    */   public float getDamage() {
/* 87 */     return ((Float)this.entityData.get(DATA_ID_DAMAGE)).floatValue();
/*    */   }
/*    */   
/*    */   public int getHurtTime() {
/* 91 */     return ((Integer)this.entityData.get(DATA_ID_HURT)).intValue();
/*    */   }
/*    */   
/*    */   public int getHurtDir() {
/* 95 */     return ((Integer)this.entityData.get(DATA_ID_HURTDIR)).intValue();
/*    */   }
/*    */   
/*    */   protected void destroy(DamageSource $$0) {
/* 99 */     destroy(getDropItem());
/*    */   }
/*    */   
/*    */   abstract Item getDropItem();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\VehicleEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */