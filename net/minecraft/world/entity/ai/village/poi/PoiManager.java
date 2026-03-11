/*     */ package net.minecraft.world.entity.ai.village.poi;
/*     */ 
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.BiPredicate;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.SectionTracker;
/*     */ import net.minecraft.tags.PoiTypeTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*     */ import net.minecraft.world.level.chunk.storage.SectionStorage;
/*     */ 
/*     */ public class PoiManager
/*     */   extends SectionStorage<PoiSection> {
/*     */   public static final int MAX_VILLAGE_DISTANCE = 6;
/*  44 */   private final LongSet loadedChunks = (LongSet)new LongOpenHashSet(); public static final int VILLAGE_SECTION_SIZE = 1; private final DistanceTracker distanceTracker;
/*     */   
/*     */   public PoiManager(Path $$0, DataFixer $$1, boolean $$2, RegistryAccess $$3, LevelHeightAccessor $$4) {
/*  47 */     super($$0, PoiSection::codec, PoiSection::new, $$1, DataFixTypes.POI_CHUNK, $$2, $$3, $$4);
/*  48 */     this.distanceTracker = new DistanceTracker();
/*     */   }
/*     */   
/*     */   public void add(BlockPos $$0, Holder<PoiType> $$1) {
/*  52 */     ((PoiSection)getOrCreate(SectionPos.asLong($$0))).add($$0, $$1);
/*     */   }
/*     */   
/*     */   public void remove(BlockPos $$0) {
/*  56 */     getOrLoad(SectionPos.asLong($$0)).ifPresent($$1 -> $$1.remove($$0));
/*     */   }
/*     */   
/*     */   public long getCountInRange(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
/*  60 */     return getInRange($$0, $$1, $$2, $$3).count();
/*     */   }
/*     */   
/*     */   public boolean existsAtPosition(ResourceKey<PoiType> $$0, BlockPos $$1) {
/*  64 */     return exists($$1, $$1 -> $$1.is($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stream<PoiRecord> getInSquare(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
/*  71 */     int $$4 = Math.floorDiv($$2, 16) + 1;
/*     */     
/*  73 */     return ChunkPos.rangeClosed(new ChunkPos($$1), $$4).flatMap($$2 -> getInChunk($$0, $$2, $$1))
/*  74 */       .filter($$2 -> {
/*     */           BlockPos $$3 = $$2.getPos();
/*  76 */           return (Math.abs($$3.getX() - $$0.getX()) <= $$1 && Math.abs($$3.getZ() - $$0.getZ()) <= $$1);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<PoiRecord> getInRange(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
/*  82 */     int $$4 = $$2 * $$2;
/*  83 */     return getInSquare($$0, $$1, $$2, $$3).filter($$2 -> ($$2.getPos().distSqr((Vec3i)$$0) <= $$1));
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public Stream<PoiRecord> getInChunk(Predicate<Holder<PoiType>> $$0, ChunkPos $$1, Occupancy $$2) {
/*  88 */     return IntStream.range(this.levelHeightAccessor.getMinSection(), this.levelHeightAccessor.getMaxSection()).boxed()
/*  89 */       .map($$1 -> getOrLoad(SectionPos.of($$0, $$1.intValue()).asLong()))
/*  90 */       .filter(Optional::isPresent)
/*  91 */       .flatMap($$2 -> ((PoiSection)$$2.get()).getRecords($$0, $$1));
/*     */   }
/*     */   
/*     */   public Stream<BlockPos> findAll(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
/*  95 */     return getInRange($$0, $$2, $$3, $$4)
/*  96 */       .<BlockPos>map(PoiRecord::getPos)
/*  97 */       .filter($$1);
/*     */   }
/*     */   
/*     */   public Stream<Pair<Holder<PoiType>, BlockPos>> findAllWithType(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
/* 101 */     return getInRange($$0, $$2, $$3, $$4)
/* 102 */       .filter($$1 -> $$0.test($$1.getPos()))
/* 103 */       .map($$0 -> Pair.of($$0.getPoiType(), $$0.getPos()));
/*     */   }
/*     */   
/*     */   public Stream<Pair<Holder<PoiType>, BlockPos>> findAllClosestFirstWithType(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
/* 107 */     return findAllWithType($$0, $$1, $$2, $$3, $$4)
/* 108 */       .sorted(Comparator.comparingDouble($$1 -> ((BlockPos)$$1.getSecond()).distSqr((Vec3i)$$0)));
/*     */   }
/*     */   
/*     */   public Optional<BlockPos> find(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
/* 112 */     return findAll($$0, $$1, $$2, $$3, $$4).findFirst();
/*     */   }
/*     */   
/*     */   public Optional<BlockPos> findClosest(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
/* 116 */     return getInRange($$0, $$1, $$2, $$3)
/* 117 */       .<BlockPos>map(PoiRecord::getPos)
/* 118 */       .min(Comparator.comparingDouble($$1 -> $$1.distSqr((Vec3i)$$0)));
/*     */   }
/*     */   
/*     */   public Optional<Pair<Holder<PoiType>, BlockPos>> findClosestWithType(Predicate<Holder<PoiType>> $$0, BlockPos $$1, int $$2, Occupancy $$3) {
/* 122 */     return getInRange($$0, $$1, $$2, $$3)
/* 123 */       .min(Comparator.comparingDouble($$1 -> $$1.getPos().distSqr((Vec3i)$$0)))
/* 124 */       .map($$0 -> Pair.of($$0.getPoiType(), $$0.getPos()));
/*     */   }
/*     */   
/*     */   public Optional<BlockPos> findClosest(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, BlockPos $$2, int $$3, Occupancy $$4) {
/* 128 */     return getInRange($$0, $$2, $$3, $$4)
/* 129 */       .<BlockPos>map(PoiRecord::getPos)
/* 130 */       .filter($$1)
/* 131 */       .min(Comparator.comparingDouble($$1 -> $$1.distSqr((Vec3i)$$0)));
/*     */   }
/*     */   
/*     */   public Optional<BlockPos> take(Predicate<Holder<PoiType>> $$0, BiPredicate<Holder<PoiType>, BlockPos> $$1, BlockPos $$2, int $$3) {
/* 135 */     return getInRange($$0, $$2, $$3, Occupancy.HAS_SPACE)
/* 136 */       .filter($$1 -> $$0.test($$1.getPoiType(), $$1.getPos()))
/* 137 */       .findFirst()
/* 138 */       .map($$0 -> {
/*     */           $$0.acquireTicket();
/*     */           return $$0.getPos();
/*     */         });
/*     */   }
/*     */   
/*     */   public Optional<BlockPos> getRandom(Predicate<Holder<PoiType>> $$0, Predicate<BlockPos> $$1, Occupancy $$2, BlockPos $$3, int $$4, RandomSource $$5) {
/* 145 */     List<PoiRecord> $$6 = Util.toShuffledList(getInRange($$0, $$3, $$4, $$2), $$5);
/* 146 */     return $$6.stream().filter($$1 -> $$0.test($$1.getPos())).findFirst().map(PoiRecord::getPos);
/*     */   }
/*     */   
/*     */   public boolean release(BlockPos $$0) {
/* 150 */     return ((Boolean)getOrLoad(SectionPos.asLong($$0))
/* 151 */       .map($$1 -> Boolean.valueOf($$1.release($$0)))
/* 152 */       .orElseThrow(() -> (IllegalStateException)Util.pauseInIde(new IllegalStateException("POI never registered at " + $$0)))).booleanValue();
/*     */   }
/*     */   
/*     */   public boolean exists(BlockPos $$0, Predicate<Holder<PoiType>> $$1) {
/* 156 */     return ((Boolean)getOrLoad(SectionPos.asLong($$0)).map($$2 -> Boolean.valueOf($$2.exists($$0, $$1))).orElse(Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */   
/*     */   public Optional<Holder<PoiType>> getType(BlockPos $$0) {
/* 160 */     return getOrLoad(SectionPos.asLong($$0)).flatMap($$1 -> $$1.getType($$0));
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   @VisibleForDebug
/*     */   public int getFreeTickets(BlockPos $$0) {
/* 166 */     return ((Integer)getOrLoad(SectionPos.asLong($$0)).map($$1 -> Integer.valueOf($$1.getFreeTickets($$0))).orElse(Integer.valueOf(0))).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int sectionsToVillage(SectionPos $$0) {
/* 175 */     this.distanceTracker.runAllUpdates();
/* 176 */     return this.distanceTracker.getLevel($$0.asLong());
/*     */   }
/*     */   
/*     */   boolean isVillageCenter(long $$0) {
/* 180 */     Optional<PoiSection> $$1 = get($$0);
/* 181 */     if ($$1 == null) {
/* 182 */       return false;
/*     */     }
/*     */     
/* 185 */     return ((Boolean)$$1.<Boolean>map($$0 -> Boolean.valueOf($$0.getRecords((), Occupancy.IS_OCCUPIED).findAny().isPresent())).orElse(Boolean.valueOf(false))).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BooleanSupplier $$0) {
/* 190 */     super.tick($$0);
/* 191 */     this.distanceTracker.runAllUpdates();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setDirty(long $$0) {
/* 196 */     super.setDirty($$0);
/* 197 */     this.distanceTracker.update($$0, this.distanceTracker.getLevelFromSource($$0), false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onSectionLoad(long $$0) {
/* 202 */     this.distanceTracker.update($$0, this.distanceTracker.getLevelFromSource($$0), false);
/*     */   }
/*     */   
/*     */   public void checkConsistencyWithBlocks(SectionPos $$0, LevelChunkSection $$1) {
/* 206 */     Util.ifElse(getOrLoad($$0.asLong()), $$2 -> $$2.refresh(()), () -> {
/*     */           if (mayHavePoi($$0)) {
/*     */             PoiSection $$2 = (PoiSection)getOrCreate($$1.asLong());
/*     */             Objects.requireNonNull($$2);
/*     */             updateFromSection($$0, $$1, $$2::add);
/*     */           } 
/*     */         });
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
/*     */   private static boolean mayHavePoi(LevelChunkSection $$0) {
/* 224 */     return $$0.maybeHas(PoiTypes::hasPoi);
/*     */   }
/*     */   
/*     */   private void updateFromSection(LevelChunkSection $$0, SectionPos $$1, BiConsumer<BlockPos, Holder<PoiType>> $$2) {
/* 228 */     $$1.blocksInside().forEach($$2 -> {
/*     */           BlockState $$3 = $$0.getBlockState(SectionPos.sectionRelative($$2.getX()), SectionPos.sectionRelative($$2.getY()), SectionPos.sectionRelative($$2.getZ()));
/*     */           PoiTypes.forState($$3).ifPresent(());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureLoadedAndValid(LevelReader $$0, BlockPos $$1, int $$2) {
/* 239 */     SectionPos.aroundChunk(new ChunkPos($$1), Math.floorDiv($$2, 16), this.levelHeightAccessor.getMinSection(), this.levelHeightAccessor.getMaxSection())
/* 240 */       .map($$0 -> Pair.of($$0, getOrLoad($$0.asLong())))
/* 241 */       .filter($$0 -> !((Boolean)((Optional)$$0.getSecond()).map(PoiSection::isValid).orElse(Boolean.valueOf(false))).booleanValue())
/* 242 */       .map($$0 -> ((SectionPos)$$0.getFirst()).chunk())
/* 243 */       .filter($$0 -> this.loadedChunks.add($$0.toLong()))
/* 244 */       .forEach($$1 -> $$0.getChunk($$1.x, $$1.z, ChunkStatus.EMPTY));
/*     */   }
/*     */   
/*     */   public enum Occupancy {
/* 248 */     HAS_SPACE((String)PoiRecord::hasSpace),
/* 249 */     IS_OCCUPIED((String)PoiRecord::isOccupied),
/* 250 */     ANY((String)($$0 -> true));
/*     */     
/*     */     private final Predicate<? super PoiRecord> test;
/*     */     
/*     */     Occupancy(Predicate<? super PoiRecord> $$0) {
/* 255 */       this.test = $$0;
/*     */     }
/*     */     
/*     */     public Predicate<? super PoiRecord> getTest() {
/* 259 */       return this.test;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class DistanceTracker extends SectionTracker {
/*     */     private final Long2ByteMap levels;
/*     */     
/*     */     protected DistanceTracker() {
/* 267 */       super(7, 16, 256);
/* 268 */       this.levels = (Long2ByteMap)new Long2ByteOpenHashMap();
/* 269 */       this.levels.defaultReturnValue((byte)7);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getLevelFromSource(long $$0) {
/* 274 */       return PoiManager.this.isVillageCenter($$0) ? 0 : 7;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getLevel(long $$0) {
/* 279 */       return this.levels.get($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void setLevel(long $$0, int $$1) {
/* 284 */       if ($$1 > 6) {
/* 285 */         this.levels.remove($$0);
/*     */       } else {
/* 287 */         this.levels.put($$0, (byte)$$1);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void runAllUpdates() {
/* 292 */       runUpdates(2147483647);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\village\poi\PoiManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */