/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.Keyable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.function.ToIntFunction;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ 
/*     */ public interface StringRepresentable
/*     */ {
/*     */   public static final int PRE_BUILT_MAP_THRESHOLD = 16;
/*     */   
/*     */   public static class StringRepresentableCodec<S extends StringRepresentable>
/*     */     implements Codec<S>
/*     */   {
/*     */     private final Codec<S> codec;
/*     */     
/*     */     public StringRepresentableCodec(S[] $$0, Function<String, S> $$1, ToIntFunction<S> $$2) {
/*  29 */       this.codec = ExtraCodecs.orCompressed(
/*  30 */           ExtraCodecs.stringResolverCodec(StringRepresentable::getSerializedName, $$1), 
/*  31 */           ExtraCodecs.idResolverCodec($$2, $$1 -> ($$1 >= 0 && $$1 < $$0.length) ? $$0[$$1] : null, -1));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> DataResult<Pair<S, T>> decode(DynamicOps<T> $$0, T $$1) {
/*  37 */       return this.codec.decode($$0, $$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public <T> DataResult<T> encode(S $$0, DynamicOps<T> $$1, T $$2) {
/*  42 */       return this.codec.encode($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static class EnumCodec<E extends Enum<E> & StringRepresentable>
/*     */     extends StringRepresentableCodec<E>
/*     */   {
/*     */     private final Function<String, E> resolver;
/*     */ 
/*     */     
/*     */     public EnumCodec(E[] $$0, Function<String, E> $$1) {
/*  55 */       super($$0, $$1, $$0 -> ((Enum)$$0).ordinal());
/*  56 */       this.resolver = $$1;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public E byName(@Nullable String $$0) {
/*  61 */       return this.resolver.apply($$0);
/*     */     }
/*     */     
/*     */     public E byName(@Nullable String $$0, E $$1) {
/*  65 */       return (E)Objects.<Enum>requireNonNullElse((Enum)byName($$0), (Enum)$$1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <E extends Enum<E> & StringRepresentable> EnumCodec<E> fromEnum(Supplier<E[]> $$0) {
/*  73 */     return fromEnumWithMapping($$0, $$0 -> $$0);
/*     */   }
/*     */   
/*     */   static <E extends Enum<E> & StringRepresentable> EnumCodec<E> fromEnumWithMapping(Supplier<E[]> $$0, Function<String, String> $$1) {
/*  77 */     Enum[] arrayOfEnum = (Enum[])$$0.get();
/*  78 */     Function<String, E> $$3 = createNameLookup((E[])arrayOfEnum, $$1);
/*  79 */     return new EnumCodec<>((E[])arrayOfEnum, $$3);
/*     */   }
/*     */   
/*     */   static <T extends StringRepresentable> Codec<T> fromValues(Supplier<T[]> $$0) {
/*  83 */     StringRepresentable[] arrayOfStringRepresentable = (StringRepresentable[])$$0.get();
/*  84 */     Function<String, T> $$2 = createNameLookup((T[])arrayOfStringRepresentable, $$0 -> $$0);
/*  85 */     ToIntFunction<T> $$3 = Util.createIndexLookup(Arrays.asList(arrayOfStringRepresentable));
/*  86 */     return new StringRepresentableCodec<>((T[])arrayOfStringRepresentable, $$2, $$3);
/*     */   }
/*     */   
/*     */   static <T extends StringRepresentable> Function<String, T> createNameLookup(T[] $$0, Function<String, String> $$1) {
/*  90 */     if ($$0.length > 16) {
/*  91 */       Map<String, T> $$2 = (Map<String, T>)Arrays.<T>stream($$0).collect(Collectors.toMap($$1 -> (String)$$0.apply($$1.getSerializedName()), $$0 -> $$0));
/*  92 */       return $$1 -> ($$1 == null) ? null : (StringRepresentable)$$0.get($$1);
/*     */     } 
/*  94 */     return $$2 -> {
/*     */         for (StringRepresentable stringRepresentable : $$0) {
/*     */           if (((String)$$1.apply(stringRepresentable.getSerializedName())).equals($$2)) {
/*     */             return stringRepresentable;
/*     */           }
/*     */         } 
/*     */         return null;
/*     */       };
/*     */   }
/*     */   
/*     */   static Keyable keys(final StringRepresentable[] values) {
/* 105 */     return new Keyable()
/*     */       {
/*     */         public <T> Stream<T> keys(DynamicOps<T> $$0) {
/* 108 */           Objects.requireNonNull($$0); return Arrays.<StringRepresentable>stream(values).map(StringRepresentable::getSerializedName).map($$0::createString);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   String getSerializedName();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\StringRepresentable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */