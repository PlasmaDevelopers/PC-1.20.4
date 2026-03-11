/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.material.MapColor;
/*     */ import net.minecraft.world.level.saveddata.maps.MapDecoration;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ import org.joml.Matrix4f;
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
/*     */ class MapInstance
/*     */   implements AutoCloseable
/*     */ {
/*     */   private MapItemSavedData data;
/*     */   private final DynamicTexture texture;
/*     */   private final RenderType renderType;
/*     */   private boolean requiresUpload = true;
/*     */   
/*     */   MapInstance(int $$0, MapItemSavedData $$1) {
/*  75 */     this.data = $$1;
/*  76 */     this.texture = new DynamicTexture(128, 128, true);
/*  77 */     ResourceLocation $$2 = paramMapRenderer.textureManager.register("map/" + $$0, this.texture);
/*  78 */     this.renderType = RenderType.text($$2);
/*     */   }
/*     */   
/*     */   void replaceMapData(MapItemSavedData $$0) {
/*  82 */     boolean $$1 = (this.data != $$0);
/*  83 */     this.data = $$0;
/*  84 */     this.requiresUpload |= $$1;
/*     */   }
/*     */   
/*     */   public void forceUpload() {
/*  88 */     this.requiresUpload = true;
/*     */   }
/*     */   
/*     */   private void updateTexture() {
/*  92 */     for (int $$0 = 0; $$0 < 128; $$0++) {
/*  93 */       for (int $$1 = 0; $$1 < 128; $$1++) {
/*  94 */         int $$2 = $$1 + $$0 * 128;
/*  95 */         this.texture.getPixels().setPixelRGBA($$1, $$0, MapColor.getColorFromPackedId(this.data.colors[$$2]));
/*     */       } 
/*     */     } 
/*  98 */     this.texture.upload();
/*     */   }
/*     */   
/*     */   void draw(PoseStack $$0, MultiBufferSource $$1, boolean $$2, int $$3) {
/* 102 */     if (this.requiresUpload) {
/* 103 */       updateTexture();
/* 104 */       this.requiresUpload = false;
/*     */     } 
/*     */     
/* 107 */     int $$4 = 0;
/* 108 */     int $$5 = 0;
/*     */     
/* 110 */     float $$6 = 0.0F;
/*     */     
/* 112 */     Matrix4f $$7 = $$0.last().pose();
/* 113 */     VertexConsumer $$8 = $$1.getBuffer(this.renderType);
/* 114 */     $$8.vertex($$7, 0.0F, 128.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 1.0F).uv2($$3).endVertex();
/* 115 */     $$8.vertex($$7, 128.0F, 128.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 1.0F).uv2($$3).endVertex();
/* 116 */     $$8.vertex($$7, 128.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(1.0F, 0.0F).uv2($$3).endVertex();
/* 117 */     $$8.vertex($$7, 0.0F, 0.0F, -0.01F).color(255, 255, 255, 255).uv(0.0F, 0.0F).uv2($$3).endVertex();
/*     */     
/* 119 */     int $$9 = 0;
/* 120 */     for (MapDecoration $$10 : this.data.getDecorations()) {
/* 121 */       if ($$2 && !$$10.renderOnFrame()) {
/*     */         continue;
/*     */       }
/* 124 */       $$0.pushPose();
/* 125 */       $$0.translate(0.0F + $$10.x() / 2.0F + 64.0F, 0.0F + $$10.y() / 2.0F + 64.0F, -0.02F);
/* 126 */       $$0.mulPose(Axis.ZP.rotationDegrees(($$10.rot() * 360) / 16.0F));
/* 127 */       $$0.scale(4.0F, 4.0F, 3.0F);
/* 128 */       $$0.translate(-0.125F, 0.125F, 0.0F);
/*     */       
/* 130 */       byte $$11 = $$10.getImage();
/* 131 */       float $$12 = ($$11 % 16 + 0) / 16.0F;
/* 132 */       float $$13 = ($$11 / 16 + 0) / 16.0F;
/* 133 */       float $$14 = ($$11 % 16 + 1) / 16.0F;
/* 134 */       float $$15 = ($$11 / 16 + 1) / 16.0F;
/*     */       
/* 136 */       Matrix4f $$16 = $$0.last().pose();
/*     */       
/* 138 */       float $$17 = -0.001F;
/*     */       
/* 140 */       VertexConsumer $$18 = $$1.getBuffer(MapRenderer.MAP_ICONS);
/* 141 */       $$18.vertex($$16, -1.0F, 1.0F, $$9 * -0.001F).color(255, 255, 255, 255).uv($$12, $$13).uv2($$3).endVertex();
/* 142 */       $$18.vertex($$16, 1.0F, 1.0F, $$9 * -0.001F).color(255, 255, 255, 255).uv($$14, $$13).uv2($$3).endVertex();
/* 143 */       $$18.vertex($$16, 1.0F, -1.0F, $$9 * -0.001F).color(255, 255, 255, 255).uv($$14, $$15).uv2($$3).endVertex();
/* 144 */       $$18.vertex($$16, -1.0F, -1.0F, $$9 * -0.001F).color(255, 255, 255, 255).uv($$12, $$15).uv2($$3).endVertex();
/* 145 */       $$0.popPose();
/*     */       
/* 147 */       if ($$10.name() != null) {
/* 148 */         Font $$19 = (Minecraft.getInstance()).font;
/* 149 */         Component $$20 = $$10.name();
/* 150 */         float $$21 = $$19.width((FormattedText)$$20);
/* 151 */         Objects.requireNonNull($$19); float $$22 = Mth.clamp(25.0F / $$21, 0.0F, 6.0F / 9.0F);
/*     */         
/* 153 */         $$0.pushPose();
/* 154 */         $$0.translate(0.0F + $$10.x() / 2.0F + 64.0F - $$21 * $$22 / 2.0F, 0.0F + $$10.y() / 2.0F + 64.0F + 4.0F, -0.025F);
/* 155 */         $$0.scale($$22, $$22, 1.0F);
/* 156 */         $$0.translate(0.0F, 0.0F, -0.1F);
/*     */         
/* 158 */         $$19.drawInBatch($$20, 0.0F, 0.0F, -1, false, $$0.last().pose(), $$1, Font.DisplayMode.NORMAL, -2147483648, $$3);
/* 159 */         $$0.popPose();
/*     */       } 
/*     */       
/* 162 */       $$9++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 168 */     this.texture.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\MapRenderer$MapInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */