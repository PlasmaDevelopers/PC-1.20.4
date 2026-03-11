/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*    */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*    */ import net.minecraft.commands.synchronization.SuggestionProviders;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ 
/*    */ 
/*    */ public class StopSoundCommand
/*    */ {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 29 */     RequiredArgumentBuilder<CommandSourceStack, EntitySelector> $$1 = (RequiredArgumentBuilder<CommandSourceStack, EntitySelector>)((RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players()).executes($$0 -> stopSound((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), null, null))).then(
/* 30 */         Commands.literal("*")
/* 31 */         .then(
/* 32 */           Commands.argument("sound", (ArgumentType)ResourceLocationArgument.id())
/* 33 */           .suggests(SuggestionProviders.AVAILABLE_SOUNDS)
/* 34 */           .executes($$0 -> stopSound((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), null, ResourceLocationArgument.getId($$0, "sound")))));
/*    */ 
/*    */ 
/*    */     
/* 38 */     for (SoundSource $$2 : SoundSource.values()) {
/* 39 */       $$1.then((
/* 40 */           (LiteralArgumentBuilder)Commands.literal($$2.getName())
/* 41 */           .executes($$1 -> stopSound((CommandSourceStack)$$1.getSource(), EntityArgument.getPlayers($$1, "targets"), $$0, null)))
/* 42 */           .then(
/* 43 */             Commands.argument("sound", (ArgumentType)ResourceLocationArgument.id())
/* 44 */             .suggests(SuggestionProviders.AVAILABLE_SOUNDS)
/* 45 */             .executes($$1 -> stopSound((CommandSourceStack)$$1.getSource(), EntityArgument.getPlayers($$1, "targets"), $$0, ResourceLocationArgument.getId($$1, "sound")))));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 50 */     $$0.register(
/* 51 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("stopsound")
/* 52 */         .requires($$0 -> $$0.hasPermission(2)))
/* 53 */         .then((ArgumentBuilder)$$1));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int stopSound(CommandSourceStack $$0, Collection<ServerPlayer> $$1, @Nullable SoundSource $$2, @Nullable ResourceLocation $$3) {
/* 60 */     ClientboundStopSoundPacket $$4 = new ClientboundStopSoundPacket($$3, $$2);
/* 61 */     for (ServerPlayer $$5 : $$1) {
/* 62 */       $$5.connection.send((Packet)$$4);
/*    */     }
/*    */     
/* 65 */     if ($$2 != null) {
/* 66 */       if ($$3 != null) {
/* 67 */         $$0.sendSuccess(() -> Component.translatable("commands.stopsound.success.source.sound", new Object[] { Component.translationArg($$0), $$1.getName() }), true);
/*    */       } else {
/* 69 */         $$0.sendSuccess(() -> Component.translatable("commands.stopsound.success.source.any", new Object[] { $$0.getName() }), true);
/*    */       }
/*    */     
/* 72 */     } else if ($$3 != null) {
/* 73 */       $$0.sendSuccess(() -> Component.translatable("commands.stopsound.success.sourceless.sound", new Object[] { Component.translationArg($$0) }), true);
/*    */     } else {
/* 75 */       $$0.sendSuccess(() -> Component.translatable("commands.stopsound.success.sourceless.any"), true);
/*    */     } 
/*    */ 
/*    */     
/* 79 */     return $$1.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\StopSoundCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */