/*     */ package net.minecraft.client.multiplayer.chat;
/*     */ 
/*     */ import com.google.common.collect.Queues;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.time.Instant;
/*     */ import java.util.Deque;
/*     */ import java.util.UUID;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.GuiMessageTag;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*     */ import net.minecraft.network.chat.ChatType;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FilterMask;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MessageSignature;
/*     */ import net.minecraft.network.chat.PlayerChatMessage;
/*     */ import net.minecraft.util.StringDecomposer;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class ChatListener {
/*  25 */   private static final Component CHAT_VALIDATION_ERROR = (Component)Component.translatable("chat.validation_error").withStyle(new ChatFormatting[] { ChatFormatting.RED, ChatFormatting.ITALIC });
/*     */   
/*     */   private final Minecraft minecraft;
/*  28 */   private final Deque<Message> delayedMessageQueue = Queues.newArrayDeque();
/*     */   
/*     */   private long messageDelay;
/*     */   private long previousMessageTime;
/*     */   
/*     */   public ChatListener(Minecraft $$0) {
/*  34 */     this.minecraft = $$0;
/*     */   }
/*     */   
/*     */   public void tick() {
/*  38 */     if (this.messageDelay == 0L) {
/*     */       return;
/*     */     }
/*     */     
/*  42 */     if (Util.getMillis() >= this.previousMessageTime + this.messageDelay) {
/*     */       
/*  44 */       Message $$0 = this.delayedMessageQueue.poll();
/*  45 */       while ($$0 != null && !$$0.accept()) {
/*  46 */         $$0 = this.delayedMessageQueue.poll();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMessageDelay(double $$0) {
/*  52 */     long $$1 = (long)($$0 * 1000.0D);
/*     */     
/*  54 */     if ($$1 == 0L && this.messageDelay > 0L) {
/*     */       
/*  56 */       this.delayedMessageQueue.forEach(Message::accept);
/*  57 */       this.delayedMessageQueue.clear();
/*     */     } 
/*     */     
/*  60 */     this.messageDelay = $$1;
/*     */   }
/*     */   
/*     */   public void acceptNextDelayedMessage() {
/*  64 */     ((Message)this.delayedMessageQueue.remove()).accept();
/*     */   }
/*     */   
/*     */   public long queueSize() {
/*  68 */     return this.delayedMessageQueue.size();
/*     */   }
/*     */   
/*     */   public void clearQueue() {
/*  72 */     this.delayedMessageQueue.forEach(Message::accept);
/*  73 */     this.delayedMessageQueue.clear();
/*     */   }
/*     */   
/*     */   public boolean removeFromDelayedMessageQueue(MessageSignature $$0) {
/*  77 */     return this.delayedMessageQueue.removeIf($$1 -> $$0.equals($$1.signature()));
/*     */   }
/*     */   
/*     */   private boolean willDelayMessages() {
/*  81 */     return (this.messageDelay > 0L && Util.getMillis() < this.previousMessageTime + this.messageDelay);
/*     */   }
/*     */   
/*     */   private void handleMessage(@Nullable MessageSignature $$0, BooleanSupplier $$1) {
/*  85 */     if (willDelayMessages()) {
/*  86 */       this.delayedMessageQueue.add(new Message($$0, $$1));
/*     */     } else {
/*  88 */       $$1.getAsBoolean();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handlePlayerChatMessage(PlayerChatMessage $$0, GameProfile $$1, ChatType.Bound $$2) {
/*  93 */     boolean $$3 = ((Boolean)this.minecraft.options.onlyShowSecureChat().get()).booleanValue();
/*  94 */     PlayerChatMessage $$4 = $$3 ? $$0.removeUnsignedContent() : $$0;
/*  95 */     Component $$5 = $$2.decorate($$4.decoratedContent());
/*     */     
/*  97 */     Instant $$6 = Instant.now();
/*  98 */     handleMessage($$0.signature(), () -> {
/*     */           boolean $$6 = showMessageToPlayer($$0, $$1, $$2, $$3, $$4, $$5);
/*     */           ClientPacketListener $$7 = this.minecraft.getConnection();
/*     */           if ($$7 != null) {
/*     */             $$7.markMessageAsProcessed($$1, $$6);
/*     */           }
/*     */           return $$6;
/*     */         });
/*     */   }
/*     */   
/*     */   public void handleChatMessageError(UUID $$0, ChatType.Bound $$1) {
/* 109 */     handleMessage(null, () -> {
/*     */           if (this.minecraft.isBlocked($$0)) {
/*     */             return false;
/*     */           }
/*     */           Component $$2 = $$1.decorate(CHAT_VALIDATION_ERROR);
/*     */           this.minecraft.gui.getChat().addMessage($$2, null, GuiMessageTag.chatError());
/*     */           this.previousMessageTime = Util.getMillis();
/*     */           return true;
/*     */         });
/*     */   }
/*     */   
/*     */   public void handleDisguisedChatMessage(Component $$0, ChatType.Bound $$1) {
/* 121 */     Instant $$2 = Instant.now();
/*     */     
/* 123 */     handleMessage(null, () -> {
/*     */           Component $$3 = $$0.decorate($$1);
/*     */           this.minecraft.gui.getChat().addMessage($$3);
/*     */           narrateChatMessage($$0, $$1);
/*     */           logSystemMessage($$3, $$2);
/*     */           this.previousMessageTime = Util.getMillis();
/*     */           return true;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean showMessageToPlayer(ChatType.Bound $$0, PlayerChatMessage $$1, Component $$2, GameProfile $$3, boolean $$4, Instant $$5) {
/* 136 */     ChatTrustLevel $$6 = evaluateTrustLevel($$1, $$2, $$5);
/* 137 */     if ($$4 && $$6.isNotSecure()) {
/* 138 */       return false;
/*     */     }
/*     */     
/* 141 */     if (this.minecraft.isBlocked($$1.sender()) || $$1.isFullyFiltered()) {
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     GuiMessageTag $$7 = $$6.createTag($$1);
/*     */     
/* 147 */     MessageSignature $$8 = $$1.signature();
/* 148 */     FilterMask $$9 = $$1.filterMask();
/* 149 */     if ($$9.isEmpty()) {
/* 150 */       this.minecraft.gui.getChat().addMessage($$2, $$8, $$7);
/* 151 */       narrateChatMessage($$0, $$1.decoratedContent());
/*     */     } else {
/* 153 */       Component $$10 = $$9.applyWithFormatting($$1.signedContent());
/* 154 */       if ($$10 != null) {
/* 155 */         this.minecraft.gui.getChat().addMessage($$0.decorate($$10), $$8, $$7);
/* 156 */         narrateChatMessage($$0, $$10);
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     logPlayerMessage($$1, $$0, $$3, $$6);
/*     */     
/* 162 */     this.previousMessageTime = Util.getMillis();
/* 163 */     return true;
/*     */   }
/*     */   
/*     */   private void narrateChatMessage(ChatType.Bound $$0, Component $$1) {
/* 167 */     this.minecraft.getNarrator().sayChat($$0.decorateNarration($$1));
/*     */   }
/*     */   
/*     */   private ChatTrustLevel evaluateTrustLevel(PlayerChatMessage $$0, Component $$1, Instant $$2) {
/* 171 */     if (isSenderLocalPlayer($$0.sender())) {
/* 172 */       return ChatTrustLevel.SECURE;
/*     */     }
/*     */     
/* 175 */     return ChatTrustLevel.evaluate($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void logPlayerMessage(PlayerChatMessage $$0, ChatType.Bound $$1, GameProfile $$2, ChatTrustLevel $$3) {
/* 179 */     ChatLog $$4 = this.minecraft.getReportingContext().chatLog();
/* 180 */     $$4.push(LoggedChatMessage.player($$2, $$0, $$3));
/*     */   }
/*     */   
/*     */   private void logSystemMessage(Component $$0, Instant $$1) {
/* 184 */     ChatLog $$2 = this.minecraft.getReportingContext().chatLog();
/* 185 */     $$2.push(LoggedChatMessage.system($$0, $$1));
/*     */   }
/*     */   
/*     */   public void handleSystemMessage(Component $$0, boolean $$1) {
/* 189 */     if (((Boolean)this.minecraft.options.hideMatchedNames().get()).booleanValue() && this.minecraft.isBlocked(guessChatUUID($$0))) {
/*     */       return;
/*     */     }
/*     */     
/* 193 */     if ($$1) {
/* 194 */       this.minecraft.gui.setOverlayMessage($$0, false);
/*     */     } else {
/* 196 */       this.minecraft.gui.getChat().addMessage($$0);
/* 197 */       logSystemMessage($$0, Instant.now());
/*     */     } 
/*     */     
/* 200 */     this.minecraft.getNarrator().say($$0);
/*     */   }
/*     */   
/*     */   private UUID guessChatUUID(Component $$0) {
/* 204 */     String $$1 = StringDecomposer.getPlainText((FormattedText)$$0);
/* 205 */     String $$2 = StringUtils.substringBetween($$1, "<", ">");
/* 206 */     if ($$2 == null) {
/* 207 */       return Util.NIL_UUID;
/*     */     }
/*     */     
/* 210 */     return this.minecraft.getPlayerSocialManager().getDiscoveredUUID($$2);
/*     */   }
/*     */   
/*     */   private boolean isSenderLocalPlayer(UUID $$0) {
/* 214 */     if (this.minecraft.isLocalServer() && this.minecraft.player != null) {
/* 215 */       UUID $$1 = this.minecraft.player.getGameProfile().getId();
/* 216 */       return $$1.equals($$0);
/*     */     } 
/* 218 */     return false;
/*     */   } private static final class Message extends Record { @Nullable
/*     */     private final MessageSignature signature; private final BooleanSupplier handler;
/* 221 */     Message(@Nullable MessageSignature $$0, BooleanSupplier $$1) { this.signature = $$0; this.handler = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/ChatListener$Message;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #221	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 221 */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/ChatListener$Message; } @Nullable public MessageSignature signature() { return this.signature; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/ChatListener$Message;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #221	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/ChatListener$Message; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/ChatListener$Message;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #221	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/ChatListener$Message;
/* 221 */       //   0	8	1	$$0	Ljava/lang/Object; } public BooleanSupplier handler() { return this.handler; }
/*     */      public boolean accept() {
/* 223 */       return this.handler.getAsBoolean();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\ChatListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */