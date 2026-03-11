/*     */ package net.minecraft.client.model.geom;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ import org.joml.Vector4f;
/*     */ 
/*     */ public final class ModelPart
/*     */ {
/*     */   public static final float DEFAULT_SCALE = 1.0F;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float xRot;
/*     */   public float yRot;
/*     */   public float zRot;
/*  28 */   public float xScale = 1.0F;
/*  29 */   public float yScale = 1.0F;
/*  30 */   public float zScale = 1.0F;
/*     */   
/*     */   public boolean visible = true;
/*     */   
/*     */   public boolean skipDraw;
/*     */   private final List<Cube> cubes;
/*     */   private final Map<String, ModelPart> children;
/*  37 */   private PartPose initialPose = PartPose.ZERO;
/*     */   
/*     */   public ModelPart(List<Cube> $$0, Map<String, ModelPart> $$1) {
/*  40 */     this.cubes = $$0;
/*  41 */     this.children = $$1;
/*     */   }
/*     */   
/*     */   public PartPose storePose() {
/*  45 */     return PartPose.offsetAndRotation(this.x, this.y, this.z, this.xRot, this.yRot, this.zRot);
/*     */   }
/*     */   
/*     */   public PartPose getInitialPose() {
/*  49 */     return this.initialPose;
/*     */   }
/*     */   
/*     */   public void setInitialPose(PartPose $$0) {
/*  53 */     this.initialPose = $$0;
/*     */   }
/*     */   
/*     */   public void resetPose() {
/*  57 */     loadPose(this.initialPose);
/*     */   }
/*     */   
/*     */   public void loadPose(PartPose $$0) {
/*  61 */     this.x = $$0.x;
/*  62 */     this.y = $$0.y;
/*  63 */     this.z = $$0.z;
/*  64 */     this.xRot = $$0.xRot;
/*  65 */     this.yRot = $$0.yRot;
/*  66 */     this.zRot = $$0.zRot;
/*  67 */     this.xScale = 1.0F;
/*  68 */     this.yScale = 1.0F;
/*  69 */     this.zScale = 1.0F;
/*     */   }
/*     */   
/*     */   public void copyFrom(ModelPart $$0) {
/*  73 */     this.xScale = $$0.xScale;
/*  74 */     this.yScale = $$0.yScale;
/*  75 */     this.zScale = $$0.zScale;
/*  76 */     this.xRot = $$0.xRot;
/*  77 */     this.yRot = $$0.yRot;
/*  78 */     this.zRot = $$0.zRot;
/*  79 */     this.x = $$0.x;
/*  80 */     this.y = $$0.y;
/*  81 */     this.z = $$0.z;
/*     */   }
/*     */   
/*     */   public boolean hasChild(String $$0) {
/*  85 */     return this.children.containsKey($$0);
/*     */   }
/*     */   
/*     */   public ModelPart getChild(String $$0) {
/*  89 */     ModelPart $$1 = this.children.get($$0);
/*  90 */     if ($$1 == null) {
/*  91 */       throw new NoSuchElementException("Can't find part " + $$0);
/*     */     }
/*  93 */     return $$1;
/*     */   }
/*     */   
/*     */   public void setPos(float $$0, float $$1, float $$2) {
/*  97 */     this.x = $$0;
/*  98 */     this.y = $$1;
/*  99 */     this.z = $$2;
/*     */   }
/*     */   
/*     */   public void setRotation(float $$0, float $$1, float $$2) {
/* 103 */     this.xRot = $$0;
/* 104 */     this.yRot = $$1;
/* 105 */     this.zRot = $$2;
/*     */   }
/*     */   
/*     */   public void render(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3) {
/* 109 */     render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void render(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 113 */     if (!this.visible) {
/*     */       return;
/*     */     }
/* 116 */     if (this.cubes.isEmpty() && this.children.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 120 */     $$0.pushPose();
/* 121 */     translateAndRotate($$0);
/*     */     
/* 123 */     if (!this.skipDraw) {
/* 124 */       compile($$0.last(), $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     }
/*     */     
/* 127 */     for (ModelPart $$8 : this.children.values()) {
/* 128 */       $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     }
/*     */     
/* 131 */     $$0.popPose();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(PoseStack $$0, Visitor $$1) {
/* 140 */     visit($$0, $$1, "");
/*     */   }
/*     */   
/*     */   private void visit(PoseStack $$0, Visitor $$1, String $$2) {
/* 144 */     if (this.cubes.isEmpty() && this.children.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 148 */     $$0.pushPose();
/* 149 */     translateAndRotate($$0);
/*     */     
/* 151 */     PoseStack.Pose $$3 = $$0.last();
/* 152 */     for (int $$4 = 0; $$4 < this.cubes.size(); $$4++) {
/* 153 */       $$1.visit($$3, $$2, $$4, this.cubes.get($$4));
/*     */     }
/*     */     
/* 156 */     String $$5 = $$2 + "/";
/* 157 */     this.children.forEach(($$3, $$4) -> $$4.visit($$0, $$1, $$2 + $$2));
/*     */ 
/*     */ 
/*     */     
/* 161 */     $$0.popPose();
/*     */   }
/*     */   
/*     */   public void translateAndRotate(PoseStack $$0) {
/* 165 */     $$0.translate(this.x / 16.0F, this.y / 16.0F, this.z / 16.0F);
/* 166 */     if (this.xRot != 0.0F || this.yRot != 0.0F || this.zRot != 0.0F) {
/* 167 */       $$0.mulPose((new Quaternionf()).rotationZYX(this.zRot, this.yRot, this.xRot));
/*     */     }
/* 169 */     if (this.xScale != 1.0F || this.yScale != 1.0F || this.zScale != 1.0F) {
/* 170 */       $$0.scale(this.xScale, this.yScale, this.zScale);
/*     */     }
/*     */   }
/*     */   
/*     */   private void compile(PoseStack.Pose $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 175 */     for (Cube $$8 : this.cubes) {
/* 176 */       $$8.compile($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*     */     }
/*     */   }
/*     */   
/*     */   public Cube getRandomCube(RandomSource $$0) {
/* 181 */     return this.cubes.get($$0.nextInt(this.cubes.size()));
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 185 */     return this.cubes.isEmpty();
/*     */   }
/*     */   
/*     */   public void offsetPos(Vector3f $$0) {
/* 189 */     this.x += $$0.x();
/* 190 */     this.y += $$0.y();
/* 191 */     this.z += $$0.z();
/*     */   }
/*     */   
/*     */   public void offsetRotation(Vector3f $$0) {
/* 195 */     this.xRot += $$0.x();
/* 196 */     this.yRot += $$0.y();
/* 197 */     this.zRot += $$0.z();
/*     */   }
/*     */   
/*     */   public void offsetScale(Vector3f $$0) {
/* 201 */     this.xScale += $$0.x();
/* 202 */     this.yScale += $$0.y();
/* 203 */     this.zScale += $$0.z();
/*     */   }
/*     */   
/*     */   public Stream<ModelPart> getAllParts() {
/* 207 */     return Stream.concat(Stream.of(this), this.children.values().stream().flatMap(ModelPart::getAllParts));
/*     */   }
/*     */   
/*     */   public static class Cube {
/*     */     private final ModelPart.Polygon[] polygons;
/*     */     public final float minX;
/*     */     public final float minY;
/*     */     public final float minZ;
/*     */     public final float maxX;
/*     */     public final float maxY;
/*     */     public final float maxZ;
/*     */     
/*     */     public Cube(int $$0, int $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10, boolean $$11, float $$12, float $$13, Set<Direction> $$14) {
/* 220 */       this.minX = $$2;
/* 221 */       this.minY = $$3;
/* 222 */       this.minZ = $$4;
/* 223 */       this.maxX = $$2 + $$5;
/* 224 */       this.maxY = $$3 + $$6;
/* 225 */       this.maxZ = $$4 + $$7;
/* 226 */       this.polygons = new ModelPart.Polygon[$$14.size()];
/*     */       
/* 228 */       float $$15 = $$2 + $$5;
/* 229 */       float $$16 = $$3 + $$6;
/* 230 */       float $$17 = $$4 + $$7;
/*     */       
/* 232 */       $$2 -= $$8;
/* 233 */       $$3 -= $$9;
/* 234 */       $$4 -= $$10;
/* 235 */       $$15 += $$8;
/* 236 */       $$16 += $$9;
/* 237 */       $$17 += $$10;
/*     */       
/* 239 */       if ($$11) {
/* 240 */         float $$18 = $$15;
/* 241 */         $$15 = $$2;
/* 242 */         $$2 = $$18;
/*     */       } 
/*     */       
/* 245 */       ModelPart.Vertex $$19 = new ModelPart.Vertex($$2, $$3, $$4, 0.0F, 0.0F);
/* 246 */       ModelPart.Vertex $$20 = new ModelPart.Vertex($$15, $$3, $$4, 0.0F, 8.0F);
/* 247 */       ModelPart.Vertex $$21 = new ModelPart.Vertex($$15, $$16, $$4, 8.0F, 8.0F);
/* 248 */       ModelPart.Vertex $$22 = new ModelPart.Vertex($$2, $$16, $$4, 8.0F, 0.0F);
/*     */       
/* 250 */       ModelPart.Vertex $$23 = new ModelPart.Vertex($$2, $$3, $$17, 0.0F, 0.0F);
/* 251 */       ModelPart.Vertex $$24 = new ModelPart.Vertex($$15, $$3, $$17, 0.0F, 8.0F);
/* 252 */       ModelPart.Vertex $$25 = new ModelPart.Vertex($$15, $$16, $$17, 8.0F, 8.0F);
/* 253 */       ModelPart.Vertex $$26 = new ModelPart.Vertex($$2, $$16, $$17, 8.0F, 0.0F);
/*     */       
/* 255 */       float $$27 = $$0;
/* 256 */       float $$28 = $$0 + $$7;
/* 257 */       float $$29 = $$0 + $$7 + $$5;
/* 258 */       float $$30 = $$0 + $$7 + $$5 + $$5;
/* 259 */       float $$31 = $$0 + $$7 + $$5 + $$7;
/* 260 */       float $$32 = $$0 + $$7 + $$5 + $$7 + $$5;
/*     */       
/* 262 */       float $$33 = $$1;
/* 263 */       float $$34 = $$1 + $$7;
/* 264 */       float $$35 = $$1 + $$7 + $$6;
/*     */       
/* 266 */       int $$36 = 0;
/* 267 */       if ($$14.contains(Direction.DOWN)) {
/* 268 */         this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$24, $$23, $$19, $$20 }, $$28, $$33, $$29, $$34, $$12, $$13, $$11, Direction.DOWN);
/*     */       }
/* 270 */       if ($$14.contains(Direction.UP)) {
/* 271 */         this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$21, $$22, $$26, $$25 }, $$29, $$34, $$30, $$33, $$12, $$13, $$11, Direction.UP);
/*     */       }
/*     */       
/* 274 */       if ($$14.contains(Direction.WEST)) {
/* 275 */         this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$19, $$23, $$26, $$22 }, $$27, $$34, $$28, $$35, $$12, $$13, $$11, Direction.WEST);
/*     */       }
/* 277 */       if ($$14.contains(Direction.NORTH)) {
/* 278 */         this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$20, $$19, $$22, $$21 }, $$28, $$34, $$29, $$35, $$12, $$13, $$11, Direction.NORTH);
/*     */       }
/* 280 */       if ($$14.contains(Direction.EAST)) {
/* 281 */         this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$24, $$20, $$21, $$25 }, $$29, $$34, $$31, $$35, $$12, $$13, $$11, Direction.EAST);
/*     */       }
/* 283 */       if ($$14.contains(Direction.SOUTH)) {
/* 284 */         this.polygons[$$36] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$23, $$24, $$25, $$26 }, $$31, $$34, $$32, $$35, $$12, $$13, $$11, Direction.SOUTH);
/*     */       }
/*     */     }
/*     */     
/*     */     public void compile(PoseStack.Pose $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 289 */       Matrix4f $$8 = $$0.pose();
/* 290 */       Matrix3f $$9 = $$0.normal();
/* 291 */       for (ModelPart.Polygon $$10 : this.polygons) {
/* 292 */         Vector3f $$11 = $$9.transform(new Vector3f((Vector3fc)$$10.normal));
/* 293 */         float $$12 = $$11.x();
/* 294 */         float $$13 = $$11.y();
/* 295 */         float $$14 = $$11.z();
/*     */         
/* 297 */         for (ModelPart.Vertex $$15 : $$10.vertices) {
/* 298 */           float $$16 = $$15.pos.x() / 16.0F;
/* 299 */           float $$17 = $$15.pos.y() / 16.0F;
/* 300 */           float $$18 = $$15.pos.z() / 16.0F;
/* 301 */           Vector4f $$19 = $$8.transform(new Vector4f($$16, $$17, $$18, 1.0F));
/* 302 */           $$1.vertex($$19.x(), $$19.y(), $$19.z(), $$4, $$5, $$6, $$7, $$15.u, $$15.v, $$3, $$2, $$12, $$13, $$14);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Polygon {
/*     */     public final ModelPart.Vertex[] vertices;
/*     */     public final Vector3f normal;
/*     */     
/*     */     public Polygon(ModelPart.Vertex[] $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, boolean $$7, Direction $$8) {
/* 313 */       this.vertices = $$0;
/*     */       
/* 315 */       float $$9 = 0.0F / $$5;
/* 316 */       float $$10 = 0.0F / $$6;
/* 317 */       $$0[0] = $$0[0].remap($$3 / $$5 - $$9, $$2 / $$6 + $$10);
/* 318 */       $$0[1] = $$0[1].remap($$1 / $$5 + $$9, $$2 / $$6 + $$10);
/* 319 */       $$0[2] = $$0[2].remap($$1 / $$5 + $$9, $$4 / $$6 - $$10);
/* 320 */       $$0[3] = $$0[3].remap($$3 / $$5 - $$9, $$4 / $$6 - $$10);
/*     */       
/* 322 */       if ($$7) {
/* 323 */         int $$11 = $$0.length;
/* 324 */         for (int $$12 = 0; $$12 < $$11 / 2; $$12++) {
/* 325 */           ModelPart.Vertex $$13 = $$0[$$12];
/* 326 */           $$0[$$12] = $$0[$$11 - 1 - $$12];
/* 327 */           $$0[$$11 - 1 - $$12] = $$13;
/*     */         } 
/*     */       } 
/*     */       
/* 331 */       this.normal = $$8.step();
/* 332 */       if ($$7) {
/* 333 */         this.normal.mul(-1.0F, 1.0F, 1.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Vertex
/*     */   {
/*     */     public final Vector3f pos;
/*     */     public final float u;
/*     */     public final float v;
/*     */     
/*     */     public Vertex(float $$0, float $$1, float $$2, float $$3, float $$4) {
/* 345 */       this(new Vector3f($$0, $$1, $$2), $$3, $$4);
/*     */     }
/*     */     
/*     */     public Vertex remap(float $$0, float $$1) {
/* 349 */       return new Vertex(this.pos, $$0, $$1);
/*     */     }
/*     */     
/*     */     public Vertex(Vector3f $$0, float $$1, float $$2) {
/* 353 */       this.pos = $$0;
/* 354 */       this.u = $$1;
/* 355 */       this.v = $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Visitor {
/*     */     void visit(PoseStack.Pose param1Pose, String param1String, int param1Int, ModelPart.Cube param1Cube);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\ModelPart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */