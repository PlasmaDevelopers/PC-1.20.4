/*     */ package net.minecraft.data.models;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.data.models.model.ModelLocationUtils;
/*     */ import net.minecraft.data.models.model.ModelTemplate;
/*     */ import net.minecraft.data.models.model.ModelTemplates;
/*     */ import net.minecraft.data.models.model.TextureMapping;
/*     */ import net.minecraft.data.models.model.TextureSlot;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ArmorItem;
/*     */ import net.minecraft.world.item.ArmorMaterial;
/*     */ import net.minecraft.world.item.ArmorMaterials;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ 
/*     */ public class ItemModelGenerators
/*     */ {
/*  26 */   public static final ResourceLocation TRIM_TYPE_PREDICATE_ID = new ResourceLocation("trim_type");
/*     */   private static final class TrimModelData extends Record { private final String name; private final float itemModelIndex; private final Map<ArmorMaterial, String> overrideArmorMaterials;
/*  28 */     TrimModelData(String $$0, float $$1, Map<ArmorMaterial, String> $$2) { this.name = $$0; this.itemModelIndex = $$1; this.overrideArmorMaterials = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  28 */       //   0	7	0	this	Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;
/*  28 */       //   0	8	1	$$0	Ljava/lang/Object; } public float itemModelIndex() { return this.itemModelIndex; } public Map<ArmorMaterial, String> overrideArmorMaterials() { return this.overrideArmorMaterials; }
/*     */      public String name(ArmorMaterial $$0) {
/*  30 */       return this.overrideArmorMaterials.getOrDefault($$0, this.name);
/*     */     } }
/*     */ 
/*     */   
/*  34 */   private static final List<TrimModelData> GENERATED_TRIM_MODELS = List.of(new TrimModelData("quartz", 0.1F, 
/*  35 */         Map.of()), new TrimModelData("iron", 0.2F, 
/*  36 */         (Map)Map.of(ArmorMaterials.IRON, "iron_darker")), new TrimModelData("netherite", 0.3F, 
/*  37 */         (Map)Map.of(ArmorMaterials.NETHERITE, "netherite_darker")), new TrimModelData("redstone", 0.4F, 
/*  38 */         Map.of()), new TrimModelData("copper", 0.5F, 
/*  39 */         Map.of()), new TrimModelData("gold", 0.6F, 
/*  40 */         (Map)Map.of(ArmorMaterials.GOLD, "gold_darker")), new TrimModelData("emerald", 0.7F, 
/*  41 */         Map.of()), new TrimModelData("diamond", 0.8F, 
/*  42 */         (Map)Map.of(ArmorMaterials.DIAMOND, "diamond_darker")), new TrimModelData("lapis", 0.9F, 
/*  43 */         Map.of()), new TrimModelData("amethyst", 1.0F, 
/*  44 */         Map.of()));
/*     */   
/*     */   private final BiConsumer<ResourceLocation, Supplier<JsonElement>> output;
/*     */ 
/*     */   
/*     */   public ItemModelGenerators(BiConsumer<ResourceLocation, Supplier<JsonElement>> $$0) {
/*  50 */     this.output = $$0;
/*     */   }
/*     */   
/*     */   private void generateFlatItem(Item $$0, ModelTemplate $$1) {
/*  54 */     $$1.create(ModelLocationUtils.getModelLocation($$0), TextureMapping.layer0($$0), this.output);
/*     */   }
/*     */   
/*     */   private void generateFlatItem(Item $$0, String $$1, ModelTemplate $$2) {
/*  58 */     $$2.create(ModelLocationUtils.getModelLocation($$0, $$1), TextureMapping.layer0(TextureMapping.getItemTexture($$0, $$1)), this.output);
/*     */   }
/*     */   
/*     */   private void generateFlatItem(Item $$0, Item $$1, ModelTemplate $$2) {
/*  62 */     $$2.create(ModelLocationUtils.getModelLocation($$0), TextureMapping.layer0($$1), this.output);
/*     */   }
/*     */   
/*     */   private void generateCompassItem(Item $$0) {
/*  66 */     for (int $$1 = 0; $$1 < 32; $$1++) {
/*  67 */       if ($$1 != 16)
/*     */       {
/*     */         
/*  70 */         generateFlatItem($$0, String.format(Locale.ROOT, "_%02d", new Object[] { Integer.valueOf($$1) }), ModelTemplates.FLAT_ITEM); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateClockItem(Item $$0) {
/*  75 */     for (int $$1 = 1; $$1 < 64; $$1++) {
/*  76 */       generateFlatItem($$0, String.format(Locale.ROOT, "_%02d", new Object[] { Integer.valueOf($$1) }), ModelTemplates.FLAT_ITEM);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateLayeredItem(ResourceLocation $$0, ResourceLocation $$1, ResourceLocation $$2) {
/*  81 */     ModelTemplates.TWO_LAYERED_ITEM.create($$0, TextureMapping.layered($$1, $$2), this.output);
/*     */   }
/*     */   
/*     */   private void generateLayeredItem(ResourceLocation $$0, ResourceLocation $$1, ResourceLocation $$2, ResourceLocation $$3) {
/*  85 */     ModelTemplates.THREE_LAYERED_ITEM.create($$0, TextureMapping.layered($$1, $$2, $$3), this.output);
/*     */   }
/*     */   
/*     */   private ResourceLocation getItemModelForTrimMaterial(ResourceLocation $$0, String $$1) {
/*  89 */     return $$0.withSuffix("_" + $$1 + "_trim");
/*     */   }
/*     */   
/*     */   private JsonObject generateBaseArmorTrimTemplate(ResourceLocation $$0, Map<TextureSlot, ResourceLocation> $$1, ArmorMaterial $$2) {
/*  93 */     JsonObject $$3 = ModelTemplates.TWO_LAYERED_ITEM.createBaseTemplate($$0, $$1);
/*  94 */     JsonArray $$4 = new JsonArray();
/*     */     
/*  96 */     for (TrimModelData $$5 : GENERATED_TRIM_MODELS) {
/*  97 */       JsonObject $$6 = new JsonObject();
/*  98 */       JsonObject $$7 = new JsonObject();
/*     */       
/* 100 */       $$7.addProperty(TRIM_TYPE_PREDICATE_ID.getPath(), Float.valueOf($$5.itemModelIndex()));
/*     */       
/* 102 */       $$6.add("predicate", (JsonElement)$$7);
/* 103 */       $$6.addProperty("model", getItemModelForTrimMaterial($$0, $$5.name($$2)).toString());
/*     */       
/* 105 */       $$4.add((JsonElement)$$6);
/*     */     } 
/*     */     
/* 108 */     $$3.add("overrides", (JsonElement)$$4);
/* 109 */     return $$3;
/*     */   }
/*     */   
/*     */   private void generateArmorTrims(ArmorItem $$0) {
/* 113 */     ResourceLocation $$1 = ModelLocationUtils.getModelLocation((Item)$$0);
/* 114 */     ResourceLocation $$2 = TextureMapping.getItemTexture((Item)$$0);
/* 115 */     ResourceLocation $$3 = TextureMapping.getItemTexture((Item)$$0, "_overlay");
/*     */     
/* 117 */     if ($$0.getMaterial() == ArmorMaterials.LEATHER) {
/* 118 */       ModelTemplates.TWO_LAYERED_ITEM.create($$1, TextureMapping.layered($$2, $$3), this.output, ($$1, $$2) -> generateBaseArmorTrimTemplate($$1, $$2, $$0.getMaterial()));
/*     */     } else {
/* 120 */       ModelTemplates.FLAT_ITEM.create($$1, TextureMapping.layer0($$2), this.output, ($$1, $$2) -> generateBaseArmorTrimTemplate($$1, $$2, $$0.getMaterial()));
/*     */     } 
/*     */     
/* 123 */     for (TrimModelData $$4 : GENERATED_TRIM_MODELS) {
/* 124 */       String $$5 = $$4.name($$0.getMaterial());
/* 125 */       ResourceLocation $$6 = getItemModelForTrimMaterial($$1, $$5);
/* 126 */       String $$7 = $$0.getType().getName() + "_trim_" + $$0.getType().getName();
/* 127 */       ResourceLocation $$8 = (new ResourceLocation($$7)).withPrefix("trims/items/");
/*     */       
/* 129 */       if ($$0.getMaterial() == ArmorMaterials.LEATHER) {
/* 130 */         generateLayeredItem($$6, $$2, $$3, $$8); continue;
/*     */       } 
/* 132 */       generateLayeredItem($$6, $$2, $$8);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 138 */     generateFlatItem(Items.ACACIA_BOAT, ModelTemplates.FLAT_ITEM);
/* 139 */     generateFlatItem(Items.CHERRY_BOAT, ModelTemplates.FLAT_ITEM);
/* 140 */     generateFlatItem(Items.ACACIA_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
/* 141 */     generateFlatItem(Items.CHERRY_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
/* 142 */     generateFlatItem(Items.AMETHYST_SHARD, ModelTemplates.FLAT_ITEM);
/* 143 */     generateFlatItem(Items.APPLE, ModelTemplates.FLAT_ITEM);
/* 144 */     generateFlatItem(Items.ARMOR_STAND, ModelTemplates.FLAT_ITEM);
/* 145 */     generateFlatItem(Items.ARROW, ModelTemplates.FLAT_ITEM);
/* 146 */     generateFlatItem(Items.BAKED_POTATO, ModelTemplates.FLAT_ITEM);
/* 147 */     generateFlatItem(Items.BAMBOO, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 148 */     generateFlatItem(Items.BEEF, ModelTemplates.FLAT_ITEM);
/* 149 */     generateFlatItem(Items.BEETROOT, ModelTemplates.FLAT_ITEM);
/* 150 */     generateFlatItem(Items.BEETROOT_SOUP, ModelTemplates.FLAT_ITEM);
/* 151 */     generateFlatItem(Items.BIRCH_BOAT, ModelTemplates.FLAT_ITEM);
/* 152 */     generateFlatItem(Items.BIRCH_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
/* 153 */     generateFlatItem(Items.BLACK_DYE, ModelTemplates.FLAT_ITEM);
/* 154 */     generateFlatItem(Items.BLAZE_POWDER, ModelTemplates.FLAT_ITEM);
/* 155 */     generateFlatItem(Items.BLAZE_ROD, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 156 */     generateFlatItem(Items.BLUE_DYE, ModelTemplates.FLAT_ITEM);
/* 157 */     generateFlatItem(Items.BONE_MEAL, ModelTemplates.FLAT_ITEM);
/* 158 */     generateFlatItem(Items.BOOK, ModelTemplates.FLAT_ITEM);
/* 159 */     generateFlatItem(Items.BOWL, ModelTemplates.FLAT_ITEM);
/* 160 */     generateFlatItem(Items.BREAD, ModelTemplates.FLAT_ITEM);
/* 161 */     generateFlatItem(Items.BRICK, ModelTemplates.FLAT_ITEM);
/* 162 */     generateFlatItem(Items.BROWN_DYE, ModelTemplates.FLAT_ITEM);
/* 163 */     generateFlatItem(Items.BUCKET, ModelTemplates.FLAT_ITEM);
/* 164 */     generateFlatItem(Items.CARROT_ON_A_STICK, ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
/* 165 */     generateFlatItem(Items.WARPED_FUNGUS_ON_A_STICK, ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
/* 166 */     generateFlatItem(Items.CHARCOAL, ModelTemplates.FLAT_ITEM);
/* 167 */     generateFlatItem(Items.CHEST_MINECART, ModelTemplates.FLAT_ITEM);
/* 168 */     generateFlatItem(Items.CHICKEN, ModelTemplates.FLAT_ITEM);
/* 169 */     generateFlatItem(Items.CHORUS_FRUIT, ModelTemplates.FLAT_ITEM);
/* 170 */     generateFlatItem(Items.CLAY_BALL, ModelTemplates.FLAT_ITEM);
/*     */     
/* 172 */     generateClockItem(Items.CLOCK);
/*     */     
/* 174 */     generateFlatItem(Items.COAL, ModelTemplates.FLAT_ITEM);
/* 175 */     generateFlatItem(Items.COD_BUCKET, ModelTemplates.FLAT_ITEM);
/* 176 */     generateFlatItem(Items.COMMAND_BLOCK_MINECART, ModelTemplates.FLAT_ITEM);
/*     */     
/* 178 */     generateCompassItem(Items.COMPASS);
/* 179 */     generateCompassItem(Items.RECOVERY_COMPASS);
/*     */     
/* 181 */     generateFlatItem(Items.COOKED_BEEF, ModelTemplates.FLAT_ITEM);
/* 182 */     generateFlatItem(Items.COOKED_CHICKEN, ModelTemplates.FLAT_ITEM);
/* 183 */     generateFlatItem(Items.COOKED_COD, ModelTemplates.FLAT_ITEM);
/* 184 */     generateFlatItem(Items.COOKED_MUTTON, ModelTemplates.FLAT_ITEM);
/* 185 */     generateFlatItem(Items.COOKED_PORKCHOP, ModelTemplates.FLAT_ITEM);
/* 186 */     generateFlatItem(Items.COOKED_RABBIT, ModelTemplates.FLAT_ITEM);
/* 187 */     generateFlatItem(Items.COOKED_SALMON, ModelTemplates.FLAT_ITEM);
/* 188 */     generateFlatItem(Items.COOKIE, ModelTemplates.FLAT_ITEM);
/* 189 */     generateFlatItem(Items.RAW_COPPER, ModelTemplates.FLAT_ITEM);
/* 190 */     generateFlatItem(Items.COPPER_INGOT, ModelTemplates.FLAT_ITEM);
/* 191 */     generateFlatItem(Items.CREEPER_BANNER_PATTERN, ModelTemplates.FLAT_ITEM);
/* 192 */     generateFlatItem(Items.CYAN_DYE, ModelTemplates.FLAT_ITEM);
/* 193 */     generateFlatItem(Items.DARK_OAK_BOAT, ModelTemplates.FLAT_ITEM);
/* 194 */     generateFlatItem(Items.DARK_OAK_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
/* 195 */     generateFlatItem(Items.DIAMOND, ModelTemplates.FLAT_ITEM);
/* 196 */     generateFlatItem(Items.DIAMOND_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 197 */     generateFlatItem(Items.DIAMOND_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 198 */     generateFlatItem(Items.DIAMOND_HORSE_ARMOR, ModelTemplates.FLAT_ITEM);
/* 199 */     generateFlatItem(Items.DIAMOND_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 200 */     generateFlatItem(Items.DIAMOND_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 201 */     generateFlatItem(Items.DIAMOND_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 202 */     generateFlatItem(Items.DRAGON_BREATH, ModelTemplates.FLAT_ITEM);
/* 203 */     generateFlatItem(Items.DRIED_KELP, ModelTemplates.FLAT_ITEM);
/* 204 */     generateFlatItem(Items.EGG, ModelTemplates.FLAT_ITEM);
/* 205 */     generateFlatItem(Items.EMERALD, ModelTemplates.FLAT_ITEM);
/* 206 */     generateFlatItem(Items.ENCHANTED_BOOK, ModelTemplates.FLAT_ITEM);
/* 207 */     generateFlatItem(Items.ENDER_EYE, ModelTemplates.FLAT_ITEM);
/* 208 */     generateFlatItem(Items.ENDER_PEARL, ModelTemplates.FLAT_ITEM);
/* 209 */     generateFlatItem(Items.END_CRYSTAL, ModelTemplates.FLAT_ITEM);
/* 210 */     generateFlatItem(Items.EXPERIENCE_BOTTLE, ModelTemplates.FLAT_ITEM);
/* 211 */     generateFlatItem(Items.FERMENTED_SPIDER_EYE, ModelTemplates.FLAT_ITEM);
/* 212 */     generateFlatItem(Items.FIREWORK_ROCKET, ModelTemplates.FLAT_ITEM);
/* 213 */     generateFlatItem(Items.FIRE_CHARGE, ModelTemplates.FLAT_ITEM);
/* 214 */     generateFlatItem(Items.FLINT, ModelTemplates.FLAT_ITEM);
/* 215 */     generateFlatItem(Items.FLINT_AND_STEEL, ModelTemplates.FLAT_ITEM);
/* 216 */     generateFlatItem(Items.FLOWER_BANNER_PATTERN, ModelTemplates.FLAT_ITEM);
/* 217 */     generateFlatItem(Items.FURNACE_MINECART, ModelTemplates.FLAT_ITEM);
/* 218 */     generateFlatItem(Items.GHAST_TEAR, ModelTemplates.FLAT_ITEM);
/* 219 */     generateFlatItem(Items.GLASS_BOTTLE, ModelTemplates.FLAT_ITEM);
/* 220 */     generateFlatItem(Items.GLISTERING_MELON_SLICE, ModelTemplates.FLAT_ITEM);
/* 221 */     generateFlatItem(Items.GLOBE_BANNER_PATTERN, ModelTemplates.FLAT_ITEM);
/* 222 */     generateFlatItem(Items.GLOW_BERRIES, ModelTemplates.FLAT_ITEM);
/* 223 */     generateFlatItem(Items.GLOWSTONE_DUST, ModelTemplates.FLAT_ITEM);
/* 224 */     generateFlatItem(Items.GLOW_INK_SAC, ModelTemplates.FLAT_ITEM);
/* 225 */     generateFlatItem(Items.GLOW_ITEM_FRAME, ModelTemplates.FLAT_ITEM);
/* 226 */     generateFlatItem(Items.RAW_GOLD, ModelTemplates.FLAT_ITEM);
/* 227 */     generateFlatItem(Items.GOLDEN_APPLE, ModelTemplates.FLAT_ITEM);
/* 228 */     generateFlatItem(Items.GOLDEN_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 229 */     generateFlatItem(Items.GOLDEN_CARROT, ModelTemplates.FLAT_ITEM);
/* 230 */     generateFlatItem(Items.GOLDEN_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 231 */     generateFlatItem(Items.GOLDEN_HORSE_ARMOR, ModelTemplates.FLAT_ITEM);
/* 232 */     generateFlatItem(Items.GOLDEN_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 233 */     generateFlatItem(Items.GOLDEN_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 234 */     generateFlatItem(Items.GOLDEN_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 235 */     generateFlatItem(Items.GOLD_INGOT, ModelTemplates.FLAT_ITEM);
/* 236 */     generateFlatItem(Items.GOLD_NUGGET, ModelTemplates.FLAT_ITEM);
/* 237 */     generateFlatItem(Items.GRAY_DYE, ModelTemplates.FLAT_ITEM);
/* 238 */     generateFlatItem(Items.GREEN_DYE, ModelTemplates.FLAT_ITEM);
/* 239 */     generateFlatItem(Items.GUNPOWDER, ModelTemplates.FLAT_ITEM);
/* 240 */     generateFlatItem(Items.HEART_OF_THE_SEA, ModelTemplates.FLAT_ITEM);
/* 241 */     generateFlatItem(Items.HONEYCOMB, ModelTemplates.FLAT_ITEM);
/* 242 */     generateFlatItem(Items.HONEY_BOTTLE, ModelTemplates.FLAT_ITEM);
/* 243 */     generateFlatItem(Items.HOPPER_MINECART, ModelTemplates.FLAT_ITEM);
/* 244 */     generateFlatItem(Items.INK_SAC, ModelTemplates.FLAT_ITEM);
/* 245 */     generateFlatItem(Items.RAW_IRON, ModelTemplates.FLAT_ITEM);
/* 246 */     generateFlatItem(Items.IRON_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 247 */     generateFlatItem(Items.IRON_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 248 */     generateFlatItem(Items.IRON_HORSE_ARMOR, ModelTemplates.FLAT_ITEM);
/* 249 */     generateFlatItem(Items.IRON_INGOT, ModelTemplates.FLAT_ITEM);
/* 250 */     generateFlatItem(Items.IRON_NUGGET, ModelTemplates.FLAT_ITEM);
/* 251 */     generateFlatItem(Items.IRON_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 252 */     generateFlatItem(Items.IRON_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 253 */     generateFlatItem(Items.IRON_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 254 */     generateFlatItem(Items.ITEM_FRAME, ModelTemplates.FLAT_ITEM);
/* 255 */     generateFlatItem(Items.JUNGLE_BOAT, ModelTemplates.FLAT_ITEM);
/* 256 */     generateFlatItem(Items.JUNGLE_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
/* 257 */     generateFlatItem(Items.KNOWLEDGE_BOOK, ModelTemplates.FLAT_ITEM);
/* 258 */     generateFlatItem(Items.LAPIS_LAZULI, ModelTemplates.FLAT_ITEM);
/* 259 */     generateFlatItem(Items.LAVA_BUCKET, ModelTemplates.FLAT_ITEM);
/* 260 */     generateFlatItem(Items.LEATHER, ModelTemplates.FLAT_ITEM);
/* 261 */     generateFlatItem(Items.LEATHER_HORSE_ARMOR, ModelTemplates.FLAT_ITEM);
/* 262 */     generateFlatItem(Items.LIGHT_BLUE_DYE, ModelTemplates.FLAT_ITEM);
/* 263 */     generateFlatItem(Items.LIGHT_GRAY_DYE, ModelTemplates.FLAT_ITEM);
/* 264 */     generateFlatItem(Items.LIME_DYE, ModelTemplates.FLAT_ITEM);
/* 265 */     generateFlatItem(Items.MAGENTA_DYE, ModelTemplates.FLAT_ITEM);
/* 266 */     generateFlatItem(Items.MAGMA_CREAM, ModelTemplates.FLAT_ITEM);
/* 267 */     generateFlatItem(Items.MANGROVE_BOAT, ModelTemplates.FLAT_ITEM);
/* 268 */     generateFlatItem(Items.MANGROVE_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
/* 269 */     generateFlatItem(Items.BAMBOO_RAFT, ModelTemplates.FLAT_ITEM);
/* 270 */     generateFlatItem(Items.BAMBOO_CHEST_RAFT, ModelTemplates.FLAT_ITEM);
/* 271 */     generateFlatItem(Items.MAP, ModelTemplates.FLAT_ITEM);
/* 272 */     generateFlatItem(Items.MELON_SLICE, ModelTemplates.FLAT_ITEM);
/* 273 */     generateFlatItem(Items.MILK_BUCKET, ModelTemplates.FLAT_ITEM);
/* 274 */     generateFlatItem(Items.MINECART, ModelTemplates.FLAT_ITEM);
/* 275 */     generateFlatItem(Items.MOJANG_BANNER_PATTERN, ModelTemplates.FLAT_ITEM);
/* 276 */     generateFlatItem(Items.MUSHROOM_STEW, ModelTemplates.FLAT_ITEM);
/* 277 */     generateFlatItem(Items.DISC_FRAGMENT_5, ModelTemplates.FLAT_ITEM);
/* 278 */     generateFlatItem(Items.MUSIC_DISC_11, ModelTemplates.MUSIC_DISC);
/* 279 */     generateFlatItem(Items.MUSIC_DISC_13, ModelTemplates.MUSIC_DISC);
/* 280 */     generateFlatItem(Items.MUSIC_DISC_BLOCKS, ModelTemplates.MUSIC_DISC);
/* 281 */     generateFlatItem(Items.MUSIC_DISC_CAT, ModelTemplates.MUSIC_DISC);
/* 282 */     generateFlatItem(Items.MUSIC_DISC_CHIRP, ModelTemplates.MUSIC_DISC);
/* 283 */     generateFlatItem(Items.MUSIC_DISC_FAR, ModelTemplates.MUSIC_DISC);
/* 284 */     generateFlatItem(Items.MUSIC_DISC_MALL, ModelTemplates.MUSIC_DISC);
/* 285 */     generateFlatItem(Items.MUSIC_DISC_MELLOHI, ModelTemplates.MUSIC_DISC);
/* 286 */     generateFlatItem(Items.MUSIC_DISC_PIGSTEP, ModelTemplates.MUSIC_DISC);
/* 287 */     generateFlatItem(Items.MUSIC_DISC_STAL, ModelTemplates.MUSIC_DISC);
/* 288 */     generateFlatItem(Items.MUSIC_DISC_STRAD, ModelTemplates.MUSIC_DISC);
/* 289 */     generateFlatItem(Items.MUSIC_DISC_WAIT, ModelTemplates.MUSIC_DISC);
/* 290 */     generateFlatItem(Items.MUSIC_DISC_WARD, ModelTemplates.MUSIC_DISC);
/* 291 */     generateFlatItem(Items.MUSIC_DISC_OTHERSIDE, ModelTemplates.MUSIC_DISC);
/* 292 */     generateFlatItem(Items.MUSIC_DISC_RELIC, ModelTemplates.MUSIC_DISC);
/* 293 */     generateFlatItem(Items.MUSIC_DISC_5, ModelTemplates.MUSIC_DISC);
/* 294 */     generateFlatItem(Items.MUTTON, ModelTemplates.FLAT_ITEM);
/* 295 */     generateFlatItem(Items.NAME_TAG, ModelTemplates.FLAT_ITEM);
/* 296 */     generateFlatItem(Items.NAUTILUS_SHELL, ModelTemplates.FLAT_ITEM);
/* 297 */     generateFlatItem(Items.NETHERITE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 298 */     generateFlatItem(Items.NETHERITE_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 299 */     generateFlatItem(Items.NETHERITE_INGOT, ModelTemplates.FLAT_ITEM);
/* 300 */     generateFlatItem(Items.NETHERITE_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 301 */     generateFlatItem(Items.NETHERITE_SCRAP, ModelTemplates.FLAT_ITEM);
/* 302 */     generateFlatItem(Items.NETHERITE_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 303 */     generateFlatItem(Items.NETHERITE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 304 */     generateFlatItem(Items.NETHER_BRICK, ModelTemplates.FLAT_ITEM);
/* 305 */     generateFlatItem(Items.NETHER_STAR, ModelTemplates.FLAT_ITEM);
/* 306 */     generateFlatItem(Items.OAK_BOAT, ModelTemplates.FLAT_ITEM);
/* 307 */     generateFlatItem(Items.OAK_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
/* 308 */     generateFlatItem(Items.ORANGE_DYE, ModelTemplates.FLAT_ITEM);
/* 309 */     generateFlatItem(Items.PAINTING, ModelTemplates.FLAT_ITEM);
/* 310 */     generateFlatItem(Items.PAPER, ModelTemplates.FLAT_ITEM);
/* 311 */     generateFlatItem(Items.PHANTOM_MEMBRANE, ModelTemplates.FLAT_ITEM);
/* 312 */     generateFlatItem(Items.PIGLIN_BANNER_PATTERN, ModelTemplates.FLAT_ITEM);
/* 313 */     generateFlatItem(Items.PINK_DYE, ModelTemplates.FLAT_ITEM);
/* 314 */     generateFlatItem(Items.POISONOUS_POTATO, ModelTemplates.FLAT_ITEM);
/* 315 */     generateFlatItem(Items.POPPED_CHORUS_FRUIT, ModelTemplates.FLAT_ITEM);
/* 316 */     generateFlatItem(Items.PORKCHOP, ModelTemplates.FLAT_ITEM);
/* 317 */     generateFlatItem(Items.POWDER_SNOW_BUCKET, ModelTemplates.FLAT_ITEM);
/* 318 */     generateFlatItem(Items.PRISMARINE_CRYSTALS, ModelTemplates.FLAT_ITEM);
/* 319 */     generateFlatItem(Items.PRISMARINE_SHARD, ModelTemplates.FLAT_ITEM);
/* 320 */     generateFlatItem(Items.PUFFERFISH, ModelTemplates.FLAT_ITEM);
/* 321 */     generateFlatItem(Items.PUFFERFISH_BUCKET, ModelTemplates.FLAT_ITEM);
/* 322 */     generateFlatItem(Items.PUMPKIN_PIE, ModelTemplates.FLAT_ITEM);
/* 323 */     generateFlatItem(Items.PURPLE_DYE, ModelTemplates.FLAT_ITEM);
/* 324 */     generateFlatItem(Items.QUARTZ, ModelTemplates.FLAT_ITEM);
/* 325 */     generateFlatItem(Items.RABBIT, ModelTemplates.FLAT_ITEM);
/* 326 */     generateFlatItem(Items.RABBIT_FOOT, ModelTemplates.FLAT_ITEM);
/* 327 */     generateFlatItem(Items.RABBIT_HIDE, ModelTemplates.FLAT_ITEM);
/* 328 */     generateFlatItem(Items.RABBIT_STEW, ModelTemplates.FLAT_ITEM);
/* 329 */     generateFlatItem(Items.RED_DYE, ModelTemplates.FLAT_ITEM);
/* 330 */     generateFlatItem(Items.ROTTEN_FLESH, ModelTemplates.FLAT_ITEM);
/* 331 */     generateFlatItem(Items.SADDLE, ModelTemplates.FLAT_ITEM);
/* 332 */     generateFlatItem(Items.SALMON, ModelTemplates.FLAT_ITEM);
/* 333 */     generateFlatItem(Items.SALMON_BUCKET, ModelTemplates.FLAT_ITEM);
/* 334 */     generateFlatItem(Items.SCUTE, ModelTemplates.FLAT_ITEM);
/* 335 */     generateFlatItem(Items.SHEARS, ModelTemplates.FLAT_ITEM);
/* 336 */     generateFlatItem(Items.SHULKER_SHELL, ModelTemplates.FLAT_ITEM);
/* 337 */     generateFlatItem(Items.SKULL_BANNER_PATTERN, ModelTemplates.FLAT_ITEM);
/* 338 */     generateFlatItem(Items.SLIME_BALL, ModelTemplates.FLAT_ITEM);
/* 339 */     generateFlatItem(Items.SNOWBALL, ModelTemplates.FLAT_ITEM);
/* 340 */     generateFlatItem(Items.ECHO_SHARD, ModelTemplates.FLAT_ITEM);
/* 341 */     generateFlatItem(Items.SPECTRAL_ARROW, ModelTemplates.FLAT_ITEM);
/* 342 */     generateFlatItem(Items.SPIDER_EYE, ModelTemplates.FLAT_ITEM);
/* 343 */     generateFlatItem(Items.SPRUCE_BOAT, ModelTemplates.FLAT_ITEM);
/* 344 */     generateFlatItem(Items.SPRUCE_CHEST_BOAT, ModelTemplates.FLAT_ITEM);
/* 345 */     generateFlatItem(Items.SPYGLASS, ModelTemplates.FLAT_ITEM);
/* 346 */     generateFlatItem(Items.STICK, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 347 */     generateFlatItem(Items.STONE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 348 */     generateFlatItem(Items.STONE_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 349 */     generateFlatItem(Items.STONE_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 350 */     generateFlatItem(Items.STONE_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 351 */     generateFlatItem(Items.STONE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 352 */     generateFlatItem(Items.SUGAR, ModelTemplates.FLAT_ITEM);
/* 353 */     generateFlatItem(Items.SUSPICIOUS_STEW, ModelTemplates.FLAT_ITEM);
/* 354 */     generateFlatItem(Items.TNT_MINECART, ModelTemplates.FLAT_ITEM);
/* 355 */     generateFlatItem(Items.TOTEM_OF_UNDYING, ModelTemplates.FLAT_ITEM);
/* 356 */     generateFlatItem(Items.TRIDENT, ModelTemplates.FLAT_ITEM);
/* 357 */     generateFlatItem(Items.TROPICAL_FISH, ModelTemplates.FLAT_ITEM);
/* 358 */     generateFlatItem(Items.TROPICAL_FISH_BUCKET, ModelTemplates.FLAT_ITEM);
/* 359 */     generateFlatItem(Items.AXOLOTL_BUCKET, ModelTemplates.FLAT_ITEM);
/* 360 */     generateFlatItem(Items.TADPOLE_BUCKET, ModelTemplates.FLAT_ITEM);
/* 361 */     generateFlatItem(Items.WATER_BUCKET, ModelTemplates.FLAT_ITEM);
/* 362 */     generateFlatItem(Items.WHEAT, ModelTemplates.FLAT_ITEM);
/* 363 */     generateFlatItem(Items.WHITE_DYE, ModelTemplates.FLAT_ITEM);
/* 364 */     generateFlatItem(Items.WOODEN_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 365 */     generateFlatItem(Items.WOODEN_HOE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 366 */     generateFlatItem(Items.WOODEN_PICKAXE, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 367 */     generateFlatItem(Items.WOODEN_SHOVEL, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 368 */     generateFlatItem(Items.WOODEN_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 369 */     generateFlatItem(Items.WRITABLE_BOOK, ModelTemplates.FLAT_ITEM);
/* 370 */     generateFlatItem(Items.WRITTEN_BOOK, ModelTemplates.FLAT_ITEM);
/* 371 */     generateFlatItem(Items.YELLOW_DYE, ModelTemplates.FLAT_ITEM);
/*     */     
/* 373 */     generateFlatItem(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 374 */     generateFlatItem(Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 375 */     generateFlatItem(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 376 */     generateFlatItem(Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 377 */     generateFlatItem(Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 378 */     generateFlatItem(Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 379 */     generateFlatItem(Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 380 */     generateFlatItem(Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 381 */     generateFlatItem(Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 382 */     generateFlatItem(Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 383 */     generateFlatItem(Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 384 */     generateFlatItem(Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 385 */     generateFlatItem(Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 386 */     generateFlatItem(Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 387 */     generateFlatItem(Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 388 */     generateFlatItem(Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/* 389 */     generateFlatItem(Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, ModelTemplates.FLAT_ITEM);
/*     */     
/* 391 */     generateFlatItem(Items.DEBUG_STICK, Items.STICK, ModelTemplates.FLAT_HANDHELD_ITEM);
/* 392 */     generateFlatItem(Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_APPLE, ModelTemplates.FLAT_ITEM);
/*     */     
/* 394 */     for (Item $$0 : BuiltInRegistries.ITEM) {
/* 395 */       if ($$0 instanceof ArmorItem) { ArmorItem $$1 = (ArmorItem)$$0;
/* 396 */         generateArmorTrims($$1); }
/*     */     
/*     */     } 
/*     */     
/* 400 */     generateFlatItem(Items.ANGLER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 401 */     generateFlatItem(Items.ARCHER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 402 */     generateFlatItem(Items.ARMS_UP_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 403 */     generateFlatItem(Items.BLADE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 404 */     generateFlatItem(Items.BREWER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 405 */     generateFlatItem(Items.BURN_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 406 */     generateFlatItem(Items.DANGER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 407 */     generateFlatItem(Items.EXPLORER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 408 */     generateFlatItem(Items.FRIEND_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 409 */     generateFlatItem(Items.HEART_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 410 */     generateFlatItem(Items.HEARTBREAK_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 411 */     generateFlatItem(Items.HOWL_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 412 */     generateFlatItem(Items.MINER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 413 */     generateFlatItem(Items.MOURNER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 414 */     generateFlatItem(Items.PLENTY_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 415 */     generateFlatItem(Items.PRIZE_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 416 */     generateFlatItem(Items.SHEAF_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 417 */     generateFlatItem(Items.SHELTER_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 418 */     generateFlatItem(Items.SKULL_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 419 */     generateFlatItem(Items.SNORT_POTTERY_SHERD, ModelTemplates.FLAT_ITEM);
/* 420 */     generateFlatItem(Items.TRIAL_KEY, ModelTemplates.FLAT_ITEM);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\ItemModelGenerators.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */