/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public class LootItemFunctions {
/*    */   static {
/* 15 */     IDENTITY = (($$0, $$1) -> $$0);
/*    */   }
/* 17 */   private static final Codec<LootItemFunction> TYPED_CODEC = BuiltInRegistries.LOOT_FUNCTION_TYPE.byNameCodec()
/* 18 */     .dispatch("function", LootItemFunction::getType, LootItemFunctionType::codec);
/*    */   public static final BiFunction<ItemStack, LootContext, ItemStack> IDENTITY;
/* 20 */   public static final Codec<LootItemFunction> CODEC = ExtraCodecs.lazyInitializedCodec(() -> ExtraCodecs.withAlternative(TYPED_CODEC, SequenceFunction.INLINE_CODEC));
/*    */   
/* 22 */   public static final LootItemFunctionType SET_COUNT = register("set_count", (Codec)SetItemCountFunction.CODEC);
/* 23 */   public static final LootItemFunctionType ENCHANT_WITH_LEVELS = register("enchant_with_levels", (Codec)EnchantWithLevelsFunction.CODEC);
/* 24 */   public static final LootItemFunctionType ENCHANT_RANDOMLY = register("enchant_randomly", (Codec)EnchantRandomlyFunction.CODEC);
/* 25 */   public static final LootItemFunctionType SET_ENCHANTMENTS = register("set_enchantments", (Codec)SetEnchantmentsFunction.CODEC);
/* 26 */   public static final LootItemFunctionType SET_NBT = register("set_nbt", (Codec)SetNbtFunction.CODEC);
/* 27 */   public static final LootItemFunctionType FURNACE_SMELT = register("furnace_smelt", (Codec)SmeltItemFunction.CODEC);
/* 28 */   public static final LootItemFunctionType LOOTING_ENCHANT = register("looting_enchant", (Codec)LootingEnchantFunction.CODEC);
/* 29 */   public static final LootItemFunctionType SET_DAMAGE = register("set_damage", (Codec)SetItemDamageFunction.CODEC);
/* 30 */   public static final LootItemFunctionType SET_ATTRIBUTES = register("set_attributes", (Codec)SetAttributesFunction.CODEC);
/* 31 */   public static final LootItemFunctionType SET_NAME = register("set_name", (Codec)SetNameFunction.CODEC);
/* 32 */   public static final LootItemFunctionType EXPLORATION_MAP = register("exploration_map", (Codec)ExplorationMapFunction.CODEC);
/* 33 */   public static final LootItemFunctionType SET_STEW_EFFECT = register("set_stew_effect", (Codec)SetStewEffectFunction.CODEC);
/* 34 */   public static final LootItemFunctionType COPY_NAME = register("copy_name", (Codec)CopyNameFunction.CODEC);
/* 35 */   public static final LootItemFunctionType SET_CONTENTS = register("set_contents", (Codec)SetContainerContents.CODEC);
/* 36 */   public static final LootItemFunctionType LIMIT_COUNT = register("limit_count", (Codec)LimitCount.CODEC);
/* 37 */   public static final LootItemFunctionType APPLY_BONUS = register("apply_bonus", (Codec)ApplyBonusCount.CODEC);
/* 38 */   public static final LootItemFunctionType SET_LOOT_TABLE = register("set_loot_table", (Codec)SetContainerLootTable.CODEC);
/* 39 */   public static final LootItemFunctionType EXPLOSION_DECAY = register("explosion_decay", (Codec)ApplyExplosionDecay.CODEC);
/* 40 */   public static final LootItemFunctionType SET_LORE = register("set_lore", (Codec)SetLoreFunction.CODEC);
/* 41 */   public static final LootItemFunctionType FILL_PLAYER_HEAD = register("fill_player_head", (Codec)FillPlayerHead.CODEC);
/* 42 */   public static final LootItemFunctionType COPY_NBT = register("copy_nbt", (Codec)CopyNbtFunction.CODEC);
/* 43 */   public static final LootItemFunctionType COPY_STATE = register("copy_state", (Codec)CopyBlockState.CODEC);
/* 44 */   public static final LootItemFunctionType SET_BANNER_PATTERN = register("set_banner_pattern", (Codec)SetBannerPatternFunction.CODEC);
/* 45 */   public static final LootItemFunctionType SET_POTION = register("set_potion", (Codec)SetPotionFunction.CODEC);
/* 46 */   public static final LootItemFunctionType SET_INSTRUMENT = register("set_instrument", (Codec)SetInstrumentFunction.CODEC);
/* 47 */   public static final LootItemFunctionType REFERENCE = register("reference", (Codec)FunctionReference.CODEC);
/* 48 */   public static final LootItemFunctionType SEQUENCE = register("sequence", (Codec)SequenceFunction.CODEC);
/*    */   
/*    */   private static LootItemFunctionType register(String $$0, Codec<? extends LootItemFunction> $$1) {
/* 51 */     return (LootItemFunctionType)Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, new ResourceLocation($$0), new LootItemFunctionType($$1));
/*    */   }
/*    */   public static BiFunction<ItemStack, LootContext, ItemStack> compose(List<? extends BiFunction<ItemStack, LootContext, ItemStack>> $$0) {
/*    */     BiFunction<ItemStack, LootContext, ItemStack> $$2, $$3;
/* 55 */     List<BiFunction<ItemStack, LootContext, ItemStack>> $$1 = List.copyOf($$0);
/* 56 */     switch ($$1.size()) { case 0: 
/*    */       case 1:
/*    */       
/*    */       case 2:
/* 60 */         $$2 = $$1.get(0);
/* 61 */         $$3 = $$1.get(1); }
/*    */     
/*    */     return ($$1, $$2) -> {
/*    */         for (BiFunction<ItemStack, LootContext, ItemStack> $$3 : (Iterable<BiFunction<ItemStack, LootContext, ItemStack>>)$$0)
/*    */           $$1 = $$3.apply($$1, $$2); 
/*    */         return $$1;
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\LootItemFunctions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */