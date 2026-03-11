/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverrides;
/*    */ import net.minecraft.client.renderer.block.model.ItemTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.random.WeightedEntry;
/*    */ import net.minecraft.util.random.WeightedRandom;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class WeightedBakedModel
/*    */   implements BakedModel {
/*    */   private final int totalWeight;
/*    */   private final List<WeightedEntry.Wrapper<BakedModel>> list;
/*    */   private final BakedModel wrapped;
/*    */   
/*    */   public WeightedBakedModel(List<WeightedEntry.Wrapper<BakedModel>> $$0) {
/* 24 */     this.list = $$0;
/* 25 */     this.totalWeight = WeightedRandom.getTotalWeight($$0);
/* 26 */     this.wrapped = (BakedModel)((WeightedEntry.Wrapper)$$0.get(0)).getData();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BakedQuad> getQuads(@Nullable BlockState $$0, @Nullable Direction $$1, RandomSource $$2) {
/* 31 */     return WeightedRandom.getWeightedItem(this.list, Math.abs((int)$$2.nextLong()) % this.totalWeight)
/* 32 */       .map($$3 -> ((BakedModel)$$3.getData()).getQuads($$0, $$1, $$2))
/* 33 */       .orElse(Collections.emptyList());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useAmbientOcclusion() {
/* 38 */     return this.wrapped.useAmbientOcclusion();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGui3d() {
/* 43 */     return this.wrapped.isGui3d();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean usesBlockLight() {
/* 48 */     return this.wrapped.usesBlockLight();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCustomRenderer() {
/* 53 */     return this.wrapped.isCustomRenderer();
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite getParticleIcon() {
/* 58 */     return this.wrapped.getParticleIcon();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemTransforms getTransforms() {
/* 63 */     return this.wrapped.getTransforms();
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemOverrides getOverrides() {
/* 68 */     return this.wrapped.getOverrides();
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 72 */     private final List<WeightedEntry.Wrapper<BakedModel>> list = Lists.newArrayList();
/*    */     
/*    */     public Builder add(@Nullable BakedModel $$0, int $$1) {
/* 75 */       if ($$0 != null) {
/* 76 */         this.list.add(WeightedEntry.wrap($$0, $$1));
/*    */       }
/* 78 */       return this;
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public BakedModel build() {
/* 83 */       if (this.list.isEmpty()) {
/* 84 */         return null;
/*    */       }
/* 86 */       if (this.list.size() == 1) {
/* 87 */         return (BakedModel)((WeightedEntry.Wrapper)this.list.get(0)).getData();
/*    */       }
/* 89 */       return new WeightedBakedModel(this.list);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\WeightedBakedModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */