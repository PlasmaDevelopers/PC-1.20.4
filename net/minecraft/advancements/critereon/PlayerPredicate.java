/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function6;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.advancements.CriterionProgress;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.PlayerAdvancements;
/*     */ import net.minecraft.server.ServerAdvancementManager;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.stats.ServerRecipeBook;
/*     */ import net.minecraft.stats.Stat;
/*     */ import net.minecraft.stats.StatType;
/*     */ import net.minecraft.stats.StatsCounter;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public final class PlayerPredicate extends Record implements EntitySubPredicate {
/*     */   private final MinMaxBounds.Ints level;
/*     */   private final Optional<GameType> gameType;
/*     */   private final List<StatMatcher<?>> stats;
/*     */   private final Object2BooleanMap<ResourceLocation> recipes;
/*     */   
/*  43 */   public PlayerPredicate(MinMaxBounds.Ints $$0, Optional<GameType> $$1, List<StatMatcher<?>> $$2, Object2BooleanMap<ResourceLocation> $$3, Map<ResourceLocation, AdvancementPredicate> $$4, Optional<EntityPredicate> $$5) { this.level = $$0; this.gameType = $$1; this.stats = $$2; this.recipes = $$3; this.advancements = $$4; this.lookingAt = $$5; } private final Map<ResourceLocation, AdvancementPredicate> advancements; private final Optional<EntityPredicate> lookingAt; public static final int LOOKING_AT_RANGE = 100; public static final MapCodec<PlayerPredicate> CODEC; public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/PlayerPredicate;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #43	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/PlayerPredicate;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #43	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/PlayerPredicate;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #43	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate;
/*  43 */     //   0	8	1	$$0	Ljava/lang/Object; } public MinMaxBounds.Ints level() { return this.level; } public Optional<GameType> gameType() { return this.gameType; } public List<StatMatcher<?>> stats() { return this.stats; } public Object2BooleanMap<ResourceLocation> recipes() { return this.recipes; } public Map<ResourceLocation, AdvancementPredicate> advancements() { return this.advancements; } public Optional<EntityPredicate> lookingAt() { return this.lookingAt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  53 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "level", MinMaxBounds.Ints.ANY).forGetter(PlayerPredicate::level), (App)GameType.CODEC.optionalFieldOf("gamemode").forGetter(PlayerPredicate::gameType), (App)ExtraCodecs.strictOptionalField(StatMatcher.CODEC.listOf(), "stats", List.of()).forGetter(PlayerPredicate::stats), (App)ExtraCodecs.strictOptionalField(ExtraCodecs.object2BooleanMap(ResourceLocation.CODEC), "recipes", Object2BooleanMaps.emptyMap()).forGetter(PlayerPredicate::recipes), (App)ExtraCodecs.strictOptionalField((Codec)Codec.unboundedMap(ResourceLocation.CODEC, AdvancementPredicate.CODEC), "advancements", Map.of()).forGetter(PlayerPredicate::advancements), (App)ExtraCodecs.strictOptionalField(EntityPredicate.CODEC, "looking_at").forGetter(PlayerPredicate::lookingAt)).apply((Applicative)$$0, PlayerPredicate::new));
/*     */   }
/*     */ 
/*     */   
/*     */   private static interface AdvancementPredicate
/*     */     extends Predicate<AdvancementProgress>
/*     */   {
/*     */     public static final Codec<AdvancementPredicate> CODEC;
/*     */     
/*     */     static {
/*  63 */       CODEC = Codec.either(PlayerPredicate.AdvancementDonePredicate.CODEC, PlayerPredicate.AdvancementCriterionsPredicate.CODEC).xmap($$0 -> (AdvancementPredicate)$$0.map((), ()), $$0 -> {
/*     */             if ($$0 instanceof PlayerPredicate.AdvancementDonePredicate) {
/*     */               PlayerPredicate.AdvancementDonePredicate $$1 = (PlayerPredicate.AdvancementDonePredicate)$$0;
/*     */               return Either.left($$1);
/*     */             } 
/*     */             if ($$0 instanceof PlayerPredicate.AdvancementCriterionsPredicate) {
/*     */               PlayerPredicate.AdvancementCriterionsPredicate $$2 = (PlayerPredicate.AdvancementCriterionsPredicate)$$0;
/*     */               return Either.right($$2);
/*     */             } 
/*     */             throw new UnsupportedOperationException();
/*     */           });
/*     */     } }
/*     */   private static final class AdvancementDonePredicate extends Record implements AdvancementPredicate { private final boolean state;
/*  76 */     AdvancementDonePredicate(boolean $$0) { this.state = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementDonePredicate;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #76	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementDonePredicate; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementDonePredicate;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #76	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementDonePredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementDonePredicate;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #76	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementDonePredicate;
/*  76 */       //   0	8	1	$$0	Ljava/lang/Object; } public boolean state() { return this.state; }
/*  77 */      public static final Codec<AdvancementDonePredicate> CODEC = Codec.BOOL.xmap(AdvancementDonePredicate::new, AdvancementDonePredicate::state);
/*     */ 
/*     */     
/*     */     public boolean test(AdvancementProgress $$0) {
/*  81 */       return ($$0.isDone() == this.state);
/*     */     } }
/*     */   private static final class AdvancementCriterionsPredicate extends Record implements AdvancementPredicate { private final Object2BooleanMap<String> criterions;
/*     */     
/*  85 */     AdvancementCriterionsPredicate(Object2BooleanMap<String> $$0) { this.criterions = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #85	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #85	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #85	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$AdvancementCriterionsPredicate;
/*  85 */       //   0	8	1	$$0	Ljava/lang/Object; } public Object2BooleanMap<String> criterions() { return this.criterions; }
/*  86 */      public static final Codec<AdvancementCriterionsPredicate> CODEC = ExtraCodecs.object2BooleanMap((Codec)Codec.STRING).xmap(AdvancementCriterionsPredicate::new, AdvancementCriterionsPredicate::criterions);
/*     */ 
/*     */     
/*     */     public boolean test(AdvancementProgress $$0) {
/*  90 */       for (ObjectIterator<Object2BooleanMap.Entry<String>> objectIterator = this.criterions.object2BooleanEntrySet().iterator(); objectIterator.hasNext(); ) { Object2BooleanMap.Entry<String> $$1 = objectIterator.next();
/*  91 */         CriterionProgress $$2 = $$0.getCriterion((String)$$1.getKey());
/*  92 */         if ($$2 == null || $$2.isDone() != $$1.getBooleanValue()) {
/*  93 */           return false;
/*     */         } }
/*     */       
/*  96 */       return true;
/*     */     } }
/*     */ 
/*     */   
/*     */   public boolean matches(Entity $$0, ServerLevel $$1, @Nullable Vec3 $$2) {
/*     */     ServerPlayer $$3;
/* 102 */     if ($$0 instanceof ServerPlayer) { $$3 = (ServerPlayer)$$0; }
/* 103 */     else { return false; }
/*     */ 
/*     */     
/* 106 */     if (!this.level.matches($$3.experienceLevel)) {
/* 107 */       return false;
/*     */     }
/*     */     
/* 110 */     if (this.gameType.isPresent() && this.gameType.get() != $$3.gameMode.getGameModeForPlayer()) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     ServerStatsCounter serverStatsCounter = $$3.getStats();
/* 115 */     for (StatMatcher<?> $$6 : this.stats) {
/* 116 */       if (!$$6.matches((StatsCounter)serverStatsCounter)) {
/* 117 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 121 */     ServerRecipeBook serverRecipeBook = $$3.getRecipeBook();
/* 122 */     for (ObjectIterator<Object2BooleanMap.Entry<ResourceLocation>> objectIterator = this.recipes.object2BooleanEntrySet().iterator(); objectIterator.hasNext(); ) { Object2BooleanMap.Entry<ResourceLocation> $$8 = objectIterator.next();
/* 123 */       if (serverRecipeBook.contains((ResourceLocation)$$8.getKey()) != $$8.getBooleanValue()) {
/* 124 */         return false;
/*     */       } }
/*     */ 
/*     */     
/* 128 */     if (!this.advancements.isEmpty()) {
/* 129 */       PlayerAdvancements $$9 = $$3.getAdvancements();
/* 130 */       ServerAdvancementManager $$10 = $$3.getServer().getAdvancements();
/*     */       
/* 132 */       for (Map.Entry<ResourceLocation, AdvancementPredicate> $$11 : this.advancements.entrySet()) {
/* 133 */         AdvancementHolder $$12 = $$10.get($$11.getKey());
/* 134 */         if ($$12 == null || !((AdvancementPredicate)$$11.getValue()).test($$9.getOrStartProgress($$12))) {
/* 135 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 140 */     if (this.lookingAt.isPresent()) {
/* 141 */       Vec3 $$13 = $$3.getEyePosition();
/* 142 */       Vec3 $$14 = $$3.getViewVector(1.0F);
/* 143 */       Vec3 $$15 = $$13.add($$14.x * 100.0D, $$14.y * 100.0D, $$14.z * 100.0D);
/* 144 */       EntityHitResult $$16 = ProjectileUtil.getEntityHitResult($$3.level(), (Entity)$$3, $$13, $$15, (new AABB($$13, $$15)).inflate(1.0D), $$0 -> !$$0.isSpectator(), 0.0F);
/* 145 */       if ($$16 == null || $$16.getType() != HitResult.Type.ENTITY) {
/* 146 */         return false;
/*     */       }
/* 148 */       Entity $$17 = $$16.getEntity();
/* 149 */       if (!((EntityPredicate)this.lookingAt.get()).matches($$3, $$17) || !$$3.hasLineOfSight($$17)) {
/* 150 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 155 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntitySubPredicate.Type type() {
/* 160 */     return EntitySubPredicate.Types.PLAYER;
/*     */   }
/*     */   private static final class StatMatcher<T> extends Record { private final StatType<T> type; private final Holder<T> value; private final MinMaxBounds.Ints range; private final Supplier<Stat<T>> stat;
/* 163 */     private StatMatcher(StatType<T> $$0, Holder<T> $$1, MinMaxBounds.Ints $$2, Supplier<Stat<T>> $$3) { this.type = $$0; this.value = $$1; this.range = $$2; this.stat = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #163	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #163	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #163	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 163 */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerPredicate$StatMatcher<TT;>; } public StatType<T> type() { return this.type; } public Holder<T> value() { return this.value; } public MinMaxBounds.Ints range() { return this.range; } public Supplier<Stat<T>> stat() { return this.stat; }
/* 164 */      public static final Codec<StatMatcher<?>> CODEC = BuiltInRegistries.STAT_TYPE.byNameCodec().dispatch(StatMatcher::type, StatMatcher::createTypedCodec);
/*     */     
/*     */     private static <T> Codec<StatMatcher<T>> createTypedCodec(StatType<T> $$0) {
/* 167 */       return RecordCodecBuilder.create($$1 -> $$1.group((App)$$0.getRegistry().holderByNameCodec().fieldOf("stat").forGetter(StatMatcher::value), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "value", MinMaxBounds.Ints.ANY).forGetter(StatMatcher::range)).apply((Applicative)$$1, ()));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public StatMatcher(StatType<T> $$0, Holder<T> $$1, MinMaxBounds.Ints $$2) {
/* 174 */       this($$0, $$1, $$2, (Supplier<Stat<T>>)Suppliers.memoize(() -> $$0.get($$1.value())));
/*     */     }
/*     */     
/*     */     public boolean matches(StatsCounter $$0) {
/* 178 */       return this.range.matches($$0.getValue(this.stat.get()));
/*     */     } }
/*     */   public static class Builder { private MinMaxBounds.Ints level; private Optional<GameType> gameType; private final ImmutableList.Builder<PlayerPredicate.StatMatcher<?>> stats; private final Object2BooleanMap<ResourceLocation> recipes; private final Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> advancements; private Optional<EntityPredicate> lookingAt;
/*     */     
/*     */     public Builder() {
/* 183 */       this.level = MinMaxBounds.Ints.ANY;
/* 184 */       this.gameType = Optional.empty();
/* 185 */       this.stats = ImmutableList.builder();
/* 186 */       this.recipes = (Object2BooleanMap<ResourceLocation>)new Object2BooleanOpenHashMap();
/* 187 */       this.advancements = Maps.newHashMap();
/* 188 */       this.lookingAt = Optional.empty();
/*     */     }
/*     */     public static Builder player() {
/* 191 */       return new Builder();
/*     */     }
/*     */     
/*     */     public Builder setLevel(MinMaxBounds.Ints $$0) {
/* 195 */       this.level = $$0;
/* 196 */       return this;
/*     */     }
/*     */     
/*     */     public <T> Builder addStat(StatType<T> $$0, Holder.Reference<T> $$1, MinMaxBounds.Ints $$2) {
/* 200 */       this.stats.add(new PlayerPredicate.StatMatcher<>($$0, (Holder<T>)$$1, $$2));
/* 201 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addRecipe(ResourceLocation $$0, boolean $$1) {
/* 205 */       this.recipes.put($$0, $$1);
/* 206 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setGameType(GameType $$0) {
/* 210 */       this.gameType = Optional.of($$0);
/* 211 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setLookingAt(EntityPredicate.Builder $$0) {
/* 215 */       this.lookingAt = Optional.of($$0.build());
/* 216 */       return this;
/*     */     }
/*     */     
/*     */     public Builder checkAdvancementDone(ResourceLocation $$0, boolean $$1) {
/* 220 */       this.advancements.put($$0, new PlayerPredicate.AdvancementDonePredicate($$1));
/* 221 */       return this;
/*     */     }
/*     */     
/*     */     public Builder checkAdvancementCriterions(ResourceLocation $$0, Map<String, Boolean> $$1) {
/* 225 */       this.advancements.put($$0, new PlayerPredicate.AdvancementCriterionsPredicate((Object2BooleanMap<String>)new Object2BooleanOpenHashMap($$1)));
/* 226 */       return this;
/*     */     }
/*     */     
/*     */     public PlayerPredicate build() {
/* 230 */       return new PlayerPredicate(this.level, this.gameType, (List<PlayerPredicate.StatMatcher<?>>)this.stats.build(), this.recipes, this.advancements, this.lookingAt);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\PlayerPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */