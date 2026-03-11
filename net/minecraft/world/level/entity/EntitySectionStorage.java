/*     */ package net.minecraft.world.level.entity;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectFunction;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongAVLTreeSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.longs.LongSortedSet;
/*     */ import java.util.Objects;
/*     */ import java.util.PrimitiveIterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.stream.LongStream;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.util.AbortableIterationConsumer;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntitySectionStorage<T extends EntityAccess>
/*     */ {
/*     */   private final Class<T> entityClass;
/*     */   private final Long2ObjectFunction<Visibility> intialSectionVisibility;
/*  30 */   private final Long2ObjectMap<EntitySection<T>> sections = (Long2ObjectMap<EntitySection<T>>)new Long2ObjectOpenHashMap();
/*     */ 
/*     */   
/*  33 */   private final LongSortedSet sectionIds = (LongSortedSet)new LongAVLTreeSet();
/*     */   
/*     */   public EntitySectionStorage(Class<T> $$0, Long2ObjectFunction<Visibility> $$1) {
/*  36 */     this.entityClass = $$0;
/*  37 */     this.intialSectionVisibility = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEachAccessibleNonEmptySection(AABB $$0, AbortableIterationConsumer<EntitySection<T>> $$1) {
/*  42 */     int $$2 = 2;
/*  43 */     int $$3 = SectionPos.posToSectionCoord($$0.minX - 2.0D);
/*  44 */     int $$4 = SectionPos.posToSectionCoord($$0.minY - 4.0D);
/*  45 */     int $$5 = SectionPos.posToSectionCoord($$0.minZ - 2.0D);
/*     */     
/*  47 */     int $$6 = SectionPos.posToSectionCoord($$0.maxX + 2.0D);
/*  48 */     int $$7 = SectionPos.posToSectionCoord($$0.maxY + 0.0D);
/*  49 */     int $$8 = SectionPos.posToSectionCoord($$0.maxZ + 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     for (int $$9 = $$3; $$9 <= $$6; $$9++) {
/*  55 */       long $$10 = SectionPos.asLong($$9, 0, 0);
/*  56 */       long $$11 = SectionPos.asLong($$9, -1, -1);
/*  57 */       LongBidirectionalIterator longBidirectionalIterator = this.sectionIds.subSet($$10, $$11 + 1L).iterator();
/*  58 */       while (longBidirectionalIterator.hasNext()) {
/*  59 */         long $$13 = longBidirectionalIterator.nextLong();
/*  60 */         int $$14 = SectionPos.y($$13);
/*  61 */         int $$15 = SectionPos.z($$13);
/*  62 */         if ($$14 >= $$4 && $$14 <= $$7 && $$15 >= $$5 && $$15 <= $$8) {
/*  63 */           EntitySection<T> $$16 = (EntitySection<T>)this.sections.get($$13);
/*  64 */           if ($$16 != null && !$$16.isEmpty() && $$16.getStatus().isAccessible() && 
/*  65 */             $$1.accept($$16).shouldAbort()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public LongStream getExistingSectionPositionsInChunk(long $$0) {
/*  75 */     int $$1 = ChunkPos.getX($$0);
/*  76 */     int $$2 = ChunkPos.getZ($$0);
/*  77 */     LongSortedSet $$3 = getChunkSections($$1, $$2);
/*  78 */     if ($$3.isEmpty()) {
/*  79 */       return LongStream.empty();
/*     */     }
/*  81 */     LongBidirectionalIterator longBidirectionalIterator = $$3.iterator();
/*  82 */     return StreamSupport.longStream(Spliterators.spliteratorUnknownSize((PrimitiveIterator.OfLong)longBidirectionalIterator, 1301), false);
/*     */   }
/*     */   
/*     */   private LongSortedSet getChunkSections(int $$0, int $$1) {
/*  86 */     long $$2 = SectionPos.asLong($$0, 0, $$1);
/*  87 */     long $$3 = SectionPos.asLong($$0, -1, $$1);
/*  88 */     return this.sectionIds.subSet($$2, $$3 + 1L);
/*     */   }
/*     */   
/*     */   public Stream<EntitySection<T>> getExistingSectionsInChunk(long $$0) {
/*  92 */     Objects.requireNonNull(this.sections); return getExistingSectionPositionsInChunk($$0).<EntitySection<T>>mapToObj(this.sections::get).filter(Objects::nonNull);
/*     */   }
/*     */   
/*     */   private static long getChunkKeyFromSectionKey(long $$0) {
/*  96 */     return ChunkPos.asLong(SectionPos.x($$0), SectionPos.z($$0));
/*     */   }
/*     */   
/*     */   public EntitySection<T> getOrCreateSection(long $$0) {
/* 100 */     return (EntitySection<T>)this.sections.computeIfAbsent($$0, this::createSection);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public EntitySection<T> getSection(long $$0) {
/* 105 */     return (EntitySection<T>)this.sections.get($$0);
/*     */   }
/*     */   
/*     */   private EntitySection<T> createSection(long $$0) {
/* 109 */     long $$1 = getChunkKeyFromSectionKey($$0);
/* 110 */     Visibility $$2 = (Visibility)this.intialSectionVisibility.get($$1);
/* 111 */     this.sectionIds.add($$0);
/* 112 */     return new EntitySection<>(this.entityClass, $$2);
/*     */   }
/*     */   
/*     */   public LongSet getAllChunksWithExistingSections() {
/* 116 */     LongOpenHashSet longOpenHashSet = new LongOpenHashSet();
/* 117 */     this.sections.keySet().forEach($$1 -> $$0.add(getChunkKeyFromSectionKey($$1)));
/* 118 */     return (LongSet)longOpenHashSet;
/*     */   }
/*     */   
/*     */   public void getEntities(AABB $$0, AbortableIterationConsumer<T> $$1) {
/* 122 */     forEachAccessibleNonEmptySection($$0, $$2 -> $$2.getEntities($$0, $$1));
/*     */   }
/*     */   
/*     */   public <U extends T> void getEntities(EntityTypeTest<T, U> $$0, AABB $$1, AbortableIterationConsumer<U> $$2) {
/* 126 */     forEachAccessibleNonEmptySection($$1, $$3 -> $$3.getEntities($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public void remove(long $$0) {
/* 130 */     this.sections.remove($$0);
/* 131 */     this.sectionIds.remove($$0);
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public int count() {
/* 136 */     return this.sectionIds.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\EntitySectionStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */