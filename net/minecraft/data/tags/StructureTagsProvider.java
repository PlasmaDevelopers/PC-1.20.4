/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.tags.StructureTags;
/*    */ import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ 
/*    */ public class StructureTagsProvider
/*    */   extends TagsProvider<Structure> {
/*    */   public StructureTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 14 */     super($$0, Registries.STRUCTURE, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 19 */     tag(StructureTags.VILLAGE)
/* 20 */       .add(BuiltinStructures.VILLAGE_PLAINS)
/* 21 */       .add(BuiltinStructures.VILLAGE_DESERT)
/* 22 */       .add(BuiltinStructures.VILLAGE_SAVANNA)
/* 23 */       .add(BuiltinStructures.VILLAGE_SNOWY)
/* 24 */       .add(BuiltinStructures.VILLAGE_TAIGA);
/*    */ 
/*    */     
/* 27 */     tag(StructureTags.MINESHAFT)
/* 28 */       .add(BuiltinStructures.MINESHAFT)
/* 29 */       .add(BuiltinStructures.MINESHAFT_MESA);
/*    */ 
/*    */     
/* 32 */     tag(StructureTags.OCEAN_RUIN)
/* 33 */       .add(BuiltinStructures.OCEAN_RUIN_COLD)
/* 34 */       .add(BuiltinStructures.OCEAN_RUIN_WARM);
/*    */ 
/*    */     
/* 37 */     tag(StructureTags.SHIPWRECK)
/* 38 */       .add(BuiltinStructures.SHIPWRECK)
/* 39 */       .add(BuiltinStructures.SHIPWRECK_BEACHED);
/*    */ 
/*    */     
/* 42 */     tag(StructureTags.RUINED_PORTAL)
/* 43 */       .add(BuiltinStructures.RUINED_PORTAL_DESERT)
/* 44 */       .add(BuiltinStructures.RUINED_PORTAL_JUNGLE)
/* 45 */       .add(BuiltinStructures.RUINED_PORTAL_MOUNTAIN)
/* 46 */       .add(BuiltinStructures.RUINED_PORTAL_NETHER)
/* 47 */       .add(BuiltinStructures.RUINED_PORTAL_OCEAN)
/* 48 */       .add(BuiltinStructures.RUINED_PORTAL_STANDARD)
/* 49 */       .add(BuiltinStructures.RUINED_PORTAL_SWAMP);
/*    */ 
/*    */     
/* 52 */     tag(StructureTags.CATS_SPAWN_IN)
/* 53 */       .add(BuiltinStructures.SWAMP_HUT);
/*    */ 
/*    */     
/* 56 */     tag(StructureTags.CATS_SPAWN_AS_BLACK)
/* 57 */       .add(BuiltinStructures.SWAMP_HUT);
/*    */ 
/*    */     
/* 60 */     tag(StructureTags.EYE_OF_ENDER_LOCATED)
/* 61 */       .add(BuiltinStructures.STRONGHOLD);
/*    */ 
/*    */     
/* 64 */     tag(StructureTags.DOLPHIN_LOCATED)
/* 65 */       .addTag(StructureTags.OCEAN_RUIN)
/* 66 */       .addTag(StructureTags.SHIPWRECK);
/*    */ 
/*    */     
/* 69 */     tag(StructureTags.ON_WOODLAND_EXPLORER_MAPS)
/* 70 */       .add(BuiltinStructures.WOODLAND_MANSION);
/*    */ 
/*    */     
/* 73 */     tag(StructureTags.ON_OCEAN_EXPLORER_MAPS)
/* 74 */       .add(BuiltinStructures.OCEAN_MONUMENT);
/*    */ 
/*    */     
/* 77 */     tag(StructureTags.ON_TREASURE_MAPS)
/* 78 */       .add(BuiltinStructures.BURIED_TREASURE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\StructureTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */