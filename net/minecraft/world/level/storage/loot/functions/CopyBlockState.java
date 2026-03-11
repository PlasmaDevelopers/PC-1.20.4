/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ 
/*     */ public class CopyBlockState extends LootItemConditionalFunction {
/*     */   static {
/*  26 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("block").forGetter(()), (App)Codec.STRING.listOf().fieldOf("properties").forGetter(()))).apply((Applicative)$$0, CopyBlockState::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<CopyBlockState> CODEC;
/*     */   private final Holder<Block> block;
/*     */   private final Set<Property<?>> properties;
/*     */   
/*     */   CopyBlockState(List<LootItemCondition> $$0, Holder<Block> $$1, Set<Property<?>> $$2) {
/*  35 */     super($$0);
/*  36 */     this.block = $$1;
/*  37 */     this.properties = $$2;
/*     */   }
/*     */   
/*     */   private CopyBlockState(List<LootItemCondition> $$0, Holder<Block> $$1, List<String> $$2) {
/*  41 */     this($$0, $$1, (Set<Property<?>>)$$2.stream()
/*  42 */         .map(((Block)$$1.value()).getStateDefinition()::getProperty)
/*  43 */         .filter(Objects::nonNull)
/*  44 */         .collect(Collectors.toSet()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/*  50 */     return LootItemFunctions.COPY_STATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/*  55 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.BLOCK_STATE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack run(ItemStack $$0, LootContext $$1) {
/*  60 */     BlockState $$2 = (BlockState)$$1.getParamOrNull(LootContextParams.BLOCK_STATE);
/*  61 */     if ($$2 != null) {
/*  62 */       CompoundTag $$5, $$3 = $$0.getOrCreateTag();
/*     */       
/*  64 */       if ($$3.contains("BlockStateTag", 10)) {
/*  65 */         CompoundTag $$4 = $$3.getCompound("BlockStateTag");
/*     */       } else {
/*  67 */         $$5 = new CompoundTag();
/*  68 */         $$3.put("BlockStateTag", (Tag)$$5);
/*     */       } 
/*     */       
/*  71 */       for (Property<?> $$6 : this.properties) {
/*  72 */         if ($$2.hasProperty($$6)) {
/*  73 */           $$5.putString($$6.getName(), serialize($$2, $$6));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     return $$0;
/*     */   }
/*     */   
/*     */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
/*     */     private final Holder<Block> block;
/*  83 */     private final ImmutableSet.Builder<Property<?>> properties = ImmutableSet.builder();
/*     */     
/*     */     Builder(Block $$0) {
/*  86 */       this.block = (Holder<Block>)$$0.builtInRegistryHolder();
/*     */     }
/*     */     
/*     */     public Builder copy(Property<?> $$0) {
/*  90 */       if (!((Block)this.block.value()).getStateDefinition().getProperties().contains($$0)) {
/*  91 */         throw new IllegalStateException("Property " + $$0 + " is not present on block " + this.block);
/*     */       }
/*  93 */       this.properties.add($$0);
/*  94 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Builder getThis() {
/*  99 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootItemFunction build() {
/* 104 */       return new CopyBlockState(getConditions(), this.block, (Set<Property<?>>)this.properties.build());
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder copyState(Block $$0) {
/* 109 */     return new Builder($$0);
/*     */   }
/*     */   
/*     */   private static <T extends Comparable<T>> String serialize(BlockState $$0, Property<T> $$1) {
/* 113 */     Comparable comparable = $$0.getValue($$1);
/* 114 */     return $$1.getName(comparable);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\CopyBlockState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */