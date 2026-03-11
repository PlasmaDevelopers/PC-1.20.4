/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.ItemOverrides;
/*    */ import net.minecraft.client.renderer.block.model.ItemTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BuiltInModel
/*    */   implements BakedModel {
/*    */   private final ItemTransforms itemTransforms;
/*    */   private final ItemOverrides overrides;
/*    */   private final TextureAtlasSprite particleTexture;
/*    */   private final boolean usesBlockLight;
/*    */   
/*    */   public BuiltInModel(ItemTransforms $$0, ItemOverrides $$1, TextureAtlasSprite $$2, boolean $$3) {
/* 22 */     this.itemTransforms = $$0;
/* 23 */     this.overrides = $$1;
/* 24 */     this.particleTexture = $$2;
/* 25 */     this.usesBlockLight = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BakedQuad> getQuads(@Nullable BlockState $$0, @Nullable Direction $$1, RandomSource $$2) {
/* 30 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean useAmbientOcclusion() {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isGui3d() {
/* 40 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean usesBlockLight() {
/* 45 */     return this.usesBlockLight;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCustomRenderer() {
/* 50 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureAtlasSprite getParticleIcon() {
/* 55 */     return this.particleTexture;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemTransforms getTransforms() {
/* 60 */     return this.itemTransforms;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemOverrides getOverrides() {
/* 65 */     return this.overrides;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\BuiltInModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */