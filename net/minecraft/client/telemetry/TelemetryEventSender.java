/*    */ package net.minecraft.client.telemetry;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface TelemetryEventSender
/*    */ {
/*    */   default TelemetryEventSender decorate(Consumer<TelemetryPropertyMap.Builder> $$0) {
/* 10 */     return ($$1, $$2) -> send($$1, ());
/*    */   }
/*    */   
/*    */   public static final TelemetryEventSender DISABLED = ($$0, $$1) -> {
/*    */     
/*    */     };
/*    */   
/*    */   void send(TelemetryEventType paramTelemetryEventType, Consumer<TelemetryPropertyMap.Builder> paramConsumer);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryEventSender.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */