/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.ToDoubleFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.valueproviders.UniformInt;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.animal.goat.Goat;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RamTarget
/*     */   extends Behavior<Goat>
/*     */ {
/*     */   public static final int TIME_OUT_DURATION = 200;
/*     */   public static final float RAM_SPEED_FORCE_FACTOR = 1.65F;
/*     */   private final Function<Goat, UniformInt> getTimeBetweenRams;
/*     */   private final TargetingConditions ramTargeting;
/*     */   private final float speed;
/*     */   private final ToDoubleFunction<Goat> getKnockbackForce;
/*     */   private Vec3 ramDirection;
/*     */   private final Function<Goat, SoundEvent> getImpactSound;
/*     */   private final Function<Goat, SoundEvent> getHornBreakSound;
/*     */   
/*     */   public RamTarget(Function<Goat, UniformInt> $$0, TargetingConditions $$1, float $$2, ToDoubleFunction<Goat> $$3, Function<Goat, SoundEvent> $$4, Function<Goat, SoundEvent> $$5) {
/*  50 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT, MemoryModuleType.RAM_TARGET, MemoryStatus.VALUE_PRESENT), 200);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     this.getTimeBetweenRams = $$0;
/*  56 */     this.ramTargeting = $$1;
/*  57 */     this.speed = $$2;
/*  58 */     this.getKnockbackForce = $$3;
/*  59 */     this.getImpactSound = $$4;
/*  60 */     this.getHornBreakSound = $$5;
/*     */     
/*  62 */     this.ramDirection = Vec3.ZERO;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Goat $$1) {
/*  67 */     return $$1.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Goat $$1, long $$2) {
/*  72 */     return $$1.getBrain().hasMemoryValue(MemoryModuleType.RAM_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Goat $$1, long $$2) {
/*  77 */     BlockPos $$3 = $$1.blockPosition();
/*  78 */     Brain<?> $$4 = $$1.getBrain();
/*  79 */     Vec3 $$5 = $$4.getMemory(MemoryModuleType.RAM_TARGET).get();
/*     */ 
/*     */     
/*  82 */     this.ramDirection = (new Vec3($$3.getX() - $$5.x(), 0.0D, $$3.getZ() - $$5.z())).normalize();
/*  83 */     $$4.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget($$5, this.speed, 0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Goat $$1, long $$2) {
/*  88 */     List<LivingEntity> $$3 = $$0.getNearbyEntities(LivingEntity.class, this.ramTargeting, (LivingEntity)$$1, $$1.getBoundingBox());
/*     */     
/*  90 */     Brain<?> $$4 = $$1.getBrain();
/*  91 */     if (!$$3.isEmpty()) {
/*  92 */       LivingEntity $$5 = $$3.get(0);
/*  93 */       $$5.hurt($$0.damageSources().noAggroMobAttack((LivingEntity)$$1), (float)$$1.getAttributeValue(Attributes.ATTACK_DAMAGE));
/*     */       
/*  95 */       int $$6 = $$1.hasEffect(MobEffects.MOVEMENT_SPEED) ? ($$1.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1) : 0;
/*  96 */       int $$7 = $$1.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? ($$1.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1) : 0;
/*  97 */       float $$8 = 0.25F * ($$6 - $$7);
/*  98 */       float $$9 = Mth.clamp($$1.getSpeed() * 1.65F, 0.2F, 3.0F) + $$8;
/*     */       
/* 100 */       float $$10 = $$5.isDamageSourceBlocked($$0.damageSources().mobAttack((LivingEntity)$$1)) ? 0.5F : 1.0F;
/* 101 */       $$5.knockback(($$10 * $$9) * this.getKnockbackForce.applyAsDouble($$1), this.ramDirection.x(), this.ramDirection.z());
/*     */       
/* 103 */       finishRam($$0, $$1);
/* 104 */       $$0.playSound(null, (Entity)$$1, this.getImpactSound.apply($$1), SoundSource.NEUTRAL, 1.0F, 1.0F);
/* 105 */     } else if (hasRammedHornBreakingBlock($$0, $$1)) {
/* 106 */       $$0.playSound(null, (Entity)$$1, this.getImpactSound.apply($$1), SoundSource.NEUTRAL, 1.0F, 1.0F);
/* 107 */       boolean $$11 = $$1.dropHorn();
/* 108 */       if ($$11) {
/* 109 */         $$0.playSound(null, (Entity)$$1, this.getHornBreakSound.apply($$1), SoundSource.NEUTRAL, 1.0F, 1.0F);
/*     */       }
/* 111 */       finishRam($$0, $$1);
/*     */     } else {
/* 113 */       Optional<WalkTarget> $$12 = $$4.getMemory(MemoryModuleType.WALK_TARGET);
/* 114 */       Optional<Vec3> $$13 = $$4.getMemory(MemoryModuleType.RAM_TARGET);
/*     */ 
/*     */       
/* 117 */       boolean $$14 = ($$12.isEmpty() || $$13.isEmpty() || ((WalkTarget)$$12.get()).getTarget().currentPosition().closerThan((Position)$$13.get(), 0.25D));
/* 118 */       if ($$14) {
/* 119 */         finishRam($$0, $$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasRammedHornBreakingBlock(ServerLevel $$0, Goat $$1) {
/* 125 */     Vec3 $$2 = $$1.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize();
/* 126 */     BlockPos $$3 = BlockPos.containing((Position)$$1.position().add($$2));
/* 127 */     return ($$0.getBlockState($$3).is(BlockTags.SNAPS_GOAT_HORN) || $$0.getBlockState($$3.above()).is(BlockTags.SNAPS_GOAT_HORN));
/*     */   }
/*     */   
/*     */   protected void finishRam(ServerLevel $$0, Goat $$1) {
/* 131 */     $$0.broadcastEntityEvent((Entity)$$1, (byte)59);
/* 132 */     $$1.getBrain().setMemory(MemoryModuleType.RAM_COOLDOWN_TICKS, Integer.valueOf(((UniformInt)this.getTimeBetweenRams.apply($$1)).sample($$0.random)));
/* 133 */     $$1.getBrain().eraseMemory(MemoryModuleType.RAM_TARGET);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\RamTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */