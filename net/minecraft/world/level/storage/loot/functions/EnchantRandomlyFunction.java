/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentInstance;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class EnchantRandomlyFunction extends LootItemConditionalFunction {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger(); private static final Codec<HolderSet<Enchantment>> ENCHANTMENT_SET_CODEC; public static final Codec<EnchantRandomlyFunction> CODEC;
/*     */   private final Optional<HolderSet<Enchantment>> enchantments;
/*     */   
/*     */   static {
/*  31 */     ENCHANTMENT_SET_CODEC = BuiltInRegistries.ENCHANTMENT.holderByNameCodec().listOf().xmap(HolderSet::direct, $$0 -> $$0.stream().toList());
/*     */     
/*  33 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)ExtraCodecs.strictOptionalField(ENCHANTMENT_SET_CODEC, "enchantments").forGetter(())).apply((Applicative)$$0, EnchantRandomlyFunction::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   EnchantRandomlyFunction(List<LootItemCondition> $$0, Optional<HolderSet<Enchantment>> $$1) {
/*  40 */     super($$0);
/*  41 */     this.enchantments = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/*  46 */     return LootItemFunctions.ENCHANT_RANDOMLY;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/*  51 */     RandomSource $$2 = $$1.getRandom();
/*  52 */     Optional<Holder<Enchantment>> $$3 = this.enchantments.<Holder<Enchantment>>flatMap($$1 -> $$1.getRandomElement($$0)).or(() -> {
/*     */           boolean $$2 = $$0.is(Items.BOOK);
/*     */ 
/*     */           
/*     */           List<Holder.Reference<Enchantment>> $$3 = BuiltInRegistries.ENCHANTMENT.holders().filter(()).filter(()).toList();
/*     */           
/*     */           return Util.getRandomSafe($$3, $$1);
/*     */         });
/*     */     
/*  61 */     if ($$3.isEmpty()) {
/*  62 */       LOGGER.warn("Couldn't find a compatible enchantment for {}", $$0);
/*  63 */       return $$0;
/*     */     } 
/*     */     
/*  66 */     return enchantItem($$0, (Enchantment)((Holder)$$3.get()).value(), $$2);
/*     */   }
/*     */   
/*     */   private static ItemStack enchantItem(ItemStack $$0, Enchantment $$1, RandomSource $$2) {
/*  70 */     int $$3 = Mth.nextInt($$2, $$1.getMinLevel(), $$1.getMaxLevel());
/*     */     
/*  72 */     if ($$0.is(Items.BOOK)) {
/*  73 */       $$0 = new ItemStack((ItemLike)Items.ENCHANTED_BOOK);
/*  74 */       EnchantedBookItem.addEnchantment($$0, new EnchantmentInstance($$1, $$3));
/*     */     } else {
/*  76 */       $$0.enchant($$1, $$3);
/*     */     } 
/*  78 */     return $$0;
/*     */   }
/*     */   
/*     */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
/*  82 */     private final List<Holder<Enchantment>> enchantments = new ArrayList<>();
/*     */ 
/*     */     
/*     */     protected Builder getThis() {
/*  86 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withEnchantment(Enchantment $$0) {
/*  90 */       this.enchantments.add($$0.builtInRegistryHolder());
/*  91 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootItemFunction build() {
/*  96 */       return new EnchantRandomlyFunction(getConditions(), this.enchantments.isEmpty() ? Optional.<HolderSet<Enchantment>>empty() : (Optional)Optional.of(HolderSet.direct(this.enchantments)));
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder randomEnchantment() {
/* 101 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static LootItemConditionalFunction.Builder<?> randomApplicableEnchantment() {
/* 105 */     return simpleBuilder($$0 -> new EnchantRandomlyFunction($$0, Optional.empty()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\EnchantRandomlyFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */