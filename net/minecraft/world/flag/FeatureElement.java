/*    */ package net.minecraft.world.flag;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface FeatureElement
/*    */ {
/* 18 */   public static final Set<ResourceKey<? extends Registry<? extends FeatureElement>>> FILTERED_REGISTRIES = Set.of(Registries.ITEM, Registries.BLOCK, Registries.ENTITY_TYPE, Registries.MENU);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   FeatureFlagSet requiredFeatures();
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean isEnabled(FeatureFlagSet $$0) {
/* 28 */     return requiredFeatures().isSubsetOf($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\flag\FeatureElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */