/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BlockModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemOverrides;
/*     */ import net.minecraft.client.renderer.block.model.ItemTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class SimpleBakedModel
/*     */   implements BakedModel {
/*     */   protected final List<BakedQuad> unculledFaces;
/*     */   protected final Map<Direction, List<BakedQuad>> culledFaces;
/*     */   protected final boolean hasAmbientOcclusion;
/*     */   protected final boolean isGui3d;
/*     */   protected final boolean usesBlockLight;
/*     */   protected final TextureAtlasSprite particleIcon;
/*     */   protected final ItemTransforms transforms;
/*     */   protected final ItemOverrides overrides;
/*     */   
/*     */   public SimpleBakedModel(List<BakedQuad> $$0, Map<Direction, List<BakedQuad>> $$1, boolean $$2, boolean $$3, boolean $$4, TextureAtlasSprite $$5, ItemTransforms $$6, ItemOverrides $$7) {
/*  29 */     this.unculledFaces = $$0;
/*  30 */     this.culledFaces = $$1;
/*  31 */     this.hasAmbientOcclusion = $$2;
/*  32 */     this.isGui3d = $$4;
/*  33 */     this.usesBlockLight = $$3;
/*  34 */     this.particleIcon = $$5;
/*  35 */     this.transforms = $$6;
/*  36 */     this.overrides = $$7;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BakedQuad> getQuads(@Nullable BlockState $$0, @Nullable Direction $$1, RandomSource $$2) {
/*  41 */     return ($$1 == null) ? this.unculledFaces : this.culledFaces.get($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useAmbientOcclusion() {
/*  46 */     return this.hasAmbientOcclusion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGui3d() {
/*  51 */     return this.isGui3d;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean usesBlockLight() {
/*  56 */     return this.usesBlockLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCustomRenderer() {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleIcon() {
/*  66 */     return this.particleIcon;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemTransforms getTransforms() {
/*  71 */     return this.transforms;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemOverrides getOverrides() {
/*  76 */     return this.overrides;
/*     */   }
/*     */   
/*     */   public static class Builder {
/*  80 */     private final List<BakedQuad> unculledFaces = Lists.newArrayList(); private final ItemOverrides overrides; private final boolean hasAmbientOcclusion; private TextureAtlasSprite particleIcon;
/*  81 */     private final Map<Direction, List<BakedQuad>> culledFaces = Maps.newEnumMap(Direction.class); private final boolean usesBlockLight; private final boolean isGui3d;
/*     */     private final ItemTransforms transforms;
/*     */     
/*     */     private Builder(boolean $$0, boolean $$1, boolean $$2, ItemTransforms $$3, ItemOverrides $$4) {
/*  85 */       for (Direction $$5 : Direction.values()) {
/*  86 */         this.culledFaces.put($$5, Lists.newArrayList());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       this.overrides = $$4;
/* 102 */       this.hasAmbientOcclusion = $$0;
/* 103 */       this.usesBlockLight = $$1;
/* 104 */       this.isGui3d = $$2;
/* 105 */       this.transforms = $$3;
/*     */     } public Builder(BlockModel $$0, ItemOverrides $$1, boolean $$2) {
/*     */       this($$0.hasAmbientOcclusion(), $$0.getGuiLight().lightLikeBlock(), $$2, $$0.getTransforms(), $$1);
/*     */     } public Builder addCulledFace(Direction $$0, BakedQuad $$1) {
/* 109 */       ((List<BakedQuad>)this.culledFaces.get($$0)).add($$1);
/* 110 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addUnculledFace(BakedQuad $$0) {
/* 114 */       this.unculledFaces.add($$0);
/* 115 */       return this;
/*     */     }
/*     */     
/*     */     public Builder particle(TextureAtlasSprite $$0) {
/* 119 */       this.particleIcon = $$0;
/*     */       
/* 121 */       return this;
/*     */     }
/*     */     
/*     */     public Builder item() {
/* 125 */       return this;
/*     */     }
/*     */     
/*     */     public BakedModel build() {
/* 129 */       if (this.particleIcon == null) {
/* 130 */         throw new RuntimeException("Missing particle!");
/*     */       }
/* 132 */       return new SimpleBakedModel(this.unculledFaces, this.culledFaces, this.hasAmbientOcclusion, this.usesBlockLight, this.isGui3d, this.particleIcon, this.transforms, this.overrides);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\SimpleBakedModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */