/*     */ package net.minecraft.world.entity.monster.piglin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PiglinBrute
/*     */   extends AbstractPiglin
/*     */ {
/*     */   private static final int MAX_HEALTH = 50;
/*     */   private static final float MOVEMENT_SPEED_WHEN_FIGHTING = 0.35F;
/*     */   private static final int ATTACK_DAMAGE = 7;
/*  42 */   protected static final ImmutableList<SensorType<? extends Sensor<? super PiglinBrute>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY, SensorType.PIGLIN_BRUTE_SPECIFIC_SENSOR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, MemoryModuleType.NEARBY_ADULT_PIGLINS, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, (Object[])new MemoryModuleType[] { MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.ANGRY_AT, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.HOME });
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
/*     */   public PiglinBrute(EntityType<? extends PiglinBrute> $$0, Level $$1) {
/*  73 */     super((EntityType)$$0, $$1);
/*  74 */     this.xpReward = 20;
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  78 */     return Monster.createMonsterAttributes()
/*  79 */       .add(Attributes.MAX_HEALTH, 50.0D)
/*  80 */       .add(Attributes.MOVEMENT_SPEED, 0.3499999940395355D)
/*  81 */       .add(Attributes.ATTACK_DAMAGE, 7.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*  87 */     PiglinBruteAi.initMemories(this);
/*  88 */     populateDefaultEquipmentSlots($$0.getRandom(), $$1);
/*  89 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/*  94 */     setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.GOLDEN_AXE));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<PiglinBrute> brainProvider() {
/*  99 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 104 */     return PiglinBruteAi.makeBrain(this, brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<PiglinBrute> getBrain() {
/* 110 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHunt() {
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean wantsToPickUp(ItemStack $$0) {
/* 120 */     if ($$0.is(Items.GOLDEN_AXE)) {
/* 121 */       return super.wantsToPickUp($$0);
/*     */     }
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 128 */     level().getProfiler().push("piglinBruteBrain");
/* 129 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/* 130 */     level().getProfiler().pop();
/*     */     
/* 132 */     PiglinBruteAi.updateActivity(this);
/* 133 */     PiglinBruteAi.maybePlayActivitySound(this);
/*     */     
/* 135 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public PiglinArmPose getArmPose() {
/* 140 */     if (isAggressive() && isHoldingMeleeWeapon()) {
/* 141 */       return PiglinArmPose.ATTACKING_WITH_MELEE_WEAPON;
/*     */     }
/* 143 */     return PiglinArmPose.DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 149 */     boolean $$2 = super.hurt($$0, $$1);
/* 150 */     if ((level()).isClientSide) {
/* 151 */       return false;
/*     */     }
/* 153 */     if ($$2 && $$0.getEntity() instanceof LivingEntity) {
/* 154 */       PiglinBruteAi.wasHurtBy(this, (LivingEntity)$$0.getEntity());
/*     */     }
/* 156 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 161 */     return SoundEvents.PIGLIN_BRUTE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 166 */     return SoundEvents.PIGLIN_BRUTE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 171 */     return SoundEvents.PIGLIN_BRUTE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 176 */     playSound(SoundEvents.PIGLIN_BRUTE_STEP, 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void playAngrySound() {
/* 180 */     playSound(SoundEvents.PIGLIN_BRUTE_ANGRY, 1.0F, getVoicePitch());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playConvertedSound() {
/* 185 */     playSound(SoundEvents.PIGLIN_BRUTE_CONVERTED_TO_ZOMBIFIED, 1.0F, getVoicePitch());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\piglin\PiglinBrute.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */