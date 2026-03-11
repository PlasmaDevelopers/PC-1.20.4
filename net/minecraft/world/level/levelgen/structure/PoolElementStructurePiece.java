/*     */ package net.minecraft.world.level.levelgen.structure;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PoolElementStructurePiece extends StructurePiece {
/*  30 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   protected final StructurePoolElement element;
/*     */   protected BlockPos position;
/*     */   private final int groundLevelDelta;
/*     */   protected final Rotation rotation;
/*  36 */   private final List<JigsawJunction> junctions = Lists.newArrayList();
/*     */   private final StructureTemplateManager structureTemplateManager;
/*     */   
/*     */   public PoolElementStructurePiece(StructureTemplateManager $$0, StructurePoolElement $$1, BlockPos $$2, int $$3, Rotation $$4, BoundingBox $$5) {
/*  40 */     super(StructurePieceType.JIGSAW, 0, $$5);
/*  41 */     this.structureTemplateManager = $$0;
/*  42 */     this.element = $$1;
/*  43 */     this.position = $$2;
/*  44 */     this.groundLevelDelta = $$3;
/*  45 */     this.rotation = $$4;
/*     */   }
/*     */   
/*     */   public PoolElementStructurePiece(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  49 */     super(StructurePieceType.JIGSAW, $$1);
/*  50 */     this.structureTemplateManager = $$0.structureTemplateManager();
/*  51 */     this.position = new BlockPos($$1.getInt("PosX"), $$1.getInt("PosY"), $$1.getInt("PosZ"));
/*  52 */     this.groundLevelDelta = $$1.getInt("ground_level_delta");
/*     */     
/*  54 */     RegistryOps registryOps = RegistryOps.create((DynamicOps)NbtOps.INSTANCE, (HolderLookup.Provider)$$0.registryAccess());
/*  55 */     Objects.requireNonNull(LOGGER); this.element = (StructurePoolElement)StructurePoolElement.CODEC.parse((DynamicOps)registryOps, $$1.getCompound("pool_element")).resultOrPartial(LOGGER::error).orElseThrow(() -> new IllegalStateException("Invalid pool element found"));
/*     */     
/*  57 */     this.rotation = Rotation.valueOf($$1.getString("rotation"));
/*  58 */     this.boundingBox = this.element.getBoundingBox(this.structureTemplateManager, this.position, this.rotation);
/*     */     
/*  60 */     ListTag $$3 = $$1.getList("junctions", 10);
/*  61 */     this.junctions.clear();
/*  62 */     $$3.forEach($$1 -> this.junctions.add(JigsawJunction.deserialize(new Dynamic($$0, $$1))));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  67 */     $$1.putInt("PosX", this.position.getX());
/*  68 */     $$1.putInt("PosY", this.position.getY());
/*  69 */     $$1.putInt("PosZ", this.position.getZ());
/*  70 */     $$1.putInt("ground_level_delta", this.groundLevelDelta);
/*     */     
/*  72 */     RegistryOps registryOps = RegistryOps.create((DynamicOps)NbtOps.INSTANCE, (HolderLookup.Provider)$$0.registryAccess());
/*  73 */     Objects.requireNonNull(LOGGER); StructurePoolElement.CODEC.encodeStart((DynamicOps)registryOps, this.element).resultOrPartial(LOGGER::error).ifPresent($$1 -> $$0.put("pool_element", $$1));
/*     */     
/*  75 */     $$1.putString("rotation", this.rotation.name());
/*  76 */     ListTag $$3 = new ListTag();
/*  77 */     for (JigsawJunction $$4 : this.junctions) {
/*  78 */       $$3.add($$4.serialize((DynamicOps)registryOps).getValue());
/*     */     }
/*  80 */     $$1.put("junctions", (Tag)$$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  85 */     place($$0, $$1, $$2, $$3, $$4, $$6, false);
/*     */   }
/*     */   
/*     */   public void place(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, BlockPos $$5, boolean $$6) {
/*  89 */     this.element.place(this.structureTemplateManager, $$0, $$1, $$2, this.position, $$5, this.rotation, $$4, $$3, $$6);
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(int $$0, int $$1, int $$2) {
/*  94 */     super.move($$0, $$1, $$2);
/*  95 */     this.position = this.position.offset($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotation getRotation() {
/* 100 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return String.format(Locale.ROOT, "<%s | %s | %s | %s>", new Object[] { getClass().getSimpleName(), this.position, this.rotation, this.element });
/*     */   }
/*     */   
/*     */   public StructurePoolElement getElement() {
/* 109 */     return this.element;
/*     */   }
/*     */   
/*     */   public BlockPos getPosition() {
/* 113 */     return this.position;
/*     */   }
/*     */   
/*     */   public int getGroundLevelDelta() {
/* 117 */     return this.groundLevelDelta;
/*     */   }
/*     */   
/*     */   public void addJunction(JigsawJunction $$0) {
/* 121 */     this.junctions.add($$0);
/*     */   }
/*     */   
/*     */   public List<JigsawJunction> getJunctions() {
/* 125 */     return this.junctions;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\PoolElementStructurePiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */