/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.tags.FlatLevelGeneratorPresetTags;
/*    */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
/*    */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPresets;
/*    */ 
/*    */ public class FlatLevelGeneratorPresetTagsProvider
/*    */   extends TagsProvider<FlatLevelGeneratorPreset> {
/*    */   public FlatLevelGeneratorPresetTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 14 */     super($$0, Registries.FLAT_LEVEL_GENERATOR_PRESET, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 19 */     tag(FlatLevelGeneratorPresetTags.VISIBLE)
/* 20 */       .add(FlatLevelGeneratorPresets.CLASSIC_FLAT)
/* 21 */       .add(FlatLevelGeneratorPresets.TUNNELERS_DREAM)
/* 22 */       .add(FlatLevelGeneratorPresets.WATER_WORLD)
/* 23 */       .add(FlatLevelGeneratorPresets.OVERWORLD)
/* 24 */       .add(FlatLevelGeneratorPresets.SNOWY_KINGDOM)
/* 25 */       .add(FlatLevelGeneratorPresets.BOTTOMLESS_PIT)
/* 26 */       .add(FlatLevelGeneratorPresets.DESERT)
/* 27 */       .add(FlatLevelGeneratorPresets.REDSTONE_READY)
/* 28 */       .add(FlatLevelGeneratorPresets.THE_VOID);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\FlatLevelGeneratorPresetTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */