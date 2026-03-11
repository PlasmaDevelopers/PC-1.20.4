/*    */ package net.minecraft.world.level.dimension;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class BuiltinDimensionTypes {
/*  8 */   public static final ResourceKey<DimensionType> OVERWORLD = register("overworld");
/*  9 */   public static final ResourceKey<DimensionType> NETHER = register("the_nether");
/* 10 */   public static final ResourceKey<DimensionType> END = register("the_end");
/* 11 */   public static final ResourceKey<DimensionType> OVERWORLD_CAVES = register("overworld_caves");
/*    */   
/* 13 */   public static final ResourceLocation OVERWORLD_EFFECTS = new ResourceLocation("overworld");
/* 14 */   public static final ResourceLocation NETHER_EFFECTS = new ResourceLocation("the_nether");
/* 15 */   public static final ResourceLocation END_EFFECTS = new ResourceLocation("the_end");
/*    */   
/*    */   private static ResourceKey<DimensionType> register(String $$0) {
/* 18 */     return ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\dimension\BuiltinDimensionTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */