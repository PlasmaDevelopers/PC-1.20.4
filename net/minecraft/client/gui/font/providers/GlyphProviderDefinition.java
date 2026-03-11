/*    */ package net.minecraft.client.gui.font.providers;
/*    */ import com.mojang.blaze3d.font.GlyphProvider;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public interface GlyphProviderDefinition {
/*    */   public static final Codec<GlyphProviderDefinition> CODEC;
/*    */   
/*    */   static {
/* 12 */     CODEC = GlyphProviderType.CODEC.dispatch(GlyphProviderDefinition::type, $$0 -> $$0.mapCodec().codec());
/*    */   }
/*    */   
/*    */   GlyphProviderType type();
/*    */   
/*    */   Either<Loader, Reference> unpack();
/*    */   
/*    */   public static final class Reference extends Record { private final ResourceLocation id;
/*    */     
/*    */     public Reference(ResourceLocation $$0) {
/* 22 */       this.id = $$0; } public ResourceLocation id() { return this.id; }
/*    */ 
/*    */     
/*    */     public final String toString() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/providers/GlyphProviderDefinition$Reference;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #22	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/GlyphProviderDefinition$Reference;
/*    */     }
/*    */     
/*    */     public final int hashCode() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/providers/GlyphProviderDefinition$Reference;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #22	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/gui/font/providers/GlyphProviderDefinition$Reference;
/*    */     }
/*    */     
/*    */     public final boolean equals(Object $$0) {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/providers/GlyphProviderDefinition$Reference;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #22	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/gui/font/providers/GlyphProviderDefinition$Reference;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */     } }
/*    */ 
/*    */   
/*    */   public static interface Loader {
/*    */     GlyphProvider load(ResourceManager param1ResourceManager) throws IOException;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\providers\GlyphProviderDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */