/*    */ package net.minecraft.world.level.biome;
/*    */ 
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class MultiNoiseBiomeSourceParameterLists {
/* 10 */   public static final ResourceKey<MultiNoiseBiomeSourceParameterList> NETHER = register("nether");
/* 11 */   public static final ResourceKey<MultiNoiseBiomeSourceParameterList> OVERWORLD = register("overworld");
/*    */   
/*    */   public static void bootstrap(BootstapContext<MultiNoiseBiomeSourceParameterList> $$0) {
/* 14 */     HolderGetter<Biome> $$1 = $$0.lookup(Registries.BIOME);
/* 15 */     $$0.register(NETHER, new MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.Preset.NETHER, $$1));
/* 16 */     $$0.register(OVERWORLD, new MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD, $$1));
/*    */   }
/*    */   
/*    */   private static ResourceKey<MultiNoiseBiomeSourceParameterList> register(String $$0) {
/* 20 */     return ResourceKey.create(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\MultiNoiseBiomeSourceParameterLists.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */