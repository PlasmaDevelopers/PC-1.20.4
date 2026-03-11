/*    */ package net.minecraft.util;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class ResourceLocationPattern {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.PATTERN.optionalFieldOf("namespace").forGetter(()), (App)ExtraCodecs.PATTERN.optionalFieldOf("path").forGetter(())).apply((Applicative)$$0, ResourceLocationPattern::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<ResourceLocationPattern> CODEC;
/*    */   private final Optional<Pattern> namespacePattern;
/*    */   private final Predicate<String> namespacePredicate;
/*    */   private final Optional<Pattern> pathPattern;
/*    */   private final Predicate<String> pathPredicate;
/*    */   private final Predicate<ResourceLocation> locationPredicate;
/*    */   
/*    */   private ResourceLocationPattern(Optional<Pattern> $$0, Optional<Pattern> $$1) {
/* 24 */     this.namespacePattern = $$0;
/* 25 */     this.namespacePredicate = $$0.<Predicate<String>>map(Pattern::asPredicate).orElse($$0 -> true);
/* 26 */     this.pathPattern = $$1;
/* 27 */     this.pathPredicate = $$1.<Predicate<String>>map(Pattern::asPredicate).orElse($$0 -> true);
/* 28 */     this.locationPredicate = ($$0 -> (this.namespacePredicate.test($$0.getNamespace()) && this.pathPredicate.test($$0.getPath())));
/*    */   }
/*    */   
/*    */   public Predicate<String> namespacePredicate() {
/* 32 */     return this.namespacePredicate;
/*    */   }
/*    */   
/*    */   public Predicate<String> pathPredicate() {
/* 36 */     return this.pathPredicate;
/*    */   }
/*    */   
/*    */   public Predicate<ResourceLocation> locationPredicate() {
/* 40 */     return this.locationPredicate;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\ResourceLocationPattern.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */