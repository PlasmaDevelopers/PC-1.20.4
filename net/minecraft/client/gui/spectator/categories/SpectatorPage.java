/*    */ package net.minecraft.client.gui.spectator.categories;
/*    */ 
/*    */ import com.google.common.base.MoreObjects;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*    */ import net.minecraft.client.gui.spectator.SpectatorMenuItem;
/*    */ 
/*    */ 
/*    */ public class SpectatorPage
/*    */ {
/*    */   public static final int NO_SELECTION = -1;
/*    */   private final List<SpectatorMenuItem> items;
/*    */   private final int selection;
/*    */   
/*    */   public SpectatorPage(List<SpectatorMenuItem> $$0, int $$1) {
/* 16 */     this.items = $$0;
/* 17 */     this.selection = $$1;
/*    */   }
/*    */   
/*    */   public SpectatorMenuItem getItem(int $$0) {
/* 21 */     if ($$0 < 0 || $$0 >= this.items.size()) {
/* 22 */       return SpectatorMenu.EMPTY_SLOT;
/*    */     }
/*    */     
/* 25 */     return (SpectatorMenuItem)MoreObjects.firstNonNull(this.items.get($$0), SpectatorMenu.EMPTY_SLOT);
/*    */   }
/*    */   
/*    */   public int getSelectedSlot() {
/* 29 */     return this.selection;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\spectator\categories\SpectatorPage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */