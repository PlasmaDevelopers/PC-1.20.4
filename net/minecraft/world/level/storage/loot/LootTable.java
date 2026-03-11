/*     */ package net.minecraft.world.level.storage.loot;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class LootTable {
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  33 */   public static final LootTable EMPTY = new LootTable(LootContextParamSets.EMPTY, Optional.empty(), List.of(), List.of());
/*  34 */   public static final LootContextParamSet DEFAULT_PARAM_SET = LootContextParamSets.ALL_PARAMS; public static final Codec<LootTable> CODEC;
/*     */   static {
/*  36 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)LootContextParamSets.CODEC.optionalFieldOf("type", DEFAULT_PARAM_SET).forGetter(()), (App)ExtraCodecs.strictOptionalField(ResourceLocation.CODEC, "random_sequence").forGetter(()), (App)ExtraCodecs.strictOptionalField(LootPool.CODEC.listOf(), "pools", List.of()).forGetter(()), (App)ExtraCodecs.strictOptionalField(LootItemFunctions.CODEC.listOf(), "functions", List.of()).forGetter(())).apply((Applicative)$$0, LootTable::new));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final LootContextParamSet paramSet;
/*     */   
/*     */   private final Optional<ResourceLocation> randomSequence;
/*     */   
/*     */   private final List<LootPool> pools;
/*     */   
/*     */   private final List<LootItemFunction> functions;
/*     */   
/*     */   private final BiFunction<ItemStack, LootContext, ItemStack> compositeFunction;
/*     */ 
/*     */   
/*     */   LootTable(LootContextParamSet $$0, Optional<ResourceLocation> $$1, List<LootPool> $$2, List<LootItemFunction> $$3) {
/*  53 */     this.paramSet = $$0;
/*  54 */     this.randomSequence = $$1;
/*  55 */     this.pools = $$2;
/*  56 */     this.functions = $$3;
/*  57 */     this.compositeFunction = LootItemFunctions.compose($$3);
/*     */   }
/*     */   
/*     */   public static Consumer<ItemStack> createStackSplitter(ServerLevel $$0, Consumer<ItemStack> $$1) {
/*  61 */     return $$2 -> {
/*     */         if (!$$2.isItemEnabled($$0.enabledFeatures())) {
/*     */           return;
/*     */         }
/*     */         if ($$2.getCount() < $$2.getMaxStackSize()) {
/*     */           $$1.accept($$2);
/*     */         } else {
/*     */           int $$3 = $$2.getCount();
/*     */           while ($$3 > 0) {
/*     */             ItemStack $$4 = $$2.copyWithCount(Math.min($$2.getMaxStackSize(), $$3));
/*     */             $$3 -= $$4.getCount();
/*     */             $$1.accept($$4);
/*     */           } 
/*     */         } 
/*     */       };
/*     */   }
/*     */   
/*     */   public void getRandomItemsRaw(LootParams $$0, Consumer<ItemStack> $$1) {
/*  79 */     getRandomItemsRaw((new LootContext.Builder($$0)).create(this.randomSequence), $$1);
/*     */   }
/*     */   
/*     */   public void getRandomItemsRaw(LootContext $$0, Consumer<ItemStack> $$1) {
/*  83 */     LootContext.VisitedEntry<?> $$2 = LootContext.createVisitedEntry(this);
/*  84 */     if ($$0.pushVisitedElement($$2)) {
/*  85 */       Consumer<ItemStack> $$3 = LootItemFunction.decorate(this.compositeFunction, $$1, $$0);
/*  86 */       for (LootPool $$4 : this.pools) {
/*  87 */         $$4.addRandomItems($$3, $$0);
/*     */       }
/*  89 */       $$0.popVisitedElement($$2);
/*     */     } else {
/*  91 */       LOGGER.warn("Detected infinite loop in loot tables");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void getRandomItems(LootParams $$0, long $$1, Consumer<ItemStack> $$2) {
/*  96 */     getRandomItemsRaw((new LootContext.Builder($$0)).withOptionalRandomSeed($$1).create(this.randomSequence), createStackSplitter($$0.getLevel(), $$2));
/*     */   }
/*     */   
/*     */   public void getRandomItems(LootParams $$0, Consumer<ItemStack> $$1) {
/* 100 */     getRandomItemsRaw($$0, createStackSplitter($$0.getLevel(), $$1));
/*     */   }
/*     */   
/*     */   public void getRandomItems(LootContext $$0, Consumer<ItemStack> $$1) {
/* 104 */     getRandomItemsRaw($$0, createStackSplitter($$0.getLevel(), $$1));
/*     */   }
/*     */   
/*     */   public ObjectArrayList<ItemStack> getRandomItems(LootParams $$0, long $$1) {
/* 108 */     return getRandomItems((new LootContext.Builder($$0)).withOptionalRandomSeed($$1).create(this.randomSequence));
/*     */   }
/*     */   
/*     */   public ObjectArrayList<ItemStack> getRandomItems(LootParams $$0) {
/* 112 */     return getRandomItems((new LootContext.Builder($$0)).create(this.randomSequence));
/*     */   }
/*     */   
/*     */   private ObjectArrayList<ItemStack> getRandomItems(LootContext $$0) {
/* 116 */     ObjectArrayList<ItemStack> $$1 = new ObjectArrayList();
/* 117 */     Objects.requireNonNull($$1); getRandomItems($$0, $$1::add);
/* 118 */     return $$1;
/*     */   }
/*     */   
/*     */   public LootContextParamSet getParamSet() {
/* 122 */     return this.paramSet;
/*     */   }
/*     */   
/*     */   public void validate(ValidationContext $$0) {
/* 126 */     for (int $$1 = 0; $$1 < this.pools.size(); $$1++) {
/* 127 */       ((LootPool)this.pools.get($$1)).validate($$0.forChild(".pools[" + $$1 + "]"));
/*     */     }
/*     */     
/* 130 */     for (int $$2 = 0; $$2 < this.functions.size(); $$2++) {
/* 131 */       ((LootItemFunction)this.functions.get($$2)).validate($$0.forChild(".functions[" + $$2 + "]"));
/*     */     }
/*     */   }
/*     */   
/*     */   public void fill(Container $$0, LootParams $$1, long $$2) {
/* 136 */     LootContext $$3 = (new LootContext.Builder($$1)).withOptionalRandomSeed($$2).create(this.randomSequence);
/* 137 */     ObjectArrayList<ItemStack> $$4 = getRandomItems($$3);
/* 138 */     RandomSource $$5 = $$3.getRandom();
/* 139 */     List<Integer> $$6 = getAvailableSlots($$0, $$5);
/* 140 */     shuffleAndSplitItems($$4, $$6.size(), $$5);
/* 141 */     for (ObjectListIterator<ItemStack> objectListIterator = $$4.iterator(); objectListIterator.hasNext(); ) { ItemStack $$7 = objectListIterator.next();
/* 142 */       if ($$6.isEmpty()) {
/* 143 */         LOGGER.warn("Tried to over-fill a container");
/*     */         
/*     */         return;
/*     */       } 
/* 147 */       if ($$7.isEmpty()) {
/* 148 */         $$0.setItem(((Integer)$$6.remove($$6.size() - 1)).intValue(), ItemStack.EMPTY); continue;
/*     */       } 
/* 150 */       $$0.setItem(((Integer)$$6.remove($$6.size() - 1)).intValue(), $$7); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private void shuffleAndSplitItems(ObjectArrayList<ItemStack> $$0, int $$1, RandomSource $$2) {
/* 156 */     List<ItemStack> $$3 = Lists.newArrayList();
/* 157 */     for (ObjectListIterator<ItemStack> objectListIterator = $$0.iterator(); objectListIterator.hasNext(); ) {
/* 158 */       ItemStack $$5 = objectListIterator.next();
/* 159 */       if ($$5.isEmpty()) {
/* 160 */         objectListIterator.remove(); continue;
/* 161 */       }  if ($$5.getCount() > 1) {
/* 162 */         $$3.add($$5);
/* 163 */         objectListIterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/* 167 */     while ($$1 - $$0.size() - $$3.size() > 0 && !$$3.isEmpty()) {
/* 168 */       ItemStack $$6 = $$3.remove(Mth.nextInt($$2, 0, $$3.size() - 1));
/* 169 */       int $$7 = Mth.nextInt($$2, 1, $$6.getCount() / 2);
/* 170 */       ItemStack $$8 = $$6.split($$7);
/*     */       
/* 172 */       if ($$6.getCount() > 1 && $$2.nextBoolean()) {
/* 173 */         $$3.add($$6);
/*     */       } else {
/* 175 */         $$0.add($$6);
/*     */       } 
/*     */       
/* 178 */       if ($$8.getCount() > 1 && $$2.nextBoolean()) {
/* 179 */         $$3.add($$8); continue;
/*     */       } 
/* 181 */       $$0.add($$8);
/*     */     } 
/*     */ 
/*     */     
/* 185 */     $$0.addAll($$3);
/*     */     
/* 187 */     Util.shuffle((List)$$0, $$2);
/*     */   }
/*     */   
/*     */   private List<Integer> getAvailableSlots(Container $$0, RandomSource $$1) {
/* 191 */     ObjectArrayList<Integer> $$2 = new ObjectArrayList();
/*     */     
/* 193 */     for (int $$3 = 0; $$3 < $$0.getContainerSize(); $$3++) {
/* 194 */       if ($$0.getItem($$3).isEmpty()) {
/* 195 */         $$2.add(Integer.valueOf($$3));
/*     */       }
/*     */     } 
/*     */     
/* 199 */     Util.shuffle((List)$$2, $$1);
/* 200 */     return (List<Integer>)$$2;
/*     */   }
/*     */   
/*     */   public static class Builder implements FunctionUserBuilder<Builder> {
/* 204 */     private final ImmutableList.Builder<LootPool> pools = ImmutableList.builder();
/*     */     
/* 206 */     private final ImmutableList.Builder<LootItemFunction> functions = ImmutableList.builder();
/*     */     
/* 208 */     private LootContextParamSet paramSet = LootTable.DEFAULT_PARAM_SET;
/* 209 */     private Optional<ResourceLocation> randomSequence = Optional.empty();
/*     */     
/*     */     public Builder withPool(LootPool.Builder $$0) {
/* 212 */       this.pools.add($$0.build());
/* 213 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setParamSet(LootContextParamSet $$0) {
/* 217 */       this.paramSet = $$0;
/* 218 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setRandomSequence(ResourceLocation $$0) {
/* 222 */       this.randomSequence = Optional.of($$0);
/* 223 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder apply(LootItemFunction.Builder $$0) {
/* 228 */       this.functions.add($$0.build());
/* 229 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Builder unwrap() {
/* 234 */       return this;
/*     */     }
/*     */     
/*     */     public LootTable build() {
/* 238 */       return new LootTable(this.paramSet, this.randomSequence, (List<LootPool>)this.pools.build(), (List<LootItemFunction>)this.functions.build());
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder lootTable() {
/* 243 */     return new Builder();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootTable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */