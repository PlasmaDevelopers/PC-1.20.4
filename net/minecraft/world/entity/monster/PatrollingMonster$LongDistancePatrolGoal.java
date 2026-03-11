/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongDistancePatrolGoal<T extends PatrollingMonster>
/*     */   extends Goal
/*     */ {
/*     */   private static final int NAVIGATION_FAILED_COOLDOWN = 200;
/*     */   private final T mob;
/*     */   private final double speedModifier;
/*     */   private final double leaderSpeedModifier;
/*     */   private long cooldownUntil;
/*     */   
/*     */   public LongDistancePatrolGoal(T $$0, double $$1, double $$2) {
/* 155 */     this.mob = $$0;
/* 156 */     this.speedModifier = $$1;
/* 157 */     this.leaderSpeedModifier = $$2;
/* 158 */     this.cooldownUntil = -1L;
/* 159 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUse() {
/* 164 */     boolean $$0 = (this.mob.level().getGameTime() < this.cooldownUntil);
/* 165 */     return (this.mob.isPatrolling() && this.mob.getTarget() == null && !this.mob.hasControllingPassenger() && this.mob.hasPatrolTarget() && !$$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {}
/*     */ 
/*     */   
/*     */   public void tick() {
/* 178 */     boolean $$0 = this.mob.isPatrolLeader();
/* 179 */     PathNavigation $$1 = this.mob.getNavigation();
/* 180 */     if ($$1.isDone()) {
/* 181 */       List<PatrollingMonster> $$2 = findPatrolCompanions();
/* 182 */       if (this.mob.isPatrolling() && $$2.isEmpty()) {
/* 183 */         this.mob.setPatrolling(false);
/* 184 */       } else if (!$$0 || !this.mob.getPatrolTarget().closerToCenterThan((Position)this.mob.position(), 10.0D)) {
/* 185 */         Vec3 $$3 = Vec3.atBottomCenterOf((Vec3i)this.mob.getPatrolTarget());
/*     */ 
/*     */         
/* 188 */         Vec3 $$4 = this.mob.position();
/* 189 */         Vec3 $$5 = $$4.subtract($$3);
/*     */         
/* 191 */         $$3 = $$5.yRot(90.0F).scale(0.4D).add($$3);
/*     */         
/* 193 */         Vec3 $$6 = $$3.subtract($$4).normalize().scale(10.0D).add($$4);
/* 194 */         BlockPos $$7 = BlockPos.containing((Position)$$6);
/* 195 */         $$7 = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, $$7);
/*     */         
/* 197 */         if (!$$1.moveTo($$7.getX(), $$7.getY(), $$7.getZ(), $$0 ? this.leaderSpeedModifier : this.speedModifier)) {
/*     */           
/* 199 */           moveRandomly();
/* 200 */           this.cooldownUntil = this.mob.level().getGameTime() + 200L;
/* 201 */         } else if ($$0) {
/* 202 */           for (PatrollingMonster $$8 : $$2) {
/* 203 */             $$8.setPatrolTarget($$7);
/*     */           }
/*     */         } 
/*     */       } else {
/* 207 */         this.mob.findPatrolTarget();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<PatrollingMonster> findPatrolCompanions() {
/* 213 */     return this.mob.level().getEntitiesOfClass(PatrollingMonster.class, this.mob.getBoundingBox().inflate(16.0D), $$0 -> ($$0.canJoinPatrol() && !$$0.is((Entity)this.mob)));
/*     */   }
/*     */   
/*     */   private boolean moveRandomly() {
/* 217 */     RandomSource $$0 = this.mob.getRandom();
/* 218 */     BlockPos $$1 = this.mob.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.mob.blockPosition().offset(-8 + $$0.nextInt(16), 0, -8 + $$0.nextInt(16)));
/* 219 */     return this.mob.getNavigation().moveTo($$1.getX(), $$1.getY(), $$1.getZ(), this.speedModifier);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\PatrollingMonster$LongDistancePatrolGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */