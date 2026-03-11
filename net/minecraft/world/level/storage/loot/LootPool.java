/*     */ package net.minecraft.world.level.storage.loot;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function5;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
/*     */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*     */ import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
/*     */ import net.minecraft.world.level.storage.loot.predicates.ConditionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ 
/*     */ public class LootPool {
/*     */   static {
/*  31 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)LootPoolEntries.CODEC.listOf().fieldOf("entries").forGetter(()), (App)ExtraCodecs.strictOptionalField(LootItemConditions.CODEC.listOf(), "conditions", List.of()).forGetter(()), (App)ExtraCodecs.strictOptionalField(LootItemFunctions.CODEC.listOf(), "functions", List.of()).forGetter(()), (App)NumberProviders.CODEC.fieldOf("rolls").forGetter(()), (App)NumberProviders.CODEC.fieldOf("bonus_rolls").orElse(ConstantValue.exactly(0.0F)).forGetter(())).apply((Applicative)$$0, LootPool::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<LootPool> CODEC;
/*     */   
/*     */   private final List<LootPoolEntryContainer> entries;
/*     */   
/*     */   private final List<LootItemCondition> conditions;
/*     */   
/*     */   private final Predicate<LootContext> compositeCondition;
/*     */   private final List<LootItemFunction> functions;
/*     */   private final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;
/*     */   private final NumberProvider rolls;
/*     */   private final NumberProvider bonusRolls;
/*     */   
/*     */   LootPool(List<LootPoolEntryContainer> $$0, List<LootItemCondition> $$1, List<LootItemFunction> $$2, NumberProvider $$3, NumberProvider $$4) {
/*  48 */     this.entries = $$0;
/*  49 */     this.conditions = $$1;
/*  50 */     this.compositeCondition = LootItemConditions.andConditions($$1);
/*  51 */     this.functions = $$2;
/*  52 */     this.compositeFunction = LootItemFunctions.compose($$2);
/*  53 */     this.rolls = $$3;
/*  54 */     this.bonusRolls = $$4;
/*     */   }
/*     */   
/*     */   private void addRandomItem(Consumer<ItemStack> $$0, LootContext $$1) {
/*  58 */     RandomSource $$2 = $$1.getRandom();
/*  59 */     List<LootPoolEntry> $$3 = Lists.newArrayList();
/*  60 */     MutableInt $$4 = new MutableInt();
/*  61 */     for (LootPoolEntryContainer $$5 : this.entries) {
/*  62 */       $$5.expand($$1, $$3 -> {
/*     */             int $$4 = $$3.getWeight($$0.getLuck());
/*     */             
/*     */             if ($$4 > 0) {
/*     */               $$1.add($$3);
/*     */               $$2.add($$4);
/*     */             } 
/*     */           });
/*     */     } 
/*  71 */     int $$6 = $$3.size();
/*  72 */     if ($$4.intValue() == 0 || $$6 == 0) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     if ($$6 == 1) {
/*  77 */       ((LootPoolEntry)$$3.get(0)).createItemStack($$0, $$1);
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     int $$7 = $$2.nextInt($$4.intValue());
/*  82 */     for (LootPoolEntry $$8 : $$3) {
/*  83 */       $$7 -= $$8.getWeight($$1.getLuck());
/*  84 */       if ($$7 < 0) {
/*  85 */         $$8.createItemStack($$0, $$1);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addRandomItems(Consumer<ItemStack> $$0, LootContext $$1) {
/*  92 */     if (!this.compositeCondition.test($$1)) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     Consumer<ItemStack> $$2 = LootItemFunction.decorate(this.compositeFunction, $$0, $$1);
/*     */     
/*  98 */     int $$3 = this.rolls.getInt($$1) + Mth.floor(this.bonusRolls.getFloat($$1) * $$1.getLuck());
/*  99 */     for (int $$4 = 0; $$4 < $$3; $$4++) {
/* 100 */       addRandomItem($$2, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void validate(ValidationContext $$0) {
/* 105 */     for (int $$1 = 0; $$1 < this.conditions.size(); $$1++) {
/* 106 */       ((LootItemCondition)this.conditions.get($$1)).validate($$0.forChild(".condition[" + $$1 + "]"));
/*     */     }
/*     */     
/* 109 */     for (int $$2 = 0; $$2 < this.functions.size(); $$2++) {
/* 110 */       ((LootItemFunction)this.functions.get($$2)).validate($$0.forChild(".functions[" + $$2 + "]"));
/*     */     }
/*     */     
/* 113 */     for (int $$3 = 0; $$3 < this.entries.size(); $$3++) {
/* 114 */       ((LootPoolEntryContainer)this.entries.get($$3)).validate($$0.forChild(".entries[" + $$3 + "]"));
/*     */     }
/*     */     
/* 117 */     this.rolls.validate($$0.forChild(".rolls"));
/* 118 */     this.bonusRolls.validate($$0.forChild(".bonusRolls"));
/*     */   }
/*     */   
/*     */   public static class Builder implements FunctionUserBuilder<Builder>, ConditionUserBuilder<Builder> {
/* 122 */     private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();
/* 123 */     private final ImmutableList.Builder<LootItemCondition> conditions = ImmutableList.builder();
/* 124 */     private final ImmutableList.Builder<LootItemFunction> functions = ImmutableList.builder();
/* 125 */     private NumberProvider rolls = (NumberProvider)ConstantValue.exactly(1.0F);
/* 126 */     private NumberProvider bonusRolls = (NumberProvider)ConstantValue.exactly(0.0F);
/*     */     
/*     */     public Builder setRolls(NumberProvider $$0) {
/* 129 */       this.rolls = $$0;
/* 130 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder unwrap() {
/* 135 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setBonusRolls(NumberProvider $$0) {
/* 139 */       this.bonusRolls = $$0;
/* 140 */       return this;
/*     */     }
/*     */     
/*     */     public Builder add(LootPoolEntryContainer.Builder<?> $$0) {
/* 144 */       this.entries.add($$0.build());
/* 145 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder when(LootItemCondition.Builder $$0) {
/* 150 */       this.conditions.add($$0.build());
/* 151 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder apply(LootItemFunction.Builder $$0) {
/* 156 */       this.functions.add($$0.build());
/* 157 */       return this;
/*     */     }
/*     */     
/*     */     public LootPool build() {
/* 161 */       return new LootPool((List<LootPoolEntryContainer>)this.entries.build(), (List<LootItemCondition>)this.conditions.build(), (List<LootItemFunction>)this.functions.build(), this.rolls, this.bonusRolls);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder lootPool() {
/* 166 */     return new Builder();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootPool.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */