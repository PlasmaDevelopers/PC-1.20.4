/*     */ package net.minecraft.world.level.saveddata.maps;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
/*     */ import net.minecraft.world.entity.player.Player;
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
/*     */ public class HoldingPlayer
/*     */ {
/*     */   public final Player player;
/*     */   private boolean dirtyData = true;
/*     */   private int minDirtyX;
/*     */   private int minDirtyY;
/*  73 */   private int maxDirtyX = 127;
/*  74 */   private int maxDirtyY = 127;
/*     */   private boolean dirtyDecorations = true;
/*     */   private int tick;
/*     */   public int step;
/*     */   
/*     */   HoldingPlayer(Player $$1) {
/*  80 */     this.player = $$1;
/*     */   }
/*     */   
/*     */   private MapItemSavedData.MapPatch createPatch() {
/*  84 */     int $$0 = this.minDirtyX;
/*  85 */     int $$1 = this.minDirtyY;
/*  86 */     int $$2 = this.maxDirtyX + 1 - this.minDirtyX;
/*  87 */     int $$3 = this.maxDirtyY + 1 - this.minDirtyY;
/*     */     
/*  89 */     byte[] $$4 = new byte[$$2 * $$3];
/*  90 */     for (int $$5 = 0; $$5 < $$2; $$5++) {
/*  91 */       for (int $$6 = 0; $$6 < $$3; $$6++) {
/*  92 */         $$4[$$5 + $$6 * $$2] = MapItemSavedData.this.colors[$$0 + $$5 + ($$1 + $$6) * 128];
/*     */       }
/*     */     } 
/*  95 */     return new MapItemSavedData.MapPatch($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   @Nullable
/*     */   Packet<?> nextUpdatePacket(int $$0) {
/*     */     MapItemSavedData.MapPatch $$2;
/*     */     Collection<MapDecoration> $$4;
/* 101 */     if (this.dirtyData) {
/* 102 */       this.dirtyData = false;
/* 103 */       MapItemSavedData.MapPatch $$1 = createPatch();
/*     */     } else {
/* 105 */       $$2 = null;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     if (this.dirtyDecorations && this.tick++ % 5 == 0) {
/* 110 */       this.dirtyDecorations = false;
/* 111 */       Collection<MapDecoration> $$3 = MapItemSavedData.this.decorations.values();
/*     */     } else {
/* 113 */       $$4 = null;
/*     */     } 
/*     */     
/* 116 */     if ($$4 != null || $$2 != null) {
/* 117 */       return (Packet<?>)new ClientboundMapItemDataPacket($$0, MapItemSavedData.this.scale, MapItemSavedData.this.locked, $$4, $$2);
/*     */     }
/*     */     
/* 120 */     return null;
/*     */   }
/*     */   
/*     */   void markColorsDirty(int $$0, int $$1) {
/* 124 */     if (this.dirtyData) {
/* 125 */       this.minDirtyX = Math.min(this.minDirtyX, $$0);
/* 126 */       this.minDirtyY = Math.min(this.minDirtyY, $$1);
/* 127 */       this.maxDirtyX = Math.max(this.maxDirtyX, $$0);
/* 128 */       this.maxDirtyY = Math.max(this.maxDirtyY, $$1);
/*     */     } else {
/* 130 */       this.dirtyData = true;
/* 131 */       this.minDirtyX = $$0;
/* 132 */       this.minDirtyY = $$1;
/* 133 */       this.maxDirtyX = $$0;
/* 134 */       this.maxDirtyY = $$1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void markDecorationsDirty() {
/* 139 */     this.dirtyDecorations = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\saveddata\maps\MapItemSavedData$HoldingPlayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */