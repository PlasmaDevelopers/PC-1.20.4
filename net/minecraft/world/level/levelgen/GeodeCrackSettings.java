/*    */ package net.minecraft.world.level.levelgen;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ 
/*    */ public class GeodeCrackSettings {
/*    */   static {
/*  8 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)GeodeConfiguration.CHANCE_RANGE.fieldOf("generate_crack_chance").orElse(Double.valueOf(1.0D)).forGetter(()), (App)Codec.doubleRange(0.0D, 5.0D).fieldOf("base_crack_size").orElse(Double.valueOf(2.0D)).forGetter(()), (App)Codec.intRange(0, 10).fieldOf("crack_point_offset").orElse(Integer.valueOf(2)).forGetter(())).apply((Applicative)$$0, GeodeCrackSettings::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<GeodeCrackSettings> CODEC;
/*    */   
/*    */   public final double generateCrackChance;
/*    */   public final double baseCrackSize;
/*    */   public final int crackPointOffset;
/*    */   
/*    */   public GeodeCrackSettings(double $$0, double $$1, int $$2) {
/* 19 */     this.generateCrackChance = $$0;
/* 20 */     this.baseCrackSize = $$1;
/* 21 */     this.crackPointOffset = $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\GeodeCrackSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */