/*    */ package net.minecraft.client.gui.screens.controls;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.InputConstants;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.KeyMapping;
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.OptionsSubScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class KeyBindsScreen extends OptionsSubScreen {
/*    */   @Nullable
/*    */   public KeyMapping selectedKey;
/*    */   public long lastKeySelection;
/*    */   private KeyBindsList keyBindsList;
/*    */   private Button resetButton;
/*    */   
/*    */   public KeyBindsScreen(Screen $$0, Options $$1) {
/* 24 */     super($$0, $$1, (Component)Component.translatable("controls.keybinds.title"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 29 */     this.keyBindsList = (KeyBindsList)addRenderableWidget((GuiEventListener)new KeyBindsList(this, this.minecraft));
/*    */     
/* 31 */     this.resetButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("controls.resetAll"), $$0 -> {
/*    */             for (KeyMapping $$1 : this.options.keyMappings) {
/*    */               $$1.setKey($$1.getDefaultKey());
/*    */             }
/*    */             this.keyBindsList.resetMappingAndUpdateButtons();
/* 36 */           }).bounds(this.width / 2 - 155, this.height - 29, 150, 20).build());
/* 37 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 - 155 + 160, this.height - 29, 150, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 42 */     if (this.selectedKey != null) {
/* 43 */       this.options.setKey(this.selectedKey, InputConstants.Type.MOUSE.getOrCreate($$2));
/* 44 */       this.selectedKey = null;
/* 45 */       this.keyBindsList.resetMappingAndUpdateButtons();
/* 46 */       return true;
/*    */     } 
/* 48 */     return super.mouseClicked($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 53 */     if (this.selectedKey != null) {
/* 54 */       if ($$0 == 256) {
/* 55 */         this.options.setKey(this.selectedKey, InputConstants.UNKNOWN);
/*    */       } else {
/* 57 */         this.options.setKey(this.selectedKey, InputConstants.getKey($$0, $$1));
/*    */       } 
/*    */       
/* 60 */       this.selectedKey = null;
/* 61 */       this.lastKeySelection = Util.getMillis();
/* 62 */       this.keyBindsList.resetMappingAndUpdateButtons();
/* 63 */       return true;
/*    */     } 
/* 65 */     return super.keyPressed($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 70 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 72 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 8, 16777215);
/*    */     
/* 74 */     boolean $$4 = false;
/* 75 */     for (KeyMapping $$5 : this.options.keyMappings) {
/* 76 */       if (!$$5.isDefault()) {
/* 77 */         $$4 = true;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 82 */     this.resetButton.active = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 87 */     renderDirtBackground($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\controls\KeyBindsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */