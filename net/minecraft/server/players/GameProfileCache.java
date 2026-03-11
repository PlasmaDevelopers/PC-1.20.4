/*     */ package net.minecraft.server.players;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.GameProfileRepository;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GameProfileCache
/*     */ {
/*  47 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int GAMEPROFILES_MRU_LIMIT = 1000;
/*     */   private static final int GAMEPROFILES_EXPIRATION_MONTHS = 1;
/*     */   private static boolean usesAuthentication;
/*  52 */   private final Map<String, GameProfileInfo> profilesByName = Maps.newConcurrentMap();
/*  53 */   private final Map<UUID, GameProfileInfo> profilesByUUID = Maps.newConcurrentMap();
/*  54 */   private final Map<String, CompletableFuture<Optional<GameProfile>>> requests = Maps.newConcurrentMap();
/*     */   private final GameProfileRepository profileRepository;
/*  56 */   private final Gson gson = (new GsonBuilder()).create();
/*     */   private final File file;
/*  58 */   private final AtomicLong operationCount = new AtomicLong();
/*     */   
/*     */   @Nullable
/*     */   private Executor executor;
/*     */   
/*     */   public GameProfileCache(GameProfileRepository $$0, File $$1) {
/*  64 */     this.profileRepository = $$0;
/*  65 */     this.file = $$1;
/*     */     
/*  67 */     Lists.reverse(load()).forEach(this::safeAdd);
/*     */   }
/*     */   
/*     */   private void safeAdd(GameProfileInfo $$0) {
/*  71 */     GameProfile $$1 = $$0.getProfile();
/*  72 */     $$0.setLastAccess(getNextOperation());
/*  73 */     this.profilesByName.put($$1.getName().toLowerCase(Locale.ROOT), $$0);
/*  74 */     this.profilesByUUID.put($$1.getId(), $$0);
/*     */   }
/*     */   
/*     */   private static Optional<GameProfile> lookupGameProfile(GameProfileRepository $$0, String $$1) {
/*  78 */     if (!Player.isValidUsername($$1)) {
/*  79 */       return createUnknownProfile($$1);
/*     */     }
/*     */     
/*  82 */     final AtomicReference<GameProfile> result = new AtomicReference<>();
/*  83 */     ProfileLookupCallback $$3 = new ProfileLookupCallback()
/*     */       {
/*     */         public void onProfileLookupSucceeded(GameProfile $$0) {
/*  86 */           result.set($$0);
/*     */         }
/*     */ 
/*     */         
/*     */         public void onProfileLookupFailed(String $$0, Exception $$1) {
/*  91 */           result.set(null);
/*     */         }
/*     */       };
/*     */     
/*  95 */     $$0.findProfilesByNames(new String[] { $$1 }, $$3);
/*  96 */     GameProfile $$4 = $$2.get();
/*  97 */     return ($$4 != null) ? Optional.<GameProfile>of($$4) : createUnknownProfile($$1);
/*     */   }
/*     */   
/*     */   private static Optional<GameProfile> createUnknownProfile(String $$0) {
/* 101 */     if (usesAuthentication()) {
/* 102 */       return Optional.empty();
/*     */     }
/* 104 */     return Optional.of(UUIDUtil.createOfflineProfile($$0));
/*     */   }
/*     */   
/*     */   public static void setUsesAuthentication(boolean $$0) {
/* 108 */     usesAuthentication = $$0;
/*     */   }
/*     */   
/*     */   private static boolean usesAuthentication() {
/* 112 */     return usesAuthentication;
/*     */   }
/*     */   
/*     */   public void add(GameProfile $$0) {
/* 116 */     Calendar $$1 = Calendar.getInstance();
/* 117 */     $$1.setTime(new Date());
/* 118 */     $$1.add(2, 1);
/* 119 */     Date $$2 = $$1.getTime();
/*     */     
/* 121 */     GameProfileInfo $$3 = new GameProfileInfo($$0, $$2);
/* 122 */     safeAdd($$3);
/* 123 */     save();
/*     */   }
/*     */   
/*     */   private long getNextOperation() {
/* 127 */     return this.operationCount.incrementAndGet();
/*     */   }
/*     */   public Optional<GameProfile> get(String $$0) {
/*     */     Optional<GameProfile> $$5;
/* 131 */     String $$1 = $$0.toLowerCase(Locale.ROOT);
/* 132 */     GameProfileInfo $$2 = this.profilesByName.get($$1);
/*     */     
/* 134 */     boolean $$3 = false;
/*     */     
/* 136 */     if ($$2 != null && (new Date()).getTime() >= $$2.expirationDate.getTime()) {
/*     */       
/* 138 */       this.profilesByUUID.remove($$2.getProfile().getId());
/* 139 */       this.profilesByName.remove($$2.getProfile().getName().toLowerCase(Locale.ROOT));
/* 140 */       $$3 = true;
/* 141 */       $$2 = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 146 */     if ($$2 != null) {
/* 147 */       $$2.setLastAccess(getNextOperation());
/* 148 */       Optional<GameProfile> $$4 = Optional.of($$2.getProfile());
/*     */     } else {
/* 150 */       $$5 = lookupGameProfile(this.profileRepository, $$1);
/* 151 */       if ($$5.isPresent()) {
/* 152 */         add($$5.get());
/*     */         
/* 154 */         $$3 = false;
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     if ($$3) {
/* 159 */       save();
/*     */     }
/* 161 */     return $$5;
/*     */   }
/*     */   
/*     */   public CompletableFuture<Optional<GameProfile>> getAsync(String $$0) {
/* 165 */     if (this.executor == null) {
/* 166 */       throw new IllegalStateException("No executor");
/*     */     }
/* 168 */     CompletableFuture<Optional<GameProfile>> $$1 = this.requests.get($$0);
/* 169 */     if ($$1 != null) {
/* 170 */       return $$1;
/*     */     }
/*     */     
/* 173 */     CompletableFuture<Optional<GameProfile>> $$2 = CompletableFuture.<Optional<GameProfile>>supplyAsync(() -> get($$0), Util.backgroundExecutor()).whenCompleteAsync(($$1, $$2) -> this.requests.remove($$0), this.executor);
/* 174 */     this.requests.put($$0, $$2);
/* 175 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<GameProfile> get(UUID $$0) {
/* 180 */     GameProfileInfo $$1 = this.profilesByUUID.get($$0);
/* 181 */     if ($$1 == null) {
/* 182 */       return Optional.empty();
/*     */     }
/* 184 */     $$1.setLastAccess(getNextOperation());
/* 185 */     return Optional.of($$1.getProfile());
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor $$0) {
/* 189 */     this.executor = $$0;
/*     */   }
/*     */   
/*     */   public void clearExecutor() {
/* 193 */     this.executor = null;
/*     */   }
/*     */   
/*     */   private static DateFormat createDateFormat() {
/* 197 */     return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
/*     */   }
/*     */   
/*     */   public List<GameProfileInfo> load() {
/* 201 */     List<GameProfileInfo> $$0 = Lists.newArrayList(); 
/* 202 */     try { Reader $$1 = Files.newReader(this.file, StandardCharsets.UTF_8); 
/* 203 */       try { JsonArray $$2 = (JsonArray)this.gson.fromJson($$1, JsonArray.class);
/* 204 */         if ($$2 == null)
/* 205 */         { List<GameProfileInfo> list = $$0;
/*     */ 
/*     */ 
/*     */           
/* 209 */           if ($$1 != null) $$1.close();  return list; }  DateFormat $$3 = createDateFormat(); $$2.forEach($$2 -> { Objects.requireNonNull($$1); readGameProfile($$2, $$0).ifPresent($$1::add); }); if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null) try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (FileNotFoundException fileNotFoundException)
/*     */     {  }
/* 211 */     catch (IOException|com.google.gson.JsonParseException $$4)
/* 212 */     { LOGGER.warn("Failed to load profile cache {}", this.file, $$4); }
/*     */     
/* 214 */     return $$0;
/*     */   }
/*     */   
/*     */   public void save() {
/* 218 */     JsonArray $$0 = new JsonArray();
/* 219 */     DateFormat $$1 = createDateFormat();
/* 220 */     getTopMRUProfiles(1000).forEach($$2 -> $$0.add(writeGameProfile($$2, $$1)));
/*     */     
/* 222 */     String $$2 = this.gson.toJson((JsonElement)$$0); 
/* 223 */     try { Writer $$3 = Files.newWriter(this.file, StandardCharsets.UTF_8); 
/* 224 */       try { $$3.write($$2);
/* 225 */         if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Stream<GameProfileInfo> getTopMRUProfiles(int $$0) {
/* 231 */     return ImmutableList.copyOf(this.profilesByUUID.values()).stream().sorted(Comparator.<GameProfileInfo, Comparable>comparing(GameProfileInfo::getLastAccess).reversed()).limit($$0);
/*     */   }
/*     */   
/*     */   private static JsonElement writeGameProfile(GameProfileInfo $$0, DateFormat $$1) {
/* 235 */     JsonObject $$2 = new JsonObject();
/* 236 */     $$2.addProperty("name", $$0.getProfile().getName());
/* 237 */     $$2.addProperty("uuid", $$0.getProfile().getId().toString());
/* 238 */     $$2.addProperty("expiresOn", $$1.format($$0.getExpirationDate()));
/* 239 */     return (JsonElement)$$2;
/*     */   }
/*     */   
/*     */   private static Optional<GameProfileInfo> readGameProfile(JsonElement $$0, DateFormat $$1) {
/* 243 */     if ($$0.isJsonObject()) {
/* 244 */       UUID $$9; JsonObject $$2 = $$0.getAsJsonObject();
/* 245 */       JsonElement $$3 = $$2.get("name");
/* 246 */       JsonElement $$4 = $$2.get("uuid");
/* 247 */       JsonElement $$5 = $$2.get("expiresOn");
/* 248 */       if ($$3 == null || $$4 == null) {
/* 249 */         return Optional.empty();
/*     */       }
/* 251 */       String $$6 = $$4.getAsString();
/* 252 */       String $$7 = $$3.getAsString();
/* 253 */       Date $$8 = null;
/* 254 */       if ($$5 != null) {
/*     */         try {
/* 256 */           $$8 = $$1.parse($$5.getAsString());
/* 257 */         } catch (ParseException parseException) {}
/*     */       }
/*     */       
/* 260 */       if ($$7 == null || $$6 == null || $$8 == null) {
/* 261 */         return Optional.empty();
/*     */       }
/*     */       
/*     */       try {
/* 265 */         $$9 = UUID.fromString($$6);
/* 266 */       } catch (Throwable $$10) {
/* 267 */         return Optional.empty();
/*     */       } 
/* 269 */       return Optional.of(new GameProfileInfo(new GameProfile($$9, $$7), $$8));
/*     */     } 
/* 271 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   private static class GameProfileInfo
/*     */   {
/*     */     private final GameProfile profile;
/*     */     final Date expirationDate;
/*     */     private volatile long lastAccess;
/*     */     
/*     */     GameProfileInfo(GameProfile $$0, Date $$1) {
/* 281 */       this.profile = $$0;
/* 282 */       this.expirationDate = $$1;
/*     */     }
/*     */     
/*     */     public GameProfile getProfile() {
/* 286 */       return this.profile;
/*     */     }
/*     */     
/*     */     public Date getExpirationDate() {
/* 290 */       return this.expirationDate;
/*     */     }
/*     */     
/*     */     public void setLastAccess(long $$0) {
/* 294 */       this.lastAccess = $$0;
/*     */     }
/*     */     
/*     */     public long getLastAccess() {
/* 298 */       return this.lastAccess;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\GameProfileCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */