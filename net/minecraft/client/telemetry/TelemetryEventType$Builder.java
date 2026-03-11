/*     */ package net.minecraft.client.telemetry;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */   private final String id;
/*     */   private final String exportKey;
/* 169 */   private final List<TelemetryProperty<?>> properties = new ArrayList<>();
/*     */   private boolean isOptIn;
/*     */   
/*     */   Builder(String $$0, String $$1) {
/* 173 */     this.id = $$0;
/* 174 */     this.exportKey = $$1;
/*     */   }
/*     */   
/*     */   public Builder defineAll(List<TelemetryProperty<?>> $$0) {
/* 178 */     this.properties.addAll($$0);
/* 179 */     return this;
/*     */   }
/*     */   
/*     */   public <T> Builder define(TelemetryProperty<T> $$0) {
/* 183 */     this.properties.add($$0);
/* 184 */     return this;
/*     */   }
/*     */   
/*     */   public Builder optIn() {
/* 188 */     this.isOptIn = true;
/* 189 */     return this;
/*     */   }
/*     */   
/*     */   public TelemetryEventType register() {
/* 193 */     TelemetryEventType $$0 = new TelemetryEventType(this.id, this.exportKey, List.copyOf(this.properties), this.isOptIn);
/* 194 */     if (TelemetryEventType.REGISTRY.putIfAbsent(this.id, $$0) != null) {
/* 195 */       throw new IllegalStateException("Duplicate TelemetryEventType with key: '" + this.id + "'");
/*     */     }
/* 197 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryEventType$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */