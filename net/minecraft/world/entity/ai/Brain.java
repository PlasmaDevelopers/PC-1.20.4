/*     */ package net.minecraft.world.entity.ai;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.RecordBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*     */ import net.minecraft.world.entity.ai.behavior.BehaviorControl;
/*     */ import net.minecraft.world.entity.ai.memory.ExpirableValue;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.ai.sensing.Sensor;
/*     */ import net.minecraft.world.entity.ai.sensing.SensorType;
/*     */ import net.minecraft.world.entity.schedule.Activity;
/*     */ import net.minecraft.world.entity.schedule.Schedule;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Brain<E extends LivingEntity>
/*     */ {
/*  49 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   private final Supplier<Codec<Brain<E>>> codec;
/*     */   private static final int SCHEDULE_UPDATE_DELAY = 20;
/*     */   
/*     */   public static final class Provider<E extends LivingEntity>
/*     */   {
/*     */     private final Collection<? extends MemoryModuleType<?>> memoryTypes;
/*     */     
/*     */     Provider(Collection<? extends MemoryModuleType<?>> $$0, Collection<? extends SensorType<? extends Sensor<? super E>>> $$1) {
/*  58 */       this.memoryTypes = $$0;
/*  59 */       this.sensorTypes = $$1;
/*  60 */       this.codec = Brain.codec($$0, $$1);
/*     */     }
/*     */     private final Collection<? extends SensorType<? extends Sensor<? super E>>> sensorTypes; private final Codec<Brain<E>> codec;
/*     */     public Brain<E> makeBrain(Dynamic<?> $$0) {
/*  64 */       Objects.requireNonNull(Brain.LOGGER); return this.codec.parse($$0).resultOrPartial(Brain.LOGGER::error).orElseGet(() -> new Brain<>(this.memoryTypes, this.sensorTypes, ImmutableList.of(), ()));
/*     */     }
/*     */   }
/*     */   
/*     */   public static <E extends LivingEntity> Provider<E> provider(Collection<? extends MemoryModuleType<?>> $$0, Collection<? extends SensorType<? extends Sensor<? super E>>> $$1) {
/*  69 */     return new Provider<>($$0, $$1);
/*     */   }
/*     */   
/*     */   public static <E extends LivingEntity> Codec<Brain<E>> codec(final Collection<? extends MemoryModuleType<?>> memoryTypes, final Collection<? extends SensorType<? extends Sensor<? super E>>> sensorTypes) {
/*  73 */     final MutableObject<Codec<Brain<E>>> codecReference = new MutableObject();
/*     */     
/*  75 */     $$2.setValue((new MapCodec<Brain<E>>()
/*     */         {
/*     */           public <T> Stream<T> keys(DynamicOps<T> $$0) {
/*  78 */             return memoryTypes.stream()
/*  79 */               .flatMap($$0 -> $$0.getCodec().map(()).stream())
/*  80 */               .map($$1 -> $$0.createString($$1.toString()));
/*     */           }
/*     */ 
/*     */           
/*     */           public <T> DataResult<Brain<E>> decode(DynamicOps<T> $$0, MapLike<T> $$1) {
/*  85 */             MutableObject<DataResult<ImmutableList.Builder<Brain.MemoryValue<?>>>> $$2 = new MutableObject(DataResult.success(ImmutableList.builder()));
/*     */             
/*  87 */             $$1.entries().forEach($$2 -> {
/*     */                   DataResult<MemoryModuleType<?>> $$3 = BuiltInRegistries.MEMORY_MODULE_TYPE.byNameCodec().parse($$0, $$2.getFirst());
/*     */                   
/*     */                   DataResult<? extends Brain.MemoryValue<?>> $$4 = $$3.flatMap(());
/*     */                   $$1.setValue(((DataResult)$$1.getValue()).apply2(ImmutableList.Builder::add, $$4));
/*     */                 });
/*  93 */             Objects.requireNonNull(Brain.LOGGER); ImmutableList<Brain.MemoryValue<?>> $$3 = ((DataResult)$$2.getValue()).resultOrPartial(Brain.LOGGER::error).map(ImmutableList.Builder::build).orElseGet(ImmutableList::of);
/*  94 */             Objects.requireNonNull(codecReference); return DataResult.success(new Brain<>(memoryTypes, sensorTypes, $$3, codecReference::getValue));
/*     */           }
/*     */           
/*     */           private <T, U> DataResult<Brain.MemoryValue<U>> captureRead(MemoryModuleType<U> $$0, DynamicOps<T> $$1, T $$2) {
/*  98 */             return ((DataResult)$$0.getCodec().map(DataResult::success).orElseGet(() -> DataResult.error(())))
/*  99 */               .flatMap($$2 -> $$2.parse($$0, $$1))
/* 100 */               .map($$1 -> new Brain.MemoryValue($$0, Optional.of($$1)));
/*     */           }
/*     */ 
/*     */           
/*     */           public <T> RecordBuilder<T> encode(Brain<E> $$0, DynamicOps<T> $$1, RecordBuilder<T> $$2) {
/* 105 */             $$0.memories().forEach($$2 -> $$2.serialize($$0, $$1));
/* 106 */             return $$2;
/*     */           }
/* 108 */         }).fieldOf("memories").codec());
/*     */     
/* 110 */     return (Codec<Brain<E>>)$$2.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 115 */   private final Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> memories = Maps.newHashMap();
/*     */ 
/*     */   
/* 118 */   private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> sensors = Maps.newLinkedHashMap();
/*     */ 
/*     */   
/* 121 */   private final Map<Integer, Map<Activity, Set<BehaviorControl<? super E>>>> availableBehaviorsByPriority = Maps.newTreeMap();
/*     */   
/* 123 */   private Schedule schedule = Schedule.EMPTY;
/*     */ 
/*     */   
/* 126 */   private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> activityRequirements = Maps.newHashMap();
/*     */   
/* 128 */   private final Map<Activity, Set<MemoryModuleType<?>>> activityMemoriesToEraseWhenStopped = Maps.newHashMap();
/*     */ 
/*     */   
/* 131 */   private Set<Activity> coreActivities = Sets.newHashSet();
/*     */ 
/*     */   
/* 134 */   private final Set<Activity> activeActivities = Sets.newHashSet();
/*     */ 
/*     */   
/* 137 */   private Activity defaultActivity = Activity.IDLE;
/*     */   
/* 139 */   private long lastScheduleUpdate = -9999L;
/*     */   
/*     */   public Brain(Collection<? extends MemoryModuleType<?>> $$0, Collection<? extends SensorType<? extends Sensor<? super E>>> $$1, ImmutableList<MemoryValue<?>> $$2, Supplier<Codec<Brain<E>>> $$3) {
/* 142 */     this.codec = $$3;
/* 143 */     for (MemoryModuleType<?> $$4 : $$0) {
/* 144 */       this.memories.put($$4, Optional.empty());
/*     */     }
/* 146 */     for (SensorType<? extends Sensor<? super E>> $$5 : $$1) {
/* 147 */       this.sensors.put($$5, $$5.create());
/*     */     }
/*     */     
/* 150 */     for (Sensor<? super E> $$6 : this.sensors.values()) {
/* 151 */       for (MemoryModuleType<?> $$7 : (Iterable<MemoryModuleType<?>>)$$6.requires()) {
/* 152 */         this.memories.put($$7, Optional.empty());
/*     */       }
/*     */     } 
/*     */     
/* 156 */     for (UnmodifiableIterator<MemoryValue> unmodifiableIterator = $$2.iterator(); unmodifiableIterator.hasNext(); ) { MemoryValue<?> $$8 = unmodifiableIterator.next();
/* 157 */       $$8.setMemoryInternal(this); }
/*     */   
/*     */   }
/*     */   
/*     */   public <T> DataResult<T> serializeStart(DynamicOps<T> $$0) {
/* 162 */     return ((Codec)this.codec.get()).encodeStart($$0, this);
/*     */   }
/*     */   
/*     */   private static final class MemoryValue<U>
/*     */   {
/*     */     private final MemoryModuleType<U> type;
/*     */     private final Optional<? extends ExpirableValue<U>> value;
/*     */     
/*     */     static <U> MemoryValue<U> createUnchecked(MemoryModuleType<U> $$0, Optional<? extends ExpirableValue<?>> $$1) {
/* 171 */       return new MemoryValue<>($$0, (Optional)$$1);
/*     */     }
/*     */     
/*     */     MemoryValue(MemoryModuleType<U> $$0, Optional<? extends ExpirableValue<U>> $$1) {
/* 175 */       this.type = $$0;
/* 176 */       this.value = $$1;
/*     */     }
/*     */     
/*     */     void setMemoryInternal(Brain<?> $$0) {
/* 180 */       $$0.setMemoryInternal(this.type, this.value);
/*     */     }
/*     */     
/*     */     public <T> void serialize(DynamicOps<T> $$0, RecordBuilder<T> $$1) {
/* 184 */       this.type.getCodec().ifPresent($$2 -> this.value.ifPresent(()));
/*     */     }
/*     */   }
/*     */   
/*     */   Stream<MemoryValue<?>> memories() {
/* 189 */     return this.memories.entrySet().stream().map($$0 -> MemoryValue.createUnchecked((MemoryModuleType)$$0.getKey(), (Optional<? extends ExpirableValue<?>>)$$0.getValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasMemoryValue(MemoryModuleType<?> $$0) {
/* 196 */     return checkMemory($$0, MemoryStatus.VALUE_PRESENT);
/*     */   }
/*     */   
/*     */   public void clearMemories() {
/* 200 */     this.memories.keySet().forEach($$0 -> this.memories.put($$0, Optional.empty()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <U> void eraseMemory(MemoryModuleType<U> $$0) {
/* 206 */     setMemory($$0, Optional.empty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <U> void setMemory(MemoryModuleType<U> $$0, @Nullable U $$1) {
/* 214 */     setMemory($$0, Optional.ofNullable($$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <U> void setMemoryWithExpiry(MemoryModuleType<U> $$0, U $$1, long $$2) {
/* 223 */     setMemoryInternal($$0, Optional.of(ExpirableValue.of($$1, $$2)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <U> void setMemory(MemoryModuleType<U> $$0, Optional<? extends U> $$1) {
/* 231 */     setMemoryInternal($$0, $$1.map(ExpirableValue::of));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <U> void setMemoryInternal(MemoryModuleType<U> $$0, Optional<? extends ExpirableValue<?>> $$1) {
/* 240 */     if (this.memories.containsKey($$0)) {
/* 241 */       if ($$1.isPresent() && isEmptyCollection(((ExpirableValue)$$1.get()).getValue())) {
/* 242 */         eraseMemory($$0);
/*     */       } else {
/* 244 */         this.memories.put($$0, $$1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public <U> Optional<U> getMemory(MemoryModuleType<U> $$0) {
/* 251 */     Optional<? extends ExpirableValue<?>> $$1 = this.memories.get($$0);
/* 252 */     if ($$1 == null) {
/* 253 */       throw new IllegalStateException("Unregistered memory fetched: " + $$0);
/*     */     }
/* 255 */     return $$1.map(ExpirableValue::getValue);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <U> Optional<U> getMemoryInternal(MemoryModuleType<U> $$0) {
/* 261 */     Optional<? extends ExpirableValue<?>> $$1 = this.memories.get($$0);
/* 262 */     if ($$1 == null) {
/* 263 */       return null;
/*     */     }
/* 265 */     return $$1.map(ExpirableValue::getValue);
/*     */   }
/*     */   
/*     */   public <U> long getTimeUntilExpiry(MemoryModuleType<U> $$0) {
/* 269 */     Optional<? extends ExpirableValue<?>> $$1 = this.memories.get($$0);
/* 270 */     return ((Long)$$1.<Long>map(ExpirableValue::getTimeToLive).orElse(Long.valueOf(0L))).longValue();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   @VisibleForDebug
/*     */   public Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> getMemories() {
/* 276 */     return this.memories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <U> boolean isMemoryValue(MemoryModuleType<U> $$0, U $$1) {
/* 283 */     if (!hasMemoryValue($$0)) {
/* 284 */       return false;
/*     */     }
/* 286 */     return getMemory($$0).filter($$1 -> $$1.equals($$0)).isPresent();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkMemory(MemoryModuleType<?> $$0, MemoryStatus $$1) {
/* 291 */     Optional<? extends ExpirableValue<?>> $$2 = this.memories.get($$0);
/* 292 */     if ($$2 == null) {
/* 293 */       return false;
/*     */     }
/*     */     
/* 296 */     return ($$1 == MemoryStatus.REGISTERED || ($$1 == MemoryStatus.VALUE_PRESENT && $$2
/* 297 */       .isPresent()) || ($$1 == MemoryStatus.VALUE_ABSENT && $$2
/* 298 */       .isEmpty()));
/*     */   }
/*     */   
/*     */   public Schedule getSchedule() {
/* 302 */     return this.schedule;
/*     */   }
/*     */   
/*     */   public void setSchedule(Schedule $$0) {
/* 306 */     this.schedule = $$0;
/*     */   }
/*     */   
/*     */   public void setCoreActivities(Set<Activity> $$0) {
/* 310 */     this.coreActivities = $$0;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   @VisibleForDebug
/*     */   public Set<Activity> getActiveActivities() {
/* 316 */     return this.activeActivities;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   @VisibleForDebug
/*     */   public List<BehaviorControl<? super E>> getRunningBehaviors() {
/* 322 */     ObjectArrayList<BehaviorControl<? super E>> objectArrayList = new ObjectArrayList();
/* 323 */     for (Map<Activity, Set<BehaviorControl<? super E>>> $$1 : this.availableBehaviorsByPriority.values()) {
/* 324 */       for (Set<BehaviorControl<? super E>> $$2 : $$1.values()) {
/* 325 */         for (BehaviorControl<? super E> $$3 : $$2) {
/* 326 */           if ($$3.getStatus() == Behavior.Status.RUNNING) {
/* 327 */             objectArrayList.add($$3);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 332 */     return (List<BehaviorControl<? super E>>)objectArrayList;
/*     */   }
/*     */   
/*     */   public void useDefaultActivity() {
/* 336 */     setActiveActivity(this.defaultActivity);
/*     */   }
/*     */   
/*     */   public Optional<Activity> getActiveNonCoreActivity() {
/* 340 */     for (Activity $$0 : this.activeActivities) {
/* 341 */       if (!this.coreActivities.contains($$0)) {
/* 342 */         return Optional.of($$0);
/*     */       }
/*     */     } 
/* 345 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActiveActivityIfPossible(Activity $$0) {
/* 354 */     if (activityRequirementsAreMet($$0)) {
/* 355 */       setActiveActivity($$0);
/*     */     } else {
/* 357 */       useDefaultActivity();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setActiveActivity(Activity $$0) {
/* 362 */     if (isActive($$0)) {
/*     */       return;
/*     */     }
/*     */     
/* 366 */     eraseMemoriesForOtherActivitesThan($$0);
/* 367 */     this.activeActivities.clear();
/* 368 */     this.activeActivities.addAll(this.coreActivities);
/* 369 */     this.activeActivities.add($$0);
/*     */   }
/*     */   
/*     */   private void eraseMemoriesForOtherActivitesThan(Activity $$0) {
/* 373 */     for (Activity $$1 : this.activeActivities) {
/* 374 */       if ($$1 != $$0) {
/* 375 */         Set<MemoryModuleType<?>> $$2 = this.activityMemoriesToEraseWhenStopped.get($$1);
/* 376 */         if ($$2 != null) {
/* 377 */           for (MemoryModuleType<?> $$3 : $$2) {
/* 378 */             eraseMemory($$3);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateActivityFromSchedule(long $$0, long $$1) {
/* 390 */     if ($$1 - this.lastScheduleUpdate > 20L) {
/* 391 */       this.lastScheduleUpdate = $$1;
/* 392 */       Activity $$2 = getSchedule().getActivityAt((int)($$0 % 24000L));
/* 393 */       if (!this.activeActivities.contains($$2)) {
/* 394 */         setActiveActivityIfPossible($$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActiveActivityToFirstValid(List<Activity> $$0) {
/* 403 */     for (Activity $$1 : $$0) {
/* 404 */       if (activityRequirementsAreMet($$1)) {
/* 405 */         setActiveActivity($$1);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDefaultActivity(Activity $$0) {
/* 412 */     this.defaultActivity = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addActivity(Activity $$0, int $$1, ImmutableList<? extends BehaviorControl<? super E>> $$2) {
/* 419 */     addActivity($$0, createPriorityPairs($$1, $$2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addActivityAndRemoveMemoryWhenStopped(Activity $$0, int $$1, ImmutableList<? extends BehaviorControl<? super E>> $$2, MemoryModuleType<?> $$3) {
/* 428 */     ImmutableSet immutableSet1 = ImmutableSet.of(
/* 429 */         Pair.of($$3, MemoryStatus.VALUE_PRESENT));
/*     */     
/* 431 */     ImmutableSet immutableSet2 = ImmutableSet.of($$3);
/* 432 */     addActivityAndRemoveMemoriesWhenStopped($$0, createPriorityPairs($$1, $$2), (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)immutableSet1, (Set<MemoryModuleType<?>>)immutableSet2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addActivity(Activity $$0, ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> $$1) {
/* 439 */     addActivityAndRemoveMemoriesWhenStopped($$0, $$1, (Set<Pair<MemoryModuleType<?>, MemoryStatus>>)ImmutableSet.of(), Sets.newHashSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addActivityWithConditions(Activity $$0, ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> $$1, Set<Pair<MemoryModuleType<?>, MemoryStatus>> $$2) {
/* 447 */     addActivityAndRemoveMemoriesWhenStopped($$0, $$1, $$2, Sets.newHashSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addActivityAndRemoveMemoriesWhenStopped(Activity $$0, ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> $$1, Set<Pair<MemoryModuleType<?>, MemoryStatus>> $$2, Set<MemoryModuleType<?>> $$3) {
/* 456 */     this.activityRequirements.put($$0, $$2);
/* 457 */     if (!$$3.isEmpty()) {
/* 458 */       this.activityMemoriesToEraseWhenStopped.put($$0, $$3);
/*     */     }
/* 460 */     for (UnmodifiableIterator<Pair<Integer, ? extends BehaviorControl<? super E>>> unmodifiableIterator = $$1.iterator(); unmodifiableIterator.hasNext(); ) { Pair<Integer, ? extends BehaviorControl<? super E>> $$4 = unmodifiableIterator.next();
/* 461 */       ((Set<BehaviorControl>)((Map<Activity, Set<BehaviorControl>>)this.availableBehaviorsByPriority
/* 462 */         .computeIfAbsent((Integer)$$4.getFirst(), $$0 -> Maps.newHashMap()))
/* 463 */         .computeIfAbsent($$0, $$0 -> Sets.newLinkedHashSet()))
/* 464 */         .add((BehaviorControl)$$4.getSecond()); }
/*     */   
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void removeAllBehaviors() {
/* 470 */     this.availableBehaviorsByPriority.clear();
/*     */   }
/*     */   
/*     */   public boolean isActive(Activity $$0) {
/* 474 */     return this.activeActivities.contains($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Brain<E> copyWithoutBehaviors() {
/* 479 */     Brain<E> $$0 = new Brain(this.memories.keySet(), this.sensors.keySet(), ImmutableList.of(), this.codec);
/* 480 */     for (Map.Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> $$1 : this.memories.entrySet()) {
/* 481 */       MemoryModuleType<?> $$2 = $$1.getKey();
/* 482 */       if (((Optional)$$1.getValue()).isPresent()) {
/* 483 */         $$0.memories.put($$2, $$1.getValue());
/*     */       }
/*     */     } 
/* 486 */     return $$0;
/*     */   }
/*     */   
/*     */   public void tick(ServerLevel $$0, E $$1) {
/* 490 */     forgetOutdatedMemories();
/* 491 */     tickSensors($$0, $$1);
/* 492 */     startEachNonRunningBehavior($$0, $$1);
/* 493 */     tickEachRunningBehavior($$0, $$1);
/*     */   }
/*     */   
/*     */   private void tickSensors(ServerLevel $$0, E $$1) {
/* 497 */     for (Sensor<? super E> $$2 : this.sensors.values()) {
/* 498 */       $$2.tick($$0, (LivingEntity)$$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void forgetOutdatedMemories() {
/* 503 */     for (Map.Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> $$0 : this.memories.entrySet()) {
/* 504 */       if (((Optional)$$0.getValue()).isPresent()) {
/* 505 */         ExpirableValue<?> $$1 = ((Optional<ExpirableValue>)$$0.getValue()).get();
/* 506 */         if ($$1.hasExpired()) {
/* 507 */           eraseMemory($$0.getKey());
/*     */         }
/* 509 */         $$1.tick();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stopAll(ServerLevel $$0, E $$1) {
/* 515 */     long $$2 = $$1.level().getGameTime();
/* 516 */     for (BehaviorControl<? super E> $$3 : getRunningBehaviors()) {
/* 517 */       $$3.doStop($$0, (LivingEntity)$$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startEachNonRunningBehavior(ServerLevel $$0, E $$1) {
/* 525 */     long $$2 = $$0.getGameTime();
/* 526 */     for (Map<Activity, Set<BehaviorControl<? super E>>> $$3 : this.availableBehaviorsByPriority.values()) {
/* 527 */       for (Map.Entry<Activity, Set<BehaviorControl<? super E>>> $$4 : $$3.entrySet()) {
/* 528 */         Activity $$5 = $$4.getKey();
/* 529 */         if (this.activeActivities.contains($$5)) {
/* 530 */           Set<BehaviorControl<? super E>> $$6 = $$4.getValue();
/* 531 */           for (BehaviorControl<? super E> $$7 : $$6) {
/* 532 */             if ($$7.getStatus() == Behavior.Status.STOPPED) {
/* 533 */               $$7.tryStart($$0, (LivingEntity)$$1, $$2);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void tickEachRunningBehavior(ServerLevel $$0, E $$1) {
/* 546 */     long $$2 = $$0.getGameTime();
/* 547 */     for (BehaviorControl<? super E> $$3 : getRunningBehaviors()) {
/* 548 */       $$3.tickOrStop($$0, (LivingEntity)$$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean activityRequirementsAreMet(Activity $$0) {
/* 553 */     if (!this.activityRequirements.containsKey($$0)) {
/* 554 */       return false;
/*     */     }
/*     */     
/* 557 */     for (Pair<MemoryModuleType<?>, MemoryStatus> $$1 : this.activityRequirements.get($$0)) {
/* 558 */       MemoryModuleType<?> $$2 = (MemoryModuleType)$$1.getFirst();
/* 559 */       MemoryStatus $$3 = (MemoryStatus)$$1.getSecond();
/* 560 */       if (!checkMemory($$2, $$3)) {
/* 561 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 565 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isEmptyCollection(Object $$0) {
/* 569 */     return ($$0 instanceof Collection && ((Collection)$$0).isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> createPriorityPairs(int $$0, ImmutableList<? extends BehaviorControl<? super E>> $$1) {
/* 576 */     int $$2 = $$0;
/* 577 */     ImmutableList.Builder<Pair<Integer, ? extends BehaviorControl<? super E>>> $$3 = ImmutableList.builder();
/* 578 */     for (UnmodifiableIterator<BehaviorControl<? super E>> unmodifiableIterator = $$1.iterator(); unmodifiableIterator.hasNext(); ) { BehaviorControl<? super E> $$4 = unmodifiableIterator.next();
/* 579 */       $$3.add(Pair.of(Integer.valueOf($$2++), $$4)); }
/*     */     
/* 581 */     return $$3.build();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\Brain.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */