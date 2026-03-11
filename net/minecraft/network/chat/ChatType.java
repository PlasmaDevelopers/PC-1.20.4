/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.BootstapContext;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ 
/*     */ public final class ChatType extends Record {
/*     */   private final ChatTypeDecoration chat;
/*     */   private final ChatTypeDecoration narration;
/*     */   public static final Codec<ChatType> CODEC;
/*     */   
/*  18 */   public ChatType(ChatTypeDecoration $$0, ChatTypeDecoration $$1) { this.chat = $$0; this.narration = $$1; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/ChatType;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #18	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  18 */     //   0	7	0	this	Lnet/minecraft/network/chat/ChatType; } public ChatTypeDecoration chat() { return this.chat; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/ChatType;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #18	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/chat/ChatType; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/ChatType;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #18	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/network/chat/ChatType;
/*  18 */     //   0	8	1	$$0	Ljava/lang/Object; } public ChatTypeDecoration narration() { return this.narration; } static {
/*  19 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ChatTypeDecoration.CODEC.fieldOf("chat").forGetter(ChatType::chat), (App)ChatTypeDecoration.CODEC.fieldOf("narration").forGetter(ChatType::narration)).apply((Applicative)$$0, ChatType::new));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  24 */   public static final ChatTypeDecoration DEFAULT_CHAT_DECORATION = ChatTypeDecoration.withSender("chat.type.text");
/*     */   
/*  26 */   public static final ResourceKey<ChatType> CHAT = create("chat");
/*     */   
/*  28 */   public static final ResourceKey<ChatType> SAY_COMMAND = create("say_command");
/*  29 */   public static final ResourceKey<ChatType> MSG_COMMAND_INCOMING = create("msg_command_incoming");
/*  30 */   public static final ResourceKey<ChatType> MSG_COMMAND_OUTGOING = create("msg_command_outgoing");
/*  31 */   public static final ResourceKey<ChatType> TEAM_MSG_COMMAND_INCOMING = create("team_msg_command_incoming");
/*  32 */   public static final ResourceKey<ChatType> TEAM_MSG_COMMAND_OUTGOING = create("team_msg_command_outgoing");
/*  33 */   public static final ResourceKey<ChatType> EMOTE_COMMAND = create("emote_command");
/*     */   
/*     */   private static ResourceKey<ChatType> create(String $$0) {
/*  36 */     return ResourceKey.create(Registries.CHAT_TYPE, new ResourceLocation($$0));
/*     */   }
/*     */   
/*     */   public static void bootstrap(BootstapContext<ChatType> $$0) {
/*  40 */     $$0.register(CHAT, new ChatType(DEFAULT_CHAT_DECORATION, 
/*     */           
/*  42 */           ChatTypeDecoration.withSender("chat.type.text.narrate")));
/*     */ 
/*     */     
/*  45 */     $$0.register(SAY_COMMAND, new ChatType(
/*  46 */           ChatTypeDecoration.withSender("chat.type.announcement"), 
/*  47 */           ChatTypeDecoration.withSender("chat.type.text.narrate")));
/*     */     
/*  49 */     $$0.register(MSG_COMMAND_INCOMING, new ChatType(
/*  50 */           ChatTypeDecoration.incomingDirectMessage("commands.message.display.incoming"), 
/*  51 */           ChatTypeDecoration.withSender("chat.type.text.narrate")));
/*     */     
/*  53 */     $$0.register(MSG_COMMAND_OUTGOING, new ChatType(
/*  54 */           ChatTypeDecoration.outgoingDirectMessage("commands.message.display.outgoing"), 
/*  55 */           ChatTypeDecoration.withSender("chat.type.text.narrate")));
/*     */     
/*  57 */     $$0.register(TEAM_MSG_COMMAND_INCOMING, new ChatType(
/*  58 */           ChatTypeDecoration.teamMessage("chat.type.team.text"), 
/*  59 */           ChatTypeDecoration.withSender("chat.type.text.narrate")));
/*     */     
/*  61 */     $$0.register(TEAM_MSG_COMMAND_OUTGOING, new ChatType(
/*  62 */           ChatTypeDecoration.teamMessage("chat.type.team.sent"), 
/*  63 */           ChatTypeDecoration.withSender("chat.type.text.narrate")));
/*     */     
/*  65 */     $$0.register(EMOTE_COMMAND, new ChatType(
/*  66 */           ChatTypeDecoration.withSender("chat.type.emote"), 
/*  67 */           ChatTypeDecoration.withSender("chat.type.emote")));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Bound bind(ResourceKey<ChatType> $$0, Entity $$1) {
/*  72 */     return bind($$0, $$1.level().registryAccess(), $$1.getDisplayName());
/*     */   }
/*     */   
/*     */   public static Bound bind(ResourceKey<ChatType> $$0, CommandSourceStack $$1) {
/*  76 */     return bind($$0, $$1.registryAccess(), $$1.getDisplayName());
/*     */   }
/*     */   
/*     */   public static Bound bind(ResourceKey<ChatType> $$0, RegistryAccess $$1, Component $$2) {
/*  80 */     Registry<ChatType> $$3 = $$1.registryOrThrow(Registries.CHAT_TYPE);
/*  81 */     return ((ChatType)$$3.getOrThrow($$0)).bind($$2);
/*     */   }
/*     */   
/*     */   public Bound bind(Component $$0) {
/*  85 */     return new Bound(this, $$0);
/*     */   } public static final class Bound extends Record { private final ChatType chatType; private final Component name; @Nullable
/*     */     private final Component targetName;
/*  88 */     public Bound(ChatType $$0, Component $$1, @Nullable Component $$2) { this.chatType = $$0; this.name = $$1; this.targetName = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/ChatType$Bound;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #88	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/network/chat/ChatType$Bound; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/ChatType$Bound;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #88	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/network/chat/ChatType$Bound; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/ChatType$Bound;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #88	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/network/chat/ChatType$Bound;
/*  88 */       //   0	8	1	$$0	Ljava/lang/Object; } public ChatType chatType() { return this.chatType; } public Component name() { return this.name; } @Nullable public Component targetName() { return this.targetName; }
/*     */      Bound(ChatType $$0, Component $$1) {
/*  90 */       this($$0, $$1, null);
/*     */     }
/*     */     
/*     */     public Component decorate(Component $$0) {
/*  94 */       return this.chatType.chat().decorate($$0, this);
/*     */     }
/*     */     
/*     */     public Component decorateNarration(Component $$0) {
/*  98 */       return this.chatType.narration().decorate($$0, this);
/*     */     }
/*     */     
/*     */     public Bound withTargetName(Component $$0) {
/* 102 */       return new Bound(this.chatType, this.name, $$0);
/*     */     }
/*     */     
/*     */     public ChatType.BoundNetwork toNetwork(RegistryAccess $$0) {
/* 106 */       Registry<ChatType> $$1 = $$0.registryOrThrow(Registries.CHAT_TYPE);
/* 107 */       return new ChatType.BoundNetwork($$1.getId(this.chatType), this.name, this.targetName);
/*     */     } }
/*     */   public static final class BoundNetwork extends Record { private final int chatType; private final Component name; @Nullable
/*     */     private final Component targetName;
/* 111 */     public BoundNetwork(int $$0, Component $$1, @Nullable Component $$2) { this.chatType = $$0; this.name = $$1; this.targetName = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/ChatType$BoundNetwork;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #111	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/network/chat/ChatType$BoundNetwork; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/ChatType$BoundNetwork;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #111	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/network/chat/ChatType$BoundNetwork; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/ChatType$BoundNetwork;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #111	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/network/chat/ChatType$BoundNetwork;
/* 111 */       //   0	8	1	$$0	Ljava/lang/Object; } public int chatType() { return this.chatType; } public Component name() { return this.name; } @Nullable public Component targetName() { return this.targetName; }
/*     */      public BoundNetwork(FriendlyByteBuf $$0) {
/* 113 */       this($$0.readVarInt(), $$0.readComponentTrusted(), (Component)$$0.readNullable(FriendlyByteBuf::readComponentTrusted));
/*     */     }
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 117 */       $$0.writeVarInt(this.chatType);
/* 118 */       $$0.writeComponent(this.name);
/* 119 */       $$0.writeNullable(this.targetName, FriendlyByteBuf::writeComponent);
/*     */     }
/*     */     
/*     */     public Optional<ChatType.Bound> resolve(RegistryAccess $$0) {
/* 123 */       Registry<ChatType> $$1 = $$0.registryOrThrow(Registries.CHAT_TYPE);
/* 124 */       ChatType $$2 = (ChatType)$$1.byId(this.chatType);
/* 125 */       return Optional.<ChatType>ofNullable($$2).map($$0 -> new ChatType.Bound($$0, this.name, this.targetName));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ChatType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */