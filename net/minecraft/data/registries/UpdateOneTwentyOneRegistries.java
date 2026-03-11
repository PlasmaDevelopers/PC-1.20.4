/*    */ package net.minecraft.data.registries;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.RegistrySetBuilder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.UpdateOneTwentyOnePools;
/*    */ import net.minecraft.data.worldgen.UpdateOneTwentyOneProcessorLists;
/*    */ import net.minecraft.data.worldgen.UpdateOneTwentyOneStructureSets;
/*    */ import net.minecraft.data.worldgen.UpdateOneTwentyOneStructures;
/*    */ 
/*    */ public class UpdateOneTwentyOneRegistries
/*    */ {
/* 14 */   private static final RegistrySetBuilder BUILDER = (new RegistrySetBuilder())
/* 15 */     .add(Registries.TEMPLATE_POOL, UpdateOneTwentyOnePools::bootstrap)
/* 16 */     .add(Registries.STRUCTURE, UpdateOneTwentyOneStructures::bootstrap)
/* 17 */     .add(Registries.STRUCTURE_SET, UpdateOneTwentyOneStructureSets::bootstrap)
/* 18 */     .add(Registries.PROCESSOR_LIST, UpdateOneTwentyOneProcessorLists::bootstrap);
/*    */   
/*    */   public static CompletableFuture<RegistrySetBuilder.PatchedRegistries> createLookup(CompletableFuture<HolderLookup.Provider> $$0) {
/* 21 */     return RegistryPatchGenerator.createLookup($$0, BUILDER);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\registries\UpdateOneTwentyOneRegistries.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */