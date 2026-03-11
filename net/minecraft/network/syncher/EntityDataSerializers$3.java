/*     */ package net.minecraft.network.syncher;
/*     */ 
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleType;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class null
/*     */   implements EntityDataSerializer.ForValueType<ParticleOptions>
/*     */ {
/*     */   public void write(FriendlyByteBuf $$0, ParticleOptions $$1) {
/*  94 */     $$0.writeId((IdMap)BuiltInRegistries.PARTICLE_TYPE, $$1.getType());
/*  95 */     $$1.writeToNetwork($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleOptions read(FriendlyByteBuf $$0) {
/* 100 */     return readParticle($$0, (ParticleType<ParticleOptions>)$$0.readById((IdMap)BuiltInRegistries.PARTICLE_TYPE));
/*     */   }
/*     */   
/*     */   private <T extends ParticleOptions> T readParticle(FriendlyByteBuf $$0, ParticleType<T> $$1) {
/* 104 */     return (T)$$1.getDeserializer().fromNetwork($$1, $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\EntityDataSerializers$3.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */