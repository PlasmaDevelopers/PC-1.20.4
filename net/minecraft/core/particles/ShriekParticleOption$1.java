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
/*    */   implements ParticleOptions.Deserializer<ShriekParticleOption>
/*    */ {
/*    */   public ShriekParticleOption fromCommand(ParticleType<ShriekParticleOption> $$0, StringReader $$1) throws CommandSyntaxException {
/* 20 */     $$1.expect(' ');
/* 21 */     int $$2 = $$1.readInt();
/* 22 */     return new ShriekParticleOption($$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public ShriekParticleOption fromNetwork(ParticleType<ShriekParticleOption> $$0, FriendlyByteBuf $$1) {
/* 27 */     return new ShriekParticleOption($$1.readVarInt());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\ShriekParticleOption$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */