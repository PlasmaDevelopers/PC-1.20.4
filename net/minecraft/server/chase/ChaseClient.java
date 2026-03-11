/*     */ package net.minecraft.server.chase;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.StringReader;
/*     */ import java.net.Socket;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Optional;
/*     */ import java.util.Scanner;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.commands.ChaseCommand;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChaseClient
/*     */ {
/*  39 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int RECONNECT_INTERVAL_SECONDS = 5;
/*     */   
/*     */   private final String serverHost;
/*     */   
/*     */   private final int serverPort;
/*     */   private final MinecraftServer server;
/*     */   private volatile boolean wantsToRun;
/*     */   @Nullable
/*     */   private Socket socket;
/*     */   @Nullable
/*     */   private Thread thread;
/*     */   
/*     */   public ChaseClient(String $$0, int $$1, MinecraftServer $$2) {
/*  54 */     this.serverHost = $$0;
/*  55 */     this.serverPort = $$1;
/*  56 */     this.server = $$2;
/*     */   }
/*     */   
/*     */   public void start() {
/*  60 */     if (this.thread != null && this.thread.isAlive()) {
/*  61 */       LOGGER.warn("Remote control client was asked to start, but it is already running. Will ignore.");
/*     */     }
/*  63 */     this.wantsToRun = true;
/*  64 */     this.thread = new Thread(this::run, "chase-client");
/*  65 */     this.thread.setDaemon(true);
/*  66 */     this.thread.start();
/*     */   }
/*     */   
/*     */   public void stop() {
/*  70 */     this.wantsToRun = false;
/*     */     
/*  72 */     IOUtils.closeQuietly(this.socket);
/*  73 */     this.socket = null;
/*  74 */     this.thread = null;
/*     */   }
/*     */   
/*     */   public void run() {
/*  78 */     String $$0 = this.serverHost + ":" + this.serverHost;
/*  79 */     while (this.wantsToRun) {
/*     */       try {
/*  81 */         LOGGER.info("Connecting to remote control server {}", $$0);
/*  82 */         this.socket = new Socket(this.serverHost, this.serverPort);
/*  83 */         LOGGER.info("Connected to remote control server! Will continuously execute the command broadcasted by that server.");
/*     */         
/*  85 */         try { BufferedReader $$1 = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), Charsets.US_ASCII)); 
/*  86 */           try { while (this.wantsToRun) {
/*  87 */               String $$2 = $$1.readLine();
/*  88 */               if ($$2 == null) {
/*     */                 
/*  90 */                 LOGGER.warn("Lost connection to remote control server {}. Will retry in {}s.", $$0, Integer.valueOf(5));
/*     */                 break;
/*     */               } 
/*  93 */               handleMessage($$2);
/*     */             } 
/*  95 */             $$1.close(); } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$3)
/*  96 */         { LOGGER.warn("Lost connection to remote control server {}. Will retry in {}s.", $$0, Integer.valueOf(5)); }
/*     */ 
/*     */       
/*  99 */       } catch (IOException $$4) {
/* 100 */         LOGGER.warn("Failed to connect to remote control server {}. Will retry in {}s.", $$0, Integer.valueOf(5));
/*     */       } 
/*     */       
/* 103 */       if (this.wantsToRun) {
/*     */         try {
/* 105 */           Thread.sleep(5000L);
/* 106 */         } catch (InterruptedException interruptedException) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleMessage(String $$0) {
/*     */     
/* 113 */     try { Scanner $$1 = new Scanner(new StringReader($$0)); 
/* 114 */       try { $$1.useLocale(Locale.ROOT);
/* 115 */         String $$2 = $$1.next();
/* 116 */         if ("t".equals($$2)) {
/* 117 */           handleTeleport($$1);
/*     */         } else {
/* 119 */           LOGGER.warn("Unknown message type '{}'", $$2);
/*     */         } 
/* 121 */         $$1.close(); } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (NoSuchElementException $$3)
/*     */     
/* 123 */     { LOGGER.warn("Could not parse message '{}', ignoring", $$0); }
/*     */   
/*     */   }
/*     */   
/*     */   private void handleTeleport(Scanner $$0) {
/* 128 */     parseTarget($$0).ifPresent($$0 -> executeCommand(String.format(Locale.ROOT, "execute in %s run tp @s %.3f %.3f %.3f %.3f %.3f", new Object[] { $$0.level.location(), Double.valueOf($$0.pos.x), Double.valueOf($$0.pos.y), Double.valueOf($$0.pos.z), Float.valueOf($$0.rot.y), Float.valueOf($$0.rot.x) })));
/*     */   }
/*     */   static final class TeleportTarget extends Record { final ResourceKey<Level> level; final Vec3 pos;
/*     */     final Vec2 rot;
/*     */     
/* 133 */     TeleportTarget(ResourceKey<Level> $$0, Vec3 $$1, Vec2 $$2) { this.level = $$0; this.pos = $$1; this.rot = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/chase/ChaseClient$TeleportTarget;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #133	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 133 */       //   0	7	0	this	Lnet/minecraft/server/chase/ChaseClient$TeleportTarget; } public ResourceKey<Level> level() { return this.level; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/chase/ChaseClient$TeleportTarget;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #133	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 133 */       //   0	7	0	this	Lnet/minecraft/server/chase/ChaseClient$TeleportTarget; } public Vec3 pos() { return this.pos; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/chase/ChaseClient$TeleportTarget;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #133	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/chase/ChaseClient$TeleportTarget;
/* 133 */       //   0	8	1	$$0	Ljava/lang/Object; } public Vec2 rot() { return this.rot; }
/*     */      }
/*     */   private Optional<TeleportTarget> parseTarget(Scanner $$0) {
/* 136 */     ResourceKey<Level> $$1 = (ResourceKey<Level>)ChaseCommand.DIMENSION_NAMES.get($$0.next());
/* 137 */     if ($$1 == null) {
/* 138 */       return Optional.empty();
/*     */     }
/*     */     
/* 141 */     float $$2 = $$0.nextFloat();
/* 142 */     float $$3 = $$0.nextFloat();
/* 143 */     float $$4 = $$0.nextFloat();
/* 144 */     float $$5 = $$0.nextFloat();
/* 145 */     float $$6 = $$0.nextFloat();
/* 146 */     return Optional.of(new TeleportTarget($$1, new Vec3($$2, $$3, $$4), new Vec2($$6, $$5)));
/*     */   }
/*     */   
/*     */   private void executeCommand(String $$0) {
/* 150 */     this.server.execute(() -> {
/*     */           List<ServerPlayer> $$1 = this.server.getPlayerList().getPlayers();
/*     */           if ($$1.isEmpty())
/*     */             return; 
/*     */           ServerPlayer $$2 = $$1.get(0);
/*     */           ServerLevel $$3 = this.server.overworld();
/*     */           CommandSourceStack $$4 = new CommandSourceStack((CommandSource)$$2, Vec3.atLowerCornerOf((Vec3i)$$3.getSharedSpawnPos()), Vec2.ZERO, $$3, 4, "", CommonComponents.EMPTY, this.server, (Entity)$$2);
/*     */           Commands $$5 = this.server.getCommands();
/*     */           $$5.performPrefixedCommand($$4, $$0);
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\chase\ChaseClient.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */