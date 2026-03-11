/*    */ package net.minecraft.client.gui.components.tabs;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/*    */   private final int width;
/*    */   private final TabManager tabManager;
/* 65 */   private final List<Tab> tabs = new ArrayList<>();
/*    */   
/*    */   Builder(TabManager $$0, int $$1) {
/* 68 */     this.tabManager = $$0;
/* 69 */     this.width = $$1;
/*    */   }
/*    */   
/*    */   public Builder addTabs(Tab... $$0) {
/* 73 */     Collections.addAll(this.tabs, $$0);
/* 74 */     return this;
/*    */   }
/*    */   
/*    */   public TabNavigationBar build() {
/* 78 */     return new TabNavigationBar(this.width, this.tabManager, this.tabs);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\tabs\TabNavigationBar$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */