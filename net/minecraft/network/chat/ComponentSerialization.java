/*     */ package net.minecraft.network.chat;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.MapDecoder;
/*     */ import com.mojang.serialization.MapEncoder;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.RecordBuilder;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.network.chat.contents.KeybindContents;
/*     */ import net.minecraft.network.chat.contents.PlainTextContents;
/*     */ import net.minecraft.network.chat.contents.ScoreContents;
/*     */ import net.minecraft.network.chat.contents.TranslatableContents;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ public class ComponentSerialization {
/*  28 */   public static final Codec<Component> CODEC = ExtraCodecs.recursive("Component", ComponentSerialization::createCodec); public static final Codec<Component> FLAT_CODEC;
/*     */   static {
/*  30 */     FLAT_CODEC = ExtraCodecs.FLAT_JSON.flatXmap($$0 -> CODEC.parse((DynamicOps)JsonOps.INSTANCE, $$0), $$0 -> CODEC.encodeStart((DynamicOps)JsonOps.INSTANCE, $$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MutableComponent createFromList(List<Component> $$0) {
/*  37 */     MutableComponent $$1 = ((Component)$$0.get(0)).copy();
/*  38 */     for (int $$2 = 1; $$2 < $$0.size(); $$2++) {
/*  39 */       $$1.append($$0.get($$2));
/*     */     }
/*  41 */     return $$1;
/*     */   }
/*     */   
/*     */   private static class StrictEither<T>
/*     */     extends MapCodec<T>
/*     */   {
/*     */     private final String typeFieldName;
/*     */     private final MapCodec<T> typed;
/*     */     private final MapCodec<T> fuzzy;
/*     */     
/*     */     public StrictEither(String $$0, MapCodec<T> $$1, MapCodec<T> $$2) {
/*  52 */       this.typeFieldName = $$0;
/*  53 */       this.typed = $$1;
/*  54 */       this.fuzzy = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public <O> DataResult<T> decode(DynamicOps<O> $$0, MapLike<O> $$1) {
/*  59 */       if ($$1.get(this.typeFieldName) != null) {
/*  60 */         return this.typed.decode($$0, $$1);
/*     */       }
/*  62 */       return this.fuzzy.decode($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public <O> RecordBuilder<O> encode(T $$0, DynamicOps<O> $$1, RecordBuilder<O> $$2) {
/*  67 */       return this.fuzzy.encode($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T1> Stream<T1> keys(DynamicOps<T1> $$0) {
/*  72 */       return Stream.<T1>concat(this.typed.keys($$0), this.fuzzy.keys($$0)).distinct();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FuzzyCodec<T> extends MapCodec<T> {
/*     */     private final List<MapCodec<? extends T>> codecs;
/*     */     private final Function<T, MapEncoder<? extends T>> encoderGetter;
/*     */     
/*     */     public FuzzyCodec(List<MapCodec<? extends T>> $$0, Function<T, MapEncoder<? extends T>> $$1) {
/*  81 */       this.codecs = $$0;
/*  82 */       this.encoderGetter = $$1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <S> DataResult<T> decode(DynamicOps<S> $$0, MapLike<S> $$1) {
/*  88 */       for (MapDecoder<? extends T> $$2 : this.codecs) {
/*  89 */         DataResult<? extends T> $$3 = $$2.decode($$0, $$1);
/*  90 */         if ($$3.result().isPresent()) {
/*  91 */           return (DataResult)$$3;
/*     */         }
/*     */       } 
/*     */       
/*  95 */       return DataResult.error(() -> "No matching codec found");
/*     */     }
/*     */ 
/*     */     
/*     */     public <S> RecordBuilder<S> encode(T $$0, DynamicOps<S> $$1, RecordBuilder<S> $$2) {
/* 100 */       MapEncoder<T> $$3 = (MapEncoder<T>)this.encoderGetter.apply($$0);
/* 101 */       return $$3.encode($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <S> Stream<S> keys(DynamicOps<S> $$0) {
/* 108 */       return this.codecs.stream().flatMap($$1 -> $$1.keys($$0)).distinct();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 113 */       return "FuzzyCodec[" + this.codecs + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends StringRepresentable, E> MapCodec<E> createLegacyComponentMatcher(T[] $$0, Function<T, MapCodec<? extends E>> $$1, Function<E, T> $$2, String $$3) {
/* 120 */     MapCodec<E> $$4 = new FuzzyCodec<>(Stream.<T>of($$0).<MapCodec<? extends E>>map($$1).toList(), $$2 -> (MapEncoder)$$0.apply($$1.apply($$2)));
/*     */ 
/*     */ 
/*     */     
/* 124 */     Codec<T> $$5 = StringRepresentable.fromValues(() -> $$0);
/* 125 */     MapCodec<E> $$6 = $$5.dispatchMap($$3, $$2, $$1 -> ((MapCodec)$$0.apply($$1)).codec());
/*     */     
/* 127 */     MapCodec<E> $$7 = new StrictEither<>($$3, $$6, $$4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     return ExtraCodecs.orCompressed($$7, $$6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Codec<Component> createCodec(Codec<Component> $$0) {
/* 140 */     ComponentContents.Type[] arrayOfType = { PlainTextContents.TYPE, TranslatableContents.TYPE, KeybindContents.TYPE, ScoreContents.TYPE, SelectorContents.TYPE, NbtContents.TYPE };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     MapCodec<ComponentContents> $$2 = createLegacyComponentMatcher(arrayOfType, ComponentContents.Type::codec, ComponentContents::type, "type");
/*     */     
/* 151 */     Codec<Component> $$3 = RecordCodecBuilder.create($$2 -> $$2.group((App)$$0.forGetter(Component::getContents), (App)ExtraCodecs.strictOptionalField(ExtraCodecs.nonEmptyList($$1.listOf()), "extra", List.of()).forGetter(Component::getSiblings), (App)Style.Serializer.MAP_CODEC.forGetter(Component::getStyle)).apply((Applicative)$$2, MutableComponent::new));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     return Codec.either(
/* 160 */         Codec.either((Codec)Codec.STRING, 
/*     */           
/* 162 */           ExtraCodecs.nonEmptyList($$0.listOf())), $$3)
/*     */ 
/*     */       
/* 165 */       .xmap($$0 -> (Component)$$0.map((), ()), $$0 -> {
/*     */           String $$1 = $$0.tryCollapseToString();
/*     */           return ($$1 != null) ? Either.left(Either.left($$1)) : Either.right($$0);
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ComponentSerialization.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */