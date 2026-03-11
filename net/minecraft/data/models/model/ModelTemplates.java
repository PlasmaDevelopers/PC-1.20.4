/*     */ package net.minecraft.data.models.model;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.stream.IntStream;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class ModelTemplates
/*     */ {
/*   9 */   public static final ModelTemplate CUBE = create("cube", new TextureSlot[] { TextureSlot.PARTICLE, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST, TextureSlot.UP, TextureSlot.DOWN });
/*  10 */   public static final ModelTemplate CUBE_DIRECTIONAL = create("cube_directional", new TextureSlot[] { TextureSlot.PARTICLE, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST, TextureSlot.UP, TextureSlot.DOWN });
/*  11 */   public static final ModelTemplate CUBE_ALL = create("cube_all", new TextureSlot[] { TextureSlot.ALL });
/*  12 */   public static final ModelTemplate CUBE_ALL_INNER_FACES = create("cube_all_inner_faces", new TextureSlot[] { TextureSlot.ALL });
/*  13 */   public static final ModelTemplate CUBE_MIRRORED_ALL = create("cube_mirrored_all", "_mirrored", new TextureSlot[] { TextureSlot.ALL });
/*  14 */   public static final ModelTemplate CUBE_NORTH_WEST_MIRRORED_ALL = create("cube_north_west_mirrored_all", "_north_west_mirrored", new TextureSlot[] { TextureSlot.ALL });
/*  15 */   public static final ModelTemplate CUBE_COLUMN_UV_LOCKED_X = create("cube_column_uv_locked_x", "_x", new TextureSlot[] { TextureSlot.END, TextureSlot.SIDE });
/*  16 */   public static final ModelTemplate CUBE_COLUMN_UV_LOCKED_Y = create("cube_column_uv_locked_y", "_y", new TextureSlot[] { TextureSlot.END, TextureSlot.SIDE });
/*  17 */   public static final ModelTemplate CUBE_COLUMN_UV_LOCKED_Z = create("cube_column_uv_locked_z", "_z", new TextureSlot[] { TextureSlot.END, TextureSlot.SIDE });
/*  18 */   public static final ModelTemplate CUBE_COLUMN = create("cube_column", new TextureSlot[] { TextureSlot.END, TextureSlot.SIDE });
/*  19 */   public static final ModelTemplate CUBE_COLUMN_HORIZONTAL = create("cube_column_horizontal", "_horizontal", new TextureSlot[] { TextureSlot.END, TextureSlot.SIDE });
/*  20 */   public static final ModelTemplate CUBE_COLUMN_MIRRORED = create("cube_column_mirrored", "_mirrored", new TextureSlot[] { TextureSlot.END, TextureSlot.SIDE });
/*  21 */   public static final ModelTemplate CUBE_TOP = create("cube_top", new TextureSlot[] { TextureSlot.TOP, TextureSlot.SIDE });
/*  22 */   public static final ModelTemplate CUBE_BOTTOM_TOP = create("cube_bottom_top", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE });
/*  23 */   public static final ModelTemplate CUBE_BOTTOM_TOP_INNER_FACES = create("cube_bottom_top_inner_faces", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE });
/*  24 */   public static final ModelTemplate CUBE_ORIENTABLE = create("orientable", new TextureSlot[] { TextureSlot.TOP, TextureSlot.FRONT, TextureSlot.SIDE });
/*  25 */   public static final ModelTemplate CUBE_ORIENTABLE_TOP_BOTTOM = create("orientable_with_bottom", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.FRONT });
/*  26 */   public static final ModelTemplate CUBE_ORIENTABLE_VERTICAL = create("orientable_vertical", "_vertical", new TextureSlot[] { TextureSlot.FRONT, TextureSlot.SIDE });
/*     */   
/*  28 */   public static final ModelTemplate BUTTON = create("button", new TextureSlot[] { TextureSlot.TEXTURE });
/*  29 */   public static final ModelTemplate BUTTON_PRESSED = create("button_pressed", "_pressed", new TextureSlot[] { TextureSlot.TEXTURE });
/*  30 */   public static final ModelTemplate BUTTON_INVENTORY = create("button_inventory", "_inventory", new TextureSlot[] { TextureSlot.TEXTURE });
/*     */   
/*  32 */   public static final ModelTemplate DOOR_BOTTOM_LEFT = create("door_bottom_left", "_bottom_left", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM });
/*  33 */   public static final ModelTemplate DOOR_BOTTOM_LEFT_OPEN = create("door_bottom_left_open", "_bottom_left_open", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM });
/*  34 */   public static final ModelTemplate DOOR_BOTTOM_RIGHT = create("door_bottom_right", "_bottom_right", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM });
/*  35 */   public static final ModelTemplate DOOR_BOTTOM_RIGHT_OPEN = create("door_bottom_right_open", "_bottom_right_open", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM });
/*  36 */   public static final ModelTemplate DOOR_TOP_LEFT = create("door_top_left", "_top_left", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM });
/*  37 */   public static final ModelTemplate DOOR_TOP_LEFT_OPEN = create("door_top_left_open", "_top_left_open", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM });
/*  38 */   public static final ModelTemplate DOOR_TOP_RIGHT = create("door_top_right", "_top_right", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM });
/*  39 */   public static final ModelTemplate DOOR_TOP_RIGHT_OPEN = create("door_top_right_open", "_top_right_open", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM });
/*     */   
/*  41 */   public static final ModelTemplate CUSTOM_FENCE_POST = create("custom_fence_post", "_post", new TextureSlot[] { TextureSlot.TEXTURE, TextureSlot.PARTICLE });
/*  42 */   public static final ModelTemplate CUSTOM_FENCE_SIDE_NORTH = create("custom_fence_side_north", "_side_north", new TextureSlot[] { TextureSlot.TEXTURE });
/*  43 */   public static final ModelTemplate CUSTOM_FENCE_SIDE_EAST = create("custom_fence_side_east", "_side_east", new TextureSlot[] { TextureSlot.TEXTURE });
/*  44 */   public static final ModelTemplate CUSTOM_FENCE_SIDE_SOUTH = create("custom_fence_side_south", "_side_south", new TextureSlot[] { TextureSlot.TEXTURE });
/*  45 */   public static final ModelTemplate CUSTOM_FENCE_SIDE_WEST = create("custom_fence_side_west", "_side_west", new TextureSlot[] { TextureSlot.TEXTURE });
/*  46 */   public static final ModelTemplate CUSTOM_FENCE_INVENTORY = create("custom_fence_inventory", "_inventory", new TextureSlot[] { TextureSlot.TEXTURE });
/*     */ 
/*     */   
/*  49 */   public static final ModelTemplate FENCE_POST = create("fence_post", "_post", new TextureSlot[] { TextureSlot.TEXTURE });
/*  50 */   public static final ModelTemplate FENCE_SIDE = create("fence_side", "_side", new TextureSlot[] { TextureSlot.TEXTURE });
/*  51 */   public static final ModelTemplate FENCE_INVENTORY = create("fence_inventory", "_inventory", new TextureSlot[] { TextureSlot.TEXTURE });
/*     */   
/*  53 */   public static final ModelTemplate WALL_POST = create("template_wall_post", "_post", new TextureSlot[] { TextureSlot.WALL });
/*  54 */   public static final ModelTemplate WALL_LOW_SIDE = create("template_wall_side", "_side", new TextureSlot[] { TextureSlot.WALL });
/*  55 */   public static final ModelTemplate WALL_TALL_SIDE = create("template_wall_side_tall", "_side_tall", new TextureSlot[] { TextureSlot.WALL });
/*  56 */   public static final ModelTemplate WALL_INVENTORY = create("wall_inventory", "_inventory", new TextureSlot[] { TextureSlot.WALL });
/*     */   
/*  58 */   public static final ModelTemplate CUSTOM_FENCE_GATE_CLOSED = create("template_custom_fence_gate", new TextureSlot[] { TextureSlot.TEXTURE, TextureSlot.PARTICLE });
/*  59 */   public static final ModelTemplate CUSTOM_FENCE_GATE_OPEN = create("template_custom_fence_gate_open", "_open", new TextureSlot[] { TextureSlot.TEXTURE, TextureSlot.PARTICLE });
/*  60 */   public static final ModelTemplate CUSTOM_FENCE_GATE_WALL_CLOSED = create("template_custom_fence_gate_wall", "_wall", new TextureSlot[] { TextureSlot.TEXTURE, TextureSlot.PARTICLE });
/*  61 */   public static final ModelTemplate CUSTOM_FENCE_GATE_WALL_OPEN = create("template_custom_fence_gate_wall_open", "_wall_open", new TextureSlot[] { TextureSlot.TEXTURE, TextureSlot.PARTICLE });
/*     */ 
/*     */   
/*  64 */   public static final ModelTemplate FENCE_GATE_CLOSED = create("template_fence_gate", new TextureSlot[] { TextureSlot.TEXTURE });
/*  65 */   public static final ModelTemplate FENCE_GATE_OPEN = create("template_fence_gate_open", "_open", new TextureSlot[] { TextureSlot.TEXTURE });
/*  66 */   public static final ModelTemplate FENCE_GATE_WALL_CLOSED = create("template_fence_gate_wall", "_wall", new TextureSlot[] { TextureSlot.TEXTURE });
/*  67 */   public static final ModelTemplate FENCE_GATE_WALL_OPEN = create("template_fence_gate_wall_open", "_wall_open", new TextureSlot[] { TextureSlot.TEXTURE });
/*     */   
/*  69 */   public static final ModelTemplate PRESSURE_PLATE_UP = create("pressure_plate_up", new TextureSlot[] { TextureSlot.TEXTURE });
/*  70 */   public static final ModelTemplate PRESSURE_PLATE_DOWN = create("pressure_plate_down", "_down", new TextureSlot[] { TextureSlot.TEXTURE });
/*     */   
/*  72 */   public static final ModelTemplate PARTICLE_ONLY = create(new TextureSlot[] { TextureSlot.PARTICLE });
/*     */   
/*  74 */   public static final ModelTemplate SLAB_BOTTOM = create("slab", new TextureSlot[] { TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE });
/*  75 */   public static final ModelTemplate SLAB_TOP = create("slab_top", "_top", new TextureSlot[] { TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE });
/*     */   
/*  77 */   public static final ModelTemplate LEAVES = create("leaves", new TextureSlot[] { TextureSlot.ALL });
/*     */   
/*  79 */   public static final ModelTemplate STAIRS_STRAIGHT = create("stairs", new TextureSlot[] { TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE });
/*  80 */   public static final ModelTemplate STAIRS_INNER = create("inner_stairs", "_inner", new TextureSlot[] { TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE });
/*  81 */   public static final ModelTemplate STAIRS_OUTER = create("outer_stairs", "_outer", new TextureSlot[] { TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE });
/*     */   
/*  83 */   public static final ModelTemplate TRAPDOOR_TOP = create("template_trapdoor_top", "_top", new TextureSlot[] { TextureSlot.TEXTURE });
/*  84 */   public static final ModelTemplate TRAPDOOR_BOTTOM = create("template_trapdoor_bottom", "_bottom", new TextureSlot[] { TextureSlot.TEXTURE });
/*  85 */   public static final ModelTemplate TRAPDOOR_OPEN = create("template_trapdoor_open", "_open", new TextureSlot[] { TextureSlot.TEXTURE });
/*     */   
/*  87 */   public static final ModelTemplate ORIENTABLE_TRAPDOOR_TOP = create("template_orientable_trapdoor_top", "_top", new TextureSlot[] { TextureSlot.TEXTURE });
/*  88 */   public static final ModelTemplate ORIENTABLE_TRAPDOOR_BOTTOM = create("template_orientable_trapdoor_bottom", "_bottom", new TextureSlot[] { TextureSlot.TEXTURE });
/*  89 */   public static final ModelTemplate ORIENTABLE_TRAPDOOR_OPEN = create("template_orientable_trapdoor_open", "_open", new TextureSlot[] { TextureSlot.TEXTURE });
/*     */   
/*  91 */   public static final ModelTemplate POINTED_DRIPSTONE = create("pointed_dripstone", new TextureSlot[] { TextureSlot.CROSS });
/*  92 */   public static final ModelTemplate CROSS = create("cross", new TextureSlot[] { TextureSlot.CROSS });
/*  93 */   public static final ModelTemplate TINTED_CROSS = create("tinted_cross", new TextureSlot[] { TextureSlot.CROSS });
/*     */   
/*  95 */   public static final ModelTemplate FLOWER_POT_CROSS = create("flower_pot_cross", new TextureSlot[] { TextureSlot.PLANT });
/*  96 */   public static final ModelTemplate TINTED_FLOWER_POT_CROSS = create("tinted_flower_pot_cross", new TextureSlot[] { TextureSlot.PLANT });
/*     */   
/*  98 */   public static final ModelTemplate RAIL_FLAT = create("rail_flat", new TextureSlot[] { TextureSlot.RAIL });
/*  99 */   public static final ModelTemplate RAIL_CURVED = create("rail_curved", "_corner", new TextureSlot[] { TextureSlot.RAIL });
/* 100 */   public static final ModelTemplate RAIL_RAISED_NE = create("template_rail_raised_ne", "_raised_ne", new TextureSlot[] { TextureSlot.RAIL });
/* 101 */   public static final ModelTemplate RAIL_RAISED_SW = create("template_rail_raised_sw", "_raised_sw", new TextureSlot[] { TextureSlot.RAIL });
/*     */   
/* 103 */   public static final ModelTemplate CARPET = create("carpet", new TextureSlot[] { TextureSlot.WOOL });
/*     */   
/* 105 */   public static final ModelTemplate FLOWERBED_1 = create("flowerbed_1", "_1", new TextureSlot[] { TextureSlot.FLOWERBED, TextureSlot.STEM });
/* 106 */   public static final ModelTemplate FLOWERBED_2 = create("flowerbed_2", "_2", new TextureSlot[] { TextureSlot.FLOWERBED, TextureSlot.STEM });
/* 107 */   public static final ModelTemplate FLOWERBED_3 = create("flowerbed_3", "_3", new TextureSlot[] { TextureSlot.FLOWERBED, TextureSlot.STEM });
/* 108 */   public static final ModelTemplate FLOWERBED_4 = create("flowerbed_4", "_4", new TextureSlot[] { TextureSlot.FLOWERBED, TextureSlot.STEM });
/*     */ 
/*     */   
/* 111 */   public static final ModelTemplate CORAL_FAN = create("coral_fan", new TextureSlot[] { TextureSlot.FAN });
/* 112 */   public static final ModelTemplate CORAL_WALL_FAN = create("coral_wall_fan", new TextureSlot[] { TextureSlot.FAN });
/* 113 */   public static final ModelTemplate GLAZED_TERRACOTTA = create("template_glazed_terracotta", new TextureSlot[] { TextureSlot.PATTERN });
/* 114 */   public static final ModelTemplate CHORUS_FLOWER = create("template_chorus_flower", new TextureSlot[] { TextureSlot.TEXTURE });
/* 115 */   public static final ModelTemplate DAYLIGHT_DETECTOR = create("template_daylight_detector", new TextureSlot[] { TextureSlot.TOP, TextureSlot.SIDE });
/*     */   
/* 117 */   public static final ModelTemplate STAINED_GLASS_PANE_NOSIDE = create("template_glass_pane_noside", "_noside", new TextureSlot[] { TextureSlot.PANE });
/* 118 */   public static final ModelTemplate STAINED_GLASS_PANE_NOSIDE_ALT = create("template_glass_pane_noside_alt", "_noside_alt", new TextureSlot[] { TextureSlot.PANE });
/* 119 */   public static final ModelTemplate STAINED_GLASS_PANE_POST = create("template_glass_pane_post", "_post", new TextureSlot[] { TextureSlot.PANE, TextureSlot.EDGE });
/* 120 */   public static final ModelTemplate STAINED_GLASS_PANE_SIDE = create("template_glass_pane_side", "_side", new TextureSlot[] { TextureSlot.PANE, TextureSlot.EDGE });
/* 121 */   public static final ModelTemplate STAINED_GLASS_PANE_SIDE_ALT = create("template_glass_pane_side_alt", "_side_alt", new TextureSlot[] { TextureSlot.PANE, TextureSlot.EDGE });
/*     */   
/* 123 */   public static final ModelTemplate COMMAND_BLOCK = create("template_command_block", new TextureSlot[] { TextureSlot.FRONT, TextureSlot.BACK, TextureSlot.SIDE });
/*     */   
/* 125 */   public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_TOP_LEFT = create("template_chiseled_bookshelf_slot_top_left", "_slot_top_left", new TextureSlot[] { TextureSlot.TEXTURE });
/* 126 */   public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_TOP_MID = create("template_chiseled_bookshelf_slot_top_mid", "_slot_top_mid", new TextureSlot[] { TextureSlot.TEXTURE });
/* 127 */   public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_TOP_RIGHT = create("template_chiseled_bookshelf_slot_top_right", "_slot_top_right", new TextureSlot[] { TextureSlot.TEXTURE });
/* 128 */   public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT = create("template_chiseled_bookshelf_slot_bottom_left", "_slot_bottom_left", new TextureSlot[] { TextureSlot.TEXTURE });
/* 129 */   public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_BOTTOM_MID = create("template_chiseled_bookshelf_slot_bottom_mid", "_slot_bottom_mid", new TextureSlot[] { TextureSlot.TEXTURE });
/* 130 */   public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT = create("template_chiseled_bookshelf_slot_bottom_right", "_slot_bottom_right", new TextureSlot[] { TextureSlot.TEXTURE });
/*     */   public static final ModelTemplate[] STEMS;
/* 132 */   public static final ModelTemplate ANVIL = create("template_anvil", new TextureSlot[] { TextureSlot.TOP }); static {
/* 133 */     STEMS = (ModelTemplate[])IntStream.range(0, 8).mapToObj($$0 -> create("stem_growth" + $$0, "_stage" + $$0, new TextureSlot[] { TextureSlot.STEM })).toArray($$0 -> new ModelTemplate[$$0]);
/* 134 */   } public static final ModelTemplate ATTACHED_STEM = create("stem_fruit", new TextureSlot[] { TextureSlot.STEM, TextureSlot.UPPER_STEM });
/* 135 */   public static final ModelTemplate CROP = create("crop", new TextureSlot[] { TextureSlot.CROP });
/* 136 */   public static final ModelTemplate FARMLAND = create("template_farmland", new TextureSlot[] { TextureSlot.DIRT, TextureSlot.TOP });
/*     */   
/* 138 */   public static final ModelTemplate FIRE_FLOOR = create("template_fire_floor", new TextureSlot[] { TextureSlot.FIRE });
/* 139 */   public static final ModelTemplate FIRE_SIDE = create("template_fire_side", new TextureSlot[] { TextureSlot.FIRE });
/* 140 */   public static final ModelTemplate FIRE_SIDE_ALT = create("template_fire_side_alt", new TextureSlot[] { TextureSlot.FIRE });
/* 141 */   public static final ModelTemplate FIRE_UP = create("template_fire_up", new TextureSlot[] { TextureSlot.FIRE });
/* 142 */   public static final ModelTemplate FIRE_UP_ALT = create("template_fire_up_alt", new TextureSlot[] { TextureSlot.FIRE });
/*     */   
/* 144 */   public static final ModelTemplate CAMPFIRE = create("template_campfire", new TextureSlot[] { TextureSlot.FIRE, TextureSlot.LIT_LOG });
/*     */   
/* 146 */   public static final ModelTemplate LANTERN = create("template_lantern", new TextureSlot[] { TextureSlot.LANTERN });
/* 147 */   public static final ModelTemplate HANGING_LANTERN = create("template_hanging_lantern", "_hanging", new TextureSlot[] { TextureSlot.LANTERN });
/*     */   
/* 149 */   public static final ModelTemplate TORCH = create("template_torch", new TextureSlot[] { TextureSlot.TORCH });
/* 150 */   public static final ModelTemplate WALL_TORCH = create("template_torch_wall", new TextureSlot[] { TextureSlot.TORCH });
/*     */   
/* 152 */   public static final ModelTemplate PISTON = create("template_piston", new TextureSlot[] { TextureSlot.PLATFORM, TextureSlot.BOTTOM, TextureSlot.SIDE });
/* 153 */   public static final ModelTemplate PISTON_HEAD = create("template_piston_head", new TextureSlot[] { TextureSlot.PLATFORM, TextureSlot.SIDE, TextureSlot.UNSTICKY });
/* 154 */   public static final ModelTemplate PISTON_HEAD_SHORT = create("template_piston_head_short", new TextureSlot[] { TextureSlot.PLATFORM, TextureSlot.SIDE, TextureSlot.UNSTICKY });
/* 155 */   public static final ModelTemplate SEAGRASS = create("template_seagrass", new TextureSlot[] { TextureSlot.TEXTURE });
/* 156 */   public static final ModelTemplate TURTLE_EGG = create("template_turtle_egg", new TextureSlot[] { TextureSlot.ALL });
/* 157 */   public static final ModelTemplate TWO_TURTLE_EGGS = create("template_two_turtle_eggs", new TextureSlot[] { TextureSlot.ALL });
/* 158 */   public static final ModelTemplate THREE_TURTLE_EGGS = create("template_three_turtle_eggs", new TextureSlot[] { TextureSlot.ALL });
/* 159 */   public static final ModelTemplate FOUR_TURTLE_EGGS = create("template_four_turtle_eggs", new TextureSlot[] { TextureSlot.ALL });
/* 160 */   public static final ModelTemplate SINGLE_FACE = create("template_single_face", new TextureSlot[] { TextureSlot.TEXTURE });
/* 161 */   public static final ModelTemplate CAULDRON_LEVEL1 = create("template_cauldron_level1", new TextureSlot[] { TextureSlot.CONTENT, TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE });
/* 162 */   public static final ModelTemplate CAULDRON_LEVEL2 = create("template_cauldron_level2", new TextureSlot[] { TextureSlot.CONTENT, TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE });
/* 163 */   public static final ModelTemplate CAULDRON_FULL = create("template_cauldron_full", new TextureSlot[] { TextureSlot.CONTENT, TextureSlot.INSIDE, TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE });
/* 164 */   public static final ModelTemplate AZALEA = create("template_azalea", new TextureSlot[] { TextureSlot.TOP, TextureSlot.SIDE });
/* 165 */   public static final ModelTemplate POTTED_AZALEA = create("template_potted_azalea_bush", new TextureSlot[] { TextureSlot.PLANT, TextureSlot.TOP, TextureSlot.SIDE });
/* 166 */   public static final ModelTemplate POTTED_FLOWERING_AZALEA = create("template_potted_azalea_bush", new TextureSlot[] { TextureSlot.PLANT, TextureSlot.TOP, TextureSlot.SIDE });
/* 167 */   public static final ModelTemplate SNIFFER_EGG = create("sniffer_egg", new TextureSlot[] { TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.NORTH, TextureSlot.SOUTH, TextureSlot.EAST, TextureSlot.WEST });
/*     */   
/* 169 */   public static final ModelTemplate FLAT_ITEM = createItem("generated", new TextureSlot[] { TextureSlot.LAYER0 });
/* 170 */   public static final ModelTemplate MUSIC_DISC = createItem("template_music_disc", new TextureSlot[] { TextureSlot.LAYER0 });
/* 171 */   public static final ModelTemplate FLAT_HANDHELD_ITEM = createItem("handheld", new TextureSlot[] { TextureSlot.LAYER0 });
/* 172 */   public static final ModelTemplate FLAT_HANDHELD_ROD_ITEM = createItem("handheld_rod", new TextureSlot[] { TextureSlot.LAYER0 });
/* 173 */   public static final ModelTemplate TWO_LAYERED_ITEM = createItem("generated", new TextureSlot[] { TextureSlot.LAYER0, TextureSlot.LAYER1 });
/* 174 */   public static final ModelTemplate THREE_LAYERED_ITEM = createItem("generated", new TextureSlot[] { TextureSlot.LAYER0, TextureSlot.LAYER1, TextureSlot.LAYER2 });
/* 175 */   public static final ModelTemplate SHULKER_BOX_INVENTORY = createItem("template_shulker_box", new TextureSlot[] { TextureSlot.PARTICLE });
/* 176 */   public static final ModelTemplate BED_INVENTORY = createItem("template_bed", new TextureSlot[] { TextureSlot.PARTICLE });
/* 177 */   public static final ModelTemplate BANNER_INVENTORY = createItem("template_banner", new TextureSlot[0]);
/* 178 */   public static final ModelTemplate SKULL_INVENTORY = createItem("template_skull", new TextureSlot[0]);
/*     */   
/* 180 */   public static final ModelTemplate CANDLE = create("template_candle", new TextureSlot[] { TextureSlot.ALL, TextureSlot.PARTICLE });
/* 181 */   public static final ModelTemplate TWO_CANDLES = create("template_two_candles", new TextureSlot[] { TextureSlot.ALL, TextureSlot.PARTICLE });
/* 182 */   public static final ModelTemplate THREE_CANDLES = create("template_three_candles", new TextureSlot[] { TextureSlot.ALL, TextureSlot.PARTICLE });
/* 183 */   public static final ModelTemplate FOUR_CANDLES = create("template_four_candles", new TextureSlot[] { TextureSlot.ALL, TextureSlot.PARTICLE });
/*     */   
/* 185 */   public static final ModelTemplate CANDLE_CAKE = create("template_cake_with_candle", new TextureSlot[] { TextureSlot.CANDLE, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE });
/* 186 */   public static final ModelTemplate SCULK_SHRIEKER = create("template_sculk_shrieker", new TextureSlot[] { TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.PARTICLE, TextureSlot.INNER_TOP });
/*     */   
/*     */   private static ModelTemplate create(TextureSlot... $$0) {
/* 189 */     return new ModelTemplate(Optional.empty(), Optional.empty(), $$0);
/*     */   }
/*     */   
/*     */   private static ModelTemplate create(String $$0, TextureSlot... $$1) {
/* 193 */     return new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "block/" + $$0)), Optional.empty(), $$1);
/*     */   }
/*     */   
/*     */   private static ModelTemplate createItem(String $$0, TextureSlot... $$1) {
/* 197 */     return new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "item/" + $$0)), Optional.empty(), $$1);
/*     */   }
/*     */   
/*     */   private static ModelTemplate create(String $$0, String $$1, TextureSlot... $$2) {
/* 201 */     return new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "block/" + $$0)), Optional.of($$1), $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\model\ModelTemplates.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */