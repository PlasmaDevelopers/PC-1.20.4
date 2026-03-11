/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.item.EnchantedBookItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentInstance;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*     */ 
/*     */ public class SetEnchantmentsFunction extends LootItemConditionalFunction {
/*     */   static {
/*  29 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.unboundedMap(BuiltInRegistries.ENCHANTMENT.holderByNameCodec(), NumberProviders.CODEC), "enchantments", Map.of()).forGetter(()), (App)Codec.BOOL.fieldOf("add").orElse(Boolean.valueOf(false)).forGetter(()))).apply((Applicative)$$0, SetEnchantmentsFunction::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<SetEnchantmentsFunction> CODEC;
/*     */   private final Map<Holder<Enchantment>, NumberProvider> enchantments;
/*     */   private final boolean add;
/*     */   
/*     */   SetEnchantmentsFunction(List<LootItemCondition> $$0, Map<Holder<Enchantment>, NumberProvider> $$1, boolean $$2) {
/*  38 */     super($$0);
/*  39 */     this.enchantments = Map.copyOf($$1);
/*  40 */     this.add = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/*  45 */     return LootItemFunctions.SET_ENCHANTMENTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/*  50 */     return (Set<LootContextParam<?>>)this.enchantments.values().stream().flatMap($$0 -> $$0.getReferencedContextParams().stream()).collect(ImmutableSet.toImmutableSet());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/*  55 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/*  56 */     this.enchantments.forEach(($$2, $$3) -> $$0.put($$2.value(), $$3.getInt($$1)));
/*     */     
/*  58 */     if ($$0.getItem() == Items.BOOK) {
/*  59 */       ItemStack $$3 = new ItemStack((ItemLike)Items.ENCHANTED_BOOK);
/*  60 */       object2IntOpenHashMap.forEach(($$1, $$2) -> EnchantedBookItem.addEnchantment($$0, new EnchantmentInstance($$1, $$2.intValue())));
/*  61 */       return $$3;
/*     */     } 
/*     */     
/*  64 */     Map<Enchantment, Integer> $$4 = EnchantmentHelper.getEnchantments($$0);
/*  65 */     if (this.add) {
/*  66 */       object2IntOpenHashMap.forEach(($$1, $$2) -> updateEnchantment($$0, $$1, Math.max(((Integer)$$0.getOrDefault($$1, Integer.valueOf(0))).intValue() + $$2.intValue(), 0)));
/*     */     } else {
/*  68 */       object2IntOpenHashMap.forEach(($$1, $$2) -> updateEnchantment($$0, $$1, Math.max($$2.intValue(), 0)));
/*     */     } 
/*  70 */     EnchantmentHelper.setEnchantments($$4, $$0);
/*  71 */     return $$0;
/*     */   }
/*     */   
/*     */   private static void updateEnchantment(Map<Enchantment, Integer> $$0, Enchantment $$1, int $$2) {
/*  75 */     if ($$2 == 0) {
/*  76 */       $$0.remove($$1);
/*     */     } else {
/*  78 */       $$0.put($$1, Integer.valueOf($$2));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
/*  83 */     private final ImmutableMap.Builder<Holder<Enchantment>, NumberProvider> enchantments = ImmutableMap.builder();
/*     */     private final boolean add;
/*     */     
/*     */     public Builder() {
/*  87 */       this(false);
/*     */     }
/*     */     
/*     */     public Builder(boolean $$0) {
/*  91 */       this.add = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Builder getThis() {
/*  96 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withEnchantment(Enchantment $$0, NumberProvider $$1) {
/* 100 */       this.enchantments.put($$0.builtInRegistryHolder(), $$1);
/* 101 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootItemFunction build() {
/* 106 */       return new SetEnchantmentsFunction(getConditions(), (Map<Holder<Enchantment>, NumberProvider>)this.enchantments.build(), this.add);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetEnchantmentsFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */