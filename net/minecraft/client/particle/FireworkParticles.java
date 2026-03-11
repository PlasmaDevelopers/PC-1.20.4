/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.FireworkRocketItem;
/*     */ 
/*     */ public class FireworkParticles {
/*     */   public static class Starter
/*     */     extends NoRenderParticle {
/*     */     private int life;
/*     */     private final ParticleEngine engine;
/*     */     private ListTag explosions;
/*     */     private boolean twinkleDelay;
/*     */     
/*     */     public Starter(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, ParticleEngine $$7, @Nullable CompoundTag $$8) {
/*  30 */       super($$0, $$1, $$2, $$3);
/*  31 */       this.xd = $$4;
/*  32 */       this.yd = $$5;
/*  33 */       this.zd = $$6;
/*  34 */       this.engine = $$7;
/*  35 */       this.lifetime = 8;
/*     */       
/*  37 */       if ($$8 != null) {
/*  38 */         this.explosions = $$8.getList("Explosions", 10);
/*  39 */         if (this.explosions.isEmpty()) {
/*  40 */           this.explosions = null;
/*     */         } else {
/*  42 */           this.lifetime = this.explosions.size() * 2 - 1;
/*     */ 
/*     */           
/*  45 */           for (int $$9 = 0; $$9 < this.explosions.size(); $$9++) {
/*  46 */             CompoundTag $$10 = this.explosions.getCompound($$9);
/*  47 */             if ($$10.getBoolean("Flicker")) {
/*  48 */               this.twinkleDelay = true;
/*  49 */               this.lifetime += 15;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/*  59 */       if (this.life == 0 && this.explosions != null) {
/*  60 */         SoundEvent $$5; boolean $$0 = isFarAwayFromCamera();
/*     */         
/*  62 */         boolean $$1 = false;
/*  63 */         if (this.explosions.size() >= 3) {
/*  64 */           $$1 = true;
/*     */         } else {
/*  66 */           for (int $$2 = 0; $$2 < this.explosions.size(); $$2++) {
/*  67 */             CompoundTag $$3 = this.explosions.getCompound($$2);
/*  68 */             if (FireworkRocketItem.Shape.byId($$3.getByte("Type")) == FireworkRocketItem.Shape.LARGE_BALL) {
/*  69 */               $$1 = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  76 */         if ($$1) {
/*  77 */           SoundEvent $$4 = $$0 ? SoundEvents.FIREWORK_ROCKET_LARGE_BLAST_FAR : SoundEvents.FIREWORK_ROCKET_LARGE_BLAST;
/*     */         } else {
/*  79 */           $$5 = $$0 ? SoundEvents.FIREWORK_ROCKET_BLAST_FAR : SoundEvents.FIREWORK_ROCKET_BLAST;
/*     */         } 
/*  81 */         this.level.playLocalSound(this.x, this.y, this.z, $$5, SoundSource.AMBIENT, 20.0F, 0.95F + this.random.nextFloat() * 0.1F, true);
/*     */       } 
/*     */       
/*  84 */       if (this.life % 2 == 0 && this.explosions != null && this.life / 2 < this.explosions.size()) {
/*  85 */         int $$6 = this.life / 2;
/*  86 */         CompoundTag $$7 = this.explosions.getCompound($$6);
/*     */         
/*  88 */         FireworkRocketItem.Shape $$8 = FireworkRocketItem.Shape.byId($$7.getByte("Type"));
/*  89 */         boolean $$9 = $$7.getBoolean("Trail");
/*  90 */         boolean $$10 = $$7.getBoolean("Flicker");
/*  91 */         int[] $$11 = $$7.getIntArray("Colors");
/*  92 */         int[] $$12 = $$7.getIntArray("FadeColors");
/*     */         
/*  94 */         if ($$11.length == 0) {
/*  95 */           $$11 = new int[] { DyeColor.BLACK.getFireworkColor() };
/*     */         }
/*     */         
/*  98 */         switch ($$8) {
/*     */           
/*     */           default:
/* 101 */             createParticleBall(0.25D, 2, $$11, $$12, $$9, $$10);
/*     */             break;
/*     */           case LARGE_BALL:
/* 104 */             createParticleBall(0.5D, 4, $$11, $$12, $$9, $$10);
/*     */             break;
/*     */           case STAR:
/* 107 */             createParticleShape(0.5D, new double[][] { { 0.0D, 1.0D }, , { 0.3455D, 0.309D }, , { 0.9511D, 0.309D }, , { 0.3795918367346939D, -0.12653061224489795D }, , { 0.6122448979591837D, -0.8040816326530612D }, , { 0.0D, -0.35918367346938773D },  }, $$11, $$12, $$9, $$10, false);
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case CREEPER:
/* 125 */             createParticleShape(0.5D, new double[][] { { 0.0D, 0.2D }, , { 0.2D, 0.2D }, , { 0.2D, 0.6D }, , { 0.6D, 0.6D }, , { 0.6D, 0.2D }, , { 0.2D, 0.2D }, , { 0.2D, 0.0D }, , { 0.4D, 0.0D }, , { 0.4D, -0.6D }, , { 0.2D, -0.6D }, , { 0.2D, -0.4D }, , { 0.0D, -0.4D },  }, $$11, $$12, $$9, $$10, true);
/*     */             break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           case BURST:
/* 149 */             createParticleBurst($$11, $$12, $$9, $$10);
/*     */             break;
/*     */         } 
/*     */         
/* 153 */         int $$13 = $$11[0];
/* 154 */         float $$14 = (($$13 & 0xFF0000) >> 16) / 255.0F;
/* 155 */         float $$15 = (($$13 & 0xFF00) >> 8) / 255.0F;
/* 156 */         float $$16 = (($$13 & 0xFF) >> 0) / 255.0F;
/* 157 */         Particle $$17 = this.engine.createParticle((ParticleOptions)ParticleTypes.FLASH, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
/* 158 */         $$17.setColor($$14, $$15, $$16);
/*     */       } 
/* 160 */       this.life++;
/* 161 */       if (this.life > this.lifetime) {
/* 162 */         if (this.twinkleDelay) {
/* 163 */           boolean $$18 = isFarAwayFromCamera();
/* 164 */           SoundEvent $$19 = $$18 ? SoundEvents.FIREWORK_ROCKET_TWINKLE_FAR : SoundEvents.FIREWORK_ROCKET_TWINKLE;
/* 165 */           this.level.playLocalSound(this.x, this.y, this.z, $$19, SoundSource.AMBIENT, 20.0F, 0.9F + this.random.nextFloat() * 0.15F, true);
/*     */         } 
/* 167 */         remove();
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean isFarAwayFromCamera() {
/* 172 */       Minecraft $$0 = Minecraft.getInstance();
/* 173 */       return ($$0.gameRenderer.getMainCamera().getPosition().distanceToSqr(this.x, this.y, this.z) >= 256.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     private void createParticle(double $$0, double $$1, double $$2, double $$3, double $$4, double $$5, int[] $$6, int[] $$7, boolean $$8, boolean $$9) {
/* 178 */       FireworkParticles.SparkParticle $$10 = (FireworkParticles.SparkParticle)this.engine.createParticle((ParticleOptions)ParticleTypes.FIREWORK, $$0, $$1, $$2, $$3, $$4, $$5);
/* 179 */       $$10.setTrail($$8);
/* 180 */       $$10.setFlicker($$9);
/* 181 */       $$10.setAlpha(0.99F);
/*     */       
/* 183 */       int $$11 = this.random.nextInt($$6.length);
/* 184 */       $$10.setColor($$6[$$11]);
/* 185 */       if ($$7.length > 0) {
/* 186 */         $$10.setFadeColor(Util.getRandom($$7, this.random));
/*     */       }
/*     */     }
/*     */     
/*     */     private void createParticleBall(double $$0, int $$1, int[] $$2, int[] $$3, boolean $$4, boolean $$5) {
/* 191 */       double $$6 = this.x;
/* 192 */       double $$7 = this.y;
/* 193 */       double $$8 = this.z;
/*     */       
/* 195 */       for (int $$9 = -$$1; $$9 <= $$1; $$9++) {
/* 196 */         for (int $$10 = -$$1; $$10 <= $$1; $$10++) {
/* 197 */           for (int $$11 = -$$1; $$11 <= $$1; $$11++) {
/* 198 */             double $$12 = $$10 + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
/* 199 */             double $$13 = $$9 + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
/* 200 */             double $$14 = $$11 + (this.random.nextDouble() - this.random.nextDouble()) * 0.5D;
/* 201 */             double $$15 = Math.sqrt($$12 * $$12 + $$13 * $$13 + $$14 * $$14) / $$0 + this.random.nextGaussian() * 0.05D;
/*     */             
/* 203 */             createParticle($$6, $$7, $$8, $$12 / $$15, $$13 / $$15, $$14 / $$15, $$2, $$3, $$4, $$5);
/*     */             
/* 205 */             if ($$9 != -$$1 && $$9 != $$1 && $$10 != -$$1 && $$10 != $$1) {
/* 206 */               $$11 += $$1 * 2 - 1;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void createParticleShape(double $$0, double[][] $$1, int[] $$2, int[] $$3, boolean $$4, boolean $$5, boolean $$6) {
/* 214 */       double $$7 = $$1[0][0];
/* 215 */       double $$8 = $$1[0][1];
/*     */       
/* 217 */       createParticle(this.x, this.y, this.z, $$7 * $$0, $$8 * $$0, 0.0D, $$2, $$3, $$4, $$5);
/*     */       
/* 219 */       float $$9 = this.random.nextFloat() * 3.1415927F;
/* 220 */       double $$10 = $$6 ? 0.034D : 0.34D;
/* 221 */       for (int $$11 = 0; $$11 < 3; $$11++) {
/* 222 */         double $$12 = $$9 + ($$11 * 3.1415927F) * $$10;
/*     */         
/* 224 */         double $$13 = $$7;
/* 225 */         double $$14 = $$8;
/*     */         
/* 227 */         for (int $$15 = 1; $$15 < $$1.length; $$15++) {
/* 228 */           double $$16 = $$1[$$15][0];
/* 229 */           double $$17 = $$1[$$15][1];
/*     */           double $$18;
/* 231 */           for ($$18 = 0.25D; $$18 <= 1.0D; $$18 += 0.25D) {
/* 232 */             double $$19 = Mth.lerp($$18, $$13, $$16) * $$0;
/* 233 */             double $$20 = Mth.lerp($$18, $$14, $$17) * $$0;
/*     */             
/* 235 */             double $$21 = $$19 * Math.sin($$12);
/* 236 */             $$19 *= Math.cos($$12);
/*     */             double $$22;
/* 238 */             for ($$22 = -1.0D; $$22 <= 1.0D; $$22 += 2.0D) {
/* 239 */               createParticle(this.x, this.y, this.z, $$19 * $$22, $$20, $$21 * $$22, $$2, $$3, $$4, $$5);
/*     */             }
/*     */           } 
/* 242 */           $$13 = $$16;
/* 243 */           $$14 = $$17;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void createParticleBurst(int[] $$0, int[] $$1, boolean $$2, boolean $$3) {
/* 249 */       double $$4 = this.random.nextGaussian() * 0.05D;
/* 250 */       double $$5 = this.random.nextGaussian() * 0.05D;
/*     */       
/* 252 */       for (int $$6 = 0; $$6 < 70; $$6++) {
/* 253 */         double $$7 = this.xd * 0.5D + this.random.nextGaussian() * 0.15D + $$4;
/* 254 */         double $$8 = this.zd * 0.5D + this.random.nextGaussian() * 0.15D + $$5;
/* 255 */         double $$9 = this.yd * 0.5D + this.random.nextDouble() * 0.5D;
/*     */         
/* 257 */         createParticle(this.x, this.y, this.z, $$7, $$9, $$8, $$0, $$1, $$2, $$3);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SparkParticle
/*     */     extends SimpleAnimatedParticle {
/*     */     private boolean trail;
/*     */     private boolean flicker;
/*     */     private final ParticleEngine engine;
/*     */     private float fadeR;
/*     */     private float fadeG;
/*     */     private float fadeB;
/*     */     private boolean hasFade;
/*     */     
/*     */     SparkParticle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6, ParticleEngine $$7, SpriteSet $$8) {
/* 273 */       super($$0, $$1, $$2, $$3, $$8, 0.1F);
/* 274 */       this.xd = $$4;
/* 275 */       this.yd = $$5;
/* 276 */       this.zd = $$6;
/* 277 */       this.engine = $$7;
/*     */       
/* 279 */       this.quadSize *= 0.75F;
/*     */       
/* 281 */       this.lifetime = 48 + this.random.nextInt(12);
/* 282 */       setSpriteFromAge($$8);
/*     */     }
/*     */     
/*     */     public void setTrail(boolean $$0) {
/* 286 */       this.trail = $$0;
/*     */     }
/*     */     
/*     */     public void setFlicker(boolean $$0) {
/* 290 */       this.flicker = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/* 295 */       if (!this.flicker || this.age < this.lifetime / 3 || (this.age + this.lifetime) / 3 % 2 == 0) {
/* 296 */         super.render($$0, $$1, $$2);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 302 */       super.tick();
/*     */       
/* 304 */       if (this.trail && this.age < this.lifetime / 2 && (this.age + this.lifetime) % 2 == 0) {
/* 305 */         SparkParticle $$0 = new SparkParticle(this.level, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D, this.engine, this.sprites);
/* 306 */         $$0.setAlpha(0.99F);
/* 307 */         $$0.setColor(this.rCol, this.gCol, this.bCol);
/* 308 */         $$0.age = $$0.lifetime / 2;
/* 309 */         if (this.hasFade) {
/* 310 */           $$0.hasFade = true;
/* 311 */           $$0.fadeR = this.fadeR;
/* 312 */           $$0.fadeG = this.fadeG;
/* 313 */           $$0.fadeB = this.fadeB;
/*     */         } 
/* 315 */         $$0.flicker = this.flicker;
/* 316 */         this.engine.add($$0);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class OverlayParticle extends TextureSheetParticle {
/*     */     OverlayParticle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/* 323 */       super($$0, $$1, $$2, $$3);
/* 324 */       this.lifetime = 4;
/*     */     }
/*     */ 
/*     */     
/*     */     public ParticleRenderType getRenderType() {
/* 329 */       return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(VertexConsumer $$0, Camera $$1, float $$2) {
/* 334 */       setAlpha(0.6F - (this.age + $$2 - 1.0F) * 0.25F * 0.5F);
/* 335 */       super.render($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public float getQuadSize(float $$0) {
/* 340 */       return 7.1F * Mth.sin((this.age + $$0 - 1.0F) * 0.25F * 3.1415927F);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class FlashProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprite;
/*     */     
/*     */     public FlashProvider(SpriteSet $$0) {
/* 348 */       this.sprite = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 353 */       FireworkParticles.OverlayParticle $$8 = new FireworkParticles.OverlayParticle($$1, $$2, $$3, $$4);
/* 354 */       $$8.pickSprite(this.sprite);
/* 355 */       return $$8;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SparkProvider implements ParticleProvider<SimpleParticleType> {
/*     */     private final SpriteSet sprites;
/*     */     
/*     */     public SparkProvider(SpriteSet $$0) {
/* 363 */       this.sprites = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Particle createParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 368 */       FireworkParticles.SparkParticle $$8 = new FireworkParticles.SparkParticle($$1, $$2, $$3, $$4, $$5, $$6, $$7, (Minecraft.getInstance()).particleEngine, this.sprites);
/* 369 */       $$8.setAlpha(0.99F);
/* 370 */       return $$8;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\FireworkParticles.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */