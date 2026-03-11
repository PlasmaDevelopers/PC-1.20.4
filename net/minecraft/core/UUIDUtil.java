/*     */ package net.minecraft.core;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import com.mojang.util.UndashedUuid;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Arrays;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.IntStream;
/*     */ import net.minecraft.Util;
/*     */ 
/*     */ public final class UUIDUtil {
/*     */   public static final Codec<UUID> CODEC;
/*     */   
/*     */   static {
/*  23 */     CODEC = Codec.INT_STREAM.comapFlatMap($$0 -> Util.fixedSize($$0, 4).map(UUIDUtil::uuidFromIntArray), $$0 -> Arrays.stream(uuidToIntArray($$0)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  28 */   public static final Codec<Set<UUID>> CODEC_SET = Codec.list(CODEC).xmap(Sets::newHashSet, Lists::newArrayList); public static final Codec<UUID> STRING_CODEC; public static Codec<UUID> AUTHLIB_CODEC;
/*     */   static {
/*  30 */     STRING_CODEC = Codec.STRING.comapFlatMap($$0 -> {
/*     */           try {
/*     */             return DataResult.success(UUID.fromString($$0), Lifecycle.stable());
/*  33 */           } catch (IllegalArgumentException $$1) {
/*     */             return DataResult.error(());
/*     */           } 
/*     */         }UUID::toString);
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
/*  55 */     AUTHLIB_CODEC = Codec.either(CODEC, Codec.STRING.comapFlatMap($$0 -> { try { return DataResult.success(UndashedUuid.fromStringLenient($$0), Lifecycle.stable()); } catch (IllegalArgumentException $$1) { return DataResult.error(()); }  }UndashedUuid::toString)).xmap($$0 -> (UUID)$$0.map((), ()), Either::right);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  61 */     LENIENT_CODEC = Codec.either(CODEC, STRING_CODEC).xmap($$0 -> (UUID)$$0.map((), ()), Either::left);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Codec<UUID> LENIENT_CODEC;
/*     */   
/*     */   public static final int UUID_BYTES = 16;
/*     */   private static final String UUID_PREFIX_OFFLINE_PLAYER = "OfflinePlayer:";
/*     */   
/*     */   public static UUID uuidFromIntArray(int[] $$0) {
/*  71 */     return new UUID($$0[0] << 32L | $$0[1] & 0xFFFFFFFFL, $$0[2] << 32L | $$0[3] & 0xFFFFFFFFL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] uuidToIntArray(UUID $$0) {
/*  78 */     long $$1 = $$0.getMostSignificantBits();
/*  79 */     long $$2 = $$0.getLeastSignificantBits();
/*  80 */     return leastMostToIntArray($$1, $$2);
/*     */   }
/*     */   
/*     */   private static int[] leastMostToIntArray(long $$0, long $$1) {
/*  84 */     return new int[] { (int)($$0 >> 32L), (int)$$0, (int)($$1 >> 32L), (int)$$1 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] uuidToByteArray(UUID $$0) {
/*  93 */     byte[] $$1 = new byte[16];
/*  94 */     ByteBuffer.wrap($$1).order(ByteOrder.BIG_ENDIAN)
/*  95 */       .putLong($$0.getMostSignificantBits())
/*  96 */       .putLong($$0.getLeastSignificantBits());
/*     */     
/*  98 */     return $$1;
/*     */   }
/*     */   
/*     */   public static UUID readUUID(Dynamic<?> $$0) {
/* 102 */     int[] $$1 = $$0.asIntStream().toArray();
/* 103 */     if ($$1.length != 4) {
/* 104 */       throw new IllegalArgumentException("Could not read UUID. Expected int-array of length 4, got " + $$1.length + ".");
/*     */     }
/* 106 */     return uuidFromIntArray($$1);
/*     */   }
/*     */   
/*     */   public static UUID createOfflinePlayerUUID(String $$0) {
/* 110 */     return UUID.nameUUIDFromBytes(("OfflinePlayer:" + $$0).getBytes(StandardCharsets.UTF_8));
/*     */   }
/*     */   
/*     */   public static GameProfile createOfflineProfile(String $$0) {
/* 114 */     UUID $$1 = createOfflinePlayerUUID($$0);
/* 115 */     return new GameProfile($$1, $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\UUIDUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */