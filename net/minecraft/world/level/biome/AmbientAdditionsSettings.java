/*    */ package net.minecraft.world.level.biome;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ 
/*    */ public class AmbientAdditionsSettings {
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)SoundEvent.CODEC.fieldOf("sound").forGetter(()), (App)Codec.DOUBLE.fieldOf("tick_chance").forGetter(())).apply((Applicative)$$0, AmbientAdditionsSettings::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<AmbientAdditionsSettings> CODEC;
/*    */   private final Holder<SoundEvent> soundEvent;
/*    */   private final double tickChance;
/*    */   
/*    */   public AmbientAdditionsSettings(Holder<SoundEvent> $$0, double $$1) {
/* 18 */     this.soundEvent = $$0;
/* 19 */     this.tickChance = $$1;
/*    */   }
/*    */   
/*    */   public Holder<SoundEvent> getSoundEvent() {
/* 23 */     return this.soundEvent;
/*    */   }
/*    */   
/*    */   public double getTickChance() {
/* 27 */     return this.tickChance;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\AmbientAdditionsSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */