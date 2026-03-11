/*     */ package net.minecraft.client.gui.screens.reporting;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.report.AbuseReportLimits;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.client.GuiMessageTag;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineLabel;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.PlayerFaceRenderer;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.navigation.CommonInputs;
/*     */ import net.minecraft.client.gui.navigation.ScreenDirection;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.chat.ChatTrustLevel;
/*     */ import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
/*     */ import net.minecraft.client.multiplayer.chat.report.ChatReport;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportingContext;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class ChatSelectionScreen extends Screen {
/*  38 */   static final ResourceLocation CHECKMARK_SPRITE = new ResourceLocation("icon/checkmark");
/*  39 */   private static final Component TITLE = (Component)Component.translatable("gui.chatSelection.title");
/*  40 */   private static final Component CONTEXT_INFO = (Component)Component.translatable("gui.chatSelection.context").withStyle(ChatFormatting.GRAY);
/*     */   
/*     */   @Nullable
/*     */   private final Screen lastScreen;
/*     */   
/*     */   private final ReportingContext reportingContext;
/*     */   
/*     */   private Button confirmSelectedButton;
/*     */   
/*     */   private MultiLineLabel contextInfoLabel;
/*     */   @Nullable
/*     */   private ChatSelectionList chatSelectionList;
/*     */   final ChatReport.Builder report;
/*     */   private final Consumer<ChatReport.Builder> onSelected;
/*     */   private ChatSelectionLogFiller chatLogFiller;
/*     */   
/*     */   public ChatSelectionScreen(@Nullable Screen $$0, ReportingContext $$1, ChatReport.Builder $$2, Consumer<ChatReport.Builder> $$3) {
/*  57 */     super(TITLE);
/*  58 */     this.lastScreen = $$0;
/*  59 */     this.reportingContext = $$1;
/*  60 */     this.report = $$2.copy();
/*  61 */     this.onSelected = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  66 */     this.chatLogFiller = new ChatSelectionLogFiller(this.reportingContext, this::canReport);
/*     */     
/*  68 */     this.contextInfoLabel = MultiLineLabel.create(this.font, (FormattedText)CONTEXT_INFO, this.width - 16);
/*  69 */     Objects.requireNonNull(this.font); this.chatSelectionList = (ChatSelectionList)addRenderableWidget((GuiEventListener)new ChatSelectionList(this.minecraft, (this.contextInfoLabel.getLineCount() + 1) * 9));
/*     */     
/*  71 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose())
/*     */         
/*  73 */         .bounds(this.width / 2 - 155, this.height - 32, 150, 20).build());
/*     */     
/*  75 */     this.confirmSelectedButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> {
/*     */             this.onSelected.accept(this.report);
/*     */             onClose();
/*  78 */           }).bounds(this.width / 2 - 155 + 160, this.height - 32, 150, 20).build());
/*  79 */     updateConfirmSelectedButton();
/*     */     
/*  81 */     extendLog();
/*  82 */     this.chatSelectionList.setScrollAmount(this.chatSelectionList.getMaxScroll());
/*     */   }
/*     */   
/*     */   private boolean canReport(LoggedChatMessage $$0) {
/*  86 */     return $$0.canReport(this.report.reportedProfileId());
/*     */   }
/*     */   
/*     */   private void extendLog() {
/*  90 */     int $$0 = this.chatSelectionList.getMaxVisibleEntries();
/*  91 */     this.chatLogFiller.fillNextPage($$0, this.chatSelectionList);
/*     */   }
/*     */   
/*     */   void onReachedScrollTop() {
/*  95 */     extendLog();
/*     */   }
/*     */   
/*     */   void updateConfirmSelectedButton() {
/*  99 */     this.confirmSelectedButton.active = !this.report.reportedMessages().isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 104 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 106 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 16, 16777215);
/*     */     
/* 108 */     AbuseReportLimits $$4 = this.reportingContext.sender().reportLimits();
/* 109 */     int $$5 = this.report.reportedMessages().size();
/* 110 */     int $$6 = $$4.maxReportedMessageCount();
/*     */     
/* 112 */     MutableComponent mutableComponent = Component.translatable("gui.chatSelection.selected", new Object[] { Integer.valueOf($$5), Integer.valueOf($$6) });
/* 113 */     Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, (Component)mutableComponent, this.width / 2, 16 + 9 * 3 / 2, 10526880);
/*     */     
/* 115 */     this.contextInfoLabel.renderCentered($$0, this.width / 2, this.chatSelectionList.getFooterTop());
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 120 */     renderDirtBackground($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 125 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/* 130 */     return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), CONTEXT_INFO });
/*     */   }
/*     */   
/*     */   public class ChatSelectionList extends ObjectSelectionList<ChatSelectionList.Entry> implements ChatSelectionLogFiller.Output {
/*     */     @Nullable
/*     */     private Heading previousHeading;
/*     */     
/*     */     public ChatSelectionList(Minecraft $$1, int $$2) {
/* 138 */       super($$1, $$0.width, $$0.height - $$2 - 80, 40, 16);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setScrollAmount(double $$0) {
/* 143 */       double $$1 = getScrollAmount();
/* 144 */       super.setScrollAmount($$0);
/* 145 */       if (getMaxScroll() > 1.0E-5F && $$0 <= 9.999999747378752E-6D && !Mth.equal($$0, $$1)) {
/* 146 */         ChatSelectionScreen.this.onReachedScrollTop();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void acceptMessage(int $$0, LoggedChatMessage.Player $$1) {
/* 152 */       boolean $$2 = $$1.canReport(ChatSelectionScreen.this.report.reportedProfileId());
/* 153 */       ChatTrustLevel $$3 = $$1.trustLevel();
/* 154 */       GuiMessageTag $$4 = $$3.createTag($$1.message());
/* 155 */       Entry $$5 = new MessageEntry($$0, $$1.toContentComponent(), $$1.toNarrationComponent(), $$4, $$2, true);
/* 156 */       addEntryToTop((AbstractSelectionList.Entry)$$5);
/* 157 */       updateHeading($$1, $$2);
/*     */     }
/*     */     
/*     */     private void updateHeading(LoggedChatMessage.Player $$0, boolean $$1) {
/* 161 */       Entry $$2 = new MessageHeadingEntry($$0.profile(), $$0.toHeadingComponent(), $$1);
/* 162 */       addEntryToTop((AbstractSelectionList.Entry)$$2);
/*     */       
/* 164 */       Heading $$3 = new Heading($$0.profileId(), $$2);
/* 165 */       if (this.previousHeading != null && this.previousHeading.canCombine($$3)) {
/* 166 */         removeEntryFromTop((AbstractSelectionList.Entry)this.previousHeading.entry());
/*     */       }
/* 168 */       this.previousHeading = $$3;
/*     */     }
/*     */ 
/*     */     
/*     */     public void acceptDivider(Component $$0) {
/* 173 */       addEntryToTop((AbstractSelectionList.Entry)new PaddingEntry());
/* 174 */       addEntryToTop((AbstractSelectionList.Entry)new DividerEntry($$0));
/* 175 */       addEntryToTop((AbstractSelectionList.Entry)new PaddingEntry());
/* 176 */       this.previousHeading = null;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollbarPosition() {
/* 181 */       return (this.width + getRowWidth()) / 2;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowWidth() {
/* 186 */       return Math.min(350, this.width - 50);
/*     */     }
/*     */     
/*     */     public int getMaxVisibleEntries() {
/* 190 */       return Mth.positiveCeilDiv(this.height, this.itemHeight);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void renderItem(GuiGraphics $$0, int $$1, int $$2, float $$3, int $$4, int $$5, int $$6, int $$7, int $$8) {
/* 195 */       Entry $$9 = (Entry)getEntry($$4);
/* 196 */       if (shouldHighlightEntry($$9)) {
/* 197 */         boolean $$10 = (getSelected() == $$9);
/* 198 */         int $$11 = (isFocused() && $$10) ? -1 : -8355712;
/* 199 */         renderSelection($$0, $$6, $$7, $$8, $$11, -16777216);
/*     */       } 
/*     */       
/* 202 */       $$9.render($$0, $$4, $$6, $$5, $$7, $$8, $$1, $$2, (getHovered() == $$9), $$3);
/*     */     }
/*     */     
/*     */     private boolean shouldHighlightEntry(Entry $$0) {
/* 206 */       if ($$0.canSelect()) {
/* 207 */         boolean $$1 = (getSelected() == $$0);
/* 208 */         boolean $$2 = (getSelected() == null);
/* 209 */         boolean $$3 = (getHovered() == $$0);
/* 210 */         return ($$1 || ($$2 && $$3 && $$0.canReport()));
/*     */       } 
/* 212 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected Entry nextEntry(ScreenDirection $$0) {
/* 218 */       return (Entry)nextEntry($$0, Entry::canSelect);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable Entry $$0) {
/* 223 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/* 224 */       Entry $$1 = nextEntry(ScreenDirection.UP);
/* 225 */       if ($$1 == null) {
/* 226 */         ChatSelectionScreen.this.onReachedScrollTop();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 232 */       Entry $$3 = (Entry)getSelected();
/* 233 */       if ($$3 != null && $$3.keyPressed($$0, $$1, $$2)) {
/* 234 */         return true;
/*     */       }
/* 236 */       return super.keyPressed($$0, $$1, $$2);
/*     */     }
/*     */     
/*     */     public int getFooterTop() {
/* 240 */       Objects.requireNonNull(ChatSelectionScreen.this.font); return getBottom() + 9;
/*     */     }
/*     */     private static final class Heading extends Record { private final UUID sender; private final ChatSelectionScreen.ChatSelectionList.Entry entry;
/* 243 */       Heading(UUID $$0, ChatSelectionScreen.ChatSelectionList.Entry $$1) { this.sender = $$0; this.entry = $$1; } public final String toString() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading;)Ljava/lang/String;
/*     */         //   6: areturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #243	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading; } public final int hashCode() { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading;)I
/*     */         //   6: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #243	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	7	0	this	Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading; } public final boolean equals(Object $$0) { // Byte code:
/*     */         //   0: aload_0
/*     */         //   1: aload_1
/*     */         //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading;Ljava/lang/Object;)Z
/*     */         //   7: ireturn
/*     */         // Line number table:
/*     */         //   Java source line number -> byte code offset
/*     */         //   #243	-> 0
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	descriptor
/*     */         //   0	8	0	this	Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading;
/* 243 */         //   0	8	1	$$0	Ljava/lang/Object; } public UUID sender() { return this.sender; } public ChatSelectionScreen.ChatSelectionList.Entry entry() { return this.entry; }
/*     */        public boolean canCombine(Heading $$0) {
/* 245 */         return $$0.sender.equals(this.sender);
/*     */       } }
/*     */ 
/*     */     
/*     */     public abstract class Entry
/*     */       extends ObjectSelectionList.Entry<Entry> {
/*     */       public Component getNarration() {
/* 252 */         return CommonComponents.EMPTY;
/*     */       }
/*     */       
/*     */       public boolean isSelected() {
/* 256 */         return false;
/*     */       }
/*     */       
/*     */       public boolean canSelect() {
/* 260 */         return false;
/*     */       }
/*     */       
/*     */       public boolean canReport() {
/* 264 */         return canSelect();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public class MessageEntry
/*     */       extends Entry
/*     */     {
/*     */       private static final int CHECKMARK_WIDTH = 9;
/*     */       
/*     */       private static final int CHECKMARK_HEIGHT = 8;
/*     */       private static final int INDENT_AMOUNT = 11;
/*     */       private static final int TAG_MARGIN_LEFT = 4;
/*     */       private final int chatId;
/*     */       private final FormattedText text;
/*     */       private final Component narration;
/*     */       @Nullable
/*     */       private final List<FormattedCharSequence> hoverText;
/*     */       @Nullable
/*     */       private final GuiMessageTag.Icon tagIcon;
/*     */       @Nullable
/*     */       private final List<FormattedCharSequence> tagHoverText;
/*     */       private final boolean canReport;
/*     */       private final boolean playerMessage;
/*     */       
/*     */       public MessageEntry(int $$1, Component $$2, @Nullable Component $$3, GuiMessageTag $$4, boolean $$5, boolean $$6) {
/* 290 */         this.chatId = $$1;
/* 291 */         this.tagIcon = (GuiMessageTag.Icon)Optionull.map($$4, GuiMessageTag::icon);
/* 292 */         this.tagHoverText = ($$4 != null && $$4.text() != null) ? ChatSelectionScreen.this.font.split((FormattedText)$$4.text(), $$0.getRowWidth()) : null;
/*     */         
/* 294 */         this.canReport = $$5;
/* 295 */         this.playerMessage = $$6;
/* 296 */         FormattedText $$7 = ChatSelectionScreen.this.font.substrByWidth((FormattedText)$$2, getMaximumTextWidth() - ChatSelectionScreen.this.font.width((FormattedText)CommonComponents.ELLIPSIS));
/* 297 */         if ($$2 != $$7) {
/* 298 */           this.text = FormattedText.composite(new FormattedText[] { $$7, (FormattedText)CommonComponents.ELLIPSIS });
/* 299 */           this.hoverText = ChatSelectionScreen.this.font.split((FormattedText)$$2, $$0.getRowWidth());
/*     */         } else {
/* 301 */           this.text = (FormattedText)$$2;
/* 302 */           this.hoverText = null;
/*     */         } 
/* 304 */         this.narration = $$3;
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 309 */         if (isSelected() && this.canReport) {
/* 310 */           renderSelectedCheckmark($$0, $$2, $$3, $$5);
/*     */         }
/*     */         
/* 313 */         int $$10 = $$3 + getTextIndent();
/* 314 */         Objects.requireNonNull(ChatSelectionScreen.this.font); int $$11 = $$2 + 1 + ($$5 - 9) / 2;
/* 315 */         $$0.drawString(ChatSelectionScreen.this.font, Language.getInstance().getVisualOrder(this.text), $$10, $$11, this.canReport ? -1 : -1593835521);
/*     */         
/* 317 */         if (this.hoverText != null && $$8) {
/* 318 */           ChatSelectionScreen.this.setTooltipForNextRenderPass(this.hoverText);
/*     */         }
/*     */         
/* 321 */         int $$12 = ChatSelectionScreen.this.font.width(this.text);
/* 322 */         renderTag($$0, $$10 + $$12 + 4, $$2, $$5, $$6, $$7);
/*     */       }
/*     */       
/*     */       private void renderTag(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 326 */         if (this.tagIcon != null) {
/* 327 */           int $$6 = $$2 + ($$3 - this.tagIcon.height) / 2;
/* 328 */           this.tagIcon.draw($$0, $$1, $$6);
/*     */           
/* 330 */           if (this.tagHoverText != null && $$4 >= $$1 && $$4 <= $$1 + this.tagIcon.width && $$5 >= $$6 && $$5 <= $$6 + this.tagIcon.height) {
/* 331 */             ChatSelectionScreen.this.setTooltipForNextRenderPass(this.tagHoverText);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/*     */       private void renderSelectedCheckmark(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 337 */         int $$4 = $$2;
/* 338 */         int $$5 = $$1 + ($$3 - 8) / 2;
/*     */         
/* 340 */         RenderSystem.enableBlend();
/* 341 */         $$0.blitSprite(ChatSelectionScreen.CHECKMARK_SPRITE, $$4, $$5, 9, 8);
/* 342 */         RenderSystem.disableBlend();
/*     */       }
/*     */       
/*     */       private int getMaximumTextWidth() {
/* 346 */         int $$0 = (this.tagIcon != null) ? (this.tagIcon.width + 4) : 0;
/* 347 */         return ChatSelectionScreen.ChatSelectionList.this.getRowWidth() - getTextIndent() - 4 - $$0;
/*     */       }
/*     */       
/*     */       private int getTextIndent() {
/* 351 */         return this.playerMessage ? 11 : 0;
/*     */       }
/*     */ 
/*     */       
/*     */       public Component getNarration() {
/* 356 */         return isSelected() ? (Component)Component.translatable("narrator.select", new Object[] { this.narration }) : this.narration;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 361 */         ChatSelectionScreen.ChatSelectionList.this.setSelected((ChatSelectionScreen.ChatSelectionList.Entry)null);
/* 362 */         return toggleReport();
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 367 */         if (CommonInputs.selected($$0)) {
/* 368 */           return toggleReport();
/*     */         }
/* 370 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean isSelected() {
/* 375 */         return ChatSelectionScreen.this.report.isReported(this.chatId);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean canSelect() {
/* 380 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean canReport() {
/* 385 */         return this.canReport;
/*     */       }
/*     */       
/*     */       private boolean toggleReport() {
/* 389 */         if (this.canReport) {
/* 390 */           ChatSelectionScreen.this.report.toggleReported(this.chatId);
/* 391 */           ChatSelectionScreen.this.updateConfirmSelectedButton();
/* 392 */           return true;
/*     */         } 
/* 394 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */     public class MessageHeadingEntry
/*     */       extends Entry {
/*     */       private static final int FACE_SIZE = 12;
/*     */       private final Component heading;
/*     */       private final Supplier<PlayerSkin> skin;
/*     */       private final boolean canReport;
/*     */       
/*     */       public MessageHeadingEntry(GameProfile $$1, Component $$2, boolean $$3) {
/* 406 */         this.heading = $$2;
/* 407 */         this.canReport = $$3;
/* 408 */         this.skin = $$0.minecraft.getSkinManager().lookupInsecure($$1);
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 413 */         int $$10 = $$3 - 12 - 4;
/* 414 */         int $$11 = $$2 + ($$5 - 12) / 2;
/* 415 */         PlayerFaceRenderer.draw($$0, this.skin.get(), $$10, $$11, 12);
/*     */         
/* 417 */         Objects.requireNonNull(ChatSelectionScreen.this.font); int $$12 = $$2 + 1 + ($$5 - 9) / 2;
/* 418 */         $$0.drawString(ChatSelectionScreen.this.font, this.heading, $$3, $$12, this.canReport ? -1 : -1593835521);
/*     */       }
/*     */     }
/*     */     
/*     */     public class DividerEntry
/*     */       extends Entry {
/*     */       private static final int COLOR = -6250336;
/*     */       private final Component text;
/*     */       
/*     */       public DividerEntry(Component $$1) {
/* 428 */         this.text = $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 433 */         int $$10 = $$2 + $$5 / 2;
/* 434 */         int $$11 = $$3 + $$4 - 8;
/*     */         
/* 436 */         int $$12 = ChatSelectionScreen.this.font.width((FormattedText)this.text);
/* 437 */         int $$13 = ($$3 + $$11 - $$12) / 2;
/* 438 */         Objects.requireNonNull(ChatSelectionScreen.this.font); int $$14 = $$10 - 9 / 2;
/*     */         
/* 440 */         $$0.drawString(ChatSelectionScreen.this.font, this.text, $$13, $$14, -6250336);
/*     */       }
/*     */       
/*     */       public Component getNarration()
/*     */       {
/* 445 */         return this.text; } } public class PaddingEntry extends Entry { public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {} } } private static final class Heading extends Record { private final UUID sender; private final ChatSelectionScreen.ChatSelectionList.Entry entry; Heading(UUID $$0, ChatSelectionScreen.ChatSelectionList.Entry $$1) { this.sender = $$0; this.entry = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #243	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #243	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #243	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/screens/reporting/ChatSelectionScreen$ChatSelectionList$Heading;
/* 445 */       //   0	8	1	$$0	Ljava/lang/Object; } public UUID sender() { return this.sender; } public ChatSelectionScreen.ChatSelectionList.Entry entry() { return this.entry; } public boolean canCombine(Heading $$0) { return $$0.sender.equals(this.sender); } } public abstract class Entry extends ObjectSelectionList.Entry<ChatSelectionList.Entry> { public Component getNarration() { return CommonComponents.EMPTY; } public boolean isSelected() { return false; } public boolean canSelect() { return false; } public boolean canReport() { return canSelect(); } } public class MessageEntry extends ChatSelectionList.Entry { private static final int CHECKMARK_WIDTH = 9; private static final int CHECKMARK_HEIGHT = 8; private static final int INDENT_AMOUNT = 11; private static final int TAG_MARGIN_LEFT = 4; private final int chatId; private final FormattedText text; private final Component narration; @Nullable private final List<FormattedCharSequence> hoverText; @Nullable private final GuiMessageTag.Icon tagIcon; @Nullable private final List<FormattedCharSequence> tagHoverText; private final boolean canReport; private final boolean playerMessage; public MessageEntry(int $$1, Component $$2, @Nullable Component $$3, GuiMessageTag $$4, boolean $$5, boolean $$6) { this.chatId = $$1; this.tagIcon = (GuiMessageTag.Icon)Optionull.map($$4, GuiMessageTag::icon); this.tagHoverText = ($$4 != null && $$4.text() != null) ? ChatSelectionScreen.this.font.split((FormattedText)$$4.text(), $$0.getRowWidth()) : null; this.canReport = $$5; this.playerMessage = $$6; FormattedText $$7 = ChatSelectionScreen.this.font.substrByWidth((FormattedText)$$2, getMaximumTextWidth() - ChatSelectionScreen.this.font.width((FormattedText)CommonComponents.ELLIPSIS)); if ($$2 != $$7) { this.text = FormattedText.composite(new FormattedText[] { $$7, (FormattedText)CommonComponents.ELLIPSIS }); this.hoverText = ChatSelectionScreen.this.font.split((FormattedText)$$2, $$0.getRowWidth()); } else { this.text = (FormattedText)$$2; this.hoverText = null; }  this.narration = $$3; } public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) { if (isSelected() && this.canReport) renderSelectedCheckmark($$0, $$2, $$3, $$5);  int $$10 = $$3 + getTextIndent(); Objects.requireNonNull(ChatSelectionScreen.this.font); int $$11 = $$2 + 1 + ($$5 - 9) / 2; $$0.drawString(ChatSelectionScreen.this.font, Language.getInstance().getVisualOrder(this.text), $$10, $$11, this.canReport ? -1 : -1593835521); if (this.hoverText != null && $$8) ChatSelectionScreen.this.setTooltipForNextRenderPass(this.hoverText);  int $$12 = ChatSelectionScreen.this.font.width(this.text); renderTag($$0, $$10 + $$12 + 4, $$2, $$5, $$6, $$7); } private void renderTag(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) { if (this.tagIcon != null) { int $$6 = $$2 + ($$3 - this.tagIcon.height) / 2; this.tagIcon.draw($$0, $$1, $$6); if (this.tagHoverText != null && $$4 >= $$1 && $$4 <= $$1 + this.tagIcon.width && $$5 >= $$6 && $$5 <= $$6 + this.tagIcon.height) ChatSelectionScreen.this.setTooltipForNextRenderPass(this.tagHoverText);  }  } private void renderSelectedCheckmark(GuiGraphics $$0, int $$1, int $$2, int $$3) { int $$4 = $$2; int $$5 = $$1 + ($$3 - 8) / 2; RenderSystem.enableBlend(); $$0.blitSprite(ChatSelectionScreen.CHECKMARK_SPRITE, $$4, $$5, 9, 8); RenderSystem.disableBlend(); } private int getMaximumTextWidth() { int $$0 = (this.tagIcon != null) ? (this.tagIcon.width + 4) : 0; return ChatSelectionScreen.ChatSelectionList.this.getRowWidth() - getTextIndent() - 4 - $$0; } private int getTextIndent() { return this.playerMessage ? 11 : 0; } public Component getNarration() { return isSelected() ? (Component)Component.translatable("narrator.select", new Object[] { this.narration }) : this.narration; } public boolean mouseClicked(double $$0, double $$1, int $$2) { ChatSelectionScreen.ChatSelectionList.this.setSelected((ChatSelectionScreen.ChatSelectionList.Entry)null); return toggleReport(); } public boolean keyPressed(int $$0, int $$1, int $$2) { if (CommonInputs.selected($$0)) return toggleReport();  return false; } public boolean isSelected() { return ChatSelectionScreen.this.report.isReported(this.chatId); } public boolean canSelect() { return true; } public boolean canReport() { return this.canReport; } private boolean toggleReport() { if (this.canReport) { ChatSelectionScreen.this.report.toggleReported(this.chatId); ChatSelectionScreen.this.updateConfirmSelectedButton(); return true; }  return false; } } public class MessageHeadingEntry extends ChatSelectionList.Entry { private static final int FACE_SIZE = 12; private final Component heading; private final Supplier<PlayerSkin> skin; private final boolean canReport; public MessageHeadingEntry(GameProfile $$1, Component $$2, boolean $$3) { this.heading = $$2; this.canReport = $$3; this.skin = $$0.minecraft.getSkinManager().lookupInsecure($$1); } public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) { int $$10 = $$3 - 12 - 4; int $$11 = $$2 + ($$5 - 12) / 2; PlayerFaceRenderer.draw($$0, this.skin.get(), $$10, $$11, 12); Objects.requireNonNull(ChatSelectionScreen.this.font); int $$12 = $$2 + 1 + ($$5 - 9) / 2; $$0.drawString(ChatSelectionScreen.this.font, this.heading, $$3, $$12, this.canReport ? -1 : -1593835521); } } public class DividerEntry extends ChatSelectionList.Entry { public Component getNarration() { return this.text; }
/*     */ 
/*     */     
/*     */     private static final int COLOR = -6250336;
/*     */     private final Component text;
/*     */     
/*     */     public DividerEntry(Component $$1) {
/*     */       this.text = $$1;
/*     */     }
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       int $$10 = $$2 + $$5 / 2;
/*     */       int $$11 = $$3 + $$4 - 8;
/*     */       int $$12 = ChatSelectionScreen.this.font.width((FormattedText)this.text);
/*     */       int $$13 = ($$3 + $$11 - $$12) / 2;
/*     */       Objects.requireNonNull(ChatSelectionScreen.this.font);
/*     */       int $$14 = $$10 - 9 / 2;
/*     */       $$0.drawString(ChatSelectionScreen.this.font, this.text, $$13, $$14, -6250336);
/*     */     } }
/*     */ 
/*     */   
/*     */   public class PaddingEntry extends ChatSelectionList.Entry {
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\ChatSelectionScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */