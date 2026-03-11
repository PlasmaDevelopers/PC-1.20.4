/*     */ package net.minecraft.client.multiplayer;
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.authlib.minecraft.InsecurePublicKeyException;
/*     */ import com.mojang.authlib.minecraft.UserApiService;
/*     */ import com.mojang.authlib.yggdrasil.response.KeyPairResponse;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.PublicKey;
/*     */ import java.time.DateTimeException;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.Crypt;
/*     */ import net.minecraft.util.CryptException;
/*     */ import net.minecraft.world.entity.player.ProfileKeyPair;
/*     */ import net.minecraft.world.entity.player.ProfilePublicKey;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class AccountProfileKeyPairManager implements ProfileKeyPairManager {
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  36 */   private static final Duration MINIMUM_PROFILE_KEY_REFRESH_INTERVAL = Duration.ofHours(1L);
/*     */   
/*  38 */   private static final Path PROFILE_KEY_PAIR_DIR = Path.of("profilekeys", new String[0]);
/*     */   
/*     */   private final UserApiService userApiService;
/*     */   
/*     */   private final Path profileKeyPairPath;
/*     */   private CompletableFuture<Optional<ProfileKeyPair>> keyPair;
/*  44 */   private Instant nextProfileKeyRefreshTime = Instant.EPOCH;
/*     */   
/*     */   public AccountProfileKeyPairManager(UserApiService $$0, UUID $$1, Path $$2) {
/*  47 */     this.userApiService = $$0;
/*  48 */     this.profileKeyPairPath = $$2.resolve(PROFILE_KEY_PAIR_DIR).resolve("" + $$1 + ".json");
/*  49 */     this
/*  50 */       .keyPair = CompletableFuture.supplyAsync(() -> readProfileKeyPair().filter(()), Util.backgroundExecutor()).thenCompose(this::readOrFetchProfileKeyPair);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Optional<ProfileKeyPair>> prepareKeyPair() {
/*  55 */     this.nextProfileKeyRefreshTime = Instant.now().plus(MINIMUM_PROFILE_KEY_REFRESH_INTERVAL);
/*  56 */     this.keyPair = this.keyPair.thenCompose(this::readOrFetchProfileKeyPair);
/*  57 */     return this.keyPair;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRefreshKeyPair() {
/*  62 */     if (this.keyPair.isDone() && Instant.now().isAfter(this.nextProfileKeyRefreshTime)) {
/*  63 */       return ((Boolean)((Optional)this.keyPair.join()).map(ProfileKeyPair::dueRefresh).orElse(Boolean.valueOf(true))).booleanValue();
/*     */     }
/*  65 */     return false;
/*     */   }
/*     */   
/*     */   private CompletableFuture<Optional<ProfileKeyPair>> readOrFetchProfileKeyPair(Optional<ProfileKeyPair> $$0) {
/*  69 */     return CompletableFuture.supplyAsync(() -> {
/*     */           if ($$0.isPresent() && !((ProfileKeyPair)$$0.get()).dueRefresh()) {
/*     */             if (!SharedConstants.IS_RUNNING_IN_IDE) {
/*     */               writeProfileKeyPair(null);
/*     */             }
/*     */             
/*     */             return $$0;
/*     */           } 
/*     */           
/*     */           try {
/*     */             ProfileKeyPair $$1 = fetchProfileKeyPair(this.userApiService);
/*     */             writeProfileKeyPair($$1);
/*     */             return Optional.of($$1);
/*  82 */           } catch (IOException|CryptException|com.mojang.authlib.exceptions.MinecraftClientException $$2) {
/*     */             LOGGER.error("Failed to retrieve profile key pair", $$2);
/*     */             
/*     */             writeProfileKeyPair(null);
/*     */             return $$0;
/*     */           } 
/*  88 */         }Util.backgroundExecutor());
/*     */   }
/*     */   
/*     */   private Optional<ProfileKeyPair> readProfileKeyPair() {
/*  92 */     if (Files.notExists(this.profileKeyPairPath, new java.nio.file.LinkOption[0])) {
/*  93 */       return Optional.empty();
/*     */     }
/*     */     
/*  96 */     try { BufferedReader $$0 = Files.newBufferedReader(this.profileKeyPairPath); 
/*  97 */       try { Optional<ProfileKeyPair> optional = ProfileKeyPair.CODEC.parse((DynamicOps)JsonOps.INSTANCE, JsonParser.parseReader($$0)).result();
/*  98 */         if ($$0 != null) $$0.close();  return optional; } catch (Throwable throwable) { if ($$0 != null) try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$1)
/*  99 */     { LOGGER.error("Failed to read profile key pair file {}", this.profileKeyPairPath, $$1);
/* 100 */       return Optional.empty(); }
/*     */   
/*     */   }
/*     */   
/*     */   private void writeProfileKeyPair(@Nullable ProfileKeyPair $$0) {
/*     */     try {
/* 106 */       Files.deleteIfExists(this.profileKeyPairPath);
/* 107 */     } catch (IOException $$1) {
/* 108 */       LOGGER.error("Failed to delete profile key pair file {}", this.profileKeyPairPath, $$1);
/*     */     } 
/*     */     
/* 111 */     if ($$0 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 115 */     if (!SharedConstants.IS_RUNNING_IN_IDE) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     ProfileKeyPair.CODEC.encodeStart((DynamicOps)JsonOps.INSTANCE, $$0).result().ifPresent($$0 -> {
/*     */           try {
/*     */             Files.createDirectories(this.profileKeyPairPath.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/*     */             Files.writeString(this.profileKeyPairPath, $$0.toString(), new java.nio.file.OpenOption[0]);
/* 123 */           } catch (Exception $$1) {
/*     */             LOGGER.error("Failed to write profile key pair file {}", this.profileKeyPairPath, $$1);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private ProfileKeyPair fetchProfileKeyPair(UserApiService $$0) throws CryptException, IOException {
/* 130 */     KeyPairResponse $$1 = $$0.getKeyPair();
/* 131 */     if ($$1 != null) {
/* 132 */       ProfilePublicKey.Data $$2 = parsePublicKey($$1);
/* 133 */       return new ProfileKeyPair(
/* 134 */           Crypt.stringToPemRsaPrivateKey($$1.keyPair().privateKey()), new ProfilePublicKey($$2), 
/*     */           
/* 136 */           Instant.parse($$1.refreshedAfter()));
/*     */     } 
/*     */     
/* 139 */     throw new IOException("Could not retrieve profile key pair");
/*     */   }
/*     */   
/*     */   private static ProfilePublicKey.Data parsePublicKey(KeyPairResponse $$0) throws CryptException {
/* 143 */     KeyPairResponse.KeyPair $$1 = $$0.keyPair();
/* 144 */     if (Strings.isNullOrEmpty($$1.publicKey()) || $$0.publicKeySignature() == null || ($$0.publicKeySignature().array()).length == 0) {
/* 145 */       throw new CryptException(new InsecurePublicKeyException.MissingException("Missing public key"));
/*     */     }
/*     */     
/*     */     try {
/* 149 */       Instant $$2 = Instant.parse($$0.expiresAt());
/* 150 */       PublicKey $$3 = Crypt.stringToRsaPublicKey($$1.publicKey());
/* 151 */       ByteBuffer $$4 = $$0.publicKeySignature();
/*     */       
/* 153 */       return new ProfilePublicKey.Data($$2, $$3, $$4.array());
/* 154 */     } catch (DateTimeException|IllegalArgumentException $$5) {
/* 155 */       throw new CryptException($$5);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\AccountProfileKeyPairManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */