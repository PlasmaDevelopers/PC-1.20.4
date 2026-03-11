/*    */ package net.minecraft.data.worldgen;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*    */ 
/*    */ public class BastionBridgePools {
/*    */   public static void bootstrap(BootstapContext<StructureTemplatePool> $$0) {
/* 14 */     HolderGetter<StructureProcessorList> $$1 = $$0.lookup(Registries.PROCESSOR_LIST);
/* 15 */     Holder.Reference reference1 = $$1.getOrThrow(ProcessorLists.ENTRANCE_REPLACEMENT);
/* 16 */     Holder.Reference reference2 = $$1.getOrThrow(ProcessorLists.BASTION_GENERIC_DEGRADATION);
/* 17 */     Holder.Reference reference3 = $$1.getOrThrow(ProcessorLists.BRIDGE);
/* 18 */     Holder.Reference reference4 = $$1.getOrThrow(ProcessorLists.RAMPART_DEGRADATION);
/*    */     
/* 20 */     HolderGetter<StructureTemplatePool> $$6 = $$0.lookup(Registries.TEMPLATE_POOL);
/* 21 */     Holder.Reference reference5 = $$6.getOrThrow(Pools.EMPTY);
/*    */     
/* 23 */     Pools.register($$0, "bastion/bridge/starting_pieces", new StructureTemplatePool((Holder)reference5, 
/*    */           
/* 25 */           (List)ImmutableList.of(
/* 26 */             Pair.of(StructurePoolElement.single("bastion/bridge/starting_pieces/entrance", (Holder)reference1), Integer.valueOf(1)), 
/* 27 */             Pair.of(StructurePoolElement.single("bastion/bridge/starting_pieces/entrance_face", (Holder)reference2), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     Pools.register($$0, "bastion/bridge/bridge_pieces", new StructureTemplatePool((Holder)reference5, 
/*    */           
/* 34 */           (List)ImmutableList.of(
/* 35 */             Pair.of(StructurePoolElement.single("bastion/bridge/bridge_pieces/bridge", (Holder)reference3), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     Pools.register($$0, "bastion/bridge/legs", new StructureTemplatePool((Holder)reference5, 
/*    */           
/* 42 */           (List)ImmutableList.of(
/* 43 */             Pair.of(StructurePoolElement.single("bastion/bridge/legs/leg_0", (Holder)reference2), Integer.valueOf(1)), 
/* 44 */             Pair.of(StructurePoolElement.single("bastion/bridge/legs/leg_1", (Holder)reference2), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     Pools.register($$0, "bastion/bridge/walls", new StructureTemplatePool((Holder)reference5, 
/*    */           
/* 51 */           (List)ImmutableList.of(
/* 52 */             Pair.of(StructurePoolElement.single("bastion/bridge/walls/wall_base_0", (Holder)reference4), Integer.valueOf(1)), 
/* 53 */             Pair.of(StructurePoolElement.single("bastion/bridge/walls/wall_base_1", (Holder)reference4), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     Pools.register($$0, "bastion/bridge/ramparts", new StructureTemplatePool((Holder)reference5, 
/*    */           
/* 60 */           (List)ImmutableList.of(
/* 61 */             Pair.of(StructurePoolElement.single("bastion/bridge/ramparts/rampart_0", (Holder)reference4), Integer.valueOf(1)), 
/* 62 */             Pair.of(StructurePoolElement.single("bastion/bridge/ramparts/rampart_1", (Holder)reference4), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 67 */     Pools.register($$0, "bastion/bridge/rampart_plates", new StructureTemplatePool((Holder)reference5, 
/*    */           
/* 69 */           (List)ImmutableList.of(
/* 70 */             Pair.of(StructurePoolElement.single("bastion/bridge/rampart_plates/plate_0", (Holder)reference4), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 75 */     Pools.register($$0, "bastion/bridge/connectors", new StructureTemplatePool((Holder)reference5, 
/*    */           
/* 77 */           (List)ImmutableList.of(
/* 78 */             Pair.of(StructurePoolElement.single("bastion/bridge/connectors/back_bridge_top", (Holder)reference2), Integer.valueOf(1)), 
/* 79 */             Pair.of(StructurePoolElement.single("bastion/bridge/connectors/back_bridge_bottom", (Holder)reference2), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\BastionBridgePools.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */