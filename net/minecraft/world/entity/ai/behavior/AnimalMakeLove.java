/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ 
/*     */ public class AnimalMakeLove
/*     */   extends Behavior<Animal>
/*     */ {
/*     */   private static final int BREED_RANGE = 3;
/*     */   private static final int MIN_DURATION = 60;
/*     */   private static final int MAX_DURATION = 110;
/*     */   private final EntityType<? extends Animal> partnerType;
/*     */   private final float speedModifier;
/*     */   private long spawnChildAtTime;
/*     */   
/*     */   public AnimalMakeLove(EntityType<? extends Animal> $$0, float $$1) {
/*  29 */     super(
/*  30 */         (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT, MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT), 110);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     this.partnerType = $$0;
/*  39 */     this.speedModifier = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Animal $$1) {
/*  44 */     return ($$1.isInLove() && findValidBreedPartner($$1).isPresent());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Animal $$1, long $$2) {
/*  49 */     Animal $$3 = findValidBreedPartner($$1).get();
/*     */     
/*  51 */     $$1.getBrain().setMemory(MemoryModuleType.BREED_TARGET, $$3);
/*  52 */     $$3.getBrain().setMemory(MemoryModuleType.BREED_TARGET, $$1);
/*     */     
/*  54 */     BehaviorUtils.lockGazeAndWalkToEachOther((LivingEntity)$$1, (LivingEntity)$$3, this.speedModifier);
/*     */     
/*  56 */     int $$4 = 60 + $$1.getRandom().nextInt(50);
/*  57 */     this.spawnChildAtTime = $$2 + $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Animal $$1, long $$2) {
/*  62 */     if (!hasBreedTargetOfRightType($$1)) {
/*  63 */       return false;
/*     */     }
/*  65 */     Animal $$3 = getBreedTarget($$1);
/*     */     
/*  67 */     return ($$3.isAlive() && $$1
/*  68 */       .canMate($$3) && 
/*  69 */       BehaviorUtils.entityIsVisible($$1.getBrain(), (LivingEntity)$$3) && $$2 <= this.spawnChildAtTime && 
/*     */       
/*  71 */       !$$1.isPanicking() && !$$3.isPanicking());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Animal $$1, long $$2) {
/*  76 */     Animal $$3 = getBreedTarget($$1);
/*     */     
/*  78 */     BehaviorUtils.lockGazeAndWalkToEachOther((LivingEntity)$$1, (LivingEntity)$$3, this.speedModifier);
/*  79 */     if (!$$1.closerThan((Entity)$$3, 3.0D)) {
/*     */       return;
/*     */     }
/*  82 */     if ($$2 >= this.spawnChildAtTime) {
/*  83 */       $$1.spawnChildFromBreeding($$0, $$3);
/*  84 */       $$1.getBrain().eraseMemory(MemoryModuleType.BREED_TARGET);
/*  85 */       $$3.getBrain().eraseMemory(MemoryModuleType.BREED_TARGET);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Animal $$1, long $$2) {
/*  91 */     $$1.getBrain().eraseMemory(MemoryModuleType.BREED_TARGET);
/*  92 */     $$1.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/*  93 */     $$1.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
/*  94 */     this.spawnChildAtTime = 0L;
/*     */   }
/*     */   
/*     */   private Animal getBreedTarget(Animal $$0) {
/*  98 */     return $$0.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
/*     */   }
/*     */   
/*     */   private boolean hasBreedTargetOfRightType(Animal $$0) {
/* 102 */     Brain<?> $$1 = $$0.getBrain();
/* 103 */     return ($$1.hasMemoryValue(MemoryModuleType.BREED_TARGET) && ((AgeableMob)$$1
/* 104 */       .getMemory(MemoryModuleType.BREED_TARGET).get()).getType() == this.partnerType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Optional<? extends Animal> findValidBreedPartner(Animal $$0) {
/* 111 */     Objects.requireNonNull(Animal.class); return ((NearestVisibleLivingEntities)$$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES).get()).findClosest($$1 -> { // Byte code:
/*     */           //   0: aload_2
/*     */           //   1: invokevirtual getType : ()Lnet/minecraft/world/entity/EntityType;
/*     */           //   4: aload_0
/*     */           //   5: getfield partnerType : Lnet/minecraft/world/entity/EntityType;
/*     */           //   8: if_acmpne -> 42
/*     */           //   11: aload_2
/*     */           //   12: instanceof net/minecraft/world/entity/animal/Animal
/*     */           //   15: ifeq -> 42
/*     */           //   18: aload_2
/*     */           //   19: checkcast net/minecraft/world/entity/animal/Animal
/*     */           //   22: astore_3
/*     */           //   23: aload_1
/*     */           //   24: aload_3
/*     */           //   25: invokevirtual canMate : (Lnet/minecraft/world/entity/animal/Animal;)Z
/*     */           //   28: ifeq -> 42
/*     */           //   31: aload_3
/*     */           //   32: invokevirtual isPanicking : ()Z
/*     */           //   35: ifne -> 42
/*     */           //   38: iconst_1
/*     */           //   39: goto -> 43
/*     */           //   42: iconst_0
/*     */           //   43: ireturn
/*     */           // Line number table:
/*     */           //   Java source line number -> byte code offset
/*     */           //   #110	-> 0
/*     */           //   #109	-> 1
/*     */           //   #110	-> 25
/*     */           // Local variable table:
/*     */           //   start	length	slot	name	descriptor
/*     */           //   0	44	0	this	Lnet/minecraft/world/entity/ai/behavior/AnimalMakeLove;
/*     */           //   0	44	1	$$0	Lnet/minecraft/world/entity/animal/Animal;
/*     */           //   0	44	2	$$1	Lnet/minecraft/world/entity/LivingEntity;
/* 111 */           //   23	19	3	$$2	Lnet/minecraft/world/entity/animal/Animal; }).map(Animal.class::cast);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\AnimalMakeLove.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */