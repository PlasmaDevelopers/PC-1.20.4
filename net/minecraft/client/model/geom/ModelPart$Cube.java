/*     */ package net.minecraft.client.model.geom;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.Direction;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ import org.joml.Vector4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Cube
/*     */ {
/*     */   private final ModelPart.Polygon[] polygons;
/*     */   public final float minX;
/*     */   public final float minY;
/*     */   public final float minZ;
/*     */   public final float maxX;
/*     */   public final float maxY;
/*     */   public final float maxZ;
/*     */   
/*     */   public Cube(int $$0, int $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10, boolean $$11, float $$12, float $$13, Set<Direction> $$14) {
/* 220 */     this.minX = $$2;
/* 221 */     this.minY = $$3;
/* 222 */     this.minZ = $$4;
/* 223 */     this.maxX = $$2 + $$5;
/* 224 */     this.maxY = $$3 + $$6;
/* 225 */     this.maxZ = $$4 + $$7;
/* 226 */     this.polygons = new ModelPart.Polygon[$$14.size()];
/*     */     
/* 228 */     float $$15 = $$2 + $$5;
/* 229 */     float $$16 = $$3 + $$6;
/* 230 */     float $$17 = $$4 + $$7;
/*     */     
/* 232 */     $$2 -= $$8;
/* 233 */     $$3 -= $$9;
/* 234 */     $$4 -= $$10;
/* 235 */     $$15 += $$8;
/* 236 */     $$16 += $$9;
/* 237 */     $$17 += $$10;
/*     */     
/* 239 */     if ($$11) {
/* 240 */       float $$18 = $$15;
/* 241 */       $$15 = $$2;
/* 242 */       $$2 = $$18;
/*     */     } 
/*     */     
/* 245 */     ModelPart.Vertex $$19 = new ModelPart.Vertex($$2, $$3, $$4, 0.0F, 0.0F);
/* 246 */     ModelPart.Vertex $$20 = new ModelPart.Vertex($$15, $$3, $$4, 0.0F, 8.0F);
/* 247 */     ModelPart.Vertex $$21 = new ModelPart.Vertex($$15, $$16, $$4, 8.0F, 8.0F);
/* 248 */     ModelPart.Vertex $$22 = new ModelPart.Vertex($$2, $$16, $$4, 8.0F, 0.0F);
/*     */     
/* 250 */     ModelPart.Vertex $$23 = new ModelPart.Vertex($$2, $$3, $$17, 0.0F, 0.0F);
/* 251 */     ModelPart.Vertex $$24 = new ModelPart.Vertex($$15, $$3, $$17, 0.0F, 8.0F);
/* 252 */     ModelPart.Vertex $$25 = new ModelPart.Vertex($$15, $$16, $$17, 8.0F, 8.0F);
/* 253 */     ModelPart.Vertex $$26 = new ModelPart.Vertex($$2, $$16, $$17, 8.0F, 0.0F);
/*     */     
/* 255 */     float $$27 = $$0;
/* 256 */     float $$28 = $$0 + $$7;
/* 257 */     float $$29 = $$0 + $$7 + $$5;
/* 258 */     float $$30 = $$0 + $$7 + $$5 + $$5;
/* 259 */     float $$31 = $$0 + $$7 + $$5 + $$7;
/* 260 */     float $$32 = $$0 + $$7 + $$5 + $$7 + $$5;
/*     */     
/* 262 */     float $$33 = $$1;
/* 263 */     float $$34 = $$1 + $$7;
/* 264 */     float $$35 = $$1 + $$7 + $$6;
/*     */     
/* 266 */     int $$36 = 0;
/* 267 */     if ($$14.contains(Direction.DOWN)) {
/* 268 */       this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$24, $$23, $$19, $$20 }, $$28, $$33, $$29, $$34, $$12, $$13, $$11, Direction.DOWN);
/*     */     }
/* 270 */     if ($$14.contains(Direction.UP)) {
/* 271 */       this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$21, $$22, $$26, $$25 }, $$29, $$34, $$30, $$33, $$12, $$13, $$11, Direction.UP);
/*     */     }
/*     */     
/* 274 */     if ($$14.contains(Direction.WEST)) {
/* 275 */       this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$19, $$23, $$26, $$22 }, $$27, $$34, $$28, $$35, $$12, $$13, $$11, Direction.WEST);
/*     */     }
/* 277 */     if ($$14.contains(Direction.NORTH)) {
/* 278 */       this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$20, $$19, $$22, $$21 }, $$28, $$34, $$29, $$35, $$12, $$13, $$11, Direction.NORTH);
/*     */     }
/* 280 */     if ($$14.contains(Direction.EAST)) {
/* 281 */       this.polygons[$$36++] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$24, $$20, $$21, $$25 }, $$29, $$34, $$31, $$35, $$12, $$13, $$11, Direction.EAST);
/*     */     }
/* 283 */     if ($$14.contains(Direction.SOUTH)) {
/* 284 */       this.polygons[$$36] = new ModelPart.Polygon(new ModelPart.Vertex[] { $$23, $$24, $$25, $$26 }, $$31, $$34, $$32, $$35, $$12, $$13, $$11, Direction.SOUTH);
/*     */     }
/*     */   }
/*     */   
/*     */   public void compile(PoseStack.Pose $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 289 */     Matrix4f $$8 = $$0.pose();
/* 290 */     Matrix3f $$9 = $$0.normal();
/* 291 */     for (ModelPart.Polygon $$10 : this.polygons) {
/* 292 */       Vector3f $$11 = $$9.transform(new Vector3f((Vector3fc)$$10.normal));
/* 293 */       float $$12 = $$11.x();
/* 294 */       float $$13 = $$11.y();
/* 295 */       float $$14 = $$11.z();
/*     */       
/* 297 */       for (ModelPart.Vertex $$15 : $$10.vertices) {
/* 298 */         float $$16 = $$15.pos.x() / 16.0F;
/* 299 */         float $$17 = $$15.pos.y() / 16.0F;
/* 300 */         float $$18 = $$15.pos.z() / 16.0F;
/* 301 */         Vector4f $$19 = $$8.transform(new Vector4f($$16, $$17, $$18, 1.0F));
/* 302 */         $$1.vertex($$19.x(), $$19.y(), $$19.z(), $$4, $$5, $$6, $$7, $$15.u, $$15.v, $$3, $$2, $$12, $$13, $$14);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\ModelPart$Cube.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */