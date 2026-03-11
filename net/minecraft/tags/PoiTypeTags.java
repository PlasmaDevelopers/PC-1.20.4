/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PoiTypeTags
/*    */ {
/* 11 */   public static final TagKey<PoiType> ACQUIRABLE_JOB_SITE = create("acquirable_job_site");
/* 12 */   public static final TagKey<PoiType> VILLAGE = create("village");
/* 13 */   public static final TagKey<PoiType> BEE_HOME = create("bee_home");
/*    */   
/*    */   private static TagKey<PoiType> create(String $$0) {
/* 16 */     return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\PoiTypeTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */