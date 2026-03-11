/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.ToIntFunction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringRepresentableCodec<S extends StringRepresentable>
/*    */   implements Codec<S>
/*    */ {
/*    */   private final Codec<S> codec;
/*    */   
/*    */   public StringRepresentableCodec(S[] $$0, Function<String, S> $$1, ToIntFunction<S> $$2) {
/* 29 */     this.codec = ExtraCodecs.orCompressed(
/* 30 */         ExtraCodecs.stringResolverCodec(StringRepresentable::getSerializedName, $$1), 
/* 31 */         ExtraCodecs.idResolverCodec($$2, $$1 -> ($$1 >= 0 && $$1 < $$0.length) ? $$0[$$1] : null, -1));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> DataResult<Pair<S, T>> decode(DynamicOps<T> $$0, T $$1) {
/* 37 */     return this.codec.decode($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> DataResult<T> encode(S $$0, DynamicOps<T> $$1, T $$2) {
/* 42 */     return this.codec.encode($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\StringRepresentable$StringRepresentableCodec.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */