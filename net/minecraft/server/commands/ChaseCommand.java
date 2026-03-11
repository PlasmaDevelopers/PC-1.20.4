/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.ImmutableBiMap;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.chase.ChaseClient;
/*     */ import net.minecraft.server.chase.ChaseServer;
/*     */ import net.minecraft.world.level.Level;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChaseCommand
/*     */ {
/*  37 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final String DEFAULT_CONNECT_HOST = "localhost";
/*     */   
/*     */   private static final String DEFAULT_BIND_ADDRESS = "0.0.0.0";
/*     */   
/*     */   private static final int DEFAULT_PORT = 10000;
/*     */   
/*     */   private static final int BROADCAST_INTERVAL_MS = 100;
/*  46 */   public static BiMap<String, ResourceKey<Level>> DIMENSION_NAMES = (BiMap<String, ResourceKey<Level>>)ImmutableBiMap.of("o", Level.OVERWORLD, "n", Level.NETHER, "e", Level.END);
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static ChaseServer chaseServer;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static ChaseClient chaseClient;
/*     */ 
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  58 */     $$0.register(
/*  59 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("chase")
/*  60 */         .then((
/*  61 */           (LiteralArgumentBuilder)Commands.literal("follow")
/*  62 */           .then(((RequiredArgumentBuilder)Commands.argument("host", (ArgumentType)StringArgumentType.string())
/*  63 */             .executes($$0 -> follow((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "host"), 10000)))
/*  64 */             .then(Commands.argument("port", (ArgumentType)IntegerArgumentType.integer(1, 65535))
/*  65 */               .executes($$0 -> follow((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "host"), IntegerArgumentType.getInteger($$0, "port"))))))
/*     */           
/*  67 */           .executes($$0 -> follow((CommandSourceStack)$$0.getSource(), "localhost", 10000))))
/*     */         
/*  69 */         .then((
/*  70 */           (LiteralArgumentBuilder)Commands.literal("lead")
/*  71 */           .then(((RequiredArgumentBuilder)Commands.argument("bind_address", (ArgumentType)StringArgumentType.string())
/*  72 */             .executes($$0 -> lead((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "bind_address"), 10000)))
/*  73 */             .then(Commands.argument("port", (ArgumentType)IntegerArgumentType.integer(1024, 65535))
/*  74 */               .executes($$0 -> lead((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "bind_address"), IntegerArgumentType.getInteger($$0, "port"))))))
/*     */           
/*  76 */           .executes($$0 -> lead((CommandSourceStack)$$0.getSource(), "0.0.0.0", 10000))))
/*     */         
/*  78 */         .then(
/*  79 */           Commands.literal("stop")
/*  80 */           .executes($$0 -> stop((CommandSourceStack)$$0.getSource()))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int stop(CommandSourceStack $$0) {
/*  86 */     if (chaseClient != null) {
/*  87 */       chaseClient.stop();
/*  88 */       $$0.sendSuccess(() -> Component.literal("You have now stopped chasing"), false);
/*  89 */       chaseClient = null;
/*     */     } 
/*  91 */     if (chaseServer != null) {
/*  92 */       chaseServer.stop();
/*  93 */       $$0.sendSuccess(() -> Component.literal("You are no longer being chased"), false);
/*  94 */       chaseServer = null;
/*     */     } 
/*  96 */     return 0;
/*     */   }
/*     */   
/*     */   private static boolean alreadyRunning(CommandSourceStack $$0) {
/* 100 */     if (chaseServer != null) {
/* 101 */       $$0.sendFailure((Component)Component.literal("Chase server is already running. Stop it using /chase stop"));
/* 102 */       return true;
/*     */     } 
/* 104 */     if (chaseClient != null) {
/* 105 */       $$0.sendFailure((Component)Component.literal("You are already chasing someone. Stop it using /chase stop"));
/* 106 */       return true;
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   private static int lead(CommandSourceStack $$0, String $$1, int $$2) {
/* 112 */     if (alreadyRunning($$0)) {
/* 113 */       return 0;
/*     */     }
/*     */     
/* 116 */     chaseServer = new ChaseServer($$1, $$2, $$0.getServer().getPlayerList(), 100);
/*     */     try {
/* 118 */       chaseServer.start();
/* 119 */       $$0.sendSuccess(() -> Component.literal("Chase server is now running on port " + $$0 + ". Clients can follow you using /chase follow <ip> <port>"), false);
/* 120 */     } catch (IOException $$3) {
/* 121 */       LOGGER.error("Failed to start chase server", $$3);
/* 122 */       $$0.sendFailure((Component)Component.literal("Failed to start chase server on port " + $$2));
/* 123 */       chaseServer = null;
/*     */     } 
/* 125 */     return 0;
/*     */   }
/*     */   
/*     */   private static int follow(CommandSourceStack $$0, String $$1, int $$2) {
/* 129 */     if (alreadyRunning($$0)) {
/* 130 */       return 0;
/*     */     }
/*     */     
/* 133 */     chaseClient = new ChaseClient($$1, $$2, $$0.getServer());
/* 134 */     chaseClient.start();
/* 135 */     $$0.sendSuccess(() -> Component.literal("You are now chasing " + $$0 + ":" + $$1 + ". If that server does '/chase lead' then you will automatically go to the same position. Use '/chase stop' to stop chasing."), false);
/* 136 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ChaseCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */