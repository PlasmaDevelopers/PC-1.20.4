/*    */ package net.minecraft.core.particles;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class ShriekParticleOption implements ParticleOptions {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("delay").forGetter(())).apply((Applicative)$$0, ShriekParticleOption::new));
/*    */   }
/*    */   public static final Codec<ShriekParticleOption> CODEC;
/*    */   
/* 17 */   public static final ParticleOptions.Deserializer<ShriekParticleOption> DESERIALIZER = new ParticleOptions.Deserializer<ShriekParticleOption>()
/*    */     {
/*    */       public ShriekParticleOption fromCommand(ParticleType<ShriekParticleOption> $$0, StringReader $$1) throws CommandSyntaxException {
/* 20 */         $$1.expect(' ');
/* 21 */         int $$2 = $$1.readInt();
/* 22 */         return new ShriekParticleOption($$2);
/*    */       }
/*    */ 
/*    */       
/*    */       public ShriekParticleOption fromNetwork(ParticleType<ShriekParticleOption> $$0, FriendlyByteBuf $$1) {
/* 27 */         return new ShriekParticleOption($$1.readVarInt());
/*    */       }
/*    */     };
/*    */   
/*    */   private final int delay;
/*    */   
/*    */   public ShriekParticleOption(int $$0) {
/* 34 */     this.delay = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNetwork(FriendlyByteBuf $$0) {
/* 39 */     $$0.writeVarInt(this.delay);
/*    */   }
/*    */ 
/*    */   
/*    */   public String writeToString() {
/* 44 */     return String.format(Locale.ROOT, "%s %d", new Object[] { BuiltInRegistries.PARTICLE_TYPE.getKey(getType()), Integer.valueOf(this.delay) });
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleType<ShriekParticleOption> getType() {
/* 49 */     return ParticleTypes.SHRIEK;
/*    */   }
/*    */   
/*    */   public int getDelay() {
/* 53 */     return this.delay;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\ShriekParticleOption.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */