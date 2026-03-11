/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.Lighting;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.font.TextFieldHelper;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.blockentity.SignRenderer;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundSignUpdatePacket;
/*     */ import net.minecraft.world.level.block.SignBlock;
/*     */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SignText;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public abstract class AbstractSignEditScreen extends Screen {
/*     */   private final SignBlockEntity sign;
/*     */   private SignText text;
/*     */   private final String[] messages;
/*     */   private final boolean isFrontText;
/*     */   protected final WoodType woodType;
/*     */   private int frame;
/*     */   private int line;
/*     */   @Nullable
/*     */   private TextFieldHelper signField;
/*     */   
/*     */   public AbstractSignEditScreen(SignBlockEntity $$0, boolean $$1, boolean $$2) {
/*  37 */     this($$0, $$1, $$2, (Component)Component.translatable("sign.edit"));
/*     */   }
/*     */   
/*     */   public AbstractSignEditScreen(SignBlockEntity $$0, boolean $$1, boolean $$2, Component $$3) {
/*  41 */     super($$3);
/*     */     
/*  43 */     this.sign = $$0;
/*  44 */     this.text = $$0.getText($$1);
/*  45 */     this.isFrontText = $$1;
/*  46 */     this.woodType = SignBlock.getWoodType($$0.getBlockState().getBlock());
/*     */     
/*  48 */     this.messages = (String[])IntStream.range(0, 4).mapToObj($$1 -> this.text.getMessage($$1, $$0)).map(Component::getString).toArray($$0 -> new String[$$0]);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  53 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> onDone()).bounds(this.width / 2 - 100, this.height / 4 + 144, 200, 20).build());
/*     */     
/*  55 */     this
/*     */ 
/*     */ 
/*     */       
/*  59 */       .signField = new TextFieldHelper(() -> this.messages[this.line], this::setMessage, TextFieldHelper.createClipboardGetter(this.minecraft), TextFieldHelper.createClipboardSetter(this.minecraft), $$0 -> (this.minecraft.font.width($$0) <= this.sign.getMaxTextLineWidth()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/*  66 */     this.frame++;
/*     */     
/*  68 */     if (!isValid()) {
/*  69 */       onDone();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isValid() {
/*  74 */     return (this.minecraft != null && this.minecraft.player != null && 
/*     */       
/*  76 */       !this.sign.isRemoved() && 
/*  77 */       !this.sign.playerIsTooFarAwayToEdit(this.minecraft.player.getUUID()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  82 */     if ($$0 == 265) {
/*  83 */       this.line = this.line - 1 & 0x3;
/*  84 */       this.signField.setCursorToEnd();
/*  85 */       return true;
/*     */     } 
/*  87 */     if ($$0 == 264 || $$0 == 257 || $$0 == 335) {
/*  88 */       this.line = this.line + 1 & 0x3;
/*  89 */       this.signField.setCursorToEnd();
/*  90 */       return true;
/*     */     } 
/*  92 */     if (this.signField.keyPressed($$0)) {
/*  93 */       return true;
/*     */     }
/*     */     
/*  96 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(char $$0, int $$1) {
/* 101 */     this.signField.charTyped($$0);
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 107 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 109 */     Lighting.setupForFlatItems();
/*     */     
/* 111 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 40, 16777215);
/* 112 */     renderSign($$0);
/*     */     
/* 114 */     Lighting.setupFor3DItems();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 119 */     onDone();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/* 124 */     ClientPacketListener $$0 = this.minecraft.getConnection();
/* 125 */     if ($$0 != null) {
/* 126 */       $$0.send((Packet)new ServerboundSignUpdatePacket(this.sign.getBlockPos(), this.isFrontText, this.messages[0], this.messages[1], this.messages[2], this.messages[3]));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void offsetSign(GuiGraphics $$0, BlockState $$1) {
/* 140 */     $$0.pose().translate(this.width / 2.0F, 90.0F, 50.0F);
/*     */   }
/*     */   
/*     */   private void renderSign(GuiGraphics $$0) {
/* 144 */     BlockState $$1 = this.sign.getBlockState();
/*     */     
/* 146 */     $$0.pose().pushPose();
/* 147 */     offsetSign($$0, $$1);
/*     */     
/* 149 */     $$0.pose().pushPose();
/* 150 */     renderSignBackground($$0, $$1);
/* 151 */     $$0.pose().popPose();
/*     */     
/* 153 */     renderSignText($$0);
/* 154 */     $$0.pose().popPose();
/*     */   }
/*     */   
/*     */   private void renderSignText(GuiGraphics $$0) {
/* 158 */     $$0.pose().translate(0.0F, 0.0F, 4.0F);
/* 159 */     Vector3f $$1 = getSignTextScale();
/* 160 */     $$0.pose().scale($$1.x(), $$1.y(), $$1.z());
/* 161 */     int $$2 = this.text.hasGlowingText() ? this.text.getColor().getTextColor() : SignRenderer.getDarkColor(this.text);
/* 162 */     boolean $$3 = (this.frame / 6 % 2 == 0);
/* 163 */     int $$4 = this.signField.getCursorPos();
/* 164 */     int $$5 = this.signField.getSelectionPos();
/*     */     
/* 166 */     int $$6 = 4 * this.sign.getTextLineHeight() / 2;
/* 167 */     int $$7 = this.line * this.sign.getTextLineHeight() - $$6;
/*     */     
/* 169 */     for (int $$8 = 0; $$8 < this.messages.length; $$8++) {
/* 170 */       String $$9 = this.messages[$$8];
/* 171 */       if ($$9 != null) {
/*     */ 
/*     */         
/* 174 */         if (this.font.isBidirectional()) {
/* 175 */           $$9 = this.font.bidirectionalShaping($$9);
/*     */         }
/*     */         
/* 178 */         int $$10 = -this.font.width($$9) / 2;
/* 179 */         $$0.drawString(this.font, $$9, $$10, $$8 * this.sign.getTextLineHeight() - $$6, $$2, false);
/*     */ 
/*     */         
/* 182 */         if ($$8 == this.line && $$4 >= 0 && $$3) {
/*     */ 
/*     */ 
/*     */           
/* 186 */           int $$11 = this.font.width($$9.substring(0, Math.max(Math.min($$4, $$9.length()), 0)));
/* 187 */           int $$12 = $$11 - this.font.width($$9) / 2;
/*     */           
/* 189 */           if ($$4 >= $$9.length())
/* 190 */             $$0.drawString(this.font, "_", $$12, $$7, $$2, false); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 194 */     for (int $$13 = 0; $$13 < this.messages.length; $$13++) {
/* 195 */       String $$14 = this.messages[$$13];
/* 196 */       if ($$14 != null && $$13 == this.line && $$4 >= 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 201 */         int $$15 = this.font.width($$14.substring(0, Math.max(Math.min($$4, $$14.length()), 0)));
/* 202 */         int $$16 = $$15 - this.font.width($$14) / 2;
/*     */         
/* 204 */         if ($$3 && $$4 < $$14.length()) {
/* 205 */           $$0.fill($$16, $$7 - 1, $$16 + 1, $$7 + this.sign.getTextLineHeight(), 0xFF000000 | $$2);
/*     */         }
/*     */         
/* 208 */         if ($$5 != $$4) {
/* 209 */           int $$17 = Math.min($$4, $$5);
/* 210 */           int $$18 = Math.max($$4, $$5);
/* 211 */           int $$19 = this.font.width($$14.substring(0, $$17)) - this.font.width($$14) / 2;
/* 212 */           int $$20 = this.font.width($$14.substring(0, $$18)) - this.font.width($$14) / 2;
/*     */           
/* 214 */           int $$21 = Math.min($$19, $$20);
/* 215 */           int $$22 = Math.max($$19, $$20);
/*     */           
/* 217 */           $$0.fill(RenderType.guiTextHighlight(), $$21, $$7, $$22, $$7 + this.sign.getTextLineHeight(), -16776961);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void setMessage(String $$0) {
/* 223 */     this.messages[this.line] = $$0;
/* 224 */     this.text = this.text.setMessage(this.line, (Component)Component.literal($$0));
/* 225 */     this.sign.setText(this.text, this.isFrontText);
/*     */   }
/*     */   
/*     */   private void onDone() {
/* 229 */     this.minecraft.setScreen(null);
/*     */   }
/*     */   
/*     */   protected abstract void renderSignBackground(GuiGraphics paramGuiGraphics, BlockState paramBlockState);
/*     */   
/*     */   protected abstract Vector3f getSignTextScale();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\AbstractSignEditScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */