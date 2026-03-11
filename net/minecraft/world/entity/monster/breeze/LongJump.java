/*     */ package net.minecraft.world.entity.monster.breeze;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*     */ import net.minecraft.world.entity.ai.behavior.LongJumpUtil;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class LongJump
/*     */   extends Behavior<Breeze> {
/*     */   private static final int REQUIRED_AIR_BLOCKS_ABOVE = 4;
/*     */   private static final double MAX_LINE_OF_SIGHT_TEST_RANGE = 50.0D;
/*  40 */   private static final int INHALING_DURATION_TICKS = Math.round(10.0F); private static final int JUMP_COOLDOWN_TICKS = 10;
/*     */   private static final int JUMP_COOLDOWN_WHEN_HURT_TICKS = 2;
/*     */   private static final float MAX_JUMP_VELOCITY = 1.4F;
/*  43 */   private static final ObjectArrayList<Integer> ALLOWED_ANGLES = new ObjectArrayList(Lists.newArrayList((Object[])new Integer[] { Integer.valueOf(40), Integer.valueOf(55), Integer.valueOf(60), Integer.valueOf(75), Integer.valueOf(80) }));
/*     */   
/*     */   @VisibleForTesting
/*     */   public LongJump() {
/*  47 */     super(Map.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.BREEZE_JUMP_COOLDOWN, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREEZE_JUMP_INHALING, MemoryStatus.REGISTERED, MemoryModuleType.BREEZE_JUMP_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.BREEZE_SHOOT, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), 200);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Breeze $$1) {
/*  59 */     if (!$$1.onGround() && !$$1.isInWater()) {
/*  60 */       return false;
/*     */     }
/*     */     
/*  63 */     if ($$1.getBrain().checkMemory(MemoryModuleType.BREEZE_JUMP_TARGET, MemoryStatus.VALUE_PRESENT)) {
/*  64 */       return true;
/*     */     }
/*     */     
/*  67 */     LivingEntity $$2 = $$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
/*  68 */     if ($$2 == null) {
/*  69 */       return false;
/*     */     }
/*     */     
/*  72 */     if (outOfAggroRange($$1, $$2)) {
/*  73 */       $$1.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
/*  74 */       return false;
/*     */     } 
/*     */     
/*  77 */     if (tooCloseForJump($$1, $$2)) {
/*  78 */       return false;
/*     */     }
/*     */     
/*  81 */     if (!canJumpFromCurrentPosition($$0, $$1)) {
/*  82 */       return false;
/*     */     }
/*     */     
/*  85 */     BlockPos $$3 = snapToSurface((LivingEntity)$$1, randomPointBehindTarget($$2, $$1.getRandom()));
/*  86 */     if ($$3 == null) {
/*  87 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  91 */     if (!hasLineOfSight($$1, $$3.getCenter()) && !hasLineOfSight($$1, $$3.above(4).getCenter())) {
/*  92 */       return false;
/*     */     }
/*     */     
/*  95 */     $$1.getBrain().setMemory(MemoryModuleType.BREEZE_JUMP_TARGET, $$3);
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Breeze $$1, long $$2) {
/* 101 */     return ($$1.getPose() != Pose.STANDING && !$$1.getBrain().hasMemoryValue(MemoryModuleType.BREEZE_JUMP_COOLDOWN));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Breeze $$1, long $$2) {
/* 106 */     if ($$1.getBrain().checkMemory(MemoryModuleType.BREEZE_JUMP_INHALING, MemoryStatus.VALUE_ABSENT)) {
/* 107 */       $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_JUMP_INHALING, Unit.INSTANCE, INHALING_DURATION_TICKS);
/*     */     }
/*     */     
/* 110 */     $$1.setPose(Pose.INHALING);
/* 111 */     $$1.getBrain().getMemory(MemoryModuleType.BREEZE_JUMP_TARGET)
/* 112 */       .ifPresent($$1 -> $$0.lookAt(EntityAnchorArgument.Anchor.EYES, $$1.getCenter()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Breeze $$1, long $$2) {
/* 117 */     if (finishedInhaling($$1)) {
/*     */ 
/*     */ 
/*     */       
/* 121 */       Vec3 $$3 = $$1.getBrain().getMemory(MemoryModuleType.BREEZE_JUMP_TARGET).flatMap($$1 -> calculateOptimalJumpVector($$0, $$0.getRandom(), Vec3.atBottomCenterOf((Vec3i)$$1))).orElse(null);
/*     */       
/* 123 */       if ($$3 == null) {
/* 124 */         $$1.setPose(Pose.STANDING);
/*     */         
/*     */         return;
/*     */       } 
/* 128 */       $$1.playSound(SoundEvents.BREEZE_JUMP, 1.0F, 1.0F);
/* 129 */       $$1.setPose(Pose.LONG_JUMPING);
/* 130 */       $$1.setYRot($$1.yBodyRot);
/* 131 */       $$1.setDiscardFriction(true);
/* 132 */       $$1.setDeltaMovement($$3);
/* 133 */     } else if (finishedJumping($$1)) {
/* 134 */       $$1.playSound(SoundEvents.BREEZE_LAND, 1.0F, 1.0F);
/* 135 */       $$1.setPose(Pose.STANDING);
/* 136 */       $$1.setDiscardFriction(false);
/*     */       
/* 138 */       boolean $$4 = $$1.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
/* 139 */       $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_JUMP_COOLDOWN, Unit.INSTANCE, $$4 ? 2L : 10L);
/*     */       
/* 141 */       $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_SHOOT, Unit.INSTANCE, 100L);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Breeze $$1, long $$2) {
/* 147 */     if ($$1.getPose() == Pose.LONG_JUMPING || $$1.getPose() == Pose.INHALING) {
/* 148 */       $$1.setPose(Pose.STANDING);
/*     */     }
/* 150 */     $$1.getBrain().eraseMemory(MemoryModuleType.BREEZE_JUMP_TARGET);
/* 151 */     $$1.getBrain().eraseMemory(MemoryModuleType.BREEZE_JUMP_INHALING);
/*     */   }
/*     */   
/*     */   private static boolean finishedInhaling(Breeze $$0) {
/* 155 */     return ($$0.getBrain().getMemory(MemoryModuleType.BREEZE_JUMP_INHALING).isEmpty() && $$0.getPose() == Pose.INHALING);
/*     */   }
/*     */   
/*     */   private static boolean finishedJumping(Breeze $$0) {
/* 159 */     return ($$0.getPose() == Pose.LONG_JUMPING && $$0.onGround());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Vec3 randomPointBehindTarget(LivingEntity $$0, RandomSource $$1) {
/* 165 */     int $$2 = 90;
/* 166 */     float $$3 = $$0.yHeadRot + 180.0F + (float)$$1.nextGaussian() * 90.0F / 2.0F;
/* 167 */     float $$4 = Mth.lerp($$1.nextFloat(), 4.0F, 8.0F);
/*     */     
/* 169 */     Vec3 $$5 = Vec3.directionFromRotation(0.0F, $$3).scale($$4);
/* 170 */     return $$0.position().add($$5);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BlockPos snapToSurface(LivingEntity $$0, Vec3 $$1) {
/* 175 */     ClipContext $$2 = new ClipContext($$1, $$1.relative(Direction.DOWN, 10.0D), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)$$0);
/* 176 */     BlockHitResult blockHitResult1 = $$0.level().clip($$2);
/* 177 */     if (blockHitResult1.getType() == HitResult.Type.BLOCK) {
/* 178 */       return BlockPos.containing((Position)blockHitResult1.getLocation()).above();
/*     */     }
/*     */     
/* 181 */     ClipContext $$4 = new ClipContext($$1, $$1.relative(Direction.UP, 10.0D), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)$$0);
/* 182 */     BlockHitResult blockHitResult2 = $$0.level().clip($$4);
/* 183 */     if (blockHitResult2.getType() == HitResult.Type.BLOCK) {
/* 184 */       return BlockPos.containing((Position)blockHitResult1.getLocation()).above();
/*     */     }
/* 186 */     return null;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public static boolean hasLineOfSight(Breeze $$0, Vec3 $$1) {
/* 191 */     Vec3 $$2 = new Vec3($$0.getX(), $$0.getY(), $$0.getZ());
/* 192 */     if ($$1.distanceTo($$2) > 50.0D) {
/* 193 */       return false;
/*     */     }
/* 195 */     return ($$0.level().clip(new ClipContext($$2, $$1, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)$$0)).getType() == HitResult.Type.MISS);
/*     */   }
/*     */   
/*     */   private static boolean outOfAggroRange(Breeze $$0, LivingEntity $$1) {
/* 199 */     return !$$1.closerThan((Entity)$$0, 24.0D);
/*     */   }
/*     */   
/*     */   private static boolean tooCloseForJump(Breeze $$0, LivingEntity $$1) {
/* 203 */     return ($$1.distanceTo((Entity)$$0) - 4.0F <= 0.0F);
/*     */   }
/*     */   
/*     */   private static boolean canJumpFromCurrentPosition(ServerLevel $$0, Breeze $$1) {
/* 207 */     BlockPos $$2 = $$1.blockPosition();
/*     */     
/* 209 */     for (int $$3 = 1; $$3 <= 4; $$3++) {
/* 210 */       BlockPos $$4 = $$2.relative(Direction.UP, $$3);
/* 211 */       if (!$$0.getBlockState($$4).isAir() && !$$0.getFluidState($$4).is(FluidTags.WATER)) {
/* 212 */         return false;
/*     */       }
/*     */     } 
/* 215 */     return true;
/*     */   }
/*     */   
/*     */   private static Optional<Vec3> calculateOptimalJumpVector(Breeze $$0, RandomSource $$1, Vec3 $$2) {
/* 219 */     List<Integer> $$3 = Util.shuffledCopy(ALLOWED_ANGLES, $$1);
/*     */     
/* 221 */     for (Iterator<Integer> iterator = $$3.iterator(); iterator.hasNext(); ) { int $$4 = ((Integer)iterator.next()).intValue();
/*     */       
/* 223 */       Optional<Vec3> $$5 = LongJumpUtil.calculateJumpVectorForAngle((Mob)$$0, $$2, 1.4F, $$4, false);
/*     */       
/* 225 */       if ($$5.isPresent()) {
/* 226 */         return $$5;
/*     */       } }
/*     */     
/* 229 */     return Optional.empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\breeze\LongJump.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */