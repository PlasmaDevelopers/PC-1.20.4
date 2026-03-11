/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/*     */   private final Screen backgroundScreen;
/*     */   private final Component title;
/* 114 */   private Component message = CommonComponents.EMPTY;
/* 115 */   private int width = 250;
/*     */   @Nullable
/*     */   private ResourceLocation image;
/* 118 */   private final List<PopupScreen.ButtonOption> buttons = new ArrayList<>(); @Nullable
/* 119 */   private Runnable onClose = null;
/*     */ 
/*     */   
/*     */   public Builder(Screen $$0, Component $$1) {
/* 123 */     this.backgroundScreen = $$0;
/* 124 */     this.title = $$1;
/*     */   }
/*     */   
/*     */   public Builder setWidth(int $$0) {
/* 128 */     this.width = $$0;
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setImage(ResourceLocation $$0) {
/* 133 */     this.image = $$0;
/* 134 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setMessage(Component $$0) {
/* 138 */     this.message = $$0;
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public Builder addButton(Component $$0, Consumer<PopupScreen> $$1) {
/* 143 */     this.buttons.add(new PopupScreen.ButtonOption($$0, $$1));
/* 144 */     return this;
/*     */   }
/*     */   
/*     */   public Builder onClose(Runnable $$0) {
/* 148 */     this.onClose = $$0;
/* 149 */     return this;
/*     */   }
/*     */   
/*     */   public PopupScreen build() {
/* 153 */     if (this.buttons.isEmpty()) {
/* 154 */       throw new IllegalStateException("Popup must have at least one button");
/*     */     }
/* 156 */     return new PopupScreen(this.backgroundScreen, this.width, this.image, this.title, this.message, List.copyOf(this.buttons), this.onClose);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\PopupScreen$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */