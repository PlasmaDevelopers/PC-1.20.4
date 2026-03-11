/*    */ package net.minecraft.client.multiplayer.chat;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.time.LocalDateTime;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.time.format.FormatStyle;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.PlayerChatMessage;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ public final class Player extends Record implements LoggedChatMessage {
/*    */   private final GameProfile profile;
/*    */   private final PlayerChatMessage message;
/*    */   private final ChatTrustLevel trustLevel;
/*    */   public static final Codec<Player> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #36	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/* 36 */   public Player(GameProfile $$0, PlayerChatMessage $$1, ChatTrustLevel $$2) { this.profile = $$0; this.message = $$1; this.trustLevel = $$2; } public GameProfile profile() { return this.profile; } public PlayerChatMessage message() { return this.message; } public ChatTrustLevel trustLevel() { return this.trustLevel; } static {
/* 37 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.GAME_PROFILE.fieldOf("profile").forGetter(Player::profile), (App)PlayerChatMessage.MAP_CODEC.forGetter(Player::message), (App)ChatTrustLevel.CODEC.optionalFieldOf("trust_level", ChatTrustLevel.SECURE).forGetter(Player::trustLevel)).apply((Applicative)$$0, Player::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
/*    */ 
/*    */   
/*    */   public Component toContentComponent() {
/* 47 */     if (!this.message.filterMask().isEmpty()) {
/* 48 */       Component $$0 = this.message.filterMask().applyWithFormatting(this.message.signedContent());
/* 49 */       return ($$0 != null) ? $$0 : (Component)Component.empty();
/*    */     } 
/* 51 */     return this.message.decoratedContent();
/*    */   }
/*    */ 
/*    */   
/*    */   public Component toNarrationComponent() {
/* 56 */     Component $$0 = toContentComponent();
/* 57 */     Component $$1 = getTimeComponent();
/* 58 */     return (Component)Component.translatable("gui.chatSelection.message.narrate", new Object[] { this.profile.getName(), $$0, $$1 });
/*    */   }
/*    */   
/*    */   public Component toHeadingComponent() {
/* 62 */     Component $$0 = getTimeComponent();
/* 63 */     return (Component)Component.translatable("gui.chatSelection.heading", new Object[] { this.profile.getName(), $$0 });
/*    */   }
/*    */   
/*    */   private Component getTimeComponent() {
/* 67 */     LocalDateTime $$0 = LocalDateTime.ofInstant(this.message.timeStamp(), ZoneOffset.systemDefault());
/* 68 */     return (Component)Component.literal($$0.format(TIME_FORMATTER)).withStyle(new ChatFormatting[] { ChatFormatting.ITALIC, ChatFormatting.GRAY });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canReport(UUID $$0) {
/* 73 */     return this.message.hasSignatureFrom($$0);
/*    */   }
/*    */   
/*    */   public UUID profileId() {
/* 77 */     return this.profile.getId();
/*    */   }
/*    */ 
/*    */   
/*    */   public LoggedChatEvent.Type type() {
/* 82 */     return LoggedChatEvent.Type.PLAYER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\LoggedChatMessage$Player.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */