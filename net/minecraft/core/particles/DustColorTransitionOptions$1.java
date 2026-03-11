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
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ParticleOptions.Deserializer<DustColorTransitionOptions>
/*    */ {
/*    */   public DustColorTransitionOptions fromCommand(ParticleType<DustColorTransitionOptions> $$0, StringReader $$1) throws CommandSyntaxException {
/* 28 */     Vector3f $$2 = DustParticleOptionsBase.readVector3f($$1);
/* 29 */     $$1.expect(' ');
/* 30 */     float $$3 = $$1.readFloat();
/* 31 */     Vector3f $$4 = DustParticleOptionsBase.readVector3f($$1);
/* 32 */     return new DustColorTransitionOptions($$2, $$4, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public DustColorTransitionOptions fromNetwork(ParticleType<DustColorTransitionOptions> $$0, FriendlyByteBuf $$1) {
/* 37 */     Vector3f $$2 = DustParticleOptionsBase.readVector3f($$1);
/* 38 */     float $$3 = $$1.readFloat();
/* 39 */     Vector3f $$4 = DustParticleOptionsBase.readVector3f($$1);
/* 40 */     return new DustColorTransitionOptions($$2, $$4, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\DustColorTransitionOptions$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */