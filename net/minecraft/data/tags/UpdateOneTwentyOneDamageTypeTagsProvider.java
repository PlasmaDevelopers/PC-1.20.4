/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.DamageTypeTags;
/*    */ import net.minecraft.world.damagesource.DamageType;
/*    */ import net.minecraft.world.damagesource.DamageTypes;
/*    */ 
/*    */ public class UpdateOneTwentyOneDamageTypeTagsProvider
/*    */   extends TagsProvider<DamageType> {
/*    */   public UpdateOneTwentyOneDamageTypeTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 15 */     super($$0, Registries.DAMAGE_TYPE, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 20 */     tag(DamageTypeTags.BREEZE_IMMUNE_TO).add((ResourceKey<DamageType>[])new ResourceKey[] { DamageTypes.ARROW, DamageTypes.TRIDENT });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\UpdateOneTwentyOneDamageTypeTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */