/*     */ package net.minecraft.client.quickplay;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.time.Instant;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class QuickPlayLog {
/*  25 */   private static final QuickPlayLog INACTIVE = new QuickPlayLog("")
/*     */     {
/*     */       public void log(Minecraft $$0) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public void setWorldData(QuickPlayLog.Type $$0, String $$1, String $$2) {}
/*     */     };
/*     */ 
/*     */   
/*  35 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  36 */   private static final Gson GSON = (new GsonBuilder()).create();
/*     */   
/*     */   private final Path path;
/*     */   
/*     */   @Nullable
/*     */   private QuickPlayWorld worldData;
/*     */   
/*     */   QuickPlayLog(String $$0) {
/*  44 */     this.path = (Minecraft.getInstance()).gameDirectory.toPath().resolve($$0);
/*     */   }
/*     */   
/*     */   public static QuickPlayLog of(@Nullable String $$0) {
/*  48 */     if ($$0 == null) {
/*  49 */       return INACTIVE;
/*     */     }
/*  51 */     return new QuickPlayLog($$0);
/*     */   }
/*     */   
/*     */   public void setWorldData(Type $$0, String $$1, String $$2) {
/*  55 */     this.worldData = new QuickPlayWorld($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public void log(Minecraft $$0) {
/*  59 */     if ($$0.gameMode == null || this.worldData == null) {
/*  60 */       LOGGER.error("Failed to log session for quickplay. Missing world data or gamemode");
/*     */       
/*     */       return;
/*     */     } 
/*  64 */     Util.ioPool().execute(() -> {
/*     */           try {
/*     */             Files.deleteIfExists(this.path);
/*  67 */           } catch (IOException $$1) {
/*     */             LOGGER.error("Failed to delete quickplay log file {}", this.path, $$1);
/*     */           } 
/*     */           QuickPlayEntry $$2 = new QuickPlayEntry(this.worldData, Instant.now(), $$0.gameMode.getPlayerMode());
/*     */           Objects.requireNonNull(LOGGER);
/*     */           Codec.list(QuickPlayEntry.CODEC).encodeStart((DynamicOps)JsonOps.INSTANCE, List.of($$2)).resultOrPartial(Util.prefix("Quick Play: ", LOGGER::error)).ifPresent(());
/*     */         });
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
/*     */   public enum Type
/*     */     implements StringRepresentable
/*     */   {
/*  87 */     SINGLEPLAYER("singleplayer"),
/*  88 */     MULTIPLAYER("multiplayer"),
/*  89 */     REALMS("realms");
/*     */     
/*  91 */     static final Codec<Type> CODEC = (Codec<Type>)StringRepresentable.fromEnum(Type::values); private final String name;
/*     */     static {
/*     */     
/*     */     }
/*     */     Type(String $$0) {
/*  96 */       this.name = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 101 */       return this.name;
/*     */     } }
/*     */   private static final class QuickPlayWorld extends Record { private final QuickPlayLog.Type type; private final String id; private final String name; public static final MapCodec<QuickPlayWorld> MAP_CODEC;
/*     */     
/* 105 */     QuickPlayWorld(QuickPlayLog.Type $$0, String $$1, String $$2) { this.type = $$0; this.id = $$1; this.name = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayWorld;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #105	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 105 */       //   0	7	0	this	Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayWorld; } public QuickPlayLog.Type type() { return this.type; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayWorld;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #105	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayWorld; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayWorld;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #105	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayWorld;
/* 105 */       //   0	8	1	$$0	Ljava/lang/Object; } public String id() { return this.id; } public String name() { return this.name; } static {
/* 106 */       MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)QuickPlayLog.Type.CODEC.fieldOf("type").forGetter(QuickPlayWorld::type), (App)ExtraCodecs.ESCAPED_STRING.fieldOf("id").forGetter(QuickPlayWorld::id), (App)Codec.STRING.fieldOf("name").forGetter(QuickPlayWorld::name)).apply((Applicative)$$0, QuickPlayWorld::new));
/*     */     } }
/*     */   private static final class QuickPlayEntry extends Record { private final QuickPlayLog.QuickPlayWorld quickPlayWorld;
/*     */     private final Instant lastPlayedTime;
/*     */     private final GameType gamemode;
/*     */     public static final Codec<QuickPlayEntry> CODEC;
/*     */     
/* 113 */     QuickPlayEntry(QuickPlayLog.QuickPlayWorld $$0, Instant $$1, GameType $$2) { this.quickPlayWorld = $$0; this.lastPlayedTime = $$1; this.gamemode = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #113	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #113	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #113	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/quickplay/QuickPlayLog$QuickPlayEntry;
/* 113 */       //   0	8	1	$$0	Ljava/lang/Object; } public QuickPlayLog.QuickPlayWorld quickPlayWorld() { return this.quickPlayWorld; } public Instant lastPlayedTime() { return this.lastPlayedTime; } public GameType gamemode() { return this.gamemode; } static {
/* 114 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)QuickPlayLog.QuickPlayWorld.MAP_CODEC.forGetter(QuickPlayEntry::quickPlayWorld), (App)ExtraCodecs.INSTANT_ISO8601.fieldOf("lastPlayedTime").forGetter(QuickPlayEntry::lastPlayedTime), (App)GameType.CODEC.fieldOf("gamemode").forGetter(QuickPlayEntry::gamemode)).apply((Applicative)$$0, QuickPlayEntry::new));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\quickplay\QuickPlayLog.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */