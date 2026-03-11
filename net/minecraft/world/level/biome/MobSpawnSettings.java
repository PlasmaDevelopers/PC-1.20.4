/*     */ package net.minecraft.world.level.biome;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Keyable;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.util.random.Weight;
/*     */ import net.minecraft.util.random.WeightedEntry;
/*     */ import net.minecraft.util.random.WeightedRandomList;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ 
/*     */ public class MobSpawnSettings {
/*  30 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final float DEFAULT_CREATURE_SPAWN_PROBABILITY = 0.1F;
/*  32 */   public static final WeightedRandomList<SpawnerData> EMPTY_MOB_LIST = WeightedRandomList.create();
/*  33 */   public static final MobSpawnSettings EMPTY = (new Builder()).build(); public static final MapCodec<MobSpawnSettings> CODEC; private final float creatureGenerationProbability; private final Map<MobCategory, WeightedRandomList<SpawnerData>> spawners; private final Map<EntityType<?>, MobSpawnCost> mobSpawnCosts;
/*     */   static {
/*  35 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> {
/*     */           Objects.requireNonNull(LOGGER);
/*     */           return (Function)$$0.group((App)Codec.floatRange(0.0F, 0.9999999F).optionalFieldOf("creature_spawn_probability", Float.valueOf(0.1F)).forGetter(()), (App)Codec.simpleMap(MobCategory.CODEC, WeightedRandomList.codec(SpawnerData.CODEC).promotePartial(Util.prefix("Spawn data: ", LOGGER::error)), StringRepresentable.keys((StringRepresentable[])MobCategory.values())).fieldOf("spawners").forGetter(()), (App)Codec.simpleMap(BuiltInRegistries.ENTITY_TYPE.byNameCodec(), MobSpawnCost.CODEC, (Keyable)BuiltInRegistries.ENTITY_TYPE).fieldOf("spawn_costs").forGetter(())).apply((Applicative)$$0, MobSpawnSettings::new);
/*     */         });
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
/*     */ 
/*     */ 
/*     */   
/*     */   MobSpawnSettings(float $$0, Map<MobCategory, WeightedRandomList<SpawnerData>> $$1, Map<EntityType<?>, MobSpawnCost> $$2) {
/*  54 */     this.creatureGenerationProbability = $$0;
/*  55 */     this.spawners = (Map<MobCategory, WeightedRandomList<SpawnerData>>)ImmutableMap.copyOf($$1);
/*  56 */     this.mobSpawnCosts = (Map<EntityType<?>, MobSpawnCost>)ImmutableMap.copyOf($$2);
/*     */   }
/*     */   
/*     */   public WeightedRandomList<SpawnerData> getMobs(MobCategory $$0) {
/*  60 */     return this.spawners.getOrDefault($$0, EMPTY_MOB_LIST);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MobSpawnCost getMobSpawnCost(EntityType<?> $$0) {
/*  65 */     return this.mobSpawnCosts.get($$0);
/*     */   }
/*     */   
/*     */   public float getCreatureProbability() {
/*  69 */     return this.creatureGenerationProbability;
/*     */   }
/*     */   public static class SpawnerData extends WeightedEntry.IntrusiveBase { public static final Codec<SpawnerData> CODEC; public final EntityType<?> type; public final int minCount; public final int maxCount;
/*     */     static {
/*  73 */       CODEC = ExtraCodecs.validate(RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("type").forGetter(()), (App)Weight.CODEC.fieldOf("weight").forGetter(WeightedEntry.IntrusiveBase::getWeight), (App)ExtraCodecs.POSITIVE_INT.fieldOf("minCount").forGetter(()), (App)ExtraCodecs.POSITIVE_INT.fieldOf("maxCount").forGetter(())).apply((Applicative)$$0, SpawnerData::new)), $$0 -> ($$0.minCount > $$0.maxCount) ? DataResult.error(()) : DataResult.success($$0));
/*     */     }
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
/*     */     public SpawnerData(EntityType<?> $$0, int $$1, int $$2, int $$3) {
/*  90 */       this($$0, Weight.of($$1), $$2, $$3);
/*     */     }
/*     */     
/*     */     public SpawnerData(EntityType<?> $$0, Weight $$1, int $$2, int $$3) {
/*  94 */       super($$1);
/*  95 */       this.type = ($$0.getCategory() == MobCategory.MISC) ? EntityType.PIG : $$0;
/*  96 */       this.minCount = $$2;
/*  97 */       this.maxCount = $$3;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 102 */       return "" + EntityType.getKey(this.type) + "*(" + EntityType.getKey(this.type) + "-" + this.minCount + "):" + this.maxCount;
/*     */     } }
/*     */   public static final class MobSpawnCost extends Record { private final double energyBudget; private final double charge; public static final Codec<MobSpawnCost> CODEC;
/*     */     
/* 106 */     public MobSpawnCost(double $$0, double $$1) { this.energyBudget = $$0; this.charge = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #106	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 106 */       //   0	7	0	this	Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost; } public double energyBudget() { return this.energyBudget; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #106	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #106	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/biome/MobSpawnSettings$MobSpawnCost;
/* 106 */       //   0	8	1	$$0	Ljava/lang/Object; } public double charge() { return this.charge; } static {
/* 107 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.DOUBLE.fieldOf("energy_budget").forGetter(()), (App)Codec.DOUBLE.fieldOf("charge").forGetter(())).apply((Applicative)$$0, MobSpawnCost::new));
/*     */     } }
/*     */   public static class Builder { private final Map<MobCategory, List<MobSpawnSettings.SpawnerData>> spawners;
/*     */     private final Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> mobSpawnCosts;
/*     */     private float creatureGenerationProbability;
/*     */     
/*     */     public Builder() {
/* 114 */       this.spawners = (Map<MobCategory, List<MobSpawnSettings.SpawnerData>>)Stream.<MobCategory>of(MobCategory.values()).collect(ImmutableMap.toImmutableMap($$0 -> $$0, $$0 -> Lists.newArrayList()));
/* 115 */       this.mobSpawnCosts = Maps.newLinkedHashMap();
/* 116 */       this.creatureGenerationProbability = 0.1F;
/*     */     }
/*     */     public Builder addSpawn(MobCategory $$0, MobSpawnSettings.SpawnerData $$1) {
/* 119 */       ((List<MobSpawnSettings.SpawnerData>)this.spawners.get($$0)).add($$1);
/* 120 */       return this;
/*     */     }
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
/*     */ 
/*     */     
/*     */     public Builder addMobCharge(EntityType<?> $$0, double $$1, double $$2) {
/* 146 */       this.mobSpawnCosts.put($$0, new MobSpawnSettings.MobSpawnCost($$2, $$1));
/* 147 */       return this;
/*     */     }
/*     */     
/*     */     public Builder creatureGenerationProbability(float $$0) {
/* 151 */       this.creatureGenerationProbability = $$0;
/* 152 */       return this;
/*     */     }
/*     */     
/*     */     public MobSpawnSettings build() {
/* 156 */       return new MobSpawnSettings(this.creatureGenerationProbability, (Map<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>>)this.spawners
/*     */           
/* 158 */           .entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, $$0 -> WeightedRandomList.create((List)$$0.getValue()))), 
/* 159 */           (Map<EntityType<?>, MobSpawnSettings.MobSpawnCost>)ImmutableMap.copyOf(this.mobSpawnCosts));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\MobSpawnSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */