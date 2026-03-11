/*    */ package net.minecraft.world.level.chunk;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.world.level.levelgen.DebugLevelSource;
/*    */ import net.minecraft.world.level.levelgen.FlatLevelSource;
/*    */ import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
/*    */ 
/*    */ public class ChunkGenerators {
/*    */   public static Codec<? extends ChunkGenerator> bootstrap(Registry<Codec<? extends ChunkGenerator>> $$0) {
/* 11 */     Registry.register($$0, "noise", NoiseBasedChunkGenerator.CODEC);
/* 12 */     Registry.register($$0, "flat", FlatLevelSource.CODEC);
/* 13 */     return (Codec<? extends ChunkGenerator>)Registry.register($$0, "debug", DebugLevelSource.CODEC);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\ChunkGenerators.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */