/*     */ package net.minecraft.client.resources.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.client.renderer.block.model.BlockModel;
/*     */ import net.minecraft.client.renderer.block.model.ItemOverrides;
/*     */ import net.minecraft.client.renderer.block.model.ItemTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.core.Direction;
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
/*     */ public class Builder
/*     */ {
/*  80 */   private final List<BakedQuad> unculledFaces = Lists.newArrayList(); private final ItemOverrides overrides; private final boolean hasAmbientOcclusion; private TextureAtlasSprite particleIcon;
/*  81 */   private final Map<Direction, List<BakedQuad>> culledFaces = Maps.newEnumMap(Direction.class); private final boolean usesBlockLight; private final boolean isGui3d;
/*     */   private final ItemTransforms transforms;
/*     */   
/*     */   private Builder(boolean $$0, boolean $$1, boolean $$2, ItemTransforms $$3, ItemOverrides $$4) {
/*  85 */     for (Direction $$5 : Direction.values()) {
/*  86 */       this.culledFaces.put($$5, Lists.newArrayList());
/*     */     }
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
/* 101 */     this.overrides = $$4;
/* 102 */     this.hasAmbientOcclusion = $$0;
/* 103 */     this.usesBlockLight = $$1;
/* 104 */     this.isGui3d = $$2;
/* 105 */     this.transforms = $$3;
/*     */   } public Builder(BlockModel $$0, ItemOverrides $$1, boolean $$2) {
/*     */     this($$0.hasAmbientOcclusion(), $$0.getGuiLight().lightLikeBlock(), $$2, $$0.getTransforms(), $$1);
/*     */   } public Builder addCulledFace(Direction $$0, BakedQuad $$1) {
/* 109 */     ((List<BakedQuad>)this.culledFaces.get($$0)).add($$1);
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public Builder addUnculledFace(BakedQuad $$0) {
/* 114 */     this.unculledFaces.add($$0);
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public Builder particle(TextureAtlasSprite $$0) {
/* 119 */     this.particleIcon = $$0;
/*     */     
/* 121 */     return this;
/*     */   }
/*     */   
/*     */   public Builder item() {
/* 125 */     return this;
/*     */   }
/*     */   
/*     */   public BakedModel build() {
/* 129 */     if (this.particleIcon == null) {
/* 130 */       throw new RuntimeException("Missing particle!");
/*     */     }
/* 132 */     return new SimpleBakedModel(this.unculledFaces, this.culledFaces, this.hasAmbientOcclusion, this.usesBlockLight, this.isGui3d, this.particleIcon, this.transforms, this.overrides);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\SimpleBakedModel$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */