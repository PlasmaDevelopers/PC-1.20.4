/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*    */ import net.minecraft.client.renderer.texture.SpriteLoader;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ 
/*    */ public class AtlasSet implements AutoCloseable {
/*    */   private final Map<ResourceLocation, AtlasEntry> atlases;
/*    */   
/*    */   public AtlasSet(Map<ResourceLocation, ResourceLocation> $$0, TextureManager $$1) {
/* 20 */     this.atlases = (Map<ResourceLocation, AtlasEntry>)$$0.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, $$1 -> {
/*    */             TextureAtlas $$2 = new TextureAtlas((ResourceLocation)$$1.getKey());
/*    */             $$0.register((ResourceLocation)$$1.getKey(), (AbstractTexture)$$2);
/*    */             return new AtlasEntry($$2, (ResourceLocation)$$1.getValue());
/*    */           }));
/*    */   }
/*    */   
/*    */   public TextureAtlas getAtlas(ResourceLocation $$0) {
/* 28 */     return ((AtlasEntry)this.atlases.get($$0)).atlas();
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 33 */     this.atlases.values().forEach(AtlasEntry::close);
/* 34 */     this.atlases.clear();
/*    */   }
/*    */   
/*    */   public Map<ResourceLocation, CompletableFuture<StitchResult>> scheduleLoad(ResourceManager $$0, int $$1, Executor $$2) {
/* 38 */     return (Map<ResourceLocation, CompletableFuture<StitchResult>>)this.atlases.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, $$3 -> {
/*    */             AtlasEntry $$4 = (AtlasEntry)$$3.getValue();
/*    */             return SpriteLoader.create($$4.atlas).loadAndStitch($$0, $$4.atlasInfoLocation, $$1, $$2).thenApply(());
/*    */           }));
/*    */   }
/*    */   
/*    */   public static class StitchResult
/*    */   {
/*    */     private final TextureAtlas atlas;
/*    */     private final SpriteLoader.Preparations preparations;
/*    */     
/*    */     public StitchResult(TextureAtlas $$0, SpriteLoader.Preparations $$1) {
/* 50 */       this.atlas = $$0;
/* 51 */       this.preparations = $$1;
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public TextureAtlasSprite getSprite(ResourceLocation $$0) {
/* 56 */       return (TextureAtlasSprite)this.preparations.regions().get($$0);
/*    */     }
/*    */     
/*    */     public TextureAtlasSprite missing() {
/* 60 */       return this.preparations.missing();
/*    */     }
/*    */     
/*    */     public CompletableFuture<Void> readyForUpload() {
/* 64 */       return this.preparations.readyForUpload();
/*    */     }
/*    */     
/*    */     public void upload() {
/* 68 */       this.atlas.upload(this.preparations);
/*    */     } }
/*    */   private static final class AtlasEntry extends Record implements AutoCloseable { final TextureAtlas atlas; final ResourceLocation atlasInfoLocation;
/*    */     
/* 72 */     AtlasEntry(TextureAtlas $$0, ResourceLocation $$1) { this.atlas = $$0; this.atlasInfoLocation = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/model/AtlasSet$AtlasEntry;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #72	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 72 */       //   0	7	0	this	Lnet/minecraft/client/resources/model/AtlasSet$AtlasEntry; } public TextureAtlas atlas() { return this.atlas; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/model/AtlasSet$AtlasEntry;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #72	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/resources/model/AtlasSet$AtlasEntry; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/model/AtlasSet$AtlasEntry;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #72	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/resources/model/AtlasSet$AtlasEntry;
/* 72 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation atlasInfoLocation() { return this.atlasInfoLocation; }
/*    */     
/*    */     public void close() {
/* 75 */       this.atlas.clearTextureData();
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\AtlasSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */