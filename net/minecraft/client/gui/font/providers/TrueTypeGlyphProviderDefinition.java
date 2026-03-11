/*    */ package net.minecraft.client.gui.font.providers;
/*    */ 
/*    */ import com.mojang.blaze3d.font.TrueTypeGlyphProvider;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function5;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.List;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import org.lwjgl.stb.STBTTFontinfo;
/*    */ 
/*    */ public final class TrueTypeGlyphProviderDefinition extends Record implements GlyphProviderDefinition {
/*    */   private final ResourceLocation location;
/*    */   private final float size;
/*    */   private final float oversample;
/*    */   private final Shift shift;
/*    */   private final String skip;
/*    */   private static final Codec<String> SKIP_LIST_CODEC;
/*    */   public static final MapCodec<TrueTypeGlyphProviderDefinition> CODEC;
/*    */   
/* 23 */   public TrueTypeGlyphProviderDefinition(ResourceLocation $$0, float $$1, float $$2, Shift $$3, String $$4) { this.location = $$0; this.size = $$1; this.oversample = $$2; this.shift = $$3; this.skip = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 23 */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition; } public ResourceLocation location() { return this.location; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition;
/* 23 */     //   0	8	1	$$0	Ljava/lang/Object; } public float size() { return this.size; } public float oversample() { return this.oversample; } public Shift shift() { return this.shift; } public String skip() { return this.skip; }
/*    */ 
/*    */   
/*    */   public static final class Shift extends Record {
/*    */     final float x;
/*    */     final float y;
/*    */     
/* 30 */     public Shift(float $$0, float $$1) { this.x = $$0; this.y = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/TrueTypeGlyphProviderDefinition$Shift;
/* 30 */       //   0	8	1	$$0	Ljava/lang/Object; } public float x() { return this.x; } public float y() { return this.y; }
/* 31 */      public static final Shift NONE = new Shift(0.0F, 0.0F); public static final Codec<Shift> CODEC;
/*    */     static {
/* 33 */       CODEC = Codec.FLOAT.listOf().comapFlatMap($$0 -> Util.fixedSize($$0, 2).map(()), $$0 -> List.of(Float.valueOf($$0.x), Float.valueOf($$0.y)));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 40 */     SKIP_LIST_CODEC = ExtraCodecs.withAlternative((Codec)Codec.STRING, Codec.STRING
/*    */         
/* 42 */         .listOf(), $$0 -> String.join("", $$0));
/*    */ 
/*    */ 
/*    */     
/* 46 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("file").forGetter(TrueTypeGlyphProviderDefinition::location), (App)Codec.FLOAT.optionalFieldOf("size", Float.valueOf(11.0F)).forGetter(TrueTypeGlyphProviderDefinition::size), (App)Codec.FLOAT.optionalFieldOf("oversample", Float.valueOf(1.0F)).forGetter(TrueTypeGlyphProviderDefinition::oversample), (App)Shift.CODEC.optionalFieldOf("shift", Shift.NONE).forGetter(TrueTypeGlyphProviderDefinition::shift), (App)SKIP_LIST_CODEC.optionalFieldOf("skip", "").forGetter(TrueTypeGlyphProviderDefinition::skip)).apply((Applicative)$$0, TrueTypeGlyphProviderDefinition::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GlyphProviderType type() {
/* 56 */     return GlyphProviderType.TTF;
/*    */   }
/*    */ 
/*    */   
/*    */   public Either<GlyphProviderDefinition.Loader, GlyphProviderDefinition.Reference> unpack() {
/* 61 */     return Either.left(this::load);
/*    */   }
/*    */   
/*    */   private GlyphProvider load(ResourceManager $$0) throws IOException {
/* 65 */     STBTTFontinfo $$1 = null;
/* 66 */     ByteBuffer $$2 = null; 
/* 67 */     try { InputStream $$3 = $$0.open(this.location.withPrefix("font/")); 
/* 68 */       try { $$1 = STBTTFontinfo.malloc();
/* 69 */         $$2 = TextureUtil.readResource($$3);
/* 70 */         $$2.flip();
/* 71 */         if (!STBTruetype.stbtt_InitFont($$1, $$2)) {
/* 72 */           throw new IOException("Invalid ttf");
/*    */         }
/* 74 */         TrueTypeGlyphProvider trueTypeGlyphProvider = new TrueTypeGlyphProvider($$2, $$1, this.size, this.oversample, this.shift.x, this.shift.y, this.skip);
/* 75 */         if ($$3 != null) $$3.close();  return (GlyphProvider)trueTypeGlyphProvider; } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$4)
/* 76 */     { if ($$1 != null) {
/* 77 */         $$1.free();
/*    */       }
/* 79 */       MemoryUtil.memFree($$2);
/* 80 */       throw $$4; }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\TrueTypeGlyphProviderDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */