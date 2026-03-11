/*     */ package net.minecraft.world.level.storage.loot.entries;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.Products;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*     */ import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ 
/*     */ public abstract class LootPoolSingletonContainer
/*     */   extends LootPoolEntryContainer {
/*     */   public static final int DEFAULT_WEIGHT = 1;
/*     */   public static final int DEFAULT_QUALITY = 0;
/*     */   protected final int weight;
/*     */   protected final int quality;
/*     */   protected final List<LootItemFunction> functions;
/*     */   final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;
/*     */   private final LootPoolEntry entry;
/*     */   
/*     */   protected LootPoolSingletonContainer(int $$0, int $$1, List<LootItemCondition> $$2, List<LootItemFunction> $$3) {
/*  32 */     super($$2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     this.entry = new EntryBase()
/*     */       {
/*     */         public void createItemStack(Consumer<ItemStack> $$0, LootContext $$1) {
/*  67 */           LootPoolSingletonContainer.this.createItemStack(LootItemFunction.decorate(LootPoolSingletonContainer.this.compositeFunction, $$0, $$1), $$1);
/*     */         }
/*     */       };
/*     */     this.weight = $$0;
/*     */     this.quality = $$1;
/*     */     this.functions = $$3;
/*     */     this.compositeFunction = LootItemFunctions.compose($$3); } protected static <T extends LootPoolSingletonContainer> Products.P4<RecordCodecBuilder.Mu<T>, Integer, Integer, List<LootItemCondition>, List<LootItemFunction>> singletonFields(RecordCodecBuilder.Instance<T> $$0) {
/*     */     return $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "weight", Integer.valueOf(1)).forGetter($$0 -> Integer.valueOf($$0.weight)), (App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "quality", Integer.valueOf(0)).forGetter($$0 -> Integer.valueOf($$0.quality))).and(commonFields((RecordCodecBuilder.Instance)$$0).t1()).and((App)ExtraCodecs.strictOptionalField(LootItemFunctions.CODEC.listOf(), "functions", List.of()).forGetter($$0 -> $$0.functions));
/*  75 */   } public boolean expand(LootContext $$0, Consumer<LootPoolEntry> $$1) { if (canRun($$0)) {
/*  76 */       $$1.accept(this.entry);
/*  77 */       return true;
/*     */     } 
/*     */     
/*  80 */     return false; } public void validate(ValidationContext $$0) { super.validate($$0); for (int $$1 = 0; $$1 < this.functions.size(); $$1++)
/*     */       ((LootItemFunction)this.functions.get($$1)).validate($$0.forChild(".functions[" + $$1 + "]"));  } protected abstract class EntryBase implements LootPoolEntry {
/*     */     public int getWeight(float $$0) { return Math.max(Mth.floor(LootPoolSingletonContainer.this.weight + LootPoolSingletonContainer.this.quality * $$0), 0); }
/*     */   } public static abstract class Builder<T extends Builder<T>> extends LootPoolEntryContainer.Builder<T> implements FunctionUserBuilder<T> {
/*  84 */     protected int weight = 1;
/*  85 */     protected int quality = 0;
/*     */     
/*  87 */     private final ImmutableList.Builder<LootItemFunction> functions = ImmutableList.builder();
/*     */ 
/*     */     
/*     */     public T apply(LootItemFunction.Builder $$0) {
/*  91 */       this.functions.add($$0.build());
/*  92 */       return getThis();
/*     */     }
/*     */     
/*     */     protected List<LootItemFunction> getFunctions() {
/*  96 */       return (List<LootItemFunction>)this.functions.build();
/*     */     }
/*     */     
/*     */     public T setWeight(int $$0) {
/* 100 */       this.weight = $$0;
/* 101 */       return getThis();
/*     */     }
/*     */     
/*     */     public T setQuality(int $$0) {
/* 105 */       this.quality = $$0;
/* 106 */       return getThis();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DummyBuilder
/*     */     extends Builder<DummyBuilder>
/*     */   {
/*     */     private final LootPoolSingletonContainer.EntryConstructor constructor;
/*     */ 
/*     */     
/*     */     public DummyBuilder(LootPoolSingletonContainer.EntryConstructor $$0) {
/* 119 */       this.constructor = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected DummyBuilder getThis() {
/* 124 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootPoolEntryContainer build() {
/* 129 */       return this.constructor.build(this.weight, this.quality, getConditions(), getFunctions());
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder<?> simpleBuilder(EntryConstructor $$0) {
/* 134 */     return new DummyBuilder($$0);
/*     */   }
/*     */   
/*     */   protected abstract void createItemStack(Consumer<ItemStack> paramConsumer, LootContext paramLootContext);
/*     */   
/*     */   @FunctionalInterface
/*     */   protected static interface EntryConstructor {
/*     */     LootPoolSingletonContainer build(int param1Int1, int param1Int2, List<LootItemCondition> param1List, List<LootItemFunction> param1List1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\LootPoolSingletonContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */