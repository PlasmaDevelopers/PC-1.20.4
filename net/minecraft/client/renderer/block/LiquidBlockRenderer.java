/*     */ package net.minecraft.client.renderer.block;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BiomeColors;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.ModelBakery;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LiquidBlockRenderer
/*     */ {
/*     */   private static final float MAX_FLUID_HEIGHT = 0.8888889F;
/*  29 */   private final TextureAtlasSprite[] lavaIcons = new TextureAtlasSprite[2];
/*  30 */   private final TextureAtlasSprite[] waterIcons = new TextureAtlasSprite[2];
/*     */   private TextureAtlasSprite waterOverlay;
/*     */   
/*     */   protected void setupSprites() {
/*  34 */     this.lavaIcons[0] = Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(Blocks.LAVA.defaultBlockState()).getParticleIcon();
/*  35 */     this.lavaIcons[1] = ModelBakery.LAVA_FLOW.sprite();
/*  36 */     this.waterIcons[0] = Minecraft.getInstance().getModelManager().getBlockModelShaper().getBlockModel(Blocks.WATER.defaultBlockState()).getParticleIcon();
/*  37 */     this.waterIcons[1] = ModelBakery.WATER_FLOW.sprite();
/*  38 */     this.waterOverlay = ModelBakery.WATER_OVERLAY.sprite();
/*     */   }
/*     */   
/*     */   private static boolean isNeighborSameFluid(FluidState $$0, FluidState $$1) {
/*  42 */     return $$1.getType().isSame($$0.getType());
/*     */   }
/*     */   
/*     */   private static boolean isFaceOccludedByState(BlockGetter $$0, Direction $$1, float $$2, BlockPos $$3, BlockState $$4) {
/*  46 */     if ($$4.canOcclude()) {
/*  47 */       VoxelShape $$5 = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, $$2, 1.0D);
/*  48 */       VoxelShape $$6 = $$4.getOcclusionShape($$0, $$3);
/*  49 */       return Shapes.blockOccudes($$5, $$6, $$1);
/*     */     } 
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean isFaceOccludedByNeighbor(BlockGetter $$0, BlockPos $$1, Direction $$2, float $$3, BlockState $$4) {
/*  55 */     return isFaceOccludedByState($$0, $$2, $$3, $$1.relative($$2), $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isFaceOccludedBySelf(BlockGetter $$0, BlockPos $$1, BlockState $$2, Direction $$3) {
/*  61 */     return isFaceOccludedByState($$0, $$3.getOpposite(), 1.0F, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static boolean shouldRenderFace(BlockAndTintGetter $$0, BlockPos $$1, FluidState $$2, BlockState $$3, Direction $$4, FluidState $$5) {
/*  65 */     return (!isFaceOccludedBySelf((BlockGetter)$$0, $$1, $$3, $$4) && !isNeighborSameFluid($$2, $$5));
/*     */   }
/*     */   public void tesselate(BlockAndTintGetter $$0, BlockPos $$1, VertexConsumer $$2, BlockState $$3, FluidState $$4) {
/*     */     float $$43, $$44, $$45, $$46;
/*  69 */     boolean $$5 = $$4.is(FluidTags.LAVA);
/*  70 */     TextureAtlasSprite[] $$6 = $$5 ? this.lavaIcons : this.waterIcons;
/*     */     
/*  72 */     int $$7 = $$5 ? 16777215 : BiomeColors.getAverageWaterColor($$0, $$1);
/*  73 */     float $$8 = ($$7 >> 16 & 0xFF) / 255.0F;
/*  74 */     float $$9 = ($$7 >> 8 & 0xFF) / 255.0F;
/*  75 */     float $$10 = ($$7 & 0xFF) / 255.0F;
/*     */     
/*  77 */     BlockState $$11 = $$0.getBlockState($$1.relative(Direction.DOWN));
/*  78 */     FluidState $$12 = $$11.getFluidState();
/*  79 */     BlockState $$13 = $$0.getBlockState($$1.relative(Direction.UP));
/*  80 */     FluidState $$14 = $$13.getFluidState();
/*  81 */     BlockState $$15 = $$0.getBlockState($$1.relative(Direction.NORTH));
/*  82 */     FluidState $$16 = $$15.getFluidState();
/*  83 */     BlockState $$17 = $$0.getBlockState($$1.relative(Direction.SOUTH));
/*  84 */     FluidState $$18 = $$17.getFluidState();
/*  85 */     BlockState $$19 = $$0.getBlockState($$1.relative(Direction.WEST));
/*  86 */     FluidState $$20 = $$19.getFluidState();
/*  87 */     BlockState $$21 = $$0.getBlockState($$1.relative(Direction.EAST));
/*  88 */     FluidState $$22 = $$21.getFluidState();
/*     */     
/*  90 */     boolean $$23 = !isNeighborSameFluid($$4, $$14);
/*  91 */     boolean $$24 = (shouldRenderFace($$0, $$1, $$4, $$3, Direction.DOWN, $$12) && !isFaceOccludedByNeighbor((BlockGetter)$$0, $$1, Direction.DOWN, 0.8888889F, $$11));
/*     */     
/*  93 */     boolean $$25 = shouldRenderFace($$0, $$1, $$4, $$3, Direction.NORTH, $$16);
/*  94 */     boolean $$26 = shouldRenderFace($$0, $$1, $$4, $$3, Direction.SOUTH, $$18);
/*  95 */     boolean $$27 = shouldRenderFace($$0, $$1, $$4, $$3, Direction.WEST, $$20);
/*  96 */     boolean $$28 = shouldRenderFace($$0, $$1, $$4, $$3, Direction.EAST, $$22);
/*     */     
/*  98 */     if (!$$23 && !$$24 && !$$28 && !$$27 && !$$25 && !$$26) {
/*     */       return;
/*     */     }
/*     */     
/* 102 */     float $$29 = $$0.getShade(Direction.DOWN, true);
/* 103 */     float $$30 = $$0.getShade(Direction.UP, true);
/* 104 */     float $$31 = $$0.getShade(Direction.NORTH, true);
/* 105 */     float $$32 = $$0.getShade(Direction.WEST, true);
/*     */     
/* 107 */     Fluid $$33 = $$4.getType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     float $$34 = getHeight($$0, $$33, $$1, $$3, $$4);
/* 115 */     if ($$34 >= 1.0F) {
/* 116 */       float $$35 = 1.0F;
/* 117 */       float $$36 = 1.0F;
/* 118 */       float $$37 = 1.0F;
/* 119 */       float $$38 = 1.0F;
/*     */     } else {
/* 121 */       float $$39 = getHeight($$0, $$33, $$1.north(), $$15, $$16);
/* 122 */       float $$40 = getHeight($$0, $$33, $$1.south(), $$17, $$18);
/* 123 */       float $$41 = getHeight($$0, $$33, $$1.east(), $$21, $$22);
/* 124 */       float $$42 = getHeight($$0, $$33, $$1.west(), $$19, $$20);
/*     */       
/* 126 */       $$43 = calculateAverageHeight($$0, $$33, $$34, $$39, $$41, $$1.relative(Direction.NORTH).relative(Direction.EAST));
/* 127 */       $$44 = calculateAverageHeight($$0, $$33, $$34, $$39, $$42, $$1.relative(Direction.NORTH).relative(Direction.WEST));
/* 128 */       $$45 = calculateAverageHeight($$0, $$33, $$34, $$40, $$41, $$1.relative(Direction.SOUTH).relative(Direction.EAST));
/* 129 */       $$46 = calculateAverageHeight($$0, $$33, $$34, $$40, $$42, $$1.relative(Direction.SOUTH).relative(Direction.WEST));
/*     */     } 
/*     */     
/* 132 */     double $$47 = ($$1.getX() & 0xF);
/* 133 */     double $$48 = ($$1.getY() & 0xF);
/* 134 */     double $$49 = ($$1.getZ() & 0xF);
/*     */     
/* 136 */     float $$50 = 0.001F;
/* 137 */     float $$51 = $$24 ? 0.001F : 0.0F;
/*     */     
/* 139 */     if ($$23 && !isFaceOccludedByNeighbor((BlockGetter)$$0, $$1, Direction.UP, Math.min(Math.min($$44, $$46), Math.min($$45, $$43)), $$13)) {
/*     */       
/* 141 */       $$44 -= 0.001F;
/* 142 */       $$46 -= 0.001F;
/* 143 */       $$45 -= 0.001F;
/* 144 */       $$43 -= 0.001F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       Vec3 $$52 = $$4.getFlow((BlockGetter)$$0, $$1);
/* 156 */       if ($$52.x == 0.0D && $$52.z == 0.0D) {
/* 157 */         TextureAtlasSprite $$53 = $$6[0];
/* 158 */         float $$54 = $$53.getU(0.0F);
/* 159 */         float $$55 = $$53.getV(0.0F);
/* 160 */         float $$56 = $$54;
/* 161 */         float $$57 = $$53.getV(1.0F);
/* 162 */         float $$58 = $$53.getU(1.0F);
/* 163 */         float $$59 = $$57;
/* 164 */         float $$60 = $$58;
/* 165 */         float $$61 = $$55;
/*     */       } else {
/* 167 */         TextureAtlasSprite $$62 = $$6[1];
/* 168 */         float $$63 = (float)Mth.atan2($$52.z, $$52.x) - 1.5707964F;
/* 169 */         float $$64 = Mth.sin($$63) * 0.25F;
/* 170 */         float $$65 = Mth.cos($$63) * 0.25F;
/* 171 */         float $$66 = 0.5F;
/* 172 */         $$67 = $$62.getU(0.5F + -$$65 - $$64);
/* 173 */         $$68 = $$62.getV(0.5F + -$$65 + $$64);
/* 174 */         $$69 = $$62.getU(0.5F + -$$65 + $$64);
/* 175 */         $$70 = $$62.getV(0.5F + $$65 + $$64);
/* 176 */         $$71 = $$62.getU(0.5F + $$65 + $$64);
/* 177 */         $$72 = $$62.getV(0.5F + $$65 - $$64);
/* 178 */         $$73 = $$62.getU(0.5F + $$65 - $$64);
/* 179 */         $$74 = $$62.getV(0.5F + -$$65 - $$64);
/*     */       } 
/*     */       
/* 182 */       float $$75 = ($$67 + $$69 + $$71 + $$73) / 4.0F;
/* 183 */       float $$76 = ($$68 + $$70 + $$72 + $$74) / 4.0F;
/*     */       
/* 185 */       float $$77 = $$6[0].uvShrinkRatio();
/*     */       
/* 187 */       float $$67 = Mth.lerp($$77, $$67, $$75);
/* 188 */       float $$69 = Mth.lerp($$77, $$69, $$75);
/* 189 */       float $$71 = Mth.lerp($$77, $$71, $$75);
/* 190 */       float $$73 = Mth.lerp($$77, $$73, $$75);
/* 191 */       float $$68 = Mth.lerp($$77, $$68, $$76);
/* 192 */       float $$70 = Mth.lerp($$77, $$70, $$76);
/* 193 */       float $$72 = Mth.lerp($$77, $$72, $$76);
/* 194 */       float $$74 = Mth.lerp($$77, $$74, $$76);
/*     */       
/* 196 */       int $$78 = getLightColor($$0, $$1);
/* 197 */       float $$79 = $$30 * $$8;
/* 198 */       float $$80 = $$30 * $$9;
/* 199 */       float $$81 = $$30 * $$10;
/*     */       
/* 201 */       vertex($$2, $$47 + 0.0D, $$48 + $$44, $$49 + 0.0D, $$79, $$80, $$81, $$67, $$68, $$78);
/* 202 */       vertex($$2, $$47 + 0.0D, $$48 + $$46, $$49 + 1.0D, $$79, $$80, $$81, $$69, $$70, $$78);
/* 203 */       vertex($$2, $$47 + 1.0D, $$48 + $$45, $$49 + 1.0D, $$79, $$80, $$81, $$71, $$72, $$78);
/* 204 */       vertex($$2, $$47 + 1.0D, $$48 + $$43, $$49 + 0.0D, $$79, $$80, $$81, $$73, $$74, $$78);
/*     */       
/* 206 */       if ($$4.shouldRenderBackwardUpFace((BlockGetter)$$0, $$1.above())) {
/*     */         
/* 208 */         vertex($$2, $$47 + 0.0D, $$48 + $$44, $$49 + 0.0D, $$79, $$80, $$81, $$67, $$68, $$78);
/* 209 */         vertex($$2, $$47 + 1.0D, $$48 + $$43, $$49 + 0.0D, $$79, $$80, $$81, $$73, $$74, $$78);
/* 210 */         vertex($$2, $$47 + 1.0D, $$48 + $$45, $$49 + 1.0D, $$79, $$80, $$81, $$71, $$72, $$78);
/* 211 */         vertex($$2, $$47 + 0.0D, $$48 + $$46, $$49 + 1.0D, $$79, $$80, $$81, $$69, $$70, $$78);
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     if ($$24) {
/* 216 */       float $$82 = $$6[0].getU0();
/* 217 */       float $$83 = $$6[0].getU1();
/* 218 */       float $$84 = $$6[0].getV0();
/* 219 */       float $$85 = $$6[0].getV1();
/*     */       
/* 221 */       int $$86 = getLightColor($$0, $$1.below());
/* 222 */       float $$87 = $$29 * $$8;
/* 223 */       float $$88 = $$29 * $$9;
/* 224 */       float $$89 = $$29 * $$10;
/*     */       
/* 226 */       vertex($$2, $$47, $$48 + $$51, $$49 + 1.0D, $$87, $$88, $$89, $$82, $$85, $$86);
/* 227 */       vertex($$2, $$47, $$48 + $$51, $$49, $$87, $$88, $$89, $$82, $$84, $$86);
/* 228 */       vertex($$2, $$47 + 1.0D, $$48 + $$51, $$49, $$87, $$88, $$89, $$83, $$84, $$86);
/* 229 */       vertex($$2, $$47 + 1.0D, $$48 + $$51, $$49 + 1.0D, $$87, $$88, $$89, $$83, $$85, $$86);
/*     */     } 
/*     */     
/* 232 */     int $$90 = getLightColor($$0, $$1);
/*     */     
/* 234 */     for (Direction $$91 : Direction.Plane.HORIZONTAL) {
/*     */       float $$92; float $$99; float $$106; float $$113; float $$93; float $$100; float $$107; float $$114; double $$94; double $$101; double $$108; double $$115; double $$96; double $$103; double $$110; double $$117; double $$95; double $$102; double $$109; double $$116; double $$97;
/*     */       double $$104;
/*     */       double $$111;
/*     */       double $$118;
/*     */       boolean $$98;
/*     */       boolean $$105;
/*     */       boolean $$112;
/*     */       boolean $$119;
/* 243 */       switch ($$91) {
/*     */         case NORTH:
/* 245 */           $$92 = $$44;
/* 246 */           $$93 = $$43;
/* 247 */           $$94 = $$47;
/* 248 */           $$95 = $$47 + 1.0D;
/* 249 */           $$96 = $$49 + 0.0010000000474974513D;
/* 250 */           $$97 = $$49 + 0.0010000000474974513D;
/* 251 */           $$98 = $$25;
/*     */           break;
/*     */         case SOUTH:
/* 254 */           $$99 = $$45;
/* 255 */           $$100 = $$46;
/* 256 */           $$101 = $$47 + 1.0D;
/* 257 */           $$102 = $$47;
/* 258 */           $$103 = $$49 + 1.0D - 0.0010000000474974513D;
/* 259 */           $$104 = $$49 + 1.0D - 0.0010000000474974513D;
/* 260 */           $$105 = $$26;
/*     */           break;
/*     */         case WEST:
/* 263 */           $$106 = $$46;
/* 264 */           $$107 = $$44;
/* 265 */           $$108 = $$47 + 0.0010000000474974513D;
/* 266 */           $$109 = $$47 + 0.0010000000474974513D;
/* 267 */           $$110 = $$49 + 1.0D;
/* 268 */           $$111 = $$49;
/* 269 */           $$112 = $$27;
/*     */           break;
/*     */         default:
/* 272 */           $$113 = $$43;
/* 273 */           $$114 = $$45;
/* 274 */           $$115 = $$47 + 1.0D - 0.0010000000474974513D;
/* 275 */           $$116 = $$47 + 1.0D - 0.0010000000474974513D;
/* 276 */           $$117 = $$49;
/* 277 */           $$118 = $$49 + 1.0D;
/* 278 */           $$119 = $$28;
/*     */           break;
/*     */       } 
/*     */       
/* 282 */       if ($$119 && !isFaceOccludedByNeighbor((BlockGetter)$$0, $$1, $$91, Math.max($$113, $$114), $$0.getBlockState($$1.relative($$91)))) {
/*     */         
/* 284 */         BlockPos $$120 = $$1.relative($$91);
/*     */         
/* 286 */         TextureAtlasSprite $$121 = $$6[1];
/*     */         
/* 288 */         if (!$$5) {
/* 289 */           Block $$122 = $$0.getBlockState($$120).getBlock();
/* 290 */           if ($$122 instanceof net.minecraft.world.level.block.HalfTransparentBlock || $$122 instanceof net.minecraft.world.level.block.LeavesBlock) {
/* 291 */             $$121 = this.waterOverlay;
/*     */           }
/*     */         } 
/*     */         
/* 295 */         float $$123 = $$121.getU(0.0F);
/* 296 */         float $$124 = $$121.getU(0.5F);
/*     */         
/* 298 */         float $$125 = $$121.getV((1.0F - $$113) * 0.5F);
/* 299 */         float $$126 = $$121.getV((1.0F - $$114) * 0.5F);
/* 300 */         float $$127 = $$121.getV(0.5F);
/*     */         
/* 302 */         float $$128 = ($$91.getAxis() == Direction.Axis.Z) ? $$31 : $$32;
/*     */         
/* 304 */         float $$129 = $$30 * $$128 * $$8;
/* 305 */         float $$130 = $$30 * $$128 * $$9;
/* 306 */         float $$131 = $$30 * $$128 * $$10;
/*     */         
/* 308 */         vertex($$2, $$115, $$48 + $$113, $$117, $$129, $$130, $$131, $$123, $$125, $$90);
/* 309 */         vertex($$2, $$116, $$48 + $$114, $$118, $$129, $$130, $$131, $$124, $$126, $$90);
/* 310 */         vertex($$2, $$116, $$48 + $$51, $$118, $$129, $$130, $$131, $$124, $$127, $$90);
/* 311 */         vertex($$2, $$115, $$48 + $$51, $$117, $$129, $$130, $$131, $$123, $$127, $$90);
/*     */         
/* 313 */         if ($$121 != this.waterOverlay) {
/* 314 */           vertex($$2, $$115, $$48 + $$51, $$117, $$129, $$130, $$131, $$123, $$127, $$90);
/* 315 */           vertex($$2, $$116, $$48 + $$51, $$118, $$129, $$130, $$131, $$124, $$127, $$90);
/* 316 */           vertex($$2, $$116, $$48 + $$114, $$118, $$129, $$130, $$131, $$124, $$126, $$90);
/* 317 */           vertex($$2, $$115, $$48 + $$113, $$117, $$129, $$130, $$131, $$123, $$125, $$90);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private float calculateAverageHeight(BlockAndTintGetter $$0, Fluid $$1, float $$2, float $$3, float $$4, BlockPos $$5) {
/* 324 */     if ($$4 >= 1.0F || $$3 >= 1.0F) {
/* 325 */       return 1.0F;
/*     */     }
/* 327 */     float[] $$6 = new float[2];
/* 328 */     if ($$4 > 0.0F || $$3 > 0.0F) {
/* 329 */       float $$7 = getHeight($$0, $$1, $$5);
/* 330 */       if ($$7 >= 1.0F) {
/* 331 */         return 1.0F;
/*     */       }
/* 333 */       addWeightedHeight($$6, $$7);
/*     */     } 
/* 335 */     addWeightedHeight($$6, $$2);
/* 336 */     addWeightedHeight($$6, $$4);
/* 337 */     addWeightedHeight($$6, $$3);
/* 338 */     return $$6[0] / $$6[1];
/*     */   }
/*     */   
/*     */   private void addWeightedHeight(float[] $$0, float $$1) {
/* 342 */     if ($$1 >= 0.8F) {
/* 343 */       $$0[0] = $$0[0] + $$1 * 10.0F;
/* 344 */       $$0[1] = $$0[1] + 10.0F;
/* 345 */     } else if ($$1 >= 0.0F) {
/* 346 */       $$0[0] = $$0[0] + $$1;
/* 347 */       $$0[1] = $$0[1] + 1.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getHeight(BlockAndTintGetter $$0, Fluid $$1, BlockPos $$2) {
/* 352 */     BlockState $$3 = $$0.getBlockState($$2);
/* 353 */     return getHeight($$0, $$1, $$2, $$3, $$3.getFluidState());
/*     */   }
/*     */   
/*     */   private float getHeight(BlockAndTintGetter $$0, Fluid $$1, BlockPos $$2, BlockState $$3, FluidState $$4) {
/* 357 */     if ($$1.isSame($$4.getType())) {
/* 358 */       BlockState $$5 = $$0.getBlockState($$2.above());
/* 359 */       if ($$1.isSame($$5.getFluidState().getType())) {
/* 360 */         return 1.0F;
/*     */       }
/* 362 */       return $$4.getOwnHeight();
/* 363 */     }  if (!$$3.isSolid()) {
/* 364 */       return 0.0F;
/*     */     }
/* 366 */     return -1.0F;
/*     */   }
/*     */   
/*     */   private void vertex(VertexConsumer $$0, double $$1, double $$2, double $$3, float $$4, float $$5, float $$6, float $$7, float $$8, int $$9) {
/* 370 */     $$0.vertex($$1, $$2, $$3).color($$4, $$5, $$6, 1.0F).uv($$7, $$8).uv2($$9).normal(0.0F, 1.0F, 0.0F).endVertex();
/*     */   }
/*     */   
/*     */   private int getLightColor(BlockAndTintGetter $$0, BlockPos $$1) {
/* 374 */     int $$2 = LevelRenderer.getLightColor($$0, $$1);
/* 375 */     int $$3 = LevelRenderer.getLightColor($$0, $$1.above());
/*     */     
/* 377 */     int $$4 = $$2 & 0xFF;
/* 378 */     int $$5 = $$3 & 0xFF;
/* 379 */     int $$6 = $$2 >> 16 & 0xFF;
/* 380 */     int $$7 = $$3 >> 16 & 0xFF;
/*     */     
/* 382 */     return (($$4 > $$5) ? $$4 : $$5) | (($$6 > $$7) ? $$6 : $$7) << 16;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\LiquidBlockRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */