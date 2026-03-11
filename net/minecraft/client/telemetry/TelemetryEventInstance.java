/*    */ package net.minecraft.client.telemetry;
/*    */ 
/*    */ public final class TelemetryEventInstance extends Record {
/*    */   private final TelemetryEventType type;
/*    */   private final TelemetryPropertyMap properties;
/*    */   
/*  7 */   public TelemetryEventType type() { return this.type; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/telemetry/TelemetryEventInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/telemetry/TelemetryEventInstance; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/telemetry/TelemetryEventInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/telemetry/TelemetryEventInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/telemetry/TelemetryEventInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #7	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/telemetry/TelemetryEventInstance;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public TelemetryPropertyMap properties() { return this.properties; }
/*  8 */    public static final Codec<TelemetryEventInstance> CODEC = TelemetryEventType.CODEC.dispatchStable(TelemetryEventInstance::type, TelemetryEventType::codec);
/*    */   
/*    */   public TelemetryEventInstance(TelemetryEventType $$0, TelemetryPropertyMap $$1) {
/* 11 */     $$1.propertySet().forEach($$1 -> {
/*    */           if (!$$0.contains($$1))
/*    */             throw new IllegalArgumentException("Property '" + $$1.id() + "' not expected for event: '" + $$0.id() + "'"); 
/*    */         });
/*    */     this.type = $$0;
/*    */     this.properties = $$1;
/*    */   }
/*    */   public TelemetryEvent export(TelemetrySession $$0) {
/* 19 */     return this.type.export($$0, this.properties);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryEventInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */