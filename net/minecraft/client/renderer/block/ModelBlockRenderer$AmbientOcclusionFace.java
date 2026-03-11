/*     */ package net.minecraft.client.renderer.block;
/*     */ 
/*     */ import java.util.BitSet;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AmbientOcclusionFace
/*     */ {
/* 378 */   final float[] brightness = new float[4];
/* 379 */   final int[] lightmap = new int[4];
/*     */ 
/*     */   
/*     */   public void calculate(BlockAndTintGetter $$0, BlockState $$1, BlockPos $$2, Direction $$3, float[] $$4, BitSet $$5, boolean $$6) {
/*     */     float $$34, $$39, $$44, $$49;
/*     */     int $$35, $$40, $$45, $$50;
/* 385 */     BlockPos $$7 = $$5.get(0) ? $$2.relative($$3) : $$2;
/* 386 */     ModelBlockRenderer.AdjacencyInfo $$8 = ModelBlockRenderer.AdjacencyInfo.fromFacing($$3);
/*     */     
/* 388 */     BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
/*     */     
/* 390 */     ModelBlockRenderer.Cache $$10 = ModelBlockRenderer.CACHE.get();
/*     */     
/* 392 */     $$9.setWithOffset((Vec3i)$$7, $$8.corners[0]);
/* 393 */     BlockState $$11 = $$0.getBlockState((BlockPos)$$9);
/* 394 */     int $$12 = $$10.getLightColor($$11, $$0, (BlockPos)$$9);
/* 395 */     float $$13 = $$10.getShadeBrightness($$11, $$0, (BlockPos)$$9);
/*     */     
/* 397 */     $$9.setWithOffset((Vec3i)$$7, $$8.corners[1]);
/* 398 */     BlockState $$14 = $$0.getBlockState((BlockPos)$$9);
/* 399 */     int $$15 = $$10.getLightColor($$14, $$0, (BlockPos)$$9);
/* 400 */     float $$16 = $$10.getShadeBrightness($$14, $$0, (BlockPos)$$9);
/*     */     
/* 402 */     $$9.setWithOffset((Vec3i)$$7, $$8.corners[2]);
/* 403 */     BlockState $$17 = $$0.getBlockState((BlockPos)$$9);
/* 404 */     int $$18 = $$10.getLightColor($$17, $$0, (BlockPos)$$9);
/* 405 */     float $$19 = $$10.getShadeBrightness($$17, $$0, (BlockPos)$$9);
/*     */     
/* 407 */     $$9.setWithOffset((Vec3i)$$7, $$8.corners[3]);
/* 408 */     BlockState $$20 = $$0.getBlockState((BlockPos)$$9);
/* 409 */     int $$21 = $$10.getLightColor($$20, $$0, (BlockPos)$$9);
/* 410 */     float $$22 = $$10.getShadeBrightness($$20, $$0, (BlockPos)$$9);
/*     */     
/* 412 */     BlockState $$23 = $$0.getBlockState((BlockPos)$$9.setWithOffset((Vec3i)$$7, $$8.corners[0]).move($$3));
/* 413 */     boolean $$24 = (!$$23.isViewBlocking((BlockGetter)$$0, (BlockPos)$$9) || $$23.getLightBlock((BlockGetter)$$0, (BlockPos)$$9) == 0);
/* 414 */     BlockState $$25 = $$0.getBlockState((BlockPos)$$9.setWithOffset((Vec3i)$$7, $$8.corners[1]).move($$3));
/* 415 */     boolean $$26 = (!$$25.isViewBlocking((BlockGetter)$$0, (BlockPos)$$9) || $$25.getLightBlock((BlockGetter)$$0, (BlockPos)$$9) == 0);
/* 416 */     BlockState $$27 = $$0.getBlockState((BlockPos)$$9.setWithOffset((Vec3i)$$7, $$8.corners[2]).move($$3));
/* 417 */     boolean $$28 = (!$$27.isViewBlocking((BlockGetter)$$0, (BlockPos)$$9) || $$27.getLightBlock((BlockGetter)$$0, (BlockPos)$$9) == 0);
/* 418 */     BlockState $$29 = $$0.getBlockState((BlockPos)$$9.setWithOffset((Vec3i)$$7, $$8.corners[3]).move($$3));
/* 419 */     boolean $$30 = (!$$29.isViewBlocking((BlockGetter)$$0, (BlockPos)$$9) || $$29.getLightBlock((BlockGetter)$$0, (BlockPos)$$9) == 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 430 */     if ($$28 || $$24) {
/* 431 */       $$9.setWithOffset((Vec3i)$$7, $$8.corners[0]).move($$8.corners[2]);
/* 432 */       BlockState $$31 = $$0.getBlockState((BlockPos)$$9);
/* 433 */       float $$32 = $$10.getShadeBrightness($$31, $$0, (BlockPos)$$9);
/* 434 */       int $$33 = $$10.getLightColor($$31, $$0, (BlockPos)$$9);
/*     */     } else {
/* 436 */       $$34 = $$13;
/* 437 */       $$35 = $$12;
/*     */     } 
/* 439 */     if ($$30 || $$24) {
/* 440 */       $$9.setWithOffset((Vec3i)$$7, $$8.corners[0]).move($$8.corners[3]);
/* 441 */       BlockState $$36 = $$0.getBlockState((BlockPos)$$9);
/* 442 */       float $$37 = $$10.getShadeBrightness($$36, $$0, (BlockPos)$$9);
/* 443 */       int $$38 = $$10.getLightColor($$36, $$0, (BlockPos)$$9);
/*     */     } else {
/* 445 */       $$39 = $$13;
/* 446 */       $$40 = $$12;
/*     */     } 
/* 448 */     if ($$28 || $$26) {
/* 449 */       $$9.setWithOffset((Vec3i)$$7, $$8.corners[1]).move($$8.corners[2]);
/* 450 */       BlockState $$41 = $$0.getBlockState((BlockPos)$$9);
/* 451 */       float $$42 = $$10.getShadeBrightness($$41, $$0, (BlockPos)$$9);
/* 452 */       int $$43 = $$10.getLightColor($$41, $$0, (BlockPos)$$9);
/*     */     } else {
/* 454 */       $$44 = $$13;
/* 455 */       $$45 = $$12;
/*     */     } 
/* 457 */     if ($$30 || $$26) {
/* 458 */       $$9.setWithOffset((Vec3i)$$7, $$8.corners[1]).move($$8.corners[3]);
/* 459 */       BlockState $$46 = $$0.getBlockState((BlockPos)$$9);
/* 460 */       float $$47 = $$10.getShadeBrightness($$46, $$0, (BlockPos)$$9);
/* 461 */       int $$48 = $$10.getLightColor($$46, $$0, (BlockPos)$$9);
/*     */     } else {
/* 463 */       $$49 = $$13;
/* 464 */       $$50 = $$12;
/*     */     } 
/*     */     
/* 467 */     int $$51 = $$10.getLightColor($$1, $$0, $$2);
/* 468 */     $$9.setWithOffset((Vec3i)$$2, $$3);
/* 469 */     BlockState $$52 = $$0.getBlockState((BlockPos)$$9);
/* 470 */     if ($$5.get(0) || !$$52.isSolidRender((BlockGetter)$$0, (BlockPos)$$9)) {
/* 471 */       $$51 = $$10.getLightColor($$52, $$0, (BlockPos)$$9);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 476 */     float $$53 = $$5.get(0) ? $$10.getShadeBrightness($$0.getBlockState($$7), $$0, $$7) : $$10.getShadeBrightness($$0.getBlockState($$2), $$0, $$2);
/*     */     
/* 478 */     ModelBlockRenderer.AmbientVertexRemap $$54 = ModelBlockRenderer.AmbientVertexRemap.fromFacing($$3);
/*     */     
/* 480 */     if (!$$5.get(1) || !$$8.doNonCubicWeight) {
/* 481 */       float $$55 = ($$22 + $$13 + $$39 + $$53) * 0.25F;
/* 482 */       float $$56 = ($$19 + $$13 + $$34 + $$53) * 0.25F;
/* 483 */       float $$57 = ($$19 + $$16 + $$44 + $$53) * 0.25F;
/* 484 */       float $$58 = ($$22 + $$16 + $$49 + $$53) * 0.25F;
/*     */       
/* 486 */       this.lightmap[$$54.vert0] = blend($$21, $$12, $$40, $$51);
/* 487 */       this.lightmap[$$54.vert1] = blend($$18, $$12, $$35, $$51);
/* 488 */       this.lightmap[$$54.vert2] = blend($$18, $$15, $$45, $$51);
/* 489 */       this.lightmap[$$54.vert3] = blend($$21, $$15, $$50, $$51);
/*     */       
/* 491 */       this.brightness[$$54.vert0] = $$55;
/* 492 */       this.brightness[$$54.vert1] = $$56;
/* 493 */       this.brightness[$$54.vert2] = $$57;
/* 494 */       this.brightness[$$54.vert3] = $$58;
/*     */     } else {
/* 496 */       float $$59 = ($$22 + $$13 + $$39 + $$53) * 0.25F;
/* 497 */       float $$60 = ($$19 + $$13 + $$34 + $$53) * 0.25F;
/* 498 */       float $$61 = ($$19 + $$16 + $$44 + $$53) * 0.25F;
/* 499 */       float $$62 = ($$22 + $$16 + $$49 + $$53) * 0.25F;
/*     */       
/* 501 */       float $$63 = $$4[($$8.vert0Weights[0]).shape] * $$4[($$8.vert0Weights[1]).shape];
/* 502 */       float $$64 = $$4[($$8.vert0Weights[2]).shape] * $$4[($$8.vert0Weights[3]).shape];
/* 503 */       float $$65 = $$4[($$8.vert0Weights[4]).shape] * $$4[($$8.vert0Weights[5]).shape];
/* 504 */       float $$66 = $$4[($$8.vert0Weights[6]).shape] * $$4[($$8.vert0Weights[7]).shape];
/*     */       
/* 506 */       float $$67 = $$4[($$8.vert1Weights[0]).shape] * $$4[($$8.vert1Weights[1]).shape];
/* 507 */       float $$68 = $$4[($$8.vert1Weights[2]).shape] * $$4[($$8.vert1Weights[3]).shape];
/* 508 */       float $$69 = $$4[($$8.vert1Weights[4]).shape] * $$4[($$8.vert1Weights[5]).shape];
/* 509 */       float $$70 = $$4[($$8.vert1Weights[6]).shape] * $$4[($$8.vert1Weights[7]).shape];
/*     */       
/* 511 */       float $$71 = $$4[($$8.vert2Weights[0]).shape] * $$4[($$8.vert2Weights[1]).shape];
/* 512 */       float $$72 = $$4[($$8.vert2Weights[2]).shape] * $$4[($$8.vert2Weights[3]).shape];
/* 513 */       float $$73 = $$4[($$8.vert2Weights[4]).shape] * $$4[($$8.vert2Weights[5]).shape];
/* 514 */       float $$74 = $$4[($$8.vert2Weights[6]).shape] * $$4[($$8.vert2Weights[7]).shape];
/*     */       
/* 516 */       float $$75 = $$4[($$8.vert3Weights[0]).shape] * $$4[($$8.vert3Weights[1]).shape];
/* 517 */       float $$76 = $$4[($$8.vert3Weights[2]).shape] * $$4[($$8.vert3Weights[3]).shape];
/* 518 */       float $$77 = $$4[($$8.vert3Weights[4]).shape] * $$4[($$8.vert3Weights[5]).shape];
/* 519 */       float $$78 = $$4[($$8.vert3Weights[6]).shape] * $$4[($$8.vert3Weights[7]).shape];
/*     */       
/* 521 */       this.brightness[$$54.vert0] = $$59 * $$63 + $$60 * $$64 + $$61 * $$65 + $$62 * $$66;
/* 522 */       this.brightness[$$54.vert1] = $$59 * $$67 + $$60 * $$68 + $$61 * $$69 + $$62 * $$70;
/* 523 */       this.brightness[$$54.vert2] = $$59 * $$71 + $$60 * $$72 + $$61 * $$73 + $$62 * $$74;
/* 524 */       this.brightness[$$54.vert3] = $$59 * $$75 + $$60 * $$76 + $$61 * $$77 + $$62 * $$78;
/*     */       
/* 526 */       int $$79 = blend($$21, $$12, $$40, $$51);
/* 527 */       int $$80 = blend($$18, $$12, $$35, $$51);
/* 528 */       int $$81 = blend($$18, $$15, $$45, $$51);
/* 529 */       int $$82 = blend($$21, $$15, $$50, $$51);
/*     */       
/* 531 */       this.lightmap[$$54.vert0] = blend($$79, $$80, $$81, $$82, $$63, $$64, $$65, $$66);
/* 532 */       this.lightmap[$$54.vert1] = blend($$79, $$80, $$81, $$82, $$67, $$68, $$69, $$70);
/* 533 */       this.lightmap[$$54.vert2] = blend($$79, $$80, $$81, $$82, $$71, $$72, $$73, $$74);
/* 534 */       this.lightmap[$$54.vert3] = blend($$79, $$80, $$81, $$82, $$75, $$76, $$77, $$78);
/*     */     } 
/*     */     
/* 537 */     float $$83 = $$0.getShade($$3, $$6);
/* 538 */     for (int $$84 = 0; $$84 < this.brightness.length; $$84++) {
/* 539 */       this.brightness[$$84] = this.brightness[$$84] * $$83;
/*     */     }
/*     */   }
/*     */   
/*     */   private int blend(int $$0, int $$1, int $$2, int $$3) {
/* 544 */     if ($$0 == 0) {
/* 545 */       $$0 = $$3;
/*     */     }
/* 547 */     if ($$1 == 0) {
/* 548 */       $$1 = $$3;
/*     */     }
/* 550 */     if ($$2 == 0) {
/* 551 */       $$2 = $$3;
/*     */     }
/* 553 */     return $$0 + $$1 + $$2 + $$3 >> 2 & 0xFF00FF;
/*     */   }
/*     */   
/*     */   private int blend(int $$0, int $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 557 */     int $$8 = (int)(($$0 >> 16 & 0xFF) * $$4 + ($$1 >> 16 & 0xFF) * $$5 + ($$2 >> 16 & 0xFF) * $$6 + ($$3 >> 16 & 0xFF) * $$7) & 0xFF;
/* 558 */     int $$9 = (int)(($$0 & 0xFF) * $$4 + ($$1 & 0xFF) * $$5 + ($$2 & 0xFF) * $$6 + ($$3 & 0xFF) * $$7) & 0xFF;
/* 559 */     return $$8 << 16 | $$9;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\ModelBlockRenderer$AmbientOcclusionFace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */