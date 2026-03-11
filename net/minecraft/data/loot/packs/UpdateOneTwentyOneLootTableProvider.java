/*    */ package net.minecraft.data.loot.packs;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.data.loot.LootTableProvider;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*    */ 
/*    */ public class UpdateOneTwentyOneLootTableProvider {
/*    */   public static LootTableProvider create(PackOutput $$0) {
/* 12 */     return new LootTableProvider($$0, 
/*    */         
/* 14 */         Set.of(), 
/* 15 */         List.of(new LootTableProvider.SubProviderEntry(UpdateOneTwentyOneBlockLoot::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(UpdateOneTwentyOneChestLoot::new, LootContextParamSets.CHEST)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\UpdateOneTwentyOneLootTableProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */