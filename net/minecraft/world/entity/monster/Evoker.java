/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.animal.Sheep;
/*     */ import net.minecraft.world.entity.npc.AbstractVillager;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.EvokerFangs;
/*     */ import net.minecraft.world.entity.raid.Raider;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ 
/*     */ public class Evoker extends SpellcasterIllager {
/*     */   public Evoker(EntityType<? extends Evoker> $$0, Level $$1) {
/*  51 */     super((EntityType)$$0, $$1);
/*     */     
/*  53 */     this.xpReward = 10;
/*     */   }
/*     */   @Nullable
/*     */   private Sheep wololoTarget;
/*     */   protected void registerGoals() {
/*  58 */     super.registerGoals();
/*     */     
/*  60 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  61 */     this.goalSelector.addGoal(1, new EvokerCastingSpellGoal());
/*  62 */     this.goalSelector.addGoal(2, (Goal)new AvoidEntityGoal((PathfinderMob)this, Player.class, 8.0F, 0.6D, 1.0D));
/*  63 */     this.goalSelector.addGoal(4, new EvokerSummonSpellGoal());
/*  64 */     this.goalSelector.addGoal(5, new EvokerAttackSpellGoal());
/*  65 */     this.goalSelector.addGoal(6, new EvokerWololoSpellGoal());
/*  66 */     this.goalSelector.addGoal(8, (Goal)new RandomStrollGoal((PathfinderMob)this, 0.6D));
/*  67 */     this.goalSelector.addGoal(9, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0F, 1.0F));
/*  68 */     this.goalSelector.addGoal(10, (Goal)new LookAtPlayerGoal((Mob)this, Mob.class, 8.0F));
/*     */     
/*  70 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal((PathfinderMob)this, new Class[] { Raider.class })).setAlertOthers(new Class[0]));
/*  71 */     this.targetSelector.addGoal(2, (Goal)(new NearestAttackableTargetGoal((Mob)this, Player.class, true)).setUnseenMemoryTicks(300));
/*  72 */     this.targetSelector.addGoal(3, (Goal)(new NearestAttackableTargetGoal((Mob)this, AbstractVillager.class, false)).setUnseenMemoryTicks(300));
/*  73 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, IronGolem.class, false));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  77 */     return Monster.createMonsterAttributes()
/*  78 */       .add(Attributes.MOVEMENT_SPEED, 0.5D)
/*  79 */       .add(Attributes.FOLLOW_RANGE, 12.0D)
/*  80 */       .add(Attributes.MAX_HEALTH, 24.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  85 */     super.defineSynchedData();
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  90 */     super.readAdditionalSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getCelebrateSound() {
/*  95 */     return SoundEvents.EVOKER_CELEBRATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 100 */     super.addAdditionalSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 105 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlliedTo(Entity $$0) {
/* 110 */     if ($$0 == null) {
/* 111 */       return false;
/*     */     }
/* 113 */     if ($$0 == this) {
/* 114 */       return true;
/*     */     }
/* 116 */     if (super.isAlliedTo($$0)) {
/* 117 */       return true;
/*     */     }
/* 119 */     if ($$0 instanceof Vex) {
/* 120 */       return isAlliedTo((Entity)((Vex)$$0).getOwner());
/*     */     }
/* 122 */     if ($$0 instanceof LivingEntity && ((LivingEntity)$$0).getMobType() == MobType.ILLAGER)
/*     */     {
/* 124 */       return (getTeam() == null && $$0.getTeam() == null);
/*     */     }
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 131 */     return SoundEvents.EVOKER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 136 */     return SoundEvents.EVOKER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 141 */     return SoundEvents.EVOKER_HURT;
/*     */   }
/*     */   
/*     */   void setWololoTarget(@Nullable Sheep $$0) {
/* 145 */     this.wololoTarget = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   Sheep getWololoTarget() {
/* 150 */     return this.wololoTarget;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getCastingSoundEvent() {
/* 155 */     return SoundEvents.EVOKER_CAST_SPELL;
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyRaidBuffs(int $$0, boolean $$1) {}
/*     */   
/*     */   private class EvokerCastingSpellGoal
/*     */     extends SpellcasterIllager.SpellcasterCastingSpellGoal
/*     */   {
/*     */     public void tick() {
/* 165 */       if (Evoker.this.getTarget() != null) {
/* 166 */         Evoker.this.getLookControl().setLookAt((Entity)Evoker.this.getTarget(), Evoker.this.getMaxHeadYRot(), Evoker.this.getMaxHeadXRot());
/* 167 */       } else if (Evoker.this.getWololoTarget() != null) {
/* 168 */         Evoker.this.getLookControl().setLookAt((Entity)Evoker.this.getWololoTarget(), Evoker.this.getMaxHeadYRot(), Evoker.this.getMaxHeadXRot());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class EvokerAttackSpellGoal
/*     */     extends SpellcasterIllager.SpellcasterUseSpellGoal {
/*     */     protected int getCastingTime() {
/* 176 */       return 40;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastingInterval() {
/* 181 */       return 100;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void performSpellCasting() {
/* 187 */       LivingEntity $$0 = Evoker.this.getTarget();
/* 188 */       double $$1 = Math.min($$0.getY(), Evoker.this.getY());
/* 189 */       double $$2 = Math.max($$0.getY(), Evoker.this.getY()) + 1.0D;
/* 190 */       float $$3 = (float)Mth.atan2($$0.getZ() - Evoker.this.getZ(), $$0.getX() - Evoker.this.getX());
/* 191 */       if (Evoker.this.distanceToSqr((Entity)$$0) < 9.0D) {
/*     */         
/* 193 */         for (int $$4 = 0; $$4 < 5; $$4++) {
/* 194 */           float $$5 = $$3 + $$4 * 3.1415927F * 0.4F;
/* 195 */           createSpellEntity(Evoker.this.getX() + Mth.cos($$5) * 1.5D, Evoker.this.getZ() + Mth.sin($$5) * 1.5D, $$1, $$2, $$5, 0);
/*     */         } 
/*     */         
/* 198 */         for (int $$6 = 0; $$6 < 8; $$6++) {
/* 199 */           float $$7 = $$3 + $$6 * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
/* 200 */           createSpellEntity(Evoker.this.getX() + Mth.cos($$7) * 2.5D, Evoker.this.getZ() + Mth.sin($$7) * 2.5D, $$1, $$2, $$7, 3);
/*     */         } 
/*     */       } else {
/*     */         
/* 204 */         for (int $$8 = 0; $$8 < 16; $$8++) {
/* 205 */           double $$9 = 1.25D * ($$8 + 1);
/* 206 */           int $$10 = 1 * $$8;
/* 207 */           createSpellEntity(Evoker.this.getX() + Mth.cos($$3) * $$9, Evoker.this.getZ() + Mth.sin($$3) * $$9, $$1, $$2, $$3, $$10);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void createSpellEntity(double $$0, double $$1, double $$2, double $$3, float $$4, int $$5) {
/* 214 */       BlockPos $$6 = BlockPos.containing($$0, $$3, $$1);
/* 215 */       boolean $$7 = false;
/* 216 */       double $$8 = 0.0D;
/*     */       do {
/* 218 */         BlockPos $$9 = $$6.below();
/* 219 */         BlockState $$10 = Evoker.this.level().getBlockState($$9);
/* 220 */         if ($$10.isFaceSturdy((BlockGetter)Evoker.this.level(), $$9, Direction.UP)) {
/* 221 */           if (!Evoker.this.level().isEmptyBlock($$6)) {
/* 222 */             BlockState $$11 = Evoker.this.level().getBlockState($$6);
/* 223 */             VoxelShape $$12 = $$11.getCollisionShape((BlockGetter)Evoker.this.level(), $$6);
/* 224 */             if (!$$12.isEmpty()) {
/* 225 */               $$8 = $$12.max(Direction.Axis.Y);
/*     */             }
/*     */           } 
/* 228 */           $$7 = true;
/*     */           break;
/*     */         } 
/* 231 */         $$6 = $$6.below();
/* 232 */       } while ($$6.getY() >= Mth.floor($$2) - 1);
/* 233 */       if ($$7) {
/* 234 */         Evoker.this.level().addFreshEntity((Entity)new EvokerFangs(Evoker.this.level(), $$0, $$6.getY() + $$8, $$1, $$4, $$5, (LivingEntity)Evoker.this));
/* 235 */         Evoker.this.level().gameEvent(GameEvent.ENTITY_PLACE, new Vec3($$0, $$6.getY() + $$8, $$1), GameEvent.Context.of((Entity)Evoker.this));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected SoundEvent getSpellPrepareSound() {
/* 241 */       return SoundEvents.EVOKER_PREPARE_ATTACK;
/*     */     }
/*     */ 
/*     */     
/*     */     protected SpellcasterIllager.IllagerSpell getSpell() {
/* 246 */       return SpellcasterIllager.IllagerSpell.FANGS;
/*     */     } }
/*     */   
/*     */   private class EvokerSummonSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
/*     */     EvokerSummonSpellGoal() {
/* 251 */       this.vexCountTargeting = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().ignoreInvisibilityTesting();
/*     */     }
/*     */     private final TargetingConditions vexCountTargeting;
/*     */     public boolean canUse() {
/* 255 */       if (!super.canUse()) {
/* 256 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 260 */       int $$0 = Evoker.this.level().getNearbyEntities(Vex.class, this.vexCountTargeting, (LivingEntity)Evoker.this, Evoker.this.getBoundingBox().inflate(16.0D)).size();
/* 261 */       return (Evoker.this.random.nextInt(8) + 1 > $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastingTime() {
/* 266 */       return 100;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastingInterval() {
/* 271 */       return 340;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void performSpellCasting() {
/* 276 */       ServerLevel $$0 = (ServerLevel)Evoker.this.level();
/* 277 */       PlayerTeam $$1 = Evoker.this.getTeam();
/* 278 */       for (int $$2 = 0; $$2 < 3; $$2++) {
/* 279 */         BlockPos $$3 = Evoker.this.blockPosition().offset(-2 + Evoker.this.random.nextInt(5), 1, -2 + Evoker.this.random.nextInt(5));
/* 280 */         Vex $$4 = (Vex)EntityType.VEX.create(Evoker.this.level());
/* 281 */         if ($$4 != null) {
/* 282 */           $$4.moveTo($$3, 0.0F, 0.0F);
/* 283 */           $$4.finalizeSpawn((ServerLevelAccessor)$$0, Evoker.this.level().getCurrentDifficultyAt($$3), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
/* 284 */           $$4.setOwner((Mob)Evoker.this);
/* 285 */           $$4.setBoundOrigin($$3);
/* 286 */           $$4.setLimitedLife(20 * (30 + Evoker.this.random.nextInt(90)));
/* 287 */           if ($$1 != null) {
/* 288 */             $$0.getScoreboard().addPlayerToTeam($$4.getScoreboardName(), $$1);
/*     */           }
/* 290 */           $$0.addFreshEntityWithPassengers((Entity)$$4);
/* 291 */           $$0.gameEvent(GameEvent.ENTITY_PLACE, $$3, GameEvent.Context.of((Entity)Evoker.this));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected SoundEvent getSpellPrepareSound() {
/* 298 */       return SoundEvents.EVOKER_PREPARE_SUMMON;
/*     */     }
/*     */ 
/*     */     
/*     */     protected SpellcasterIllager.IllagerSpell getSpell() {
/* 303 */       return SpellcasterIllager.IllagerSpell.SUMMON_VEX;
/*     */     } }
/*     */   public class EvokerWololoSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal { private final TargetingConditions wololoTargeting;
/*     */     
/*     */     public EvokerWololoSpellGoal() {
/* 308 */       this.wololoTargeting = TargetingConditions.forNonCombat().range(16.0D).selector($$0 -> (((Sheep)$$0).getColor() == DyeColor.BLUE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 313 */       if (Evoker.this.getTarget() != null)
/*     */       {
/* 315 */         return false;
/*     */       }
/* 317 */       if (Evoker.this.isCastingSpell())
/*     */       {
/* 319 */         return false;
/*     */       }
/* 321 */       if (Evoker.this.tickCount < this.nextAttackTickCount) {
/* 322 */         return false;
/*     */       }
/* 324 */       if (!Evoker.this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 325 */         return false;
/*     */       }
/*     */       
/* 328 */       List<Sheep> $$0 = Evoker.this.level().getNearbyEntities(Sheep.class, this.wololoTargeting, (LivingEntity)Evoker.this, Evoker.this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
/*     */       
/* 330 */       if ($$0.isEmpty()) {
/* 331 */         return false;
/*     */       }
/* 333 */       Evoker.this.setWololoTarget($$0.get(Evoker.this.random.nextInt($$0.size())));
/* 334 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 340 */       return (Evoker.this.getWololoTarget() != null && this.attackWarmupDelay > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 345 */       super.stop();
/* 346 */       Evoker.this.setWololoTarget((Sheep)null);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void performSpellCasting() {
/* 351 */       Sheep $$0 = Evoker.this.getWololoTarget();
/* 352 */       if ($$0 != null && $$0.isAlive()) {
/* 353 */         $$0.setColor(DyeColor.RED);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastWarmupTime() {
/* 359 */       return 40;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastingTime() {
/* 364 */       return 60;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getCastingInterval() {
/* 369 */       return 140;
/*     */     }
/*     */ 
/*     */     
/*     */     protected SoundEvent getSpellPrepareSound() {
/* 374 */       return SoundEvents.EVOKER_PREPARE_WOLOLO;
/*     */     }
/*     */ 
/*     */     
/*     */     protected SpellcasterIllager.IllagerSpell getSpell() {
/* 379 */       return SpellcasterIllager.IllagerSpell.WOLOLO;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Evoker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */