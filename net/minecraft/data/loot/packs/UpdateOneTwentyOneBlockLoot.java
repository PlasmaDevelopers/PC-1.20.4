/*    */ package net.minecraft.data.loot.packs;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.data.loot.BlockLootSubProvider;
/*    */ import net.minecraft.world.flag.FeatureFlagSet;
/*    */ import net.minecraft.world.flag.FeatureFlags;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.storage.loot.LootTable;
/*    */ 
/*    */ public class UpdateOneTwentyOneBlockLoot
/*    */   extends BlockLootSubProvider
/*    */ {
/*    */   protected UpdateOneTwentyOneBlockLoot() {
/* 15 */     super(Set.of(), FeatureFlagSet.of(FeatureFlags.UPDATE_1_21));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void generate() {
/* 20 */     dropSelf(Blocks.CRAFTER);
/* 21 */     dropSelf(Blocks.CHISELED_TUFF);
/* 22 */     dropSelf(Blocks.TUFF_STAIRS);
/* 23 */     dropSelf(Blocks.TUFF_WALL);
/* 24 */     dropSelf(Blocks.POLISHED_TUFF);
/* 25 */     dropSelf(Blocks.POLISHED_TUFF_STAIRS);
/* 26 */     dropSelf(Blocks.POLISHED_TUFF_WALL);
/* 27 */     dropSelf(Blocks.TUFF_BRICKS);
/* 28 */     dropSelf(Blocks.TUFF_BRICK_STAIRS);
/* 29 */     dropSelf(Blocks.TUFF_BRICK_WALL);
/* 30 */     dropSelf(Blocks.CHISELED_TUFF_BRICKS);
/* 31 */     add(Blocks.TUFF_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/* 32 */     add(Blocks.TUFF_BRICK_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/* 33 */     add(Blocks.POLISHED_TUFF_SLAB, $$1 -> $$0.createSlabItemTable($$1));
/* 34 */     dropSelf(Blocks.CHISELED_COPPER);
/* 35 */     dropSelf(Blocks.EXPOSED_CHISELED_COPPER);
/* 36 */     dropSelf(Blocks.WEATHERED_CHISELED_COPPER);
/* 37 */     dropSelf(Blocks.OXIDIZED_CHISELED_COPPER);
/* 38 */     dropSelf(Blocks.WAXED_CHISELED_COPPER);
/* 39 */     dropSelf(Blocks.WAXED_EXPOSED_CHISELED_COPPER);
/* 40 */     dropSelf(Blocks.WAXED_WEATHERED_CHISELED_COPPER);
/* 41 */     dropSelf(Blocks.WAXED_OXIDIZED_CHISELED_COPPER);
/* 42 */     add(Blocks.COPPER_DOOR, $$1 -> $$0.createDoorTable($$1));
/* 43 */     add(Blocks.EXPOSED_COPPER_DOOR, $$1 -> $$0.createDoorTable($$1));
/* 44 */     add(Blocks.WEATHERED_COPPER_DOOR, $$1 -> $$0.createDoorTable($$1));
/* 45 */     add(Blocks.OXIDIZED_COPPER_DOOR, $$1 -> $$0.createDoorTable($$1));
/* 46 */     add(Blocks.WAXED_COPPER_DOOR, $$1 -> $$0.createDoorTable($$1));
/* 47 */     add(Blocks.WAXED_EXPOSED_COPPER_DOOR, $$1 -> $$0.createDoorTable($$1));
/* 48 */     add(Blocks.WAXED_WEATHERED_COPPER_DOOR, $$1 -> $$0.createDoorTable($$1));
/* 49 */     add(Blocks.WAXED_OXIDIZED_COPPER_DOOR, $$1 -> $$0.createDoorTable($$1));
/* 50 */     dropSelf(Blocks.COPPER_TRAPDOOR);
/* 51 */     dropSelf(Blocks.EXPOSED_COPPER_TRAPDOOR);
/* 52 */     dropSelf(Blocks.WEATHERED_COPPER_TRAPDOOR);
/* 53 */     dropSelf(Blocks.OXIDIZED_COPPER_TRAPDOOR);
/* 54 */     dropSelf(Blocks.WAXED_COPPER_TRAPDOOR);
/* 55 */     dropSelf(Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR);
/* 56 */     dropSelf(Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR);
/* 57 */     dropSelf(Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR);
/* 58 */     dropSelf(Blocks.COPPER_GRATE);
/* 59 */     dropSelf(Blocks.EXPOSED_COPPER_GRATE);
/* 60 */     dropSelf(Blocks.WEATHERED_COPPER_GRATE);
/* 61 */     dropSelf(Blocks.OXIDIZED_COPPER_GRATE);
/* 62 */     dropSelf(Blocks.WAXED_COPPER_GRATE);
/* 63 */     dropSelf(Blocks.WAXED_EXPOSED_COPPER_GRATE);
/* 64 */     dropSelf(Blocks.WAXED_WEATHERED_COPPER_GRATE);
/* 65 */     dropSelf(Blocks.WAXED_OXIDIZED_COPPER_GRATE);
/* 66 */     dropSelf(Blocks.COPPER_BULB);
/* 67 */     dropSelf(Blocks.EXPOSED_COPPER_BULB);
/* 68 */     dropSelf(Blocks.WEATHERED_COPPER_BULB);
/* 69 */     dropSelf(Blocks.OXIDIZED_COPPER_BULB);
/* 70 */     dropSelf(Blocks.WAXED_COPPER_BULB);
/* 71 */     dropSelf(Blocks.WAXED_EXPOSED_COPPER_BULB);
/* 72 */     dropSelf(Blocks.WAXED_WEATHERED_COPPER_BULB);
/* 73 */     dropSelf(Blocks.WAXED_OXIDIZED_COPPER_BULB);
/* 74 */     add(Blocks.TRIAL_SPAWNER, noDrop());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\packs\UpdateOneTwentyOneBlockLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */