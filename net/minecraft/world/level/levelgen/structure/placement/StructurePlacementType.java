/*    */ package net.minecraft.world.level.levelgen.structure.placement;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public interface StructurePlacementType<SP extends StructurePlacement> {
/*  8 */   public static final StructurePlacementType<RandomSpreadStructurePlacement> RANDOM_SPREAD = register("random_spread", RandomSpreadStructurePlacement.CODEC);
/*  9 */   public static final StructurePlacementType<ConcentricRingsStructurePlacement> CONCENTRIC_RINGS = register("concentric_rings", ConcentricRingsStructurePlacement.CODEC);
/*    */   
/*    */   Codec<SP> codec();
/*    */   
/*    */   private static <SP extends StructurePlacement> StructurePlacementType<SP> register(String $$0, Codec<SP> $$1) {
/* 14 */     return (StructurePlacementType<SP>)Registry.register(BuiltInRegistries.STRUCTURE_PLACEMENT, $$0, () -> $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\placement\StructurePlacementType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */