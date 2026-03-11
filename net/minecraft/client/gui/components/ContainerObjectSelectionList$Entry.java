/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.ContainerEventHandler;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.Mth;
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
/*     */ 
/*     */ 
/*     */ public abstract class Entry<E extends ContainerObjectSelectionList.Entry<E>>
/*     */   extends AbstractSelectionList.Entry<E>
/*     */   implements ContainerEventHandler
/*     */ {
/*     */   @Nullable
/*     */   private GuiEventListener focused;
/*     */   @Nullable
/*     */   private NarratableEntry lastNarratable;
/*     */   private boolean dragging;
/*     */   
/*     */   public boolean isDragging() {
/* 125 */     return this.dragging;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDragging(boolean $$0) {
/* 130 */     this.dragging = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 135 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFocused(@Nullable GuiEventListener $$0) {
/* 140 */     if (this.focused != null) {
/* 141 */       this.focused.setFocused(false);
/*     */     }
/* 143 */     if ($$0 != null) {
/* 144 */       $$0.setFocused(true);
/*     */     }
/* 146 */     this.focused = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GuiEventListener getFocused() {
/* 152 */     return this.focused;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ComponentPath focusPathAtIndex(FocusNavigationEvent $$0, int $$1) {
/* 157 */     if (children().isEmpty()) {
/* 158 */       return null;
/*     */     }
/* 160 */     ComponentPath $$2 = ((GuiEventListener)children().get(Math.min($$1, children().size() - 1))).nextFocusPath($$0);
/* 161 */     return ComponentPath.path(this, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/* 167 */     if ($$0 instanceof FocusNavigationEvent.ArrowNavigation) { FocusNavigationEvent.ArrowNavigation $$1 = (FocusNavigationEvent.ArrowNavigation)$$0;
/* 168 */       switch (ContainerObjectSelectionList.null.$SwitchMap$net$minecraft$client$gui$navigation$ScreenDirection[$$1.direction().ordinal()]) { default: throw new IncompatibleClassChangeError();
/*     */         case 3: case 4: 
/*     */         case 1: 
/* 171 */         case 2: break; }  int $$2 = 1;
/*     */       
/* 173 */       if ($$2 == 0) {
/* 174 */         return null;
/*     */       }
/* 176 */       int $$3 = Mth.clamp($$2 + children().indexOf(getFocused()), 0, children().size() - 1); int $$4;
/* 177 */       for ($$4 = $$3; $$4 >= 0 && $$4 < children().size(); $$4 += $$2) {
/* 178 */         GuiEventListener $$5 = children().get($$4);
/* 179 */         ComponentPath $$6 = $$5.nextFocusPath($$0);
/* 180 */         if ($$6 != null) {
/* 181 */           return ComponentPath.path(this, $$6);
/*     */         }
/*     */       }  }
/*     */ 
/*     */     
/* 186 */     return super.nextFocusPath($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void updateNarration(NarrationElementOutput $$0) {
/* 192 */     List<? extends NarratableEntry> $$1 = narratables();
/* 193 */     Screen.NarratableSearchResult $$2 = Screen.findNarratableWidget($$1, this.lastNarratable);
/* 194 */     if ($$2 != null) {
/* 195 */       if ($$2.priority.isTerminal()) {
/* 196 */         this.lastNarratable = $$2.entry;
/*     */       }
/* 198 */       if ($$1.size() > 1) {
/* 199 */         $$0.add(NarratedElementType.POSITION, (Component)Component.translatable("narrator.position.object_list", new Object[] { Integer.valueOf($$2.index + 1), Integer.valueOf($$1.size()) }));
/* 200 */         if ($$2.priority == NarratableEntry.NarrationPriority.FOCUSED) {
/* 201 */           $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.component_list.usage"));
/*     */         }
/*     */       } 
/* 204 */       $$2.entry.updateNarration($$0.nest());
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract List<? extends NarratableEntry> narratables();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\ContainerObjectSelectionList$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */