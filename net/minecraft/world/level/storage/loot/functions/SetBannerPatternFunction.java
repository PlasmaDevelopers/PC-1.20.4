/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.entity.BannerPattern;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ 
/*     */ public class SetBannerPatternFunction extends LootItemConditionalFunction {
/*  25 */   private static final Codec<Pair<Holder<BannerPattern>, DyeColor>> PATTERN_CODEC = Codec.mapPair(BuiltInRegistries.BANNER_PATTERN
/*  26 */       .holderByNameCodec().fieldOf("pattern"), DyeColor.CODEC
/*  27 */       .fieldOf("color"))
/*  28 */     .codec(); public static final Codec<SetBannerPatternFunction> CODEC;
/*     */   static {
/*  30 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)PATTERN_CODEC.listOf().fieldOf("patterns").forGetter(()), (App)Codec.BOOL.fieldOf("append").forGetter(()))).apply((Applicative)$$0, SetBannerPatternFunction::new));
/*     */   }
/*     */ 
/*     */   
/*     */   private final List<Pair<Holder<BannerPattern>, DyeColor>> patterns;
/*     */   
/*     */   private final boolean append;
/*     */   
/*     */   SetBannerPatternFunction(List<LootItemCondition> $$0, List<Pair<Holder<BannerPattern>, DyeColor>> $$1, boolean $$2) {
/*  39 */     super($$0);
/*  40 */     this.patterns = $$1;
/*  41 */     this.append = $$2;
/*     */   }
/*     */   
/*     */   protected ItemStack run(ItemStack $$0, LootContext $$1) {
/*     */     ListTag $$6;
/*  46 */     CompoundTag $$2 = BlockItem.getBlockEntityData($$0);
/*  47 */     if ($$2 == null) {
/*  48 */       $$2 = new CompoundTag();
/*     */     }
/*  50 */     BannerPattern.Builder $$3 = new BannerPattern.Builder();
/*  51 */     Objects.requireNonNull($$3); this.patterns.forEach($$3::addPattern);
/*  52 */     ListTag $$4 = $$3.toListTag();
/*     */ 
/*     */     
/*  55 */     if (this.append) {
/*  56 */       ListTag $$5 = $$2.getList("Patterns", 10).copy();
/*  57 */       $$5.addAll((Collection)$$4);
/*     */     } else {
/*  59 */       $$6 = $$4;
/*     */     } 
/*  61 */     $$2.put("Patterns", (Tag)$$6);
/*  62 */     BlockItem.setBlockEntityData($$0, BlockEntityType.BANNER, $$2);
/*  63 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/*  68 */     return LootItemFunctions.SET_BANNER_PATTERN;
/*     */   }
/*     */   
/*     */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
/*  72 */     private final ImmutableList.Builder<Pair<Holder<BannerPattern>, DyeColor>> patterns = ImmutableList.builder();
/*     */     private final boolean append;
/*     */     
/*     */     Builder(boolean $$0) {
/*  76 */       this.append = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Builder getThis() {
/*  81 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootItemFunction build() {
/*  86 */       return new SetBannerPatternFunction(getConditions(), (List<Pair<Holder<BannerPattern>, DyeColor>>)this.patterns.build(), this.append);
/*     */     }
/*     */     
/*     */     public Builder addPattern(ResourceKey<BannerPattern> $$0, DyeColor $$1) {
/*  90 */       return addPattern((Holder<BannerPattern>)BuiltInRegistries.BANNER_PATTERN.getHolderOrThrow($$0), $$1);
/*     */     }
/*     */     
/*     */     public Builder addPattern(Holder<BannerPattern> $$0, DyeColor $$1) {
/*  94 */       this.patterns.add(Pair.of($$0, $$1));
/*  95 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder setBannerPattern(boolean $$0) {
/* 100 */     return new Builder($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetBannerPatternFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */