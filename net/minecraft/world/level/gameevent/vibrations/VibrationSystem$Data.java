/*     */ package net.minecraft.world.level.gameevent.vibrations;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Data
/*     */ {
/*     */   public static Codec<Data> CODEC;
/*     */   public static final String NBT_TAG_KEY = "listener";
/*     */   @Nullable
/*     */   VibrationInfo currentVibration;
/*     */   private int travelTimeInTicks;
/*     */   final VibrationSelector selectionStrategy;
/*     */   private boolean reloadVibrationParticle;
/*     */   
/*     */   static {
/* 153 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)VibrationInfo.CODEC.optionalFieldOf("event").forGetter(()), (App)VibrationSelector.CODEC.fieldOf("selector").forGetter(Data::getSelectionStrategy), (App)ExtraCodecs.NON_NEGATIVE_INT.fieldOf("event_delay").orElse(Integer.valueOf(0)).forGetter(Data::getTravelTimeInTicks)).apply((Applicative)$$0, ()));
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
/*     */   private Data(@Nullable VibrationInfo $$0, VibrationSelector $$1, int $$2, boolean $$3) {
/* 168 */     this.currentVibration = $$0;
/* 169 */     this.travelTimeInTicks = $$2;
/* 170 */     this.selectionStrategy = $$1;
/* 171 */     this.reloadVibrationParticle = $$3;
/*     */   }
/*     */   
/*     */   public Data() {
/* 175 */     this(null, new VibrationSelector(), 0, false);
/*     */   }
/*     */   
/*     */   public VibrationSelector getSelectionStrategy() {
/* 179 */     return this.selectionStrategy;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public VibrationInfo getCurrentVibration() {
/* 184 */     return this.currentVibration;
/*     */   }
/*     */   
/*     */   public void setCurrentVibration(@Nullable VibrationInfo $$0) {
/* 188 */     this.currentVibration = $$0;
/*     */   }
/*     */   
/*     */   public int getTravelTimeInTicks() {
/* 192 */     return this.travelTimeInTicks;
/*     */   }
/*     */   
/*     */   public void setTravelTimeInTicks(int $$0) {
/* 196 */     this.travelTimeInTicks = $$0;
/*     */   }
/*     */   
/*     */   public void decrementTravelTime() {
/* 200 */     this.travelTimeInTicks = Math.max(0, this.travelTimeInTicks - 1);
/*     */   }
/*     */   
/*     */   public boolean shouldReloadVibrationParticle() {
/* 204 */     return this.reloadVibrationParticle;
/*     */   }
/*     */   
/*     */   public void setReloadVibrationParticle(boolean $$0) {
/* 208 */     this.reloadVibrationParticle = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\vibrations\VibrationSystem$Data.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */