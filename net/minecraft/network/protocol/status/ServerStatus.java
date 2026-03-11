/*    */ package net.minecraft.network.protocol.status;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public final class ServerStatus extends Record {
/*    */   private final Component description;
/*    */   private final Optional<Players> players;
/*    */   private final Optional<Version> version;
/*    */   private final Optional<Favicon> favicon;
/*    */   private final boolean enforcesSecureChat;
/*    */   public static final Codec<ServerStatus> CODEC;
/*    */   
/* 19 */   public ServerStatus(Component $$0, Optional<Players> $$1, Optional<Version> $$2, Optional<Favicon> $$3, boolean $$4) { this.description = $$0; this.players = $$1; this.version = $$2; this.favicon = $$3; this.enforcesSecureChat = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/status/ServerStatus;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 19 */     //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus; } public Component description() { return this.description; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/status/ServerStatus;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/status/ServerStatus;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/status/ServerStatus;
/* 19 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Players> players() { return this.players; } public Optional<Version> version() { return this.version; } public Optional<Favicon> favicon() { return this.favicon; } public boolean enforcesSecureChat() { return this.enforcesSecureChat; } static {
/* 20 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ComponentSerialization.CODEC.optionalFieldOf("description", CommonComponents.EMPTY).forGetter(ServerStatus::description), (App)Players.CODEC.optionalFieldOf("players").forGetter(ServerStatus::players), (App)Version.CODEC.optionalFieldOf("version").forGetter(ServerStatus::version), (App)Favicon.CODEC.optionalFieldOf("favicon").forGetter(ServerStatus::favicon), (App)Codec.BOOL.optionalFieldOf("enforcesSecureChat", Boolean.valueOf(false)).forGetter(ServerStatus::enforcesSecureChat)).apply((Applicative)$$0, ServerStatus::new));
/*    */   }
/*    */   public static final class Players extends Record { private final int max;
/*    */     private final int online;
/*    */     private final List<GameProfile> sample;
/*    */     private static final Codec<GameProfile> PROFILE_CODEC;
/*    */     public static final Codec<Players> CODEC;
/*    */     
/* 28 */     public Players(int $$0, int $$1, List<GameProfile> $$2) { this.max = $$0; this.online = $$1; this.sample = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/status/ServerStatus$Players;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #28	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Players; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/status/ServerStatus$Players;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #28	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Players; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/status/ServerStatus$Players;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #28	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Players;
/* 28 */       //   0	8	1	$$0	Ljava/lang/Object; } public int max() { return this.max; } public int online() { return this.online; } public List<GameProfile> sample() { return this.sample; } static {
/* 29 */       PROFILE_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)UUIDUtil.STRING_CODEC.fieldOf("id").forGetter(GameProfile::getId), (App)Codec.STRING.fieldOf("name").forGetter(GameProfile::getName)).apply((Applicative)$$0, GameProfile::new));
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 34 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("max").forGetter(Players::max), (App)Codec.INT.fieldOf("online").forGetter(Players::online), (App)PROFILE_CODEC.listOf().optionalFieldOf("sample", List.of()).forGetter(Players::sample)).apply((Applicative)$$0, Players::new));
/*    */     } }
/*    */   
/*    */   public static final class Version extends Record { private final String name;
/*    */     private final int protocol;
/*    */     public static final Codec<Version> CODEC;
/*    */     
/* 41 */     public Version(String $$0, int $$1) { this.name = $$0; this.protocol = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/status/ServerStatus$Version;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #41	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Version; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/status/ServerStatus$Version;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #41	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Version; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/status/ServerStatus$Version;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #41	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Version;
/* 41 */       //   0	8	1	$$0	Ljava/lang/Object; } public String name() { return this.name; } public int protocol() { return this.protocol; } static {
/* 42 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.STRING.fieldOf("name").forGetter(Version::name), (App)Codec.INT.fieldOf("protocol").forGetter(Version::protocol)).apply((Applicative)$$0, Version::new));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public static Version current() {
/* 48 */       WorldVersion $$0 = SharedConstants.getCurrentVersion();
/* 49 */       return new Version($$0.getName(), $$0.getProtocolVersion());
/*    */     } }
/*    */   public static final class Favicon extends Record { private final byte[] iconBytes; private static final String PREFIX = "data:image/png;base64,"; public static final Codec<Favicon> CODEC;
/*    */     
/* 53 */     public Favicon(byte[] $$0) { this.iconBytes = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/status/ServerStatus$Favicon;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Favicon; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/status/ServerStatus$Favicon;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Favicon; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/status/ServerStatus$Favicon;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Favicon;
/* 53 */       //   0	8	1	$$0	Ljava/lang/Object; } public byte[] iconBytes() { return this.iconBytes; }
/*    */     
/*    */     static {
/* 56 */       CODEC = Codec.STRING.comapFlatMap($$0 -> {
/*    */             if (!$$0.startsWith("data:image/png;base64,")) {
/*    */               return DataResult.error(());
/*    */             }
/*    */             
/*    */             try {
/*    */               String $$1 = $$0.substring("data:image/png;base64,".length()).replaceAll("\n", "");
/*    */               
/*    */               byte[] $$2 = Base64.getDecoder().decode($$1.getBytes(StandardCharsets.UTF_8));
/*    */               return DataResult.success(new Favicon($$2));
/* 66 */             } catch (IllegalArgumentException $$3) {
/*    */               return DataResult.error(());
/*    */             } 
/*    */           }$$0 -> "data:image/png;base64," + new String(Base64.getEncoder().encode($$0.iconBytes), StandardCharsets.UTF_8));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\status\ServerStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */