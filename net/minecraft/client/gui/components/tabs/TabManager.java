/*    */ package net.minecraft.client.gui.components.tabs;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*    */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*    */ import net.minecraft.client.resources.sounds.SoundInstance;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TabManager
/*    */ {
/*    */   private final Consumer<AbstractWidget> addWidget;
/*    */   private final Consumer<AbstractWidget> removeWidget;
/*    */   
/*    */   public TabManager(Consumer<AbstractWidget> $$0, Consumer<AbstractWidget> $$1) {
/* 22 */     this.addWidget = $$0;
/* 23 */     this.removeWidget = $$1;
/*    */   } @Nullable
/*    */   private Tab currentTab; @Nullable
/*    */   private ScreenRectangle tabArea; public void setTabArea(ScreenRectangle $$0) {
/* 27 */     this.tabArea = $$0;
/* 28 */     Tab $$1 = getCurrentTab();
/* 29 */     if ($$1 != null) {
/* 30 */       $$1.doLayout($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setCurrentTab(Tab $$0, boolean $$1) {
/* 35 */     if (!Objects.equals(this.currentTab, $$0)) {
/* 36 */       if (this.currentTab != null) {
/* 37 */         this.currentTab.visitChildren(this.removeWidget);
/*    */       }
/* 39 */       this.currentTab = $$0;
/* 40 */       $$0.visitChildren(this.addWidget);
/* 41 */       if (this.tabArea != null) {
/* 42 */         $$0.doLayout(this.tabArea);
/*    */       }
/* 44 */       if ($$1) {
/* 45 */         Minecraft.getInstance().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Tab getCurrentTab() {
/* 52 */     return this.currentTab;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\tabs\TabManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */