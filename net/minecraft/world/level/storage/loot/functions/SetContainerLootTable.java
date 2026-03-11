/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.storage.loot.LootDataId;
/*    */ import net.minecraft.world.level.storage.loot.LootDataType;
/*    */ import net.minecraft.world.level.storage.loot.LootTable;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class SetContainerLootTable extends LootItemConditionalFunction {
/*    */   static {
/* 24 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)ResourceLocation.CODEC.fieldOf("name").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.LONG, "seed", Long.valueOf(0L)).forGetter(()), (App)BuiltInRegistries.BLOCK_ENTITY_TYPE.holderByNameCodec().fieldOf("type").forGetter(()))).apply((Applicative)$$0, SetContainerLootTable::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<SetContainerLootTable> CODEC;
/*    */   
/*    */   private final ResourceLocation name;
/*    */   private final long seed;
/*    */   private final Holder<BlockEntityType<?>> type;
/*    */   
/*    */   private SetContainerLootTable(List<LootItemCondition> $$0, ResourceLocation $$1, long $$2, Holder<BlockEntityType<?>> $$3) {
/* 35 */     super($$0);
/* 36 */     this.name = $$1;
/* 37 */     this.seed = $$2;
/* 38 */     this.type = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 43 */     return LootItemFunctions.SET_LOOT_TABLE;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 48 */     if ($$0.isEmpty()) {
/* 49 */       return $$0;
/*    */     }
/*    */     
/* 52 */     CompoundTag $$2 = BlockItem.getBlockEntityData($$0);
/* 53 */     if ($$2 == null) {
/* 54 */       $$2 = new CompoundTag();
/*    */     }
/*    */     
/* 57 */     $$2.putString("LootTable", this.name.toString());
/* 58 */     if (this.seed != 0L) {
/* 59 */       $$2.putLong("LootTableSeed", this.seed);
/*    */     }
/* 61 */     BlockItem.setBlockEntityData($$0, (BlockEntityType)this.type.value(), $$2);
/* 62 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 67 */     super.validate($$0);
/*    */     
/* 69 */     LootDataId<LootTable> $$1 = new LootDataId(LootDataType.TABLE, this.name);
/*    */     
/* 71 */     if ($$0.resolver().getElementOptional($$1).isEmpty()) {
/* 72 */       $$0.reportProblem("Missing loot table used for container: " + this.name);
/*    */     }
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> withLootTable(BlockEntityType<?> $$0, ResourceLocation $$1) {
/* 77 */     return simpleBuilder($$2 -> new SetContainerLootTable($$2, $$0, 0L, (Holder<BlockEntityType<?>>)$$1.builtInRegistryHolder()));
/*    */   }
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> withLootTable(BlockEntityType<?> $$0, ResourceLocation $$1, long $$2) {
/* 81 */     return simpleBuilder($$3 -> new SetContainerLootTable($$3, $$0, $$1, (Holder<BlockEntityType<?>>)$$2.builtInRegistryHolder()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetContainerLootTable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */