/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.util.random.WeightedRandomList;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobCategory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/*     */   private final Map<MobCategory, List<MobSpawnSettings.SpawnerData>> spawners;
/*     */   private final Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> mobSpawnCosts;
/*     */   private float creatureGenerationProbability;
/*     */   
/*     */   public Builder() {
/* 114 */     this.spawners = (Map<MobCategory, List<MobSpawnSettings.SpawnerData>>)Stream.<MobCategory>of(MobCategory.values()).collect(ImmutableMap.toImmutableMap($$0 -> $$0, $$0 -> Lists.newArrayList()));
/* 115 */     this.mobSpawnCosts = Maps.newLinkedHashMap();
/* 116 */     this.creatureGenerationProbability = 0.1F;
/*     */   }
/*     */   public Builder addSpawn(MobCategory $$0, MobSpawnSettings.SpawnerData $$1) {
/* 119 */     ((List<MobSpawnSettings.SpawnerData>)this.spawners.get($$0)).add($$1);
/* 120 */     return this;
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
/*     */   public Builder addMobCharge(EntityType<?> $$0, double $$1, double $$2) {
/* 146 */     this.mobSpawnCosts.put($$0, new MobSpawnSettings.MobSpawnCost($$2, $$1));
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public Builder creatureGenerationProbability(float $$0) {
/* 151 */     this.creatureGenerationProbability = $$0;
/* 152 */     return this;
/*     */   }
/*     */   
/*     */   public MobSpawnSettings build() {
/* 156 */     return new MobSpawnSettings(this.creatureGenerationProbability, (Map<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>>)this.spawners
/*     */         
/* 158 */         .entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, $$0 -> WeightedRandomList.create((List)$$0.getValue()))), 
/* 159 */         (Map<EntityType<?>, MobSpawnSettings.MobSpawnCost>)ImmutableMap.copyOf(this.mobSpawnCosts));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\MobSpawnSettings$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */