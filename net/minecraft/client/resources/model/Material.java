/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.Comparator;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class Material
/*    */ {
/* 17 */   public static final Comparator<Material> COMPARATOR = Comparator.comparing(Material::atlasLocation).thenComparing(Material::texture);
/*    */   
/*    */   private final ResourceLocation atlasLocation;
/*    */   private final ResourceLocation texture;
/*    */   @Nullable
/*    */   private RenderType renderType;
/*    */   
/*    */   public Material(ResourceLocation $$0, ResourceLocation $$1) {
/* 25 */     this.atlasLocation = $$0;
/* 26 */     this.texture = $$1;
/*    */   }
/*    */   
/*    */   public ResourceLocation atlasLocation() {
/* 30 */     return this.atlasLocation;
/*    */   }
/*    */   
/*    */   public ResourceLocation texture() {
/* 34 */     return this.texture;
/*    */   }
/*    */   
/*    */   public TextureAtlasSprite sprite() {
/* 38 */     return Minecraft.getInstance().getTextureAtlas(atlasLocation()).apply(texture());
/*    */   }
/*    */   
/*    */   public RenderType renderType(Function<ResourceLocation, RenderType> $$0) {
/* 42 */     if (this.renderType == null) {
/* 43 */       this.renderType = $$0.apply(this.atlasLocation);
/*    */     }
/* 45 */     return this.renderType;
/*    */   }
/*    */   
/*    */   public VertexConsumer buffer(MultiBufferSource $$0, Function<ResourceLocation, RenderType> $$1) {
/* 49 */     return sprite().wrap($$0.getBuffer(renderType($$1)));
/*    */   }
/*    */   
/*    */   public VertexConsumer buffer(MultiBufferSource $$0, Function<ResourceLocation, RenderType> $$1, boolean $$2) {
/* 53 */     return sprite().wrap(ItemRenderer.getFoilBufferDirect($$0, renderType($$1), true, $$2));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 58 */     if (this == $$0) {
/* 59 */       return true;
/*    */     }
/* 61 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 62 */       return false;
/*    */     }
/* 64 */     Material $$1 = (Material)$$0;
/* 65 */     return (this.atlasLocation.equals($$1.atlasLocation) && this.texture.equals($$1.texture));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 70 */     return Objects.hash(new Object[] { this.atlasLocation, this.texture });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return "Material{atlasLocation=" + this.atlasLocation + ", texture=" + this.texture + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\Material.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */