/*     */ package net.minecraft.world.level.saveddata.maps;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.entity.decoration.ItemFrame;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.level.saveddata.SavedData;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class MapItemSavedData extends SavedData {
/*  37 */   private static final Logger LOGGER = LogUtils.getLogger(); private static final int MAP_SIZE = 128;
/*     */   private static final int HALF_MAP_SIZE = 64;
/*     */   public static final int MAX_SCALE = 4;
/*     */   public static final int TRACKED_DECORATION_LIMIT = 256;
/*     */   public final int centerX;
/*     */   public final int centerZ;
/*     */   public final ResourceKey<Level> dimension;
/*     */   private final boolean trackingPosition;
/*     */   private final boolean unlimitedTracking;
/*     */   public final byte scale;
/*     */   
/*     */   public static class MapPatch { public final int startX;
/*     */     public final int startY;
/*     */     
/*     */     public MapPatch(int $$0, int $$1, int $$2, int $$3, byte[] $$4) {
/*  52 */       this.startX = $$0;
/*  53 */       this.startY = $$1;
/*  54 */       this.width = $$2;
/*  55 */       this.height = $$3;
/*  56 */       this.mapColors = $$4;
/*     */     }
/*     */     public final int width; public final int height; public final byte[] mapColors;
/*     */     public void applyToMap(MapItemSavedData $$0) {
/*  60 */       for (int $$1 = 0; $$1 < this.width; $$1++) {
/*  61 */         for (int $$2 = 0; $$2 < this.height; $$2++)
/*  62 */           $$0.setColor(this.startX + $$1, this.startY + $$2, this.mapColors[$$1 + $$2 * this.width]); 
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   public class HoldingPlayer
/*     */   {
/*     */     public final Player player;
/*     */     private boolean dirtyData = true;
/*     */     private int minDirtyX;
/*     */     private int minDirtyY;
/*  73 */     private int maxDirtyX = 127;
/*  74 */     private int maxDirtyY = 127;
/*     */     private boolean dirtyDecorations = true;
/*     */     private int tick;
/*     */     public int step;
/*     */     
/*     */     HoldingPlayer(Player $$1) {
/*  80 */       this.player = $$1;
/*     */     }
/*     */     
/*     */     private MapItemSavedData.MapPatch createPatch() {
/*  84 */       int $$0 = this.minDirtyX;
/*  85 */       int $$1 = this.minDirtyY;
/*  86 */       int $$2 = this.maxDirtyX + 1 - this.minDirtyX;
/*  87 */       int $$3 = this.maxDirtyY + 1 - this.minDirtyY;
/*     */       
/*  89 */       byte[] $$4 = new byte[$$2 * $$3];
/*  90 */       for (int $$5 = 0; $$5 < $$2; $$5++) {
/*  91 */         for (int $$6 = 0; $$6 < $$3; $$6++) {
/*  92 */           $$4[$$5 + $$6 * $$2] = MapItemSavedData.this.colors[$$0 + $$5 + ($$1 + $$6) * 128];
/*     */         }
/*     */       } 
/*  95 */       return new MapItemSavedData.MapPatch($$0, $$1, $$2, $$3, $$4);
/*     */     }
/*     */     @Nullable
/*     */     Packet<?> nextUpdatePacket(int $$0) {
/*     */       MapItemSavedData.MapPatch $$2;
/*     */       Collection<MapDecoration> $$4;
/* 101 */       if (this.dirtyData) {
/* 102 */         this.dirtyData = false;
/* 103 */         MapItemSavedData.MapPatch $$1 = createPatch();
/*     */       } else {
/* 105 */         $$2 = null;
/*     */       } 
/*     */ 
/*     */       
/* 109 */       if (this.dirtyDecorations && this.tick++ % 5 == 0) {
/* 110 */         this.dirtyDecorations = false;
/* 111 */         Collection<MapDecoration> $$3 = MapItemSavedData.this.decorations.values();
/*     */       } else {
/* 113 */         $$4 = null;
/*     */       } 
/*     */       
/* 116 */       if ($$4 != null || $$2 != null) {
/* 117 */         return (Packet<?>)new ClientboundMapItemDataPacket($$0, MapItemSavedData.this.scale, MapItemSavedData.this.locked, $$4, $$2);
/*     */       }
/*     */       
/* 120 */       return null;
/*     */     }
/*     */     
/*     */     void markColorsDirty(int $$0, int $$1) {
/* 124 */       if (this.dirtyData) {
/* 125 */         this.minDirtyX = Math.min(this.minDirtyX, $$0);
/* 126 */         this.minDirtyY = Math.min(this.minDirtyY, $$1);
/* 127 */         this.maxDirtyX = Math.max(this.maxDirtyX, $$0);
/* 128 */         this.maxDirtyY = Math.max(this.maxDirtyY, $$1);
/*     */       } else {
/* 130 */         this.dirtyData = true;
/* 131 */         this.minDirtyX = $$0;
/* 132 */         this.minDirtyY = $$1;
/* 133 */         this.maxDirtyX = $$0;
/* 134 */         this.maxDirtyY = $$1;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void markDecorationsDirty() {
/* 139 */       this.dirtyDecorations = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   public byte[] colors = new byte[16384];
/*     */   public final boolean locked;
/* 151 */   private final List<HoldingPlayer> carriedBy = Lists.newArrayList();
/* 152 */   private final Map<Player, HoldingPlayer> carriedByPlayers = Maps.newHashMap();
/* 153 */   private final Map<String, MapBanner> bannerMarkers = Maps.newHashMap();
/* 154 */   final Map<String, MapDecoration> decorations = Maps.newLinkedHashMap();
/* 155 */   private final Map<String, MapFrame> frameMarkers = Maps.newHashMap();
/*     */   private int trackedDecorationCount;
/*     */   
/*     */   public static SavedData.Factory<MapItemSavedData> factory() {
/* 159 */     return new SavedData.Factory(() -> { throw new IllegalStateException("Should never create an empty map saved data"); }MapItemSavedData::load, DataFixTypes.SAVED_DATA_MAP_DATA);
/*     */   }
/*     */   
/*     */   private MapItemSavedData(int $$0, int $$1, byte $$2, boolean $$3, boolean $$4, boolean $$5, ResourceKey<Level> $$6) {
/* 163 */     this.scale = $$2;
/* 164 */     this.centerX = $$0;
/* 165 */     this.centerZ = $$1;
/* 166 */     this.dimension = $$6;
/* 167 */     this.trackingPosition = $$3;
/* 168 */     this.unlimitedTracking = $$4;
/* 169 */     this.locked = $$5;
/* 170 */     setDirty();
/*     */   }
/*     */   
/*     */   public static MapItemSavedData createFresh(double $$0, double $$1, byte $$2, boolean $$3, boolean $$4, ResourceKey<Level> $$5) {
/* 174 */     int $$6 = 128 * (1 << $$2);
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
/* 185 */     int $$7 = Mth.floor(($$0 + 64.0D) / $$6);
/* 186 */     int $$8 = Mth.floor(($$1 + 64.0D) / $$6);
/*     */     
/* 188 */     int $$9 = $$7 * $$6 + $$6 / 2 - 64;
/* 189 */     int $$10 = $$8 * $$6 + $$6 / 2 - 64;
/*     */     
/* 191 */     return new MapItemSavedData($$9, $$10, $$2, $$3, $$4, false, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MapItemSavedData createForClient(byte $$0, boolean $$1, ResourceKey<Level> $$2) {
/* 196 */     return new MapItemSavedData(0, 0, $$0, false, false, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MapItemSavedData load(CompoundTag $$0) {
/* 201 */     Objects.requireNonNull(LOGGER);
/* 202 */     ResourceKey<Level> $$1 = (ResourceKey<Level>)DimensionType.parseLegacy(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.get("dimension"))).resultOrPartial(LOGGER::error).orElseThrow(() -> new IllegalArgumentException("Invalid map dimension: " + $$0.get("dimension")));
/*     */     
/* 204 */     int $$2 = $$0.getInt("xCenter");
/* 205 */     int $$3 = $$0.getInt("zCenter");
/* 206 */     byte $$4 = (byte)Mth.clamp($$0.getByte("scale"), 0, 4);
/*     */     
/* 208 */     boolean $$5 = (!$$0.contains("trackingPosition", 1) || $$0.getBoolean("trackingPosition"));
/* 209 */     boolean $$6 = $$0.getBoolean("unlimitedTracking");
/*     */     
/* 211 */     boolean $$7 = $$0.getBoolean("locked");
/*     */     
/* 213 */     MapItemSavedData $$8 = new MapItemSavedData($$2, $$3, $$4, $$5, $$6, $$7, $$1);
/*     */     
/* 215 */     byte[] $$9 = $$0.getByteArray("colors");
/* 216 */     if ($$9.length == 16384) {
/* 217 */       $$8.colors = $$9;
/*     */     }
/*     */     
/* 220 */     ListTag $$10 = $$0.getList("banners", 10);
/* 221 */     for (int $$11 = 0; $$11 < $$10.size(); $$11++) {
/* 222 */       MapBanner $$12 = MapBanner.load($$10.getCompound($$11));
/* 223 */       $$8.bannerMarkers.put($$12.getId(), $$12);
/* 224 */       $$8.addDecoration($$12.getDecoration(), null, $$12.getId(), $$12.getPos().getX(), $$12.getPos().getZ(), 180.0D, $$12.getName());
/*     */     } 
/*     */     
/* 227 */     ListTag $$13 = $$0.getList("frames", 10);
/* 228 */     for (int $$14 = 0; $$14 < $$13.size(); $$14++) {
/* 229 */       MapFrame $$15 = MapFrame.load($$13.getCompound($$14));
/* 230 */       $$8.frameMarkers.put($$15.getId(), $$15);
/* 231 */       $$8.addDecoration(MapDecoration.Type.FRAME, null, "frame-" + $$15.getEntityId(), $$15.getPos().getX(), $$15.getPos().getZ(), $$15.getRotation(), null);
/*     */     } 
/*     */     
/* 234 */     return $$8;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/* 239 */     Objects.requireNonNull(LOGGER); ResourceLocation.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.dimension.location()).resultOrPartial(LOGGER::error).ifPresent($$1 -> $$0.put("dimension", $$1));
/* 240 */     $$0.putInt("xCenter", this.centerX);
/* 241 */     $$0.putInt("zCenter", this.centerZ);
/* 242 */     $$0.putByte("scale", this.scale);
/* 243 */     $$0.putByteArray("colors", this.colors);
/* 244 */     $$0.putBoolean("trackingPosition", this.trackingPosition);
/* 245 */     $$0.putBoolean("unlimitedTracking", this.unlimitedTracking);
/* 246 */     $$0.putBoolean("locked", this.locked);
/*     */     
/* 248 */     ListTag $$1 = new ListTag();
/* 249 */     for (MapBanner $$2 : this.bannerMarkers.values()) {
/* 250 */       $$1.add($$2.save());
/*     */     }
/* 252 */     $$0.put("banners", (Tag)$$1);
/*     */     
/* 254 */     ListTag $$3 = new ListTag();
/* 255 */     for (MapFrame $$4 : this.frameMarkers.values()) {
/* 256 */       $$3.add($$4.save());
/*     */     }
/* 258 */     $$0.put("frames", (Tag)$$3);
/*     */     
/* 260 */     return $$0;
/*     */   }
/*     */   
/*     */   public MapItemSavedData locked() {
/* 264 */     MapItemSavedData $$0 = new MapItemSavedData(this.centerX, this.centerZ, this.scale, this.trackingPosition, this.unlimitedTracking, true, this.dimension);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 274 */     $$0.bannerMarkers.putAll(this.bannerMarkers);
/* 275 */     $$0.decorations.putAll(this.decorations);
/* 276 */     $$0.trackedDecorationCount = this.trackedDecorationCount;
/* 277 */     System.arraycopy(this.colors, 0, $$0.colors, 0, this.colors.length);
/* 278 */     $$0.setDirty();
/*     */     
/* 280 */     return $$0;
/*     */   }
/*     */   
/*     */   public MapItemSavedData scaled(int $$0) {
/* 284 */     return createFresh(this.centerX, this.centerZ, (byte)Mth.clamp(this.scale + $$0, 0, 4), this.trackingPosition, this.unlimitedTracking, this.dimension);
/*     */   }
/*     */   
/*     */   public void tickCarriedBy(Player $$0, ItemStack $$1) {
/* 288 */     if (!this.carriedByPlayers.containsKey($$0)) {
/* 289 */       HoldingPlayer $$2 = new HoldingPlayer($$0);
/* 290 */       this.carriedByPlayers.put($$0, $$2);
/* 291 */       this.carriedBy.add($$2);
/*     */     } 
/*     */     
/* 294 */     if (!$$0.getInventory().contains($$1)) {
/* 295 */       removeDecoration($$0.getName().getString());
/*     */     }
/*     */     
/* 298 */     for (int $$3 = 0; $$3 < this.carriedBy.size(); $$3++) {
/* 299 */       HoldingPlayer $$4 = this.carriedBy.get($$3);
/* 300 */       String $$5 = $$4.player.getName().getString();
/*     */       
/* 302 */       if ($$4.player.isRemoved() || (!$$4.player.getInventory().contains($$1) && !$$1.isFramed())) {
/* 303 */         this.carriedByPlayers.remove($$4.player);
/* 304 */         this.carriedBy.remove($$4);
/* 305 */         removeDecoration($$5);
/* 306 */       } else if (!$$1.isFramed() && $$4.player.level().dimension() == this.dimension && this.trackingPosition) {
/* 307 */         addDecoration(MapDecoration.Type.PLAYER, (LevelAccessor)$$4.player.level(), $$5, $$4.player.getX(), $$4.player.getZ(), $$4.player.getYRot(), null);
/*     */       } 
/*     */     } 
/*     */     
/* 311 */     if ($$1.isFramed() && this.trackingPosition) {
/* 312 */       ItemFrame $$6 = $$1.getFrame();
/* 313 */       BlockPos $$7 = $$6.getPos();
/* 314 */       MapFrame $$8 = this.frameMarkers.get(MapFrame.frameId($$7));
/*     */ 
/*     */       
/* 317 */       if ($$8 != null && $$6.getId() != $$8.getEntityId() && this.frameMarkers.containsKey($$8.getId())) {
/* 318 */         removeDecoration("frame-" + $$8.getEntityId());
/*     */       }
/* 320 */       MapFrame $$9 = new MapFrame($$7, $$6.getDirection().get2DDataValue() * 90, $$6.getId());
/* 321 */       addDecoration(MapDecoration.Type.FRAME, (LevelAccessor)$$0.level(), "frame-" + $$6.getId(), $$7.getX(), $$7.getZ(), ($$6.getDirection().get2DDataValue() * 90), null);
/* 322 */       this.frameMarkers.put($$9.getId(), $$9);
/*     */     } 
/*     */     
/* 325 */     CompoundTag $$10 = $$1.getTag();
/* 326 */     if ($$10 != null && $$10.contains("Decorations", 9)) {
/* 327 */       ListTag $$11 = $$10.getList("Decorations", 10);
/* 328 */       for (int $$12 = 0; $$12 < $$11.size(); $$12++) {
/* 329 */         CompoundTag $$13 = $$11.getCompound($$12);
/* 330 */         if (!this.decorations.containsKey($$13.getString("id"))) {
/* 331 */           addDecoration(MapDecoration.Type.byIcon($$13.getByte("type")), (LevelAccessor)$$0.level(), $$13.getString("id"), $$13.getDouble("x"), $$13.getDouble("z"), $$13.getDouble("rot"), null);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeDecoration(String $$0) {
/* 338 */     MapDecoration $$1 = this.decorations.remove($$0);
/* 339 */     if ($$1 != null && $$1.type().shouldTrackCount()) {
/* 340 */       this.trackedDecorationCount--;
/*     */     }
/* 342 */     setDecorationsDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addTargetDecoration(ItemStack $$0, BlockPos $$1, String $$2, MapDecoration.Type $$3) {
/*     */     ListTag $$5;
/* 348 */     if ($$0.hasTag() && $$0.getTag().contains("Decorations", 9)) {
/* 349 */       ListTag $$4 = $$0.getTag().getList("Decorations", 10);
/*     */     } else {
/* 351 */       $$5 = new ListTag();
/* 352 */       $$0.addTagElement("Decorations", (Tag)$$5);
/*     */     } 
/* 354 */     CompoundTag $$6 = new CompoundTag();
/* 355 */     $$6.putByte("type", $$3.getIcon());
/* 356 */     $$6.putString("id", $$2);
/* 357 */     $$6.putDouble("x", $$1.getX());
/* 358 */     $$6.putDouble("z", $$1.getZ());
/* 359 */     $$6.putDouble("rot", 180.0D);
/* 360 */     $$5.add($$6);
/*     */ 
/*     */     
/* 363 */     if ($$3.hasMapColor()) {
/* 364 */       CompoundTag $$7 = $$0.getOrCreateTagElement("display");
/* 365 */       $$7.putInt("MapColor", $$3.getMapColor());
/*     */     } 
/*     */   }
/*     */   private void addDecoration(MapDecoration.Type $$0, @Nullable LevelAccessor $$1, String $$2, double $$3, double $$4, double $$5, @Nullable Component $$6) {
/*     */     byte $$16;
/* 370 */     int $$7 = 1 << this.scale;
/* 371 */     float $$8 = (float)($$3 - this.centerX) / $$7;
/* 372 */     float $$9 = (float)($$4 - this.centerZ) / $$7;
/* 373 */     byte $$10 = (byte)(int)(($$8 * 2.0F) + 0.5D);
/* 374 */     byte $$11 = (byte)(int)(($$9 * 2.0F) + 0.5D);
/*     */     
/* 376 */     int $$12 = 63;
/*     */     
/* 378 */     if ($$8 >= -63.0F && $$9 >= -63.0F && $$8 <= 63.0F && $$9 <= 63.0F) {
/* 379 */       $$5 += ($$5 < 0.0D) ? -8.0D : 8.0D;
/* 380 */       byte $$13 = (byte)(int)($$5 * 16.0D / 360.0D);
/*     */       
/* 382 */       if (this.dimension == Level.NETHER && $$1 != null) {
/* 383 */         int $$14 = (int)($$1.getLevelData().getDayTime() / 10L);
/* 384 */         $$13 = (byte)($$14 * $$14 * 34187121 + $$14 * 121 >> 15 & 0xF);
/*     */       } 
/* 386 */     } else if ($$0 == MapDecoration.Type.PLAYER) {
/* 387 */       int $$15 = 320;
/* 388 */       if (Math.abs($$8) < 320.0F && Math.abs($$9) < 320.0F) {
/* 389 */         $$0 = MapDecoration.Type.PLAYER_OFF_MAP;
/* 390 */       } else if (this.unlimitedTracking) {
/* 391 */         $$0 = MapDecoration.Type.PLAYER_OFF_LIMITS;
/*     */       } else {
/* 393 */         removeDecoration($$2);
/*     */         return;
/*     */       } 
/* 396 */       $$16 = 0;
/* 397 */       if ($$8 <= -63.0F) {
/* 398 */         $$10 = Byte.MIN_VALUE;
/*     */       }
/* 400 */       if ($$9 <= -63.0F) {
/* 401 */         $$11 = Byte.MIN_VALUE;
/*     */       }
/* 403 */       if ($$8 >= 63.0F) {
/* 404 */         $$10 = Byte.MAX_VALUE;
/*     */       }
/* 406 */       if ($$9 >= 63.0F) {
/* 407 */         $$11 = Byte.MAX_VALUE;
/*     */       }
/*     */     } else {
/* 410 */       removeDecoration($$2);
/*     */       
/*     */       return;
/*     */     } 
/* 414 */     MapDecoration $$18 = new MapDecoration($$0, $$10, $$11, $$16, $$6);
/* 415 */     MapDecoration $$19 = this.decorations.put($$2, $$18);
/* 416 */     if (!$$18.equals($$19)) {
/* 417 */       if ($$19 != null && $$19.type().shouldTrackCount()) {
/* 418 */         this.trackedDecorationCount--;
/*     */       }
/* 420 */       if ($$0.shouldTrackCount()) {
/* 421 */         this.trackedDecorationCount++;
/*     */       }
/* 423 */       setDecorationsDirty();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Packet<?> getUpdatePacket(int $$0, Player $$1) {
/* 429 */     HoldingPlayer $$2 = this.carriedByPlayers.get($$1);
/*     */     
/* 431 */     if ($$2 == null) {
/* 432 */       return null;
/*     */     }
/*     */     
/* 435 */     return $$2.nextUpdatePacket($$0);
/*     */   }
/*     */   
/*     */   private void setColorsDirty(int $$0, int $$1) {
/* 439 */     setDirty();
/* 440 */     for (HoldingPlayer $$2 : this.carriedBy) {
/* 441 */       $$2.markColorsDirty($$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setDecorationsDirty() {
/* 446 */     setDirty();
/* 447 */     this.carriedBy.forEach(HoldingPlayer::markDecorationsDirty);
/*     */   }
/*     */   
/*     */   public HoldingPlayer getHoldingPlayer(Player $$0) {
/* 451 */     HoldingPlayer $$1 = this.carriedByPlayers.get($$0);
/*     */     
/* 453 */     if ($$1 == null) {
/* 454 */       $$1 = new HoldingPlayer($$0);
/* 455 */       this.carriedByPlayers.put($$0, $$1);
/* 456 */       this.carriedBy.add($$1);
/*     */     } 
/*     */     
/* 459 */     return $$1;
/*     */   }
/*     */   
/*     */   public boolean toggleBanner(LevelAccessor $$0, BlockPos $$1) {
/* 463 */     double $$2 = $$1.getX() + 0.5D;
/* 464 */     double $$3 = $$1.getZ() + 0.5D;
/* 465 */     int $$4 = 1 << this.scale;
/* 466 */     double $$5 = ($$2 - this.centerX) / $$4;
/* 467 */     double $$6 = ($$3 - this.centerZ) / $$4;
/* 468 */     int $$7 = 63;
/* 469 */     if ($$5 >= -63.0D && $$6 >= -63.0D && $$5 <= 63.0D && $$6 <= 63.0D) {
/* 470 */       MapBanner $$8 = MapBanner.fromWorld((BlockGetter)$$0, $$1);
/* 471 */       if ($$8 == null) {
/* 472 */         return false;
/*     */       }
/*     */       
/* 475 */       if (this.bannerMarkers.remove($$8.getId(), $$8)) {
/* 476 */         removeDecoration($$8.getId());
/* 477 */         return true;
/* 478 */       }  if (!isTrackedCountOverLimit(256)) {
/* 479 */         this.bannerMarkers.put($$8.getId(), $$8);
/* 480 */         addDecoration($$8.getDecoration(), $$0, $$8.getId(), $$2, $$3, 180.0D, $$8.getName());
/* 481 */         return true;
/*     */       } 
/*     */     } 
/* 484 */     return false;
/*     */   }
/*     */   
/*     */   public void checkBanners(BlockGetter $$0, int $$1, int $$2) {
/* 488 */     for (Iterator<MapBanner> $$3 = this.bannerMarkers.values().iterator(); $$3.hasNext(); ) {
/* 489 */       MapBanner $$4 = $$3.next();
/* 490 */       if ($$4.getPos().getX() == $$1 && $$4.getPos().getZ() == $$2) {
/* 491 */         MapBanner $$5 = MapBanner.fromWorld($$0, $$4.getPos());
/* 492 */         if (!$$4.equals($$5)) {
/* 493 */           $$3.remove();
/* 494 */           removeDecoration($$4.getId());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<MapBanner> getBanners() {
/* 501 */     return this.bannerMarkers.values();
/*     */   }
/*     */   
/*     */   public void removedFromFrame(BlockPos $$0, int $$1) {
/* 505 */     removeDecoration("frame-" + $$1);
/* 506 */     this.frameMarkers.remove(MapFrame.frameId($$0));
/*     */   }
/*     */   
/*     */   public boolean updateColor(int $$0, int $$1, byte $$2) {
/* 510 */     byte $$3 = this.colors[$$0 + $$1 * 128];
/* 511 */     if ($$3 != $$2) {
/* 512 */       setColor($$0, $$1, $$2);
/* 513 */       return true;
/*     */     } 
/* 515 */     return false;
/*     */   }
/*     */   
/*     */   public void setColor(int $$0, int $$1, byte $$2) {
/* 519 */     this.colors[$$0 + $$1 * 128] = $$2;
/* 520 */     setColorsDirty($$0, $$1);
/*     */   }
/*     */   
/*     */   public boolean isExplorationMap() {
/* 524 */     for (MapDecoration $$0 : this.decorations.values()) {
/* 525 */       if ($$0.type().isExplorationMapElement()) {
/* 526 */         return true;
/*     */       }
/*     */     } 
/* 529 */     return false;
/*     */   }
/*     */   
/*     */   public void addClientSideDecorations(List<MapDecoration> $$0) {
/* 533 */     this.decorations.clear();
/* 534 */     this.trackedDecorationCount = 0;
/* 535 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 536 */       MapDecoration $$2 = $$0.get($$1);
/* 537 */       this.decorations.put("icon-" + $$1, $$2);
/* 538 */       if ($$2.type().shouldTrackCount()) {
/* 539 */         this.trackedDecorationCount++;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Iterable<MapDecoration> getDecorations() {
/* 545 */     return this.decorations.values();
/*     */   }
/*     */   
/*     */   public boolean isTrackedCountOverLimit(int $$0) {
/* 549 */     return (this.trackedDecorationCount >= $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\saveddata\maps\MapItemSavedData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */