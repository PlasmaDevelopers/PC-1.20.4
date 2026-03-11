/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.LinkedHashMultiset;
/*     */ import com.google.common.collect.Multiset;
/*     */ import com.google.common.collect.Multisets;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.MapColor;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ 
/*     */ public class MapItem
/*     */   extends ComplexItem {
/*     */   public static final int IMAGE_WIDTH = 128;
/*     */   public static final int IMAGE_HEIGHT = 128;
/*     */   private static final int DEFAULT_MAP_COLOR = -12173266;
/*     */   private static final String TAG_MAP = "map";
/*     */   public static final String MAP_SCALE_TAG = "map_scale_direction";
/*     */   public static final String MAP_LOCK_TAG = "map_to_lock";
/*     */   
/*     */   public MapItem(Item.Properties $$0) {
/*  49 */     super($$0);
/*     */   }
/*     */   
/*     */   public static ItemStack create(Level $$0, int $$1, int $$2, byte $$3, boolean $$4, boolean $$5) {
/*  53 */     ItemStack $$6 = new ItemStack(Items.FILLED_MAP);
/*  54 */     createAndStoreSavedData($$6, $$0, $$1, $$2, $$3, $$4, $$5, $$0.dimension());
/*  55 */     return $$6;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static MapItemSavedData getSavedData(@Nullable Integer $$0, Level $$1) {
/*  60 */     return ($$0 == null) ? null : $$1.getMapData(makeKey($$0.intValue()));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static MapItemSavedData getSavedData(ItemStack $$0, Level $$1) {
/*  65 */     Integer $$2 = getMapId($$0);
/*  66 */     return getSavedData($$2, $$1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Integer getMapId(ItemStack $$0) {
/*  71 */     CompoundTag $$1 = $$0.getTag();
/*  72 */     return ($$1 != null && $$1.contains("map", 99)) ? Integer.valueOf($$1.getInt("map")) : null;
/*     */   }
/*     */   
/*     */   private static int createNewSavedData(Level $$0, int $$1, int $$2, int $$3, boolean $$4, boolean $$5, ResourceKey<Level> $$6) {
/*  76 */     MapItemSavedData $$7 = MapItemSavedData.createFresh($$1, $$2, (byte)$$3, $$4, $$5, $$6);
/*  77 */     int $$8 = $$0.getFreeMapId();
/*  78 */     $$0.setMapData(makeKey($$8), $$7);
/*  79 */     return $$8;
/*     */   }
/*     */   
/*     */   private static void storeMapData(ItemStack $$0, int $$1) {
/*  83 */     $$0.getOrCreateTag().putInt("map", $$1);
/*     */   }
/*     */   
/*     */   private static void createAndStoreSavedData(ItemStack $$0, Level $$1, int $$2, int $$3, int $$4, boolean $$5, boolean $$6, ResourceKey<Level> $$7) {
/*  87 */     int $$8 = createNewSavedData($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  88 */     storeMapData($$0, $$8);
/*     */   }
/*     */   
/*     */   public static String makeKey(int $$0) {
/*  92 */     return "map_" + $$0;
/*     */   }
/*     */   
/*     */   public void update(Level $$0, Entity $$1, MapItemSavedData $$2) {
/*  96 */     if ($$0.dimension() != $$2.dimension || !($$1 instanceof Player)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 101 */     int $$3 = 1 << $$2.scale;
/* 102 */     int $$4 = $$2.centerX;
/* 103 */     int $$5 = $$2.centerZ;
/*     */ 
/*     */     
/* 106 */     int $$6 = Mth.floor($$1.getX() - $$4) / $$3 + 64;
/* 107 */     int $$7 = Mth.floor($$1.getZ() - $$5) / $$3 + 64;
/* 108 */     int $$8 = 128 / $$3;
/*     */     
/* 110 */     if ($$0.dimensionType().hasCeiling()) {
/* 111 */       $$8 /= 2;
/*     */     }
/*     */     
/* 114 */     MapItemSavedData.HoldingPlayer $$9 = $$2.getHoldingPlayer((Player)$$1);
/* 115 */     $$9.step++;
/* 116 */     BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
/* 117 */     BlockPos.MutableBlockPos $$11 = new BlockPos.MutableBlockPos();
/*     */     
/* 119 */     boolean $$12 = false;
/* 120 */     for (int $$13 = $$6 - $$8 + 1; $$13 < $$6 + $$8; $$13++) {
/* 121 */       if (($$13 & 0xF) == ($$9.step & 0xF) || $$12) {
/*     */ 
/*     */ 
/*     */         
/* 125 */         $$12 = false;
/* 126 */         double $$14 = 0.0D;
/* 127 */         for (int $$15 = $$7 - $$8 - 1; $$15 < $$7 + $$8; $$15++) {
/* 128 */           if ($$13 >= 0 && $$15 >= -1 && $$13 < 128 && $$15 < 128) {
/*     */ 
/*     */ 
/*     */             
/* 132 */             int $$16 = Mth.square($$13 - $$6) + Mth.square($$15 - $$7);
/*     */             
/* 134 */             boolean $$17 = ($$16 > ($$8 - 2) * ($$8 - 2));
/*     */             
/* 136 */             int $$18 = ($$4 / $$3 + $$13 - 64) * $$3;
/* 137 */             int $$19 = ($$5 / $$3 + $$15 - 64) * $$3;
/*     */             
/* 139 */             LinkedHashMultiset linkedHashMultiset = LinkedHashMultiset.create();
/*     */             
/* 141 */             LevelChunk $$21 = $$0.getChunk(SectionPos.blockToSectionCoord($$18), SectionPos.blockToSectionCoord($$19));
/* 142 */             if (!$$21.isEmpty()) {
/*     */               MapColor.Brightness $$40;
/*     */ 
/*     */               
/* 146 */               int $$22 = 0;
/*     */               
/* 148 */               double $$23 = 0.0D;
/* 149 */               if ($$0.dimensionType().hasCeiling()) {
/* 150 */                 int $$24 = $$18 + $$19 * 231871;
/* 151 */                 $$24 = $$24 * $$24 * 31287121 + $$24 * 11;
/*     */                 
/* 153 */                 if (($$24 >> 20 & 0x1) == 0) {
/* 154 */                   linkedHashMultiset.add(Blocks.DIRT.defaultBlockState().getMapColor((BlockGetter)$$0, BlockPos.ZERO), 10);
/*     */                 } else {
/* 156 */                   linkedHashMultiset.add(Blocks.STONE.defaultBlockState().getMapColor((BlockGetter)$$0, BlockPos.ZERO), 100);
/*     */                 } 
/*     */                 
/* 159 */                 $$23 = 100.0D;
/*     */               
/*     */               }
/*     */               else {
/*     */ 
/*     */                 
/* 165 */                 for (int $$25 = 0; $$25 < $$3; $$25++) {
/* 166 */                   for (int $$26 = 0; $$26 < $$3; $$26++) {
/* 167 */                     BlockState $$31; $$10.set($$18 + $$25, 0, $$19 + $$26);
/* 168 */                     int $$27 = $$21.getHeight(Heightmap.Types.WORLD_SURFACE, $$10.getX(), $$10.getZ()) + 1;
/*     */                     
/* 170 */                     if ($$27 > $$0.getMinBuildHeight() + 1) {
/*     */                       BlockState $$28; do {
/* 172 */                         $$27--;
/* 173 */                         $$10.setY($$27);
/* 174 */                         $$28 = $$21.getBlockState((BlockPos)$$10);
/* 175 */                       } while ($$28.getMapColor((BlockGetter)$$0, (BlockPos)$$10) == MapColor.NONE && $$27 > $$0.getMinBuildHeight());
/*     */                       
/* 177 */                       if ($$27 > $$0.getMinBuildHeight() && !$$28.getFluidState().isEmpty()) {
/*     */                         BlockState $$30;
/* 179 */                         int $$29 = $$27 - 1;
/*     */                         
/* 181 */                         $$11.set((Vec3i)$$10);
/*     */                         do {
/* 183 */                           $$11.setY($$29--);
/* 184 */                           $$30 = $$21.getBlockState((BlockPos)$$11);
/* 185 */                           $$22++;
/* 186 */                         } while ($$29 > $$0.getMinBuildHeight() && !$$30.getFluidState().isEmpty());
/*     */                         
/* 188 */                         $$28 = getCorrectStateForFluidBlock($$0, $$28, (BlockPos)$$10);
/*     */                       } 
/*     */                     } else {
/* 191 */                       $$31 = Blocks.BEDROCK.defaultBlockState();
/*     */                     } 
/*     */                     
/* 194 */                     $$2.checkBanners((BlockGetter)$$0, $$10.getX(), $$10.getZ());
/*     */                     
/* 196 */                     $$23 += $$27 / ($$3 * $$3);
/*     */                     
/* 198 */                     linkedHashMultiset.add($$31.getMapColor((BlockGetter)$$0, (BlockPos)$$10));
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 202 */               $$22 /= $$3 * $$3;
/*     */               
/* 204 */               MapColor $$32 = (MapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)linkedHashMultiset), MapColor.NONE);
/*     */               
/* 206 */               if ($$32 == MapColor.WATER) {
/* 207 */                 double $$33 = $$22 * 0.1D + ($$13 + $$15 & 0x1) * 0.2D;
/* 208 */                 if ($$33 < 0.5D) {
/* 209 */                   MapColor.Brightness $$34 = MapColor.Brightness.HIGH;
/* 210 */                 } else if ($$33 > 0.9D) {
/* 211 */                   MapColor.Brightness $$35 = MapColor.Brightness.LOW;
/*     */                 } else {
/* 213 */                   MapColor.Brightness $$36 = MapColor.Brightness.NORMAL;
/*     */                 } 
/*     */               } else {
/* 216 */                 double $$37 = ($$23 - $$14) * 4.0D / ($$3 + 4) + (($$13 + $$15 & 0x1) - 0.5D) * 0.4D;
/* 217 */                 if ($$37 > 0.6D) {
/* 218 */                   MapColor.Brightness $$38 = MapColor.Brightness.HIGH;
/* 219 */                 } else if ($$37 < -0.6D) {
/* 220 */                   MapColor.Brightness $$39 = MapColor.Brightness.LOW;
/*     */                 } else {
/* 222 */                   $$40 = MapColor.Brightness.NORMAL;
/*     */                 } 
/*     */               } 
/*     */               
/* 226 */               $$14 = $$23;
/*     */               
/* 228 */               if ($$15 >= 0)
/*     */               {
/*     */                 
/* 231 */                 if ($$16 < $$8 * $$8)
/*     */                 {
/*     */                   
/* 234 */                   if (!$$17 || ($$13 + $$15 & 0x1) != 0)
/*     */                   {
/*     */ 
/*     */                     
/* 238 */                     $$12 |= $$2.updateColor($$13, $$15, $$32.getPackedId($$40)); }  }  } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } private BlockState getCorrectStateForFluidBlock(Level $$0, BlockState $$1, BlockPos $$2) {
/* 244 */     FluidState $$3 = $$1.getFluidState();
/* 245 */     if (!$$3.isEmpty() && !$$1.isFaceSturdy((BlockGetter)$$0, $$2, Direction.UP)) {
/* 246 */       return $$3.createLegacyBlock();
/*     */     }
/*     */     
/* 249 */     return $$1;
/*     */   }
/*     */   
/*     */   private static boolean isBiomeWatery(boolean[] $$0, int $$1, int $$2) {
/* 253 */     return $$0[$$2 * 128 + $$1];
/*     */   }
/*     */   
/*     */   public static void renderBiomePreviewMap(ServerLevel $$0, ItemStack $$1) {
/* 257 */     MapItemSavedData $$2 = getSavedData($$1, (Level)$$0);
/* 258 */     if ($$2 == null) {
/*     */       return;
/*     */     }
/* 261 */     if ($$0.dimension() != $$2.dimension) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 266 */     int $$3 = 1 << $$2.scale;
/* 267 */     int $$4 = $$2.centerX;
/* 268 */     int $$5 = $$2.centerZ;
/*     */     
/* 270 */     boolean[] $$6 = new boolean[16384];
/*     */     
/* 272 */     int $$7 = $$4 / $$3 - 64;
/* 273 */     int $$8 = $$5 / $$3 - 64;
/* 274 */     BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
/* 275 */     for (int $$10 = 0; $$10 < 128; $$10++) {
/* 276 */       for (int $$11 = 0; $$11 < 128; $$11++) {
/* 277 */         Holder<Biome> $$12 = $$0.getBiome((BlockPos)$$9.set(($$7 + $$11) * $$3, 0, ($$8 + $$10) * $$3));
/* 278 */         $$6[$$10 * 128 + $$11] = $$12.is(BiomeTags.WATER_ON_MAP_OUTLINES);
/*     */       } 
/*     */     } 
/* 281 */     for (int $$13 = 1; $$13 < 127; $$13++) {
/* 282 */       for (int $$14 = 1; $$14 < 127; $$14++) {
/* 283 */         int $$15 = 0;
/* 284 */         for (int $$16 = -1; $$16 < 2; $$16++) {
/* 285 */           for (int $$17 = -1; $$17 < 2; $$17++) {
/* 286 */             if (($$16 != 0 || $$17 != 0) && isBiomeWatery($$6, $$13 + $$16, $$14 + $$17)) {
/* 287 */               $$15++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 292 */         MapColor.Brightness $$18 = MapColor.Brightness.LOWEST;
/* 293 */         MapColor $$19 = MapColor.NONE;
/*     */         
/* 295 */         if (isBiomeWatery($$6, $$13, $$14)) {
/* 296 */           $$19 = MapColor.COLOR_ORANGE;
/* 297 */           if ($$15 > 7 && $$14 % 2 == 0) {
/* 298 */             switch (($$13 + (int)(Mth.sin($$14 + 0.0F) * 7.0F)) / 8 % 5) { case 0: case 4:
/* 299 */                 $$18 = MapColor.Brightness.LOW; break;
/* 300 */               case 1: case 3: $$18 = MapColor.Brightness.NORMAL; break;
/* 301 */               case 2: $$18 = MapColor.Brightness.HIGH; break; }
/*     */           
/* 303 */           } else if ($$15 > 7) {
/* 304 */             $$19 = MapColor.NONE;
/* 305 */           } else if ($$15 > 5) {
/* 306 */             $$18 = MapColor.Brightness.NORMAL;
/* 307 */           } else if ($$15 > 3) {
/* 308 */             $$18 = MapColor.Brightness.LOW;
/* 309 */           } else if ($$15 > 1) {
/* 310 */             $$18 = MapColor.Brightness.LOW;
/*     */           } 
/* 312 */         } else if ($$15 > 0) {
/* 313 */           $$19 = MapColor.COLOR_BROWN;
/* 314 */           if ($$15 > 3) {
/* 315 */             $$18 = MapColor.Brightness.NORMAL;
/*     */           } else {
/* 317 */             $$18 = MapColor.Brightness.LOWEST;
/*     */           } 
/*     */         } 
/*     */         
/* 321 */         if ($$19 != MapColor.NONE) {
/* 322 */           $$2.setColor($$13, $$14, $$19.getPackedId($$18));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void inventoryTick(ItemStack $$0, Level $$1, Entity $$2, int $$3, boolean $$4) {
/* 330 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 334 */     MapItemSavedData $$5 = getSavedData($$0, $$1);
/* 335 */     if ($$5 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 339 */     if ($$2 instanceof Player) { Player $$6 = (Player)$$2;
/* 340 */       $$5.tickCarriedBy($$6, $$0); }
/*     */ 
/*     */     
/* 343 */     if (!$$5.locked && ($$4 || ($$2 instanceof Player && ((Player)$$2).getOffhandItem() == $$0))) {
/* 344 */       update($$1, $$2, $$5);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Packet<?> getUpdatePacket(ItemStack $$0, Level $$1, Player $$2) {
/* 351 */     Integer $$3 = getMapId($$0);
/* 352 */     MapItemSavedData $$4 = getSavedData($$3, $$1);
/* 353 */     if ($$4 != null) {
/* 354 */       return $$4.getUpdatePacket($$3.intValue(), $$2);
/*     */     }
/* 356 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCraftedPostProcess(ItemStack $$0, Level $$1) {
/* 361 */     CompoundTag $$2 = $$0.getTag();
/* 362 */     if ($$2 != null && $$2.contains("map_scale_direction", 99)) {
/* 363 */       scaleMap($$0, $$1, $$2.getInt("map_scale_direction"));
/* 364 */       $$2.remove("map_scale_direction");
/* 365 */     } else if ($$2 != null && $$2.contains("map_to_lock", 1) && $$2.getBoolean("map_to_lock")) {
/* 366 */       lockMap($$1, $$0);
/* 367 */       $$2.remove("map_to_lock");
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void scaleMap(ItemStack $$0, Level $$1, int $$2) {
/* 372 */     MapItemSavedData $$3 = getSavedData($$0, $$1);
/*     */     
/* 374 */     if ($$3 != null) {
/* 375 */       int $$4 = $$1.getFreeMapId();
/* 376 */       $$1.setMapData(makeKey($$4), $$3.scaled($$2));
/* 377 */       storeMapData($$0, $$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void lockMap(Level $$0, ItemStack $$1) {
/* 382 */     MapItemSavedData $$2 = getSavedData($$1, $$0);
/* 383 */     if ($$2 != null) {
/* 384 */       int $$3 = $$0.getFreeMapId();
/* 385 */       String $$4 = makeKey($$3);
/* 386 */       MapItemSavedData $$5 = $$2.locked();
/* 387 */       $$0.setMapData($$4, $$5);
/* 388 */       storeMapData($$1, $$3);
/*     */     } 
/*     */   }
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/*     */     boolean $$9;
/*     */     byte $$10;
/* 394 */     Integer $$4 = getMapId($$0);
/* 395 */     MapItemSavedData $$5 = ($$1 == null) ? null : getSavedData($$4, $$1);
/*     */     
/* 397 */     CompoundTag $$6 = $$0.getTag();
/*     */ 
/*     */     
/* 400 */     if ($$6 != null) {
/* 401 */       boolean $$7 = $$6.getBoolean("map_to_lock");
/* 402 */       byte $$8 = $$6.getByte("map_scale_direction");
/*     */     } else {
/* 404 */       $$9 = false;
/* 405 */       $$10 = 0;
/*     */     } 
/*     */     
/* 408 */     if ($$5 != null && ($$5.locked || $$9)) {
/* 409 */       $$2.add(Component.translatable("filled_map.locked", new Object[] { $$4 }).withStyle(ChatFormatting.GRAY));
/*     */     }
/*     */     
/* 412 */     if ($$3.isAdvanced()) {
/* 413 */       if ($$5 != null) {
/* 414 */         if (!$$9 && $$10 == 0)
/*     */         {
/* 416 */           $$2.add(getTooltipForId($$4.intValue()));
/*     */         }
/* 418 */         int $$11 = Math.min($$5.scale + $$10, 4);
/* 419 */         $$2.add(Component.translatable("filled_map.scale", new Object[] { Integer.valueOf(1 << $$11) }).withStyle(ChatFormatting.GRAY));
/* 420 */         $$2.add(Component.translatable("filled_map.level", new Object[] { Integer.valueOf($$11), Integer.valueOf(4) }).withStyle(ChatFormatting.GRAY));
/*     */       } else {
/* 422 */         $$2.add(Component.translatable("filled_map.unknown").withStyle(ChatFormatting.GRAY));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static Component getTooltipForId(int $$0) {
/* 428 */     return (Component)Component.translatable("filled_map.id", new Object[] { Integer.valueOf($$0) }).withStyle(ChatFormatting.GRAY);
/*     */   }
/*     */   
/*     */   public static Component getTooltipForId(ItemStack $$0) {
/* 432 */     return getTooltipForId(getMapId($$0).intValue());
/*     */   }
/*     */   
/*     */   public static int getColor(ItemStack $$0) {
/* 436 */     CompoundTag $$1 = $$0.getTagElement("display");
/* 437 */     if ($$1 != null && $$1.contains("MapColor", 99)) {
/* 438 */       int $$2 = $$1.getInt("MapColor");
/* 439 */       return 0xFF000000 | $$2 & 0xFFFFFF;
/*     */     } 
/* 441 */     return -12173266;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/* 446 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos());
/* 447 */     if ($$1.is(BlockTags.BANNERS)) {
/* 448 */       if (!($$0.getLevel()).isClientSide) {
/* 449 */         MapItemSavedData $$2 = getSavedData($$0.getItemInHand(), $$0.getLevel());
/* 450 */         if ($$2 != null && 
/* 451 */           !$$2.toggleBanner((LevelAccessor)$$0.getLevel(), $$0.getClickedPos())) {
/* 452 */           return InteractionResult.FAIL;
/*     */         }
/*     */       } 
/*     */       
/* 456 */       return InteractionResult.sidedSuccess(($$0.getLevel()).isClientSide);
/*     */     } 
/* 458 */     return super.useOn($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\MapItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */