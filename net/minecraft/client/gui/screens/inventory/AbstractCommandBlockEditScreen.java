/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.CommandSuggestions;
/*     */ import net.minecraft.client.gui.components.CycleButton;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.world.level.BaseCommandBlock;
/*     */ 
/*     */ public abstract class AbstractCommandBlockEditScreen extends Screen {
/*  19 */   private static final Component SET_COMMAND_LABEL = (Component)Component.translatable("advMode.setCommand");
/*  20 */   private static final Component COMMAND_LABEL = (Component)Component.translatable("advMode.command");
/*  21 */   private static final Component PREVIOUS_OUTPUT_LABEL = (Component)Component.translatable("advMode.previousOutput");
/*     */   
/*     */   protected EditBox commandEdit;
/*     */   
/*     */   protected EditBox previousEdit;
/*     */   protected Button doneButton;
/*     */   protected Button cancelButton;
/*     */   protected CycleButton<Boolean> outputButton;
/*     */   CommandSuggestions commandSuggestions;
/*     */   
/*     */   public AbstractCommandBlockEditScreen() {
/*  32 */     super(GameNarrator.NO_TITLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  37 */     if (!getCommandBlock().isValid()) {
/*  38 */       onClose();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/*  48 */     this.doneButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> onDone()).bounds(this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20).build());
/*  49 */     this.cancelButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> onClose()).bounds(this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20).build());
/*  50 */     boolean $$0 = getCommandBlock().isTrackOutput();
/*  51 */     this.outputButton = (CycleButton<Boolean>)addRenderableWidget((GuiEventListener)CycleButton.booleanBuilder((Component)Component.literal("O"), (Component)Component.literal("X"))
/*  52 */         .withInitialValue(Boolean.valueOf($$0))
/*  53 */         .displayOnlyValue()
/*  54 */         .create(this.width / 2 + 150 - 20, getPreviousY(), 20, 20, (Component)Component.translatable("advMode.trackOutput"), ($$0, $$1) -> {
/*     */             BaseCommandBlock $$2 = getCommandBlock();
/*     */             
/*     */             $$2.setTrackOutput($$1.booleanValue());
/*     */             
/*     */             updatePreviousOutput($$1.booleanValue());
/*     */           }));
/*  61 */     this.commandEdit = new EditBox(this.font, this.width / 2 - 150, 50, 300, 20, (Component)Component.translatable("advMode.command"))
/*     */       {
/*     */         protected MutableComponent createNarrationMessage() {
/*  64 */           return super.createNarrationMessage().append(AbstractCommandBlockEditScreen.this.commandSuggestions.getNarrationMessage());
/*     */         }
/*     */       };
/*  67 */     this.commandEdit.setMaxLength(32500);
/*  68 */     this.commandEdit.setResponder(this::onEdited);
/*  69 */     addWidget((GuiEventListener)this.commandEdit);
/*     */     
/*  71 */     this.previousEdit = new EditBox(this.font, this.width / 2 - 150, getPreviousY(), 276, 20, (Component)Component.translatable("advMode.previousOutput"));
/*  72 */     this.previousEdit.setMaxLength(32500);
/*  73 */     this.previousEdit.setEditable(false);
/*  74 */     this.previousEdit.setValue("-");
/*  75 */     addWidget((GuiEventListener)this.previousEdit);
/*     */     
/*  77 */     setInitialFocus((GuiEventListener)this.commandEdit);
/*     */     
/*  79 */     this.commandSuggestions = new CommandSuggestions(this.minecraft, this, this.commandEdit, this.font, true, true, 0, 7, false, -2147483648);
/*  80 */     this.commandSuggestions.setAllowSuggestions(true);
/*  81 */     this.commandSuggestions.updateCommandInfo();
/*     */     
/*  83 */     updatePreviousOutput($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component getUsageNarration() {
/*  88 */     if (this.commandSuggestions.isVisible()) {
/*  89 */       return this.commandSuggestions.getUsageNarration();
/*     */     }
/*  91 */     return super.getUsageNarration();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(Minecraft $$0, int $$1, int $$2) {
/*  96 */     String $$3 = this.commandEdit.getValue();
/*  97 */     init($$0, $$1, $$2);
/*  98 */     this.commandEdit.setValue($$3);
/*     */     
/* 100 */     this.commandSuggestions.updateCommandInfo();
/*     */   }
/*     */   
/*     */   protected void updatePreviousOutput(boolean $$0) {
/* 104 */     this.previousEdit.setValue($$0 ? getCommandBlock().getLastOutput().getString() : "-");
/*     */   }
/*     */   
/*     */   protected void onDone() {
/* 108 */     BaseCommandBlock $$0 = getCommandBlock();
/* 109 */     populateAndSendPacket($$0);
/*     */     
/* 111 */     if (!$$0.isTrackOutput()) {
/* 112 */       $$0.setLastOutput(null);
/*     */     }
/* 114 */     this.minecraft.setScreen(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void onEdited(String $$0) {
/* 120 */     this.commandSuggestions.updateCommandInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 126 */     if (this.commandSuggestions.keyPressed($$0, $$1, $$2)) {
/* 127 */       return true;
/*     */     }
/*     */     
/* 130 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 131 */       return true;
/*     */     }
/*     */     
/* 134 */     if ($$0 == 257 || $$0 == 335) {
/* 135 */       onDone();
/* 136 */       return true;
/*     */     } 
/*     */     
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 144 */     if (this.commandSuggestions.mouseScrolled($$3)) {
/* 145 */       return true;
/*     */     }
/*     */     
/* 148 */     return super.mouseScrolled($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 153 */     if (this.commandSuggestions.mouseClicked($$0, $$1, $$2)) {
/* 154 */       return true;
/*     */     }
/*     */     
/* 157 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 162 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 164 */     $$0.drawCenteredString(this.font, SET_COMMAND_LABEL, this.width / 2, 20, 16777215);
/* 165 */     $$0.drawString(this.font, COMMAND_LABEL, this.width / 2 - 150 + 1, 40, 10526880);
/* 166 */     this.commandEdit.render($$0, $$1, $$2, $$3);
/*     */     
/* 168 */     int $$4 = 75;
/* 169 */     if (!this.previousEdit.getValue().isEmpty()) {
/* 170 */       Objects.requireNonNull(this.font); $$4 += 5 * 9 + 1 + getPreviousY() - 135;
/* 171 */       $$0.drawString(this.font, PREVIOUS_OUTPUT_LABEL, this.width / 2 - 150 + 1, $$4 + 4, 10526880);
/* 172 */       this.previousEdit.render($$0, $$1, $$2, $$3);
/*     */     } 
/*     */     
/* 175 */     this.commandSuggestions.render($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   abstract BaseCommandBlock getCommandBlock();
/*     */   
/*     */   abstract int getPreviousY();
/*     */   
/*     */   protected abstract void populateAndSendPacket(BaseCommandBlock paramBaseCommandBlock);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\AbstractCommandBlockEditScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */