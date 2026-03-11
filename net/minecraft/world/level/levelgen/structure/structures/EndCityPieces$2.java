/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.Tuple;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements EndCityPieces.SectionGenerator
/*     */ {
/*     */   public void init() {}
/*     */   
/*     */   public boolean generate(StructureTemplateManager $$0, int $$1, EndCityPieces.EndCityPiece $$2, BlockPos $$3, List<StructurePiece> $$4, RandomSource $$5) {
/* 197 */     Rotation $$6 = $$2.placeSettings().getRotation();
/* 198 */     EndCityPieces.EndCityPiece $$7 = $$2;
/* 199 */     $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(3 + $$5.nextInt(2), -3, 3 + $$5.nextInt(2)), "tower_base", $$6, true));
/* 200 */     $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(0, 7, 0), "tower_piece", $$6, true));
/*     */     
/* 202 */     EndCityPieces.EndCityPiece $$8 = ($$5.nextInt(3) == 0) ? $$7 : null;
/*     */     
/* 204 */     int $$9 = 1 + $$5.nextInt(3);
/* 205 */     for (int $$10 = 0; $$10 < $$9; $$10++) {
/* 206 */       $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(0, 4, 0), "tower_piece", $$6, true));
/* 207 */       if ($$10 < $$9 - 1 && $$5.nextBoolean()) {
/* 208 */         $$8 = $$7;
/*     */       }
/*     */     } 
/*     */     
/* 212 */     if ($$8 != null) {
/* 213 */       for (Tuple<Rotation, BlockPos> $$11 : EndCityPieces.TOWER_BRIDGES) {
/* 214 */         if ($$5.nextBoolean()) {
/*     */           
/* 216 */           EndCityPieces.EndCityPiece $$12 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, (BlockPos)$$11.getB(), "bridge_end", $$6.getRotated((Rotation)$$11.getA()), true));
/* 217 */           EndCityPieces.recursiveChildren($$0, EndCityPieces.TOWER_BRIDGE_GENERATOR, $$1 + 1, $$12, null, $$4, $$5);
/*     */         } 
/*     */       } 
/*     */       
/* 221 */       $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 4, -1), "tower_top", $$6, true));
/*     */     }
/* 223 */     else if ($$1 == 7) {
/* 224 */       $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 4, -1), "tower_top", $$6, true));
/*     */     } else {
/* 226 */       return EndCityPieces.recursiveChildren($$0, EndCityPieces.FAT_TOWER_GENERATOR, $$1 + 1, $$7, null, $$4, $$5);
/*     */     } 
/*     */     
/* 229 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\EndCityPieces$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */