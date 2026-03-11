/*     */ package net.minecraft.world.entity.animal.frog;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*     */ import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ShootTongue
/*     */   extends Behavior<Frog>
/*     */ {
/*     */   public static final int TIME_OUT_DURATION = 100;
/*     */   public static final int CATCH_ANIMATION_DURATION = 6;
/*     */   public static final int TONGUE_ANIMATION_DURATION = 10;
/*     */   private static final float EATING_DISTANCE = 1.75F;
/*     */   private static final float EATING_MOVEMENT_FACTOR = 0.75F;
/*     */   public static final int UNREACHABLE_TONGUE_TARGETS_COOLDOWN_DURATION = 100;
/*     */   public static final int MAX_UNREACHBLE_TONGUE_TARGETS_IN_MEMORY = 5;
/*     */   private int eatAnimationTimer;
/*     */   private int calculatePathCounter;
/*     */   private final SoundEvent tongueSound;
/*     */   private final SoundEvent eatSound;
/*     */   private Vec3 itemSpawnPos;
/*     */   
/*     */   private enum State {
/*  40 */     MOVE_TO_TARGET,
/*  41 */     CATCH_ANIMATION,
/*  42 */     EAT_ANIMATION,
/*  43 */     DONE;
/*     */   }
/*     */   
/*  46 */   private State state = State.DONE;
/*     */   
/*     */   public ShootTongue(SoundEvent $$0, SoundEvent $$1) {
/*  49 */     super((Map)ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT), 100);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     this.tongueSound = $$0;
/*  57 */     this.eatSound = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Frog $$1) {
/*  62 */     LivingEntity $$2 = $$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
/*     */     
/*  64 */     boolean $$3 = canPathfindToTarget($$1, $$2);
/*  65 */     if (!$$3) {
/*  66 */       $$1.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
/*  67 */       addUnreachableTargetToMemory($$1, $$2);
/*     */     } 
/*  69 */     return ($$3 && $$1
/*  70 */       .getPose() != Pose.CROAKING && 
/*  71 */       Frog.canEat($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Frog $$1, long $$2) {
/*  76 */     return ($$1.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && this.state != State.DONE && 
/*     */       
/*  78 */       !$$1.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Frog $$1, long $$2) {
/*  83 */     LivingEntity $$3 = $$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
/*     */     
/*  85 */     BehaviorUtils.lookAtEntity((LivingEntity)$$1, $$3);
/*  86 */     $$1.setTongueTarget((Entity)$$3);
/*     */     
/*  88 */     $$1.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget($$3.position(), 2.0F, 0));
/*  89 */     this.calculatePathCounter = 10;
/*  90 */     this.state = State.MOVE_TO_TARGET;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Frog $$1, long $$2) {
/*  95 */     $$1.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
/*  96 */     $$1.eraseTongueTarget();
/*  97 */     $$1.setPose(Pose.STANDING);
/*     */   }
/*     */   
/*     */   private void eatEntity(ServerLevel $$0, Frog $$1) {
/* 101 */     $$0.playSound(null, (Entity)$$1, this.eatSound, SoundSource.NEUTRAL, 2.0F, 1.0F);
/*     */     
/* 103 */     Optional<Entity> $$2 = $$1.getTongueTarget();
/* 104 */     if ($$2.isPresent()) {
/* 105 */       Entity $$3 = $$2.get();
/* 106 */       if ($$3.isAlive()) {
/* 107 */         $$1.doHurtTarget($$3);
/*     */         
/* 109 */         if (!$$3.isAlive()) {
/* 110 */           $$3.remove(Entity.RemovalReason.KILLED);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Frog $$1, long $$2) {
/* 118 */     LivingEntity $$3 = $$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
/* 119 */     $$1.setTongueTarget((Entity)$$3);
/*     */     
/* 121 */     switch (this.state) {
/*     */       case MOVE_TO_TARGET:
/* 123 */         if ($$3.distanceTo((Entity)$$1) < 1.75F) {
/* 124 */           $$0.playSound(null, (Entity)$$1, this.tongueSound, SoundSource.NEUTRAL, 2.0F, 1.0F);
/* 125 */           $$1.setPose(Pose.USING_TONGUE);
/* 126 */           $$3.setDeltaMovement($$3.position().vectorTo($$1.position()).normalize().scale(0.75D));
/* 127 */           this.itemSpawnPos = $$3.position();
/* 128 */           this.eatAnimationTimer = 0;
/* 129 */           this.state = State.CATCH_ANIMATION; break;
/*     */         } 
/* 131 */         if (this.calculatePathCounter <= 0) {
/* 132 */           $$1.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget($$3.position(), 2.0F, 0));
/* 133 */           this.calculatePathCounter = 10; break;
/*     */         } 
/* 135 */         this.calculatePathCounter--;
/*     */         break;
/*     */ 
/*     */       
/*     */       case CATCH_ANIMATION:
/* 140 */         if (this.eatAnimationTimer++ >= 6) {
/* 141 */           this.state = State.EAT_ANIMATION;
/* 142 */           eatEntity($$0, $$1);
/*     */         } 
/*     */         break;
/*     */       case EAT_ANIMATION:
/* 146 */         if (this.eatAnimationTimer >= 10) {
/* 147 */           this.state = State.DONE; break;
/*     */         } 
/* 149 */         this.eatAnimationTimer++;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canPathfindToTarget(Frog $$0, LivingEntity $$1) {
/* 158 */     Path $$2 = $$0.getNavigation().createPath((Entity)$$1, 0);
/* 159 */     return ($$2 != null && $$2.getDistToTarget() < 1.75F);
/*     */   }
/*     */   
/*     */   private void addUnreachableTargetToMemory(Frog $$0, LivingEntity $$1) {
/* 163 */     List<UUID> $$2 = $$0.getBrain().getMemory(MemoryModuleType.UNREACHABLE_TONGUE_TARGETS).orElseGet(java.util.ArrayList::new);
/* 164 */     boolean $$3 = !$$2.contains($$1.getUUID());
/*     */     
/* 166 */     if ($$2.size() == 5 && $$3) {
/* 167 */       $$2.remove(0);
/*     */     }
/*     */     
/* 170 */     if ($$3) {
/* 171 */       $$2.add($$1.getUUID());
/*     */     }
/* 173 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.UNREACHABLE_TONGUE_TARGETS, $$2, 100L);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\frog\ShootTongue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */