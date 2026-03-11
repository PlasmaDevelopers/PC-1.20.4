/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.tree.CommandNode;
/*    */ import com.mojang.brigadier.tree.LiteralCommandNode;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.MessageArgument;
/*    */ import net.minecraft.network.chat.ChatType;
/*    */ import net.minecraft.network.chat.OutgoingChatMessage;
/*    */ import net.minecraft.network.chat.PlayerChatMessage;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.server.players.PlayerList;
/*    */ 
/*    */ public class MsgCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 23 */     LiteralCommandNode<CommandSourceStack> $$1 = $$0.register(
/* 24 */         (LiteralArgumentBuilder)Commands.literal("msg")
/* 25 */         .then(
/* 26 */           Commands.argument("targets", (ArgumentType)EntityArgument.players())
/* 27 */           .then(
/* 28 */             Commands.argument("message", (ArgumentType)MessageArgument.message())
/* 29 */             .executes($$0 -> {
/*    */                 Collection<ServerPlayer> $$1 = EntityArgument.getPlayers($$0, "targets");
/*    */ 
/*    */                 
/*    */                 if (!$$1.isEmpty()) {
/*    */                   MessageArgument.resolveChatMessage($$0, "message", ());
/*    */                 }
/*    */ 
/*    */                 
/*    */                 return $$1.size();
/*    */               }))));
/*    */     
/* 41 */     $$0.register((LiteralArgumentBuilder)Commands.literal("tell").redirect((CommandNode)$$1));
/* 42 */     $$0.register((LiteralArgumentBuilder)Commands.literal("w").redirect((CommandNode)$$1));
/*    */   }
/*    */   private static void sendMessage(CommandSourceStack $$0, Collection<ServerPlayer> $$1, PlayerChatMessage $$2) {
/*    */     int i;
/* 46 */     ChatType.Bound $$3 = ChatType.bind(ChatType.MSG_COMMAND_INCOMING, $$0);
/* 47 */     OutgoingChatMessage $$4 = OutgoingChatMessage.create($$2);
/*    */     
/* 49 */     boolean $$5 = false;
/*    */     
/* 51 */     for (ServerPlayer $$6 : $$1) {
/*    */       
/* 53 */       ChatType.Bound $$7 = ChatType.bind(ChatType.MSG_COMMAND_OUTGOING, $$0).withTargetName($$6.getDisplayName());
/* 54 */       $$0.sendChatMessage($$4, false, $$7);
/*    */       
/* 56 */       boolean $$8 = $$0.shouldFilterMessageTo($$6);
/* 57 */       $$6.sendChatMessage($$4, $$8, $$3);
/*    */       
/* 59 */       i = $$5 | (($$8 && $$2.isFullyFiltered()) ? 1 : 0);
/*    */     } 
/*    */     
/* 62 */     if (i != 0)
/* 63 */       $$0.sendSystemMessage(PlayerList.CHAT_FILTERED_FULL); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\MsgCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */