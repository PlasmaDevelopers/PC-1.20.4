/*      */ package net.minecraft.world.entity;
/*      */ 
/*      */ import com.google.common.annotations.VisibleForTesting;
/*      */ import com.google.common.collect.Maps;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.UUID;
/*      */ import java.util.function.Predicate;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.NonNullList;
/*      */ import net.minecraft.core.Vec3i;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.FloatTag;
/*      */ import net.minecraft.nbt.ListTag;
/*      */ import net.minecraft.nbt.NbtUtils;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
/*      */ import net.minecraft.network.protocol.game.DebugPackets;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.Difficulty;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.ai.control.BodyRotationControl;
/*      */ import net.minecraft.world.entity.ai.control.JumpControl;
/*      */ import net.minecraft.world.entity.ai.control.LookControl;
/*      */ import net.minecraft.world.entity.ai.control.MoveControl;
/*      */ import net.minecraft.world.entity.ai.goal.Goal;
/*      */ import net.minecraft.world.entity.ai.goal.GoalSelector;
/*      */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*      */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*      */ import net.minecraft.world.entity.ai.sensing.Sensing;
/*      */ import net.minecraft.world.entity.decoration.HangingEntity;
/*      */ import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.item.ArmorItem;
/*      */ import net.minecraft.world.item.DiggerItem;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.ProjectileWeaponItem;
/*      */ import net.minecraft.world.item.SpawnEggItem;
/*      */ import net.minecraft.world.item.SwordItem;
/*      */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.GameRules;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelAccessor;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.ServerLevelAccessor;
/*      */ import net.minecraft.world.level.gameevent.GameEvent;
/*      */ import net.minecraft.world.level.material.Fluid;
/*      */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Mob
/*      */   extends LivingEntity
/*      */   implements Targeting
/*      */ {
/*   82 */   private static final EntityDataAccessor<Byte> DATA_MOB_FLAGS_ID = SynchedEntityData.defineId(Mob.class, EntityDataSerializers.BYTE);
/*      */   private static final int MOB_FLAG_NO_AI = 1;
/*      */   private static final int MOB_FLAG_LEFTHANDED = 2;
/*      */   private static final int MOB_FLAG_AGGRESSIVE = 4;
/*      */   protected static final int PICKUP_REACH = 1;
/*   87 */   private static final Vec3i ITEM_PICKUP_REACH = new Vec3i(1, 0, 1);
/*      */   
/*      */   public static final float MAX_WEARING_ARMOR_CHANCE = 0.15F;
/*      */   
/*      */   public static final float MAX_PICKUP_LOOT_CHANCE = 0.55F;
/*      */   
/*      */   public static final float MAX_ENCHANTED_ARMOR_CHANCE = 0.5F;
/*      */   
/*      */   public static final float MAX_ENCHANTED_WEAPON_CHANCE = 0.25F;
/*      */   
/*      */   public static final String LEASH_TAG = "Leash";
/*      */   public static final float DEFAULT_EQUIPMENT_DROP_CHANCE = 0.085F;
/*      */   public static final int PRESERVE_ITEM_DROP_CHANCE = 2;
/*      */   public static final int UPDATE_GOAL_SELECTOR_EVERY_N_TICKS = 2;
/*  101 */   private static final double DEFAULT_ATTACK_REACH = Math.sqrt(2.0399999618530273D) - 0.6000000238418579D;
/*      */   
/*      */   public int ambientSoundTime;
/*      */   protected int xpReward;
/*      */   protected LookControl lookControl;
/*      */   protected MoveControl moveControl;
/*      */   protected JumpControl jumpControl;
/*      */   private final BodyRotationControl bodyRotationControl;
/*      */   protected PathNavigation navigation;
/*      */   protected final GoalSelector goalSelector;
/*      */   protected final GoalSelector targetSelector;
/*      */   @Nullable
/*      */   private LivingEntity target;
/*      */   private final Sensing sensing;
/*  115 */   private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
/*  116 */   protected final float[] handDropChances = new float[2];
/*  117 */   private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
/*  118 */   protected final float[] armorDropChances = new float[4];
/*      */   private boolean canPickUpLoot;
/*      */   private boolean persistenceRequired;
/*  121 */   private final Map<BlockPathTypes, Float> pathfindingMalus = Maps.newEnumMap(BlockPathTypes.class);
/*      */   
/*      */   @Nullable
/*      */   private ResourceLocation lootTable;
/*      */   
/*      */   private long lootTableSeed;
/*      */   
/*      */   @Nullable
/*      */   private Entity leashHolder;
/*      */   
/*      */   private int delayedLeashHolderId;
/*      */   @Nullable
/*      */   private CompoundTag leashInfoTag;
/*  134 */   private BlockPos restrictCenter = BlockPos.ZERO;
/*  135 */   private float restrictRadius = -1.0F;
/*      */   
/*      */   protected Mob(EntityType<? extends Mob> $$0, Level $$1) {
/*  138 */     super((EntityType)$$0, $$1);
/*      */     
/*  140 */     this.goalSelector = new GoalSelector($$1.getProfilerSupplier());
/*  141 */     this.targetSelector = new GoalSelector($$1.getProfilerSupplier());
/*  142 */     this.lookControl = new LookControl(this);
/*  143 */     this.moveControl = new MoveControl(this);
/*  144 */     this.jumpControl = new JumpControl(this);
/*  145 */     this.bodyRotationControl = createBodyControl();
/*  146 */     this.navigation = createNavigation($$1);
/*  147 */     this.sensing = new Sensing(this);
/*      */     
/*  149 */     Arrays.fill(this.armorDropChances, 0.085F);
/*  150 */     Arrays.fill(this.handDropChances, 0.085F);
/*      */     
/*  152 */     if ($$1 != null && !$$1.isClientSide) {
/*  153 */       registerGoals();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void registerGoals() {}
/*      */   
/*      */   public static AttributeSupplier.Builder createMobAttributes() {
/*  161 */     return LivingEntity.createLivingAttributes()
/*  162 */       .add(Attributes.FOLLOW_RANGE, 16.0D)
/*  163 */       .add(Attributes.ATTACK_KNOCKBACK);
/*      */   }
/*      */   
/*      */   protected PathNavigation createNavigation(Level $$0) {
/*  167 */     return (PathNavigation)new GroundPathNavigation(this, $$0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean shouldPassengersInheritMalus() {
/*  180 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getPathfindingMalus(BlockPathTypes $$0) {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokevirtual getControlledVehicle : ()Lnet/minecraft/world/entity/Entity;
/*      */     //   4: astore #4
/*      */     //   6: aload #4
/*      */     //   8: instanceof net/minecraft/world/entity/Mob
/*      */     //   11: ifeq -> 32
/*      */     //   14: aload #4
/*      */     //   16: checkcast net/minecraft/world/entity/Mob
/*      */     //   19: astore_3
/*      */     //   20: aload_3
/*      */     //   21: invokevirtual shouldPassengersInheritMalus : ()Z
/*      */     //   24: ifeq -> 32
/*      */     //   27: aload_3
/*      */     //   28: astore_2
/*      */     //   29: goto -> 34
/*      */     //   32: aload_0
/*      */     //   33: astore_2
/*      */     //   34: aload_2
/*      */     //   35: getfield pathfindingMalus : Ljava/util/Map;
/*      */     //   38: aload_1
/*      */     //   39: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   44: checkcast java/lang/Float
/*      */     //   47: astore_3
/*      */     //   48: aload_3
/*      */     //   49: ifnonnull -> 59
/*      */     //   52: aload_1
/*      */     //   53: invokevirtual getMalus : ()F
/*      */     //   56: goto -> 63
/*      */     //   59: aload_3
/*      */     //   60: invokevirtual floatValue : ()F
/*      */     //   63: freturn
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #188	-> 0
/*      */     //   #189	-> 27
/*      */     //   #191	-> 32
/*      */     //   #194	-> 34
/*      */     //   #195	-> 48
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	64	0	this	Lnet/minecraft/world/entity/Mob;
/*      */     //   0	64	1	$$0	Lnet/minecraft/world/level/pathfinder/BlockPathTypes;
/*      */     //   20	12	3	$$1	Lnet/minecraft/world/entity/Mob;
/*      */     //   29	3	2	$$2	Lnet/minecraft/world/entity/Mob;
/*      */     //   34	30	2	$$3	Lnet/minecraft/world/entity/Mob;
/*      */     //   48	16	3	$$4	Ljava/lang/Float;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPathfindingMalus(BlockPathTypes $$0, float $$1) {
/*  199 */     this.pathfindingMalus.put($$0, Float.valueOf($$1));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPathfindingStart() {}
/*      */ 
/*      */   
/*      */   public void onPathfindingDone() {}
/*      */ 
/*      */   
/*      */   protected BodyRotationControl createBodyControl() {
/*  211 */     return new BodyRotationControl(this);
/*      */   }
/*      */   
/*      */   public LookControl getLookControl() {
/*  215 */     return this.lookControl;
/*      */   }
/*      */   
/*      */   public MoveControl getMoveControl() {
/*  219 */     Entity entity = getControlledVehicle(); if (entity instanceof Mob) { Mob $$0 = (Mob)entity;
/*  220 */       return $$0.getMoveControl(); }
/*      */     
/*  222 */     return this.moveControl;
/*      */   }
/*      */   
/*      */   public JumpControl getJumpControl() {
/*  226 */     return this.jumpControl;
/*      */   }
/*      */   
/*      */   public PathNavigation getNavigation() {
/*  230 */     Entity entity = getControlledVehicle(); if (entity instanceof Mob) { Mob $$0 = (Mob)entity;
/*  231 */       return $$0.getNavigation(); }
/*      */     
/*  233 */     return this.navigation;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public LivingEntity getControllingPassenger() {
/*  239 */     Entity $$0 = getFirstPassenger();
/*  240 */     if (!isNoAi() && $$0 instanceof Mob) { Mob $$1 = (Mob)$$0; if ($$0.canControlVehicle()); }  return null;
/*      */   }
/*      */   
/*      */   public Sensing getSensing() {
/*  244 */     return this.sensing;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public LivingEntity getTarget() {
/*  250 */     return this.target;
/*      */   }
/*      */   
/*      */   public void setTarget(@Nullable LivingEntity $$0) {
/*  254 */     this.target = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canAttackType(EntityType<?> $$0) {
/*  259 */     return ($$0 != EntityType.GHAST);
/*      */   }
/*      */   
/*      */   public boolean canFireProjectileWeapon(ProjectileWeaponItem $$0) {
/*  263 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void ate() {
/*  268 */     gameEvent(GameEvent.EAT);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  273 */     super.defineSynchedData();
/*  274 */     this.entityData.define(DATA_MOB_FLAGS_ID, Byte.valueOf((byte)0));
/*      */   }
/*      */   
/*      */   public int getAmbientSoundInterval() {
/*  278 */     return 80;
/*      */   }
/*      */   
/*      */   public void playAmbientSound() {
/*  282 */     SoundEvent $$0 = getAmbientSound();
/*  283 */     if ($$0 != null) {
/*  284 */       playSound($$0, getSoundVolume(), getVoicePitch());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void baseTick() {
/*  290 */     super.baseTick();
/*      */     
/*  292 */     level().getProfiler().push("mobBaseTick");
/*  293 */     if (isAlive() && this.random.nextInt(1000) < this.ambientSoundTime++) {
/*  294 */       resetAmbientSoundTime();
/*  295 */       playAmbientSound();
/*      */     } 
/*  297 */     level().getProfiler().pop();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playHurtSound(DamageSource $$0) {
/*  302 */     resetAmbientSoundTime();
/*  303 */     super.playHurtSound($$0);
/*      */   }
/*      */   
/*      */   private void resetAmbientSoundTime() {
/*  307 */     this.ambientSoundTime = -getAmbientSoundInterval();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getExperienceReward() {
/*  312 */     if (this.xpReward > 0) {
/*  313 */       int $$0 = this.xpReward;
/*      */       
/*  315 */       for (int $$1 = 0; $$1 < this.armorItems.size(); $$1++) {
/*  316 */         if (!((ItemStack)this.armorItems.get($$1)).isEmpty() && this.armorDropChances[$$1] <= 1.0F) {
/*  317 */           $$0 += 1 + this.random.nextInt(3);
/*      */         }
/*      */       } 
/*  320 */       for (int $$2 = 0; $$2 < this.handItems.size(); $$2++) {
/*  321 */         if (!((ItemStack)this.handItems.get($$2)).isEmpty() && this.handDropChances[$$2] <= 1.0F) {
/*  322 */           $$0 += 1 + this.random.nextInt(3);
/*      */         }
/*      */       } 
/*      */       
/*  326 */       return $$0;
/*      */     } 
/*  328 */     return this.xpReward;
/*      */   }
/*      */ 
/*      */   
/*      */   public void spawnAnim() {
/*  333 */     if ((level()).isClientSide) {
/*  334 */       for (int $$0 = 0; $$0 < 20; $$0++) {
/*  335 */         double $$1 = this.random.nextGaussian() * 0.02D;
/*  336 */         double $$2 = this.random.nextGaussian() * 0.02D;
/*  337 */         double $$3 = this.random.nextGaussian() * 0.02D;
/*  338 */         double $$4 = 10.0D;
/*  339 */         level().addParticle((ParticleOptions)ParticleTypes.POOF, getX(1.0D) - $$1 * 10.0D, getRandomY() - $$2 * 10.0D, getRandomZ(1.0D) - $$3 * 10.0D, $$1, $$2, $$3);
/*      */       } 
/*      */     } else {
/*  342 */       level().broadcastEntityEvent(this, (byte)20);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEvent(byte $$0) {
/*  348 */     if ($$0 == 20) {
/*  349 */       spawnAnim();
/*      */     } else {
/*  351 */       super.handleEntityEvent($$0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  357 */     super.tick();
/*      */     
/*  359 */     if (!(level()).isClientSide) {
/*  360 */       tickLeash();
/*      */       
/*  362 */       if (this.tickCount % 5 == 0) {
/*  363 */         updateControlFlags();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateControlFlags() {
/*  372 */     boolean $$0 = !(getControllingPassenger() instanceof Mob);
/*  373 */     boolean $$1 = !(getVehicle() instanceof net.minecraft.world.entity.vehicle.Boat);
/*  374 */     this.goalSelector.setControlFlag(Goal.Flag.MOVE, $$0);
/*  375 */     this.goalSelector.setControlFlag(Goal.Flag.JUMP, ($$0 && $$1));
/*  376 */     this.goalSelector.setControlFlag(Goal.Flag.LOOK, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected float tickHeadTurn(float $$0, float $$1) {
/*  381 */     this.bodyRotationControl.clientTick();
/*  382 */     return $$1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getAmbientSound() {
/*  392 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  397 */     super.addAdditionalSaveData($$0);
/*  398 */     $$0.putBoolean("CanPickUpLoot", canPickUpLoot());
/*  399 */     $$0.putBoolean("PersistenceRequired", this.persistenceRequired);
/*      */     
/*  401 */     ListTag $$1 = new ListTag();
/*  402 */     for (ItemStack $$2 : this.armorItems) {
/*  403 */       CompoundTag $$3 = new CompoundTag();
/*  404 */       if (!$$2.isEmpty()) {
/*  405 */         $$2.save($$3);
/*      */       }
/*  407 */       $$1.add($$3);
/*      */     } 
/*  409 */     $$0.put("ArmorItems", (Tag)$$1);
/*      */     
/*  411 */     ListTag $$4 = new ListTag();
/*  412 */     for (ItemStack $$5 : this.handItems) {
/*  413 */       CompoundTag $$6 = new CompoundTag();
/*  414 */       if (!$$5.isEmpty()) {
/*  415 */         $$5.save($$6);
/*      */       }
/*  417 */       $$4.add($$6);
/*      */     } 
/*  419 */     $$0.put("HandItems", (Tag)$$4);
/*      */     
/*  421 */     ListTag $$7 = new ListTag();
/*  422 */     for (float $$8 : this.armorDropChances) {
/*  423 */       $$7.add(FloatTag.valueOf($$8));
/*      */     }
/*  425 */     $$0.put("ArmorDropChances", (Tag)$$7);
/*      */     
/*  427 */     ListTag $$9 = new ListTag();
/*  428 */     for (float $$10 : this.handDropChances) {
/*  429 */       $$9.add(FloatTag.valueOf($$10));
/*      */     }
/*  431 */     $$0.put("HandDropChances", (Tag)$$9);
/*      */     
/*  433 */     if (this.leashHolder != null) {
/*  434 */       CompoundTag $$11 = new CompoundTag();
/*  435 */       if (this.leashHolder instanceof LivingEntity) {
/*      */         
/*  437 */         UUID $$12 = this.leashHolder.getUUID();
/*  438 */         $$11.putUUID("UUID", $$12);
/*  439 */       } else if (this.leashHolder instanceof HangingEntity) {
/*      */         
/*  441 */         BlockPos $$13 = ((HangingEntity)this.leashHolder).getPos();
/*  442 */         $$11.putInt("X", $$13.getX());
/*  443 */         $$11.putInt("Y", $$13.getY());
/*  444 */         $$11.putInt("Z", $$13.getZ());
/*      */       } 
/*  446 */       $$0.put("Leash", (Tag)$$11);
/*  447 */     } else if (this.leashInfoTag != null) {
/*  448 */       $$0.put("Leash", (Tag)this.leashInfoTag.copy());
/*      */     } 
/*      */     
/*  451 */     $$0.putBoolean("LeftHanded", isLeftHanded());
/*      */     
/*  453 */     if (this.lootTable != null) {
/*  454 */       $$0.putString("DeathLootTable", this.lootTable.toString());
/*  455 */       if (this.lootTableSeed != 0L) {
/*  456 */         $$0.putLong("DeathLootTableSeed", this.lootTableSeed);
/*      */       }
/*      */     } 
/*      */     
/*  460 */     if (isNoAi()) {
/*  461 */       $$0.putBoolean("NoAI", isNoAi());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  467 */     super.readAdditionalSaveData($$0);
/*      */     
/*  469 */     if ($$0.contains("CanPickUpLoot", 1)) {
/*  470 */       setCanPickUpLoot($$0.getBoolean("CanPickUpLoot"));
/*      */     }
/*  472 */     this.persistenceRequired = $$0.getBoolean("PersistenceRequired");
/*      */     
/*  474 */     if ($$0.contains("ArmorItems", 9)) {
/*  475 */       ListTag $$1 = $$0.getList("ArmorItems", 10);
/*      */       
/*  477 */       for (int $$2 = 0; $$2 < this.armorItems.size(); $$2++) {
/*  478 */         this.armorItems.set($$2, ItemStack.of($$1.getCompound($$2)));
/*      */       }
/*      */     } 
/*  481 */     if ($$0.contains("HandItems", 9)) {
/*  482 */       ListTag $$3 = $$0.getList("HandItems", 10);
/*      */       
/*  484 */       for (int $$4 = 0; $$4 < this.handItems.size(); $$4++) {
/*  485 */         this.handItems.set($$4, ItemStack.of($$3.getCompound($$4)));
/*      */       }
/*      */     } 
/*      */     
/*  489 */     if ($$0.contains("ArmorDropChances", 9)) {
/*  490 */       ListTag $$5 = $$0.getList("ArmorDropChances", 5);
/*  491 */       for (int $$6 = 0; $$6 < $$5.size(); $$6++) {
/*  492 */         this.armorDropChances[$$6] = $$5.getFloat($$6);
/*      */       }
/*      */     } 
/*  495 */     if ($$0.contains("HandDropChances", 9)) {
/*  496 */       ListTag $$7 = $$0.getList("HandDropChances", 5);
/*  497 */       for (int $$8 = 0; $$8 < $$7.size(); $$8++) {
/*  498 */         this.handDropChances[$$8] = $$7.getFloat($$8);
/*      */       }
/*      */     } 
/*      */     
/*  502 */     if ($$0.contains("Leash", 10)) {
/*  503 */       this.leashInfoTag = $$0.getCompound("Leash");
/*      */     }
/*      */     
/*  506 */     setLeftHanded($$0.getBoolean("LeftHanded"));
/*      */     
/*  508 */     if ($$0.contains("DeathLootTable", 8)) {
/*  509 */       this.lootTable = new ResourceLocation($$0.getString("DeathLootTable"));
/*  510 */       this.lootTableSeed = $$0.getLong("DeathLootTableSeed");
/*      */     } 
/*      */     
/*  513 */     setNoAi($$0.getBoolean("NoAI"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void dropFromLootTable(DamageSource $$0, boolean $$1) {
/*  518 */     super.dropFromLootTable($$0, $$1);
/*  519 */     this.lootTable = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public final ResourceLocation getLootTable() {
/*  524 */     return (this.lootTable == null) ? getDefaultLootTable() : this.lootTable;
/*      */   }
/*      */   
/*      */   protected ResourceLocation getDefaultLootTable() {
/*  528 */     return super.getLootTable();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLootTableSeed() {
/*  533 */     return this.lootTableSeed;
/*      */   }
/*      */   
/*      */   public void setZza(float $$0) {
/*  537 */     this.zza = $$0;
/*      */   }
/*      */   
/*      */   public void setYya(float $$0) {
/*  541 */     this.yya = $$0;
/*      */   }
/*      */   
/*      */   public void setXxa(float $$0) {
/*  545 */     this.xxa = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpeed(float $$0) {
/*  550 */     super.setSpeed($$0);
/*  551 */     setZza($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void aiStep() {
/*  556 */     super.aiStep();
/*      */     
/*  558 */     level().getProfiler().push("looting");
/*      */     
/*  560 */     if (!(level()).isClientSide && canPickUpLoot() && isAlive() && !this.dead && level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/*  561 */       Vec3i $$0 = getPickupReach();
/*  562 */       List<ItemEntity> $$1 = level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate($$0.getX(), $$0.getY(), $$0.getZ()));
/*  563 */       for (ItemEntity $$2 : $$1) {
/*  564 */         if ($$2.isRemoved() || $$2.getItem().isEmpty() || $$2.hasPickUpDelay()) {
/*      */           continue;
/*      */         }
/*  567 */         if (wantsToPickUp($$2.getItem())) {
/*  568 */           pickUpItem($$2);
/*      */         }
/*      */       } 
/*      */     } 
/*  572 */     level().getProfiler().pop();
/*      */   }
/*      */   
/*      */   protected Vec3i getPickupReach() {
/*  576 */     return ITEM_PICKUP_REACH;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void pickUpItem(ItemEntity $$0) {
/*  583 */     ItemStack $$1 = $$0.getItem();
/*  584 */     ItemStack $$2 = equipItemIfPossible($$1.copy());
/*      */     
/*  586 */     if (!$$2.isEmpty()) {
/*  587 */       onItemPickup($$0);
/*  588 */       take((Entity)$$0, $$2.getCount());
/*      */ 
/*      */       
/*  591 */       $$1.shrink($$2.getCount());
/*      */       
/*  593 */       if ($$1.isEmpty()) {
/*  594 */         $$0.discard();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public ItemStack equipItemIfPossible(ItemStack $$0) {
/*  600 */     EquipmentSlot $$1 = getEquipmentSlotForItem($$0);
/*  601 */     ItemStack $$2 = getItemBySlot($$1);
/*  602 */     boolean $$3 = canReplaceCurrentItem($$0, $$2);
/*      */     
/*  604 */     if ($$1.isArmor() && !$$3) {
/*  605 */       $$1 = EquipmentSlot.MAINHAND;
/*  606 */       $$2 = getItemBySlot($$1);
/*  607 */       $$3 = $$2.isEmpty();
/*      */     } 
/*      */     
/*  610 */     if ($$3 && canHoldItem($$0)) {
/*  611 */       double $$4 = getEquipmentDropChance($$1);
/*  612 */       if (!$$2.isEmpty() && Math.max(this.random.nextFloat() - 0.1F, 0.0F) < $$4) {
/*  613 */         spawnAtLocation($$2);
/*      */       }
/*      */       
/*  616 */       if ($$1.isArmor() && $$0.getCount() > 1) {
/*  617 */         ItemStack $$5 = $$0.copyWithCount(1);
/*  618 */         setItemSlotAndDropWhenKilled($$1, $$5);
/*  619 */         return $$5;
/*      */       } 
/*      */       
/*  622 */       setItemSlotAndDropWhenKilled($$1, $$0);
/*  623 */       return $$0;
/*      */     } 
/*  625 */     return ItemStack.EMPTY;
/*      */   }
/*      */   
/*      */   protected void setItemSlotAndDropWhenKilled(EquipmentSlot $$0, ItemStack $$1) {
/*  629 */     setItemSlot($$0, $$1);
/*  630 */     setGuaranteedDrop($$0);
/*  631 */     this.persistenceRequired = true;
/*      */   }
/*      */   
/*      */   public void setGuaranteedDrop(EquipmentSlot $$0) {
/*  635 */     switch ($$0.getType()) {
/*      */       case HEAD:
/*  637 */         this.handDropChances[$$0.getIndex()] = 2.0F;
/*      */         break;
/*      */       case CHEST:
/*  640 */         this.armorDropChances[$$0.getIndex()] = 2.0F;
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean canReplaceCurrentItem(ItemStack $$0, ItemStack $$1) {
/*  646 */     if ($$1.isEmpty()) {
/*  647 */       return true;
/*      */     }
/*      */     
/*  650 */     if ($$0.getItem() instanceof SwordItem) {
/*  651 */       if (!($$1.getItem() instanceof SwordItem)) {
/*  652 */         return true;
/*      */       }
/*  654 */       SwordItem $$2 = (SwordItem)$$0.getItem();
/*  655 */       SwordItem $$3 = (SwordItem)$$1.getItem();
/*  656 */       if ($$2.getDamage() != $$3.getDamage()) {
/*  657 */         return ($$2.getDamage() > $$3.getDamage());
/*      */       }
/*  659 */       return canReplaceEqualItem($$0, $$1);
/*      */     } 
/*  661 */     if ($$0.getItem() instanceof net.minecraft.world.item.BowItem && $$1.getItem() instanceof net.minecraft.world.item.BowItem)
/*  662 */       return canReplaceEqualItem($$0, $$1); 
/*  663 */     if ($$0.getItem() instanceof net.minecraft.world.item.CrossbowItem && $$1.getItem() instanceof net.minecraft.world.item.CrossbowItem)
/*  664 */       return canReplaceEqualItem($$0, $$1); 
/*  665 */     Item item = $$0.getItem(); if (item instanceof ArmorItem) { ArmorItem $$4 = (ArmorItem)item;
/*  666 */       if (EnchantmentHelper.hasBindingCurse($$1))
/*  667 */         return false; 
/*  668 */       if (!($$1.getItem() instanceof ArmorItem)) {
/*  669 */         return true;
/*      */       }
/*  671 */       ArmorItem $$5 = (ArmorItem)$$1.getItem();
/*  672 */       if ($$4.getDefense() != $$5.getDefense())
/*  673 */         return ($$4.getDefense() > $$5.getDefense()); 
/*  674 */       if ($$4.getToughness() != $$5.getToughness()) {
/*  675 */         return ($$4.getToughness() > $$5.getToughness());
/*      */       }
/*  677 */       return canReplaceEqualItem($$0, $$1); }
/*      */     
/*  679 */     if ($$0.getItem() instanceof DiggerItem) {
/*  680 */       if ($$1.getItem() instanceof net.minecraft.world.item.BlockItem)
/*  681 */         return true; 
/*  682 */       Item item1 = $$1.getItem(); if (item1 instanceof DiggerItem) { DiggerItem $$6 = (DiggerItem)item1;
/*  683 */         DiggerItem $$7 = (DiggerItem)$$0.getItem();
/*  684 */         if ($$7.getAttackDamage() != $$6.getAttackDamage()) {
/*  685 */           return ($$7.getAttackDamage() > $$6.getAttackDamage());
/*      */         }
/*  687 */         return canReplaceEqualItem($$0, $$1); }
/*      */     
/*      */     } 
/*      */ 
/*      */     
/*  692 */     return false;
/*      */   }
/*      */   
/*      */   public boolean canReplaceEqualItem(ItemStack $$0, ItemStack $$1) {
/*  696 */     if ($$0.getDamageValue() < $$1.getDamageValue() || ($$0.hasTag() && !$$1.hasTag()))
/*  697 */       return true; 
/*  698 */     if ($$0.hasTag() && $$1.hasTag())
/*      */     {
/*  700 */       return ($$0.getTag().getAllKeys().stream().anyMatch($$0 -> !$$0.equals("Damage")) && !$$1.getTag().getAllKeys().stream().anyMatch($$0 -> !$$0.equals("Damage")));
/*      */     }
/*  702 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canHoldItem(ItemStack $$0) {
/*  707 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean wantsToPickUp(ItemStack $$0) {
/*  712 */     return canHoldItem($$0);
/*      */   }
/*      */   
/*      */   public boolean removeWhenFarAway(double $$0) {
/*  716 */     return true;
/*      */   }
/*      */   
/*      */   public boolean requiresCustomPersistence() {
/*  720 */     return isPassenger();
/*      */   }
/*      */   
/*      */   protected boolean shouldDespawnInPeaceful() {
/*  724 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkDespawn() {
/*  729 */     if (level().getDifficulty() == Difficulty.PEACEFUL && shouldDespawnInPeaceful()) {
/*  730 */       discard();
/*      */       
/*      */       return;
/*      */     } 
/*  734 */     if (isPersistenceRequired() || requiresCustomPersistence()) {
/*  735 */       this.noActionTime = 0;
/*      */       
/*      */       return;
/*      */     } 
/*  739 */     Player player = level().getNearestPlayer(this, -1.0D);
/*  740 */     if (player != null) {
/*  741 */       double $$1 = player.distanceToSqr(this);
/*  742 */       int $$2 = getType().getCategory().getDespawnDistance();
/*  743 */       int $$3 = $$2 * $$2;
/*      */       
/*  745 */       if ($$1 > $$3 && removeWhenFarAway($$1)) {
/*  746 */         discard();
/*      */       }
/*      */       
/*  749 */       int $$4 = getType().getCategory().getNoDespawnDistance();
/*  750 */       int $$5 = $$4 * $$4;
/*  751 */       if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && $$1 > $$5 && removeWhenFarAway($$1)) {
/*  752 */         discard();
/*  753 */       } else if ($$1 < $$5) {
/*  754 */         this.noActionTime = 0;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void serverAiStep() {
/*  762 */     this.noActionTime++;
/*      */     
/*  764 */     level().getProfiler().push("sensing");
/*  765 */     this.sensing.tick();
/*  766 */     level().getProfiler().pop();
/*      */     
/*  768 */     int $$0 = level().getServer().getTickCount() + getId();
/*  769 */     if ($$0 % 2 == 0 || this.tickCount <= 1) {
/*  770 */       level().getProfiler().push("targetSelector");
/*  771 */       this.targetSelector.tick();
/*  772 */       level().getProfiler().pop();
/*      */       
/*  774 */       level().getProfiler().push("goalSelector");
/*  775 */       this.goalSelector.tick();
/*  776 */       level().getProfiler().pop();
/*      */     } else {
/*  778 */       level().getProfiler().push("targetSelector");
/*  779 */       this.targetSelector.tickRunningGoals(false);
/*  780 */       level().getProfiler().pop();
/*      */       
/*  782 */       level().getProfiler().push("goalSelector");
/*  783 */       this.goalSelector.tickRunningGoals(false);
/*  784 */       level().getProfiler().pop();
/*      */     } 
/*      */     
/*  787 */     level().getProfiler().push("navigation");
/*  788 */     this.navigation.tick();
/*  789 */     level().getProfiler().pop();
/*      */     
/*  791 */     level().getProfiler().push("mob tick");
/*  792 */     customServerAiStep();
/*  793 */     level().getProfiler().pop();
/*      */     
/*  795 */     level().getProfiler().push("controls");
/*  796 */     level().getProfiler().push("move");
/*  797 */     this.moveControl.tick();
/*  798 */     level().getProfiler().popPush("look");
/*  799 */     this.lookControl.tick();
/*  800 */     level().getProfiler().popPush("jump");
/*  801 */     this.jumpControl.tick();
/*  802 */     level().getProfiler().pop();
/*  803 */     level().getProfiler().pop();
/*      */     
/*  805 */     sendDebugPackets();
/*      */   }
/*      */   
/*      */   protected void sendDebugPackets() {
/*  809 */     DebugPackets.sendGoalSelector(level(), this, this.goalSelector);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void customServerAiStep() {}
/*      */   
/*      */   public int getMaxHeadXRot() {
/*  816 */     return 40;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxHeadYRot() {
/*  823 */     return 75;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeadRotSpeed() {
/*  830 */     return 10;
/*      */   }
/*      */   
/*      */   public void lookAt(Entity $$0, float $$1, float $$2) {
/*  834 */     double $$7, $$3 = $$0.getX() - getX();
/*      */     
/*  836 */     double $$4 = $$0.getZ() - getZ();
/*      */     
/*  838 */     if ($$0 instanceof LivingEntity) { LivingEntity $$5 = (LivingEntity)$$0;
/*  839 */       double $$6 = $$5.getEyeY() - getEyeY(); }
/*      */     else
/*  841 */     { $$7 = (($$0.getBoundingBox()).minY + ($$0.getBoundingBox()).maxY) / 2.0D - getEyeY(); }
/*      */ 
/*      */     
/*  844 */     double $$8 = Math.sqrt($$3 * $$3 + $$4 * $$4);
/*      */     
/*  846 */     float $$9 = (float)(Mth.atan2($$4, $$3) * 57.2957763671875D) - 90.0F;
/*  847 */     float $$10 = (float)-(Mth.atan2($$7, $$8) * 57.2957763671875D);
/*  848 */     setXRot(rotlerp(getXRot(), $$10, $$2));
/*  849 */     setYRot(rotlerp(getYRot(), $$9, $$1));
/*      */   }
/*      */   
/*      */   private float rotlerp(float $$0, float $$1, float $$2) {
/*  853 */     float $$3 = Mth.wrapDegrees($$1 - $$0);
/*  854 */     if ($$3 > $$2) {
/*  855 */       $$3 = $$2;
/*      */     }
/*  857 */     if ($$3 < -$$2) {
/*  858 */       $$3 = -$$2;
/*      */     }
/*  860 */     return $$0 + $$3;
/*      */   }
/*      */   
/*      */   public static boolean checkMobSpawnRules(EntityType<? extends Mob> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/*  864 */     BlockPos $$5 = $$3.below();
/*  865 */     return ($$2 == MobSpawnType.SPAWNER || $$1.getBlockState($$5).isValidSpawn((BlockGetter)$$1, $$5, $$0));
/*      */   }
/*      */   
/*      */   public boolean checkSpawnRules(LevelAccessor $$0, MobSpawnType $$1) {
/*  869 */     return true;
/*      */   }
/*      */   
/*      */   public boolean checkSpawnObstruction(LevelReader $$0) {
/*  873 */     return (!$$0.containsAnyLiquid(getBoundingBox()) && $$0.isUnobstructed(this));
/*      */   }
/*      */   
/*      */   public int getMaxSpawnClusterSize() {
/*  877 */     return 4;
/*      */   }
/*      */   
/*      */   public boolean isMaxGroupSizeReached(int $$0) {
/*  881 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxFallDistance() {
/*  886 */     if (getTarget() == null) {
/*  887 */       return 3;
/*      */     }
/*  889 */     int $$0 = (int)(getHealth() - getMaxHealth() * 0.33F);
/*  890 */     $$0 -= (3 - level().getDifficulty().getId()) * 4;
/*  891 */     if ($$0 < 0) {
/*  892 */       $$0 = 0;
/*      */     }
/*  894 */     return $$0 + 3;
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getHandSlots() {
/*  899 */     return (Iterable<ItemStack>)this.handItems;
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getArmorSlots() {
/*  904 */     return (Iterable<ItemStack>)this.armorItems;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getItemBySlot(EquipmentSlot $$0) {
/*  909 */     switch ($$0.getType()) {
/*      */       case HEAD:
/*  911 */         return (ItemStack)this.handItems.get($$0.getIndex());
/*      */       case CHEST:
/*  913 */         return (ItemStack)this.armorItems.get($$0.getIndex());
/*      */     } 
/*  915 */     return ItemStack.EMPTY;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setItemSlot(EquipmentSlot $$0, ItemStack $$1) {
/*  920 */     verifyEquippedItem($$1);
/*  921 */     switch ($$0.getType()) { case HEAD:
/*  922 */         onEquipItem($$0, (ItemStack)this.handItems.set($$0.getIndex(), $$1), $$1); break;
/*  923 */       case CHEST: onEquipItem($$0, (ItemStack)this.armorItems.set($$0.getIndex(), $$1), $$1);
/*      */         break; }
/*      */   
/*      */   }
/*      */   
/*      */   protected void dropCustomDeathLoot(DamageSource $$0, int $$1, boolean $$2) {
/*  929 */     super.dropCustomDeathLoot($$0, $$1, $$2);
/*  930 */     for (EquipmentSlot $$3 : EquipmentSlot.values()) {
/*  931 */       ItemStack $$4 = getItemBySlot($$3);
/*  932 */       float $$5 = getEquipmentDropChance($$3);
/*  933 */       boolean $$6 = ($$5 > 1.0F);
/*  934 */       if (!$$4.isEmpty() && !EnchantmentHelper.hasVanishingCurse($$4) && ($$2 || $$6) && Math.max(this.random.nextFloat() - $$1 * 0.01F, 0.0F) < $$5) {
/*  935 */         if (!$$6 && $$4.isDamageableItem()) {
/*  936 */           $$4.setDamageValue($$4.getMaxDamage() - this.random.nextInt(1 + this.random.nextInt(Math.max($$4.getMaxDamage() - 3, 1))));
/*      */         }
/*  938 */         spawnAtLocation($$4);
/*  939 */         setItemSlot($$3, ItemStack.EMPTY);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected float getEquipmentDropChance(EquipmentSlot $$0) {
/*      */     float $$1, $$2;
/*  946 */     switch ($$0.getType())
/*      */     { case HEAD:
/*  948 */         $$1 = this.handDropChances[$$0.getIndex()];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  958 */         return $$1;case CHEST: $$2 = this.armorDropChances[$$0.getIndex()]; return $$2; }  float $$3 = 0.0F; return $$3;
/*      */   }
/*      */   
/*      */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/*  962 */     if ($$0.nextFloat() < 0.15F * $$1.getSpecialMultiplier()) {
/*  963 */       int $$2 = $$0.nextInt(2);
/*  964 */       float $$3 = (level().getDifficulty() == Difficulty.HARD) ? 0.1F : 0.25F;
/*  965 */       if ($$0.nextFloat() < 0.095F) {
/*  966 */         $$2++;
/*      */       }
/*  968 */       if ($$0.nextFloat() < 0.095F) {
/*  969 */         $$2++;
/*      */       }
/*  971 */       if ($$0.nextFloat() < 0.095F) {
/*  972 */         $$2++;
/*      */       }
/*      */       
/*  975 */       boolean $$4 = true;
/*  976 */       for (EquipmentSlot $$5 : EquipmentSlot.values()) {
/*  977 */         if ($$5.getType() == EquipmentSlot.Type.ARMOR) {
/*      */ 
/*      */           
/*  980 */           ItemStack $$6 = getItemBySlot($$5);
/*  981 */           if (!$$4 && $$0.nextFloat() < $$3) {
/*      */             break;
/*      */           }
/*  984 */           $$4 = false;
/*  985 */           if ($$6.isEmpty()) {
/*  986 */             Item $$7 = getEquipmentForSlot($$5, $$2);
/*  987 */             if ($$7 != null)
/*  988 */               setItemSlot($$5, new ItemStack((ItemLike)$$7)); 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static Item getEquipmentForSlot(EquipmentSlot $$0, int $$1) {
/*  997 */     switch ($$0) {
/*      */       case HEAD:
/*  999 */         if ($$1 == 0) {
/* 1000 */           return Items.LEATHER_HELMET;
/*      */         }
/* 1002 */         if ($$1 == 1) {
/* 1003 */           return Items.GOLDEN_HELMET;
/*      */         }
/* 1005 */         if ($$1 == 2) {
/* 1006 */           return Items.CHAINMAIL_HELMET;
/*      */         }
/* 1008 */         if ($$1 == 3) {
/* 1009 */           return Items.IRON_HELMET;
/*      */         }
/* 1011 */         if ($$1 == 4) {
/* 1012 */           return Items.DIAMOND_HELMET;
/*      */         }
/*      */       case CHEST:
/* 1015 */         if ($$1 == 0) {
/* 1016 */           return Items.LEATHER_CHESTPLATE;
/*      */         }
/* 1018 */         if ($$1 == 1) {
/* 1019 */           return Items.GOLDEN_CHESTPLATE;
/*      */         }
/* 1021 */         if ($$1 == 2) {
/* 1022 */           return Items.CHAINMAIL_CHESTPLATE;
/*      */         }
/* 1024 */         if ($$1 == 3) {
/* 1025 */           return Items.IRON_CHESTPLATE;
/*      */         }
/* 1027 */         if ($$1 == 4) {
/* 1028 */           return Items.DIAMOND_CHESTPLATE;
/*      */         }
/*      */       case LEGS:
/* 1031 */         if ($$1 == 0) {
/* 1032 */           return Items.LEATHER_LEGGINGS;
/*      */         }
/* 1034 */         if ($$1 == 1) {
/* 1035 */           return Items.GOLDEN_LEGGINGS;
/*      */         }
/* 1037 */         if ($$1 == 2) {
/* 1038 */           return Items.CHAINMAIL_LEGGINGS;
/*      */         }
/* 1040 */         if ($$1 == 3) {
/* 1041 */           return Items.IRON_LEGGINGS;
/*      */         }
/* 1043 */         if ($$1 == 4) {
/* 1044 */           return Items.DIAMOND_LEGGINGS;
/*      */         }
/*      */       case FEET:
/* 1047 */         if ($$1 == 0) {
/* 1048 */           return Items.LEATHER_BOOTS;
/*      */         }
/* 1050 */         if ($$1 == 1) {
/* 1051 */           return Items.GOLDEN_BOOTS;
/*      */         }
/* 1053 */         if ($$1 == 2) {
/* 1054 */           return Items.CHAINMAIL_BOOTS;
/*      */         }
/* 1056 */         if ($$1 == 3) {
/* 1057 */           return Items.IRON_BOOTS;
/*      */         }
/* 1059 */         if ($$1 == 4) {
/* 1060 */           return Items.DIAMOND_BOOTS;
/*      */         }
/*      */         break;
/*      */     } 
/* 1064 */     return null;
/*      */   }
/*      */   
/*      */   protected void populateDefaultEquipmentEnchantments(RandomSource $$0, DifficultyInstance $$1) {
/* 1068 */     float $$2 = $$1.getSpecialMultiplier();
/*      */     
/* 1070 */     enchantSpawnedWeapon($$0, $$2);
/*      */     
/* 1072 */     for (EquipmentSlot $$3 : EquipmentSlot.values()) {
/* 1073 */       if ($$3.getType() == EquipmentSlot.Type.ARMOR)
/*      */       {
/*      */         
/* 1076 */         enchantSpawnedArmor($$0, $$2, $$3); } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void enchantSpawnedWeapon(RandomSource $$0, float $$1) {
/* 1081 */     if (!getMainHandItem().isEmpty() && $$0.nextFloat() < 0.25F * $$1) {
/* 1082 */       setItemSlot(EquipmentSlot.MAINHAND, EnchantmentHelper.enchantItem($$0, getMainHandItem(), (int)(5.0F + $$1 * $$0.nextInt(18)), false));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void enchantSpawnedArmor(RandomSource $$0, float $$1, EquipmentSlot $$2) {
/* 1087 */     ItemStack $$3 = getItemBySlot($$2);
/* 1088 */     if (!$$3.isEmpty() && $$0.nextFloat() < 0.5F * $$1) {
/* 1089 */       setItemSlot($$2, EnchantmentHelper.enchantItem($$0, $$3, (int)(5.0F + $$1 * $$0.nextInt(18)), false));
/*      */     }
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 1095 */     RandomSource $$5 = $$0.getRandom();
/* 1096 */     getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random spawn bonus", $$5.triangle(0.0D, 0.11485000000000001D), AttributeModifier.Operation.MULTIPLY_BASE));
/*      */     
/* 1098 */     if ($$5.nextFloat() < 0.05F) {
/* 1099 */       setLeftHanded(true);
/*      */     } else {
/* 1101 */       setLeftHanded(false);
/*      */     } 
/*      */     
/* 1104 */     return $$3;
/*      */   }
/*      */   
/*      */   public void setPersistenceRequired() {
/* 1108 */     this.persistenceRequired = true;
/*      */   }
/*      */   
/*      */   public void setDropChance(EquipmentSlot $$0, float $$1) {
/* 1112 */     switch ($$0.getType()) {
/*      */       case HEAD:
/* 1114 */         this.handDropChances[$$0.getIndex()] = $$1;
/*      */         break;
/*      */       case CHEST:
/* 1117 */         this.armorDropChances[$$0.getIndex()] = $$1;
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean canPickUpLoot() {
/* 1123 */     return this.canPickUpLoot;
/*      */   }
/*      */   
/*      */   public void setCanPickUpLoot(boolean $$0) {
/* 1127 */     this.canPickUpLoot = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canTakeItem(ItemStack $$0) {
/* 1132 */     EquipmentSlot $$1 = getEquipmentSlotForItem($$0);
/* 1133 */     return (getItemBySlot($$1).isEmpty() && canPickUpLoot());
/*      */   }
/*      */   
/*      */   public boolean isPersistenceRequired() {
/* 1137 */     return this.persistenceRequired;
/*      */   }
/*      */ 
/*      */   
/*      */   public final InteractionResult interact(Player $$0, InteractionHand $$1) {
/* 1142 */     if (!isAlive()) {
/* 1143 */       return InteractionResult.PASS;
/*      */     }
/*      */     
/* 1146 */     if (getLeashHolder() == $$0) {
/* 1147 */       dropLeash(true, !($$0.getAbilities()).instabuild);
/* 1148 */       gameEvent(GameEvent.ENTITY_INTERACT, (Entity)$$0);
/* 1149 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*      */     } 
/*      */     
/* 1152 */     InteractionResult $$2 = checkAndHandleImportantInteractions($$0, $$1);
/* 1153 */     if ($$2.consumesAction()) {
/* 1154 */       gameEvent(GameEvent.ENTITY_INTERACT, (Entity)$$0);
/* 1155 */       return $$2;
/*      */     } 
/*      */     
/* 1158 */     $$2 = mobInteract($$0, $$1);
/* 1159 */     if ($$2.consumesAction()) {
/* 1160 */       gameEvent(GameEvent.ENTITY_INTERACT, (Entity)$$0);
/* 1161 */       return $$2;
/*      */     } 
/*      */     
/* 1164 */     return super.interact($$0, $$1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private InteractionResult checkAndHandleImportantInteractions(Player $$0, InteractionHand $$1) {
/* 1170 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 1171 */     if ($$2.is(Items.LEAD) && canBeLeashed($$0)) {
/* 1172 */       setLeashedTo((Entity)$$0, true);
/* 1173 */       $$2.shrink(1);
/* 1174 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*      */     } 
/*      */ 
/*      */     
/* 1178 */     if ($$2.is(Items.NAME_TAG)) {
/* 1179 */       InteractionResult $$3 = $$2.interactLivingEntity($$0, this, $$1);
/* 1180 */       if ($$3.consumesAction()) {
/* 1181 */         return $$3;
/*      */       }
/*      */     } 
/*      */     
/* 1185 */     if ($$2.getItem() instanceof SpawnEggItem) {
/* 1186 */       if (level() instanceof ServerLevel) {
/* 1187 */         SpawnEggItem $$4 = (SpawnEggItem)$$2.getItem();
/* 1188 */         Optional<Mob> $$5 = $$4.spawnOffspringFromSpawnEgg($$0, this, getType(), (ServerLevel)level(), position(), $$2);
/* 1189 */         $$5.ifPresent($$1 -> onOffspringSpawnedFromEgg($$0, $$1));
/* 1190 */         return $$5.isPresent() ? InteractionResult.SUCCESS : InteractionResult.PASS;
/*      */       } 
/*      */       
/* 1193 */       return InteractionResult.CONSUME;
/*      */     } 
/* 1195 */     return InteractionResult.PASS;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onOffspringSpawnedFromEgg(Player $$0, Mob $$1) {}
/*      */   
/*      */   protected InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 1202 */     return InteractionResult.PASS;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWithinRestriction() {
/* 1207 */     return isWithinRestriction(blockPosition());
/*      */   }
/*      */   
/*      */   public boolean isWithinRestriction(BlockPos $$0) {
/* 1211 */     if (this.restrictRadius == -1.0F) {
/* 1212 */       return true;
/*      */     }
/* 1214 */     return (this.restrictCenter.distSqr((Vec3i)$$0) < (this.restrictRadius * this.restrictRadius));
/*      */   }
/*      */   
/*      */   public void restrictTo(BlockPos $$0, int $$1) {
/* 1218 */     this.restrictCenter = $$0;
/* 1219 */     this.restrictRadius = $$1;
/*      */   }
/*      */   
/*      */   public BlockPos getRestrictCenter() {
/* 1223 */     return this.restrictCenter;
/*      */   }
/*      */   
/*      */   public float getRestrictRadius() {
/* 1227 */     return this.restrictRadius;
/*      */   }
/*      */   
/*      */   public void clearRestriction() {
/* 1231 */     this.restrictRadius = -1.0F;
/*      */   }
/*      */   
/*      */   public boolean hasRestriction() {
/* 1235 */     return (this.restrictRadius != -1.0F);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public <T extends Mob> T convertTo(EntityType<T> $$0, boolean $$1) {
/* 1240 */     if (isRemoved()) {
/* 1241 */       return null;
/*      */     }
/*      */     
/* 1244 */     Mob mob = (Mob)$$0.create(level());
/* 1245 */     if (mob == null) {
/* 1246 */       return null;
/*      */     }
/*      */     
/* 1249 */     mob.copyPosition(this);
/* 1250 */     mob.setBaby(isBaby());
/* 1251 */     mob.setNoAi(isNoAi());
/* 1252 */     if (hasCustomName()) {
/* 1253 */       mob.setCustomName(getCustomName());
/* 1254 */       mob.setCustomNameVisible(isCustomNameVisible());
/*      */     } 
/* 1256 */     if (isPersistenceRequired()) {
/* 1257 */       mob.setPersistenceRequired();
/*      */     }
/* 1259 */     mob.setInvulnerable(isInvulnerable());
/* 1260 */     if ($$1) {
/* 1261 */       mob.setCanPickUpLoot(canPickUpLoot());
/* 1262 */       for (EquipmentSlot $$3 : EquipmentSlot.values()) {
/* 1263 */         ItemStack $$4 = getItemBySlot($$3);
/* 1264 */         if (!$$4.isEmpty()) {
/* 1265 */           mob.setItemSlot($$3, $$4.copyAndClear());
/* 1266 */           mob.setDropChance($$3, getEquipmentDropChance($$3));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1271 */     level().addFreshEntity(mob);
/*      */     
/* 1273 */     if (isPassenger()) {
/* 1274 */       Entity $$5 = getVehicle();
/* 1275 */       stopRiding();
/* 1276 */       mob.startRiding($$5, true);
/*      */     } 
/*      */     
/* 1279 */     discard();
/* 1280 */     return (T)mob;
/*      */   }
/*      */   
/*      */   protected void tickLeash() {
/* 1284 */     if (this.leashInfoTag != null) {
/* 1285 */       restoreLeashFromSave();
/*      */     }
/* 1287 */     if (this.leashHolder == null) {
/*      */       return;
/*      */     }
/* 1290 */     if (!isAlive() || !this.leashHolder.isAlive()) {
/* 1291 */       dropLeash(true, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void dropLeash(boolean $$0, boolean $$1) {
/* 1296 */     if (this.leashHolder != null) {
/* 1297 */       this.leashHolder = null;
/* 1298 */       this.leashInfoTag = null;
/* 1299 */       if (!(level()).isClientSide && $$1) {
/* 1300 */         spawnAtLocation((ItemLike)Items.LEAD);
/*      */       }
/*      */       
/* 1303 */       if (!(level()).isClientSide && $$0 && level() instanceof ServerLevel) {
/* 1304 */         ((ServerLevel)level()).getChunkSource().broadcast(this, (Packet)new ClientboundSetEntityLinkPacket(this, null));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean canBeLeashed(Player $$0) {
/* 1310 */     return (!isLeashed() && !(this instanceof net.minecraft.world.entity.monster.Enemy));
/*      */   }
/*      */   
/*      */   public boolean isLeashed() {
/* 1314 */     return (this.leashHolder != null);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity getLeashHolder() {
/* 1320 */     if (this.leashHolder == null && this.delayedLeashHolderId != 0 && (level()).isClientSide) {
/* 1321 */       this.leashHolder = level().getEntity(this.delayedLeashHolderId);
/*      */     }
/* 1323 */     return this.leashHolder;
/*      */   }
/*      */   
/*      */   public void setLeashedTo(Entity $$0, boolean $$1) {
/* 1327 */     this.leashHolder = $$0;
/* 1328 */     this.leashInfoTag = null;
/*      */     
/* 1330 */     if (!(level()).isClientSide && $$1 && level() instanceof ServerLevel) {
/* 1331 */       ((ServerLevel)level()).getChunkSource().broadcast(this, (Packet)new ClientboundSetEntityLinkPacket(this, this.leashHolder));
/*      */     }
/*      */     
/* 1334 */     if (isPassenger()) {
/* 1335 */       stopRiding();
/*      */     }
/*      */   }
/*      */   
/*      */   public void setDelayedLeashHolderId(int $$0) {
/* 1340 */     this.delayedLeashHolderId = $$0;
/* 1341 */     dropLeash(false, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean startRiding(Entity $$0, boolean $$1) {
/* 1346 */     boolean $$2 = super.startRiding($$0, $$1);
/* 1347 */     if ($$2 && isLeashed()) {
/* 1348 */       dropLeash(true, true);
/*      */     }
/*      */     
/* 1351 */     return $$2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreLeashFromSave() {
/* 1357 */     if (this.leashInfoTag != null && level() instanceof ServerLevel) {
/* 1358 */       if (this.leashInfoTag.hasUUID("UUID")) {
/* 1359 */         UUID $$0 = this.leashInfoTag.getUUID("UUID");
/* 1360 */         Entity $$1 = ((ServerLevel)level()).getEntity($$0);
/* 1361 */         if ($$1 != null) {
/* 1362 */           setLeashedTo($$1, true);
/*      */           return;
/*      */         } 
/* 1365 */       } else if (this.leashInfoTag.contains("X", 99) && this.leashInfoTag.contains("Y", 99) && this.leashInfoTag.contains("Z", 99)) {
/* 1366 */         BlockPos $$2 = NbtUtils.readBlockPos(this.leashInfoTag);
/* 1367 */         setLeashedTo((Entity)LeashFenceKnotEntity.getOrCreateKnot(level(), $$2), true);
/*      */         
/*      */         return;
/*      */       } 
/* 1371 */       if (this.tickCount > 100) {
/* 1372 */         spawnAtLocation((ItemLike)Items.LEAD);
/* 1373 */         this.leashInfoTag = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEffectiveAi() {
/* 1380 */     return (super.isEffectiveAi() && !isNoAi());
/*      */   }
/*      */   
/*      */   public void setNoAi(boolean $$0) {
/* 1384 */     byte $$1 = ((Byte)this.entityData.get(DATA_MOB_FLAGS_ID)).byteValue();
/* 1385 */     this.entityData.set(DATA_MOB_FLAGS_ID, Byte.valueOf($$0 ? (byte)($$1 | 0x1) : (byte)($$1 & 0xFFFFFFFE)));
/*      */   }
/*      */   
/*      */   public void setLeftHanded(boolean $$0) {
/* 1389 */     byte $$1 = ((Byte)this.entityData.get(DATA_MOB_FLAGS_ID)).byteValue();
/* 1390 */     this.entityData.set(DATA_MOB_FLAGS_ID, Byte.valueOf($$0 ? (byte)($$1 | 0x2) : (byte)($$1 & 0xFFFFFFFD)));
/*      */   }
/*      */   
/*      */   public void setAggressive(boolean $$0) {
/* 1394 */     byte $$1 = ((Byte)this.entityData.get(DATA_MOB_FLAGS_ID)).byteValue();
/* 1395 */     this.entityData.set(DATA_MOB_FLAGS_ID, Byte.valueOf($$0 ? (byte)($$1 | 0x4) : (byte)($$1 & 0xFFFFFFFB)));
/*      */   }
/*      */   
/*      */   public boolean isNoAi() {
/* 1399 */     return ((((Byte)this.entityData.get(DATA_MOB_FLAGS_ID)).byteValue() & 0x1) != 0);
/*      */   }
/*      */   
/*      */   public boolean isLeftHanded() {
/* 1403 */     return ((((Byte)this.entityData.get(DATA_MOB_FLAGS_ID)).byteValue() & 0x2) != 0);
/*      */   }
/*      */   
/*      */   public boolean isAggressive() {
/* 1407 */     return ((((Byte)this.entityData.get(DATA_MOB_FLAGS_ID)).byteValue() & 0x4) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBaby(boolean $$0) {}
/*      */ 
/*      */   
/*      */   public HumanoidArm getMainArm() {
/* 1416 */     return isLeftHanded() ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
/*      */   }
/*      */   
/*      */   public boolean isWithinMeleeAttackRange(LivingEntity $$0) {
/* 1420 */     return getAttackBoundingBox().intersects($$0.getHitbox());
/*      */   }
/*      */   
/*      */   protected AABB getAttackBoundingBox() {
/*      */     AABB $$4;
/* 1425 */     Entity $$0 = getVehicle();
/* 1426 */     if ($$0 != null) {
/* 1427 */       AABB $$1 = $$0.getBoundingBox();
/* 1428 */       AABB $$2 = getBoundingBox();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1435 */       AABB $$3 = new AABB(Math.min($$2.minX, $$1.minX), $$2.minY, Math.min($$2.minZ, $$1.minZ), Math.max($$2.maxX, $$1.maxX), $$2.maxY, Math.max($$2.maxZ, $$1.maxZ));
/*      */     } else {
/*      */       
/* 1438 */       $$4 = getBoundingBox();
/*      */     } 
/* 1440 */     return $$4.inflate(DEFAULT_ATTACK_REACH, 0.0D, DEFAULT_ATTACK_REACH);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doHurtTarget(Entity $$0) {
/* 1451 */     float $$1 = (float)getAttributeValue(Attributes.ATTACK_DAMAGE);
/* 1452 */     float $$2 = (float)getAttributeValue(Attributes.ATTACK_KNOCKBACK);
/*      */     
/* 1454 */     if ($$0 instanceof LivingEntity) {
/* 1455 */       $$1 += EnchantmentHelper.getDamageBonus(getMainHandItem(), ((LivingEntity)$$0).getMobType());
/* 1456 */       $$2 += EnchantmentHelper.getKnockbackBonus(this);
/*      */     } 
/*      */     
/* 1459 */     int $$3 = EnchantmentHelper.getFireAspect(this);
/* 1460 */     if ($$3 > 0) {
/* 1461 */       $$0.setSecondsOnFire($$3 * 4);
/*      */     }
/*      */     
/* 1464 */     boolean $$4 = $$0.hurt(damageSources().mobAttack(this), $$1);
/*      */     
/* 1466 */     if ($$4) {
/* 1467 */       if ($$2 > 0.0F && $$0 instanceof LivingEntity) {
/* 1468 */         ((LivingEntity)$$0).knockback(($$2 * 0.5F), Mth.sin(getYRot() * 0.017453292F), -Mth.cos(getYRot() * 0.017453292F));
/* 1469 */         setDeltaMovement(getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1476 */       if ($$0 instanceof Player) { Player $$5 = (Player)$$0;
/* 1477 */         maybeDisableShield($$5, getMainHandItem(), $$5.isUsingItem() ? $$5.getUseItem() : ItemStack.EMPTY); }
/*      */ 
/*      */       
/* 1480 */       doEnchantDamageEffects(this, $$0);
/* 1481 */       setLastHurtMob($$0);
/*      */     } 
/*      */     
/* 1484 */     return $$4;
/*      */   }
/*      */   
/*      */   private void maybeDisableShield(Player $$0, ItemStack $$1, ItemStack $$2) {
/* 1488 */     if (!$$1.isEmpty() && !$$2.isEmpty() && $$1.getItem() instanceof net.minecraft.world.item.AxeItem && $$2.is(Items.SHIELD)) {
/* 1489 */       float $$3 = 0.25F + EnchantmentHelper.getBlockEfficiency(this) * 0.05F;
/*      */       
/* 1491 */       if (this.random.nextFloat() < $$3) {
/* 1492 */         $$0.getCooldowns().addCooldown(Items.SHIELD, 100);
/* 1493 */         level().broadcastEntityEvent((Entity)$$0, (byte)30);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isSunBurnTick() {
/* 1499 */     if (level().isDay() && !(level()).isClientSide) {
/* 1500 */       float $$0 = getLightLevelDependentMagicValue();
/* 1501 */       BlockPos $$1 = BlockPos.containing(getX(), getEyeY(), getZ());
/* 1502 */       boolean $$2 = (isInWaterRainOrBubble() || this.isInPowderSnow || this.wasInPowderSnow);
/* 1503 */       if ($$0 > 0.5F && this.random.nextFloat() * 30.0F < ($$0 - 0.4F) * 2.0F && !$$2 && level().canSeeSky($$1)) {
/* 1504 */         return true;
/*      */       }
/*      */     } 
/* 1507 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void jumpInLiquid(TagKey<Fluid> $$0) {
/* 1512 */     if (getNavigation().canFloat()) {
/* 1513 */       super.jumpInLiquid($$0);
/*      */     } else {
/* 1515 */       setDeltaMovement(getDeltaMovement().add(0.0D, 0.3D, 0.0D));
/*      */     } 
/*      */   }
/*      */   
/*      */   @VisibleForTesting
/*      */   public void removeFreeWill() {
/* 1521 */     removeAllGoals($$0 -> true);
/* 1522 */     getBrain().removeAllBehaviors();
/*      */   }
/*      */   
/*      */   public void removeAllGoals(Predicate<Goal> $$0) {
/* 1526 */     this.goalSelector.removeAllGoals($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void removeAfterChangingDimensions() {
/* 1531 */     super.removeAfterChangingDimensions();
/*      */     
/* 1533 */     dropLeash(true, false);
/* 1534 */     getAllSlots().forEach($$0 -> {
/*      */           if (!$$0.isEmpty()) {
/*      */             $$0.setCount(0);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ItemStack getPickResult() {
/* 1544 */     SpawnEggItem $$0 = SpawnEggItem.byId(getType());
/* 1545 */     if ($$0 == null) {
/* 1546 */       return null;
/*      */     }
/* 1548 */     return new ItemStack((ItemLike)$$0);
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\Mob.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */