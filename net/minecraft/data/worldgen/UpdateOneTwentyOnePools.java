/*    */ package net.minecraft.data.worldgen;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ 
/*    */ public class UpdateOneTwentyOnePools {
/*  9 */   public static final ResourceKey<StructureTemplatePool> EMPTY = createKey("empty");
/*    */   
/*    */   public static ResourceKey<StructureTemplatePool> createKey(String $$0) {
/* 12 */     return ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation($$0));
/*    */   }
/*    */   
/*    */   public static void register(BootstapContext<StructureTemplatePool> $$0, String $$1, StructureTemplatePool $$2) {
/* 16 */     Pools.register($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public static void bootstrap(BootstapContext<StructureTemplatePool> $$0) {
/* 20 */     TrialChambersStructurePools.bootstrap($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\UpdateOneTwentyOnePools.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */