/*     */ package net.minecraft.util.datafix.fixes;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.DataFix;
/*     */ import com.mojang.datafixers.DataFixUtils;
/*     */ import com.mojang.datafixers.OpticFinder;
/*     */ import com.mojang.datafixers.TypeRewriteRule;
/*     */ import com.mojang.datafixers.Typed;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import com.mojang.datafixers.types.templates.List;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntIterator;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.datafix.PackedBitStorage;
/*     */ 
/*     */ 
/*     */ public class LeavesFix
/*     */   extends DataFix
/*     */ {
/*     */   private static final int NORTH_WEST_MASK = 128;
/*     */   private static final int WEST_MASK = 64;
/*     */   private static final int SOUTH_WEST_MASK = 32;
/*     */   private static final int SOUTH_MASK = 16;
/*  45 */   private static final int[][] DIRECTIONS = new int[][] { { -1, 0, 0 }, { 1, 0, 0 }, { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 } };
/*     */   
/*     */   private static final int SOUTH_EAST_MASK = 8;
/*     */   
/*     */   private static final int EAST_MASK = 4;
/*     */   private static final int NORTH_EAST_MASK = 2;
/*     */   private static final int NORTH_MASK = 1;
/*     */   private static final int DECAY_DISTANCE = 7;
/*     */   private static final int SIZE_BITS = 12;
/*     */   private static final int SIZE = 4096;
/*     */   static final Object2IntMap<String> LEAVES;
/*     */   
/*     */   static {
/*  58 */     LEAVES = (Object2IntMap<String>)DataFixUtils.make(new Object2IntOpenHashMap(), $$0 -> {
/*     */           $$0.put("minecraft:acacia_leaves", 0);
/*     */           $$0.put("minecraft:birch_leaves", 1);
/*     */           $$0.put("minecraft:dark_oak_leaves", 2);
/*     */           $$0.put("minecraft:jungle_leaves", 3);
/*     */           $$0.put("minecraft:oak_leaves", 4);
/*     */           $$0.put("minecraft:spruce_leaves", 5);
/*     */         });
/*     */   }
/*  67 */   static final Set<String> LOGS = (Set<String>)ImmutableSet.of("minecraft:acacia_bark", "minecraft:birch_bark", "minecraft:dark_oak_bark", "minecraft:jungle_bark", "minecraft:oak_bark", "minecraft:spruce_bark", (Object[])new String[] { "minecraft:acacia_log", "minecraft:birch_log", "minecraft:dark_oak_log", "minecraft:jungle_log", "minecraft:oak_log", "minecraft:spruce_log", "minecraft:stripped_acacia_log", "minecraft:stripped_birch_log", "minecraft:stripped_dark_oak_log", "minecraft:stripped_jungle_log", "minecraft:stripped_oak_log", "minecraft:stripped_spruce_log" });
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
/*     */   public LeavesFix(Schema $$0, boolean $$1) {
/*  89 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected TypeRewriteRule makeRule() {
/*  94 */     Type<?> $$0 = getInputSchema().getType(References.CHUNK);
/*     */     
/*  96 */     OpticFinder<?> $$1 = $$0.findField("Level");
/*  97 */     OpticFinder<?> $$2 = $$1.type().findField("Sections");
/*  98 */     Type<?> $$3 = $$2.type();
/*  99 */     if (!($$3 instanceof List.ListType)) {
/* 100 */       throw new IllegalStateException("Expecting sections to be a list.");
/*     */     }
/* 102 */     Type<?> $$4 = ((List.ListType)$$3).getElement();
/* 103 */     OpticFinder<?> $$5 = DSL.typeFinder($$4);
/*     */     
/* 105 */     return fixTypeEverywhereTyped("Leaves fix", $$0, $$3 -> $$3.updateTyped($$0, ()));
/*     */   }
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
/*     */   public static abstract class Section
/*     */   {
/*     */     protected static final String BLOCK_STATES_TAG = "BlockStates";
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
/*     */     protected static final String NAME_TAG = "Name";
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
/*     */     protected static final String PROPERTIES_TAG = "Properties";
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
/* 193 */     private final Type<Pair<String, Dynamic<?>>> blockStateType = DSL.named(References.BLOCK_STATE.typeName(), DSL.remainderType());
/* 194 */     protected final OpticFinder<List<Pair<String, Dynamic<?>>>> paletteFinder = DSL.fieldFinder("Palette", (Type)DSL.list(this.blockStateType));
/*     */     
/*     */     protected final List<Dynamic<?>> palette;
/*     */     protected final int index;
/*     */     @Nullable
/*     */     protected PackedBitStorage storage;
/*     */     
/*     */     public Section(Typed<?> $$0, Schema $$1) {
/* 202 */       if (!Objects.equals($$1.getType(References.BLOCK_STATE), this.blockStateType)) {
/* 203 */         throw new IllegalStateException("Block state type is not what was expected.");
/*     */       }
/*     */       
/* 206 */       Optional<List<Pair<String, Dynamic<?>>>> $$2 = $$0.getOptional(this.paletteFinder);
/*     */       
/* 208 */       this.palette = (List<Dynamic<?>>)$$2.map($$0 -> (List)$$0.stream().map(Pair::getSecond).collect(Collectors.toList())).orElse(ImmutableList.of());
/*     */       
/* 210 */       Dynamic<?> $$3 = (Dynamic)$$0.get(DSL.remainderFinder());
/* 211 */       this.index = $$3.get("Y").asInt(0);
/*     */       
/* 213 */       readStorage($$3);
/*     */     }
/*     */     
/*     */     protected void readStorage(Dynamic<?> $$0) {
/* 217 */       if (skippable()) {
/* 218 */         this.storage = null;
/*     */       } else {
/* 220 */         long[] $$1 = $$0.get("BlockStates").asLongStream().toArray();
/* 221 */         int $$2 = Math.max(4, DataFixUtils.ceillog2(this.palette.size()));
/* 222 */         this.storage = new PackedBitStorage($$2, 4096, $$1);
/*     */       } 
/*     */     }
/*     */     
/*     */     public Typed<?> write(Typed<?> $$0) {
/* 227 */       if (isSkippable()) {
/* 228 */         return $$0;
/*     */       }
/* 230 */       return $$0
/* 231 */         .update(DSL.remainderFinder(), $$0 -> $$0.set("BlockStates", $$0.createLongList(Arrays.stream(this.storage.getRaw()))))
/* 232 */         .set(this.paletteFinder, this.palette.stream().map($$0 -> Pair.of(References.BLOCK_STATE.typeName(), $$0)).collect(Collectors.toList()));
/*     */     }
/*     */     
/*     */     public boolean isSkippable() {
/* 236 */       return (this.storage == null);
/*     */     }
/*     */     
/*     */     public int getBlock(int $$0) {
/* 240 */       return this.storage.get($$0);
/*     */     }
/*     */     
/*     */     protected int getStateId(String $$0, boolean $$1, int $$2) {
/* 244 */       return LeavesFix.LEAVES.get($$0).intValue() << 5 | ($$1 ? 16 : 0) | $$2;
/*     */     }
/*     */     
/*     */     int getIndex() {
/* 248 */       return this.index;
/*     */     }
/*     */     
/*     */     protected abstract boolean skippable();
/*     */   }
/*     */   
/*     */   public static final class LeavesSection
/*     */     extends Section
/*     */   {
/*     */     private static final String PERSISTENT = "persistent";
/*     */     private static final String DECAYABLE = "decayable";
/*     */     private static final String DISTANCE = "distance";
/*     */     @Nullable
/*     */     private IntSet leaveIds;
/*     */     @Nullable
/*     */     private IntSet logIds;
/*     */     @Nullable
/*     */     private Int2IntMap stateToIdMap;
/*     */     
/*     */     public LeavesSection(Typed<?> $$0, Schema $$1) {
/* 268 */       super($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean skippable() {
/* 273 */       this.leaveIds = (IntSet)new IntOpenHashSet();
/* 274 */       this.logIds = (IntSet)new IntOpenHashSet();
/* 275 */       this.stateToIdMap = (Int2IntMap)new Int2IntOpenHashMap();
/*     */       
/* 277 */       for (int $$0 = 0; $$0 < this.palette.size(); $$0++) {
/* 278 */         Dynamic<?> $$1 = this.palette.get($$0);
/* 279 */         String $$2 = $$1.get("Name").asString("");
/* 280 */         if (LeavesFix.LEAVES.containsKey($$2)) {
/* 281 */           boolean $$3 = Objects.equals($$1.get("Properties").get("decayable").asString(""), "false");
/* 282 */           this.leaveIds.add($$0);
/* 283 */           this.stateToIdMap.put(getStateId($$2, $$3, 7), $$0);
/* 284 */           this.palette.set($$0, makeLeafTag($$1, $$2, $$3, 7));
/*     */         } 
/* 286 */         if (LeavesFix.LOGS.contains($$2)) {
/* 287 */           this.logIds.add($$0);
/*     */         }
/*     */       } 
/*     */       
/* 291 */       return (this.leaveIds.isEmpty() && this.logIds.isEmpty());
/*     */     }
/*     */     
/*     */     private Dynamic<?> makeLeafTag(Dynamic<?> $$0, String $$1, boolean $$2, int $$3) {
/* 295 */       Dynamic<?> $$4 = $$0.emptyMap();
/* 296 */       $$4 = $$4.set("persistent", $$4.createString($$2 ? "true" : "false"));
/* 297 */       $$4 = $$4.set("distance", $$4.createString(Integer.toString($$3)));
/*     */       
/* 299 */       Dynamic<?> $$5 = $$0.emptyMap();
/* 300 */       $$5 = $$5.set("Properties", $$4);
/* 301 */       $$5 = $$5.set("Name", $$5.createString($$1));
/* 302 */       return $$5;
/*     */     }
/*     */     
/*     */     public boolean isLog(int $$0) {
/* 306 */       return this.logIds.contains($$0);
/*     */     }
/*     */     
/*     */     public boolean isLeaf(int $$0) {
/* 310 */       return this.leaveIds.contains($$0);
/*     */     }
/*     */     
/*     */     int getDistance(int $$0) {
/* 314 */       if (isLog($$0)) {
/* 315 */         return 0;
/*     */       }
/* 317 */       return Integer.parseInt(((Dynamic)this.palette.get($$0)).get("Properties").get("distance").asString(""));
/*     */     }
/*     */     
/*     */     void setDistance(int $$0, int $$1, int $$2) {
/* 321 */       Dynamic<?> $$3 = this.palette.get($$1);
/* 322 */       String $$4 = $$3.get("Name").asString("");
/* 323 */       boolean $$5 = Objects.equals($$3.get("Properties").get("persistent").asString(""), "true");
/* 324 */       int $$6 = getStateId($$4, $$5, $$2);
/*     */       
/* 326 */       if (!this.stateToIdMap.containsKey($$6)) {
/* 327 */         int $$7 = this.palette.size();
/* 328 */         this.leaveIds.add($$7);
/* 329 */         this.stateToIdMap.put($$6, $$7);
/* 330 */         this.palette.add(makeLeafTag($$3, $$4, $$5, $$2));
/*     */       } 
/*     */       
/* 333 */       int $$8 = this.stateToIdMap.get($$6);
/* 334 */       if (1 << this.storage.getBits() <= $$8) {
/* 335 */         PackedBitStorage $$9 = new PackedBitStorage(this.storage.getBits() + 1, 4096);
/* 336 */         for (int $$10 = 0; $$10 < 4096; $$10++) {
/* 337 */           $$9.set($$10, this.storage.get($$10));
/*     */         }
/* 339 */         this.storage = $$9;
/*     */       } 
/* 341 */       this.storage.set($$0, $$8);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getIndex(int $$0, int $$1, int $$2) {
/* 346 */     return $$1 << 8 | $$2 << 4 | $$0;
/*     */   }
/*     */   
/*     */   private int getX(int $$0) {
/* 350 */     return $$0 & 0xF;
/*     */   }
/*     */   
/*     */   private int getY(int $$0) {
/* 354 */     return $$0 >> 8 & 0xFF;
/*     */   }
/*     */   
/*     */   private int getZ(int $$0) {
/* 358 */     return $$0 >> 4 & 0xF;
/*     */   }
/*     */   
/*     */   public static int getSideMask(boolean $$0, boolean $$1, boolean $$2, boolean $$3) {
/* 362 */     int $$4 = 0;
/* 363 */     if ($$2) {
/* 364 */       if ($$1) {
/* 365 */         $$4 |= 0x2;
/* 366 */       } else if ($$0) {
/* 367 */         $$4 |= 0x80;
/*     */       } else {
/* 369 */         $$4 |= 0x1;
/*     */       } 
/* 371 */     } else if ($$3) {
/* 372 */       if ($$0) {
/* 373 */         $$4 |= 0x20;
/* 374 */       } else if ($$1) {
/* 375 */         $$4 |= 0x8;
/*     */       } else {
/* 377 */         $$4 |= 0x10;
/*     */       } 
/* 379 */     } else if ($$1) {
/* 380 */       $$4 |= 0x4;
/* 381 */     } else if ($$0) {
/* 382 */       $$4 |= 0x40;
/*     */     } 
/* 384 */     return $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\LeavesFix.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */