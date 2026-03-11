/*    */ package net.minecraft.core.particles;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class DustColorTransitionOptions extends DustParticleOptionsBase {
/* 16 */   public static final Vector3f SCULK_PARTICLE_COLOR = Vec3.fromRGB24(3790560).toVector3f();
/* 17 */   public static final DustColorTransitionOptions SCULK_TO_REDSTONE = new DustColorTransitionOptions(SCULK_PARTICLE_COLOR, DustParticleOptions.REDSTONE_PARTICLE_COLOR, 1.0F); public static final Codec<DustColorTransitionOptions> CODEC;
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.VECTOR3F.fieldOf("fromColor").forGetter(()), (App)ExtraCodecs.VECTOR3F.fieldOf("toColor").forGetter(()), (App)Codec.FLOAT.fieldOf("scale").forGetter(())).apply((Applicative)$$0, DustColorTransitionOptions::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public static final ParticleOptions.Deserializer<DustColorTransitionOptions> DESERIALIZER = new ParticleOptions.Deserializer<DustColorTransitionOptions>()
/*    */     {
/*    */       public DustColorTransitionOptions fromCommand(ParticleType<DustColorTransitionOptions> $$0, StringReader $$1) throws CommandSyntaxException {
/* 28 */         Vector3f $$2 = DustParticleOptionsBase.readVector3f($$1);
/* 29 */         $$1.expect(' ');
/* 30 */         float $$3 = $$1.readFloat();
/* 31 */         Vector3f $$4 = DustParticleOptionsBase.readVector3f($$1);
/* 32 */         return new DustColorTransitionOptions($$2, $$4, $$3);
/*    */       }
/*    */ 
/*    */       
/*    */       public DustColorTransitionOptions fromNetwork(ParticleType<DustColorTransitionOptions> $$0, FriendlyByteBuf $$1) {
/* 37 */         Vector3f $$2 = DustParticleOptionsBase.readVector3f($$1);
/* 38 */         float $$3 = $$1.readFloat();
/* 39 */         Vector3f $$4 = DustParticleOptionsBase.readVector3f($$1);
/* 40 */         return new DustColorTransitionOptions($$2, $$4, $$3);
/*    */       }
/*    */     };
/*    */   
/*    */   private final Vector3f toColor;
/*    */   
/*    */   public DustColorTransitionOptions(Vector3f $$0, Vector3f $$1, float $$2) {
/* 47 */     super($$0, $$2);
/* 48 */     this.toColor = $$1;
/*    */   }
/*    */   
/*    */   public Vector3f getFromColor() {
/* 52 */     return this.color;
/*    */   }
/*    */   
/*    */   public Vector3f getToColor() {
/* 56 */     return this.toColor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNetwork(FriendlyByteBuf $$0) {
/* 61 */     super.writeToNetwork($$0);
/*    */     
/* 63 */     $$0.writeFloat(this.toColor.x());
/* 64 */     $$0.writeFloat(this.toColor.y());
/* 65 */     $$0.writeFloat(this.toColor.z());
/*    */   }
/*    */ 
/*    */   
/*    */   public String writeToString() {
/* 70 */     return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f %.2f", new Object[] { BuiltInRegistries.PARTICLE_TYPE.getKey(getType()), Float.valueOf(this.color.x()), Float.valueOf(this.color.y()), Float.valueOf(this.color.z()), Float.valueOf(this.scale), Float.valueOf(this.toColor.x()), Float.valueOf(this.toColor.y()), Float.valueOf(this.toColor.z()) });
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleType<DustColorTransitionOptions> getType() {
/* 75 */     return ParticleTypes.DUST_COLOR_TRANSITION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\DustColorTransitionOptions.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */