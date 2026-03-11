/*    */ package net.minecraft.client.main;
/*    */ 
/*    */ import com.mojang.authlib.properties.PropertyMap;
/*    */ import com.mojang.blaze3d.platform.DisplayData;
/*    */ import java.io.File;
/*    */ import java.net.Proxy;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.User;
/*    */ import net.minecraft.client.resources.IndexedAssetSource;
/*    */ 
/*    */ public class GameConfig
/*    */ {
/*    */   public final UserData user;
/*    */   public final DisplayData display;
/*    */   public final FolderData location;
/*    */   public final GameData game;
/*    */   public final QuickPlayData quickPlay;
/*    */   
/*    */   public GameConfig(UserData $$0, DisplayData $$1, FolderData $$2, GameData $$3, QuickPlayData $$4) {
/* 22 */     this.user = $$0;
/* 23 */     this.display = $$1;
/* 24 */     this.location = $$2;
/* 25 */     this.game = $$3;
/* 26 */     this.quickPlay = $$4;
/*    */   }
/*    */   
/*    */   public static class GameData {
/*    */     public final boolean demo;
/*    */     public final String launchVersion;
/*    */     public final String versionType;
/*    */     public final boolean disableMultiplayer;
/*    */     public final boolean disableChat;
/*    */     
/*    */     public GameData(boolean $$0, String $$1, String $$2, boolean $$3, boolean $$4) {
/* 37 */       this.demo = $$0;
/* 38 */       this.launchVersion = $$1;
/* 39 */       this.versionType = $$2;
/* 40 */       this.disableMultiplayer = $$3;
/* 41 */       this.disableChat = $$4;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class UserData {
/*    */     public final User user;
/*    */     public final PropertyMap userProperties;
/*    */     public final PropertyMap profileProperties;
/*    */     public final Proxy proxy;
/*    */     
/*    */     public UserData(User $$0, PropertyMap $$1, PropertyMap $$2, Proxy $$3) {
/* 52 */       this.user = $$0;
/* 53 */       this.userProperties = $$1;
/* 54 */       this.profileProperties = $$2;
/* 55 */       this.proxy = $$3;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class FolderData {
/*    */     public final File gameDirectory;
/*    */     public final File resourcePackDirectory;
/*    */     public final File assetDirectory;
/*    */     @Nullable
/*    */     public final String assetIndex;
/*    */     
/*    */     public FolderData(File $$0, File $$1, File $$2, @Nullable String $$3) {
/* 67 */       this.gameDirectory = $$0;
/* 68 */       this.resourcePackDirectory = $$1;
/* 69 */       this.assetDirectory = $$2;
/* 70 */       this.assetIndex = $$3;
/*    */     }
/*    */     
/*    */     public Path getExternalAssetSource() {
/* 74 */       return (this.assetIndex == null) ? this.assetDirectory.toPath() : IndexedAssetSource.createIndexFs(this.assetDirectory.toPath(), this.assetIndex); } } public static final class QuickPlayData extends Record { @Nullable
/*    */     private final String path; @Nullable
/*    */     private final String singleplayer; @Nullable
/*    */     private final String multiplayer; @Nullable
/* 78 */     private final String realms; public QuickPlayData(@Nullable String $$0, @Nullable String $$1, @Nullable String $$2, @Nullable String $$3) { this.path = $$0; this.singleplayer = $$1; this.multiplayer = $$2; this.realms = $$3; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/main/GameConfig$QuickPlayData;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #78	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 78 */       //   0	7	0	this	Lnet/minecraft/client/main/GameConfig$QuickPlayData; } @Nullable public String path() { return this.path; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/main/GameConfig$QuickPlayData;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #78	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/client/main/GameConfig$QuickPlayData; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/main/GameConfig$QuickPlayData;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #78	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/client/main/GameConfig$QuickPlayData;
/* 78 */       //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public String singleplayer() { return this.singleplayer; } @Nullable public String multiplayer() { return this.multiplayer; } @Nullable public String realms() { return this.realms; }
/*    */      public boolean isEnabled() {
/* 80 */       return (!Util.isBlank(this.singleplayer) || !Util.isBlank(this.multiplayer) || !Util.isBlank(this.realms));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\main\GameConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */