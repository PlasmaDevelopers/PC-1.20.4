/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.mojang.math.Transformation;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.FaceInfo;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.ModelState;
/*     */ import net.minecraft.core.BlockMath;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ import org.joml.Vector4f;
/*     */ import org.joml.Vector4fc;
/*     */ 
/*     */ public class FaceBakery {
/*  24 */   private static final float RESCALE_22_5 = 1.0F / (float)Math.cos(0.39269909262657166D) - 1.0F; public static final int VERTEX_INT_SIZE = 8;
/*  25 */   private static final float RESCALE_45 = 1.0F / (float)Math.cos(0.7853981852531433D) - 1.0F;
/*     */   public static final int VERTEX_COUNT = 4;
/*     */   private static final int COLOR_INDEX = 3;
/*     */   public static final int UV_INDEX = 4;
/*     */   
/*     */   public BakedQuad bakeQuad(Vector3f $$0, Vector3f $$1, BlockElementFace $$2, TextureAtlasSprite $$3, Direction $$4, ModelState $$5, @Nullable BlockElementRotation $$6, boolean $$7, ResourceLocation $$8) {
/*  31 */     BlockFaceUV $$9 = $$2.uv;
/*  32 */     if ($$5.isUvLocked()) {
/*  33 */       $$9 = recomputeUVs($$2.uv, $$4, $$5.getRotation(), $$8);
/*     */     }
/*     */     
/*  36 */     float[] $$10 = new float[$$9.uvs.length];
/*  37 */     System.arraycopy($$9.uvs, 0, $$10, 0, $$10.length);
/*     */     
/*  39 */     float $$11 = $$3.uvShrinkRatio();
/*     */     
/*  41 */     float $$12 = ($$9.uvs[0] + $$9.uvs[0] + $$9.uvs[2] + $$9.uvs[2]) / 4.0F;
/*  42 */     float $$13 = ($$9.uvs[1] + $$9.uvs[1] + $$9.uvs[3] + $$9.uvs[3]) / 4.0F;
/*     */     
/*  44 */     $$9.uvs[0] = Mth.lerp($$11, $$9.uvs[0], $$12);
/*  45 */     $$9.uvs[2] = Mth.lerp($$11, $$9.uvs[2], $$12);
/*  46 */     $$9.uvs[1] = Mth.lerp($$11, $$9.uvs[1], $$13);
/*  47 */     $$9.uvs[3] = Mth.lerp($$11, $$9.uvs[3], $$13);
/*     */     
/*  49 */     int[] $$14 = makeVertices($$9, $$3, $$4, setupShape($$0, $$1), $$5.getRotation(), $$6, $$7);
/*  50 */     Direction $$15 = calculateFacing($$14);
/*     */     
/*  52 */     System.arraycopy($$10, 0, $$9.uvs, 0, $$10.length);
/*     */     
/*  54 */     if ($$6 == null) {
/*  55 */       recalculateWinding($$14, $$15);
/*     */     }
/*     */     
/*  58 */     return new BakedQuad($$14, $$2.tintIndex, $$15, $$3, $$7);
/*     */   }
/*     */   public static BlockFaceUV recomputeUVs(BlockFaceUV $$0, Direction $$1, Transformation $$2, ResourceLocation $$3) {
/*     */     float $$17, $$18, $$21, $$22;
/*  62 */     Matrix4f $$4 = BlockMath.getUVLockTransform($$2, $$1, () -> "Unable to resolve UVLock for model: " + $$0).getMatrix();
/*     */     
/*  64 */     float $$5 = $$0.getU($$0.getReverseIndex(0));
/*  65 */     float $$6 = $$0.getV($$0.getReverseIndex(0));
/*  66 */     Vector4f $$7 = $$4.transform(new Vector4f($$5 / 16.0F, $$6 / 16.0F, 0.0F, 1.0F));
/*  67 */     float $$8 = 16.0F * $$7.x();
/*  68 */     float $$9 = 16.0F * $$7.y();
/*     */     
/*  70 */     float $$10 = $$0.getU($$0.getReverseIndex(2));
/*  71 */     float $$11 = $$0.getV($$0.getReverseIndex(2));
/*  72 */     Vector4f $$12 = $$4.transform(new Vector4f($$10 / 16.0F, $$11 / 16.0F, 0.0F, 1.0F));
/*  73 */     float $$13 = 16.0F * $$12.x();
/*  74 */     float $$14 = 16.0F * $$12.y();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     if (Math.signum($$10 - $$5) == Math.signum($$13 - $$8)) {
/*  81 */       float $$15 = $$8;
/*  82 */       float $$16 = $$13;
/*     */     } else {
/*  84 */       $$17 = $$13;
/*  85 */       $$18 = $$8;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     if (Math.signum($$11 - $$6) == Math.signum($$14 - $$9)) {
/*  92 */       float $$19 = $$9;
/*  93 */       float $$20 = $$14;
/*     */     } else {
/*  95 */       $$21 = $$14;
/*  96 */       $$22 = $$9;
/*     */     } 
/*     */     
/*  99 */     float $$23 = (float)Math.toRadians($$0.rotation);
/* 100 */     Matrix3f $$24 = new Matrix3f((Matrix4fc)$$4);
/* 101 */     Vector3f $$25 = $$24.transform(new Vector3f(Mth.cos($$23), Mth.sin($$23), 0.0F));
/* 102 */     int $$26 = Math.floorMod(-((int)Math.round(Math.toDegrees(Math.atan2($$25.y(), $$25.x())) / 90.0D)) * 90, 360);
/* 103 */     return new BlockFaceUV(new float[] { $$17, $$21, $$18, $$22 }, $$26);
/*     */   }
/*     */   
/*     */   private int[] makeVertices(BlockFaceUV $$0, TextureAtlasSprite $$1, Direction $$2, float[] $$3, Transformation $$4, @Nullable BlockElementRotation $$5, boolean $$6) {
/* 107 */     int[] $$7 = new int[32];
/* 108 */     for (int $$8 = 0; $$8 < 4; $$8++) {
/* 109 */       bakeVertex($$7, $$8, $$2, $$0, $$3, $$1, $$4, $$5, $$6);
/*     */     }
/* 111 */     return $$7;
/*     */   }
/*     */   
/*     */   private float[] setupShape(Vector3f $$0, Vector3f $$1) {
/* 115 */     float[] $$2 = new float[(Direction.values()).length];
/* 116 */     $$2[FaceInfo.Constants.MIN_X] = $$0.x() / 16.0F;
/* 117 */     $$2[FaceInfo.Constants.MIN_Y] = $$0.y() / 16.0F;
/* 118 */     $$2[FaceInfo.Constants.MIN_Z] = $$0.z() / 16.0F;
/* 119 */     $$2[FaceInfo.Constants.MAX_X] = $$1.x() / 16.0F;
/* 120 */     $$2[FaceInfo.Constants.MAX_Y] = $$1.y() / 16.0F;
/* 121 */     $$2[FaceInfo.Constants.MAX_Z] = $$1.z() / 16.0F;
/* 122 */     return $$2;
/*     */   }
/*     */   
/*     */   private void bakeVertex(int[] $$0, int $$1, Direction $$2, BlockFaceUV $$3, float[] $$4, TextureAtlasSprite $$5, Transformation $$6, @Nullable BlockElementRotation $$7, boolean $$8) {
/* 126 */     FaceInfo.VertexInfo $$9 = FaceInfo.fromFacing($$2).getVertexInfo($$1);
/*     */     
/* 128 */     Vector3f $$10 = new Vector3f($$4[$$9.xFace], $$4[$$9.yFace], $$4[$$9.zFace]);
/* 129 */     applyElementRotation($$10, $$7);
/* 130 */     applyModelRotation($$10, $$6);
/*     */     
/* 132 */     fillVertex($$0, $$1, $$10, $$5, $$3);
/*     */   }
/*     */   
/*     */   private void fillVertex(int[] $$0, int $$1, Vector3f $$2, TextureAtlasSprite $$3, BlockFaceUV $$4) {
/* 136 */     int $$5 = $$1 * 8;
/* 137 */     $$0[$$5] = Float.floatToRawIntBits($$2.x());
/* 138 */     $$0[$$5 + 1] = Float.floatToRawIntBits($$2.y());
/* 139 */     $$0[$$5 + 2] = Float.floatToRawIntBits($$2.z());
/* 140 */     $$0[$$5 + 3] = -1;
/* 141 */     $$0[$$5 + 4] = Float.floatToRawIntBits($$3.getU($$4.getU($$1) / 16.0F));
/* 142 */     $$0[$$5 + 4 + 1] = Float.floatToRawIntBits($$3.getV($$4.getV($$1) / 16.0F));
/*     */   }
/*     */   private void applyElementRotation(Vector3f $$0, @Nullable BlockElementRotation $$1) {
/*     */     Vector3f $$2, $$4, $$6, $$3, $$5, $$7;
/* 146 */     if ($$1 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 152 */     switch ($$1.axis()) {
/*     */       case X:
/* 154 */         $$2 = new Vector3f(1.0F, 0.0F, 0.0F);
/* 155 */         $$3 = new Vector3f(0.0F, 1.0F, 1.0F);
/*     */         break;
/*     */       case Y:
/* 158 */         $$4 = new Vector3f(0.0F, 1.0F, 0.0F);
/* 159 */         $$5 = new Vector3f(1.0F, 0.0F, 1.0F);
/*     */         break;
/*     */       case Z:
/* 162 */         $$6 = new Vector3f(0.0F, 0.0F, 1.0F);
/* 163 */         $$7 = new Vector3f(1.0F, 1.0F, 0.0F); break;
/*     */       default:
/* 165 */         throw new IllegalArgumentException("There are only 3 axes");
/*     */     } 
/*     */     
/* 168 */     Quaternionf $$10 = (new Quaternionf()).rotationAxis($$1.angle() * 0.017453292F, (Vector3fc)$$6);
/*     */     
/* 170 */     if ($$1.rescale()) {
/* 171 */       if (Math.abs($$1.angle()) == 22.5F) {
/* 172 */         $$7.mul(RESCALE_22_5);
/*     */       } else {
/* 174 */         $$7.mul(RESCALE_45);
/*     */       } 
/* 176 */       $$7.add(1.0F, 1.0F, 1.0F);
/*     */     } else {
/* 178 */       $$7.set(1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */     
/* 181 */     rotateVertexBy($$0, new Vector3f((Vector3fc)$$1.origin()), (new Matrix4f()).rotation((Quaternionfc)$$10), $$7);
/*     */   }
/*     */   
/*     */   public void applyModelRotation(Vector3f $$0, Transformation $$1) {
/* 185 */     if ($$1 == Transformation.identity()) {
/*     */       return;
/*     */     }
/*     */     
/* 189 */     rotateVertexBy($$0, new Vector3f(0.5F, 0.5F, 0.5F), $$1.getMatrix(), new Vector3f(1.0F, 1.0F, 1.0F));
/*     */   }
/*     */   
/*     */   private void rotateVertexBy(Vector3f $$0, Vector3f $$1, Matrix4f $$2, Vector3f $$3) {
/* 193 */     Vector4f $$4 = $$2.transform(new Vector4f($$0.x() - $$1.x(), $$0.y() - $$1.y(), $$0.z() - $$1.z(), 1.0F));
/* 194 */     $$4.mul((Vector4fc)new Vector4f((Vector3fc)$$3, 1.0F));
/* 195 */     $$0.set($$4.x() + $$1.x(), $$4.y() + $$1.y(), $$4.z() + $$1.z());
/*     */   }
/*     */   
/*     */   public static Direction calculateFacing(int[] $$0) {
/* 199 */     Vector3f $$1 = new Vector3f(Float.intBitsToFloat($$0[0]), Float.intBitsToFloat($$0[1]), Float.intBitsToFloat($$0[2]));
/* 200 */     Vector3f $$2 = new Vector3f(Float.intBitsToFloat($$0[8]), Float.intBitsToFloat($$0[9]), Float.intBitsToFloat($$0[10]));
/* 201 */     Vector3f $$3 = new Vector3f(Float.intBitsToFloat($$0[16]), Float.intBitsToFloat($$0[17]), Float.intBitsToFloat($$0[18]));
/* 202 */     Vector3f $$4 = (new Vector3f((Vector3fc)$$1)).sub((Vector3fc)$$2);
/* 203 */     Vector3f $$5 = (new Vector3f((Vector3fc)$$3)).sub((Vector3fc)$$2);
/* 204 */     Vector3f $$6 = (new Vector3f((Vector3fc)$$5)).cross((Vector3fc)$$4).normalize();
/* 205 */     if (!$$6.isFinite()) {
/* 206 */       return Direction.UP;
/*     */     }
/*     */     
/* 209 */     Direction $$7 = null;
/* 210 */     float $$8 = 0.0F;
/* 211 */     for (Direction $$9 : Direction.values()) {
/* 212 */       Vec3i $$10 = $$9.getNormal();
/* 213 */       Vector3f $$11 = new Vector3f($$10.getX(), $$10.getY(), $$10.getZ());
/* 214 */       float $$12 = $$6.dot((Vector3fc)$$11);
/*     */       
/* 216 */       if ($$12 >= 0.0F && $$12 > $$8) {
/* 217 */         $$8 = $$12;
/* 218 */         $$7 = $$9;
/*     */       } 
/*     */     } 
/* 221 */     if ($$7 == null) {
/* 222 */       return Direction.UP;
/*     */     }
/* 224 */     return $$7;
/*     */   }
/*     */   
/*     */   private void recalculateWinding(int[] $$0, Direction $$1) {
/* 228 */     int[] $$2 = new int[$$0.length];
/* 229 */     System.arraycopy($$0, 0, $$2, 0, $$0.length);
/*     */     
/* 231 */     float[] $$3 = new float[(Direction.values()).length];
/* 232 */     $$3[FaceInfo.Constants.MIN_X] = 999.0F;
/* 233 */     $$3[FaceInfo.Constants.MIN_Y] = 999.0F;
/* 234 */     $$3[FaceInfo.Constants.MIN_Z] = 999.0F;
/* 235 */     $$3[FaceInfo.Constants.MAX_X] = -999.0F;
/* 236 */     $$3[FaceInfo.Constants.MAX_Y] = -999.0F;
/* 237 */     $$3[FaceInfo.Constants.MAX_Z] = -999.0F;
/* 238 */     for (int $$4 = 0; $$4 < 4; $$4++) {
/* 239 */       int $$5 = 8 * $$4;
/* 240 */       float $$6 = Float.intBitsToFloat($$2[$$5]);
/* 241 */       float $$7 = Float.intBitsToFloat($$2[$$5 + 1]);
/* 242 */       float $$8 = Float.intBitsToFloat($$2[$$5 + 2]);
/* 243 */       if ($$6 < $$3[FaceInfo.Constants.MIN_X]) {
/* 244 */         $$3[FaceInfo.Constants.MIN_X] = $$6;
/*     */       }
/* 246 */       if ($$7 < $$3[FaceInfo.Constants.MIN_Y]) {
/* 247 */         $$3[FaceInfo.Constants.MIN_Y] = $$7;
/*     */       }
/* 249 */       if ($$8 < $$3[FaceInfo.Constants.MIN_Z]) {
/* 250 */         $$3[FaceInfo.Constants.MIN_Z] = $$8;
/*     */       }
/* 252 */       if ($$6 > $$3[FaceInfo.Constants.MAX_X]) {
/* 253 */         $$3[FaceInfo.Constants.MAX_X] = $$6;
/*     */       }
/* 255 */       if ($$7 > $$3[FaceInfo.Constants.MAX_Y]) {
/* 256 */         $$3[FaceInfo.Constants.MAX_Y] = $$7;
/*     */       }
/* 258 */       if ($$8 > $$3[FaceInfo.Constants.MAX_Z]) {
/* 259 */         $$3[FaceInfo.Constants.MAX_Z] = $$8;
/*     */       }
/*     */     } 
/*     */     
/* 263 */     FaceInfo $$9 = FaceInfo.fromFacing($$1);
/* 264 */     for (int $$10 = 0; $$10 < 4; $$10++) {
/* 265 */       int $$11 = 8 * $$10;
/* 266 */       FaceInfo.VertexInfo $$12 = $$9.getVertexInfo($$10);
/*     */       
/* 268 */       float $$13 = $$3[$$12.xFace];
/* 269 */       float $$14 = $$3[$$12.yFace];
/* 270 */       float $$15 = $$3[$$12.zFace];
/*     */       
/* 272 */       $$0[$$11] = Float.floatToRawIntBits($$13);
/* 273 */       $$0[$$11 + 1] = Float.floatToRawIntBits($$14);
/* 274 */       $$0[$$11 + 2] = Float.floatToRawIntBits($$15);
/*     */       
/* 276 */       for (int $$16 = 0; $$16 < 4; $$16++) {
/* 277 */         int $$17 = 8 * $$16;
/* 278 */         float $$18 = Float.intBitsToFloat($$2[$$17]);
/* 279 */         float $$19 = Float.intBitsToFloat($$2[$$17 + 1]);
/* 280 */         float $$20 = Float.intBitsToFloat($$2[$$17 + 2]);
/* 281 */         if (Mth.equal($$13, $$18) && Mth.equal($$14, $$19) && Mth.equal($$15, $$20)) {
/* 282 */           $$0[$$11 + 4] = $$2[$$17 + 4];
/* 283 */           $$0[$$11 + 4 + 1] = $$2[$$17 + 4 + 1];
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\FaceBakery.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */