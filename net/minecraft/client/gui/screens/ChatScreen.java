/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.GuiMessageTag;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.ChatComponent;
/*     */ import net.minecraft.client.gui.components.CommandSuggestions;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.StringUtil;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class ChatScreen extends Screen {
/*     */   public static final double MOUSE_SCROLL_SPEED = 7.0D;
/*  24 */   private static final Component USAGE_TEXT = (Component)Component.translatable("chat_screen.usage");
/*     */   
/*     */   private static final int TOOLTIP_MAX_WIDTH = 210;
/*     */   
/*  28 */   private String historyBuffer = "";
/*  29 */   private int historyPos = -1;
/*     */   
/*     */   protected EditBox input;
/*     */   
/*     */   private String initial;
/*     */   CommandSuggestions commandSuggestions;
/*     */   
/*     */   public ChatScreen(String $$0) {
/*  37 */     super((Component)Component.translatable("chat_screen.title"));
/*  38 */     this.initial = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  43 */     this.historyPos = this.minecraft.gui.getChat().getRecentChat().size();
/*  44 */     this.input = new EditBox(this.minecraft.fontFilterFishy, 4, this.height - 12, this.width - 4, 12, (Component)Component.translatable("chat.editBox"))
/*     */       {
/*     */         protected MutableComponent createNarrationMessage() {
/*  47 */           return super.createNarrationMessage().append(ChatScreen.this.commandSuggestions.getNarrationMessage());
/*     */         }
/*     */       };
/*  50 */     this.input.setMaxLength(256);
/*  51 */     this.input.setBordered(false);
/*  52 */     this.input.setValue(this.initial);
/*  53 */     this.input.setResponder(this::onEdited);
/*  54 */     this.input.setCanLoseFocus(false);
/*  55 */     addWidget(this.input);
/*     */     
/*  57 */     this.commandSuggestions = new CommandSuggestions(this.minecraft, this, this.input, this.font, false, false, 1, 10, true, -805306368);
/*  58 */     this.commandSuggestions.setAllowHiding(false);
/*  59 */     this.commandSuggestions.updateCommandInfo();
/*  60 */     setInitialFocus((GuiEventListener)this.input);
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(Minecraft $$0, int $$1, int $$2) {
/*  65 */     String $$3 = this.input.getValue();
/*  66 */     init($$0, $$1, $$2);
/*  67 */     setChatLine($$3);
/*     */     
/*  69 */     this.commandSuggestions.updateCommandInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/*  74 */     this.minecraft.gui.getChat().resetChatScroll();
/*     */   }
/*     */   
/*     */   private void onEdited(String $$0) {
/*  78 */     String $$1 = this.input.getValue();
/*  79 */     this.commandSuggestions.setAllowSuggestions(!$$1.equals(this.initial));
/*  80 */     this.commandSuggestions.updateCommandInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  86 */     if (this.commandSuggestions.keyPressed($$0, $$1, $$2)) {
/*  87 */       return true;
/*     */     }
/*     */     
/*  90 */     if (super.keyPressed($$0, $$1, $$2)) {
/*  91 */       return true;
/*     */     }
/*     */     
/*  94 */     if ($$0 == 256) {
/*  95 */       this.minecraft.setScreen(null);
/*  96 */       return true;
/*  97 */     }  if ($$0 == 257 || $$0 == 335) {
/*  98 */       if (handleChatInput(this.input.getValue(), true)) {
/*  99 */         this.minecraft.setScreen(null);
/*     */       }
/* 101 */       return true;
/* 102 */     }  if ($$0 == 265) {
/* 103 */       moveInHistory(-1);
/* 104 */       return true;
/* 105 */     }  if ($$0 == 264) {
/* 106 */       moveInHistory(1);
/* 107 */       return true;
/* 108 */     }  if ($$0 == 266) {
/* 109 */       this.minecraft.gui.getChat().scrollChat(this.minecraft.gui.getChat().getLinesPerPage() - 1);
/* 110 */       return true;
/* 111 */     }  if ($$0 == 267) {
/* 112 */       this.minecraft.gui.getChat().scrollChat(-this.minecraft.gui.getChat().getLinesPerPage() + 1);
/* 113 */       return true;
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 120 */     $$3 = Mth.clamp($$3, -1.0D, 1.0D);
/* 121 */     if (this.commandSuggestions.mouseScrolled($$3)) {
/* 122 */       return true;
/*     */     }
/* 124 */     if (!hasShiftDown()) {
/* 125 */       $$3 *= 7.0D;
/*     */     }
/* 127 */     this.minecraft.gui.getChat().scrollChat((int)$$3);
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 133 */     if (this.commandSuggestions.mouseClicked((int)$$0, (int)$$1, $$2)) {
/* 134 */       return true;
/*     */     }
/*     */     
/* 137 */     if ($$2 == 0) {
/* 138 */       ChatComponent $$3 = this.minecraft.gui.getChat();
/* 139 */       if ($$3.handleChatQueueClicked($$0, $$1)) {
/* 140 */         return true;
/*     */       }
/*     */       
/* 143 */       Style $$4 = getComponentStyleAt($$0, $$1);
/* 144 */       if ($$4 != null && handleComponentClicked($$4)) {
/* 145 */         this.initial = this.input.getValue();
/* 146 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     if (this.input.mouseClicked($$0, $$1, $$2)) {
/* 151 */       return true;
/*     */     }
/* 153 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void insertText(String $$0, boolean $$1) {
/* 158 */     if ($$1) {
/* 159 */       this.input.setValue($$0);
/*     */     } else {
/* 161 */       this.input.insertText($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void moveInHistory(int $$0) {
/* 166 */     int $$1 = this.historyPos + $$0;
/* 167 */     int $$2 = this.minecraft.gui.getChat().getRecentChat().size();
/*     */     
/* 169 */     $$1 = Mth.clamp($$1, 0, $$2);
/* 170 */     if ($$1 == this.historyPos) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     if ($$1 == $$2) {
/* 175 */       this.historyPos = $$2;
/* 176 */       this.input.setValue(this.historyBuffer);
/*     */       
/*     */       return;
/*     */     } 
/* 180 */     if (this.historyPos == $$2) {
/* 181 */       this.historyBuffer = this.input.getValue();
/*     */     }
/*     */     
/* 184 */     this.input.setValue((String)this.minecraft.gui.getChat().getRecentChat().get($$1));
/* 185 */     this.commandSuggestions.setAllowSuggestions(false);
/* 186 */     this.historyPos = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 191 */     $$0.fill(2, this.height - 14, this.width - 2, this.height - 2, this.minecraft.options.getBackgroundColor(-2147483648));
/* 192 */     this.input.render($$0, $$1, $$2, $$3);
/*     */     
/* 194 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 196 */     this.commandSuggestions.render($$0, $$1, $$2);
/*     */     
/* 198 */     GuiMessageTag $$4 = this.minecraft.gui.getChat().getMessageTagAt($$1, $$2);
/* 199 */     if ($$4 != null && $$4.text() != null) {
/* 200 */       $$0.renderTooltip(this.font, this.font.split((FormattedText)$$4.text(), 210), $$1, $$2);
/*     */     } else {
/* 202 */       Style $$5 = getComponentStyleAt($$1, $$2);
/* 203 */       if ($$5 != null && $$5.getHoverEvent() != null) {
/* 204 */         $$0.renderComponentHoverEffect(this.font, $$5, $$1, $$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 216 */     return false;
/*     */   }
/*     */   
/*     */   private void setChatLine(String $$0) {
/* 220 */     this.input.setValue($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateNarrationState(NarrationElementOutput $$0) {
/* 225 */     $$0.add(NarratedElementType.TITLE, getTitle());
/* 226 */     $$0.add(NarratedElementType.USAGE, USAGE_TEXT);
/* 227 */     String $$1 = this.input.getValue();
/* 228 */     if (!$$1.isEmpty()) {
/* 229 */       $$0.nest().add(NarratedElementType.TITLE, (Component)Component.translatable("chat_screen.message", new Object[] { $$1 }));
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Style getComponentStyleAt(double $$0, double $$1) {
/* 235 */     return this.minecraft.gui.getChat().getClickedComponentStyleAt($$0, $$1);
/*     */   }
/*     */   
/*     */   public boolean handleChatInput(String $$0, boolean $$1) {
/* 239 */     $$0 = normalizeChatMessage($$0);
/* 240 */     if ($$0.isEmpty()) {
/* 241 */       return true;
/*     */     }
/*     */     
/* 244 */     if ($$1) {
/* 245 */       this.minecraft.gui.getChat().addRecentChat($$0);
/*     */     }
/*     */     
/* 248 */     if ($$0.startsWith("/")) {
/* 249 */       this.minecraft.player.connection.sendCommand($$0.substring(1));
/*     */     } else {
/* 251 */       this.minecraft.player.connection.sendChat($$0);
/*     */     } 
/* 253 */     return true;
/*     */   }
/*     */   
/*     */   public String normalizeChatMessage(String $$0) {
/* 257 */     return StringUtil.trimChatMessage(StringUtils.normalizeSpace($$0.trim()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ChatScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */