/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public final class SculkChargeParticleOptions extends Record implements ParticleOptions {
/*    */   private final float roll;
/*    */   public static final Codec<SculkChargeParticleOptions> CODEC;
/*    */   
/* 12 */   public SculkChargeParticleOptions(float $$0) { this.roll = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/core/particles/SculkChargeParticleOptions;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/core/particles/SculkChargeParticleOptions; } public float roll() { return this.roll; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/particles/SculkChargeParticleOptions;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/core/particles/SculkChargeParticleOptions; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/core/particles/SculkChargeParticleOptions;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/core/particles/SculkChargeParticleOptions;
/* 13 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("roll").forGetter(())).apply((Applicative)$$0, SculkChargeParticleOptions::new)); }
/*    */ 
/*    */ 
/*    */   
/* 17 */   public static final ParticleOptions.Deserializer<SculkChargeParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<SculkChargeParticleOptions>()
/*    */     {
/*    */       public SculkChargeParticleOptions fromCommand(ParticleType<SculkChargeParticleOptions> $$0, StringReader $$1) throws CommandSyntaxException {
/* 20 */         $$1.expect(' ');
/* 21 */         float $$2 = $$1.readFloat();
/* 22 */         return new SculkChargeParticleOptions($$2);
/*    */       }
/*    */ 
/*    */       
/*    */       public SculkChargeParticleOptions fromNetwork(ParticleType<SculkChargeParticleOptions> $$0, FriendlyByteBuf $$1) {
/* 27 */         return new SculkChargeParticleOptions($$1.readFloat());
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public ParticleType<SculkChargeParticleOptions> getType() {
/* 33 */     return ParticleTypes.SCULK_CHARGE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNetwork(FriendlyByteBuf $$0) {
/* 38 */     $$0.writeFloat(this.roll);
/*    */   }
/*    */ 
/*    */   
/*    */   public String writeToString() {
/* 43 */     return String.format(Locale.ROOT, "%s %.2f", new Object[] { BuiltInRegistries.PARTICLE_TYPE.getKey(getType()), Float.valueOf(this.roll) });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\SculkChargeParticleOptions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */