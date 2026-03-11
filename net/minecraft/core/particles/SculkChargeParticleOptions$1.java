/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.network.FriendlyByteBuf;
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
/*    */   implements ParticleOptions.Deserializer<SculkChargeParticleOptions>
/*    */ {
/*    */   public SculkChargeParticleOptions fromCommand(ParticleType<SculkChargeParticleOptions> $$0, StringReader $$1) throws CommandSyntaxException {
/* 20 */     $$1.expect(' ');
/* 21 */     float $$2 = $$1.readFloat();
/* 22 */     return new SculkChargeParticleOptions($$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public SculkChargeParticleOptions fromNetwork(ParticleType<SculkChargeParticleOptions> $$0, FriendlyByteBuf $$1) {
/* 27 */     return new SculkChargeParticleOptions($$1.readFloat());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\SculkChargeParticleOptions$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */