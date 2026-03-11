/*    */ package net.minecraft.data.worldgen;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ 
/*    */ public class Pools {
/* 12 */   public static final ResourceKey<StructureTemplatePool> EMPTY = createKey("empty");
/*    */   
/*    */   public static ResourceKey<StructureTemplatePool> createKey(String $$0) {
/* 15 */     return ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation($$0));
/*    */   }
/*    */   
/*    */   public static void register(BootstapContext<StructureTemplatePool> $$0, String $$1, StructureTemplatePool $$2) {
/* 19 */     $$0.register(createKey($$1), $$2);
/*    */   }
/*    */   
/*    */   public static void bootstrap(BootstapContext<StructureTemplatePool> $$0) {
/* 23 */     HolderGetter<StructureTemplatePool> $$1 = $$0.lookup(Registries.TEMPLATE_POOL);
/* 24 */     Holder.Reference reference = $$1.getOrThrow(EMPTY);
/*    */ 
/*    */     
/* 27 */     $$0.register(EMPTY, new StructureTemplatePool((Holder)reference, (List)ImmutableList.of(), StructureTemplatePool.Projection.RIGID));
/*    */     
/* 29 */     BastionPieces.bootstrap($$0);
/* 30 */     PillagerOutpostPools.bootstrap($$0);
/* 31 */     VillagePools.bootstrap($$0);
/* 32 */     AncientCityStructurePieces.bootstrap($$0);
/* 33 */     TrailRuinsStructurePools.bootstrap($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\Pools.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */