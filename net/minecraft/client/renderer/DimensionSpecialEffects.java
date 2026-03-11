/*     */ package net.minecraft.client.renderer;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class DimensionSpecialEffects {
/*     */   private static final Object2ObjectMap<ResourceLocation, DimensionSpecialEffects> EFFECTS;
/*     */   
/*     */   static {
/*  15 */     EFFECTS = (Object2ObjectMap<ResourceLocation, DimensionSpecialEffects>)Util.make(new Object2ObjectArrayMap(), $$0 -> {
/*     */           OverworldEffects $$1 = new OverworldEffects();
/*     */           $$0.defaultReturnValue($$1);
/*     */           $$0.put(BuiltinDimensionTypes.OVERWORLD_EFFECTS, $$1);
/*     */           $$0.put(BuiltinDimensionTypes.NETHER_EFFECTS, new NetherEffects());
/*     */           $$0.put(BuiltinDimensionTypes.END_EFFECTS, new EndEffects());
/*     */         });
/*     */   }
/*     */   
/*     */   public enum SkyType {
/*  25 */     NONE,
/*  26 */     NORMAL,
/*  27 */     END;
/*     */   }
/*     */   
/*  30 */   private final float[] sunriseCol = new float[4];
/*     */   private final float cloudLevel;
/*     */   private final boolean hasGround;
/*     */   private final SkyType skyType;
/*     */   private final boolean forceBrightLightmap;
/*     */   private final boolean constantAmbientLight;
/*     */   
/*     */   public DimensionSpecialEffects(float $$0, boolean $$1, SkyType $$2, boolean $$3, boolean $$4) {
/*  38 */     this.cloudLevel = $$0;
/*  39 */     this.hasGround = $$1;
/*  40 */     this.skyType = $$2;
/*  41 */     this.forceBrightLightmap = $$3;
/*  42 */     this.constantAmbientLight = $$4;
/*     */   }
/*     */   
/*     */   public static DimensionSpecialEffects forType(DimensionType $$0) {
/*  46 */     return (DimensionSpecialEffects)EFFECTS.get($$0.effectsLocation());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public float[] getSunriseColor(float $$0, float $$1) {
/*  51 */     float $$2 = 0.4F;
/*  52 */     float $$3 = Mth.cos($$0 * 6.2831855F) - 0.0F;
/*  53 */     float $$4 = -0.0F;
/*  54 */     if ($$3 >= -0.4F && $$3 <= 0.4F) {
/*  55 */       float $$5 = ($$3 - -0.0F) / 0.4F * 0.5F + 0.5F;
/*  56 */       float $$6 = 1.0F - (1.0F - Mth.sin($$5 * 3.1415927F)) * 0.99F;
/*  57 */       $$6 *= $$6;
/*  58 */       this.sunriseCol[0] = $$5 * 0.3F + 0.7F;
/*  59 */       this.sunriseCol[1] = $$5 * $$5 * 0.7F + 0.2F;
/*  60 */       this.sunriseCol[2] = $$5 * $$5 * 0.0F + 0.2F;
/*  61 */       this.sunriseCol[3] = $$6;
/*  62 */       return this.sunriseCol;
/*     */     } 
/*     */     
/*  65 */     return null;
/*     */   }
/*     */   
/*     */   public float getCloudHeight() {
/*  69 */     return this.cloudLevel;
/*     */   }
/*     */   
/*     */   public boolean hasGround() {
/*  73 */     return this.hasGround;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkyType skyType() {
/*  81 */     return this.skyType;
/*     */   }
/*     */   
/*     */   public boolean forceBrightLightmap() {
/*  85 */     return this.forceBrightLightmap;
/*     */   }
/*     */   public abstract Vec3 getBrightnessDependentFogColor(Vec3 paramVec3, float paramFloat);
/*     */   public boolean constantAmbientLight() {
/*  89 */     return this.constantAmbientLight;
/*     */   }
/*     */   public abstract boolean isFoggyAt(int paramInt1, int paramInt2);
/*     */   
/*     */   public static class NetherEffects extends DimensionSpecialEffects { public NetherEffects() {
/*  94 */       super(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public Vec3 getBrightnessDependentFogColor(Vec3 $$0, float $$1) {
/*  99 */       return $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFoggyAt(int $$0, int $$1) {
/* 104 */       return true;
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class OverworldEffects
/*     */     extends DimensionSpecialEffects {
/*     */     public static final int CLOUD_LEVEL = 192;
/*     */     
/*     */     public OverworldEffects() {
/* 113 */       super(192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public Vec3 getBrightnessDependentFogColor(Vec3 $$0, float $$1) {
/* 118 */       return $$0.multiply(($$1 * 0.94F + 0.06F), ($$1 * 0.94F + 0.06F), ($$1 * 0.91F + 0.09F));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isFoggyAt(int $$0, int $$1) {
/* 126 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class EndEffects extends DimensionSpecialEffects {
/*     */     public EndEffects() {
/* 132 */       super(Float.NaN, false, DimensionSpecialEffects.SkyType.END, true, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public Vec3 getBrightnessDependentFogColor(Vec3 $$0, float $$1) {
/* 137 */       return $$0.scale(0.15000000596046448D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isFoggyAt(int $$0, int $$1) {
/* 143 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public float[] getSunriseColor(float $$0, float $$1) {
/* 149 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\DimensionSpecialEffects.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */