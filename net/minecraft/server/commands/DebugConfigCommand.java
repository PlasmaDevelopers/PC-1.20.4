/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.UuidArgument;
/*    */ import net.minecraft.network.Connection;
/*    */ import net.minecraft.network.PacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
/*    */ 
/*    */ public class DebugConfigCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 26 */     $$0.register(
/* 27 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("debugconfig")
/* 28 */         .requires($$0 -> $$0.hasPermission(3)))
/* 29 */         .then(
/* 30 */           Commands.literal("config")
/* 31 */           .then(
/* 32 */             Commands.argument("target", (ArgumentType)EntityArgument.player())
/* 33 */             .executes($$0 -> config((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayer($$0, "target"))))))
/*    */ 
/*    */         
/* 36 */         .then(
/* 37 */           Commands.literal("unconfig")
/* 38 */           .then(
/* 39 */             Commands.argument("target", (ArgumentType)UuidArgument.uuid())
/* 40 */             .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(getUuidsInConfig(((CommandSourceStack)$$0.getSource()).getServer()), $$1))
/* 41 */             .executes($$0 -> unconfig((CommandSourceStack)$$0.getSource(), UuidArgument.getUuid($$0, "target"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static Iterable<String> getUuidsInConfig(MinecraftServer $$0) {
/* 48 */     Set<String> $$1 = new HashSet<>();
/* 49 */     for (Connection $$2 : $$0.getConnection().getConnections()) {
/* 50 */       PacketListener packetListener = $$2.getPacketListener(); if (packetListener instanceof ServerConfigurationPacketListenerImpl) { ServerConfigurationPacketListenerImpl $$3 = (ServerConfigurationPacketListenerImpl)packetListener;
/* 51 */         $$1.add($$3.getOwner().getId().toString()); }
/*    */     
/*    */     } 
/* 54 */     return $$1;
/*    */   }
/*    */   
/*    */   private static int config(CommandSourceStack $$0, ServerPlayer $$1) {
/* 58 */     GameProfile $$2 = $$1.getGameProfile();
/* 59 */     $$1.connection.switchToConfig();
/* 60 */     $$0.sendSuccess(() -> Component.literal("Switched player " + $$0.getName() + "(" + $$0.getId() + ") to config mode"), false);
/* 61 */     return 1;
/*    */   }
/*    */   
/*    */   private static int unconfig(CommandSourceStack $$0, UUID $$1) {
/* 65 */     for (Connection $$2 : $$0.getServer().getConnection().getConnections()) {
/* 66 */       PacketListener packetListener = $$2.getPacketListener(); if (packetListener instanceof ServerConfigurationPacketListenerImpl) { ServerConfigurationPacketListenerImpl $$3 = (ServerConfigurationPacketListenerImpl)packetListener;
/* 67 */         if ($$3.getOwner().getId().equals($$1)) {
/* 68 */           $$3.returnToWorld();
/*    */         } }
/*    */     
/*    */     } 
/* 72 */     $$0.sendFailure((Component)Component.literal("Can't find player to unconfig"));
/* 73 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DebugConfigCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */