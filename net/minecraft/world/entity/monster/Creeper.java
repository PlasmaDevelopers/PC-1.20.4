/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.AreaEffectCloud;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PowerableMob;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.SwellGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.animal.Cat;
/*     */ import net.minecraft.world.entity.animal.Ocelot;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ public class Creeper
/*     */   extends Monster implements PowerableMob {
/*  48 */   private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(Creeper.class, EntityDataSerializers.INT);
/*  49 */   private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(Creeper.class, EntityDataSerializers.BOOLEAN);
/*  50 */   private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(Creeper.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private int oldSwell;
/*     */   private int swell;
/*  54 */   private int maxSwell = 30;
/*  55 */   private int explosionRadius = 3;
/*     */   private int droppedSkulls;
/*     */   
/*     */   public Creeper(EntityType<? extends Creeper> $$0, Level $$1) {
/*  59 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  64 */     this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
/*  65 */     this.goalSelector.addGoal(2, (Goal)new SwellGoal(this));
/*  66 */     this.goalSelector.addGoal(3, (Goal)new AvoidEntityGoal(this, Ocelot.class, 6.0F, 1.0D, 1.2D));
/*  67 */     this.goalSelector.addGoal(3, (Goal)new AvoidEntityGoal(this, Cat.class, 6.0F, 1.0D, 1.2D));
/*  68 */     this.goalSelector.addGoal(4, (Goal)new MeleeAttackGoal(this, 1.0D, false));
/*  69 */     this.goalSelector.addGoal(5, (Goal)new WaterAvoidingRandomStrollGoal(this, 0.8D));
/*  70 */     this.goalSelector.addGoal(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/*  71 */     this.goalSelector.addGoal(6, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/*  73 */     this.targetSelector.addGoal(1, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/*  74 */     this.targetSelector.addGoal(2, (Goal)new HurtByTargetGoal(this, new Class[0]));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  78 */     return Monster.createMonsterAttributes()
/*  79 */       .add(Attributes.MOVEMENT_SPEED, 0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxFallDistance() {
/*  84 */     if (getTarget() == null) {
/*  85 */       return 3;
/*     */     }
/*     */     
/*  88 */     return 3 + (int)(getHealth() - 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean causeFallDamage(float $$0, float $$1, DamageSource $$2) {
/*  93 */     boolean $$3 = super.causeFallDamage($$0, $$1, $$2);
/*     */     
/*  95 */     this.swell += (int)($$0 * 1.5F);
/*  96 */     if (this.swell > this.maxSwell - 5) {
/*  97 */       this.swell = this.maxSwell - 5;
/*     */     }
/*  99 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 104 */     super.defineSynchedData();
/*     */     
/* 106 */     this.entityData.define(DATA_SWELL_DIR, Integer.valueOf(-1));
/* 107 */     this.entityData.define(DATA_IS_POWERED, Boolean.valueOf(false));
/* 108 */     this.entityData.define(DATA_IS_IGNITED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 113 */     super.addAdditionalSaveData($$0);
/* 114 */     if (((Boolean)this.entityData.get(DATA_IS_POWERED)).booleanValue()) {
/* 115 */       $$0.putBoolean("powered", true);
/*     */     }
/* 117 */     $$0.putShort("Fuse", (short)this.maxSwell);
/* 118 */     $$0.putByte("ExplosionRadius", (byte)this.explosionRadius);
/* 119 */     $$0.putBoolean("ignited", isIgnited());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 124 */     super.readAdditionalSaveData($$0);
/* 125 */     this.entityData.set(DATA_IS_POWERED, Boolean.valueOf($$0.getBoolean("powered")));
/* 126 */     if ($$0.contains("Fuse", 99)) {
/* 127 */       this.maxSwell = $$0.getShort("Fuse");
/*     */     }
/* 129 */     if ($$0.contains("ExplosionRadius", 99)) {
/* 130 */       this.explosionRadius = $$0.getByte("ExplosionRadius");
/*     */     }
/* 132 */     if ($$0.getBoolean("ignited")) {
/* 133 */       ignite();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 139 */     if (isAlive()) {
/* 140 */       this.oldSwell = this.swell;
/*     */ 
/*     */       
/* 143 */       if (isIgnited()) {
/* 144 */         setSwellDir(1);
/*     */       }
/*     */       
/* 147 */       int $$0 = getSwellDir();
/* 148 */       if ($$0 > 0 && this.swell == 0) {
/* 149 */         playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
/* 150 */         gameEvent(GameEvent.PRIME_FUSE);
/*     */       } 
/* 152 */       this.swell += $$0;
/* 153 */       if (this.swell < 0) {
/* 154 */         this.swell = 0;
/*     */       }
/* 156 */       if (this.swell >= this.maxSwell) {
/* 157 */         this.swell = this.maxSwell;
/* 158 */         explodeCreeper();
/*     */       } 
/*     */     } 
/* 161 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTarget(@Nullable LivingEntity $$0) {
/* 166 */     if ($$0 instanceof net.minecraft.world.entity.animal.goat.Goat) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     super.setTarget($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 175 */     return SoundEvents.CREEPER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 180 */     return SoundEvents.CREEPER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropCustomDeathLoot(DamageSource $$0, int $$1, boolean $$2) {
/* 185 */     super.dropCustomDeathLoot($$0, $$1, $$2);
/* 186 */     Entity $$3 = $$0.getEntity();
/* 187 */     if ($$3 != this && $$3 instanceof Creeper) { Creeper $$4 = (Creeper)$$3;
/* 188 */       if ($$4.canDropMobsSkull()) {
/* 189 */         $$4.increaseDroppedSkulls();
/* 190 */         spawnAtLocation((ItemLike)Items.CREEPER_HEAD);
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 197 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPowered() {
/* 202 */     return ((Boolean)this.entityData.get(DATA_IS_POWERED)).booleanValue();
/*     */   }
/*     */   
/*     */   public float getSwelling(float $$0) {
/* 206 */     return Mth.lerp($$0, this.oldSwell, this.swell) / (this.maxSwell - 2);
/*     */   }
/*     */   
/*     */   public int getSwellDir() {
/* 210 */     return ((Integer)this.entityData.get(DATA_SWELL_DIR)).intValue();
/*     */   }
/*     */   
/*     */   public void setSwellDir(int $$0) {
/* 214 */     this.entityData.set(DATA_SWELL_DIR, Integer.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void thunderHit(ServerLevel $$0, LightningBolt $$1) {
/* 219 */     super.thunderHit($$0, $$1);
/* 220 */     this.entityData.set(DATA_IS_POWERED, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 225 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 226 */     if ($$2.is(ItemTags.CREEPER_IGNITERS)) {
/* 227 */       SoundEvent $$3 = $$2.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
/* 228 */       level().playSound($$0, getX(), getY(), getZ(), $$3, getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
/* 229 */       if (!(level()).isClientSide) {
/* 230 */         ignite();
/* 231 */         if (!$$2.isDamageableItem()) {
/* 232 */           $$2.shrink(1);
/*     */         } else {
/* 234 */           $$2.hurtAndBreak(1, (LivingEntity)$$0, $$1 -> $$1.broadcastBreakEvent($$0));
/*     */         } 
/*     */       } 
/* 237 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */     
/* 240 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */   
/*     */   private void explodeCreeper() {
/* 244 */     if (!(level()).isClientSide) {
/* 245 */       float $$0 = isPowered() ? 2.0F : 1.0F;
/* 246 */       this.dead = true;
/* 247 */       level().explode((Entity)this, getX(), getY(), getZ(), this.explosionRadius * $$0, Level.ExplosionInteraction.MOB);
/* 248 */       discard();
/* 249 */       spawnLingeringCloud();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spawnLingeringCloud() {
/* 254 */     Collection<MobEffectInstance> $$0 = getActiveEffects();
/* 255 */     if (!$$0.isEmpty()) {
/* 256 */       AreaEffectCloud $$1 = new AreaEffectCloud(level(), getX(), getY(), getZ());
/* 257 */       $$1.setRadius(2.5F);
/* 258 */       $$1.setRadiusOnUse(-0.5F);
/* 259 */       $$1.setWaitTime(10);
/* 260 */       $$1.setDuration($$1.getDuration() / 2);
/* 261 */       $$1.setRadiusPerTick(-$$1.getRadius() / $$1.getDuration());
/* 262 */       for (MobEffectInstance $$2 : $$0) {
/* 263 */         $$1.addEffect(new MobEffectInstance($$2));
/*     */       }
/* 265 */       level().addFreshEntity((Entity)$$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isIgnited() {
/* 270 */     return ((Boolean)this.entityData.get(DATA_IS_IGNITED)).booleanValue();
/*     */   }
/*     */   
/*     */   public void ignite() {
/* 274 */     this.entityData.set(DATA_IS_IGNITED, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public boolean canDropMobsSkull() {
/* 278 */     return (isPowered() && this.droppedSkulls < 1);
/*     */   }
/*     */   
/*     */   public void increaseDroppedSkulls() {
/* 282 */     this.droppedSkulls++;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Creeper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */