/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.ContainerEventHandler;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.ScreenDirection;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSelectionList<E extends AbstractSelectionList.Entry<E>>
/*     */   extends AbstractContainerWidget
/*     */ {
/*     */   protected static final int SCROLLBAR_WIDTH = 6;
/*  31 */   private static final ResourceLocation SCROLLER_SPRITE = new ResourceLocation("widget/scroller");
/*     */   
/*     */   protected final Minecraft minecraft;
/*     */   protected final int itemHeight;
/*  35 */   private final List<E> children = new TrackedList();
/*     */   protected boolean centerListVertically = true;
/*     */   private double scrollAmount;
/*     */   private boolean renderHeader;
/*     */   protected int headerHeight;
/*     */   private boolean scrolling;
/*     */   @Nullable
/*     */   private E selected;
/*     */   private boolean renderBackground = true;
/*     */   @Nullable
/*     */   private E hovered;
/*     */   
/*     */   public AbstractSelectionList(Minecraft $$0, int $$1, int $$2, int $$3, int $$4) {
/*  48 */     super(0, $$3, $$1, $$2, CommonComponents.EMPTY);
/*  49 */     this.minecraft = $$0;
/*  50 */     this.itemHeight = $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setRenderHeader(boolean $$0, int $$1) {
/*  55 */     this.renderHeader = $$0;
/*  56 */     this.headerHeight = $$1;
/*     */     
/*  58 */     if (!$$0) {
/*  59 */       this.headerHeight = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public int getRowWidth() {
/*  64 */     return 220;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public E getSelected() {
/*  69 */     return this.selected;
/*     */   }
/*     */   
/*     */   public void setSelected(@Nullable E $$0) {
/*  73 */     this.selected = $$0;
/*     */   }
/*     */   
/*     */   public E getFirstElement() {
/*  77 */     return this.children.get(0);
/*     */   }
/*     */   
/*     */   public void setRenderBackground(boolean $$0) {
/*  81 */     this.renderBackground = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public E getFocused() {
/*  88 */     return (E)super.getFocused();
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<E> children() {
/*  93 */     return this.children;
/*     */   }
/*     */   
/*     */   protected void clearEntries() {
/*  97 */     this.children.clear();
/*     */     
/*  99 */     this.selected = null;
/*     */   }
/*     */   
/*     */   protected void replaceEntries(Collection<E> $$0) {
/* 103 */     clearEntries();
/* 104 */     this.children.addAll($$0);
/*     */   }
/*     */   
/*     */   protected E getEntry(int $$0) {
/* 108 */     return children().get($$0);
/*     */   }
/*     */   
/*     */   protected int addEntry(E $$0) {
/* 112 */     this.children.add($$0);
/* 113 */     return this.children.size() - 1;
/*     */   }
/*     */   
/*     */   protected void addEntryToTop(E $$0) {
/* 117 */     double $$1 = getMaxScroll() - getScrollAmount();
/* 118 */     this.children.add(0, $$0);
/* 119 */     setScrollAmount(getMaxScroll() - $$1);
/*     */   }
/*     */   
/*     */   protected boolean removeEntryFromTop(E $$0) {
/* 123 */     double $$1 = getMaxScroll() - getScrollAmount();
/* 124 */     boolean $$2 = removeEntry($$0);
/* 125 */     setScrollAmount(getMaxScroll() - $$1);
/* 126 */     return $$2;
/*     */   }
/*     */   
/*     */   protected int getItemCount() {
/* 130 */     return children().size();
/*     */   }
/*     */   
/*     */   protected boolean isSelectedItem(int $$0) {
/* 134 */     return Objects.equals(getSelected(), children().get($$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected final E getEntryAtPosition(double $$0, double $$1) {
/* 139 */     int $$2 = getRowWidth() / 2;
/* 140 */     int $$3 = getX() + this.width / 2;
/*     */     
/* 142 */     int $$4 = $$3 - $$2;
/* 143 */     int $$5 = $$3 + $$2;
/*     */     
/* 145 */     int $$6 = Mth.floor($$1 - getY()) - this.headerHeight + (int)getScrollAmount() - 4;
/* 146 */     int $$7 = $$6 / this.itemHeight;
/* 147 */     if ($$0 < getScrollbarPosition() && $$0 >= $$4 && $$0 <= $$5 && $$7 >= 0 && $$6 >= 0 && $$7 < getItemCount()) {
/* 148 */       return children().get($$7);
/*     */     }
/* 150 */     return null;
/*     */   }
/*     */   
/*     */   protected int getMaxPosition() {
/* 154 */     return getItemCount() * this.itemHeight + this.headerHeight;
/*     */   }
/*     */   
/*     */   protected boolean clickedHeader(int $$0, int $$1) {
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderHeader(GuiGraphics $$0, int $$1, int $$2) {}
/*     */ 
/*     */   
/*     */   protected void renderDecorations(GuiGraphics $$0, int $$1, int $$2) {}
/*     */ 
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 169 */     this.hovered = isMouseOver($$1, $$2) ? getEntryAtPosition($$1, $$2) : null;
/*     */     
/* 171 */     if (this.renderBackground) {
/* 172 */       $$0.setColor(0.125F, 0.125F, 0.125F, 1.0F);
/* 173 */       int $$4 = 32;
/* 174 */       $$0.blit(Screen.BACKGROUND_LOCATION, getX(), getY(), getRight(), (getBottom() + (int)getScrollAmount()), this.width, this.height, 32, 32);
/* 175 */       $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */     
/* 178 */     enableScissor($$0);
/* 179 */     if (this.renderHeader) {
/* 180 */       int $$5 = getRowLeft();
/* 181 */       int $$6 = getY() + 4 - (int)getScrollAmount();
/* 182 */       renderHeader($$0, $$5, $$6);
/*     */     } 
/* 184 */     renderList($$0, $$1, $$2, $$3);
/* 185 */     $$0.disableScissor();
/*     */     
/* 187 */     if (this.renderBackground) {
/*     */       
/* 189 */       int $$7 = 4;
/* 190 */       $$0.fillGradient(RenderType.guiOverlay(), getX(), getY(), getRight(), getY() + 4, -16777216, 0, 0);
/* 191 */       $$0.fillGradient(RenderType.guiOverlay(), getX(), getBottom() - 4, getRight(), getBottom(), 0, -16777216, 0);
/*     */     } 
/*     */     
/* 194 */     int $$8 = getMaxScroll();
/* 195 */     if ($$8 > 0) {
/* 196 */       int $$9 = getScrollbarPosition();
/*     */       
/* 198 */       int $$10 = (int)((this.height * this.height) / getMaxPosition());
/* 199 */       $$10 = Mth.clamp($$10, 32, this.height - 8);
/*     */       
/* 201 */       int $$11 = (int)getScrollAmount() * (this.height - $$10) / $$8 + getY();
/* 202 */       if ($$11 < getY()) {
/* 203 */         $$11 = getY();
/*     */       }
/*     */       
/* 206 */       $$0.fill($$9, getY(), $$9 + 6, getBottom(), -16777216);
/* 207 */       $$0.blitSprite(SCROLLER_SPRITE, $$9, $$11, 6, $$10);
/*     */     } 
/*     */     
/* 210 */     renderDecorations($$0, $$1, $$2);
/*     */     
/* 212 */     RenderSystem.disableBlend();
/*     */   }
/*     */   
/*     */   protected void enableScissor(GuiGraphics $$0) {
/* 216 */     $$0.enableScissor(getX(), getY(), getRight(), getBottom());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void centerScrollOn(E $$0) {
/* 221 */     setScrollAmount((children().indexOf($$0) * this.itemHeight + this.itemHeight / 2 - this.height / 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void ensureVisible(E $$0) {
/* 228 */     int $$1 = getRowTop(children().indexOf($$0));
/* 229 */     int $$2 = $$1 - getY() - 4 - this.itemHeight;
/* 230 */     if ($$2 < 0) {
/* 231 */       scroll($$2);
/*     */     }
/*     */     
/* 234 */     int $$3 = getBottom() - $$1 - this.itemHeight - this.itemHeight;
/* 235 */     if ($$3 < 0) {
/* 236 */       scroll(-$$3);
/*     */     }
/*     */   }
/*     */   
/*     */   private void scroll(int $$0) {
/* 241 */     setScrollAmount(getScrollAmount() + $$0);
/*     */   }
/*     */   
/*     */   public double getScrollAmount() {
/* 245 */     return this.scrollAmount;
/*     */   }
/*     */   
/*     */   public void setScrollAmount(double $$0) {
/* 249 */     this.scrollAmount = Mth.clamp($$0, 0.0D, getMaxScroll());
/*     */   }
/*     */   
/*     */   public int getMaxScroll() {
/* 253 */     return Math.max(0, getMaxPosition() - this.height - 4);
/*     */   }
/*     */   
/*     */   protected void updateScrollingState(double $$0, double $$1, int $$2) {
/* 257 */     this.scrolling = ($$2 == 0 && $$0 >= getScrollbarPosition() && $$0 < (getScrollbarPosition() + 6));
/*     */   }
/*     */   
/*     */   protected int getScrollbarPosition() {
/* 261 */     return this.width / 2 + 124;
/*     */   }
/*     */   
/*     */   protected boolean isValidMouseClick(int $$0) {
/* 265 */     return ($$0 == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 270 */     if (!isValidMouseClick($$2)) {
/* 271 */       return false;
/*     */     }
/* 273 */     updateScrollingState($$0, $$1, $$2);
/* 274 */     if (!isMouseOver($$0, $$1)) {
/* 275 */       return false;
/*     */     }
/* 277 */     E $$3 = getEntryAtPosition($$0, $$1);
/* 278 */     if ($$3 != null) {
/* 279 */       if ($$3.mouseClicked($$0, $$1, $$2)) {
/* 280 */         E $$4 = getFocused();
/*     */         
/* 282 */         if ($$4 != $$3 && $$4 instanceof ContainerEventHandler) { ContainerEventHandler $$5 = (ContainerEventHandler)$$4;
/* 283 */           $$5.setFocused(null); }
/*     */         
/* 285 */         setFocused((GuiEventListener)$$3);
/* 286 */         setDragging(true);
/* 287 */         return true;
/*     */       } 
/* 289 */     } else if (clickedHeader((int)($$0 - (getX() + this.width / 2 - getRowWidth() / 2)), (int)($$1 - getY()) + (int)getScrollAmount() - 4)) {
/* 290 */       return true;
/*     */     } 
/* 292 */     return this.scrolling;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/* 297 */     if (getFocused() != null) {
/* 298 */       getFocused().mouseReleased($$0, $$1, $$2);
/*     */     }
/* 300 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 305 */     if (super.mouseDragged($$0, $$1, $$2, $$3, $$4)) {
/* 306 */       return true;
/*     */     }
/*     */     
/* 309 */     if ($$2 != 0 || !this.scrolling) {
/* 310 */       return false;
/*     */     }
/* 312 */     if ($$1 < getY()) {
/* 313 */       setScrollAmount(0.0D);
/* 314 */     } else if ($$1 > getBottom()) {
/* 315 */       setScrollAmount(getMaxScroll());
/*     */     } else {
/* 317 */       double $$5 = Math.max(1, getMaxScroll());
/* 318 */       int $$6 = this.height;
/* 319 */       int $$7 = Mth.clamp((int)(($$6 * $$6) / getMaxPosition()), 32, $$6 - 8);
/* 320 */       double $$8 = Math.max(1.0D, $$5 / ($$6 - $$7));
/* 321 */       setScrollAmount(getScrollAmount() + $$4 * $$8);
/*     */     } 
/* 323 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 328 */     setScrollAmount(getScrollAmount() - $$3 * this.itemHeight / 2.0D);
/* 329 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFocused(@Nullable GuiEventListener $$0) {
/* 334 */     super.setFocused($$0);
/*     */     
/* 336 */     int $$1 = this.children.indexOf($$0);
/* 337 */     if ($$1 >= 0) {
/* 338 */       Entry entry = (Entry)this.children.get($$1);
/* 339 */       setSelected((E)entry);
/* 340 */       if (this.minecraft.getLastInputType().isKeyboard()) {
/* 341 */         ensureVisible((E)entry);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected E nextEntry(ScreenDirection $$0) {
/* 348 */     return nextEntry($$0, $$0 -> true);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected E nextEntry(ScreenDirection $$0, Predicate<E> $$1) {
/* 353 */     return nextEntry($$0, $$1, getSelected());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected E nextEntry(ScreenDirection $$0, Predicate<E> $$1, @Nullable E $$2) {
/* 358 */     switch ($$0) { default: throw new IncompatibleClassChangeError();
/*     */       case RIGHT: case LEFT: 
/*     */       case UP: 
/* 361 */       case DOWN: break; }  int $$3 = 1;
/*     */     
/* 363 */     if (!children().isEmpty() && $$3 != 0) {
/*     */       int $$5;
/* 365 */       if ($$2 == null) {
/* 366 */         int $$4 = ($$3 > 0) ? 0 : (children().size() - 1);
/*     */       } else {
/* 368 */         $$5 = children().indexOf($$2) + $$3;
/*     */       } 
/*     */       int $$6;
/* 371 */       for ($$6 = $$5; $$6 >= 0 && $$6 < this.children.size(); $$6 += $$3) {
/* 372 */         Entry entry = (Entry)children().get($$6);
/* 373 */         if ($$1.test((E)entry)) {
/* 374 */           return (E)entry;
/*     */         }
/*     */       } 
/*     */     } 
/* 378 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseOver(double $$0, double $$1) {
/* 383 */     return ($$1 >= getY() && $$1 <= getBottom() && $$0 >= getX() && $$0 <= getRight());
/*     */   }
/*     */   
/*     */   protected void renderList(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 387 */     int $$4 = getRowLeft();
/* 388 */     int $$5 = getRowWidth();
/* 389 */     int $$6 = this.itemHeight - 4;
/*     */     
/* 391 */     int $$7 = getItemCount();
/* 392 */     for (int $$8 = 0; $$8 < $$7; $$8++) {
/* 393 */       int $$9 = getRowTop($$8);
/* 394 */       int $$10 = getRowBottom($$8);
/* 395 */       if ($$10 >= getY() && $$9 <= getBottom()) {
/* 396 */         renderItem($$0, $$1, $$2, $$3, $$8, $$4, $$9, $$5, $$6);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void renderItem(GuiGraphics $$0, int $$1, int $$2, float $$3, int $$4, int $$5, int $$6, int $$7, int $$8) {
/* 402 */     E $$9 = getEntry($$4);
/*     */     
/* 404 */     $$9.renderBack($$0, $$4, $$6, $$5, $$7, $$8, $$1, $$2, Objects.equals(this.hovered, $$9), $$3);
/*     */     
/* 406 */     if (isSelectedItem($$4)) {
/* 407 */       int $$10 = isFocused() ? -1 : -8355712;
/* 408 */       renderSelection($$0, $$6, $$7, $$8, $$10, -16777216);
/*     */     } 
/*     */     
/* 411 */     $$9.render($$0, $$4, $$6, $$5, $$7, $$8, $$1, $$2, Objects.equals(this.hovered, $$9), $$3);
/*     */   }
/*     */   
/*     */   protected void renderSelection(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5) {
/* 415 */     int $$6 = getX() + (this.width - $$2) / 2;
/* 416 */     int $$7 = getX() + (this.width + $$2) / 2;
/* 417 */     $$0.fill($$6, $$1 - 2, $$7, $$1 + $$3 + 2, $$4);
/* 418 */     $$0.fill($$6 + 1, $$1 - 1, $$7 - 1, $$1 + $$3 + 1, $$5);
/*     */   }
/*     */   
/*     */   public int getRowLeft() {
/* 422 */     return getX() + this.width / 2 - getRowWidth() / 2 + 2;
/*     */   }
/*     */   
/*     */   public int getRowRight() {
/* 426 */     return getRowLeft() + getRowWidth();
/*     */   }
/*     */   
/*     */   protected int getRowTop(int $$0) {
/* 430 */     return getY() + 4 - (int)getScrollAmount() + $$0 * this.itemHeight + this.headerHeight;
/*     */   }
/*     */   
/*     */   protected int getRowBottom(int $$0) {
/* 434 */     return getRowTop($$0) + this.itemHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public NarratableEntry.NarrationPriority narrationPriority() {
/* 439 */     if (isFocused()) {
/* 440 */       return NarratableEntry.NarrationPriority.FOCUSED;
/*     */     }
/* 442 */     if (this.hovered != null) {
/* 443 */       return NarratableEntry.NarrationPriority.HOVERED;
/*     */     }
/* 445 */     return NarratableEntry.NarrationPriority.NONE;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected E remove(int $$0) {
/* 450 */     Entry entry = (Entry)this.children.get($$0);
/* 451 */     if (removeEntry(this.children.get($$0))) {
/* 452 */       return (E)entry;
/*     */     }
/* 454 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean removeEntry(E $$0) {
/* 459 */     boolean $$1 = this.children.remove($$0);
/* 460 */     if ($$1 && $$0 == getSelected()) {
/* 461 */       setSelected((E)null);
/*     */     }
/* 463 */     return $$1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected E getHovered() {
/* 468 */     return this.hovered;
/*     */   }
/*     */   
/*     */   void bindEntryToSelf(Entry<E> $$0) {
/* 472 */     $$0.list = this;
/*     */   }
/*     */   
/*     */   protected void narrateListElementPosition(NarrationElementOutput $$0, E $$1) {
/* 476 */     List<E> $$2 = children();
/* 477 */     if ($$2.size() > 1) {
/* 478 */       int $$3 = $$2.indexOf($$1);
/* 479 */       if ($$3 != -1) {
/* 480 */         $$0.add(NarratedElementType.POSITION, (Component)Component.translatable("narrator.position.list", new Object[] { Integer.valueOf($$3 + 1), Integer.valueOf($$2.size()) }));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static abstract class Entry<E extends Entry<E>>
/*     */     implements GuiEventListener
/*     */   {
/*     */     @Deprecated
/*     */     AbstractSelectionList<E> list;
/*     */ 
/*     */     
/*     */     public void setFocused(boolean $$0) {}
/*     */     
/*     */     public boolean isFocused() {
/* 496 */       return (this.list.getFocused() == this);
/*     */     }
/*     */ 
/*     */     
/*     */     public abstract void render(GuiGraphics param1GuiGraphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, int param1Int7, boolean param1Boolean, float param1Float);
/*     */ 
/*     */     
/*     */     public void renderBack(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {}
/*     */     
/*     */     public boolean isMouseOver(double $$0, double $$1) {
/* 506 */       return Objects.equals(this.list.getEntryAtPosition($$0, $$1), this);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TrackedList extends AbstractList<E> {
/* 511 */     private final List<E> delegate = Lists.newArrayList();
/*     */ 
/*     */     
/*     */     public E get(int $$0) {
/* 515 */       return this.delegate.get($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int size() {
/* 520 */       return this.delegate.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public E set(int $$0, E $$1) {
/* 525 */       AbstractSelectionList.Entry entry = (AbstractSelectionList.Entry)this.delegate.set($$0, $$1);
/* 526 */       AbstractSelectionList.this.bindEntryToSelf((AbstractSelectionList.Entry)$$1);
/* 527 */       return (E)entry;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(int $$0, E $$1) {
/* 532 */       this.delegate.add($$0, $$1);
/* 533 */       AbstractSelectionList.this.bindEntryToSelf((AbstractSelectionList.Entry)$$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public E remove(int $$0) {
/* 538 */       return this.delegate.remove($$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\AbstractSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */