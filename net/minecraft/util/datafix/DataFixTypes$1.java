/*    */ package net.minecraft.util.datafix;
/*    */ 
/*    */ import com.mojang.datafixers.DataFixer;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import java.util.Objects;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements Codec<A>
/*    */ {
/*    */   public <T> DataResult<T> encode(A $$0, DynamicOps<T> $$1, T $$2) {
/* 55 */     return codec.encode($$0, $$1, $$2).flatMap($$1 -> $$0.mergeToMap($$1, $$0.createString("DataVersion"), $$0.createInt(DataFixTypes.currentVersion())));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T> DataResult<Pair<A, T>> decode(DynamicOps<T> $$0, T $$1) {
/* 63 */     Objects.requireNonNull($$0);
/*    */ 
/*    */     
/* 66 */     int $$2 = ((Integer)$$0.get($$1, "DataVersion").flatMap($$0::getNumberValue).map(Number::intValue).result().orElse(Integer.valueOf(defaultVersion))).intValue();
/* 67 */     Dynamic<T> $$3 = new Dynamic($$0, $$0.remove($$1, "DataVersion"));
/* 68 */     Dynamic<T> $$4 = DataFixTypes.this.updateToCurrentVersion(dataFixer, $$3, $$2);
/* 69 */     return codec.decode($$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\DataFixTypes$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */