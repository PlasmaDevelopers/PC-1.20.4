/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.time.LocalDate;
/*     */ import java.time.temporal.ChronoField;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FleeSunGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.animal.Turtle;
/*     */ import net.minecraft.world.entity.animal.Wolf;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.entity.projectile.ProjectileUtil;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.ProjectileWeaponItem;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public abstract class AbstractSkeleton extends Monster implements RangedAttackMob {
/*  53 */   private final RangedBowAttackGoal<AbstractSkeleton> bowGoal = new RangedBowAttackGoal(this, 1.0D, 20, 15.0F);
/*  54 */   private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2D, false)
/*     */     {
/*     */       public void stop() {
/*  57 */         super.stop();
/*  58 */         AbstractSkeleton.this.setAggressive(false);
/*     */       }
/*     */ 
/*     */       
/*     */       public void start() {
/*  63 */         super.start();
/*  64 */         AbstractSkeleton.this.setAggressive(true);
/*     */       }
/*     */     };
/*     */   
/*     */   protected AbstractSkeleton(EntityType<? extends AbstractSkeleton> $$0, Level $$1) {
/*  69 */     super((EntityType)$$0, $$1);
/*     */     
/*  71 */     reassessWeaponGoal();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  76 */     this.goalSelector.addGoal(2, (Goal)new RestrictSunGoal(this));
/*  77 */     this.goalSelector.addGoal(3, (Goal)new FleeSunGoal(this, 1.0D));
/*  78 */     this.goalSelector.addGoal(3, (Goal)new AvoidEntityGoal(this, Wolf.class, 6.0F, 1.0D, 1.2D));
/*  79 */     this.goalSelector.addGoal(5, (Goal)new WaterAvoidingRandomStrollGoal(this, 1.0D));
/*  80 */     this.goalSelector.addGoal(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 8.0F));
/*  81 */     this.goalSelector.addGoal(6, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */     
/*  83 */     this.targetSelector.addGoal(1, (Goal)new HurtByTargetGoal(this, new Class[0]));
/*  84 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/*  85 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, IronGolem.class, true));
/*  86 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  90 */     return Monster.createMonsterAttributes()
/*  91 */       .add(Attributes.MOVEMENT_SPEED, 0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/*  96 */     playSound(getStepSound(), 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   abstract SoundEvent getStepSound();
/*     */   
/*     */   public MobType getMobType() {
/* 103 */     return MobType.UNDEAD;
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 108 */     boolean $$0 = isSunBurnTick();
/* 109 */     if ($$0) {
/* 110 */       ItemStack $$1 = getItemBySlot(EquipmentSlot.HEAD);
/* 111 */       if (!$$1.isEmpty()) {
/* 112 */         if ($$1.isDamageableItem()) {
/* 113 */           $$1.setDamageValue($$1.getDamageValue() + this.random.nextInt(2));
/* 114 */           if ($$1.getDamageValue() >= $$1.getMaxDamage()) {
/* 115 */             broadcastBreakEvent(EquipmentSlot.HEAD);
/* 116 */             setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
/*     */           } 
/*     */         } 
/*     */         
/* 120 */         $$0 = false;
/*     */       } 
/*     */       
/* 123 */       if ($$0) {
/* 124 */         setSecondsOnFire(8);
/*     */       }
/*     */     } 
/*     */     
/* 128 */     super.aiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public void rideTick() {
/* 133 */     super.rideTick();
/*     */     
/* 135 */     Entity entity = getControlledVehicle(); if (entity instanceof PathfinderMob) { PathfinderMob $$0 = (PathfinderMob)entity;
/* 136 */       this.yBodyRot = $$0.yBodyRot; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/* 142 */     super.populateDefaultEquipmentSlots($$0, $$1);
/*     */     
/* 144 */     setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.BOW));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 150 */     $$3 = super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 152 */     RandomSource $$5 = $$0.getRandom();
/* 153 */     populateDefaultEquipmentSlots($$5, $$1);
/* 154 */     populateDefaultEquipmentEnchantments($$5, $$1);
/* 155 */     reassessWeaponGoal();
/*     */     
/* 157 */     setCanPickUpLoot(($$5.nextFloat() < 0.55F * $$1.getSpecialMultiplier()));
/*     */     
/* 159 */     if (getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
/* 160 */       LocalDate $$6 = LocalDate.now();
/* 161 */       int $$7 = $$6.get(ChronoField.DAY_OF_MONTH);
/* 162 */       int $$8 = $$6.get(ChronoField.MONTH_OF_YEAR);
/*     */       
/* 164 */       if ($$8 == 10 && $$7 == 31 && $$5.nextFloat() < 0.25F) {
/*     */         
/* 166 */         setItemSlot(EquipmentSlot.HEAD, new ItemStack(($$5.nextFloat() < 0.1F) ? (ItemLike)Blocks.JACK_O_LANTERN : (ItemLike)Blocks.CARVED_PUMPKIN));
/* 167 */         this.armorDropChances[EquipmentSlot.HEAD.getIndex()] = 0.0F;
/*     */       } 
/*     */     } 
/* 170 */     return $$3;
/*     */   }
/*     */   
/*     */   public void reassessWeaponGoal() {
/* 174 */     if (level() == null || (level()).isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 178 */     this.goalSelector.removeGoal((Goal)this.meleeGoal);
/* 179 */     this.goalSelector.removeGoal((Goal)this.bowGoal);
/*     */     
/* 181 */     ItemStack $$0 = getItemInHand(ProjectileUtil.getWeaponHoldingHand((LivingEntity)this, Items.BOW));
/* 182 */     if ($$0.is(Items.BOW)) {
/*     */       
/* 184 */       int $$1 = 20;
/* 185 */       if (level().getDifficulty() != Difficulty.HARD) {
/* 186 */         $$1 = 40;
/*     */       }
/* 188 */       this.bowGoal.setMinAttackInterval($$1);
/* 189 */       this.goalSelector.addGoal(4, (Goal)this.bowGoal);
/*     */     } else {
/* 191 */       this.goalSelector.addGoal(4, (Goal)this.meleeGoal);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void performRangedAttack(LivingEntity $$0, float $$1) {
/* 197 */     ItemStack $$2 = getProjectile(getItemInHand(ProjectileUtil.getWeaponHoldingHand((LivingEntity)this, Items.BOW)));
/* 198 */     AbstractArrow $$3 = getArrow($$2, $$1);
/*     */     
/* 200 */     double $$4 = $$0.getX() - getX();
/* 201 */     double $$5 = $$0.getY(0.3333333333333333D) - $$3.getY();
/* 202 */     double $$6 = $$0.getZ() - getZ();
/* 203 */     double $$7 = Math.sqrt($$4 * $$4 + $$6 * $$6);
/* 204 */     $$3.shoot($$4, $$5 + $$7 * 0.20000000298023224D, $$6, 1.6F, (14 - level().getDifficulty().getId() * 4));
/* 205 */     playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
/* 206 */     level().addFreshEntity((Entity)$$3);
/*     */   }
/*     */   
/*     */   protected AbstractArrow getArrow(ItemStack $$0, float $$1) {
/* 210 */     return ProjectileUtil.getMobArrow((LivingEntity)this, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFireProjectileWeapon(ProjectileWeaponItem $$0) {
/* 215 */     return ($$0 == Items.BOW);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 220 */     super.readAdditionalSaveData($$0);
/*     */     
/* 222 */     reassessWeaponGoal();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItemSlot(EquipmentSlot $$0, ItemStack $$1) {
/* 227 */     super.setItemSlot($$0, $$1);
/*     */     
/* 229 */     if (!(level()).isClientSide) {
/* 230 */       reassessWeaponGoal();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 236 */     return 1.74F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float ridingOffset(Entity $$0) {
/* 241 */     return -0.7F;
/*     */   }
/*     */   
/*     */   public boolean isShaking() {
/* 245 */     return isFullyFrozen();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\AbstractSkeleton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */