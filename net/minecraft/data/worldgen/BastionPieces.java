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
/*    */ public class BastionPieces {
/* 14 */   public static final ResourceKey<StructureTemplatePool> START = Pools.createKey("bastion/starts");
/*    */   
/*    */   public static void bootstrap(BootstapContext<StructureTemplatePool> $$0) {
/* 17 */     HolderGetter<StructureProcessorList> $$1 = $$0.lookup(Registries.PROCESSOR_LIST);
/* 18 */     Holder.Reference reference1 = $$1.getOrThrow(ProcessorLists.BASTION_GENERIC_DEGRADATION);
/*    */     
/* 20 */     HolderGetter<StructureTemplatePool> $$3 = $$0.lookup(Registries.TEMPLATE_POOL);
/* 21 */     Holder.Reference reference2 = $$3.getOrThrow(Pools.EMPTY);
/*    */     
/* 23 */     $$0.register(START, new StructureTemplatePool((Holder)reference2, 
/*    */           
/* 25 */           (List)ImmutableList.of(
/* 26 */             Pair.of(StructurePoolElement.single("bastion/units/air_base", (Holder)reference1), Integer.valueOf(1)), 
/* 27 */             Pair.of(StructurePoolElement.single("bastion/hoglin_stable/air_base", (Holder)reference1), Integer.valueOf(1)), 
/* 28 */             Pair.of(StructurePoolElement.single("bastion/treasure/big_air_full", (Holder)reference1), Integer.valueOf(1)), 
/* 29 */             Pair.of(StructurePoolElement.single("bastion/bridge/starting_pieces/entrance_base", (Holder)reference1), Integer.valueOf(1))), StructureTemplatePool.Projection.RIGID));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 34 */     BastionHousingUnitsPools.bootstrap($$0);
/* 35 */     BastionHoglinStablePools.bootstrap($$0);
/* 36 */     BastionTreasureRoomPools.bootstrap($$0);
/* 37 */     BastionBridgePools.bootstrap($$0);
/* 38 */     BastionSharedPools.bootstrap($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\BastionPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */