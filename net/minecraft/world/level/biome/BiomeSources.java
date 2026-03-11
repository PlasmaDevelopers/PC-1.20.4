/*    */ package net.minecraft.world.level.biome;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ 
/*    */ public class BiomeSources {
/*    */   public static Codec<? extends BiomeSource> bootstrap(Registry<Codec<? extends BiomeSource>> $$0) {
/*  8 */     Registry.register($$0, "fixed", FixedBiomeSource.CODEC);
/*  9 */     Registry.register($$0, "multi_noise", MultiNoiseBiomeSource.CODEC);
/* 10 */     Registry.register($$0, "checkerboard", CheckerboardColumnBiomeSource.CODEC);
/* 11 */     return (Codec<? extends BiomeSource>)Registry.register($$0, "the_end", TheEndBiomeSource.CODEC);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\BiomeSources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */