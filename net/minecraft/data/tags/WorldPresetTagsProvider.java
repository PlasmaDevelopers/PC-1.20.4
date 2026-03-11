/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.tags.WorldPresetTags;
/*    */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*    */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*    */ 
/*    */ public class WorldPresetTagsProvider
/*    */   extends TagsProvider<WorldPreset> {
/*    */   public WorldPresetTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 14 */     super($$0, Registries.WORLD_PRESET, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 19 */     tag(WorldPresetTags.NORMAL)
/* 20 */       .add(WorldPresets.NORMAL)
/* 21 */       .add(WorldPresets.FLAT)
/* 22 */       .add(WorldPresets.LARGE_BIOMES)
/* 23 */       .add(WorldPresets.AMPLIFIED)
/* 24 */       .add(WorldPresets.SINGLE_BIOME_SURFACE);
/*    */ 
/*    */     
/* 27 */     tag(WorldPresetTags.EXTENDED)
/* 28 */       .addTag(WorldPresetTags.NORMAL)
/* 29 */       .add(WorldPresets.DEBUG);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\WorldPresetTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */