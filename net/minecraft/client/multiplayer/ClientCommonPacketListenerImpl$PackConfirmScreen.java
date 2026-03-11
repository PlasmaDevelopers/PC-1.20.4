/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.resources.server.DownloadedPackSource;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
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
/*     */ 
/*     */ class PackConfirmScreen
/*     */   extends ConfirmScreen
/*     */ {
/*     */   private final List<PendingRequest> requests;
/*     */   @Nullable
/*     */   private final Screen parentScreen;
/*     */   
/*     */   private static final class PendingRequest
/*     */     extends Record
/*     */   {
/*     */     final UUID id;
/*     */     final URL url;
/*     */     final String hash;
/*     */     
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #259	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #259	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #259	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/multiplayer/ClientCommonPacketListenerImpl$PackConfirmScreen$PendingRequest;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */     }
/*     */     
/*     */     PendingRequest(UUID $$0, URL $$1, String $$2) {
/* 259 */       this.id = $$0; this.url = $$1; this.hash = $$2; } public UUID id() { return this.id; } public URL url() { return this.url; } public String hash() { return this.hash; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PackConfirmScreen(@Nullable Minecraft $$0, Screen $$1, List<PendingRequest> $$2, @Nullable boolean $$3, Component $$4) {
/* 267 */     super($$5 -> {
/*     */           $$0.setScreen($$1);
/*     */           
/*     */           DownloadedPackSource $$6 = $$0.getDownloadedPackSource();
/*     */           
/*     */           if ($$5) {
/*     */             if ($$4.serverData != null) {
/*     */               $$4.serverData.setResourcePackStatus(ServerData.ServerPackStatus.ENABLED);
/*     */             }
/*     */             
/*     */             $$6.allowServerPacks();
/*     */           } else {
/*     */             $$6.rejectServerPacks();
/*     */             
/*     */             if ($$2) {
/*     */               $$4.connection.disconnect((Component)Component.translatable("multiplayer.requiredTexturePrompt.disconnect"));
/*     */             } else if ($$4.serverData != null) {
/*     */               $$4.serverData.setResourcePackStatus(ServerData.ServerPackStatus.DISABLED);
/*     */             } 
/*     */           } 
/*     */           
/*     */           for (PendingRequest $$7 : $$3) {
/*     */             $$6.pushPack($$7.id, $$7.url, $$7.hash);
/*     */           }
/*     */           if ($$4.serverData != null) {
/*     */             ServerList.saveSingleServer($$4.serverData);
/*     */           }
/* 294 */         }$$3 ? (Component)Component.translatable("multiplayer.requiredTexturePrompt.line1") : (Component)Component.translatable("multiplayer.texturePrompt.line1"), 
/* 295 */         ClientCommonPacketListenerImpl.preparePackPrompt($$3 ? (Component)Component.translatable("multiplayer.requiredTexturePrompt.line2").withStyle(new ChatFormatting[] { ChatFormatting.YELLOW, ChatFormatting.BOLD }, ) : (Component)Component.translatable("multiplayer.texturePrompt.line2"), $$4), 
/* 296 */         $$3 ? CommonComponents.GUI_PROCEED : CommonComponents.GUI_YES, 
/* 297 */         $$3 ? CommonComponents.GUI_DISCONNECT : CommonComponents.GUI_NO);
/*     */     
/* 299 */     this.requests = $$2;
/* 300 */     this.parentScreen = $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PackConfirmScreen update(Minecraft $$0, UUID $$1, URL $$2, String $$3, boolean $$4, @Nullable Component $$5) {
/* 307 */     ImmutableList immutableList = ImmutableList.builderWithExpectedSize(this.requests.size() + 1).addAll(this.requests).add(new PendingRequest($$1, $$2, $$3)).build();
/* 308 */     return new PackConfirmScreen($$0, this.parentScreen, (List<PendingRequest>)immutableList, $$4, $$5);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientCommonPacketListenerImpl$PackConfirmScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */