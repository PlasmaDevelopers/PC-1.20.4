/*     */ package net.minecraft.client.gui.screens.controls;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
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
/*     */ public class CategoryEntry
/*     */   extends KeyBindsList.Entry
/*     */ {
/*     */   final Component name;
/*     */   private final int width;
/*     */   
/*     */   public CategoryEntry(Component $$1) {
/*  73 */     this.name = $$1;
/*  74 */     this.width = (KeyBindsList.access$000($$0)).font.width((FormattedText)this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*  79 */     Objects.requireNonNull((KeyBindsList.access$300(KeyBindsList.this)).font); $$0.drawString((KeyBindsList.access$100(KeyBindsList.this)).font, this.name, (KeyBindsList.access$200(KeyBindsList.this)).screen.width / 2 - this.width / 2, $$2 + $$5 - 9 - 1, 16777215, false);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends GuiEventListener> children() {
/*  90 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends NarratableEntry> narratables() {
/*  95 */     return (List<? extends NarratableEntry>)ImmutableList.of(new NarratableEntry()
/*     */         {
/*     */           public NarratableEntry.NarrationPriority narrationPriority() {
/*  98 */             return NarratableEntry.NarrationPriority.HOVERED;
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateNarration(NarrationElementOutput $$0) {
/* 103 */             $$0.add(NarratedElementType.TITLE, KeyBindsList.CategoryEntry.this.name);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void refreshEntry() {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\controls\KeyBindsList$CategoryEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */