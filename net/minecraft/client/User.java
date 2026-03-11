/*    */ package net.minecraft.client;
/*    */ 
/*    */ import com.mojang.util.UndashedUuid;
/*    */ import java.util.Arrays;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class User
/*    */ {
/*    */   private final String name;
/*    */   private final UUID uuid;
/*    */   private final String accessToken;
/*    */   private final Optional<String> xuid;
/*    */   private final Optional<String> clientId;
/*    */   private final Type type;
/*    */   
/*    */   public User(String $$0, UUID $$1, String $$2, Optional<String> $$3, Optional<String> $$4, Type $$5) {
/* 23 */     this.name = $$0;
/* 24 */     this.uuid = $$1;
/* 25 */     this.accessToken = $$2;
/* 26 */     this.xuid = $$3;
/* 27 */     this.clientId = $$4;
/* 28 */     this.type = $$5;
/*    */   }
/*    */   
/*    */   public String getSessionId() {
/* 32 */     return "token:" + this.accessToken + ":" + UndashedUuid.toString(this.uuid);
/*    */   }
/*    */   
/*    */   public UUID getProfileId() {
/* 36 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 40 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getAccessToken() {
/* 44 */     return this.accessToken;
/*    */   }
/*    */   
/*    */   public Optional<String> getClientId() {
/* 48 */     return this.clientId;
/*    */   }
/*    */   
/*    */   public Optional<String> getXuid() {
/* 52 */     return this.xuid;
/*    */   }
/*    */   
/*    */   public Type getType() {
/* 56 */     return this.type;
/*    */   }
/*    */   
/*    */   public enum Type {
/* 60 */     LEGACY("legacy"),
/* 61 */     MOJANG("mojang"),
/* 62 */     MSA("msa"); private static final Map<String, Type> BY_NAME;
/*    */     
/*    */     static {
/* 65 */       BY_NAME = (Map<String, Type>)Arrays.<Type>stream(values()).collect(Collectors.toMap($$0 -> $$0.name, Function.identity()));
/*    */     }
/*    */     private final String name;
/*    */     
/*    */     Type(String $$0) {
/* 70 */       this.name = $$0;
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public static Type byName(String $$0) {
/* 75 */       return BY_NAME.get($$0.toLowerCase(Locale.ROOT));
/*    */     }
/*    */     
/*    */     public String getName() {
/* 79 */       return this.name;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\User.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */