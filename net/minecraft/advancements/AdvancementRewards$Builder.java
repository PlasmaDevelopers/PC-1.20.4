/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private int experience;
/*  73 */   private final ImmutableList.Builder<ResourceLocation> loot = ImmutableList.builder();
/*  74 */   private final ImmutableList.Builder<ResourceLocation> recipes = ImmutableList.builder();
/*  75 */   private Optional<ResourceLocation> function = Optional.empty();
/*     */   
/*     */   public static Builder experience(int $$0) {
/*  78 */     return (new Builder()).addExperience($$0);
/*     */   }
/*     */   
/*     */   public Builder addExperience(int $$0) {
/*  82 */     this.experience += $$0;
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   public static Builder loot(ResourceLocation $$0) {
/*  87 */     return (new Builder()).addLootTable($$0);
/*     */   }
/*     */   
/*     */   public Builder addLootTable(ResourceLocation $$0) {
/*  91 */     this.loot.add($$0);
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public static Builder recipe(ResourceLocation $$0) {
/*  96 */     return (new Builder()).addRecipe($$0);
/*     */   }
/*     */   
/*     */   public Builder addRecipe(ResourceLocation $$0) {
/* 100 */     this.recipes.add($$0);
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public static Builder function(ResourceLocation $$0) {
/* 105 */     return (new Builder()).runs($$0);
/*     */   }
/*     */   
/*     */   public Builder runs(ResourceLocation $$0) {
/* 109 */     this.function = Optional.of($$0);
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public AdvancementRewards build() {
/* 114 */     return new AdvancementRewards(this.experience, (List<ResourceLocation>)this.loot.build(), (List<ResourceLocation>)this.recipes.build(), this.function.map(net.minecraft.commands.CacheableFunction::new));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\AdvancementRewards$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */