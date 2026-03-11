/*    */ package net.minecraft.client.renderer.texture.atlas.sources;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.client.renderer.texture.atlas.SpriteSource;
/*    */ import net.minecraft.resources.FileToIdConverter;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.Resource;
/*    */ 
/*    */ public class DirectoryLister implements SpriteSource {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.STRING.fieldOf("source").forGetter(()), (App)Codec.STRING.fieldOf("prefix").forGetter(())).apply((Applicative)$$0, DirectoryLister::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<DirectoryLister> CODEC;
/*    */   private final String sourcePath;
/*    */   private final String idPrefix;
/*    */   
/*    */   public DirectoryLister(String $$0, String $$1) {
/* 22 */     this.sourcePath = $$0;
/* 23 */     this.idPrefix = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(ResourceManager $$0, SpriteSource.Output $$1) {
/* 28 */     FileToIdConverter $$2 = new FileToIdConverter("textures/" + this.sourcePath, ".png");
/*    */     
/* 30 */     $$2.listMatchingResources($$0).forEach(($$2, $$3) -> {
/*    */           ResourceLocation $$4 = $$0.fileToId($$2).withPrefix(this.idPrefix);
/*    */           $$1.add($$4, $$3);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public SpriteSourceType type() {
/* 38 */     return SpriteSources.DIRECTORY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\sources\DirectoryLister.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */