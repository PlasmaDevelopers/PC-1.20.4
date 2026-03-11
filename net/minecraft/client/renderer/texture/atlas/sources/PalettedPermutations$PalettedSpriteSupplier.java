/*     */ package net.minecraft.client.renderer.texture.atlas.sources;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import java.io.IOException;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.texture.SpriteContents;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteSource;
/*     */ import net.minecraft.client.resources.metadata.animation.FrameSize;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceMetadata;
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
/*     */ final class PalettedSpriteSupplier
/*     */   extends Record
/*     */   implements SpriteSource.SpriteSupplier
/*     */ {
/*     */   private final LazyLoadedImage baseImage;
/*     */   private final Supplier<IntUnaryOperator> palette;
/*     */   private final ResourceLocation permutationLocation;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #120	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #120	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #120	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   PalettedSpriteSupplier(LazyLoadedImage $$0, Supplier<IntUnaryOperator> $$1, ResourceLocation $$2) {
/* 120 */     this.baseImage = $$0; this.palette = $$1; this.permutationLocation = $$2; } public LazyLoadedImage baseImage() { return this.baseImage; } public Supplier<IntUnaryOperator> palette() { return this.palette; } public ResourceLocation permutationLocation() { return this.permutationLocation; }
/*     */   
/*     */   @Nullable
/*     */   public SpriteContents apply(SpriteResourceLoader $$0) {
/*     */     try {
/* 125 */       NativeImage $$1 = this.baseImage.get().mappedCopy(this.palette.get());
/* 126 */       return new SpriteContents(this.permutationLocation, new FrameSize($$1.getWidth(), $$1.getHeight()), $$1, ResourceMetadata.EMPTY);
/* 127 */     } catch (IOException|IllegalArgumentException $$2) {
/* 128 */       PalettedPermutations.LOGGER.error("unable to apply palette to {}", this.permutationLocation, $$2);
/* 129 */       return null;
/*     */     } finally {
/* 131 */       this.baseImage.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void discard() {
/* 137 */     this.baseImage.release();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\sources\PalettedPermutations$PalettedSpriteSupplier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */