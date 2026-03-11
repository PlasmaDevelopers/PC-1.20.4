/*    */ package net.minecraft.client.renderer.texture.atlas;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParser;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.io.BufferedReader;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
/*    */ import net.minecraft.client.renderer.texture.SpriteContents;
/*    */ import net.minecraft.resources.FileToIdConverter;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.Resource;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SpriteSourceList {
/* 27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/* 29 */   private static final FileToIdConverter ATLAS_INFO_CONVERTER = new FileToIdConverter("atlases", ".json");
/*    */   
/*    */   private final List<SpriteSource> sources;
/*    */   
/*    */   private SpriteSourceList(List<SpriteSource> $$0) {
/* 34 */     this.sources = $$0;
/*    */   }
/*    */   
/*    */   public List<Function<SpriteResourceLoader, SpriteContents>> list(ResourceManager $$0) {
/* 38 */     final Map<ResourceLocation, SpriteSource.SpriteSupplier> sprites = new HashMap<>();
/* 39 */     SpriteSource.Output $$2 = new SpriteSource.Output()
/*    */       {
/*    */         public void add(ResourceLocation $$0, SpriteSource.SpriteSupplier $$1) {
/* 42 */           SpriteSource.SpriteSupplier $$2 = sprites.put($$0, $$1);
/* 43 */           if ($$2 != null) {
/* 44 */             $$2.discard();
/*    */           }
/*    */         }
/*    */ 
/*    */         
/*    */         public void removeAll(Predicate<ResourceLocation> $$0) {
/* 50 */           Iterator<Map.Entry<ResourceLocation, SpriteSource.SpriteSupplier>> $$1 = sprites.entrySet().iterator();
/* 51 */           while ($$1.hasNext()) {
/* 52 */             Map.Entry<ResourceLocation, SpriteSource.SpriteSupplier> $$2 = $$1.next();
/* 53 */             if ($$0.test($$2.getKey())) {
/* 54 */               ((SpriteSource.SpriteSupplier)$$2.getValue()).discard();
/* 55 */               $$1.remove();
/*    */             } 
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 61 */     this.sources.forEach($$2 -> $$2.run($$0, $$1));
/*    */     
/* 63 */     ImmutableList.Builder<Function<SpriteResourceLoader, SpriteContents>> $$3 = ImmutableList.builder();
/* 64 */     $$3.add($$0 -> MissingTextureAtlasSprite.create());
/* 65 */     $$3.addAll($$1.values());
/* 66 */     return (List<Function<SpriteResourceLoader, SpriteContents>>)$$3.build();
/*    */   }
/*    */   
/*    */   public static SpriteSourceList load(ResourceManager $$0, ResourceLocation $$1) {
/* 70 */     ResourceLocation $$2 = ATLAS_INFO_CONVERTER.idToFile($$1);
/* 71 */     List<SpriteSource> $$3 = new ArrayList<>();
/* 72 */     for (Resource $$4 : $$0.getResourceStack($$2)) { 
/* 73 */       try { BufferedReader $$5 = $$4.openAsReader(); 
/* 74 */         try { Dynamic<JsonElement> $$6 = new Dynamic((DynamicOps)JsonOps.INSTANCE, JsonParser.parseReader($$5));
/* 75 */           Objects.requireNonNull(LOGGER); $$3.addAll((Collection<? extends SpriteSource>)SpriteSources.FILE_CODEC.parse($$6).getOrThrow(false, LOGGER::error));
/* 76 */           if ($$5 != null) $$5.close();  } catch (Throwable throwable) { if ($$5 != null) try { $$5.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$7)
/* 77 */       { LOGGER.warn("Failed to parse atlas definition {} in pack {}", new Object[] { $$2, $$4.sourcePackId(), $$7 }); }
/*    */        }
/*    */     
/* 80 */     return new SpriteSourceList($$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\SpriteSourceList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */