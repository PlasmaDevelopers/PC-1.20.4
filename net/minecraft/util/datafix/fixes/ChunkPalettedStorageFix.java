/*     */ package net.minecraft.util.datafix.fixes;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.BitSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.CrudeIncrementalIntIdentityHashBiMap;
/*     */ import net.minecraft.util.datafix.PackedBitStorage;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ChunkPalettedStorageFix extends DataFix {
/*     */   private static final int NORTH_WEST_MASK = 128;
/*     */   private static final int WEST_MASK = 64;
/*     */   private static final int SOUTH_WEST_MASK = 32;
/*     */   private static final int SOUTH_MASK = 16;
/*     */   private static final int SOUTH_EAST_MASK = 8;
/*     */   private static final int EAST_MASK = 4;
/*     */   private static final int NORTH_EAST_MASK = 2;
/*     */   private static final int NORTH_MASK = 1;
/*     */   
/*     */   public ChunkPalettedStorageFix(Schema $$0, boolean $$1) {
/*  44 */     super($$0, $$1);
/*     */   }
/*     */   
/*  47 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  49 */   static final BitSet VIRTUAL = new BitSet(256);
/*  50 */   static final BitSet FIX = new BitSet(256);
/*  51 */   static final Dynamic<?> PUMPKIN = BlockStateData.parse("{Name:'minecraft:pumpkin'}");
/*  52 */   static final Dynamic<?> SNOWY_PODZOL = BlockStateData.parse("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
/*  53 */   static final Dynamic<?> SNOWY_GRASS = BlockStateData.parse("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
/*  54 */   static final Dynamic<?> SNOWY_MYCELIUM = BlockStateData.parse("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
/*  55 */   static final Dynamic<?> UPPER_SUNFLOWER = BlockStateData.parse("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
/*  56 */   static final Dynamic<?> UPPER_LILAC = BlockStateData.parse("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
/*  57 */   static final Dynamic<?> UPPER_TALL_GRASS = BlockStateData.parse("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
/*  58 */   static final Dynamic<?> UPPER_LARGE_FERN = BlockStateData.parse("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
/*  59 */   static final Dynamic<?> UPPER_ROSE_BUSH = BlockStateData.parse("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
/*  60 */   static final Dynamic<?> UPPER_PEONY = BlockStateData.parse("{Name:'minecraft:peony',Properties:{half:'upper'}}"); static final Map<String, Dynamic<?>> FLOWER_POT_MAP; static final Map<String, Dynamic<?>> SKULL_MAP; static final Map<String, Dynamic<?>> DOOR_MAP; static final Map<String, Dynamic<?>> NOTE_BLOCK_MAP; private static final Int2ObjectMap<String> DYE_COLOR_MAP; static final Map<String, Dynamic<?>> BED_BLOCK_MAP; static final Map<String, Dynamic<?>> BANNER_BLOCK_MAP;
/*     */   
/*  62 */   static { FLOWER_POT_MAP = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put("minecraft:air0", BlockStateData.parse("{Name:'minecraft:flower_pot'}"));
/*     */           
/*     */           $$0.put("minecraft:red_flower0", BlockStateData.parse("{Name:'minecraft:potted_poppy'}"));
/*     */           $$0.put("minecraft:red_flower1", BlockStateData.parse("{Name:'minecraft:potted_blue_orchid'}"));
/*     */           $$0.put("minecraft:red_flower2", BlockStateData.parse("{Name:'minecraft:potted_allium'}"));
/*     */           $$0.put("minecraft:red_flower3", BlockStateData.parse("{Name:'minecraft:potted_azure_bluet'}"));
/*     */           $$0.put("minecraft:red_flower4", BlockStateData.parse("{Name:'minecraft:potted_red_tulip'}"));
/*     */           $$0.put("minecraft:red_flower5", BlockStateData.parse("{Name:'minecraft:potted_orange_tulip'}"));
/*     */           $$0.put("minecraft:red_flower6", BlockStateData.parse("{Name:'minecraft:potted_white_tulip'}"));
/*     */           $$0.put("minecraft:red_flower7", BlockStateData.parse("{Name:'minecraft:potted_pink_tulip'}"));
/*     */           $$0.put("minecraft:red_flower8", BlockStateData.parse("{Name:'minecraft:potted_oxeye_daisy'}"));
/*     */           $$0.put("minecraft:yellow_flower0", BlockStateData.parse("{Name:'minecraft:potted_dandelion'}"));
/*     */           $$0.put("minecraft:sapling0", BlockStateData.parse("{Name:'minecraft:potted_oak_sapling'}"));
/*     */           $$0.put("minecraft:sapling1", BlockStateData.parse("{Name:'minecraft:potted_spruce_sapling'}"));
/*     */           $$0.put("minecraft:sapling2", BlockStateData.parse("{Name:'minecraft:potted_birch_sapling'}"));
/*     */           $$0.put("minecraft:sapling3", BlockStateData.parse("{Name:'minecraft:potted_jungle_sapling'}"));
/*     */           $$0.put("minecraft:sapling4", BlockStateData.parse("{Name:'minecraft:potted_acacia_sapling'}"));
/*     */           $$0.put("minecraft:sapling5", BlockStateData.parse("{Name:'minecraft:potted_dark_oak_sapling'}"));
/*     */           $$0.put("minecraft:red_mushroom0", BlockStateData.parse("{Name:'minecraft:potted_red_mushroom'}"));
/*     */           $$0.put("minecraft:brown_mushroom0", BlockStateData.parse("{Name:'minecraft:potted_brown_mushroom'}"));
/*     */           $$0.put("minecraft:deadbush0", BlockStateData.parse("{Name:'minecraft:potted_dead_bush'}"));
/*     */           $$0.put("minecraft:tallgrass2", BlockStateData.parse("{Name:'minecraft:potted_fern'}"));
/*     */           $$0.put("minecraft:cactus0", BlockStateData.getTag(2240));
/*     */         });
/*  87 */     SKULL_MAP = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*     */           mapSkull($$0, 0, "skeleton", "skull");
/*     */ 
/*     */           
/*     */           mapSkull($$0, 1, "wither_skeleton", "skull");
/*     */ 
/*     */           
/*     */           mapSkull($$0, 2, "zombie", "head");
/*     */ 
/*     */           
/*     */           mapSkull($$0, 3, "player", "head");
/*     */ 
/*     */           
/*     */           mapSkull($$0, 4, "creeper", "head");
/*     */ 
/*     */           
/*     */           mapSkull($$0, 5, "dragon", "head");
/*     */         });
/*     */     
/* 106 */     DOOR_MAP = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*     */           mapDoor($$0, "oak_door", 1024);
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
/*     */           mapDoor($$0, "iron_door", 1136);
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
/*     */           mapDoor($$0, "spruce_door", 3088);
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
/*     */           mapDoor($$0, "birch_door", 3104);
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
/*     */           mapDoor($$0, "jungle_door", 3120);
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
/*     */           mapDoor($$0, "acacia_door", 3136);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           mapDoor($$0, "dark_oak_door", 3152);
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     NOTE_BLOCK_MAP = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*     */           for (int $$1 = 0; $$1 < 26; $$1++) {
/*     */             $$0.put("true" + $$1, BlockStateData.parse("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + $$1 + "'}}"));
/*     */             
/*     */             $$0.put("false" + $$1, BlockStateData.parse("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + $$1 + "'}}"));
/*     */           } 
/*     */         });
/* 190 */     DYE_COLOR_MAP = (Int2ObjectMap<String>)DataFixUtils.make(new Int2ObjectOpenHashMap(), $$0 -> {
/*     */           $$0.put(0, "white");
/*     */           
/*     */           $$0.put(1, "orange");
/*     */           $$0.put(2, "magenta");
/*     */           $$0.put(3, "light_blue");
/*     */           $$0.put(4, "yellow");
/*     */           $$0.put(5, "lime");
/*     */           $$0.put(6, "pink");
/*     */           $$0.put(7, "gray");
/*     */           $$0.put(8, "light_gray");
/*     */           $$0.put(9, "cyan");
/*     */           $$0.put(10, "purple");
/*     */           $$0.put(11, "blue");
/*     */           $$0.put(12, "brown");
/*     */           $$0.put(13, "green");
/*     */           $$0.put(14, "red");
/*     */           $$0.put(15, "black");
/*     */         });
/* 209 */     BED_BLOCK_MAP = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*     */           ObjectIterator<Int2ObjectMap.Entry<String>> objectIterator = DYE_COLOR_MAP.int2ObjectEntrySet().iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           while (objectIterator.hasNext()) {
/*     */             Int2ObjectMap.Entry<String> $$1 = objectIterator.next();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (!Objects.equals($$1.getValue(), "red")) {
/*     */               addBeds($$0, $$1.getIntKey(), (String)$$1.getValue());
/*     */             }
/*     */           } 
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     BANNER_BLOCK_MAP = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), $$0 -> {
/*     */           ObjectIterator<Int2ObjectMap.Entry<String>> objectIterator = DYE_COLOR_MAP.int2ObjectEntrySet().iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           while (objectIterator.hasNext()) {
/*     */             Int2ObjectMap.Entry<String> $$1 = objectIterator.next();
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (!Objects.equals($$1.getValue(), "white")) {
/*     */               addBanners($$0, 15 - $$1.getIntKey(), (String)$$1.getValue());
/*     */             }
/*     */           } 
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 252 */     FIX.set(2);
/* 253 */     FIX.set(3);
/* 254 */     FIX.set(110);
/*     */     
/* 256 */     FIX.set(140);
/* 257 */     FIX.set(144);
/*     */     
/* 259 */     FIX.set(25);
/*     */     
/* 261 */     FIX.set(86);
/*     */ 
/*     */     
/* 264 */     FIX.set(26);
/* 265 */     FIX.set(176);
/* 266 */     FIX.set(177);
/*     */     
/* 268 */     FIX.set(175);
/*     */     
/* 270 */     FIX.set(64);
/* 271 */     FIX.set(71);
/* 272 */     FIX.set(193);
/* 273 */     FIX.set(194);
/* 274 */     FIX.set(195);
/* 275 */     FIX.set(196);
/* 276 */     FIX.set(197);
/*     */     
/* 278 */     VIRTUAL.set(54);
/* 279 */     VIRTUAL.set(146);
/*     */     
/* 281 */     VIRTUAL.set(25);
/*     */     
/* 283 */     VIRTUAL.set(26);
/*     */     
/* 285 */     VIRTUAL.set(51);
/*     */     
/* 287 */     VIRTUAL.set(53);
/* 288 */     VIRTUAL.set(67);
/* 289 */     VIRTUAL.set(108);
/* 290 */     VIRTUAL.set(109);
/* 291 */     VIRTUAL.set(114);
/* 292 */     VIRTUAL.set(128);
/* 293 */     VIRTUAL.set(134);
/* 294 */     VIRTUAL.set(135);
/* 295 */     VIRTUAL.set(136);
/* 296 */     VIRTUAL.set(156);
/* 297 */     VIRTUAL.set(163);
/* 298 */     VIRTUAL.set(164);
/* 299 */     VIRTUAL.set(180);
/* 300 */     VIRTUAL.set(203);
/*     */     
/* 302 */     VIRTUAL.set(55);
/*     */     
/* 304 */     VIRTUAL.set(85);
/* 305 */     VIRTUAL.set(113);
/* 306 */     VIRTUAL.set(188);
/* 307 */     VIRTUAL.set(189);
/* 308 */     VIRTUAL.set(190);
/* 309 */     VIRTUAL.set(191);
/* 310 */     VIRTUAL.set(192);
/*     */     
/* 312 */     VIRTUAL.set(93);
/* 313 */     VIRTUAL.set(94);
/*     */     
/* 315 */     VIRTUAL.set(101);
/* 316 */     VIRTUAL.set(102);
/* 317 */     VIRTUAL.set(160);
/*     */     
/* 319 */     VIRTUAL.set(106);
/*     */ 
/*     */     
/* 322 */     VIRTUAL.set(107);
/* 323 */     VIRTUAL.set(183);
/* 324 */     VIRTUAL.set(184);
/* 325 */     VIRTUAL.set(185);
/* 326 */     VIRTUAL.set(186);
/* 327 */     VIRTUAL.set(187);
/*     */     
/* 329 */     VIRTUAL.set(132);
/* 330 */     VIRTUAL.set(139);
/*     */     
/* 332 */     VIRTUAL.set(199); }
/*     */   private static void mapSkull(Map<String, Dynamic<?>> $$0, int $$1, String $$2, String $$3) { $$0.put("" + $$1 + "north", BlockStateData.parse("{Name:'minecraft:" + $$2 + "_wall_" + $$3 + "',Properties:{facing:'north'}}")); $$0.put("" + $$1 + "east", BlockStateData.parse("{Name:'minecraft:" + $$2 + "_wall_" + $$3 + "',Properties:{facing:'east'}}")); $$0.put("" + $$1 + "south", BlockStateData.parse("{Name:'minecraft:" + $$2 + "_wall_" + $$3 + "',Properties:{facing:'south'}}")); $$0.put("" + $$1 + "west", BlockStateData.parse("{Name:'minecraft:" + $$2 + "_wall_" + $$3 + "',Properties:{facing:'west'}}")); for (int $$4 = 0; $$4 < 16; $$4++)
/*     */       $$0.put("" + $$1 + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_" + $$3 + "',Properties:{rotation:'" + $$4 + "'}}"));  }
/* 335 */   private static void mapDoor(Map<String, Dynamic<?>> $$0, String $$1, int $$2) { $$0.put("minecraft:" + $$1 + "eastlowerleftfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "eastlowerleftfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "eastlowerlefttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "eastlowerlefttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "eastlowerrightfalsefalse", BlockStateData.getTag($$2)); $$0.put("minecraft:" + $$1 + "eastlowerrightfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "eastlowerrighttruefalse", BlockStateData.getTag($$2 + 4)); $$0.put("minecraft:" + $$1 + "eastlowerrighttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "eastupperleftfalsefalse", BlockStateData.getTag($$2 + 8)); $$0.put("minecraft:" + $$1 + "eastupperleftfalsetrue", BlockStateData.getTag($$2 + 10)); $$0.put("minecraft:" + $$1 + "eastupperlefttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "eastupperlefttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "eastupperrightfalsefalse", BlockStateData.getTag($$2 + 9)); $$0.put("minecraft:" + $$1 + "eastupperrightfalsetrue", BlockStateData.getTag($$2 + 11)); $$0.put("minecraft:" + $$1 + "eastupperrighttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "eastupperrighttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "northlowerleftfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "northlowerleftfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "northlowerlefttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "northlowerlefttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "northlowerrightfalsefalse", BlockStateData.getTag($$2 + 3)); $$0.put("minecraft:" + $$1 + "northlowerrightfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "northlowerrighttruefalse", BlockStateData.getTag($$2 + 7)); $$0.put("minecraft:" + $$1 + "northlowerrighttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "northupperleftfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "northupperleftfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "northupperlefttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "northupperlefttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "northupperrightfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "northupperrightfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "northupperrighttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "northupperrighttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "southlowerleftfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "southlowerleftfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "southlowerlefttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "southlowerlefttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "southlowerrightfalsefalse", BlockStateData.getTag($$2 + 1)); $$0.put("minecraft:" + $$1 + "southlowerrightfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "southlowerrighttruefalse", BlockStateData.getTag($$2 + 5)); $$0.put("minecraft:" + $$1 + "southlowerrighttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "southupperleftfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "southupperleftfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "southupperlefttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "southupperlefttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "southupperrightfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "southupperrightfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "southupperrighttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "southupperrighttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "westlowerleftfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "westlowerleftfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "westlowerlefttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "westlowerlefttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "westlowerrightfalsefalse", BlockStateData.getTag($$2 + 2)); $$0.put("minecraft:" + $$1 + "westlowerrightfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "westlowerrighttruefalse", BlockStateData.getTag($$2 + 6)); $$0.put("minecraft:" + $$1 + "westlowerrighttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "westupperleftfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "westupperleftfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "westupperlefttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "westupperlefttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "westupperrightfalsefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "westupperrightfalsetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}")); $$0.put("minecraft:" + $$1 + "westupperrighttruefalse", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}")); $$0.put("minecraft:" + $$1 + "westupperrighttruetrue", BlockStateData.parse("{Name:'minecraft:" + $$1 + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}")); } static final Dynamic<?> AIR = BlockStateData.getTag(0);
/*     */   private static void addBeds(Map<String, Dynamic<?>> $$0, int $$1, String $$2) { $$0.put("southfalsefoot" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}")); $$0.put("westfalsefoot" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}")); $$0.put("northfalsefoot" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}")); $$0.put("eastfalsefoot" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}")); $$0.put("southfalsehead" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}")); $$0.put("westfalsehead" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}")); $$0.put("northfalsehead" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}")); $$0.put("eastfalsehead" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}")); $$0.put("southtruehead" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}")); $$0.put("westtruehead" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}")); $$0.put("northtruehead" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}")); $$0.put("easttruehead" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}")); }
/*     */   private static void addBanners(Map<String, Dynamic<?>> $$0, int $$1, String $$2) { for (int $$3 = 0; $$3 < 16; $$3++)
/*     */       $$0.put("" + $$3 + "_" + $$3, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_banner',Properties:{rotation:'" + $$3 + "'}}"));  $$0.put("north_" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_wall_banner',Properties:{facing:'north'}}")); $$0.put("south_" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_wall_banner',Properties:{facing:'south'}}")); $$0.put("west_" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_wall_banner',Properties:{facing:'west'}}"));
/* 339 */     $$0.put("east_" + $$1, BlockStateData.parse("{Name:'minecraft:" + $$2 + "_wall_banner',Properties:{facing:'east'}}")); } private static final int SIZE = 4096; public static String getName(Dynamic<?> $$0) { return $$0.get("Name").asString(""); }
/*     */ 
/*     */   
/*     */   public static String getProperty(Dynamic<?> $$0, String $$1) {
/* 343 */     return $$0.get("Properties").get($$1).asString("");
/*     */   }
/*     */   
/*     */   public static int idFor(CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> $$0, Dynamic<?> $$1) {
/* 347 */     int $$2 = $$0.getId($$1);
/* 348 */     if ($$2 == -1) {
/* 349 */       $$2 = $$0.add($$1);
/*     */     }
/* 351 */     return $$2;
/*     */   }
/*     */   
/*     */   private Dynamic<?> fix(Dynamic<?> $$0) {
/* 355 */     Optional<? extends Dynamic<?>> $$1 = $$0.get("Level").result();
/* 356 */     if ($$1.isPresent() && ((Dynamic)$$1.get()).get("Sections").asStreamOpt().result().isPresent()) {
/* 357 */       return $$0.set("Level", (new UpgradeChunk($$1.get())).write());
/*     */     }
/* 359 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeRewriteRule makeRule() {
/* 364 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/* 365 */     Type<?> $$1 = getOutputSchema().getType(References.CHUNK);
/* 366 */     return writeFixAndRead("ChunkPalettedStorageFix", $$0, $$1, this::fix);
/*     */   }
/*     */   
/*     */   private static class Section {
/* 370 */     private final CrudeIncrementalIntIdentityHashBiMap<Dynamic<?>> palette = CrudeIncrementalIntIdentityHashBiMap.create(32);
/*     */     
/*     */     private final List<Dynamic<?>> listTag;
/*     */     private final Dynamic<?> section;
/*     */     private final boolean hasData;
/* 375 */     final Int2ObjectMap<IntList> toFix = (Int2ObjectMap<IntList>)new Int2ObjectLinkedOpenHashMap();
/*     */     
/* 377 */     final IntList update = (IntList)new IntArrayList();
/*     */     public final int y;
/* 379 */     private final Set<Dynamic<?>> seen = Sets.newIdentityHashSet();
/* 380 */     private final int[] buffer = new int[4096];
/*     */     
/*     */     public Section(Dynamic<?> $$0) {
/* 383 */       this.listTag = Lists.newArrayList();
/* 384 */       this.section = $$0;
/* 385 */       this.y = $$0.get("Y").asInt(0);
/* 386 */       this.hasData = $$0.get("Blocks").result().isPresent();
/*     */     }
/*     */     
/*     */     public Dynamic<?> getBlock(int $$0) {
/* 390 */       if ($$0 < 0 || $$0 > 4095) {
/* 391 */         return ChunkPalettedStorageFix.AIR;
/*     */       }
/*     */       
/* 394 */       Dynamic<?> $$1 = (Dynamic)this.palette.byId(this.buffer[$$0]);
/* 395 */       return ($$1 == null) ? ChunkPalettedStorageFix.AIR : $$1;
/*     */     }
/*     */     
/*     */     public void setBlock(int $$0, Dynamic<?> $$1) {
/* 399 */       if (this.seen.add($$1)) {
/* 400 */         this.listTag.add("%%FILTER_ME%%".equals(ChunkPalettedStorageFix.getName($$1)) ? ChunkPalettedStorageFix.AIR : $$1);
/*     */       }
/* 402 */       this.buffer[$$0] = ChunkPalettedStorageFix.idFor(this.palette, $$1);
/*     */     }
/*     */     
/*     */     public int upgrade(int $$0) {
/* 406 */       if (!this.hasData) {
/* 407 */         return $$0;
/*     */       }
/* 409 */       ByteBuffer $$1 = this.section.get("Blocks").asByteBufferOpt().result().get();
/* 410 */       ChunkPalettedStorageFix.DataLayer $$2 = this.section.get("Data").asByteBufferOpt().map($$0 -> new ChunkPalettedStorageFix.DataLayer(DataFixUtils.toArray($$0))).result().orElseGet(DataLayer::new);
/* 411 */       ChunkPalettedStorageFix.DataLayer $$3 = this.section.get("Add").asByteBufferOpt().map($$0 -> new ChunkPalettedStorageFix.DataLayer(DataFixUtils.toArray($$0))).result().orElseGet(DataLayer::new);
/*     */       
/* 413 */       this.seen.add(ChunkPalettedStorageFix.AIR);
/* 414 */       ChunkPalettedStorageFix.idFor(this.palette, ChunkPalettedStorageFix.AIR);
/* 415 */       this.listTag.add(ChunkPalettedStorageFix.AIR);
/*     */       
/* 417 */       for (int $$4 = 0; $$4 < 4096; $$4++) {
/* 418 */         int $$5 = $$4 & 0xF;
/* 419 */         int $$6 = $$4 >> 8 & 0xF;
/* 420 */         int $$7 = $$4 >> 4 & 0xF;
/* 421 */         int $$8 = $$3.get($$5, $$6, $$7) << 12 | ($$1.get($$4) & 0xFF) << 4 | $$2.get($$5, $$6, $$7);
/*     */         
/* 423 */         if (ChunkPalettedStorageFix.FIX.get($$8 >> 4)) {
/* 424 */           addFix($$8 >> 4, $$4);
/*     */         }
/* 426 */         if (ChunkPalettedStorageFix.VIRTUAL.get($$8 >> 4)) {
/*     */           
/* 428 */           int $$9 = ChunkPalettedStorageFix.getSideMask(($$5 == 0), ($$5 == 15), ($$7 == 0), ($$7 == 15));
/* 429 */           if ($$9 == 0) {
/*     */             
/* 431 */             this.update.add($$4);
/*     */           } else {
/* 433 */             $$0 |= $$9;
/*     */           } 
/*     */         } 
/*     */         
/* 437 */         setBlock($$4, BlockStateData.getTag($$8));
/*     */       } 
/*     */       
/* 440 */       return $$0;
/*     */     }
/*     */     private void addFix(int $$0, int $$1) {
/*     */       IntArrayList intArrayList;
/* 444 */       IntList $$2 = (IntList)this.toFix.get($$0);
/* 445 */       if ($$2 == null) {
/* 446 */         intArrayList = new IntArrayList();
/* 447 */         this.toFix.put($$0, intArrayList);
/*     */       } 
/* 449 */       intArrayList.add($$1);
/*     */     }
/*     */     
/*     */     public Dynamic<?> write() {
/* 453 */       Dynamic<?> $$0 = this.section;
/* 454 */       if (!this.hasData) {
/* 455 */         return $$0;
/*     */       }
/* 457 */       $$0 = $$0.set("Palette", $$0.createList(this.listTag.stream()));
/*     */       
/* 459 */       int $$1 = Math.max(4, DataFixUtils.ceillog2(this.seen.size()));
/* 460 */       PackedBitStorage $$2 = new PackedBitStorage($$1, 4096);
/* 461 */       for (int $$3 = 0; $$3 < this.buffer.length; $$3++) {
/* 462 */         $$2.set($$3, this.buffer[$$3]);
/*     */       }
/*     */       
/* 465 */       $$0 = $$0.set("BlockStates", $$0.createLongList(Arrays.stream($$2.getRaw())));
/*     */       
/* 467 */       $$0 = $$0.remove("Blocks");
/* 468 */       $$0 = $$0.remove("Data");
/* 469 */       $$0 = $$0.remove("Add");
/*     */       
/* 471 */       return $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class UpgradeChunk
/*     */   {
/*     */     private int sides;
/* 478 */     private final ChunkPalettedStorageFix.Section[] sections = new ChunkPalettedStorageFix.Section[16];
/*     */     
/*     */     private final Dynamic<?> level;
/*     */     private final int x;
/*     */     private final int z;
/* 483 */     private final Int2ObjectMap<Dynamic<?>> blockEntities = (Int2ObjectMap<Dynamic<?>>)new Int2ObjectLinkedOpenHashMap(16);
/*     */     
/*     */     public UpgradeChunk(Dynamic<?> $$0) {
/* 486 */       this.level = $$0;
/* 487 */       this.x = $$0.get("xPos").asInt(0) << 4;
/* 488 */       this.z = $$0.get("zPos").asInt(0) << 4;
/*     */       
/* 490 */       $$0.get("TileEntities").asStreamOpt().result().ifPresent($$0 -> $$0.forEach(()));
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
/* 503 */       boolean $$1 = $$0.get("convertedFromAlphaFormat").asBoolean(false);
/*     */       
/* 505 */       $$0.get("Sections").asStreamOpt().result().ifPresent($$0 -> $$0.forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 513 */       for (ChunkPalettedStorageFix.Section $$2 : this.sections) {
/* 514 */         if ($$2 != null)
/*     */         {
/*     */ 
/*     */           
/* 518 */           for (ObjectIterator<Map.Entry<Integer, IntList>> objectIterator = $$2.toFix.entrySet().iterator(); objectIterator.hasNext(); ) { IntListIterator<Integer> intListIterator; Map.Entry<Integer, IntList> $$3 = objectIterator.next();
/* 519 */             int $$4 = $$2.y << 12;
/* 520 */             switch (((Integer)$$3.getKey()).intValue()) {
/*     */               case 2:
/* 522 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$5 = ((Integer)intListIterator.next()).intValue();
/* 523 */                   $$5 |= $$4;
/*     */                   
/* 525 */                   Dynamic<?> $$6 = getBlock($$5);
/* 526 */                   if ("minecraft:grass_block".equals(ChunkPalettedStorageFix.getName($$6))) {
/* 527 */                     String $$7 = ChunkPalettedStorageFix.getName(getBlock(relative($$5, ChunkPalettedStorageFix.Direction.UP)));
/* 528 */                     if ("minecraft:snow".equals($$7) || "minecraft:snow_layer".equals($$7)) {
/* 529 */                       setBlock($$5, ChunkPalettedStorageFix.SNOWY_GRASS);
/*     */                     }
/*     */                   }  }
/*     */               
/*     */ 
/*     */               
/*     */               case 3:
/* 536 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$8 = ((Integer)intListIterator.next()).intValue();
/* 537 */                   $$8 |= $$4;
/*     */                   
/* 539 */                   Dynamic<?> $$9 = getBlock($$8);
/* 540 */                   if ("minecraft:podzol".equals(ChunkPalettedStorageFix.getName($$9))) {
/* 541 */                     String $$10 = ChunkPalettedStorageFix.getName(getBlock(relative($$8, ChunkPalettedStorageFix.Direction.UP)));
/* 542 */                     if ("minecraft:snow".equals($$10) || "minecraft:snow_layer".equals($$10)) {
/* 543 */                       setBlock($$8, ChunkPalettedStorageFix.SNOWY_PODZOL);
/*     */                     }
/*     */                   }  }
/*     */               
/*     */ 
/*     */               
/*     */               case 110:
/* 550 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$11 = ((Integer)intListIterator.next()).intValue();
/* 551 */                   $$11 |= $$4;
/*     */                   
/* 553 */                   Dynamic<?> $$12 = getBlock($$11);
/* 554 */                   if ("minecraft:mycelium".equals(ChunkPalettedStorageFix.getName($$12))) {
/* 555 */                     String $$13 = ChunkPalettedStorageFix.getName(getBlock(relative($$11, ChunkPalettedStorageFix.Direction.UP)));
/* 556 */                     if ("minecraft:snow".equals($$13) || "minecraft:snow_layer".equals($$13)) {
/* 557 */                       setBlock($$11, ChunkPalettedStorageFix.SNOWY_MYCELIUM);
/*     */                     }
/*     */                   }  }
/*     */               
/*     */ 
/*     */               
/*     */               case 25:
/* 564 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$14 = ((Integer)intListIterator.next()).intValue();
/* 565 */                   $$14 |= $$4;
/* 566 */                   Dynamic<?> $$15 = removeBlockEntity($$14);
/* 567 */                   if ($$15 != null) {
/* 568 */                     String $$16 = Boolean.toString($$15.get("powered").asBoolean(false)) + Boolean.toString($$15.get("powered").asBoolean(false));
/* 569 */                     setBlock($$14, ChunkPalettedStorageFix.NOTE_BLOCK_MAP.getOrDefault($$16, ChunkPalettedStorageFix.NOTE_BLOCK_MAP.get("false0")));
/*     */                   }  }
/*     */               
/*     */ 
/*     */               
/*     */               case 26:
/* 575 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$17 = ((Integer)intListIterator.next()).intValue();
/* 576 */                   $$17 |= $$4;
/* 577 */                   Dynamic<?> $$18 = getBlockEntity($$17);
/* 578 */                   Dynamic<?> $$19 = getBlock($$17);
/* 579 */                   if ($$18 != null) {
/* 580 */                     int $$20 = $$18.get("color").asInt(0);
/* 581 */                     if ($$20 != 14 && $$20 >= 0 && $$20 < 16) {
/* 582 */                       String $$21 = ChunkPalettedStorageFix.getProperty($$19, "facing") + ChunkPalettedStorageFix.getProperty($$19, "facing") + ChunkPalettedStorageFix.getProperty($$19, "occupied") + ChunkPalettedStorageFix.getProperty($$19, "part");
/* 583 */                       if (ChunkPalettedStorageFix.BED_BLOCK_MAP.containsKey($$21)) {
/* 584 */                         setBlock($$17, ChunkPalettedStorageFix.BED_BLOCK_MAP.get($$21));
/*     */                       }
/*     */                     } 
/*     */                   }  }
/*     */               
/*     */ 
/*     */               
/*     */               case 176:
/*     */               case 177:
/* 593 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$22 = ((Integer)intListIterator.next()).intValue();
/* 594 */                   $$22 |= $$4;
/* 595 */                   Dynamic<?> $$23 = getBlockEntity($$22);
/* 596 */                   Dynamic<?> $$24 = getBlock($$22);
/* 597 */                   if ($$23 != null) {
/* 598 */                     int $$25 = $$23.get("Base").asInt(0);
/* 599 */                     if ($$25 != 15 && $$25 >= 0 && $$25 < 16) {
/* 600 */                       String $$26 = ChunkPalettedStorageFix.getProperty($$24, (((Integer)$$3.getKey()).intValue() == 176) ? "rotation" : "facing") + "_" + ChunkPalettedStorageFix.getProperty($$24, (((Integer)$$3.getKey()).intValue() == 176) ? "rotation" : "facing");
/* 601 */                       if (ChunkPalettedStorageFix.BANNER_BLOCK_MAP.containsKey($$26)) {
/* 602 */                         setBlock($$22, ChunkPalettedStorageFix.BANNER_BLOCK_MAP.get($$26));
/*     */                       }
/*     */                     } 
/*     */                   }  }
/*     */               
/*     */ 
/*     */               
/*     */               case 86:
/* 610 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$27 = ((Integer)intListIterator.next()).intValue();
/* 611 */                   $$27 |= $$4;
/*     */                   
/* 613 */                   Dynamic<?> $$28 = getBlock($$27);
/* 614 */                   if ("minecraft:carved_pumpkin".equals(ChunkPalettedStorageFix.getName($$28))) {
/* 615 */                     String $$29 = ChunkPalettedStorageFix.getName(getBlock(relative($$27, ChunkPalettedStorageFix.Direction.DOWN)));
/* 616 */                     if ("minecraft:grass_block".equals($$29) || "minecraft:dirt".equals($$29)) {
/* 617 */                       setBlock($$27, ChunkPalettedStorageFix.PUMPKIN);
/*     */                     }
/*     */                   }  }
/*     */               
/*     */ 
/*     */               
/*     */               case 140:
/* 624 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$30 = ((Integer)intListIterator.next()).intValue();
/* 625 */                   $$30 |= $$4;
/* 626 */                   Dynamic<?> $$31 = removeBlockEntity($$30);
/* 627 */                   if ($$31 != null) {
/* 628 */                     String $$32 = $$31.get("Item").asString("") + $$31.get("Item").asString("");
/* 629 */                     setBlock($$30, ChunkPalettedStorageFix.FLOWER_POT_MAP.getOrDefault($$32, ChunkPalettedStorageFix.FLOWER_POT_MAP.get("minecraft:air0")));
/*     */                   }  }
/*     */               
/*     */ 
/*     */               
/*     */               case 144:
/* 635 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$33 = ((Integer)intListIterator.next()).intValue();
/* 636 */                   $$33 |= $$4;
/* 637 */                   Dynamic<?> $$34 = getBlockEntity($$33);
/* 638 */                   if ($$34 != null) {
/* 639 */                     String $$38, $$35 = String.valueOf($$34.get("SkullType").asInt(0));
/* 640 */                     String $$36 = ChunkPalettedStorageFix.getProperty(getBlock($$33), "facing");
/*     */                     
/* 642 */                     if ("up".equals($$36) || "down".equals($$36)) {
/* 643 */                       String $$37 = $$35 + $$35;
/*     */                     } else {
/* 645 */                       $$38 = $$35 + $$35;
/*     */                     } 
/*     */                     
/* 648 */                     $$34.remove("SkullType");
/* 649 */                     $$34.remove("facing");
/* 650 */                     $$34.remove("Rot");
/*     */                     
/* 652 */                     setBlock($$33, ChunkPalettedStorageFix.SKULL_MAP.getOrDefault($$38, ChunkPalettedStorageFix.SKULL_MAP.get("0north")));
/*     */                   }  }
/*     */               
/*     */               
/*     */               case 64:
/*     */               case 71:
/*     */               case 193:
/*     */               case 194:
/*     */               case 195:
/*     */               case 196:
/*     */               case 197:
/* 663 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$39 = ((Integer)intListIterator.next()).intValue();
/* 664 */                   $$39 |= $$4;
/*     */                   
/* 666 */                   Dynamic<?> $$40 = getBlock($$39);
/* 667 */                   if (ChunkPalettedStorageFix.getName($$40).endsWith("_door")) {
/* 668 */                     Dynamic<?> $$41 = getBlock($$39);
/* 669 */                     if ("lower".equals(ChunkPalettedStorageFix.getProperty($$41, "half"))) {
/* 670 */                       int $$42 = relative($$39, ChunkPalettedStorageFix.Direction.UP);
/* 671 */                       Dynamic<?> $$43 = getBlock($$42);
/* 672 */                       String $$44 = ChunkPalettedStorageFix.getName($$41);
/* 673 */                       if ($$44.equals(ChunkPalettedStorageFix.getName($$43))) {
/* 674 */                         String $$45 = ChunkPalettedStorageFix.getProperty($$41, "facing");
/* 675 */                         String $$46 = ChunkPalettedStorageFix.getProperty($$41, "open");
/* 676 */                         String $$47 = $$1 ? "left" : ChunkPalettedStorageFix.getProperty($$43, "hinge");
/* 677 */                         String $$48 = $$1 ? "false" : ChunkPalettedStorageFix.getProperty($$43, "powered");
/* 678 */                         setBlock($$39, ChunkPalettedStorageFix.DOOR_MAP.get($$44 + $$44 + "lower" + $$45 + $$47 + $$46));
/* 679 */                         setBlock($$42, ChunkPalettedStorageFix.DOOR_MAP.get($$44 + $$44 + "upper" + $$45 + $$47 + $$46));
/*     */                       } 
/*     */                     } 
/*     */                   }  }
/*     */               
/*     */ 
/*     */               
/*     */               case 175:
/* 687 */                 for (intListIterator = ((IntList)$$3.getValue()).iterator(); intListIterator.hasNext(); ) { int $$49 = ((Integer)intListIterator.next()).intValue();
/* 688 */                   $$49 |= $$4;
/*     */                   
/* 690 */                   Dynamic<?> $$50 = getBlock($$49);
/* 691 */                   if ("upper".equals(ChunkPalettedStorageFix.getProperty($$50, "half"))) {
/* 692 */                     Dynamic<?> $$51 = getBlock(relative($$49, ChunkPalettedStorageFix.Direction.DOWN));
/* 693 */                     String $$52 = ChunkPalettedStorageFix.getName($$51);
/* 694 */                     if ("minecraft:sunflower".equals($$52)) {
/* 695 */                       setBlock($$49, ChunkPalettedStorageFix.UPPER_SUNFLOWER); continue;
/* 696 */                     }  if ("minecraft:lilac".equals($$52)) {
/* 697 */                       setBlock($$49, ChunkPalettedStorageFix.UPPER_LILAC); continue;
/* 698 */                     }  if ("minecraft:tall_grass".equals($$52)) {
/* 699 */                       setBlock($$49, ChunkPalettedStorageFix.UPPER_TALL_GRASS); continue;
/* 700 */                     }  if ("minecraft:large_fern".equals($$52)) {
/* 701 */                       setBlock($$49, ChunkPalettedStorageFix.UPPER_LARGE_FERN); continue;
/* 702 */                     }  if ("minecraft:rose_bush".equals($$52)) {
/* 703 */                       setBlock($$49, ChunkPalettedStorageFix.UPPER_ROSE_BUSH); continue;
/* 704 */                     }  if ("minecraft:peony".equals($$52)) {
/* 705 */                       setBlock($$49, ChunkPalettedStorageFix.UPPER_PEONY);
/*     */                     }
/*     */                   }  }
/*     */               
/*     */             }  }
/*     */         
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private Dynamic<?> getBlockEntity(int $$0) {
/* 718 */       return (Dynamic)this.blockEntities.get($$0);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     private Dynamic<?> removeBlockEntity(int $$0) {
/* 723 */       return (Dynamic)this.blockEntities.remove($$0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static int relative(int $$0, ChunkPalettedStorageFix.Direction $$1) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$1.$SwitchMap$net$minecraft$util$datafix$fixes$ChunkPalettedStorageFix$Direction$Axis : [I
/*     */       //   3: aload_1
/*     */       //   4: invokevirtual getAxis : ()Lnet/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$Direction$Axis;
/*     */       //   7: invokevirtual ordinal : ()I
/*     */       //   10: iaload
/*     */       //   11: tableswitch default -> 152, 1 -> 36, 2 -> 70, 3 -> 109
/*     */       //   36: iload_0
/*     */       //   37: bipush #15
/*     */       //   39: iand
/*     */       //   40: aload_1
/*     */       //   41: invokevirtual getAxisDirection : ()Lnet/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$Direction$AxisDirection;
/*     */       //   44: invokevirtual getStep : ()I
/*     */       //   47: iadd
/*     */       //   48: istore_2
/*     */       //   49: iload_2
/*     */       //   50: iflt -> 59
/*     */       //   53: iload_2
/*     */       //   54: bipush #15
/*     */       //   56: if_icmple -> 63
/*     */       //   59: iconst_m1
/*     */       //   60: goto -> 69
/*     */       //   63: iload_0
/*     */       //   64: bipush #-16
/*     */       //   66: iand
/*     */       //   67: iload_2
/*     */       //   68: ior
/*     */       //   69: ireturn
/*     */       //   70: iload_0
/*     */       //   71: bipush #8
/*     */       //   73: ishr
/*     */       //   74: aload_1
/*     */       //   75: invokevirtual getAxisDirection : ()Lnet/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$Direction$AxisDirection;
/*     */       //   78: invokevirtual getStep : ()I
/*     */       //   81: iadd
/*     */       //   82: istore_3
/*     */       //   83: iload_3
/*     */       //   84: iflt -> 94
/*     */       //   87: iload_3
/*     */       //   88: sipush #255
/*     */       //   91: if_icmple -> 98
/*     */       //   94: iconst_m1
/*     */       //   95: goto -> 108
/*     */       //   98: iload_0
/*     */       //   99: sipush #255
/*     */       //   102: iand
/*     */       //   103: iload_3
/*     */       //   104: bipush #8
/*     */       //   106: ishl
/*     */       //   107: ior
/*     */       //   108: ireturn
/*     */       //   109: iload_0
/*     */       //   110: iconst_4
/*     */       //   111: ishr
/*     */       //   112: bipush #15
/*     */       //   114: iand
/*     */       //   115: aload_1
/*     */       //   116: invokevirtual getAxisDirection : ()Lnet/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$Direction$AxisDirection;
/*     */       //   119: invokevirtual getStep : ()I
/*     */       //   122: iadd
/*     */       //   123: istore #4
/*     */       //   125: iload #4
/*     */       //   127: iflt -> 137
/*     */       //   130: iload #4
/*     */       //   132: bipush #15
/*     */       //   134: if_icmple -> 141
/*     */       //   137: iconst_m1
/*     */       //   138: goto -> 151
/*     */       //   141: iload_0
/*     */       //   142: sipush #-241
/*     */       //   145: iand
/*     */       //   146: iload #4
/*     */       //   148: iconst_4
/*     */       //   149: ishl
/*     */       //   150: ior
/*     */       //   151: ireturn
/*     */       //   152: iconst_m1
/*     */       //   153: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #727	-> 0
/*     */       //   #729	-> 36
/*     */       //   #730	-> 49
/*     */       //   #732	-> 70
/*     */       //   #733	-> 83
/*     */       //   #735	-> 109
/*     */       //   #736	-> 125
/*     */       //   #738	-> 152
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	154	0	$$0	I
/*     */       //   0	154	1	$$1	Lnet/minecraft/util/datafix/fixes/ChunkPalettedStorageFix$Direction;
/*     */       //   49	21	2	$$2	I
/*     */       //   83	26	3	$$3	I
/*     */       //   125	27	4	$$4	I
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void setBlock(int $$0, Dynamic<?> $$1) {
/* 742 */       if ($$0 < 0 || $$0 > 65535) {
/*     */         return;
/*     */       }
/*     */       
/* 746 */       ChunkPalettedStorageFix.Section $$2 = getSection($$0);
/*     */       
/* 748 */       if ($$2 == null) {
/*     */         return;
/*     */       }
/*     */       
/* 752 */       $$2.setBlock($$0 & 0xFFF, $$1);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     private ChunkPalettedStorageFix.Section getSection(int $$0) {
/* 757 */       int $$1 = $$0 >> 12;
/* 758 */       return ($$1 < this.sections.length) ? this.sections[$$1] : null;
/*     */     }
/*     */     
/*     */     public Dynamic<?> getBlock(int $$0) {
/* 762 */       if ($$0 < 0 || $$0 > 65535) {
/* 763 */         return ChunkPalettedStorageFix.AIR;
/*     */       }
/*     */       
/* 766 */       ChunkPalettedStorageFix.Section $$1 = getSection($$0);
/*     */       
/* 768 */       if ($$1 == null) {
/* 769 */         return ChunkPalettedStorageFix.AIR;
/*     */       }
/*     */       
/* 772 */       return $$1.getBlock($$0 & 0xFFF);
/*     */     }
/*     */     
/*     */     public Dynamic<?> write() {
/* 776 */       Dynamic<?> $$0 = this.level;
/* 777 */       if (this.blockEntities.isEmpty()) {
/* 778 */         $$0 = $$0.remove("TileEntities");
/*     */       } else {
/* 780 */         $$0 = $$0.set("TileEntities", $$0.createList(this.blockEntities.values().stream()));
/*     */       } 
/*     */       
/* 783 */       Dynamic<?> $$1 = $$0.emptyMap();
/* 784 */       List<Dynamic<?>> $$2 = Lists.newArrayList();
/* 785 */       for (ChunkPalettedStorageFix.Section $$3 : this.sections) {
/* 786 */         if ($$3 != null) {
/* 787 */           $$2.add($$3.write());
/* 788 */           $$1 = $$1.set(String.valueOf($$3.y), $$1.createIntList(Arrays.stream($$3.update.toIntArray())));
/*     */         } 
/*     */       } 
/*     */       
/* 792 */       Dynamic<?> $$4 = $$0.emptyMap();
/* 793 */       $$4 = $$4.set("Sides", $$4.createByte((byte)this.sides));
/* 794 */       $$4 = $$4.set("Indices", $$1);
/* 795 */       return $$0.set("UpgradeData", $$4).set("Sections", $$4.createList($$2.stream()));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DataLayer
/*     */   {
/*     */     private static final int SIZE = 2048;
/*     */     private static final int NIBBLE_SIZE = 4;
/*     */     private final byte[] data;
/*     */     
/*     */     public DataLayer() {
/* 806 */       this.data = new byte[2048];
/*     */     }
/*     */     
/*     */     public DataLayer(byte[] $$0) {
/* 810 */       this.data = $$0;
/*     */       
/* 812 */       if ($$0.length != 2048) {
/* 813 */         throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + $$0.length);
/*     */       }
/*     */     }
/*     */     
/*     */     public int get(int $$0, int $$1, int $$2) {
/* 818 */       int $$3 = getPosition($$1 << 8 | $$2 << 4 | $$0);
/*     */       
/* 820 */       if (isFirst($$1 << 8 | $$2 << 4 | $$0)) {
/* 821 */         return this.data[$$3] & 0xF;
/*     */       }
/* 823 */       return this.data[$$3] >> 4 & 0xF;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isFirst(int $$0) {
/* 828 */       return (($$0 & 0x1) == 0);
/*     */     }
/*     */     
/*     */     private int getPosition(int $$0) {
/* 832 */       return $$0 >> 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getSideMask(boolean $$0, boolean $$1, boolean $$2, boolean $$3) {
/* 837 */     int $$4 = 0;
/* 838 */     if ($$2) {
/* 839 */       if ($$1) {
/* 840 */         $$4 |= 0x2;
/* 841 */       } else if ($$0) {
/* 842 */         $$4 |= 0x80;
/*     */       } else {
/* 844 */         $$4 |= 0x1;
/*     */       } 
/* 846 */     } else if ($$3) {
/* 847 */       if ($$0) {
/* 848 */         $$4 |= 0x20;
/* 849 */       } else if ($$1) {
/* 850 */         $$4 |= 0x8;
/*     */       } else {
/* 852 */         $$4 |= 0x10;
/*     */       } 
/* 854 */     } else if ($$1) {
/* 855 */       $$4 |= 0x4;
/* 856 */     } else if ($$0) {
/* 857 */       $$4 |= 0x40;
/*     */     } 
/* 859 */     return $$4;
/*     */   }
/*     */   
/*     */   public enum Direction {
/* 863 */     DOWN((String)AxisDirection.NEGATIVE, Axis.Y),
/* 864 */     UP((String)AxisDirection.POSITIVE, Axis.Y),
/* 865 */     NORTH((String)AxisDirection.NEGATIVE, Axis.Z),
/* 866 */     SOUTH((String)AxisDirection.POSITIVE, Axis.Z),
/* 867 */     WEST((String)AxisDirection.NEGATIVE, Axis.X),
/* 868 */     EAST((String)AxisDirection.POSITIVE, Axis.X);
/*     */     
/*     */     private final Axis axis;
/*     */     
/*     */     private final AxisDirection axisDirection;
/*     */     
/*     */     Direction(AxisDirection $$0, Axis $$1) {
/* 875 */       this.axis = $$1;
/* 876 */       this.axisDirection = $$0;
/*     */     }
/*     */     
/*     */     public AxisDirection getAxisDirection() {
/* 880 */       return this.axisDirection;
/*     */     }
/*     */     
/*     */     public Axis getAxis() {
/* 884 */       return this.axis;
/*     */     }
/*     */     
/*     */     public enum Axis {
/* 888 */       X,
/* 889 */       Y,
/* 890 */       Z;
/*     */     }
/*     */     
/*     */     public enum AxisDirection {
/* 894 */       POSITIVE(1),
/* 895 */       NEGATIVE(-1);
/*     */       
/*     */       private final int step;
/*     */ 
/*     */       
/*     */       AxisDirection(int $$0) {
/* 901 */         this.step = $$0;
/*     */       }
/*     */       
/*     */       public int getStep() {
/* 905 */         return this.step; } } } public enum Axis { X, Y, Z; } public enum AxisDirection { POSITIVE(1), NEGATIVE(-1); private final int step; AxisDirection(int $$0) { this.step = $$0; } public int getStep() { return this.step; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\ChunkPalettedStorageFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */