/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.BossEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AddOperation
/*     */   implements ClientboundBossEventPacket.Operation
/*     */ {
/*     */   private final Component name;
/*     */   private final float progress;
/*     */   private final BossEvent.BossBarColor color;
/*     */   private final BossEvent.BossBarOverlay overlay;
/*     */   private final boolean darkenScreen;
/*     */   private final boolean playMusic;
/*     */   private final boolean createWorldFog;
/*     */   
/*     */   AddOperation(BossEvent $$0) {
/* 139 */     this.name = $$0.getName();
/* 140 */     this.progress = $$0.getProgress();
/* 141 */     this.color = $$0.getColor();
/* 142 */     this.overlay = $$0.getOverlay();
/* 143 */     this.darkenScreen = $$0.shouldDarkenScreen();
/* 144 */     this.playMusic = $$0.shouldPlayBossMusic();
/* 145 */     this.createWorldFog = $$0.shouldCreateWorldFog();
/*     */   }
/*     */   
/*     */   private AddOperation(FriendlyByteBuf $$0) {
/* 149 */     this.name = $$0.readComponentTrusted();
/* 150 */     this.progress = $$0.readFloat();
/* 151 */     this.color = (BossEvent.BossBarColor)$$0.readEnum(BossEvent.BossBarColor.class);
/* 152 */     this.overlay = (BossEvent.BossBarOverlay)$$0.readEnum(BossEvent.BossBarOverlay.class);
/* 153 */     int $$1 = $$0.readUnsignedByte();
/* 154 */     this.darkenScreen = (($$1 & 0x1) > 0);
/* 155 */     this.playMusic = (($$1 & 0x2) > 0);
/* 156 */     this.createWorldFog = (($$1 & 0x4) > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBossEventPacket.OperationType getType() {
/* 161 */     return ClientboundBossEventPacket.OperationType.ADD;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispatch(UUID $$0, ClientboundBossEventPacket.Handler $$1) {
/* 166 */     $$1.add($$0, this.name, this.progress, this.color, this.overlay, this.darkenScreen, this.playMusic, this.createWorldFog);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 171 */     $$0.writeComponent(this.name);
/* 172 */     $$0.writeFloat(this.progress);
/* 173 */     $$0.writeEnum((Enum)this.color);
/* 174 */     $$0.writeEnum((Enum)this.overlay);
/* 175 */     $$0.writeByte(ClientboundBossEventPacket.encodeProperties(this.darkenScreen, this.playMusic, this.createWorldFog));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBossEventPacket$AddOperation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */