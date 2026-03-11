/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.BannerPatternTags;
/*    */ import net.minecraft.world.level.block.entity.BannerPattern;
/*    */ import net.minecraft.world.level.block.entity.BannerPatterns;
/*    */ 
/*    */ public class BannerPatternTagsProvider extends TagsProvider<BannerPattern> {
/*    */   public BannerPatternTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 14 */     super($$0, Registries.BANNER_PATTERN, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 19 */     tag(BannerPatternTags.NO_ITEM_REQUIRED)
/* 20 */       .add((ResourceKey<BannerPattern>[])new ResourceKey[] { 
/*    */           BannerPatterns.SQUARE_BOTTOM_LEFT, BannerPatterns.SQUARE_BOTTOM_RIGHT, BannerPatterns.SQUARE_TOP_LEFT, BannerPatterns.SQUARE_TOP_RIGHT, BannerPatterns.STRIPE_BOTTOM, BannerPatterns.STRIPE_TOP, BannerPatterns.STRIPE_LEFT, BannerPatterns.STRIPE_RIGHT, BannerPatterns.STRIPE_CENTER, BannerPatterns.STRIPE_MIDDLE, 
/*    */           BannerPatterns.STRIPE_DOWNRIGHT, BannerPatterns.STRIPE_DOWNLEFT, BannerPatterns.STRIPE_SMALL, BannerPatterns.CROSS, BannerPatterns.STRAIGHT_CROSS, BannerPatterns.TRIANGLE_BOTTOM, BannerPatterns.TRIANGLE_TOP, BannerPatterns.TRIANGLES_BOTTOM, BannerPatterns.TRIANGLES_TOP, BannerPatterns.DIAGONAL_LEFT, 
/*    */           BannerPatterns.DIAGONAL_RIGHT, BannerPatterns.DIAGONAL_LEFT_MIRROR, BannerPatterns.DIAGONAL_RIGHT_MIRROR, BannerPatterns.CIRCLE_MIDDLE, BannerPatterns.RHOMBUS_MIDDLE, BannerPatterns.HALF_VERTICAL, BannerPatterns.HALF_HORIZONTAL, BannerPatterns.HALF_VERTICAL_MIRROR, BannerPatterns.HALF_HORIZONTAL_MIRROR, BannerPatterns.BORDER, 
/*    */           BannerPatterns.CURLY_BORDER, BannerPatterns.GRADIENT, BannerPatterns.GRADIENT_UP, BannerPatterns.BRICKS });
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
/* 57 */     tag(BannerPatternTags.PATTERN_ITEM_FLOWER)
/* 58 */       .add(BannerPatterns.FLOWER);
/*    */     
/* 60 */     tag(BannerPatternTags.PATTERN_ITEM_CREEPER)
/* 61 */       .add(BannerPatterns.CREEPER);
/*    */     
/* 63 */     tag(BannerPatternTags.PATTERN_ITEM_SKULL)
/* 64 */       .add(BannerPatterns.SKULL);
/*    */     
/* 66 */     tag(BannerPatternTags.PATTERN_ITEM_MOJANG)
/* 67 */       .add(BannerPatterns.MOJANG);
/*    */     
/* 69 */     tag(BannerPatternTags.PATTERN_ITEM_GLOBE)
/* 70 */       .add(BannerPatterns.GLOBE);
/*    */     
/* 72 */     tag(BannerPatternTags.PATTERN_ITEM_PIGLIN)
/* 73 */       .add(BannerPatterns.PIGLIN);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\BannerPatternTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */