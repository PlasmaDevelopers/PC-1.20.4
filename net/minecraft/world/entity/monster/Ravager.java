/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.npc.AbstractVillager;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.raid.Raider;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Ravager extends Raider {
/*     */   private static final Predicate<Entity> NO_RAVAGER_AND_ALIVE;
/*     */   
/*     */   static {
/*  49 */     NO_RAVAGER_AND_ALIVE = ($$0 -> ($$0.isAlive() && !($$0 instanceof Ravager)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static final double BASE_MOVEMENT_SPEED = 0.3D;
/*     */   private static final double ATTACK_MOVEMENT_SPEED = 0.35D;
/*     */   private static final int STUNNED_COLOR = 8356754;
/*     */   private static final double STUNNED_COLOR_BLUE = 0.5725490196078431D;
/*     */   private static final double STUNNED_COLOR_GREEN = 0.5137254901960784D;
/*     */   private static final double STUNNED_COLOR_RED = 0.4980392156862745D;
/*     */   private static final int ATTACK_DURATION = 10;
/*     */   public static final int STUN_DURATION = 40;
/*     */   private int attackTick;
/*     */   private int stunnedTick;
/*     */   private int roarTick;
/*     */   
/*     */   public Ravager(EntityType<? extends Ravager> $$0, Level $$1) {
/*  66 */     super($$0, $$1);
/*     */     
/*  68 */     setMaxUpStep(1.0F);
/*  69 */     this.xpReward = 20;
/*     */     
/*  71 */     setPathfindingMalus(BlockPathTypes.LEAVES, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  76 */     super.registerGoals();
/*     */     
/*  78 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  79 */     this.goalSelector.addGoal(4, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.0D, true));
/*  80 */     this.goalSelector.addGoal(5, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.4D));
/*  81 */     this.goalSelector.addGoal(6, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/*  82 */     this.goalSelector.addGoal(10, (Goal)new LookAtPlayerGoal((Mob)this, Mob.class, 8.0F));
/*     */     
/*  84 */     this.targetSelector.addGoal(2, (Goal)(new HurtByTargetGoal((PathfinderMob)this, new Class[] { Raider.class })).setAlertOthers(new Class[0]));
/*  85 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/*  86 */     this.targetSelector.addGoal(4, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractVillager.class, true, $$0 -> !$$0.isBaby()));
/*  87 */     this.targetSelector.addGoal(4, (Goal)new NearestAttackableTargetGoal((Mob)this, IronGolem.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateControlFlags() {
/*  92 */     boolean $$0 = (!(getControllingPassenger() instanceof Mob) || getControllingPassenger().getType().is(EntityTypeTags.RAIDERS));
/*  93 */     boolean $$1 = !(getVehicle() instanceof net.minecraft.world.entity.vehicle.Boat);
/*  94 */     this.goalSelector.setControlFlag(Goal.Flag.MOVE, $$0);
/*  95 */     this.goalSelector.setControlFlag(Goal.Flag.JUMP, ($$0 && $$1));
/*  96 */     this.goalSelector.setControlFlag(Goal.Flag.LOOK, $$0);
/*  97 */     this.goalSelector.setControlFlag(Goal.Flag.TARGET, $$0);
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 101 */     return Monster.createMonsterAttributes()
/* 102 */       .add(Attributes.MAX_HEALTH, 100.0D)
/* 103 */       .add(Attributes.MOVEMENT_SPEED, 0.3D)
/* 104 */       .add(Attributes.KNOCKBACK_RESISTANCE, 0.75D)
/* 105 */       .add(Attributes.ATTACK_DAMAGE, 12.0D)
/* 106 */       .add(Attributes.ATTACK_KNOCKBACK, 1.5D)
/* 107 */       .add(Attributes.FOLLOW_RANGE, 32.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 112 */     super.addAdditionalSaveData($$0);
/*     */     
/* 114 */     $$0.putInt("AttackTick", this.attackTick);
/* 115 */     $$0.putInt("StunTick", this.stunnedTick);
/* 116 */     $$0.putInt("RoarTick", this.roarTick);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 121 */     super.readAdditionalSaveData($$0);
/*     */     
/* 123 */     this.attackTick = $$0.getInt("AttackTick");
/* 124 */     this.stunnedTick = $$0.getInt("StunTick");
/* 125 */     this.roarTick = $$0.getInt("RoarTick");
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getCelebrateSound() {
/* 130 */     return SoundEvents.RAVAGER_CELEBRATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadYRot() {
/* 135 */     return 45;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 140 */     return new Vector3f(0.0F, $$1.height + 0.0625F * $$2, -0.0625F * $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 145 */     super.aiStep();
/*     */     
/* 147 */     if (!isAlive()) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     if (isImmobile()) {
/* 152 */       getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
/*     */     } else {
/* 154 */       double $$0 = (getTarget() != null) ? 0.35D : 0.3D;
/* 155 */       double $$1 = getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
/* 156 */       getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Mth.lerp(0.1D, $$1, $$0));
/*     */     } 
/*     */     
/* 159 */     if (this.horizontalCollision && level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 160 */       boolean $$2 = false;
/* 161 */       AABB $$3 = getBoundingBox().inflate(0.2D);
/* 162 */       for (BlockPos $$4 : BlockPos.betweenClosed(Mth.floor($$3.minX), Mth.floor($$3.minY), Mth.floor($$3.minZ), Mth.floor($$3.maxX), Mth.floor($$3.maxY), Mth.floor($$3.maxZ))) {
/* 163 */         BlockState $$5 = level().getBlockState($$4);
/* 164 */         Block $$6 = $$5.getBlock();
/* 165 */         if ($$6 instanceof net.minecraft.world.level.block.LeavesBlock) {
/* 166 */           $$2 = (level().destroyBlock($$4, true, (Entity)this) || $$2);
/*     */         }
/*     */       } 
/*     */       
/* 170 */       if (!$$2 && onGround()) {
/* 171 */         jumpFromGround();
/*     */       }
/*     */     } 
/*     */     
/* 175 */     if (this.roarTick > 0) {
/* 176 */       this.roarTick--;
/*     */       
/* 178 */       if (this.roarTick == 10) {
/* 179 */         roar();
/*     */       }
/*     */     } 
/* 182 */     if (this.attackTick > 0) {
/* 183 */       this.attackTick--;
/*     */     }
/* 185 */     if (this.stunnedTick > 0) {
/* 186 */       this.stunnedTick--;
/* 187 */       stunEffect();
/*     */       
/* 189 */       if (this.stunnedTick == 0) {
/* 190 */         playSound(SoundEvents.RAVAGER_ROAR, 1.0F, 1.0F);
/* 191 */         this.roarTick = 20;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void stunEffect() {
/* 197 */     if (this.random.nextInt(6) == 0) {
/* 198 */       double $$0 = getX() - getBbWidth() * Math.sin((this.yBodyRot * 0.017453292F)) + this.random.nextDouble() * 0.6D - 0.3D;
/* 199 */       double $$1 = getY() + getBbHeight() - 0.3D;
/* 200 */       double $$2 = getZ() + getBbWidth() * Math.cos((this.yBodyRot * 0.017453292F)) + this.random.nextDouble() * 0.6D - 0.3D;
/*     */       
/* 202 */       level().addParticle((ParticleOptions)ParticleTypes.ENTITY_EFFECT, $$0, $$1, $$2, 0.4980392156862745D, 0.5137254901960784D, 0.5725490196078431D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isImmobile() {
/* 208 */     return (super.isImmobile() || this.attackTick > 0 || this.stunnedTick > 0 || this.roarTick > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasLineOfSight(Entity $$0) {
/* 213 */     if (this.stunnedTick > 0 || this.roarTick > 0) {
/* 214 */       return false;
/*     */     }
/* 216 */     return super.hasLineOfSight($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void blockedByShield(LivingEntity $$0) {
/* 221 */     if (this.roarTick == 0) {
/* 222 */       if (this.random.nextDouble() < 0.5D) {
/* 223 */         this.stunnedTick = 40;
/* 224 */         playSound(SoundEvents.RAVAGER_STUNNED, 1.0F, 1.0F);
/* 225 */         level().broadcastEntityEvent((Entity)this, (byte)39);
/*     */         
/* 227 */         $$0.push((Entity)this);
/*     */       } else {
/* 229 */         strongKnockback((Entity)$$0);
/*     */       } 
/* 231 */       $$0.hurtMarked = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void roar() {
/* 236 */     if (isAlive()) {
/* 237 */       List<? extends LivingEntity> $$0 = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4.0D), NO_RAVAGER_AND_ALIVE);
/* 238 */       for (LivingEntity $$1 : $$0) {
/* 239 */         if (!($$1 instanceof AbstractIllager)) {
/* 240 */           $$1.hurt(damageSources().mobAttack((LivingEntity)this), 6.0F);
/*     */         }
/* 242 */         strongKnockback((Entity)$$1);
/*     */       } 
/*     */       
/* 245 */       Vec3 $$2 = getBoundingBox().getCenter();
/* 246 */       for (int $$3 = 0; $$3 < 40; $$3++) {
/* 247 */         double $$4 = this.random.nextGaussian() * 0.2D;
/* 248 */         double $$5 = this.random.nextGaussian() * 0.2D;
/* 249 */         double $$6 = this.random.nextGaussian() * 0.2D;
/* 250 */         level().addParticle((ParticleOptions)ParticleTypes.POOF, $$2.x, $$2.y, $$2.z, $$4, $$5, $$6);
/*     */       } 
/*     */       
/* 253 */       gameEvent(GameEvent.ENTITY_ACTION);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void strongKnockback(Entity $$0) {
/* 259 */     double $$1 = $$0.getX() - getX();
/* 260 */     double $$2 = $$0.getZ() - getZ();
/* 261 */     double $$3 = Math.max($$1 * $$1 + $$2 * $$2, 0.001D);
/* 262 */     $$0.push($$1 / $$3 * 4.0D, 0.2D, $$2 / $$3 * 4.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 267 */     if ($$0 == 4) {
/* 268 */       this.attackTick = 10;
/* 269 */       playSound(SoundEvents.RAVAGER_ATTACK, 1.0F, 1.0F);
/* 270 */     } else if ($$0 == 39) {
/* 271 */       this.stunnedTick = 40;
/*     */     } 
/* 273 */     super.handleEntityEvent($$0);
/*     */   }
/*     */   
/*     */   public int getAttackTick() {
/* 277 */     return this.attackTick;
/*     */   }
/*     */   
/*     */   public int getStunnedTick() {
/* 281 */     return this.stunnedTick;
/*     */   }
/*     */   
/*     */   public int getRoarTick() {
/* 285 */     return this.roarTick;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 290 */     this.attackTick = 10;
/* 291 */     level().broadcastEntityEvent((Entity)this, (byte)4);
/* 292 */     playSound(SoundEvents.RAVAGER_ATTACK, 1.0F, 1.0F);
/*     */     
/* 294 */     return super.doHurtTarget($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 300 */     return SoundEvents.RAVAGER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 305 */     return SoundEvents.RAVAGER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 310 */     return SoundEvents.RAVAGER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 315 */     playSound(SoundEvents.RAVAGER_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 320 */     return !$$0.containsAnyLiquid(getBoundingBox());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyRaidBuffs(int $$0, boolean $$1) {}
/*     */ 
/*     */   
/*     */   public boolean canBeLeader() {
/* 329 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AABB getAttackBoundingBox() {
/* 335 */     AABB $$0 = super.getAttackBoundingBox();
/* 336 */     return $$0.deflate(0.05D, 0.0D, 0.05D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Ravager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */