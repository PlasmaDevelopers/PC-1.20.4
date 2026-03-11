/*     */ package net.minecraft.client.gui.screens.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.screens.FaviconTexture;
/*     */ import net.minecraft.client.gui.screens.LoadingDotsText;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.multiplayer.ServerList;
/*     */ import net.minecraft.client.server.LanServer;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerSelectionList extends ObjectSelectionList<ServerSelectionList.Entry> {
/*  40 */   static final ResourceLocation INCOMPATIBLE_SPRITE = new ResourceLocation("server_list/incompatible");
/*  41 */   static final ResourceLocation UNREACHABLE_SPRITE = new ResourceLocation("server_list/unreachable");
/*  42 */   static final ResourceLocation PING_1_SPRITE = new ResourceLocation("server_list/ping_1");
/*  43 */   static final ResourceLocation PING_2_SPRITE = new ResourceLocation("server_list/ping_2");
/*  44 */   static final ResourceLocation PING_3_SPRITE = new ResourceLocation("server_list/ping_3");
/*  45 */   static final ResourceLocation PING_4_SPRITE = new ResourceLocation("server_list/ping_4");
/*  46 */   static final ResourceLocation PING_5_SPRITE = new ResourceLocation("server_list/ping_5");
/*  47 */   static final ResourceLocation PINGING_1_SPRITE = new ResourceLocation("server_list/pinging_1");
/*  48 */   static final ResourceLocation PINGING_2_SPRITE = new ResourceLocation("server_list/pinging_2");
/*  49 */   static final ResourceLocation PINGING_3_SPRITE = new ResourceLocation("server_list/pinging_3");
/*  50 */   static final ResourceLocation PINGING_4_SPRITE = new ResourceLocation("server_list/pinging_4");
/*  51 */   static final ResourceLocation PINGING_5_SPRITE = new ResourceLocation("server_list/pinging_5");
/*  52 */   static final ResourceLocation JOIN_HIGHLIGHTED_SPRITE = new ResourceLocation("server_list/join_highlighted");
/*  53 */   static final ResourceLocation JOIN_SPRITE = new ResourceLocation("server_list/join");
/*  54 */   static final ResourceLocation MOVE_UP_HIGHLIGHTED_SPRITE = new ResourceLocation("server_list/move_up_highlighted");
/*  55 */   static final ResourceLocation MOVE_UP_SPRITE = new ResourceLocation("server_list/move_up");
/*  56 */   static final ResourceLocation MOVE_DOWN_HIGHLIGHTED_SPRITE = new ResourceLocation("server_list/move_down_highlighted");
/*  57 */   static final ResourceLocation MOVE_DOWN_SPRITE = new ResourceLocation("server_list/move_down");
/*  58 */   static final Logger LOGGER = LogUtils.getLogger();
/*  59 */   static final ThreadPoolExecutor THREAD_POOL = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER)).build());
/*  60 */   private static final ResourceLocation ICON_MISSING = new ResourceLocation("textures/misc/unknown_server.png");
/*     */   
/*  62 */   static final Component SCANNING_LABEL = (Component)Component.translatable("lanServer.scanning");
/*  63 */   static final Component CANT_RESOLVE_TEXT = (Component)Component.translatable("multiplayer.status.cannot_resolve").withColor(-65536);
/*  64 */   static final Component CANT_CONNECT_TEXT = (Component)Component.translatable("multiplayer.status.cannot_connect").withColor(-65536);
/*  65 */   static final Component INCOMPATIBLE_STATUS = (Component)Component.translatable("multiplayer.status.incompatible");
/*  66 */   static final Component NO_CONNECTION_STATUS = (Component)Component.translatable("multiplayer.status.no_connection");
/*  67 */   static final Component PINGING_STATUS = (Component)Component.translatable("multiplayer.status.pinging");
/*  68 */   static final Component ONLINE_STATUS = (Component)Component.translatable("multiplayer.status.online");
/*     */   
/*     */   private final JoinMultiplayerScreen screen;
/*  71 */   private final List<OnlineServerEntry> onlineServers = Lists.newArrayList();
/*  72 */   private final Entry lanHeader = new LANHeader();
/*  73 */   private final List<NetworkServerEntry> networkServers = Lists.newArrayList();
/*     */   
/*     */   public ServerSelectionList(JoinMultiplayerScreen $$0, Minecraft $$1, int $$2, int $$3, int $$4, int $$5) {
/*  76 */     super($$1, $$2, $$3, $$4, $$5);
/*  77 */     this.screen = $$0;
/*     */   }
/*     */   
/*     */   private void refreshEntries() {
/*  81 */     clearEntries();
/*  82 */     this.onlineServers.forEach($$1 -> $$0.addEntry($$1));
/*  83 */     addEntry((AbstractSelectionList.Entry)this.lanHeader);
/*  84 */     this.networkServers.forEach($$1 -> $$0.addEntry($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable Entry $$0) {
/*  89 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/*  90 */     this.screen.onSelectedChange();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  95 */     Entry $$3 = (Entry)getSelected();
/*  96 */     return (($$3 != null && $$3.keyPressed($$0, $$1, $$2)) || super.keyPressed($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public void updateOnlineServers(ServerList $$0) {
/* 100 */     this.onlineServers.clear();
/*     */     
/* 102 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 103 */       this.onlineServers.add(new OnlineServerEntry(this.screen, $$0.get($$1)));
/*     */     }
/*     */     
/* 106 */     refreshEntries();
/*     */   }
/*     */   
/*     */   public void updateNetworkServers(List<LanServer> $$0) {
/* 110 */     int $$1 = $$0.size() - this.networkServers.size();
/* 111 */     this.networkServers.clear();
/*     */     
/* 113 */     for (LanServer $$2 : $$0) {
/* 114 */       this.networkServers.add(new NetworkServerEntry(this.screen, $$2));
/*     */     }
/* 116 */     refreshEntries();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     for (int $$3 = this.networkServers.size() - $$1; $$3 < this.networkServers.size(); $$3++) {
/* 122 */       NetworkServerEntry $$4 = this.networkServers.get($$3);
/* 123 */       int $$5 = $$3 - this.networkServers.size() + children().size();
/* 124 */       int $$6 = getRowTop($$5);
/* 125 */       int $$7 = getRowBottom($$5);
/* 126 */       if ($$7 >= getY() && $$6 <= getBottom()) {
/* 127 */         this.minecraft.getNarrator().say((Component)Component.translatable("multiplayer.lan.server_found", new Object[] { $$4.getServerNarration() }));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollbarPosition() {
/* 134 */     return super.getScrollbarPosition() + 30;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 139 */     return super.getRowWidth() + 85;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {}
/*     */   
/*     */   public static abstract class Entry
/*     */     extends ObjectSelectionList.Entry<Entry>
/*     */     implements AutoCloseable
/*     */   {
/*     */     public void close() {}
/*     */   }
/*     */   
/*     */   public static class LANHeader
/*     */     extends Entry
/*     */   {
/* 155 */     private final Minecraft minecraft = Minecraft.getInstance();
/*     */ 
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 160 */       Objects.requireNonNull(this.minecraft.font); int $$10 = $$2 + $$5 / 2 - 9 / 2;
/* 161 */       $$0.drawString(this.minecraft.font, ServerSelectionList.SCANNING_LABEL, this.minecraft.screen.width / 2 - this.minecraft.font.width((FormattedText)ServerSelectionList.SCANNING_LABEL) / 2, $$10, 16777215, false);
/* 162 */       String $$11 = LoadingDotsText.get(Util.getMillis());
/* 163 */       Objects.requireNonNull(this.minecraft.font); $$0.drawString(this.minecraft.font, $$11, this.minecraft.screen.width / 2 - this.minecraft.font.width($$11) / 2, $$10 + 9, -8355712, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 168 */       return ServerSelectionList.SCANNING_LABEL;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NetworkServerEntry extends Entry {
/*     */     private static final int ICON_WIDTH = 32;
/* 174 */     private static final Component LAN_SERVER_HEADER = (Component)Component.translatable("lanServer.title");
/* 175 */     private static final Component HIDDEN_ADDRESS_TEXT = (Component)Component.translatable("selectServer.hiddenAddress");
/*     */     
/*     */     private final JoinMultiplayerScreen screen;
/*     */     protected final Minecraft minecraft;
/*     */     protected final LanServer serverData;
/*     */     private long lastClickTime;
/*     */     
/*     */     protected NetworkServerEntry(JoinMultiplayerScreen $$0, LanServer $$1) {
/* 183 */       this.screen = $$0;
/* 184 */       this.serverData = $$1;
/* 185 */       this.minecraft = Minecraft.getInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 190 */       $$0.drawString(this.minecraft.font, LAN_SERVER_HEADER, $$3 + 32 + 3, $$2 + 1, 16777215, false);
/* 191 */       $$0.drawString(this.minecraft.font, this.serverData.getMotd(), $$3 + 32 + 3, $$2 + 12, -8355712, false);
/*     */       
/* 193 */       if (this.minecraft.options.hideServerAddress) {
/* 194 */         $$0.drawString(this.minecraft.font, HIDDEN_ADDRESS_TEXT, $$3 + 32 + 3, $$2 + 12 + 11, 3158064, false);
/*     */       } else {
/* 196 */         $$0.drawString(this.minecraft.font, this.serverData.getAddress(), $$3 + 32 + 3, $$2 + 12 + 11, 3158064, false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 202 */       this.screen.setSelected(this);
/* 203 */       if (Util.getMillis() - this.lastClickTime < 250L) {
/* 204 */         this.screen.joinSelectedServer();
/*     */       }
/* 206 */       this.lastClickTime = Util.getMillis();
/* 207 */       return false;
/*     */     }
/*     */     
/*     */     public LanServer getServerData() {
/* 211 */       return this.serverData;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 216 */       return (Component)Component.translatable("narrator.select", new Object[] { getServerNarration() });
/*     */     }
/*     */     
/*     */     public Component getServerNarration() {
/* 220 */       return (Component)Component.empty().append(LAN_SERVER_HEADER).append(CommonComponents.SPACE).append(this.serverData.getMotd());
/*     */     }
/*     */   }
/*     */   
/*     */   public class OnlineServerEntry
/*     */     extends Entry {
/*     */     private static final int ICON_WIDTH = 32;
/*     */     private static final int ICON_HEIGHT = 32;
/*     */     private static final int ICON_OVERLAY_X_MOVE_LEFT = 32;
/*     */     private final JoinMultiplayerScreen screen;
/*     */     private final Minecraft minecraft;
/*     */     private final ServerData serverData;
/*     */     private final FaviconTexture icon;
/*     */     @Nullable
/*     */     private byte[] lastIconBytes;
/*     */     private long lastClickTime;
/*     */     
/*     */     protected OnlineServerEntry(JoinMultiplayerScreen $$1, ServerData $$2) {
/* 238 */       this.screen = $$1;
/* 239 */       this.serverData = $$2;
/* 240 */       this.minecraft = Minecraft.getInstance();
/* 241 */       this.icon = FaviconTexture.forServer(this.minecraft.getTextureManager(), $$2.ip);
/*     */     } public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       ResourceLocation $$29;
/*     */       List<Component> $$31;
/*     */       Component $$30;
/* 246 */       if (!this.serverData.pinged) {
/* 247 */         this.serverData.pinged = true;
/* 248 */         this.serverData.ping = -2L;
/* 249 */         this.serverData.motd = CommonComponents.EMPTY;
/* 250 */         this.serverData.status = CommonComponents.EMPTY;
/*     */         
/* 252 */         ServerSelectionList.THREAD_POOL.submit(() -> {
/*     */               try {
/*     */                 this.screen.getPinger().pingServer(this.serverData, ());
/* 255 */               } catch (UnknownHostException $$0) {
/*     */                 this.serverData.ping = -1L;
/*     */                 this.serverData.motd = ServerSelectionList.CANT_RESOLVE_TEXT;
/* 258 */               } catch (Exception $$1) {
/*     */                 this.serverData.ping = -1L;
/*     */                 
/*     */                 this.serverData.motd = ServerSelectionList.CANT_CONNECT_TEXT;
/*     */               } 
/*     */             });
/*     */       } 
/* 265 */       boolean $$10 = !isCompatible();
/*     */       
/* 267 */       $$0.drawString(this.minecraft.font, this.serverData.name, $$3 + 32 + 3, $$2 + 1, 16777215, false);
/*     */       
/* 269 */       List<FormattedCharSequence> $$11 = this.minecraft.font.split((FormattedText)this.serverData.motd, $$4 - 32 - 2);
/* 270 */       for (int $$12 = 0; $$12 < Math.min($$11.size(), 2); $$12++) {
/* 271 */         Objects.requireNonNull(this.minecraft.font); $$0.drawString(this.minecraft.font, $$11.get($$12), $$3 + 32 + 3, $$2 + 12 + 9 * $$12, -8355712, false);
/*     */       } 
/*     */       
/* 274 */       Component $$13 = $$10 ? (Component)this.serverData.version.copy().withStyle(ChatFormatting.RED) : this.serverData.status;
/* 275 */       int $$14 = this.minecraft.font.width((FormattedText)$$13);
/* 276 */       $$0.drawString(this.minecraft.font, $$13, $$3 + $$4 - $$14 - 15 - 2, $$2 + 1, -8355712, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 282 */       if ($$10) {
/* 283 */         ResourceLocation $$15 = ServerSelectionList.INCOMPATIBLE_SPRITE;
/* 284 */         Component $$16 = ServerSelectionList.INCOMPATIBLE_STATUS;
/* 285 */         List<Component> $$17 = this.serverData.playerList;
/* 286 */       } else if (pingCompleted()) {
/* 287 */         if (this.serverData.ping < 0L) {
/* 288 */           ResourceLocation $$18 = ServerSelectionList.UNREACHABLE_SPRITE;
/* 289 */         } else if (this.serverData.ping < 150L) {
/* 290 */           ResourceLocation $$19 = ServerSelectionList.PING_5_SPRITE;
/* 291 */         } else if (this.serverData.ping < 300L) {
/* 292 */           ResourceLocation $$20 = ServerSelectionList.PING_4_SPRITE;
/* 293 */         } else if (this.serverData.ping < 600L) {
/* 294 */           ResourceLocation $$21 = ServerSelectionList.PING_3_SPRITE;
/* 295 */         } else if (this.serverData.ping < 1000L) {
/* 296 */           ResourceLocation $$22 = ServerSelectionList.PING_2_SPRITE;
/*     */         } else {
/* 298 */           ResourceLocation $$23 = ServerSelectionList.PING_1_SPRITE;
/*     */         } 
/*     */         
/* 301 */         if (this.serverData.ping < 0L) {
/* 302 */           Component $$24 = ServerSelectionList.NO_CONNECTION_STATUS;
/* 303 */           List<Component> $$25 = Collections.emptyList();
/*     */         } else {
/* 305 */           MutableComponent mutableComponent = Component.translatable("multiplayer.status.ping", new Object[] { Long.valueOf(this.serverData.ping) });
/* 306 */           List<Component> $$27 = this.serverData.playerList;
/*     */         } 
/*     */       } else {
/* 309 */         int $$28 = (int)(Util.getMillis() / 100L + ($$1 * 2) & 0x7L);
/* 310 */         if ($$28 > 4) {
/* 311 */           $$28 = 8 - $$28;
/*     */         }
/* 313 */         switch ($$28) { default: 
/*     */           case 1: 
/*     */           case 2: 
/*     */           case 3: 
/*     */           case 4:
/* 318 */             break; }  $$29 = ServerSelectionList.PINGING_5_SPRITE;
/*     */         
/* 320 */         $$30 = ServerSelectionList.PINGING_STATUS;
/* 321 */         $$31 = Collections.emptyList();
/*     */       } 
/*     */       
/* 324 */       $$0.blitSprite($$29, $$3 + $$4 - 15, $$2, 10, 8);
/*     */       
/* 326 */       byte[] $$32 = this.serverData.getIconBytes();
/* 327 */       if (!Arrays.equals($$32, this.lastIconBytes)) {
/* 328 */         if (uploadServerIcon($$32)) {
/* 329 */           this.lastIconBytes = $$32;
/*     */         } else {
/* 331 */           this.serverData.setIconBytes(null);
/* 332 */           updateServerList();
/*     */         } 
/*     */       }
/*     */       
/* 336 */       drawIcon($$0, $$3, $$2, this.icon.textureLocation());
/*     */       
/* 338 */       int $$33 = $$6 - $$3;
/* 339 */       int $$34 = $$7 - $$2;
/* 340 */       if ($$33 >= $$4 - 15 && $$33 <= $$4 - 5 && $$34 >= 0 && $$34 <= 8) {
/* 341 */         this.screen.setToolTip(Collections.singletonList($$30));
/* 342 */       } else if ($$33 >= $$4 - $$14 - 15 - 2 && $$33 <= $$4 - 15 - 2 && $$34 >= 0 && $$34 <= 8) {
/* 343 */         this.screen.setToolTip($$31);
/*     */       } 
/*     */       
/* 346 */       if (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue() || $$8) {
/* 347 */         $$0.fill($$3, $$2, $$3 + 32, $$2 + 32, -1601138544);
/* 348 */         int $$35 = $$6 - $$3;
/* 349 */         int $$36 = $$7 - $$2;
/*     */         
/* 351 */         if (canJoin()) {
/* 352 */           if ($$35 < 32 && $$35 > 16) {
/* 353 */             $$0.blitSprite(ServerSelectionList.JOIN_HIGHLIGHTED_SPRITE, $$3, $$2, 32, 32);
/*     */           } else {
/* 355 */             $$0.blitSprite(ServerSelectionList.JOIN_SPRITE, $$3, $$2, 32, 32);
/*     */           } 
/*     */         }
/* 358 */         if ($$1 > 0) {
/* 359 */           if ($$35 < 16 && $$36 < 16) {
/* 360 */             $$0.blitSprite(ServerSelectionList.MOVE_UP_HIGHLIGHTED_SPRITE, $$3, $$2, 32, 32);
/*     */           } else {
/* 362 */             $$0.blitSprite(ServerSelectionList.MOVE_UP_SPRITE, $$3, $$2, 32, 32);
/*     */           } 
/*     */         }
/* 365 */         if ($$1 < this.screen.getServers().size() - 1) {
/* 366 */           if ($$35 < 16 && $$36 > 16) {
/* 367 */             $$0.blitSprite(ServerSelectionList.MOVE_DOWN_HIGHLIGHTED_SPRITE, $$3, $$2, 32, 32);
/*     */           } else {
/* 369 */             $$0.blitSprite(ServerSelectionList.MOVE_DOWN_SPRITE, $$3, $$2, 32, 32);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean pingCompleted() {
/* 376 */       return (this.serverData.pinged && this.serverData.ping != -2L);
/*     */     }
/*     */     
/*     */     private boolean isCompatible() {
/* 380 */       return (this.serverData.protocol == SharedConstants.getCurrentVersion().getProtocolVersion());
/*     */     }
/*     */     
/*     */     public void updateServerList() {
/* 384 */       this.screen.getServers().save();
/*     */     }
/*     */     
/*     */     protected void drawIcon(GuiGraphics $$0, int $$1, int $$2, ResourceLocation $$3) {
/* 388 */       RenderSystem.enableBlend();
/* 389 */       $$0.blit($$3, $$1, $$2, 0.0F, 0.0F, 32, 32, 32, 32);
/* 390 */       RenderSystem.disableBlend();
/*     */     }
/*     */     
/*     */     private boolean canJoin() {
/* 394 */       return true;
/*     */     }
/*     */     
/*     */     private boolean uploadServerIcon(@Nullable byte[] $$0) {
/* 398 */       if ($$0 == null) {
/* 399 */         this.icon.clear();
/*     */       } else {
/*     */         try {
/* 402 */           this.icon.upload(NativeImage.read($$0));
/* 403 */         } catch (Throwable $$1) {
/* 404 */           ServerSelectionList.LOGGER.error("Invalid icon for server {} ({})", new Object[] { this.serverData.name, this.serverData.ip, $$1 });
/* 405 */           return false;
/*     */         } 
/*     */       } 
/* 408 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 413 */       if (Screen.hasShiftDown()) {
/*     */         
/* 415 */         ServerSelectionList $$3 = this.screen.serverSelectionList;
/* 416 */         int $$4 = $$3.children().indexOf(this);
/*     */         
/* 418 */         if ($$4 == -1) {
/* 419 */           return true;
/*     */         }
/*     */         
/* 422 */         if (($$0 == 264 && $$4 < this.screen.getServers().size() - 1) || ($$0 == 265 && $$4 > 0)) {
/*     */ 
/*     */           
/* 425 */           swap($$4, ($$0 == 264) ? ($$4 + 1) : ($$4 - 1));
/* 426 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 430 */       return super.keyPressed($$0, $$1, $$2);
/*     */     }
/*     */     
/*     */     private void swap(int $$0, int $$1) {
/* 434 */       this.screen.getServers().swap($$0, $$1);
/* 435 */       this.screen.serverSelectionList.updateOnlineServers(this.screen.getServers());
/*     */       
/* 437 */       ServerSelectionList.Entry $$2 = this.screen.serverSelectionList.children().get($$1);
/* 438 */       this.screen.serverSelectionList.setSelected($$2);
/* 439 */       ServerSelectionList.this.ensureVisible((AbstractSelectionList.Entry)$$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 444 */       double $$3 = $$0 - ServerSelectionList.this.getRowLeft();
/* 445 */       double $$4 = $$1 - ServerSelectionList.this.getRowTop(ServerSelectionList.this.children().indexOf(this));
/*     */       
/* 447 */       if ($$3 <= 32.0D) {
/* 448 */         if ($$3 < 32.0D && $$3 > 16.0D && canJoin()) {
/*     */           
/* 450 */           this.screen.setSelected(this);
/* 451 */           this.screen.joinSelectedServer();
/* 452 */           return true;
/*     */         } 
/*     */         
/* 455 */         int $$5 = this.screen.serverSelectionList.children().indexOf(this);
/*     */         
/* 457 */         if ($$3 < 16.0D && $$4 < 16.0D && $$5 > 0) {
/* 458 */           swap($$5, $$5 - 1);
/* 459 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 463 */         if ($$3 < 16.0D && $$4 > 16.0D && $$5 < this.screen.getServers().size() - 1) {
/* 464 */           swap($$5, $$5 + 1);
/* 465 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 469 */       this.screen.setSelected(this);
/* 470 */       if (Util.getMillis() - this.lastClickTime < 250L) {
/* 471 */         this.screen.joinSelectedServer();
/*     */       }
/*     */       
/* 474 */       this.lastClickTime = Util.getMillis();
/*     */       
/* 476 */       return true;
/*     */     }
/*     */     
/*     */     public ServerData getServerData() {
/* 480 */       return this.serverData;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 485 */       MutableComponent $$0 = Component.empty();
/* 486 */       $$0.append((Component)Component.translatable("narrator.select", new Object[] { this.serverData.name }));
/* 487 */       $$0.append(CommonComponents.NARRATION_SEPARATOR);
/* 488 */       if (!isCompatible()) {
/* 489 */         $$0.append(ServerSelectionList.INCOMPATIBLE_STATUS);
/* 490 */         $$0.append(CommonComponents.NARRATION_SEPARATOR);
/* 491 */         $$0.append((Component)Component.translatable("multiplayer.status.version.narration", new Object[] { this.serverData.version }));
/* 492 */         $$0.append(CommonComponents.NARRATION_SEPARATOR);
/* 493 */         $$0.append((Component)Component.translatable("multiplayer.status.motd.narration", new Object[] { this.serverData.motd }));
/* 494 */       } else if (this.serverData.ping < 0L) {
/* 495 */         $$0.append(ServerSelectionList.NO_CONNECTION_STATUS);
/* 496 */       } else if (!pingCompleted()) {
/* 497 */         $$0.append(ServerSelectionList.PINGING_STATUS);
/*     */       } else {
/* 499 */         $$0.append(ServerSelectionList.ONLINE_STATUS);
/* 500 */         $$0.append(CommonComponents.NARRATION_SEPARATOR);
/* 501 */         $$0.append((Component)Component.translatable("multiplayer.status.ping.narration", new Object[] { Long.valueOf(this.serverData.ping) }));
/* 502 */         $$0.append(CommonComponents.NARRATION_SEPARATOR);
/* 503 */         $$0.append((Component)Component.translatable("multiplayer.status.motd.narration", new Object[] { this.serverData.motd }));
/* 504 */         if (this.serverData.players != null) {
/* 505 */           $$0.append(CommonComponents.NARRATION_SEPARATOR);
/* 506 */           $$0.append((Component)Component.translatable("multiplayer.status.player_count.narration", new Object[] { Integer.valueOf(this.serverData.players.online()), Integer.valueOf(this.serverData.players.max()) }));
/* 507 */           $$0.append(CommonComponents.NARRATION_SEPARATOR);
/* 508 */           $$0.append(ComponentUtils.formatList(this.serverData.playerList, (Component)Component.literal(", ")));
/*     */         } 
/*     */       } 
/* 511 */       return (Component)$$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 516 */       this.icon.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\multiplayer\ServerSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */