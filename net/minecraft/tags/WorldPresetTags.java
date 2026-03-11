/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldPresetTags
/*    */ {
/* 12 */   public static final TagKey<WorldPreset> NORMAL = create("normal");
/*    */   
/* 14 */   public static final TagKey<WorldPreset> EXTENDED = create("extended");
/*    */   
/*    */   private static TagKey<WorldPreset> create(String $$0) {
/* 17 */     return TagKey.create(Registries.WORLD_PRESET, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\WorldPresetTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */