/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FlatLevelGeneratorPresetTags
/*    */ {
/* 12 */   public static final TagKey<FlatLevelGeneratorPreset> VISIBLE = create("visible");
/*    */   
/*    */   private static TagKey<FlatLevelGeneratorPreset> create(String $$0) {
/* 15 */     return TagKey.create(Registries.FLAT_LEVEL_GENERATOR_PRESET, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\FlatLevelGeneratorPresetTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */