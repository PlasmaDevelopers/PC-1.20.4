/*    */ package net.minecraft.client.telemetry.events;
/*    */ 
/*    */ import com.google.common.base.Stopwatch;
/*    */ import com.google.common.base.Ticker;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.OptionalLong;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.telemetry.TelemetryEventSender;
/*    */ import net.minecraft.client.telemetry.TelemetryEventType;
/*    */ import net.minecraft.client.telemetry.TelemetryProperty;
/*    */ import net.minecraft.client.telemetry.TelemetryPropertyMap;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class GameLoadTimesEvent {
/* 19 */   public static final GameLoadTimesEvent INSTANCE = new GameLoadTimesEvent(Ticker.systemTicker());
/*    */   
/* 21 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final Ticker timeSource;
/* 24 */   private final Map<TelemetryProperty<Measurement>, Stopwatch> measurements = new HashMap<>();
/* 25 */   private OptionalLong bootstrapTime = OptionalLong.empty();
/*    */   
/*    */   protected GameLoadTimesEvent(Ticker $$0) {
/* 28 */     this.timeSource = $$0;
/*    */   }
/*    */   
/*    */   public synchronized void beginStep(TelemetryProperty<Measurement> $$0) {
/* 32 */     beginStep($$0, $$0 -> Stopwatch.createStarted(this.timeSource));
/*    */   }
/*    */   
/*    */   public synchronized void beginStep(TelemetryProperty<Measurement> $$0, Stopwatch $$1) {
/* 36 */     beginStep($$0, $$1 -> $$0);
/*    */   }
/*    */   
/*    */   private synchronized void beginStep(TelemetryProperty<Measurement> $$0, Function<TelemetryProperty<Measurement>, Stopwatch> $$1) {
/* 40 */     this.measurements.computeIfAbsent($$0, $$1);
/*    */   }
/*    */   
/*    */   public synchronized void endStep(TelemetryProperty<Measurement> $$0) {
/* 44 */     Stopwatch $$1 = this.measurements.get($$0);
/* 45 */     if ($$1 == null) {
/* 46 */       LOGGER.warn("Attempted to end step for {} before starting it", $$0.id());
/*    */       return;
/*    */     } 
/* 49 */     if ($$1.isRunning()) {
/* 50 */       $$1.stop();
/*    */     }
/*    */   }
/*    */   
/*    */   public void send(TelemetryEventSender $$0) {
/* 55 */     $$0.send(TelemetryEventType.GAME_LOAD_TIMES, $$0 -> {
/*    */           synchronized (this) {
/*    */             this.measurements.forEach(());
/*    */             this.bootstrapTime.ifPresent(());
/*    */             this.measurements.clear();
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void setBootstrapTime(long $$0) {
/* 72 */     this.bootstrapTime = OptionalLong.of($$0);
/*    */   }
/*    */   public static final class Measurement extends Record { private final int millis; public static final Codec<Measurement> CODEC;
/* 75 */     public Measurement(int $$0) { this.millis = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #75	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 75 */       //   0	7	0	this	Lnet/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement; } public int millis() { return this.millis; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #75	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #75	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/telemetry/events/GameLoadTimesEvent$Measurement;
/* 76 */       //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = Codec.INT.xmap(Measurement::new, $$0 -> Integer.valueOf($$0.millis)); }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\events\GameLoadTimesEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */