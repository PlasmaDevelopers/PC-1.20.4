/*      */ package net.minecraft.world.entity.npc;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*      */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.stream.Collectors;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.server.level.ServerLevel;
/*      */ import net.minecraft.tags.StructureTags;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.effect.MobEffect;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.item.DyeColor;
/*      */ import net.minecraft.world.item.DyeItem;
/*      */ import net.minecraft.world.item.DyeableLeatherItem;
/*      */ import net.minecraft.world.item.EnchantedBookItem;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.MapItem;
/*      */ import net.minecraft.world.item.SuspiciousStewItem;
/*      */ import net.minecraft.world.item.alchemy.Potion;
/*      */ import net.minecraft.world.item.alchemy.PotionBrewing;
/*      */ import net.minecraft.world.item.alchemy.PotionUtils;
/*      */ import net.minecraft.world.item.alchemy.Potions;
/*      */ import net.minecraft.world.item.enchantment.Enchantment;
/*      */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.world.item.enchantment.EnchantmentInstance;
/*      */ import net.minecraft.world.item.enchantment.Enchantments;
/*      */ import net.minecraft.world.item.trading.MerchantOffer;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.SuspiciousEffectHolder;
/*      */ import net.minecraft.world.level.levelgen.structure.Structure;
/*      */ import net.minecraft.world.level.saveddata.maps.MapDecoration;
/*      */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*      */ import org.apache.commons.lang3.tuple.Pair;
/*      */ 
/*      */ public class VillagerTrades {
/*      */   private static final int DEFAULT_SUPPLY = 12;
/*      */   private static final int COMMON_ITEMS_SUPPLY = 16;
/*      */   private static final int UNCOMMON_ITEMS_SUPPLY = 3;
/*      */   private static final int XP_LEVEL_1_SELL = 1;
/*      */   private static final int XP_LEVEL_1_BUY = 2;
/*      */   private static final int XP_LEVEL_2_SELL = 5;
/*      */   private static final int XP_LEVEL_2_BUY = 10;
/*      */   private static final int XP_LEVEL_3_SELL = 10;
/*      */   private static final int XP_LEVEL_3_BUY = 20;
/*      */   private static final int XP_LEVEL_4_SELL = 15;
/*      */   private static final int XP_LEVEL_4_BUY = 30;
/*      */   private static final int XP_LEVEL_5_TRADE = 30;
/*      */   private static final float LOW_TIER_PRICE_MULTIPLIER = 0.05F;
/*      */   private static final float HIGH_TIER_PRICE_MULTIPLIER = 0.2F;
/*      */   public static final Map<VillagerProfession, Int2ObjectMap<ItemListing[]>> TRADES;
/*      */   
/*      */   static {
/*   73 */     TRADES = (Map<VillagerProfession, Int2ObjectMap<ItemListing[]>>)Util.make(Maps.newHashMap(), $$0 -> {
/*      */           $$0.put(VillagerProfession.FARMER, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.WHEAT, 20, 16, 2), new EmeraldForItems((ItemLike)Items.POTATO, 26, 16, 2), new EmeraldForItems((ItemLike)Items.CARROT, 22, 16, 2), new EmeraldForItems((ItemLike)Items.BEETROOT, 15, 16, 2), new ItemsForEmeralds(Items.BREAD, 1, 6, 16, 1) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Blocks.PUMPKIN, 6, 12, 10), new ItemsForEmeralds(Items.PUMPKIN_PIE, 1, 4, 5), new ItemsForEmeralds(Items.APPLE, 1, 4, 16, 5) }Integer.valueOf(3), new ItemListing[] { new ItemsForEmeralds(Items.COOKIE, 3, 18, 10), new EmeraldForItems((ItemLike)Blocks.MELON, 4, 12, 20) }Integer.valueOf(4), new ItemListing[] { new ItemsForEmeralds(Blocks.CAKE, 1, 1, 12, 15), new SuspiciousStewForEmerald(MobEffects.NIGHT_VISION, 100, 15), new SuspiciousStewForEmerald(MobEffects.JUMP, 160, 15), new SuspiciousStewForEmerald(MobEffects.WEAKNESS, 140, 15), new SuspiciousStewForEmerald(MobEffects.BLINDNESS, 120, 15), new SuspiciousStewForEmerald(MobEffects.POISON, 280, 15), new SuspiciousStewForEmerald(MobEffects.SATURATION, 7, 15) }Integer.valueOf(5), new ItemListing[] { new ItemsForEmeralds(Items.GOLDEN_CARROT, 3, 3, 30), new ItemsForEmeralds(Items.GLISTERING_MELON_SLICE, 4, 3, 30) })));
/*      */           $$0.put(VillagerProfession.FISHERMAN, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.STRING, 20, 16, 2), new EmeraldForItems((ItemLike)Items.COAL, 10, 16, 2), new ItemsAndEmeraldsToItems((ItemLike)Items.COD, 6, 1, Items.COOKED_COD, 6, 16, 1, 0.05F), new ItemsForEmeralds(Items.COD_BUCKET, 3, 1, 16, 1) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.COD, 15, 16, 10), new ItemsAndEmeraldsToItems((ItemLike)Items.SALMON, 6, 1, Items.COOKED_SALMON, 6, 16, 5, 0.05F), new ItemsForEmeralds(Items.CAMPFIRE, 2, 1, 5) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.SALMON, 13, 16, 20), new EnchantedItemForEmeralds(Items.FISHING_ROD, 3, 3, 10, 0.2F) }Integer.valueOf(4), new ItemListing[] { new EmeraldForItems((ItemLike)Items.TROPICAL_FISH, 6, 12, 30) }Integer.valueOf(5), new ItemListing[] { new EmeraldForItems((ItemLike)Items.PUFFERFISH, 4, 12, 30), new EmeraldsForVillagerTypeItem(1, 12, 30, (Map<VillagerType, Item>)ImmutableMap.builder().put(VillagerType.PLAINS, Items.OAK_BOAT).put(VillagerType.TAIGA, Items.SPRUCE_BOAT).put(VillagerType.SNOW, Items.SPRUCE_BOAT).put(VillagerType.DESERT, Items.JUNGLE_BOAT).put(VillagerType.JUNGLE, Items.JUNGLE_BOAT).put(VillagerType.SAVANNA, Items.ACACIA_BOAT).put(VillagerType.SWAMP, Items.DARK_OAK_BOAT).build()) })));
/*      */           $$0.put(VillagerProfession.SHEPHERD, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Blocks.WHITE_WOOL, 18, 16, 2), new EmeraldForItems((ItemLike)Blocks.BROWN_WOOL, 18, 16, 2), new EmeraldForItems((ItemLike)Blocks.BLACK_WOOL, 18, 16, 2), new EmeraldForItems((ItemLike)Blocks.GRAY_WOOL, 18, 16, 2), new ItemsForEmeralds(Items.SHEARS, 2, 1, 1) }Integer.valueOf(2), new ItemListing[] { 
/*      */                     new EmeraldForItems((ItemLike)Items.WHITE_DYE, 12, 16, 10), new EmeraldForItems((ItemLike)Items.GRAY_DYE, 12, 16, 10), new EmeraldForItems((ItemLike)Items.BLACK_DYE, 12, 16, 10), new EmeraldForItems((ItemLike)Items.LIGHT_BLUE_DYE, 12, 16, 10), new EmeraldForItems((ItemLike)Items.LIME_DYE, 12, 16, 10), new ItemsForEmeralds(Blocks.WHITE_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.ORANGE_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.MAGENTA_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.LIGHT_BLUE_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.YELLOW_WOOL, 1, 1, 16, 5), 
/*      */                     new ItemsForEmeralds(Blocks.LIME_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.PINK_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.GRAY_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.LIGHT_GRAY_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.CYAN_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.PURPLE_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.BLUE_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.BROWN_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.GREEN_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.RED_WOOL, 1, 1, 16, 5), 
/*      */                     new ItemsForEmeralds(Blocks.BLACK_WOOL, 1, 1, 16, 5), new ItemsForEmeralds(Blocks.WHITE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.ORANGE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.MAGENTA_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.LIGHT_BLUE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.YELLOW_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.LIME_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.PINK_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.GRAY_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.LIGHT_GRAY_CARPET, 1, 4, 16, 5), 
/*      */                     new ItemsForEmeralds(Blocks.CYAN_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.PURPLE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.BLUE_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.BROWN_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.GREEN_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.RED_CARPET, 1, 4, 16, 5), new ItemsForEmeralds(Blocks.BLACK_CARPET, 1, 4, 16, 5) }Integer.valueOf(3), new ItemListing[] { 
/*      */                     new EmeraldForItems((ItemLike)Items.YELLOW_DYE, 12, 16, 20), new EmeraldForItems((ItemLike)Items.LIGHT_GRAY_DYE, 12, 16, 20), new EmeraldForItems((ItemLike)Items.ORANGE_DYE, 12, 16, 20), new EmeraldForItems((ItemLike)Items.RED_DYE, 12, 16, 20), new EmeraldForItems((ItemLike)Items.PINK_DYE, 12, 16, 20), new ItemsForEmeralds(Blocks.WHITE_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.YELLOW_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.RED_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.BLACK_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.BLUE_BED, 3, 1, 12, 10), 
/*      */                     new ItemsForEmeralds(Blocks.BROWN_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.CYAN_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.GRAY_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.GREEN_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.LIGHT_BLUE_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.LIGHT_GRAY_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.LIME_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.MAGENTA_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.ORANGE_BED, 3, 1, 12, 10), new ItemsForEmeralds(Blocks.PINK_BED, 3, 1, 12, 10), 
/*      */                     new ItemsForEmeralds(Blocks.PURPLE_BED, 3, 1, 12, 10) }Integer.valueOf(4), new ItemListing[] { 
/*      */                     new EmeraldForItems((ItemLike)Items.BROWN_DYE, 12, 16, 30), new EmeraldForItems((ItemLike)Items.PURPLE_DYE, 12, 16, 30), new EmeraldForItems((ItemLike)Items.BLUE_DYE, 12, 16, 30), new EmeraldForItems((ItemLike)Items.GREEN_DYE, 12, 16, 30), new EmeraldForItems((ItemLike)Items.MAGENTA_DYE, 12, 16, 30), new EmeraldForItems((ItemLike)Items.CYAN_DYE, 12, 16, 30), new ItemsForEmeralds(Items.WHITE_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.BLUE_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.LIGHT_BLUE_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.RED_BANNER, 3, 1, 12, 15), 
/*      */                     new ItemsForEmeralds(Items.PINK_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.GREEN_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.LIME_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.GRAY_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.BLACK_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.PURPLE_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.MAGENTA_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.CYAN_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.BROWN_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.YELLOW_BANNER, 3, 1, 12, 15), 
/*      */                     new ItemsForEmeralds(Items.ORANGE_BANNER, 3, 1, 12, 15), new ItemsForEmeralds(Items.LIGHT_GRAY_BANNER, 3, 1, 12, 15) }Integer.valueOf(5), new ItemListing[] { new ItemsForEmeralds(Items.PAINTING, 2, 3, 30) })));
/*      */           $$0.put(VillagerProfession.FLETCHER, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.STICK, 32, 16, 2), new ItemsForEmeralds(Items.ARROW, 1, 16, 1), new ItemsAndEmeraldsToItems((ItemLike)Blocks.GRAVEL, 10, 1, Items.FLINT, 10, 12, 1, 0.05F) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.FLINT, 26, 12, 10), new ItemsForEmeralds(Items.BOW, 2, 1, 5) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.STRING, 14, 16, 20), new ItemsForEmeralds(Items.CROSSBOW, 3, 1, 10) }Integer.valueOf(4), new ItemListing[] { new EmeraldForItems((ItemLike)Items.FEATHER, 24, 16, 30), new EnchantedItemForEmeralds(Items.BOW, 2, 3, 15) }Integer.valueOf(5), new ItemListing[] { new EmeraldForItems((ItemLike)Items.TRIPWIRE_HOOK, 8, 12, 30), new EnchantedItemForEmeralds(Items.CROSSBOW, 3, 3, 15), new TippedArrowForItemsAndEmeralds(Items.ARROW, 5, Items.TIPPED_ARROW, 5, 2, 12, 30) })));
/*      */           $$0.put(VillagerProfession.LIBRARIAN, toIntMap(ImmutableMap.builder().put(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.PAPER, 24, 16, 2), new EnchantBookForEmeralds(1), new ItemsForEmeralds(Blocks.BOOKSHELF, 9, 1, 12, 1) }).put(Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.BOOK, 4, 12, 10), new EnchantBookForEmeralds(5), new ItemsForEmeralds(Items.LANTERN, 1, 1, 5) }).put(Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.INK_SAC, 5, 12, 20), new EnchantBookForEmeralds(10), new ItemsForEmeralds(Items.GLASS, 1, 4, 10) }).put(Integer.valueOf(4), new ItemListing[] { new EmeraldForItems((ItemLike)Items.WRITABLE_BOOK, 2, 12, 30), new EnchantBookForEmeralds(15), new ItemsForEmeralds(Items.CLOCK, 5, 1, 15), new ItemsForEmeralds(Items.COMPASS, 4, 1, 15) }).put(Integer.valueOf(5), new ItemListing[] { new ItemsForEmeralds(Items.NAME_TAG, 20, 1, 30) }).build()));
/*      */           $$0.put(VillagerProfession.CARTOGRAPHER, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.PAPER, 24, 16, 2), new ItemsForEmeralds(Items.MAP, 7, 1, 1) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.GLASS_PANE, 11, 16, 10), new TreasureMapForEmeralds(13, StructureTags.ON_OCEAN_EXPLORER_MAPS, "filled_map.monument", MapDecoration.Type.MONUMENT, 12, 5) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.COMPASS, 1, 12, 20), new TreasureMapForEmeralds(14, StructureTags.ON_WOODLAND_EXPLORER_MAPS, "filled_map.mansion", MapDecoration.Type.MANSION, 12, 10) }Integer.valueOf(4), new ItemListing[] { 
/*      */                     new ItemsForEmeralds(Items.ITEM_FRAME, 7, 1, 15), new ItemsForEmeralds(Items.WHITE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.BLUE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.LIGHT_BLUE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.RED_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.PINK_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.GREEN_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.LIME_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.GRAY_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.BLACK_BANNER, 3, 1, 15), 
/*      */                     new ItemsForEmeralds(Items.PURPLE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.MAGENTA_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.CYAN_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.BROWN_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.YELLOW_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.ORANGE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.LIGHT_GRAY_BANNER, 3, 1, 15) }Integer.valueOf(5), new ItemListing[] { new ItemsForEmeralds(Items.GLOBE_BANNER_PATTERN, 8, 1, 30) })));
/*      */           $$0.put(VillagerProfession.CLERIC, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.ROTTEN_FLESH, 32, 16, 2), new ItemsForEmeralds(Items.REDSTONE, 1, 2, 1) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.GOLD_INGOT, 3, 12, 10), new ItemsForEmeralds(Items.LAPIS_LAZULI, 1, 1, 5) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.RABBIT_FOOT, 2, 12, 20), new ItemsForEmeralds(Blocks.GLOWSTONE, 4, 1, 12, 10) }Integer.valueOf(4), new ItemListing[] { new EmeraldForItems((ItemLike)Items.SCUTE, 4, 12, 30), new EmeraldForItems((ItemLike)Items.GLASS_BOTTLE, 9, 12, 30), new ItemsForEmeralds(Items.ENDER_PEARL, 5, 1, 15) }Integer.valueOf(5), new ItemListing[] { new EmeraldForItems((ItemLike)Items.NETHER_WART, 22, 12, 30), new ItemsForEmeralds(Items.EXPERIENCE_BOTTLE, 3, 1, 30) })));
/*      */           $$0.put(VillagerProfession.ARMORER, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.COAL, 15, 16, 2), new ItemsForEmeralds(new ItemStack((ItemLike)Items.IRON_LEGGINGS), 7, 1, 12, 1, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.IRON_BOOTS), 4, 1, 12, 1, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.IRON_HELMET), 5, 1, 12, 1, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.IRON_CHESTPLATE), 9, 1, 12, 1, 0.2F) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.IRON_INGOT, 4, 12, 10), new ItemsForEmeralds(new ItemStack((ItemLike)Items.BELL), 36, 1, 12, 5, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.CHAINMAIL_BOOTS), 1, 1, 12, 5, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.CHAINMAIL_LEGGINGS), 3, 1, 12, 5, 0.2F) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.LAVA_BUCKET, 1, 12, 20), new EmeraldForItems((ItemLike)Items.DIAMOND, 1, 12, 20), new ItemsForEmeralds(new ItemStack((ItemLike)Items.CHAINMAIL_HELMET), 1, 1, 12, 10, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.CHAINMAIL_CHESTPLATE), 4, 1, 12, 10, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.SHIELD), 5, 1, 12, 10, 0.2F) }Integer.valueOf(4), new ItemListing[] { new EnchantedItemForEmeralds(Items.DIAMOND_LEGGINGS, 14, 3, 15, 0.2F), new EnchantedItemForEmeralds(Items.DIAMOND_BOOTS, 8, 3, 15, 0.2F) }Integer.valueOf(5), new ItemListing[] { new EnchantedItemForEmeralds(Items.DIAMOND_HELMET, 8, 3, 30, 0.2F), new EnchantedItemForEmeralds(Items.DIAMOND_CHESTPLATE, 16, 3, 30, 0.2F) })));
/*      */           $$0.put(VillagerProfession.WEAPONSMITH, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.COAL, 15, 16, 2), new ItemsForEmeralds(new ItemStack((ItemLike)Items.IRON_AXE), 3, 1, 12, 1, 0.2F), new EnchantedItemForEmeralds(Items.IRON_SWORD, 2, 3, 1) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.IRON_INGOT, 4, 12, 10), new ItemsForEmeralds(new ItemStack((ItemLike)Items.BELL), 36, 1, 12, 5, 0.2F) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.FLINT, 24, 12, 20) }Integer.valueOf(4), new ItemListing[] { new EmeraldForItems((ItemLike)Items.DIAMOND, 1, 12, 30), new EnchantedItemForEmeralds(Items.DIAMOND_AXE, 12, 3, 15, 0.2F) }Integer.valueOf(5), new ItemListing[] { new EnchantedItemForEmeralds(Items.DIAMOND_SWORD, 8, 3, 30, 0.2F) })));
/*      */           $$0.put(VillagerProfession.TOOLSMITH, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.COAL, 15, 16, 2), new ItemsForEmeralds(new ItemStack((ItemLike)Items.STONE_AXE), 1, 1, 12, 1, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.STONE_SHOVEL), 1, 1, 12, 1, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.STONE_PICKAXE), 1, 1, 12, 1, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.STONE_HOE), 1, 1, 12, 1, 0.2F) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.IRON_INGOT, 4, 12, 10), new ItemsForEmeralds(new ItemStack((ItemLike)Items.BELL), 36, 1, 12, 5, 0.2F) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.FLINT, 30, 12, 20), new EnchantedItemForEmeralds(Items.IRON_AXE, 1, 3, 10, 0.2F), new EnchantedItemForEmeralds(Items.IRON_SHOVEL, 2, 3, 10, 0.2F), new EnchantedItemForEmeralds(Items.IRON_PICKAXE, 3, 3, 10, 0.2F), new ItemsForEmeralds(new ItemStack((ItemLike)Items.DIAMOND_HOE), 4, 1, 3, 10, 0.2F) }Integer.valueOf(4), new ItemListing[] { new EmeraldForItems((ItemLike)Items.DIAMOND, 1, 12, 30), new EnchantedItemForEmeralds(Items.DIAMOND_AXE, 12, 3, 15, 0.2F), new EnchantedItemForEmeralds(Items.DIAMOND_SHOVEL, 5, 3, 15, 0.2F) }Integer.valueOf(5), new ItemListing[] { new EnchantedItemForEmeralds(Items.DIAMOND_PICKAXE, 13, 3, 30, 0.2F) })));
/*      */           $$0.put(VillagerProfession.BUTCHER, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.CHICKEN, 14, 16, 2), new EmeraldForItems((ItemLike)Items.PORKCHOP, 7, 16, 2), new EmeraldForItems((ItemLike)Items.RABBIT, 4, 16, 2), new ItemsForEmeralds(Items.RABBIT_STEW, 1, 1, 1) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.COAL, 15, 16, 2), new ItemsForEmeralds(Items.COOKED_PORKCHOP, 1, 5, 16, 5), new ItemsForEmeralds(Items.COOKED_CHICKEN, 1, 8, 16, 5) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.MUTTON, 7, 16, 20), new EmeraldForItems((ItemLike)Items.BEEF, 10, 16, 20) }Integer.valueOf(4), new ItemListing[] { new EmeraldForItems((ItemLike)Items.DRIED_KELP_BLOCK, 10, 12, 30) }Integer.valueOf(5), new ItemListing[] { new EmeraldForItems((ItemLike)Items.SWEET_BERRIES, 10, 12, 30) })));
/*      */           $$0.put(VillagerProfession.LEATHERWORKER, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.LEATHER, 6, 16, 2), new DyedArmorForEmeralds(Items.LEATHER_LEGGINGS, 3), new DyedArmorForEmeralds(Items.LEATHER_CHESTPLATE, 7) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.FLINT, 26, 12, 10), new DyedArmorForEmeralds(Items.LEATHER_HELMET, 5, 12, 5), new DyedArmorForEmeralds(Items.LEATHER_BOOTS, 4, 12, 5) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.RABBIT_HIDE, 9, 12, 20), new DyedArmorForEmeralds(Items.LEATHER_CHESTPLATE, 7) }Integer.valueOf(4), new ItemListing[] { new EmeraldForItems((ItemLike)Items.SCUTE, 4, 12, 30), new DyedArmorForEmeralds(Items.LEATHER_HORSE_ARMOR, 6, 12, 15) }Integer.valueOf(5), new ItemListing[] { new ItemsForEmeralds(new ItemStack((ItemLike)Items.SADDLE), 6, 1, 12, 30, 0.2F), new DyedArmorForEmeralds(Items.LEATHER_HELMET, 5, 12, 30) })));
/*      */           $$0.put(VillagerProfession.MASON, toIntMap(ImmutableMap.of(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.CLAY_BALL, 10, 16, 2), new ItemsForEmeralds(Items.BRICK, 1, 10, 16, 1) }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Blocks.STONE, 20, 16, 10), new ItemsForEmeralds(Blocks.CHISELED_STONE_BRICKS, 1, 4, 16, 5) }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Blocks.GRANITE, 16, 16, 20), new EmeraldForItems((ItemLike)Blocks.ANDESITE, 16, 16, 20), new EmeraldForItems((ItemLike)Blocks.DIORITE, 16, 16, 20), new ItemsForEmeralds(Blocks.DRIPSTONE_BLOCK, 1, 4, 16, 10), new ItemsForEmeralds(Blocks.POLISHED_ANDESITE, 1, 4, 16, 10), new ItemsForEmeralds(Blocks.POLISHED_DIORITE, 1, 4, 16, 10), new ItemsForEmeralds(Blocks.POLISHED_GRANITE, 1, 4, 16, 10) }Integer.valueOf(4), new ItemListing[] { 
/*      */                     new EmeraldForItems((ItemLike)Items.QUARTZ, 12, 12, 30), new ItemsForEmeralds(Blocks.ORANGE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.WHITE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BLUE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIGHT_BLUE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.GRAY_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIGHT_GRAY_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BLACK_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.RED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.PINK_TERRACOTTA, 1, 1, 12, 15), 
/*      */                     new ItemsForEmeralds(Blocks.MAGENTA_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIME_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.GREEN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.CYAN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.PURPLE_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.YELLOW_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BROWN_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.ORANGE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.WHITE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), 
/*      */                     new ItemsForEmeralds(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BLACK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.RED_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.PINK_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.MAGENTA_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.LIME_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.GREEN_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.CYAN_GLAZED_TERRACOTTA, 1, 1, 12, 15), 
/*      */                     new ItemsForEmeralds(Blocks.PURPLE_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.YELLOW_GLAZED_TERRACOTTA, 1, 1, 12, 15), new ItemsForEmeralds(Blocks.BROWN_GLAZED_TERRACOTTA, 1, 1, 12, 15) }Integer.valueOf(5), new ItemListing[] { new ItemsForEmeralds(Blocks.QUARTZ_PILLAR, 1, 1, 12, 30), new ItemsForEmeralds(Blocks.QUARTZ_BLOCK, 1, 1, 12, 30) })));
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  534 */   public static final Int2ObjectMap<ItemListing[]> WANDERING_TRADER_TRADES = toIntMap(ImmutableMap.of(
/*  535 */         Integer.valueOf(1), new ItemListing[] { new ItemsForEmeralds(Items.SEA_PICKLE, 2, 1, 5, 1), new ItemsForEmeralds(Items.SLIME_BALL, 4, 1, 5, 1), new ItemsForEmeralds(Items.GLOWSTONE, 2, 1, 5, 1), new ItemsForEmeralds(Items.NAUTILUS_SHELL, 5, 1, 5, 1), new ItemsForEmeralds(Items.FERN, 1, 1, 12, 1), new ItemsForEmeralds(Items.SUGAR_CANE, 1, 1, 8, 1), new ItemsForEmeralds(Items.PUMPKIN, 1, 1, 4, 1), new ItemsForEmeralds(Items.KELP, 3, 1, 12, 1), new ItemsForEmeralds(Items.CACTUS, 3, 1, 8, 1), new ItemsForEmeralds(Items.DANDELION, 1, 1, 12, 1), new ItemsForEmeralds(Items.POPPY, 1, 1, 12, 1), new ItemsForEmeralds(Items.BLUE_ORCHID, 1, 1, 8, 1), new ItemsForEmeralds(Items.ALLIUM, 1, 1, 12, 1), new ItemsForEmeralds(Items.AZURE_BLUET, 1, 1, 12, 1), new ItemsForEmeralds(Items.RED_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.ORANGE_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.WHITE_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.PINK_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.OXEYE_DAISY, 1, 1, 12, 1), new ItemsForEmeralds(Items.CORNFLOWER, 1, 1, 12, 1), new ItemsForEmeralds(Items.LILY_OF_THE_VALLEY, 1, 1, 7, 1), new ItemsForEmeralds(Items.WHEAT_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.BEETROOT_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.PUMPKIN_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.MELON_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.ACACIA_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.BIRCH_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.DARK_OAK_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.JUNGLE_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.OAK_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.SPRUCE_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.CHERRY_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.MANGROVE_PROPAGULE, 5, 1, 8, 1), new ItemsForEmeralds(Items.RED_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.WHITE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BLUE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.PINK_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BLACK_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.GREEN_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.LIGHT_GRAY_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.MAGENTA_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.YELLOW_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.GRAY_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.PURPLE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.LIGHT_BLUE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.LIME_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.ORANGE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BROWN_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.CYAN_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BRAIN_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.BUBBLE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.FIRE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.HORN_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.TUBE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.VINE, 1, 1, 12, 1), new ItemsForEmeralds(Items.BROWN_MUSHROOM, 1, 1, 12, 1), new ItemsForEmeralds(Items.RED_MUSHROOM, 1, 1, 12, 1), new ItemsForEmeralds(Items.LILY_PAD, 1, 2, 5, 1), new ItemsForEmeralds(Items.SMALL_DRIPLEAF, 1, 2, 5, 1), new ItemsForEmeralds(Items.SAND, 1, 8, 8, 1), new ItemsForEmeralds(Items.RED_SAND, 1, 4, 6, 1), new ItemsForEmeralds(Items.POINTED_DRIPSTONE, 1, 2, 5, 1), new ItemsForEmeralds(Items.ROOTED_DIRT, 1, 2, 5, 1), new ItemsForEmeralds(Items.MOSS_BLOCK, 1, 2, 5, 1)
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  601 */         }Integer.valueOf(2), new ItemListing[] { new ItemsForEmeralds(Items.TROPICAL_FISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PUFFERFISH_BUCKET, 5, 1, 4, 1), new ItemsForEmeralds(Items.PACKED_ICE, 3, 1, 6, 1), new ItemsForEmeralds(Items.BLUE_ICE, 6, 1, 6, 1), new ItemsForEmeralds(Items.GUNPOWDER, 1, 1, 8, 1), new ItemsForEmeralds(Items.PODZOL, 3, 3, 6, 1) }));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ItemListing commonBooks(int $$0) {
/*  612 */     return new TypeSpecificTrade(
/*  613 */         (Map<VillagerType, ItemListing>)ImmutableMap.builder()
/*  614 */         .put(VillagerType.DESERT, new EnchantBookForEmeralds($$0, new Enchantment[] { Enchantments.FIRE_PROTECTION, Enchantments.THORNS, Enchantments.INFINITY_ARROWS
/*  615 */             })).put(VillagerType.JUNGLE, new EnchantBookForEmeralds($$0, new Enchantment[] { Enchantments.FALL_PROTECTION, Enchantments.PROJECTILE_PROTECTION, Enchantments.POWER_ARROWS
/*  616 */             })).put(VillagerType.PLAINS, new EnchantBookForEmeralds($$0, new Enchantment[] { Enchantments.PUNCH_ARROWS, Enchantments.SMITE, Enchantments.BANE_OF_ARTHROPODS
/*  617 */             })).put(VillagerType.SAVANNA, new EnchantBookForEmeralds($$0, new Enchantment[] { Enchantments.KNOCKBACK, Enchantments.BINDING_CURSE, Enchantments.SWEEPING_EDGE
/*  618 */             })).put(VillagerType.SNOW, new EnchantBookForEmeralds($$0, new Enchantment[] { Enchantments.AQUA_AFFINITY, Enchantments.MOB_LOOTING, Enchantments.FROST_WALKER
/*  619 */             })).put(VillagerType.SWAMP, new EnchantBookForEmeralds($$0, new Enchantment[] { Enchantments.DEPTH_STRIDER, Enchantments.RESPIRATION, Enchantments.VANISHING_CURSE
/*  620 */             })).put(VillagerType.TAIGA, new EnchantBookForEmeralds($$0, new Enchantment[] { Enchantments.BLAST_PROTECTION, Enchantments.FIRE_ASPECT, Enchantments.FLAMING_ARROWS
/*  621 */             })).build());
/*      */   }
/*      */   
/*      */   private static ItemListing specialBooks() {
/*  625 */     return new TypeSpecificTrade(
/*  626 */         (Map<VillagerType, ItemListing>)ImmutableMap.builder()
/*  627 */         .put(VillagerType.DESERT, new EnchantBookForEmeralds(30, 3, 3, new Enchantment[] { Enchantments.BLOCK_EFFICIENCY
/*  628 */             })).put(VillagerType.JUNGLE, new EnchantBookForEmeralds(30, 2, 2, new Enchantment[] { Enchantments.UNBREAKING
/*  629 */             })).put(VillagerType.PLAINS, new EnchantBookForEmeralds(30, 3, 3, new Enchantment[] { Enchantments.ALL_DAMAGE_PROTECTION
/*  630 */             })).put(VillagerType.SAVANNA, new EnchantBookForEmeralds(30, 3, 3, new Enchantment[] { Enchantments.SHARPNESS
/*  631 */             })).put(VillagerType.SNOW, new EnchantBookForEmeralds(30, new Enchantment[] { Enchantments.SILK_TOUCH
/*  632 */             })).put(VillagerType.SWAMP, new EnchantBookForEmeralds(30, new Enchantment[] { Enchantments.MENDING
/*  633 */             })).put(VillagerType.TAIGA, new EnchantBookForEmeralds(30, 2, 2, new Enchantment[] { Enchantments.BLOCK_FORTUNE
/*  634 */             })).build());
/*      */   }
/*      */ 
/*      */   
/*  638 */   private static final TreasureMapForEmeralds DESERT_MAP = new TreasureMapForEmeralds(8, StructureTags.ON_DESERT_VILLAGE_MAPS, "filled_map.village_desert", MapDecoration.Type.DESERT_VILLAGE, 12, 5);
/*  639 */   private static final TreasureMapForEmeralds SAVANNA_MAP = new TreasureMapForEmeralds(8, StructureTags.ON_SAVANNA_VILLAGE_MAPS, "filled_map.village_savanna", MapDecoration.Type.SAVANNA_VILLAGE, 12, 5);
/*  640 */   private static final TreasureMapForEmeralds PLAINS_MAP = new TreasureMapForEmeralds(8, StructureTags.ON_PLAINS_VILLAGE_MAPS, "filled_map.village_plains", MapDecoration.Type.PLAINS_VILLAGE, 12, 5);
/*  641 */   private static final TreasureMapForEmeralds TAIGA_MAP = new TreasureMapForEmeralds(8, StructureTags.ON_TAIGA_VILLAGE_MAPS, "filled_map.village_taiga", MapDecoration.Type.TAIGA_VILLAGE, 12, 5);
/*  642 */   private static final TreasureMapForEmeralds SNOWY_MAP = new TreasureMapForEmeralds(8, StructureTags.ON_SNOWY_VILLAGE_MAPS, "filled_map.village_snowy", MapDecoration.Type.SNOWY_VILLAGE, 12, 5);
/*  643 */   private static final TreasureMapForEmeralds JUNGLE_MAP = new TreasureMapForEmeralds(8, StructureTags.ON_JUNGLE_EXPLORER_MAPS, "filled_map.explorer_jungle", MapDecoration.Type.JUNGLE_TEMPLE, 12, 5);
/*  644 */   private static final TreasureMapForEmeralds SWAMP_MAP = new TreasureMapForEmeralds(8, StructureTags.ON_SWAMP_EXPLORER_MAPS, "filled_map.explorer_swamp", MapDecoration.Type.SWAMP_HUT, 12, 5);
/*      */   
/*  646 */   public static final Map<VillagerProfession, Int2ObjectMap<ItemListing[]>> EXPERIMENTAL_TRADES = Map.of(VillagerProfession.LIBRARIAN, 
/*  647 */       toIntMap(ImmutableMap.builder()
/*  648 */         .put(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.PAPER, 24, 16, 2), 
/*      */             
/*  650 */             commonBooks(1), new ItemsForEmeralds(Blocks.BOOKSHELF, 9, 1, 12, 1)
/*      */ 
/*      */           
/*  653 */           }).put(Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.BOOK, 4, 12, 10), 
/*      */             
/*  655 */             commonBooks(5), new ItemsForEmeralds(Items.LANTERN, 1, 1, 5)
/*      */ 
/*      */           
/*  658 */           }).put(Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.INK_SAC, 5, 12, 20), 
/*      */             
/*  660 */             commonBooks(10), new ItemsForEmeralds(Items.GLASS, 1, 4, 10)
/*      */ 
/*      */           
/*  663 */           }).put(Integer.valueOf(4), new ItemListing[] { new EmeraldForItems((ItemLike)Items.WRITABLE_BOOK, 2, 12, 30), new ItemsForEmeralds(Items.CLOCK, 5, 1, 15), new ItemsForEmeralds(Items.COMPASS, 4, 1, 15)
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  668 */           }).put(Integer.valueOf(5), new ItemListing[] {
/*  669 */             specialBooks(), new ItemsForEmeralds(Items.NAME_TAG, 20, 1, 30)
/*      */ 
/*      */           
/*  672 */           }).build()), VillagerProfession.ARMORER, 
/*  673 */       toIntMap(ImmutableMap.builder()
/*  674 */         .put(Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.COAL, 15, 12, 2), new EmeraldForItems((ItemLike)Items.IRON_INGOT, 5, 12, 2)
/*      */ 
/*      */ 
/*      */           
/*  678 */           }).put(Integer.valueOf(2), new ItemListing[] {
/*  679 */             TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(Items.IRON_BOOTS, 4, 1, 12, 5, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.DESERT, VillagerType.PLAINS, VillagerType.SAVANNA, VillagerType.SNOW, VillagerType.TAIGA
/*      */               
/*  683 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(Items.CHAINMAIL_BOOTS, 4, 1, 12, 5, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.JUNGLE, VillagerType.SWAMP
/*      */               
/*  687 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(Items.IRON_HELMET, 5, 1, 12, 5, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.DESERT, VillagerType.PLAINS, VillagerType.SAVANNA, VillagerType.SNOW, VillagerType.TAIGA
/*      */               
/*  691 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(Items.CHAINMAIL_HELMET, 5, 1, 12, 5, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.JUNGLE, VillagerType.SWAMP
/*      */               
/*  695 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(Items.IRON_LEGGINGS, 7, 1, 12, 5, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.DESERT, VillagerType.PLAINS, VillagerType.SAVANNA, VillagerType.SNOW, VillagerType.TAIGA
/*      */               
/*  699 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(Items.CHAINMAIL_LEGGINGS, 7, 1, 12, 5, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.JUNGLE, VillagerType.SWAMP
/*      */               
/*  703 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(Items.IRON_CHESTPLATE, 9, 1, 12, 5, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.DESERT, VillagerType.PLAINS, VillagerType.SAVANNA, VillagerType.SNOW, VillagerType.TAIGA
/*      */               
/*  707 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(Items.CHAINMAIL_CHESTPLATE, 9, 1, 12, 5, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.JUNGLE, VillagerType.SWAMP
/*      */               })
/*      */           
/*  712 */           }).put(Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.LAVA_BUCKET, 1, 12, 20), new ItemsForEmeralds(Items.SHIELD, 5, 1, 12, 10, 0.05F), new ItemsForEmeralds(Items.BELL, 36, 1, 12, 10, 0.2F)
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  717 */           }).put(Integer.valueOf(4), new ItemListing[] { 
/*  718 */             TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  719 */                 enchant(Items.IRON_BOOTS, Enchantments.THORNS, 1), 8, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.DESERT
/*      */ 
/*      */               
/*  722 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  723 */                 enchant(Items.IRON_HELMET, Enchantments.THORNS, 1), 9, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.DESERT
/*      */ 
/*      */               
/*  726 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  727 */                 enchant(Items.IRON_LEGGINGS, Enchantments.THORNS, 1), 11, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.DESERT
/*      */ 
/*      */               
/*  730 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  731 */                 enchant(Items.IRON_CHESTPLATE, Enchantments.THORNS, 1), 13, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.DESERT
/*      */ 
/*      */               
/*  734 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  735 */                 enchant(Items.IRON_BOOTS, Enchantments.ALL_DAMAGE_PROTECTION, 1), 8, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.PLAINS
/*      */ 
/*      */               
/*  738 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  739 */                 enchant(Items.IRON_HELMET, Enchantments.ALL_DAMAGE_PROTECTION, 1), 9, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.PLAINS
/*      */ 
/*      */               
/*  742 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  743 */                 enchant(Items.IRON_LEGGINGS, Enchantments.ALL_DAMAGE_PROTECTION, 1), 11, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.PLAINS
/*      */ 
/*      */               
/*  746 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  747 */                 enchant(Items.IRON_CHESTPLATE, Enchantments.ALL_DAMAGE_PROTECTION, 1), 13, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.PLAINS
/*      */ 
/*      */               
/*  750 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  751 */                 enchant(Items.IRON_BOOTS, Enchantments.BINDING_CURSE, 1), 2, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SAVANNA
/*      */ 
/*      */               
/*  754 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  755 */                 enchant(Items.IRON_HELMET, Enchantments.BINDING_CURSE, 1), 3, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SAVANNA
/*      */               }),
/*      */             
/*  758 */             TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  759 */                 enchant(Items.IRON_LEGGINGS, Enchantments.BINDING_CURSE, 1), 5, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SAVANNA
/*      */ 
/*      */               
/*  762 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  763 */                 enchant(Items.IRON_CHESTPLATE, Enchantments.BINDING_CURSE, 1), 7, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SAVANNA
/*      */ 
/*      */               
/*  766 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  767 */                 enchant(Items.IRON_BOOTS, Enchantments.FROST_WALKER, 1), 8, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SNOW
/*      */ 
/*      */               
/*  770 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  771 */                 enchant(Items.IRON_HELMET, Enchantments.AQUA_AFFINITY, 1), 9, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SNOW
/*      */ 
/*      */               
/*  774 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  775 */                 enchant(Items.CHAINMAIL_BOOTS, Enchantments.UNBREAKING, 1), 8, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.JUNGLE
/*      */ 
/*      */               
/*  778 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  779 */                 enchant(Items.CHAINMAIL_HELMET, Enchantments.UNBREAKING, 1), 9, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.JUNGLE
/*      */ 
/*      */               
/*  782 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  783 */                 enchant(Items.CHAINMAIL_LEGGINGS, Enchantments.UNBREAKING, 1), 11, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.JUNGLE
/*      */ 
/*      */               
/*  786 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  787 */                 enchant(Items.CHAINMAIL_CHESTPLATE, Enchantments.UNBREAKING, 1), 13, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.JUNGLE
/*      */ 
/*      */               
/*  790 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  791 */                 enchant(Items.CHAINMAIL_BOOTS, Enchantments.MENDING, 1), 8, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SWAMP
/*      */ 
/*      */               
/*  794 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  795 */                 enchant(Items.CHAINMAIL_HELMET, Enchantments.MENDING, 1), 9, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SWAMP
/*      */               }),
/*      */             
/*  798 */             TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  799 */                 enchant(Items.CHAINMAIL_LEGGINGS, Enchantments.MENDING, 1), 11, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SWAMP
/*      */ 
/*      */               
/*  802 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  803 */                 enchant(Items.CHAINMAIL_CHESTPLATE, Enchantments.MENDING, 1), 13, 1, 3, 15, 0.05F), new VillagerType[] { VillagerType.SWAMP
/*      */ 
/*      */               
/*  806 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND_BOOTS, 1, 4, Items.DIAMOND_LEGGINGS, 1, 3, 15, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.TAIGA
/*      */               
/*  810 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND_LEGGINGS, 1, 4, Items.DIAMOND_CHESTPLATE, 1, 3, 15, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.TAIGA
/*      */               
/*  814 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND_HELMET, 1, 4, Items.DIAMOND_BOOTS, 1, 3, 15, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.TAIGA
/*      */               
/*  818 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND_CHESTPLATE, 1, 2, Items.DIAMOND_HELMET, 1, 3, 15, 0.05F), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.TAIGA
/*      */               
/*      */               })
/*  823 */           }).put(Integer.valueOf(5), new ItemListing[] { 
/*  824 */             TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 4, 16, 
/*  825 */                 enchant(Items.DIAMOND_CHESTPLATE, Enchantments.THORNS, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.DESERT
/*      */ 
/*      */               
/*  828 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 3, 16, 
/*  829 */                 enchant(Items.DIAMOND_LEGGINGS, Enchantments.THORNS, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.DESERT
/*      */ 
/*      */               
/*  832 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 3, 16, 
/*  833 */                 enchant(Items.DIAMOND_LEGGINGS, Enchantments.ALL_DAMAGE_PROTECTION, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.PLAINS
/*      */ 
/*      */               
/*  836 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 2, 12, 
/*  837 */                 enchant(Items.DIAMOND_BOOTS, Enchantments.ALL_DAMAGE_PROTECTION, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.PLAINS
/*      */ 
/*      */               
/*  840 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 2, 6, 
/*  841 */                 enchant(Items.DIAMOND_HELMET, Enchantments.BINDING_CURSE, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.SAVANNA
/*      */ 
/*      */               
/*  844 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 3, 8, 
/*  845 */                 enchant(Items.DIAMOND_CHESTPLATE, Enchantments.BINDING_CURSE, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.SAVANNA
/*      */ 
/*      */               
/*  848 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 2, 12, 
/*  849 */                 enchant(Items.DIAMOND_BOOTS, Enchantments.FROST_WALKER, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.SNOW
/*      */ 
/*      */               
/*  852 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 3, 12, 
/*  853 */                 enchant(Items.DIAMOND_HELMET, Enchantments.AQUA_AFFINITY, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.SNOW
/*      */ 
/*      */               
/*  856 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  857 */                 enchant(Items.CHAINMAIL_HELMET, Enchantments.PROJECTILE_PROTECTION, 1), 9, 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.JUNGLE
/*      */ 
/*      */               
/*  860 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  861 */                 enchant(Items.CHAINMAIL_BOOTS, Enchantments.FALL_PROTECTION, 1), 8, 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.JUNGLE
/*      */               }),
/*      */             
/*  864 */             TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  865 */                 enchant(Items.CHAINMAIL_HELMET, Enchantments.RESPIRATION, 1), 9, 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.SWAMP
/*      */ 
/*      */               
/*  868 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsForEmeralds(
/*  869 */                 enchant(Items.CHAINMAIL_BOOTS, Enchantments.DEPTH_STRIDER, 1), 8, 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.SWAMP
/*      */ 
/*      */               
/*  872 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 4, 18, 
/*  873 */                 enchant(Items.DIAMOND_CHESTPLATE, Enchantments.BLAST_PROTECTION, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.TAIGA
/*      */ 
/*      */               
/*  876 */               }), TypeSpecificTrade.oneTradeInBiomes(new ItemsAndEmeraldsToItems((ItemLike)Items.DIAMOND, 3, 18, 
/*  877 */                 enchant(Items.DIAMOND_LEGGINGS, Enchantments.BLAST_PROTECTION, 1), 1, 3, 30, 0.05F), new VillagerType[] { VillagerType.TAIGA
/*      */ 
/*      */               
/*  880 */               }), TypeSpecificTrade.oneTradeInBiomes(new EmeraldForItems((ItemLike)Items.DIAMOND_BLOCK, 1, 12, 30, 42), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.TAIGA
/*      */               
/*  884 */               }), TypeSpecificTrade.oneTradeInBiomes(new EmeraldForItems((ItemLike)Items.IRON_BLOCK, 1, 12, 30, 4), new VillagerType[] {
/*      */                 
/*      */                 VillagerType.DESERT, VillagerType.JUNGLE, VillagerType.PLAINS, VillagerType.SAVANNA, VillagerType.SNOW, VillagerType.SWAMP
/*      */               
/*      */               })
/*  889 */           }).build()), VillagerProfession.CARTOGRAPHER, 
/*      */       
/*  891 */       toIntMap(ImmutableMap.of(
/*  892 */           Integer.valueOf(1), new ItemListing[] { new EmeraldForItems((ItemLike)Items.PAPER, 24, 16, 2), new ItemsForEmeralds(Items.MAP, 7, 1, 1)
/*      */ 
/*      */ 
/*      */           
/*  896 */           }Integer.valueOf(2), new ItemListing[] { new EmeraldForItems((ItemLike)Items.GLASS_PANE, 11, 16, 10), new TypeSpecificTrade(
/*      */ 
/*      */ 
/*      */               
/*  900 */               (Map<VillagerType, ItemListing>)ImmutableMap.builder()
/*  901 */               .put(VillagerType.DESERT, SAVANNA_MAP)
/*  902 */               .put(VillagerType.SAVANNA, PLAINS_MAP)
/*  903 */               .put(VillagerType.PLAINS, TAIGA_MAP)
/*  904 */               .put(VillagerType.TAIGA, SNOWY_MAP)
/*  905 */               .put(VillagerType.SNOW, PLAINS_MAP)
/*  906 */               .put(VillagerType.JUNGLE, SAVANNA_MAP)
/*  907 */               .put(VillagerType.SWAMP, SNOWY_MAP)
/*  908 */               .build()), new TypeSpecificTrade(
/*      */ 
/*      */               
/*  911 */               (Map<VillagerType, ItemListing>)ImmutableMap.builder()
/*  912 */               .put(VillagerType.DESERT, PLAINS_MAP)
/*  913 */               .put(VillagerType.SAVANNA, DESERT_MAP)
/*  914 */               .put(VillagerType.PLAINS, SAVANNA_MAP)
/*  915 */               .put(VillagerType.TAIGA, PLAINS_MAP)
/*  916 */               .put(VillagerType.SNOW, TAIGA_MAP)
/*  917 */               .put(VillagerType.JUNGLE, DESERT_MAP)
/*  918 */               .put(VillagerType.SWAMP, TAIGA_MAP)
/*  919 */               .build()), new TypeSpecificTrade(
/*      */ 
/*      */               
/*  922 */               (Map<VillagerType, ItemListing>)ImmutableMap.builder()
/*  923 */               .put(VillagerType.DESERT, JUNGLE_MAP)
/*  924 */               .put(VillagerType.SAVANNA, JUNGLE_MAP)
/*  925 */               .put(VillagerType.PLAINS, new FailureItemListing())
/*  926 */               .put(VillagerType.TAIGA, SWAMP_MAP)
/*  927 */               .put(VillagerType.SNOW, SWAMP_MAP)
/*  928 */               .put(VillagerType.JUNGLE, SWAMP_MAP)
/*  929 */               .put(VillagerType.SWAMP, JUNGLE_MAP)
/*  930 */               .build())
/*      */           
/*  932 */           }Integer.valueOf(3), new ItemListing[] { new EmeraldForItems((ItemLike)Items.COMPASS, 1, 12, 20), new TreasureMapForEmeralds(13, StructureTags.ON_OCEAN_EXPLORER_MAPS, "filled_map.monument", MapDecoration.Type.MONUMENT, 12, 10)
/*      */ 
/*      */ 
/*      */           
/*  936 */           }Integer.valueOf(4), new ItemListing[] { new ItemsForEmeralds(Items.ITEM_FRAME, 7, 1, 15), new ItemsForEmeralds(Items.WHITE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.BLUE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.LIGHT_BLUE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.RED_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.PINK_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.GREEN_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.LIME_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.GRAY_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.BLACK_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.PURPLE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.MAGENTA_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.CYAN_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.BROWN_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.YELLOW_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.ORANGE_BANNER, 3, 1, 15), new ItemsForEmeralds(Items.LIGHT_GRAY_BANNER, 3, 1, 15)
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  955 */           }Integer.valueOf(5), new ItemListing[] { new ItemsForEmeralds(Items.GLOBE_BANNER_PATTERN, 8, 1, 30), new TreasureMapForEmeralds(14, StructureTags.ON_WOODLAND_EXPLORER_MAPS, "filled_map.mansion", MapDecoration.Type.MANSION, 1, 30) })));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  961 */   public static final List<Pair<ItemListing[], Integer>> EXPERIMENTAL_WANDERING_TRADER_TRADES = (List<Pair<ItemListing[], Integer>>)ImmutableList.builder()
/*  962 */     .add(Pair.of(new ItemListing[] {
/*  963 */           new EmeraldForItems(potion(Potions.WATER), 1, 1, 1), new EmeraldForItems((ItemLike)Items.WATER_BUCKET, 1, 1, 1, 2), new EmeraldForItems((ItemLike)Items.MILK_BUCKET, 1, 1, 1, 2), new EmeraldForItems((ItemLike)Items.FERMENTED_SPIDER_EYE, 1, 1, 1, 3), new EmeraldForItems((ItemLike)Items.BAKED_POTATO, 4, 1, 1), new EmeraldForItems((ItemLike)Items.HAY_BLOCK, 1, 1, 1)
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  969 */         }Integer.valueOf(2)))
/*  970 */     .add(Pair.of(new ItemListing[] {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           new ItemsForEmeralds(Items.PACKED_ICE, 1, 1, 6, 1), new ItemsForEmeralds(Items.BLUE_ICE, 6, 1, 6, 1), new ItemsForEmeralds(Items.GUNPOWDER, 1, 4, 2, 1), new ItemsForEmeralds(Items.PODZOL, 3, 3, 6, 1), new ItemsForEmeralds(Blocks.ACACIA_LOG, 1, 8, 4, 1), new ItemsForEmeralds(Blocks.BIRCH_LOG, 1, 8, 4, 1), new ItemsForEmeralds(Blocks.DARK_OAK_LOG, 1, 8, 4, 1), new ItemsForEmeralds(Blocks.JUNGLE_LOG, 1, 8, 4, 1), new ItemsForEmeralds(Blocks.OAK_LOG, 1, 8, 4, 1), new ItemsForEmeralds(Blocks.SPRUCE_LOG, 1, 8, 4, 1),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  983 */           new ItemsForEmeralds(Blocks.CHERRY_LOG, 1, 8, 4, 1), new EnchantedItemForEmeralds(Items.IRON_PICKAXE, 1, 1, 1, 0.2F), new ItemsForEmeralds(potion(Potions.LONG_INVISIBILITY), 5, 1, 1, 1)
/*  984 */         }Integer.valueOf(2)))
/*  985 */     .add(Pair.of(new ItemListing[] {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           new ItemsForEmeralds(Items.TROPICAL_FISH_BUCKET, 3, 1, 4, 1), new ItemsForEmeralds(Items.PUFFERFISH_BUCKET, 3, 1, 4, 1), new ItemsForEmeralds(Items.SEA_PICKLE, 2, 1, 5, 1), new ItemsForEmeralds(Items.SLIME_BALL, 4, 1, 5, 1), new ItemsForEmeralds(Items.GLOWSTONE, 2, 1, 5, 1), new ItemsForEmeralds(Items.NAUTILUS_SHELL, 5, 1, 5, 1), new ItemsForEmeralds(Items.FERN, 1, 1, 12, 1), new ItemsForEmeralds(Items.SUGAR_CANE, 1, 1, 8, 1), new ItemsForEmeralds(Items.PUMPKIN, 1, 1, 4, 1), new ItemsForEmeralds(Items.KELP, 3, 1, 12, 1),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           new ItemsForEmeralds(Items.CACTUS, 3, 1, 8, 1), new ItemsForEmeralds(Items.DANDELION, 1, 1, 12, 1), new ItemsForEmeralds(Items.POPPY, 1, 1, 12, 1), new ItemsForEmeralds(Items.BLUE_ORCHID, 1, 1, 8, 1), new ItemsForEmeralds(Items.ALLIUM, 1, 1, 12, 1), new ItemsForEmeralds(Items.AZURE_BLUET, 1, 1, 12, 1), new ItemsForEmeralds(Items.RED_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.ORANGE_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.WHITE_TULIP, 1, 1, 12, 1), new ItemsForEmeralds(Items.PINK_TULIP, 1, 1, 12, 1),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           new ItemsForEmeralds(Items.OXEYE_DAISY, 1, 1, 12, 1), new ItemsForEmeralds(Items.CORNFLOWER, 1, 1, 12, 1), new ItemsForEmeralds(Items.LILY_OF_THE_VALLEY, 1, 1, 7, 1), new ItemsForEmeralds(Items.WHEAT_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.BEETROOT_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.PUMPKIN_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.MELON_SEEDS, 1, 1, 12, 1), new ItemsForEmeralds(Items.ACACIA_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.BIRCH_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.DARK_OAK_SAPLING, 5, 1, 8, 1),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           new ItemsForEmeralds(Items.JUNGLE_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.OAK_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.SPRUCE_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.CHERRY_SAPLING, 5, 1, 8, 1), new ItemsForEmeralds(Items.MANGROVE_PROPAGULE, 5, 1, 8, 1), new ItemsForEmeralds(Items.RED_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.WHITE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BLUE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.PINK_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BLACK_DYE, 1, 3, 12, 1),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           new ItemsForEmeralds(Items.GREEN_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.LIGHT_GRAY_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.MAGENTA_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.YELLOW_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.GRAY_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.PURPLE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.LIGHT_BLUE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.LIME_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.ORANGE_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BROWN_DYE, 1, 3, 12, 1),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           new ItemsForEmeralds(Items.CYAN_DYE, 1, 3, 12, 1), new ItemsForEmeralds(Items.BRAIN_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.BUBBLE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.FIRE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.HORN_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.TUBE_CORAL_BLOCK, 3, 1, 8, 1), new ItemsForEmeralds(Items.VINE, 1, 3, 4, 1), new ItemsForEmeralds(Items.BROWN_MUSHROOM, 1, 3, 4, 1), new ItemsForEmeralds(Items.RED_MUSHROOM, 1, 3, 4, 1), new ItemsForEmeralds(Items.LILY_PAD, 1, 5, 2, 1),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           new ItemsForEmeralds(Items.SMALL_DRIPLEAF, 1, 2, 5, 1), new ItemsForEmeralds(Items.SAND, 1, 8, 8, 1), new ItemsForEmeralds(Items.RED_SAND, 1, 4, 6, 1), new ItemsForEmeralds(Items.POINTED_DRIPSTONE, 1, 2, 5, 1), new ItemsForEmeralds(Items.ROOTED_DIRT, 1, 2, 5, 1), new ItemsForEmeralds(Items.MOSS_BLOCK, 1, 2, 5, 1)
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1052 */         }Integer.valueOf(5)))
/* 1053 */     .build();
/*      */   
/*      */   private static Int2ObjectMap<ItemListing[]> toIntMap(ImmutableMap<Integer, ItemListing[]> $$0) {
/* 1056 */     return (Int2ObjectMap<ItemListing[]>)new Int2ObjectOpenHashMap((Map)$$0);
/*      */   }
/*      */ 
/*      */   
/*      */   private static class EmeraldForItems
/*      */     implements ItemListing
/*      */   {
/*      */     private final ItemStack itemStack;
/*      */     
/*      */     private final int maxUses;
/*      */     
/*      */     private final int villagerXp;
/*      */     private final int emeraldAmount;
/*      */     private final float priceMultiplier;
/*      */     
/*      */     public EmeraldForItems(ItemLike $$0, int $$1, int $$2, int $$3) {
/* 1072 */       this($$0, $$1, $$2, $$3, 1);
/*      */     }
/*      */     
/*      */     public EmeraldForItems(ItemLike $$0, int $$1, int $$2, int $$3, int $$4) {
/* 1076 */       this(new ItemStack((ItemLike)$$0.asItem(), $$1), $$2, $$3, $$4);
/*      */     }
/*      */     
/*      */     public EmeraldForItems(ItemStack $$0, int $$1, int $$2, int $$3) {
/* 1080 */       this.itemStack = $$0;
/* 1081 */       this.maxUses = $$1;
/* 1082 */       this.villagerXp = $$2;
/* 1083 */       this.emeraldAmount = $$3;
/* 1084 */       this.priceMultiplier = 0.05F;
/*      */     }
/*      */ 
/*      */     
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1089 */       return new MerchantOffer(this.itemStack.copy(), new ItemStack((ItemLike)Items.EMERALD, this.emeraldAmount), this.maxUses, this.villagerXp, this.priceMultiplier);
/*      */     } }
/*      */   private static final class TypeSpecificTrade extends Record implements ItemListing { private final Map<VillagerType, VillagerTrades.ItemListing> trades;
/*      */     
/* 1093 */     TypeSpecificTrade(Map<VillagerType, VillagerTrades.ItemListing> $$0) { this.trades = $$0; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/npc/VillagerTrades$TypeSpecificTrade;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1093	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/* 1093 */       //   0	7	0	this	Lnet/minecraft/world/entity/npc/VillagerTrades$TypeSpecificTrade; } public Map<VillagerType, VillagerTrades.ItemListing> trades() { return this.trades; }
/*      */     public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/npc/VillagerTrades$TypeSpecificTrade;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1093	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/npc/VillagerTrades$TypeSpecificTrade; }
/*      */     public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/npc/VillagerTrades$TypeSpecificTrade;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1093	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/npc/VillagerTrades$TypeSpecificTrade;
/*      */       //   0	8	1	$$0	Ljava/lang/Object; } public static TypeSpecificTrade oneTradeInBiomes(VillagerTrades.ItemListing $$0, VillagerType... $$1) {
/* 1096 */       return new TypeSpecificTrade((Map<VillagerType, VillagerTrades.ItemListing>)Arrays.<VillagerType>stream($$1).collect(Collectors.toMap($$0 -> $$0, $$1 -> $$0)));
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1102 */       if ($$0 instanceof VillagerDataHolder) { VillagerDataHolder $$2 = (VillagerDataHolder)$$0;
/* 1103 */         VillagerType $$3 = $$2.getVillagerData().getType();
/* 1104 */         VillagerTrades.ItemListing $$4 = this.trades.get($$3);
/* 1105 */         if ($$4 == null) {
/* 1106 */           return null;
/*      */         }
/* 1108 */         return $$4.getOffer($$0, $$1); }
/*      */       
/* 1110 */       return null;
/*      */     } }
/*      */ 
/*      */   
/*      */   private static class EmeraldsForVillagerTypeItem implements ItemListing {
/*      */     private final Map<VillagerType, Item> trades;
/*      */     private final int cost;
/*      */     private final int maxUses;
/*      */     private final int villagerXp;
/*      */     
/*      */     public EmeraldsForVillagerTypeItem(int $$0, int $$1, int $$2, Map<VillagerType, Item> $$3) {
/* 1121 */       BuiltInRegistries.VILLAGER_TYPE.stream().filter($$1 -> !$$0.containsKey($$1)).findAny().ifPresent($$0 -> {
/*      */             throw new IllegalStateException("Missing trade for villager type: " + BuiltInRegistries.VILLAGER_TYPE.getKey($$0));
/*      */           });
/* 1124 */       this.trades = $$3;
/*      */       
/* 1126 */       this.cost = $$0;
/* 1127 */       this.maxUses = $$1;
/* 1128 */       this.villagerXp = $$2;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1134 */       if ($$0 instanceof VillagerDataHolder) { VillagerDataHolder $$2 = (VillagerDataHolder)$$0;
/* 1135 */         ItemStack $$3 = new ItemStack((ItemLike)this.trades.get($$2.getVillagerData().getType()), this.cost);
/* 1136 */         return new MerchantOffer($$3, new ItemStack((ItemLike)Items.EMERALD), this.maxUses, this.villagerXp, 0.05F); }
/*      */       
/* 1138 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ItemsForEmeralds implements ItemListing {
/*      */     private final ItemStack itemStack;
/*      */     private final int emeraldCost;
/*      */     private final int maxUses;
/*      */     private final int villagerXp;
/*      */     private final float priceMultiplier;
/*      */     
/*      */     public ItemsForEmeralds(Block $$0, int $$1, int $$2, int $$3, int $$4) {
/* 1150 */       this(new ItemStack((ItemLike)$$0), $$1, $$2, $$3, $$4);
/*      */     }
/*      */     
/*      */     public ItemsForEmeralds(Item $$0, int $$1, int $$2, int $$3) {
/* 1154 */       this(new ItemStack((ItemLike)$$0), $$1, $$2, 12, $$3);
/*      */     }
/*      */     
/*      */     public ItemsForEmeralds(Item $$0, int $$1, int $$2, int $$3, int $$4) {
/* 1158 */       this(new ItemStack((ItemLike)$$0), $$1, $$2, $$3, $$4);
/*      */     }
/*      */     
/*      */     public ItemsForEmeralds(ItemStack $$0, int $$1, int $$2, int $$3, int $$4) {
/* 1162 */       this($$0, $$1, $$2, $$3, $$4, 0.05F);
/*      */     }
/*      */     
/*      */     public ItemsForEmeralds(Item $$0, int $$1, int $$2, int $$3, int $$4, float $$5) {
/* 1166 */       this(new ItemStack((ItemLike)$$0), $$1, $$2, $$3, $$4, $$5);
/*      */     }
/*      */     
/*      */     public ItemsForEmeralds(ItemStack $$0, int $$1, int $$2, int $$3, int $$4, float $$5) {
/* 1170 */       this.itemStack = $$0;
/* 1171 */       this.emeraldCost = $$1;
/* 1172 */       this.itemStack.setCount($$2);
/* 1173 */       this.maxUses = $$3;
/* 1174 */       this.villagerXp = $$4;
/* 1175 */       this.priceMultiplier = $$5;
/*      */     }
/*      */ 
/*      */     
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1180 */       return new MerchantOffer(new ItemStack((ItemLike)Items.EMERALD, this.emeraldCost), this.itemStack.copy(), this.maxUses, this.villagerXp, this.priceMultiplier);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class SuspiciousStewForEmerald implements ItemListing {
/*      */     private final List<SuspiciousEffectHolder.EffectEntry> effects;
/*      */     private final int xp;
/*      */     private final float priceMultiplier;
/*      */     
/*      */     public SuspiciousStewForEmerald(MobEffect $$0, int $$1, int $$2) {
/* 1190 */       this(List.of(new SuspiciousEffectHolder.EffectEntry($$0, $$1)), $$2, 0.05F);
/*      */     }
/*      */     
/*      */     public SuspiciousStewForEmerald(List<SuspiciousEffectHolder.EffectEntry> $$0, int $$1, float $$2) {
/* 1194 */       this.effects = $$0;
/* 1195 */       this.xp = $$1;
/* 1196 */       this.priceMultiplier = $$2;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1202 */       ItemStack $$2 = new ItemStack((ItemLike)Items.SUSPICIOUS_STEW, 1);
/* 1203 */       SuspiciousStewItem.saveMobEffects($$2, this.effects);
/* 1204 */       return new MerchantOffer(new ItemStack((ItemLike)Items.EMERALD, 1), $$2, 12, this.xp, this.priceMultiplier);
/*      */     }
/*      */   }
/*      */   
/*      */   private static ItemStack potion(Potion $$0) {
/* 1209 */     return PotionUtils.setPotion(new ItemStack((ItemLike)Items.POTION), $$0);
/*      */   }
/*      */   
/*      */   private static ItemStack enchant(Item $$0, Enchantment $$1, int $$2) {
/* 1213 */     ItemStack $$3 = new ItemStack((ItemLike)$$0);
/* 1214 */     $$3.enchant($$1, $$2);
/* 1215 */     return $$3;
/*      */   }
/*      */   
/*      */   private static class EnchantedItemForEmeralds implements ItemListing {
/*      */     private final ItemStack itemStack;
/*      */     private final int baseEmeraldCost;
/*      */     private final int maxUses;
/*      */     private final int villagerXp;
/*      */     private final float priceMultiplier;
/*      */     
/*      */     public EnchantedItemForEmeralds(Item $$0, int $$1, int $$2, int $$3) {
/* 1226 */       this($$0, $$1, $$2, $$3, 0.05F);
/*      */     }
/*      */     
/*      */     public EnchantedItemForEmeralds(Item $$0, int $$1, int $$2, int $$3, float $$4) {
/* 1230 */       this.itemStack = new ItemStack((ItemLike)$$0);
/* 1231 */       this.baseEmeraldCost = $$1;
/* 1232 */       this.maxUses = $$2;
/* 1233 */       this.villagerXp = $$3;
/* 1234 */       this.priceMultiplier = $$4;
/*      */     }
/*      */ 
/*      */     
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1239 */       int $$2 = 5 + $$1.nextInt(15);
/* 1240 */       ItemStack $$3 = EnchantmentHelper.enchantItem($$1, new ItemStack((ItemLike)this.itemStack.getItem()), $$2, false);
/* 1241 */       int $$4 = Math.min(this.baseEmeraldCost + $$2, 64);
/* 1242 */       ItemStack $$5 = new ItemStack((ItemLike)Items.EMERALD, $$4);
/*      */       
/* 1244 */       return new MerchantOffer($$5, $$3, this.maxUses, this.villagerXp, this.priceMultiplier);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class TippedArrowForItemsAndEmeralds implements ItemListing {
/*      */     private final ItemStack toItem;
/*      */     private final int toCount;
/*      */     private final int emeraldCost;
/*      */     private final int maxUses;
/*      */     private final int villagerXp;
/*      */     private final Item fromItem;
/*      */     private final int fromCount;
/*      */     private final float priceMultiplier;
/*      */     
/*      */     public TippedArrowForItemsAndEmeralds(Item $$0, int $$1, Item $$2, int $$3, int $$4, int $$5, int $$6) {
/* 1259 */       this.toItem = new ItemStack((ItemLike)$$2);
/* 1260 */       this.emeraldCost = $$4;
/* 1261 */       this.maxUses = $$5;
/* 1262 */       this.villagerXp = $$6;
/* 1263 */       this.fromItem = $$0;
/* 1264 */       this.fromCount = $$1;
/* 1265 */       this.toCount = $$3;
/* 1266 */       this.priceMultiplier = 0.05F;
/*      */     }
/*      */ 
/*      */     
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1271 */       ItemStack $$2 = new ItemStack((ItemLike)Items.EMERALD, this.emeraldCost);
/* 1272 */       List<Potion> $$3 = (List<Potion>)BuiltInRegistries.POTION.stream().filter($$0 -> (!$$0.getEffects().isEmpty() && PotionBrewing.isBrewablePotion($$0))).collect(Collectors.toList());
/* 1273 */       Potion $$4 = $$3.get($$1.nextInt($$3.size()));
/* 1274 */       ItemStack $$5 = PotionUtils.setPotion(new ItemStack((ItemLike)this.toItem.getItem(), this.toCount), $$4);
/*      */       
/* 1276 */       return new MerchantOffer($$2, new ItemStack((ItemLike)this.fromItem, this.fromCount), $$5, this.maxUses, this.villagerXp, this.priceMultiplier);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class DyedArmorForEmeralds implements ItemListing {
/*      */     private final Item item;
/*      */     private final int value;
/*      */     private final int maxUses;
/*      */     private final int villagerXp;
/*      */     
/*      */     public DyedArmorForEmeralds(Item $$0, int $$1) {
/* 1287 */       this($$0, $$1, 12, 1);
/*      */     }
/*      */     
/*      */     public DyedArmorForEmeralds(Item $$0, int $$1, int $$2, int $$3) {
/* 1291 */       this.item = $$0;
/* 1292 */       this.value = $$1;
/* 1293 */       this.maxUses = $$2;
/* 1294 */       this.villagerXp = $$3;
/*      */     }
/*      */ 
/*      */     
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1299 */       ItemStack $$2 = new ItemStack((ItemLike)Items.EMERALD, this.value);
/* 1300 */       ItemStack $$3 = new ItemStack((ItemLike)this.item);
/*      */       
/* 1302 */       if (this.item instanceof net.minecraft.world.item.DyeableArmorItem) {
/* 1303 */         List<DyeItem> $$4 = Lists.newArrayList();
/* 1304 */         $$4.add(getRandomDye($$1));
/*      */         
/* 1306 */         if ($$1.nextFloat() > 0.7F) {
/* 1307 */           $$4.add(getRandomDye($$1));
/*      */         }
/*      */         
/* 1310 */         if ($$1.nextFloat() > 0.8F) {
/* 1311 */           $$4.add(getRandomDye($$1));
/*      */         }
/*      */         
/* 1314 */         $$3 = DyeableLeatherItem.dyeArmor($$3, $$4);
/*      */       } 
/*      */       
/* 1317 */       return new MerchantOffer($$2, $$3, this.maxUses, this.villagerXp, 0.2F);
/*      */     }
/*      */     
/*      */     private static DyeItem getRandomDye(RandomSource $$0) {
/* 1321 */       return DyeItem.byColor(DyeColor.byId($$0.nextInt(16)));
/*      */     }
/*      */   }
/*      */   
/*      */   private static class EnchantBookForEmeralds
/*      */     implements ItemListing {
/*      */     private final int villagerXp;
/*      */     private final List<Enchantment> tradeableEnchantments;
/*      */     private final int minLevel;
/*      */     private final int maxLevel;
/*      */     
/*      */     public EnchantBookForEmeralds(int $$0) {
/* 1333 */       this($$0, (Enchantment[])BuiltInRegistries.ENCHANTMENT.stream().filter(Enchantment::isTradeable).toArray($$0 -> new Enchantment[$$0]));
/*      */     }
/*      */     
/*      */     public EnchantBookForEmeralds(int $$0, Enchantment... $$1) {
/* 1337 */       this($$0, 0, 2147483647, $$1);
/*      */     }
/*      */     
/*      */     public EnchantBookForEmeralds(int $$0, int $$1, int $$2, Enchantment... $$3) {
/* 1341 */       this.minLevel = $$1;
/* 1342 */       this.maxLevel = $$2;
/* 1343 */       this.villagerXp = $$0;
/* 1344 */       this.tradeableEnchantments = Arrays.asList($$3);
/*      */     }
/*      */ 
/*      */     
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1349 */       Enchantment $$2 = this.tradeableEnchantments.get($$1.nextInt(this.tradeableEnchantments.size()));
/* 1350 */       int $$3 = Math.max($$2.getMinLevel(), this.minLevel);
/* 1351 */       int $$4 = Math.min($$2.getMaxLevel(), this.maxLevel);
/* 1352 */       int $$5 = Mth.nextInt($$1, $$3, $$4);
/* 1353 */       ItemStack $$6 = EnchantedBookItem.createForEnchantment(new EnchantmentInstance($$2, $$5));
/* 1354 */       int $$7 = 2 + $$1.nextInt(5 + $$5 * 10) + 3 * $$5;
/* 1355 */       if ($$2.isTreasureOnly()) {
/* 1356 */         $$7 *= 2;
/*      */       }
/* 1358 */       if ($$7 > 64) {
/* 1359 */         $$7 = 64;
/*      */       }
/*      */       
/* 1362 */       return new MerchantOffer(new ItemStack((ItemLike)Items.EMERALD, $$7), new ItemStack((ItemLike)Items.BOOK), $$6, 12, this.villagerXp, 0.2F);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class FailureItemListing
/*      */     implements ItemListing {
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1369 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class TreasureMapForEmeralds implements ItemListing {
/*      */     private final int emeraldCost;
/*      */     private final TagKey<Structure> destination;
/*      */     private final String displayName;
/*      */     private final MapDecoration.Type destinationType;
/*      */     private final int maxUses;
/*      */     private final int villagerXp;
/*      */     
/*      */     public TreasureMapForEmeralds(int $$0, TagKey<Structure> $$1, String $$2, MapDecoration.Type $$3, int $$4, int $$5) {
/* 1382 */       this.emeraldCost = $$0;
/* 1383 */       this.destination = $$1;
/* 1384 */       this.displayName = $$2;
/* 1385 */       this.destinationType = $$3;
/* 1386 */       this.maxUses = $$4;
/* 1387 */       this.villagerXp = $$5;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1394 */       if (!($$0.level() instanceof ServerLevel)) {
/* 1395 */         return null;
/*      */       }
/*      */       
/* 1398 */       ServerLevel $$2 = (ServerLevel)$$0.level();
/* 1399 */       BlockPos $$3 = $$2.findNearestMapStructure(this.destination, $$0.blockPosition(), 100, true);
/* 1400 */       if ($$3 != null) {
/* 1401 */         ItemStack $$4 = MapItem.create((Level)$$2, $$3.getX(), $$3.getZ(), (byte)2, true, true);
/* 1402 */         MapItem.renderBiomePreviewMap($$2, $$4);
/* 1403 */         MapItemSavedData.addTargetDecoration($$4, $$3, "+", this.destinationType);
/* 1404 */         $$4.setHoverName((Component)Component.translatable(this.displayName));
/* 1405 */         return new MerchantOffer(new ItemStack((ItemLike)Items.EMERALD, this.emeraldCost), new ItemStack((ItemLike)Items.COMPASS), $$4, this.maxUses, this.villagerXp, 0.2F);
/*      */       } 
/* 1407 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ItemsAndEmeraldsToItems implements ItemListing {
/*      */     private final ItemStack fromItem;
/*      */     private final int emeraldCost;
/*      */     private final ItemStack toItem;
/*      */     private final int maxUses;
/*      */     private final int villagerXp;
/*      */     private final float priceMultiplier;
/*      */     
/*      */     public ItemsAndEmeraldsToItems(ItemLike $$0, int $$1, int $$2, Item $$3, int $$4, int $$5, int $$6, float $$7) {
/* 1420 */       this($$0, $$1, $$2, new ItemStack((ItemLike)$$3), $$4, $$5, $$6, $$7);
/*      */     }
/*      */     
/*      */     public ItemsAndEmeraldsToItems(ItemLike $$0, int $$1, int $$2, ItemStack $$3, int $$4, int $$5, int $$6, float $$7) {
/* 1424 */       this.fromItem = new ItemStack($$0, $$1);
/* 1425 */       this.emeraldCost = $$2;
/* 1426 */       this.toItem = $$3.copyWithCount($$4);
/* 1427 */       this.maxUses = $$5;
/* 1428 */       this.villagerXp = $$6;
/* 1429 */       this.priceMultiplier = $$7;
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public MerchantOffer getOffer(Entity $$0, RandomSource $$1) {
/* 1435 */       return new MerchantOffer(new ItemStack((ItemLike)Items.EMERALD, this.emeraldCost), this.fromItem.copy(), this.toItem.copy(), this.maxUses, this.villagerXp, this.priceMultiplier);
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface ItemListing {
/*      */     @Nullable
/*      */     MerchantOffer getOffer(Entity param1Entity, RandomSource param1RandomSource);
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\VillagerTrades.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */