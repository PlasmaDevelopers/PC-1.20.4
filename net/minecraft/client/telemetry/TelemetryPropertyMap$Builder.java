/*     */ package net.minecraft.client.telemetry;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/*  88 */   private final Map<TelemetryProperty<?>, Object> entries = (Map<TelemetryProperty<?>, Object>)new Reference2ObjectOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Builder put(TelemetryProperty<T> $$0, T $$1) {
/*  94 */     this.entries.put($$0, $$1);
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   public <T> Builder putIfNotNull(TelemetryProperty<T> $$0, @Nullable T $$1) {
/*  99 */     if ($$1 != null) {
/* 100 */       this.entries.put($$0, $$1);
/*     */     }
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public Builder putAll(TelemetryPropertyMap $$0) {
/* 106 */     this.entries.putAll($$0.entries);
/* 107 */     return this;
/*     */   }
/*     */   
/*     */   public TelemetryPropertyMap build() {
/* 111 */     return new TelemetryPropertyMap(this.entries);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryPropertyMap$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */