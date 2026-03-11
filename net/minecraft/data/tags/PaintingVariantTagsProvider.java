/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.PaintingVariantTags;
/*    */ import net.minecraft.world.entity.decoration.PaintingVariant;
/*    */ import net.minecraft.world.entity.decoration.PaintingVariants;
/*    */ 
/*    */ public class PaintingVariantTagsProvider extends TagsProvider<PaintingVariant> {
/*    */   public PaintingVariantTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 14 */     super($$0, Registries.PAINTING_VARIANT, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 19 */     tag(PaintingVariantTags.PLACEABLE)
/* 20 */       .add((ResourceKey<PaintingVariant>[])new ResourceKey[] { 
/*    */           PaintingVariants.KEBAB, PaintingVariants.AZTEC, PaintingVariants.ALBAN, PaintingVariants.AZTEC2, PaintingVariants.BOMB, PaintingVariants.PLANT, PaintingVariants.WASTELAND, PaintingVariants.POOL, PaintingVariants.COURBET, PaintingVariants.SEA, 
/*    */           PaintingVariants.SUNSET, PaintingVariants.CREEBET, PaintingVariants.WANDERER, PaintingVariants.GRAHAM, PaintingVariants.MATCH, PaintingVariants.BUST, PaintingVariants.STAGE, PaintingVariants.VOID, PaintingVariants.SKULL_AND_ROSES, PaintingVariants.WITHER, 
/*    */           PaintingVariants.FIGHTERS, PaintingVariants.POINTER, PaintingVariants.PIGSCENE, PaintingVariants.BURNING_SKULL, PaintingVariants.SKELETON, PaintingVariants.DONKEY_KONG });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\PaintingVariantTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */