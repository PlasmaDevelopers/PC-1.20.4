/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Maps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.stats.StatType;
/*     */ import net.minecraft.world.level.GameType;
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
/* 183 */   private MinMaxBounds.Ints level = MinMaxBounds.Ints.ANY;
/* 184 */   private Optional<GameType> gameType = Optional.empty();
/* 185 */   private final ImmutableList.Builder<PlayerPredicate.StatMatcher<?>> stats = ImmutableList.builder();
/* 186 */   private final Object2BooleanMap<ResourceLocation> recipes = (Object2BooleanMap<ResourceLocation>)new Object2BooleanOpenHashMap();
/* 187 */   private final Map<ResourceLocation, PlayerPredicate.AdvancementPredicate> advancements = Maps.newHashMap();
/* 188 */   private Optional<EntityPredicate> lookingAt = Optional.empty();
/*     */   
/*     */   public static Builder player() {
/* 191 */     return new Builder();
/*     */   }
/*     */   
/*     */   public Builder setLevel(MinMaxBounds.Ints $$0) {
/* 195 */     this.level = $$0;
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   public <T> Builder addStat(StatType<T> $$0, Holder.Reference<T> $$1, MinMaxBounds.Ints $$2) {
/* 200 */     this.stats.add(new PlayerPredicate.StatMatcher<>($$0, (Holder<T>)$$1, $$2));
/* 201 */     return this;
/*     */   }
/*     */   
/*     */   public Builder addRecipe(ResourceLocation $$0, boolean $$1) {
/* 205 */     this.recipes.put($$0, $$1);
/* 206 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setGameType(GameType $$0) {
/* 210 */     this.gameType = Optional.of($$0);
/* 211 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setLookingAt(EntityPredicate.Builder $$0) {
/* 215 */     this.lookingAt = Optional.of($$0.build());
/* 216 */     return this;
/*     */   }
/*     */   
/*     */   public Builder checkAdvancementDone(ResourceLocation $$0, boolean $$1) {
/* 220 */     this.advancements.put($$0, new PlayerPredicate.AdvancementDonePredicate($$1));
/* 221 */     return this;
/*     */   }
/*     */   
/*     */   public Builder checkAdvancementCriterions(ResourceLocation $$0, Map<String, Boolean> $$1) {
/* 225 */     this.advancements.put($$0, new PlayerPredicate.AdvancementCriterionsPredicate((Object2BooleanMap<String>)new Object2BooleanOpenHashMap($$1)));
/* 226 */     return this;
/*     */   }
/*     */   
/*     */   public PlayerPredicate build() {
/* 230 */     return new PlayerPredicate(this.level, this.gameType, (List<PlayerPredicate.StatMatcher<?>>)this.stats.build(), this.recipes, this.advancements, this.lookingAt);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\PlayerPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */