/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 107 */   private Optional<ResourceLocation> parent = Optional.empty();
/* 108 */   private Optional<DisplayInfo> display = Optional.empty();
/* 109 */   private AdvancementRewards rewards = AdvancementRewards.EMPTY;
/* 110 */   private final ImmutableMap.Builder<String, Criterion<?>> criteria = ImmutableMap.builder();
/* 111 */   private Optional<AdvancementRequirements> requirements = Optional.empty();
/* 112 */   private AdvancementRequirements.Strategy requirementsStrategy = AdvancementRequirements.Strategy.AND;
/*     */   private boolean sendsTelemetryEvent;
/*     */   
/*     */   public static Builder advancement() {
/* 116 */     return (new Builder()).sendsTelemetryEvent();
/*     */   }
/*     */   
/*     */   public static Builder recipeAdvancement() {
/* 120 */     return new Builder();
/*     */   }
/*     */   
/*     */   public Builder parent(AdvancementHolder $$0) {
/* 124 */     this.parent = Optional.of($$0.id());
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public Builder parent(ResourceLocation $$0) {
/* 131 */     this.parent = Optional.of($$0);
/* 132 */     return this;
/*     */   }
/*     */   
/*     */   public Builder display(ItemStack $$0, Component $$1, Component $$2, @Nullable ResourceLocation $$3, AdvancementType $$4, boolean $$5, boolean $$6, boolean $$7) {
/* 136 */     return display(new DisplayInfo($$0, $$1, $$2, Optional.ofNullable($$3), $$4, $$5, $$6, $$7));
/*     */   }
/*     */   
/*     */   public Builder display(ItemLike $$0, Component $$1, Component $$2, @Nullable ResourceLocation $$3, AdvancementType $$4, boolean $$5, boolean $$6, boolean $$7) {
/* 140 */     return display(new DisplayInfo(new ItemStack((ItemLike)$$0.asItem()), $$1, $$2, Optional.ofNullable($$3), $$4, $$5, $$6, $$7));
/*     */   }
/*     */   
/*     */   public Builder display(DisplayInfo $$0) {
/* 144 */     this.display = Optional.of($$0);
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public Builder rewards(AdvancementRewards.Builder $$0) {
/* 149 */     return rewards($$0.build());
/*     */   }
/*     */   
/*     */   public Builder rewards(AdvancementRewards $$0) {
/* 153 */     this.rewards = $$0;
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public Builder addCriterion(String $$0, Criterion<?> $$1) {
/* 158 */     this.criteria.put($$0, $$1);
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   public Builder requirements(AdvancementRequirements.Strategy $$0) {
/* 163 */     this.requirementsStrategy = $$0;
/* 164 */     return this;
/*     */   }
/*     */   
/*     */   public Builder requirements(AdvancementRequirements $$0) {
/* 168 */     this.requirements = Optional.of($$0);
/* 169 */     return this;
/*     */   }
/*     */   
/*     */   public Builder sendsTelemetryEvent() {
/* 173 */     this.sendsTelemetryEvent = true;
/* 174 */     return this;
/*     */   }
/*     */   
/*     */   public AdvancementHolder build(ResourceLocation $$0) {
/* 178 */     ImmutableMap immutableMap = this.criteria.buildOrThrow();
/* 179 */     AdvancementRequirements $$2 = this.requirements.orElseGet(() -> this.requirementsStrategy.create($$0.keySet()));
/* 180 */     return new AdvancementHolder($$0, new Advancement(this.parent, this.display, this.rewards, (Map<String, Criterion<?>>)immutableMap, $$2, this.sendsTelemetryEvent));
/*     */   }
/*     */   
/*     */   public AdvancementHolder save(Consumer<AdvancementHolder> $$0, String $$1) {
/* 184 */     AdvancementHolder $$2 = build(new ResourceLocation($$1));
/* 185 */     $$0.accept($$2);
/* 186 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\Advancement$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */