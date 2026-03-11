/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.apache.commons.lang3.tuple.Triple;
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
/*     */ public class MultiTextureStateShard
/*     */   extends RenderStateShard.EmptyTextureStateShard
/*     */ {
/*     */   private final Optional<ResourceLocation> cutoutTexture;
/*     */   
/*     */   MultiTextureStateShard(ImmutableList<Triple<ResourceLocation, Boolean, Boolean>> $$0) {
/* 201 */     super(() -> {
/*     */           int $$1 = 0; UnmodifiableIterator<Triple<ResourceLocation, Boolean, Boolean>> unmodifiableIterator = $$0.iterator(); while (unmodifiableIterator.hasNext()) {
/*     */             Triple<ResourceLocation, Boolean, Boolean> $$2 = unmodifiableIterator.next(); TextureManager $$3 = Minecraft.getInstance().getTextureManager(); $$3.getTexture((ResourceLocation)$$2.getLeft()).setFilter(((Boolean)$$2.getMiddle()).booleanValue(), ((Boolean)$$2.getRight()).booleanValue());
/*     */             RenderSystem.setShaderTexture($$1++, (ResourceLocation)$$2.getLeft());
/*     */           } 
/*     */         }() -> {
/*     */         
/*     */         });
/* 209 */     this.cutoutTexture = $$0.stream().findFirst().map(Triple::getLeft);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Optional<ResourceLocation> cutoutTexture() {
/* 214 */     return this.cutoutTexture;
/*     */   }
/*     */   
/*     */   public static Builder builder() {
/* 218 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static final class Builder {
/* 222 */     private final ImmutableList.Builder<Triple<ResourceLocation, Boolean, Boolean>> builder = new ImmutableList.Builder();
/*     */     
/*     */     public Builder add(ResourceLocation $$0, boolean $$1, boolean $$2) {
/* 225 */       this.builder.add(Triple.of($$0, Boolean.valueOf($$1), Boolean.valueOf($$2)));
/* 226 */       return this;
/*     */     }
/*     */     
/*     */     public RenderStateShard.MultiTextureStateShard build() {
/* 230 */       return new RenderStateShard.MultiTextureStateShard(this.builder.build());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\RenderStateShard$MultiTextureStateShard.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */