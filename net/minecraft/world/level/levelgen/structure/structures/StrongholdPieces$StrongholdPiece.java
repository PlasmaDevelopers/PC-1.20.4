/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.ButtonBlock;
/*     */ import net.minecraft.world.level.block.DoorBlock;
/*     */ import net.minecraft.world.level.block.IronBarsBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
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
/*     */ abstract class StrongholdPiece
/*     */   extends StructurePiece
/*     */ {
/* 216 */   protected SmallDoorType entryDoor = SmallDoorType.OPENING;
/*     */   
/*     */   protected StrongholdPiece(StructurePieceType $$0, int $$1, BoundingBox $$2) {
/* 219 */     super($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public StrongholdPiece(StructurePieceType $$0, CompoundTag $$1) {
/* 223 */     super($$0, $$1);
/* 224 */     this.entryDoor = SmallDoorType.valueOf($$1.getString("EntryDoor"));
/*     */   }
/*     */   
/*     */   protected enum SmallDoorType {
/* 228 */     OPENING, WOOD_DOOR, GRATES, IRON_DOOR;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 233 */     $$1.putString("EntryDoor", this.entryDoor.name());
/*     */   }
/*     */   
/*     */   protected void generateSmallDoor(WorldGenLevel $$0, RandomSource $$1, BoundingBox $$2, SmallDoorType $$3, int $$4, int $$5, int $$6) {
/* 237 */     switch (StrongholdPieces.null.$SwitchMap$net$minecraft$world$level$levelgen$structure$structures$StrongholdPieces$StrongholdPiece$SmallDoorType[$$3.ordinal()]) {
/*     */       case 1:
/* 239 */         generateBox($$0, $$2, $$4, $$5, $$6, $$4 + 3 - 1, $$5 + 3 - 1, $$6, CAVE_AIR, CAVE_AIR, false);
/*     */         break;
/*     */       case 2:
/* 242 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4, $$5, $$6, $$2);
/* 243 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4, $$5 + 1, $$6, $$2);
/* 244 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4, $$5 + 2, $$6, $$2);
/* 245 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4 + 1, $$5 + 2, $$6, $$2);
/* 246 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4 + 2, $$5 + 2, $$6, $$2);
/* 247 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4 + 2, $$5 + 1, $$6, $$2);
/* 248 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4 + 2, $$5, $$6, $$2);
/* 249 */         placeBlock($$0, Blocks.OAK_DOOR.defaultBlockState(), $$4 + 1, $$5, $$6, $$2);
/* 250 */         placeBlock($$0, (BlockState)Blocks.OAK_DOOR.defaultBlockState().setValue((Property)DoorBlock.HALF, (Comparable)DoubleBlockHalf.UPPER), $$4 + 1, $$5 + 1, $$6, $$2);
/*     */         break;
/*     */       case 3:
/* 253 */         placeBlock($$0, Blocks.CAVE_AIR.defaultBlockState(), $$4 + 1, $$5, $$6, $$2);
/* 254 */         placeBlock($$0, Blocks.CAVE_AIR.defaultBlockState(), $$4 + 1, $$5 + 1, $$6, $$2);
/* 255 */         placeBlock($$0, (BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true)), $$4, $$5, $$6, $$2);
/* 256 */         placeBlock($$0, (BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true)), $$4, $$5 + 1, $$6, $$2);
/* 257 */         placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true)), $$4, $$5 + 2, $$6, $$2);
/* 258 */         placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true)), $$4 + 1, $$5 + 2, $$6, $$2);
/* 259 */         placeBlock($$0, (BlockState)((BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true))).setValue((Property)IronBarsBlock.WEST, Boolean.valueOf(true)), $$4 + 2, $$5 + 2, $$6, $$2);
/* 260 */         placeBlock($$0, (BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true)), $$4 + 2, $$5 + 1, $$6, $$2);
/* 261 */         placeBlock($$0, (BlockState)Blocks.IRON_BARS.defaultBlockState().setValue((Property)IronBarsBlock.EAST, Boolean.valueOf(true)), $$4 + 2, $$5, $$6, $$2);
/*     */         break;
/*     */       case 4:
/* 264 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4, $$5, $$6, $$2);
/* 265 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4, $$5 + 1, $$6, $$2);
/* 266 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4, $$5 + 2, $$6, $$2);
/* 267 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4 + 1, $$5 + 2, $$6, $$2);
/* 268 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4 + 2, $$5 + 2, $$6, $$2);
/* 269 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4 + 2, $$5 + 1, $$6, $$2);
/* 270 */         placeBlock($$0, Blocks.STONE_BRICKS.defaultBlockState(), $$4 + 2, $$5, $$6, $$2);
/* 271 */         placeBlock($$0, Blocks.IRON_DOOR.defaultBlockState(), $$4 + 1, $$5, $$6, $$2);
/* 272 */         placeBlock($$0, (BlockState)Blocks.IRON_DOOR.defaultBlockState().setValue((Property)DoorBlock.HALF, (Comparable)DoubleBlockHalf.UPPER), $$4 + 1, $$5 + 1, $$6, $$2);
/* 273 */         placeBlock($$0, (BlockState)Blocks.STONE_BUTTON.defaultBlockState().setValue((Property)ButtonBlock.FACING, (Comparable)Direction.NORTH), $$4 + 2, $$5 + 1, $$6 + 1, $$2);
/* 274 */         placeBlock($$0, (BlockState)Blocks.STONE_BUTTON.defaultBlockState().setValue((Property)ButtonBlock.FACING, (Comparable)Direction.SOUTH), $$4 + 2, $$5 + 1, $$6 - 1, $$2);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SmallDoorType randomSmallDoor(RandomSource $$0) {
/* 280 */     int $$1 = $$0.nextInt(5);
/* 281 */     switch ($$1)
/*     */     
/*     */     { 
/*     */       default:
/* 285 */         return SmallDoorType.OPENING;
/*     */       case 2:
/* 287 */         return SmallDoorType.WOOD_DOOR;
/*     */       case 3:
/* 289 */         return SmallDoorType.GRATES;
/*     */       case 4:
/* 291 */         break; }  return SmallDoorType.IRON_DOOR;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected StructurePiece generateSmallDoorChildForward(StrongholdPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4) {
/* 297 */     Direction $$5 = getOrientation();
/* 298 */     if ($$5 != null) {
/* 299 */       switch (StrongholdPieces.null.$SwitchMap$net$minecraft$core$Direction[$$5.ordinal()]) {
/*     */         case 1:
/* 301 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$3, this.boundingBox.minY() + $$4, this.boundingBox.minZ() - 1, $$5, getGenDepth());
/*     */         case 2:
/* 303 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$3, this.boundingBox.minY() + $$4, this.boundingBox.maxZ() + 1, $$5, getGenDepth());
/*     */         case 3:
/* 305 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$4, this.boundingBox.minZ() + $$3, $$5, getGenDepth());
/*     */         case 4:
/* 307 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$4, this.boundingBox.minZ() + $$3, $$5, getGenDepth());
/*     */       } 
/*     */     }
/* 310 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected StructurePiece generateSmallDoorChildLeft(StrongholdPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4) {
/* 315 */     Direction $$5 = getOrientation();
/* 316 */     if ($$5 != null) {
/* 317 */       switch (StrongholdPieces.null.$SwitchMap$net$minecraft$core$Direction[$$5.ordinal()]) {
/*     */         case 1:
/* 319 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.WEST, getGenDepth());
/*     */         case 2:
/* 321 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.WEST, getGenDepth());
/*     */         case 3:
/* 323 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.minZ() - 1, Direction.NORTH, getGenDepth());
/*     */         case 4:
/* 325 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.minZ() - 1, Direction.NORTH, getGenDepth());
/*     */       } 
/*     */     }
/* 328 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected StructurePiece generateSmallDoorChildRight(StrongholdPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4) {
/* 333 */     Direction $$5 = getOrientation();
/* 334 */     if ($$5 != null) {
/* 335 */       switch (StrongholdPieces.null.$SwitchMap$net$minecraft$core$Direction[$$5.ordinal()]) {
/*     */         case 1:
/* 337 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.EAST, getGenDepth());
/*     */         case 2:
/* 339 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.EAST, getGenDepth());
/*     */         case 3:
/* 341 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.maxZ() + 1, Direction.SOUTH, getGenDepth());
/*     */         case 4:
/* 343 */           return StrongholdPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.maxZ() + 1, Direction.SOUTH, getGenDepth());
/*     */       } 
/*     */     }
/* 346 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean isOkBox(BoundingBox $$0) {
/* 350 */     return ($$0 != null && $$0.minY() > 10);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\StrongholdPieces$StrongholdPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */