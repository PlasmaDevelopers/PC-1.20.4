/*     */ package net.minecraft.client.renderer.block;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
/*     */ import java.util.BitSet;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.color.block.BlockColors;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ModelBlockRenderer {
/*     */   private static final int FACE_CUBIC = 0;
/*     */   private static final int FACE_PARTIAL = 1;
/*  34 */   static final Direction[] DIRECTIONS = Direction.values();
/*     */   private final BlockColors blockColors;
/*     */   private static final int CACHE_SIZE = 100;
/*     */   
/*     */   public ModelBlockRenderer(BlockColors $$0) {
/*  39 */     this.blockColors = $$0;
/*     */   }
/*     */   
/*     */   public void tesselateBlock(BlockAndTintGetter $$0, BakedModel $$1, BlockState $$2, BlockPos $$3, PoseStack $$4, VertexConsumer $$5, boolean $$6, RandomSource $$7, long $$8, int $$9) {
/*  43 */     boolean $$10 = (Minecraft.useAmbientOcclusion() && $$2.getLightEmission() == 0 && $$1.useAmbientOcclusion());
/*  44 */     Vec3 $$11 = $$2.getOffset((BlockGetter)$$0, $$3);
/*  45 */     $$4.translate($$11.x, $$11.y, $$11.z);
/*     */     try {
/*  47 */       if ($$10) {
/*  48 */         tesselateWithAO($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*     */       } else {
/*  50 */         tesselateWithoutAO($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
/*     */       } 
/*  52 */     } catch (Throwable $$12) {
/*  53 */       CrashReport $$13 = CrashReport.forThrowable($$12, "Tesselating block model");
/*  54 */       CrashReportCategory $$14 = $$13.addCategory("Block model being tesselated");
/*     */       
/*  56 */       CrashReportCategory.populateBlockDetails($$14, (LevelHeightAccessor)$$0, $$3, $$2);
/*  57 */       $$14.setDetail("Using AO", Boolean.valueOf($$10));
/*  58 */       throw new ReportedException($$13);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tesselateWithAO(BlockAndTintGetter $$0, BakedModel $$1, BlockState $$2, BlockPos $$3, PoseStack $$4, VertexConsumer $$5, boolean $$6, RandomSource $$7, long $$8, int $$9) {
/*  63 */     float[] $$10 = new float[DIRECTIONS.length * 2];
/*  64 */     BitSet $$11 = new BitSet(3);
/*  65 */     AmbientOcclusionFace $$12 = new AmbientOcclusionFace();
/*     */     
/*  67 */     BlockPos.MutableBlockPos $$13 = $$3.mutable();
/*  68 */     for (Direction $$14 : DIRECTIONS) {
/*  69 */       $$7.setSeed($$8);
/*  70 */       List<BakedQuad> $$15 = $$1.getQuads($$2, $$14, $$7);
/*  71 */       if (!$$15.isEmpty()) {
/*     */ 
/*     */ 
/*     */         
/*  75 */         $$13.setWithOffset((Vec3i)$$3, $$14);
/*  76 */         if (!$$6 || Block.shouldRenderFace($$2, (BlockGetter)$$0, $$3, $$14, (BlockPos)$$13)) {
/*  77 */           renderModelFaceAO($$0, $$2, $$3, $$4, $$5, $$15, $$10, $$11, $$12, $$9);
/*     */         }
/*     */       } 
/*     */     } 
/*  81 */     $$7.setSeed($$8);
/*  82 */     List<BakedQuad> $$16 = $$1.getQuads($$2, null, $$7);
/*  83 */     if (!$$16.isEmpty()) {
/*  84 */       renderModelFaceAO($$0, $$2, $$3, $$4, $$5, $$16, $$10, $$11, $$12, $$9);
/*     */     }
/*     */   }
/*     */   
/*     */   public void tesselateWithoutAO(BlockAndTintGetter $$0, BakedModel $$1, BlockState $$2, BlockPos $$3, PoseStack $$4, VertexConsumer $$5, boolean $$6, RandomSource $$7, long $$8, int $$9) {
/*  89 */     BitSet $$10 = new BitSet(3);
/*  90 */     BlockPos.MutableBlockPos $$11 = $$3.mutable();
/*  91 */     for (Direction $$12 : DIRECTIONS) {
/*  92 */       $$7.setSeed($$8);
/*  93 */       List<BakedQuad> $$13 = $$1.getQuads($$2, $$12, $$7);
/*  94 */       if (!$$13.isEmpty()) {
/*     */ 
/*     */ 
/*     */         
/*  98 */         $$11.setWithOffset((Vec3i)$$3, $$12);
/*  99 */         if (!$$6 || Block.shouldRenderFace($$2, (BlockGetter)$$0, $$3, $$12, (BlockPos)$$11)) {
/*     */ 
/*     */ 
/*     */           
/* 103 */           int $$14 = LevelRenderer.getLightColor($$0, $$2, (BlockPos)$$11);
/*     */           
/* 105 */           renderModelFaceFlat($$0, $$2, $$3, $$14, $$9, false, $$4, $$5, $$13, $$10);
/*     */         } 
/*     */       } 
/* 108 */     }  $$7.setSeed($$8);
/* 109 */     List<BakedQuad> $$15 = $$1.getQuads($$2, null, $$7);
/* 110 */     if (!$$15.isEmpty()) {
/* 111 */       renderModelFaceFlat($$0, $$2, $$3, -1, $$9, true, $$4, $$5, $$15, $$10);
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderModelFaceAO(BlockAndTintGetter $$0, BlockState $$1, BlockPos $$2, PoseStack $$3, VertexConsumer $$4, List<BakedQuad> $$5, float[] $$6, BitSet $$7, AmbientOcclusionFace $$8, int $$9) {
/* 116 */     for (BakedQuad $$10 : $$5) {
/* 117 */       calculateShape($$0, $$1, $$2, $$10.getVertices(), $$10.getDirection(), $$6, $$7);
/* 118 */       $$8.calculate($$0, $$1, $$2, $$10.getDirection(), $$6, $$7, $$10.isShade());
/*     */       
/* 120 */       putQuadData($$0, $$1, $$2, $$4, $$3
/* 121 */           .last(), $$10, $$8.brightness[0], $$8.brightness[1], $$8.brightness[2], $$8.brightness[3], $$8.lightmap[0], $$8.lightmap[1], $$8.lightmap[2], $$8.lightmap[3], $$9);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void putQuadData(BlockAndTintGetter $$0, BlockState $$1, BlockPos $$2, VertexConsumer $$3, PoseStack.Pose $$4, BakedQuad $$5, float $$6, float $$7, float $$8, float $$9, int $$10, int $$11, int $$12, int $$13, int $$14) {
/*     */     float $$19, $$20, $$21;
/* 133 */     if ($$5.isTinted()) {
/* 134 */       int $$15 = this.blockColors.getColor($$1, $$0, $$2, $$5.getTintIndex());
/* 135 */       float $$16 = ($$15 >> 16 & 0xFF) / 255.0F;
/* 136 */       float $$17 = ($$15 >> 8 & 0xFF) / 255.0F;
/* 137 */       float $$18 = ($$15 & 0xFF) / 255.0F;
/*     */     } else {
/* 139 */       $$19 = 1.0F;
/* 140 */       $$20 = 1.0F;
/* 141 */       $$21 = 1.0F;
/*     */     } 
/*     */     
/* 144 */     $$3.putBulkData($$4, $$5, new float[] { $$6, $$7, $$8, $$9 }, $$19, $$20, $$21, new int[] { $$10, $$11, $$12, $$13 }, $$14, true);
/*     */   }
/*     */   
/*     */   private void calculateShape(BlockAndTintGetter $$0, BlockState $$1, BlockPos $$2, int[] $$3, Direction $$4, @Nullable float[] $$5, BitSet $$6) {
/* 148 */     float $$7 = 32.0F;
/* 149 */     float $$8 = 32.0F;
/* 150 */     float $$9 = 32.0F;
/* 151 */     float $$10 = -32.0F;
/* 152 */     float $$11 = -32.0F;
/* 153 */     float $$12 = -32.0F;
/* 154 */     for (int $$13 = 0; $$13 < 4; $$13++) {
/* 155 */       float $$14 = Float.intBitsToFloat($$3[$$13 * 8]);
/* 156 */       float $$15 = Float.intBitsToFloat($$3[$$13 * 8 + 1]);
/* 157 */       float $$16 = Float.intBitsToFloat($$3[$$13 * 8 + 2]);
/* 158 */       $$7 = Math.min($$7, $$14);
/* 159 */       $$8 = Math.min($$8, $$15);
/* 160 */       $$9 = Math.min($$9, $$16);
/* 161 */       $$10 = Math.max($$10, $$14);
/* 162 */       $$11 = Math.max($$11, $$15);
/* 163 */       $$12 = Math.max($$12, $$16);
/*     */     } 
/*     */     
/* 166 */     if ($$5 != null) {
/* 167 */       $$5[Direction.WEST.get3DDataValue()] = $$7;
/* 168 */       $$5[Direction.EAST.get3DDataValue()] = $$10;
/* 169 */       $$5[Direction.DOWN.get3DDataValue()] = $$8;
/* 170 */       $$5[Direction.UP.get3DDataValue()] = $$11;
/* 171 */       $$5[Direction.NORTH.get3DDataValue()] = $$9;
/* 172 */       $$5[Direction.SOUTH.get3DDataValue()] = $$12;
/* 173 */       int $$17 = DIRECTIONS.length;
/* 174 */       $$5[Direction.WEST.get3DDataValue() + $$17] = 1.0F - $$7;
/* 175 */       $$5[Direction.EAST.get3DDataValue() + $$17] = 1.0F - $$10;
/* 176 */       $$5[Direction.DOWN.get3DDataValue() + $$17] = 1.0F - $$8;
/* 177 */       $$5[Direction.UP.get3DDataValue() + $$17] = 1.0F - $$11;
/* 178 */       $$5[Direction.NORTH.get3DDataValue() + $$17] = 1.0F - $$9;
/* 179 */       $$5[Direction.SOUTH.get3DDataValue() + $$17] = 1.0F - $$12;
/*     */     } 
/*     */     
/* 182 */     float $$18 = 1.0E-4F;
/* 183 */     float $$19 = 0.9999F;
/* 184 */     switch ($$4) {
/*     */       case DOWN:
/* 186 */         $$6.set(1, ($$7 >= 1.0E-4F || $$9 >= 1.0E-4F || $$10 <= 0.9999F || $$12 <= 0.9999F));
/* 187 */         $$6.set(0, ($$8 == $$11 && ($$8 < 1.0E-4F || $$1.isCollisionShapeFullBlock((BlockGetter)$$0, $$2))));
/*     */         break;
/*     */       case UP:
/* 190 */         $$6.set(1, ($$7 >= 1.0E-4F || $$9 >= 1.0E-4F || $$10 <= 0.9999F || $$12 <= 0.9999F));
/* 191 */         $$6.set(0, ($$8 == $$11 && ($$11 > 0.9999F || $$1.isCollisionShapeFullBlock((BlockGetter)$$0, $$2))));
/*     */         break;
/*     */       case NORTH:
/* 194 */         $$6.set(1, ($$7 >= 1.0E-4F || $$8 >= 1.0E-4F || $$10 <= 0.9999F || $$11 <= 0.9999F));
/* 195 */         $$6.set(0, ($$9 == $$12 && ($$9 < 1.0E-4F || $$1.isCollisionShapeFullBlock((BlockGetter)$$0, $$2))));
/*     */         break;
/*     */       case SOUTH:
/* 198 */         $$6.set(1, ($$7 >= 1.0E-4F || $$8 >= 1.0E-4F || $$10 <= 0.9999F || $$11 <= 0.9999F));
/* 199 */         $$6.set(0, ($$9 == $$12 && ($$12 > 0.9999F || $$1.isCollisionShapeFullBlock((BlockGetter)$$0, $$2))));
/*     */         break;
/*     */       case WEST:
/* 202 */         $$6.set(1, ($$8 >= 1.0E-4F || $$9 >= 1.0E-4F || $$11 <= 0.9999F || $$12 <= 0.9999F));
/* 203 */         $$6.set(0, ($$7 == $$10 && ($$7 < 1.0E-4F || $$1.isCollisionShapeFullBlock((BlockGetter)$$0, $$2))));
/*     */         break;
/*     */       case EAST:
/* 206 */         $$6.set(1, ($$8 >= 1.0E-4F || $$9 >= 1.0E-4F || $$11 <= 0.9999F || $$12 <= 0.9999F));
/* 207 */         $$6.set(0, ($$7 == $$10 && ($$10 > 0.9999F || $$1.isCollisionShapeFullBlock((BlockGetter)$$0, $$2))));
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderModelFaceFlat(BlockAndTintGetter $$0, BlockState $$1, BlockPos $$2, int $$3, int $$4, boolean $$5, PoseStack $$6, VertexConsumer $$7, List<BakedQuad> $$8, BitSet $$9) {
/* 213 */     for (BakedQuad $$10 : $$8) {
/* 214 */       if ($$5) {
/* 215 */         calculateShape($$0, $$1, $$2, $$10.getVertices(), $$10.getDirection(), null, $$9);
/*     */         
/* 217 */         BlockPos $$11 = $$9.get(0) ? $$2.relative($$10.getDirection()) : $$2;
/* 218 */         $$3 = LevelRenderer.getLightColor($$0, $$1, $$11);
/*     */       } 
/*     */       
/* 221 */       float $$12 = $$0.getShade($$10.getDirection(), $$10.isShade());
/* 222 */       putQuadData($$0, $$1, $$2, $$7, $$6
/* 223 */           .last(), $$10, $$12, $$12, $$12, $$12, $$3, $$3, $$3, $$3, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderModel(PoseStack.Pose $$0, VertexConsumer $$1, @Nullable BlockState $$2, BakedModel $$3, float $$4, float $$5, float $$6, int $$7, int $$8) {
/* 231 */     RandomSource $$9 = RandomSource.create();
/* 232 */     long $$10 = 42L;
/* 233 */     for (Direction $$11 : DIRECTIONS) {
/* 234 */       $$9.setSeed(42L);
/* 235 */       renderQuadList($$0, $$1, $$4, $$5, $$6, $$3.getQuads($$2, $$11, $$9), $$7, $$8);
/*     */     } 
/* 237 */     $$9.setSeed(42L);
/* 238 */     renderQuadList($$0, $$1, $$4, $$5, $$6, $$3.getQuads($$2, null, $$9), $$7, $$8);
/*     */   }
/*     */   
/*     */   private static void renderQuadList(PoseStack.Pose $$0, VertexConsumer $$1, float $$2, float $$3, float $$4, List<BakedQuad> $$5, int $$6, int $$7) {
/* 242 */     for (BakedQuad $$8 : $$5) {
/*     */       float $$12, $$13, $$14;
/*     */ 
/*     */       
/* 246 */       if ($$8.isTinted()) {
/* 247 */         float $$9 = Mth.clamp($$2, 0.0F, 1.0F);
/* 248 */         float $$10 = Mth.clamp($$3, 0.0F, 1.0F);
/* 249 */         float $$11 = Mth.clamp($$4, 0.0F, 1.0F);
/*     */       } else {
/* 251 */         $$12 = 1.0F;
/* 252 */         $$13 = 1.0F;
/* 253 */         $$14 = 1.0F;
/*     */       } 
/* 255 */       $$1.putBulkData($$0, $$8, $$12, $$13, $$14, $$6, $$7);
/*     */     } 
/*     */   }
/*     */   
/*     */   private enum AmbientVertexRemap {
/* 260 */     DOWN(0, 1, 2, 3),
/* 261 */     UP(2, 3, 0, 1),
/* 262 */     NORTH(3, 0, 1, 2),
/* 263 */     SOUTH(0, 1, 2, 3),
/* 264 */     WEST(3, 0, 1, 2),
/* 265 */     EAST(1, 2, 3, 0); final int vert0;
/*     */     final int vert1;
/*     */     final int vert2;
/*     */     final int vert3;
/*     */     private static final AmbientVertexRemap[] BY_FACING;
/*     */     
/*     */     static {
/* 272 */       BY_FACING = (AmbientVertexRemap[])Util.make(new AmbientVertexRemap[6], $$0 -> {
/*     */             $$0[Direction.DOWN.get3DDataValue()] = DOWN;
/*     */             $$0[Direction.UP.get3DDataValue()] = UP;
/*     */             $$0[Direction.NORTH.get3DDataValue()] = NORTH;
/*     */             $$0[Direction.SOUTH.get3DDataValue()] = SOUTH;
/*     */             $$0[Direction.WEST.get3DDataValue()] = WEST;
/*     */             $$0[Direction.EAST.get3DDataValue()] = EAST;
/*     */           });
/*     */     }
/*     */     AmbientVertexRemap(int $$0, int $$1, int $$2, int $$3) {
/* 282 */       this.vert0 = $$0;
/* 283 */       this.vert1 = $$1;
/* 284 */       this.vert2 = $$2;
/* 285 */       this.vert3 = $$3;
/*     */     }
/*     */     
/*     */     public static AmbientVertexRemap fromFacing(Direction $$0) {
/* 289 */       return BY_FACING[$$0.get3DDataValue()];
/*     */     } }
/*     */   
/*     */   private static class Cache {
/*     */     private boolean enabled;
/*     */     private final Long2IntLinkedOpenHashMap colorCache;
/*     */     private final Long2FloatLinkedOpenHashMap brightnessCache;
/*     */     
/*     */     private Cache() {
/* 298 */       this.colorCache = (Long2IntLinkedOpenHashMap)Util.make(() -> {
/*     */             Long2IntLinkedOpenHashMap $$0 = new Long2IntLinkedOpenHashMap(100, 0.25F)
/*     */               {
/*     */                 protected void rehash(int $$0) {}
/*     */               };
/*     */             
/*     */             $$0.defaultReturnValue(2147483647);
/*     */             
/*     */             return $$0;
/*     */           });
/* 308 */       this.brightnessCache = (Long2FloatLinkedOpenHashMap)Util.make(() -> {
/*     */             Long2FloatLinkedOpenHashMap $$0 = new Long2FloatLinkedOpenHashMap(100, 0.25F)
/*     */               {
/*     */                 protected void rehash(int $$0) {}
/*     */               };
/*     */             $$0.defaultReturnValue(Float.NaN);
/*     */             return $$0;
/*     */           });
/*     */     }
/*     */     
/*     */     public void enable() {
/* 319 */       this.enabled = true;
/*     */     }
/*     */     
/*     */     public void disable() {
/* 323 */       this.enabled = false;
/* 324 */       this.colorCache.clear();
/* 325 */       this.brightnessCache.clear();
/*     */     }
/*     */     
/*     */     public int getLightColor(BlockState $$0, BlockAndTintGetter $$1, BlockPos $$2) {
/* 329 */       long $$3 = $$2.asLong();
/* 330 */       if (this.enabled) {
/* 331 */         int $$4 = this.colorCache.get($$3);
/* 332 */         if ($$4 != Integer.MAX_VALUE) {
/* 333 */           return $$4;
/*     */         }
/*     */       } 
/*     */       
/* 337 */       int $$5 = LevelRenderer.getLightColor($$1, $$0, $$2);
/* 338 */       if (this.enabled) {
/* 339 */         if (this.colorCache.size() == 100) {
/* 340 */           this.colorCache.removeFirstInt();
/*     */         }
/* 342 */         this.colorCache.put($$3, $$5);
/*     */       } 
/* 344 */       return $$5;
/*     */     }
/*     */     
/*     */     public float getShadeBrightness(BlockState $$0, BlockAndTintGetter $$1, BlockPos $$2) {
/* 348 */       long $$3 = $$2.asLong();
/* 349 */       if (this.enabled) {
/* 350 */         float $$4 = this.brightnessCache.get($$3);
/* 351 */         if (!Float.isNaN($$4)) {
/* 352 */           return $$4;
/*     */         }
/*     */       } 
/*     */       
/* 356 */       float $$5 = $$0.getShadeBrightness((BlockGetter)$$1, $$2);
/* 357 */       if (this.enabled) {
/* 358 */         if (this.brightnessCache.size() == 100) {
/* 359 */           this.brightnessCache.removeFirstFloat();
/*     */         }
/* 361 */         this.brightnessCache.put($$3, $$5);
/*     */       } 
/* 363 */       return $$5;
/*     */     }
/*     */   }
/*     */   
/* 367 */   static final ThreadLocal<Cache> CACHE = ThreadLocal.withInitial(Cache::new);
/*     */   class null extends Long2IntLinkedOpenHashMap {
/*     */     null(int $$1, float $$2) { super($$1, $$2); }
/* 370 */     protected void rehash(int $$0) {} } public static void enableCaching() { ((Cache)CACHE.get()).enable(); } class null extends Long2FloatLinkedOpenHashMap {
/*     */     null(int $$1, float $$2) {
/*     */       super($$1, $$2);
/*     */     } protected void rehash(int $$0) {} } public static void clearCache() {
/* 374 */     ((Cache)CACHE.get()).disable();
/*     */   }
/*     */   
/*     */   private static class AmbientOcclusionFace {
/* 378 */     final float[] brightness = new float[4];
/* 379 */     final int[] lightmap = new int[4];
/*     */ 
/*     */     
/*     */     public void calculate(BlockAndTintGetter $$0, BlockState $$1, BlockPos $$2, Direction $$3, float[] $$4, BitSet $$5, boolean $$6) {
/*     */       float $$34, $$39, $$44, $$49;
/*     */       int $$35, $$40, $$45, $$50;
/* 385 */       BlockPos $$7 = $$5.get(0) ? $$2.relative($$3) : $$2;
/* 386 */       ModelBlockRenderer.AdjacencyInfo $$8 = ModelBlockRenderer.AdjacencyInfo.fromFacing($$3);
/*     */       
/* 388 */       BlockPos.MutableBlockPos $$9 = new BlockPos.MutableBlockPos();
/*     */       
/* 390 */       ModelBlockRenderer.Cache $$10 = ModelBlockRenderer.CACHE.get();
/*     */       
/* 392 */       $$9.setWithOffset((Vec3i)$$7, $$8.corners[0]);
/* 393 */       BlockState $$11 = $$0.getBlockState((BlockPos)$$9);
/* 394 */       int $$12 = $$10.getLightColor($$11, $$0, (BlockPos)$$9);
/* 395 */       float $$13 = $$10.getShadeBrightness($$11, $$0, (BlockPos)$$9);
/*     */       
/* 397 */       $$9.setWithOffset((Vec3i)$$7, $$8.corners[1]);
/* 398 */       BlockState $$14 = $$0.getBlockState((BlockPos)$$9);
/* 399 */       int $$15 = $$10.getLightColor($$14, $$0, (BlockPos)$$9);
/* 400 */       float $$16 = $$10.getShadeBrightness($$14, $$0, (BlockPos)$$9);
/*     */       
/* 402 */       $$9.setWithOffset((Vec3i)$$7, $$8.corners[2]);
/* 403 */       BlockState $$17 = $$0.getBlockState((BlockPos)$$9);
/* 404 */       int $$18 = $$10.getLightColor($$17, $$0, (BlockPos)$$9);
/* 405 */       float $$19 = $$10.getShadeBrightness($$17, $$0, (BlockPos)$$9);
/*     */       
/* 407 */       $$9.setWithOffset((Vec3i)$$7, $$8.corners[3]);
/* 408 */       BlockState $$20 = $$0.getBlockState((BlockPos)$$9);
/* 409 */       int $$21 = $$10.getLightColor($$20, $$0, (BlockPos)$$9);
/* 410 */       float $$22 = $$10.getShadeBrightness($$20, $$0, (BlockPos)$$9);
/*     */       
/* 412 */       BlockState $$23 = $$0.getBlockState((BlockPos)$$9.setWithOffset((Vec3i)$$7, $$8.corners[0]).move($$3));
/* 413 */       boolean $$24 = (!$$23.isViewBlocking((BlockGetter)$$0, (BlockPos)$$9) || $$23.getLightBlock((BlockGetter)$$0, (BlockPos)$$9) == 0);
/* 414 */       BlockState $$25 = $$0.getBlockState((BlockPos)$$9.setWithOffset((Vec3i)$$7, $$8.corners[1]).move($$3));
/* 415 */       boolean $$26 = (!$$25.isViewBlocking((BlockGetter)$$0, (BlockPos)$$9) || $$25.getLightBlock((BlockGetter)$$0, (BlockPos)$$9) == 0);
/* 416 */       BlockState $$27 = $$0.getBlockState((BlockPos)$$9.setWithOffset((Vec3i)$$7, $$8.corners[2]).move($$3));
/* 417 */       boolean $$28 = (!$$27.isViewBlocking((BlockGetter)$$0, (BlockPos)$$9) || $$27.getLightBlock((BlockGetter)$$0, (BlockPos)$$9) == 0);
/* 418 */       BlockState $$29 = $$0.getBlockState((BlockPos)$$9.setWithOffset((Vec3i)$$7, $$8.corners[3]).move($$3));
/* 419 */       boolean $$30 = (!$$29.isViewBlocking((BlockGetter)$$0, (BlockPos)$$9) || $$29.getLightBlock((BlockGetter)$$0, (BlockPos)$$9) == 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 430 */       if ($$28 || $$24) {
/* 431 */         $$9.setWithOffset((Vec3i)$$7, $$8.corners[0]).move($$8.corners[2]);
/* 432 */         BlockState $$31 = $$0.getBlockState((BlockPos)$$9);
/* 433 */         float $$32 = $$10.getShadeBrightness($$31, $$0, (BlockPos)$$9);
/* 434 */         int $$33 = $$10.getLightColor($$31, $$0, (BlockPos)$$9);
/*     */       } else {
/* 436 */         $$34 = $$13;
/* 437 */         $$35 = $$12;
/*     */       } 
/* 439 */       if ($$30 || $$24) {
/* 440 */         $$9.setWithOffset((Vec3i)$$7, $$8.corners[0]).move($$8.corners[3]);
/* 441 */         BlockState $$36 = $$0.getBlockState((BlockPos)$$9);
/* 442 */         float $$37 = $$10.getShadeBrightness($$36, $$0, (BlockPos)$$9);
/* 443 */         int $$38 = $$10.getLightColor($$36, $$0, (BlockPos)$$9);
/*     */       } else {
/* 445 */         $$39 = $$13;
/* 446 */         $$40 = $$12;
/*     */       } 
/* 448 */       if ($$28 || $$26) {
/* 449 */         $$9.setWithOffset((Vec3i)$$7, $$8.corners[1]).move($$8.corners[2]);
/* 450 */         BlockState $$41 = $$0.getBlockState((BlockPos)$$9);
/* 451 */         float $$42 = $$10.getShadeBrightness($$41, $$0, (BlockPos)$$9);
/* 452 */         int $$43 = $$10.getLightColor($$41, $$0, (BlockPos)$$9);
/*     */       } else {
/* 454 */         $$44 = $$13;
/* 455 */         $$45 = $$12;
/*     */       } 
/* 457 */       if ($$30 || $$26) {
/* 458 */         $$9.setWithOffset((Vec3i)$$7, $$8.corners[1]).move($$8.corners[3]);
/* 459 */         BlockState $$46 = $$0.getBlockState((BlockPos)$$9);
/* 460 */         float $$47 = $$10.getShadeBrightness($$46, $$0, (BlockPos)$$9);
/* 461 */         int $$48 = $$10.getLightColor($$46, $$0, (BlockPos)$$9);
/*     */       } else {
/* 463 */         $$49 = $$13;
/* 464 */         $$50 = $$12;
/*     */       } 
/*     */       
/* 467 */       int $$51 = $$10.getLightColor($$1, $$0, $$2);
/* 468 */       $$9.setWithOffset((Vec3i)$$2, $$3);
/* 469 */       BlockState $$52 = $$0.getBlockState((BlockPos)$$9);
/* 470 */       if ($$5.get(0) || !$$52.isSolidRender((BlockGetter)$$0, (BlockPos)$$9)) {
/* 471 */         $$51 = $$10.getLightColor($$52, $$0, (BlockPos)$$9);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 476 */       float $$53 = $$5.get(0) ? $$10.getShadeBrightness($$0.getBlockState($$7), $$0, $$7) : $$10.getShadeBrightness($$0.getBlockState($$2), $$0, $$2);
/*     */       
/* 478 */       ModelBlockRenderer.AmbientVertexRemap $$54 = ModelBlockRenderer.AmbientVertexRemap.fromFacing($$3);
/*     */       
/* 480 */       if (!$$5.get(1) || !$$8.doNonCubicWeight) {
/* 481 */         float $$55 = ($$22 + $$13 + $$39 + $$53) * 0.25F;
/* 482 */         float $$56 = ($$19 + $$13 + $$34 + $$53) * 0.25F;
/* 483 */         float $$57 = ($$19 + $$16 + $$44 + $$53) * 0.25F;
/* 484 */         float $$58 = ($$22 + $$16 + $$49 + $$53) * 0.25F;
/*     */         
/* 486 */         this.lightmap[$$54.vert0] = blend($$21, $$12, $$40, $$51);
/* 487 */         this.lightmap[$$54.vert1] = blend($$18, $$12, $$35, $$51);
/* 488 */         this.lightmap[$$54.vert2] = blend($$18, $$15, $$45, $$51);
/* 489 */         this.lightmap[$$54.vert3] = blend($$21, $$15, $$50, $$51);
/*     */         
/* 491 */         this.brightness[$$54.vert0] = $$55;
/* 492 */         this.brightness[$$54.vert1] = $$56;
/* 493 */         this.brightness[$$54.vert2] = $$57;
/* 494 */         this.brightness[$$54.vert3] = $$58;
/*     */       } else {
/* 496 */         float $$59 = ($$22 + $$13 + $$39 + $$53) * 0.25F;
/* 497 */         float $$60 = ($$19 + $$13 + $$34 + $$53) * 0.25F;
/* 498 */         float $$61 = ($$19 + $$16 + $$44 + $$53) * 0.25F;
/* 499 */         float $$62 = ($$22 + $$16 + $$49 + $$53) * 0.25F;
/*     */         
/* 501 */         float $$63 = $$4[($$8.vert0Weights[0]).shape] * $$4[($$8.vert0Weights[1]).shape];
/* 502 */         float $$64 = $$4[($$8.vert0Weights[2]).shape] * $$4[($$8.vert0Weights[3]).shape];
/* 503 */         float $$65 = $$4[($$8.vert0Weights[4]).shape] * $$4[($$8.vert0Weights[5]).shape];
/* 504 */         float $$66 = $$4[($$8.vert0Weights[6]).shape] * $$4[($$8.vert0Weights[7]).shape];
/*     */         
/* 506 */         float $$67 = $$4[($$8.vert1Weights[0]).shape] * $$4[($$8.vert1Weights[1]).shape];
/* 507 */         float $$68 = $$4[($$8.vert1Weights[2]).shape] * $$4[($$8.vert1Weights[3]).shape];
/* 508 */         float $$69 = $$4[($$8.vert1Weights[4]).shape] * $$4[($$8.vert1Weights[5]).shape];
/* 509 */         float $$70 = $$4[($$8.vert1Weights[6]).shape] * $$4[($$8.vert1Weights[7]).shape];
/*     */         
/* 511 */         float $$71 = $$4[($$8.vert2Weights[0]).shape] * $$4[($$8.vert2Weights[1]).shape];
/* 512 */         float $$72 = $$4[($$8.vert2Weights[2]).shape] * $$4[($$8.vert2Weights[3]).shape];
/* 513 */         float $$73 = $$4[($$8.vert2Weights[4]).shape] * $$4[($$8.vert2Weights[5]).shape];
/* 514 */         float $$74 = $$4[($$8.vert2Weights[6]).shape] * $$4[($$8.vert2Weights[7]).shape];
/*     */         
/* 516 */         float $$75 = $$4[($$8.vert3Weights[0]).shape] * $$4[($$8.vert3Weights[1]).shape];
/* 517 */         float $$76 = $$4[($$8.vert3Weights[2]).shape] * $$4[($$8.vert3Weights[3]).shape];
/* 518 */         float $$77 = $$4[($$8.vert3Weights[4]).shape] * $$4[($$8.vert3Weights[5]).shape];
/* 519 */         float $$78 = $$4[($$8.vert3Weights[6]).shape] * $$4[($$8.vert3Weights[7]).shape];
/*     */         
/* 521 */         this.brightness[$$54.vert0] = $$59 * $$63 + $$60 * $$64 + $$61 * $$65 + $$62 * $$66;
/* 522 */         this.brightness[$$54.vert1] = $$59 * $$67 + $$60 * $$68 + $$61 * $$69 + $$62 * $$70;
/* 523 */         this.brightness[$$54.vert2] = $$59 * $$71 + $$60 * $$72 + $$61 * $$73 + $$62 * $$74;
/* 524 */         this.brightness[$$54.vert3] = $$59 * $$75 + $$60 * $$76 + $$61 * $$77 + $$62 * $$78;
/*     */         
/* 526 */         int $$79 = blend($$21, $$12, $$40, $$51);
/* 527 */         int $$80 = blend($$18, $$12, $$35, $$51);
/* 528 */         int $$81 = blend($$18, $$15, $$45, $$51);
/* 529 */         int $$82 = blend($$21, $$15, $$50, $$51);
/*     */         
/* 531 */         this.lightmap[$$54.vert0] = blend($$79, $$80, $$81, $$82, $$63, $$64, $$65, $$66);
/* 532 */         this.lightmap[$$54.vert1] = blend($$79, $$80, $$81, $$82, $$67, $$68, $$69, $$70);
/* 533 */         this.lightmap[$$54.vert2] = blend($$79, $$80, $$81, $$82, $$71, $$72, $$73, $$74);
/* 534 */         this.lightmap[$$54.vert3] = blend($$79, $$80, $$81, $$82, $$75, $$76, $$77, $$78);
/*     */       } 
/*     */       
/* 537 */       float $$83 = $$0.getShade($$3, $$6);
/* 538 */       for (int $$84 = 0; $$84 < this.brightness.length; $$84++) {
/* 539 */         this.brightness[$$84] = this.brightness[$$84] * $$83;
/*     */       }
/*     */     }
/*     */     
/*     */     private int blend(int $$0, int $$1, int $$2, int $$3) {
/* 544 */       if ($$0 == 0) {
/* 545 */         $$0 = $$3;
/*     */       }
/* 547 */       if ($$1 == 0) {
/* 548 */         $$1 = $$3;
/*     */       }
/* 550 */       if ($$2 == 0) {
/* 551 */         $$2 = $$3;
/*     */       }
/* 553 */       return $$0 + $$1 + $$2 + $$3 >> 2 & 0xFF00FF;
/*     */     }
/*     */     
/*     */     private int blend(int $$0, int $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 557 */       int $$8 = (int)(($$0 >> 16 & 0xFF) * $$4 + ($$1 >> 16 & 0xFF) * $$5 + ($$2 >> 16 & 0xFF) * $$6 + ($$3 >> 16 & 0xFF) * $$7) & 0xFF;
/* 558 */       int $$9 = (int)(($$0 & 0xFF) * $$4 + ($$1 & 0xFF) * $$5 + ($$2 & 0xFF) * $$6 + ($$3 & 0xFF) * $$7) & 0xFF;
/* 559 */       return $$8 << 16 | $$9;
/*     */     }
/*     */   }
/*     */   
/*     */   protected enum SizeInfo {
/* 564 */     DOWN((String)Direction.DOWN, false),
/* 565 */     UP((String)Direction.UP, false),
/* 566 */     NORTH((String)Direction.NORTH, false),
/* 567 */     SOUTH((String)Direction.SOUTH, false),
/* 568 */     WEST((String)Direction.WEST, false),
/* 569 */     EAST((String)Direction.EAST, false),
/* 570 */     FLIP_DOWN((String)Direction.DOWN, true),
/* 571 */     FLIP_UP((String)Direction.UP, true),
/* 572 */     FLIP_NORTH((String)Direction.NORTH, true),
/* 573 */     FLIP_SOUTH((String)Direction.SOUTH, true),
/* 574 */     FLIP_WEST((String)Direction.WEST, true),
/* 575 */     FLIP_EAST((String)Direction.EAST, true);
/*     */     
/*     */     final int shape;
/*     */     
/*     */     SizeInfo(Direction $$0, boolean $$1) {
/* 580 */       this.shape = $$0.get3DDataValue() + ($$1 ? ModelBlockRenderer.DIRECTIONS.length : 0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected enum AdjacencyInfo {
/* 585 */     DOWN((String)new Direction[] { Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH }, 0.5F, true, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.SOUTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.NORTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.NORTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.SOUTH
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }),
/* 591 */     UP((String)new Direction[] { Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH }, 1.0F, true, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.SOUTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.NORTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.NORTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.SOUTH
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }),
/* 597 */     NORTH((String)new Direction[] { Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST }, 0.8F, true, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_WEST }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_EAST }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_EAST }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_WEST
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }),
/* 603 */     SOUTH((String)new Direction[] { Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP }, 0.8F, true, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.WEST }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_WEST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.WEST, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.WEST }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.EAST }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_EAST, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.EAST, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.EAST
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }),
/* 609 */     WEST((String)new Direction[] { Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH }, 0.6F, true, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.SOUTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.NORTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.NORTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.SOUTH
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }),
/* 615 */     EAST((String)new Direction[] { Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH }, 0.6F, true, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.SOUTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.FLIP_DOWN, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.DOWN, ModelBlockRenderer.SizeInfo.NORTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.NORTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_NORTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.NORTH }, new ModelBlockRenderer.SizeInfo[] { ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.SOUTH, ModelBlockRenderer.SizeInfo.FLIP_UP, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.FLIP_SOUTH, ModelBlockRenderer.SizeInfo.UP, ModelBlockRenderer.SizeInfo.SOUTH });
/*     */     
/*     */     final Direction[] corners;
/*     */     
/*     */     final boolean doNonCubicWeight;
/*     */     
/*     */     final ModelBlockRenderer.SizeInfo[] vert0Weights;
/*     */     
/*     */     final ModelBlockRenderer.SizeInfo[] vert1Weights;
/*     */     final ModelBlockRenderer.SizeInfo[] vert2Weights;
/*     */     final ModelBlockRenderer.SizeInfo[] vert3Weights;
/*     */     private static final AdjacencyInfo[] BY_FACING;
/*     */     
/*     */     static {
/* 629 */       BY_FACING = (AdjacencyInfo[])Util.make(new AdjacencyInfo[6], $$0 -> {
/*     */             $$0[Direction.DOWN.get3DDataValue()] = DOWN;
/*     */             $$0[Direction.UP.get3DDataValue()] = UP;
/*     */             $$0[Direction.NORTH.get3DDataValue()] = NORTH;
/*     */             $$0[Direction.SOUTH.get3DDataValue()] = SOUTH;
/*     */             $$0[Direction.WEST.get3DDataValue()] = WEST;
/*     */             $$0[Direction.EAST.get3DDataValue()] = EAST;
/*     */           });
/*     */     }
/*     */     AdjacencyInfo(Direction[] $$0, float $$1, boolean $$2, ModelBlockRenderer.SizeInfo[] $$3, ModelBlockRenderer.SizeInfo[] $$4, ModelBlockRenderer.SizeInfo[] $$5, ModelBlockRenderer.SizeInfo[] $$6) {
/* 639 */       this.corners = $$0;
/* 640 */       this.doNonCubicWeight = $$2;
/* 641 */       this.vert0Weights = $$3;
/* 642 */       this.vert1Weights = $$4;
/* 643 */       this.vert2Weights = $$5;
/* 644 */       this.vert3Weights = $$6;
/*     */     }
/*     */     
/*     */     public static AdjacencyInfo fromFacing(Direction $$0) {
/* 648 */       return BY_FACING[$$0.get3DDataValue()];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\ModelBlockRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */