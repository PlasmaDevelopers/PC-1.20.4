/*     */ package net.minecraft.client.gui.screens.controls;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.KeyMapping;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
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
/*     */ public class KeyEntry
/*     */   extends KeyBindsList.Entry
/*     */ {
/*     */   private final KeyMapping key;
/*     */   private final Component name;
/*     */   private final Button changeButton;
/*     */   private final Button resetButton;
/*     */   private boolean hasCollision = false;
/*     */   
/*     */   KeyEntry(KeyMapping $$1, Component $$2) {
/* 122 */     this.key = $$1;
/* 123 */     this.name = $$2;
/*     */     
/* 125 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       .changeButton = Button.builder($$2, $$1 -> { KeyBindsList.this.keyBindsScreen.selectedKey = $$0; KeyBindsList.this.resetMappingAndUpdateButtons(); }).bounds(0, 0, 75, 20).createNarration($$2 -> $$0.isUnbound() ? Component.translatable("narrator.controls.unbound", new Object[] { $$1 }) : Component.translatable("narrator.controls.bound", new Object[] { $$1, $$2.get() })).build();
/*     */     
/* 136 */     this
/*     */ 
/*     */       
/* 139 */       .resetButton = Button.builder((Component)Component.translatable("controls.reset"), $$1 -> { (KeyBindsList.access$700(KeyBindsList.this)).options.setKey($$0, $$0.getDefaultKey()); KeyBindsList.this.resetMappingAndUpdateButtons(); }).bounds(0, 0, 50, 20).createNarration($$1 -> Component.translatable("narrator.controls.reset", new Object[] { $$0 })).build();
/* 140 */     refreshEntry();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 145 */     Objects.requireNonNull((KeyBindsList.access$500(KeyBindsList.this)).font); $$0.drawString((KeyBindsList.access$400(KeyBindsList.this)).font, this.name, $$3 + 90 - KeyBindsList.this.maxNameWidth, $$2 + $$5 / 2 - 9 / 2, 16777215, false);
/*     */     
/* 147 */     this.resetButton.setX($$3 + 190);
/* 148 */     this.resetButton.setY($$2);
/* 149 */     this.resetButton.render($$0, $$6, $$7, $$9);
/*     */     
/* 151 */     this.changeButton.setX($$3 + 105);
/* 152 */     this.changeButton.setY($$2);
/*     */     
/* 154 */     if (this.hasCollision) {
/* 155 */       int $$10 = 3;
/* 156 */       int $$11 = this.changeButton.getX() - 6;
/* 157 */       $$0.fill($$11, $$2 + 2, $$11 + 3, $$2 + $$5 + 2, ChatFormatting.RED.getColor().intValue() | 0xFF000000);
/*     */     } 
/*     */     
/* 160 */     this.changeButton.render($$0, $$6, $$7, $$9);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends GuiEventListener> children() {
/* 165 */     return (List<? extends GuiEventListener>)ImmutableList.of(this.changeButton, this.resetButton);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends NarratableEntry> narratables() {
/* 170 */     return (List<? extends NarratableEntry>)ImmutableList.of(this.changeButton, this.resetButton);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void refreshEntry() {
/* 175 */     this.changeButton.setMessage(this.key.getTranslatedKeyMessage());
/* 176 */     this.resetButton.active = !this.key.isDefault();
/* 177 */     this.hasCollision = false;
/* 178 */     MutableComponent $$0 = Component.empty();
/*     */     
/* 180 */     if (!this.key.isUnbound()) {
/* 181 */       for (KeyMapping $$1 : (KeyBindsList.access$600(KeyBindsList.this)).options.keyMappings) {
/* 182 */         if ($$1 != this.key && this.key.same($$1)) {
/* 183 */           if (this.hasCollision) {
/* 184 */             $$0.append(", ");
/*     */           }
/* 186 */           this.hasCollision = true;
/* 187 */           $$0.append((Component)Component.translatable($$1.getName()));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 192 */     if (this.hasCollision) {
/* 193 */       this.changeButton.setMessage((Component)Component.literal("[ ").append((Component)this.changeButton.getMessage().copy().withStyle(ChatFormatting.WHITE)).append(" ]").withStyle(ChatFormatting.RED));
/* 194 */       this.changeButton.setTooltip(Tooltip.create((Component)Component.translatable("controls.keybinds.duplicateKeybinds", new Object[] { $$0 })));
/*     */     } else {
/* 196 */       this.changeButton.setTooltip(null);
/*     */     } 
/*     */     
/* 199 */     if (KeyBindsList.this.keyBindsScreen.selectedKey == this.key)
/* 200 */       this.changeButton.setMessage((Component)Component.literal("> ").append((Component)this.changeButton.getMessage().copy().withStyle(new ChatFormatting[] { ChatFormatting.WHITE, ChatFormatting.UNDERLINE })).append(" <").withStyle(ChatFormatting.YELLOW)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\controls\KeyBindsList$KeyEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */