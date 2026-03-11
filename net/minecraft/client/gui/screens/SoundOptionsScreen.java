/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.client.OptionInstance;
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.OptionsList;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ 
/*    */ public class SoundOptionsScreen
/*    */   extends OptionsSubScreen {
/*    */   private OptionsList list;
/*    */   
/*    */   private static OptionInstance<?>[] buttonOptions(Options $$0) {
/* 18 */     return (OptionInstance<?>[])new OptionInstance[] { $$0
/* 19 */         .showSubtitles(), $$0.directionalAudio() };
/*    */   }
/*    */ 
/*    */   
/*    */   public SoundOptionsScreen(Screen $$0, Options $$1) {
/* 24 */     super($$0, $$1, (Component)Component.translatable("options.sounds.title"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 29 */     this.list = addRenderableWidget(new OptionsList(this.minecraft, this.width, this.height - 64, 32, 25));
/* 30 */     this.list.addBig(this.options.getSoundSourceOptionInstance(SoundSource.MASTER));
/* 31 */     this.list.addSmall((OptionInstance[])getAllSoundOptionsExceptMaster());
/* 32 */     this.list.addBig(this.options.soundDevice());
/* 33 */     this.list.addSmall((OptionInstance[])buttonOptions(this.options));
/*    */     
/* 35 */     addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $$0 -> {
/*    */             this.minecraft.options.save();
/*    */             this.minecraft.setScreen(this.lastScreen);
/* 38 */           }).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
/*    */   }
/*    */   
/*    */   private OptionInstance<?>[] getAllSoundOptionsExceptMaster() {
/* 42 */     return (OptionInstance<?>[])Arrays.<SoundSource>stream(SoundSource.values()).filter($$0 -> ($$0 != SoundSource.MASTER)).map($$0 -> this.options.getSoundSourceOptionInstance($$0)).toArray($$0 -> new OptionInstance[$$0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 47 */     super.render($$0, $$1, $$2, $$3);
/* 48 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 53 */     renderDirtBackground($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\SoundOptionsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */