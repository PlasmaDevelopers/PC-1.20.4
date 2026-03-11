/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.tags.BiomeTags;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ 
/*    */ public class UpdateOneTwentyOneBiomeTagsProvider
/*    */   extends TagsProvider<Biome>
/*    */ {
/*    */   public UpdateOneTwentyOneBiomeTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1, CompletableFuture<TagsProvider.TagLookup<Biome>> $$2) {
/* 14 */     super($$0, Registries.BIOME, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 19 */     tag(BiomeTags.HAS_TRIAL_CHAMBERS).addTag(BiomeTags.IS_OVERWORLD);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\UpdateOneTwentyOneBiomeTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */