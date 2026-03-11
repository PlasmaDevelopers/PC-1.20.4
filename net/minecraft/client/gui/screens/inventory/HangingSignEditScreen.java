/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public class HangingSignEditScreen extends AbstractSignEditScreen {
/*    */   public static final float MAGIC_BACKGROUND_SCALE = 4.5F;
/* 12 */   private static final Vector3f TEXT_SCALE = new Vector3f(1.0F, 1.0F, 1.0F);
/*    */   private static final int TEXTURE_WIDTH = 16;
/*    */   private static final int TEXTURE_HEIGHT = 16;
/*    */   private final ResourceLocation texture;
/*    */   
/*    */   public HangingSignEditScreen(SignBlockEntity $$0, boolean $$1, boolean $$2) {
/* 18 */     super($$0, $$1, $$2, (Component)Component.translatable("hanging_sign.edit"));
/* 19 */     this.texture = new ResourceLocation("textures/gui/hanging_signs/" + this.woodType.name() + ".png");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void offsetSign(GuiGraphics $$0, BlockState $$1) {
/* 24 */     $$0.pose().translate(this.width / 2.0F, 125.0F, 50.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderSignBackground(GuiGraphics $$0, BlockState $$1) {
/* 29 */     $$0.pose().translate(0.0F, -13.0F, 0.0F);
/* 30 */     $$0.pose().scale(4.5F, 4.5F, 1.0F);
/*    */     
/* 32 */     $$0.blit(this.texture, -8, -8, 0.0F, 0.0F, 16, 16, 16, 16);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vector3f getSignTextScale() {
/* 37 */     return TEXT_SCALE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\HangingSignEditScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */