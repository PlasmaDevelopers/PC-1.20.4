/*    */ package net.minecraft.client.renderer.texture.atlas.sources;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.renderer.texture.atlas.SpriteSource;
/*    */ import net.minecraft.client.renderer.texture.atlas.SpriteSources;
/*    */ import net.minecraft.util.ResourceLocationPattern;
/*    */ 
/*    */ public class SourceFilter implements SpriteSource {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocationPattern.CODEC.fieldOf("pattern").forGetter(())).apply((Applicative)$$0, SourceFilter::new));
/*    */   }
/*    */   
/*    */   public static final Codec<SourceFilter> CODEC;
/*    */   private final ResourceLocationPattern filter;
/*    */   
/*    */   public SourceFilter(ResourceLocationPattern $$0) {
/* 19 */     this.filter = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(ResourceManager $$0, SpriteSource.Output $$1) {
/* 24 */     $$1.removeAll(this.filter.locationPredicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public SpriteSourceType type() {
/* 29 */     return SpriteSources.FILTER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\atlas\sources\SourceFilter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */