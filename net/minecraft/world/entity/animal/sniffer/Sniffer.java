/*     */ package net.minecraft.world.entity.animal.sniffer;
/*     */ 
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.BlockParticleOption;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.AnimationState;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ 
/*     */ public class Sniffer
/*     */   extends Animal
/*     */ {
/*     */   private static final int DIGGING_PARTICLES_DELAY_TICKS = 1700;
/*     */   private static final int DIGGING_PARTICLES_DURATION_TICKS = 6000;
/*     */   private static final int DIGGING_PARTICLES_AMOUNT = 30;
/*     */   private static final int DIGGING_DROP_SEED_OFFSET_TICKS = 120;
/*     */   private static final int SNIFFER_BABY_AGE_TICKS = 48000;
/*     */   private static final float DIGGING_BB_HEIGHT_OFFSET = 0.4F;
/*     */   
/*     */   public enum State
/*     */   {
/*  81 */     IDLING,
/*  82 */     FEELING_HAPPY,
/*  83 */     SCENTING,
/*  84 */     SNIFFING,
/*  85 */     SEARCHING,
/*  86 */     DIGGING,
/*  87 */     RISING;
/*     */   }
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
/*  99 */   private static final EntityDimensions DIGGING_DIMENSIONS = EntityDimensions.scalable(EntityType.SNIFFER.getWidth(), EntityType.SNIFFER.getHeight() - 0.4F);
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 102 */     return Mob.createMobAttributes()
/* 103 */       .add(Attributes.MOVEMENT_SPEED, 0.10000000149011612D)
/* 104 */       .add(Attributes.MAX_HEALTH, 14.0D);
/*     */   }
/*     */ 
/*     */   
/* 108 */   private static final EntityDataAccessor<State> DATA_STATE = SynchedEntityData.defineId(Sniffer.class, EntityDataSerializers.SNIFFER_STATE);
/* 109 */   private static final EntityDataAccessor<Integer> DATA_DROP_SEED_AT_TICK = SynchedEntityData.defineId(Sniffer.class, EntityDataSerializers.INT);
/*     */   
/* 111 */   public final AnimationState feelingHappyAnimationState = new AnimationState();
/* 112 */   public final AnimationState scentingAnimationState = new AnimationState();
/* 113 */   public final AnimationState sniffingAnimationState = new AnimationState();
/* 114 */   public final AnimationState diggingAnimationState = new AnimationState();
/* 115 */   public final AnimationState risingAnimationState = new AnimationState();
/*     */   
/*     */   public Sniffer(EntityType<? extends Animal> $$0, Level $$1) {
/* 118 */     super($$0, $$1);
/*     */     
/* 120 */     this.entityData.define(DATA_STATE, State.IDLING);
/* 121 */     this.entityData.define(DATA_DROP_SEED_AT_TICK, Integer.valueOf(0));
/*     */     
/* 123 */     getNavigation().setCanFloat(true);
/* 124 */     setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
/* 125 */     setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
/* 126 */     setPathfindingMalus(BlockPathTypes.DAMAGE_CAUTIOUS, -1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 131 */     return (getDimensions($$0)).height * 0.6F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPathfindingStart() {
/* 136 */     super.onPathfindingStart();
/*     */ 
/*     */ 
/*     */     
/* 140 */     if (isOnFire() || isInWater()) {
/* 141 */       setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPathfindingDone() {
/* 147 */     setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 152 */     if (this.entityData.hasItem(DATA_STATE) && getState() == State.DIGGING) {
/* 153 */       return DIGGING_DIMENSIONS.scale(getScale());
/*     */     }
/* 155 */     return super.getDimensions($$0);
/*     */   }
/*     */   
/*     */   public boolean isSearching() {
/* 159 */     return (getState() == State.SEARCHING);
/*     */   }
/*     */   
/*     */   public boolean isTempted() {
/* 163 */     return ((Boolean)this.brain.getMemory(MemoryModuleType.IS_TEMPTED).orElse(Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean canSniff() {
/* 167 */     return (!isTempted() && !isPanicking() && !isInWater() && !isInLove() && onGround() && !isPassenger() && !isLeashed());
/*     */   }
/*     */   
/*     */   public boolean canPlayDiggingSound() {
/* 171 */     return (getState() == State.DIGGING || getState() == State.SEARCHING);
/*     */   }
/*     */   
/*     */   private BlockPos getHeadBlock() {
/* 175 */     Vec3 $$0 = getHeadPosition();
/*     */ 
/*     */     
/* 178 */     return BlockPos.containing($$0.x(), getY() + 0.20000000298023224D, $$0.z());
/*     */   }
/*     */ 
/*     */   
/*     */   private Vec3 getHeadPosition() {
/* 183 */     return position().add(getForward().scale(2.25D));
/*     */   }
/*     */   
/*     */   private State getState() {
/* 187 */     return (State)this.entityData.get(DATA_STATE);
/*     */   }
/*     */   
/*     */   private Sniffer setState(State $$0) {
/* 191 */     this.entityData.set(DATA_STATE, $$0);
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 197 */     if (DATA_STATE.equals($$0)) {
/* 198 */       State $$1 = getState();
/*     */       
/* 200 */       resetAnimations();
/*     */       
/* 202 */       switch ($$1) { case SCENTING:
/* 203 */           this.scentingAnimationState.startIfStopped(this.tickCount); break;
/* 204 */         case SNIFFING: this.sniffingAnimationState.startIfStopped(this.tickCount); break;
/* 205 */         case DIGGING: this.diggingAnimationState.startIfStopped(this.tickCount); break;
/* 206 */         case RISING: this.risingAnimationState.startIfStopped(this.tickCount); break;
/* 207 */         case FEELING_HAPPY: this.feelingHappyAnimationState.startIfStopped(this.tickCount);
/*     */           break; }
/*     */       
/* 210 */       refreshDimensions();
/*     */     } 
/*     */     
/* 213 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */   
/*     */   private void resetAnimations() {
/* 217 */     this.diggingAnimationState.stop();
/* 218 */     this.sniffingAnimationState.stop();
/* 219 */     this.risingAnimationState.stop();
/* 220 */     this.feelingHappyAnimationState.stop();
/* 221 */     this.scentingAnimationState.stop();
/*     */   }
/*     */   
/*     */   public Sniffer transitionTo(State $$0) {
/* 225 */     switch ($$0) {
/*     */       case IDLING:
/* 227 */         setState(State.IDLING);
/*     */         break;
/*     */       case SCENTING:
/* 230 */         setState(State.SCENTING).onScentingStart();
/*     */         break;
/*     */       case SNIFFING:
/* 233 */         playSound(SoundEvents.SNIFFER_SNIFFING, 1.0F, 1.0F);
/* 234 */         setState(State.SNIFFING);
/*     */         break;
/*     */       case SEARCHING:
/* 237 */         setState(State.SEARCHING);
/*     */         break;
/*     */       case DIGGING:
/* 240 */         setState(State.DIGGING).onDiggingStart();
/*     */         break;
/*     */       case RISING:
/* 243 */         playSound(SoundEvents.SNIFFER_DIGGING_STOP, 1.0F, 1.0F);
/* 244 */         setState(State.RISING);
/*     */         break;
/*     */       case FEELING_HAPPY:
/* 247 */         playSound(SoundEvents.SNIFFER_HAPPY, 1.0F, 1.0F);
/* 248 */         setState(State.FEELING_HAPPY);
/*     */         break;
/*     */     } 
/* 251 */     return this;
/*     */   }
/*     */   
/*     */   private Sniffer onScentingStart() {
/* 255 */     playSound(SoundEvents.SNIFFER_SCENTING, 1.0F, isBaby() ? 1.3F : 1.0F);
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   private Sniffer onDiggingStart() {
/* 261 */     this.entityData.set(DATA_DROP_SEED_AT_TICK, Integer.valueOf(this.tickCount + 120));
/*     */ 
/*     */     
/* 264 */     level().broadcastEntityEvent((Entity)this, (byte)63);
/*     */     
/* 266 */     return this;
/*     */   }
/*     */   
/*     */   public Sniffer onDiggingComplete(boolean $$0) {
/* 270 */     if ($$0) {
/* 271 */       storeExploredPosition(getOnPos());
/*     */     }
/* 273 */     return this;
/*     */   }
/*     */   
/*     */   Optional<BlockPos> calculateDigPosition() {
/* 277 */     return 
/* 278 */       IntStream.range(0, 5)
/* 279 */       .mapToObj($$0 -> LandRandomPos.getPos((PathfinderMob)this, 10 + 2 * $$0, 3))
/* 280 */       .filter(Objects::nonNull)
/* 281 */       .map(BlockPos::containing)
/* 282 */       .filter($$0 -> level().getWorldBorder().isWithinBounds($$0))
/* 283 */       .map(BlockPos::below)
/* 284 */       .filter(this::canDig)
/* 285 */       .findFirst();
/*     */   }
/*     */   
/*     */   boolean canDig() {
/* 289 */     return (!isPanicking() && !isTempted() && !isBaby() && !isInWater() && onGround() && !isPassenger() && canDig(getHeadBlock().below()));
/*     */   }
/*     */   
/*     */   private boolean canDig(BlockPos $$0) {
/* 293 */     return (level().getBlockState($$0).is(BlockTags.SNIFFER_DIGGABLE_BLOCK) && 
/* 294 */       getExploredPositions().noneMatch($$1 -> GlobalPos.of(level().dimension(), $$0).equals($$1)) && (
/* 295 */       (Boolean)Optional.<Path>ofNullable(getNavigation().createPath($$0, 1)).map(Path::canReach).orElse(Boolean.valueOf(false))).booleanValue());
/*     */   }
/*     */   
/*     */   private void dropSeed() {
/* 299 */     if (level().isClientSide() || ((Integer)this.entityData.get(DATA_DROP_SEED_AT_TICK)).intValue() != this.tickCount) {
/*     */       return;
/*     */     }
/*     */     
/* 303 */     ServerLevel $$0 = (ServerLevel)level();
/* 304 */     LootTable $$1 = $$0.getServer().getLootData().getLootTable(BuiltInLootTables.SNIFFER_DIGGING);
/*     */ 
/*     */ 
/*     */     
/* 308 */     LootParams $$2 = (new LootParams.Builder($$0)).withParameter(LootContextParams.ORIGIN, getHeadPosition()).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.GIFT);
/* 309 */     ObjectArrayList objectArrayList = $$1.getRandomItems($$2);
/* 310 */     BlockPos $$4 = getHeadBlock();
/* 311 */     for (ItemStack $$5 : objectArrayList) {
/* 312 */       ItemEntity $$6 = new ItemEntity((Level)$$0, $$4.getX(), $$4.getY(), $$4.getZ(), $$5);
/* 313 */       $$6.setDefaultPickUpDelay();
/* 314 */       $$0.addFreshEntity((Entity)$$6);
/*     */     } 
/*     */     
/* 317 */     playSound(SoundEvents.SNIFFER_DROP_SEED, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Sniffer emitDiggingParticles(AnimationState $$0) {
/* 323 */     boolean $$1 = ($$0.getAccumulatedTime() > 1700L && $$0.getAccumulatedTime() < 6000L);
/*     */     
/* 325 */     if ($$1) {
/* 326 */       BlockPos $$2 = getHeadBlock();
/* 327 */       BlockState $$3 = level().getBlockState($$2.below());
/*     */       
/* 329 */       if ($$3.getRenderShape() != RenderShape.INVISIBLE) {
/* 330 */         for (int $$4 = 0; $$4 < 30; $$4++) {
/* 331 */           Vec3 $$5 = Vec3.atCenterOf((Vec3i)$$2).add(0.0D, -0.6499999761581421D, 0.0D);
/*     */           
/* 333 */           level().addParticle((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK, $$3), $$5.x, $$5.y, $$5.z, 0.0D, 0.0D, 0.0D);
/*     */         } 
/*     */ 
/*     */         
/* 337 */         if (this.tickCount % 10 == 0) {
/* 338 */           level().playLocalSound(getX(), getY(), getZ(), $$3.getSoundType().getHitSound(), getSoundSource(), 0.5F, 0.5F, false);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 344 */     if (this.tickCount % 10 == 0) {
/* 345 */       level().gameEvent(GameEvent.ENTITY_ACTION, getHeadBlock(), GameEvent.Context.of((Entity)this));
/*     */     }
/*     */     
/* 348 */     return this;
/*     */   }
/*     */   
/*     */   private Sniffer storeExploredPosition(BlockPos $$0) {
/* 352 */     List<GlobalPos> $$1 = (List<GlobalPos>)getExploredPositions().limit(20L).collect(Collectors.toList());
/*     */     
/* 354 */     $$1.add(0, GlobalPos.of(level().dimension(), $$0));
/* 355 */     getBrain().setMemory(MemoryModuleType.SNIFFER_EXPLORED_POSITIONS, $$1);
/* 356 */     return this;
/*     */   }
/*     */   
/*     */   private Stream<GlobalPos> getExploredPositions() {
/* 360 */     return getBrain().getMemory(MemoryModuleType.SNIFFER_EXPLORED_POSITIONS)
/* 361 */       .stream()
/* 362 */       .flatMap(Collection::stream);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jumpFromGround() {
/* 368 */     super.jumpFromGround();
/* 369 */     double $$0 = this.moveControl.getSpeedModifier();
/* 370 */     if ($$0 > 0.0D) {
/* 371 */       double $$1 = getDeltaMovement().horizontalDistanceSqr();
/* 372 */       if ($$1 < 0.01D) {
/* 373 */         moveRelative(0.1F, new Vec3(0.0D, 0.0D, 1.0D));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnChildFromBreeding(ServerLevel $$0, Animal $$1) {
/* 380 */     ItemStack $$2 = new ItemStack((ItemLike)Items.SNIFFER_EGG);
/* 381 */     ItemEntity $$3 = new ItemEntity((Level)$$0, position().x(), position().y(), position().z(), $$2);
/* 382 */     $$3.setDefaultPickUpDelay();
/*     */     
/* 384 */     finalizeSpawnChildFromBreeding($$0, $$1, null);
/*     */     
/* 386 */     playSound(SoundEvents.SNIFFER_EGG_PLOP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.5F);
/* 387 */     $$0.addFreshEntity((Entity)$$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void die(DamageSource $$0) {
/* 392 */     transitionTo(State.IDLING);
/* 393 */     super.die($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 398 */     switch (getState()) { case DIGGING:
/* 399 */         emitDiggingParticles(this.diggingAnimationState).dropSeed(); break;
/* 400 */       case SEARCHING: playSearchingSound(); break; }
/*     */     
/* 402 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 407 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 408 */     boolean $$3 = isFood($$2);
/*     */     
/* 410 */     InteractionResult $$4 = super.mobInteract($$0, $$1);
/* 411 */     if ($$4.consumesAction() && $$3) {
/* 412 */       level().playSound(null, (Entity)this, getEatingSound($$2), SoundSource.NEUTRAL, 1.0F, Mth.randomBetween((level()).random, 0.8F, 1.2F));
/*     */     }
/* 414 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 419 */     return new Vector3f(0.0F, $$1.height + 0.34375F * $$2, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getNameTagOffsetY() {
/* 424 */     return super.getNameTagOffsetY() + 0.3F;
/*     */   }
/*     */   
/*     */   private void playSearchingSound() {
/* 428 */     if (level().isClientSide() && this.tickCount % 20 == 0) {
/* 429 */       level().playLocalSound(getX(), getY(), getZ(), SoundEvents.SNIFFER_SEARCHING, getSoundSource(), 1.0F, 1.0F, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 435 */     playSound(SoundEvents.SNIFFER_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getEatingSound(ItemStack $$0) {
/* 440 */     return SoundEvents.SNIFFER_EAT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 445 */     return Set.<State>of(State.DIGGING, State.SEARCHING).contains(getState()) ? null : SoundEvents.SNIFFER_IDLE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 450 */     return SoundEvents.SNIFFER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 455 */     return SoundEvents.SNIFFER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHeadYRot() {
/* 460 */     return 50;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBaby(boolean $$0) {
/* 465 */     setAge($$0 ? -48000 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 470 */     return (AgeableMob)EntityType.SNIFFER.create((Level)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMate(Animal $$0) {
/* 475 */     if ($$0 instanceof Sniffer) { Sniffer $$1 = (Sniffer)$$0;
/* 476 */       Set<State> $$2 = Set.of(State.IDLING, State.SCENTING, State.FEELING_HAPPY);
/* 477 */       return ($$2.contains(getState()) && $$2.contains($$1.getState()) && super.canMate($$0)); }
/*     */ 
/*     */     
/* 480 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AABB getBoundingBoxForCulling() {
/* 486 */     return super.getBoundingBoxForCulling().inflate(0.6000000238418579D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 491 */     return $$0.is(ItemTags.SNIFFER_FOOD);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/* 496 */     return SnifferAi.makeBrain(brainProvider().makeBrain($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Brain<Sniffer> getBrain() {
/* 501 */     return super.getBrain();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Brain.Provider<Sniffer> brainProvider() {
/* 506 */     return Brain.provider(SnifferAi.MEMORY_TYPES, SnifferAi.SENSOR_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 511 */     level().getProfiler().push("snifferBrain");
/* 512 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/*     */     
/* 514 */     level().getProfiler().popPush("snifferActivityUpdate");
/*     */     
/* 516 */     SnifferAi.updateActivity(this);
/* 517 */     level().getProfiler().pop();
/*     */     
/* 519 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void sendDebugPackets() {
/* 524 */     super.sendDebugPackets();
/* 525 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\sniffer\Sniffer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */