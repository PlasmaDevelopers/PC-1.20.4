/*     */ package net.minecraft.client.gui.font.providers;
/*     */ 
/*     */ import com.mojang.blaze3d.font.GlyphProvider;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import net.minecraft.client.gui.font.CodepointMap;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.FastBufferedInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Definition
/*     */   implements GlyphProviderDefinition
/*     */ {
/*     */   public static final MapCodec<Definition> CODEC;
/*     */   private final ResourceLocation hexFile;
/*     */   private final List<UnihexProvider.OverrideRange> sizeOverrides;
/*     */   
/*     */   static {
/* 117 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("hex_file").forGetter(()), (App)UnihexProvider.OverrideRange.CODEC.listOf().fieldOf("size_overrides").forGetter(())).apply((Applicative)$$0, Definition::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Definition(ResourceLocation $$0, List<UnihexProvider.OverrideRange> $$1) {
/* 126 */     this.hexFile = $$0;
/* 127 */     this.sizeOverrides = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public GlyphProviderType type() {
/* 132 */     return GlyphProviderType.UNIHEX;
/*     */   }
/*     */ 
/*     */   
/*     */   public Either<GlyphProviderDefinition.Loader, GlyphProviderDefinition.Reference> unpack() {
/* 137 */     return Either.left(this::load);
/*     */   }
/*     */   
/*     */   private GlyphProvider load(ResourceManager $$0) throws IOException {
/* 141 */     InputStream $$1 = $$0.open(this.hexFile); 
/* 142 */     try { UnihexProvider unihexProvider = loadData($$1);
/* 143 */       if ($$1 != null) $$1.close();  return unihexProvider; } catch (Throwable throwable) { if ($$1 != null)
/*     */         try { $$1.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 147 */      } private UnihexProvider loadData(InputStream $$0) throws IOException { CodepointMap<UnihexProvider.LineData> $$1 = new CodepointMap($$0 -> new UnihexProvider.LineData[$$0], $$0 -> new UnihexProvider.LineData[$$0][]);
/* 148 */     Objects.requireNonNull($$1); UnihexProvider.ReaderOutput $$2 = $$1::put;
/*     */     
/* 150 */     ZipInputStream $$3 = new ZipInputStream($$0); try {
/*     */       ZipEntry $$4;
/* 152 */       while (($$4 = $$3.getNextEntry()) != null) {
/* 153 */         String $$5 = $$4.getName();
/* 154 */         if ($$5.endsWith(".hex")) {
/* 155 */           UnihexProvider.LOGGER.info("Found {}, loading", $$5);
/* 156 */           UnihexProvider.readFromStream((InputStream)new FastBufferedInputStream($$3), $$2);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 161 */       CodepointMap<UnihexProvider.Glyph> $$6 = new CodepointMap($$0 -> new UnihexProvider.Glyph[$$0], $$0 -> new UnihexProvider.Glyph[$$0][]);
/*     */       
/* 163 */       for (UnihexProvider.OverrideRange $$7 : this.sizeOverrides) {
/* 164 */         int $$8 = $$7.from;
/* 165 */         int $$9 = $$7.to;
/* 166 */         UnihexProvider.Dimensions $$10 = $$7.dimensions;
/* 167 */         for (int $$11 = $$8; $$11 <= $$9; $$11++) {
/* 168 */           UnihexProvider.LineData $$12 = (UnihexProvider.LineData)$$1.remove($$11);
/* 169 */           if ($$12 != null) {
/* 170 */             $$6.put($$11, new UnihexProvider.Glyph($$12, $$10.left, $$10.right));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 175 */       $$1.forEach(($$1, $$2) -> {
/*     */             int $$3 = $$2.calculateWidth();
/*     */             
/*     */             int $$4 = UnihexProvider.Dimensions.left($$3);
/*     */             int $$5 = UnihexProvider.Dimensions.right($$3);
/*     */             $$0.put($$1, new UnihexProvider.Glyph($$2, $$4, $$5));
/*     */           });
/* 182 */       UnihexProvider unihexProvider = new UnihexProvider($$6);
/* 183 */       $$3.close();
/*     */       return unihexProvider;
/*     */     } catch (Throwable throwable) {
/*     */       try {
/*     */         $$3.close();
/*     */       } catch (Throwable throwable1) {
/*     */         throwable.addSuppressed(throwable1);
/*     */       } 
/*     */       throw throwable;
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\UnihexProvider$Definition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */