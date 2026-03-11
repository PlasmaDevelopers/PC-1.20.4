/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.client.GuiMessage;
/*     */ import net.minecraft.client.GuiMessageTag;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.multiplayer.chat.ChatListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MessageSignature;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.ArrayListDeque;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.player.ChatVisiblity;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ChatComponent
/*     */ {
/*  29 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int MAX_CHAT_HISTORY = 100;
/*     */   
/*     */   private static final int MESSAGE_NOT_FOUND = -1;
/*     */   
/*     */   private static final int MESSAGE_INDENT = 4;
/*     */   private static final int MESSAGE_TAG_MARGIN_LEFT = 4;
/*     */   private static final int BOTTOM_MARGIN = 40;
/*     */   private static final int TIME_BEFORE_MESSAGE_DELETION = 60;
/*  39 */   private static final Component DELETED_CHAT_MESSAGE = (Component)Component.translatable("chat.deleted_marker").withStyle(new ChatFormatting[] { ChatFormatting.GRAY, ChatFormatting.ITALIC });
/*     */   
/*     */   private final Minecraft minecraft;
/*  42 */   private final ArrayListDeque<String> recentChat = new ArrayListDeque(100);
/*  43 */   private final List<GuiMessage> allMessages = Lists.newArrayList();
/*  44 */   private final List<GuiMessage.Line> trimmedMessages = Lists.newArrayList();
/*     */   
/*     */   private int chatScrollbarPos;
/*     */   private boolean newMessageSinceScroll;
/*  48 */   private final List<DelayedMessageDeletion> messageDeletionQueue = new ArrayList<>();
/*     */   
/*     */   public ChatComponent(Minecraft $$0) {
/*  51 */     this.minecraft = $$0;
/*  52 */     this.recentChat.addAll($$0.commandHistory().history());
/*     */   }
/*     */   
/*     */   public void tick() {
/*  56 */     if (!this.messageDeletionQueue.isEmpty()) {
/*  57 */       processMessageDeletionQueue();
/*     */     }
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/*  62 */     if (isChatHidden()) {
/*     */       return;
/*     */     }
/*     */     
/*  66 */     int $$4 = getLinesPerPage();
/*  67 */     int $$5 = this.trimmedMessages.size();
/*     */     
/*  69 */     if ($$5 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     boolean $$6 = isChatFocused();
/*     */     
/*  75 */     float $$7 = (float)getScale();
/*  76 */     int $$8 = Mth.ceil(getWidth() / $$7);
/*     */     
/*  78 */     int $$9 = $$0.guiHeight();
/*  79 */     $$0.pose().pushPose();
/*  80 */     $$0.pose().scale($$7, $$7, 1.0F);
/*  81 */     $$0.pose().translate(4.0F, 0.0F, 0.0F);
/*     */     
/*  83 */     int $$10 = Mth.floor(($$9 - 40) / $$7);
/*     */     
/*  85 */     int $$11 = getMessageEndIndexAt(screenToChatX($$2), screenToChatY($$3));
/*     */     
/*  87 */     double $$12 = ((Double)this.minecraft.options.chatOpacity().get()).doubleValue() * 0.8999999761581421D + 0.10000000149011612D;
/*  88 */     double $$13 = ((Double)this.minecraft.options.textBackgroundOpacity().get()).doubleValue();
/*     */     
/*  90 */     double $$14 = ((Double)this.minecraft.options.chatLineSpacing().get()).doubleValue();
/*  91 */     int $$15 = getLineHeight();
/*  92 */     int $$16 = (int)Math.round(-8.0D * ($$14 + 1.0D) + 4.0D * $$14);
/*     */     
/*  94 */     int $$17 = 0;
/*  95 */     for (int $$18 = 0; $$18 + this.chatScrollbarPos < this.trimmedMessages.size() && $$18 < $$4; $$18++) {
/*  96 */       int $$19 = $$18 + this.chatScrollbarPos;
/*  97 */       GuiMessage.Line $$20 = this.trimmedMessages.get($$19);
/*  98 */       if ($$20 != null) {
/*     */ 
/*     */         
/* 101 */         int $$21 = $$1 - $$20.addedTime();
/*     */         
/* 103 */         if ($$21 < 200 || $$6) {
/* 104 */           double $$22 = $$6 ? 1.0D : getTimeFactor($$21);
/* 105 */           int $$23 = (int)(255.0D * $$22 * $$12);
/* 106 */           int $$24 = (int)(255.0D * $$22 * $$13);
/*     */           
/* 108 */           $$17++;
/*     */           
/* 110 */           if ($$23 > 3) {
/* 111 */             int $$25 = 0;
/* 112 */             int $$26 = $$10 - $$18 * $$15;
/* 113 */             int $$27 = $$26 + $$16;
/*     */             
/* 115 */             $$0.pose().pushPose();
/* 116 */             $$0.pose().translate(0.0F, 0.0F, 50.0F);
/* 117 */             $$0.fill(-4, $$26 - $$15, 0 + $$8 + 4 + 4, $$26, $$24 << 24);
/*     */             
/* 119 */             GuiMessageTag $$28 = $$20.tag();
/* 120 */             if ($$28 != null) {
/* 121 */               int $$29 = $$28.indicatorColor() | $$23 << 24;
/* 122 */               $$0.fill(-4, $$26 - $$15, -2, $$26, $$29);
/*     */               
/* 124 */               if ($$19 == $$11 && $$28.icon() != null) {
/* 125 */                 int $$30 = getTagIconLeft($$20);
/* 126 */                 Objects.requireNonNull(this.minecraft.font); int $$31 = $$27 + 9;
/* 127 */                 drawTagIcon($$0, $$30, $$31, $$28.icon());
/*     */               } 
/*     */             } 
/*     */             
/* 131 */             $$0.pose().translate(0.0F, 0.0F, 50.0F);
/* 132 */             $$0.drawString(this.minecraft.font, $$20.content(), 0, $$27, 16777215 + ($$23 << 24));
/* 133 */             $$0.pose().popPose();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 138 */     long $$32 = this.minecraft.getChatListener().queueSize();
/* 139 */     if ($$32 > 0L) {
/* 140 */       int $$33 = (int)(128.0D * $$12);
/* 141 */       int $$34 = (int)(255.0D * $$13);
/* 142 */       $$0.pose().pushPose();
/* 143 */       $$0.pose().translate(0.0F, $$10, 50.0F);
/* 144 */       $$0.fill(-2, 0, $$8 + 4, 9, $$34 << 24);
/*     */       
/* 146 */       $$0.pose().translate(0.0F, 0.0F, 50.0F);
/* 147 */       $$0.drawString(this.minecraft.font, (Component)Component.translatable("chat.queue", new Object[] { Long.valueOf($$32) }), 0, 1, 16777215 + ($$33 << 24));
/* 148 */       $$0.pose().popPose();
/*     */     } 
/*     */     
/* 151 */     if ($$6) {
/* 152 */       int $$35 = getLineHeight();
/*     */       
/* 154 */       int $$36 = $$5 * $$35;
/* 155 */       int $$37 = $$17 * $$35;
/* 156 */       int $$38 = this.chatScrollbarPos * $$37 / $$5 - $$10;
/* 157 */       int $$39 = $$37 * $$37 / $$36;
/*     */       
/* 159 */       if ($$36 != $$37) {
/* 160 */         int $$40 = ($$38 > 0) ? 170 : 96;
/* 161 */         int $$41 = this.newMessageSinceScroll ? 13382451 : 3355562;
/*     */         
/* 163 */         int $$42 = $$8 + 4;
/* 164 */         $$0.fill($$42, -$$38, $$42 + 2, -$$38 - $$39, 100, $$41 + ($$40 << 24));
/* 165 */         $$0.fill($$42 + 2, -$$38, $$42 + 1, -$$38 - $$39, 100, 13421772 + ($$40 << 24));
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     $$0.pose().popPose();
/*     */   }
/*     */   
/*     */   private void drawTagIcon(GuiGraphics $$0, int $$1, int $$2, GuiMessageTag.Icon $$3) {
/* 173 */     int $$4 = $$2 - $$3.height - 1;
/* 174 */     $$3.draw($$0, $$1, $$4);
/*     */   }
/*     */   
/*     */   private int getTagIconLeft(GuiMessage.Line $$0) {
/* 178 */     return this.minecraft.font.width($$0.content()) + 4;
/*     */   }
/*     */   
/*     */   private boolean isChatHidden() {
/* 182 */     return (this.minecraft.options.chatVisibility().get() == ChatVisiblity.HIDDEN);
/*     */   }
/*     */   
/*     */   private static double getTimeFactor(int $$0) {
/* 186 */     double $$1 = $$0 / 200.0D;
/* 187 */     $$1 = 1.0D - $$1;
/* 188 */     $$1 *= 10.0D;
/* 189 */     $$1 = Mth.clamp($$1, 0.0D, 1.0D);
/* 190 */     $$1 *= $$1;
/* 191 */     return $$1;
/*     */   }
/*     */   
/*     */   public void clearMessages(boolean $$0) {
/* 195 */     this.minecraft.getChatListener().clearQueue();
/* 196 */     this.messageDeletionQueue.clear();
/* 197 */     this.trimmedMessages.clear();
/* 198 */     this.allMessages.clear();
/* 199 */     if ($$0) {
/* 200 */       this.recentChat.clear();
/* 201 */       this.recentChat.addAll(this.minecraft.commandHistory().history());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addMessage(Component $$0) {
/* 206 */     addMessage($$0, null, this.minecraft.isSingleplayer() ? GuiMessageTag.systemSinglePlayer() : GuiMessageTag.system());
/*     */   }
/*     */   
/*     */   public void addMessage(Component $$0, @Nullable MessageSignature $$1, @Nullable GuiMessageTag $$2) {
/* 210 */     logChatMessage($$0, $$2);
/* 211 */     addMessage($$0, $$1, this.minecraft.gui.getGuiTicks(), $$2, false);
/*     */   }
/*     */   
/*     */   private void logChatMessage(Component $$0, @Nullable GuiMessageTag $$1) {
/* 215 */     String $$2 = $$0.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n");
/* 216 */     String $$3 = (String)Optionull.map($$1, GuiMessageTag::logTag);
/* 217 */     if ($$3 != null) {
/* 218 */       LOGGER.info("[{}] [CHAT] {}", $$3, $$2);
/*     */     } else {
/* 220 */       LOGGER.info("[CHAT] {}", $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addMessage(Component $$0, @Nullable MessageSignature $$1, int $$2, @Nullable GuiMessageTag $$3, boolean $$4) {
/* 225 */     int $$5 = Mth.floor(getWidth() / getScale());
/* 226 */     if ($$3 != null && $$3.icon() != null) {
/* 227 */       $$5 -= ($$3.icon()).width + 4 + 2;
/*     */     }
/*     */     
/* 230 */     List<FormattedCharSequence> $$6 = ComponentRenderUtils.wrapComponents((FormattedText)$$0, $$5, this.minecraft.font);
/*     */     
/* 232 */     boolean $$7 = isChatFocused();
/* 233 */     for (int $$8 = 0; $$8 < $$6.size(); $$8++) {
/* 234 */       FormattedCharSequence $$9 = $$6.get($$8);
/* 235 */       if ($$7 && this.chatScrollbarPos > 0) {
/* 236 */         this.newMessageSinceScroll = true;
/* 237 */         scrollChat(1);
/*     */       } 
/*     */       
/* 240 */       boolean $$10 = ($$8 == $$6.size() - 1);
/* 241 */       this.trimmedMessages.add(0, new GuiMessage.Line($$2, $$9, $$3, $$10));
/*     */     } 
/*     */     
/* 244 */     while (this.trimmedMessages.size() > 100) {
/* 245 */       this.trimmedMessages.remove(this.trimmedMessages.size() - 1);
/*     */     }
/*     */     
/* 248 */     if (!$$4) {
/* 249 */       this.allMessages.add(0, new GuiMessage($$2, $$0, $$1, $$3));
/*     */       
/* 251 */       while (this.allMessages.size() > 100) {
/* 252 */         this.allMessages.remove(this.allMessages.size() - 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processMessageDeletionQueue() {
/* 258 */     int $$0 = this.minecraft.gui.getGuiTicks();
/* 259 */     this.messageDeletionQueue.removeIf($$1 -> ($$0 >= $$1.deletableAfter()) ? ((deleteMessageOrDelay($$1.signature()) == null)) : false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteMessage(MessageSignature $$0) {
/* 268 */     DelayedMessageDeletion $$1 = deleteMessageOrDelay($$0);
/* 269 */     if ($$1 != null) {
/* 270 */       this.messageDeletionQueue.add($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private DelayedMessageDeletion deleteMessageOrDelay(MessageSignature $$0) {
/* 276 */     int $$1 = this.minecraft.gui.getGuiTicks();
/*     */     
/* 278 */     for (ListIterator<GuiMessage> $$2 = this.allMessages.listIterator(); $$2.hasNext(); ) {
/* 279 */       GuiMessage $$3 = $$2.next();
/* 280 */       if ($$0.equals($$3.signature())) {
/* 281 */         int $$4 = $$3.addedTime() + 60;
/* 282 */         if ($$1 >= $$4) {
/* 283 */           $$2.set(createDeletedMarker($$3));
/* 284 */           refreshTrimmedMessage();
/* 285 */           return null;
/*     */         } 
/* 287 */         return new DelayedMessageDeletion($$0, $$4);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 292 */     return null;
/*     */   }
/*     */   
/*     */   private GuiMessage createDeletedMarker(GuiMessage $$0) {
/* 296 */     return new GuiMessage($$0.addedTime(), DELETED_CHAT_MESSAGE, null, GuiMessageTag.system());
/*     */   }
/*     */   
/*     */   public void rescaleChat() {
/* 300 */     resetChatScroll();
/* 301 */     refreshTrimmedMessage();
/*     */   }
/*     */   
/*     */   private void refreshTrimmedMessage() {
/* 305 */     this.trimmedMessages.clear();
/* 306 */     for (int $$0 = this.allMessages.size() - 1; $$0 >= 0; $$0--) {
/* 307 */       GuiMessage $$1 = this.allMessages.get($$0);
/* 308 */       addMessage($$1.content(), $$1.signature(), $$1.addedTime(), $$1.tag(), true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ArrayListDeque<String> getRecentChat() {
/* 313 */     return this.recentChat;
/*     */   }
/*     */   
/*     */   public void addRecentChat(String $$0) {
/* 317 */     if (!$$0.equals(this.recentChat.peekLast())) {
/* 318 */       if (this.recentChat.size() >= 100) {
/* 319 */         this.recentChat.removeFirst();
/*     */       }
/* 321 */       this.recentChat.addLast($$0);
/*     */     } 
/* 323 */     if ($$0.startsWith("/")) {
/* 324 */       this.minecraft.commandHistory().addCommand($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetChatScroll() {
/* 329 */     this.chatScrollbarPos = 0;
/* 330 */     this.newMessageSinceScroll = false;
/*     */   }
/*     */   
/*     */   public void scrollChat(int $$0) {
/* 334 */     this.chatScrollbarPos += $$0;
/* 335 */     int $$1 = this.trimmedMessages.size();
/*     */     
/* 337 */     if (this.chatScrollbarPos > $$1 - getLinesPerPage()) {
/* 338 */       this.chatScrollbarPos = $$1 - getLinesPerPage();
/*     */     }
/*     */     
/* 341 */     if (this.chatScrollbarPos <= 0) {
/* 342 */       this.chatScrollbarPos = 0;
/* 343 */       this.newMessageSinceScroll = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean handleChatQueueClicked(double $$0, double $$1) {
/* 348 */     if (!isChatFocused() || this.minecraft.options.hideGui || isChatHidden()) {
/* 349 */       return false;
/*     */     }
/*     */     
/* 352 */     ChatListener $$2 = this.minecraft.getChatListener();
/* 353 */     if ($$2.queueSize() == 0L) {
/* 354 */       return false;
/*     */     }
/*     */     
/* 357 */     double $$3 = $$0 - 2.0D;
/* 358 */     double $$4 = this.minecraft.getWindow().getGuiScaledHeight() - $$1 - 40.0D;
/*     */     
/* 360 */     if ($$3 <= Mth.floor(getWidth() / getScale()) && $$4 < 0.0D && $$4 > Mth.floor(-9.0D * getScale())) {
/* 361 */       $$2.acceptNextDelayedMessage();
/* 362 */       return true;
/*     */     } 
/*     */     
/* 365 */     return false;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Style getClickedComponentStyleAt(double $$0, double $$1) {
/* 370 */     double $$2 = screenToChatX($$0);
/* 371 */     double $$3 = screenToChatY($$1);
/*     */     
/* 373 */     int $$4 = getMessageLineIndexAt($$2, $$3);
/* 374 */     if ($$4 >= 0 && $$4 < this.trimmedMessages.size()) {
/* 375 */       GuiMessage.Line $$5 = this.trimmedMessages.get($$4);
/* 376 */       return this.minecraft.font.getSplitter().componentStyleAtWidth($$5.content(), Mth.floor($$2));
/*     */     } 
/*     */     
/* 379 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public GuiMessageTag getMessageTagAt(double $$0, double $$1) {
/* 384 */     double $$2 = screenToChatX($$0);
/* 385 */     double $$3 = screenToChatY($$1);
/*     */     
/* 387 */     int $$4 = getMessageEndIndexAt($$2, $$3);
/* 388 */     if ($$4 >= 0 && $$4 < this.trimmedMessages.size()) {
/* 389 */       GuiMessage.Line $$5 = this.trimmedMessages.get($$4);
/* 390 */       GuiMessageTag $$6 = $$5.tag();
/* 391 */       if ($$6 != null && hasSelectedMessageTag($$2, $$5, $$6)) {
/* 392 */         return $$6;
/*     */       }
/*     */     } 
/*     */     
/* 396 */     return null;
/*     */   }
/*     */   
/*     */   private boolean hasSelectedMessageTag(double $$0, GuiMessage.Line $$1, GuiMessageTag $$2) {
/* 400 */     if ($$0 < 0.0D) {
/* 401 */       return true;
/*     */     }
/*     */     
/* 404 */     GuiMessageTag.Icon $$3 = $$2.icon();
/* 405 */     if ($$3 != null) {
/* 406 */       int $$4 = getTagIconLeft($$1);
/* 407 */       int $$5 = $$4 + $$3.width;
/* 408 */       return ($$0 >= $$4 && $$0 <= $$5);
/*     */     } 
/*     */     
/* 411 */     return false;
/*     */   }
/*     */   
/*     */   private double screenToChatX(double $$0) {
/* 415 */     return $$0 / getScale() - 4.0D;
/*     */   }
/*     */   
/*     */   private double screenToChatY(double $$0) {
/* 419 */     double $$1 = this.minecraft.getWindow().getGuiScaledHeight() - $$0 - 40.0D;
/* 420 */     return $$1 / getScale() * getLineHeight();
/*     */   }
/*     */   
/*     */   private int getMessageEndIndexAt(double $$0, double $$1) {
/* 424 */     int $$2 = getMessageLineIndexAt($$0, $$1);
/* 425 */     if ($$2 == -1) {
/* 426 */       return -1;
/*     */     }
/* 428 */     while ($$2 >= 0) {
/* 429 */       if (((GuiMessage.Line)this.trimmedMessages.get($$2)).endOfEntry()) {
/* 430 */         return $$2;
/*     */       }
/* 432 */       $$2--;
/*     */     } 
/* 434 */     return $$2;
/*     */   }
/*     */   
/*     */   private int getMessageLineIndexAt(double $$0, double $$1) {
/* 438 */     if (!isChatFocused() || this.minecraft.options.hideGui || isChatHidden()) {
/* 439 */       return -1;
/*     */     }
/*     */     
/* 442 */     if ($$0 < -4.0D || $$0 > Mth.floor(getWidth() / getScale())) {
/* 443 */       return -1;
/*     */     }
/*     */     
/* 446 */     int $$2 = Math.min(getLinesPerPage(), this.trimmedMessages.size());
/*     */     
/* 448 */     if ($$1 >= 0.0D && $$1 < $$2) {
/* 449 */       int $$3 = Mth.floor($$1 + this.chatScrollbarPos);
/* 450 */       if ($$3 >= 0 && $$3 < this.trimmedMessages.size()) {
/* 451 */         return $$3;
/*     */       }
/*     */     } 
/*     */     
/* 455 */     return -1;
/*     */   }
/*     */   
/*     */   private boolean isChatFocused() {
/* 459 */     return this.minecraft.screen instanceof net.minecraft.client.gui.screens.ChatScreen;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 463 */     return getWidth(((Double)this.minecraft.options.chatWidth().get()).doubleValue());
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 467 */     return getHeight((isChatFocused() ? (Double)this.minecraft.options.chatHeightFocused().get() : (Double)this.minecraft.options.chatHeightUnfocused().get()).doubleValue());
/*     */   }
/*     */   
/*     */   public double getScale() {
/* 471 */     return ((Double)this.minecraft.options.chatScale().get()).doubleValue();
/*     */   }
/*     */   
/*     */   public static int getWidth(double $$0) {
/* 475 */     int $$1 = 320;
/* 476 */     int $$2 = 40;
/* 477 */     return Mth.floor($$0 * 280.0D + 40.0D);
/*     */   }
/*     */   
/*     */   public static int getHeight(double $$0) {
/* 481 */     int $$1 = 180;
/* 482 */     int $$2 = 20;
/* 483 */     return Mth.floor($$0 * 160.0D + 20.0D);
/*     */   }
/*     */   
/*     */   public static double defaultUnfocusedPct() {
/* 487 */     int $$0 = 180;
/* 488 */     int $$1 = 20;
/* 489 */     return 70.0D / (getHeight(1.0D) - 20);
/*     */   }
/*     */   
/*     */   public int getLinesPerPage() {
/* 493 */     return getHeight() / getLineHeight();
/*     */   }
/*     */   
/*     */   private int getLineHeight() {
/* 497 */     Objects.requireNonNull(this.minecraft.font); return (int)(9.0D * (((Double)this.minecraft.options.chatLineSpacing().get()).doubleValue() + 1.0D));
/*     */   }
/*     */   private static final class DelayedMessageDeletion extends Record { private final MessageSignature signature; private final int deletableAfter;
/* 500 */     DelayedMessageDeletion(MessageSignature $$0, int $$1) { this.signature = $$0; this.deletableAfter = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #500	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 500 */       //   0	7	0	this	Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion; } public MessageSignature signature() { return this.signature; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #500	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #500	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/components/ChatComponent$DelayedMessageDeletion;
/* 500 */       //   0	8	1	$$0	Ljava/lang/Object; } public int deletableAfter() { return this.deletableAfter; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\ChatComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */