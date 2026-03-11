/*    */ package net.minecraft.server.packs.resources;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.ResourceLocationPattern;
/*    */ 
/*    */ public class ResourceFilterSection {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.list(ResourceLocationPattern.CODEC).fieldOf("block").forGetter(())).apply((Applicative)$$0, ResourceFilterSection::new));
/*    */   }
/*    */   
/*    */   private static final Codec<ResourceFilterSection> CODEC;
/* 15 */   public static final MetadataSectionType<ResourceFilterSection> TYPE = MetadataSectionType.fromCodec("filter", CODEC);
/*    */   
/*    */   private final List<ResourceLocationPattern> blockList;
/*    */   
/*    */   public ResourceFilterSection(List<ResourceLocationPattern> $$0) {
/* 20 */     this.blockList = List.copyOf($$0);
/*    */   }
/*    */   
/*    */   public boolean isNamespaceFiltered(String $$0) {
/* 24 */     return this.blockList.stream().anyMatch($$1 -> $$1.namespacePredicate().test($$0));
/*    */   }
/*    */   
/*    */   public boolean isPathFiltered(String $$0) {
/* 28 */     return this.blockList.stream().anyMatch($$1 -> $$1.pathPredicate().test($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ResourceFilterSection.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */