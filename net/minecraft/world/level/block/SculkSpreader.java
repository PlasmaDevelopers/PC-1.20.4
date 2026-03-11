/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function5;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SculkSpreader
/*     */ {
/*     */   public static final int MAX_GROWTH_RATE_RADIUS = 24;
/*     */   public static final int MAX_CHARGE = 1000;
/*     */   public static final float MAX_DECAY_FACTOR = 0.5F;
/*     */   private static final int MAX_CURSORS = 32;
/*     */   public static final int SHRIEKER_PLACEMENT_RATE = 11;
/*     */   final boolean isWorldGeneration;
/*     */   private final TagKey<Block> replaceableBlocks;
/*     */   private final int growthSpawnCost;
/*     */   private final int noGrowthRadius;
/*     */   private final int chargeDecayRate;
/*     */   private final int additionalDecayRate;
/*  64 */   private List<ChargeCursor> cursors = new ArrayList<>();
/*     */   
/*     */   public SculkSpreader(boolean $$0, TagKey<Block> $$1, int $$2, int $$3, int $$4, int $$5) {
/*  67 */     this.isWorldGeneration = $$0;
/*  68 */     this.replaceableBlocks = $$1;
/*  69 */     this.growthSpawnCost = $$2;
/*  70 */     this.noGrowthRadius = $$3;
/*  71 */     this.chargeDecayRate = $$4;
/*  72 */     this.additionalDecayRate = $$5;
/*     */   }
/*     */   
/*     */   public static SculkSpreader createLevelSpreader() {
/*  76 */     return new SculkSpreader(false, BlockTags.SCULK_REPLACEABLE, 10, 4, 10, 5);
/*     */   }
/*     */   
/*     */   public static SculkSpreader createWorldGenSpreader() {
/*  80 */     return new SculkSpreader(true, BlockTags.SCULK_REPLACEABLE_WORLD_GEN, 50, 1, 5, 10);
/*     */   }
/*     */   
/*     */   public TagKey<Block> replaceableBlocks() {
/*  84 */     return this.replaceableBlocks;
/*     */   }
/*     */   
/*     */   public int growthSpawnCost() {
/*  88 */     return this.growthSpawnCost;
/*     */   }
/*     */   
/*     */   public int noGrowthRadius() {
/*  92 */     return this.noGrowthRadius;
/*     */   }
/*     */   
/*     */   public int chargeDecayRate() {
/*  96 */     return this.chargeDecayRate;
/*     */   }
/*     */   
/*     */   public int additionalDecayRate() {
/* 100 */     return this.additionalDecayRate;
/*     */   }
/*     */   
/*     */   public boolean isWorldGeneration() {
/* 104 */     return this.isWorldGeneration;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public List<ChargeCursor> getCursors() {
/* 109 */     return this.cursors;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 113 */     this.cursors.clear();
/*     */   }
/*     */   
/* 116 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 119 */     if ($$0.contains("cursors", 9)) {
/* 120 */       this.cursors.clear();
/*     */ 
/*     */ 
/*     */       
/* 124 */       Objects.requireNonNull(LOGGER);
/* 125 */       List<ChargeCursor> $$1 = ChargeCursor.CODEC.listOf().parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.getList("cursors", 10))).resultOrPartial(LOGGER::error).orElseGet(ArrayList::new);
/*     */       
/* 127 */       int $$2 = Math.min($$1.size(), 32);
/* 128 */       for (int $$3 = 0; $$3 < $$2; $$3++) {
/* 129 */         addCursor($$1.get($$3));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void save(CompoundTag $$0) {
/* 136 */     Objects.requireNonNull(LOGGER); ChargeCursor.CODEC.listOf().encodeStart((DynamicOps)NbtOps.INSTANCE, this.cursors).resultOrPartial(LOGGER::error)
/* 137 */       .ifPresent($$1 -> $$0.put("cursors", $$1));
/*     */   }
/*     */   
/*     */   public static class ChargeCursor {
/*     */     private static final ObjectArrayList<Vec3i> NON_CORNER_NEIGHBOURS;
/*     */     public static final int MAX_CURSOR_DECAY_DELAY = 1;
/*     */     private BlockPos pos;
/*     */     int charge;
/*     */     private int updateDelay;
/*     */     private int decayDelay;
/*     */     @Nullable
/*     */     private Set<Direction> facings;
/*     */     private static final Codec<Set<Direction>> DIRECTION_SET;
/*     */     public static final Codec<ChargeCursor> CODEC;
/*     */     
/*     */     static {
/* 153 */       NON_CORNER_NEIGHBOURS = (ObjectArrayList<Vec3i>)Util.make(new ObjectArrayList(18), $$0 -> {
/*     */             Objects.requireNonNull($$0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             BlockPos.betweenClosedStream(new BlockPos(-1, -1, -1), new BlockPos(1, 1, 1)).filter(()).map(BlockPos::immutable).forEach($$0::add);
/*     */           });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 171 */       DIRECTION_SET = Direction.CODEC.listOf().xmap($$0 -> Sets.newEnumSet($$0, Direction.class), Lists::newArrayList);
/*     */       
/* 173 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockPos.CODEC.fieldOf("pos").forGetter(ChargeCursor::getPos), (App)Codec.intRange(0, 1000).fieldOf("charge").orElse(Integer.valueOf(0)).forGetter(ChargeCursor::getCharge), (App)Codec.intRange(0, 1).fieldOf("decay_delay").orElse(Integer.valueOf(1)).forGetter(ChargeCursor::getDecayDelay), (App)Codec.intRange(0, 2147483647).fieldOf("update_delay").orElse(Integer.valueOf(0)).forGetter(()), (App)DIRECTION_SET.optionalFieldOf("facings").forGetter(())).apply((Applicative)$$0, ChargeCursor::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ChargeCursor(BlockPos $$0, int $$1, int $$2, int $$3, Optional<Set<Direction>> $$4) {
/* 182 */       this.pos = $$0;
/* 183 */       this.charge = $$1;
/* 184 */       this.decayDelay = $$2;
/* 185 */       this.updateDelay = $$3;
/* 186 */       this.facings = $$4.orElse(null);
/*     */     }
/*     */     
/*     */     public ChargeCursor(BlockPos $$0, int $$1) {
/* 190 */       this($$0, $$1, 1, 0, Optional.empty());
/*     */     }
/*     */     
/*     */     public BlockPos getPos() {
/* 194 */       return this.pos;
/*     */     }
/*     */     
/*     */     public int getCharge() {
/* 198 */       return this.charge;
/*     */     }
/*     */     
/*     */     public int getDecayDelay() {
/* 202 */       return this.decayDelay;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public Set<Direction> getFacingData() {
/* 207 */       return this.facings;
/*     */     }
/*     */     
/*     */     private boolean shouldUpdate(LevelAccessor $$0, BlockPos $$1, boolean $$2) {
/* 211 */       if (this.charge <= 0) {
/* 212 */         return false;
/*     */       }
/* 214 */       if ($$2) {
/* 215 */         return true;
/*     */       }
/* 217 */       if ($$0 instanceof ServerLevel) { ServerLevel $$3 = (ServerLevel)$$0;
/* 218 */         return $$3.shouldTickBlocksAt($$1); }
/*     */       
/* 220 */       return false;
/*     */     }
/*     */     
/*     */     public void update(LevelAccessor $$0, BlockPos $$1, RandomSource $$2, SculkSpreader $$3, boolean $$4) {
/* 224 */       if (!shouldUpdate($$0, $$1, $$3.isWorldGeneration)) {
/*     */         return;
/*     */       }
/*     */       
/* 228 */       if (this.updateDelay > 0) {
/* 229 */         this.updateDelay--;
/*     */         
/*     */         return;
/*     */       } 
/* 233 */       BlockState $$5 = $$0.getBlockState(this.pos);
/* 234 */       SculkBehaviour $$6 = getBlockBehaviour($$5);
/*     */ 
/*     */       
/* 237 */       if ($$4 && $$6.attemptSpreadVein($$0, this.pos, $$5, this.facings, $$3.isWorldGeneration())) {
/* 238 */         if ($$6.canChangeBlockStateOnSpread()) {
/* 239 */           $$5 = $$0.getBlockState(this.pos);
/* 240 */           $$6 = getBlockBehaviour($$5);
/*     */         } 
/* 242 */         $$0.playSound(null, this.pos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */       } 
/*     */ 
/*     */       
/* 246 */       this.charge = $$6.attemptUseCharge(this, $$0, $$1, $$2, $$3, $$4);
/*     */       
/* 248 */       if (this.charge <= 0) {
/* 249 */         $$6.onDischarged($$0, $$5, this.pos, $$2);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 254 */       BlockPos $$7 = getValidMovementPos($$0, this.pos, $$2);
/* 255 */       if ($$7 != null) {
/* 256 */         $$6.onDischarged($$0, $$5, this.pos, $$2);
/* 257 */         this.pos = $$7.immutable();
/* 258 */         if ($$3.isWorldGeneration() && !this.pos.closerThan(new Vec3i($$1.getX(), this.pos.getY(), $$1.getZ()), 15.0D)) {
/* 259 */           this.charge = 0;
/*     */           return;
/*     */         } 
/* 262 */         $$5 = $$0.getBlockState($$7);
/*     */       } 
/*     */       
/* 265 */       if ($$5.getBlock() instanceof SculkBehaviour) {
/* 266 */         this.facings = MultifaceBlock.availableFaces($$5);
/*     */       }
/* 268 */       this.decayDelay = $$6.updateDecayDelay(this.decayDelay);
/* 269 */       this.updateDelay = $$6.getSculkSpreadDelay();
/*     */     }
/*     */     
/*     */     void mergeWith(ChargeCursor $$0) {
/* 273 */       this.charge += $$0.charge;
/* 274 */       $$0.charge = 0;
/* 275 */       this.updateDelay = Math.min(this.updateDelay, $$0.updateDelay);
/*     */     }
/*     */     
/*     */     private static SculkBehaviour getBlockBehaviour(BlockState $$0) {
/* 279 */       Block block = $$0.getBlock(); SculkBehaviour $$1 = (SculkBehaviour)block; return (block instanceof SculkBehaviour) ? $$1 : SculkBehaviour.DEFAULT;
/*     */     }
/*     */     
/*     */     private static List<Vec3i> getRandomizedNonCornerNeighbourOffsets(RandomSource $$0) {
/* 283 */       return Util.shuffledCopy(NON_CORNER_NEIGHBOURS, $$0);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     private static BlockPos getValidMovementPos(LevelAccessor $$0, BlockPos $$1, RandomSource $$2) {
/* 288 */       BlockPos.MutableBlockPos $$3 = $$1.mutable();
/* 289 */       BlockPos.MutableBlockPos $$4 = $$1.mutable();
/*     */       
/* 291 */       for (Vec3i $$5 : getRandomizedNonCornerNeighbourOffsets($$2)) {
/* 292 */         $$4.setWithOffset((Vec3i)$$1, $$5);
/* 293 */         BlockState $$6 = $$0.getBlockState((BlockPos)$$4);
/*     */         
/* 295 */         if ($$6.getBlock() instanceof SculkBehaviour && isMovementUnobstructed($$0, $$1, (BlockPos)$$4)) {
/* 296 */           $$3.set((Vec3i)$$4);
/*     */           
/* 298 */           if (SculkVeinBlock.hasSubstrateAccess($$0, $$6, (BlockPos)$$4)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/* 303 */       return $$3.equals($$1) ? null : (BlockPos)$$3;
/*     */     }
/*     */     
/*     */     private static boolean isMovementUnobstructed(LevelAccessor $$0, BlockPos $$1, BlockPos $$2) {
/* 307 */       if ($$1.distManhattan((Vec3i)$$2) == 1) {
/* 308 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 312 */       BlockPos $$3 = $$2.subtract((Vec3i)$$1);
/* 313 */       Direction $$4 = Direction.fromAxisAndDirection(Direction.Axis.X, ($$3.getX() < 0) ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
/* 314 */       Direction $$5 = Direction.fromAxisAndDirection(Direction.Axis.Y, ($$3.getY() < 0) ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
/* 315 */       Direction $$6 = Direction.fromAxisAndDirection(Direction.Axis.Z, ($$3.getZ() < 0) ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
/*     */       
/* 317 */       if ($$3.getX() == 0)
/* 318 */         return (isUnobstructed($$0, $$1, $$5) || isUnobstructed($$0, $$1, $$6)); 
/* 319 */       if ($$3.getY() == 0) {
/* 320 */         return (isUnobstructed($$0, $$1, $$4) || isUnobstructed($$0, $$1, $$6));
/*     */       }
/* 322 */       return (isUnobstructed($$0, $$1, $$4) || isUnobstructed($$0, $$1, $$5));
/*     */     }
/*     */ 
/*     */     
/*     */     private static boolean isUnobstructed(LevelAccessor $$0, BlockPos $$1, Direction $$2) {
/* 327 */       BlockPos $$3 = $$1.relative($$2);
/* 328 */       return !$$0.getBlockState($$3).isFaceSturdy((BlockGetter)$$0, $$3, $$2.getOpposite());
/*     */     }
/*     */   }
/*     */   
/*     */   public void addCursors(BlockPos $$0, int $$1) {
/* 333 */     while ($$1 > 0) {
/* 334 */       int $$2 = Math.min($$1, 1000);
/* 335 */       addCursor(new ChargeCursor($$0, $$2));
/* 336 */       $$1 -= $$2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addCursor(ChargeCursor $$0) {
/* 341 */     if (this.cursors.size() >= 32) {
/*     */       return;
/*     */     }
/* 344 */     this.cursors.add($$0);
/*     */   }
/*     */   
/*     */   public void updateCursors(LevelAccessor $$0, BlockPos $$1, RandomSource $$2, boolean $$3) {
/* 348 */     if (this.cursors.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 352 */     List<ChargeCursor> $$4 = new ArrayList<>();
/* 353 */     Map<BlockPos, ChargeCursor> $$5 = new HashMap<>();
/* 354 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/*     */     
/* 356 */     for (ChargeCursor $$7 : this.cursors) {
/* 357 */       $$7.update($$0, $$1, $$2, this, $$3);
/*     */       
/* 359 */       if ($$7.charge <= 0) {
/* 360 */         $$0.levelEvent(3006, $$7.getPos(), 0);
/*     */         
/*     */         continue;
/*     */       } 
/* 364 */       BlockPos $$8 = $$7.getPos();
/* 365 */       object2IntOpenHashMap.computeInt($$8, ($$1, $$2) -> Integer.valueOf((($$2 == null) ? 0 : $$2.intValue()) + $$0.charge));
/*     */       
/* 367 */       ChargeCursor $$9 = $$5.get($$8);
/* 368 */       if ($$9 == null) {
/* 369 */         $$5.put($$8, $$7);
/* 370 */         $$4.add($$7);
/*     */         
/*     */         continue;
/*     */       } 
/* 374 */       if (!isWorldGeneration() && $$7.charge + $$9.charge <= 1000) {
/* 375 */         $$9.mergeWith($$7);
/*     */         
/*     */         continue;
/*     */       } 
/* 379 */       $$4.add($$7);
/*     */       
/* 381 */       if ($$7.charge < $$9.charge) {
/* 382 */         $$5.put($$8, $$7);
/*     */       }
/*     */     } 
/*     */     
/* 386 */     for (ObjectIterator<Object2IntMap.Entry<BlockPos>> objectIterator = object2IntOpenHashMap.object2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<BlockPos> $$10 = objectIterator.next();
/* 387 */       BlockPos $$11 = (BlockPos)$$10.getKey();
/* 388 */       int $$12 = $$10.getIntValue();
/*     */       
/* 390 */       ChargeCursor $$13 = $$5.get($$11);
/* 391 */       Collection<Direction> $$14 = ($$13 == null) ? null : $$13.getFacingData();
/*     */       
/* 393 */       if ($$12 > 0 && $$14 != null) {
/* 394 */         int $$15 = (int)(Math.log1p($$12) / 2.299999952316284D) + 1;
/* 395 */         int $$16 = ($$15 << 6) + MultifaceBlock.pack($$14);
/* 396 */         $$0.levelEvent(3006, $$11, $$16);
/*     */       }  }
/*     */     
/* 399 */     this.cursors = $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SculkSpreader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */