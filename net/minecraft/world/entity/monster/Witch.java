/*     */ package net.minecraft.world.entity.monster;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableWitchTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestHealableRaiderTargetGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.ThrownPotion;
/*     */ import net.minecraft.world.entity.raid.Raider;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potion;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Witch extends Raider implements RangedAttackMob {
/*  51 */   private static final UUID SPEED_MODIFIER_DRINKING_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
/*  52 */   private static final AttributeModifier SPEED_MODIFIER_DRINKING = new AttributeModifier(SPEED_MODIFIER_DRINKING_UUID, "Drinking speed penalty", -0.25D, AttributeModifier.Operation.ADDITION);
/*     */   
/*  54 */   private static final EntityDataAccessor<Boolean> DATA_USING_ITEM = SynchedEntityData.defineId(Witch.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private int usingTime;
/*     */   
/*     */   private NearestHealableRaiderTargetGoal<Raider> healRaidersGoal;
/*     */   private NearestAttackableWitchTargetGoal<Player> attackPlayersGoal;
/*     */   
/*     */   public Witch(EntityType<? extends Witch> $$0, Level $$1) {
/*  62 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  67 */     super.registerGoals();
/*     */ 
/*     */     
/*  70 */     this.healRaidersGoal = new NearestHealableRaiderTargetGoal(this, Raider.class, true, $$0 -> ($$0 != null && hasActiveRaid() && $$0.getType() != EntityType.WITCH));
/*  71 */     this.attackPlayersGoal = new NearestAttackableWitchTargetGoal(this, Player.class, 10, true, false, null);
/*     */     
/*  73 */     this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
/*  74 */     this.goalSelector.addGoal(2, (Goal)new RangedAttackGoal(this, 1.0D, 60, 10.0F));
/*  75 */     this.goalSelector.addGoal(2, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0D));
/*  76 */     this.goalSelector.addGoal(3, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/*  77 */     this.goalSelector.addGoal(3, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/*  79 */     this.targetSelector.addGoal(1, (Goal)new HurtByTargetGoal((PathfinderMob)this, new Class[] { Raider.class }));
/*  80 */     this.targetSelector.addGoal(2, (Goal)this.healRaidersGoal);
/*  81 */     this.targetSelector.addGoal(3, (Goal)this.attackPlayersGoal);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  86 */     super.defineSynchedData();
/*     */     
/*  88 */     getEntityData().define(DATA_USING_ITEM, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  93 */     return SoundEvents.WITCH_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  98 */     return SoundEvents.WITCH_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 103 */     return SoundEvents.WITCH_DEATH;
/*     */   }
/*     */   
/*     */   public void setUsingItem(boolean $$0) {
/* 107 */     getEntityData().set(DATA_USING_ITEM, Boolean.valueOf($$0));
/*     */   }
/*     */   
/*     */   public boolean isDrinkingPotion() {
/* 111 */     return ((Boolean)getEntityData().get(DATA_USING_ITEM)).booleanValue();
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 115 */     return Monster.createMonsterAttributes()
/* 116 */       .add(Attributes.MAX_HEALTH, 26.0D)
/* 117 */       .add(Attributes.MOVEMENT_SPEED, 0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 122 */     if (!(level()).isClientSide && isAlive()) {
/* 123 */       this.healRaidersGoal.decrementCooldown();
/*     */       
/* 125 */       if (this.healRaidersGoal.getCooldown() <= 0) {
/* 126 */         this.attackPlayersGoal.setCanAttack(true);
/*     */       } else {
/* 128 */         this.attackPlayersGoal.setCanAttack(false);
/*     */       } 
/*     */       
/* 131 */       if (isDrinkingPotion()) {
/* 132 */         if (this.usingTime-- <= 0) {
/* 133 */           setUsingItem(false);
/* 134 */           ItemStack $$0 = getMainHandItem();
/* 135 */           setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/*     */           
/* 137 */           if ($$0.is(Items.POTION)) {
/* 138 */             List<MobEffectInstance> $$1 = PotionUtils.getMobEffects($$0);
/* 139 */             if ($$1 != null) {
/* 140 */               for (MobEffectInstance $$2 : $$1) {
/* 141 */                 addEffect(new MobEffectInstance($$2));
/*     */               }
/*     */             }
/*     */           } 
/* 145 */           gameEvent(GameEvent.DRINK);
/* 146 */           getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_DRINKING.getId());
/*     */         } 
/*     */       } else {
/* 149 */         Potion $$3 = null;
/*     */         
/* 151 */         if (this.random.nextFloat() < 0.15F && isEyeInFluid(FluidTags.WATER) && !hasEffect(MobEffects.WATER_BREATHING)) {
/* 152 */           $$3 = Potions.WATER_BREATHING;
/* 153 */         } else if (this.random.nextFloat() < 0.15F && (isOnFire() || (getLastDamageSource() != null && getLastDamageSource().is(DamageTypeTags.IS_FIRE))) && !hasEffect(MobEffects.FIRE_RESISTANCE)) {
/* 154 */           $$3 = Potions.FIRE_RESISTANCE;
/* 155 */         } else if (this.random.nextFloat() < 0.05F && getHealth() < getMaxHealth()) {
/* 156 */           $$3 = Potions.HEALING;
/* 157 */         } else if (this.random.nextFloat() < 0.5F && getTarget() != null && !hasEffect(MobEffects.MOVEMENT_SPEED) && getTarget().distanceToSqr((Entity)this) > 121.0D) {
/* 158 */           $$3 = Potions.SWIFTNESS;
/*     */         } 
/*     */         
/* 161 */         if ($$3 != null) {
/* 162 */           setItemSlot(EquipmentSlot.MAINHAND, PotionUtils.setPotion(new ItemStack((ItemLike)Items.POTION), $$3));
/* 163 */           this.usingTime = getMainHandItem().getUseDuration();
/* 164 */           setUsingItem(true);
/* 165 */           if (!isSilent()) {
/* 166 */             level().playSound(null, getX(), getY(), getZ(), SoundEvents.WITCH_DRINK, getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
/*     */           }
/* 168 */           AttributeInstance $$4 = getAttribute(Attributes.MOVEMENT_SPEED);
/* 169 */           $$4.removeModifier(SPEED_MODIFIER_DRINKING.getId());
/* 170 */           $$4.addTransientModifier(SPEED_MODIFIER_DRINKING);
/*     */         } 
/*     */       } 
/*     */       
/* 174 */       if (this.random.nextFloat() < 7.5E-4F) {
/* 175 */         level().broadcastEntityEvent((Entity)this, (byte)15);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     super.aiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getCelebrateSound() {
/* 184 */     return SoundEvents.WITCH_CELEBRATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 189 */     if ($$0 == 15) {
/* 190 */       for (int $$1 = 0; $$1 < this.random.nextInt(35) + 10; $$1++) {
/* 191 */         level().addParticle((ParticleOptions)ParticleTypes.WITCH, getX() + this.random.nextGaussian() * 0.12999999523162842D, (getBoundingBox()).maxY + 0.5D + this.random.nextGaussian() * 0.12999999523162842D, getZ() + this.random.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D);
/*     */       }
/*     */     } else {
/* 194 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getDamageAfterMagicAbsorb(DamageSource $$0, float $$1) {
/* 200 */     $$1 = super.getDamageAfterMagicAbsorb($$0, $$1);
/*     */     
/* 202 */     if ($$0.getEntity() == this) {
/* 203 */       $$1 = 0.0F;
/*     */     }
/* 205 */     if ($$0.is(DamageTypeTags.WITCH_RESISTANT_TO)) {
/* 206 */       $$1 *= 0.15F;
/*     */     }
/*     */     
/* 209 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performRangedAttack(LivingEntity $$0, float $$1) {
/* 214 */     if (isDrinkingPotion()) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     Vec3 $$2 = $$0.getDeltaMovement();
/* 219 */     double $$3 = $$0.getX() + $$2.x - getX();
/* 220 */     double $$4 = $$0.getEyeY() - 1.100000023841858D - getY();
/* 221 */     double $$5 = $$0.getZ() + $$2.z - getZ();
/* 222 */     double $$6 = Math.sqrt($$3 * $$3 + $$5 * $$5);
/* 223 */     Potion $$7 = Potions.HARMING;
/*     */ 
/*     */     
/* 226 */     if ($$0 instanceof Raider) {
/* 227 */       if ($$0.getHealth() <= 4.0F) {
/* 228 */         $$7 = Potions.HEALING;
/*     */       } else {
/* 230 */         $$7 = Potions.REGENERATION;
/*     */       } 
/* 232 */       setTarget(null);
/*     */     }
/* 234 */     else if ($$6 >= 8.0D && !$$0.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
/* 235 */       $$7 = Potions.SLOWNESS;
/* 236 */     } else if ($$0.getHealth() >= 8.0F && !$$0.hasEffect(MobEffects.POISON)) {
/* 237 */       $$7 = Potions.POISON;
/* 238 */     } else if ($$6 <= 3.0D && !$$0.hasEffect(MobEffects.WEAKNESS) && this.random.nextFloat() < 0.25F) {
/* 239 */       $$7 = Potions.WEAKNESS;
/*     */     } 
/*     */ 
/*     */     
/* 243 */     ThrownPotion $$8 = new ThrownPotion(level(), (LivingEntity)this);
/* 244 */     $$8.setItem(PotionUtils.setPotion(new ItemStack((ItemLike)Items.SPLASH_POTION), $$7));
/* 245 */     $$8.setXRot($$8.getXRot() - -20.0F);
/* 246 */     $$8.shoot($$3, $$4 + $$6 * 0.2D, $$5, 0.75F, 8.0F);
/* 247 */     if (!isSilent()) {
/* 248 */       level().playSound(null, getX(), getY(), getZ(), SoundEvents.WITCH_THROW, getSoundSource(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
/*     */     }
/*     */     
/* 251 */     level().addFreshEntity((Entity)$$8);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 256 */     return 1.62F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 261 */     return new Vector3f(0.0F, $$1.height + 0.3125F * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyRaidBuffs(int $$0, boolean $$1) {}
/*     */ 
/*     */   
/*     */   public boolean canBeLeader() {
/* 270 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Witch.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */