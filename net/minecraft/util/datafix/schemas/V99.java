/*     */ package net.minecraft.util.datafix.schemas;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.templates.Hook;
/*     */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.util.datafix.fixes.References;
/*     */ import org.slf4j.Logger;
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
/*     */ public class V99
/*     */   extends Schema
/*     */ {
/*  59 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public V99(int $$0, Schema $$1) {
/*  62 */     super($$0, $$1);
/*     */   }
/*     */   static final Map<String, String> ITEM_TO_BLOCKENTITY;
/*     */   protected static TypeTemplate equipment(Schema $$0) {
/*  66 */     return DSL.optionalFields("Equipment", 
/*  67 */         DSL.list(References.ITEM_STACK.in($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/*  72 */     $$0.register($$1, $$2, () -> equipment($$0));
/*     */   }
/*     */   
/*     */   protected static void registerThrowableProjectile(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/*  76 */     $$0.register($$1, $$2, () -> DSL.optionalFields("inTile", References.BLOCK_NAME.in($$0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void registerMinecart(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/*  83 */     $$0.register($$1, $$2, () -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void registerInventory(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/*  89 */     $$0.register($$1, $$2, () -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/*  96 */     Map<String, Supplier<TypeTemplate>> $$1 = Maps.newHashMap();
/*     */     
/*  98 */     $$0.register($$1, "Item", $$1 -> DSL.optionalFields("Item", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 101 */     $$0.registerSimple($$1, "XPOrb");
/* 102 */     registerThrowableProjectile($$0, $$1, "ThrownEgg");
/* 103 */     $$0.registerSimple($$1, "LeashKnot");
/* 104 */     $$0.registerSimple($$1, "Painting");
/* 105 */     $$0.register($$1, "Arrow", $$1 -> DSL.optionalFields("inTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */     
/* 108 */     $$0.register($$1, "TippedArrow", $$1 -> DSL.optionalFields("inTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */     
/* 111 */     $$0.register($$1, "SpectralArrow", $$1 -> DSL.optionalFields("inTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */     
/* 114 */     registerThrowableProjectile($$0, $$1, "Snowball");
/* 115 */     registerThrowableProjectile($$0, $$1, "Fireball");
/* 116 */     registerThrowableProjectile($$0, $$1, "SmallFireball");
/* 117 */     registerThrowableProjectile($$0, $$1, "ThrownEnderpearl");
/* 118 */     $$0.registerSimple($$1, "EyeOfEnderSignal");
/* 119 */     $$0.register($$1, "ThrownPotion", $$1 -> DSL.optionalFields("inTile", References.BLOCK_NAME.in($$0), "Potion", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */ 
/*     */     
/* 123 */     registerThrowableProjectile($$0, $$1, "ThrownExpBottle");
/* 124 */     $$0.register($$1, "ItemFrame", $$1 -> DSL.optionalFields("Item", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 127 */     registerThrowableProjectile($$0, $$1, "WitherSkull");
/* 128 */     $$0.registerSimple($$1, "PrimedTnt");
/* 129 */     $$0.register($$1, "FallingSand", $$1 -> DSL.optionalFields("Block", References.BLOCK_NAME.in($$0), "TileEntityData", References.BLOCK_ENTITY.in($$0)));
/*     */ 
/*     */ 
/*     */     
/* 133 */     $$0.register($$1, "FireworksRocketEntity", $$1 -> DSL.optionalFields("FireworksItem", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 136 */     $$0.registerSimple($$1, "Boat");
/*     */ 
/*     */     
/* 139 */     $$0.register($$1, "Minecart", () -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0), "Items", DSL.list(References.ITEM_STACK.in($$0))));
/*     */ 
/*     */ 
/*     */     
/* 143 */     registerMinecart($$0, $$1, "MinecartRideable");
/* 144 */     $$0.register($$1, "MinecartChest", $$1 -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0), "Items", DSL.list(References.ITEM_STACK.in($$0))));
/*     */ 
/*     */ 
/*     */     
/* 148 */     registerMinecart($$0, $$1, "MinecartFurnace");
/* 149 */     registerMinecart($$0, $$1, "MinecartTNT");
/* 150 */     $$0.register($$1, "MinecartSpawner", () -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0), References.UNTAGGED_SPAWNER.in($$0)));
/*     */ 
/*     */ 
/*     */     
/* 154 */     $$0.register($$1, "MinecartHopper", $$1 -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0), "Items", DSL.list(References.ITEM_STACK.in($$0))));
/*     */ 
/*     */ 
/*     */     
/* 158 */     registerMinecart($$0, $$1, "MinecartCommandBlock");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     registerMob($$0, $$1, "ArmorStand");
/* 164 */     registerMob($$0, $$1, "Creeper");
/* 165 */     registerMob($$0, $$1, "Skeleton");
/* 166 */     registerMob($$0, $$1, "Spider");
/* 167 */     registerMob($$0, $$1, "Giant");
/* 168 */     registerMob($$0, $$1, "Zombie");
/* 169 */     registerMob($$0, $$1, "Slime");
/* 170 */     registerMob($$0, $$1, "Ghast");
/* 171 */     registerMob($$0, $$1, "PigZombie");
/* 172 */     $$0.register($$1, "Enderman", $$1 -> DSL.optionalFields("carried", References.BLOCK_NAME.in($$0), equipment($$0)));
/*     */ 
/*     */ 
/*     */     
/* 176 */     registerMob($$0, $$1, "CaveSpider");
/* 177 */     registerMob($$0, $$1, "Silverfish");
/* 178 */     registerMob($$0, $$1, "Blaze");
/* 179 */     registerMob($$0, $$1, "LavaSlime");
/* 180 */     registerMob($$0, $$1, "EnderDragon");
/* 181 */     registerMob($$0, $$1, "WitherBoss");
/* 182 */     registerMob($$0, $$1, "Bat");
/* 183 */     registerMob($$0, $$1, "Witch");
/* 184 */     registerMob($$0, $$1, "Endermite");
/* 185 */     registerMob($$0, $$1, "Guardian");
/* 186 */     registerMob($$0, $$1, "Pig");
/* 187 */     registerMob($$0, $$1, "Sheep");
/* 188 */     registerMob($$0, $$1, "Cow");
/* 189 */     registerMob($$0, $$1, "Chicken");
/* 190 */     registerMob($$0, $$1, "Squid");
/* 191 */     registerMob($$0, $$1, "Wolf");
/* 192 */     registerMob($$0, $$1, "MushroomCow");
/* 193 */     registerMob($$0, $$1, "SnowMan");
/* 194 */     registerMob($$0, $$1, "Ozelot");
/* 195 */     registerMob($$0, $$1, "VillagerGolem");
/* 196 */     $$0.register($$1, "EntityHorse", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "ArmorItem", References.ITEM_STACK.in($$0), "SaddleItem", References.ITEM_STACK.in($$0), equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     registerMob($$0, $$1, "Rabbit");
/* 203 */     $$0.register($$1, "Villager", $$1 -> DSL.optionalFields("Inventory", DSL.list(References.ITEM_STACK.in($$0)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", References.ITEM_STACK.in($$0), "buyB", References.ITEM_STACK.in($$0), "sell", References.ITEM_STACK.in($$0)))), equipment($$0)));
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
/* 216 */     $$0.registerSimple($$1, "EnderCrystal");
/*     */ 
/*     */ 
/*     */     
/* 220 */     $$0.registerSimple($$1, "AreaEffectCloud");
/* 221 */     $$0.registerSimple($$1, "ShulkerBullet");
/* 222 */     registerMob($$0, $$1, "Shulker");
/*     */     
/* 224 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema $$0) {
/* 229 */     Map<String, Supplier<TypeTemplate>> $$1 = Maps.newHashMap();
/*     */     
/* 231 */     registerInventory($$0, $$1, "Furnace");
/* 232 */     registerInventory($$0, $$1, "Chest");
/* 233 */     $$0.registerSimple($$1, "EnderChest");
/* 234 */     $$0.register($$1, "RecordPlayer", $$1 -> DSL.optionalFields("RecordItem", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 237 */     registerInventory($$0, $$1, "Trap");
/* 238 */     registerInventory($$0, $$1, "Dropper");
/* 239 */     $$0.registerSimple($$1, "Sign");
/* 240 */     $$0.register($$1, "MobSpawner", $$1 -> References.UNTAGGED_SPAWNER.in($$0));
/* 241 */     $$0.registerSimple($$1, "Music");
/* 242 */     $$0.registerSimple($$1, "Piston");
/* 243 */     registerInventory($$0, $$1, "Cauldron");
/* 244 */     $$0.registerSimple($$1, "EnchantTable");
/* 245 */     $$0.registerSimple($$1, "Airportal");
/* 246 */     $$0.registerSimple($$1, "Control");
/* 247 */     $$0.registerSimple($$1, "Beacon");
/* 248 */     $$0.registerSimple($$1, "Skull");
/* 249 */     $$0.registerSimple($$1, "DLDetector");
/* 250 */     registerInventory($$0, $$1, "Hopper");
/* 251 */     $$0.registerSimple($$1, "Comparator");
/* 252 */     $$0.register($$1, "FlowerPot", $$1 -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), References.ITEM_NAME.in($$0))));
/*     */ 
/*     */     
/* 255 */     $$0.registerSimple($$1, "Banner");
/*     */ 
/*     */ 
/*     */     
/* 259 */     $$0.registerSimple($$1, "Structure");
/* 260 */     $$0.registerSimple($$1, "EndGateway");
/* 261 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 266 */     $$0.registerType(false, References.LEVEL, DSL::remainder);
/* 267 */     $$0.registerType(false, References.PLAYER, () -> DSL.optionalFields("Inventory", DSL.list(References.ITEM_STACK.in($$0)), "EnderItems", DSL.list(References.ITEM_STACK.in($$0))));
/*     */ 
/*     */ 
/*     */     
/* 271 */     $$0.registerType(false, References.CHUNK, () -> DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(References.ENTITY_TREE.in($$0)), "TileEntities", DSL.list(DSL.or(References.BLOCK_ENTITY.in($$0), DSL.remainder())), "TileTicks", DSL.list(DSL.fields("i", References.BLOCK_NAME.in($$0))))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 278 */     $$0.registerType(true, References.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy("id", DSL.string(), $$0));
/* 279 */     $$0.registerType(true, References.ENTITY_TREE, () -> DSL.optionalFields("Riding", References.ENTITY_TREE.in($$0), References.ENTITY.in($$0)));
/*     */ 
/*     */ 
/*     */     
/* 283 */     $$0.registerType(false, References.ENTITY_NAME, () -> DSL.constType(NamespacedSchema.namespacedString()));
/* 284 */     $$0.registerType(true, References.ENTITY, () -> DSL.taggedChoiceLazy("id", DSL.string(), $$0));
/* 285 */     $$0.registerType(true, References.ITEM_STACK, () -> DSL.hook(DSL.optionalFields("id", DSL.or(DSL.constType(DSL.intType()), References.ITEM_NAME.in($$0)), "tag", DSL.optionalFields("EntityTag", References.ENTITY_TREE.in($$0), "BlockEntityTag", References.BLOCK_ENTITY.in($$0), "CanDestroy", DSL.list(References.BLOCK_NAME.in($$0)), "CanPlaceOn", DSL.list(References.BLOCK_NAME.in($$0)), "Items", DSL.list(References.ITEM_STACK.in($$0)))), ADD_NAMES, Hook.HookFunction.IDENTITY));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     $$0.registerType(false, References.OPTIONS, DSL::remainder);
/* 296 */     $$0.registerType(false, References.BLOCK_NAME, () -> DSL.or(DSL.constType(DSL.intType()), DSL.constType(NamespacedSchema.namespacedString())));
/* 297 */     $$0.registerType(false, References.ITEM_NAME, () -> DSL.constType(NamespacedSchema.namespacedString()));
/* 298 */     $$0.registerType(false, References.STATS, DSL::remainder);
/* 299 */     $$0.registerType(false, References.SAVED_DATA_COMMAND_STORAGE, DSL::remainder);
/* 300 */     $$0.registerType(false, References.SAVED_DATA_FORCED_CHUNKS, DSL::remainder);
/* 301 */     $$0.registerType(false, References.SAVED_DATA_MAP_DATA, DSL::remainder);
/* 302 */     $$0.registerType(false, References.SAVED_DATA_MAP_INDEX, DSL::remainder);
/* 303 */     $$0.registerType(false, References.SAVED_DATA_RAIDS, DSL::remainder);
/* 304 */     $$0.registerType(false, References.SAVED_DATA_RANDOM_SEQUENCES, DSL::remainder);
/* 305 */     $$0.registerType(false, References.SAVED_DATA_SCOREBOARD, () -> DSL.optionalFields("data", DSL.optionalFields("Objectives", DSL.list(References.OBJECTIVE.in($$0)), "Teams", DSL.list(References.TEAM.in($$0)))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     $$0.registerType(false, References.SAVED_DATA_STRUCTURE_FEATURE_INDICES, () -> DSL.optionalFields("data", DSL.optionalFields("Features", DSL.compoundList(References.STRUCTURE_FEATURE.in($$0)))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     $$0.registerType(false, References.STRUCTURE_FEATURE, DSL::remainder);
/* 317 */     $$0.registerType(false, References.OBJECTIVE, DSL::remainder);
/* 318 */     $$0.registerType(false, References.TEAM, DSL::remainder);
/*     */     
/* 320 */     $$0.registerType(true, References.UNTAGGED_SPAWNER, DSL::remainder);
/* 321 */     $$0.registerType(false, References.POI_CHUNK, DSL::remainder);
/* 322 */     $$0.registerType(false, References.WORLD_GEN_SETTINGS, DSL::remainder);
/* 323 */     $$0.registerType(false, References.ENTITY_CHUNK, () -> DSL.optionalFields("Entities", DSL.list(References.ENTITY_TREE.in($$0))));
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 328 */     ITEM_TO_BLOCKENTITY = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put("minecraft:furnace", "Furnace");
/*     */           $$0.put("minecraft:lit_furnace", "Furnace");
/*     */           $$0.put("minecraft:chest", "Chest");
/*     */           $$0.put("minecraft:trapped_chest", "Chest");
/*     */           $$0.put("minecraft:ender_chest", "EnderChest");
/*     */           $$0.put("minecraft:jukebox", "RecordPlayer");
/*     */           $$0.put("minecraft:dispenser", "Trap");
/*     */           $$0.put("minecraft:dropper", "Dropper");
/*     */           $$0.put("minecraft:sign", "Sign");
/*     */           $$0.put("minecraft:mob_spawner", "MobSpawner");
/*     */           $$0.put("minecraft:noteblock", "Music");
/*     */           $$0.put("minecraft:brewing_stand", "Cauldron");
/*     */           $$0.put("minecraft:enhanting_table", "EnchantTable");
/*     */           $$0.put("minecraft:command_block", "CommandBlock");
/*     */           $$0.put("minecraft:beacon", "Beacon");
/*     */           $$0.put("minecraft:skull", "Skull");
/*     */           $$0.put("minecraft:daylight_detector", "DLDetector");
/*     */           $$0.put("minecraft:hopper", "Hopper");
/*     */           $$0.put("minecraft:banner", "Banner");
/*     */           $$0.put("minecraft:flower_pot", "FlowerPot");
/*     */           $$0.put("minecraft:repeating_command_block", "CommandBlock");
/*     */           $$0.put("minecraft:chain_command_block", "CommandBlock");
/*     */           $$0.put("minecraft:standing_sign", "Sign");
/*     */           $$0.put("minecraft:wall_sign", "Sign");
/*     */           $$0.put("minecraft:piston_head", "Piston");
/*     */           $$0.put("minecraft:daylight_detector_inverted", "DLDetector");
/*     */           $$0.put("minecraft:unpowered_comparator", "Comparator");
/*     */           $$0.put("minecraft:powered_comparator", "Comparator");
/*     */           $$0.put("minecraft:wall_banner", "Banner");
/*     */           $$0.put("minecraft:standing_banner", "Banner");
/*     */           $$0.put("minecraft:structure_block", "Structure");
/*     */           $$0.put("minecraft:end_portal", "Airportal");
/*     */           $$0.put("minecraft:end_gateway", "EndGateway");
/*     */           $$0.put("minecraft:shield", "Banner");
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 370 */   protected static final Hook.HookFunction ADD_NAMES = new Hook.HookFunction()
/*     */     {
/*     */       public <T> T apply(DynamicOps<T> $$0, T $$1) {
/* 373 */         return V99.addNames(new Dynamic($$0, $$1), V99.ITEM_TO_BLOCKENTITY, "ArmorStand");
/*     */       }
/*     */     };
/*     */   
/*     */   protected static <T> T addNames(Dynamic<T> $$0, Map<String, String> $$1, String $$2) {
/* 378 */     return (T)$$0.update("tag", $$3 -> $$3.update("BlockEntityTag", ()).update("EntityTag", ()))
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
/* 396 */       .getValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V99.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */