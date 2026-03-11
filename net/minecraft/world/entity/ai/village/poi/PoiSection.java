/*     */ package net.minecraft.world.entity.ai.village.poi;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ 
/*     */ public class PoiSection {
/*  28 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Codec<PoiSection> codec(Runnable $$0) {
/*  39 */     Objects.requireNonNull(LOGGER); return RecordCodecBuilder.create($$1 -> $$1.group((App)RecordCodecBuilder.point($$0), (App)Codec.BOOL.optionalFieldOf("Valid", Boolean.valueOf(false)).forGetter(()), (App)PoiRecord.codec($$0).listOf().fieldOf("Records").forGetter(())).apply((Applicative)$$1, PoiSection::new)).orElseGet(Util.prefix("Failed to read POI section: ", LOGGER::error), () -> new PoiSection($$0, false, (List<PoiRecord>)ImmutableList.of()));
/*     */   }
/*     */   
/*  42 */   private final Short2ObjectMap<PoiRecord> records = (Short2ObjectMap<PoiRecord>)new Short2ObjectOpenHashMap();
/*  43 */   private final Map<Holder<PoiType>, Set<PoiRecord>> byType = Maps.newHashMap();
/*     */   private final Runnable setDirty;
/*     */   private boolean isValid;
/*     */   
/*     */   public PoiSection(Runnable $$0) {
/*  48 */     this($$0, true, (List<PoiRecord>)ImmutableList.of());
/*     */   }
/*     */   
/*     */   private PoiSection(Runnable $$0, boolean $$1, List<PoiRecord> $$2) {
/*  52 */     this.setDirty = $$0;
/*  53 */     this.isValid = $$1;
/*  54 */     $$2.forEach(this::add);
/*     */   }
/*     */   
/*     */   public Stream<PoiRecord> getRecords(Predicate<Holder<PoiType>> $$0, PoiManager.Occupancy $$1) {
/*  58 */     return this.byType.entrySet()
/*  59 */       .stream()
/*  60 */       .filter($$1 -> $$0.test((Holder)$$1.getKey()))
/*  61 */       .flatMap($$0 -> ((Set)$$0.getValue()).stream())
/*  62 */       .filter($$1.getTest());
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(BlockPos $$0, Holder<PoiType> $$1) {
/*  67 */     if (add(new PoiRecord($$0, $$1, this.setDirty))) {
/*  68 */       LOGGER.debug("Added POI of type {} @ {}", $$1.unwrapKey().map($$0 -> $$0.location().toString()).orElse("[unregistered]"), $$0);
/*  69 */       this.setDirty.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean add(PoiRecord $$0) {
/*  74 */     BlockPos $$1 = $$0.getPos();
/*  75 */     Holder<PoiType> $$2 = $$0.getPoiType();
/*  76 */     short $$3 = SectionPos.sectionRelativePos($$1);
/*  77 */     PoiRecord $$4 = (PoiRecord)this.records.get($$3);
/*     */     
/*  79 */     if ($$4 != null) {
/*  80 */       if ($$2.equals($$4.getPoiType())) {
/*  81 */         return false;
/*     */       }
/*  83 */       Util.logAndPauseIfInIde("POI data mismatch: already registered at " + $$1);
/*     */     } 
/*     */ 
/*     */     
/*  87 */     this.records.put($$3, $$0);
/*  88 */     ((Set<PoiRecord>)this.byType.computeIfAbsent($$2, $$0 -> Sets.newHashSet())).add($$0);
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   public void remove(BlockPos $$0) {
/*  93 */     PoiRecord $$1 = (PoiRecord)this.records.remove(SectionPos.sectionRelativePos($$0));
/*  94 */     if ($$1 == null) {
/*  95 */       LOGGER.error("POI data mismatch: never registered at {}", $$0);
/*     */       return;
/*     */     } 
/*  98 */     ((Set)this.byType.get($$1.getPoiType())).remove($$1);
/*     */     
/* 100 */     Objects.requireNonNull($$1); Objects.requireNonNull($$1); LOGGER.debug("Removed POI of type {} @ {}", LogUtils.defer($$1::getPoiType), LogUtils.defer($$1::getPos));
/* 101 */     this.setDirty.run();
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   @VisibleForDebug
/*     */   public int getFreeTickets(BlockPos $$0) {
/* 107 */     return ((Integer)getPoiRecord($$0).<Integer>map(PoiRecord::getFreeTickets).orElse(Integer.valueOf(0))).intValue();
/*     */   }
/*     */   
/*     */   public boolean release(BlockPos $$0) {
/* 111 */     PoiRecord $$1 = (PoiRecord)this.records.get(SectionPos.sectionRelativePos($$0));
/* 112 */     if ($$1 == null) {
/* 113 */       throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("POI never registered at " + $$0));
/*     */     }
/* 115 */     boolean $$2 = $$1.releaseTicket();
/* 116 */     this.setDirty.run();
/* 117 */     return $$2;
/*     */   }
/*     */   
/*     */   public boolean exists(BlockPos $$0, Predicate<Holder<PoiType>> $$1) {
/* 121 */     return getType($$0).filter($$1).isPresent();
/*     */   }
/*     */   
/*     */   public Optional<Holder<PoiType>> getType(BlockPos $$0) {
/* 125 */     return getPoiRecord($$0).map(PoiRecord::getPoiType);
/*     */   }
/*     */   
/*     */   private Optional<PoiRecord> getPoiRecord(BlockPos $$0) {
/* 129 */     return Optional.ofNullable((PoiRecord)this.records.get(SectionPos.sectionRelativePos($$0)));
/*     */   }
/*     */   
/*     */   public void refresh(Consumer<BiConsumer<BlockPos, Holder<PoiType>>> $$0) {
/* 133 */     if (!this.isValid) {
/* 134 */       Short2ObjectOpenHashMap short2ObjectOpenHashMap = new Short2ObjectOpenHashMap(this.records);
/* 135 */       clear();
/* 136 */       $$0.accept(($$1, $$2) -> {
/*     */             short $$3 = SectionPos.sectionRelativePos($$1);
/*     */             PoiRecord $$4 = (PoiRecord)$$0.computeIfAbsent($$3, ());
/*     */             add($$4);
/*     */           });
/* 141 */       this.isValid = true;
/* 142 */       this.setDirty.run();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void clear() {
/* 147 */     this.records.clear();
/* 148 */     this.byType.clear();
/*     */   }
/*     */   
/*     */   boolean isValid() {
/* 152 */     return this.isValid;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\village\poi\PoiSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */