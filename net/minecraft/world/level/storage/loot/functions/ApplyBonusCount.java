/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ 
/*     */ public class ApplyBonusCount extends LootItemConditionalFunction {
/*     */   private static final class FormulaType extends Record {
/*     */     private final ResourceLocation id;
/*     */     private final Codec<? extends ApplyBonusCount.Formula> codec;
/*     */     
/*  29 */     FormulaType(ResourceLocation $$0, Codec<? extends ApplyBonusCount.Formula> $$1) { this.id = $$0; this.codec = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$FormulaType;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #29	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  29 */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$FormulaType; } public ResourceLocation id() { return this.id; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$FormulaType;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #29	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$FormulaType; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$FormulaType;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #29	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$FormulaType;
/*  29 */       //   0	8	1	$$0	Ljava/lang/Object; } public Codec<? extends ApplyBonusCount.Formula> codec() { return this.codec; }
/*     */   
/*     */   }
/*     */   
/*     */   private static final class BinomialWithBonusCount extends Record implements Formula {
/*     */     private final int extraRounds;
/*     */     private final float probability;
/*     */     private static final Codec<BinomialWithBonusCount> CODEC;
/*     */     
/*  38 */     BinomialWithBonusCount(int $$0, float $$1) { this.extraRounds = $$0; this.probability = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #38	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #38	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #38	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;
/*  38 */       //   0	8	1	$$0	Ljava/lang/Object; } public int extraRounds() { return this.extraRounds; } public float probability() { return this.probability; } static {
/*  39 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("extra").forGetter(BinomialWithBonusCount::extraRounds), (App)Codec.FLOAT.fieldOf("probability").forGetter(BinomialWithBonusCount::probability)).apply((Applicative)$$0, BinomialWithBonusCount::new));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  44 */     public static final ApplyBonusCount.FormulaType TYPE = new ApplyBonusCount.FormulaType(new ResourceLocation("binomial_with_bonus_count"), (Codec)CODEC);
/*     */ 
/*     */     
/*     */     public int calculateNewCount(RandomSource $$0, int $$1, int $$2) {
/*  48 */       for (int $$3 = 0; $$3 < $$2 + this.extraRounds; $$3++) {
/*  49 */         if ($$0.nextFloat() < this.probability) {
/*  50 */           $$1++;
/*     */         }
/*     */       } 
/*  53 */       return $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public ApplyBonusCount.FormulaType getType() {
/*  58 */       return TYPE;
/*     */     } }
/*     */   private static final class UniformBonusCount extends Record implements Formula { private final int bonusMultiplier; public static final Codec<UniformBonusCount> CODEC;
/*     */     
/*  62 */     UniformBonusCount(int $$0) { this.bonusMultiplier = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$UniformBonusCount;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #62	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$UniformBonusCount; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$UniformBonusCount;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #62	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$UniformBonusCount; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$UniformBonusCount;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #62	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$UniformBonusCount;
/*  62 */       //   0	8	1	$$0	Ljava/lang/Object; } public int bonusMultiplier() { return this.bonusMultiplier; } static {
/*  63 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("bonusMultiplier").forGetter(UniformBonusCount::bonusMultiplier)).apply((Applicative)$$0, UniformBonusCount::new));
/*     */     }
/*     */ 
/*     */     
/*  67 */     public static final ApplyBonusCount.FormulaType TYPE = new ApplyBonusCount.FormulaType(new ResourceLocation("uniform_bonus_count"), (Codec)CODEC);
/*     */ 
/*     */     
/*     */     public int calculateNewCount(RandomSource $$0, int $$1, int $$2) {
/*  71 */       return $$1 + $$0.nextInt(this.bonusMultiplier * $$2 + 1);
/*     */     }
/*     */     
/*     */     public ApplyBonusCount.FormulaType getType()
/*     */     {
/*  76 */       return TYPE;
/*     */     } } private static final class OreDrops extends Record implements Formula { public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$OreDrops;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #80	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$OreDrops; } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$OreDrops;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #80	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$OreDrops;
/*     */     } public final boolean equals(Object $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$OreDrops;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #80	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$OreDrops;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*  81 */     } public static final Codec<OreDrops> CODEC = Codec.unit(OreDrops::new);
/*  82 */     public static final ApplyBonusCount.FormulaType TYPE = new ApplyBonusCount.FormulaType(new ResourceLocation("ore_drops"), (Codec)CODEC);
/*     */ 
/*     */     
/*     */     public int calculateNewCount(RandomSource $$0, int $$1, int $$2) {
/*  86 */       if ($$2 > 0) {
/*  87 */         int $$3 = $$0.nextInt($$2 + 2) - 1;
/*  88 */         if ($$3 < 0) {
/*  89 */           $$3 = 0;
/*     */         }
/*  91 */         return $$1 * ($$3 + 1);
/*     */       } 
/*     */       
/*  94 */       return $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public ApplyBonusCount.FormulaType getType() {
/*  99 */       return TYPE;
/*     */     } }
/*     */ 
/*     */   
/* 103 */   private static final Map<ResourceLocation, FormulaType> FORMULAS = (Map<ResourceLocation, FormulaType>)Stream.<FormulaType>of(new FormulaType[] { BinomialWithBonusCount.TYPE, OreDrops.TYPE, UniformBonusCount.TYPE
/*     */ 
/*     */ 
/*     */       
/* 107 */       }).collect(Collectors.toMap(FormulaType::id, Function.identity())); private static final Codec<FormulaType> FORMULA_TYPE_CODEC;
/*     */   static {
/* 109 */     FORMULA_TYPE_CODEC = ResourceLocation.CODEC.comapFlatMap($$0 -> { FormulaType $$1 = FORMULAS.get($$0); return ($$1 != null) ? DataResult.success($$1) : DataResult.error(()); }FormulaType::id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private static final MapCodec<Formula> FORMULA_CODEC = ExtraCodecs.dispatchOptionalValue("formula", "parameters", FORMULA_TYPE_CODEC, Formula::getType, FormulaType::codec); public static final Codec<ApplyBonusCount> CODEC; private final Holder<Enchantment> enchantment; private final Formula formula;
/*     */   static {
/* 119 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)BuiltInRegistries.ENCHANTMENT.holderByNameCodec().fieldOf("enchantment").forGetter(()), (App)FORMULA_CODEC.forGetter(()))).apply((Applicative)$$0, ApplyBonusCount::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ApplyBonusCount(List<LootItemCondition> $$0, Holder<Enchantment> $$1, Formula $$2) {
/* 128 */     super($$0);
/* 129 */     this.enchantment = $$1;
/* 130 */     this.formula = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/* 135 */     return LootItemFunctions.APPLY_BONUS;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 140 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.TOOL);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 145 */     ItemStack $$2 = (ItemStack)$$1.getParamOrNull(LootContextParams.TOOL);
/*     */     
/* 147 */     if ($$2 != null) {
/* 148 */       int $$3 = EnchantmentHelper.getItemEnchantmentLevel((Enchantment)this.enchantment.value(), $$2);
/* 149 */       int $$4 = this.formula.calculateNewCount($$1.getRandom(), $$0.getCount(), $$3);
/* 150 */       $$0.setCount($$4);
/*     */     } 
/* 152 */     return $$0;
/*     */   }
/*     */   
/*     */   public static LootItemConditionalFunction.Builder<?> addBonusBinomialDistributionCount(Enchantment $$0, float $$1, int $$2) {
/* 156 */     return simpleBuilder($$3 -> new ApplyBonusCount($$3, (Holder<Enchantment>)$$0.builtInRegistryHolder(), new BinomialWithBonusCount($$1, $$2)));
/*     */   }
/*     */   
/*     */   public static LootItemConditionalFunction.Builder<?> addOreBonusCount(Enchantment $$0) {
/* 160 */     return simpleBuilder($$1 -> new ApplyBonusCount($$1, (Holder<Enchantment>)$$0.builtInRegistryHolder(), new OreDrops()));
/*     */   }
/*     */   
/*     */   public static LootItemConditionalFunction.Builder<?> addUniformBonusCount(Enchantment $$0) {
/* 164 */     return simpleBuilder($$1 -> new ApplyBonusCount($$1, (Holder<Enchantment>)$$0.builtInRegistryHolder(), new UniformBonusCount(1)));
/*     */   }
/*     */   
/*     */   public static LootItemConditionalFunction.Builder<?> addUniformBonusCount(Enchantment $$0, int $$1) {
/* 168 */     return simpleBuilder($$2 -> new ApplyBonusCount($$2, (Holder<Enchantment>)$$0.builtInRegistryHolder(), new UniformBonusCount($$1)));
/*     */   }
/*     */   
/*     */   private static interface Formula {
/*     */     int calculateNewCount(RandomSource param1RandomSource, int param1Int1, int param1Int2);
/*     */     
/*     */     ApplyBonusCount.FormulaType getType();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\ApplyBonusCount.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */