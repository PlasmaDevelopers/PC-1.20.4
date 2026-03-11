/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.level.gameevent.BlockPositionSource;
/*    */ import net.minecraft.world.level.gameevent.PositionSource;
/*    */ import net.minecraft.world.level.gameevent.PositionSourceType;
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
/*    */   implements ParticleOptions.Deserializer<VibrationParticleOption>
/*    */ {
/*    */   public VibrationParticleOption fromCommand(ParticleType<VibrationParticleOption> $$0, StringReader $$1) throws CommandSyntaxException {
/* 26 */     $$1.expect(' ');
/* 27 */     float $$2 = (float)$$1.readDouble();
/* 28 */     $$1.expect(' ');
/* 29 */     float $$3 = (float)$$1.readDouble();
/* 30 */     $$1.expect(' ');
/* 31 */     float $$4 = (float)$$1.readDouble();
/* 32 */     $$1.expect(' ');
/* 33 */     int $$5 = $$1.readInt();
/*    */     
/* 35 */     BlockPos $$6 = BlockPos.containing($$2, $$3, $$4);
/*    */     
/* 37 */     return new VibrationParticleOption((PositionSource)new BlockPositionSource($$6), $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public VibrationParticleOption fromNetwork(ParticleType<VibrationParticleOption> $$0, FriendlyByteBuf $$1) {
/* 42 */     PositionSource $$2 = PositionSourceType.fromNetwork($$1);
/* 43 */     int $$3 = $$1.readVarInt();
/*    */     
/* 45 */     return new VibrationParticleOption($$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\VibrationParticleOption$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */