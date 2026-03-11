/*    */ package net.minecraft.core.particles;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class DustParticleOptions extends DustParticleOptionsBase {
/* 13 */   public static final Vector3f REDSTONE_PARTICLE_COLOR = Vec3.fromRGB24(16711680).toVector3f();
/* 14 */   public static final DustParticleOptions REDSTONE = new DustParticleOptions(REDSTONE_PARTICLE_COLOR, 1.0F); public static final Codec<DustParticleOptions> CODEC;
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.VECTOR3F.fieldOf("color").forGetter(()), (App)Codec.FLOAT.fieldOf("scale").forGetter(())).apply((Applicative)$$0, DustParticleOptions::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public static final ParticleOptions.Deserializer<DustParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<DustParticleOptions>()
/*    */     {
/*    */       public DustParticleOptions fromCommand(ParticleType<DustParticleOptions> $$0, StringReader $$1) throws CommandSyntaxException
/*    */       {
/* 25 */         Vector3f $$2 = DustParticleOptionsBase.readVector3f($$1);
/* 26 */         $$1.expect(' ');
/* 27 */         float $$3 = $$1.readFloat();
/* 28 */         return new DustParticleOptions($$2, $$3);
/*    */       }
/*    */ 
/*    */       
/*    */       public DustParticleOptions fromNetwork(ParticleType<DustParticleOptions> $$0, FriendlyByteBuf $$1) {
/* 33 */         return new DustParticleOptions(DustParticleOptionsBase.readVector3f($$1), $$1.readFloat());
/*    */       }
/*    */     };
/*    */   
/*    */   public DustParticleOptions(Vector3f $$0, float $$1) {
/* 38 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleType<DustParticleOptions> getType() {
/* 43 */     return ParticleTypes.DUST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\DustParticleOptions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */