/*    */ package com.mojang.blaze3d.font;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import it.unimi.dsi.fastutil.ints.IntSet;
/*    */ import it.unimi.dsi.fastutil.ints.IntSets;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.gui.font.providers.GlyphProviderDefinition;
/*    */ import net.minecraft.client.gui.font.providers.GlyphProviderType;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ public class SpaceProvider implements GlyphProvider {
/*    */   private final Int2ObjectMap<GlyphInfo.SpaceGlyphInfo> glyphs;
/*    */   
/*    */   public SpaceProvider(Map<Integer, Float> $$0) {
/* 22 */     this.glyphs = (Int2ObjectMap<GlyphInfo.SpaceGlyphInfo>)new Int2ObjectOpenHashMap($$0.size());
/* 23 */     $$0.forEach(($$0, $$1) -> this.glyphs.put($$0.intValue(), ()));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public GlyphInfo getGlyph(int $$0) {
/* 29 */     return (GlyphInfo)this.glyphs.get($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public IntSet getSupportedGlyphs() {
/* 34 */     return IntSets.unmodifiable(this.glyphs.keySet());
/*    */   }
/*    */   public static final class Definition extends Record implements GlyphProviderDefinition { private final Map<Integer, Float> advances; public static final MapCodec<Definition> CODEC;
/* 37 */     public Definition(Map<Integer, Float> $$0) { this.advances = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lcom/mojang/blaze3d/font/SpaceProvider$Definition;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #37	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 37 */       //   0	7	0	this	Lcom/mojang/blaze3d/font/SpaceProvider$Definition; } public Map<Integer, Float> advances() { return this.advances; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lcom/mojang/blaze3d/font/SpaceProvider$Definition;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #37	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lcom/mojang/blaze3d/font/SpaceProvider$Definition; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lcom/mojang/blaze3d/font/SpaceProvider$Definition;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #37	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lcom/mojang/blaze3d/font/SpaceProvider$Definition;
/* 38 */       //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.unboundedMap(ExtraCodecs.CODEPOINT, (Codec)Codec.FLOAT).fieldOf("advances").forGetter(Definition::advances)).apply((Applicative)$$0, Definition::new)); }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public GlyphProviderType type() {
/* 44 */       return GlyphProviderType.SPACE;
/*    */     }
/*    */ 
/*    */     
/*    */     public Either<GlyphProviderDefinition.Loader, GlyphProviderDefinition.Reference> unpack() {
/* 49 */       GlyphProviderDefinition.Loader $$0 = $$0 -> new SpaceProvider(this.advances);
/* 50 */       return Either.left($$0);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\font\SpaceProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */