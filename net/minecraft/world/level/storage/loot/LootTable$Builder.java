/*     */ package net.minecraft.world.level.storage.loot;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   implements FunctionUserBuilder<LootTable.Builder>
/*     */ {
/* 204 */   private final ImmutableList.Builder<LootPool> pools = ImmutableList.builder();
/*     */   
/* 206 */   private final ImmutableList.Builder<LootItemFunction> functions = ImmutableList.builder();
/*     */   
/* 208 */   private LootContextParamSet paramSet = LootTable.DEFAULT_PARAM_SET;
/* 209 */   private Optional<ResourceLocation> randomSequence = Optional.empty();
/*     */   
/*     */   public Builder withPool(LootPool.Builder $$0) {
/* 212 */     this.pools.add($$0.build());
/* 213 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setParamSet(LootContextParamSet $$0) {
/* 217 */     this.paramSet = $$0;
/* 218 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setRandomSequence(ResourceLocation $$0) {
/* 222 */     this.randomSequence = Optional.of($$0);
/* 223 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder apply(LootItemFunction.Builder $$0) {
/* 228 */     this.functions.add($$0.build());
/* 229 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder unwrap() {
/* 234 */     return this;
/*     */   }
/*     */   
/*     */   public LootTable build() {
/* 238 */     return new LootTable(this.paramSet, this.randomSequence, (List<LootPool>)this.pools.build(), (List<LootItemFunction>)this.functions.build());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootTable$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */