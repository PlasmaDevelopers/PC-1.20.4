/*    */ package com.mojang.realmsclient.gui;
/*    */ 
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import com.mojang.realmsclient.dto.RealmsWorldOptions;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class State
/*    */ {
/*    */   final boolean isCurrentlyActiveSlot;
/*    */   final String slotName;
/*    */   final String slotVersion;
/*    */   final RealmsServer.Compatibility compatibility;
/*    */   final long imageId;
/*    */   @Nullable
/*    */   final String image;
/*    */   public final boolean empty;
/*    */   public final boolean minigame;
/*    */   public final RealmsWorldSlotButton.Action action;
/*    */   
/*    */   public State(RealmsServer $$0, int $$1) {
/* 69 */     this.minigame = ($$1 == 4);
/* 70 */     if (this.minigame) {
/* 71 */       this.isCurrentlyActiveSlot = ($$0.worldType == RealmsServer.WorldType.MINIGAME);
/* 72 */       this.slotName = RealmsWorldSlotButton.MINIGAME.getString();
/* 73 */       this.imageId = $$0.minigameId;
/* 74 */       this.image = $$0.minigameImage;
/* 75 */       this.empty = ($$0.minigameId == -1);
/* 76 */       this.slotVersion = "";
/* 77 */       this.compatibility = RealmsServer.Compatibility.UNVERIFIABLE;
/*    */     } else {
/* 79 */       RealmsWorldOptions $$2 = (RealmsWorldOptions)$$0.slots.get(Integer.valueOf($$1));
/* 80 */       this.isCurrentlyActiveSlot = ($$0.activeSlot == $$1 && $$0.worldType != RealmsServer.WorldType.MINIGAME);
/* 81 */       this.slotName = $$2.getSlotName($$1);
/* 82 */       this.imageId = $$2.templateId;
/* 83 */       this.image = $$2.templateImage;
/* 84 */       this.empty = $$2.empty;
/* 85 */       this.slotVersion = $$2.version;
/* 86 */       this.compatibility = $$2.compatibility;
/*    */     } 
/* 88 */     this.action = RealmsWorldSlotButton.getAction($$0, this.isCurrentlyActiveSlot, this.minigame);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\RealmsWorldSlotButton$State.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */