/*     */ package net.minecraft.client.gui.font.providers;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.blaze3d.font.GlyphInfo;
/*     */ import com.mojang.blaze3d.font.GlyphProvider;
/*     */ import com.mojang.blaze3d.font.SheetGlyphInfo;
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.bytes.ByteList;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.Function;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.font.CodepointMap;
/*     */ import net.minecraft.client.gui.font.glyphs.BakedGlyph;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.FastBufferedInputStream;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class UnihexProvider implements GlyphProvider {
/*  38 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int GLYPH_HEIGHT = 16;
/*     */   
/*     */   private static final int DIGITS_PER_BYTE = 2;
/*     */   
/*     */   private static final int DIGITS_FOR_WIDTH_8 = 32;
/*     */   
/*     */   private static final int DIGITS_FOR_WIDTH_16 = 64;
/*     */   
/*     */   private static final int DIGITS_FOR_WIDTH_24 = 96;
/*     */   private static final int DIGITS_FOR_WIDTH_32 = 128;
/*     */   private final CodepointMap<Glyph> glyphs;
/*     */   
/*     */   UnihexProvider(CodepointMap<Glyph> $$0) {
/*  53 */     this.glyphs = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GlyphInfo getGlyph(int $$0) {
/*  59 */     return (GlyphInfo)this.glyphs.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public IntSet getSupportedGlyphs() {
/*  64 */     return this.glyphs.keySet();
/*     */   }
/*     */   private static final class OverrideRange extends Record { final int from; final int to; final UnihexProvider.Dimensions dimensions; private static final Codec<OverrideRange> RAW_CODEC; public static final Codec<OverrideRange> CODEC;
/*  67 */     private OverrideRange(int $$0, int $$1, UnihexProvider.Dimensions $$2) { this.from = $$0; this.to = $$1; this.dimensions = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #67	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  67 */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange; } public int from() { return this.from; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #67	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #67	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$OverrideRange;
/*  67 */       //   0	8	1	$$0	Ljava/lang/Object; } public int to() { return this.to; } public UnihexProvider.Dimensions dimensions() { return this.dimensions; } static {
/*  68 */       RAW_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.CODEPOINT.fieldOf("from").forGetter(OverrideRange::from), (App)ExtraCodecs.CODEPOINT.fieldOf("to").forGetter(OverrideRange::to), (App)UnihexProvider.Dimensions.MAP_CODEC.forGetter(OverrideRange::dimensions)).apply((Applicative)$$0, OverrideRange::new));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  74 */       CODEC = ExtraCodecs.validate(RAW_CODEC, $$0 -> ($$0.from >= $$0.to) ? DataResult.error(()) : DataResult.success($$0));
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Dimensions
/*     */     extends Record
/*     */   {
/*     */     final int left;
/*     */     
/*     */     final int right;
/*     */     public static final MapCodec<Dimensions> MAP_CODEC;
/*     */     
/*     */     public Dimensions(int $$0, int $$1)
/*     */     {
/*  89 */       this.left = $$0; this.right = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$Dimensions;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$Dimensions; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$Dimensions;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$Dimensions; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$Dimensions;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$Dimensions;
/*  89 */       //   0	8	1	$$0	Ljava/lang/Object; } public int left() { return this.left; } public int right() { return this.right; } static {
/*  90 */       MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.INT.fieldOf("left").forGetter(Dimensions::left), (App)Codec.INT.fieldOf("right").forGetter(Dimensions::right)).apply((Applicative)$$0, Dimensions::new));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  95 */     public static final Codec<Dimensions> CODEC = MAP_CODEC.codec();
/*     */     
/*     */     public int pack() {
/*  98 */       return pack(this.left, this.right);
/*     */     }
/*     */     
/*     */     public static int pack(int $$0, int $$1) {
/* 102 */       return ($$0 & 0xFF) << 8 | $$1 & 0xFF;
/*     */     }
/*     */ 
/*     */     
/*     */     public static int left(int $$0) {
/* 107 */       return (byte)($$0 >> 8);
/*     */     }
/*     */ 
/*     */     
/*     */     public static int right(int $$0) {
/* 112 */       return (byte)$$0;
/*     */     } }
/*     */   public static class Definition implements GlyphProviderDefinition { public static final MapCodec<Definition> CODEC; private final ResourceLocation hexFile; private final List<UnihexProvider.OverrideRange> sizeOverrides;
/*     */     
/*     */     static {
/* 117 */       CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("hex_file").forGetter(()), (App)UnihexProvider.OverrideRange.CODEC.listOf().fieldOf("size_overrides").forGetter(())).apply((Applicative)$$0, Definition::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Definition(ResourceLocation $$0, List<UnihexProvider.OverrideRange> $$1) {
/* 126 */       this.hexFile = $$0;
/* 127 */       this.sizeOverrides = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public GlyphProviderType type() {
/* 132 */       return GlyphProviderType.UNIHEX;
/*     */     }
/*     */ 
/*     */     
/*     */     public Either<GlyphProviderDefinition.Loader, GlyphProviderDefinition.Reference> unpack() {
/* 137 */       return Either.left(this::load);
/*     */     }
/*     */     
/*     */     private GlyphProvider load(ResourceManager $$0) throws IOException {
/* 141 */       InputStream $$1 = $$0.open(this.hexFile); 
/* 142 */       try { UnihexProvider unihexProvider = loadData($$1);
/* 143 */         if ($$1 != null) $$1.close();  return unihexProvider; } catch (Throwable throwable) { if ($$1 != null)
/*     */           try { $$1.close(); }
/*     */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/* 147 */        } private UnihexProvider loadData(InputStream $$0) throws IOException { CodepointMap<UnihexProvider.LineData> $$1 = new CodepointMap($$0 -> new UnihexProvider.LineData[$$0], $$0 -> new UnihexProvider.LineData[$$0][]);
/* 148 */       Objects.requireNonNull($$1); UnihexProvider.ReaderOutput $$2 = $$1::put;
/*     */       
/* 150 */       ZipInputStream $$3 = new ZipInputStream($$0); try {
/*     */         ZipEntry $$4;
/* 152 */         while (($$4 = $$3.getNextEntry()) != null) {
/* 153 */           String $$5 = $$4.getName();
/* 154 */           if ($$5.endsWith(".hex")) {
/* 155 */             UnihexProvider.LOGGER.info("Found {}, loading", $$5);
/* 156 */             UnihexProvider.readFromStream((InputStream)new FastBufferedInputStream($$3), $$2);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 161 */         CodepointMap<UnihexProvider.Glyph> $$6 = new CodepointMap($$0 -> new UnihexProvider.Glyph[$$0], $$0 -> new UnihexProvider.Glyph[$$0][]);
/*     */         
/* 163 */         for (UnihexProvider.OverrideRange $$7 : this.sizeOverrides) {
/* 164 */           int $$8 = $$7.from;
/* 165 */           int $$9 = $$7.to;
/* 166 */           UnihexProvider.Dimensions $$10 = $$7.dimensions;
/* 167 */           for (int $$11 = $$8; $$11 <= $$9; $$11++) {
/* 168 */             UnihexProvider.LineData $$12 = (UnihexProvider.LineData)$$1.remove($$11);
/* 169 */             if ($$12 != null) {
/* 170 */               $$6.put($$11, new UnihexProvider.Glyph($$12, $$10.left, $$10.right));
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 175 */         $$1.forEach(($$1, $$2) -> {
/*     */               int $$3 = $$2.calculateWidth();
/*     */               
/*     */               int $$4 = UnihexProvider.Dimensions.left($$3);
/*     */               int $$5 = UnihexProvider.Dimensions.right($$3);
/*     */               $$0.put($$1, new UnihexProvider.Glyph($$2, $$4, $$5));
/*     */             });
/* 182 */         UnihexProvider unihexProvider = new UnihexProvider($$6);
/* 183 */         $$3.close();
/*     */         return unihexProvider;
/*     */       } catch (Throwable throwable) {
/*     */         try {
/*     */           $$3.close();
/*     */         } catch (Throwable throwable1) {
/*     */           throwable.addSuppressed(throwable1);
/*     */         } 
/*     */         throw throwable;
/*     */       }  } } public static interface LineData { int line(int param1Int); int bitWidth(); default int mask() {
/* 193 */       int $$0 = 0;
/* 194 */       for (int $$1 = 0; $$1 < 16; $$1++) {
/* 195 */         $$0 |= line($$1);
/*     */       }
/* 197 */       return $$0;
/*     */     }
/*     */     
/*     */     default int calculateWidth() {
/* 201 */       int $$4, $$5, $$0 = mask();
/* 202 */       int $$1 = bitWidth();
/*     */ 
/*     */ 
/*     */       
/* 206 */       if ($$0 == 0) {
/*     */         
/* 208 */         int $$2 = 0;
/* 209 */         int $$3 = $$1;
/*     */       } else {
/* 211 */         $$4 = Integer.numberOfLeadingZeros($$0);
/* 212 */         $$5 = 32 - Integer.numberOfTrailingZeros($$0) - 1;
/*     */       } 
/* 214 */       return UnihexProvider.Dimensions.pack($$4, $$5);
/*     */     } }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static void unpackBitsToBytes(IntBuffer $$0, int $$1, int $$2, int $$3) {
/* 220 */     int $$4 = 32 - $$2 - 1;
/* 221 */     int $$5 = 32 - $$3 - 1;
/*     */     
/* 223 */     for (int $$6 = $$4; $$6 >= $$5; $$6--) {
/* 224 */       if ($$6 >= 32 || $$6 < 0) {
/* 225 */         $$0.put(0);
/*     */       } else {
/* 227 */         boolean $$7 = (($$1 >> $$6 & 0x1) != 0);
/* 228 */         $$0.put($$7 ? -1 : 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static void unpackBitsToBytes(IntBuffer $$0, LineData $$1, int $$2, int $$3) {
/* 234 */     for (int $$4 = 0; $$4 < 16; $$4++) {
/* 235 */       int $$5 = $$1.line($$4);
/* 236 */       unpackBitsToBytes($$0, $$5, $$2, $$3);
/*     */     } 
/*     */   }
/*     */   private static final class ByteContents extends Record implements LineData { private final byte[] contents;
/* 240 */     private ByteContents(byte[] $$0) { this.contents = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$ByteContents;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #240	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$ByteContents; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$ByteContents;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #240	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$ByteContents; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$ByteContents;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #240	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$ByteContents;
/* 240 */       //   0	8	1	$$0	Ljava/lang/Object; } public byte[] contents() { return this.contents; }
/*     */     
/*     */     public int line(int $$0) {
/* 243 */       return this.contents[$$0] << 24;
/*     */     }
/*     */     
/*     */     static UnihexProvider.LineData read(int $$0, ByteList $$1) {
/* 247 */       byte[] $$2 = new byte[16];
/* 248 */       int $$3 = 0;
/* 249 */       for (int $$4 = 0; $$4 < 16; $$4++) {
/* 250 */         int $$5 = UnihexProvider.decodeHex($$0, $$1, $$3++);
/* 251 */         int $$6 = UnihexProvider.decodeHex($$0, $$1, $$3++);
/* 252 */         byte $$7 = (byte)($$5 << 4 | $$6);
/* 253 */         $$2[$$4] = $$7;
/*     */       } 
/* 255 */       return new ByteContents($$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public int bitWidth() {
/* 260 */       return 8;
/*     */     } }
/*     */   private static final class ShortContents extends Record implements LineData { private final short[] contents;
/*     */     
/* 264 */     private ShortContents(short[] $$0) { this.contents = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$ShortContents;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #264	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$ShortContents; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$ShortContents;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #264	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$ShortContents; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$ShortContents;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #264	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$ShortContents;
/* 264 */       //   0	8	1	$$0	Ljava/lang/Object; } public short[] contents() { return this.contents; }
/*     */     
/*     */     public int line(int $$0) {
/* 267 */       return this.contents[$$0] << 16;
/*     */     }
/*     */     
/*     */     static UnihexProvider.LineData read(int $$0, ByteList $$1) {
/* 271 */       short[] $$2 = new short[16];
/* 272 */       int $$3 = 0;
/* 273 */       for (int $$4 = 0; $$4 < 16; $$4++) {
/* 274 */         int $$5 = UnihexProvider.decodeHex($$0, $$1, $$3++);
/* 275 */         int $$6 = UnihexProvider.decodeHex($$0, $$1, $$3++);
/* 276 */         int $$7 = UnihexProvider.decodeHex($$0, $$1, $$3++);
/* 277 */         int $$8 = UnihexProvider.decodeHex($$0, $$1, $$3++);
/* 278 */         short $$9 = (short)($$5 << 12 | $$6 << 8 | $$7 << 4 | $$8);
/* 279 */         $$2[$$4] = $$9;
/*     */       } 
/* 281 */       return new ShortContents($$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public int bitWidth() {
/* 286 */       return 16;
/*     */     } }
/*     */   private static final class IntContents extends Record implements LineData { private final int[] contents; private final int bitWidth; private static final int SIZE_24 = 24;
/*     */     
/* 290 */     private IntContents(int[] $$0, int $$1) { this.contents = $$0; this.bitWidth = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #290	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #290	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #290	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$IntContents;
/* 290 */       //   0	8	1	$$0	Ljava/lang/Object; } public int[] contents() { return this.contents; } public int bitWidth() { return this.bitWidth; }
/*     */ 
/*     */ 
/*     */     
/*     */     public int line(int $$0) {
/* 295 */       return this.contents[$$0];
/*     */     }
/*     */     
/*     */     static UnihexProvider.LineData read24(int $$0, ByteList $$1) {
/* 299 */       int[] $$2 = new int[16];
/* 300 */       int $$3 = 0;
/* 301 */       int $$4 = 0;
/* 302 */       for (int $$5 = 0; $$5 < 16; $$5++) {
/* 303 */         int $$6 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 304 */         int $$7 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 305 */         int $$8 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 306 */         int $$9 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 307 */         int $$10 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 308 */         int $$11 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 309 */         int $$12 = $$6 << 20 | $$7 << 16 | $$8 << 12 | $$9 << 8 | $$10 << 4 | $$11;
/* 310 */         $$2[$$5] = $$12 << 8;
/* 311 */         $$3 |= $$12;
/*     */       } 
/* 313 */       return new IntContents($$2, 24);
/*     */     }
/*     */     
/*     */     public static UnihexProvider.LineData read32(int $$0, ByteList $$1) {
/* 317 */       int[] $$2 = new int[16];
/* 318 */       int $$3 = 0;
/* 319 */       int $$4 = 0;
/* 320 */       for (int $$5 = 0; $$5 < 16; $$5++) {
/* 321 */         int $$6 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 322 */         int $$7 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 323 */         int $$8 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 324 */         int $$9 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 325 */         int $$10 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 326 */         int $$11 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 327 */         int $$12 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 328 */         int $$13 = UnihexProvider.decodeHex($$0, $$1, $$4++);
/* 329 */         int $$14 = $$6 << 28 | $$7 << 24 | $$8 << 20 | $$9 << 16 | $$10 << 12 | $$11 << 8 | $$12 << 4 | $$13;
/* 330 */         $$2[$$5] = $$14;
/* 331 */         $$3 |= $$14;
/*     */       } 
/* 333 */       return new IntContents($$2, 32);
/*     */     } }
/*     */   private static final class Glyph extends Record implements GlyphInfo { final UnihexProvider.LineData contents; final int left; final int right;
/*     */     
/* 337 */     Glyph(UnihexProvider.LineData $$0, int $$1, int $$2) { this.contents = $$0; this.left = $$1; this.right = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$Glyph;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #337	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$Glyph; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$Glyph;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #337	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$Glyph; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/UnihexProvider$Glyph;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #337	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/UnihexProvider$Glyph;
/* 337 */       //   0	8	1	$$0	Ljava/lang/Object; } public UnihexProvider.LineData contents() { return this.contents; } public int left() { return this.left; } public int right() { return this.right; }
/*     */      public int width() {
/* 339 */       return this.right - this.left + 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getAdvance() {
/* 344 */       return (width() / 2 + 1);
/*     */     }
/*     */ 
/*     */     
/*     */     public float getShadowOffset() {
/* 349 */       return 0.5F;
/*     */     }
/*     */ 
/*     */     
/*     */     public float getBoldOffset() {
/* 354 */       return 0.5F;
/*     */     }
/*     */     
/*     */     public BakedGlyph bake(Function<SheetGlyphInfo, BakedGlyph> $$0)
/*     */     {
/* 359 */       return $$0.apply(new SheetGlyphInfo()
/*     */           {
/*     */             public float getOversample() {
/* 362 */               return 2.0F;
/*     */             }
/*     */ 
/*     */             
/*     */             public int getPixelWidth() {
/* 367 */               return UnihexProvider.Glyph.this.width();
/*     */             }
/*     */ 
/*     */             
/*     */             public int getPixelHeight() {
/* 372 */               return 16;
/*     */             }
/*     */ 
/*     */             
/*     */             public void upload(int $$0, int $$1) {
/* 377 */               IntBuffer $$2 = MemoryUtil.memAllocInt(UnihexProvider.Glyph.this.width() * 16);
/* 378 */               UnihexProvider.unpackBitsToBytes($$2, UnihexProvider.Glyph.this.contents, UnihexProvider.Glyph.this.left, UnihexProvider.Glyph.this.right);
/* 379 */               $$2.rewind();
/* 380 */               GlStateManager.upload(0, $$0, $$1, UnihexProvider.Glyph.this.width(), 16, NativeImage.Format.RGBA, $$2, MemoryUtil::memFree);
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean isColored()
/*     */             {
/* 386 */               return true; } }); } } class null implements SheetGlyphInfo { public float getOversample() { return 2.0F; } public int getPixelWidth() { return UnihexProvider.Glyph.this.width(); } public boolean isColored() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getPixelHeight() {
/*     */       return 16;
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
/*     */     public void upload(int $$0, int $$1) {
/*     */       IntBuffer $$2 = MemoryUtil.memAllocInt(UnihexProvider.Glyph.this.width() * 16);
/*     */       UnihexProvider.unpackBitsToBytes($$2, UnihexProvider.Glyph.this.contents, UnihexProvider.Glyph.this.left, UnihexProvider.Glyph.this.right);
/*     */       $$2.rewind();
/*     */       GlStateManager.upload(0, $$0, $$1, UnihexProvider.Glyph.this.width(), 16, NativeImage.Format.RGBA, $$2, MemoryUtil::memFree);
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   static void readFromStream(InputStream $$0, ReaderOutput $$1) throws IOException {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_2
/*     */     //   2: new it/unimi/dsi/fastutil/bytes/ByteArrayList
/*     */     //   5: dup
/*     */     //   6: sipush #128
/*     */     //   9: invokespecial <init> : (I)V
/*     */     //   12: astore_3
/*     */     //   13: aload_0
/*     */     //   14: aload_3
/*     */     //   15: bipush #58
/*     */     //   17: invokestatic copyUntil : (Ljava/io/InputStream;Lit/unimi/dsi/fastutil/bytes/ByteList;I)Z
/*     */     //   20: istore #4
/*     */     //   22: aload_3
/*     */     //   23: invokeinterface size : ()I
/*     */     //   28: istore #5
/*     */     //   30: iload #5
/*     */     //   32: ifne -> 43
/*     */     //   35: iload #4
/*     */     //   37: ifne -> 43
/*     */     //   40: goto -> 254
/*     */     //   43: iload #4
/*     */     //   45: ifeq -> 67
/*     */     //   48: iload #5
/*     */     //   50: iconst_4
/*     */     //   51: if_icmpeq -> 81
/*     */     //   54: iload #5
/*     */     //   56: iconst_5
/*     */     //   57: if_icmpeq -> 81
/*     */     //   60: iload #5
/*     */     //   62: bipush #6
/*     */     //   64: if_icmpeq -> 81
/*     */     //   67: new java/lang/IllegalArgumentException
/*     */     //   70: dup
/*     */     //   71: iload_2
/*     */     //   72: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
/*     */     //   77: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   80: athrow
/*     */     //   81: iconst_0
/*     */     //   82: istore #6
/*     */     //   84: iconst_0
/*     */     //   85: istore #7
/*     */     //   87: iload #7
/*     */     //   89: iload #5
/*     */     //   91: if_icmpge -> 119
/*     */     //   94: iload #6
/*     */     //   96: iconst_4
/*     */     //   97: ishl
/*     */     //   98: iload_2
/*     */     //   99: aload_3
/*     */     //   100: iload #7
/*     */     //   102: invokeinterface getByte : (I)B
/*     */     //   107: invokestatic decodeHex : (IB)I
/*     */     //   110: ior
/*     */     //   111: istore #6
/*     */     //   113: iinc #7, 1
/*     */     //   116: goto -> 87
/*     */     //   119: aload_3
/*     */     //   120: invokeinterface clear : ()V
/*     */     //   125: aload_0
/*     */     //   126: aload_3
/*     */     //   127: bipush #10
/*     */     //   129: invokestatic copyUntil : (Ljava/io/InputStream;Lit/unimi/dsi/fastutil/bytes/ByteList;I)Z
/*     */     //   132: pop
/*     */     //   133: aload_3
/*     */     //   134: invokeinterface size : ()I
/*     */     //   139: istore #7
/*     */     //   141: iload #7
/*     */     //   143: lookupswitch default -> 216, 32 -> 184, 64 -> 192, 96 -> 200, 128 -> 208
/*     */     //   184: iload_2
/*     */     //   185: aload_3
/*     */     //   186: invokestatic read : (ILit/unimi/dsi/fastutil/bytes/ByteList;)Lnet/minecraft/client/gui/font/providers/UnihexProvider$LineData;
/*     */     //   189: goto -> 230
/*     */     //   192: iload_2
/*     */     //   193: aload_3
/*     */     //   194: invokestatic read : (ILit/unimi/dsi/fastutil/bytes/ByteList;)Lnet/minecraft/client/gui/font/providers/UnihexProvider$LineData;
/*     */     //   197: goto -> 230
/*     */     //   200: iload_2
/*     */     //   201: aload_3
/*     */     //   202: invokestatic read24 : (ILit/unimi/dsi/fastutil/bytes/ByteList;)Lnet/minecraft/client/gui/font/providers/UnihexProvider$LineData;
/*     */     //   205: goto -> 230
/*     */     //   208: iload_2
/*     */     //   209: aload_3
/*     */     //   210: invokestatic read32 : (ILit/unimi/dsi/fastutil/bytes/ByteList;)Lnet/minecraft/client/gui/font/providers/UnihexProvider$LineData;
/*     */     //   213: goto -> 230
/*     */     //   216: new java/lang/IllegalArgumentException
/*     */     //   219: dup
/*     */     //   220: iload_2
/*     */     //   221: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
/*     */     //   226: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   229: athrow
/*     */     //   230: astore #8
/*     */     //   232: aload_1
/*     */     //   233: iload #6
/*     */     //   235: aload #8
/*     */     //   237: invokeinterface accept : (ILnet/minecraft/client/gui/font/providers/UnihexProvider$LineData;)V
/*     */     //   242: iinc #2, 1
/*     */     //   245: aload_3
/*     */     //   246: invokeinterface clear : ()V
/*     */     //   251: goto -> 13
/*     */     //   254: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #399	-> 0
/*     */     //   #401	-> 2
/*     */     //   #403	-> 13
/*     */     //   #404	-> 22
/*     */     //   #405	-> 30
/*     */     //   #406	-> 40
/*     */     //   #410	-> 43
/*     */     //   #411	-> 67
/*     */     //   #413	-> 81
/*     */     //   #414	-> 84
/*     */     //   #415	-> 94
/*     */     //   #414	-> 113
/*     */     //   #418	-> 119
/*     */     //   #419	-> 125
/*     */     //   #420	-> 133
/*     */     //   #421	-> 141
/*     */     //   #422	-> 184
/*     */     //   #423	-> 192
/*     */     //   #424	-> 200
/*     */     //   #425	-> 208
/*     */     //   #427	-> 216
/*     */     //   #430	-> 232
/*     */     //   #431	-> 242
/*     */     //   #432	-> 245
/*     */     //   #433	-> 251
/*     */     //   #434	-> 254
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	255	0	$$0	Ljava/io/InputStream;
/*     */     //   0	255	1	$$1	Lnet/minecraft/client/gui/font/providers/UnihexProvider$ReaderOutput;
/*     */     //   2	253	2	$$2	I
/*     */     //   13	242	3	$$3	Lit/unimi/dsi/fastutil/bytes/ByteList;
/*     */     //   22	229	4	$$4	Z
/*     */     //   30	221	5	$$5	I
/*     */     //   84	167	6	$$6	I
/*     */     //   87	32	7	$$7	I
/*     */     //   141	110	7	$$8	I
/*     */     //   232	19	8	$$9	Lnet/minecraft/client/gui/font/providers/UnihexProvider$LineData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int decodeHex(int $$0, ByteList $$1, int $$2) {
/* 437 */     return decodeHex($$0, $$1.getByte($$2));
/*     */   }
/*     */   
/*     */   private static int decodeHex(int $$0, byte $$1) {
/* 441 */     switch ($$1) { case 48: 
/*     */       case 49: 
/*     */       case 50: 
/*     */       case 51: 
/*     */       case 52: 
/*     */       case 53: 
/*     */       case 54: 
/*     */       case 55: 
/*     */       case 56: 
/*     */       case 57: 
/*     */       case 65: 
/*     */       case 66: 
/*     */       case 67: 
/*     */       case 68:
/*     */       
/*     */       case 69:
/*     */       
/*     */       case 70:
/* 459 */        }  throw new IllegalArgumentException("Invalid entry at line " + $$0 + ": expected hex digit, got " + (char)$$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean copyUntil(InputStream $$0, ByteList $$1, int $$2) throws IOException {
/*     */     while (true) {
/* 465 */       int $$3 = $$0.read();
/* 466 */       if ($$3 == -1)
/* 467 */         return false; 
/* 468 */       if ($$3 == $$2) {
/* 469 */         return true;
/*     */       }
/* 471 */       $$1.add((byte)$$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ReaderOutput {
/*     */     void accept(int param1Int, UnihexProvider.LineData param1LineData);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\UnihexProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */