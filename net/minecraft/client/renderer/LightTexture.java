/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector3fc;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LightTexture
/*     */   implements AutoCloseable
/*     */ {
/*     */   public static final int FULL_BRIGHT = 15728880;
/*     */   public static final int FULL_SKY = 15728640;
/*     */   public static final int FULL_BLOCK = 240;
/*     */   private final DynamicTexture lightTexture;
/*     */   private final NativeImage lightPixels;
/*     */   private final ResourceLocation lightTextureLocation;
/*     */   private boolean updateLightTexture;
/*     */   private float blockLightRedFlicker;
/*     */   private final GameRenderer renderer;
/*     */   private final Minecraft minecraft;
/*     */   
/*     */   public LightTexture(GameRenderer $$0, Minecraft $$1) {
/*  34 */     this.renderer = $$0;
/*  35 */     this.minecraft = $$1;
/*     */     
/*  37 */     this.lightTexture = new DynamicTexture(16, 16, false);
/*  38 */     this.lightTextureLocation = this.minecraft.getTextureManager().register("light_map", this.lightTexture);
/*  39 */     this.lightPixels = this.lightTexture.getPixels();
/*  40 */     for (int $$2 = 0; $$2 < 16; $$2++) {
/*  41 */       for (int $$3 = 0; $$3 < 16; $$3++) {
/*  42 */         this.lightPixels.setPixelRGBA($$3, $$2, -1);
/*     */       }
/*     */     } 
/*  45 */     this.lightTexture.upload();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  50 */     this.lightTexture.close();
/*     */   }
/*     */   
/*     */   public void tick() {
/*  54 */     this.blockLightRedFlicker += (float)((Math.random() - Math.random()) * Math.random() * Math.random() * 0.1D);
/*  55 */     this.blockLightRedFlicker *= 0.9F;
/*  56 */     this.updateLightTexture = true;
/*     */   }
/*     */   
/*     */   public void turnOffLightLayer() {
/*  60 */     RenderSystem.setShaderTexture(2, 0);
/*     */   }
/*     */   
/*     */   public void turnOnLightLayer() {
/*  64 */     RenderSystem.setShaderTexture(2, this.lightTextureLocation);
/*     */     
/*  66 */     this.minecraft.getTextureManager().bindForSetup(this.lightTextureLocation);
/*  67 */     RenderSystem.texParameter(3553, 10241, 9729);
/*  68 */     RenderSystem.texParameter(3553, 10240, 9729);
/*     */   }
/*     */   
/*     */   private float getDarknessGamma(float $$0) {
/*  72 */     if (this.minecraft.player.hasEffect(MobEffects.DARKNESS)) {
/*  73 */       MobEffectInstance $$1 = this.minecraft.player.getEffect(MobEffects.DARKNESS);
/*  74 */       if ($$1 != null && $$1.getFactorData().isPresent()) {
/*  75 */         return ((MobEffectInstance.FactorData)$$1.getFactorData().get()).getFactor((LivingEntity)this.minecraft.player, $$0);
/*     */       }
/*     */     } 
/*  78 */     return 0.0F;
/*     */   }
/*     */   
/*     */   private float calculateDarknessScale(LivingEntity $$0, float $$1, float $$2) {
/*  82 */     float $$3 = 0.45F * $$1;
/*  83 */     return Math.max(0.0F, Mth.cos(($$0.tickCount - $$2) * 3.1415927F * 0.025F) * $$3);
/*     */   }
/*     */   public void updateLightTexture(float $$0) {
/*     */     float $$4, $$11;
/*  87 */     if (!this.updateLightTexture) {
/*     */       return;
/*     */     }
/*  90 */     this.updateLightTexture = false;
/*  91 */     this.minecraft.getProfiler().push("lightTex");
/*  92 */     ClientLevel $$1 = this.minecraft.level;
/*  93 */     if ($$1 == null) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     float $$2 = $$1.getSkyDarken(1.0F);
/*     */     
/*  99 */     if ($$1.getSkyFlashTime() > 0) {
/* 100 */       float $$3 = 1.0F;
/*     */     } else {
/* 102 */       $$4 = $$2 * 0.95F + 0.05F;
/*     */     } 
/*     */     
/* 105 */     float $$5 = ((Double)this.minecraft.options.darknessEffectScale().get()).floatValue();
/* 106 */     float $$6 = getDarknessGamma($$0) * $$5;
/* 107 */     float $$7 = calculateDarknessScale((LivingEntity)this.minecraft.player, $$6, $$0) * $$5;
/*     */ 
/*     */ 
/*     */     
/* 111 */     float $$8 = this.minecraft.player.getWaterVision();
/* 112 */     if (this.minecraft.player.hasEffect(MobEffects.NIGHT_VISION)) {
/* 113 */       float $$9 = GameRenderer.getNightVisionScale((LivingEntity)this.minecraft.player, $$0);
/* 114 */     } else if ($$8 > 0.0F && this.minecraft.player.hasEffect(MobEffects.CONDUIT_POWER)) {
/* 115 */       float $$10 = $$8;
/*     */     } else {
/* 117 */       $$11 = 0.0F;
/*     */     } 
/*     */ 
/*     */     
/* 121 */     Vector3f $$12 = (new Vector3f($$2, $$2, 1.0F)).lerp((Vector3fc)new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
/*     */ 
/*     */     
/* 124 */     float $$13 = this.blockLightRedFlicker + 1.5F;
/*     */     
/* 126 */     Vector3f $$14 = new Vector3f();
/*     */     
/* 128 */     for (int $$15 = 0; $$15 < 16; $$15++) {
/* 129 */       for (int $$16 = 0; $$16 < 16; $$16++) {
/* 130 */         float $$17 = getBrightness($$1.dimensionType(), $$15) * $$4;
/* 131 */         float $$18 = getBrightness($$1.dimensionType(), $$16) * $$13;
/*     */ 
/*     */         
/* 134 */         float $$19 = $$18;
/* 135 */         float $$20 = $$18 * (($$18 * 0.6F + 0.4F) * 0.6F + 0.4F);
/* 136 */         float $$21 = $$18 * ($$18 * $$18 * 0.6F + 0.4F);
/*     */         
/* 138 */         $$14.set($$19, $$20, $$21);
/*     */         
/* 140 */         boolean $$22 = $$1.effects().forceBrightLightmap();
/* 141 */         if ($$22) {
/* 142 */           $$14.lerp((Vector3fc)new Vector3f(0.99F, 1.12F, 1.0F), 0.25F);
/* 143 */           clampColor($$14);
/*     */         } else {
/* 145 */           Vector3f $$23 = (new Vector3f((Vector3fc)$$12)).mul($$17);
/* 146 */           $$14.add((Vector3fc)$$23);
/*     */           
/* 148 */           $$14.lerp((Vector3fc)new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
/*     */           
/* 150 */           if (this.renderer.getDarkenWorldAmount($$0) > 0.0F) {
/* 151 */             float $$24 = this.renderer.getDarkenWorldAmount($$0);
/*     */             
/* 153 */             Vector3f $$25 = (new Vector3f((Vector3fc)$$14)).mul(0.7F, 0.6F, 0.6F);
/* 154 */             $$14.lerp((Vector3fc)$$25, $$24);
/*     */           } 
/*     */         } 
/*     */         
/* 158 */         if ($$11 > 0.0F) {
/*     */           
/* 160 */           float $$26 = Math.max($$14.x(), Math.max($$14.y(), $$14.z()));
/* 161 */           if ($$26 < 1.0F) {
/* 162 */             float $$27 = 1.0F / $$26;
/*     */             
/* 164 */             Vector3f $$28 = (new Vector3f((Vector3fc)$$14)).mul($$27);
/* 165 */             $$14.lerp((Vector3fc)$$28, $$11);
/*     */           } 
/*     */         } 
/*     */         
/* 169 */         if (!$$22) {
/* 170 */           if ($$7 > 0.0F) {
/* 171 */             $$14.add(-$$7, -$$7, -$$7);
/*     */           }
/* 173 */           clampColor($$14);
/*     */         } 
/*     */         
/* 176 */         float $$29 = ((Double)this.minecraft.options.gamma().get()).floatValue();
/*     */         
/* 178 */         Vector3f $$30 = new Vector3f(notGamma($$14.x), notGamma($$14.y), notGamma($$14.z));
/* 179 */         $$14.lerp((Vector3fc)$$30, Math.max(0.0F, $$29 - $$6));
/*     */         
/* 181 */         $$14.lerp((Vector3fc)new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
/* 182 */         clampColor($$14);
/* 183 */         $$14.mul(255.0F);
/*     */         
/* 185 */         int $$31 = 255;
/* 186 */         int $$32 = (int)$$14.x();
/* 187 */         int $$33 = (int)$$14.y();
/* 188 */         int $$34 = (int)$$14.z();
/*     */         
/* 190 */         this.lightPixels.setPixelRGBA($$16, $$15, 0xFF000000 | $$34 << 16 | $$33 << 8 | $$32);
/*     */       } 
/*     */     } 
/* 193 */     this.lightTexture.upload();
/* 194 */     this.minecraft.getProfiler().pop();
/*     */   }
/*     */   
/*     */   private static void clampColor(Vector3f $$0) {
/* 198 */     $$0.set(
/* 199 */         Mth.clamp($$0.x, 0.0F, 1.0F), 
/* 200 */         Mth.clamp($$0.y, 0.0F, 1.0F), 
/* 201 */         Mth.clamp($$0.z, 0.0F, 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   private float notGamma(float $$0) {
/* 206 */     float $$1 = 1.0F - $$0;
/* 207 */     return 1.0F - $$1 * $$1 * $$1 * $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getBrightness(DimensionType $$0, int $$1) {
/* 213 */     float $$2 = $$1 / 15.0F;
/*     */     
/* 215 */     float $$3 = $$2 / (4.0F - 3.0F * $$2);
/* 216 */     return Mth.lerp($$0.ambientLight(), $$3, 1.0F);
/*     */   }
/*     */   
/*     */   public static int pack(int $$0, int $$1) {
/* 220 */     return $$0 << 4 | $$1 << 20;
/*     */   }
/*     */   
/*     */   public static int block(int $$0) {
/* 224 */     return $$0 >> 4 & 0xFFFF;
/*     */   }
/*     */   
/*     */   public static int sky(int $$0) {
/* 228 */     return $$0 >> 20 & 0xFFFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\LightTexture.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */