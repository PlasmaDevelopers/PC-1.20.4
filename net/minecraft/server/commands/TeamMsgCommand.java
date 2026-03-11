/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import com.mojang.brigadier.tree.CommandNode;
/*    */ import com.mojang.brigadier.tree.LiteralCommandNode;
/*    */ import java.util.List;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.MessageArgument;
/*    */ import net.minecraft.network.chat.ChatType;
/*    */ import net.minecraft.network.chat.ClickEvent;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.HoverEvent;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.network.chat.OutgoingChatMessage;
/*    */ import net.minecraft.network.chat.PlayerChatMessage;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.server.players.PlayerList;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.scores.PlayerTeam;
/*    */ 
/*    */ public class TeamMsgCommand {
/* 27 */   private static final Style SUGGEST_STYLE = Style.EMPTY
/* 28 */     .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("chat.type.team.hover")))
/* 29 */     .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/teammsg "));
/*    */   
/* 31 */   private static final SimpleCommandExceptionType ERROR_NOT_ON_TEAM = new SimpleCommandExceptionType((Message)Component.translatable("commands.teammsg.failed.noteam"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 34 */     LiteralCommandNode<CommandSourceStack> $$1 = $$0.register(
/* 35 */         (LiteralArgumentBuilder)Commands.literal("teammsg")
/* 36 */         .then(
/* 37 */           Commands.argument("message", (ArgumentType)MessageArgument.message())
/* 38 */           .executes($$0 -> {
/*    */               CommandSourceStack $$1 = (CommandSourceStack)$$0.getSource();
/*    */ 
/*    */               
/*    */               Entity $$2 = $$1.getEntityOrException();
/*    */ 
/*    */               
/*    */               PlayerTeam $$3 = $$2.getTeam();
/*    */               
/*    */               if ($$3 == null) {
/*    */                 throw ERROR_NOT_ON_TEAM.create();
/*    */               }
/*    */               
/*    */               List<ServerPlayer> $$4 = $$1.getServer().getPlayerList().getPlayers().stream().filter(()).toList();
/*    */               
/*    */               if (!$$4.isEmpty()) {
/*    */                 MessageArgument.resolveChatMessage($$0, "message", ());
/*    */               }
/*    */               
/*    */               return $$4.size();
/*    */             })));
/*    */     
/* 60 */     $$0.register((LiteralArgumentBuilder)Commands.literal("tm").redirect((CommandNode)$$1));
/*    */   }
/*    */   private static void sendMessage(CommandSourceStack $$0, Entity $$1, PlayerTeam $$2, List<ServerPlayer> $$3, PlayerChatMessage $$4) {
/*    */     int i;
/* 64 */     MutableComponent mutableComponent = $$2.getFormattedDisplayName().withStyle(SUGGEST_STYLE);
/* 65 */     ChatType.Bound $$6 = ChatType.bind(ChatType.TEAM_MSG_COMMAND_INCOMING, $$0).withTargetName((Component)mutableComponent);
/* 66 */     ChatType.Bound $$7 = ChatType.bind(ChatType.TEAM_MSG_COMMAND_OUTGOING, $$0).withTargetName((Component)mutableComponent);
/* 67 */     OutgoingChatMessage $$8 = OutgoingChatMessage.create($$4);
/*    */     
/* 69 */     boolean $$9 = false;
/*    */     
/* 71 */     for (ServerPlayer $$10 : $$3) {
/* 72 */       ChatType.Bound $$11 = ($$10 == $$1) ? $$7 : $$6;
/*    */       
/* 74 */       boolean $$12 = $$0.shouldFilterMessageTo($$10);
/* 75 */       $$10.sendChatMessage($$8, $$12, $$11);
/*    */       
/* 77 */       i = $$9 | (($$12 && $$4.isFullyFiltered()) ? 1 : 0);
/*    */     } 
/*    */     
/* 80 */     if (i != 0)
/* 81 */       $$0.sendSystemMessage(PlayerList.CHAT_FILTERED_FULL); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\TeamMsgCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */