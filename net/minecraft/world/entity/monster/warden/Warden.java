/*     */ package net.minecraft.world.entity.monster.warden;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.BlockParticleOption;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.GameEventTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffectUtil;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.AnimationState;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.behavior.warden.SonicBoom;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.DynamicGameEventListener;
/*     */ import net.minecraft.world.level.gameevent.EntityPositionSource;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.NodeEvaluator;
/*     */ import net.minecraft.world.level.pathfinder.PathFinder;
/*     */ import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.joml.Vector3f;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Warden extends Monster implements VibrationSystem {
/*  80 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int VIBRATION_COOLDOWN_TICKS = 40;
/*     */   
/*     */   private static final int TIME_TO_USE_MELEE_UNTIL_SONIC_BOOM = 200;
/*     */   
/*     */   private static final int MAX_HEALTH = 500;
/*     */   private static final float MOVEMENT_SPEED_WHEN_FIGHTING = 0.3F;
/*     */   private static final float KNOCKBACK_RESISTANCE = 1.0F;
/*     */   private static final float ATTACK_KNOCKBACK = 1.5F;
/*     */   private static final int ATTACK_DAMAGE = 30;
/*  91 */   private static final EntityDataAccessor<Integer> CLIENT_ANGER_LEVEL = SynchedEntityData.defineId(Warden.class, EntityDataSerializers.INT);
/*     */   
/*     */   private static final int DARKNESS_DISPLAY_LIMIT = 200;
/*     */   
/*     */   private static final int DARKNESS_DURATION = 260;
/*     */   
/*     */   private static final int DARKNESS_RADIUS = 20;
/*     */   
/*     */   private static final int DARKNESS_INTERVAL = 120;
/*     */   
/*     */   private static final int ANGERMANAGEMENT_TICK_DELAY = 20;
/*     */   
/*     */   private static final int DEFAULT_ANGER = 35;
/*     */   
/*     */   private static final int PROJECTILE_ANGER = 10;
/*     */   
/*     */   private static final int ON_HURT_ANGER_BOOST = 20;
/*     */   
/*     */   private static final int RECENT_PROJECTILE_TICK_THRESHOLD = 100;
/*     */   private static final int TOUCH_COOLDOWN_TICKS = 20;
/*     */   private static final int DIGGING_PARTICLES_AMOUNT = 30;
/*     */   private static final float DIGGING_PARTICLES_DURATION = 4.5F;
/*     */   private static final float DIGGING_PARTICLES_OFFSET = 0.7F;
/*     */   private static final int PROJECTILE_ANGER_DISTANCE = 30;
/*     */   private int tendrilAnimation;
/*     */   private int tendrilAnimationO;
/*     */   private int heartAnimation;
/*     */   private int heartAnimationO;
/* 119 */   public AnimationState roarAnimationState = new AnimationState();
/* 120 */   public AnimationState sniffAnimationState = new AnimationState();
/* 121 */   public AnimationState emergeAnimationState = new AnimationState();
/* 122 */   public AnimationState diggingAnimationState = new AnimationState();
/* 123 */   public AnimationState attackAnimationState = new AnimationState();
/* 124 */   public AnimationState sonicBoomAnimationState = new AnimationState();
/*     */   
/*     */   private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
/*     */   
/*     */   private final VibrationSystem.User vibrationUser;
/*     */   private VibrationSystem.Data vibrationData;
/* 130 */   AngerManagement angerManagement = new AngerManagement(this::canTargetEntity, Collections.emptyList());
/*     */   
/*     */   public Warden(EntityType<? extends Monster> $$0, Level $$1) {
/* 133 */     super($$0, $$1);
/* 134 */     this.vibrationUser = new VibrationUser();
/* 135 */     this.vibrationData = new VibrationSystem.Data();
/* 136 */     this.dynamicGameEventListener = new DynamicGameEventListener((GameEventListener)new VibrationSystem.Listener(this));
/*     */     
/* 138 */     this.xpReward = 5;
/* 139 */     getNavigation().setCanFloat(true);
/*     */     
/* 141 */     setPathfindingMalus(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
/* 142 */     setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 8.0F);
/* 143 */     setPathfindingMalus(BlockPathTypes.POWDER_SNOW, 8.0F);
/* 144 */     setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
/* 145 */     setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);
/* 146 */     setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 151 */     return (Packet<ClientGamePacketListener>)new ClientboundAddEntityPacket((Entity)this, hasPose(Pose.EMERGING) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 156 */     super.recreateFromPacket($$0);
/* 157 */     if ($$0.getData() == 1) {
/* 158 */       setPose(Pose.EMERGING);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkSpawnObstruction(LevelReader $$0) {
/* 164 */     return (super.checkSpawnObstruction($$0) && $$0.noCollision((Entity)this, getType().getDimensions().makeBoundingBox(position())));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 169 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInvulnerableTo(DamageSource $$0) {
/* 174 */     if (isDiggingOrEmerging() && !$$0.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
/* 175 */       return true;
/*     */     }
/* 177 */     return super.isInvulnerableTo($$0);
/*     */   }
/*     */   
/*     */   boolean isDiggingOrEmerging() {
/* 181 */     return (hasPose(Pose.DIGGING) || hasPose(Pose.EMERGING));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRide(Entity $$0) {
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDisableShield() {
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected float nextStep() {
/* 197 */     return this.moveDist + 0.55F;
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 201 */     return Monster.createMonsterAttributes()
/* 202 */       .add(Attributes.MAX_HEALTH, 500.0D)
/* 203 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D)
/* 204 */       .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
/* 205 */       .add(Attributes.ATTACK_KNOCKBACK, 1.5D)
/* 206 */       .add(Attributes.ATTACK_DAMAGE, 30.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dampensVibrations() {
/* 211 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 217 */     return 4.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 223 */     if (hasPose(Pose.ROARING) || isDiggingOrEmerging()) {
/* 224 */       return null;
/*     */     }
/*     */     
/* 227 */     return getAngerLevel().getAmbientSound();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 232 */     return SoundEvents.WARDEN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 237 */     return SoundEvents.WARDEN_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 242 */     playSound(SoundEvents.WARDEN_STEP, 10.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 247 */     level().broadcastEntityEvent((Entity)this, (byte)4);
/* 248 */     playSound(SoundEvents.WARDEN_ATTACK_IMPACT, 10.0F, getVoicePitch());
/*     */     
/* 250 */     SonicBoom.setCooldown((LivingEntity)this, 40);
/*     */     
/* 252 */     return super.doHurtTarget($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 257 */     super.defineSynchedData();
/*     */     
/* 259 */     this.entityData.define(CLIENT_ANGER_LEVEL, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public int getClientAngerLevel() {
/* 263 */     return ((Integer)this.entityData.get(CLIENT_ANGER_LEVEL)).intValue();
/*     */   }
/*     */   
/*     */   private void syncClientAngerLevel() {
/* 267 */     this.entityData.set(CLIENT_ANGER_LEVEL, Integer.valueOf(getActiveAnger()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 272 */     Level level = level(); if (level instanceof ServerLevel) { ServerLevel $$0 = (ServerLevel)level;
/* 273 */       VibrationSystem.Ticker.tick((Level)$$0, this.vibrationData, this.vibrationUser);
/*     */       
/* 275 */       if (isPersistenceRequired() || requiresCustomPersistence()) {
/* 276 */         WardenAi.setDigCooldown((LivingEntity)this);
/*     */       } }
/*     */ 
/*     */     
/* 280 */     super.tick();
/*     */ 
/*     */     
/* 283 */     if (level().isClientSide()) {
/* 284 */       if (this.tickCount % getHeartBeatDelay() == 0) {
/* 285 */         this.heartAnimation = 10;
/* 286 */         if (!isSilent()) {
/* 287 */           level().playLocalSound(getX(), getY(), getZ(), SoundEvents.WARDEN_HEARTBEAT, getSoundSource(), 5.0F, getVoicePitch(), false);
/*     */         }
/*     */       } 
/*     */       
/* 291 */       this.tendrilAnimationO = this.tendrilAnimation;
/* 292 */       if (this.tendrilAnimation > 0) {
/* 293 */         this.tendrilAnimation--;
/*     */       }
/*     */       
/* 296 */       this.heartAnimationO = this.heartAnimation;
/* 297 */       if (this.heartAnimation > 0) {
/* 298 */         this.heartAnimation--;
/*     */       }
/*     */       
/* 301 */       switch (getPose()) { case EMERGING:
/* 302 */           clientDiggingParticles(this.emergeAnimationState); break;
/* 303 */         case DIGGING: clientDiggingParticles(this.diggingAnimationState);
/*     */           break; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void customServerAiStep() {
/* 310 */     ServerLevel $$0 = (ServerLevel)level();
/*     */     
/* 312 */     $$0.getProfiler().push("wardenBrain");
/* 313 */     getBrain().tick($$0, (LivingEntity)this);
/* 314 */     level().getProfiler().pop();
/*     */     
/* 316 */     super.customServerAiStep();
/*     */     
/* 318 */     if ((this.tickCount + getId()) % 120 == 0) {
/* 319 */       applyDarknessAround($$0, position(), (Entity)this, 20);
/*     */     }
/*     */     
/* 322 */     if (this.tickCount % 20 == 0) {
/* 323 */       this.angerManagement.tick($$0, this::canTargetEntity);
/* 324 */       syncClientAngerLevel();
/*     */     } 
/*     */     
/* 327 */     WardenAi.updateActivity(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 332 */     if ($$0 == 4) {
/* 333 */       this.roarAnimationState.stop();
/* 334 */       this.attackAnimationState.start(this.tickCount);
/* 335 */     } else if ($$0 == 61) {
/* 336 */       this.tendrilAnimation = 10;
/* 337 */     } else if ($$0 == 62) {
/* 338 */       this.sonicBoomAnimationState.start(this.tickCount);
/*     */     } else {
/* 340 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getHeartBeatDelay() {
/* 345 */     float $$0 = getClientAngerLevel() / AngerLevel.ANGRY.getMinimumAnger();
/* 346 */     return 40 - Mth.floor(Mth.clamp($$0, 0.0F, 1.0F) * 30.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTendrilAnimation(float $$0) {
/* 351 */     return Mth.lerp($$0, this.tendrilAnimationO, this.tendrilAnimation) / 10.0F;
/*     */   }
/*     */   
/*     */   public float getHeartAnimation(float $$0) {
/* 355 */     return Mth.lerp($$0, this.heartAnimationO, this.heartAnimation) / 10.0F;
/*     */   }
/*     */   
/*     */   private void clientDiggingParticles(AnimationState $$0) {
/* 359 */     if ((float)$$0.getAccumulatedTime() < 4500.0F) {
/* 360 */       RandomSource $$1 = getRandom();
/* 361 */       BlockState $$2 = getBlockStateOn();
/*     */       
/* 363 */       if ($$2.getRenderShape() != RenderShape.INVISIBLE) {
/* 364 */         for (int $$3 = 0; $$3 < 30; $$3++) {
/* 365 */           double $$4 = getX() + Mth.randomBetween($$1, -0.7F, 0.7F);
/* 366 */           double $$5 = getY();
/* 367 */           double $$6 = getZ() + Mth.randomBetween($$1, -0.7F, 0.7F);
/*     */           
/* 369 */           level().addParticle((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK, $$2), $$4, $$5, $$6, 0.0D, 0.0D, 0.0D);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 377 */     if (DATA_POSE.equals($$0)) {
/* 378 */       switch (getPose()) { case ROARING:
/* 379 */           this.roarAnimationState.start(this.tickCount); break;
/* 380 */         case SNIFFING: this.sniffAnimationState.start(this.tickCount); break;
/* 381 */         case EMERGING: this.emergeAnimationState.start(this.tickCount); break;
/* 382 */         case DIGGING: this.diggingAnimationState.start(this.tickCount);
/*     */           break; }
/*     */     
/*     */     }
/* 386 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean ignoreExplosion(Explosion $$0) {
/* 391 */     return isDiggingOrEmerging();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 396 */     return WardenAi.makeBrain(this, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Brain<Warden> getBrain() {
/* 402 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 407 */     super.sendDebugPackets();
/* 408 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> $$0) {
/* 413 */     Level level = level(); if (level instanceof ServerLevel) { ServerLevel $$1 = (ServerLevel)level;
/* 414 */       $$0.accept(this.dynamicGameEventListener, $$1); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Contract("null->false")
/*     */   public boolean canTargetEntity(@Nullable Entity $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: instanceof net/minecraft/world/entity/LivingEntity
/*     */     //   4: ifeq -> 98
/*     */     //   7: aload_1
/*     */     //   8: checkcast net/minecraft/world/entity/LivingEntity
/*     */     //   11: astore_2
/*     */     //   12: aload_0
/*     */     //   13: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*     */     //   16: aload_1
/*     */     //   17: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*     */     //   20: if_acmpne -> 98
/*     */     //   23: getstatic net/minecraft/world/entity/EntitySelector.NO_CREATIVE_OR_SPECTATOR : Ljava/util/function/Predicate;
/*     */     //   26: aload_1
/*     */     //   27: invokeinterface test : (Ljava/lang/Object;)Z
/*     */     //   32: ifeq -> 98
/*     */     //   35: aload_0
/*     */     //   36: aload_1
/*     */     //   37: invokevirtual isAlliedTo : (Lnet/minecraft/world/entity/Entity;)Z
/*     */     //   40: ifne -> 98
/*     */     //   43: aload_2
/*     */     //   44: invokevirtual getType : ()Lnet/minecraft/world/entity/EntityType;
/*     */     //   47: getstatic net/minecraft/world/entity/EntityType.ARMOR_STAND : Lnet/minecraft/world/entity/EntityType;
/*     */     //   50: if_acmpeq -> 98
/*     */     //   53: aload_2
/*     */     //   54: invokevirtual getType : ()Lnet/minecraft/world/entity/EntityType;
/*     */     //   57: getstatic net/minecraft/world/entity/EntityType.WARDEN : Lnet/minecraft/world/entity/EntityType;
/*     */     //   60: if_acmpeq -> 98
/*     */     //   63: aload_2
/*     */     //   64: invokevirtual isInvulnerable : ()Z
/*     */     //   67: ifne -> 98
/*     */     //   70: aload_2
/*     */     //   71: invokevirtual isDeadOrDying : ()Z
/*     */     //   74: ifne -> 98
/*     */     //   77: aload_0
/*     */     //   78: invokevirtual level : ()Lnet/minecraft/world/level/Level;
/*     */     //   81: invokevirtual getWorldBorder : ()Lnet/minecraft/world/level/border/WorldBorder;
/*     */     //   84: aload_2
/*     */     //   85: invokevirtual getBoundingBox : ()Lnet/minecraft/world/phys/AABB;
/*     */     //   88: invokevirtual isWithinBounds : (Lnet/minecraft/world/phys/AABB;)Z
/*     */     //   91: ifeq -> 98
/*     */     //   94: iconst_1
/*     */     //   95: goto -> 99
/*     */     //   98: iconst_0
/*     */     //   99: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #428	-> 0
/*     */     //   #420	-> 7
/*     */     //   #421	-> 13
/*     */     //   #422	-> 27
/*     */     //   #423	-> 37
/*     */     //   #424	-> 44
/*     */     //   #425	-> 54
/*     */     //   #426	-> 64
/*     */     //   #427	-> 71
/*     */     //   #428	-> 78
/*     */     //   #420	-> 99
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	100	0	this	Lnet/minecraft/world/entity/monster/warden/Warden;
/*     */     //   0	100	1	$$0	Lnet/minecraft/world/entity/Entity;
/*     */     //   12	86	2	$$1	Lnet/minecraft/world/entity/LivingEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void applyDarknessAround(ServerLevel $$0, Vec3 $$1, @Nullable Entity $$2, int $$3) {
/* 433 */     MobEffectInstance $$4 = new MobEffectInstance(MobEffects.DARKNESS, 260, 0, false, false);
/* 434 */     MobEffectUtil.addEffectToPlayersAround($$0, $$2, $$1, $$3, $$4, 200);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 439 */     super.addAdditionalSaveData($$0);
/*     */ 
/*     */ 
/*     */     
/* 443 */     Objects.requireNonNull(LOGGER); AngerManagement.codec(this::canTargetEntity).encodeStart((DynamicOps)NbtOps.INSTANCE, this.angerManagement).resultOrPartial(LOGGER::error)
/* 444 */       .ifPresent($$1 -> $$0.put("anger", $$1));
/*     */ 
/*     */ 
/*     */     
/* 448 */     Objects.requireNonNull(LOGGER); VibrationSystem.Data.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error)
/* 449 */       .ifPresent($$1 -> $$0.put("listener", $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 454 */     super.readAdditionalSaveData($$0);
/*     */     
/* 456 */     if ($$0.contains("anger")) {
/*     */ 
/*     */       
/* 459 */       Objects.requireNonNull(LOGGER); AngerManagement.codec(this::canTargetEntity).parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.get("anger"))).resultOrPartial(LOGGER::error)
/* 460 */         .ifPresent($$0 -> this.angerManagement = $$0);
/* 461 */       syncClientAngerLevel();
/*     */     } 
/*     */     
/* 464 */     if ($$0.contains("listener", 10)) {
/*     */ 
/*     */       
/* 467 */       Objects.requireNonNull(LOGGER); VibrationSystem.Data.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.getCompound("listener"))).resultOrPartial(LOGGER::error)
/* 468 */         .ifPresent($$0 -> this.vibrationData = $$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void playListeningSound() {
/* 473 */     if (!hasPose(Pose.ROARING)) {
/* 474 */       playSound(getAngerLevel().getListeningSound(), 10.0F, getVoicePitch());
/*     */     }
/*     */   }
/*     */   
/*     */   public AngerLevel getAngerLevel() {
/* 479 */     return AngerLevel.byAnger(getActiveAnger());
/*     */   }
/*     */   
/*     */   private int getActiveAnger() {
/* 483 */     return this.angerManagement.getActiveAnger((Entity)getTarget());
/*     */   }
/*     */   
/*     */   public void clearAnger(Entity $$0) {
/* 487 */     this.angerManagement.clearAnger($$0);
/*     */   }
/*     */   
/*     */   public void increaseAngerAt(@Nullable Entity $$0) {
/* 491 */     increaseAngerAt($$0, 35, true);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void increaseAngerAt(@Nullable Entity $$0, int $$1, boolean $$2) {
/* 496 */     if (!isNoAi() && canTargetEntity($$0)) {
/* 497 */       WardenAi.setDigCooldown((LivingEntity)this);
/*     */       
/* 499 */       boolean $$3 = !(getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null) instanceof net.minecraft.world.entity.player.Player);
/*     */       
/* 501 */       int $$4 = this.angerManagement.increaseAnger($$0, $$1);
/*     */       
/* 503 */       if ($$0 instanceof net.minecraft.world.entity.player.Player && $$3 && AngerLevel.byAnger($$4).isAngry())
/*     */       {
/* 505 */         getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
/*     */       }
/*     */       
/* 508 */       if ($$2) {
/* 509 */         playListeningSound();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Optional<LivingEntity> getEntityAngryAt() {
/* 515 */     if (getAngerLevel().isAngry()) {
/* 516 */       return this.angerManagement.getActiveEntity();
/*     */     }
/* 518 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getTarget() {
/* 524 */     return getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 529 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 535 */     getBrain().setMemoryWithExpiry(MemoryModuleType.DIG_COOLDOWN, Unit.INSTANCE, 1200L);
/*     */     
/* 537 */     if ($$2 == MobSpawnType.TRIGGERED) {
/* 538 */       setPose(Pose.EMERGING);
/* 539 */       getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, WardenAi.EMERGE_DURATION);
/* 540 */       playSound(SoundEvents.WARDEN_AGITATED, 5.0F, 1.0F);
/*     */     } 
/*     */     
/* 543 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 548 */     boolean $$2 = super.hurt($$0, $$1);
/* 549 */     if (!(level()).isClientSide && !isNoAi() && !isDiggingOrEmerging()) {
/* 550 */       Entity $$3 = $$0.getEntity();
/*     */       
/* 552 */       increaseAngerAt($$3, AngerLevel.ANGRY.getMinimumAnger() + 20, false);
/*     */ 
/*     */       
/* 555 */       if (this.brain.getMemory(MemoryModuleType.ATTACK_TARGET).isEmpty() && $$3 instanceof LivingEntity) {
/* 556 */         LivingEntity $$4 = (LivingEntity)$$3;
/* 557 */         if (!$$0.isIndirect() || closerThan((Entity)$$4, 5.0D))
/*     */         {
/* 559 */           setAttackTarget($$4); } 
/*     */       } 
/*     */     } 
/* 562 */     return $$2;
/*     */   }
/*     */   
/*     */   public void setAttackTarget(LivingEntity $$0) {
/* 566 */     getBrain().eraseMemory(MemoryModuleType.ROAR_TARGET);
/* 567 */     getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, $$0);
/* 568 */     getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
/* 569 */     SonicBoom.setCooldown((LivingEntity)this, 200);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 574 */     EntityDimensions $$1 = super.getDimensions($$0);
/*     */     
/* 576 */     if (isDiggingOrEmerging()) {
/* 577 */       return EntityDimensions.fixed($$1.width, 1.0F);
/*     */     }
/*     */     
/* 580 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPushable() {
/* 585 */     return (!isDiggingOrEmerging() && super.isPushable());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doPush(Entity $$0) {
/* 590 */     if (!isNoAi() && !getBrain().hasMemoryValue(MemoryModuleType.TOUCH_COOLDOWN)) {
/* 591 */       getBrain().setMemoryWithExpiry(MemoryModuleType.TOUCH_COOLDOWN, Unit.INSTANCE, 20L);
/* 592 */       increaseAngerAt($$0);
/* 593 */       WardenAi.setDisturbanceLocation(this, $$0.blockPosition());
/*     */     } 
/*     */     
/* 596 */     super.doPush($$0);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public AngerManagement getAngerManagement() {
/* 601 */     return this.angerManagement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PathNavigation createNavigation(Level $$0) {
/* 609 */     return (PathNavigation)new GroundPathNavigation((Mob)this, $$0)
/*     */       {
/*     */         protected PathFinder createPathFinder(int $$0) {
/* 612 */           this.nodeEvaluator = (NodeEvaluator)new WalkNodeEvaluator();
/* 613 */           this.nodeEvaluator.setCanPassDoors(true);
/* 614 */           return new PathFinder(this.nodeEvaluator, $$0)
/*     */             {
/*     */ 
/*     */               
/*     */               protected float distance(Node $$0, Node $$1)
/*     */               {
/* 620 */                 return $$0.distanceToXZ($$1);
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 629 */     return new Vector3f(0.0F, $$1.height + 0.25F * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public VibrationSystem.Data getVibrationData() {
/* 634 */     return this.vibrationData;
/*     */   }
/*     */ 
/*     */   
/*     */   public VibrationSystem.User getVibrationUser() {
/* 639 */     return this.vibrationUser;
/*     */   }
/*     */   
/*     */   private class VibrationUser implements VibrationSystem.User {
/*     */     private static final int GAME_EVENT_LISTENER_RANGE = 16;
/* 644 */     private final PositionSource positionSource = (PositionSource)new EntityPositionSource((Entity)Warden.this, Warden.this.getEyeHeight());
/*     */ 
/*     */     
/*     */     public int getListenerRadius() {
/* 648 */       return 16;
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionSource getPositionSource() {
/* 653 */       return this.positionSource;
/*     */     }
/*     */ 
/*     */     
/*     */     public TagKey<GameEvent> getListenableEvents() {
/* 658 */       return GameEventTags.WARDEN_CAN_LISTEN;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canTriggerAvoidVibration() {
/* 663 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, GameEvent.Context $$3) {
/* 668 */       if (Warden.this.isNoAi() || Warden.this
/* 669 */         .isDeadOrDying() || Warden.this
/* 670 */         .getBrain().hasMemoryValue(MemoryModuleType.VIBRATION_COOLDOWN) || Warden.this
/* 671 */         .isDiggingOrEmerging() || 
/* 672 */         !$$0.getWorldBorder().isWithinBounds($$1))
/*     */       {
/* 674 */         return false;
/*     */       }
/*     */       
/* 677 */       Entity entity = $$3.sourceEntity(); if (entity instanceof LivingEntity) { LivingEntity $$4 = (LivingEntity)entity; if (Warden.this.canTargetEntity((Entity)$$4)); return false; }
/*     */     
/*     */     }
/*     */     
/*     */     public void onReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable Entity $$3, @Nullable Entity $$4, float $$5) {
/* 682 */       if (Warden.this.isDeadOrDying()) {
/*     */         return;
/*     */       }
/*     */       
/* 686 */       Warden.this.brain.setMemoryWithExpiry(MemoryModuleType.VIBRATION_COOLDOWN, Unit.INSTANCE, 40L);
/*     */       
/* 688 */       $$0.broadcastEntityEvent((Entity)Warden.this, (byte)61);
/* 689 */       Warden.this.playSound(SoundEvents.WARDEN_TENDRIL_CLICKS, 5.0F, Warden.this.getVoicePitch());
/*     */       
/* 691 */       BlockPos $$6 = $$1;
/*     */ 
/*     */       
/* 694 */       if ($$4 != null) {
/* 695 */         if (Warden.this.closerThan($$4, 30.0D)) {
/* 696 */           if (Warden.this.getBrain().hasMemoryValue(MemoryModuleType.RECENT_PROJECTILE)) {
/* 697 */             if (Warden.this.canTargetEntity($$4)) {
/* 698 */               $$6 = $$4.blockPosition();
/*     */             }
/* 700 */             Warden.this.increaseAngerAt($$4);
/*     */           } else {
/* 702 */             Warden.this.increaseAngerAt($$4, 10, true);
/*     */           } 
/*     */         }
/* 705 */         Warden.this.getBrain().setMemoryWithExpiry(MemoryModuleType.RECENT_PROJECTILE, Unit.INSTANCE, 100L);
/*     */       } else {
/* 707 */         Warden.this.increaseAngerAt($$3);
/*     */       } 
/*     */       
/* 710 */       if (!Warden.this.getAngerLevel().isAngry()) {
/*     */         
/* 712 */         Optional<LivingEntity> $$7 = Warden.this.angerManagement.getActiveEntity();
/* 713 */         if ($$4 != null || $$7.isEmpty() || $$7.get() == $$3)
/* 714 */           WardenAi.setDisturbanceLocation(Warden.this, $$6); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\warden\Warden.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */