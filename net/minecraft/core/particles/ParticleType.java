/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ 
/*    */ public abstract class ParticleType<T extends ParticleOptions> {
/*    */   private final boolean overrideLimiter;
/*    */   private final ParticleOptions.Deserializer<T> deserializer;
/*    */   
/*    */   protected ParticleType(boolean $$0, ParticleOptions.Deserializer<T> $$1) {
/* 10 */     this.overrideLimiter = $$0;
/* 11 */     this.deserializer = $$1;
/*    */   }
/*    */   
/*    */   public boolean getOverrideLimiter() {
/* 15 */     return this.overrideLimiter;
/*    */   }
/*    */   
/*    */   public ParticleOptions.Deserializer<T> getDeserializer() {
/* 19 */     return this.deserializer;
/*    */   }
/*    */   
/*    */   public abstract Codec<T> codec();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\ParticleType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */