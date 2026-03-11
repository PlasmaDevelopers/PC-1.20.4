/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.raid.Raid;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class PatrollingMonster extends Monster {
/*     */   @Nullable
/*     */   private BlockPos patrolTarget;
/*     */   
/*     */   protected PatrollingMonster(EntityType<? extends PatrollingMonster> $$0, Level $$1) {
/*  34 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   private boolean patrolLeader; private boolean patrolling;
/*     */   
/*     */   protected void registerGoals() {
/*  39 */     super.registerGoals();
/*  40 */     this.goalSelector.addGoal(4, new LongDistancePatrolGoal<>(this, 0.7D, 0.595D));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  45 */     super.addAdditionalSaveData($$0);
/*     */     
/*  47 */     if (this.patrolTarget != null) {
/*  48 */       $$0.put("PatrolTarget", (Tag)NbtUtils.writeBlockPos(this.patrolTarget));
/*     */     }
/*     */     
/*  51 */     $$0.putBoolean("PatrolLeader", this.patrolLeader);
/*  52 */     $$0.putBoolean("Patrolling", this.patrolling);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  57 */     super.readAdditionalSaveData($$0);
/*     */     
/*  59 */     if ($$0.contains("PatrolTarget")) {
/*  60 */       this.patrolTarget = NbtUtils.readBlockPos($$0.getCompound("PatrolTarget"));
/*     */     }
/*     */     
/*  63 */     this.patrolLeader = $$0.getBoolean("PatrolLeader");
/*  64 */     this.patrolling = $$0.getBoolean("Patrolling");
/*     */   }
/*     */   
/*     */   public boolean canBeLeader() {
/*  68 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*  76 */     if ($$2 != MobSpawnType.PATROL && $$2 != MobSpawnType.EVENT && $$2 != MobSpawnType.STRUCTURE && 
/*  77 */       $$0.getRandom().nextFloat() < 0.06F && canBeLeader()) {
/*  78 */       this.patrolLeader = true;
/*     */     }
/*     */ 
/*     */     
/*  82 */     if (isPatrolLeader()) {
/*  83 */       setItemSlot(EquipmentSlot.HEAD, Raid.getLeaderBannerInstance());
/*  84 */       setDropChance(EquipmentSlot.HEAD, 2.0F);
/*     */     } 
/*     */     
/*  87 */     if ($$2 == MobSpawnType.PATROL) {
/*  88 */       this.patrolling = true;
/*     */     }
/*     */     
/*  91 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static boolean checkPatrollingMonsterSpawnRules(EntityType<? extends PatrollingMonster> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/*  95 */     if ($$1.getBrightness(LightLayer.BLOCK, $$3) > 8) {
/*  96 */       return false;
/*     */     }
/*     */     
/*  99 */     return checkAnyLightMonsterSpawnRules((EntityType)$$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 104 */     return (!this.patrolling || $$0 > 16384.0D);
/*     */   }
/*     */   
/*     */   public void setPatrolTarget(BlockPos $$0) {
/* 108 */     this.patrolTarget = $$0;
/* 109 */     this.patrolling = true;
/*     */   }
/*     */   
/*     */   public BlockPos getPatrolTarget() {
/* 113 */     return this.patrolTarget;
/*     */   }
/*     */   
/*     */   public boolean hasPatrolTarget() {
/* 117 */     return (this.patrolTarget != null);
/*     */   }
/*     */   
/*     */   public void setPatrolLeader(boolean $$0) {
/* 121 */     this.patrolLeader = $$0;
/* 122 */     this.patrolling = true;
/*     */   }
/*     */   
/*     */   public boolean isPatrolLeader() {
/* 126 */     return this.patrolLeader;
/*     */   }
/*     */   
/*     */   public boolean canJoinPatrol() {
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   public void findPatrolTarget() {
/* 134 */     this.patrolTarget = blockPosition().offset(-500 + this.random.nextInt(1000), 0, -500 + this.random.nextInt(1000));
/* 135 */     this.patrolling = true;
/*     */   }
/*     */   
/*     */   protected boolean isPatrolling() {
/* 139 */     return this.patrolling;
/*     */   }
/*     */   
/*     */   protected void setPatrolling(boolean $$0) {
/* 143 */     this.patrolling = $$0;
/*     */   }
/*     */   
/*     */   public static class LongDistancePatrolGoal<T extends PatrollingMonster>
/*     */     extends Goal {
/*     */     private static final int NAVIGATION_FAILED_COOLDOWN = 200;
/*     */     private final T mob;
/*     */     private final double speedModifier;
/*     */     private final double leaderSpeedModifier;
/*     */     private long cooldownUntil;
/*     */     
/*     */     public LongDistancePatrolGoal(T $$0, double $$1, double $$2) {
/* 155 */       this.mob = $$0;
/* 156 */       this.speedModifier = $$1;
/* 157 */       this.leaderSpeedModifier = $$2;
/* 158 */       this.cooldownUntil = -1L;
/* 159 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 164 */       boolean $$0 = (this.mob.level().getGameTime() < this.cooldownUntil);
/* 165 */       return (this.mob.isPatrolling() && this.mob.getTarget() == null && !this.mob.hasControllingPassenger() && this.mob.hasPatrolTarget() && !$$0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void start() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void stop() {}
/*     */ 
/*     */     
/*     */     public void tick() {
/* 178 */       boolean $$0 = this.mob.isPatrolLeader();
/* 179 */       PathNavigation $$1 = this.mob.getNavigation();
/* 180 */       if ($$1.isDone()) {
/* 181 */         List<PatrollingMonster> $$2 = findPatrolCompanions();
/* 182 */         if (this.mob.isPatrolling() && $$2.isEmpty()) {
/* 183 */           this.mob.setPatrolling(false);
/* 184 */         } else if (!$$0 || !this.mob.getPatrolTarget().closerToCenterThan((Position)this.mob.position(), 10.0D)) {
/* 185 */           Vec3 $$3 = Vec3.atBottomCenterOf((Vec3i)this.mob.getPatrolTarget());
/*     */ 
/*     */           
/* 188 */           Vec3 $$4 = this.mob.position();
/* 189 */           Vec3 $$5 = $$4.subtract($$3);
/*     */           
/* 191 */           $$3 = $$5.yRot(90.0F).scale(0.4D).add($$3);
/*     */           
/* 193 */           Vec3 $$6 = $$3.subtract($$4).normalize().scale(10.0D).add($$4);
/* 194 */           BlockPos $$7 = BlockPos.containing((Position)$$6);
/* 195 */           $$7 = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$7);
/*     */           
/* 197 */           if (!$$1.moveTo($$7.getX(), $$7.getY(), $$7.getZ(), $$0 ? this.leaderSpeedModifier : this.speedModifier)) {
/*     */             
/* 199 */             moveRandomly();
/* 200 */             this.cooldownUntil = this.mob.level().getGameTime() + 200L;
/* 201 */           } else if ($$0) {
/* 202 */             for (PatrollingMonster $$8 : $$2) {
/* 203 */               $$8.setPatrolTarget($$7);
/*     */             }
/*     */           } 
/*     */         } else {
/* 207 */           this.mob.findPatrolTarget();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private List<PatrollingMonster> findPatrolCompanions() {
/* 213 */       return this.mob.level().getEntitiesOfClass(PatrollingMonster.class, this.mob.getBoundingBox().inflate(16.0D), $$0 -> ($$0.canJoinPatrol() && !$$0.is((Entity)this.mob)));
/*     */     }
/*     */     
/*     */     private boolean moveRandomly() {
/* 217 */       RandomSource $$0 = this.mob.getRandom();
/* 218 */       BlockPos $$1 = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.mob.blockPosition().offset(-8 + $$0.nextInt(16), 0, -8 + $$0.nextInt(16)));
/* 219 */       return this.mob.getNavigation().moveTo($$1.getX(), $$1.getY(), $$1.getZ(), this.speedModifier);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\PatrollingMonster.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */