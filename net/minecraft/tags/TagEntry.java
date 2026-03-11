/*     */ package net.minecraft.tags;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ 
/*     */ public class TagEntry {
/*     */   static {
/*  15 */     FULL_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.TAG_OR_ELEMENT_ID.fieldOf("id").forGetter(TagEntry::elementOrTag), (App)Codec.BOOL.optionalFieldOf("required", Boolean.valueOf(true)).forGetter(())).apply((Applicative)$$0, TagEntry::new));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  20 */     CODEC = Codec.either(ExtraCodecs.TAG_OR_ELEMENT_ID, FULL_CODEC).xmap($$0 -> (TagEntry)$$0.map((), ()), $$0 -> $$0.required ? Either.left($$0.elementOrTag()) : Either.right($$0));
/*     */   }
/*     */   
/*     */   private static final Codec<TagEntry> FULL_CODEC;
/*     */   public static final Codec<TagEntry> CODEC;
/*     */   private final ResourceLocation id;
/*     */   private final boolean tag;
/*     */   private final boolean required;
/*     */   
/*     */   private TagEntry(ResourceLocation $$0, boolean $$1, boolean $$2) {
/*  30 */     this.id = $$0;
/*  31 */     this.tag = $$1;
/*  32 */     this.required = $$2;
/*     */   }
/*     */   
/*     */   private TagEntry(ExtraCodecs.TagOrElementLocation $$0, boolean $$1) {
/*  36 */     this.id = $$0.id();
/*  37 */     this.tag = $$0.tag();
/*  38 */     this.required = $$1;
/*     */   }
/*     */   
/*     */   private ExtraCodecs.TagOrElementLocation elementOrTag() {
/*  42 */     return new ExtraCodecs.TagOrElementLocation(this.id, this.tag);
/*     */   }
/*     */   
/*     */   public static TagEntry element(ResourceLocation $$0) {
/*  46 */     return new TagEntry($$0, false, true);
/*     */   }
/*     */   
/*     */   public static TagEntry optionalElement(ResourceLocation $$0) {
/*  50 */     return new TagEntry($$0, false, false);
/*     */   }
/*     */   
/*     */   public static TagEntry tag(ResourceLocation $$0) {
/*  54 */     return new TagEntry($$0, true, true);
/*     */   }
/*     */   
/*     */   public static TagEntry optionalTag(ResourceLocation $$0) {
/*  58 */     return new TagEntry($$0, true, false);
/*     */   }
/*     */   
/*     */   public <T> boolean build(Lookup<T> $$0, Consumer<T> $$1) {
/*  62 */     if (this.tag) {
/*  63 */       Collection<T> $$2 = $$0.tag(this.id);
/*  64 */       if ($$2 == null) {
/*  65 */         return !this.required;
/*     */       }
/*  67 */       $$2.forEach($$1);
/*     */     } else {
/*  69 */       T $$3 = $$0.element(this.id);
/*  70 */       if ($$3 == null) {
/*  71 */         return !this.required;
/*     */       }
/*  73 */       $$1.accept($$3);
/*     */     } 
/*  75 */     return true;
/*     */   }
/*     */   
/*     */   public void visitRequiredDependencies(Consumer<ResourceLocation> $$0) {
/*  79 */     if (this.tag && this.required) {
/*  80 */       $$0.accept(this.id);
/*     */     }
/*     */   }
/*     */   
/*     */   public void visitOptionalDependencies(Consumer<ResourceLocation> $$0) {
/*  85 */     if (this.tag && !this.required) {
/*  86 */       $$0.accept(this.id);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean verifyIfPresent(Predicate<ResourceLocation> $$0, Predicate<ResourceLocation> $$1) {
/*  91 */     return (!this.required || (this.tag ? $$1 : $$0).test(this.id));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  96 */     StringBuilder $$0 = new StringBuilder();
/*  97 */     if (this.tag) {
/*  98 */       $$0.append('#');
/*     */     }
/* 100 */     $$0.append(this.id);
/* 101 */     if (!this.required) {
/* 102 */       $$0.append('?');
/*     */     }
/* 104 */     return $$0.toString();
/*     */   }
/*     */   
/*     */   public static interface Lookup<T> {
/*     */     @Nullable
/*     */     T element(ResourceLocation param1ResourceLocation);
/*     */     
/*     */     @Nullable
/*     */     Collection<T> tag(ResourceLocation param1ResourceLocation);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */