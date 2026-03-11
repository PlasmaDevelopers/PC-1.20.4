/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.UnaryOperator;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentSerialization;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ 
/*     */ public class SetLoreFunction extends LootItemConditionalFunction {
/*     */   static {
/*  25 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)Codec.BOOL.fieldOf("replace").orElse(Boolean.valueOf(false)).forGetter(()), (App)ComponentSerialization.CODEC.listOf().fieldOf("lore").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)LootContext.EntityTarget.CODEC, "entity").forGetter(()))).apply((Applicative)$$0, SetLoreFunction::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<SetLoreFunction> CODEC;
/*     */   
/*     */   private final boolean replace;
/*     */   private final List<Component> lore;
/*     */   private final Optional<LootContext.EntityTarget> resolutionContext;
/*     */   
/*     */   public SetLoreFunction(List<LootItemCondition> $$0, boolean $$1, List<Component> $$2, Optional<LootContext.EntityTarget> $$3) {
/*  36 */     super($$0);
/*  37 */     this.replace = $$1;
/*  38 */     this.lore = List.copyOf($$2);
/*  39 */     this.resolutionContext = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/*  44 */     return LootItemFunctions.SET_LORE;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/*  49 */     return this.resolutionContext.<Set<LootContextParam<?>>>map($$0 -> Set.of($$0.getParam())).orElseGet(Set::of);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/*  54 */     ListTag $$2 = getLoreTag($$0, !this.lore.isEmpty());
/*     */     
/*  56 */     if ($$2 != null) {
/*  57 */       if (this.replace) {
/*  58 */         $$2.clear();
/*     */       }
/*     */       
/*  61 */       UnaryOperator<Component> $$3 = SetNameFunction.createResolver($$1, this.resolutionContext.orElse(null));
/*  62 */       Objects.requireNonNull($$2); this.lore.stream().map($$3).map(Component.Serializer::toJson).map(StringTag::valueOf).forEach($$2::add);
/*     */     } 
/*     */     
/*  65 */     return $$0;
/*     */   }
/*     */   @Nullable
/*     */   private ListTag getLoreTag(ItemStack $$0, boolean $$1) {
/*     */     CompoundTag $$3;
/*     */     CompoundTag $$6;
/*  71 */     if ($$0.hasTag()) {
/*  72 */       CompoundTag $$2 = $$0.getTag();
/*  73 */     } else if ($$1) {
/*  74 */       $$3 = new CompoundTag();
/*  75 */       $$0.setTag($$3);
/*     */     } else {
/*  77 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  82 */     if ($$3.contains("display", 10)) {
/*  83 */       CompoundTag $$5 = $$3.getCompound("display");
/*  84 */     } else if ($$1) {
/*  85 */       $$6 = new CompoundTag();
/*  86 */       $$3.put("display", (Tag)$$6);
/*     */     } else {
/*  88 */       return null;
/*     */     } 
/*     */     
/*  91 */     if ($$6.contains("Lore", 9))
/*  92 */       return $$6.getList("Lore", 8); 
/*  93 */     if ($$1) {
/*  94 */       ListTag $$8 = new ListTag();
/*  95 */       $$6.put("Lore", (Tag)$$8);
/*  96 */       return $$8;
/*     */     } 
/*  98 */     return null;
/*     */   }
/*     */   
/*     */   public static class Builder
/*     */     extends LootItemConditionalFunction.Builder<Builder> {
/*     */     private boolean replace;
/* 104 */     private Optional<LootContext.EntityTarget> resolutionContext = Optional.empty();
/* 105 */     private final ImmutableList.Builder<Component> lore = ImmutableList.builder();
/*     */     
/*     */     public Builder setReplace(boolean $$0) {
/* 108 */       this.replace = $$0;
/* 109 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setResolutionContext(LootContext.EntityTarget $$0) {
/* 113 */       this.resolutionContext = Optional.of($$0);
/* 114 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addLine(Component $$0) {
/* 118 */       this.lore.add($$0);
/* 119 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Builder getThis() {
/* 124 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootItemFunction build() {
/* 129 */       return new SetLoreFunction(getConditions(), this.replace, (List<Component>)this.lore.build(), this.resolutionContext);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder setLore() {
/* 134 */     return new Builder();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetLoreFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */