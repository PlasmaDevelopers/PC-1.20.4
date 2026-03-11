/*    */ package net.minecraft.data.loot.packs;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.data.loot.LootTableProvider;
/*    */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*    */ 
/*    */ public class VanillaLootTableProvider
/*    */ {
/*    */   public static LootTableProvider create(PackOutput $$0) {
/* 13 */     return new LootTableProvider($$0, 
/*    */         
/* 15 */         BuiltInLootTables.all(), 
/* 16 */         List.of(new LootTableProvider.SubProviderEntry(VanillaFishingLoot::new, LootContextParamSets.FISHING), new LootTableProvider.SubProviderEntry(VanillaChestLoot::new, LootContextParamSets.CHEST), new LootTableProvider.SubProviderEntry(VanillaEntityLoot::new, LootContextParamSets.ENTITY), new LootTableProvider.SubProviderEntry(VanillaBlockLoot::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(VanillaPiglinBarterLoot::new, LootContextParamSets.PIGLIN_BARTER), new LootTableProvider.SubProviderEntry(VanillaGiftLoot::new, LootContextParamSets.GIFT), new LootTableProvider.SubProviderEntry(VanillaArchaeologyLoot::new, LootContextParamSets.ARCHAEOLOGY)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\VanillaLootTableProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */