/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.RandomSource;
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
/*     */ abstract class NetherBridgePiece
/*     */   extends StructurePiece
/*     */ {
/*     */   protected NetherBridgePiece(StructurePieceType $$0, int $$1, BoundingBox $$2) {
/* 118 */     super($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public NetherBridgePiece(StructurePieceType $$0, CompoundTag $$1) {
/* 122 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {}
/*     */ 
/*     */   
/*     */   private int updatePieceWeight(List<NetherFortressPieces.PieceWeight> $$0) {
/* 130 */     boolean $$1 = false;
/* 131 */     int $$2 = 0;
/* 132 */     for (NetherFortressPieces.PieceWeight $$3 : $$0) {
/* 133 */       if ($$3.maxPlaceCount > 0 && $$3.placeCount < $$3.maxPlaceCount) {
/* 134 */         $$1 = true;
/*     */       }
/* 136 */       $$2 += $$3.weight;
/*     */     } 
/* 138 */     return $$1 ? $$2 : -1;
/*     */   }
/*     */   
/*     */   private NetherBridgePiece generatePiece(NetherFortressPieces.StartPiece $$0, List<NetherFortressPieces.PieceWeight> $$1, StructurePieceAccessor $$2, RandomSource $$3, int $$4, int $$5, int $$6, Direction $$7, int $$8) {
/* 142 */     int $$9 = updatePieceWeight($$1);
/* 143 */     boolean $$10 = ($$9 > 0 && $$8 <= 30);
/*     */     
/* 145 */     int $$11 = 0;
/* 146 */     while ($$11 < 5 && $$10) {
/* 147 */       $$11++;
/*     */       
/* 149 */       int $$12 = $$3.nextInt($$9);
/* 150 */       for (NetherFortressPieces.PieceWeight $$13 : $$1) {
/* 151 */         $$12 -= $$13.weight;
/* 152 */         if ($$12 < 0) {
/* 153 */           if (!$$13.doPlace($$8) || ($$13 == $$0.previousPiece && !$$13.allowInRow)) {
/*     */             break;
/*     */           }
/*     */           
/* 157 */           NetherBridgePiece $$14 = NetherFortressPieces.findAndCreateBridgePieceFactory($$13, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
/* 158 */           if ($$14 != null) {
/* 159 */             $$13.placeCount++;
/* 160 */             $$0.previousPiece = $$13;
/*     */             
/* 162 */             if (!$$13.isValid()) {
/* 163 */               $$1.remove($$13);
/*     */             }
/* 165 */             return $$14;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 170 */     return NetherFortressPieces.BridgeEndFiller.createPiece($$2, $$3, $$4, $$5, $$6, $$7, $$8);
/*     */   }
/*     */   
/*     */   private StructurePiece generateAndAddPiece(NetherFortressPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, int $$5, @Nullable Direction $$6, int $$7, boolean $$8) {
/* 174 */     if (Math.abs($$3 - $$0.getBoundingBox().minX()) > 112 || Math.abs($$5 - $$0.getBoundingBox().minZ()) > 112) {
/* 175 */       return NetherFortressPieces.BridgeEndFiller.createPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     }
/* 177 */     List<NetherFortressPieces.PieceWeight> $$9 = $$0.availableBridgePieces;
/* 178 */     if ($$8) {
/* 179 */       $$9 = $$0.availableCastlePieces;
/*     */     }
/* 181 */     StructurePiece $$10 = generatePiece($$0, $$9, $$1, $$2, $$3, $$4, $$5, $$6, $$7 + 1);
/* 182 */     if ($$10 != null) {
/* 183 */       $$1.addPiece($$10);
/* 184 */       $$0.pendingChildren.add($$10);
/*     */     } 
/* 186 */     return $$10;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected StructurePiece generateChildForward(NetherFortressPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, boolean $$5) {
/* 191 */     Direction $$6 = getOrientation();
/* 192 */     if ($$6 != null) {
/* 193 */       switch (NetherFortressPieces.null.$SwitchMap$net$minecraft$core$Direction[$$6.ordinal()]) {
/*     */         case 1:
/* 195 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$3, this.boundingBox.minY() + $$4, this.boundingBox.minZ() - 1, $$6, getGenDepth(), $$5);
/*     */         case 2:
/* 197 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$3, this.boundingBox.minY() + $$4, this.boundingBox.maxZ() + 1, $$6, getGenDepth(), $$5);
/*     */         case 3:
/* 199 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$4, this.boundingBox.minZ() + $$3, $$6, getGenDepth(), $$5);
/*     */         case 4:
/* 201 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$4, this.boundingBox.minZ() + $$3, $$6, getGenDepth(), $$5);
/*     */       } 
/*     */     }
/* 204 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected StructurePiece generateChildLeft(NetherFortressPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, boolean $$5) {
/* 209 */     Direction $$6 = getOrientation();
/* 210 */     if ($$6 != null) {
/* 211 */       switch (NetherFortressPieces.null.$SwitchMap$net$minecraft$core$Direction[$$6.ordinal()]) {
/*     */         case 1:
/* 213 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.WEST, getGenDepth(), $$5);
/*     */         case 2:
/* 215 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.WEST, getGenDepth(), $$5);
/*     */         case 3:
/* 217 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.minZ() - 1, Direction.NORTH, getGenDepth(), $$5);
/*     */         case 4:
/* 219 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.minZ() - 1, Direction.NORTH, getGenDepth(), $$5);
/*     */       } 
/*     */     }
/* 222 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected StructurePiece generateChildRight(NetherFortressPieces.StartPiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, boolean $$5) {
/* 227 */     Direction $$6 = getOrientation();
/* 228 */     if ($$6 != null) {
/* 229 */       switch (NetherFortressPieces.null.$SwitchMap$net$minecraft$core$Direction[$$6.ordinal()]) {
/*     */         case 1:
/* 231 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.EAST, getGenDepth(), $$5);
/*     */         case 2:
/* 233 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$3, this.boundingBox.minZ() + $$4, Direction.EAST, getGenDepth(), $$5);
/*     */         case 3:
/* 235 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.maxZ() + 1, Direction.SOUTH, getGenDepth(), $$5);
/*     */         case 4:
/* 237 */           return generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$4, this.boundingBox.minY() + $$3, this.boundingBox.maxZ() + 1, Direction.SOUTH, getGenDepth(), $$5);
/*     */       } 
/*     */     }
/* 240 */     return null;
/*     */   }
/*     */   
/*     */   protected static boolean isOkBox(BoundingBox $$0) {
/* 244 */     return ($$0 != null && $$0.minY() > 10);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\NetherFortressPieces$NetherBridgePiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */