/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ParticleOptions.Deserializer<DustParticleOptions>
/*    */ {
/*    */   public DustParticleOptions fromCommand(ParticleType<DustParticleOptions> $$0, StringReader $$1) throws CommandSyntaxException {
/* 25 */     Vector3f $$2 = DustParticleOptionsBase.readVector3f($$1);
/* 26 */     $$1.expect(' ');
/* 27 */     float $$3 = $$1.readFloat();
/* 28 */     return new DustParticleOptions($$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public DustParticleOptions fromNetwork(ParticleType<DustParticleOptions> $$0, FriendlyByteBuf $$1) {
/* 33 */     return new DustParticleOptions(DustParticleOptionsBase.readVector3f($$1), $$1.readFloat());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\DustParticleOptions$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */