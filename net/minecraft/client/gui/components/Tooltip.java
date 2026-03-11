/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.narration.NarratedElementType;
/*    */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*    */ import net.minecraft.client.gui.narration.NarrationSupplier;
/*    */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.client.gui.screens.inventory.tooltip.BelowOrAboveWidgetTooltipPositioner;
/*    */ import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
/*    */ import net.minecraft.client.gui.screens.inventory.tooltip.MenuTooltipPositioner;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ 
/*    */ public class Tooltip
/*    */   implements NarrationSupplier {
/*    */   private static final int MAX_WIDTH = 170;
/*    */   private final Component message;
/*    */   @Nullable
/*    */   private List<FormattedCharSequence> cachedTooltip;
/*    */   @Nullable
/*    */   private final Component narration;
/*    */   private int msDelay;
/*    */   private long hoverOrFocusedStartTime;
/*    */   private boolean wasHoveredOrFocused;
/*    */   
/*    */   private Tooltip(Component $$0, @Nullable Component $$1) {
/* 32 */     this.message = $$0;
/* 33 */     this.narration = $$1;
/*    */   }
/*    */   
/*    */   public void setDelay(int $$0) {
/* 37 */     this.msDelay = $$0;
/*    */   }
/*    */   
/*    */   public static Tooltip create(Component $$0, @Nullable Component $$1) {
/* 41 */     return new Tooltip($$0, $$1);
/*    */   }
/*    */   
/*    */   public static Tooltip create(Component $$0) {
/* 45 */     return new Tooltip($$0, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateNarration(NarrationElementOutput $$0) {
/* 50 */     if (this.narration != null) {
/* 51 */       $$0.add(NarratedElementType.HINT, this.narration);
/*    */     }
/*    */   }
/*    */   
/*    */   public List<FormattedCharSequence> toCharSequence(Minecraft $$0) {
/* 56 */     if (this.cachedTooltip == null) {
/* 57 */       this.cachedTooltip = splitTooltip($$0, this.message);
/*    */     }
/* 59 */     return this.cachedTooltip;
/*    */   }
/*    */   
/*    */   public static List<FormattedCharSequence> splitTooltip(Minecraft $$0, Component $$1) {
/* 63 */     return $$0.font.split((FormattedText)$$1, 170);
/*    */   }
/*    */   
/*    */   public void refreshTooltipForNextRenderPass(boolean $$0, boolean $$1, ScreenRectangle $$2) {
/* 67 */     boolean $$3 = ($$0 || ($$1 && Minecraft.getInstance().getLastInputType().isKeyboard()));
/* 68 */     if ($$3 != this.wasHoveredOrFocused) {
/* 69 */       if ($$3) {
/* 70 */         this.hoverOrFocusedStartTime = Util.getMillis();
/*    */       }
/* 72 */       this.wasHoveredOrFocused = $$3;
/*    */     } 
/*    */     
/* 75 */     if ($$3 && Util.getMillis() - this.hoverOrFocusedStartTime > this.msDelay) {
/* 76 */       Screen $$4 = (Minecraft.getInstance()).screen;
/* 77 */       if ($$4 != null) {
/* 78 */         $$4.setTooltipForNextRenderPass(this, createTooltipPositioner($$0, $$1, $$2), $$1);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected ClientTooltipPositioner createTooltipPositioner(boolean $$0, boolean $$1, ScreenRectangle $$2) {
/* 84 */     if (!$$0 && $$1 && Minecraft.getInstance().getLastInputType().isKeyboard()) {
/* 85 */       return (ClientTooltipPositioner)new BelowOrAboveWidgetTooltipPositioner($$2);
/*    */     }
/* 87 */     return (ClientTooltipPositioner)new MenuTooltipPositioner($$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\Tooltip.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */