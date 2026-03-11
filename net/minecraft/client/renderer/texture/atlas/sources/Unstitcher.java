/*     */ package net.minecraft.client.renderer.texture.atlas.sources;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.datafixers.util.Function5;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.SpriteContents;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteSource;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteSources;
/*     */ import net.minecraft.client.resources.metadata.animation.FrameSize;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceMetadata;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Unstitcher implements SpriteSource {
/*  26 */   static final Logger LOGGER = LogUtils.getLogger(); public static final Codec<Unstitcher> CODEC; private final ResourceLocation resource; private final List<Region> regions; private final double xDivisor; private final double yDivisor;
/*     */   static {
/*  28 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("resource").forGetter(()), (App)ExtraCodecs.nonEmptyList(Region.CODEC.listOf()).fieldOf("regions").forGetter(()), (App)Codec.DOUBLE.optionalFieldOf("divisor_x", Double.valueOf(1.0D)).forGetter(()), (App)Codec.DOUBLE.optionalFieldOf("divisor_y", Double.valueOf(1.0D)).forGetter(())).apply((Applicative)$$0, Unstitcher::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Unstitcher(ResourceLocation $$0, List<Region> $$1, double $$2, double $$3) {
/*  41 */     this.resource = $$0;
/*  42 */     this.regions = $$1;
/*  43 */     this.xDivisor = $$2;
/*  44 */     this.yDivisor = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run(ResourceManager $$0, SpriteSource.Output $$1) {
/*  49 */     ResourceLocation $$2 = TEXTURE_ID_CONVERTER.idToFile(this.resource);
/*  50 */     Optional<Resource> $$3 = $$0.getResource($$2);
/*  51 */     if ($$3.isPresent()) {
/*  52 */       LazyLoadedImage $$4 = new LazyLoadedImage($$2, $$3.get(), this.regions.size());
/*  53 */       for (Region $$5 : this.regions) {
/*  54 */         $$1.add($$5.sprite, new RegionInstance($$4, $$5, this.xDivisor, this.yDivisor));
/*     */       }
/*     */     } else {
/*  57 */       LOGGER.warn("Missing sprite: {}", $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SpriteSourceType type() {
/*  63 */     return SpriteSources.UNSTITCHER;
/*     */   }
/*     */   private static final class Region extends Record { final ResourceLocation sprite; final double x; final double y; final double width; final double height; public static final Codec<Region> CODEC;
/*  66 */     private Region(ResourceLocation $$0, double $$1, double $$2, double $$3, double $$4) { this.sprite = $$0; this.x = $$1; this.y = $$2; this.width = $$3; this.height = $$4; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/texture/atlas/sources/Unstitcher$Region;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  66 */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/atlas/sources/Unstitcher$Region; } public ResourceLocation sprite() { return this.sprite; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/texture/atlas/sources/Unstitcher$Region;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/atlas/sources/Unstitcher$Region; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/texture/atlas/sources/Unstitcher$Region;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/renderer/texture/atlas/sources/Unstitcher$Region;
/*  66 */       //   0	8	1	$$0	Ljava/lang/Object; } public double x() { return this.x; } public double y() { return this.y; } public double width() { return this.width; } public double height() { return this.height; } static {
/*  67 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("sprite").forGetter(Region::sprite), (App)Codec.DOUBLE.fieldOf("x").forGetter(Region::x), (App)Codec.DOUBLE.fieldOf("y").forGetter(Region::y), (App)Codec.DOUBLE.fieldOf("width").forGetter(Region::width), (App)Codec.DOUBLE.fieldOf("height").forGetter(Region::height)).apply((Applicative)$$0, Region::new));
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RegionInstance
/*     */     implements SpriteSource.SpriteSupplier
/*     */   {
/*     */     private final LazyLoadedImage image;
/*     */     
/*     */     private final Unstitcher.Region region;
/*     */     
/*     */     private final double xDivisor;
/*     */     private final double yDivisor;
/*     */     
/*     */     RegionInstance(LazyLoadedImage $$0, Unstitcher.Region $$1, double $$2, double $$3) {
/*  83 */       this.image = $$0;
/*  84 */       this.region = $$1;
/*  85 */       this.xDivisor = $$2;
/*  86 */       this.yDivisor = $$3;
/*     */     }
/*     */ 
/*     */     
/*     */     public SpriteContents apply(SpriteResourceLoader $$0) {
/*     */       try {
/*  92 */         NativeImage $$1 = this.image.get();
/*     */         
/*  94 */         double $$2 = $$1.getWidth() / this.xDivisor;
/*  95 */         double $$3 = $$1.getHeight() / this.yDivisor;
/*     */         
/*  97 */         int $$4 = Mth.floor(this.region.x * $$2);
/*  98 */         int $$5 = Mth.floor(this.region.y * $$3);
/*     */         
/* 100 */         int $$6 = Mth.floor(this.region.width * $$2);
/* 101 */         int $$7 = Mth.floor(this.region.height * $$3);
/*     */         
/* 103 */         NativeImage $$8 = new NativeImage(NativeImage.Format.RGBA, $$6, $$7, false);
/* 104 */         $$1.copyRect($$8, $$4, $$5, 0, 0, $$6, $$7, false, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 110 */         return new SpriteContents(this.region.sprite, new FrameSize($$6, $$7), $$8, ResourceMetadata.EMPTY);
/* 111 */       } catch (Exception $$9) {
/* 112 */         Unstitcher.LOGGER.error("Failed to unstitch region {}", this.region.sprite, $$9);
/*     */       } finally {
/* 114 */         this.image.release();
/*     */       } 
/* 116 */       return MissingTextureAtlasSprite.create();
/*     */     }
/*     */ 
/*     */     
/*     */     public void discard() {
/* 121 */       this.image.release();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\sources\Unstitcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */