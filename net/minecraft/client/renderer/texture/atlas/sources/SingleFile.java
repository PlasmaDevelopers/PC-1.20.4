/*    */ package net.minecraft.client.renderer.texture.atlas.sources;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.client.renderer.texture.atlas.SpriteSource;
/*    */ import net.minecraft.client.renderer.texture.atlas.SpriteSources;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.Resource;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SingleFile implements SpriteSource {
/* 17 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final Codec<SingleFile> CODEC;
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("resource").forGetter(()), (App)ResourceLocation.CODEC.optionalFieldOf("sprite").forGetter(())).apply((Applicative)$$0, SingleFile::new));
/*    */   }
/*    */ 
/*    */   
/*    */   private final ResourceLocation resourceId;
/*    */   
/*    */   private final Optional<ResourceLocation> spriteId;
/*    */   
/*    */   public SingleFile(ResourceLocation $$0, Optional<ResourceLocation> $$1) {
/* 28 */     this.resourceId = $$0;
/* 29 */     this.spriteId = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(ResourceManager $$0, SpriteSource.Output $$1) {
/* 34 */     ResourceLocation $$2 = TEXTURE_ID_CONVERTER.idToFile(this.resourceId);
/* 35 */     Optional<Resource> $$3 = $$0.getResource($$2);
/* 36 */     if ($$3.isPresent()) {
/* 37 */       $$1.add(this.spriteId.orElse(this.resourceId), $$3.get());
/*    */     } else {
/* 39 */       LOGGER.warn("Missing sprite: {}", $$2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public SpriteSourceType type() {
/* 45 */     return SpriteSources.SINGLE_FILE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\sources\SingleFile.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */