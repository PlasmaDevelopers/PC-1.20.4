/*    */ package net.minecraft.data.worldgen;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*    */ import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
/*    */ import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
/*    */ import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
/*    */ 
/*    */ public interface UpdateOneTwentyOneStructureSets {
/*    */   static void bootstrap(BootstapContext<StructureSet> $$0) {
/* 14 */     HolderGetter<Structure> $$1 = $$0.lookup(Registries.STRUCTURE);
/* 15 */     $$0.register(BuiltinStructureSets.TRIAL_CHAMBERS, new StructureSet((Holder)$$1.getOrThrow(BuiltinStructures.TRIAL_CHAMBERS), (StructurePlacement)new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 94251327)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\UpdateOneTwentyOneStructureSets.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */