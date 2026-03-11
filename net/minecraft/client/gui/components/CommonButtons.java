/*    */ package net.minecraft.client.gui.components;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class CommonButtons {
/*    */   public static SpriteIconButton language(int $$0, Button.OnPress $$1, boolean $$2) {
/*  8 */     return SpriteIconButton.builder((Component)Component.translatable("options.language"), $$1, $$2)
/*  9 */       .width($$0)
/* 10 */       .sprite(new ResourceLocation("icon/language"), 15, 15)
/* 11 */       .build();
/*    */   }
/*    */   
/*    */   public static SpriteIconButton accessibility(int $$0, Button.OnPress $$1, boolean $$2) {
/* 15 */     MutableComponent mutableComponent = $$2 ? Component.translatable("options.accessibility") : Component.translatable("accessibility.onboarding.accessibility.button");
/* 16 */     return SpriteIconButton.builder((Component)mutableComponent, $$1, $$2)
/* 17 */       .width($$0)
/* 18 */       .sprite(new ResourceLocation("icon/accessibility"), 15, 15)
/* 19 */       .build();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\CommonButtons.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */