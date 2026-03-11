/*     */ package net.minecraft.client.telemetry;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.Encoder;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.MapLike;
/*     */ import com.mojang.serialization.RecordBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class TelemetryPropertyMap
/*     */ {
/*     */   TelemetryPropertyMap(Map<TelemetryProperty<?>, Object> $$0) {
/*  21 */     this.entries = $$0;
/*     */   }
/*     */   final Map<TelemetryProperty<?>, Object> entries;
/*     */   public static Builder builder() {
/*  25 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static Codec<TelemetryPropertyMap> createCodec(final List<TelemetryProperty<?>> properties) {
/*  29 */     return (new MapCodec<TelemetryPropertyMap>()
/*     */       {
/*     */         public <T> RecordBuilder<T> encode(TelemetryPropertyMap $$0, DynamicOps<T> $$1, RecordBuilder<T> $$2) {
/*  32 */           RecordBuilder<T> $$3 = $$2;
/*  33 */           for (TelemetryProperty<?> $$4 : (Iterable<TelemetryProperty<?>>)properties) {
/*  34 */             $$3 = encodeProperty($$0, $$3, $$4);
/*     */           }
/*  36 */           return $$3;
/*     */         }
/*     */         
/*     */         private <T, V> RecordBuilder<T> encodeProperty(TelemetryPropertyMap $$0, RecordBuilder<T> $$1, TelemetryProperty<V> $$2) {
/*  40 */           V $$3 = $$0.get($$2);
/*  41 */           if ($$3 != null) {
/*  42 */             return $$1.add($$2.id(), $$3, (Encoder)$$2.codec());
/*     */           }
/*  44 */           return $$1;
/*     */         }
/*     */ 
/*     */         
/*     */         public <T> DataResult<TelemetryPropertyMap> decode(DynamicOps<T> $$0, MapLike<T> $$1) {
/*  49 */           DataResult<TelemetryPropertyMap.Builder> $$2 = DataResult.success(new TelemetryPropertyMap.Builder());
/*  50 */           for (TelemetryProperty<?> $$3 : (Iterable<TelemetryProperty<?>>)properties) {
/*  51 */             $$2 = decodeProperty($$2, $$0, $$1, $$3);
/*     */           }
/*  53 */           return $$2.map(TelemetryPropertyMap.Builder::build);
/*     */         }
/*     */         
/*     */         private <T, V> DataResult<TelemetryPropertyMap.Builder> decodeProperty(DataResult<TelemetryPropertyMap.Builder> $$0, DynamicOps<T> $$1, MapLike<T> $$2, TelemetryProperty<V> $$3) {
/*  57 */           T $$4 = (T)$$2.get($$3.id());
/*  58 */           if ($$4 != null) {
/*  59 */             DataResult<V> $$5 = $$3.codec().parse($$1, $$4);
/*  60 */             return $$0.apply2stable(($$1, $$2) -> $$1.put($$0, $$2), $$5);
/*     */           } 
/*  62 */           return $$0;
/*     */         }
/*     */ 
/*     */         
/*     */         public <T> Stream<T> keys(DynamicOps<T> $$0) {
/*  67 */           Objects.requireNonNull($$0); return properties.stream().map(TelemetryProperty::id).map($$0::createString);
/*     */         }
/*  69 */       }).codec();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T> T get(TelemetryProperty<T> $$0) {
/*  75 */     return (T)this.entries.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  80 */     return this.entries.toString();
/*     */   }
/*     */   
/*     */   public Set<TelemetryProperty<?>> propertySet() {
/*  84 */     return this.entries.keySet();
/*     */   }
/*     */   
/*     */   public static class Builder {
/*  88 */     private final Map<TelemetryProperty<?>, Object> entries = (Map<TelemetryProperty<?>, Object>)new Reference2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> Builder put(TelemetryProperty<T> $$0, T $$1) {
/*  94 */       this.entries.put($$0, $$1);
/*  95 */       return this;
/*     */     }
/*     */     
/*     */     public <T> Builder putIfNotNull(TelemetryProperty<T> $$0, @Nullable T $$1) {
/*  99 */       if ($$1 != null) {
/* 100 */         this.entries.put($$0, $$1);
/*     */       }
/* 102 */       return this;
/*     */     }
/*     */     
/*     */     public Builder putAll(TelemetryPropertyMap $$0) {
/* 106 */       this.entries.putAll($$0.entries);
/* 107 */       return this;
/*     */     }
/*     */     
/*     */     public TelemetryPropertyMap build() {
/* 111 */       return new TelemetryPropertyMap(this.entries);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryPropertyMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */