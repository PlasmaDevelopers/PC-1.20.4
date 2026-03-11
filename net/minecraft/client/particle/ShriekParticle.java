/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ShriekParticleOption;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class ShriekParticle extends TextureSheetParticle {
/*  16 */   private static final Vector3f ROTATION_VECTOR = (new Vector3f(0.5F, 0.5F, 0.5F)).normalize();
/*  17 */   private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);
/*     */   
/*     */   private static final float MAGICAL_X_ROT = 1.0472F;
/*     */   private int delay;
/*     */   
/*     */   ShriekParticle(ClientLevel $$0, double $$1, double $$2, double $$3, int $$4) {
/*  23 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/*     */     
/*  25 */     this.quadSize = 0.85F;
/*     */     
/*  27 */     this.delay = $$4;
/*  28 */     this.lifetime = 30;
/*  29 */     this.gravity = 0.0F;
/*     */     
/*  31 */     this.xd = 0.0D;
/*  32 */     this.yd = 0.1D;
/*  33 */     this.zd = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getQuadSize(float $$0) {
/*  38 */     return this.quadSize * Mth.clamp((this.age + $$0) / this.lifetime * 0.75F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/*  43 */     if (this.delay > 0) {
/*     */       return;
/*     */     }
/*     */     
/*  47 */     this.alpha = 1.0F - Mth.clamp((this.age + $$2) / this.lifetime, 0.0F, 1.0F);
/*     */     
/*  49 */     renderRotatedParticle($$0, $$1, $$2, $$0 -> $$0.mul((Quaternionfc)(new Quaternionf()).rotationX(-1.0472F)));
/*     */ 
/*     */     
/*  52 */     renderRotatedParticle($$0, $$1, $$2, $$0 -> $$0.mul((Quaternionfc)(new Quaternionf()).rotationYXZ(-3.1415927F, 1.0472F, 0.0F)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderRotatedParticle(VertexConsumer $$0, Camera $$1, float $$2, Consumer<Quaternionf> $$3) {
/*  58 */     Vec3 $$4 = $$1.getPosition();
/*     */     
/*  60 */     float $$5 = (float)(Mth.lerp($$2, this.xo, this.x) - $$4.x());
/*  61 */     float $$6 = (float)(Mth.lerp($$2, this.yo, this.y) - $$4.y());
/*  62 */     float $$7 = (float)(Mth.lerp($$2, this.zo, this.z) - $$4.z());
/*     */     
/*  64 */     Quaternionf $$8 = (new Quaternionf()).setAngleAxis(0.0F, ROTATION_VECTOR.x(), ROTATION_VECTOR.y(), ROTATION_VECTOR.z());
/*  65 */     $$3.accept($$8);
/*  66 */     $$8.transform(TRANSFORM_VECTOR);
/*     */     
/*  68 */     Vector3f[] $$9 = { new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     float $$10 = getQuadSize($$2);
/*  76 */     for (int $$11 = 0; $$11 < 4; $$11++) {
/*  77 */       Vector3f $$12 = $$9[$$11];
/*  78 */       $$12.rotate((Quaternionfc)$$8);
/*  79 */       $$12.mul($$10);
/*  80 */       $$12.add($$5, $$6, $$7);
/*     */     } 
/*     */     
/*  83 */     int $$13 = getLightColor($$2);
/*  84 */     makeCornerVertex($$0, $$9[0], getU1(), getV1(), $$13);
/*  85 */     makeCornerVertex($$0, $$9[1], getU1(), getV0(), $$13);
/*  86 */     makeCornerVertex($$0, $$9[2], getU0(), getV0(), $$13);
/*  87 */     makeCornerVertex($$0, $$9[3], getU0(), getV1(), $$13);
/*     */   }
/*     */   
/*     */   private void makeCornerVertex(VertexConsumer $$0, Vector3f $$1, float $$2, float $$3, int $$4) {
/*  91 */     $$0.vertex($$1.x(), $$1.y(), $$1.z()).uv($$2, $$3).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$4).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightColor(float $$0) {
/*  96 */     return 240;
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/* 101 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 106 */     if (this.delay > 0) {
/* 107 */       this.delay--;
/*     */       
/*     */       return;
/*     */     } 
/* 111 */     super.tick();
/*     */   }
/*     */   
/*     */   public static class Provider implements ParticleProvider<ShriekParticleOption> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public Provider(SpriteSet $$0) {
/* 118 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(ShriekParticleOption $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 123 */       ShriekParticle $$8 = new ShriekParticle($$1, $$2, $$3, $$4, $$0.getDelay());
/* 124 */       $$8.pickSprite(this.sprite);
/* 125 */       $$8.setAlpha(1.0F);
/* 126 */       return $$8;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ShriekParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */