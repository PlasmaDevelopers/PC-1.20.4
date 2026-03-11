/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ 
/*     */ public class DripParticle extends TextureSheetParticle {
/*     */   private final Fluid type;
/*     */   protected boolean isGlowing;
/*     */   
/*     */   private static class DripHangParticle extends DripParticle {
/*     */     DripHangParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4, ParticleOptions $$5) {
/*  23 */       super($$0, $$1, $$2, $$3, $$4);
/*  24 */       this.fallingParticle = $$5;
/*  25 */       this.gravity *= 0.02F;
/*  26 */       this.lifetime = 40;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void preMoveUpdate() {
/*  31 */       if (this.lifetime-- <= 0) {
/*  32 */         remove();
/*  33 */         this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
/*     */       } 
/*     */     }
/*     */     private final ParticleOptions fallingParticle;
/*     */     
/*     */     protected void postMoveUpdate() {
/*  39 */       this.xd *= 0.02D;
/*  40 */       this.yd *= 0.02D;
/*  41 */       this.zd *= 0.02D;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CoolingDripHangParticle extends DripHangParticle {
/*     */     CoolingDripHangParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4, ParticleOptions $$5) {
/*  47 */       super($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void preMoveUpdate() {
/*  52 */       this.rCol = 1.0F;
/*  53 */       this.gCol = 16.0F / (40 - this.lifetime + 16);
/*  54 */       this.bCol = 4.0F / (40 - this.lifetime + 8);
/*  55 */       super.preMoveUpdate();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FallAndLandParticle extends FallingParticle {
/*     */     protected final ParticleOptions landParticle;
/*     */     
/*     */     FallAndLandParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4, ParticleOptions $$5) {
/*  63 */       super($$0, $$1, $$2, $$3, $$4);
/*  64 */       this.landParticle = $$5;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void postMoveUpdate() {
/*  69 */       if (this.onGround) {
/*  70 */         remove();
/*  71 */         this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class HoneyFallAndLandParticle extends FallAndLandParticle {
/*     */     HoneyFallAndLandParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4, ParticleOptions $$5) {
/*  78 */       super($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void postMoveUpdate() {
/*  83 */       if (this.onGround) {
/*  84 */         remove();
/*  85 */         this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
/*  86 */         float $$0 = Mth.randomBetween(this.random, 0.3F, 1.0F);
/*  87 */         this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.BEEHIVE_DRIP, SoundSource.BLOCKS, $$0, 1.0F, false);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DripstoneFallAndLandParticle extends FallAndLandParticle {
/*     */     DripstoneFallAndLandParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4, ParticleOptions $$5) {
/*  94 */       super($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void postMoveUpdate() {
/*  99 */       if (this.onGround) {
/* 100 */         remove();
/* 101 */         this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
/* 102 */         SoundEvent $$0 = (getType() == Fluids.LAVA) ? SoundEvents.POINTED_DRIPSTONE_DRIP_LAVA : SoundEvents.POINTED_DRIPSTONE_DRIP_WATER;
/* 103 */         float $$1 = Mth.randomBetween(this.random, 0.3F, 1.0F);
/* 104 */         this.level.playLocalSound(this.x, this.y, this.z, $$0, SoundSource.BLOCKS, $$1, 1.0F, false);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FallingParticle extends DripParticle {
/*     */     FallingParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4) {
/* 111 */       this($$0, $$1, $$2, $$3, $$4, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
/*     */     }
/*     */     
/*     */     FallingParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4, int $$5) {
/* 115 */       super($$0, $$1, $$2, $$3, $$4);
/* 116 */       this.lifetime = $$5;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void postMoveUpdate() {
/* 121 */       if (this.onGround)
/* 122 */         remove(); 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DripLandParticle
/*     */     extends DripParticle {
/*     */     DripLandParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4) {
/* 129 */       super($$0, $$1, $$2, $$3, $$4);
/* 130 */       this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DripParticle(ClientLevel $$0, double $$1, double $$2, double $$3, Fluid $$4) {
/* 138 */     super($$0, $$1, $$2, $$3);
/* 139 */     setSize(0.01F, 0.01F);
/* 140 */     this.gravity = 0.06F;
/* 141 */     this.type = $$4;
/*     */   }
/*     */   
/*     */   protected Fluid getType() {
/* 145 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleRenderType getRenderType() {
/* 150 */     return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLightColor(float $$0) {
/* 155 */     if (this.isGlowing) {
/* 156 */       return 240;
/*     */     }
/*     */     
/* 159 */     return super.getLightColor($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 164 */     this.xo = this.x;
/* 165 */     this.yo = this.y;
/* 166 */     this.zo = this.z;
/*     */     
/* 168 */     preMoveUpdate();
/* 169 */     if (this.removed) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     this.yd -= this.gravity;
/* 174 */     move(this.xd, this.yd, this.zd);
/* 175 */     postMoveUpdate();
/* 176 */     if (this.removed) {
/*     */       return;
/*     */     }
/*     */     
/* 180 */     this.xd *= 0.9800000190734863D;
/* 181 */     this.yd *= 0.9800000190734863D;
/* 182 */     this.zd *= 0.9800000190734863D;
/*     */     
/* 184 */     if (this.type == Fluids.EMPTY) {
/*     */       return;
/*     */     }
/*     */     
/* 188 */     BlockPos $$0 = BlockPos.containing(this.x, this.y, this.z);
/* 189 */     FluidState $$1 = this.level.getFluidState($$0);
/* 190 */     if ($$1.getType() == this.type && 
/* 191 */       this.y < ($$0.getY() + $$1.getHeight((BlockGetter)this.level, $$0))) {
/* 192 */       remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void preMoveUpdate() {
/* 198 */     if (this.lifetime-- <= 0) {
/* 199 */       remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postMoveUpdate() {}
/*     */   
/*     */   public static TextureSheetParticle createWaterHangParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 207 */     DripParticle $$8 = new DripHangParticle($$1, $$2, $$3, $$4, (Fluid)Fluids.WATER, (ParticleOptions)ParticleTypes.FALLING_WATER);
/* 208 */     $$8.setColor(0.2F, 0.3F, 1.0F);
/* 209 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createWaterFallParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 213 */     DripParticle $$8 = new FallAndLandParticle($$1, $$2, $$3, $$4, (Fluid)Fluids.WATER, (ParticleOptions)ParticleTypes.SPLASH);
/* 214 */     $$8.setColor(0.2F, 0.3F, 1.0F);
/* 215 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createLavaHangParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 219 */     return new CoolingDripHangParticle($$1, $$2, $$3, $$4, (Fluid)Fluids.LAVA, (ParticleOptions)ParticleTypes.FALLING_LAVA);
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createLavaFallParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 223 */     DripParticle $$8 = new FallAndLandParticle($$1, $$2, $$3, $$4, (Fluid)Fluids.LAVA, (ParticleOptions)ParticleTypes.LANDING_LAVA);
/* 224 */     $$8.setColor(1.0F, 0.2857143F, 0.083333336F);
/* 225 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createLavaLandParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 229 */     DripParticle $$8 = new DripLandParticle($$1, $$2, $$3, $$4, (Fluid)Fluids.LAVA);
/* 230 */     $$8.setColor(1.0F, 0.2857143F, 0.083333336F);
/* 231 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createHoneyHangParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 235 */     DripHangParticle $$8 = new DripHangParticle($$1, $$2, $$3, $$4, Fluids.EMPTY, (ParticleOptions)ParticleTypes.FALLING_HONEY);
/* 236 */     $$8.gravity *= 0.01F;
/* 237 */     $$8.lifetime = 100;
/* 238 */     $$8.setColor(0.622F, 0.508F, 0.082F);
/* 239 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createHoneyFallParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 243 */     DripParticle $$8 = new HoneyFallAndLandParticle($$1, $$2, $$3, $$4, Fluids.EMPTY, (ParticleOptions)ParticleTypes.LANDING_HONEY);
/* 244 */     $$8.gravity = 0.01F;
/* 245 */     $$8.setColor(0.582F, 0.448F, 0.082F);
/* 246 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createHoneyLandParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 250 */     DripParticle $$8 = new DripLandParticle($$1, $$2, $$3, $$4, Fluids.EMPTY);
/* 251 */     $$8.lifetime = (int)(128.0D / (Math.random() * 0.8D + 0.2D));
/* 252 */     $$8.setColor(0.522F, 0.408F, 0.082F);
/* 253 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createDripstoneWaterHangParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 257 */     DripParticle $$8 = new DripHangParticle($$1, $$2, $$3, $$4, (Fluid)Fluids.WATER, (ParticleOptions)ParticleTypes.FALLING_DRIPSTONE_WATER);
/* 258 */     $$8.setColor(0.2F, 0.3F, 1.0F);
/* 259 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createDripstoneWaterFallParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 263 */     DripParticle $$8 = new DripstoneFallAndLandParticle($$1, $$2, $$3, $$4, (Fluid)Fluids.WATER, (ParticleOptions)ParticleTypes.SPLASH);
/* 264 */     $$8.setColor(0.2F, 0.3F, 1.0F);
/* 265 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createDripstoneLavaHangParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 269 */     return new CoolingDripHangParticle($$1, $$2, $$3, $$4, (Fluid)Fluids.LAVA, (ParticleOptions)ParticleTypes.FALLING_DRIPSTONE_LAVA);
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createDripstoneLavaFallParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 273 */     DripParticle $$8 = new DripstoneFallAndLandParticle($$1, $$2, $$3, $$4, (Fluid)Fluids.LAVA, (ParticleOptions)ParticleTypes.LANDING_LAVA);
/* 274 */     $$8.setColor(1.0F, 0.2857143F, 0.083333336F);
/* 275 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createNectarFallParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 279 */     DripParticle $$8 = new FallingParticle($$1, $$2, $$3, $$4, Fluids.EMPTY);
/* 280 */     $$8.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/* 281 */     $$8.gravity = 0.007F;
/* 282 */     $$8.setColor(0.92F, 0.782F, 0.72F);
/* 283 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createSporeBlossomFallParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 287 */     int $$8 = (int)(64.0F / Mth.randomBetween($$1.getRandom(), 0.1F, 0.9F));
/* 288 */     DripParticle $$9 = new FallingParticle($$1, $$2, $$3, $$4, Fluids.EMPTY, $$8);
/* 289 */     $$9.gravity = 0.005F;
/* 290 */     $$9.setColor(0.32F, 0.5F, 0.22F);
/* 291 */     return $$9;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createObsidianTearHangParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 295 */     DripHangParticle $$8 = new DripHangParticle($$1, $$2, $$3, $$4, Fluids.EMPTY, (ParticleOptions)ParticleTypes.FALLING_OBSIDIAN_TEAR);
/* 296 */     $$8.isGlowing = true;
/* 297 */     $$8.gravity *= 0.01F;
/* 298 */     $$8.lifetime = 100;
/* 299 */     $$8.setColor(0.51171875F, 0.03125F, 0.890625F);
/* 300 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createObsidianTearFallParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 304 */     DripParticle $$8 = new FallAndLandParticle($$1, $$2, $$3, $$4, Fluids.EMPTY, (ParticleOptions)ParticleTypes.LANDING_OBSIDIAN_TEAR);
/* 305 */     $$8.isGlowing = true;
/* 306 */     $$8.gravity = 0.01F;
/* 307 */     $$8.setColor(0.51171875F, 0.03125F, 0.890625F);
/* 308 */     return $$8;
/*     */   }
/*     */   
/*     */   public static TextureSheetParticle createObsidianTearLandParticle(SimpleParticleType $$0, ClientLevel $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7) {
/* 312 */     DripParticle $$8 = new DripLandParticle($$1, $$2, $$3, $$4, Fluids.EMPTY);
/* 313 */     $$8.isGlowing = true;
/* 314 */     $$8.lifetime = (int)(28.0D / (Math.random() * 0.8D + 0.2D));
/* 315 */     $$8.setColor(0.51171875F, 0.03125F, 0.890625F);
/* 316 */     return $$8;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\DripParticle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */