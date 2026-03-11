/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.animal.CatVariant;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CatVariantTags
/*    */ {
/* 11 */   public static final TagKey<CatVariant> DEFAULT_SPAWNS = create("default_spawns");
/* 12 */   public static final TagKey<CatVariant> FULL_MOON_SPAWNS = create("full_moon_spawns");
/*    */   
/*    */   private static TagKey<CatVariant> create(String $$0) {
/* 15 */     return TagKey.create(Registries.CAT_VARIANT, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\CatVariantTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */