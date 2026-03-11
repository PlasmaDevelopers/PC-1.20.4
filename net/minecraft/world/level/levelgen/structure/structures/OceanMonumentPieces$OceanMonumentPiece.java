/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.monster.ElderGuardian;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class OceanMonumentPiece
/*     */   extends StructurePiece
/*     */ {
/*  34 */   protected static final BlockState BASE_GRAY = Blocks.PRISMARINE.defaultBlockState();
/*  35 */   protected static final BlockState BASE_LIGHT = Blocks.PRISMARINE_BRICKS.defaultBlockState();
/*  36 */   protected static final BlockState BASE_BLACK = Blocks.DARK_PRISMARINE.defaultBlockState();
/*     */   
/*  38 */   protected static final BlockState DOT_DECO_DATA = BASE_LIGHT;
/*     */   
/*  40 */   protected static final BlockState LAMP_BLOCK = Blocks.SEA_LANTERN.defaultBlockState();
/*     */   
/*     */   protected static final boolean DO_FILL = true;
/*  43 */   protected static final BlockState FILL_BLOCK = Blocks.WATER.defaultBlockState();
/*  44 */   protected static final Set<Block> FILL_KEEP = (Set<Block>)ImmutableSet.builder()
/*  45 */     .add(Blocks.ICE)
/*  46 */     .add(Blocks.PACKED_ICE)
/*  47 */     .add(Blocks.BLUE_ICE)
/*  48 */     .add(FILL_BLOCK.getBlock())
/*  49 */     .build();
/*     */   
/*     */   protected static final int GRIDROOM_WIDTH = 8;
/*     */   
/*     */   protected static final int GRIDROOM_DEPTH = 8;
/*     */   protected static final int GRIDROOM_HEIGHT = 4;
/*     */   protected static final int GRID_WIDTH = 5;
/*     */   protected static final int GRID_DEPTH = 5;
/*     */   protected static final int GRID_HEIGHT = 3;
/*     */   protected static final int GRID_FLOOR_COUNT = 25;
/*     */   protected static final int GRID_SIZE = 75;
/*  60 */   protected static final int GRIDROOM_SOURCE_INDEX = getRoomIndex(2, 0, 0);
/*  61 */   protected static final int GRIDROOM_TOP_CONNECT_INDEX = getRoomIndex(2, 2, 0);
/*  62 */   protected static final int GRIDROOM_LEFTWING_CONNECT_INDEX = getRoomIndex(0, 1, 0);
/*  63 */   protected static final int GRIDROOM_RIGHTWING_CONNECT_INDEX = getRoomIndex(4, 1, 0);
/*     */   
/*     */   protected static final int LEFTWING_INDEX = 1001;
/*     */   
/*     */   protected static final int RIGHTWING_INDEX = 1002;
/*     */   protected static final int PENTHOUSE_INDEX = 1003;
/*     */   protected OceanMonumentPieces.RoomDefinition roomDefinition;
/*     */   
/*     */   protected static int getRoomIndex(int $$0, int $$1, int $$2) {
/*  72 */     return $$1 * 25 + $$2 * 5 + $$0;
/*     */   }
/*     */   
/*     */   public OceanMonumentPiece(StructurePieceType $$0, Direction $$1, int $$2, BoundingBox $$3) {
/*  76 */     super($$0, $$2, $$3);
/*  77 */     setOrientation($$1);
/*     */   }
/*     */   
/*     */   protected OceanMonumentPiece(StructurePieceType $$0, int $$1, Direction $$2, OceanMonumentPieces.RoomDefinition $$3, int $$4, int $$5, int $$6) {
/*  81 */     super($$0, $$1, makeBoundingBox($$2, $$3, $$4, $$5, $$6));
/*     */     
/*  83 */     setOrientation($$2);
/*  84 */     this.roomDefinition = $$3;
/*     */   }
/*     */   
/*     */   private static BoundingBox makeBoundingBox(Direction $$0, OceanMonumentPieces.RoomDefinition $$1, int $$2, int $$3, int $$4) {
/*  88 */     int $$5 = $$1.index;
/*  89 */     int $$6 = $$5 % 5;
/*  90 */     int $$7 = $$5 / 5 % 5;
/*  91 */     int $$8 = $$5 / 25;
/*     */ 
/*     */ 
/*     */     
/*  95 */     BoundingBox $$9 = makeBoundingBox(0, 0, 0, $$0, $$2 * 8, $$3 * 4, $$4 * 8);
/*     */     
/*  97 */     switch (OceanMonumentPieces.null.$SwitchMap$net$minecraft$core$Direction[$$0.ordinal()])
/*     */     { case 1:
/*  99 */         $$9.move($$6 * 8, $$8 * 4, -($$7 + $$4) * 8 + 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 114 */         return $$9;case 2: $$9.move($$6 * 8, $$8 * 4, $$7 * 8); return $$9;case 3: $$9.move(-($$7 + $$4) * 8 + 1, $$8 * 4, $$6 * 8); return $$9; }  $$9.move($$7 * 8, $$8 * 4, $$6 * 8); return $$9;
/*     */   }
/*     */   
/*     */   public OceanMonumentPiece(StructurePieceType $$0, CompoundTag $$1) {
/* 118 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {}
/*     */ 
/*     */   
/*     */   protected void generateWaterBox(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
/* 126 */     for (int $$8 = $$3; $$8 <= $$6; $$8++) {
/* 127 */       for (int $$9 = $$2; $$9 <= $$5; $$9++) {
/* 128 */         for (int $$10 = $$4; $$10 <= $$7; $$10++) {
/* 129 */           BlockState $$11 = getBlock((BlockGetter)$$0, $$9, $$8, $$10, $$1);
/* 130 */           if (!FILL_KEEP.contains($$11.getBlock())) {
/* 131 */             if (getWorldY($$8) >= $$0.getSeaLevel() && $$11 != FILL_BLOCK) {
/* 132 */               placeBlock($$0, Blocks.AIR.defaultBlockState(), $$9, $$8, $$10, $$1);
/*     */             } else {
/* 134 */               placeBlock($$0, FILL_BLOCK, $$9, $$8, $$10, $$1);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void generateDefaultFloor(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, boolean $$4) {
/* 143 */     if ($$4) {
/* 144 */       generateBox($$0, $$1, $$2 + 0, 0, $$3 + 0, $$2 + 2, 0, $$3 + 8 - 1, BASE_GRAY, BASE_GRAY, false);
/* 145 */       generateBox($$0, $$1, $$2 + 5, 0, $$3 + 0, $$2 + 8 - 1, 0, $$3 + 8 - 1, BASE_GRAY, BASE_GRAY, false);
/* 146 */       generateBox($$0, $$1, $$2 + 3, 0, $$3 + 0, $$2 + 4, 0, $$3 + 2, BASE_GRAY, BASE_GRAY, false);
/* 147 */       generateBox($$0, $$1, $$2 + 3, 0, $$3 + 5, $$2 + 4, 0, $$3 + 8 - 1, BASE_GRAY, BASE_GRAY, false);
/*     */       
/* 149 */       generateBox($$0, $$1, $$2 + 3, 0, $$3 + 2, $$2 + 4, 0, $$3 + 2, BASE_LIGHT, BASE_LIGHT, false);
/* 150 */       generateBox($$0, $$1, $$2 + 3, 0, $$3 + 5, $$2 + 4, 0, $$3 + 5, BASE_LIGHT, BASE_LIGHT, false);
/* 151 */       generateBox($$0, $$1, $$2 + 2, 0, $$3 + 3, $$2 + 2, 0, $$3 + 4, BASE_LIGHT, BASE_LIGHT, false);
/* 152 */       generateBox($$0, $$1, $$2 + 5, 0, $$3 + 3, $$2 + 5, 0, $$3 + 4, BASE_LIGHT, BASE_LIGHT, false);
/*     */     } else {
/* 154 */       generateBox($$0, $$1, $$2 + 0, 0, $$3 + 0, $$2 + 8 - 1, 0, $$3 + 8 - 1, BASE_GRAY, BASE_GRAY, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void generateBoxOnFillOnly(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, BlockState $$8) {
/* 159 */     for (int $$9 = $$3; $$9 <= $$6; $$9++) {
/* 160 */       for (int $$10 = $$2; $$10 <= $$5; $$10++) {
/* 161 */         for (int $$11 = $$4; $$11 <= $$7; $$11++) {
/* 162 */           if (getBlock((BlockGetter)$$0, $$10, $$9, $$11, $$1) == FILL_BLOCK)
/*     */           {
/*     */             
/* 165 */             placeBlock($$0, $$8, $$10, $$9, $$11, $$1); } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean chunkIntersects(BoundingBox $$0, int $$1, int $$2, int $$3, int $$4) {
/* 172 */     int $$5 = getWorldX($$1, $$2);
/* 173 */     int $$6 = getWorldZ($$1, $$2);
/* 174 */     int $$7 = getWorldX($$3, $$4);
/* 175 */     int $$8 = getWorldZ($$3, $$4);
/* 176 */     return $$0.intersects(Math.min($$5, $$7), Math.min($$6, $$8), Math.max($$5, $$7), Math.max($$6, $$8));
/*     */   }
/*     */   
/*     */   protected void spawnElder(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4) {
/* 180 */     BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$2, $$3, $$4);
/* 181 */     if ($$1.isInside((Vec3i)mutableBlockPos)) {
/* 182 */       ElderGuardian $$6 = (ElderGuardian)EntityType.ELDER_GUARDIAN.create((Level)$$0.getLevel());
/* 183 */       if ($$6 != null) {
/* 184 */         $$6.heal($$6.getMaxHealth());
/* 185 */         $$6.moveTo(mutableBlockPos.getX() + 0.5D, mutableBlockPos.getY(), mutableBlockPos.getZ() + 0.5D, 0.0F, 0.0F);
/* 186 */         $$6.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$6.blockPosition()), MobSpawnType.STRUCTURE, null, null);
/* 187 */         $$0.addFreshEntityWithPassengers((Entity)$$6);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\OceanMonumentPieces$OceanMonumentPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */