/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.monster.CrossbowAttackMob;
/*     */ import net.minecraft.world.entity.monster.RangedAttackMob;
/*     */ import net.minecraft.world.entity.projectile.ProjectileUtil;
/*     */ import net.minecraft.world.item.CrossbowItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ 
/*     */ public class CrossbowAttack<E extends Mob & CrossbowAttackMob, T extends LivingEntity>
/*     */   extends Behavior<E> {
/*     */   private static final int TIMEOUT = 1200;
/*     */   private int attackDelay;
/*     */   
/*     */   private enum CrossbowState {
/*  24 */     UNCHARGED,
/*  25 */     CHARGING,
/*  26 */     CHARGED,
/*  27 */     READY_TO_ATTACK;
/*     */   }
/*     */ 
/*     */   
/*  31 */   private CrossbowState crossbowState = CrossbowState.UNCHARGED;
/*     */   
/*     */   public CrossbowAttack() {
/*  34 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), 1200);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, E $$1) {
/*  42 */     LivingEntity $$2 = getAttackTarget((LivingEntity)$$1);
/*  43 */     return ($$1.isHolding(Items.CROSSBOW) && BehaviorUtils.canSee((LivingEntity)$$1, $$2) && BehaviorUtils.isWithinAttackRange((Mob)$$1, $$2, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, E $$1, long $$2) {
/*  48 */     return ($$1.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && checkExtraStartConditions($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, E $$1, long $$2) {
/*  54 */     LivingEntity $$3 = getAttackTarget((LivingEntity)$$1);
/*  55 */     lookAtTarget((Mob)$$1, $$3);
/*  56 */     crossbowAttack($$1, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, E $$1, long $$2) {
/*  61 */     if ($$1.isUsingItem()) {
/*  62 */       $$1.stopUsingItem();
/*     */     }
/*  64 */     if ($$1.isHolding(Items.CROSSBOW)) {
/*  65 */       ((CrossbowAttackMob)$$1).setChargingCrossbow(false);
/*  66 */       CrossbowItem.setCharged($$1.getUseItem(), false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void crossbowAttack(E $$0, LivingEntity $$1) {
/*  72 */     if (this.crossbowState == CrossbowState.UNCHARGED) {
/*  73 */       $$0.startUsingItem(ProjectileUtil.getWeaponHoldingHand((LivingEntity)$$0, Items.CROSSBOW));
/*  74 */       this.crossbowState = CrossbowState.CHARGING;
/*  75 */       ((CrossbowAttackMob)$$0).setChargingCrossbow(true);
/*  76 */     } else if (this.crossbowState == CrossbowState.CHARGING) {
/*  77 */       if (!$$0.isUsingItem()) {
/*  78 */         this.crossbowState = CrossbowState.UNCHARGED;
/*     */       }
/*  80 */       int $$2 = $$0.getTicksUsingItem();
/*  81 */       ItemStack $$3 = $$0.getUseItem();
/*  82 */       if ($$2 >= CrossbowItem.getChargeDuration($$3)) {
/*  83 */         $$0.releaseUsingItem();
/*  84 */         this.crossbowState = CrossbowState.CHARGED;
/*  85 */         this.attackDelay = 20 + $$0.getRandom().nextInt(20);
/*  86 */         ((CrossbowAttackMob)$$0).setChargingCrossbow(false);
/*     */       } 
/*  88 */     } else if (this.crossbowState == CrossbowState.CHARGED) {
/*  89 */       this.attackDelay--;
/*  90 */       if (this.attackDelay == 0) {
/*  91 */         this.crossbowState = CrossbowState.READY_TO_ATTACK;
/*     */       }
/*  93 */     } else if (this.crossbowState == CrossbowState.READY_TO_ATTACK) {
/*  94 */       ((RangedAttackMob)$$0).performRangedAttack($$1, 1.0F);
/*     */       
/*  96 */       ItemStack $$4 = $$0.getItemInHand(ProjectileUtil.getWeaponHoldingHand((LivingEntity)$$0, Items.CROSSBOW));
/*  97 */       CrossbowItem.setCharged($$4, false);
/*  98 */       this.crossbowState = CrossbowState.UNCHARGED;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void lookAtTarget(Mob $$0, LivingEntity $$1) {
/* 103 */     $$0.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker((Entity)$$1, true));
/*     */   }
/*     */   
/*     */   private static LivingEntity getAttackTarget(LivingEntity $$0) {
/* 107 */     return $$0.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\CrossbowAttack.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */