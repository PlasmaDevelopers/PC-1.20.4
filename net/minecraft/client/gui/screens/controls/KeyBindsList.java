/*     */ package net.minecraft.client.gui.screens.controls;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.KeyMapping;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ContainerObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ 
/*     */ public class KeyBindsList extends ContainerObjectSelectionList<KeyBindsList.Entry> {
/*     */   final KeyBindsScreen keyBindsScreen;
/*     */   int maxNameWidth;
/*     */   
/*     */   public KeyBindsList(KeyBindsScreen $$0, Minecraft $$1) {
/*  31 */     super($$1, $$0.width + 45, $$0.height - 52, 20, 20);
/*  32 */     this.keyBindsScreen = $$0;
/*     */     
/*  34 */     KeyMapping[] $$2 = (KeyMapping[])ArrayUtils.clone((Object[])$$1.options.keyMappings);
/*  35 */     Arrays.sort((Object[])$$2);
/*  36 */     String $$3 = null;
/*     */     
/*  38 */     for (KeyMapping $$4 : $$2) {
/*  39 */       String $$5 = $$4.getCategory();
/*     */       
/*  41 */       if (!$$5.equals($$3)) {
/*  42 */         $$3 = $$5;
/*  43 */         addEntry((AbstractSelectionList.Entry)new CategoryEntry((Component)Component.translatable($$5)));
/*     */       } 
/*     */       
/*  46 */       MutableComponent mutableComponent = Component.translatable($$4.getName());
/*  47 */       int $$7 = $$1.font.width((FormattedText)mutableComponent);
/*  48 */       if ($$7 > this.maxNameWidth) {
/*  49 */         this.maxNameWidth = $$7;
/*     */       }
/*  51 */       addEntry((AbstractSelectionList.Entry)new KeyEntry($$4, (Component)mutableComponent));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void resetMappingAndUpdateButtons() {
/*  56 */     KeyMapping.resetMapping();
/*  57 */     refreshEntries();
/*     */   }
/*     */   
/*     */   public void refreshEntries() {
/*  61 */     children().forEach(Entry::refreshEntry);
/*     */   }
/*     */   
/*     */   public static abstract class Entry extends ContainerObjectSelectionList.Entry<Entry> {
/*     */     abstract void refreshEntry();
/*     */   }
/*     */   
/*     */   public class CategoryEntry extends Entry {
/*     */     final Component name;
/*     */     private final int width;
/*     */     
/*     */     public CategoryEntry(Component $$1) {
/*  73 */       this.name = $$1;
/*  74 */       this.width = $$0.minecraft.font.width((FormattedText)this.name);
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*  79 */       Objects.requireNonNull(KeyBindsList.this.minecraft.font); $$0.drawString(KeyBindsList.this.minecraft.font, this.name, KeyBindsList.this.minecraft.screen.width / 2 - this.width / 2, $$2 + $$5 - 9 - 1, 16777215, false);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/*  85 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<? extends GuiEventListener> children() {
/*  90 */       return Collections.emptyList();
/*     */     }
/*     */     
/*     */     public List<? extends NarratableEntry> narratables()
/*     */     {
/*  95 */       return (List<? extends NarratableEntry>)ImmutableList.of(new NarratableEntry()
/*     */           {
/*     */             public NarratableEntry.NarrationPriority narrationPriority() {
/*  98 */               return NarratableEntry.NarrationPriority.HOVERED;
/*     */             }
/*     */             
/*     */             public void updateNarration(NarrationElementOutput $$0)
/*     */             {
/* 103 */               $$0.add(NarratedElementType.TITLE, KeyBindsList.CategoryEntry.this.name); } }); } protected void refreshEntry() {} } class null implements NarratableEntry { public void updateNarration(NarrationElementOutput $$0) { $$0.add(NarratedElementType.TITLE, KeyBindsList.CategoryEntry.this.name); }
/*     */ 
/*     */ 
/*     */     
/*     */     public NarratableEntry.NarrationPriority narrationPriority() {
/*     */       return NarratableEntry.NarrationPriority.HOVERED;
/*     */     } }
/*     */ 
/*     */   
/*     */   public class KeyEntry
/*     */     extends Entry
/*     */   {
/*     */     private final KeyMapping key;
/*     */     private final Component name;
/*     */     private final Button changeButton;
/*     */     private final Button resetButton;
/*     */     private boolean hasCollision = false;
/*     */     
/*     */     KeyEntry(KeyMapping $$1, Component $$2) {
/* 122 */       this.key = $$1;
/* 123 */       this.name = $$2;
/*     */       
/* 125 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 134 */         .changeButton = Button.builder($$2, $$1 -> { KeyBindsList.this.keyBindsScreen.selectedKey = $$0; KeyBindsList.this.resetMappingAndUpdateButtons(); }).bounds(0, 0, 75, 20).createNarration($$2 -> $$0.isUnbound() ? Component.translatable("narrator.controls.unbound", new Object[] { $$1 }) : Component.translatable("narrator.controls.bound", new Object[] { $$1, $$2.get() })).build();
/*     */       
/* 136 */       this
/*     */ 
/*     */         
/* 139 */         .resetButton = Button.builder((Component)Component.translatable("controls.reset"), $$1 -> { KeyBindsList.this.minecraft.options.setKey($$0, $$0.getDefaultKey()); KeyBindsList.this.resetMappingAndUpdateButtons(); }).bounds(0, 0, 50, 20).createNarration($$1 -> Component.translatable("narrator.controls.reset", new Object[] { $$0 })).build();
/* 140 */       refreshEntry();
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 145 */       Objects.requireNonNull(KeyBindsList.this.minecraft.font); $$0.drawString(KeyBindsList.this.minecraft.font, this.name, $$3 + 90 - KeyBindsList.this.maxNameWidth, $$2 + $$5 / 2 - 9 / 2, 16777215, false);
/*     */       
/* 147 */       this.resetButton.setX($$3 + 190);
/* 148 */       this.resetButton.setY($$2);
/* 149 */       this.resetButton.render($$0, $$6, $$7, $$9);
/*     */       
/* 151 */       this.changeButton.setX($$3 + 105);
/* 152 */       this.changeButton.setY($$2);
/*     */       
/* 154 */       if (this.hasCollision) {
/* 155 */         int $$10 = 3;
/* 156 */         int $$11 = this.changeButton.getX() - 6;
/* 157 */         $$0.fill($$11, $$2 + 2, $$11 + 3, $$2 + $$5 + 2, ChatFormatting.RED.getColor().intValue() | 0xFF000000);
/*     */       } 
/*     */       
/* 160 */       this.changeButton.render($$0, $$6, $$7, $$9);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<? extends GuiEventListener> children() {
/* 165 */       return (List<? extends GuiEventListener>)ImmutableList.of(this.changeButton, this.resetButton);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<? extends NarratableEntry> narratables() {
/* 170 */       return (List<? extends NarratableEntry>)ImmutableList.of(this.changeButton, this.resetButton);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void refreshEntry() {
/* 175 */       this.changeButton.setMessage(this.key.getTranslatedKeyMessage());
/* 176 */       this.resetButton.active = !this.key.isDefault();
/* 177 */       this.hasCollision = false;
/* 178 */       MutableComponent $$0 = Component.empty();
/*     */       
/* 180 */       if (!this.key.isUnbound()) {
/* 181 */         for (KeyMapping $$1 : KeyBindsList.this.minecraft.options.keyMappings) {
/* 182 */           if ($$1 != this.key && this.key.same($$1)) {
/* 183 */             if (this.hasCollision) {
/* 184 */               $$0.append(", ");
/*     */             }
/* 186 */             this.hasCollision = true;
/* 187 */             $$0.append((Component)Component.translatable($$1.getName()));
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 192 */       if (this.hasCollision) {
/* 193 */         this.changeButton.setMessage((Component)Component.literal("[ ").append((Component)this.changeButton.getMessage().copy().withStyle(ChatFormatting.WHITE)).append(" ]").withStyle(ChatFormatting.RED));
/* 194 */         this.changeButton.setTooltip(Tooltip.create((Component)Component.translatable("controls.keybinds.duplicateKeybinds", new Object[] { $$0 })));
/*     */       } else {
/* 196 */         this.changeButton.setTooltip(null);
/*     */       } 
/*     */       
/* 199 */       if (KeyBindsList.this.keyBindsScreen.selectedKey == this.key) {
/* 200 */         this.changeButton.setMessage((Component)Component.literal("> ").append((Component)this.changeButton.getMessage().copy().withStyle(new ChatFormatting[] { ChatFormatting.WHITE, ChatFormatting.UNDERLINE })).append(" <").withStyle(ChatFormatting.YELLOW));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollbarPosition() {
/* 207 */     return super.getScrollbarPosition() + 15;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 212 */     return super.getRowWidth() + 32;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\controls\KeyBindsList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */