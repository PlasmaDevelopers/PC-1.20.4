/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.animal.allay.Allay;
/*     */ import net.minecraft.world.entity.animal.allay.AllayAi;
/*     */ import net.minecraft.world.entity.npc.InventoryCarrier;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class GoAndGiveItemsToTarget<E extends LivingEntity & InventoryCarrier>
/*     */   extends Behavior<E>
/*     */ {
/*     */   private static final int CLOSE_ENOUGH_DISTANCE_TO_TARGET = 3;
/*     */   private static final int ITEM_PICKUP_COOLDOWN_AFTER_THROWING = 60;
/*     */   private final Function<LivingEntity, Optional<PositionTracker>> targetPositionGetter;
/*     */   private final float speedModifier;
/*     */   
/*     */   public GoAndGiveItemsToTarget(Function<LivingEntity, Optional<PositionTracker>> $$0, float $$1, int $$2) {
/*  34 */     super(Map.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, MemoryStatus.REGISTERED), $$2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     this.targetPositionGetter = $$0;
/*  40 */     this.speedModifier = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, E $$1) {
/*  45 */     return canThrowItemToTarget($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, E $$1, long $$2) {
/*  50 */     return canThrowItemToTarget($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, E $$1, long $$2) {
/*  55 */     ((Optional)this.targetPositionGetter.apply((LivingEntity)$$1)).ifPresent($$1 -> BehaviorUtils.setWalkAndLookTargetMemories($$0, $$1, this.speedModifier, 3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, E $$1, long $$2) {
/*  62 */     Optional<PositionTracker> $$3 = this.targetPositionGetter.apply((LivingEntity)$$1);
/*  63 */     if ($$3.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  67 */     PositionTracker $$4 = $$3.get();
/*  68 */     double $$5 = $$4.currentPosition().distanceTo($$1.getEyePosition());
/*  69 */     if ($$5 < 3.0D) {
/*  70 */       ItemStack $$6 = ((InventoryCarrier)$$1).getInventory().removeItem(0, 1);
/*  71 */       if (!$$6.isEmpty()) {
/*  72 */         throwItem((LivingEntity)$$1, $$6, getThrowPosition($$4));
/*  73 */         if ($$1 instanceof Allay) { Allay $$7 = (Allay)$$1;
/*  74 */           AllayAi.getLikedPlayer((LivingEntity)$$7).ifPresent($$2 -> triggerDropItemOnBlock($$0, $$1, $$2)); }
/*     */         
/*  76 */         $$1.getBrain().setMemory(MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, Integer.valueOf(60));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void triggerDropItemOnBlock(PositionTracker $$0, ItemStack $$1, ServerPlayer $$2) {
/*  82 */     BlockPos $$3 = $$0.currentBlockPosition().below();
/*  83 */     CriteriaTriggers.ALLAY_DROP_ITEM_ON_BLOCK.trigger($$2, $$3, $$1);
/*     */   }
/*     */   
/*     */   private boolean canThrowItemToTarget(E $$0) {
/*  87 */     if (((InventoryCarrier)$$0).getInventory().isEmpty()) {
/*  88 */       return false;
/*     */     }
/*  90 */     Optional<PositionTracker> $$1 = this.targetPositionGetter.apply((LivingEntity)$$0);
/*  91 */     return $$1.isPresent();
/*     */   }
/*     */   
/*     */   private static Vec3 getThrowPosition(PositionTracker $$0) {
/*  95 */     return $$0.currentPosition().add(0.0D, 1.0D, 0.0D);
/*     */   }
/*     */   
/*     */   public static void throwItem(LivingEntity $$0, ItemStack $$1, Vec3 $$2) {
/*  99 */     Vec3 $$3 = new Vec3(0.20000000298023224D, 0.30000001192092896D, 0.20000000298023224D);
/* 100 */     BehaviorUtils.throwItem($$0, $$1, $$2, $$3, 0.2F);
/*     */     
/* 102 */     Level $$4 = $$0.level();
/* 103 */     if ($$4.getGameTime() % 7L == 0L && $$4.random.nextDouble() < 0.9D) {
/* 104 */       float $$5 = ((Float)Util.getRandom((List)Allay.THROW_SOUND_PITCHES, $$4.getRandom())).floatValue();
/* 105 */       $$4.playSound(null, (Entity)$$0, SoundEvents.ALLAY_THROW, SoundSource.NEUTRAL, 1.0F, $$5);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\GoAndGiveItemsToTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */