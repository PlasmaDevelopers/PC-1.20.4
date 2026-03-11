/*    */ package net.minecraft.client.telemetry;
/*    */ 
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.Encoder;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.MapLike;
/*    */ import com.mojang.serialization.RecordBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
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
/*    */ class null
/*    */   extends MapCodec<TelemetryPropertyMap>
/*    */ {
/*    */   public <T> RecordBuilder<T> encode(TelemetryPropertyMap $$0, DynamicOps<T> $$1, RecordBuilder<T> $$2) {
/* 32 */     RecordBuilder<T> $$3 = $$2;
/* 33 */     for (TelemetryProperty<?> $$4 : (Iterable<TelemetryProperty<?>>)properties) {
/* 34 */       $$3 = encodeProperty($$0, $$3, $$4);
/*    */     }
/* 36 */     return $$3;
/*    */   }
/*    */   
/*    */   private <T, V> RecordBuilder<T> encodeProperty(TelemetryPropertyMap $$0, RecordBuilder<T> $$1, TelemetryProperty<V> $$2) {
/* 40 */     V $$3 = $$0.get($$2);
/* 41 */     if ($$3 != null) {
/* 42 */       return $$1.add($$2.id(), $$3, (Encoder)$$2.codec());
/*    */     }
/* 44 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> DataResult<TelemetryPropertyMap> decode(DynamicOps<T> $$0, MapLike<T> $$1) {
/* 49 */     DataResult<TelemetryPropertyMap.Builder> $$2 = DataResult.success(new TelemetryPropertyMap.Builder());
/* 50 */     for (TelemetryProperty<?> $$3 : (Iterable<TelemetryProperty<?>>)properties) {
/* 51 */       $$2 = decodeProperty($$2, $$0, $$1, $$3);
/*    */     }
/* 53 */     return $$2.map(TelemetryPropertyMap.Builder::build);
/*    */   }
/*    */   
/*    */   private <T, V> DataResult<TelemetryPropertyMap.Builder> decodeProperty(DataResult<TelemetryPropertyMap.Builder> $$0, DynamicOps<T> $$1, MapLike<T> $$2, TelemetryProperty<V> $$3) {
/* 57 */     T $$4 = (T)$$2.get($$3.id());
/* 58 */     if ($$4 != null) {
/* 59 */       DataResult<V> $$5 = $$3.codec().parse($$1, $$4);
/* 60 */       return $$0.apply2stable(($$1, $$2) -> $$1.put($$0, $$2), $$5);
/*    */     } 
/* 62 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> Stream<T> keys(DynamicOps<T> $$0) {
/* 67 */     Objects.requireNonNull($$0); return properties.stream().map(TelemetryProperty::id).map($$0::createString);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryPropertyMap$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */