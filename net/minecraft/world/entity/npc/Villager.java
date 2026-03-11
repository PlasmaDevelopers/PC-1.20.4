/*      */ package net.minecraft.world.entity.npc;
/*      */ 
/*      */ import com.google.common.annotations.VisibleForTesting;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.serialization.DataResult;
/*      */ import com.mojang.serialization.Dynamic;
/*      */ import com.mojang.serialization.DynamicOps;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.Set;
/*      */ import java.util.function.BiPredicate;
/*      */ import java.util.stream.Collectors;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.GlobalPos;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.ListTag;
/*      */ import net.minecraft.nbt.NbtOps;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.protocol.game.DebugPackets;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.stats.Stats;
/*      */ import net.minecraft.tags.ItemTags;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.SpawnUtil;
/*      */ import net.minecraft.world.Difficulty;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.SimpleContainer;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.effect.MobEffectInstance;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.AgeableMob;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EntityType;
/*      */ import net.minecraft.world.entity.ExperienceOrb;
/*      */ import net.minecraft.world.entity.LightningBolt;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.Mob;
/*      */ import net.minecraft.world.entity.MobSpawnType;
/*      */ import net.minecraft.world.entity.ReputationEventHandler;
/*      */ import net.minecraft.world.entity.SpawnGroupData;
/*      */ import net.minecraft.world.entity.ai.Brain;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
/*      */ import net.minecraft.world.entity.ai.gossip.GossipContainer;
/*      */ import net.minecraft.world.entity.ai.gossip.GossipType;
/*      */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*      */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*      */ import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
/*      */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*      */ import net.minecraft.world.entity.ai.sensing.GolemSensor;
/*      */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*      */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*      */ import net.minecraft.world.entity.ai.village.ReputationEventType;
/*      */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*      */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*      */ import net.minecraft.world.entity.ai.village.poi.PoiTypes;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.monster.Witch;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.raid.Raid;
/*      */ import net.minecraft.world.entity.schedule.Activity;
/*      */ import net.minecraft.world.entity.schedule.Schedule;
/*      */ import net.minecraft.world.flag.FeatureFlags;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.trading.MerchantOffer;
/*      */ import net.minecraft.world.item.trading.MerchantOffers;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.ServerLevelAccessor;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class Villager extends AbstractVillager implements ReputationEventHandler, VillagerDataHolder {
/*   97 */   private static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*   99 */   private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(Villager.class, EntityDataSerializers.VILLAGER_DATA);
/*      */   
/*      */   public static final int BREEDING_FOOD_THRESHOLD = 12;
/*  102 */   public static final Map<Item, Integer> FOOD_POINTS = (Map<Item, Integer>)ImmutableMap.of(Items.BREAD, 
/*  103 */       Integer.valueOf(4), Items.POTATO, 
/*  104 */       Integer.valueOf(1), Items.CARROT, 
/*  105 */       Integer.valueOf(1), Items.BEETROOT, 
/*  106 */       Integer.valueOf(1));
/*      */   
/*      */   private static final int TRADES_PER_LEVEL = 2;
/*      */   
/*  110 */   private static final Set<Item> WANTED_ITEMS = (Set<Item>)ImmutableSet.of(Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, (Object[])new Item[] { Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD });
/*      */   
/*      */   private static final int MAX_GOSSIP_TOPICS = 10;
/*      */   
/*      */   private static final int GOSSIP_COOLDOWN = 1200;
/*      */   
/*      */   private static final int GOSSIP_DECAY_INTERVAL = 24000;
/*      */   
/*      */   private static final int REPUTATION_CHANGE_PER_EVENT = 25;
/*      */   
/*      */   private static final int HOW_FAR_AWAY_TO_TALK_TO_OTHER_VILLAGERS_ABOUT_GOLEMS = 10;
/*      */   
/*      */   private static final int HOW_MANY_VILLAGERS_NEED_TO_AGREE_TO_SPAWN_A_GOLEM = 5;
/*      */   
/*      */   private static final long TIME_SINCE_SLEEPING_FOR_GOLEM_SPAWNING = 24000L;
/*      */   
/*      */   @VisibleForTesting
/*      */   public static final float SPEED_MODIFIER = 0.5F;
/*      */   private int updateMerchantTimer;
/*      */   private boolean increaseProfessionLevelOnUpdate;
/*      */   @Nullable
/*      */   private Player lastTradedPlayer;
/*      */   private boolean chasing;
/*      */   private int foodLevel;
/*  134 */   private final GossipContainer gossips = new GossipContainer();
/*      */   
/*      */   private long lastGossipTime;
/*      */   
/*      */   private long lastGossipDecayTime;
/*      */   
/*      */   private int villagerXp;
/*      */   private long lastRestockGameTime;
/*      */   private int numberOfRestocksToday;
/*      */   private long lastRestockCheckDayTime;
/*      */   private boolean assignProfessionWhenSpawned;
/*  145 */   private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HOME, MemoryModuleType.JOB_SITE, MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleType.MEETING_POINT, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.VISIBLE_VILLAGER_BABIES, MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, (Object[])new MemoryModuleType[] { MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.BREED_TARGET, MemoryModuleType.PATH, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleType.NEAREST_BED, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_HOSTILE, MemoryModuleType.SECONDARY_JOB_SITE, MemoryModuleType.HIDING_PLACE, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.LAST_SLEPT, MemoryModuleType.LAST_WOKEN, MemoryModuleType.LAST_WORKED_AT_POI, MemoryModuleType.GOLEM_DETECTED_RECENTLY });
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
/*      */ 
/*      */   
/*  178 */   private static final ImmutableList<SensorType<? extends Sensor<? super Villager>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.NEAREST_BED, SensorType.HURT_BY, SensorType.VILLAGER_HOSTILES, SensorType.VILLAGER_BABIES, SensorType.SECONDARY_POIS, SensorType.GOLEM_DETECTED);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<Villager, Holder<PoiType>>> POI_MEMORIES;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  190 */     POI_MEMORIES = (Map<MemoryModuleType<GlobalPos>, BiPredicate<Villager, Holder<PoiType>>>)ImmutableMap.of(MemoryModuleType.HOME, ($$0, $$1) -> $$1.is(PoiTypes.HOME), MemoryModuleType.JOB_SITE, ($$0, $$1) -> $$0.getVillagerData().getProfession().heldJobSite().test($$1), MemoryModuleType.POTENTIAL_JOB_SITE, ($$0, $$1) -> VillagerProfession.ALL_ACQUIRABLE_JOBS.test($$1), MemoryModuleType.MEETING_POINT, ($$0, $$1) -> $$1.is(PoiTypes.MEETING));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Villager(EntityType<? extends Villager> $$0, Level $$1) {
/*  198 */     this($$0, $$1, VillagerType.PLAINS);
/*      */   }
/*      */   
/*      */   public Villager(EntityType<? extends Villager> $$0, Level $$1, VillagerType $$2) {
/*  202 */     super((EntityType)$$0, $$1);
/*  203 */     ((GroundPathNavigation)getNavigation()).setCanOpenDoors(true);
/*  204 */     getNavigation().setCanFloat(true);
/*  205 */     setCanPickUpLoot(true);
/*  206 */     setVillagerData(getVillagerData().setType($$2).setProfession(VillagerProfession.NONE));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Brain<Villager> getBrain() {
/*  212 */     return super.getBrain();
/*      */   }
/*      */ 
/*      */   
/*      */   protected Brain.Provider<Villager> brainProvider() {
/*  217 */     return Brain.provider((Collection)MEMORY_TYPES, (Collection)SENSOR_TYPES);
/*      */   }
/*      */ 
/*      */   
/*      */   protected Brain<?> makeBrain(Dynamic<?> $$0) {
/*  222 */     Brain<Villager> $$1 = brainProvider().makeBrain($$0);
/*  223 */     registerBrainGoals($$1);
/*  224 */     return $$1;
/*      */   }
/*      */   
/*      */   public void refreshBrain(ServerLevel $$0) {
/*  228 */     Brain<Villager> $$1 = getBrain();
/*  229 */     $$1.stopAll($$0, (LivingEntity)this);
/*  230 */     this.brain = $$1.copyWithoutBehaviors();
/*  231 */     registerBrainGoals(getBrain());
/*      */   }
/*      */   
/*      */   private void registerBrainGoals(Brain<Villager> $$0) {
/*  235 */     VillagerProfession $$1 = getVillagerData().getProfession();
/*      */     
/*  237 */     if (isBaby()) {
/*  238 */       $$0.setSchedule(Schedule.VILLAGER_BABY);
/*  239 */       $$0.addActivity(Activity.PLAY, VillagerGoalPackages.getPlayPackage(0.5F));
/*      */     } else {
/*  241 */       $$0.setSchedule(Schedule.VILLAGER_DEFAULT);
/*  242 */       $$0.addActivityWithConditions(Activity.WORK, VillagerGoalPackages.getWorkPackage($$1, 0.5F), (Set)ImmutableSet.of(Pair.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT)));
/*      */     } 
/*      */     
/*  245 */     $$0.addActivity(Activity.CORE, VillagerGoalPackages.getCorePackage($$1, 0.5F));
/*  246 */     $$0.addActivityWithConditions(Activity.MEET, VillagerGoalPackages.getMeetPackage($$1, 0.5F), (Set)ImmutableSet.of(Pair.of(MemoryModuleType.MEETING_POINT, MemoryStatus.VALUE_PRESENT)));
/*  247 */     $$0.addActivity(Activity.REST, VillagerGoalPackages.getRestPackage($$1, 0.5F));
/*  248 */     $$0.addActivity(Activity.IDLE, VillagerGoalPackages.getIdlePackage($$1, 0.5F));
/*  249 */     $$0.addActivity(Activity.PANIC, VillagerGoalPackages.getPanicPackage($$1, 0.5F));
/*  250 */     $$0.addActivity(Activity.PRE_RAID, VillagerGoalPackages.getPreRaidPackage($$1, 0.5F));
/*  251 */     $$0.addActivity(Activity.RAID, VillagerGoalPackages.getRaidPackage($$1, 0.5F));
/*  252 */     $$0.addActivity(Activity.HIDE, VillagerGoalPackages.getHidePackage($$1, 0.5F));
/*  253 */     $$0.setCoreActivities((Set)ImmutableSet.of(Activity.CORE));
/*  254 */     $$0.setDefaultActivity(Activity.IDLE);
/*  255 */     $$0.setActiveActivityIfPossible(Activity.IDLE);
/*  256 */     $$0.updateActivityFromSchedule(level().getDayTime(), level().getGameTime());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void ageBoundaryReached() {
/*  261 */     super.ageBoundaryReached();
/*  262 */     if (level() instanceof ServerLevel) {
/*  263 */       refreshBrain((ServerLevel)level());
/*      */     }
/*      */   }
/*      */   
/*      */   public static AttributeSupplier.Builder createAttributes() {
/*  268 */     return Mob.createMobAttributes()
/*  269 */       .add(Attributes.MOVEMENT_SPEED, 0.5D)
/*  270 */       .add(Attributes.FOLLOW_RANGE, 48.0D);
/*      */   }
/*      */   
/*      */   public boolean assignProfessionWhenSpawned() {
/*  274 */     return this.assignProfessionWhenSpawned;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void customServerAiStep() {
/*  279 */     level().getProfiler().push("villagerBrain");
/*  280 */     getBrain().tick((ServerLevel)level(), (LivingEntity)this);
/*  281 */     level().getProfiler().pop();
/*      */     
/*  283 */     if (this.assignProfessionWhenSpawned) {
/*  284 */       this.assignProfessionWhenSpawned = false;
/*      */     }
/*      */     
/*  287 */     if (!isTrading() && this.updateMerchantTimer > 0) {
/*  288 */       this.updateMerchantTimer--;
/*  289 */       if (this.updateMerchantTimer <= 0) {
/*  290 */         if (this.increaseProfessionLevelOnUpdate) {
/*  291 */           increaseMerchantCareer();
/*  292 */           this.increaseProfessionLevelOnUpdate = false;
/*      */         } 
/*  294 */         addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
/*      */       } 
/*      */     } 
/*      */     
/*  298 */     if (this.lastTradedPlayer != null && level() instanceof ServerLevel) {
/*  299 */       ((ServerLevel)level()).onReputationEvent(ReputationEventType.TRADE, (Entity)this.lastTradedPlayer, this);
/*  300 */       level().broadcastEntityEvent((Entity)this, (byte)14);
/*  301 */       this.lastTradedPlayer = null;
/*      */     } 
/*      */ 
/*      */     
/*  305 */     if (!isNoAi() && this.random.nextInt(100) == 0) {
/*  306 */       Raid $$0 = ((ServerLevel)level()).getRaidAt(blockPosition());
/*  307 */       if ($$0 != null && $$0.isActive() && !$$0.isOver()) {
/*  308 */         level().broadcastEntityEvent((Entity)this, (byte)42);
/*      */       }
/*      */     } 
/*      */     
/*  312 */     if (getVillagerData().getProfession() == VillagerProfession.NONE && isTrading()) {
/*  313 */       stopTrading();
/*      */     }
/*      */     
/*  316 */     super.customServerAiStep();
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  321 */     super.tick();
/*      */     
/*  323 */     if (getUnhappyCounter() > 0) {
/*  324 */       setUnhappyCounter(getUnhappyCounter() - 1);
/*      */     }
/*      */     
/*  327 */     maybeDecayGossip();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/*  333 */     ItemStack $$2 = $$0.getItemInHand($$1);
/*  334 */     if (!$$2.is(Items.VILLAGER_SPAWN_EGG) && isAlive() && !isTrading() && !isSleeping()) {
/*  335 */       if (isBaby()) {
/*  336 */         setUnhappy();
/*  337 */         return InteractionResult.sidedSuccess((level()).isClientSide);
/*      */       } 
/*      */       
/*  340 */       boolean $$3 = getOffers().isEmpty();
/*      */ 
/*      */       
/*  343 */       if ($$1 == InteractionHand.MAIN_HAND) {
/*  344 */         if ($$3 && 
/*  345 */           !(level()).isClientSide) {
/*  346 */           setUnhappy();
/*      */         }
/*      */         
/*  349 */         $$0.awardStat(Stats.TALKED_TO_VILLAGER);
/*      */       } 
/*      */       
/*  352 */       if ($$3) {
/*  353 */         return InteractionResult.sidedSuccess((level()).isClientSide);
/*      */       }
/*      */       
/*  356 */       if (!(level()).isClientSide && !this.offers.isEmpty())
/*      */       {
/*  358 */         startTrading($$0);
/*      */       }
/*      */       
/*  361 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*      */     } 
/*  363 */     return super.mobInteract($$0, $$1);
/*      */   }
/*      */   
/*      */   private void setUnhappy() {
/*  367 */     setUnhappyCounter(40);
/*  368 */     if (!level().isClientSide()) {
/*  369 */       playSound(SoundEvents.VILLAGER_NO, getSoundVolume(), getVoicePitch());
/*      */     }
/*      */   }
/*      */   
/*      */   private void startTrading(Player $$0) {
/*  374 */     updateSpecialPrices($$0);
/*  375 */     setTradingPlayer($$0);
/*  376 */     openTradingScreen($$0, getDisplayName(), getVillagerData().getLevel());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTradingPlayer(@Nullable Player $$0) {
/*  381 */     boolean $$1 = (getTradingPlayer() != null && $$0 == null);
/*  382 */     super.setTradingPlayer($$0);
/*  383 */     if ($$1) {
/*  384 */       stopTrading();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void stopTrading() {
/*  390 */     super.stopTrading();
/*  391 */     resetSpecialPrices();
/*      */   }
/*      */   
/*      */   private void resetSpecialPrices() {
/*  395 */     for (MerchantOffer $$0 : getOffers()) {
/*  396 */       $$0.resetSpecialPriceDiff();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canRestock() {
/*  402 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isClientSide() {
/*  407 */     return (level()).isClientSide;
/*      */   }
/*      */   
/*      */   public void restock() {
/*  411 */     updateDemand();
/*  412 */     for (MerchantOffer $$0 : getOffers()) {
/*  413 */       $$0.resetUses();
/*      */     }
/*  415 */     resendOffersToTradingPlayer();
/*      */     
/*  417 */     this.lastRestockGameTime = level().getGameTime();
/*  418 */     this.numberOfRestocksToday++;
/*      */   }
/*      */   
/*      */   private void resendOffersToTradingPlayer() {
/*  422 */     MerchantOffers $$0 = getOffers();
/*  423 */     Player $$1 = getTradingPlayer();
/*  424 */     if ($$1 != null && !$$0.isEmpty()) {
/*  425 */       $$1.sendMerchantOffers($$1.containerMenu.containerId, $$0, getVillagerData().getLevel(), getVillagerXp(), showProgressBar(), canRestock());
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean needsToRestock() {
/*  430 */     for (MerchantOffer $$0 : getOffers()) {
/*  431 */       if ($$0.needsRestock()) {
/*  432 */         return true;
/*      */       }
/*      */     } 
/*  435 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean allowedToRestock() {
/*  440 */     return (this.numberOfRestocksToday == 0 || (this.numberOfRestocksToday < 2 && level().getGameTime() > this.lastRestockGameTime + 2400L));
/*      */   }
/*      */   public boolean shouldRestock() {
/*      */     int i;
/*  444 */     long $$0 = this.lastRestockGameTime + 12000L;
/*  445 */     long $$1 = level().getGameTime();
/*  446 */     boolean $$2 = ($$1 > $$0);
/*      */ 
/*      */ 
/*      */     
/*  450 */     long $$3 = level().getDayTime();
/*  451 */     if (this.lastRestockCheckDayTime > 0L) {
/*  452 */       long $$4 = this.lastRestockCheckDayTime / 24000L;
/*  453 */       long $$5 = $$3 / 24000L;
/*  454 */       i = $$2 | (($$5 > $$4) ? 1 : 0);
/*      */     } 
/*  456 */     this.lastRestockCheckDayTime = $$3;
/*      */     
/*  458 */     if (i != 0) {
/*  459 */       this.lastRestockGameTime = $$1;
/*  460 */       resetNumberOfRestocks();
/*      */     } 
/*      */     
/*  463 */     return (allowedToRestock() && needsToRestock());
/*      */   }
/*      */ 
/*      */   
/*      */   private void catchUpDemand() {
/*  468 */     int $$0 = 2 - this.numberOfRestocksToday;
/*  469 */     if ($$0 > 0) {
/*  470 */       for (MerchantOffer $$1 : getOffers()) {
/*  471 */         $$1.resetUses();
/*      */       }
/*      */     }
/*  474 */     for (int $$2 = 0; $$2 < $$0; $$2++) {
/*  475 */       updateDemand();
/*      */     }
/*  477 */     resendOffersToTradingPlayer();
/*      */   }
/*      */   
/*      */   private void updateDemand() {
/*  481 */     for (MerchantOffer $$0 : getOffers()) {
/*  482 */       $$0.updateDemand();
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateSpecialPrices(Player $$0) {
/*  487 */     int $$1 = getPlayerReputation($$0);
/*  488 */     if ($$1 != 0) {
/*  489 */       for (MerchantOffer $$2 : getOffers()) {
/*  490 */         $$2.addToSpecialPriceDiff(-Mth.floor($$1 * $$2.getPriceMultiplier()));
/*      */       }
/*      */     }
/*      */     
/*  494 */     if ($$0.hasEffect(MobEffects.HERO_OF_THE_VILLAGE)) {
/*  495 */       MobEffectInstance $$3 = $$0.getEffect(MobEffects.HERO_OF_THE_VILLAGE);
/*  496 */       int $$4 = $$3.getAmplifier();
/*  497 */       for (MerchantOffer $$5 : getOffers()) {
/*  498 */         double $$6 = 0.3D + 0.0625D * $$4;
/*  499 */         int $$7 = (int)Math.floor($$6 * $$5.getBaseCostA().getCount());
/*  500 */         $$5.addToSpecialPriceDiff(-Math.max($$7, 1));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  507 */     super.defineSynchedData();
/*  508 */     this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
/*      */   }
/*      */ 
/*      */   
/*      */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  513 */     super.addAdditionalSaveData($$0);
/*      */     
/*  515 */     Objects.requireNonNull(LOGGER); VillagerData.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, getVillagerData()).resultOrPartial(LOGGER::error)
/*  516 */       .ifPresent($$1 -> $$0.put("VillagerData", $$1));
/*      */     
/*  518 */     $$0.putByte("FoodLevel", (byte)this.foodLevel);
/*  519 */     $$0.put("Gossips", (Tag)this.gossips.store((DynamicOps)NbtOps.INSTANCE));
/*  520 */     $$0.putInt("Xp", this.villagerXp);
/*  521 */     $$0.putLong("LastRestock", this.lastRestockGameTime);
/*  522 */     $$0.putLong("LastGossipDecay", this.lastGossipDecayTime);
/*  523 */     $$0.putInt("RestocksToday", this.numberOfRestocksToday);
/*  524 */     if (this.assignProfessionWhenSpawned) {
/*  525 */       $$0.putBoolean("AssignProfessionWhenSpawned", true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  531 */     super.readAdditionalSaveData($$0);
/*      */     
/*  533 */     if ($$0.contains("VillagerData", 10)) {
/*  534 */       DataResult<VillagerData> $$1 = VillagerData.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.get("VillagerData")));
/*  535 */       Objects.requireNonNull(LOGGER); $$1.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
/*      */     } 
/*      */ 
/*      */     
/*  539 */     if ($$0.contains("Offers", 10)) {
/*  540 */       this.offers = new MerchantOffers($$0.getCompound("Offers"));
/*      */     }
/*      */     
/*  543 */     if ($$0.contains("FoodLevel", 1)) {
/*  544 */       this.foodLevel = $$0.getByte("FoodLevel");
/*      */     }
/*      */     
/*  547 */     ListTag $$2 = $$0.getList("Gossips", 10);
/*  548 */     this.gossips.update(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$2));
/*      */     
/*  550 */     if ($$0.contains("Xp", 3)) {
/*  551 */       this.villagerXp = $$0.getInt("Xp");
/*      */     }
/*      */     
/*  554 */     this.lastRestockGameTime = $$0.getLong("LastRestock");
/*      */     
/*  556 */     this.lastGossipDecayTime = $$0.getLong("LastGossipDecay");
/*      */     
/*  558 */     setCanPickUpLoot(true);
/*      */ 
/*      */     
/*  561 */     if (level() instanceof ServerLevel) {
/*  562 */       refreshBrain((ServerLevel)level());
/*      */     }
/*      */     
/*  565 */     this.numberOfRestocksToday = $$0.getInt("RestocksToday");
/*      */     
/*  567 */     if ($$0.contains("AssignProfessionWhenSpawned")) {
/*  568 */       this.assignProfessionWhenSpawned = $$0.getBoolean("AssignProfessionWhenSpawned");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean removeWhenFarAway(double $$0) {
/*  574 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getAmbientSound() {
/*  580 */     if (isSleeping()) {
/*  581 */       return null;
/*      */     }
/*      */     
/*  584 */     if (isTrading()) {
/*  585 */       return SoundEvents.VILLAGER_TRADE;
/*      */     }
/*  587 */     return SoundEvents.VILLAGER_AMBIENT;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  592 */     return SoundEvents.VILLAGER_HURT;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getDeathSound() {
/*  597 */     return SoundEvents.VILLAGER_DEATH;
/*      */   }
/*      */   
/*      */   public void playWorkSound() {
/*  601 */     SoundEvent $$0 = getVillagerData().getProfession().workSound();
/*  602 */     if ($$0 != null) {
/*  603 */       playSound($$0, getSoundVolume(), getVoicePitch());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void setVillagerData(VillagerData $$0) {
/*  609 */     VillagerData $$1 = getVillagerData();
/*  610 */     if ($$1.getProfession() != $$0.getProfession()) {
/*  611 */       this.offers = null;
/*      */     }
/*      */     
/*  614 */     this.entityData.set(DATA_VILLAGER_DATA, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public VillagerData getVillagerData() {
/*  619 */     return (VillagerData)this.entityData.get(DATA_VILLAGER_DATA);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void rewardTradeXp(MerchantOffer $$0) {
/*  624 */     int $$1 = 3 + this.random.nextInt(4);
/*      */     
/*  626 */     this.villagerXp += $$0.getXp();
/*  627 */     this.lastTradedPlayer = getTradingPlayer();
/*      */     
/*  629 */     if (shouldIncreaseLevel()) {
/*  630 */       this.updateMerchantTimer = 40;
/*  631 */       this.increaseProfessionLevelOnUpdate = true;
/*  632 */       $$1 += 5;
/*      */     } 
/*      */     
/*  635 */     if ($$0.shouldRewardExp()) {
/*  636 */       level().addFreshEntity((Entity)new ExperienceOrb(level(), getX(), getY() + 0.5D, getZ(), $$1));
/*      */     }
/*      */   }
/*      */   
/*      */   public void setChasing(boolean $$0) {
/*  641 */     this.chasing = $$0;
/*      */   }
/*      */   
/*      */   public boolean isChasing() {
/*  645 */     return this.chasing;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLastHurtByMob(@Nullable LivingEntity $$0) {
/*  651 */     if ($$0 != null && level() instanceof ServerLevel) {
/*  652 */       ((ServerLevel)level()).onReputationEvent(ReputationEventType.VILLAGER_HURT, (Entity)$$0, this);
/*  653 */       if (isAlive() && $$0 instanceof Player) {
/*  654 */         level().broadcastEntityEvent((Entity)this, (byte)13);
/*      */       }
/*      */     } 
/*  657 */     super.setLastHurtByMob($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void die(DamageSource $$0) {
/*  662 */     LOGGER.info("Villager {} died, message: '{}'", this, $$0.getLocalizedDeathMessage((LivingEntity)this).getString());
/*  663 */     Entity $$1 = $$0.getEntity();
/*  664 */     if ($$1 != null) {
/*  665 */       tellWitnessesThatIWasMurdered($$1);
/*      */     }
/*      */     
/*  668 */     releaseAllPois();
/*  669 */     super.die($$0);
/*      */   }
/*      */   
/*      */   private void releaseAllPois() {
/*  673 */     releasePoi(MemoryModuleType.HOME);
/*  674 */     releasePoi(MemoryModuleType.JOB_SITE);
/*  675 */     releasePoi(MemoryModuleType.POTENTIAL_JOB_SITE);
/*  676 */     releasePoi(MemoryModuleType.MEETING_POINT);
/*      */   }
/*      */   private void tellWitnessesThatIWasMurdered(Entity $$0) {
/*      */     ServerLevel $$1;
/*  680 */     Level level = level(); if (level instanceof ServerLevel) { $$1 = (ServerLevel)level; }
/*      */     else
/*      */     { return; }
/*      */     
/*  684 */     Optional<NearestVisibleLivingEntities> $$3 = this.brain.getMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
/*  685 */     if ($$3.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  689 */     Objects.requireNonNull(ReputationEventHandler.class); ((NearestVisibleLivingEntities)$$3.get()).findAll(ReputationEventHandler.class::isInstance)
/*  690 */       .forEach($$2 -> $$0.onReputationEvent(ReputationEventType.VILLAGER_KILLED, $$1, (ReputationEventHandler)$$2));
/*      */   }
/*      */   
/*      */   public void releasePoi(MemoryModuleType<GlobalPos> $$0) {
/*  694 */     if (!(level() instanceof ServerLevel)) {
/*      */       return;
/*      */     }
/*  697 */     MinecraftServer $$1 = ((ServerLevel)level()).getServer();
/*  698 */     this.brain.getMemory($$0).ifPresent($$2 -> {
/*      */           ServerLevel $$3 = $$0.getLevel($$2.dimension());
/*      */           if ($$3 == null) {
/*      */             return;
/*      */           }
/*      */           PoiManager $$4 = $$3.getPoiManager();
/*      */           Optional<Holder<PoiType>> $$5 = $$4.getType($$2.pos());
/*      */           BiPredicate<Villager, Holder<PoiType>> $$6 = POI_MEMORIES.get($$1);
/*      */           if ($$5.isPresent() && $$6.test(this, $$5.get())) {
/*      */             $$4.release($$2.pos());
/*      */             DebugPackets.sendPoiTicketCountPacket($$3, $$2.pos());
/*      */           } 
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBreed() {
/*  715 */     return (this.foodLevel + countFoodPointsInInventory() >= 12 && !isSleeping() && getAge() == 0);
/*      */   }
/*      */   
/*      */   private boolean hungry() {
/*  719 */     return (this.foodLevel < 12);
/*      */   }
/*      */   
/*      */   private void eatUntilFull() {
/*  723 */     if (!hungry() || countFoodPointsInInventory() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  727 */     for (int $$0 = 0; $$0 < getInventory().getContainerSize(); $$0++) {
/*  728 */       ItemStack $$1 = getInventory().getItem($$0);
/*      */       
/*  730 */       if (!$$1.isEmpty()) {
/*  731 */         Integer $$2 = FOOD_POINTS.get($$1.getItem());
/*  732 */         if ($$2 != null) {
/*  733 */           int $$3 = $$1.getCount();
/*  734 */           for (int $$4 = $$3; $$4 > 0; $$4--) {
/*  735 */             this.foodLevel += $$2.intValue();
/*  736 */             getInventory().removeItem($$0, 1);
/*      */             
/*  738 */             if (!hungry()) {
/*      */               return;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getPlayerReputation(Player $$0) {
/*  748 */     return this.gossips.getReputation($$0.getUUID(), $$0 -> true);
/*      */   }
/*      */   
/*      */   private void digestFood(int $$0) {
/*  752 */     this.foodLevel -= $$0;
/*      */   }
/*      */   
/*      */   public void eatAndDigestFood() {
/*  756 */     eatUntilFull();
/*  757 */     digestFood(12);
/*      */   }
/*      */   
/*      */   public void setOffers(MerchantOffers $$0) {
/*  761 */     this.offers = $$0;
/*      */   }
/*      */   
/*      */   private boolean shouldIncreaseLevel() {
/*  765 */     int $$0 = getVillagerData().getLevel();
/*  766 */     return (VillagerData.canLevelUp($$0) && this.villagerXp >= VillagerData.getMaxXpPerLevel($$0));
/*      */   }
/*      */   
/*      */   private void increaseMerchantCareer() {
/*  770 */     setVillagerData(getVillagerData().setLevel(getVillagerData().getLevel() + 1));
/*      */     
/*  772 */     updateTrades();
/*      */   }
/*      */ 
/*      */   
/*      */   protected Component getTypeName() {
/*  777 */     return (Component)Component.translatable(getType().getDescriptionId() + "." + getType().getDescriptionId());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEvent(byte $$0) {
/*  782 */     if ($$0 == 12) {
/*  783 */       addParticlesAroundSelf((ParticleOptions)ParticleTypes.HEART);
/*  784 */     } else if ($$0 == 13) {
/*  785 */       addParticlesAroundSelf((ParticleOptions)ParticleTypes.ANGRY_VILLAGER);
/*  786 */     } else if ($$0 == 14) {
/*  787 */       addParticlesAroundSelf((ParticleOptions)ParticleTypes.HAPPY_VILLAGER);
/*  788 */     } else if ($$0 == 42) {
/*  789 */       addParticlesAroundSelf((ParticleOptions)ParticleTypes.SPLASH);
/*      */     } else {
/*  791 */       super.handleEntityEvent($$0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*  798 */     if ($$2 == MobSpawnType.BREEDING) {
/*  799 */       setVillagerData(getVillagerData().setProfession(VillagerProfession.NONE));
/*      */     }
/*  801 */     if ($$2 == MobSpawnType.COMMAND || $$2 == MobSpawnType.SPAWN_EGG || MobSpawnType.isSpawner($$2) || $$2 == MobSpawnType.DISPENSER) {
/*  802 */       setVillagerData(getVillagerData().setType(VillagerType.byBiome($$0.getBiome(blockPosition()))));
/*      */     }
/*      */     
/*  805 */     if ($$2 == MobSpawnType.STRUCTURE) {
/*  806 */       this.assignProfessionWhenSpawned = true;
/*      */     }
/*      */     
/*  809 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Villager getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/*      */     VillagerType $$5;
/*  816 */     double $$2 = this.random.nextDouble();
/*  817 */     if ($$2 < 0.5D) {
/*  818 */       VillagerType $$3 = VillagerType.byBiome($$0.getBiome(blockPosition()));
/*  819 */     } else if ($$2 < 0.75D) {
/*  820 */       VillagerType $$4 = getVillagerData().getType();
/*      */     } else {
/*  822 */       $$5 = ((Villager)$$1).getVillagerData().getType();
/*      */     } 
/*      */     
/*  825 */     Villager $$6 = new Villager(EntityType.VILLAGER, (Level)$$0, $$5);
/*  826 */     $$6.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$6.blockPosition()), MobSpawnType.BREEDING, (SpawnGroupData)null, (CompoundTag)null);
/*  827 */     return $$6;
/*      */   }
/*      */ 
/*      */   
/*      */   public void thunderHit(ServerLevel $$0, LightningBolt $$1) {
/*  832 */     if ($$0.getDifficulty() != Difficulty.PEACEFUL) {
/*  833 */       LOGGER.info("Villager {} was struck by lightning {}.", this, $$1);
/*  834 */       Witch $$2 = (Witch)EntityType.WITCH.create((Level)$$0);
/*  835 */       if ($$2 != null) {
/*  836 */         $$2.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
/*  837 */         $$2.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$2.blockPosition()), MobSpawnType.CONVERSION, null, null);
/*  838 */         $$2.setNoAi(isNoAi());
/*  839 */         if (hasCustomName()) {
/*  840 */           $$2.setCustomName(getCustomName());
/*  841 */           $$2.setCustomNameVisible(isCustomNameVisible());
/*      */         } 
/*  843 */         $$2.setPersistenceRequired();
/*  844 */         $$0.addFreshEntityWithPassengers((Entity)$$2);
/*  845 */         releaseAllPois();
/*  846 */         discard();
/*      */       } else {
/*  848 */         super.thunderHit($$0, $$1);
/*      */       } 
/*      */     } else {
/*  851 */       super.thunderHit($$0, $$1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void pickUpItem(ItemEntity $$0) {
/*  857 */     InventoryCarrier.pickUpItem((Mob)this, this, $$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean wantsToPickUp(ItemStack $$0) {
/*  862 */     Item $$1 = $$0.getItem();
/*  863 */     return ((WANTED_ITEMS.contains($$1) || getVillagerData().getProfession().requestedItems().contains($$1)) && getInventory().canAddItem($$0));
/*      */   }
/*      */   
/*      */   public boolean hasExcessFood() {
/*  867 */     return (countFoodPointsInInventory() >= 24);
/*      */   }
/*      */   
/*      */   public boolean wantsMoreFood() {
/*  871 */     return (countFoodPointsInInventory() < 12);
/*      */   }
/*      */   
/*      */   private int countFoodPointsInInventory() {
/*  875 */     SimpleContainer $$0 = getInventory();
/*  876 */     return FOOD_POINTS.entrySet().stream().mapToInt($$1 -> $$0.countItem((Item)$$1.getKey()) * ((Integer)$$1.getValue()).intValue()).sum();
/*      */   }
/*      */   
/*      */   public boolean hasFarmSeeds() {
/*  880 */     return getInventory().hasAnyMatching($$0 -> $$0.is(ItemTags.VILLAGER_PLANTABLE_SEEDS));
/*      */   }
/*      */   
/*      */   protected void updateTrades() {
/*      */     Int2ObjectMap<VillagerTrades.ItemListing[]> $$3;
/*  885 */     VillagerData $$0 = getVillagerData();
/*      */ 
/*      */     
/*  888 */     if (level().enabledFeatures().contains(FeatureFlags.TRADE_REBALANCE)) {
/*  889 */       Int2ObjectMap<VillagerTrades.ItemListing[]> $$1 = VillagerTrades.EXPERIMENTAL_TRADES.get($$0.getProfession());
/*  890 */       Int2ObjectMap<VillagerTrades.ItemListing[]> $$2 = ($$1 != null) ? $$1 : VillagerTrades.TRADES.get($$0.getProfession());
/*      */     } else {
/*  892 */       $$3 = VillagerTrades.TRADES.get($$0.getProfession());
/*      */     } 
/*      */     
/*  895 */     if ($$3 == null || $$3.isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/*  899 */     VillagerTrades.ItemListing[] $$4 = (VillagerTrades.ItemListing[])$$3.get($$0.getLevel());
/*      */     
/*  901 */     if ($$4 == null) {
/*      */       return;
/*      */     }
/*      */     
/*  905 */     MerchantOffers $$5 = getOffers();
/*  906 */     addOffersFromItemListings($$5, $$4, 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void gossip(ServerLevel $$0, Villager $$1, long $$2) {
/*  913 */     if (($$2 >= this.lastGossipTime && $$2 < this.lastGossipTime + 1200L) || ($$2 >= $$1.lastGossipTime && $$2 < $$1.lastGossipTime + 1200L)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  919 */     this.gossips.transferFrom($$1.gossips, this.random, 10);
/*      */     
/*  921 */     this.lastGossipTime = $$2;
/*  922 */     $$1.lastGossipTime = $$2;
/*      */     
/*  924 */     spawnGolemIfNeeded($$0, $$2, 5);
/*      */   }
/*      */   
/*      */   private void maybeDecayGossip() {
/*  928 */     long $$0 = level().getGameTime();
/*      */     
/*  930 */     if (this.lastGossipDecayTime == 0L) {
/*  931 */       this.lastGossipDecayTime = $$0;
/*      */       
/*      */       return;
/*      */     } 
/*  935 */     if ($$0 < this.lastGossipDecayTime + 24000L) {
/*      */       return;
/*      */     }
/*      */     
/*  939 */     this.gossips.decay();
/*  940 */     this.lastGossipDecayTime = $$0;
/*      */   }
/*      */   
/*      */   public void spawnGolemIfNeeded(ServerLevel $$0, long $$1, int $$2) {
/*  944 */     if (!wantsToSpawnGolem($$1)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  949 */     AABB $$3 = getBoundingBox().inflate(10.0D, 10.0D, 10.0D);
/*      */     
/*  951 */     List<Villager> $$4 = $$0.getEntitiesOfClass(Villager.class, $$3);
/*      */ 
/*      */ 
/*      */     
/*  955 */     List<Villager> $$5 = (List<Villager>)$$4.stream().filter($$1 -> $$1.wantsToSpawnGolem($$0)).limit(5L).collect(Collectors.toList());
/*      */     
/*  957 */     if ($$5.size() < $$2) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  963 */     if (SpawnUtil.trySpawnMob(EntityType.IRON_GOLEM, MobSpawnType.MOB_SUMMONED, $$0, blockPosition(), 10, 8, 6, SpawnUtil.Strategy.LEGACY_IRON_GOLEM).isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  968 */     $$4.forEach(GolemSensor::golemDetected);
/*      */   }
/*      */   
/*      */   public boolean wantsToSpawnGolem(long $$0) {
/*  972 */     if (!golemSpawnConditionsMet(level().getGameTime())) {
/*  973 */       return false;
/*      */     }
/*  975 */     if (this.brain.hasMemoryValue(MemoryModuleType.GOLEM_DETECTED_RECENTLY)) {
/*  976 */       return false;
/*      */     }
/*  978 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onReputationEventFrom(ReputationEventType $$0, Entity $$1) {
/*  983 */     if ($$0 == ReputationEventType.ZOMBIE_VILLAGER_CURED) {
/*  984 */       this.gossips.add($$1.getUUID(), GossipType.MAJOR_POSITIVE, 20);
/*  985 */       this.gossips.add($$1.getUUID(), GossipType.MINOR_POSITIVE, 25);
/*  986 */     } else if ($$0 == ReputationEventType.TRADE) {
/*  987 */       this.gossips.add($$1.getUUID(), GossipType.TRADING, 2);
/*  988 */     } else if ($$0 == ReputationEventType.VILLAGER_HURT) {
/*  989 */       this.gossips.add($$1.getUUID(), GossipType.MINOR_NEGATIVE, 25);
/*  990 */     } else if ($$0 == ReputationEventType.VILLAGER_KILLED) {
/*  991 */       this.gossips.add($$1.getUUID(), GossipType.MAJOR_NEGATIVE, 25);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getVillagerXp() {
/*  997 */     return this.villagerXp;
/*      */   }
/*      */   
/*      */   public void setVillagerXp(int $$0) {
/* 1001 */     this.villagerXp = $$0;
/*      */   }
/*      */   
/*      */   private void resetNumberOfRestocks() {
/* 1005 */     catchUpDemand();
/* 1006 */     this.numberOfRestocksToday = 0;
/*      */   }
/*      */   
/*      */   public GossipContainer getGossips() {
/* 1010 */     return this.gossips;
/*      */   }
/*      */   
/*      */   public void setGossips(Tag $$0) {
/* 1014 */     this.gossips.update(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void sendDebugPackets() {
/* 1019 */     super.sendDebugPackets();
/*      */     
/* 1021 */     DebugPackets.sendEntityBrain((LivingEntity)this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void startSleeping(BlockPos $$0) {
/* 1026 */     super.startSleeping($$0);
/* 1027 */     this.brain.setMemory(MemoryModuleType.LAST_SLEPT, Long.valueOf(level().getGameTime()));
/* 1028 */     this.brain.eraseMemory(MemoryModuleType.WALK_TARGET);
/* 1029 */     this.brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopSleeping() {
/* 1034 */     super.stopSleeping();
/* 1035 */     this.brain.setMemory(MemoryModuleType.LAST_WOKEN, Long.valueOf(level().getGameTime()));
/*      */   }
/*      */   
/*      */   private boolean golemSpawnConditionsMet(long $$0) {
/* 1039 */     Optional<Long> $$1 = this.brain.getMemory(MemoryModuleType.LAST_SLEPT);
/* 1040 */     if ($$1.isPresent()) {
/* 1041 */       return ($$0 - ((Long)$$1.get()).longValue() < 24000L);
/*      */     }
/* 1043 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\Villager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */