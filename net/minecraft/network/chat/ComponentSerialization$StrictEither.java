/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.MapLike;
/*    */ import com.mojang.serialization.RecordBuilder;
/*    */ import java.util.stream.Stream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class StrictEither<T>
/*    */   extends MapCodec<T>
/*    */ {
/*    */   private final String typeFieldName;
/*    */   private final MapCodec<T> typed;
/*    */   private final MapCodec<T> fuzzy;
/*    */   
/*    */   public StrictEither(String $$0, MapCodec<T> $$1, MapCodec<T> $$2) {
/* 52 */     this.typeFieldName = $$0;
/* 53 */     this.typed = $$1;
/* 54 */     this.fuzzy = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public <O> DataResult<T> decode(DynamicOps<O> $$0, MapLike<O> $$1) {
/* 59 */     if ($$1.get(this.typeFieldName) != null) {
/* 60 */       return this.typed.decode($$0, $$1);
/*    */     }
/* 62 */     return this.fuzzy.decode($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public <O> RecordBuilder<O> encode(T $$0, DynamicOps<O> $$1, RecordBuilder<O> $$2) {
/* 67 */     return this.fuzzy.encode($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T1> Stream<T1> keys(DynamicOps<T1> $$0) {
/* 72 */     return Stream.<T1>concat(this.typed.keys($$0), this.fuzzy.keys($$0)).distinct();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ComponentSerialization$StrictEither.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */