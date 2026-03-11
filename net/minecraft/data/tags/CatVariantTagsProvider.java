/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.CatVariantTags;
/*    */ import net.minecraft.world.entity.animal.CatVariant;
/*    */ 
/*    */ public class CatVariantTagsProvider extends TagsProvider<CatVariant> {
/*    */   public CatVariantTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 13 */     super($$0, Registries.CAT_VARIANT, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 18 */     tag(CatVariantTags.DEFAULT_SPAWNS)
/* 19 */       .add((ResourceKey<CatVariant>[])new ResourceKey[] { CatVariant.TABBY, CatVariant.BLACK, CatVariant.RED, CatVariant.SIAMESE, CatVariant.BRITISH_SHORTHAIR, CatVariant.CALICO, CatVariant.PERSIAN, CatVariant.RAGDOLL, CatVariant.WHITE, CatVariant.JELLIE });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 31 */     tag(CatVariantTags.FULL_MOON_SPAWNS)
/* 32 */       .addTag(CatVariantTags.DEFAULT_SPAWNS)
/* 33 */       .add(CatVariant.ALL_BLACK);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\CatVariantTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */