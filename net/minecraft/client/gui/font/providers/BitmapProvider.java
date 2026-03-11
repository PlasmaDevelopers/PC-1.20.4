/*     */ package net.minecraft.client.gui.font.providers;
/*     */ import com.mojang.blaze3d.font.GlyphInfo;
/*     */ import com.mojang.blaze3d.font.GlyphProvider;
/*     */ import com.mojang.blaze3d.font.SheetGlyphInfo;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSets;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.font.CodepointMap;
/*     */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class BitmapProvider implements GlyphProvider {
/*  31 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final NativeImage image;
/*     */   private final CodepointMap<Glyph> glyphs;
/*     */   
/*     */   BitmapProvider(NativeImage $$0, CodepointMap<Glyph> $$1) {
/*  37 */     this.image = $$0;
/*  38 */     this.glyphs = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  43 */     this.image.close();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GlyphInfo getGlyph(int $$0) {
/*  49 */     return (GlyphInfo)this.glyphs.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public IntSet getSupportedGlyphs() {
/*  54 */     return IntSets.unmodifiable(this.glyphs.keySet());
/*     */   }
/*     */   public static final class Definition extends Record implements GlyphProviderDefinition { private final ResourceLocation file; private final int height; private final int ascent; private final int[][] codepointGrid; private static final Codec<int[][]> CODEPOINT_GRID_CODEC; public static final MapCodec<Definition> CODEC;
/*  57 */     public Definition(ResourceLocation $$0, int $$1, int $$2, int[][] $$3) { this.file = $$0; this.height = $$1; this.ascent = $$2; this.codepointGrid = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #57	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  57 */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition; } public ResourceLocation file() { return this.file; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #57	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #57	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;
/*  57 */       //   0	8	1	$$0	Ljava/lang/Object; } public int height() { return this.height; } public int ascent() { return this.ascent; } public int[][] codepointGrid() { return this.codepointGrid; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  63 */       CODEPOINT_GRID_CODEC = ExtraCodecs.validate(Codec.STRING.listOf().xmap($$0 -> { int $$1 = $$0.size(); int[][] $$2 = new int[$$1][]; for (int $$3 = 0; $$3 < $$1; $$3++) $$2[$$3] = ((String)$$0.get($$3)).codePoints().toArray();  return $$2; }$$0 -> { List<String> $$1 = new ArrayList<>($$0.length); for (int[] $$2 : $$0) $$1.add(new String($$2, 0, $$2.length));  return $$1; }), Definition::validateDimensions);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 106 */       CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("file").forGetter(Definition::file), (App)Codec.INT.optionalFieldOf("height", Integer.valueOf(8)).forGetter(Definition::height), (App)Codec.INT.fieldOf("ascent").forGetter(Definition::ascent), (App)CODEPOINT_GRID_CODEC.fieldOf("chars").forGetter(Definition::codepointGrid)).apply((Applicative)$$0, Definition::new)), Definition::validate);
/*     */     } private static DataResult<int[][]> validateDimensions(int[][] $$0) { int $$1 = $$0.length; if ($$1 == 0)
/*     */         return DataResult.error(() -> "Expected to find data in codepoint grid");  int[] $$2 = $$0[0]; int $$3 = $$2.length; if ($$3 == 0)
/*     */         return DataResult.error(() -> "Expected to find data in codepoint grid");  for (int $$4 = 1; $$4 < $$1; $$4++) {
/*     */         int[] $$5 = $$0[$$4];
/*     */         if ($$5.length != $$3)
/*     */           return DataResult.error(() -> "Lines in codepoint grid have to be the same length (found: " + $$0.length + " codepoints, expected: " + $$1 + "), pad with \\u0000"); 
/*     */       } 
/* 114 */       return DataResult.success($$0); } private static DataResult<Definition> validate(Definition $$0) { if ($$0.ascent > $$0.height) {
/* 115 */         return DataResult.error(() -> "Ascent " + $$0.ascent + " higher than height " + $$0.height);
/*     */       }
/* 117 */       return DataResult.success($$0); }
/*     */ 
/*     */ 
/*     */     
/*     */     public GlyphProviderType type() {
/* 122 */       return GlyphProviderType.BITMAP;
/*     */     }
/*     */ 
/*     */     
/*     */     public Either<GlyphProviderDefinition.Loader, GlyphProviderDefinition.Reference> unpack() {
/* 127 */       return Either.left(this::load);
/*     */     }
/*     */     
/*     */     private GlyphProvider load(ResourceManager $$0) throws IOException {
/* 131 */       ResourceLocation $$1 = this.file.withPrefix("textures/");
/* 132 */       InputStream $$2 = $$0.open($$1); 
/* 133 */       try { NativeImage $$3 = NativeImage.read(NativeImage.Format.RGBA, $$2);
/*     */         
/* 135 */         int $$4 = $$3.getWidth();
/* 136 */         int $$5 = $$3.getHeight();
/*     */         
/* 138 */         int $$6 = $$4 / (this.codepointGrid[0]).length;
/* 139 */         int $$7 = $$5 / this.codepointGrid.length;
/*     */         
/* 141 */         float $$8 = this.height / $$7;
/*     */         
/* 143 */         CodepointMap<BitmapProvider.Glyph> $$9 = new CodepointMap($$0 -> new BitmapProvider.Glyph[$$0], $$0 -> new BitmapProvider.Glyph[$$0][]);
/*     */         
/* 145 */         for (int $$10 = 0; $$10 < this.codepointGrid.length; $$10++) {
/* 146 */           int $$11 = 0;
/* 147 */           for (int $$12 : this.codepointGrid[$$10]) {
/* 148 */             int $$13 = $$11++;
/* 149 */             if ($$12 != 0) {
/*     */ 
/*     */               
/* 152 */               int $$14 = getActualGlyphWidth($$3, $$6, $$7, $$13, $$10);
/*     */               
/* 154 */               BitmapProvider.Glyph $$15 = (BitmapProvider.Glyph)$$9.put($$12, new BitmapProvider.Glyph($$8, $$3, $$13 * $$6, $$10 * $$7, $$6, $$7, (int)(0.5D + ($$14 * $$8)) + 1, this.ascent));
/* 155 */               if ($$15 != null) {
/* 156 */                 BitmapProvider.LOGGER.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString($$12), $$1);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 161 */         BitmapProvider bitmapProvider = new BitmapProvider($$3, $$9);
/* 162 */         if ($$2 != null) $$2.close();  return bitmapProvider; } catch (Throwable throwable) { if ($$2 != null)
/*     */           try { $$2.close(); }
/*     */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/* 166 */        } private int getActualGlyphWidth(NativeImage $$0, int $$1, int $$2, int $$3, int $$4) { int $$5 = $$1 - 1;
/* 167 */       for (; $$5 >= 0; $$5--) {
/* 168 */         int $$6 = $$3 * $$1 + $$5;
/* 169 */         for (int $$7 = 0; $$7 < $$2; $$7++) {
/* 170 */           int $$8 = $$4 * $$2 + $$7;
/*     */           
/* 172 */           if ($$0.getLuminanceOrAlpha($$6, $$8) != 0) {
/* 173 */             return $$5 + 1;
/*     */           }
/*     */         } 
/*     */       } 
/* 177 */       return $$5 + 1; }
/*     */      }
/*     */   private static final class Glyph extends Record implements GlyphInfo { final float scale; final NativeImage image; final int offsetX; final int offsetY; final int width; final int height; private final int advance; final int ascent;
/*     */     
/* 181 */     Glyph(float $$0, NativeImage $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) { this.scale = $$0; this.image = $$1; this.offsetX = $$2; this.offsetY = $$3; this.width = $$4; this.height = $$5; this.advance = $$6; this.ascent = $$7; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #181	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #181	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #181	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;
/* 181 */       //   0	8	1	$$0	Ljava/lang/Object; } public float scale() { return this.scale; } public NativeImage image() { return this.image; } public int offsetX() { return this.offsetX; } public int offsetY() { return this.offsetY; } public int width() { return this.width; } public int height() { return this.height; } public int advance() { return this.advance; } public int ascent() { return this.ascent; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public float getAdvance() {
/* 194 */       return this.advance;
/*     */     }
/*     */     
/*     */     public BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> $$0)
/*     */     {
/* 199 */       return $$0.apply(new SheetGlyphInfo()
/*     */           {
/*     */             public float getOversample() {
/* 202 */               return 1.0F / BitmapProvider.Glyph.this.scale;
/*     */             }
/*     */ 
/*     */             
/*     */             public int getPixelWidth() {
/* 207 */               return BitmapProvider.Glyph.this.width;
/*     */             }
/*     */ 
/*     */             
/*     */             public int getPixelHeight() {
/* 212 */               return BitmapProvider.Glyph.this.height;
/*     */             }
/*     */ 
/*     */             
/*     */             public float getBearingY() {
/* 217 */               return super.getBearingY() + 7.0F - BitmapProvider.Glyph.this.ascent;
/*     */             }
/*     */ 
/*     */             
/*     */             public void upload(int $$0, int $$1) {
/* 222 */               BitmapProvider.Glyph.this.image.upload(0, $$0, $$1, BitmapProvider.Glyph.this.offsetX, BitmapProvider.Glyph.this.offsetY, BitmapProvider.Glyph.this.width, BitmapProvider.Glyph.this.height, false, false);
/*     */             }
/*     */             
/*     */             public boolean isColored()
/*     */             {
/* 227 */               return (BitmapProvider.Glyph.this.image.format().components() > 1); } }); } } class null implements SheetGlyphInfo { public float getOversample() { return 1.0F / BitmapProvider.Glyph.this.scale; } public int getPixelWidth() { return BitmapProvider.Glyph.this.width; } public boolean isColored() { return (BitmapProvider.Glyph.this.image.format().components() > 1); }
/*     */ 
/*     */     
/*     */     public int getPixelHeight() {
/*     */       return BitmapProvider.Glyph.this.height;
/*     */     }
/*     */     
/*     */     public float getBearingY() {
/*     */       return super.getBearingY() + 7.0F - BitmapProvider.Glyph.this.ascent;
/*     */     }
/*     */     
/*     */     public void upload(int $$0, int $$1) {
/*     */       BitmapProvider.Glyph.this.image.upload(0, $$0, $$1, BitmapProvider.Glyph.this.offsetX, BitmapProvider.Glyph.this.offsetY, BitmapProvider.Glyph.this.width, BitmapProvider.Glyph.this.height, false, false);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\BitmapProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */