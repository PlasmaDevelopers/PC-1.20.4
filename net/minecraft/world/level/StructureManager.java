/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import it.unimi.dsi.fastutil.longs.LongIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.WorldGenRegion;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.StructureAccess;
/*     */ import net.minecraft.world.level.levelgen.WorldOptions;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureCheck;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureStart;
/*     */ 
/*     */ public class StructureManager {
/*     */   private final LevelAccessor level;
/*     */   
/*     */   public StructureManager(LevelAccessor $$0, WorldOptions $$1, StructureCheck $$2) {
/*  35 */     this.level = $$0;
/*  36 */     this.worldOptions = $$1;
/*  37 */     this.structureCheck = $$2;
/*     */   }
/*     */   private final WorldOptions worldOptions; private final StructureCheck structureCheck;
/*     */   
/*     */   public StructureManager forWorldGenRegion(WorldGenRegion $$0) {
/*  42 */     if ($$0.getLevel() != this.level) {
/*  43 */       throw new IllegalStateException("Using invalid structure manager (source level: " + $$0.getLevel() + ", region: " + $$0);
/*     */     }
/*  45 */     return new StructureManager((LevelAccessor)$$0, this.worldOptions, this.structureCheck);
/*     */   }
/*     */   
/*     */   public List<StructureStart> startsForStructure(ChunkPos $$0, Predicate<Structure> $$1) {
/*  49 */     Map<Structure, LongSet> $$2 = this.level.getChunk($$0.x, $$0.z, ChunkStatus.STRUCTURE_REFERENCES).getAllReferences();
/*  50 */     ImmutableList.Builder<StructureStart> $$3 = ImmutableList.builder();
/*     */     
/*  52 */     for (Map.Entry<Structure, LongSet> $$4 : $$2.entrySet()) {
/*  53 */       Structure $$5 = $$4.getKey();
/*  54 */       if ($$1.test($$5)) {
/*  55 */         Objects.requireNonNull($$3); fillStartsForStructure($$5, $$4.getValue(), $$3::add);
/*     */       } 
/*     */     } 
/*     */     
/*  59 */     return (List<StructureStart>)$$3.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<StructureStart> startsForStructure(SectionPos $$0, Structure $$1) {
/*  68 */     LongSet $$2 = this.level.getChunk($$0.x(), $$0.z(), ChunkStatus.STRUCTURE_REFERENCES).getReferencesForStructure($$1);
/*  69 */     ImmutableList.Builder<StructureStart> $$3 = ImmutableList.builder();
/*  70 */     Objects.requireNonNull($$3); fillStartsForStructure($$1, $$2, $$3::add);
/*  71 */     return (List<StructureStart>)$$3.build();
/*     */   }
/*     */   
/*     */   public void fillStartsForStructure(Structure $$0, LongSet $$1, Consumer<StructureStart> $$2) {
/*  75 */     for (LongIterator<Long> longIterator = $$1.iterator(); longIterator.hasNext(); ) { long $$3 = ((Long)longIterator.next()).longValue();
/*  76 */       SectionPos $$4 = SectionPos.of(new ChunkPos($$3), this.level.getMinSection());
/*  77 */       StructureStart $$5 = getStartForStructure($$4, $$0, (StructureAccess)this.level.getChunk($$4.x(), $$4.z(), ChunkStatus.STRUCTURE_STARTS));
/*  78 */       if ($$5 != null && $$5.isValid()) {
/*  79 */         $$2.accept($$5);
/*     */       } }
/*     */   
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public StructureStart getStartForStructure(SectionPos $$0, Structure $$1, StructureAccess $$2) {
/*  86 */     return $$2.getStartForStructure($$1);
/*     */   }
/*     */   
/*     */   public void setStartForStructure(SectionPos $$0, Structure $$1, StructureStart $$2, StructureAccess $$3) {
/*  90 */     $$3.setStartForStructure($$1, $$2);
/*     */   }
/*     */   
/*     */   public void addReferenceForStructure(SectionPos $$0, Structure $$1, long $$2, StructureAccess $$3) {
/*  94 */     $$3.addReferenceForStructure($$1, $$2);
/*     */   }
/*     */   
/*     */   public boolean shouldGenerateStructures() {
/*  98 */     return this.worldOptions.generateStructures();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureStart getStructureAt(BlockPos $$0, Structure $$1) {
/* 105 */     for (StructureStart $$2 : startsForStructure(SectionPos.of($$0), $$1)) {
/* 106 */       if ($$2.getBoundingBox().isInside((Vec3i)$$0)) {
/* 107 */         return $$2;
/*     */       }
/*     */     } 
/* 110 */     return StructureStart.INVALID_START;
/*     */   }
/*     */   
/*     */   public StructureStart getStructureWithPieceAt(BlockPos $$0, ResourceKey<Structure> $$1) {
/* 114 */     Structure $$2 = (Structure)registryAccess().registryOrThrow(Registries.STRUCTURE).get($$1);
/* 115 */     if ($$2 == null) {
/* 116 */       return StructureStart.INVALID_START;
/*     */     }
/* 118 */     return getStructureWithPieceAt($$0, $$2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureStart getStructureWithPieceAt(BlockPos $$0, TagKey<Structure> $$1) {
/* 124 */     Registry<Structure> $$2 = registryAccess().registryOrThrow(Registries.STRUCTURE);
/* 125 */     for (StructureStart $$3 : startsForStructure(new ChunkPos($$0), $$2 -> ((Boolean)$$0.getHolder($$0.getId($$2)).map(()).orElse(Boolean.valueOf(false))).booleanValue())) {
/* 126 */       if (structureHasPieceAt($$0, $$3)) {
/* 127 */         return $$3;
/*     */       }
/*     */     } 
/* 130 */     return StructureStart.INVALID_START;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureStart getStructureWithPieceAt(BlockPos $$0, Structure $$1) {
/* 137 */     for (StructureStart $$2 : startsForStructure(SectionPos.of($$0), $$1)) {
/* 138 */       if (structureHasPieceAt($$0, $$2)) {
/* 139 */         return $$2;
/*     */       }
/*     */     } 
/* 142 */     return StructureStart.INVALID_START;
/*     */   }
/*     */   
/*     */   public boolean structureHasPieceAt(BlockPos $$0, StructureStart $$1) {
/* 146 */     for (StructurePiece $$2 : $$1.getPieces()) {
/* 147 */       if ($$2.getBoundingBox().isInside((Vec3i)$$0)) {
/* 148 */         return true;
/*     */       }
/*     */     } 
/* 151 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasAnyStructureAt(BlockPos $$0) {
/* 155 */     SectionPos $$1 = SectionPos.of($$0);
/* 156 */     return this.level.getChunk($$1.x(), $$1.z(), ChunkStatus.STRUCTURE_REFERENCES).hasAnyStructureReferences();
/*     */   }
/*     */   
/*     */   public Map<Structure, LongSet> getAllStructuresAt(BlockPos $$0) {
/* 160 */     SectionPos $$1 = SectionPos.of($$0);
/* 161 */     return this.level.getChunk($$1.x(), $$1.z(), ChunkStatus.STRUCTURE_REFERENCES).getAllReferences();
/*     */   }
/*     */   
/*     */   public StructureCheckResult checkStructurePresence(ChunkPos $$0, Structure $$1, boolean $$2) {
/* 165 */     return this.structureCheck.checkStart($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public void addReference(StructureStart $$0) {
/* 169 */     $$0.addReference();
/* 170 */     this.structureCheck.incrementReference($$0.getChunkPos(), $$0.getStructure());
/*     */   }
/*     */   
/*     */   public RegistryAccess registryAccess() {
/* 174 */     return this.level.registryAccess();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\StructureManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */