/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MineShaftRoom
/*     */   extends MineshaftPieces.MineShaftPiece
/*     */ {
/* 197 */   private final List<BoundingBox> childEntranceBoxes = Lists.newLinkedList();
/*     */   
/*     */   public MineShaftRoom(int $$0, RandomSource $$1, int $$2, int $$3, MineshaftStructure.Type $$4) {
/* 200 */     super(StructurePieceType.MINE_SHAFT_ROOM, $$0, $$4, new BoundingBox($$2, 50, $$3, $$2 + 7 + $$1.nextInt(6), 54 + $$1.nextInt(6), $$3 + 7 + $$1.nextInt(6)));
/* 201 */     this.type = $$4;
/*     */   }
/*     */   
/*     */   public MineShaftRoom(CompoundTag $$0) {
/* 205 */     super(StructurePieceType.MINE_SHAFT_ROOM, $$0);
/*     */ 
/*     */     
/* 208 */     Objects.requireNonNull(MineshaftPieces.LOGGER);
/* 209 */     Objects.requireNonNull(this.childEntranceBoxes); BoundingBox.CODEC.listOf().parse((DynamicOps)NbtOps.INSTANCE, $$0.getList("Entrances", 11)).resultOrPartial(MineshaftPieces.LOGGER::error).ifPresent(this.childEntranceBoxes::addAll);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 214 */     int $$3 = getGenDepth();
/*     */ 
/*     */ 
/*     */     
/* 218 */     int $$4 = this.boundingBox.getYSpan() - 3 - 1;
/* 219 */     if ($$4 <= 0) {
/* 220 */       $$4 = 1;
/*     */     }
/*     */ 
/*     */     
/* 224 */     int $$5 = 0;
/* 225 */     while ($$5 < this.boundingBox.getXSpan()) {
/* 226 */       $$5 += $$2.nextInt(this.boundingBox.getXSpan());
/* 227 */       if ($$5 + 3 > this.boundingBox.getXSpan()) {
/*     */         break;
/*     */       }
/* 230 */       MineshaftPieces.MineShaftPiece $$6 = MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$5, this.boundingBox.minY() + $$2.nextInt($$4) + 1, this.boundingBox.minZ() - 1, Direction.NORTH, $$3);
/* 231 */       if ($$6 != null) {
/* 232 */         BoundingBox $$7 = $$6.getBoundingBox();
/* 233 */         this.childEntranceBoxes.add(new BoundingBox($$7.minX(), $$7.minY(), this.boundingBox.minZ(), $$7.maxX(), $$7.maxY(), this.boundingBox.minZ() + 1));
/*     */       } 
/* 235 */       $$5 += 4;
/*     */     } 
/*     */     
/* 238 */     $$5 = 0;
/* 239 */     while ($$5 < this.boundingBox.getXSpan()) {
/* 240 */       $$5 += $$2.nextInt(this.boundingBox.getXSpan());
/* 241 */       if ($$5 + 3 > this.boundingBox.getXSpan()) {
/*     */         break;
/*     */       }
/* 244 */       MineshaftPieces.MineShaftPiece $$8 = MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$5, this.boundingBox.minY() + $$2.nextInt($$4) + 1, this.boundingBox.maxZ() + 1, Direction.SOUTH, $$3);
/* 245 */       if ($$8 != null) {
/* 246 */         BoundingBox $$9 = $$8.getBoundingBox();
/* 247 */         this.childEntranceBoxes.add(new BoundingBox($$9.minX(), $$9.minY(), this.boundingBox.maxZ() - 1, $$9.maxX(), $$9.maxY(), this.boundingBox.maxZ()));
/*     */       } 
/* 249 */       $$5 += 4;
/*     */     } 
/*     */     
/* 252 */     $$5 = 0;
/* 253 */     while ($$5 < this.boundingBox.getZSpan()) {
/* 254 */       $$5 += $$2.nextInt(this.boundingBox.getZSpan());
/* 255 */       if ($$5 + 3 > this.boundingBox.getZSpan()) {
/*     */         break;
/*     */       }
/* 258 */       MineshaftPieces.MineShaftPiece $$10 = MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$2.nextInt($$4) + 1, this.boundingBox.minZ() + $$5, Direction.WEST, $$3);
/* 259 */       if ($$10 != null) {
/* 260 */         BoundingBox $$11 = $$10.getBoundingBox();
/* 261 */         this.childEntranceBoxes.add(new BoundingBox(this.boundingBox.minX(), $$11.minY(), $$11.minZ(), this.boundingBox.minX() + 1, $$11.maxY(), $$11.maxZ()));
/*     */       } 
/* 263 */       $$5 += 4;
/*     */     } 
/*     */     
/* 266 */     $$5 = 0;
/* 267 */     while ($$5 < this.boundingBox.getZSpan()) {
/* 268 */       $$5 += $$2.nextInt(this.boundingBox.getZSpan());
/* 269 */       if ($$5 + 3 > this.boundingBox.getZSpan()) {
/*     */         break;
/*     */       }
/* 272 */       StructurePiece $$12 = MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$2.nextInt($$4) + 1, this.boundingBox.minZ() + $$5, Direction.EAST, $$3);
/* 273 */       if ($$12 != null) {
/* 274 */         BoundingBox $$13 = $$12.getBoundingBox();
/* 275 */         this.childEntranceBoxes.add(new BoundingBox(this.boundingBox.maxX() - 1, $$13.minY(), $$13.minZ(), this.boundingBox.maxX(), $$13.maxY(), $$13.maxZ()));
/*     */       } 
/* 277 */       $$5 += 4;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 283 */     if (isInInvalidLocation((LevelAccessor)$$0, $$4)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 288 */     generateBox($$0, $$4, this.boundingBox.minX(), this.boundingBox.minY() + 1, this.boundingBox.minZ(), this.boundingBox.maxX(), Math.min(this.boundingBox.minY() + 3, this.boundingBox.maxY()), this.boundingBox.maxZ(), CAVE_AIR, CAVE_AIR, false);
/* 289 */     for (BoundingBox $$7 : this.childEntranceBoxes) {
/* 290 */       generateBox($$0, $$4, $$7.minX(), $$7.maxY() - 2, $$7.minZ(), $$7.maxX(), $$7.maxY(), $$7.maxZ(), CAVE_AIR, CAVE_AIR, false);
/*     */     }
/* 292 */     generateUpperHalfSphere($$0, $$4, this.boundingBox.minX(), this.boundingBox.minY() + 4, this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ(), CAVE_AIR, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(int $$0, int $$1, int $$2) {
/* 297 */     super.move($$0, $$1, $$2);
/* 298 */     for (BoundingBox $$3 : this.childEntranceBoxes) {
/* 299 */       $$3.move($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 305 */     super.addAdditionalSaveData($$0, $$1);
/*     */ 
/*     */     
/* 308 */     Objects.requireNonNull(MineshaftPieces.LOGGER); BoundingBox.CODEC.listOf().encodeStart((DynamicOps)NbtOps.INSTANCE, this.childEntranceBoxes).resultOrPartial(MineshaftPieces.LOGGER::error)
/* 309 */       .ifPresent($$1 -> $$0.put("Entrances", $$1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\MineshaftPieces$MineShaftRoom.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */