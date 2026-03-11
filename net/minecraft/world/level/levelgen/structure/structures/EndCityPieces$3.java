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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   public boolean shipCreated;
/*     */   
/*     */   public void init() {
/* 238 */     this.shipCreated = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(StructureTemplateManager $$0, int $$1, EndCityPieces.EndCityPiece $$2, BlockPos $$3, List<StructurePiece> $$4, RandomSource $$5) {
/* 243 */     Rotation $$6 = $$2.placeSettings().getRotation();
/* 244 */     int $$7 = $$5.nextInt(4) + 1;
/*     */     
/* 246 */     EndCityPieces.EndCityPiece $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$2, new BlockPos(0, 0, -4), "bridge_piece", $$6, true));
/* 247 */     $$8.setGenDepth(-1);
/* 248 */     int $$9 = 0;
/* 249 */     for (int $$10 = 0; $$10 < $$7; $$10++) {
/* 250 */       if ($$5.nextBoolean()) {
/* 251 */         $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(0, $$9, -4), "bridge_piece", $$6, true));
/* 252 */         $$9 = 0;
/*     */       } else {
/* 254 */         if ($$5.nextBoolean()) {
/* 255 */           $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(0, $$9, -4), "bridge_steep_stairs", $$6, true));
/*     */         } else {
/* 257 */           $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(0, $$9, -8), "bridge_gentle_stairs", $$6, true));
/*     */         } 
/* 259 */         $$9 = 4;
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     if (this.shipCreated || $$5.nextInt(10 - $$1) != 0) {
/* 264 */       if (!EndCityPieces.recursiveChildren($$0, EndCityPieces.HOUSE_TOWER_GENERATOR, $$1 + 1, $$8, new BlockPos(-3, $$9 + 1, -11), $$4, $$5)) {
/* 265 */         return false;
/*     */       }
/*     */     } else {
/*     */       
/* 269 */       EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(-8 + $$5.nextInt(8), $$9, -70 + $$5.nextInt(10)), "ship", $$6, true));
/* 270 */       this.shipCreated = true;
/*     */     } 
/*     */ 
/*     */     
/* 274 */     $$8 = EndCityPieces.addHelper($$4, EndCityPieces.addPiece($$0, $$8, new BlockPos(4, $$9, 0), "bridge_end", $$6.getRotated(Rotation.CLOCKWISE_180), true));
/* 275 */     $$8.setGenDepth(-1);
/*     */     
/* 277 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\EndCityPieces$3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */