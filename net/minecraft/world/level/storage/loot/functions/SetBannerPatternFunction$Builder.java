/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ import net.minecraft.world.level.block.entity.BannerPattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */   extends LootItemConditionalFunction.Builder<SetBannerPatternFunction.Builder>
/*    */ {
/* 72 */   private final ImmutableList.Builder<Pair<Holder<BannerPattern>, DyeColor>> patterns = ImmutableList.builder();
/*    */   private final boolean append;
/*    */   
/*    */   Builder(boolean $$0) {
/* 76 */     this.append = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Builder getThis() {
/* 81 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunction build() {
/* 86 */     return new SetBannerPatternFunction(getConditions(), (List<Pair<Holder<BannerPattern>, DyeColor>>)this.patterns.build(), this.append);
/*    */   }
/*    */   
/*    */   public Builder addPattern(ResourceKey<BannerPattern> $$0, DyeColor $$1) {
/* 90 */     return addPattern((Holder<BannerPattern>)BuiltInRegistries.BANNER_PATTERN.getHolderOrThrow($$0), $$1);
/*    */   }
/*    */   
/*    */   public Builder addPattern(Holder<BannerPattern> $$0, DyeColor $$1) {
/* 94 */     this.patterns.add(Pair.of($$0, $$1));
/* 95 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetBannerPatternFunction$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */