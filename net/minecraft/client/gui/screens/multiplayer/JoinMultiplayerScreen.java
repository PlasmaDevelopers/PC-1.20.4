/*     */ package net.minecraft.client.gui.screens.multiplayer;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.EqualSpacingLayout;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.layouts.SpacerElement;
/*     */ import net.minecraft.client.gui.navigation.CommonInputs;
/*     */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.ConnectScreen;
/*     */ import net.minecraft.client.gui.screens.DirectJoinServerScreen;
/*     */ import net.minecraft.client.gui.screens.EditServerScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.multiplayer.ServerList;
/*     */ import net.minecraft.client.multiplayer.ServerStatusPinger;
/*     */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.client.server.LanServer;
/*     */ import net.minecraft.client.server.LanServerDetection;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class JoinMultiplayerScreen
/*     */   extends Screen {
/*     */   public static final int BUTTON_ROW_WIDTH = 308;
/*     */   public static final int TOP_ROW_BUTTON_WIDTH = 100;
/*  36 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final int LOWER_ROW_BUTTON_WIDTH = 74;
/*     */   public static final int FOOTER_HEIGHT = 64;
/*  38 */   private final ServerStatusPinger pinger = new ServerStatusPinger();
/*     */   private final Screen lastScreen;
/*     */   protected ServerSelectionList serverSelectionList;
/*     */   private ServerList servers;
/*     */   private Button editButton;
/*     */   private Button selectButton;
/*     */   private Button deleteButton;
/*     */   @Nullable
/*     */   private List<Component> toolTip;
/*     */   private ServerData editingServer;
/*     */   private LanServerDetection.LanServerList lanServerList;
/*     */   @Nullable
/*     */   private LanServerDetection.LanServerDetector lanServerDetector;
/*     */   private boolean initedOnce;
/*     */   
/*     */   public JoinMultiplayerScreen(Screen $$0) {
/*  54 */     super((Component)Component.translatable("multiplayer.title"));
/*  55 */     this.lastScreen = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  60 */     if (this.initedOnce) {
/*  61 */       this.serverSelectionList.setRectangle(this.width, this.height - 64 - 32, 0, 32);
/*     */     } else {
/*  63 */       this.initedOnce = true;
/*     */       
/*  65 */       this.servers = new ServerList(this.minecraft);
/*  66 */       this.servers.load();
/*     */       
/*  68 */       this.lanServerList = new LanServerDetection.LanServerList();
/*     */       try {
/*  70 */         this.lanServerDetector = new LanServerDetection.LanServerDetector(this.lanServerList);
/*  71 */         this.lanServerDetector.start();
/*  72 */       } catch (Exception $$0) {
/*  73 */         LOGGER.warn("Unable to start LAN server detection: {}", $$0.getMessage());
/*     */       } 
/*     */       
/*  76 */       this.serverSelectionList = new ServerSelectionList(this, this.minecraft, this.width, this.height - 64 - 32, 32, 36);
/*  77 */       this.serverSelectionList.updateOnlineServers(this.servers);
/*     */     } 
/*     */     
/*  80 */     addWidget((GuiEventListener)this.serverSelectionList);
/*     */     
/*  82 */     this.selectButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectServer.select"), $$0 -> joinSelectedServer()).width(100).build());
/*  83 */     Button $$1 = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectServer.direct"), $$0 -> {
/*     */             this.editingServer = new ServerData(I18n.get("selectServer.defaultName", new Object[0]), "", ServerData.Type.OTHER);
/*     */             this.minecraft.setScreen((Screen)new DirectJoinServerScreen(this, this::directJoinCallback, this.editingServer));
/*  86 */           }).width(100).build());
/*  87 */     Button $$2 = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectServer.add"), $$0 -> {
/*     */             this.editingServer = new ServerData(I18n.get("selectServer.defaultName", new Object[0]), "", ServerData.Type.OTHER);
/*     */             this.minecraft.setScreen((Screen)new EditServerScreen(this, this::addServerCallback, this.editingServer));
/*  90 */           }).width(100).build());
/*     */     
/*  92 */     this.editButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectServer.edit"), $$0 -> {
/*     */             ServerSelectionList.Entry $$1 = (ServerSelectionList.Entry)this.serverSelectionList.getSelected();
/*     */             
/*     */             if ($$1 instanceof ServerSelectionList.OnlineServerEntry) {
/*     */               ServerData $$2 = ((ServerSelectionList.OnlineServerEntry)$$1).getServerData();
/*     */               
/*     */               this.editingServer = new ServerData($$2.name, $$2.ip, ServerData.Type.OTHER);
/*     */               this.editingServer.copyFrom($$2);
/*     */               this.minecraft.setScreen((Screen)new EditServerScreen(this, this::editServerCallback, this.editingServer));
/*     */             } 
/* 102 */           }).width(74).build());
/* 103 */     this.deleteButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectServer.delete"), $$0 -> {
/*     */             ServerSelectionList.Entry $$1 = (ServerSelectionList.Entry)this.serverSelectionList.getSelected();
/*     */             
/*     */             if ($$1 instanceof ServerSelectionList.OnlineServerEntry) {
/*     */               String $$2 = (((ServerSelectionList.OnlineServerEntry)$$1).getServerData()).name;
/*     */               if ($$2 != null) {
/*     */                 MutableComponent mutableComponent1 = Component.translatable("selectServer.deleteQuestion");
/*     */                 MutableComponent mutableComponent2 = Component.translatable("selectServer.deleteWarning", new Object[] { $$2 });
/*     */                 MutableComponent mutableComponent3 = Component.translatable("selectServer.deleteButton");
/*     */                 Component $$6 = CommonComponents.GUI_CANCEL;
/*     */                 this.minecraft.setScreen((Screen)new ConfirmScreen(this::deleteCallback, (Component)mutableComponent1, (Component)mutableComponent2, (Component)mutableComponent3, $$6));
/*     */               } 
/*     */             } 
/* 116 */           }).width(74).build());
/* 117 */     Button $$3 = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("selectServer.refresh"), $$0 -> refreshServerList()).width(74).build());
/* 118 */     Button $$4 = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> this.minecraft.setScreen(this.lastScreen)).width(74).build());
/*     */ 
/*     */     
/* 121 */     LinearLayout $$5 = LinearLayout.vertical();
/* 122 */     EqualSpacingLayout $$6 = (EqualSpacingLayout)$$5.addChild((LayoutElement)new EqualSpacingLayout(308, 20, EqualSpacingLayout.Orientation.HORIZONTAL));
/* 123 */     $$6.addChild((LayoutElement)this.selectButton);
/* 124 */     $$6.addChild((LayoutElement)$$1);
/* 125 */     $$6.addChild((LayoutElement)$$2);
/*     */     
/* 127 */     $$5.addChild((LayoutElement)SpacerElement.height(4));
/*     */     
/* 129 */     EqualSpacingLayout $$7 = (EqualSpacingLayout)$$5.addChild((LayoutElement)new EqualSpacingLayout(308, 20, EqualSpacingLayout.Orientation.HORIZONTAL));
/* 130 */     $$7.addChild((LayoutElement)this.editButton);
/* 131 */     $$7.addChild((LayoutElement)this.deleteButton);
/* 132 */     $$7.addChild((LayoutElement)$$3);
/* 133 */     $$7.addChild((LayoutElement)$$4);
/*     */     
/* 135 */     $$5.arrangeElements();
/* 136 */     FrameLayout.centerInRectangle((LayoutElement)$$5, 0, this.height - 64, this.width, 64);
/*     */     
/* 138 */     onSelectedChange();
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 143 */     super.tick();
/*     */     
/* 145 */     List<LanServer> $$0 = this.lanServerList.takeDirtyServers();
/* 146 */     if ($$0 != null) {
/* 147 */       this.serverSelectionList.updateNetworkServers($$0);
/*     */     }
/*     */     
/* 150 */     this.pinger.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/* 155 */     if (this.lanServerDetector != null) {
/* 156 */       this.lanServerDetector.interrupt();
/* 157 */       this.lanServerDetector = null;
/*     */     } 
/* 159 */     this.pinger.removeAll();
/* 160 */     this.serverSelectionList.removed();
/*     */   }
/*     */   
/*     */   private void refreshServerList() {
/* 164 */     this.minecraft.setScreen(new JoinMultiplayerScreen(this.lastScreen));
/*     */   }
/*     */   
/*     */   private void deleteCallback(boolean $$0) {
/* 168 */     ServerSelectionList.Entry $$1 = (ServerSelectionList.Entry)this.serverSelectionList.getSelected();
/* 169 */     if ($$0 && $$1 instanceof ServerSelectionList.OnlineServerEntry) {
/* 170 */       this.servers.remove(((ServerSelectionList.OnlineServerEntry)$$1).getServerData());
/* 171 */       this.servers.save();
/* 172 */       this.serverSelectionList.setSelected((ServerSelectionList.Entry)null);
/* 173 */       this.serverSelectionList.updateOnlineServers(this.servers);
/*     */     } 
/* 175 */     this.minecraft.setScreen(this);
/*     */   }
/*     */   
/*     */   private void editServerCallback(boolean $$0) {
/* 179 */     ServerSelectionList.Entry $$1 = (ServerSelectionList.Entry)this.serverSelectionList.getSelected();
/* 180 */     if ($$0 && $$1 instanceof ServerSelectionList.OnlineServerEntry) {
/* 181 */       ServerData $$2 = ((ServerSelectionList.OnlineServerEntry)$$1).getServerData();
/* 182 */       $$2.name = this.editingServer.name;
/* 183 */       $$2.ip = this.editingServer.ip;
/* 184 */       $$2.copyFrom(this.editingServer);
/* 185 */       this.servers.save();
/* 186 */       this.serverSelectionList.updateOnlineServers(this.servers);
/*     */     } 
/* 188 */     this.minecraft.setScreen(this);
/*     */   }
/*     */   
/*     */   private void addServerCallback(boolean $$0) {
/* 192 */     if ($$0) {
/* 193 */       ServerData $$1 = this.servers.unhide(this.editingServer.ip);
/* 194 */       if ($$1 != null) {
/* 195 */         $$1.copyNameIconFrom(this.editingServer);
/* 196 */         this.servers.save();
/*     */       } else {
/* 198 */         this.servers.add(this.editingServer, false);
/* 199 */         this.servers.save();
/*     */       } 
/* 201 */       this.serverSelectionList.setSelected((ServerSelectionList.Entry)null);
/* 202 */       this.serverSelectionList.updateOnlineServers(this.servers);
/*     */     } 
/* 204 */     this.minecraft.setScreen(this);
/*     */   }
/*     */   
/*     */   private void directJoinCallback(boolean $$0) {
/* 208 */     if ($$0) {
/* 209 */       ServerData $$1 = this.servers.get(this.editingServer.ip);
/* 210 */       if ($$1 == null) {
/* 211 */         this.servers.add(this.editingServer, true);
/* 212 */         this.servers.save();
/* 213 */         join(this.editingServer);
/*     */       } else {
/* 215 */         join($$1);
/*     */       } 
/*     */     } else {
/* 218 */       this.minecraft.setScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 224 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 225 */       return true;
/*     */     }
/*     */     
/* 228 */     if ($$0 == 294) {
/* 229 */       refreshServerList();
/* 230 */       return true;
/*     */     } 
/*     */     
/* 233 */     if (this.serverSelectionList.getSelected() != null) {
/* 234 */       if (CommonInputs.selected($$0)) {
/* 235 */         joinSelectedServer();
/* 236 */         return true;
/*     */       } 
/* 238 */       return this.serverSelectionList.keyPressed($$0, $$1, $$2);
/*     */     } 
/*     */     
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 246 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 248 */     this.toolTip = null;
/*     */     
/* 250 */     this.serverSelectionList.render($$0, $$1, $$2, $$3);
/* 251 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
/*     */     
/* 253 */     if (this.toolTip != null) {
/* 254 */       $$0.renderComponentTooltip(this.font, this.toolTip, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public void joinSelectedServer() {
/* 259 */     ServerSelectionList.Entry $$0 = (ServerSelectionList.Entry)this.serverSelectionList.getSelected();
/*     */     
/* 261 */     if ($$0 instanceof ServerSelectionList.OnlineServerEntry) {
/* 262 */       join(((ServerSelectionList.OnlineServerEntry)$$0).getServerData());
/* 263 */     } else if ($$0 instanceof ServerSelectionList.NetworkServerEntry) {
/* 264 */       LanServer $$1 = ((ServerSelectionList.NetworkServerEntry)$$0).getServerData();
/* 265 */       join(new ServerData($$1.getMotd(), $$1.getAddress(), ServerData.Type.LAN));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void join(ServerData $$0) {
/* 270 */     ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString($$0.ip), $$0, false);
/*     */   }
/*     */   
/*     */   public void setSelected(ServerSelectionList.Entry $$0) {
/* 274 */     this.serverSelectionList.setSelected($$0);
/*     */     
/* 276 */     onSelectedChange();
/*     */   }
/*     */   
/*     */   protected void onSelectedChange() {
/* 280 */     this.selectButton.active = false;
/* 281 */     this.editButton.active = false;
/* 282 */     this.deleteButton.active = false;
/*     */     
/* 284 */     ServerSelectionList.Entry $$0 = (ServerSelectionList.Entry)this.serverSelectionList.getSelected();
/* 285 */     if ($$0 != null && !($$0 instanceof ServerSelectionList.LANHeader)) {
/* 286 */       this.selectButton.active = true;
/* 287 */       if ($$0 instanceof ServerSelectionList.OnlineServerEntry) {
/* 288 */         this.editButton.active = true;
/* 289 */         this.deleteButton.active = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public ServerStatusPinger getPinger() {
/* 295 */     return this.pinger;
/*     */   }
/*     */   
/*     */   public void setToolTip(List<Component> $$0) {
/* 299 */     this.toolTip = $$0;
/*     */   }
/*     */   
/*     */   public ServerList getServers() {
/* 303 */     return this.servers;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\multiplayer\JoinMultiplayerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */