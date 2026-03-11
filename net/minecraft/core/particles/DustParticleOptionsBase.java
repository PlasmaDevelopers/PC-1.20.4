/*    */ package net.minecraft.core.particles;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.util.Mth;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public abstract class DustParticleOptionsBase
/*    */   implements ParticleOptions
/*    */ {
/*    */   public static final float MIN_SCALE = 0.01F;
/*    */   public static final float MAX_SCALE = 4.0F;
/*    */   protected final Vector3f color;
/*    */   protected final float scale;
/*    */   
/*    */   public DustParticleOptionsBase(Vector3f $$0, float $$1) {
/* 20 */     this.color = $$0;
/* 21 */     this.scale = Mth.clamp($$1, 0.01F, 4.0F);
/*    */   }
/*    */   
/*    */   public static Vector3f readVector3f(StringReader $$0) throws CommandSyntaxException {
/* 25 */     $$0.expect(' ');
/* 26 */     float $$1 = $$0.readFloat();
/* 27 */     $$0.expect(' ');
/* 28 */     float $$2 = $$0.readFloat();
/* 29 */     $$0.expect(' ');
/* 30 */     float $$3 = $$0.readFloat();
/*    */     
/* 32 */     return new Vector3f($$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public static Vector3f readVector3f(FriendlyByteBuf $$0) {
/* 36 */     return new Vector3f($$0.readFloat(), $$0.readFloat(), $$0.readFloat());
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToNetwork(FriendlyByteBuf $$0) {
/* 41 */     $$0.writeFloat(this.color.x());
/* 42 */     $$0.writeFloat(this.color.y());
/* 43 */     $$0.writeFloat(this.color.z());
/* 44 */     $$0.writeFloat(this.scale);
/*    */   }
/*    */ 
/*    */   
/*    */   public String writeToString() {
/* 49 */     return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", new Object[] { BuiltInRegistries.PARTICLE_TYPE.getKey(getType()), Float.valueOf(this.color.x()), Float.valueOf(this.color.y()), Float.valueOf(this.color.z()), Float.valueOf(this.scale) });
/*    */   }
/*    */   
/*    */   public Vector3f getColor() {
/* 53 */     return this.color;
/*    */   }
/*    */   
/*    */   public float getScale() {
/* 57 */     return this.scale;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\particles\DustParticleOptionsBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */