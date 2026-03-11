/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ParticleOptions.Deserializer<SimpleParticleType>
/*    */ {
/*    */   public SimpleParticleType fromCommand(ParticleType<SimpleParticleType> $$0, StringReader $$1) {
/* 12 */     return (SimpleParticleType)$$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public SimpleParticleType fromNetwork(ParticleType<SimpleParticleType> $$0, FriendlyByteBuf $$1) {
/* 17 */     return (SimpleParticleType)$$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\SimpleParticleType$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */