/*    */ package net.minecraft.client.renderer.texture.atlas;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
/*    */ import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
/*    */ import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
/*    */ import net.minecraft.client.renderer.texture.atlas.sources.SourceFilter;
/*    */ import net.minecraft.client.renderer.texture.atlas.sources.Unstitcher;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class SpriteSources
/*    */ {
/* 17 */   private static final BiMap<ResourceLocation, SpriteSourceType> TYPES = (BiMap<ResourceLocation, SpriteSourceType>)HashBiMap.create();
/*    */   
/* 19 */   public static final SpriteSourceType SINGLE_FILE = register("single", SingleFile.CODEC);
/* 20 */   public static final SpriteSourceType DIRECTORY = register("directory", DirectoryLister.CODEC);
/* 21 */   public static final SpriteSourceType FILTER = register("filter", SourceFilter.CODEC);
/* 22 */   public static final SpriteSourceType UNSTITCHER = register("unstitch", Unstitcher.CODEC);
/* 23 */   public static final SpriteSourceType PALETTED_PERMUTATIONS = register("paletted_permutations", PalettedPermutations.CODEC);
/*    */   static {
/* 25 */     TYPE_CODEC = ResourceLocation.CODEC.flatXmap($$0 -> {
/*    */           SpriteSourceType $$1 = (SpriteSourceType)TYPES.get($$0);
/*    */           return ($$1 != null) ? DataResult.success($$1) : DataResult.error(());
/*    */         }$$0 -> {
/*    */           ResourceLocation $$1 = (ResourceLocation)TYPES.inverse().get($$0);
/*    */           return ($$0 != null) ? DataResult.success($$1) : DataResult.error(());
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public static Codec<SpriteSourceType> TYPE_CODEC;
/* 36 */   public static Codec<SpriteSource> CODEC = TYPE_CODEC.dispatch(SpriteSource::type, SpriteSourceType::codec);
/*    */ 
/*    */   
/* 39 */   public static Codec<List<SpriteSource>> FILE_CODEC = CODEC.listOf().fieldOf("sources").codec();
/*    */   
/*    */   private static SpriteSourceType register(String $$0, Codec<? extends SpriteSource> $$1) {
/* 42 */     SpriteSourceType $$2 = new SpriteSourceType($$1);
/* 43 */     ResourceLocation $$3 = new ResourceLocation($$0);
/* 44 */     SpriteSourceType $$4 = (SpriteSourceType)TYPES.putIfAbsent($$3, $$2);
/* 45 */     if ($$4 != null) {
/* 46 */       throw new IllegalStateException("Duplicate registration " + $$3);
/*    */     }
/* 48 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\SpriteSources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */