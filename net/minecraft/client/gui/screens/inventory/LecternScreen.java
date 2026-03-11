/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ContainerListener;
/*     */ import net.minecraft.world.inventory.LecternMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class LecternScreen extends BookViewScreen implements MenuAccess<LecternMenu> {
/*     */   private final LecternMenu menu;
/*     */   
/*  16 */   private final ContainerListener listener = new ContainerListener()
/*     */     {
/*     */       public void slotChanged(AbstractContainerMenu $$0, int $$1, ItemStack $$2) {
/*  19 */         LecternScreen.this.bookChanged();
/*     */       }
/*     */ 
/*     */       
/*     */       public void dataChanged(AbstractContainerMenu $$0, int $$1, int $$2) {
/*  24 */         if ($$1 == 0) {
/*  25 */           LecternScreen.this.pageChanged();
/*     */         }
/*     */       }
/*     */     };
/*     */   
/*     */   public LecternScreen(LecternMenu $$0, Inventory $$1, Component $$2) {
/*  31 */     this.menu = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public LecternMenu getMenu() {
/*  36 */     return this.menu;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  41 */     super.init();
/*  42 */     this.menu.addSlotListener(this.listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  47 */     this.minecraft.player.closeContainer();
/*  48 */     super.onClose();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/*  53 */     super.removed();
/*  54 */     this.menu.removeSlotListener(this.listener);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createMenuControls() {
/*  59 */     if (this.minecraft.player.mayBuild()) {
/*  60 */       addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> onClose()).bounds(this.width / 2 - 100, 196, 98, 20).build());
/*  61 */       addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("lectern.take_book"), $$0 -> sendButtonClick(3)).bounds(this.width / 2 + 2, 196, 98, 20).build());
/*     */     } else {
/*  63 */       super.createMenuControls();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pageBack() {
/*  69 */     sendButtonClick(1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pageForward() {
/*  74 */     sendButtonClick(2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean forcePage(int $$0) {
/*  79 */     if ($$0 != this.menu.getPage()) {
/*  80 */       sendButtonClick(100 + $$0);
/*  81 */       return true;
/*     */     } 
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   private void sendButtonClick(int $$0) {
/*  87 */     this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   void bookChanged() {
/*  96 */     ItemStack $$0 = this.menu.getBook();
/*  97 */     setBookAccess(BookViewScreen.BookAccess.fromItem($$0));
/*     */   }
/*     */   
/*     */   void pageChanged() {
/* 101 */     setPage(this.menu.getPage());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void closeScreen() {
/* 106 */     this.minecraft.player.closeContainer();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\LecternScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */