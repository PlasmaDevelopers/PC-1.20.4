/*     */ package net.minecraft.world.effect;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function7;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.LivingEntity;
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
/*     */ public class FactorData
/*     */ {
/*     */   public static final Codec<FactorData> CODEC;
/*     */   private final int paddingDuration;
/*     */   private float factorStart;
/*     */   private float factorTarget;
/*     */   private float factorCurrent;
/*     */   private int ticksActive;
/*     */   private float factorPreviousFrame;
/*     */   private boolean hadEffectLastTick;
/*     */   
/*     */   static {
/* 349 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("padding_duration").forGetter(()), (App)Codec.FLOAT.fieldOf("factor_start").orElse(Float.valueOf(0.0F)).forGetter(()), (App)Codec.FLOAT.fieldOf("factor_target").orElse(Float.valueOf(1.0F)).forGetter(()), (App)Codec.FLOAT.fieldOf("factor_current").orElse(Float.valueOf(0.0F)).forGetter(()), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("ticks_active").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.FLOAT.fieldOf("factor_previous_frame").orElse(Float.valueOf(0.0F)).forGetter(()), (App)Codec.BOOL.fieldOf("had_effect_last_tick").orElse(Boolean.valueOf(false)).forGetter(())).apply((Applicative)$$0, FactorData::new));
/*     */   }
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
/*     */   public FactorData(int $$0, float $$1, float $$2, float $$3, int $$4, float $$5, boolean $$6) {
/* 368 */     this.paddingDuration = $$0;
/* 369 */     this.factorStart = $$1;
/* 370 */     this.factorTarget = $$2;
/* 371 */     this.factorCurrent = $$3;
/* 372 */     this.ticksActive = $$4;
/* 373 */     this.factorPreviousFrame = $$5;
/* 374 */     this.hadEffectLastTick = $$6;
/*     */   }
/*     */   
/*     */   public FactorData(int $$0) {
/* 378 */     this($$0, 0.0F, 1.0F, 0.0F, 0, 0.0F, false);
/*     */   }
/*     */   
/*     */   public void tick(MobEffectInstance $$0) {
/* 382 */     this.factorPreviousFrame = this.factorCurrent;
/* 383 */     boolean $$1 = !$$0.endsWithin(this.paddingDuration);
/*     */     
/* 385 */     this.ticksActive++;
/* 386 */     if (this.hadEffectLastTick != $$1) {
/* 387 */       this.hadEffectLastTick = $$1;
/* 388 */       this.ticksActive = 0;
/* 389 */       this.factorStart = this.factorCurrent;
/* 390 */       this.factorTarget = $$1 ? 1.0F : 0.0F;
/*     */     } 
/*     */     
/* 393 */     float $$2 = Mth.clamp(this.ticksActive / this.paddingDuration, 0.0F, 1.0F);
/* 394 */     this.factorCurrent = Mth.lerp($$2, this.factorStart, this.factorTarget);
/*     */   }
/*     */   
/*     */   public float getFactor(LivingEntity $$0, float $$1) {
/* 398 */     if ($$0.isRemoved())
/*     */     {
/*     */ 
/*     */       
/* 402 */       this.factorPreviousFrame = this.factorCurrent;
/*     */     }
/*     */     
/* 405 */     return Mth.lerp($$1, this.factorPreviousFrame, this.factorCurrent);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\MobEffectInstance$FactorData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */