/*     */ package net.minecraft.client.gui.font;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.blaze3d.font.GlyphInfo;
/*     */ import com.mojang.blaze3d.font.GlyphProvider;
/*     */ import com.mojang.blaze3d.font.SheetGlyphInfo;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*     */ import net.minecraft.client.gui.font.glyphs.SpecialGlyphs;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ 
/*     */ public class FontSet implements AutoCloseable {
/*  25 */   private static final RandomSource RANDOM = RandomSource.create();
/*     */   
/*     */   private static final float LARGE_FORWARD_ADVANCE = 32.0F;
/*     */   
/*     */   private final TextureManager textureManager;
/*     */   
/*     */   private final ResourceLocation name;
/*     */   private BakedGlyph missingGlyph;
/*     */   private BakedGlyph whiteGlyph;
/*  34 */   private final List<GlyphProvider> providers = Lists.newArrayList(); private final CodepointMap<BakedGlyph> glyphs; private final CodepointMap<GlyphInfoFilter> glyphInfos; private final Int2ObjectMap<IntList> glyphsByWidth; private final List<FontTexture> textures; public FontSet(TextureManager $$0, ResourceLocation $$1) {
/*  35 */     this.glyphs = new CodepointMap<>($$0 -> new BakedGlyph[$$0], $$0 -> new BakedGlyph[$$0][]);
/*  36 */     this.glyphInfos = new CodepointMap<>($$0 -> new GlyphInfoFilter[$$0], $$0 -> new GlyphInfoFilter[$$0][]);
/*  37 */     this.glyphsByWidth = (Int2ObjectMap<IntList>)new Int2ObjectOpenHashMap();
/*  38 */     this.textures = Lists.newArrayList();
/*     */ 
/*     */     
/*  41 */     this.textureManager = $$0;
/*  42 */     this.name = $$1;
/*     */   }
/*     */   
/*     */   public void reload(List<GlyphProvider> $$0) {
/*  46 */     closeProviders();
/*  47 */     closeTextures();
/*     */     
/*  49 */     this.glyphs.clear();
/*  50 */     this.glyphInfos.clear();
/*  51 */     this.glyphsByWidth.clear();
/*     */     
/*  53 */     this.missingGlyph = SpecialGlyphs.MISSING.bake(this::stitch);
/*  54 */     this.whiteGlyph = SpecialGlyphs.WHITE.bake(this::stitch);
/*     */     
/*  56 */     IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
/*  57 */     for (GlyphProvider $$2 : $$0) {
/*  58 */       intOpenHashSet.addAll((IntCollection)$$2.getSupportedGlyphs());
/*     */     }
/*     */     
/*  61 */     Set<GlyphProvider> $$3 = Sets.newHashSet();
/*  62 */     intOpenHashSet.forEach($$2 -> {
/*     */           for (GlyphProvider $$3 : $$0) {
/*     */             GlyphInfo $$4 = $$3.getGlyph($$2);
/*     */             
/*     */             if ($$4 != null) {
/*     */               $$1.add($$3);
/*     */               if ($$4 != SpecialGlyphs.MISSING) {
/*     */                 ((IntList)this.glyphsByWidth.computeIfAbsent(Mth.ceil($$4.getAdvance(false)), ())).add($$2);
/*     */               }
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         });
/*  75 */     Objects.requireNonNull($$3); Objects.requireNonNull(this.providers); $$0.stream().filter($$3::contains).forEach(this.providers::add);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/*  80 */     closeProviders();
/*  81 */     closeTextures();
/*     */   }
/*     */   
/*     */   private void closeProviders() {
/*  85 */     for (GlyphProvider $$0 : this.providers) {
/*  86 */       $$0.close();
/*     */     }
/*  88 */     this.providers.clear();
/*     */   }
/*     */   
/*     */   private void closeTextures() {
/*  92 */     for (FontTexture $$0 : this.textures) {
/*  93 */       $$0.close();
/*     */     }
/*  95 */     this.textures.clear();
/*     */   }
/*     */   
/*     */   private static boolean hasFishyAdvance(GlyphInfo $$0) {
/*  99 */     float $$1 = $$0.getAdvance(false);
/* 100 */     if ($$1 < 0.0F || $$1 > 32.0F) {
/* 101 */       return true;
/*     */     }
/*     */     
/* 104 */     float $$2 = $$0.getAdvance(true);
/* 105 */     if ($$2 < 0.0F || $$2 > 32.0F) {
/* 106 */       return true;
/*     */     }
/*     */     
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   private GlyphInfoFilter computeGlyphInfo(int $$0) {
/* 113 */     GlyphInfo $$1 = null;
/*     */     
/* 115 */     for (GlyphProvider $$2 : this.providers) {
/* 116 */       GlyphInfo $$3 = $$2.getGlyph($$0);
/* 117 */       if ($$3 == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 121 */       if ($$1 == null) {
/* 122 */         $$1 = $$3;
/*     */       }
/* 124 */       if (!hasFishyAdvance($$3)) {
/* 125 */         return new GlyphInfoFilter($$1, $$3);
/*     */       }
/*     */     } 
/*     */     
/* 129 */     if ($$1 != null) {
/* 130 */       return new GlyphInfoFilter($$1, (GlyphInfo)SpecialGlyphs.MISSING);
/*     */     }
/*     */     
/* 133 */     return GlyphInfoFilter.MISSING;
/*     */   }
/*     */   
/*     */   public GlyphInfo getGlyphInfo(int $$0, boolean $$1) {
/* 137 */     return ((GlyphInfoFilter)this.glyphInfos.computeIfAbsent($$0, this::computeGlyphInfo)).select($$1);
/*     */   }
/*     */   
/*     */   private BakedGlyph computeBakedGlyph(int $$0) {
/* 141 */     for (GlyphProvider $$1 : this.providers) {
/* 142 */       GlyphInfo $$2 = $$1.getGlyph($$0);
/* 143 */       if ($$2 != null) {
/* 144 */         return $$2.bake(this::stitch);
/*     */       }
/*     */     } 
/*     */     
/* 148 */     return this.missingGlyph;
/*     */   }
/*     */   
/*     */   public BakedGlyph getGlyph(int $$0) {
/* 152 */     return this.glyphs.computeIfAbsent($$0, this::computeBakedGlyph);
/*     */   }
/*     */   
/*     */   private BakedGlyph stitch(SheetGlyphInfo $$0) {
/* 156 */     for (FontTexture $$1 : this.textures) {
/* 157 */       BakedGlyph $$2 = $$1.add($$0);
/* 158 */       if ($$2 != null) {
/* 159 */         return $$2;
/*     */       }
/*     */     } 
/*     */     
/* 163 */     ResourceLocation $$3 = this.name.withSuffix("/" + this.textures.size());
/* 164 */     boolean $$4 = $$0.isColored();
/* 165 */     GlyphRenderTypes $$5 = $$4 ? GlyphRenderTypes.createForColorTexture($$3) : GlyphRenderTypes.createForIntensityTexture($$3);
/* 166 */     FontTexture $$6 = new FontTexture($$5, $$4);
/* 167 */     this.textures.add($$6);
/* 168 */     this.textureManager.register($$3, $$6);
/* 169 */     BakedGlyph $$7 = $$6.add($$0);
/*     */     
/* 171 */     return ($$7 == null) ? this.missingGlyph : $$7;
/*     */   }
/*     */   
/*     */   public BakedGlyph getRandomGlyph(GlyphInfo $$0) {
/* 175 */     IntList $$1 = (IntList)this.glyphsByWidth.get(Mth.ceil($$0.getAdvance(false)));
/* 176 */     if ($$1 != null && !$$1.isEmpty()) {
/* 177 */       return getGlyph($$1.getInt(RANDOM.nextInt($$1.size())));
/*     */     }
/* 179 */     return this.missingGlyph;
/*     */   }
/*     */   
/*     */   public BakedGlyph whiteGlyph() {
/* 183 */     return this.whiteGlyph;
/*     */   }
/*     */   private static final class GlyphInfoFilter extends Record { private final GlyphInfo glyphInfo; private final GlyphInfo glyphInfoNotFishy;
/* 186 */     GlyphInfoFilter(GlyphInfo $$0, GlyphInfo $$1) { this.glyphInfo = $$0; this.glyphInfoNotFishy = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/FontSet$GlyphInfoFilter;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #186	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 186 */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontSet$GlyphInfoFilter; } public GlyphInfo glyphInfo() { return this.glyphInfo; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/FontSet$GlyphInfoFilter;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #186	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/FontSet$GlyphInfoFilter; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/FontSet$GlyphInfoFilter;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #186	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/FontSet$GlyphInfoFilter;
/* 186 */       //   0	8	1	$$0	Ljava/lang/Object; } public GlyphInfo glyphInfoNotFishy() { return this.glyphInfoNotFishy; }
/* 187 */      static final GlyphInfoFilter MISSING = new GlyphInfoFilter((GlyphInfo)SpecialGlyphs.MISSING, (GlyphInfo)SpecialGlyphs.MISSING);
/*     */     
/*     */     GlyphInfo select(boolean $$0) {
/* 190 */       return $$0 ? this.glyphInfoNotFishy : this.glyphInfo;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\FontSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */