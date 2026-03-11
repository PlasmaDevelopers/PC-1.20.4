/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.entity.BannerPattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BannerPatternTags
/*    */ {
/* 11 */   public static final TagKey<BannerPattern> NO_ITEM_REQUIRED = create("no_item_required");
/* 12 */   public static final TagKey<BannerPattern> PATTERN_ITEM_FLOWER = create("pattern_item/flower");
/* 13 */   public static final TagKey<BannerPattern> PATTERN_ITEM_CREEPER = create("pattern_item/creeper");
/* 14 */   public static final TagKey<BannerPattern> PATTERN_ITEM_SKULL = create("pattern_item/skull");
/* 15 */   public static final TagKey<BannerPattern> PATTERN_ITEM_MOJANG = create("pattern_item/mojang");
/* 16 */   public static final TagKey<BannerPattern> PATTERN_ITEM_GLOBE = create("pattern_item/globe");
/* 17 */   public static final TagKey<BannerPattern> PATTERN_ITEM_PIGLIN = create("pattern_item/piglin");
/*    */   
/*    */   private static TagKey<BannerPattern> create(String $$0) {
/* 20 */     return TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\BannerPatternTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */