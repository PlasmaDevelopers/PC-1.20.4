/*    */ package net.minecraft.data.worldgen;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*    */ 
/*    */ public class AncientCityStructurePieces {
/* 14 */   public static final ResourceKey<StructureTemplatePool> START = Pools.createKey("ancient_city/city_center");
/*    */   
/*    */   public static void bootstrap(BootstapContext<StructureTemplatePool> $$0) {
/* 17 */     HolderGetter<StructureProcessorList> $$1 = $$0.lookup(Registries.PROCESSOR_LIST);
/* 18 */     Holder.Reference reference1 = $$1.getOrThrow(ProcessorLists.ANCIENT_CITY_START_DEGRADATION);
/*    */     
/* 20 */     HolderGetter<StructureTemplatePool> $$3 = $$0.lookup(Registries.TEMPLATE_POOL);
/* 21 */     Holder.Reference reference2 = $$3.getOrThrow(Pools.EMPTY);
/*    */     
/* 23 */     $$0.register(START, new StructureTemplatePool((Holder)reference2, 
/*    */           
/* 25 */           (List)ImmutableList.of(
/* 26 */             Pair.of(StructurePoolElement.single("ancient_city/city_center/city_center_1", (Holder)reference1), Integer.valueOf(1)), 
/* 27 */             Pair.of(StructurePoolElement.single("ancient_city/city_center/city_center_2", (Holder)reference1), Integer.valueOf(1)), 
/* 28 */             Pair.of(StructurePoolElement.single("ancient_city/city_center/city_center_3", (Holder)reference1), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     AncientCityStructurePools.bootstrap($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\AncientCityStructurePieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */