/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class PopupScreen extends Screen {
/*  21 */   private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("popup/background");
/*     */   
/*     */   private static final int SPACING = 12;
/*     */   
/*     */   private static final int BG_BORDER_WITH_SPACING = 18;
/*     */   private static final int BUTTON_SPACING = 6;
/*     */   private static final int IMAGE_SIZE_X = 130;
/*     */   private static final int IMAGE_SIZE_Y = 64;
/*     */   private static final int POPUP_DEFAULT_WIDTH = 250;
/*     */   private final Screen backgroundScreen;
/*     */   @Nullable
/*     */   private final ResourceLocation image;
/*     */   private final Component message;
/*     */   private final List<ButtonOption> buttons;
/*     */   @Nullable
/*     */   private final Runnable onClose;
/*     */   private final int contentWidth;
/*  38 */   private final LinearLayout layout = LinearLayout.vertical();
/*     */   
/*     */   PopupScreen(Screen $$0, int $$1, @Nullable ResourceLocation $$2, Component $$3, Component $$4, List<ButtonOption> $$5, @Nullable Runnable $$6) {
/*  41 */     super($$3);
/*  42 */     this.backgroundScreen = $$0;
/*  43 */     this.image = $$2;
/*  44 */     this.message = $$4;
/*  45 */     this.buttons = $$5;
/*  46 */     this.onClose = $$6;
/*  47 */     this.contentWidth = $$1 - 36;
/*     */   }
/*     */ 
/*     */   
/*     */   public void added() {
/*  52 */     super.added();
/*  53 */     this.backgroundScreen.clearFocus();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  58 */     this.layout.spacing(12).defaultCellSetting().alignHorizontallyCenter();
/*  59 */     this.layout.addChild((new MultiLineTextWidget((Component)this.title.copy().withStyle(ChatFormatting.BOLD), this.font)).setMaxWidth(this.contentWidth).setCentered(true));
/*  60 */     if (this.image != null) {
/*  61 */       this.layout.addChild(ImageWidget.texture(130, 64, this.image, 130, 64));
/*     */     }
/*  63 */     this.layout.addChild((new MultiLineTextWidget(this.message, this.font)).setMaxWidth(this.contentWidth).setCentered(true));
/*  64 */     this.layout.addChild((LayoutElement)buildButtonRow());
/*     */     
/*  66 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  67 */     repositionElements();
/*     */   }
/*     */   
/*     */   private LinearLayout buildButtonRow() {
/*  71 */     int $$0 = 6 * (this.buttons.size() - 1);
/*  72 */     int $$1 = Math.min((this.contentWidth - $$0) / this.buttons.size(), 150);
/*  73 */     LinearLayout $$2 = LinearLayout.horizontal();
/*  74 */     $$2.spacing(6);
/*  75 */     for (ButtonOption $$3 : this.buttons) {
/*  76 */       $$2.addChild(Button.builder($$3.message(), $$1 -> $$0.action().accept(this)).width($$1).build());
/*     */     }
/*  78 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/*  83 */     this.backgroundScreen.resize(this.minecraft, this.width, this.height);
/*  84 */     this.layout.arrangeElements();
/*  85 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  90 */     this.backgroundScreen.render($$0, -1, -1, $$3);
/*  91 */     $$0.flush();
/*  92 */     RenderSystem.clear(256, Minecraft.ON_OSX);
/*     */     
/*  94 */     renderTransparentBackground($$0);
/*  95 */     $$0.blitSprite(BACKGROUND_SPRITE, this.layout.getX() - 18, this.layout.getY() - 18, this.layout.getWidth() + 36, this.layout.getHeight() + 36);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/* 100 */     return (Component)CommonComponents.joinForNarration(new Component[] { this.title, this.message });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 105 */     if (this.onClose != null) {
/* 106 */       this.onClose.run();
/*     */     }
/* 108 */     this.minecraft.setScreen(this.backgroundScreen);
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private final Screen backgroundScreen;
/*     */     private final Component title;
/* 114 */     private Component message = CommonComponents.EMPTY;
/* 115 */     private int width = 250;
/*     */     @Nullable
/*     */     private ResourceLocation image;
/* 118 */     private final List<PopupScreen.ButtonOption> buttons = new ArrayList<>(); @Nullable
/* 119 */     private Runnable onClose = null;
/*     */ 
/*     */     
/*     */     public Builder(Screen $$0, Component $$1) {
/* 123 */       this.backgroundScreen = $$0;
/* 124 */       this.title = $$1;
/*     */     }
/*     */     
/*     */     public Builder setWidth(int $$0) {
/* 128 */       this.width = $$0;
/* 129 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setImage(ResourceLocation $$0) {
/* 133 */       this.image = $$0;
/* 134 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setMessage(Component $$0) {
/* 138 */       this.message = $$0;
/* 139 */       return this;
/*     */     }
/*     */     
/*     */     public Builder addButton(Component $$0, Consumer<PopupScreen> $$1) {
/* 143 */       this.buttons.add(new PopupScreen.ButtonOption($$0, $$1));
/* 144 */       return this;
/*     */     }
/*     */     
/*     */     public Builder onClose(Runnable $$0) {
/* 148 */       this.onClose = $$0;
/* 149 */       return this;
/*     */     }
/*     */     
/*     */     public PopupScreen build() {
/* 153 */       if (this.buttons.isEmpty()) {
/* 154 */         throw new IllegalStateException("Popup must have at least one button");
/*     */       }
/* 156 */       return new PopupScreen(this.backgroundScreen, this.width, this.image, this.title, this.message, List.copyOf(this.buttons), this.onClose);
/*     */     } }
/*     */   private static final class ButtonOption extends Record { private final Component message; private final Consumer<PopupScreen> action;
/*     */     
/* 160 */     ButtonOption(Component $$0, Consumer<PopupScreen> $$1) { this.message = $$0; this.action = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/components/PopupScreen$ButtonOption;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #160	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 160 */       //   0	7	0	this	Lnet/minecraft/client/gui/components/PopupScreen$ButtonOption; } public Component message() { return this.message; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/components/PopupScreen$ButtonOption;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #160	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/components/PopupScreen$ButtonOption; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/components/PopupScreen$ButtonOption;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #160	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/components/PopupScreen$ButtonOption;
/* 160 */       //   0	8	1	$$0	Ljava/lang/Object; } public Consumer<PopupScreen> action() { return this.action; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\PopupScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */