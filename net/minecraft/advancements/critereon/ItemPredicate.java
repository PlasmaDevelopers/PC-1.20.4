/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public final class ItemPredicate extends Record {
/*     */   private final Optional<TagKey<Item>> tag;
/*     */   private final Optional<HolderSet<Item>> items;
/*     */   private final MinMaxBounds.Ints count;
/*     */   private final MinMaxBounds.Ints durability;
/*     */   private final List<EnchantmentPredicate> enchantments;
/*     */   private final List<EnchantmentPredicate> storedEnchantments;
/*     */   private final Optional<Holder<Potion>> potion;
/*     */   private final Optional<NbtPredicate> nbt;
/*     */   private static final Codec<HolderSet<Item>> ITEMS_CODEC;
/*     */   public static final Codec<ItemPredicate> CODEC;
/*     */   
/*  26 */   public ItemPredicate(Optional<TagKey<Item>> $$0, Optional<HolderSet<Item>> $$1, MinMaxBounds.Ints $$2, MinMaxBounds.Ints $$3, List<EnchantmentPredicate> $$4, List<EnchantmentPredicate> $$5, Optional<Holder<Potion>> $$6, Optional<NbtPredicate> $$7) { this.tag = $$0; this.items = $$1; this.count = $$2; this.durability = $$3; this.enchantments = $$4; this.storedEnchantments = $$5; this.potion = $$6; this.nbt = $$7; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/ItemPredicate;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #26	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  26 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/ItemPredicate; } public Optional<TagKey<Item>> tag() { return this.tag; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/ItemPredicate;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #26	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/ItemPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/ItemPredicate;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #26	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/ItemPredicate;
/*  26 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<HolderSet<Item>> items() { return this.items; } public MinMaxBounds.Ints count() { return this.count; } public MinMaxBounds.Ints durability() { return this.durability; } public List<EnchantmentPredicate> enchantments() { return this.enchantments; } public List<EnchantmentPredicate> storedEnchantments() { return this.storedEnchantments; } public Optional<Holder<Potion>> potion() { return this.potion; } public Optional<NbtPredicate> nbt() { return this.nbt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  39 */     ITEMS_CODEC = BuiltInRegistries.ITEM.holderByNameCodec().listOf().xmap(HolderSet::direct, $$0 -> $$0.stream().toList());
/*     */     
/*  41 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(TagKey.codec(Registries.ITEM), "tag").forGetter(ItemPredicate::tag), (App)ExtraCodecs.strictOptionalField(ITEMS_CODEC, "items").forGetter(ItemPredicate::items), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "count", MinMaxBounds.Ints.ANY).forGetter(ItemPredicate::count), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "durability", MinMaxBounds.Ints.ANY).forGetter(ItemPredicate::durability), (App)ExtraCodecs.strictOptionalField(EnchantmentPredicate.CODEC.listOf(), "enchantments", List.of()).forGetter(ItemPredicate::enchantments), (App)ExtraCodecs.strictOptionalField(EnchantmentPredicate.CODEC.listOf(), "stored_enchantments", List.of()).forGetter(ItemPredicate::storedEnchantments), (App)ExtraCodecs.strictOptionalField(BuiltInRegistries.POTION.holderByNameCodec(), "potion").forGetter(ItemPredicate::potion), (App)ExtraCodecs.strictOptionalField(NbtPredicate.CODEC, "nbt").forGetter(ItemPredicate::nbt)).apply((Applicative)$$0, ItemPredicate::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(ItemStack $$0) {
/*  53 */     if (this.tag.isPresent() && !$$0.is(this.tag.get())) {
/*  54 */       return false;
/*     */     }
/*  56 */     if (this.items.isPresent() && !$$0.is(this.items.get())) {
/*  57 */       return false;
/*     */     }
/*  59 */     if (!this.count.matches($$0.getCount())) {
/*  60 */       return false;
/*     */     }
/*  62 */     if (!this.durability.isAny() && !$$0.isDamageableItem()) {
/*  63 */       return false;
/*     */     }
/*  65 */     if (!this.durability.matches($$0.getMaxDamage() - $$0.getDamageValue())) {
/*  66 */       return false;
/*     */     }
/*  68 */     if (this.nbt.isPresent() && !((NbtPredicate)this.nbt.get()).matches($$0)) {
/*  69 */       return false;
/*     */     }
/*  71 */     if (!this.enchantments.isEmpty()) {
/*  72 */       Map<Enchantment, Integer> $$1 = EnchantmentHelper.deserializeEnchantments($$0.getEnchantmentTags());
/*  73 */       for (EnchantmentPredicate $$2 : this.enchantments) {
/*  74 */         if (!$$2.containedIn($$1)) {
/*  75 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*  79 */     if (!this.storedEnchantments.isEmpty()) {
/*  80 */       Map<Enchantment, Integer> $$3 = EnchantmentHelper.deserializeEnchantments(EnchantedBookItem.getEnchantments($$0));
/*  81 */       for (EnchantmentPredicate $$4 : this.storedEnchantments) {
/*  82 */         if (!$$4.containedIn($$3)) {
/*  83 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     if (this.potion.isPresent() && ((Holder)this.potion.get()).value() != PotionUtils.getPotion($$0)) {
/*  89 */       return false;
/*     */     }
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   public static class Builder {
/*  95 */     private final ImmutableList.Builder<EnchantmentPredicate> enchantments = ImmutableList.builder();
/*  96 */     private final ImmutableList.Builder<EnchantmentPredicate> storedEnchantments = ImmutableList.builder();
/*  97 */     private Optional<HolderSet<Item>> items = Optional.empty();
/*  98 */     private Optional<TagKey<Item>> tag = Optional.empty();
/*  99 */     private MinMaxBounds.Ints count = MinMaxBounds.Ints.ANY;
/* 100 */     private MinMaxBounds.Ints durability = MinMaxBounds.Ints.ANY;
/* 101 */     private Optional<Holder<Potion>> potion = Optional.empty();
/* 102 */     private Optional<NbtPredicate> nbt = Optional.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Builder item() {
/* 108 */       return new Builder();
/*     */     }
/*     */     
/*     */     public Builder of(ItemLike... $$0) {
/* 112 */       this.items = Optional.of(HolderSet.direct($$0 -> $$0.asItem().builtInRegistryHolder(), (Object[])$$0));
/* 113 */       return this;
/*     */     }
/*     */     
/*     */     public Builder of(TagKey<Item> $$0) {
/* 117 */       this.tag = Optional.of($$0);
/* 118 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withCount(MinMaxBounds.Ints $$0) {
/* 122 */       this.count = $$0;
/* 123 */       return this;
/*     */     }
/*     */     
/*     */     public Builder hasDurability(MinMaxBounds.Ints $$0) {
/* 127 */       this.durability = $$0;
/* 128 */       return this;
/*     */     }
/*     */     
/*     */     public Builder isPotion(Potion $$0) {
/* 132 */       this.potion = Optional.of($$0.builtInRegistryHolder());
/* 133 */       return this;
/*     */     }
/*     */     
/*     */     public Builder hasNbt(CompoundTag $$0) {
/* 137 */       this.nbt = Optional.of(new NbtPredicate($$0));
/* 138 */       return this;
/*     */     }
/*     */     
/*     */     public Builder hasEnchantment(EnchantmentPredicate $$0) {
/* 142 */       this.enchantments.add($$0);
/* 143 */       return this;
/*     */     }
/*     */     
/*     */     public Builder hasStoredEnchantment(EnchantmentPredicate $$0) {
/* 147 */       this.storedEnchantments.add($$0);
/* 148 */       return this;
/*     */     }
/*     */     
/*     */     public ItemPredicate build() {
/* 152 */       ImmutableList immutableList1 = this.enchantments.build();
/* 153 */       ImmutableList immutableList2 = this.storedEnchantments.build();
/* 154 */       return new ItemPredicate(this.tag, this.items, this.count, this.durability, (List<EnchantmentPredicate>)immutableList1, (List<EnchantmentPredicate>)immutableList2, this.potion, this.nbt);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\ItemPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */