/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.alchemy.Potion;
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
/*     */ public class Builder
/*     */ {
/*  95 */   private final ImmutableList.Builder<EnchantmentPredicate> enchantments = ImmutableList.builder();
/*  96 */   private final ImmutableList.Builder<EnchantmentPredicate> storedEnchantments = ImmutableList.builder();
/*  97 */   private Optional<HolderSet<Item>> items = Optional.empty();
/*  98 */   private Optional<TagKey<Item>> tag = Optional.empty();
/*  99 */   private MinMaxBounds.Ints count = MinMaxBounds.Ints.ANY;
/* 100 */   private MinMaxBounds.Ints durability = MinMaxBounds.Ints.ANY;
/* 101 */   private Optional<Holder<Potion>> potion = Optional.empty();
/* 102 */   private Optional<NbtPredicate> nbt = Optional.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder item() {
/* 108 */     return new Builder();
/*     */   }
/*     */   
/*     */   public Builder of(ItemLike... $$0) {
/* 112 */     this.items = Optional.of(HolderSet.direct($$0 -> $$0.asItem().builtInRegistryHolder(), (Object[])$$0));
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public Builder of(TagKey<Item> $$0) {
/* 117 */     this.tag = Optional.of($$0);
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public Builder withCount(MinMaxBounds.Ints $$0) {
/* 122 */     this.count = $$0;
/* 123 */     return this;
/*     */   }
/*     */   
/*     */   public Builder hasDurability(MinMaxBounds.Ints $$0) {
/* 127 */     this.durability = $$0;
/* 128 */     return this;
/*     */   }
/*     */   
/*     */   public Builder isPotion(Potion $$0) {
/* 132 */     this.potion = Optional.of($$0.builtInRegistryHolder());
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   public Builder hasNbt(CompoundTag $$0) {
/* 137 */     this.nbt = Optional.of(new NbtPredicate($$0));
/* 138 */     return this;
/*     */   }
/*     */   
/*     */   public Builder hasEnchantment(EnchantmentPredicate $$0) {
/* 142 */     this.enchantments.add($$0);
/* 143 */     return this;
/*     */   }
/*     */   
/*     */   public Builder hasStoredEnchantment(EnchantmentPredicate $$0) {
/* 147 */     this.storedEnchantments.add($$0);
/* 148 */     return this;
/*     */   }
/*     */   
/*     */   public ItemPredicate build() {
/* 152 */     ImmutableList immutableList1 = this.enchantments.build();
/* 153 */     ImmutableList immutableList2 = this.storedEnchantments.build();
/* 154 */     return new ItemPredicate(this.tag, this.items, this.count, this.durability, (List<EnchantmentPredicate>)immutableList1, (List<EnchantmentPredicate>)immutableList2, this.potion, this.nbt);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\ItemPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */