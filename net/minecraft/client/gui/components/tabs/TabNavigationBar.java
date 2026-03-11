/*     */ package net.minecraft.client.gui.components.tabs;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Renderable;
/*     */ import net.minecraft.client.gui.components.TabButton;
/*     */ import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
/*     */ import net.minecraft.client.gui.components.events.ContainerEventHandler;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*     */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class TabNavigationBar extends AbstractContainerEventHandler implements Renderable, NarratableEntry {
/*     */   private static final int NO_TAB = -1;
/*     */   private static final int MAX_WIDTH = 400;
/*     */   private static final int HEIGHT = 24;
/*     */   private static final int MARGIN = 14;
/*  36 */   private static final Component USAGE_NARRATION = (Component)Component.translatable("narration.tab_navigation.usage");
/*     */   
/*     */   private final GridLayout layout;
/*     */   
/*     */   private int width;
/*     */   
/*     */   private final TabManager tabManager;
/*     */   private final ImmutableList<Tab> tabs;
/*     */   private final ImmutableList<TabButton> tabButtons;
/*     */   
/*     */   TabNavigationBar(int $$0, TabManager $$1, Iterable<Tab> $$2) {
/*  47 */     this.width = $$0;
/*  48 */     this.tabManager = $$1;
/*  49 */     this.tabs = ImmutableList.copyOf($$2);
/*     */     
/*  51 */     this.layout = new GridLayout(0, 0);
/*  52 */     this.layout.defaultCellSetting().alignHorizontallyCenter();
/*     */     
/*  54 */     ImmutableList.Builder<TabButton> $$3 = ImmutableList.builder();
/*  55 */     int $$4 = 0;
/*  56 */     for (Tab $$5 : $$2) {
/*  57 */       $$3.add(this.layout.addChild((LayoutElement)new TabButton($$1, $$5, 0, 24), 0, $$4++));
/*     */     }
/*  59 */     this.tabButtons = $$3.build();
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private final int width;
/*     */     private final TabManager tabManager;
/*  65 */     private final List<Tab> tabs = new ArrayList<>();
/*     */     
/*     */     Builder(TabManager $$0, int $$1) {
/*  68 */       this.tabManager = $$0;
/*  69 */       this.width = $$1;
/*     */     }
/*     */     
/*     */     public Builder addTabs(Tab... $$0) {
/*  73 */       Collections.addAll(this.tabs, $$0);
/*  74 */       return this;
/*     */     }
/*     */     
/*     */     public TabNavigationBar build() {
/*  78 */       return new TabNavigationBar(this.width, this.tabManager, this.tabs);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder builder(TabManager $$0, int $$1) {
/*  83 */     return new Builder($$0, $$1);
/*     */   }
/*     */   
/*     */   public void setWidth(int $$0) {
/*  87 */     this.width = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFocused(boolean $$0) {
/*  92 */     super.setFocused($$0);
/*  93 */     if (getFocused() != null) {
/*  94 */       getFocused().setFocused($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFocused(@Nullable GuiEventListener $$0) {
/* 100 */     super.setFocused($$0);
/* 101 */     if ($$0 instanceof TabButton) { TabButton $$1 = (TabButton)$$0;
/* 102 */       this.tabManager.setCurrentTab($$1.tab(), true); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/* 109 */     if (!isFocused()) {
/* 110 */       TabButton $$1 = currentTabButton();
/* 111 */       if ($$1 != null) {
/* 112 */         return ComponentPath.path((ContainerEventHandler)this, ComponentPath.leaf((GuiEventListener)$$1));
/*     */       }
/*     */     } 
/* 115 */     if ($$0 instanceof FocusNavigationEvent.TabNavigation) {
/* 116 */       return null;
/*     */     }
/* 118 */     return super.nextFocusPath($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends GuiEventListener> children() {
/* 123 */     return (List)this.tabButtons;
/*     */   }
/*     */ 
/*     */   
/*     */   public NarratableEntry.NarrationPriority narrationPriority() {
/* 128 */     return this.tabButtons.stream()
/* 129 */       .map(AbstractWidget::narrationPriority)
/* 130 */       .max(Comparator.naturalOrder())
/* 131 */       .orElse(NarratableEntry.NarrationPriority.NONE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNarration(NarrationElementOutput $$0) {
/* 137 */     Optional<TabButton> $$1 = this.tabButtons.stream().filter(AbstractWidget::isHovered).findFirst().or(() -> Optional.ofNullable(currentTabButton()));
/* 138 */     $$1.ifPresent($$1 -> {
/*     */           narrateListElementPosition($$0.nest(), $$1);
/*     */           $$1.updateNarration($$0);
/*     */         });
/* 142 */     if (isFocused()) {
/* 143 */       $$0.add(NarratedElementType.USAGE, USAGE_NARRATION);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void narrateListElementPosition(NarrationElementOutput $$0, TabButton $$1) {
/* 148 */     if (this.tabs.size() > 1) {
/* 149 */       int $$2 = this.tabButtons.indexOf($$1);
/* 150 */       if ($$2 != -1) {
/* 151 */         $$0.add(NarratedElementType.POSITION, (Component)Component.translatable("narrator.position.tab", new Object[] { Integer.valueOf($$2 + 1), Integer.valueOf(this.tabs.size()) }));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 158 */     $$0.fill(0, 0, this.width, 24, -16777216);
/* 159 */     $$0.blit(CreateWorldScreen.HEADER_SEPERATOR, 0, this.layout.getY() + this.layout.getHeight() - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
/* 160 */     for (UnmodifiableIterator<TabButton> unmodifiableIterator = this.tabButtons.iterator(); unmodifiableIterator.hasNext(); ) { TabButton $$4 = unmodifiableIterator.next();
/* 161 */       $$4.render($$0, $$1, $$2, $$3); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public ScreenRectangle getRectangle() {
/* 167 */     return this.layout.getRectangle();
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrangeElements() {
/* 172 */     int $$0 = Math.min(400, this.width) - 28;
/* 173 */     int $$1 = Mth.roundToward($$0 / this.tabs.size(), 2);
/* 174 */     for (UnmodifiableIterator<TabButton> unmodifiableIterator = this.tabButtons.iterator(); unmodifiableIterator.hasNext(); ) { TabButton $$2 = unmodifiableIterator.next();
/* 175 */       $$2.setWidth($$1); }
/*     */     
/* 177 */     this.layout.arrangeElements();
/* 178 */     this.layout.setX(Mth.roundToward((this.width - $$0) / 2, 2));
/* 179 */     this.layout.setY(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectTab(int $$0, boolean $$1) {
/* 184 */     if (isFocused()) {
/* 185 */       setFocused((GuiEventListener)this.tabButtons.get($$0));
/*     */     } else {
/* 187 */       this.tabManager.setCurrentTab((Tab)this.tabs.get($$0), $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean keyPressed(int $$0) {
/* 192 */     if (Screen.hasControlDown()) {
/* 193 */       int $$1 = getNextTabIndex($$0);
/* 194 */       if ($$1 != -1) {
/* 195 */         selectTab(Mth.clamp($$1, 0, this.tabs.size() - 1), true);
/* 196 */         return true;
/*     */       } 
/*     */     } 
/* 199 */     return false;
/*     */   }
/*     */   
/*     */   private int getNextTabIndex(int $$0) {
/* 203 */     if ($$0 >= 49 && $$0 <= 57)
/* 204 */       return $$0 - 49; 
/* 205 */     if ($$0 == 258) {
/* 206 */       int $$1 = currentTabIndex();
/* 207 */       if ($$1 != -1) {
/* 208 */         int $$2 = Screen.hasShiftDown() ? ($$1 - 1) : ($$1 + 1);
/* 209 */         return Math.floorMod($$2, this.tabs.size());
/*     */       } 
/*     */     } 
/* 212 */     return -1;
/*     */   }
/*     */   
/*     */   private int currentTabIndex() {
/* 216 */     Tab $$0 = this.tabManager.getCurrentTab();
/* 217 */     int $$1 = this.tabs.indexOf($$0);
/* 218 */     return ($$1 != -1) ? $$1 : -1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private TabButton currentTabButton() {
/* 223 */     int $$0 = currentTabIndex();
/* 224 */     return ($$0 != -1) ? (TabButton)this.tabButtons.get($$0) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\tabs\TabNavigationBar.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */