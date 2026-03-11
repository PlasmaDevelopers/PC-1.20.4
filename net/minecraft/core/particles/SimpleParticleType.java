/*    */ package net.minecraft.core.particles;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class SimpleParticleType extends ParticleType<SimpleParticleType> implements ParticleOptions {
/*  9 */   private static final ParticleOptions.Deserializer<SimpleParticleType> DESERIALIZER = new ParticleOptions.Deserializer<SimpleParticleType>()
/*    */     {
/*    */       public SimpleParticleType fromCommand(ParticleType<SimpleParticleType> $$0, StringReader $$1) {
/* 12 */         return (SimpleParticleType)$$0;
/*    */       }
/*    */ 
/*    */       
/*    */       public SimpleParticleType fromNetwork(ParticleType<SimpleParticleType> $$0, FriendlyByteBuf $$1) {
/* 17 */         return (SimpleParticleType)$$0;
/*    */       }
/*    */     };
/*    */   
/* 21 */   private final Codec<SimpleParticleType> codec = Codec.unit(this::getType);
/*    */   
/*    */   protected SimpleParticleType(boolean $$0) {
/* 24 */     super($$0, DESERIALIZER);
/*    */   }
/*    */ 
/*    */   
/*    */   public SimpleParticleType getType() {
/* 29 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public Codec<SimpleParticleType> codec() {
/* 34 */     return this.codec;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeToNetwork(FriendlyByteBuf $$0) {}
/*    */ 
/*    */   
/*    */   public String writeToString() {
/* 43 */     return BuiltInRegistries.PARTICLE_TYPE.getKey(this).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\SimpleParticleType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */