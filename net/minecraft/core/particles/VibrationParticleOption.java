/*    */ package net.minecraft.core.particles;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.level.gameevent.BlockPositionSource;
/*    */ import net.minecraft.world.level.gameevent.PositionSource;
/*    */ import net.minecraft.world.level.gameevent.PositionSourceType;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class VibrationParticleOption implements ParticleOptions {
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)PositionSource.CODEC.fieldOf("destination").forGetter(()), (App)Codec.INT.fieldOf("arrival_in_ticks").forGetter(())).apply((Applicative)$$0, VibrationParticleOption::new));
/*    */   }
/*    */   
/*    */   public static final Codec<VibrationParticleOption> CODEC;
/*    */   
/* 23 */   public static final ParticleOptions.Deserializer<VibrationParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<VibrationParticleOption>()
/*    */     {
/*    */       public VibrationParticleOption fromCommand(ParticleType<VibrationParticleOption> $$0, StringReader $$1) throws CommandSyntaxException {
/* 26 */         $$1.expect(' ');
/* 27 */         float $$2 = (float)$$1.readDouble();
/* 28 */         $$1.expect(' ');
/* 29 */         float $$3 = (float)$$1.readDouble();
/* 30 */         $$1.expect(' ');
/* 31 */         float $$4 = (float)$$1.readDouble();
/* 32 */         $$1.expect(' ');
/* 33 */         int $$5 = $$1.readInt();
/*    */         
/* 35 */         BlockPos $$6 = BlockPos.containing($$2, $$3, $$4);
/*    */         
/* 37 */         return new VibrationParticleOption((PositionSource)new BlockPositionSource($$6), $$5);
/*    */       }
/*    */ 
/*    */       
/*    */       public VibrationParticleOption fromNetwork(ParticleType<VibrationParticleOption> $$0, FriendlyByteBuf $$1) {
/* 42 */         PositionSource $$2 = PositionSourceType.fromNetwork($$1);
/* 43 */         int $$3 = $$1.readVarInt();
/*    */         
/* 45 */         return new VibrationParticleOption($$2, $$3);
/*    */       }
/*    */     };
/*    */   
/*    */   private final PositionSource destination;
/*    */   private final int arrivalInTicks;
/*    */   
/*    */   public VibrationParticleOption(PositionSource $$0, int $$1) {
/* 53 */     this.destination = $$0;
/* 54 */     this.arrivalInTicks = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNetwork(FriendlyByteBuf $$0) {
/* 59 */     PositionSourceType.toNetwork(this.destination, $$0);
/* 60 */     $$0.writeVarInt(this.arrivalInTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String writeToString() {
/* 66 */     Vec3 $$0 = this.destination.getPosition(null).get();
/* 67 */     double $$1 = $$0.x();
/* 68 */     double $$2 = $$0.y();
/* 69 */     double $$3 = $$0.z();
/*    */     
/* 71 */     return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %d", new Object[] { BuiltInRegistries.PARTICLE_TYPE.getKey(getType()), Double.valueOf($$1), Double.valueOf($$2), Double.valueOf($$3), Integer.valueOf(this.arrivalInTicks) });
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleType<VibrationParticleOption> getType() {
/* 76 */     return ParticleTypes.VIBRATION;
/*    */   }
/*    */   
/*    */   public PositionSource getDestination() {
/* 80 */     return this.destination;
/*    */   }
/*    */   
/*    */   public int getArrivalInTicks() {
/* 84 */     return this.arrivalInTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\VibrationParticleOption.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */