/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.EntityTypeTags;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ 
/*    */ public class UpdateOneTwentyOneEntityTypeTagsProvider extends IntrinsicHolderTagsProvider<EntityType<?>> {
/*    */   public UpdateOneTwentyOneEntityTypeTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 13 */     super($$0, Registries.ENTITY_TYPE, $$1, $$0 -> $$0.builtInRegistryHolder().key());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 18 */     tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(EntityType.BREEZE);
/* 19 */     tag(EntityTypeTags.DEFLECTS_ARROWS).add(EntityType.BREEZE);
/* 20 */     tag(EntityTypeTags.DEFLECTS_TRIDENTS).add(EntityType.BREEZE);
/* 21 */     tag(EntityTypeTags.CAN_TURN_IN_BOATS).add(EntityType.BREEZE);
/* 22 */     tag(EntityTypeTags.IMPACT_PROJECTILES).add(EntityType.WIND_CHARGE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\UpdateOneTwentyOneEntityTypeTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */