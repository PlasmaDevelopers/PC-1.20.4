/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.OptionInstance;
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.CycleButton;
/*    */ import net.minecraft.client.gui.components.OptionsList;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public abstract class SimpleOptionsSubScreen
/*    */   extends OptionsSubScreen
/*    */ {
/*    */   protected final OptionInstance<?>[] smallOptions;
/*    */   @Nullable
/*    */   private AbstractWidget narratorButton;
/*    */   protected OptionsList list;
/*    */   
/*    */   public SimpleOptionsSubScreen(Screen $$0, Options $$1, Component $$2, OptionInstance<?>[] $$3) {
/* 23 */     super($$0, $$1, $$2);
/* 24 */     this.smallOptions = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 29 */     this.list = addRenderableWidget(new OptionsList(this.minecraft, this.width, this.height - 64, 32, 25));
/* 30 */     this.list.addSmall((OptionInstance[])this.smallOptions);
/*    */     
/* 32 */     createFooter();
/*    */     
/* 34 */     this.narratorButton = this.list.findOption(this.options.narrator());
/* 35 */     if (this.narratorButton != null) {
/* 36 */       this.narratorButton.active = this.minecraft.getNarrator().isActive();
/*    */     }
/*    */   }
/*    */   
/*    */   protected void createFooter() {
/* 41 */     addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 46 */     super.render($$0, $$1, $$2, $$3);
/* 47 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 52 */     renderDirtBackground($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateNarratorButton() {
/* 57 */     if (this.narratorButton instanceof CycleButton)
/* 58 */       ((CycleButton)this.narratorButton).setValue(this.options.narrator().get()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\SimpleOptionsSubScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */