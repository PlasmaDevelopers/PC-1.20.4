/*     */ package net.minecraft.world.level.levelgen.structure;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StructureStart
/*     */ {
/*     */   public static final String INVALID_START_ID = "INVALID";
/*  27 */   public static final StructureStart INVALID_START = new StructureStart(null, new ChunkPos(0, 0), 0, new PiecesContainer(List.of()));
/*     */   
/*  29 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final Structure structure;
/*     */   
/*     */   private final PiecesContainer pieceContainer;
/*     */   
/*     */   private final ChunkPos chunkPos;
/*     */   private int references;
/*     */   @Nullable
/*     */   private volatile BoundingBox cachedBoundingBox;
/*     */   
/*     */   public StructureStart(Structure $$0, ChunkPos $$1, int $$2, PiecesContainer $$3) {
/*  41 */     this.structure = $$0;
/*  42 */     this.chunkPos = $$1;
/*  43 */     this.references = $$2;
/*  44 */     this.pieceContainer = $$3;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static StructureStart loadStaticStart(StructurePieceSerializationContext $$0, CompoundTag $$1, long $$2) {
/*  49 */     String $$3 = $$1.getString("id");
/*  50 */     if ("INVALID".equals($$3)) {
/*  51 */       return INVALID_START;
/*     */     }
/*     */ 
/*     */     
/*  55 */     Registry<Structure> $$4 = $$0.registryAccess().registryOrThrow(Registries.STRUCTURE);
/*  56 */     Structure $$5 = (Structure)$$4.get(new ResourceLocation($$3));
/*  57 */     if ($$5 == null) {
/*  58 */       LOGGER.error("Unknown stucture id: {}", $$3);
/*  59 */       return null;
/*     */     } 
/*     */     
/*  62 */     ChunkPos $$6 = new ChunkPos($$1.getInt("ChunkX"), $$1.getInt("ChunkZ"));
/*  63 */     int $$7 = $$1.getInt("references");
/*  64 */     ListTag $$8 = $$1.getList("Children", 10);
/*     */     
/*     */     try {
/*  67 */       PiecesContainer $$9 = PiecesContainer.load($$8, $$0);
/*  68 */       if ($$5 instanceof OceanMonumentStructure)
/*     */       {
/*  70 */         $$9 = OceanMonumentStructure.regeneratePiecesAfterLoad($$6, $$2, $$9);
/*     */       }
/*  72 */       return new StructureStart($$5, $$6, $$7, $$9);
/*  73 */     } catch (Exception $$10) {
/*  74 */       LOGGER.error("Failed Start with id {}", $$3, $$10);
/*  75 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public BoundingBox getBoundingBox() {
/*  80 */     BoundingBox $$0 = this.cachedBoundingBox;
/*  81 */     if ($$0 == null) {
/*  82 */       $$0 = this.structure.adjustBoundingBox(this.pieceContainer.calculateBoundingBox());
/*  83 */       this.cachedBoundingBox = $$0;
/*     */     } 
/*  85 */     return $$0;
/*     */   }
/*     */   
/*     */   public void placeInChunk(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5) {
/*  89 */     List<StructurePiece> $$6 = this.pieceContainer.pieces();
/*  90 */     if ($$6.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  95 */     BoundingBox $$7 = ((StructurePiece)$$6.get(0)).boundingBox;
/*  96 */     BlockPos $$8 = $$7.getCenter();
/*  97 */     BlockPos $$9 = new BlockPos($$8.getX(), $$7.minY(), $$8.getZ());
/*  98 */     for (StructurePiece $$10 : $$6) {
/*  99 */       if ($$10.getBoundingBox().intersects($$4)) {
/* 100 */         $$10.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$9);
/*     */       }
/*     */     } 
/*     */     
/* 104 */     this.structure.afterPlace($$0, $$1, $$2, $$3, $$4, $$5, this.pieceContainer);
/*     */   }
/*     */   
/*     */   public CompoundTag createTag(StructurePieceSerializationContext $$0, ChunkPos $$1) {
/* 108 */     CompoundTag $$2 = new CompoundTag();
/*     */     
/* 110 */     if (isValid()) {
/* 111 */       $$2.putString("id", $$0.registryAccess().registryOrThrow(Registries.STRUCTURE).getKey(this.structure).toString());
/*     */     } else {
/* 113 */       $$2.putString("id", "INVALID");
/* 114 */       return $$2;
/*     */     } 
/* 116 */     $$2.putInt("ChunkX", $$1.x);
/* 117 */     $$2.putInt("ChunkZ", $$1.z);
/* 118 */     $$2.putInt("references", this.references);
/* 119 */     $$2.put("Children", this.pieceContainer.save($$0));
/*     */     
/* 121 */     return $$2;
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 125 */     return !this.pieceContainer.isEmpty();
/*     */   }
/*     */   
/*     */   public ChunkPos getChunkPos() {
/* 129 */     return this.chunkPos;
/*     */   }
/*     */   
/*     */   public boolean canBeReferenced() {
/* 133 */     return (this.references < getMaxReferences());
/*     */   }
/*     */   
/*     */   public void addReference() {
/* 137 */     this.references++;
/*     */   }
/*     */   
/*     */   public int getReferences() {
/* 141 */     return this.references;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getMaxReferences() {
/* 146 */     return 1;
/*     */   }
/*     */   
/*     */   public Structure getStructure() {
/* 150 */     return this.structure;
/*     */   }
/*     */   
/*     */   public List<StructurePiece> getPieces() {
/* 154 */     return this.pieceContainer.pieces();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\StructureStart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */