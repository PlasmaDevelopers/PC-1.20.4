/*     */ package net.minecraft.server.network;
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.internal.Streams;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Base64;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.network.chat.FilterMask;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.thread.ProcessorMailbox;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class TextFilterClient implements AutoCloseable {
/*  39 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  40 */   private static final AtomicInteger WORKER_COUNT = new AtomicInteger(1); private static final ThreadFactory THREAD_FACTORY; private static final String DEFAULT_ENDPOINT = "v1/chat"; private final URL chatEndpoint; private final MessageEncoder chatEncoder; final URL joinEndpoint; final JoinOrLeaveEncoder joinEncoder; final URL leaveEndpoint; final JoinOrLeaveEncoder leaveEncoder; private final String authKey; final IgnoreStrategy chatIgnoreStrategy; final ExecutorService workerPool; static {
/*  41 */     THREAD_FACTORY = ($$0 -> {
/*     */         Thread $$1 = new Thread($$0);
/*     */         $$1.setName("Chat-Filter-Worker-" + WORKER_COUNT.getAndIncrement());
/*     */         return $$1;
/*     */       });
/*     */   }
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
/*     */ 
/*     */   
/*     */   private TextFilterClient(URL $$0, MessageEncoder $$1, URL $$2, JoinOrLeaveEncoder $$3, URL $$4, JoinOrLeaveEncoder $$5, String $$6, IgnoreStrategy $$7, int $$8) {
/*  72 */     this.authKey = $$6;
/*  73 */     this.chatIgnoreStrategy = $$7;
/*     */     
/*  75 */     this.chatEndpoint = $$0;
/*  76 */     this.chatEncoder = $$1;
/*  77 */     this.joinEndpoint = $$2;
/*  78 */     this.joinEncoder = $$3;
/*  79 */     this.leaveEndpoint = $$4;
/*  80 */     this.leaveEncoder = $$5;
/*     */     
/*  82 */     this.workerPool = Executors.newFixedThreadPool($$8, THREAD_FACTORY);
/*     */   }
/*     */   
/*     */   private static URL getEndpoint(URI $$0, @Nullable JsonObject $$1, String $$2, String $$3) throws MalformedURLException {
/*  86 */     String $$4 = getEndpointFromConfig($$1, $$2, $$3);
/*  87 */     return $$0.resolve("/" + $$4).toURL();
/*     */   }
/*     */   
/*     */   private static String getEndpointFromConfig(@Nullable JsonObject $$0, String $$1, String $$2) {
/*  91 */     return ($$0 != null) ? GsonHelper.getAsString($$0, $$1, $$2) : $$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static TextFilterClient createFromConfig(String $$0) {
/*  96 */     if (Strings.isNullOrEmpty($$0)) {
/*  97 */       return null;
/*     */     }
/*     */     try {
/*     */       MessageEncoder $$18;
/* 101 */       JsonObject $$1 = GsonHelper.parse($$0);
/*     */       
/* 103 */       URI $$2 = new URI(GsonHelper.getAsString($$1, "apiServer"));
/* 104 */       String $$3 = GsonHelper.getAsString($$1, "apiKey");
/* 105 */       if ($$3.isEmpty()) {
/* 106 */         throw new IllegalArgumentException("Missing API key");
/*     */       }
/* 108 */       int $$4 = GsonHelper.getAsInt($$1, "ruleId", 1);
/* 109 */       String $$5 = GsonHelper.getAsString($$1, "serverId", "");
/* 110 */       String $$6 = GsonHelper.getAsString($$1, "roomId", "Java:Chat");
/* 111 */       int $$7 = GsonHelper.getAsInt($$1, "hashesToDrop", -1);
/*     */       
/* 113 */       int $$8 = GsonHelper.getAsInt($$1, "maxConcurrentRequests", 7);
/*     */       
/* 115 */       JsonObject $$9 = GsonHelper.getAsJsonObject($$1, "endpoints", null);
/* 116 */       String $$10 = getEndpointFromConfig($$9, "chat", "v1/chat");
/* 117 */       boolean $$11 = $$10.equals("v1/chat");
/* 118 */       URL $$12 = $$2.resolve("/" + $$10).toURL();
/* 119 */       URL $$13 = getEndpoint($$2, $$9, "join", "v1/join");
/* 120 */       URL $$14 = getEndpoint($$2, $$9, "leave", "v1/leave");
/*     */       
/* 122 */       JoinOrLeaveEncoder $$15 = $$2 -> {
/*     */           JsonObject $$3 = new JsonObject();
/*     */           
/*     */           $$3.addProperty("server", $$0);
/*     */           
/*     */           $$3.addProperty("room", $$1);
/*     */           $$3.addProperty("user_id", $$2.getId().toString());
/*     */           $$3.addProperty("user_display_name", $$2.getName());
/*     */           return $$3;
/*     */         };
/* 132 */       if ($$11) {
/* 133 */         MessageEncoder $$16 = ($$3, $$4) -> {
/*     */             JsonObject $$5 = new JsonObject();
/*     */             $$5.addProperty("rule", Integer.valueOf($$0));
/*     */             $$5.addProperty("server", $$1);
/*     */             $$5.addProperty("room", $$2);
/*     */             $$5.addProperty("player", $$3.getId().toString());
/*     */             $$5.addProperty("player_display_name", $$3.getName());
/*     */             $$5.addProperty("text", $$4);
/*     */             $$5.addProperty("language", "*");
/*     */             return $$5;
/*     */           };
/*     */       } else {
/* 145 */         String $$17 = String.valueOf($$4);
/* 146 */         $$18 = (($$3, $$4) -> {
/*     */             JsonObject $$5 = new JsonObject();
/*     */             
/*     */             $$5.addProperty("rule_id", $$0);
/*     */             $$5.addProperty("category", $$1);
/*     */             $$5.addProperty("subcategory", $$2);
/*     */             $$5.addProperty("user_id", $$3.getId().toString());
/*     */             $$5.addProperty("user_display_name", $$3.getName());
/*     */             $$5.addProperty("text", $$4);
/*     */             $$5.addProperty("language", "*");
/*     */             return $$5;
/*     */           });
/*     */       } 
/* 159 */       IgnoreStrategy $$19 = IgnoreStrategy.select($$7);
/* 160 */       String $$20 = Base64.getEncoder().encodeToString($$3.getBytes(StandardCharsets.US_ASCII));
/* 161 */       return new TextFilterClient($$12, $$18, $$13, $$15, $$14, $$15, $$20, $$19, $$8);
/* 162 */     } catch (Exception $$21) {
/* 163 */       LOGGER.warn("Failed to parse chat filter config {}", $$0, $$21);
/*     */ 
/*     */       
/* 166 */       return null;
/*     */     } 
/*     */   }
/*     */   void processJoinOrLeave(GameProfile $$0, URL $$1, JoinOrLeaveEncoder $$2, Executor $$3) {
/* 170 */     $$3.execute(() -> {
/*     */           JsonObject $$3 = $$0.encode($$1);
/*     */           try {
/*     */             processRequest($$3, $$2);
/* 174 */           } catch (Exception $$4) {
/*     */             LOGGER.warn("Failed to send join/leave packet to {} for player {}", new Object[] { $$2, $$1, $$4 });
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   CompletableFuture<FilteredText> requestMessageProcessing(GameProfile $$0, String $$1, IgnoreStrategy $$2, Executor $$3) {
/* 181 */     if ($$1.isEmpty()) {
/* 182 */       return CompletableFuture.completedFuture(FilteredText.EMPTY);
/*     */     }
/* 184 */     return CompletableFuture.supplyAsync(() -> {
/*     */           JsonObject $$3 = this.chatEncoder.encode($$0, $$1);
/*     */           
/*     */           try {
/*     */             JsonObject $$4 = processRequestResponse($$3, this.chatEndpoint);
/*     */             boolean $$5 = GsonHelper.getAsBoolean($$4, "response", false);
/*     */             if ($$5) {
/*     */               return FilteredText.passThrough($$1);
/*     */             }
/*     */             String $$6 = GsonHelper.getAsString($$4, "hashed", null);
/*     */             if ($$6 == null) {
/*     */               return FilteredText.fullyFiltered($$1);
/*     */             }
/*     */             JsonArray $$7 = GsonHelper.getAsJsonArray($$4, "hashes");
/*     */             FilterMask $$8 = parseMask($$1, $$7, $$2);
/*     */             return new FilteredText($$1, $$8);
/* 200 */           } catch (Exception $$9) {
/*     */             LOGGER.warn("Failed to validate message '{}'", $$1, $$9);
/*     */             return FilteredText.fullyFiltered($$1);
/*     */           } 
/*     */         }$$3);
/*     */   }
/*     */   
/*     */   private FilterMask parseMask(String $$0, JsonArray $$1, IgnoreStrategy $$2) {
/* 208 */     if ($$1.isEmpty())
/* 209 */       return FilterMask.PASS_THROUGH; 
/* 210 */     if ($$2.shouldIgnore($$0, $$1.size())) {
/* 211 */       return FilterMask.FULLY_FILTERED;
/*     */     }
/* 213 */     FilterMask $$3 = new FilterMask($$0.length());
/* 214 */     for (int $$4 = 0; $$4 < $$1.size(); $$4++) {
/* 215 */       $$3.setFiltered($$1.get($$4).getAsInt());
/*     */     }
/* 217 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 222 */     this.workerPool.shutdownNow();
/*     */   }
/*     */   
/*     */   private void drainStream(InputStream $$0) throws IOException {
/* 226 */     byte[] $$1 = new byte[1024];
/* 227 */     while ($$0.read($$1) != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   private JsonObject processRequestResponse(JsonObject $$0, URL $$1) throws IOException {
/* 232 */     HttpURLConnection $$2 = makeRequest($$0, $$1);
/*     */     
/* 234 */     InputStream $$3 = $$2.getInputStream(); 
/* 235 */     try { if ($$2.getResponseCode() == 204)
/* 236 */       { JsonObject jsonObject = new JsonObject();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 243 */         if ($$3 != null) $$3.close();  return jsonObject; }  try { JsonObject jsonObject = Streams.parse(new JsonReader(new InputStreamReader($$3, StandardCharsets.UTF_8))).getAsJsonObject(); drainStream($$3); return jsonObject; } finally { drainStream($$3); }  } catch (Throwable throwable) { if ($$3 != null)
/*     */         try { $$3.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 247 */      } private void processRequest(JsonObject $$0, URL $$1) throws IOException { HttpURLConnection $$2 = makeRequest($$0, $$1);
/*     */     
/* 249 */     InputStream $$3 = $$2.getInputStream(); 
/* 250 */     try { drainStream($$3);
/* 251 */       if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/*     */         try { $$3.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 255 */      } private HttpURLConnection makeRequest(JsonObject $$0, URL $$1) throws IOException { HttpURLConnection $$2 = (HttpURLConnection)$$1.openConnection();
/* 256 */     $$2.setConnectTimeout(15000);
/* 257 */     $$2.setReadTimeout(2000);
/* 258 */     $$2.setUseCaches(false);
/* 259 */     $$2.setDoOutput(true);
/* 260 */     $$2.setDoInput(true);
/*     */     
/* 262 */     $$2.setRequestMethod("POST");
/* 263 */     $$2.setRequestProperty("Content-Type", "application/json; charset=utf-8");
/* 264 */     $$2.setRequestProperty("Accept", "application/json");
/* 265 */     $$2.setRequestProperty("Authorization", "Basic " + this.authKey);
/* 266 */     $$2.setRequestProperty("User-Agent", "Minecraft server" + SharedConstants.getCurrentVersion().getName());
/*     */     
/* 268 */     OutputStreamWriter $$3 = new OutputStreamWriter($$2.getOutputStream(), StandardCharsets.UTF_8); 
/* 269 */     try { JsonWriter $$4 = new JsonWriter($$3); 
/* 270 */       try { Streams.write((JsonElement)$$0, $$4);
/* 271 */         $$4.close(); } catch (Throwable throwable) { try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/* 272 */        $$3.close(); } catch (Throwable throwable) { try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 274 */      int $$5 = $$2.getResponseCode();
/* 275 */     if ($$5 < 200 || $$5 >= 300) {
/* 276 */       throw new RequestFailedException("" + $$5 + " " + $$5);
/*     */     }
/* 278 */     return $$2; }
/*     */ 
/*     */   
/*     */   public TextFilter createContext(GameProfile $$0) {
/* 282 */     return new PlayerContext($$0);
/*     */   }
/*     */   
/*     */   public static class RequestFailedException extends RuntimeException {
/*     */     RequestFailedException(String $$0) {
/* 287 */       super($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerContext implements TextFilter {
/*     */     private final GameProfile profile;
/*     */     private final Executor streamExecutor;
/*     */     
/*     */     PlayerContext(GameProfile $$0) {
/* 296 */       this.profile = $$0;
/* 297 */       ProcessorMailbox<Runnable> $$1 = ProcessorMailbox.create(TextFilterClient.this.workerPool, "chat stream for " + $$0.getName());
/* 298 */       Objects.requireNonNull($$1); this.streamExecutor = $$1::tell;
/*     */     }
/*     */ 
/*     */     
/*     */     public void join() {
/* 303 */       TextFilterClient.this.processJoinOrLeave(this.profile, TextFilterClient.this.joinEndpoint, TextFilterClient.this.joinEncoder, this.streamExecutor);
/*     */     }
/*     */ 
/*     */     
/*     */     public void leave() {
/* 308 */       TextFilterClient.this.processJoinOrLeave(this.profile, TextFilterClient.this.leaveEndpoint, TextFilterClient.this.leaveEncoder, this.streamExecutor);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CompletableFuture<List<FilteredText>> processMessageBundle(List<String> $$0) {
/* 316 */       List<CompletableFuture<FilteredText>> $$1 = (List<CompletableFuture<FilteredText>>)$$0.stream().map($$0 -> TextFilterClient.this.requestMessageProcessing(this.profile, $$0, TextFilterClient.this.chatIgnoreStrategy, this.streamExecutor)).collect(ImmutableList.toImmutableList());
/*     */       
/* 318 */       return Util.sequenceFailFast($$1)
/*     */         
/* 320 */         .exceptionally($$0 -> ImmutableList.of());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public CompletableFuture<FilteredText> processStreamMessage(String $$0) {
/* 326 */       return TextFilterClient.this.requestMessageProcessing(this.profile, $$0, TextFilterClient.this.chatIgnoreStrategy, this.streamExecutor);
/*     */     } }
/*     */   @FunctionalInterface
/*     */   public static interface IgnoreStrategy { public static final IgnoreStrategy NEVER_IGNORE = ($$0, $$1) -> false;
/*     */     public static final IgnoreStrategy IGNORE_FULLY_FILTERED;
/*     */     
/*     */     static {
/* 333 */       IGNORE_FULLY_FILTERED = (($$0, $$1) -> ($$0.length() == $$1));
/*     */     }
/*     */     static IgnoreStrategy ignoreOverThreshold(int $$0) {
/* 336 */       return ($$1, $$2) -> ($$2 >= $$0);
/*     */     }
/*     */     
/*     */     static IgnoreStrategy select(int $$0) {
/* 340 */       switch ($$0) { case -1: case 0:  }  return 
/*     */ 
/*     */         
/* 343 */         ignoreOverThreshold($$0);
/*     */     }
/*     */     
/*     */     boolean shouldIgnore(String param1String, int param1Int); }
/*     */ 
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface MessageEncoder {
/*     */     JsonObject encode(GameProfile param1GameProfile, String param1String);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface JoinOrLeaveEncoder {
/*     */     JsonObject encode(GameProfile param1GameProfile);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\TextFilterClient.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */