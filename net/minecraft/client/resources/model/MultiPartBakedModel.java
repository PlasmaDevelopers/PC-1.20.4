/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
/*     */ import java.util.BitSet;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.ItemOverrides;
/*     */ import net.minecraft.client.renderer.block.model.ItemTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ public class MultiPartBakedModel
/*     */   implements BakedModel
/*     */ {
/*     */   private final List<Pair<Predicate<BlockState>, BakedModel>> selectors;
/*     */   protected final boolean hasAmbientOcclusion;
/*     */   protected final boolean isGui3d;
/*     */   protected final boolean usesBlockLight;
/*     */   protected final TextureAtlasSprite particleIcon;
/*     */   protected final ItemTransforms transforms;
/*     */   protected final ItemOverrides overrides;
/*  30 */   private final Map<BlockState, BitSet> selectorCache = (Map<BlockState, BitSet>)new Reference2ObjectOpenHashMap();
/*     */   
/*     */   public MultiPartBakedModel(List<Pair<Predicate<BlockState>, BakedModel>> $$0) {
/*  33 */     this.selectors = $$0;
/*     */     
/*  35 */     BakedModel $$1 = (BakedModel)((Pair)$$0.iterator().next()).getRight();
/*  36 */     this.hasAmbientOcclusion = $$1.useAmbientOcclusion();
/*  37 */     this.isGui3d = $$1.isGui3d();
/*  38 */     this.usesBlockLight = $$1.usesBlockLight();
/*  39 */     this.particleIcon = $$1.getParticleIcon();
/*  40 */     this.transforms = $$1.getTransforms();
/*  41 */     this.overrides = $$1.getOverrides();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getQuads(@Nullable BlockState $$0, @Nullable Direction $$1, RandomSource $$2) {
/*  46 */     if ($$0 == null) {
/*  47 */       return Collections.emptyList();
/*     */     }
/*     */     
/*  50 */     BitSet $$3 = this.selectorCache.get($$0);
/*  51 */     if ($$3 == null) {
/*  52 */       $$3 = new BitSet();
/*  53 */       for (int $$4 = 0; $$4 < this.selectors.size(); $$4++) {
/*  54 */         Pair<Predicate<BlockState>, BakedModel> $$5 = this.selectors.get($$4);
/*  55 */         if (((Predicate<BlockState>)$$5.getLeft()).test($$0)) {
/*  56 */           $$3.set($$4);
/*     */         }
/*     */       } 
/*  59 */       this.selectorCache.put($$0, $$3);
/*     */     } 
/*     */     
/*  62 */     List<BakedQuad> $$6 = Lists.newArrayList();
/*  63 */     long $$7 = $$2.nextLong();
/*  64 */     for (int $$8 = 0; $$8 < $$3.length(); $$8++) {
/*  65 */       if ($$3.get($$8)) {
/*  66 */         $$6.addAll(((BakedModel)((Pair)this.selectors.get($$8)).getRight()).getQuads($$0, $$1, RandomSource.create($$7)));
/*     */       }
/*     */     } 
/*  69 */     return $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useAmbientOcclusion() {
/*  74 */     return this.hasAmbientOcclusion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  79 */     return this.isGui3d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean usesBlockLight() {
/*  84 */     return this.usesBlockLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCustomRenderer() {
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleIcon() {
/*  94 */     return this.particleIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemTransforms getTransforms() {
/*  99 */     return this.transforms;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemOverrides getOverrides() {
/* 104 */     return this.overrides;
/*     */   }
/*     */   
/*     */   public static class Builder {
/* 108 */     private final List<Pair<Predicate<BlockState>, BakedModel>> selectors = Lists.newArrayList();
/*     */     
/*     */     public void add(Predicate<BlockState> $$0, BakedModel $$1) {
/* 111 */       this.selectors.add(Pair.of($$0, $$1));
/*     */     }
/*     */     
/*     */     public BakedModel build() {
/* 115 */       return new MultiPartBakedModel(this.selectors);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\MultiPartBakedModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */