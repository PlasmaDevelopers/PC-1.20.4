/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
/*    */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
/*    */ import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class SetContainerContents extends LootItemConditionalFunction {
/*    */   static {
/* 24 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)BuiltInRegistries.BLOCK_ENTITY_TYPE.holderByNameCodec().fieldOf("type").forGetter(()), (App)LootPoolEntries.CODEC.listOf().fieldOf("entries").forGetter(()))).apply((Applicative)$$0, SetContainerContents::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<SetContainerContents> CODEC;
/*    */   private final Holder<BlockEntityType<?>> type;
/*    */   private final List<LootPoolEntryContainer> entries;
/*    */   
/*    */   SetContainerContents(List<LootItemCondition> $$0, Holder<BlockEntityType<?>> $$1, List<LootPoolEntryContainer> $$2) {
/* 33 */     super($$0);
/* 34 */     this.type = $$1;
/* 35 */     this.entries = List.copyOf($$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 40 */     return LootItemFunctions.SET_CONTENTS;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/* 45 */     if ($$0.isEmpty()) {
/* 46 */       return $$0;
/*    */     }
/*    */     
/* 49 */     NonNullList<ItemStack> $$2 = NonNullList.create();
/* 50 */     this.entries.forEach($$2 -> $$2.expand($$0, ()));
/*    */     
/* 52 */     CompoundTag $$3 = new CompoundTag();
/* 53 */     ContainerHelper.saveAllItems($$3, $$2);
/*    */     
/* 55 */     CompoundTag $$4 = BlockItem.getBlockEntityData($$0);
/* 56 */     if ($$4 == null) {
/* 57 */       $$4 = $$3;
/*    */     } else {
/* 59 */       $$4.merge($$3);
/*    */     } 
/* 61 */     BlockItem.setBlockEntityData($$0, (BlockEntityType)this.type.value(), $$4);
/* 62 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 67 */     super.validate($$0);
/*    */     
/* 69 */     for (int $$1 = 0; $$1 < this.entries.size(); $$1++)
/* 70 */       ((LootPoolEntryContainer)this.entries.get($$1)).validate($$0.forChild(".entry[" + $$1 + "]")); 
/*    */   }
/*    */   
/*    */   public static class Builder
/*    */     extends LootItemConditionalFunction.Builder<Builder> {
/* 75 */     private final ImmutableList.Builder<LootPoolEntryContainer> entries = ImmutableList.builder();
/*    */     private final BlockEntityType<?> type;
/*    */     
/*    */     public Builder(BlockEntityType<?> $$0) {
/* 79 */       this.type = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     protected Builder getThis() {
/* 84 */       return this;
/*    */     }
/*    */     
/*    */     public Builder withEntry(LootPoolEntryContainer.Builder<?> $$0) {
/* 88 */       this.entries.add($$0.build());
/* 89 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public LootItemFunction build() {
/* 94 */       return new SetContainerContents(getConditions(), (Holder<BlockEntityType<?>>)this.type.builtInRegistryHolder(), (List<LootPoolEntryContainer>)this.entries.build());
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder setContents(BlockEntityType<?> $$0) {
/* 99 */     return new Builder($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetContainerContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */