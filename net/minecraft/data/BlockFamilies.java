/*     */ package net.minecraft.data;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ public class BlockFamilies
/*     */ {
/*  12 */   private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();
/*     */   
/*     */   private static final String RECIPE_GROUP_PREFIX_WOODEN = "wooden";
/*     */   
/*     */   private static final String RECIPE_UNLOCKED_BY_HAS_PLANKS = "has_planks";
/*  17 */   public static final BlockFamily ACACIA_PLANKS = familyBuilder(Blocks.ACACIA_PLANKS)
/*  18 */     .button(Blocks.ACACIA_BUTTON)
/*  19 */     .fence(Blocks.ACACIA_FENCE)
/*  20 */     .fenceGate(Blocks.ACACIA_FENCE_GATE)
/*  21 */     .pressurePlate(Blocks.ACACIA_PRESSURE_PLATE)
/*  22 */     .sign(Blocks.ACACIA_SIGN, Blocks.ACACIA_WALL_SIGN)
/*  23 */     .slab(Blocks.ACACIA_SLAB)
/*  24 */     .stairs(Blocks.ACACIA_STAIRS)
/*  25 */     .door(Blocks.ACACIA_DOOR)
/*  26 */     .trapdoor(Blocks.ACACIA_TRAPDOOR)
/*  27 */     .recipeGroupPrefix("wooden")
/*  28 */     .recipeUnlockedBy("has_planks")
/*  29 */     .getFamily();
/*     */   
/*  31 */   public static final BlockFamily CHERRY_PLANKS = familyBuilder(Blocks.CHERRY_PLANKS)
/*  32 */     .button(Blocks.CHERRY_BUTTON)
/*  33 */     .fence(Blocks.CHERRY_FENCE)
/*  34 */     .fenceGate(Blocks.CHERRY_FENCE_GATE)
/*  35 */     .pressurePlate(Blocks.CHERRY_PRESSURE_PLATE)
/*  36 */     .sign(Blocks.CHERRY_SIGN, Blocks.CHERRY_WALL_SIGN)
/*  37 */     .slab(Blocks.CHERRY_SLAB)
/*  38 */     .stairs(Blocks.CHERRY_STAIRS)
/*  39 */     .door(Blocks.CHERRY_DOOR)
/*  40 */     .trapdoor(Blocks.CHERRY_TRAPDOOR)
/*  41 */     .recipeGroupPrefix("wooden")
/*  42 */     .recipeUnlockedBy("has_planks")
/*  43 */     .getFamily();
/*     */   
/*  45 */   public static final BlockFamily BIRCH_PLANKS = familyBuilder(Blocks.BIRCH_PLANKS)
/*  46 */     .button(Blocks.BIRCH_BUTTON)
/*  47 */     .fence(Blocks.BIRCH_FENCE)
/*  48 */     .fenceGate(Blocks.BIRCH_FENCE_GATE)
/*  49 */     .pressurePlate(Blocks.BIRCH_PRESSURE_PLATE)
/*  50 */     .sign(Blocks.BIRCH_SIGN, Blocks.BIRCH_WALL_SIGN)
/*  51 */     .slab(Blocks.BIRCH_SLAB)
/*  52 */     .stairs(Blocks.BIRCH_STAIRS)
/*  53 */     .door(Blocks.BIRCH_DOOR)
/*  54 */     .trapdoor(Blocks.BIRCH_TRAPDOOR)
/*  55 */     .recipeGroupPrefix("wooden")
/*  56 */     .recipeUnlockedBy("has_planks")
/*  57 */     .getFamily();
/*     */   
/*  59 */   public static final BlockFamily CRIMSON_PLANKS = familyBuilder(Blocks.CRIMSON_PLANKS)
/*  60 */     .button(Blocks.CRIMSON_BUTTON)
/*  61 */     .fence(Blocks.CRIMSON_FENCE)
/*  62 */     .fenceGate(Blocks.CRIMSON_FENCE_GATE)
/*  63 */     .pressurePlate(Blocks.CRIMSON_PRESSURE_PLATE)
/*  64 */     .sign(Blocks.CRIMSON_SIGN, Blocks.CRIMSON_WALL_SIGN)
/*  65 */     .slab(Blocks.CRIMSON_SLAB)
/*  66 */     .stairs(Blocks.CRIMSON_STAIRS)
/*  67 */     .door(Blocks.CRIMSON_DOOR)
/*  68 */     .trapdoor(Blocks.CRIMSON_TRAPDOOR)
/*  69 */     .recipeGroupPrefix("wooden")
/*  70 */     .recipeUnlockedBy("has_planks")
/*  71 */     .getFamily();
/*     */   
/*  73 */   public static final BlockFamily JUNGLE_PLANKS = familyBuilder(Blocks.JUNGLE_PLANKS)
/*  74 */     .button(Blocks.JUNGLE_BUTTON)
/*  75 */     .fence(Blocks.JUNGLE_FENCE)
/*  76 */     .fenceGate(Blocks.JUNGLE_FENCE_GATE)
/*  77 */     .pressurePlate(Blocks.JUNGLE_PRESSURE_PLATE)
/*  78 */     .sign(Blocks.JUNGLE_SIGN, Blocks.JUNGLE_WALL_SIGN)
/*  79 */     .slab(Blocks.JUNGLE_SLAB)
/*  80 */     .stairs(Blocks.JUNGLE_STAIRS)
/*  81 */     .door(Blocks.JUNGLE_DOOR)
/*  82 */     .trapdoor(Blocks.JUNGLE_TRAPDOOR)
/*  83 */     .recipeGroupPrefix("wooden")
/*  84 */     .recipeUnlockedBy("has_planks")
/*  85 */     .getFamily();
/*     */   
/*  87 */   public static final BlockFamily OAK_PLANKS = familyBuilder(Blocks.OAK_PLANKS)
/*  88 */     .button(Blocks.OAK_BUTTON)
/*  89 */     .fence(Blocks.OAK_FENCE)
/*  90 */     .fenceGate(Blocks.OAK_FENCE_GATE)
/*  91 */     .pressurePlate(Blocks.OAK_PRESSURE_PLATE)
/*  92 */     .sign(Blocks.OAK_SIGN, Blocks.OAK_WALL_SIGN)
/*  93 */     .slab(Blocks.OAK_SLAB)
/*  94 */     .stairs(Blocks.OAK_STAIRS)
/*  95 */     .door(Blocks.OAK_DOOR)
/*  96 */     .trapdoor(Blocks.OAK_TRAPDOOR)
/*  97 */     .recipeGroupPrefix("wooden")
/*  98 */     .recipeUnlockedBy("has_planks")
/*  99 */     .getFamily();
/*     */   
/* 101 */   public static final BlockFamily DARK_OAK_PLANKS = familyBuilder(Blocks.DARK_OAK_PLANKS)
/* 102 */     .button(Blocks.DARK_OAK_BUTTON)
/* 103 */     .fence(Blocks.DARK_OAK_FENCE)
/* 104 */     .fenceGate(Blocks.DARK_OAK_FENCE_GATE)
/* 105 */     .pressurePlate(Blocks.DARK_OAK_PRESSURE_PLATE)
/* 106 */     .sign(Blocks.DARK_OAK_SIGN, Blocks.DARK_OAK_WALL_SIGN)
/* 107 */     .slab(Blocks.DARK_OAK_SLAB)
/* 108 */     .stairs(Blocks.DARK_OAK_STAIRS)
/* 109 */     .door(Blocks.DARK_OAK_DOOR)
/* 110 */     .trapdoor(Blocks.DARK_OAK_TRAPDOOR)
/* 111 */     .recipeGroupPrefix("wooden")
/* 112 */     .recipeUnlockedBy("has_planks")
/* 113 */     .getFamily();
/*     */   
/* 115 */   public static final BlockFamily SPRUCE_PLANKS = familyBuilder(Blocks.SPRUCE_PLANKS)
/* 116 */     .button(Blocks.SPRUCE_BUTTON)
/* 117 */     .fence(Blocks.SPRUCE_FENCE)
/* 118 */     .fenceGate(Blocks.SPRUCE_FENCE_GATE)
/* 119 */     .pressurePlate(Blocks.SPRUCE_PRESSURE_PLATE)
/* 120 */     .sign(Blocks.SPRUCE_SIGN, Blocks.SPRUCE_WALL_SIGN)
/* 121 */     .slab(Blocks.SPRUCE_SLAB)
/* 122 */     .stairs(Blocks.SPRUCE_STAIRS)
/* 123 */     .door(Blocks.SPRUCE_DOOR)
/* 124 */     .trapdoor(Blocks.SPRUCE_TRAPDOOR)
/* 125 */     .recipeGroupPrefix("wooden")
/* 126 */     .recipeUnlockedBy("has_planks")
/* 127 */     .getFamily();
/*     */   
/* 129 */   public static final BlockFamily WARPED_PLANKS = familyBuilder(Blocks.WARPED_PLANKS)
/* 130 */     .button(Blocks.WARPED_BUTTON)
/* 131 */     .fence(Blocks.WARPED_FENCE)
/* 132 */     .fenceGate(Blocks.WARPED_FENCE_GATE)
/* 133 */     .pressurePlate(Blocks.WARPED_PRESSURE_PLATE)
/* 134 */     .sign(Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN)
/* 135 */     .slab(Blocks.WARPED_SLAB)
/* 136 */     .stairs(Blocks.WARPED_STAIRS)
/* 137 */     .door(Blocks.WARPED_DOOR)
/* 138 */     .trapdoor(Blocks.WARPED_TRAPDOOR)
/* 139 */     .recipeGroupPrefix("wooden")
/* 140 */     .recipeUnlockedBy("has_planks")
/* 141 */     .getFamily();
/*     */   
/* 143 */   public static final BlockFamily MANGROVE_PLANKS = familyBuilder(Blocks.MANGROVE_PLANKS)
/* 144 */     .button(Blocks.MANGROVE_BUTTON)
/* 145 */     .slab(Blocks.MANGROVE_SLAB)
/* 146 */     .stairs(Blocks.MANGROVE_STAIRS)
/* 147 */     .fence(Blocks.MANGROVE_FENCE)
/* 148 */     .fenceGate(Blocks.MANGROVE_FENCE_GATE)
/* 149 */     .pressurePlate(Blocks.MANGROVE_PRESSURE_PLATE)
/* 150 */     .sign(Blocks.MANGROVE_SIGN, Blocks.MANGROVE_WALL_SIGN)
/* 151 */     .door(Blocks.MANGROVE_DOOR)
/* 152 */     .trapdoor(Blocks.MANGROVE_TRAPDOOR)
/* 153 */     .recipeGroupPrefix("wooden")
/* 154 */     .recipeUnlockedBy("has_planks")
/* 155 */     .getFamily();
/*     */   
/* 157 */   public static final BlockFamily BAMBOO_PLANKS = familyBuilder(Blocks.BAMBOO_PLANKS)
/* 158 */     .button(Blocks.BAMBOO_BUTTON)
/* 159 */     .slab(Blocks.BAMBOO_SLAB)
/* 160 */     .stairs(Blocks.BAMBOO_STAIRS)
/* 161 */     .customFence(Blocks.BAMBOO_FENCE)
/* 162 */     .customFenceGate(Blocks.BAMBOO_FENCE_GATE)
/* 163 */     .pressurePlate(Blocks.BAMBOO_PRESSURE_PLATE)
/* 164 */     .sign(Blocks.BAMBOO_SIGN, Blocks.BAMBOO_WALL_SIGN)
/* 165 */     .door(Blocks.BAMBOO_DOOR)
/* 166 */     .trapdoor(Blocks.BAMBOO_TRAPDOOR)
/* 167 */     .mosaic(Blocks.BAMBOO_MOSAIC)
/* 168 */     .recipeGroupPrefix("wooden")
/* 169 */     .recipeUnlockedBy("has_planks")
/* 170 */     .getFamily();
/*     */   
/* 172 */   public static final BlockFamily BAMBOO_MOSAIC = familyBuilder(Blocks.BAMBOO_MOSAIC)
/* 173 */     .slab(Blocks.BAMBOO_MOSAIC_SLAB)
/* 174 */     .stairs(Blocks.BAMBOO_MOSAIC_STAIRS)
/* 175 */     .getFamily();
/*     */   
/* 177 */   public static final BlockFamily MUD_BRICKS = familyBuilder(Blocks.MUD_BRICKS)
/* 178 */     .wall(Blocks.MUD_BRICK_WALL)
/* 179 */     .stairs(Blocks.MUD_BRICK_STAIRS)
/* 180 */     .slab(Blocks.MUD_BRICK_SLAB)
/* 181 */     .getFamily();
/*     */   
/* 183 */   public static final BlockFamily ANDESITE = familyBuilder(Blocks.ANDESITE)
/* 184 */     .wall(Blocks.ANDESITE_WALL)
/* 185 */     .stairs(Blocks.ANDESITE_STAIRS)
/* 186 */     .slab(Blocks.ANDESITE_SLAB)
/* 187 */     .polished(Blocks.POLISHED_ANDESITE)
/* 188 */     .getFamily();
/*     */   
/* 190 */   public static final BlockFamily POLISHED_ANDESITE = familyBuilder(Blocks.POLISHED_ANDESITE)
/* 191 */     .stairs(Blocks.POLISHED_ANDESITE_STAIRS)
/* 192 */     .slab(Blocks.POLISHED_ANDESITE_SLAB)
/* 193 */     .getFamily();
/*     */   
/* 195 */   public static final BlockFamily BLACKSTONE = familyBuilder(Blocks.BLACKSTONE)
/* 196 */     .wall(Blocks.BLACKSTONE_WALL)
/* 197 */     .stairs(Blocks.BLACKSTONE_STAIRS)
/* 198 */     .slab(Blocks.BLACKSTONE_SLAB)
/* 199 */     .polished(Blocks.POLISHED_BLACKSTONE)
/* 200 */     .getFamily();
/*     */   
/* 202 */   public static final BlockFamily POLISHED_BLACKSTONE = familyBuilder(Blocks.POLISHED_BLACKSTONE)
/* 203 */     .wall(Blocks.POLISHED_BLACKSTONE_WALL)
/* 204 */     .pressurePlate(Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE)
/* 205 */     .button(Blocks.POLISHED_BLACKSTONE_BUTTON)
/* 206 */     .stairs(Blocks.POLISHED_BLACKSTONE_STAIRS)
/* 207 */     .slab(Blocks.POLISHED_BLACKSTONE_SLAB)
/* 208 */     .polished(Blocks.POLISHED_BLACKSTONE_BRICKS)
/* 209 */     .chiseled(Blocks.CHISELED_POLISHED_BLACKSTONE)
/* 210 */     .getFamily();
/*     */   
/* 212 */   public static final BlockFamily POLISHED_BLACKSTONE_BRICKS = familyBuilder(Blocks.POLISHED_BLACKSTONE_BRICKS)
/* 213 */     .wall(Blocks.POLISHED_BLACKSTONE_BRICK_WALL)
/* 214 */     .stairs(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS)
/* 215 */     .slab(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB)
/* 216 */     .cracked(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS)
/* 217 */     .getFamily();
/*     */   
/* 219 */   public static final BlockFamily BRICKS = familyBuilder(Blocks.BRICKS)
/* 220 */     .wall(Blocks.BRICK_WALL)
/* 221 */     .stairs(Blocks.BRICK_STAIRS)
/* 222 */     .slab(Blocks.BRICK_SLAB)
/* 223 */     .getFamily();
/*     */   
/* 225 */   public static final BlockFamily END_STONE_BRICKS = familyBuilder(Blocks.END_STONE_BRICKS)
/* 226 */     .wall(Blocks.END_STONE_BRICK_WALL)
/* 227 */     .stairs(Blocks.END_STONE_BRICK_STAIRS)
/* 228 */     .slab(Blocks.END_STONE_BRICK_SLAB)
/* 229 */     .getFamily();
/*     */   
/* 231 */   public static final BlockFamily MOSSY_STONE_BRICKS = familyBuilder(Blocks.MOSSY_STONE_BRICKS)
/* 232 */     .wall(Blocks.MOSSY_STONE_BRICK_WALL)
/* 233 */     .stairs(Blocks.MOSSY_STONE_BRICK_STAIRS)
/* 234 */     .slab(Blocks.MOSSY_STONE_BRICK_SLAB)
/* 235 */     .getFamily();
/*     */   
/* 237 */   public static final BlockFamily COPPER_BLOCK = familyBuilder(Blocks.COPPER_BLOCK)
/* 238 */     .cut(Blocks.CUT_COPPER)
/* 239 */     .door(Blocks.COPPER_DOOR)
/* 240 */     .trapdoor(Blocks.COPPER_TRAPDOOR)
/* 241 */     .dontGenerateModel()
/* 242 */     .getFamily();
/*     */   
/* 244 */   public static final BlockFamily CUT_COPPER = familyBuilder(Blocks.CUT_COPPER)
/* 245 */     .slab(Blocks.CUT_COPPER_SLAB)
/* 246 */     .stairs(Blocks.CUT_COPPER_STAIRS)
/* 247 */     .chiseled(Blocks.CHISELED_COPPER)
/* 248 */     .dontGenerateModel()
/* 249 */     .getFamily();
/*     */   
/* 251 */   public static final BlockFamily WAXED_COPPER_BLOCK = familyBuilder(Blocks.WAXED_COPPER_BLOCK)
/* 252 */     .cut(Blocks.WAXED_CUT_COPPER)
/* 253 */     .door(Blocks.WAXED_COPPER_DOOR)
/* 254 */     .trapdoor(Blocks.WAXED_COPPER_TRAPDOOR)
/* 255 */     .recipeGroupPrefix("waxed_cut_copper")
/* 256 */     .dontGenerateModel()
/* 257 */     .getFamily();
/*     */   
/* 259 */   public static final BlockFamily WAXED_CUT_COPPER = familyBuilder(Blocks.WAXED_CUT_COPPER)
/* 260 */     .slab(Blocks.WAXED_CUT_COPPER_SLAB)
/* 261 */     .stairs(Blocks.WAXED_CUT_COPPER_STAIRS)
/* 262 */     .chiseled(Blocks.WAXED_CHISELED_COPPER)
/* 263 */     .recipeGroupPrefix("waxed_cut_copper")
/* 264 */     .dontGenerateModel()
/* 265 */     .getFamily();
/*     */   
/* 267 */   public static final BlockFamily EXPOSED_COPPER = familyBuilder(Blocks.EXPOSED_COPPER)
/* 268 */     .cut(Blocks.EXPOSED_CUT_COPPER)
/* 269 */     .door(Blocks.EXPOSED_COPPER_DOOR)
/* 270 */     .trapdoor(Blocks.EXPOSED_COPPER_TRAPDOOR)
/* 271 */     .dontGenerateModel()
/* 272 */     .getFamily();
/*     */   
/* 274 */   public static final BlockFamily EXPOSED_CUT_COPPER = familyBuilder(Blocks.EXPOSED_CUT_COPPER)
/* 275 */     .slab(Blocks.EXPOSED_CUT_COPPER_SLAB)
/* 276 */     .stairs(Blocks.EXPOSED_CUT_COPPER_STAIRS)
/* 277 */     .chiseled(Blocks.EXPOSED_CHISELED_COPPER)
/* 278 */     .dontGenerateModel()
/* 279 */     .getFamily();
/*     */   
/* 281 */   public static final BlockFamily WAXED_EXPOSED_COPPER = familyBuilder(Blocks.WAXED_EXPOSED_COPPER)
/* 282 */     .cut(Blocks.WAXED_EXPOSED_CUT_COPPER)
/* 283 */     .door(Blocks.WAXED_EXPOSED_COPPER_DOOR)
/* 284 */     .trapdoor(Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR)
/* 285 */     .recipeGroupPrefix("waxed_exposed_cut_copper")
/* 286 */     .dontGenerateModel()
/* 287 */     .getFamily();
/*     */   
/* 289 */   public static final BlockFamily WAXED_EXPOSED_CUT_COPPER = familyBuilder(Blocks.WAXED_EXPOSED_CUT_COPPER)
/* 290 */     .slab(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB)
/* 291 */     .stairs(Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS)
/* 292 */     .chiseled(Blocks.WAXED_EXPOSED_CHISELED_COPPER)
/* 293 */     .recipeGroupPrefix("waxed_exposed_cut_copper")
/* 294 */     .dontGenerateModel()
/* 295 */     .getFamily();
/*     */   
/* 297 */   public static final BlockFamily WEATHERED_COPPER = familyBuilder(Blocks.WEATHERED_COPPER)
/* 298 */     .cut(Blocks.WEATHERED_CUT_COPPER)
/* 299 */     .door(Blocks.WEATHERED_COPPER_DOOR)
/* 300 */     .trapdoor(Blocks.WEATHERED_COPPER_TRAPDOOR)
/* 301 */     .dontGenerateModel()
/* 302 */     .getFamily();
/*     */   
/* 304 */   public static final BlockFamily WEATHERED_CUT_COPPER = familyBuilder(Blocks.WEATHERED_CUT_COPPER)
/* 305 */     .slab(Blocks.WEATHERED_CUT_COPPER_SLAB)
/* 306 */     .stairs(Blocks.WEATHERED_CUT_COPPER_STAIRS)
/* 307 */     .chiseled(Blocks.WEATHERED_CHISELED_COPPER)
/* 308 */     .dontGenerateModel()
/* 309 */     .getFamily();
/*     */   
/* 311 */   public static final BlockFamily WAXED_WEATHERED_COPPER = familyBuilder(Blocks.WAXED_WEATHERED_COPPER)
/* 312 */     .cut(Blocks.WAXED_WEATHERED_CUT_COPPER)
/* 313 */     .door(Blocks.WAXED_WEATHERED_COPPER_DOOR)
/* 314 */     .trapdoor(Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR)
/* 315 */     .recipeGroupPrefix("waxed_weathered_cut_copper")
/* 316 */     .dontGenerateModel()
/* 317 */     .getFamily();
/*     */   
/* 319 */   public static final BlockFamily WAXED_WEATHERED_CUT_COPPER = familyBuilder(Blocks.WAXED_WEATHERED_CUT_COPPER)
/* 320 */     .slab(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB)
/* 321 */     .stairs(Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS)
/* 322 */     .chiseled(Blocks.WAXED_WEATHERED_CHISELED_COPPER)
/* 323 */     .recipeGroupPrefix("waxed_weathered_cut_copper")
/* 324 */     .dontGenerateModel()
/* 325 */     .getFamily();
/*     */   
/* 327 */   public static final BlockFamily OXIDIZED_COPPER = familyBuilder(Blocks.OXIDIZED_COPPER)
/* 328 */     .cut(Blocks.OXIDIZED_CUT_COPPER)
/* 329 */     .door(Blocks.OXIDIZED_COPPER_DOOR)
/* 330 */     .trapdoor(Blocks.OXIDIZED_COPPER_TRAPDOOR)
/* 331 */     .dontGenerateModel()
/* 332 */     .getFamily();
/*     */   
/* 334 */   public static final BlockFamily OXIDIZED_CUT_COPPER = familyBuilder(Blocks.OXIDIZED_CUT_COPPER)
/* 335 */     .slab(Blocks.OXIDIZED_CUT_COPPER_SLAB)
/* 336 */     .stairs(Blocks.OXIDIZED_CUT_COPPER_STAIRS)
/* 337 */     .chiseled(Blocks.OXIDIZED_CHISELED_COPPER)
/* 338 */     .dontGenerateModel()
/* 339 */     .getFamily();
/*     */   
/* 341 */   public static final BlockFamily WAXED_OXIDIZED_COPPER = familyBuilder(Blocks.WAXED_OXIDIZED_COPPER)
/* 342 */     .cut(Blocks.WAXED_OXIDIZED_CUT_COPPER)
/* 343 */     .door(Blocks.WAXED_OXIDIZED_COPPER_DOOR)
/* 344 */     .trapdoor(Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR)
/* 345 */     .recipeGroupPrefix("waxed_oxidized_cut_copper")
/* 346 */     .dontGenerateModel()
/* 347 */     .getFamily();
/*     */   
/* 349 */   public static final BlockFamily WAXED_OXIDIZED_CUT_COPPER = familyBuilder(Blocks.WAXED_OXIDIZED_CUT_COPPER)
/* 350 */     .slab(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB)
/* 351 */     .stairs(Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS)
/* 352 */     .chiseled(Blocks.WAXED_OXIDIZED_CHISELED_COPPER)
/* 353 */     .recipeGroupPrefix("waxed_oxidized_cut_copper")
/* 354 */     .dontGenerateModel()
/* 355 */     .getFamily();
/*     */   
/* 357 */   public static final BlockFamily COBBLESTONE = familyBuilder(Blocks.COBBLESTONE)
/* 358 */     .wall(Blocks.COBBLESTONE_WALL)
/* 359 */     .stairs(Blocks.COBBLESTONE_STAIRS)
/* 360 */     .slab(Blocks.COBBLESTONE_SLAB)
/* 361 */     .getFamily();
/*     */   
/* 363 */   public static final BlockFamily MOSSY_COBBLESTONE = familyBuilder(Blocks.MOSSY_COBBLESTONE)
/* 364 */     .wall(Blocks.MOSSY_COBBLESTONE_WALL)
/* 365 */     .stairs(Blocks.MOSSY_COBBLESTONE_STAIRS)
/* 366 */     .slab(Blocks.MOSSY_COBBLESTONE_SLAB)
/* 367 */     .getFamily();
/*     */   
/* 369 */   public static final BlockFamily DIORITE = familyBuilder(Blocks.DIORITE)
/* 370 */     .wall(Blocks.DIORITE_WALL)
/* 371 */     .stairs(Blocks.DIORITE_STAIRS)
/* 372 */     .slab(Blocks.DIORITE_SLAB)
/* 373 */     .polished(Blocks.POLISHED_DIORITE)
/* 374 */     .getFamily();
/*     */   
/* 376 */   public static final BlockFamily POLISHED_DIORITE = familyBuilder(Blocks.POLISHED_DIORITE)
/* 377 */     .stairs(Blocks.POLISHED_DIORITE_STAIRS)
/* 378 */     .slab(Blocks.POLISHED_DIORITE_SLAB)
/* 379 */     .getFamily();
/*     */   
/* 381 */   public static final BlockFamily GRANITE = familyBuilder(Blocks.GRANITE)
/* 382 */     .wall(Blocks.GRANITE_WALL)
/* 383 */     .stairs(Blocks.GRANITE_STAIRS)
/* 384 */     .slab(Blocks.GRANITE_SLAB)
/* 385 */     .polished(Blocks.POLISHED_GRANITE)
/* 386 */     .getFamily();
/*     */   
/* 388 */   public static final BlockFamily POLISHED_GRANITE = familyBuilder(Blocks.POLISHED_GRANITE)
/* 389 */     .stairs(Blocks.POLISHED_GRANITE_STAIRS)
/* 390 */     .slab(Blocks.POLISHED_GRANITE_SLAB)
/* 391 */     .getFamily();
/*     */   
/* 393 */   public static final BlockFamily TUFF = familyBuilder(Blocks.TUFF)
/* 394 */     .wall(Blocks.TUFF_WALL)
/* 395 */     .stairs(Blocks.TUFF_STAIRS)
/* 396 */     .slab(Blocks.TUFF_SLAB)
/* 397 */     .chiseled(Blocks.CHISELED_TUFF)
/* 398 */     .polished(Blocks.POLISHED_TUFF)
/* 399 */     .getFamily();
/*     */   
/* 401 */   public static final BlockFamily POLISHED_TUFF = familyBuilder(Blocks.POLISHED_TUFF)
/* 402 */     .wall(Blocks.POLISHED_TUFF_WALL)
/* 403 */     .stairs(Blocks.POLISHED_TUFF_STAIRS)
/* 404 */     .slab(Blocks.POLISHED_TUFF_SLAB)
/* 405 */     .polished(Blocks.TUFF_BRICKS)
/* 406 */     .getFamily();
/*     */   
/* 408 */   public static final BlockFamily TUFF_BRICKS = familyBuilder(Blocks.TUFF_BRICKS)
/* 409 */     .wall(Blocks.TUFF_BRICK_WALL)
/* 410 */     .stairs(Blocks.TUFF_BRICK_STAIRS)
/* 411 */     .slab(Blocks.TUFF_BRICK_SLAB)
/* 412 */     .chiseled(Blocks.CHISELED_TUFF_BRICKS)
/* 413 */     .getFamily();
/*     */   
/* 415 */   public static final BlockFamily NETHER_BRICKS = familyBuilder(Blocks.NETHER_BRICKS)
/* 416 */     .fence(Blocks.NETHER_BRICK_FENCE)
/* 417 */     .wall(Blocks.NETHER_BRICK_WALL)
/* 418 */     .stairs(Blocks.NETHER_BRICK_STAIRS)
/* 419 */     .slab(Blocks.NETHER_BRICK_SLAB)
/* 420 */     .chiseled(Blocks.CHISELED_NETHER_BRICKS)
/* 421 */     .cracked(Blocks.CRACKED_NETHER_BRICKS)
/* 422 */     .getFamily();
/*     */   
/* 424 */   public static final BlockFamily RED_NETHER_BRICKS = familyBuilder(Blocks.RED_NETHER_BRICKS)
/* 425 */     .slab(Blocks.RED_NETHER_BRICK_SLAB)
/* 426 */     .stairs(Blocks.RED_NETHER_BRICK_STAIRS)
/* 427 */     .wall(Blocks.RED_NETHER_BRICK_WALL)
/* 428 */     .getFamily();
/*     */   
/* 430 */   public static final BlockFamily PRISMARINE = familyBuilder(Blocks.PRISMARINE)
/* 431 */     .wall(Blocks.PRISMARINE_WALL)
/* 432 */     .stairs(Blocks.PRISMARINE_STAIRS)
/* 433 */     .slab(Blocks.PRISMARINE_SLAB)
/* 434 */     .getFamily();
/*     */   
/* 436 */   public static final BlockFamily PURPUR = familyBuilder(Blocks.PURPUR_BLOCK)
/* 437 */     .stairs(Blocks.PURPUR_STAIRS)
/* 438 */     .slab(Blocks.PURPUR_SLAB)
/* 439 */     .dontGenerateRecipe()
/* 440 */     .getFamily();
/*     */   
/* 442 */   public static final BlockFamily PRISMARINE_BRICKS = familyBuilder(Blocks.PRISMARINE_BRICKS)
/* 443 */     .stairs(Blocks.PRISMARINE_BRICK_STAIRS)
/* 444 */     .slab(Blocks.PRISMARINE_BRICK_SLAB)
/* 445 */     .getFamily();
/*     */   
/* 447 */   public static final BlockFamily DARK_PRISMARINE = familyBuilder(Blocks.DARK_PRISMARINE)
/* 448 */     .stairs(Blocks.DARK_PRISMARINE_STAIRS)
/* 449 */     .slab(Blocks.DARK_PRISMARINE_SLAB)
/* 450 */     .getFamily();
/*     */   
/* 452 */   public static final BlockFamily QUARTZ = familyBuilder(Blocks.QUARTZ_BLOCK)
/* 453 */     .stairs(Blocks.QUARTZ_STAIRS)
/* 454 */     .slab(Blocks.QUARTZ_SLAB)
/* 455 */     .chiseled(Blocks.CHISELED_QUARTZ_BLOCK)
/* 456 */     .dontGenerateRecipe()
/* 457 */     .getFamily();
/*     */   
/* 459 */   public static final BlockFamily SMOOTH_QUARTZ = familyBuilder(Blocks.SMOOTH_QUARTZ)
/* 460 */     .stairs(Blocks.SMOOTH_QUARTZ_STAIRS)
/* 461 */     .slab(Blocks.SMOOTH_QUARTZ_SLAB)
/* 462 */     .getFamily();
/*     */   
/* 464 */   public static final BlockFamily SANDSTONE = familyBuilder(Blocks.SANDSTONE)
/* 465 */     .wall(Blocks.SANDSTONE_WALL)
/* 466 */     .stairs(Blocks.SANDSTONE_STAIRS)
/* 467 */     .slab(Blocks.SANDSTONE_SLAB)
/* 468 */     .chiseled(Blocks.CHISELED_SANDSTONE)
/* 469 */     .cut(Blocks.CUT_SANDSTONE)
/* 470 */     .dontGenerateRecipe()
/* 471 */     .getFamily();
/*     */   
/* 473 */   public static final BlockFamily CUT_SANDSTONE = familyBuilder(Blocks.CUT_SANDSTONE)
/* 474 */     .slab(Blocks.CUT_SANDSTONE_SLAB)
/* 475 */     .getFamily();
/*     */   
/* 477 */   public static final BlockFamily SMOOTH_SANDSTONE = familyBuilder(Blocks.SMOOTH_SANDSTONE)
/* 478 */     .slab(Blocks.SMOOTH_SANDSTONE_SLAB)
/* 479 */     .stairs(Blocks.SMOOTH_SANDSTONE_STAIRS)
/* 480 */     .getFamily();
/*     */   
/* 482 */   public static final BlockFamily RED_SANDSTONE = familyBuilder(Blocks.RED_SANDSTONE)
/* 483 */     .wall(Blocks.RED_SANDSTONE_WALL)
/* 484 */     .stairs(Blocks.RED_SANDSTONE_STAIRS)
/* 485 */     .slab(Blocks.RED_SANDSTONE_SLAB)
/* 486 */     .chiseled(Blocks.CHISELED_RED_SANDSTONE)
/* 487 */     .cut(Blocks.CUT_RED_SANDSTONE)
/* 488 */     .dontGenerateRecipe()
/* 489 */     .getFamily();
/*     */   
/* 491 */   public static final BlockFamily CUT_RED_SANDSTONE = familyBuilder(Blocks.CUT_RED_SANDSTONE)
/* 492 */     .slab(Blocks.CUT_RED_SANDSTONE_SLAB)
/* 493 */     .getFamily();
/*     */   
/* 495 */   public static final BlockFamily SMOOTH_RED_SANDSTONE = familyBuilder(Blocks.SMOOTH_RED_SANDSTONE)
/* 496 */     .slab(Blocks.SMOOTH_RED_SANDSTONE_SLAB)
/* 497 */     .stairs(Blocks.SMOOTH_RED_SANDSTONE_STAIRS)
/* 498 */     .getFamily();
/*     */   
/* 500 */   public static final BlockFamily STONE = familyBuilder(Blocks.STONE)
/* 501 */     .slab(Blocks.STONE_SLAB)
/* 502 */     .pressurePlate(Blocks.STONE_PRESSURE_PLATE)
/* 503 */     .button(Blocks.STONE_BUTTON)
/* 504 */     .stairs(Blocks.STONE_STAIRS)
/* 505 */     .getFamily();
/*     */   
/* 507 */   public static final BlockFamily STONE_BRICK = familyBuilder(Blocks.STONE_BRICKS)
/* 508 */     .wall(Blocks.STONE_BRICK_WALL)
/* 509 */     .stairs(Blocks.STONE_BRICK_STAIRS)
/* 510 */     .slab(Blocks.STONE_BRICK_SLAB)
/* 511 */     .chiseled(Blocks.CHISELED_STONE_BRICKS)
/* 512 */     .cracked(Blocks.CRACKED_STONE_BRICKS)
/* 513 */     .dontGenerateRecipe()
/* 514 */     .getFamily();
/*     */   
/* 516 */   public static final BlockFamily DEEPSLATE = familyBuilder(Blocks.DEEPSLATE)
/* 517 */     .getFamily();
/*     */   
/* 519 */   public static final BlockFamily COBBLED_DEEPSLATE = familyBuilder(Blocks.COBBLED_DEEPSLATE)
/* 520 */     .slab(Blocks.COBBLED_DEEPSLATE_SLAB)
/* 521 */     .stairs(Blocks.COBBLED_DEEPSLATE_STAIRS)
/* 522 */     .wall(Blocks.COBBLED_DEEPSLATE_WALL)
/* 523 */     .chiseled(Blocks.CHISELED_DEEPSLATE)
/* 524 */     .polished(Blocks.POLISHED_DEEPSLATE)
/* 525 */     .getFamily();
/*     */   
/* 527 */   public static final BlockFamily POLISHED_DEEPSLATE = familyBuilder(Blocks.POLISHED_DEEPSLATE)
/* 528 */     .slab(Blocks.POLISHED_DEEPSLATE_SLAB)
/* 529 */     .stairs(Blocks.POLISHED_DEEPSLATE_STAIRS)
/* 530 */     .wall(Blocks.POLISHED_DEEPSLATE_WALL)
/* 531 */     .getFamily();
/*     */   
/* 533 */   public static final BlockFamily DEEPSLATE_BRICKS = familyBuilder(Blocks.DEEPSLATE_BRICKS)
/* 534 */     .slab(Blocks.DEEPSLATE_BRICK_SLAB)
/* 535 */     .stairs(Blocks.DEEPSLATE_BRICK_STAIRS)
/* 536 */     .wall(Blocks.DEEPSLATE_BRICK_WALL)
/* 537 */     .cracked(Blocks.CRACKED_DEEPSLATE_BRICKS)
/* 538 */     .getFamily();
/*     */   
/* 540 */   public static final BlockFamily DEEPSLATE_TILES = familyBuilder(Blocks.DEEPSLATE_TILES)
/* 541 */     .slab(Blocks.DEEPSLATE_TILE_SLAB)
/* 542 */     .stairs(Blocks.DEEPSLATE_TILE_STAIRS)
/* 543 */     .wall(Blocks.DEEPSLATE_TILE_WALL)
/* 544 */     .cracked(Blocks.CRACKED_DEEPSLATE_TILES)
/* 545 */     .getFamily();
/*     */   
/*     */   private static BlockFamily.Builder familyBuilder(Block $$0) {
/* 548 */     BlockFamily.Builder $$1 = new BlockFamily.Builder($$0);
/* 549 */     BlockFamily $$2 = MAP.put($$0, $$1.getFamily());
/* 550 */     if ($$2 != null) {
/* 551 */       throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey($$0));
/*     */     }
/* 553 */     return $$1;
/*     */   }
/*     */   
/*     */   public static Stream<BlockFamily> getAllFamilies() {
/* 557 */     return MAP.values().stream();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\BlockFamilies.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */