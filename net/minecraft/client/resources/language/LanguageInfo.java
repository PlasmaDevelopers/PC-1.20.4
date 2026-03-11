/*    */ package net.minecraft.client.resources.language;
/*    */ public final class LanguageInfo extends Record {
/*    */   private final String region;
/*    */   private final String name;
/*    */   private final boolean bidirectional;
/*    */   public static final Codec<LanguageInfo> CODEC;
/*    */   
/*  8 */   public LanguageInfo(String $$0, String $$1, boolean $$2) { this.region = $$0; this.name = $$1; this.bidirectional = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/resources/language/LanguageInfo;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  8 */     //   0	7	0	this	Lnet/minecraft/client/resources/language/LanguageInfo; } public String region() { return this.region; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/resources/language/LanguageInfo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/resources/language/LanguageInfo; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/resources/language/LanguageInfo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #8	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/resources/language/LanguageInfo;
/*  8 */     //   0	8	1	$$0	Ljava/lang/Object; } public String name() { return this.name; } public boolean bidirectional() { return this.bidirectional; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.NON_EMPTY_STRING.fieldOf("region").forGetter(LanguageInfo::region), (App)ExtraCodecs.NON_EMPTY_STRING.fieldOf("name").forGetter(LanguageInfo::name), (App)Codec.BOOL.optionalFieldOf("bidirectional", Boolean.valueOf(false)).forGetter(LanguageInfo::bidirectional)).apply((Applicative)$$0, LanguageInfo::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Component toComponent() {
/* 24 */     return (Component)Component.literal(this.name + " (" + this.name + ")");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\language\LanguageInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */