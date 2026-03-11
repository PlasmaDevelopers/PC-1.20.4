/*     */ package net.minecraft.client.gui.spectator;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.spectator.categories.SpectatorPage;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class SpectatorMenu
/*     */ {
/*  14 */   static final ResourceLocation CLOSE_SPRITE = new ResourceLocation("spectator/close");
/*  15 */   static final ResourceLocation SCROLL_LEFT_SPRITE = new ResourceLocation("spectator/scroll_left");
/*  16 */   static final ResourceLocation SCROLL_RIGHT_SPRITE = new ResourceLocation("spectator/scroll_right");
/*  17 */   private static final SpectatorMenuItem CLOSE_ITEM = new CloseSpectatorItem();
/*  18 */   private static final SpectatorMenuItem SCROLL_LEFT = new ScrollMenuItem(-1, true);
/*  19 */   private static final SpectatorMenuItem SCROLL_RIGHT_ENABLED = new ScrollMenuItem(1, true);
/*  20 */   private static final SpectatorMenuItem SCROLL_RIGHT_DISABLED = new ScrollMenuItem(1, false);
/*     */   
/*     */   private static final int MAX_PER_PAGE = 8;
/*     */   
/*  24 */   static final Component CLOSE_MENU_TEXT = (Component)Component.translatable("spectatorMenu.close");
/*  25 */   static final Component PREVIOUS_PAGE_TEXT = (Component)Component.translatable("spectatorMenu.previous_page");
/*  26 */   static final Component NEXT_PAGE_TEXT = (Component)Component.translatable("spectatorMenu.next_page");
/*     */   
/*  28 */   public static final SpectatorMenuItem EMPTY_SLOT = new SpectatorMenuItem()
/*     */     {
/*     */       public void selectItem(SpectatorMenu $$0) {}
/*     */ 
/*     */ 
/*     */       
/*     */       public Component getName() {
/*  35 */         return CommonComponents.EMPTY;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void renderIcon(GuiGraphics $$0, float $$1, int $$2) {}
/*     */ 
/*     */       
/*     */       public boolean isEnabled() {
/*  44 */         return false;
/*     */       }
/*     */     };
/*     */   
/*     */   private final SpectatorMenuListener listener;
/*     */   private SpectatorMenuCategory category;
/*  50 */   private int selectedSlot = -1;
/*     */   int page;
/*     */   
/*     */   public SpectatorMenu(SpectatorMenuListener $$0) {
/*  54 */     this.category = new RootSpectatorMenuCategory();
/*  55 */     this.listener = $$0;
/*     */   }
/*     */   
/*     */   public SpectatorMenuItem getItem(int $$0) {
/*  59 */     int $$1 = $$0 + this.page * 6;
/*     */     
/*  61 */     if (this.page > 0 && $$0 == 0)
/*  62 */       return SCROLL_LEFT; 
/*  63 */     if ($$0 == 7) {
/*  64 */       if ($$1 < this.category.getItems().size()) {
/*  65 */         return SCROLL_RIGHT_ENABLED;
/*     */       }
/*  67 */       return SCROLL_RIGHT_DISABLED;
/*     */     } 
/*     */ 
/*     */     
/*  71 */     if ($$0 == 8) {
/*  72 */       return CLOSE_ITEM;
/*     */     }
/*     */     
/*  75 */     if ($$1 < 0 || $$1 >= this.category.getItems().size()) {
/*  76 */       return EMPTY_SLOT;
/*     */     }
/*  78 */     return (SpectatorMenuItem)MoreObjects.firstNonNull(this.category.getItems().get($$1), EMPTY_SLOT);
/*     */   }
/*     */   
/*     */   public List<SpectatorMenuItem> getItems() {
/*  82 */     List<SpectatorMenuItem> $$0 = Lists.newArrayList();
/*     */     
/*  84 */     for (int $$1 = 0; $$1 <= 8; $$1++) {
/*  85 */       $$0.add(getItem($$1));
/*     */     }
/*     */     
/*  88 */     return $$0;
/*     */   }
/*     */   
/*     */   public SpectatorMenuItem getSelectedItem() {
/*  92 */     return getItem(this.selectedSlot);
/*     */   }
/*     */   
/*     */   public SpectatorMenuCategory getSelectedCategory() {
/*  96 */     return this.category;
/*     */   }
/*     */   
/*     */   public void selectSlot(int $$0) {
/* 100 */     SpectatorMenuItem $$1 = getItem($$0);
/*     */     
/* 102 */     if ($$1 != EMPTY_SLOT) {
/* 103 */       if (this.selectedSlot == $$0 && $$1.isEnabled()) {
/* 104 */         $$1.selectItem(this);
/*     */       } else {
/* 106 */         this.selectedSlot = $$0;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void exit() {
/* 112 */     this.listener.onSpectatorMenuClosed(this);
/*     */   }
/*     */   
/*     */   public int getSelectedSlot() {
/* 116 */     return this.selectedSlot;
/*     */   }
/*     */   
/*     */   public void selectCategory(SpectatorMenuCategory $$0) {
/* 120 */     this.category = $$0;
/* 121 */     this.selectedSlot = -1;
/* 122 */     this.page = 0;
/*     */   }
/*     */   
/*     */   public SpectatorPage getCurrentPage() {
/* 126 */     return new SpectatorPage(getItems(), this.selectedSlot);
/*     */   }
/*     */   
/*     */   private static class CloseSpectatorItem
/*     */     implements SpectatorMenuItem {
/*     */     public void selectItem(SpectatorMenu $$0) {
/* 132 */       $$0.exit();
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getName() {
/* 137 */       return SpectatorMenu.CLOSE_MENU_TEXT;
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderIcon(GuiGraphics $$0, float $$1, int $$2) {
/* 142 */       $$0.blitSprite(SpectatorMenu.CLOSE_SPRITE, 0, 0, 16, 16);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEnabled() {
/* 147 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ScrollMenuItem implements SpectatorMenuItem {
/*     */     private final int direction;
/*     */     private final boolean enabled;
/*     */     
/*     */     public ScrollMenuItem(int $$0, boolean $$1) {
/* 156 */       this.direction = $$0;
/* 157 */       this.enabled = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void selectItem(SpectatorMenu $$0) {
/* 162 */       $$0.page += this.direction;
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getName() {
/* 167 */       return (this.direction < 0) ? SpectatorMenu.PREVIOUS_PAGE_TEXT : SpectatorMenu.NEXT_PAGE_TEXT;
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderIcon(GuiGraphics $$0, float $$1, int $$2) {
/* 172 */       if (this.direction < 0) {
/* 173 */         $$0.blitSprite(SpectatorMenu.SCROLL_LEFT_SPRITE, 0, 0, 16, 16);
/*     */       } else {
/* 175 */         $$0.blitSprite(SpectatorMenu.SCROLL_RIGHT_SPRITE, 0, 0, 16, 16);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEnabled() {
/* 181 */       return this.enabled;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\spectator\SpectatorMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */