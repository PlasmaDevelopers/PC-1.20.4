/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VillagerMakeLove
/*     */   extends Behavior<Villager>
/*     */ {
/*     */   private static final int INTERACT_DIST_SQR = 5;
/*     */   private static final float SPEED_MODIFIER = 0.5F;
/*     */   private long birthTimestamp;
/*     */   
/*     */   public VillagerMakeLove() {
/*  33 */     super(
/*  34 */         (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT), 350, 350);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/*  45 */     return isBreedingPossible($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/*  50 */     return ($$2 <= this.birthTimestamp && isBreedingPossible($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Villager $$1, long $$2) {
/*  55 */     AgeableMob $$3 = $$1.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
/*     */     
/*  57 */     BehaviorUtils.lockGazeAndWalkToEachOther((LivingEntity)$$1, (LivingEntity)$$3, 0.5F);
/*     */     
/*  59 */     $$0.broadcastEntityEvent((Entity)$$3, (byte)18);
/*  60 */     $$0.broadcastEntityEvent((Entity)$$1, (byte)18);
/*     */     
/*  62 */     int $$4 = 275 + $$1.getRandom().nextInt(50);
/*  63 */     this.birthTimestamp = $$2 + $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Villager $$1, long $$2) {
/*  68 */     Villager $$3 = $$1.getBrain().getMemory(MemoryModuleType.BREED_TARGET).get();
/*     */     
/*  70 */     if ($$1.distanceToSqr((Entity)$$3) > 5.0D) {
/*     */       return;
/*     */     }
/*     */     
/*  74 */     BehaviorUtils.lockGazeAndWalkToEachOther((LivingEntity)$$1, (LivingEntity)$$3, 0.5F);
/*     */     
/*  76 */     if ($$2 >= this.birthTimestamp) {
/*     */       
/*  78 */       $$1.eatAndDigestFood();
/*  79 */       $$3.eatAndDigestFood();
/*     */       
/*  81 */       tryToGiveBirth($$0, $$1, $$3);
/*  82 */     } else if ($$1.getRandom().nextInt(35) == 0) {
/*  83 */       $$0.broadcastEntityEvent((Entity)$$3, (byte)12);
/*  84 */       $$0.broadcastEntityEvent((Entity)$$1, (byte)12);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void tryToGiveBirth(ServerLevel $$0, Villager $$1, Villager $$2) {
/*  90 */     Optional<BlockPos> $$3 = takeVacantBed($$0, $$1);
/*  91 */     if ($$3.isEmpty()) {
/*     */       
/*  93 */       $$0.broadcastEntityEvent((Entity)$$2, (byte)13);
/*  94 */       $$0.broadcastEntityEvent((Entity)$$1, (byte)13);
/*     */     } else {
/*  96 */       Optional<Villager> $$4 = breed($$0, $$1, $$2);
/*     */       
/*  98 */       if ($$4.isPresent()) {
/*  99 */         giveBedToChild($$0, $$4.get(), $$3.get());
/*     */       } else {
/* 101 */         $$0.getPoiManager().release($$3.get());
/* 102 */         DebugPackets.sendPoiTicketCountPacket($$0, $$3.get());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Villager $$1, long $$2) {
/* 109 */     $$1.getBrain().eraseMemory(MemoryModuleType.BREED_TARGET);
/*     */   }
/*     */   
/*     */   private boolean isBreedingPossible(Villager $$0) {
/* 113 */     Brain<Villager> $$1 = $$0.getBrain();
/*     */     
/* 115 */     Optional<AgeableMob> $$2 = $$1.getMemory(MemoryModuleType.BREED_TARGET).filter($$0 -> ($$0.getType() == EntityType.VILLAGER));
/* 116 */     if ($$2.isEmpty()) {
/* 117 */       return false;
/*     */     }
/* 119 */     return (BehaviorUtils.targetIsValid($$1, MemoryModuleType.BREED_TARGET, EntityType.VILLAGER) && $$0
/* 120 */       .canBreed() && ((AgeableMob)$$2
/* 121 */       .get()).canBreed());
/*     */   }
/*     */   
/*     */   private Optional<BlockPos> takeVacantBed(ServerLevel $$0, Villager $$1) {
/* 125 */     return $$0.getPoiManager().take($$0 -> $$0.is(PoiTypes.HOME), ($$1, $$2) -> canReach($$0, $$2, $$1), $$1
/*     */ 
/*     */         
/* 128 */         .blockPosition(), 48);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canReach(Villager $$0, BlockPos $$1, Holder<PoiType> $$2) {
/* 134 */     Path $$3 = $$0.getNavigation().createPath($$1, ((PoiType)$$2.value()).validRange());
/* 135 */     return ($$3 != null && $$3.canReach());
/*     */   }
/*     */   
/*     */   private Optional<Villager> breed(ServerLevel $$0, Villager $$1, Villager $$2) {
/* 139 */     Villager $$3 = $$1.getBreedOffspring($$0, (AgeableMob)$$2);
/* 140 */     if ($$3 == null) {
/* 141 */       return Optional.empty();
/*     */     }
/* 143 */     $$1.setAge(6000);
/* 144 */     $$2.setAge(6000);
/* 145 */     $$3.setAge(-24000);
/* 146 */     $$3.moveTo($$1.getX(), $$1.getY(), $$1.getZ(), 0.0F, 0.0F);
/*     */     
/* 148 */     $$0.addFreshEntityWithPassengers((Entity)$$3);
/* 149 */     $$0.broadcastEntityEvent((Entity)$$3, (byte)12);
/*     */     
/* 151 */     return Optional.of($$3);
/*     */   }
/*     */   
/*     */   private void giveBedToChild(ServerLevel $$0, Villager $$1, BlockPos $$2) {
/* 155 */     GlobalPos $$3 = GlobalPos.of($$0.dimension(), $$2);
/* 156 */     $$1.getBrain().setMemory(MemoryModuleType.HOME, $$3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\VillagerMakeLove.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */