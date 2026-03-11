/*    */ package net.minecraft.util.datafix.fixes;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ final class Conversion extends Record {
/*    */   private final Map<String, String> biomeMapping;
/*    */   final String fallback;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/datafix/fixes/StructuresBecomeConfiguredFix$Conversion;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   private Conversion(Map<String, String> $$0, String $$1)
/*    */   {
/* 26 */     this.biomeMapping = $$0; this.fallback = $$1; } public Map<String, String> biomeMapping() { return this.biomeMapping; } public String fallback() { return this.fallback; }
/*    */    public static Conversion trivial(String $$0) {
/* 28 */     return new Conversion(Map.of(), $$0);
/*    */   }
/*    */   
/*    */   public static Conversion biomeMapped(Map<List<String>, String> $$0, String $$1) {
/* 32 */     return new Conversion(unpack($$0), $$1);
/*    */   }
/*    */   
/*    */   private static Map<String, String> unpack(Map<List<String>, String> $$0) {
/* 36 */     ImmutableMap.Builder<String, String> $$1 = ImmutableMap.builder();
/* 37 */     for (Iterator<Map.Entry<List<String>, String>> iterator = $$0.entrySet().iterator(); iterator.hasNext(); ) { Map.Entry<List<String>, String> $$2 = iterator.next();
/* 38 */       ((List)$$2.getKey()).forEach($$2 -> $$0.put($$2, $$1.getValue())); }
/*    */     
/* 40 */     return (Map<String, String>)$$1.build();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\StructuresBecomeConfiguredFix$Conversion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */