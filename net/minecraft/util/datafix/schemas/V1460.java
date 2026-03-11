/*     */ package net.minecraft.util.datafix.schemas;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.templates.Hook;
/*     */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.util.datafix.fixes.References;
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
/*     */ public class V1460
/*     */   extends NamespacedSchema
/*     */ {
/*     */   public V1460(int $$0, Schema $$1) {
/*  60 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/*  64 */     $$0.register($$1, $$2, () -> V100.equipment($$0));
/*     */   }
/*     */   
/*     */   protected static void registerInventory(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/*  68 */     $$0.register($$1, $$2, () -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/*  75 */     Map<String, Supplier<TypeTemplate>> $$1 = Maps.newHashMap();
/*     */     
/*  77 */     $$0.registerSimple($$1, "minecraft:area_effect_cloud");
/*  78 */     registerMob($$0, $$1, "minecraft:armor_stand");
/*  79 */     $$0.register($$1, "minecraft:arrow", $$1 -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in($$0)));
/*     */ 
/*     */     
/*  82 */     registerMob($$0, $$1, "minecraft:bat");
/*  83 */     registerMob($$0, $$1, "minecraft:blaze");
/*  84 */     $$0.registerSimple($$1, "minecraft:boat");
/*  85 */     registerMob($$0, $$1, "minecraft:cave_spider");
/*  86 */     $$0.register($$1, "minecraft:chest_minecart", $$1 -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), "Items", DSL.list(References.ITEM_STACK.in($$0))));
/*     */ 
/*     */ 
/*     */     
/*  90 */     registerMob($$0, $$1, "minecraft:chicken");
/*  91 */     $$0.register($$1, "minecraft:commandblock_minecart", $$1 -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0)));
/*     */ 
/*     */     
/*  94 */     registerMob($$0, $$1, "minecraft:cow");
/*  95 */     registerMob($$0, $$1, "minecraft:creeper");
/*  96 */     $$0.register($$1, "minecraft:donkey", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     $$0.registerSimple($$1, "minecraft:dragon_fireball");
/* 102 */     $$0.registerSimple($$1, "minecraft:egg");
/* 103 */     registerMob($$0, $$1, "minecraft:elder_guardian");
/* 104 */     $$0.registerSimple($$1, "minecraft:ender_crystal");
/* 105 */     registerMob($$0, $$1, "minecraft:ender_dragon");
/* 106 */     $$0.register($$1, "minecraft:enderman", $$1 -> DSL.optionalFields("carriedBlockState", References.BLOCK_STATE.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */     
/* 110 */     registerMob($$0, $$1, "minecraft:endermite");
/* 111 */     $$0.registerSimple($$1, "minecraft:ender_pearl");
/* 112 */     $$0.registerSimple($$1, "minecraft:evocation_fangs");
/* 113 */     registerMob($$0, $$1, "minecraft:evocation_illager");
/* 114 */     $$0.registerSimple($$1, "minecraft:eye_of_ender_signal");
/* 115 */     $$0.register($$1, "minecraft:falling_block", $$1 -> DSL.optionalFields("BlockState", References.BLOCK_STATE.in($$0), "TileEntityData", References.BLOCK_ENTITY.in($$0)));
/*     */ 
/*     */ 
/*     */     
/* 119 */     $$0.registerSimple($$1, "minecraft:fireball");
/* 120 */     $$0.register($$1, "minecraft:fireworks_rocket", $$1 -> DSL.optionalFields("FireworksItem", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 123 */     $$0.register($$1, "minecraft:furnace_minecart", $$1 -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0)));
/*     */ 
/*     */     
/* 126 */     registerMob($$0, $$1, "minecraft:ghast");
/* 127 */     registerMob($$0, $$1, "minecraft:giant");
/* 128 */     registerMob($$0, $$1, "minecraft:guardian");
/* 129 */     $$0.register($$1, "minecraft:hopper_minecart", $$1 -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), "Items", DSL.list(References.ITEM_STACK.in($$0))));
/*     */ 
/*     */ 
/*     */     
/* 133 */     $$0.register($$1, "minecraft:horse", $$1 -> DSL.optionalFields("ArmorItem", References.ITEM_STACK.in($$0), "SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     registerMob($$0, $$1, "minecraft:husk");
/* 140 */     $$0.registerSimple($$1, "minecraft:illusion_illager");
/* 141 */     $$0.register($$1, "minecraft:item", $$1 -> DSL.optionalFields("Item", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 144 */     $$0.register($$1, "minecraft:item_frame", $$1 -> DSL.optionalFields("Item", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 147 */     $$0.registerSimple($$1, "minecraft:leash_knot");
/* 148 */     $$0.register($$1, "minecraft:llama", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), "DecorItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     $$0.registerSimple($$1, "minecraft:llama_spit");
/* 155 */     registerMob($$0, $$1, "minecraft:magma_cube");
/* 156 */     $$0.register($$1, "minecraft:minecart", $$1 -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0)));
/*     */ 
/*     */     
/* 159 */     registerMob($$0, $$1, "minecraft:mooshroom");
/* 160 */     $$0.register($$1, "minecraft:mule", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 165 */     registerMob($$0, $$1, "minecraft:ocelot");
/* 166 */     $$0.registerSimple($$1, "minecraft:painting");
/* 167 */     $$0.registerSimple($$1, "minecraft:parrot");
/* 168 */     registerMob($$0, $$1, "minecraft:pig");
/* 169 */     registerMob($$0, $$1, "minecraft:polar_bear");
/* 170 */     $$0.register($$1, "minecraft:potion", $$1 -> DSL.optionalFields("Potion", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 173 */     registerMob($$0, $$1, "minecraft:rabbit");
/* 174 */     registerMob($$0, $$1, "minecraft:sheep");
/* 175 */     registerMob($$0, $$1, "minecraft:shulker");
/* 176 */     $$0.registerSimple($$1, "minecraft:shulker_bullet");
/* 177 */     registerMob($$0, $$1, "minecraft:silverfish");
/* 178 */     registerMob($$0, $$1, "minecraft:skeleton");
/* 179 */     $$0.register($$1, "minecraft:skeleton_horse", $$1 -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */     
/* 183 */     registerMob($$0, $$1, "minecraft:slime");
/* 184 */     $$0.registerSimple($$1, "minecraft:small_fireball");
/* 185 */     $$0.registerSimple($$1, "minecraft:snowball");
/* 186 */     registerMob($$0, $$1, "minecraft:snowman");
/* 187 */     $$0.register($$1, "minecraft:spawner_minecart", $$1 -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0), References.UNTAGGED_SPAWNER.in($$0)));
/*     */ 
/*     */ 
/*     */     
/* 191 */     $$0.register($$1, "minecraft:spectral_arrow", $$1 -> DSL.optionalFields("inBlockState", References.BLOCK_STATE.in($$0)));
/*     */ 
/*     */     
/* 194 */     registerMob($$0, $$1, "minecraft:spider");
/* 195 */     registerMob($$0, $$1, "minecraft:squid");
/* 196 */     registerMob($$0, $$1, "minecraft:stray");
/* 197 */     $$0.registerSimple($$1, "minecraft:tnt");
/* 198 */     $$0.register($$1, "minecraft:tnt_minecart", $$1 -> DSL.optionalFields("DisplayState", References.BLOCK_STATE.in($$0)));
/*     */ 
/*     */     
/* 201 */     registerMob($$0, $$1, "minecraft:vex");
/* 202 */     $$0.register($$1, "minecraft:villager", $$1 -> DSL.optionalFields("Inventory", DSL.list(References.ITEM_STACK.in($$0)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", References.ITEM_STACK.in($$0), "buyB", References.ITEM_STACK.in($$0), "sell", References.ITEM_STACK.in($$0)))), V100.equipment($$0)));
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
/* 215 */     registerMob($$0, $$1, "minecraft:villager_golem");
/* 216 */     registerMob($$0, $$1, "minecraft:vindication_illager");
/* 217 */     registerMob($$0, $$1, "minecraft:witch");
/* 218 */     registerMob($$0, $$1, "minecraft:wither");
/* 219 */     registerMob($$0, $$1, "minecraft:wither_skeleton");
/* 220 */     $$0.registerSimple($$1, "minecraft:wither_skull");
/* 221 */     registerMob($$0, $$1, "minecraft:wolf");
/* 222 */     $$0.registerSimple($$1, "minecraft:xp_bottle");
/* 223 */     $$0.registerSimple($$1, "minecraft:xp_orb");
/* 224 */     registerMob($$0, $$1, "minecraft:zombie");
/* 225 */     $$0.register($$1, "minecraft:zombie_horse", $$1 -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */     
/* 229 */     registerMob($$0, $$1, "minecraft:zombie_pigman");
/* 230 */     registerMob($$0, $$1, "minecraft:zombie_villager");
/*     */     
/* 232 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
/* 237 */     Map<String, Supplier<TypeTemplate>> $$1 = Maps.newHashMap();
/*     */     
/* 239 */     registerInventory($$0, $$1, "minecraft:furnace");
/* 240 */     registerInventory($$0, $$1, "minecraft:chest");
/* 241 */     registerInventory($$0, $$1, "minecraft:trapped_chest");
/* 242 */     $$0.registerSimple($$1, "minecraft:ender_chest");
/* 243 */     $$0.register($$1, "minecraft:jukebox", $$1 -> DSL.optionalFields("RecordItem", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 246 */     registerInventory($$0, $$1, "minecraft:dispenser");
/* 247 */     registerInventory($$0, $$1, "minecraft:dropper");
/* 248 */     $$0.registerSimple($$1, "minecraft:sign");
/* 249 */     $$0.register($$1, "minecraft:mob_spawner", $$1 -> References.UNTAGGED_SPAWNER.in($$0));
/* 250 */     $$0.register($$1, "minecraft:piston", $$1 -> DSL.optionalFields("blockState", References.BLOCK_STATE.in($$0)));
/*     */ 
/*     */     
/* 253 */     registerInventory($$0, $$1, "minecraft:brewing_stand");
/* 254 */     $$0.registerSimple($$1, "minecraft:enchanting_table");
/* 255 */     $$0.registerSimple($$1, "minecraft:end_portal");
/* 256 */     $$0.registerSimple($$1, "minecraft:beacon");
/* 257 */     $$0.registerSimple($$1, "minecraft:skull");
/* 258 */     $$0.registerSimple($$1, "minecraft:daylight_detector");
/* 259 */     registerInventory($$0, $$1, "minecraft:hopper");
/* 260 */     $$0.registerSimple($$1, "minecraft:comparator");
/* 261 */     $$0.registerSimple($$1, "minecraft:banner");
/* 262 */     $$0.registerSimple($$1, "minecraft:structure_block");
/* 263 */     $$0.registerSimple($$1, "minecraft:end_gateway");
/* 264 */     $$0.registerSimple($$1, "minecraft:command_block");
/* 265 */     registerInventory($$0, $$1, "minecraft:shulker_box");
/* 266 */     $$0.registerSimple($$1, "minecraft:bed");
/*     */     
/* 268 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 273 */     $$0.registerType(false, References.LEVEL, DSL::remainder);
/* 274 */     $$0.registerType(false, References.RECIPE, () -> DSL.constType(namespacedString()));
/* 275 */     $$0.registerType(false, References.PLAYER, () -> DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", References.ENTITY_TREE.in($$0)), "Inventory", DSL.list(References.ITEM_STACK.in($$0)), "EnderItems", DSL.list(References.ITEM_STACK.in($$0)), DSL.optionalFields("ShoulderEntityLeft", References.ENTITY_TREE.in($$0), "ShoulderEntityRight", References.ENTITY_TREE.in($$0), "recipeBook", DSL.optionalFields("recipes", DSL.list(References.RECIPE.in($$0)), "toBeDisplayed", DSL.list(References.RECIPE.in($$0))))));
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
/* 292 */     $$0.registerType(false, References.CHUNK, () -> DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(References.ENTITY_TREE.in($$0)), "TileEntities", DSL.list(DSL.or(References.BLOCK_ENTITY.in($$0), DSL.remainder())), "TileTicks", DSL.list(DSL.fields("i", References.BLOCK_NAME.in($$0))), "Sections", DSL.list(DSL.optionalFields("Palette", DSL.list(References.BLOCK_STATE.in($$0)))))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     $$0.registerType(true, References.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy("id", namespacedString(), $$0));
/* 303 */     $$0.registerType(true, References.ENTITY_TREE, () -> DSL.optionalFields("Passengers", DSL.list(References.ENTITY_TREE.in($$0)), References.ENTITY.in($$0)));
/*     */ 
/*     */ 
/*     */     
/* 307 */     $$0.registerType(true, References.ENTITY, () -> DSL.taggedChoiceLazy("id", namespacedString(), $$0));
/* 308 */     $$0.registerType(true, References.ITEM_STACK, () -> DSL.hook(DSL.optionalFields("id", References.ITEM_NAME.in($$0), "tag", DSL.optionalFields("EntityTag", References.ENTITY_TREE.in($$0), "BlockEntityTag", References.BLOCK_ENTITY.in($$0), "CanDestroy", DSL.list(References.BLOCK_NAME.in($$0)), "CanPlaceOn", DSL.list(References.BLOCK_NAME.in($$0)), "Items", DSL.list(References.ITEM_STACK.in($$0)))), V705.ADD_NAMES, Hook.HookFunction.IDENTITY));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 318 */     $$0.registerType(false, References.HOTBAR, () -> DSL.compoundList(DSL.list(References.ITEM_STACK.in($$0))));
/* 319 */     $$0.registerType(false, References.OPTIONS, DSL::remainder);
/* 320 */     $$0.registerType(false, References.STRUCTURE, () -> DSL.optionalFields("entities", DSL.list(DSL.optionalFields("nbt", References.ENTITY_TREE.in($$0))), "blocks", DSL.list(DSL.optionalFields("nbt", References.BLOCK_ENTITY.in($$0))), "palette", DSL.list(References.BLOCK_STATE.in($$0))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     $$0.registerType(false, References.BLOCK_NAME, () -> DSL.constType(namespacedString()));
/* 326 */     $$0.registerType(false, References.ITEM_NAME, () -> DSL.constType(namespacedString()));
/* 327 */     $$0.registerType(false, References.BLOCK_STATE, DSL::remainder);
/* 328 */     Supplier<TypeTemplate> $$3 = () -> DSL.compoundList(References.ITEM_NAME.in($$0), DSL.constType(DSL.intType()));
/*     */     
/* 330 */     $$0.registerType(false, References.STATS, () -> DSL.optionalFields("stats", DSL.optionalFields("minecraft:mined", DSL.compoundList(References.BLOCK_NAME.in($$0), DSL.constType(DSL.intType())), "minecraft:crafted", $$1.get(), "minecraft:used", $$1.get(), "minecraft:broken", $$1.get(), "minecraft:picked_up", $$1.get(), DSL.optionalFields("minecraft:dropped", $$1.get(), "minecraft:killed", DSL.compoundList(References.ENTITY_NAME.in($$0), DSL.constType(DSL.intType())), "minecraft:killed_by", DSL.compoundList(References.ENTITY_NAME.in($$0), DSL.constType(DSL.intType())), "minecraft:custom", DSL.compoundList(DSL.constType(namespacedString()), DSL.constType(DSL.intType()))))));
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
/* 345 */     $$0.registerType(false, References.SAVED_DATA_COMMAND_STORAGE, DSL::remainder);
/* 346 */     $$0.registerType(false, References.SAVED_DATA_FORCED_CHUNKS, DSL::remainder);
/* 347 */     $$0.registerType(false, References.SAVED_DATA_MAP_DATA, DSL::remainder);
/* 348 */     $$0.registerType(false, References.SAVED_DATA_MAP_INDEX, DSL::remainder);
/* 349 */     $$0.registerType(false, References.SAVED_DATA_RAIDS, DSL::remainder);
/* 350 */     $$0.registerType(false, References.SAVED_DATA_RANDOM_SEQUENCES, DSL::remainder);
/* 351 */     $$0.registerType(false, References.SAVED_DATA_SCOREBOARD, () -> DSL.optionalFields("data", DSL.optionalFields("Objectives", DSL.list(References.OBJECTIVE.in($$0)), "Teams", DSL.list(References.TEAM.in($$0)))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 357 */     $$0.registerType(false, References.SAVED_DATA_STRUCTURE_FEATURE_INDICES, () -> DSL.optionalFields("data", DSL.optionalFields("Features", DSL.compoundList(References.STRUCTURE_FEATURE.in($$0)))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 362 */     $$0.registerType(false, References.STRUCTURE_FEATURE, DSL::remainder);
/*     */     
/* 364 */     Map<String, Supplier<TypeTemplate>> $$4 = V1451_6.createCriterionTypes($$0);
/* 365 */     $$0.registerType(false, References.OBJECTIVE, () -> DSL.hook(DSL.optionalFields("CriteriaType", (TypeTemplate)DSL.taggedChoiceLazy("type", DSL.string(), $$0)), V1451_6.UNPACK_OBJECTIVE_ID, V1451_6.REPACK_OBJECTIVE_ID));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     $$0.registerType(false, References.TEAM, DSL::remainder);
/* 373 */     $$0.registerType(true, References.UNTAGGED_SPAWNER, () -> DSL.optionalFields("SpawnPotentials", DSL.list(DSL.fields("Entity", References.ENTITY_TREE.in($$0))), "SpawnData", References.ENTITY_TREE.in($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 379 */     $$0.registerType(false, References.ADVANCEMENTS, () -> DSL.optionalFields("minecraft:adventure/adventuring_time", DSL.optionalFields("criteria", DSL.compoundList(References.BIOME.in($$0), DSL.constType(DSL.string()))), "minecraft:adventure/kill_a_mob", DSL.optionalFields("criteria", DSL.compoundList(References.ENTITY_NAME.in($$0), DSL.constType(DSL.string()))), "minecraft:adventure/kill_all_mobs", DSL.optionalFields("criteria", DSL.compoundList(References.ENTITY_NAME.in($$0), DSL.constType(DSL.string()))), "minecraft:husbandry/bred_all_animals", DSL.optionalFields("criteria", DSL.compoundList(References.ENTITY_NAME.in($$0), DSL.constType(DSL.string())))));
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
/* 393 */     $$0.registerType(false, References.BIOME, () -> DSL.constType(namespacedString()));
/* 394 */     $$0.registerType(false, References.ENTITY_NAME, () -> DSL.constType(namespacedString()));
/* 395 */     $$0.registerType(false, References.POI_CHUNK, DSL::remainder);
/* 396 */     $$0.registerType(false, References.WORLD_GEN_SETTINGS, DSL::remainder);
/* 397 */     $$0.registerType(false, References.ENTITY_CHUNK, () -> DSL.optionalFields("Entities", DSL.list(References.ENTITY_TREE.in($$0))));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V1460.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */