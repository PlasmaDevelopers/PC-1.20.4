/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class ChatTypeDecoration extends Record {
/*    */   private final String translationKey;
/*    */   private final List<Parameter> parameters;
/*    */   private final Style style;
/*    */   public static final Codec<ChatTypeDecoration> CODEC;
/*    */   
/* 12 */   public ChatTypeDecoration(String $$0, List<Parameter> $$1, Style $$2) { this.translationKey = $$0; this.parameters = $$1; this.style = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/ChatTypeDecoration;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/network/chat/ChatTypeDecoration; } public String translationKey() { return this.translationKey; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/ChatTypeDecoration;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/ChatTypeDecoration; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/ChatTypeDecoration;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/ChatTypeDecoration;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public List<Parameter> parameters() { return this.parameters; } public Style style() { return this.style; } static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.STRING.fieldOf("translation_key").forGetter(ChatTypeDecoration::translationKey), (App)Parameter.CODEC.listOf().fieldOf("parameters").forGetter(ChatTypeDecoration::parameters), (App)Style.Serializer.CODEC.optionalFieldOf("style", Style.EMPTY).forGetter(ChatTypeDecoration::style)).apply((Applicative)$$0, ChatTypeDecoration::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ChatTypeDecoration withSender(String $$0) {
/* 20 */     return new ChatTypeDecoration($$0, List.of(Parameter.SENDER, Parameter.CONTENT), Style.EMPTY);
/*    */   }
/*    */   
/*    */   public static ChatTypeDecoration incomingDirectMessage(String $$0) {
/* 24 */     Style $$1 = Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(Boolean.valueOf(true));
/* 25 */     return new ChatTypeDecoration($$0, List.of(Parameter.SENDER, Parameter.CONTENT), $$1);
/*    */   }
/*    */   
/*    */   public static ChatTypeDecoration outgoingDirectMessage(String $$0) {
/* 29 */     Style $$1 = Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(Boolean.valueOf(true));
/* 30 */     return new ChatTypeDecoration($$0, List.of(Parameter.TARGET, Parameter.CONTENT), $$1);
/*    */   }
/*    */   
/*    */   public static ChatTypeDecoration teamMessage(String $$0) {
/* 34 */     return new ChatTypeDecoration($$0, List.of(Parameter.TARGET, Parameter.SENDER, Parameter.CONTENT), Style.EMPTY);
/*    */   }
/*    */   
/*    */   public Component decorate(Component $$0, ChatType.Bound $$1) {
/* 38 */     Component[] arrayOfComponent = resolveParameters($$0, $$1);
/* 39 */     return Component.translatable(this.translationKey, (Object[])arrayOfComponent).withStyle(this.style);
/*    */   }
/*    */   
/*    */   private Component[] resolveParameters(Component $$0, ChatType.Bound $$1) {
/* 43 */     Component[] $$2 = new Component[this.parameters.size()];
/* 44 */     for (int $$3 = 0; $$3 < $$2.length; $$3++) {
/* 45 */       Parameter $$4 = this.parameters.get($$3);
/* 46 */       $$2[$$3] = $$4.select($$0, $$1);
/*    */     } 
/* 48 */     return $$2;
/*    */   }
/*    */   public enum Parameter implements StringRepresentable { SENDER, TARGET, CONTENT; public static final Codec<Parameter> CODEC; private final String name; private final Selector selector;
/*    */     static {
/* 52 */       SENDER = new Parameter("SENDER", 0, "sender", ($$0, $$1) -> $$1.name());
/* 53 */       TARGET = new Parameter("TARGET", 1, "target", ($$0, $$1) -> $$1.targetName());
/* 54 */       CONTENT = new Parameter("CONTENT", 2, "content", ($$0, $$1) -> $$0);
/*    */     } static {
/* 56 */       CODEC = (Codec<Parameter>)StringRepresentable.fromEnum(Parameter::values);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     Parameter(String $$0, Selector $$1) {
/* 62 */       this.name = $$0;
/* 63 */       this.selector = $$1;
/*    */     }
/*    */     
/*    */     public Component select(Component $$0, ChatType.Bound $$1) {
/* 67 */       Component $$2 = this.selector.select($$0, $$1);
/* 68 */       return Objects.<Component>requireNonNullElse($$2, CommonComponents.EMPTY);
/*    */     }
/*    */ 
/*    */     
/*    */     public String getSerializedName() {
/* 73 */       return this.name;
/*    */     }
/*    */     
/*    */     public static interface Selector {
/*    */       @Nullable
/*    */       Component select(Component param2Component, ChatType.Bound param2Bound);
/*    */     } }
/*    */ 
/*    */   
/*    */   public static interface Selector {
/*    */     @Nullable
/*    */     Component select(Component param1Component, ChatType.Bound param1Bound);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ChatTypeDecoration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */