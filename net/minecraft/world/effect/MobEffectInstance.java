/*     */ package net.minecraft.world.effect;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function7;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntFunction;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class MobEffectInstance implements Comparable<MobEffectInstance> {
/*  24 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final int INFINITE_DURATION = -1;
/*     */   
/*     */   private static final String TAG_ID = "id";
/*     */   private static final String TAG_AMBIENT = "ambient";
/*     */   private static final String TAG_HIDDEN_EFFECT = "hidden_effect";
/*     */   private static final String TAG_AMPLIFIER = "amplifier";
/*     */   private static final String TAG_DURATION = "duration";
/*     */   private static final String TAG_SHOW_PARTICLES = "show_particles";
/*     */   private static final String TAG_SHOW_ICON = "show_icon";
/*     */   private static final String TAG_FACTOR_CALCULATION_DATA = "factor_calculation_data";
/*     */   private final MobEffect effect;
/*     */   private int duration;
/*     */   private int amplifier;
/*     */   private boolean ambient;
/*     */   private boolean visible;
/*     */   private boolean showIcon;
/*     */   @Nullable
/*     */   private MobEffectInstance hiddenEffect;
/*     */   private final Optional<FactorData> factorData;
/*     */   
/*     */   public MobEffectInstance(MobEffect $$0) {
/*  47 */     this($$0, 0, 0);
/*     */   }
/*     */   
/*     */   public MobEffectInstance(MobEffect $$0, int $$1) {
/*  51 */     this($$0, $$1, 0);
/*     */   }
/*     */   
/*     */   public MobEffectInstance(MobEffect $$0, int $$1, int $$2) {
/*  55 */     this($$0, $$1, $$2, false, true);
/*     */   }
/*     */   
/*     */   public MobEffectInstance(MobEffect $$0, int $$1, int $$2, boolean $$3, boolean $$4) {
/*  59 */     this($$0, $$1, $$2, $$3, $$4, $$4);
/*     */   }
/*     */   
/*     */   public MobEffectInstance(MobEffect $$0, int $$1, int $$2, boolean $$3, boolean $$4, boolean $$5) {
/*  63 */     this($$0, $$1, $$2, $$3, $$4, $$5, null, $$0.createFactorData());
/*     */   }
/*     */   
/*     */   public MobEffectInstance(MobEffect $$0, int $$1, int $$2, boolean $$3, boolean $$4, boolean $$5, @Nullable MobEffectInstance $$6, Optional<FactorData> $$7) {
/*  67 */     this.effect = $$0;
/*  68 */     this.duration = $$1;
/*  69 */     this.amplifier = $$2;
/*  70 */     this.ambient = $$3;
/*  71 */     this.visible = $$4;
/*  72 */     this.showIcon = $$5;
/*  73 */     this.hiddenEffect = $$6;
/*  74 */     this.factorData = $$7;
/*     */   }
/*     */   
/*     */   public MobEffectInstance(MobEffectInstance $$0) {
/*  78 */     this.effect = $$0.effect;
/*  79 */     this.factorData = this.effect.createFactorData();
/*  80 */     setDetailsFrom($$0);
/*     */   }
/*     */   
/*     */   public Optional<FactorData> getFactorData() {
/*  84 */     return this.factorData;
/*     */   }
/*     */   
/*     */   void setDetailsFrom(MobEffectInstance $$0) {
/*  88 */     this.duration = $$0.duration;
/*  89 */     this.amplifier = $$0.amplifier;
/*  90 */     this.ambient = $$0.ambient;
/*  91 */     this.visible = $$0.visible;
/*  92 */     this.showIcon = $$0.showIcon;
/*     */   }
/*     */   
/*     */   public boolean update(MobEffectInstance $$0) {
/*  96 */     if (this.effect != $$0.effect) {
/*  97 */       LOGGER.warn("This method should only be called for matching effects!");
/*     */     }
/*     */     
/* 100 */     boolean $$1 = false;
/* 101 */     if ($$0.amplifier > this.amplifier) {
/* 102 */       if ($$0.isShorterDurationThan(this)) {
/* 103 */         MobEffectInstance $$2 = this.hiddenEffect;
/* 104 */         this.hiddenEffect = new MobEffectInstance(this);
/* 105 */         this.hiddenEffect.hiddenEffect = $$2;
/*     */       } 
/* 107 */       this.amplifier = $$0.amplifier;
/* 108 */       this.duration = $$0.duration;
/* 109 */       $$1 = true;
/* 110 */     } else if (isShorterDurationThan($$0)) {
/* 111 */       if ($$0.amplifier == this.amplifier) {
/* 112 */         this.duration = $$0.duration;
/* 113 */         $$1 = true;
/*     */       }
/* 115 */       else if (this.hiddenEffect == null) {
/* 116 */         this.hiddenEffect = new MobEffectInstance($$0);
/*     */       } else {
/* 118 */         this.hiddenEffect.update($$0);
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     if ((!$$0.ambient && this.ambient) || $$1) {
/* 123 */       this.ambient = $$0.ambient;
/* 124 */       $$1 = true;
/*     */     } 
/* 126 */     if ($$0.visible != this.visible) {
/* 127 */       this.visible = $$0.visible;
/* 128 */       $$1 = true;
/*     */     } 
/* 130 */     if ($$0.showIcon != this.showIcon) {
/* 131 */       this.showIcon = $$0.showIcon;
/* 132 */       $$1 = true;
/*     */     } 
/*     */     
/* 135 */     return $$1;
/*     */   }
/*     */   
/*     */   private boolean isShorterDurationThan(MobEffectInstance $$0) {
/* 139 */     return (!isInfiniteDuration() && (this.duration < $$0.duration || $$0.isInfiniteDuration()));
/*     */   }
/*     */   
/*     */   public boolean isInfiniteDuration() {
/* 143 */     return (this.duration == -1);
/*     */   }
/*     */   
/*     */   public boolean endsWithin(int $$0) {
/* 147 */     return (!isInfiniteDuration() && this.duration <= $$0);
/*     */   }
/*     */   
/*     */   public int mapDuration(Int2IntFunction $$0) {
/* 151 */     if (isInfiniteDuration() || this.duration == 0) {
/* 152 */       return this.duration;
/*     */     }
/* 154 */     return $$0.applyAsInt(this.duration);
/*     */   }
/*     */   
/*     */   public MobEffect getEffect() {
/* 158 */     return this.effect;
/*     */   }
/*     */   
/*     */   public int getDuration() {
/* 162 */     return this.duration;
/*     */   }
/*     */   
/*     */   public int getAmplifier() {
/* 166 */     return this.amplifier;
/*     */   }
/*     */   
/*     */   public boolean isAmbient() {
/* 170 */     return this.ambient;
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 174 */     return this.visible;
/*     */   }
/*     */   
/*     */   public boolean showIcon() {
/* 178 */     return this.showIcon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tick(LivingEntity $$0, Runnable $$1) {
/* 188 */     if (hasRemainingDuration()) {
/* 189 */       int $$2 = isInfiniteDuration() ? $$0.tickCount : this.duration;
/* 190 */       if (this.effect.shouldApplyEffectTickThisTick($$2, this.amplifier)) {
/* 191 */         this.effect.applyEffectTick($$0, this.amplifier);
/*     */       }
/* 193 */       tickDownDuration();
/* 194 */       if (this.duration == 0 && this.hiddenEffect != null) {
/* 195 */         setDetailsFrom(this.hiddenEffect);
/* 196 */         this.hiddenEffect = this.hiddenEffect.hiddenEffect;
/* 197 */         $$1.run();
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     this.factorData.ifPresent($$0 -> $$0.tick(this));
/* 202 */     return hasRemainingDuration();
/*     */   }
/*     */   
/*     */   private boolean hasRemainingDuration() {
/* 206 */     return (isInfiniteDuration() || this.duration > 0);
/*     */   }
/*     */   
/*     */   private int tickDownDuration() {
/* 210 */     if (this.hiddenEffect != null) {
/* 211 */       this.hiddenEffect.tickDownDuration();
/*     */     }
/* 213 */     return this.duration = mapDuration($$0 -> $$0 - 1);
/*     */   }
/*     */   
/*     */   public void onEffectStarted(LivingEntity $$0) {
/* 217 */     this.effect.onEffectStarted($$0, this.amplifier);
/*     */   }
/*     */   
/*     */   public String getDescriptionId() {
/* 221 */     return this.effect.getDescriptionId();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     String $$1;
/* 227 */     if (this.amplifier > 0) {
/* 228 */       String $$0 = getDescriptionId() + " x " + getDescriptionId() + ", Duration: " + this.amplifier + 1;
/*     */     } else {
/* 230 */       $$1 = getDescriptionId() + ", Duration: " + getDescriptionId();
/*     */     } 
/* 232 */     if (!this.visible) {
/* 233 */       $$1 = $$1 + ", Particles: false";
/*     */     }
/* 235 */     if (!this.showIcon) {
/* 236 */       $$1 = $$1 + ", Show Icon: false";
/*     */     }
/*     */     
/* 239 */     return $$1;
/*     */   }
/*     */   
/*     */   private String describeDuration() {
/* 243 */     if (isInfiniteDuration()) {
/* 244 */       return "infinite";
/*     */     }
/* 246 */     return Integer.toString(this.duration);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 251 */     if (this == $$0) {
/* 252 */       return true;
/*     */     }
/*     */     
/* 255 */     if ($$0 instanceof MobEffectInstance) { MobEffectInstance $$1 = (MobEffectInstance)$$0;
/* 256 */       return (this.duration == $$1.duration && this.amplifier == $$1.amplifier && this.ambient == $$1.ambient && this.effect.equals($$1.effect)); }
/*     */     
/* 258 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 263 */     int $$0 = this.effect.hashCode();
/* 264 */     $$0 = 31 * $$0 + this.duration;
/* 265 */     $$0 = 31 * $$0 + this.amplifier;
/* 266 */     $$0 = 31 * $$0 + (this.ambient ? 1 : 0);
/* 267 */     return $$0;
/*     */   }
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/* 271 */     ResourceLocation $$1 = BuiltInRegistries.MOB_EFFECT.getKey(this.effect);
/* 272 */     $$0.putString("id", $$1.toString());
/* 273 */     writeDetailsTo($$0);
/* 274 */     return $$0;
/*     */   }
/*     */   
/*     */   private void writeDetailsTo(CompoundTag $$0) {
/* 278 */     $$0.putByte("amplifier", (byte)getAmplifier());
/* 279 */     $$0.putInt("duration", getDuration());
/* 280 */     $$0.putBoolean("ambient", isAmbient());
/* 281 */     $$0.putBoolean("show_particles", isVisible());
/* 282 */     $$0.putBoolean("show_icon", showIcon());
/* 283 */     if (this.hiddenEffect != null) {
/* 284 */       CompoundTag $$1 = new CompoundTag();
/* 285 */       this.hiddenEffect.save($$1);
/* 286 */       $$0.put("hidden_effect", (Tag)$$1);
/*     */     } 
/* 288 */     this.factorData.ifPresent($$1 -> {
/*     */           Objects.requireNonNull(LOGGER);
/*     */           FactorData.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$1).resultOrPartial(LOGGER::error).ifPresent(());
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static MobEffectInstance load(CompoundTag $$0) {
/* 297 */     String $$1 = $$0.getString("id");
/* 298 */     MobEffect $$2 = (MobEffect)BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse($$1));
/* 299 */     if ($$2 == null) {
/* 300 */       return null;
/*     */     }
/* 302 */     return loadSpecifiedEffect($$2, $$0);
/*     */   }
/*     */   private static MobEffectInstance loadSpecifiedEffect(MobEffect $$0, CompoundTag $$1) {
/*     */     Optional<FactorData> $$9;
/* 306 */     int $$2 = $$1.getByte("amplifier");
/* 307 */     int $$3 = $$1.getInt("duration");
/* 308 */     boolean $$4 = $$1.getBoolean("ambient");
/* 309 */     boolean $$5 = true;
/* 310 */     if ($$1.contains("show_particles", 1)) {
/* 311 */       $$5 = $$1.getBoolean("show_particles");
/*     */     }
/* 313 */     boolean $$6 = $$5;
/* 314 */     if ($$1.contains("show_icon", 1)) {
/* 315 */       $$6 = $$1.getBoolean("show_icon");
/*     */     }
/* 317 */     MobEffectInstance $$7 = null;
/* 318 */     if ($$1.contains("hidden_effect", 10)) {
/* 319 */       $$7 = loadSpecifiedEffect($$0, $$1.getCompound("hidden_effect"));
/*     */     }
/*     */     
/* 322 */     if ($$1.contains("factor_calculation_data", 10)) {
/* 323 */       Objects.requireNonNull(LOGGER); Optional<FactorData> $$8 = FactorData.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$1.getCompound("factor_calculation_data"))).resultOrPartial(LOGGER::error);
/*     */     } else {
/* 325 */       $$9 = Optional.empty();
/*     */     } 
/* 327 */     return new MobEffectInstance($$0, $$3, Math.max($$2, 0), $$4, $$5, $$6, $$7, $$9);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(MobEffectInstance $$0) {
/* 332 */     int $$1 = 32147;
/* 333 */     if ((getDuration() > 32147 && $$0.getDuration() > 32147) || (isAmbient() && $$0.isAmbient()))
/*     */     {
/* 335 */       return ComparisonChain.start()
/* 336 */         .compare(Boolean.valueOf(isAmbient()), Boolean.valueOf($$0.isAmbient()))
/* 337 */         .compare(getEffect().getColor(), $$0.getEffect().getColor())
/* 338 */         .result();
/*     */     }
/* 340 */     return ComparisonChain.start()
/* 341 */       .compareFalseFirst(isAmbient(), $$0.isAmbient())
/* 342 */       .compareFalseFirst(isInfiniteDuration(), $$0.isInfiniteDuration())
/* 343 */       .compare(getDuration(), $$0.getDuration())
/* 344 */       .compare(getEffect().getColor(), $$0.getEffect().getColor())
/* 345 */       .result();
/*     */   }
/*     */   public static class FactorData { public static final Codec<FactorData> CODEC;
/*     */     static {
/* 349 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("padding_duration").forGetter(()), (App)Codec.FLOAT.fieldOf("factor_start").orElse(Float.valueOf(0.0F)).forGetter(()), (App)Codec.FLOAT.fieldOf("factor_target").orElse(Float.valueOf(1.0F)).forGetter(()), (App)Codec.FLOAT.fieldOf("factor_current").orElse(Float.valueOf(0.0F)).forGetter(()), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("ticks_active").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.FLOAT.fieldOf("factor_previous_frame").orElse(Float.valueOf(0.0F)).forGetter(()), (App)Codec.BOOL.fieldOf("had_effect_last_tick").orElse(Boolean.valueOf(false)).forGetter(())).apply((Applicative)$$0, FactorData::new));
/*     */     }
/*     */ 
/*     */     
/*     */     private final int paddingDuration;
/*     */     
/*     */     private float factorStart;
/*     */     
/*     */     private float factorTarget;
/*     */     
/*     */     private float factorCurrent;
/*     */     
/*     */     private int ticksActive;
/*     */     
/*     */     private float factorPreviousFrame;
/*     */     
/*     */     private boolean hadEffectLastTick;
/*     */     
/*     */     public FactorData(int $$0, float $$1, float $$2, float $$3, int $$4, float $$5, boolean $$6) {
/* 368 */       this.paddingDuration = $$0;
/* 369 */       this.factorStart = $$1;
/* 370 */       this.factorTarget = $$2;
/* 371 */       this.factorCurrent = $$3;
/* 372 */       this.ticksActive = $$4;
/* 373 */       this.factorPreviousFrame = $$5;
/* 374 */       this.hadEffectLastTick = $$6;
/*     */     }
/*     */     
/*     */     public FactorData(int $$0) {
/* 378 */       this($$0, 0.0F, 1.0F, 0.0F, 0, 0.0F, false);
/*     */     }
/*     */     
/*     */     public void tick(MobEffectInstance $$0) {
/* 382 */       this.factorPreviousFrame = this.factorCurrent;
/* 383 */       boolean $$1 = !$$0.endsWithin(this.paddingDuration);
/*     */       
/* 385 */       this.ticksActive++;
/* 386 */       if (this.hadEffectLastTick != $$1) {
/* 387 */         this.hadEffectLastTick = $$1;
/* 388 */         this.ticksActive = 0;
/* 389 */         this.factorStart = this.factorCurrent;
/* 390 */         this.factorTarget = $$1 ? 1.0F : 0.0F;
/*     */       } 
/*     */       
/* 393 */       float $$2 = Mth.clamp(this.ticksActive / this.paddingDuration, 0.0F, 1.0F);
/* 394 */       this.factorCurrent = Mth.lerp($$2, this.factorStart, this.factorTarget);
/*     */     }
/*     */     
/*     */     public float getFactor(LivingEntity $$0, float $$1) {
/* 398 */       if ($$0.isRemoved())
/*     */       {
/*     */ 
/*     */         
/* 402 */         this.factorPreviousFrame = this.factorCurrent;
/*     */       }
/*     */       
/* 405 */       return Mth.lerp($$1, this.factorPreviousFrame, this.factorCurrent);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\MobEffectInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */