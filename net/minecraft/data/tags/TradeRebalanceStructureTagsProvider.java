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
/*    */ public class TradeRebalanceStructureTagsProvider
/*    */   extends TagsProvider<Structure> {
/*    */   public TradeRebalanceStructureTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 14 */     super($$0, Registries.STRUCTURE, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 19 */     tag(StructureTags.ON_SAVANNA_VILLAGE_MAPS)
/* 20 */       .add(BuiltinStructures.VILLAGE_SAVANNA);
/*    */ 
/*    */     
/* 23 */     tag(StructureTags.ON_DESERT_VILLAGE_MAPS)
/* 24 */       .add(BuiltinStructures.VILLAGE_DESERT);
/*    */ 
/*    */     
/* 27 */     tag(StructureTags.ON_PLAINS_VILLAGE_MAPS)
/* 28 */       .add(BuiltinStructures.VILLAGE_PLAINS);
/*    */ 
/*    */     
/* 31 */     tag(StructureTags.ON_TAIGA_VILLAGE_MAPS)
/* 32 */       .add(BuiltinStructures.VILLAGE_TAIGA);
/*    */ 
/*    */     
/* 35 */     tag(StructureTags.ON_SNOWY_VILLAGE_MAPS)
/* 36 */       .add(BuiltinStructures.VILLAGE_SNOWY);
/*    */ 
/*    */     
/* 39 */     tag(StructureTags.ON_SWAMP_EXPLORER_MAPS)
/* 40 */       .add(BuiltinStructures.SWAMP_HUT);
/*    */ 
/*    */     
/* 43 */     tag(StructureTags.ON_JUNGLE_EXPLORER_MAPS)
/* 44 */       .add(BuiltinStructures.JUNGLE_TEMPLE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\TradeRebalanceStructureTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */