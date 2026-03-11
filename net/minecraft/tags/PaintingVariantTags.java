/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.decoration.PaintingVariant;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PaintingVariantTags
/*    */ {
/* 11 */   public static final TagKey<PaintingVariant> PLACEABLE = create("placeable");
/*    */   
/*    */   private static TagKey<PaintingVariant> create(String $$0) {
/* 14 */     return TagKey.create(Registries.PAINTING_VARIANT, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\PaintingVariantTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */