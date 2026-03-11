/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class AnimalPanic extends Behavior<PathfinderMob> {
/*     */   private static final int PANIC_MIN_DURATION = 100;
/*     */   private static final int PANIC_MAX_DURATION = 120;
/*     */   private static final int PANIC_DISTANCE_HORIZONTAL = 5;
/*     */   
/*     */   static {
/*  28 */     DEFAULT_SHOULD_PANIC_PREDICATE = ($$0 -> ($$0.getLastHurtByMob() != null || $$0.isFreezing() || $$0.isOnFire()));
/*     */   }
/*     */   private static final int PANIC_DISTANCE_VERTICAL = 4;
/*     */   private static final Predicate<PathfinderMob> DEFAULT_SHOULD_PANIC_PREDICATE;
/*     */   
/*     */   public AnimalPanic(float $$0) {
/*  34 */     this($$0, DEFAULT_SHOULD_PANIC_PREDICATE);
/*     */   }
/*     */   private final float speedMultiplier; private final Predicate<PathfinderMob> shouldPanic;
/*     */   public AnimalPanic(float $$0, Predicate<PathfinderMob> $$1) {
/*  38 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.IS_PANICKING, MemoryStatus.REGISTERED, MemoryModuleType.HURT_BY, MemoryStatus.VALUE_PRESENT), 100, 120);
/*  39 */     this.speedMultiplier = $$0;
/*  40 */     this.shouldPanic = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, PathfinderMob $$1) {
/*  45 */     return this.shouldPanic.test($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/*  55 */     $$1.getBrain().setMemory(MemoryModuleType.IS_PANICKING, Boolean.valueOf(true));
/*  56 */     $$1.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/*  61 */     Brain<?> $$3 = $$1.getBrain();
/*  62 */     $$3.eraseMemory(MemoryModuleType.IS_PANICKING);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, PathfinderMob $$1, long $$2) {
/*  67 */     if ($$1.getNavigation().isDone()) {
/*  68 */       Vec3 $$3 = getPanicPos($$1, $$0);
/*  69 */       if ($$3 != null) {
/*  70 */         $$1.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget($$3, this.speedMultiplier, 0));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Vec3 getPanicPos(PathfinderMob $$0, ServerLevel $$1) {
/*  77 */     if ($$0.isOnFire()) {
/*  78 */       Optional<Vec3> $$2 = lookForWater((BlockGetter)$$1, (Entity)$$0).map(Vec3::atBottomCenterOf);
/*  79 */       if ($$2.isPresent()) {
/*  80 */         return $$2.get();
/*     */       }
/*     */     } 
/*     */     
/*  84 */     return LandRandomPos.getPos($$0, 5, 4);
/*     */   }
/*     */   private Optional<BlockPos> lookForWater(BlockGetter $$0, Entity $$1) {
/*     */     Predicate<BlockPos> $$4;
/*  88 */     BlockPos $$2 = $$1.blockPosition();
/*  89 */     if (!$$0.getBlockState($$2).getCollisionShape($$0, $$2).isEmpty()) {
/*  90 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/*  94 */     if (Mth.ceil($$1.getBbWidth()) == 2) {
/*  95 */       Predicate<BlockPos> $$3 = $$1 -> BlockPos.squareOutSouthEast($$1).allMatch(());
/*     */     } else {
/*  97 */       $$4 = ($$1 -> $$0.getFluidState($$1).is(FluidTags.WATER));
/*     */     } 
/*     */     
/* 100 */     return BlockPos.findClosestMatch($$2, 5, 1, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\AnimalPanic.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */