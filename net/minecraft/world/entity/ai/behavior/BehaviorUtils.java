/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.ProjectileWeaponItem;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BehaviorUtils
/*     */ {
/*     */   public static void lockGazeAndWalkToEachOther(LivingEntity $$0, LivingEntity $$1, float $$2) {
/*  37 */     lookAtEachOther($$0, $$1);
/*  38 */     setWalkAndLookTargetMemoriesToEachOther($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static boolean entityIsVisible(Brain<?> $$0, LivingEntity $$1) {
/*  42 */     Optional<NearestVisibleLivingEntities> $$2 = $$0.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
/*  43 */     return ($$2.isPresent() && ((NearestVisibleLivingEntities)$$2.get()).contains($$1));
/*     */   }
/*     */   
/*     */   public static boolean targetIsValid(Brain<?> $$0, MemoryModuleType<? extends LivingEntity> $$1, EntityType<?> $$2) {
/*  47 */     return targetIsValid($$0, $$1, $$1 -> ($$1.getType() == $$0));
/*     */   }
/*     */   
/*     */   private static boolean targetIsValid(Brain<?> $$0, MemoryModuleType<? extends LivingEntity> $$1, Predicate<LivingEntity> $$2) {
/*  51 */     return $$0.getMemory($$1)
/*  52 */       .filter($$2)
/*  53 */       .filter(LivingEntity::isAlive)
/*  54 */       .filter($$1 -> entityIsVisible($$0, $$1))
/*  55 */       .isPresent();
/*     */   }
/*     */   
/*     */   private static void lookAtEachOther(LivingEntity $$0, LivingEntity $$1) {
/*  59 */     lookAtEntity($$0, $$1);
/*  60 */     lookAtEntity($$1, $$0);
/*     */   }
/*     */   
/*     */   public static void lookAtEntity(LivingEntity $$0, LivingEntity $$1) {
/*  64 */     $$0.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker((Entity)$$1, true));
/*     */   }
/*     */   
/*     */   private static void setWalkAndLookTargetMemoriesToEachOther(LivingEntity $$0, LivingEntity $$1, float $$2) {
/*  68 */     int $$3 = 2;
/*  69 */     setWalkAndLookTargetMemories($$0, (Entity)$$1, $$2, 2);
/*  70 */     setWalkAndLookTargetMemories($$1, (Entity)$$0, $$2, 2);
/*     */   }
/*     */   
/*     */   public static void setWalkAndLookTargetMemories(LivingEntity $$0, Entity $$1, float $$2, int $$3) {
/*  74 */     setWalkAndLookTargetMemories($$0, new EntityTracker($$1, true), $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void setWalkAndLookTargetMemories(LivingEntity $$0, BlockPos $$1, float $$2, int $$3) {
/*  78 */     setWalkAndLookTargetMemories($$0, new BlockPosTracker($$1), $$2, $$3);
/*     */   }
/*     */   
/*     */   public static void setWalkAndLookTargetMemories(LivingEntity $$0, PositionTracker $$1, float $$2, int $$3) {
/*  82 */     WalkTarget $$4 = new WalkTarget($$1, $$2, $$3);
/*  83 */     $$0.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, $$1);
/*  84 */     $$0.getBrain().setMemory(MemoryModuleType.WALK_TARGET, $$4);
/*     */   }
/*     */   
/*     */   public static void throwItem(LivingEntity $$0, ItemStack $$1, Vec3 $$2) {
/*  88 */     Vec3 $$3 = new Vec3(0.30000001192092896D, 0.30000001192092896D, 0.30000001192092896D);
/*  89 */     throwItem($$0, $$1, $$2, $$3, 0.3F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void throwItem(LivingEntity $$0, ItemStack $$1, Vec3 $$2, Vec3 $$3, float $$4) {
/*  94 */     double $$5 = $$0.getEyeY() - $$4;
/*  95 */     ItemEntity $$6 = new ItemEntity($$0.level(), $$0.getX(), $$5, $$0.getZ(), $$1);
/*  96 */     $$6.setThrower((Entity)$$0);
/*     */     
/*  98 */     Vec3 $$7 = $$2.subtract($$0.position());
/*  99 */     $$7 = $$7.normalize().multiply($$3.x, $$3.y, $$3.z);
/*     */     
/* 101 */     $$6.setDeltaMovement($$7);
/* 102 */     $$6.setDefaultPickUpDelay();
/* 103 */     $$0.level().addFreshEntity((Entity)$$6);
/*     */   }
/*     */   
/*     */   public static SectionPos findSectionClosestToVillage(ServerLevel $$0, SectionPos $$1, int $$2) {
/* 107 */     int $$3 = $$0.sectionsToVillage($$1);
/*     */ 
/*     */ 
/*     */     
/* 111 */     Objects.requireNonNull($$0); return SectionPos.cube($$1, $$2).filter($$2 -> ($$0.sectionsToVillage($$2) < $$1)).min(Comparator.comparingInt($$0::sectionsToVillage))
/* 112 */       .orElse($$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWithinAttackRange(Mob $$0, LivingEntity $$1, int $$2) {
/* 119 */     Item item = $$0.getMainHandItem().getItem(); if (item instanceof ProjectileWeaponItem) { ProjectileWeaponItem $$3 = (ProjectileWeaponItem)item; if ($$0.canFireProjectileWeapon($$3)) {
/* 120 */         int $$4 = $$3.getDefaultProjectileRange() - $$2;
/* 121 */         return $$0.closerThan((Entity)$$1, $$4);
/*     */       }  }
/* 123 */      return $$0.isWithinMeleeAttackRange($$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOtherTargetMuchFurtherAwayThanCurrentAttackTarget(LivingEntity $$0, LivingEntity $$1, double $$2) {
/* 131 */     Optional<LivingEntity> $$3 = $$0.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
/* 132 */     if ($$3.isEmpty()) {
/* 133 */       return false;
/*     */     }
/* 135 */     double $$4 = $$0.distanceToSqr(((LivingEntity)$$3.get()).position());
/* 136 */     double $$5 = $$0.distanceToSqr($$1.position());
/* 137 */     return ($$5 > $$4 + $$2 * $$2);
/*     */   }
/*     */   
/*     */   public static boolean canSee(LivingEntity $$0, LivingEntity $$1) {
/* 141 */     Brain<?> $$2 = $$0.getBrain();
/* 142 */     if (!$$2.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)) {
/* 143 */       return false;
/*     */     }
/* 145 */     return ((NearestVisibleLivingEntities)$$2.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).get()).contains($$1);
/*     */   }
/*     */   
/*     */   public static LivingEntity getNearestTarget(LivingEntity $$0, Optional<LivingEntity> $$1, LivingEntity $$2) {
/* 149 */     if ($$1.isEmpty()) {
/* 150 */       return $$2;
/*     */     }
/* 152 */     return getTargetNearestMe($$0, $$1.get(), $$2);
/*     */   }
/*     */   
/*     */   public static LivingEntity getTargetNearestMe(LivingEntity $$0, LivingEntity $$1, LivingEntity $$2) {
/* 156 */     Vec3 $$3 = $$1.position();
/* 157 */     Vec3 $$4 = $$2.position();
/* 158 */     return ($$0.distanceToSqr($$3) < $$0.distanceToSqr($$4)) ? $$1 : $$2;
/*     */   }
/*     */   
/*     */   public static Optional<LivingEntity> getLivingEntityFromUUIDMemory(LivingEntity $$0, MemoryModuleType<UUID> $$1) {
/* 162 */     Optional<UUID> $$2 = $$0.getBrain().getMemory($$1);
/*     */     
/* 164 */     return $$2.map($$1 -> ((ServerLevel)$$0.level()).getEntity($$1)).map($$0 -> {
/*     */           LivingEntity $$1 = (LivingEntity)$$0;
/*     */           return ($$0 instanceof LivingEntity) ? $$1 : null;
/*     */         }); } @Nullable
/*     */   public static Vec3 getRandomSwimmablePos(PathfinderMob $$0, int $$1, int $$2) {
/* 169 */     Vec3 $$3 = DefaultRandomPos.getPos($$0, $$1, $$2);
/* 170 */     int $$4 = 0;
/* 171 */     while ($$3 != null && !$$0.level().getBlockState(BlockPos.containing((Position)$$3)).isPathfindable((BlockGetter)$$0.level(), BlockPos.containing((Position)$$3), PathComputationType.WATER) && $$4++ < 10) {
/* 172 */       $$3 = DefaultRandomPos.getPos($$0, $$1, $$2);
/*     */     }
/* 174 */     return $$3;
/*     */   }
/*     */   
/*     */   public static boolean isBreeding(LivingEntity $$0) {
/* 178 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\BehaviorUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */