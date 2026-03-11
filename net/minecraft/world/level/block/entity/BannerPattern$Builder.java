/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.item.DyeColor;
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
/*    */ {
/* 38 */   private final List<Pair<Holder<BannerPattern>, DyeColor>> patterns = Lists.newArrayList();
/*    */   
/*    */   public Builder addPattern(ResourceKey<BannerPattern> $$0, DyeColor $$1) {
/* 41 */     return addPattern((Holder<BannerPattern>)BuiltInRegistries.BANNER_PATTERN.getHolderOrThrow($$0), $$1);
/*    */   }
/*    */   
/*    */   public Builder addPattern(Holder<BannerPattern> $$0, DyeColor $$1) {
/* 45 */     return addPattern(Pair.of($$0, $$1));
/*    */   }
/*    */   
/*    */   public Builder addPattern(Pair<Holder<BannerPattern>, DyeColor> $$0) {
/* 49 */     this.patterns.add($$0);
/* 50 */     return this;
/*    */   }
/*    */   
/*    */   public ListTag toListTag() {
/* 54 */     ListTag $$0 = new ListTag();
/*    */     
/* 56 */     for (Pair<Holder<BannerPattern>, DyeColor> $$1 : this.patterns) {
/* 57 */       CompoundTag $$2 = new CompoundTag();
/* 58 */       $$2.putString("Pattern", ((BannerPattern)((Holder)$$1.getFirst()).value()).hashname);
/* 59 */       $$2.putInt("Color", ((DyeColor)$$1.getSecond()).getId());
/* 60 */       $$0.add($$2);
/*    */     } 
/*    */     
/* 63 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BannerPattern$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */