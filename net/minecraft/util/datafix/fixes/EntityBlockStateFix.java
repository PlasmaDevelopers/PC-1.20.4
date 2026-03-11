/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.types.templates.Tag;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.datafixers.util.Unit;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.util.datafix.schemas.NamespacedSchema;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityBlockStateFix
/*     */   extends DataFix
/*     */ {
/*     */   private static final Map<String, Integer> MAP;
/*     */   
/*     */   public EntityBlockStateFix(Schema $$0, boolean $$1) {
/*  34 */     super($$0, $$1);
/*     */   }
/*     */   static {
/*  37 */     MAP = (Map<String, Integer>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put("minecraft:air", Integer.valueOf(0));
/*     */           $$0.put("minecraft:stone", Integer.valueOf(1));
/*     */           $$0.put("minecraft:grass", Integer.valueOf(2));
/*     */           $$0.put("minecraft:dirt", Integer.valueOf(3));
/*     */           $$0.put("minecraft:cobblestone", Integer.valueOf(4));
/*     */           $$0.put("minecraft:planks", Integer.valueOf(5));
/*     */           $$0.put("minecraft:sapling", Integer.valueOf(6));
/*     */           $$0.put("minecraft:bedrock", Integer.valueOf(7));
/*     */           $$0.put("minecraft:flowing_water", Integer.valueOf(8));
/*     */           $$0.put("minecraft:water", Integer.valueOf(9));
/*     */           $$0.put("minecraft:flowing_lava", Integer.valueOf(10));
/*     */           $$0.put("minecraft:lava", Integer.valueOf(11));
/*     */           $$0.put("minecraft:sand", Integer.valueOf(12));
/*     */           $$0.put("minecraft:gravel", Integer.valueOf(13));
/*     */           $$0.put("minecraft:gold_ore", Integer.valueOf(14));
/*     */           $$0.put("minecraft:iron_ore", Integer.valueOf(15));
/*     */           $$0.put("minecraft:coal_ore", Integer.valueOf(16));
/*     */           $$0.put("minecraft:log", Integer.valueOf(17));
/*     */           $$0.put("minecraft:leaves", Integer.valueOf(18));
/*     */           $$0.put("minecraft:sponge", Integer.valueOf(19));
/*     */           $$0.put("minecraft:glass", Integer.valueOf(20));
/*     */           $$0.put("minecraft:lapis_ore", Integer.valueOf(21));
/*     */           $$0.put("minecraft:lapis_block", Integer.valueOf(22));
/*     */           $$0.put("minecraft:dispenser", Integer.valueOf(23));
/*     */           $$0.put("minecraft:sandstone", Integer.valueOf(24));
/*     */           $$0.put("minecraft:noteblock", Integer.valueOf(25));
/*     */           $$0.put("minecraft:bed", Integer.valueOf(26));
/*     */           $$0.put("minecraft:golden_rail", Integer.valueOf(27));
/*     */           $$0.put("minecraft:detector_rail", Integer.valueOf(28));
/*     */           $$0.put("minecraft:sticky_piston", Integer.valueOf(29));
/*     */           $$0.put("minecraft:web", Integer.valueOf(30));
/*     */           $$0.put("minecraft:tallgrass", Integer.valueOf(31));
/*     */           $$0.put("minecraft:deadbush", Integer.valueOf(32));
/*     */           $$0.put("minecraft:piston", Integer.valueOf(33));
/*     */           $$0.put("minecraft:piston_head", Integer.valueOf(34));
/*     */           $$0.put("minecraft:wool", Integer.valueOf(35));
/*     */           $$0.put("minecraft:piston_extension", Integer.valueOf(36));
/*     */           $$0.put("minecraft:yellow_flower", Integer.valueOf(37));
/*     */           $$0.put("minecraft:red_flower", Integer.valueOf(38));
/*     */           $$0.put("minecraft:brown_mushroom", Integer.valueOf(39));
/*     */           $$0.put("minecraft:red_mushroom", Integer.valueOf(40));
/*     */           $$0.put("minecraft:gold_block", Integer.valueOf(41));
/*     */           $$0.put("minecraft:iron_block", Integer.valueOf(42));
/*     */           $$0.put("minecraft:double_stone_slab", Integer.valueOf(43));
/*     */           $$0.put("minecraft:stone_slab", Integer.valueOf(44));
/*     */           $$0.put("minecraft:brick_block", Integer.valueOf(45));
/*     */           $$0.put("minecraft:tnt", Integer.valueOf(46));
/*     */           $$0.put("minecraft:bookshelf", Integer.valueOf(47));
/*     */           $$0.put("minecraft:mossy_cobblestone", Integer.valueOf(48));
/*     */           $$0.put("minecraft:obsidian", Integer.valueOf(49));
/*     */           $$0.put("minecraft:torch", Integer.valueOf(50));
/*     */           $$0.put("minecraft:fire", Integer.valueOf(51));
/*     */           $$0.put("minecraft:mob_spawner", Integer.valueOf(52));
/*     */           $$0.put("minecraft:oak_stairs", Integer.valueOf(53));
/*     */           $$0.put("minecraft:chest", Integer.valueOf(54));
/*     */           $$0.put("minecraft:redstone_wire", Integer.valueOf(55));
/*     */           $$0.put("minecraft:diamond_ore", Integer.valueOf(56));
/*     */           $$0.put("minecraft:diamond_block", Integer.valueOf(57));
/*     */           $$0.put("minecraft:crafting_table", Integer.valueOf(58));
/*     */           $$0.put("minecraft:wheat", Integer.valueOf(59));
/*     */           $$0.put("minecraft:farmland", Integer.valueOf(60));
/*     */           $$0.put("minecraft:furnace", Integer.valueOf(61));
/*     */           $$0.put("minecraft:lit_furnace", Integer.valueOf(62));
/*     */           $$0.put("minecraft:standing_sign", Integer.valueOf(63));
/*     */           $$0.put("minecraft:wooden_door", Integer.valueOf(64));
/*     */           $$0.put("minecraft:ladder", Integer.valueOf(65));
/*     */           $$0.put("minecraft:rail", Integer.valueOf(66));
/*     */           $$0.put("minecraft:stone_stairs", Integer.valueOf(67));
/*     */           $$0.put("minecraft:wall_sign", Integer.valueOf(68));
/*     */           $$0.put("minecraft:lever", Integer.valueOf(69));
/*     */           $$0.put("minecraft:stone_pressure_plate", Integer.valueOf(70));
/*     */           $$0.put("minecraft:iron_door", Integer.valueOf(71));
/*     */           $$0.put("minecraft:wooden_pressure_plate", Integer.valueOf(72));
/*     */           $$0.put("minecraft:redstone_ore", Integer.valueOf(73));
/*     */           $$0.put("minecraft:lit_redstone_ore", Integer.valueOf(74));
/*     */           $$0.put("minecraft:unlit_redstone_torch", Integer.valueOf(75));
/*     */           $$0.put("minecraft:redstone_torch", Integer.valueOf(76));
/*     */           $$0.put("minecraft:stone_button", Integer.valueOf(77));
/*     */           $$0.put("minecraft:snow_layer", Integer.valueOf(78));
/*     */           $$0.put("minecraft:ice", Integer.valueOf(79));
/*     */           $$0.put("minecraft:snow", Integer.valueOf(80));
/*     */           $$0.put("minecraft:cactus", Integer.valueOf(81));
/*     */           $$0.put("minecraft:clay", Integer.valueOf(82));
/*     */           $$0.put("minecraft:reeds", Integer.valueOf(83));
/*     */           $$0.put("minecraft:jukebox", Integer.valueOf(84));
/*     */           $$0.put("minecraft:fence", Integer.valueOf(85));
/*     */           $$0.put("minecraft:pumpkin", Integer.valueOf(86));
/*     */           $$0.put("minecraft:netherrack", Integer.valueOf(87));
/*     */           $$0.put("minecraft:soul_sand", Integer.valueOf(88));
/*     */           $$0.put("minecraft:glowstone", Integer.valueOf(89));
/*     */           $$0.put("minecraft:portal", Integer.valueOf(90));
/*     */           $$0.put("minecraft:lit_pumpkin", Integer.valueOf(91));
/*     */           $$0.put("minecraft:cake", Integer.valueOf(92));
/*     */           $$0.put("minecraft:unpowered_repeater", Integer.valueOf(93));
/*     */           $$0.put("minecraft:powered_repeater", Integer.valueOf(94));
/*     */           $$0.put("minecraft:stained_glass", Integer.valueOf(95));
/*     */           $$0.put("minecraft:trapdoor", Integer.valueOf(96));
/*     */           $$0.put("minecraft:monster_egg", Integer.valueOf(97));
/*     */           $$0.put("minecraft:stonebrick", Integer.valueOf(98));
/*     */           $$0.put("minecraft:brown_mushroom_block", Integer.valueOf(99));
/*     */           $$0.put("minecraft:red_mushroom_block", Integer.valueOf(100));
/*     */           $$0.put("minecraft:iron_bars", Integer.valueOf(101));
/*     */           $$0.put("minecraft:glass_pane", Integer.valueOf(102));
/*     */           $$0.put("minecraft:melon_block", Integer.valueOf(103));
/*     */           $$0.put("minecraft:pumpkin_stem", Integer.valueOf(104));
/*     */           $$0.put("minecraft:melon_stem", Integer.valueOf(105));
/*     */           $$0.put("minecraft:vine", Integer.valueOf(106));
/*     */           $$0.put("minecraft:fence_gate", Integer.valueOf(107));
/*     */           $$0.put("minecraft:brick_stairs", Integer.valueOf(108));
/*     */           $$0.put("minecraft:stone_brick_stairs", Integer.valueOf(109));
/*     */           $$0.put("minecraft:mycelium", Integer.valueOf(110));
/*     */           $$0.put("minecraft:waterlily", Integer.valueOf(111));
/*     */           $$0.put("minecraft:nether_brick", Integer.valueOf(112));
/*     */           $$0.put("minecraft:nether_brick_fence", Integer.valueOf(113));
/*     */           $$0.put("minecraft:nether_brick_stairs", Integer.valueOf(114));
/*     */           $$0.put("minecraft:nether_wart", Integer.valueOf(115));
/*     */           $$0.put("minecraft:enchanting_table", Integer.valueOf(116));
/*     */           $$0.put("minecraft:brewing_stand", Integer.valueOf(117));
/*     */           $$0.put("minecraft:cauldron", Integer.valueOf(118));
/*     */           $$0.put("minecraft:end_portal", Integer.valueOf(119));
/*     */           $$0.put("minecraft:end_portal_frame", Integer.valueOf(120));
/*     */           $$0.put("minecraft:end_stone", Integer.valueOf(121));
/*     */           $$0.put("minecraft:dragon_egg", Integer.valueOf(122));
/*     */           $$0.put("minecraft:redstone_lamp", Integer.valueOf(123));
/*     */           $$0.put("minecraft:lit_redstone_lamp", Integer.valueOf(124));
/*     */           $$0.put("minecraft:double_wooden_slab", Integer.valueOf(125));
/*     */           $$0.put("minecraft:wooden_slab", Integer.valueOf(126));
/*     */           $$0.put("minecraft:cocoa", Integer.valueOf(127));
/*     */           $$0.put("minecraft:sandstone_stairs", Integer.valueOf(128));
/*     */           $$0.put("minecraft:emerald_ore", Integer.valueOf(129));
/*     */           $$0.put("minecraft:ender_chest", Integer.valueOf(130));
/*     */           $$0.put("minecraft:tripwire_hook", Integer.valueOf(131));
/*     */           $$0.put("minecraft:tripwire", Integer.valueOf(132));
/*     */           $$0.put("minecraft:emerald_block", Integer.valueOf(133));
/*     */           $$0.put("minecraft:spruce_stairs", Integer.valueOf(134));
/*     */           $$0.put("minecraft:birch_stairs", Integer.valueOf(135));
/*     */           $$0.put("minecraft:jungle_stairs", Integer.valueOf(136));
/*     */           $$0.put("minecraft:command_block", Integer.valueOf(137));
/*     */           $$0.put("minecraft:beacon", Integer.valueOf(138));
/*     */           $$0.put("minecraft:cobblestone_wall", Integer.valueOf(139));
/*     */           $$0.put("minecraft:flower_pot", Integer.valueOf(140));
/*     */           $$0.put("minecraft:carrots", Integer.valueOf(141));
/*     */           $$0.put("minecraft:potatoes", Integer.valueOf(142));
/*     */           $$0.put("minecraft:wooden_button", Integer.valueOf(143));
/*     */           $$0.put("minecraft:skull", Integer.valueOf(144));
/*     */           $$0.put("minecraft:anvil", Integer.valueOf(145));
/*     */           $$0.put("minecraft:trapped_chest", Integer.valueOf(146));
/*     */           $$0.put("minecraft:light_weighted_pressure_plate", Integer.valueOf(147));
/*     */           $$0.put("minecraft:heavy_weighted_pressure_plate", Integer.valueOf(148));
/*     */           $$0.put("minecraft:unpowered_comparator", Integer.valueOf(149));
/*     */           $$0.put("minecraft:powered_comparator", Integer.valueOf(150));
/*     */           $$0.put("minecraft:daylight_detector", Integer.valueOf(151));
/*     */           $$0.put("minecraft:redstone_block", Integer.valueOf(152));
/*     */           $$0.put("minecraft:quartz_ore", Integer.valueOf(153));
/*     */           $$0.put("minecraft:hopper", Integer.valueOf(154));
/*     */           $$0.put("minecraft:quartz_block", Integer.valueOf(155));
/*     */           $$0.put("minecraft:quartz_stairs", Integer.valueOf(156));
/*     */           $$0.put("minecraft:activator_rail", Integer.valueOf(157));
/*     */           $$0.put("minecraft:dropper", Integer.valueOf(158));
/*     */           $$0.put("minecraft:stained_hardened_clay", Integer.valueOf(159));
/*     */           $$0.put("minecraft:stained_glass_pane", Integer.valueOf(160));
/*     */           $$0.put("minecraft:leaves2", Integer.valueOf(161));
/*     */           $$0.put("minecraft:log2", Integer.valueOf(162));
/*     */           $$0.put("minecraft:acacia_stairs", Integer.valueOf(163));
/*     */           $$0.put("minecraft:dark_oak_stairs", Integer.valueOf(164));
/*     */           $$0.put("minecraft:slime", Integer.valueOf(165));
/*     */           $$0.put("minecraft:barrier", Integer.valueOf(166));
/*     */           $$0.put("minecraft:iron_trapdoor", Integer.valueOf(167));
/*     */           $$0.put("minecraft:prismarine", Integer.valueOf(168));
/*     */           $$0.put("minecraft:sea_lantern", Integer.valueOf(169));
/*     */           $$0.put("minecraft:hay_block", Integer.valueOf(170));
/*     */           $$0.put("minecraft:carpet", Integer.valueOf(171));
/*     */           $$0.put("minecraft:hardened_clay", Integer.valueOf(172));
/*     */           $$0.put("minecraft:coal_block", Integer.valueOf(173));
/*     */           $$0.put("minecraft:packed_ice", Integer.valueOf(174));
/*     */           $$0.put("minecraft:double_plant", Integer.valueOf(175));
/*     */           $$0.put("minecraft:standing_banner", Integer.valueOf(176));
/*     */           $$0.put("minecraft:wall_banner", Integer.valueOf(177));
/*     */           $$0.put("minecraft:daylight_detector_inverted", Integer.valueOf(178));
/*     */           $$0.put("minecraft:red_sandstone", Integer.valueOf(179));
/*     */           $$0.put("minecraft:red_sandstone_stairs", Integer.valueOf(180));
/*     */           $$0.put("minecraft:double_stone_slab2", Integer.valueOf(181));
/*     */           $$0.put("minecraft:stone_slab2", Integer.valueOf(182));
/*     */           $$0.put("minecraft:spruce_fence_gate", Integer.valueOf(183));
/*     */           $$0.put("minecraft:birch_fence_gate", Integer.valueOf(184));
/*     */           $$0.put("minecraft:jungle_fence_gate", Integer.valueOf(185));
/*     */           $$0.put("minecraft:dark_oak_fence_gate", Integer.valueOf(186));
/*     */           $$0.put("minecraft:acacia_fence_gate", Integer.valueOf(187));
/*     */           $$0.put("minecraft:spruce_fence", Integer.valueOf(188));
/*     */           $$0.put("minecraft:birch_fence", Integer.valueOf(189));
/*     */           $$0.put("minecraft:jungle_fence", Integer.valueOf(190));
/*     */           $$0.put("minecraft:dark_oak_fence", Integer.valueOf(191));
/*     */           $$0.put("minecraft:acacia_fence", Integer.valueOf(192));
/*     */           $$0.put("minecraft:spruce_door", Integer.valueOf(193));
/*     */           $$0.put("minecraft:birch_door", Integer.valueOf(194));
/*     */           $$0.put("minecraft:jungle_door", Integer.valueOf(195));
/*     */           $$0.put("minecraft:acacia_door", Integer.valueOf(196));
/*     */           $$0.put("minecraft:dark_oak_door", Integer.valueOf(197));
/*     */           $$0.put("minecraft:end_rod", Integer.valueOf(198));
/*     */           $$0.put("minecraft:chorus_plant", Integer.valueOf(199));
/*     */           $$0.put("minecraft:chorus_flower", Integer.valueOf(200));
/*     */           $$0.put("minecraft:purpur_block", Integer.valueOf(201));
/*     */           $$0.put("minecraft:purpur_pillar", Integer.valueOf(202));
/*     */           $$0.put("minecraft:purpur_stairs", Integer.valueOf(203));
/*     */           $$0.put("minecraft:purpur_double_slab", Integer.valueOf(204));
/*     */           $$0.put("minecraft:purpur_slab", Integer.valueOf(205));
/*     */           $$0.put("minecraft:end_bricks", Integer.valueOf(206));
/*     */           $$0.put("minecraft:beetroots", Integer.valueOf(207));
/*     */           $$0.put("minecraft:grass_path", Integer.valueOf(208));
/*     */           $$0.put("minecraft:end_gateway", Integer.valueOf(209));
/*     */           $$0.put("minecraft:repeating_command_block", Integer.valueOf(210));
/*     */           $$0.put("minecraft:chain_command_block", Integer.valueOf(211));
/*     */           $$0.put("minecraft:frosted_ice", Integer.valueOf(212));
/*     */           $$0.put("minecraft:magma", Integer.valueOf(213));
/*     */           $$0.put("minecraft:nether_wart_block", Integer.valueOf(214));
/*     */           $$0.put("minecraft:red_nether_brick", Integer.valueOf(215));
/*     */           $$0.put("minecraft:bone_block", Integer.valueOf(216));
/*     */           $$0.put("minecraft:structure_void", Integer.valueOf(217));
/*     */           $$0.put("minecraft:observer", Integer.valueOf(218));
/*     */           $$0.put("minecraft:white_shulker_box", Integer.valueOf(219));
/*     */           $$0.put("minecraft:orange_shulker_box", Integer.valueOf(220));
/*     */           $$0.put("minecraft:magenta_shulker_box", Integer.valueOf(221));
/*     */           $$0.put("minecraft:light_blue_shulker_box", Integer.valueOf(222));
/*     */           $$0.put("minecraft:yellow_shulker_box", Integer.valueOf(223));
/*     */           $$0.put("minecraft:lime_shulker_box", Integer.valueOf(224));
/*     */           $$0.put("minecraft:pink_shulker_box", Integer.valueOf(225));
/*     */           $$0.put("minecraft:gray_shulker_box", Integer.valueOf(226));
/*     */           $$0.put("minecraft:silver_shulker_box", Integer.valueOf(227));
/*     */           $$0.put("minecraft:cyan_shulker_box", Integer.valueOf(228));
/*     */           $$0.put("minecraft:purple_shulker_box", Integer.valueOf(229));
/*     */           $$0.put("minecraft:blue_shulker_box", Integer.valueOf(230));
/*     */           $$0.put("minecraft:brown_shulker_box", Integer.valueOf(231));
/*     */           $$0.put("minecraft:green_shulker_box", Integer.valueOf(232));
/*     */           $$0.put("minecraft:red_shulker_box", Integer.valueOf(233));
/*     */           $$0.put("minecraft:black_shulker_box", Integer.valueOf(234));
/*     */           $$0.put("minecraft:white_glazed_terracotta", Integer.valueOf(235));
/*     */           $$0.put("minecraft:orange_glazed_terracotta", Integer.valueOf(236));
/*     */           $$0.put("minecraft:magenta_glazed_terracotta", Integer.valueOf(237));
/*     */           $$0.put("minecraft:light_blue_glazed_terracotta", Integer.valueOf(238));
/*     */           $$0.put("minecraft:yellow_glazed_terracotta", Integer.valueOf(239));
/*     */           $$0.put("minecraft:lime_glazed_terracotta", Integer.valueOf(240));
/*     */           $$0.put("minecraft:pink_glazed_terracotta", Integer.valueOf(241));
/*     */           $$0.put("minecraft:gray_glazed_terracotta", Integer.valueOf(242));
/*     */           $$0.put("minecraft:silver_glazed_terracotta", Integer.valueOf(243));
/*     */           $$0.put("minecraft:cyan_glazed_terracotta", Integer.valueOf(244));
/*     */           $$0.put("minecraft:purple_glazed_terracotta", Integer.valueOf(245));
/*     */           $$0.put("minecraft:blue_glazed_terracotta", Integer.valueOf(246));
/*     */           $$0.put("minecraft:brown_glazed_terracotta", Integer.valueOf(247));
/*     */           $$0.put("minecraft:green_glazed_terracotta", Integer.valueOf(248));
/*     */           $$0.put("minecraft:red_glazed_terracotta", Integer.valueOf(249));
/*     */           $$0.put("minecraft:black_glazed_terracotta", Integer.valueOf(250));
/*     */           $$0.put("minecraft:concrete", Integer.valueOf(251));
/*     */           $$0.put("minecraft:concrete_powder", Integer.valueOf(252));
/*     */           $$0.put("minecraft:structure_block", Integer.valueOf(255));
/*     */         });
/*     */   }
/*     */   public static int getBlockId(String $$0) {
/* 295 */     Integer $$1 = MAP.get($$0);
/* 296 */     return ($$1 == null) ? 0 : $$1.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeRewriteRule makeRule() {
/* 301 */     Schema $$0 = getInputSchema();
/* 302 */     Schema $$1 = getOutputSchema();
/*     */     
/* 304 */     Function<Typed<?>, Typed<?>> $$2 = $$0 -> updateBlockToBlockState($$0, "DisplayTile", "DisplayData", "DisplayState");
/* 305 */     Function<Typed<?>, Typed<?>> $$3 = $$0 -> updateBlockToBlockState($$0, "inTile", "inData", "inBlockState");
/*     */     
/* 307 */     Type<Pair<Either<Pair<String, Either<Integer, String>>, Unit>, Dynamic<?>>> $$4 = DSL.and(
/* 308 */         DSL.optional((Type)DSL.field("inTile", DSL.named(References.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.namespacedString())))), 
/* 309 */         DSL.remainderType());
/*     */ 
/*     */     
/* 312 */     Function<Typed<?>, Typed<?>> $$5 = $$1 -> $$1.update($$0.finder(), DSL.remainderType(), Pair::getSecond);
/*     */     
/* 314 */     return fixTypeEverywhereTyped("EntityBlockStateFix", $$0.getType(References.ENTITY), $$1.getType(References.ENTITY), $$3 -> {
/*     */           $$3 = updateEntity($$3, "minecraft:falling_block", this::updateFallingBlock);
/*     */           $$3 = updateEntity($$3, "minecraft:enderman", ());
/*     */           $$3 = updateEntity($$3, "minecraft:arrow", $$0);
/*     */           $$3 = updateEntity($$3, "minecraft:spectral_arrow", $$0);
/*     */           $$3 = updateEntity($$3, "minecraft:egg", $$1);
/*     */           $$3 = updateEntity($$3, "minecraft:ender_pearl", $$1);
/*     */           $$3 = updateEntity($$3, "minecraft:fireball", $$1);
/*     */           $$3 = updateEntity($$3, "minecraft:potion", $$1);
/*     */           $$3 = updateEntity($$3, "minecraft:small_fireball", $$1);
/*     */           $$3 = updateEntity($$3, "minecraft:snowball", $$1);
/*     */           $$3 = updateEntity($$3, "minecraft:wither_skull", $$1);
/*     */           $$3 = updateEntity($$3, "minecraft:xp_bottle", $$1);
/*     */           $$3 = updateEntity($$3, "minecraft:commandblock_minecart", $$2);
/*     */           $$3 = updateEntity($$3, "minecraft:minecart", $$2);
/*     */           $$3 = updateEntity($$3, "minecraft:chest_minecart", $$2);
/*     */           $$3 = updateEntity($$3, "minecraft:furnace_minecart", $$2);
/*     */           $$3 = updateEntity($$3, "minecraft:tnt_minecart", $$2);
/*     */           $$3 = updateEntity($$3, "minecraft:hopper_minecart", $$2);
/*     */           return updateEntity($$3, "minecraft:spawner_minecart", $$2);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private Typed<?> updateFallingBlock(Typed<?> $$0) {
/* 339 */     Type<Either<Pair<String, Either<Integer, String>>, Unit>> $$1 = DSL.optional((Type)DSL.field("Block", DSL.named(References.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.namespacedString()))));
/* 340 */     Type<Either<Pair<String, Dynamic<?>>, Unit>> $$2 = DSL.optional((Type)DSL.field("BlockState", DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType())));
/*     */     
/* 342 */     Dynamic<?> $$3 = (Dynamic)$$0.get(DSL.remainderFinder());
/*     */     
/* 344 */     return $$0.update($$1.finder(), $$2, $$1 -> {
/*     */           int $$2 = ((Integer)$$1.map((), ())).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           int $$3 = $$0.get("Data").asInt(0) & 0xF;
/*     */ 
/*     */ 
/*     */           
/*     */           return Either.left(Pair.of(References.BLOCK_STATE.typeName(), BlockStateData.getTag($$2 << 4 | $$3)));
/* 355 */         }).set(DSL.remainderFinder(), $$3.remove("Data").remove("TileID").remove("Tile"));
/*     */   }
/*     */   
/*     */   private Typed<?> updateBlockToBlockState(Typed<?> $$0, String $$1, String $$2, String $$3) {
/* 359 */     Tag.TagType tagType1 = DSL.field($$1, DSL.named(References.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), NamespacedSchema.namespacedString())));
/* 360 */     Tag.TagType tagType2 = DSL.field($$3, DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType()));
/*     */     
/* 362 */     Dynamic<?> $$6 = (Dynamic)$$0.getOrCreate(DSL.remainderFinder());
/*     */     
/* 364 */     return $$0.update(tagType1.finder(), (Type)tagType2, $$2 -> {
/*     */           int $$3 = ((Integer)((Either)$$2.getSecond()).map((), EntityBlockStateFix::getBlockId)).intValue();
/*     */           
/*     */           int $$4 = $$0.get($$1).asInt(0) & 0xF;
/*     */           return Pair.of(References.BLOCK_STATE.typeName(), BlockStateData.getTag($$3 << 4 | $$4));
/* 369 */         }).set(DSL.remainderFinder(), $$6.remove($$2));
/*     */   }
/*     */   
/*     */   private Typed<?> updateEntity(Typed<?> $$0, String $$1, Function<Typed<?>, Typed<?>> $$2) {
/* 373 */     Type<?> $$3 = getInputSchema().getChoiceType(References.ENTITY, $$1);
/* 374 */     Type<?> $$4 = getOutputSchema().getChoiceType(References.ENTITY, $$1);
/* 375 */     return $$0.updateTyped(DSL.namedChoice($$1, $$3), $$4, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\EntityBlockStateFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */