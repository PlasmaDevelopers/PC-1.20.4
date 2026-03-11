/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.world.BossEvent;
/*     */ 
/*     */ public class ClientboundBossEventPacket
/*     */   implements Packet<ClientGamePacketListener> {
/*     */   private static final int FLAG_DARKEN = 1;
/*     */   private static final int FLAG_MUSIC = 2;
/*     */   private static final int FLAG_FOG = 4;
/*     */   private final UUID id;
/*     */   private final Operation operation;
/*     */   
/*     */   private ClientboundBossEventPacket(UUID $$0, Operation $$1) {
/*  20 */     this.id = $$0;
/*  21 */     this.operation = $$1;
/*     */   }
/*     */   
/*     */   public ClientboundBossEventPacket(FriendlyByteBuf $$0) {
/*  25 */     this.id = $$0.readUUID();
/*  26 */     OperationType $$1 = (OperationType)$$0.readEnum(OperationType.class);
/*  27 */     this.operation = $$1.reader.apply($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ClientboundBossEventPacket createAddPacket(BossEvent $$0) {
/*  32 */     return new ClientboundBossEventPacket($$0.getId(), new AddOperation($$0));
/*     */   }
/*     */   
/*     */   public static ClientboundBossEventPacket createRemovePacket(UUID $$0) {
/*  36 */     return new ClientboundBossEventPacket($$0, REMOVE_OPERATION);
/*     */   }
/*     */   
/*     */   public static ClientboundBossEventPacket createUpdateProgressPacket(BossEvent $$0) {
/*  40 */     return new ClientboundBossEventPacket($$0.getId(), new UpdateProgressOperation($$0.getProgress()));
/*     */   }
/*     */   
/*     */   public static ClientboundBossEventPacket createUpdateNamePacket(BossEvent $$0) {
/*  44 */     return new ClientboundBossEventPacket($$0.getId(), new UpdateNameOperation($$0.getName()));
/*     */   }
/*     */   
/*     */   public static ClientboundBossEventPacket createUpdateStylePacket(BossEvent $$0) {
/*  48 */     return new ClientboundBossEventPacket($$0.getId(), new UpdateStyleOperation($$0.getColor(), $$0.getOverlay()));
/*     */   }
/*     */   
/*     */   public static ClientboundBossEventPacket createUpdatePropertiesPacket(BossEvent $$0) {
/*  52 */     return new ClientboundBossEventPacket($$0.getId(), new UpdatePropertiesOperation($$0.shouldDarkenScreen(), $$0.shouldPlayBossMusic(), $$0.shouldCreateWorldFog()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  57 */     $$0.writeUUID(this.id);
/*  58 */     $$0.writeEnum(this.operation.getType());
/*  59 */     this.operation.write($$0);
/*     */   }
/*     */   
/*     */   static int encodeProperties(boolean $$0, boolean $$1, boolean $$2) {
/*  63 */     int $$3 = 0;
/*  64 */     if ($$0) {
/*  65 */       $$3 |= 0x1;
/*     */     }
/*  67 */     if ($$1) {
/*  68 */       $$3 |= 0x2;
/*     */     }
/*  70 */     if ($$2) {
/*  71 */       $$3 |= 0x4;
/*     */     }
/*  73 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/*  78 */     $$0.handleBossUpdate(this);
/*     */   }
/*     */   
/*     */   public void dispatch(Handler $$0) {
/*  82 */     this.operation.dispatch(this.id, $$0);
/*     */   }
/*     */   
/*     */   private enum OperationType {
/*  86 */     ADD((String)AddOperation::new),
/*  87 */     REMOVE((String)($$0 -> ClientboundBossEventPacket.REMOVE_OPERATION)),
/*  88 */     UPDATE_PROGRESS((String)UpdateProgressOperation::new),
/*  89 */     UPDATE_NAME((String)UpdateNameOperation::new),
/*  90 */     UPDATE_STYLE((String)UpdateStyleOperation::new),
/*  91 */     UPDATE_PROPERTIES((String)UpdatePropertiesOperation::new);
/*     */     
/*     */     final Function<FriendlyByteBuf, ClientboundBossEventPacket.Operation> reader;
/*     */ 
/*     */     
/*     */     OperationType(Function<FriendlyByteBuf, ClientboundBossEventPacket.Operation> $$0) {
/*  97 */       this.reader = $$0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface Handler
/*     */   {
/*     */     default void add(UUID $$0, Component $$1, float $$2, BossEvent.BossBarColor $$3, BossEvent.BossBarOverlay $$4, boolean $$5, boolean $$6, boolean $$7) {}
/*     */ 
/*     */     
/*     */     default void remove(UUID $$0) {}
/*     */ 
/*     */     
/*     */     default void updateProgress(UUID $$0, float $$1) {}
/*     */ 
/*     */     
/*     */     default void updateName(UUID $$0, Component $$1) {}
/*     */ 
/*     */     
/*     */     default void updateStyle(UUID $$0, BossEvent.BossBarColor $$1, BossEvent.BossBarOverlay $$2) {}
/*     */ 
/*     */     
/*     */     default void updateProperties(UUID $$0, boolean $$1, boolean $$2, boolean $$3) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static class AddOperation
/*     */     implements Operation
/*     */   {
/*     */     private final Component name;
/*     */     
/*     */     private final float progress;
/*     */     
/*     */     private final BossEvent.BossBarColor color;
/*     */     
/*     */     private final BossEvent.BossBarOverlay overlay;
/*     */     
/*     */     private final boolean darkenScreen;
/*     */     private final boolean playMusic;
/*     */     private final boolean createWorldFog;
/*     */     
/*     */     AddOperation(BossEvent $$0) {
/* 139 */       this.name = $$0.getName();
/* 140 */       this.progress = $$0.getProgress();
/* 141 */       this.color = $$0.getColor();
/* 142 */       this.overlay = $$0.getOverlay();
/* 143 */       this.darkenScreen = $$0.shouldDarkenScreen();
/* 144 */       this.playMusic = $$0.shouldPlayBossMusic();
/* 145 */       this.createWorldFog = $$0.shouldCreateWorldFog();
/*     */     }
/*     */     
/*     */     private AddOperation(FriendlyByteBuf $$0) {
/* 149 */       this.name = $$0.readComponentTrusted();
/* 150 */       this.progress = $$0.readFloat();
/* 151 */       this.color = (BossEvent.BossBarColor)$$0.readEnum(BossEvent.BossBarColor.class);
/* 152 */       this.overlay = (BossEvent.BossBarOverlay)$$0.readEnum(BossEvent.BossBarOverlay.class);
/* 153 */       int $$1 = $$0.readUnsignedByte();
/* 154 */       this.darkenScreen = (($$1 & 0x1) > 0);
/* 155 */       this.playMusic = (($$1 & 0x2) > 0);
/* 156 */       this.createWorldFog = (($$1 & 0x4) > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public ClientboundBossEventPacket.OperationType getType() {
/* 161 */       return ClientboundBossEventPacket.OperationType.ADD;
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispatch(UUID $$0, ClientboundBossEventPacket.Handler $$1) {
/* 166 */       $$1.add($$0, this.name, this.progress, this.color, this.overlay, this.darkenScreen, this.playMusic, this.createWorldFog);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 171 */       $$0.writeComponent(this.name);
/* 172 */       $$0.writeFloat(this.progress);
/* 173 */       $$0.writeEnum((Enum)this.color);
/* 174 */       $$0.writeEnum((Enum)this.overlay);
/* 175 */       $$0.writeByte(ClientboundBossEventPacket.encodeProperties(this.darkenScreen, this.playMusic, this.createWorldFog));
/*     */     }
/*     */   }
/*     */   
/* 179 */   static final Operation REMOVE_OPERATION = new Operation()
/*     */     {
/*     */       public ClientboundBossEventPacket.OperationType getType() {
/* 182 */         return ClientboundBossEventPacket.OperationType.REMOVE;
/*     */       }
/*     */ 
/*     */       
/*     */       public void dispatch(UUID $$0, ClientboundBossEventPacket.Handler $$1) {
/* 187 */         $$1.remove($$0);
/*     */       }
/*     */       
/*     */       public void write(FriendlyByteBuf $$0) {}
/*     */     };
/*     */   
/*     */   private static class UpdateProgressOperation
/*     */     implements Operation
/*     */   {
/*     */     private final float progress;
/*     */     
/*     */     UpdateProgressOperation(float $$0) {
/* 199 */       this.progress = $$0;
/*     */     }
/*     */     
/*     */     private UpdateProgressOperation(FriendlyByteBuf $$0) {
/* 203 */       this.progress = $$0.readFloat();
/*     */     }
/*     */ 
/*     */     
/*     */     public ClientboundBossEventPacket.OperationType getType() {
/* 208 */       return ClientboundBossEventPacket.OperationType.UPDATE_PROGRESS;
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispatch(UUID $$0, ClientboundBossEventPacket.Handler $$1) {
/* 213 */       $$1.updateProgress($$0, this.progress);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 218 */       $$0.writeFloat(this.progress);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class UpdateNameOperation implements Operation {
/*     */     private final Component name;
/*     */     
/*     */     UpdateNameOperation(Component $$0) {
/* 226 */       this.name = $$0;
/*     */     }
/*     */     
/*     */     private UpdateNameOperation(FriendlyByteBuf $$0) {
/* 230 */       this.name = $$0.readComponentTrusted();
/*     */     }
/*     */ 
/*     */     
/*     */     public ClientboundBossEventPacket.OperationType getType() {
/* 235 */       return ClientboundBossEventPacket.OperationType.UPDATE_NAME;
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispatch(UUID $$0, ClientboundBossEventPacket.Handler $$1) {
/* 240 */       $$1.updateName($$0, this.name);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 245 */       $$0.writeComponent(this.name);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class UpdateStyleOperation implements Operation {
/*     */     private final BossEvent.BossBarColor color;
/*     */     private final BossEvent.BossBarOverlay overlay;
/*     */     
/*     */     UpdateStyleOperation(BossEvent.BossBarColor $$0, BossEvent.BossBarOverlay $$1) {
/* 254 */       this.color = $$0;
/* 255 */       this.overlay = $$1;
/*     */     }
/*     */     
/*     */     private UpdateStyleOperation(FriendlyByteBuf $$0) {
/* 259 */       this.color = (BossEvent.BossBarColor)$$0.readEnum(BossEvent.BossBarColor.class);
/* 260 */       this.overlay = (BossEvent.BossBarOverlay)$$0.readEnum(BossEvent.BossBarOverlay.class);
/*     */     }
/*     */ 
/*     */     
/*     */     public ClientboundBossEventPacket.OperationType getType() {
/* 265 */       return ClientboundBossEventPacket.OperationType.UPDATE_STYLE;
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispatch(UUID $$0, ClientboundBossEventPacket.Handler $$1) {
/* 270 */       $$1.updateStyle($$0, this.color, this.overlay);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 275 */       $$0.writeEnum((Enum)this.color);
/* 276 */       $$0.writeEnum((Enum)this.overlay);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class UpdatePropertiesOperation implements Operation {
/*     */     private final boolean darkenScreen;
/*     */     private final boolean playMusic;
/*     */     private final boolean createWorldFog;
/*     */     
/*     */     UpdatePropertiesOperation(boolean $$0, boolean $$1, boolean $$2) {
/* 286 */       this.darkenScreen = $$0;
/* 287 */       this.playMusic = $$1;
/* 288 */       this.createWorldFog = $$2;
/*     */     }
/*     */     
/*     */     private UpdatePropertiesOperation(FriendlyByteBuf $$0) {
/* 292 */       int $$1 = $$0.readUnsignedByte();
/* 293 */       this.darkenScreen = (($$1 & 0x1) > 0);
/* 294 */       this.playMusic = (($$1 & 0x2) > 0);
/* 295 */       this.createWorldFog = (($$1 & 0x4) > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public ClientboundBossEventPacket.OperationType getType() {
/* 300 */       return ClientboundBossEventPacket.OperationType.UPDATE_PROPERTIES;
/*     */     }
/*     */ 
/*     */     
/*     */     public void dispatch(UUID $$0, ClientboundBossEventPacket.Handler $$1) {
/* 305 */       $$1.updateProperties($$0, this.darkenScreen, this.playMusic, this.createWorldFog);
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 310 */       $$0.writeByte(ClientboundBossEventPacket.encodeProperties(this.darkenScreen, this.playMusic, this.createWorldFog));
/*     */     }
/*     */   }
/*     */   
/*     */   private static interface Operation {
/*     */     ClientboundBossEventPacket.OperationType getType();
/*     */     
/*     */     void dispatch(UUID param1UUID, ClientboundBossEventPacket.Handler param1Handler);
/*     */     
/*     */     void write(FriendlyByteBuf param1FriendlyByteBuf);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBossEventPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */