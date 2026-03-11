/*    */ package net.minecraft.network.protocol.status;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class Players extends Record {
/*    */   private final int max;
/*    */   private final int online;
/*    */   private final List<GameProfile> sample;
/*    */   private static final Codec<GameProfile> PROFILE_CODEC;
/*    */   public static final Codec<Players> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/status/ServerStatus$Players;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Players;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/status/ServerStatus$Players;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Players;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/status/ServerStatus$Players;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Players;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/* 28 */   public Players(int $$0, int $$1, List<GameProfile> $$2) { this.max = $$0; this.online = $$1; this.sample = $$2; } public int max() { return this.max; } public int online() { return this.online; } public List<GameProfile> sample() { return this.sample; } static {
/* 29 */     PROFILE_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)UUIDUtil.STRING_CODEC.fieldOf("id").forGetter(GameProfile::getId), (App)Codec.STRING.fieldOf("name").forGetter(GameProfile::getName)).apply((Applicative)$$0, GameProfile::new));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 34 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("max").forGetter(Players::max), (App)Codec.INT.fieldOf("online").forGetter(Players::online), (App)PROFILE_CODEC.listOf().optionalFieldOf("sample", List.of()).forGetter(Players::sample)).apply((Applicative)$$0, Players::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\status\ServerStatus$Players.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */