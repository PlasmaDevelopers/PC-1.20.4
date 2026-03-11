/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.VibrationParticleOption;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class VibrationSignalParticle extends TextureSheetParticle {
/*     */   private final PositionSource target;
/*     */   private float rot;
/*     */   private float rotO;
/*     */   private float pitch;
/*     */   private float pitchO;
/*     */   
/*     */   VibrationSignalParticle(ClientLevel $$0, double $$1, double $$2, double $$3, PositionSource $$4, int $$5) {
/*  26 */     super($$0, $$1, $$2, $$3, 0.0D, 0.0D, 0.0D);
/*     */     
/*  28 */     this.quadSize = 0.3F;
/*     */     
/*  30 */     this.target = $$4;
/*  31 */     this.lifetime = $$5;
/*     */     
/*  33 */     Optional<Vec3> $$6 = $$4.getPosition((Level)$$0);
/*  34 */     if ($$6.isPresent()) {
/*  35 */       Vec3 $$7 = $$6.get();
/*  36 */       double $$8 = $$1 - $$7.x();
/*  37 */       double $$9 = $$2 - $$7.y();
/*  38 */       double $$10 = $$3 - $$7.z();
/*  39 */       this.rotO = this.rot = (float)Mth.atan2($$8, $$10);
/*  40 */       this.pitchO = this.pitch = (float)Mth.atan2($$9, Math.sqrt($$8 * $$8 + $$10 * $$10));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/*  46 */     float $$3 = Mth.sin((this.age + $$2 - 6.2831855F) * 0.05F) * 2.0F;
/*  47 */     float $$4 = Mth.lerp($$2, this.rotO, this.rot);
/*  48 */     float $$5 = Mth.lerp($$2, this.pitchO, this.pitch) + 1.5707964F;
/*     */     
/*  50 */     renderSignal($$0, $$1, $$2, $$3 -> $$3.rotateY($$0).rotateX(-$$1).rotateY($$2));
/*  51 */     renderSignal($$0, $$1, $$2, $$3 -> $$3.rotateY(-3.1415927F + $$0).rotateX($$1).rotateY($$2));
/*     */   }
/*     */   
/*     */   private void renderSignal(VertexConsumer $$0, Camera $$1, float $$2, Consumer<Quaternionf> $$3) {
/*  55 */     Vec3 $$4 = $$1.getPosition();
/*     */     
/*  57 */     float $$5 = (float)(Mth.lerp($$2, this.xo, this.x) - $$4.x());
/*  58 */     float $$6 = (float)(Mth.lerp($$2, this.yo, this.y) - $$4.y());
/*  59 */     float $$7 = (float)(Mth.lerp($$2, this.zo, this.z) - $$4.z());
/*     */     
/*  61 */     Vector3f $$8 = (new Vector3f(0.5F, 0.5F, 0.5F)).normalize();
/*     */     
/*  63 */     Quaternionf $$9 = (new Quaternionf()).setAngleAxis(0.0F, $$8.x(), $$8.y(), $$8.z());
/*  64 */     $$3.accept($$9);
/*     */     
/*  66 */     Vector3f[] $$10 = { new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     float $$11 = getQuadSize($$2);
/*     */     
/*  75 */     for (int $$12 = 0; $$12 < 4; $$12++) {
/*  76 */       Vector3f $$13 = $$10[$$12];
/*     */       
/*  78 */       $$13.rotate((Quaternionfc)$$9);
/*  79 */       $$13.mul($$11);
/*  80 */       $$13.add($$5, $$6, $$7);
/*     */     } 
/*     */     
/*  83 */     float $$14 = getU0();
/*  84 */     float $$15 = getU1();
/*  85 */     float $$16 = getV0();
/*  86 */     float $$17 = getV1();
/*     */     
/*  88 */     int $$18 = getLightColor($$2);
/*     */     
/*  90 */     $$0.vertex($$10[0].x(), $$10[0].y(), $$10[0].z()).uv($$15, $$17).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$18).endVertex();
/*  91 */     $$0.vertex($$10[1].x(), $$10[1].y(), $$10[1].z()).uv($$15, $$16).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$18).endVertex();
/*  92 */     $$0.vertex($$10[2].x(), $$10[2].y(), $$10[2].z()).uv($$14, $$16).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$18).endVertex();
/*  93 */     $$0.vertex($$10[3].x(), $$10[3].y(), $$10[3].z()).uv($$14, $$17).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2($$18).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightColor(float $$0) {
/*  98 */     return 240;
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/* 103 */     return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 108 */     this.xo = this.x;
/* 109 */     this.yo = this.y;
/* 110 */     this.zo = this.z;
/*     */     
/* 112 */     if (this.age++ >= this.lifetime) {
/* 113 */       remove();
/*     */       
/*     */       return;
/*     */     } 
/* 117 */     Optional<Vec3> $$0 = this.target.getPosition((Level)this.level);
/*     */ 
/*     */     
/* 120 */     if ($$0.isEmpty()) {
/* 121 */       remove();
/*     */       
/*     */       return;
/*     */     } 
/* 125 */     int $$1 = this.lifetime - this.age;
/* 126 */     double $$2 = 1.0D / $$1;
/*     */     
/* 128 */     Vec3 $$3 = $$0.get();
/*     */     
/* 130 */     this.x = Mth.lerp($$2, this.x, $$3.x());
/* 131 */     this.y = Mth.lerp($$2, this.y, $$3.y());
/* 132 */     this.z = Mth.lerp($$2, this.z, $$3.z());
/*     */     
/* 134 */     double $$4 = this.x - $$3.x();
/* 135 */     double $$5 = this.y - $$3.y();
/* 136 */     double $$6 = this.z - $$3.z();
/*     */     
/* 138 */     this.rotO = this.rot;
/* 139 */     this.rot = (float)Mth.atan2($$4, $$6);
/*     */     
/* 141 */     this.pitchO = this.pitch;
/* 142 */     this.pitch = (float)Mth.atan2($$5, Math.sqrt($$4 * $$4 + $$6 * $$6));
/*     */   }
/*     */   
/*     */   public static class Provider implements ParticleProvider<VibrationParticleOption> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public Provider(SpriteSet $$0) {
/* 149 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(VibrationParticleOption $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 154 */       VibrationSignalParticle $$8 = new VibrationSignalParticle($$1, $$2, $$3, $$4, $$0.getDestination(), $$0.getArrivalInTicks());
/* 155 */       $$8.pickSprite(this.sprite);
/* 156 */       $$8.setAlpha(1.0F);
/* 157 */       return $$8;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\VibrationSignalParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */