/*     */ package net.minecraft.server.rcon.thread;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.PortUnreachableException;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.server.ServerInterface;
/*     */ import net.minecraft.server.rcon.NetworkDataOutputStream;
/*     */ import net.minecraft.server.rcon.PktUtils;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class QueryThreadGs4
/*     */   extends GenericThread {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final String GAME_TYPE = "SMP";
/*     */   private static final String GAME_ID = "MINECRAFT";
/*     */   private static final long CHALLENGE_CHECK_INTERVAL = 30000L;
/*     */   private static final long RESPONSE_CACHE_TIME = 5000L;
/*     */   private long lastChallengeCheck;
/*     */   private final int port;
/*     */   private final int serverPort;
/*     */   private final int maxPlayers;
/*     */   private final String serverName;
/*     */   private final String worldName;
/*     */   private DatagramSocket socket;
/*  39 */   private final byte[] buffer = new byte[1460];
/*     */   private String hostIp;
/*     */   private String serverIp;
/*     */   private final Map<SocketAddress, RequestChallenge> validChallenges;
/*     */   private final NetworkDataOutputStream rulesResponse;
/*     */   private long lastRulesResponse;
/*     */   private final ServerInterface serverInterface;
/*     */   
/*     */   private QueryThreadGs4(ServerInterface $$0, int $$1) {
/*  48 */     super("Query Listener");
/*  49 */     this.serverInterface = $$0;
/*     */     
/*  51 */     this.port = $$1;
/*  52 */     this.serverIp = $$0.getServerIp();
/*  53 */     this.serverPort = $$0.getServerPort();
/*  54 */     this.serverName = $$0.getServerName();
/*  55 */     this.maxPlayers = $$0.getMaxPlayers();
/*  56 */     this.worldName = $$0.getLevelIdName();
/*     */ 
/*     */     
/*  59 */     this.lastRulesResponse = 0L;
/*     */     
/*  61 */     this.hostIp = "0.0.0.0";
/*     */ 
/*     */     
/*  64 */     if (this.serverIp.isEmpty() || this.hostIp.equals(this.serverIp)) {
/*     */       
/*  66 */       this.serverIp = "0.0.0.0";
/*     */       try {
/*  68 */         InetAddress $$2 = InetAddress.getLocalHost();
/*  69 */         this.hostIp = $$2.getHostAddress();
/*  70 */       } catch (UnknownHostException $$3) {
/*  71 */         LOGGER.warn("Unable to determine local host IP, please set server-ip in server.properties", $$3);
/*     */       } 
/*     */     } else {
/*  74 */       this.hostIp = this.serverIp;
/*     */     } 
/*     */ 
/*     */     
/*  78 */     this.rulesResponse = new NetworkDataOutputStream(1460);
/*  79 */     this.validChallenges = Maps.newHashMap();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static QueryThreadGs4 create(ServerInterface $$0) {
/*  84 */     int $$1 = ($$0.getProperties()).queryPort;
/*  85 */     if (0 >= $$1 || 65535 < $$1) {
/*  86 */       LOGGER.warn("Invalid query port {} found in server.properties (queries disabled)", Integer.valueOf($$1));
/*  87 */       return null;
/*     */     } 
/*     */     
/*  90 */     QueryThreadGs4 $$2 = new QueryThreadGs4($$0, $$1);
/*  91 */     if (!$$2.start()) {
/*  92 */       return null;
/*     */     }
/*  94 */     return $$2;
/*     */   }
/*     */   
/*     */   private void sendTo(byte[] $$0, DatagramPacket $$1) throws IOException {
/*  98 */     this.socket.send(new DatagramPacket($$0, $$0.length, $$1.getSocketAddress()));
/*     */   }
/*     */   private boolean processPacket(DatagramPacket $$0) throws IOException {
/*     */     NetworkDataOutputStream $$4;
/* 102 */     byte[] $$1 = $$0.getData();
/* 103 */     int $$2 = $$0.getLength();
/* 104 */     SocketAddress $$3 = $$0.getSocketAddress();
/* 105 */     LOGGER.debug("Packet len {} [{}]", Integer.valueOf($$2), $$3);
/* 106 */     if (3 > $$2 || -2 != $$1[0] || -3 != $$1[1]) {
/*     */       
/* 108 */       LOGGER.debug("Invalid packet [{}]", $$3);
/* 109 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 113 */     LOGGER.debug("Packet '{}' [{}]", PktUtils.toHexString($$1[2]), $$3);
/* 114 */     switch ($$1[2]) {
/*     */       
/*     */       case 9:
/* 117 */         sendChallenge($$0);
/* 118 */         LOGGER.debug("Challenge [{}]", $$3);
/* 119 */         return true;
/*     */ 
/*     */       
/*     */       case 0:
/* 123 */         if (!validChallenge($$0).booleanValue()) {
/* 124 */           LOGGER.debug("Invalid challenge [{}]", $$3);
/* 125 */           return false;
/*     */         } 
/*     */         
/* 128 */         if (15 == $$2) {
/*     */           
/* 130 */           sendTo(buildRuleResponse($$0), $$0);
/* 131 */           LOGGER.debug("Rules [{}]", $$3);
/*     */           break;
/*     */         } 
/* 134 */         $$4 = new NetworkDataOutputStream(1460);
/* 135 */         $$4.write(0);
/* 136 */         $$4.writeBytes(getIdentBytes($$0.getSocketAddress()));
/* 137 */         $$4.writeString(this.serverName);
/* 138 */         $$4.writeString("SMP");
/* 139 */         $$4.writeString(this.worldName);
/* 140 */         $$4.writeString(Integer.toString(this.serverInterface.getPlayerCount()));
/* 141 */         $$4.writeString(Integer.toString(this.maxPlayers));
/* 142 */         $$4.writeShort((short)this.serverPort);
/* 143 */         $$4.writeString(this.hostIp);
/*     */         
/* 145 */         sendTo($$4.toByteArray(), $$0);
/* 146 */         LOGGER.debug("Status [{}]", $$3);
/*     */         break;
/*     */     } 
/*     */     
/* 150 */     return true;
/*     */   }
/*     */   
/*     */   private byte[] buildRuleResponse(DatagramPacket $$0) throws IOException {
/* 154 */     long $$1 = Util.getMillis();
/* 155 */     if ($$1 < this.lastRulesResponse + 5000L) {
/*     */       
/* 157 */       byte[] $$2 = this.rulesResponse.toByteArray();
/* 158 */       byte[] $$3 = getIdentBytes($$0.getSocketAddress());
/* 159 */       $$2[1] = $$3[0];
/* 160 */       $$2[2] = $$3[1];
/* 161 */       $$2[3] = $$3[2];
/* 162 */       $$2[4] = $$3[3];
/*     */       
/* 164 */       return $$2;
/*     */     } 
/*     */     
/* 167 */     this.lastRulesResponse = $$1;
/*     */     
/* 169 */     this.rulesResponse.reset();
/* 170 */     this.rulesResponse.write(0);
/* 171 */     this.rulesResponse.writeBytes(getIdentBytes($$0.getSocketAddress()));
/* 172 */     this.rulesResponse.writeString("splitnum");
/* 173 */     this.rulesResponse.write(128);
/* 174 */     this.rulesResponse.write(0);
/*     */ 
/*     */     
/* 177 */     this.rulesResponse.writeString("hostname");
/* 178 */     this.rulesResponse.writeString(this.serverName);
/* 179 */     this.rulesResponse.writeString("gametype");
/* 180 */     this.rulesResponse.writeString("SMP");
/* 181 */     this.rulesResponse.writeString("game_id");
/* 182 */     this.rulesResponse.writeString("MINECRAFT");
/* 183 */     this.rulesResponse.writeString("version");
/* 184 */     this.rulesResponse.writeString(this.serverInterface.getServerVersion());
/* 185 */     this.rulesResponse.writeString("plugins");
/* 186 */     this.rulesResponse.writeString(this.serverInterface.getPluginNames());
/* 187 */     this.rulesResponse.writeString("map");
/* 188 */     this.rulesResponse.writeString(this.worldName);
/* 189 */     this.rulesResponse.writeString("numplayers");
/* 190 */     this.rulesResponse.writeString("" + this.serverInterface.getPlayerCount());
/* 191 */     this.rulesResponse.writeString("maxplayers");
/* 192 */     this.rulesResponse.writeString("" + this.maxPlayers);
/* 193 */     this.rulesResponse.writeString("hostport");
/* 194 */     this.rulesResponse.writeString("" + this.serverPort);
/* 195 */     this.rulesResponse.writeString("hostip");
/* 196 */     this.rulesResponse.writeString(this.hostIp);
/* 197 */     this.rulesResponse.write(0);
/* 198 */     this.rulesResponse.write(1);
/*     */ 
/*     */ 
/*     */     
/* 202 */     this.rulesResponse.writeString("player_");
/* 203 */     this.rulesResponse.write(0);
/*     */     
/* 205 */     String[] $$4 = this.serverInterface.getPlayerNames();
/* 206 */     for (String $$5 : $$4) {
/* 207 */       this.rulesResponse.writeString($$5);
/*     */     }
/* 209 */     this.rulesResponse.write(0);
/*     */     
/* 211 */     return this.rulesResponse.toByteArray();
/*     */   }
/*     */   
/*     */   private byte[] getIdentBytes(SocketAddress $$0) {
/* 215 */     return ((RequestChallenge)this.validChallenges.get($$0)).getIdentBytes();
/*     */   }
/*     */   
/*     */   private Boolean validChallenge(DatagramPacket $$0) {
/* 219 */     SocketAddress $$1 = $$0.getSocketAddress();
/* 220 */     if (!this.validChallenges.containsKey($$1))
/*     */     {
/* 222 */       return Boolean.valueOf(false);
/*     */     }
/*     */     
/* 225 */     byte[] $$2 = $$0.getData();
/* 226 */     return Boolean.valueOf((((RequestChallenge)this.validChallenges.get($$1)).getChallenge() == PktUtils.intFromNetworkByteArray($$2, 7, $$0.getLength())));
/*     */   }
/*     */   
/*     */   private void sendChallenge(DatagramPacket $$0) throws IOException {
/* 230 */     RequestChallenge $$1 = new RequestChallenge($$0);
/* 231 */     this.validChallenges.put($$0.getSocketAddress(), $$1);
/*     */     
/* 233 */     sendTo($$1.getChallengeBytes(), $$0);
/*     */   }
/*     */   
/*     */   private void pruneChallenges() {
/* 237 */     if (!this.running) {
/*     */       return;
/*     */     }
/*     */     
/* 241 */     long $$0 = Util.getMillis();
/* 242 */     if ($$0 < this.lastChallengeCheck + 30000L) {
/*     */       return;
/*     */     }
/* 245 */     this.lastChallengeCheck = $$0;
/*     */     
/* 247 */     this.validChallenges.values().removeIf($$1 -> $$1.before($$0).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 252 */     LOGGER.info("Query running on {}:{}", this.serverIp, Integer.valueOf(this.port));
/* 253 */     this.lastChallengeCheck = Util.getMillis();
/* 254 */     DatagramPacket $$0 = new DatagramPacket(this.buffer, this.buffer.length);
/*     */     
/*     */     try {
/* 257 */       while (this.running) {
/*     */         try {
/* 259 */           this.socket.receive($$0);
/*     */ 
/*     */           
/* 262 */           pruneChallenges();
/*     */ 
/*     */           
/* 265 */           processPacket($$0);
/* 266 */         } catch (SocketTimeoutException $$1) {
/*     */           
/* 268 */           pruneChallenges();
/* 269 */         } catch (PortUnreachableException portUnreachableException) {
/*     */         
/* 271 */         } catch (IOException $$2) {
/*     */           
/* 273 */           recoverSocketError($$2);
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 277 */       LOGGER.debug("closeSocket: {}:{}", this.serverIp, Integer.valueOf(this.port));
/* 278 */       this.socket.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean start() {
/* 284 */     if (this.running) {
/* 285 */       return true;
/*     */     }
/*     */     
/* 288 */     if (!initSocket()) {
/* 289 */       return false;
/*     */     }
/*     */     
/* 292 */     return super.start();
/*     */   }
/*     */   
/*     */   private void recoverSocketError(Exception $$0) {
/* 296 */     if (!this.running) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 301 */     LOGGER.warn("Unexpected exception", $$0);
/*     */ 
/*     */     
/* 304 */     if (!initSocket()) {
/* 305 */       LOGGER.error("Failed to recover from exception, shutting down!");
/* 306 */       this.running = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean initSocket() {
/*     */     try {
/* 312 */       this.socket = new DatagramSocket(this.port, InetAddress.getByName(this.serverIp));
/* 313 */       this.socket.setSoTimeout(500);
/* 314 */       return true;
/* 315 */     } catch (Exception $$0) {
/* 316 */       LOGGER.warn("Unable to initialise query system on {}:{}", new Object[] { this.serverIp, Integer.valueOf(this.port), $$0 });
/*     */       
/* 318 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RequestChallenge
/*     */   {
/* 329 */     private final long time = (new Date()).getTime(); private final int challenge; private final byte[] identBytes; public RequestChallenge(DatagramPacket $$0) {
/* 330 */       byte[] $$1 = $$0.getData();
/* 331 */       this.identBytes = new byte[4];
/* 332 */       this.identBytes[0] = $$1[3];
/* 333 */       this.identBytes[1] = $$1[4];
/* 334 */       this.identBytes[2] = $$1[5];
/* 335 */       this.identBytes[3] = $$1[6];
/* 336 */       this.ident = new String(this.identBytes, StandardCharsets.UTF_8);
/* 337 */       this.challenge = RandomSource.create().nextInt(16777216);
/* 338 */       this.challengeBytes = String.format(Locale.ROOT, "\t%s%d\000", new Object[] { this.ident, Integer.valueOf(this.challenge) }).getBytes(StandardCharsets.UTF_8);
/*     */     }
/*     */     private final byte[] challengeBytes; private final String ident;
/*     */     public Boolean before(long $$0) {
/* 342 */       return Boolean.valueOf((this.time < $$0));
/*     */     }
/*     */     
/*     */     public int getChallenge() {
/* 346 */       return this.challenge;
/*     */     }
/*     */     
/*     */     public byte[] getChallengeBytes() {
/* 350 */       return this.challengeBytes;
/*     */     }
/*     */     
/*     */     public byte[] getIdentBytes() {
/* 354 */       return this.identBytes;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getIdent() {
/* 359 */       return this.ident;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\rcon\thread\QueryThreadGs4.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */