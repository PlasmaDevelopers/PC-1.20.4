/*    */ package net.minecraft.world.level.biome;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ 
/*    */ public class AmbientMoodSettings {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)SoundEvent.CODEC.fieldOf("sound").forGetter(()), (App)Codec.INT.fieldOf("tick_delay").forGetter(()), (App)Codec.INT.fieldOf("block_search_extent").forGetter(()), (App)Codec.DOUBLE.fieldOf("offset").forGetter(())).apply((Applicative)$$0, AmbientMoodSettings::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static final Codec<AmbientMoodSettings> CODEC;
/*    */   
/* 18 */   public static final AmbientMoodSettings LEGACY_CAVE_SETTINGS = new AmbientMoodSettings((Holder<SoundEvent>)SoundEvents.AMBIENT_CAVE, 6000, 8, 2.0D);
/*    */ 
/*    */   
/*    */   private final Holder<SoundEvent> soundEvent;
/*    */   
/*    */   private final int tickDelay;
/*    */   
/*    */   private final int blockSearchExtent;
/*    */   
/*    */   private final double soundPositionOffset;
/*    */ 
/*    */   
/*    */   public AmbientMoodSettings(Holder<SoundEvent> $$0, int $$1, int $$2, double $$3) {
/* 31 */     this.soundEvent = $$0;
/* 32 */     this.tickDelay = $$1;
/* 33 */     this.blockSearchExtent = $$2;
/* 34 */     this.soundPositionOffset = $$3;
/*    */   }
/*    */   
/*    */   public Holder<SoundEvent> getSoundEvent() {
/* 38 */     return this.soundEvent;
/*    */   }
/*    */   
/*    */   public int getTickDelay() {
/* 42 */     return this.tickDelay;
/*    */   }
/*    */   
/*    */   public int getBlockSearchExtent() {
/* 46 */     return this.blockSearchExtent;
/*    */   }
/*    */   
/*    */   public double getSoundPositionOffset() {
/* 50 */     return this.soundPositionOffset;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\AmbientMoodSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */