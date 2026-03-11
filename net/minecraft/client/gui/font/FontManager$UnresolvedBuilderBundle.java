/*     */ package net.minecraft.client.gui.font;
/*     */ 
/*     */ import com.mojang.blaze3d.font.GlyphProvider;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.client.gui.font.providers.GlyphProviderDefinition;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.DependencySorter;
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
/*     */ final class UnresolvedBuilderBundle
/*     */   extends Record
/*     */   implements DependencySorter.Entry<ResourceLocation>
/*     */ {
/*     */   final ResourceLocation fontId;
/*     */   private final List<FontManager.BuilderResult> builders;
/*     */   private final Set<ResourceLocation> dependencies;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #89	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #89	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #89	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/client/gui/font/FontManager$UnresolvedBuilderBundle;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   private UnresolvedBuilderBundle(ResourceLocation $$0, List<FontManager.BuilderResult> $$1, Set<ResourceLocation> $$2) {
/*  89 */     this.fontId = $$0; this.builders = $$1; this.dependencies = $$2; } public ResourceLocation fontId() { return this.fontId; } public List<FontManager.BuilderResult> builders() { return this.builders; } public Set<ResourceLocation> dependencies() { return this.dependencies; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UnresolvedBuilderBundle(ResourceLocation $$0) {
/*  95 */     this($$0, new ArrayList<>(), new HashSet<>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(FontManager.BuilderId $$0, GlyphProviderDefinition.Reference $$1) {
/* 103 */     this.builders.add(new FontManager.BuilderResult($$0, Either.right($$1.id())));
/* 104 */     this.dependencies.add($$1.id());
/*     */   }
/*     */   
/*     */   public void add(FontManager.BuilderId $$0, CompletableFuture<Optional<GlyphProvider>> $$1) {
/* 108 */     this.builders.add(new FontManager.BuilderResult($$0, Either.left($$1)));
/*     */   }
/*     */   
/*     */   private Stream<CompletableFuture<Optional<GlyphProvider>>> listBuilders() {
/* 112 */     return this.builders.stream().flatMap($$0 -> $$0.result.left().stream());
/*     */   }
/*     */   
/*     */   public Optional<List<GlyphProvider>> resolve(Function<ResourceLocation, List<GlyphProvider>> $$0) {
/* 116 */     List<GlyphProvider> $$1 = new ArrayList<>();
/* 117 */     for (FontManager.BuilderResult $$2 : this.builders) {
/* 118 */       Optional<List<GlyphProvider>> $$3 = $$2.resolve($$0);
/* 119 */       if ($$3.isPresent()) {
/* 120 */         $$1.addAll($$3.get()); continue;
/*     */       } 
/* 122 */       return Optional.empty();
/*     */     } 
/*     */     
/* 125 */     return Optional.of($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitRequiredDependencies(Consumer<ResourceLocation> $$0) {
/* 130 */     this.dependencies.forEach($$0);
/*     */   }
/*     */   
/*     */   public void visitOptionalDependencies(Consumer<ResourceLocation> $$0) {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\font\FontManager$UnresolvedBuilderBundle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */