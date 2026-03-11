/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class LootPoolEntries {
/*  9 */   public static final Codec<LootPoolEntryContainer> CODEC = BuiltInRegistries.LOOT_POOL_ENTRY_TYPE.byNameCodec()
/* 10 */     .dispatch(LootPoolEntryContainer::getType, LootPoolEntryType::codec);
/*    */   
/* 12 */   public static final LootPoolEntryType EMPTY = register("empty", (Codec)EmptyLootItem.CODEC);
/* 13 */   public static final LootPoolEntryType ITEM = register("item", (Codec)LootItem.CODEC);
/* 14 */   public static final LootPoolEntryType REFERENCE = register("loot_table", (Codec)LootTableReference.CODEC);
/* 15 */   public static final LootPoolEntryType DYNAMIC = register("dynamic", (Codec)DynamicLoot.CODEC);
/* 16 */   public static final LootPoolEntryType TAG = register("tag", (Codec)TagEntry.CODEC);
/*    */   
/* 18 */   public static final LootPoolEntryType ALTERNATIVES = register("alternatives", (Codec)AlternativesEntry.CODEC);
/* 19 */   public static final LootPoolEntryType SEQUENCE = register("sequence", (Codec)SequentialEntry.CODEC);
/* 20 */   public static final LootPoolEntryType GROUP = register("group", (Codec)EntryGroup.CODEC);
/*    */   
/*    */   private static LootPoolEntryType register(String $$0, Codec<? extends LootPoolEntryContainer> $$1) {
/* 23 */     return (LootPoolEntryType)Registry.register(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE, new ResourceLocation($$0), new LootPoolEntryType($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\LootPoolEntries.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */