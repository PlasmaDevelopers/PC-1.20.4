/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.UuidArgument;
/*    */ import net.minecraft.network.Connection;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.common.ClientboundResourcePackPopPacket;
/*    */ import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;
/*    */ 
/*    */ public class ServerPackCommand
/*    */ {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 24 */     $$0.register(
/* 25 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("serverpack")
/* 26 */         .requires($$0 -> $$0.hasPermission(2)))
/* 27 */         .then(
/* 28 */           Commands.literal("push")
/* 29 */           .then((
/* 30 */             (RequiredArgumentBuilder)Commands.argument("url", (ArgumentType)StringArgumentType.string())
/* 31 */             .then((
/* 32 */               (RequiredArgumentBuilder)Commands.argument("uuid", (ArgumentType)UuidArgument.uuid())
/* 33 */               .then(
/* 34 */                 Commands.argument("hash", (ArgumentType)StringArgumentType.word())
/* 35 */                 .executes($$0 -> pushPack((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "url"), Optional.of(UuidArgument.getUuid($$0, "uuid")), Optional.of(StringArgumentType.getString($$0, "hash"))))))
/*    */               
/* 37 */               .executes($$0 -> pushPack((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "url"), Optional.of(UuidArgument.getUuid($$0, "uuid")), Optional.empty()))))
/*    */             
/* 39 */             .executes($$0 -> pushPack((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "url"), Optional.empty(), Optional.empty())))))
/*    */ 
/*    */         
/* 42 */         .then(
/* 43 */           Commands.literal("pop")
/* 44 */           .then(
/* 45 */             Commands.argument("uuid", (ArgumentType)UuidArgument.uuid())
/* 46 */             .executes($$0 -> popPack((CommandSourceStack)$$0.getSource(), UuidArgument.getUuid($$0, "uuid"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void sendToAllConnections(CommandSourceStack $$0, Packet<?> $$1) {
/* 54 */     $$0.getServer().getConnection().getConnections().forEach($$1 -> $$1.send($$0));
/*    */   }
/*    */   
/*    */   private static int pushPack(CommandSourceStack $$0, String $$1, Optional<UUID> $$2, Optional<String> $$3) {
/* 58 */     UUID $$4 = $$2.orElseGet(() -> UUID.nameUUIDFromBytes($$0.getBytes(StandardCharsets.UTF_8)));
/* 59 */     String $$5 = $$3.orElse("");
/*    */     
/* 61 */     ClientboundResourcePackPushPacket $$6 = new ClientboundResourcePackPushPacket($$4, $$1, $$5, false, null);
/* 62 */     sendToAllConnections($$0, (Packet<?>)$$6);
/* 63 */     return 0;
/*    */   }
/*    */   
/*    */   private static int popPack(CommandSourceStack $$0, UUID $$1) {
/* 67 */     ClientboundResourcePackPopPacket $$2 = new ClientboundResourcePackPopPacket(Optional.of($$1));
/* 68 */     sendToAllConnections($$0, (Packet<?>)$$2);
/* 69 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ServerPackCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */