/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.RandomSource;
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
/*     */ class null
/*     */   implements EndCityPieces.SectionGenerator
/*     */ {
/*     */   public void init() {}
/*     */   
/*     */   public boolean generate(StructureTemplateManager $$0, int $$1, EndCityPieces.EndCityPiece $$2, BlockPos $$3, List<StructurePiece> $$4, RandomSource $$5) {
/* 157 */     if ($$1 > 8) {
/* 158 */       return false;
/*     */     }
/*     */     
/* 161 */     Rotation $$6 = $$2.placeSettings().getRotation();
/* 162 */     EndCityPieces.EndCityPiece $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$2, $$3, "base_floor", $$6, true));
/*     */     
/* 164 */     int $$8 = $$5.nextInt(3);
/* 165 */     if ($$8 == 0) {
/* 166 */       $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 4, -1), "base_roof", $$6, true));
/* 167 */     } else if ($$8 == 1) {
/* 168 */       $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 0, -1), "second_floor_2", $$6, false));
/* 169 */       $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 8, -1), "second_roof", $$6, false));
/*     */       
/* 171 */       EndCityPieces.recursiveChildren($$0, EndCityPieces.TOWER_GENERATOR, $$1 + 1, $$7, null, $$4, $$5);
/* 172 */     } else if ($$8 == 2) {
/* 173 */       $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 0, -1), "second_floor_2", $$6, false));
/* 174 */       $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 4, -1), "third_floor_2", $$6, false));
/* 175 */       $$7 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$7, new BlockPos(-1, 8, -1), "third_roof", $$6, true));
/*     */       
/* 177 */       EndCityPieces.recursiveChildren($$0, EndCityPieces.TOWER_GENERATOR, $$1 + 1, $$7, null, $$4, $$5);
/*     */     } 
/* 179 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\EndCityPieces$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */