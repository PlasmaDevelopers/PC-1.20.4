/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum GrassColorModifier
/*     */   implements StringRepresentable
/*     */ {
/* 197 */   NONE("none")
/*     */   {
/*     */     public int modifyColor(double $$0, double $$1, int $$2) {
/* 200 */       return $$2;
/*     */     }
/*     */   },
/* 203 */   DARK_FOREST("dark_forest")
/*     */   {
/*     */     public int modifyColor(double $$0, double $$1, int $$2) {
/* 206 */       return ($$2 & 0xFEFEFE) + 2634762 >> 1;
/*     */     }
/*     */   },
/* 209 */   SWAMP("swamp")
/*     */   {
/*     */     public int modifyColor(double $$0, double $$1, int $$2) {
/* 212 */       double $$3 = Biome.BIOME_INFO_NOISE.getValue($$0 * 0.0225D, $$1 * 0.0225D, false);
/* 213 */       if ($$3 < -0.1D) {
/* 214 */         return 5011004;
/*     */       }
/* 216 */       return 6975545;
/*     */     }
/*     */   };
/*     */ 
/*     */   
/*     */   private final String name;
/*     */   public static final Codec<GrassColorModifier> CODEC;
/*     */   
/*     */   GrassColorModifier(String $$0) {
/* 225 */     this.name = $$0;
/*     */   }
/*     */   static {
/* 228 */     CODEC = (Codec<GrassColorModifier>)StringRepresentable.fromEnum(GrassColorModifier::values);
/*     */   }
/*     */   public String getName() {
/* 231 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 236 */     return this.name;
/*     */   }
/*     */   
/*     */   public abstract int modifyColor(double paramDouble1, double paramDouble2, int paramInt);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\BiomeSpecialEffects$GrassColorModifier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */