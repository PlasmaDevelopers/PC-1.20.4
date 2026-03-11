/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.world.level.saveddata.maps.MapDecoration;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ 
/*     */ public class ClientboundMapItemDataPacket implements Packet<ClientGamePacketListener> {
/*     */   private final int mapId;
/*     */   private final byte scale;
/*     */   private final boolean locked;
/*     */   @Nullable
/*     */   private final List<MapDecoration> decorations;
/*     */   @Nullable
/*     */   private final MapItemSavedData.MapPatch colorPatch;
/*     */   
/*     */   public ClientboundMapItemDataPacket(int $$0, byte $$1, boolean $$2, @Nullable Collection<MapDecoration> $$3, @Nullable MapItemSavedData.MapPatch $$4) {
/*  24 */     this.mapId = $$0;
/*  25 */     this.scale = $$1;
/*  26 */     this.locked = $$2;
/*  27 */     this.decorations = ($$3 != null) ? Lists.newArrayList($$3) : null;
/*  28 */     this.colorPatch = $$4;
/*     */   }
/*     */   
/*     */   public ClientboundMapItemDataPacket(FriendlyByteBuf $$0) {
/*  32 */     this.mapId = $$0.readVarInt();
/*  33 */     this.scale = $$0.readByte();
/*  34 */     this.locked = $$0.readBoolean();
/*  35 */     this.decorations = (List<MapDecoration>)$$0.readNullable($$0 -> $$0.readList(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  46 */     int $$1 = $$0.readUnsignedByte();
/*  47 */     if ($$1 > 0) {
/*  48 */       int $$2 = $$0.readUnsignedByte();
/*  49 */       int $$3 = $$0.readUnsignedByte();
/*  50 */       int $$4 = $$0.readUnsignedByte();
/*  51 */       byte[] $$5 = $$0.readByteArray();
/*  52 */       this.colorPatch = new MapItemSavedData.MapPatch($$3, $$4, $$1, $$2, $$5);
/*     */     } else {
/*  54 */       this.colorPatch = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  60 */     $$0.writeVarInt(this.mapId);
/*  61 */     $$0.writeByte(this.scale);
/*  62 */     $$0.writeBoolean(this.locked);
/*     */ 
/*     */     
/*  65 */     $$0.writeNullable(this.decorations, ($$0, $$1) -> $$0.writeCollection($$1, ()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     if (this.colorPatch != null) {
/*  76 */       $$0.writeByte(this.colorPatch.width);
/*  77 */       $$0.writeByte(this.colorPatch.height);
/*  78 */       $$0.writeByte(this.colorPatch.startX);
/*  79 */       $$0.writeByte(this.colorPatch.startY);
/*  80 */       $$0.writeByteArray(this.colorPatch.mapColors);
/*     */     } else {
/*  82 */       $$0.writeByte(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/*  88 */     $$0.handleMapItemData(this);
/*     */   }
/*     */   
/*     */   public int getMapId() {
/*  92 */     return this.mapId;
/*     */   }
/*     */   
/*     */   public void applyToMap(MapItemSavedData $$0) {
/*  96 */     if (this.decorations != null) {
/*  97 */       $$0.addClientSideDecorations(this.decorations);
/*     */     }
/*  99 */     if (this.colorPatch != null) {
/* 100 */       this.colorPatch.applyToMap($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public byte getScale() {
/* 105 */     return this.scale;
/*     */   }
/*     */   
/*     */   public boolean isLocked() {
/* 109 */     return this.locked;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundMapItemDataPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */