/*     */ package net.minecraft.world.level.storage.loot;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   implements FunctionUserBuilder<LootPool.Builder>, ConditionUserBuilder<LootPool.Builder>
/*     */ {
/* 122 */   private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();
/* 123 */   private final ImmutableList.Builder<LootItemCondition> conditions = ImmutableList.builder();
/* 124 */   private final ImmutableList.Builder<LootItemFunction> functions = ImmutableList.builder();
/* 125 */   private NumberProvider rolls = (NumberProvider)ConstantValue.exactly(1.0F);
/* 126 */   private NumberProvider bonusRolls = (NumberProvider)ConstantValue.exactly(0.0F);
/*     */   
/*     */   public Builder setRolls(NumberProvider $$0) {
/* 129 */     this.rolls = $$0;
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder unwrap() {
/* 135 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setBonusRolls(NumberProvider $$0) {
/* 139 */     this.bonusRolls = $$0;
/* 140 */     return this;
/*     */   }
/*     */   
/*     */   public Builder add(LootPoolEntryContainer.Builder<?> $$0) {
/* 144 */     this.entries.add($$0.build());
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder when(LootItemCondition.Builder $$0) {
/* 150 */     this.conditions.add($$0.build());
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder apply(LootItemFunction.Builder $$0) {
/* 156 */     this.functions.add($$0.build());
/* 157 */     return this;
/*     */   }
/*     */   
/*     */   public LootPool build() {
/* 161 */     return new LootPool((List<LootPoolEntryContainer>)this.entries.build(), (List<LootItemCondition>)this.conditions.build(), (List<LootItemFunction>)this.functions.build(), this.rolls, this.bonusRolls);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootPool$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */