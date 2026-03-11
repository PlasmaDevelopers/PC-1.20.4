/*     */ package net.minecraft.client.renderer.texture.atlas.sources;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.IntUnaryOperator;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.texture.SpriteContents;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteSource;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteSources;
/*     */ import net.minecraft.client.resources.metadata.animation.FrameSize;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceMetadata;
/*     */ import net.minecraft.util.FastColor;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PalettedPermutations implements SpriteSource {
/*  34 */   static final Logger LOGGER = LogUtils.getLogger(); public static final Codec<PalettedPermutations> CODEC; private final List<ResourceLocation> textures; private final Map<String, ResourceLocation> permutations; private final ResourceLocation paletteKey;
/*     */   static {
/*  36 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.list(ResourceLocation.CODEC).fieldOf("textures").forGetter(()), (App)ResourceLocation.CODEC.fieldOf("palette_key").forGetter(()), (App)Codec.unboundedMap((Codec)Codec.STRING, ResourceLocation.CODEC).fieldOf("permutations").forGetter(())).apply((Applicative)$$0, PalettedPermutations::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PalettedPermutations(List<ResourceLocation> $$0, ResourceLocation $$1, Map<String, ResourceLocation> $$2) {
/*  47 */     this.textures = $$0;
/*  48 */     this.permutations = $$2;
/*  49 */     this.paletteKey = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(ResourceManager $$0, SpriteSource.Output $$1) {
/*  54 */     Supplier supplier = Suppliers.memoize(() -> loadPaletteEntryFromImage($$0, this.paletteKey));
/*  55 */     Map<String, Supplier<IntUnaryOperator>> $$3 = new HashMap<>();
/*  56 */     this.permutations.forEach(($$3, $$4) -> $$0.put($$3, Suppliers.memoize(())));
/*     */ 
/*     */     
/*  59 */     for (ResourceLocation $$4 : this.textures) {
/*  60 */       ResourceLocation $$5 = TEXTURE_ID_CONVERTER.idToFile($$4);
/*  61 */       Optional<Resource> $$6 = $$0.getResource($$5);
/*  62 */       if ($$6.isEmpty()) {
/*  63 */         LOGGER.warn("Unable to find texture {}", $$5);
/*     */         continue;
/*     */       } 
/*  66 */       LazyLoadedImage $$7 = new LazyLoadedImage($$5, $$6.get(), $$3.size());
/*  67 */       for (Map.Entry<String, Supplier<IntUnaryOperator>> $$8 : $$3.entrySet()) {
/*  68 */         ResourceLocation $$9 = $$4.withSuffix("_" + (String)$$8.getKey());
/*  69 */         $$1.add($$9, new PalettedSpriteSupplier($$7, $$8.getValue(), $$9));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static IntUnaryOperator createPaletteMapping(int[] $$0, int[] $$1) {
/*  75 */     if ($$1.length != $$0.length) {
/*  76 */       LOGGER.warn("Palette mapping has different sizes: {} and {}", Integer.valueOf($$0.length), Integer.valueOf($$1.length));
/*  77 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/*  80 */     Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap($$1.length);
/*  81 */     for (int $$3 = 0; $$3 < $$0.length; $$3++) {
/*  82 */       int $$4 = $$0[$$3];
/*  83 */       if (FastColor.ABGR32.alpha($$4) != 0) {
/*  84 */         int2IntOpenHashMap.put(FastColor.ABGR32.transparent($$4), $$1[$$3]);
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return $$1 -> {
/*     */         int $$2 = FastColor.ABGR32.alpha($$1);
/*     */         if ($$2 == 0) {
/*     */           return $$1;
/*     */         }
/*     */         int $$3 = FastColor.ABGR32.transparent($$1);
/*     */         int $$4 = $$0.getOrDefault($$3, FastColor.ABGR32.opaque($$3));
/*     */         int $$5 = FastColor.ABGR32.alpha($$4);
/*     */         return FastColor.ABGR32.color($$2 * $$5 / 255, $$4);
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] loadPaletteEntryFromImage(ResourceManager $$0, ResourceLocation $$1) {
/* 102 */     Optional<Resource> $$2 = $$0.getResource(TEXTURE_ID_CONVERTER.idToFile($$1));
/* 103 */     if ($$2.isEmpty()) {
/* 104 */       LOGGER.error("Failed to load palette image {}", $$1);
/* 105 */       throw new IllegalArgumentException();
/*     */     }  
/* 107 */     try { InputStream $$3 = ((Resource)$$2.get()).open(); try { NativeImage $$4 = NativeImage.read($$3); 
/* 108 */         try { int[] arrayOfInt = $$4.getPixelsRGBA();
/* 109 */           if ($$4 != null) $$4.close();  if ($$3 != null) $$3.close();  return arrayOfInt; } catch (Throwable throwable) { if ($$4 != null) try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$5)
/* 110 */     { LOGGER.error("Couldn't load texture {}", $$1, $$5);
/* 111 */       throw new IllegalArgumentException(); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSourceType type() {
/* 117 */     return SpriteSources.PALETTED_PERMUTATIONS;
/*     */   } private static final class PalettedSpriteSupplier extends Record implements SpriteSource.SpriteSupplier {
/*     */     private final LazyLoadedImage baseImage; private final Supplier<IntUnaryOperator> palette; private final ResourceLocation permutationLocation;
/* 120 */     PalettedSpriteSupplier(LazyLoadedImage $$0, Supplier<IntUnaryOperator> $$1, ResourceLocation $$2) { this.baseImage = $$0; this.palette = $$1; this.permutationLocation = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #120	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #120	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #120	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;
/* 120 */       //   0	8	1	$$0	Ljava/lang/Object; } public LazyLoadedImage baseImage() { return this.baseImage; } public Supplier<IntUnaryOperator> palette() { return this.palette; } public ResourceLocation permutationLocation() { return this.permutationLocation; }
/*     */     
/*     */     @Nullable
/*     */     public SpriteContents apply(SpriteResourceLoader $$0) {
/*     */       try {
/* 125 */         NativeImage $$1 = this.baseImage.get().mappedCopy(this.palette.get());
/* 126 */         return new SpriteContents(this.permutationLocation, new FrameSize($$1.getWidth(), $$1.getHeight()), $$1, ResourceMetadata.EMPTY);
/* 127 */       } catch (IOException|IllegalArgumentException $$2) {
/* 128 */         PalettedPermutations.LOGGER.error("unable to apply palette to {}", this.permutationLocation, $$2);
/* 129 */         return null;
/*     */       } finally {
/* 131 */         this.baseImage.release();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void discard() {
/* 137 */       this.baseImage.release();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\sources\PalettedPermutations.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */