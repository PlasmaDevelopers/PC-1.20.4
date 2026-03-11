/*    */ package net.minecraft.world.level.biome;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ 
/*    */ public class AmbientParticleSettings {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ParticleTypes.CODEC.fieldOf("options").forGetter(()), (App)Codec.FLOAT.fieldOf("probability").forGetter(())).apply((Applicative)$$0, AmbientParticleSettings::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<AmbientParticleSettings> CODEC;
/*    */   private final ParticleOptions options;
/*    */   private final float probability;
/*    */   
/*    */   public AmbientParticleSettings(ParticleOptions $$0, float $$1) {
/* 19 */     this.options = $$0;
/* 20 */     this.probability = $$1;
/*    */   }
/*    */   
/*    */   public ParticleOptions getOptions() {
/* 24 */     return this.options;
/*    */   }
/*    */   
/*    */   public boolean canSpawn(RandomSource $$0) {
/* 28 */     return ($$0.nextFloat() <= this.probability);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\AmbientParticleSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */