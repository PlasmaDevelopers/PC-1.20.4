/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.world.entity.Display;
/*     */ import net.minecraft.world.entity.Entity;
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
/*     */ public class TextDisplayRenderer
/*     */   extends DisplayRenderer<Display.TextDisplay, Display.TextDisplay.TextRenderState>
/*     */ {
/*     */   private final Font font;
/*     */   
/*     */   protected TextDisplayRenderer(EntityRendererProvider.Context $$0) {
/* 151 */     super($$0);
/* 152 */     this.font = $$0.getFont();
/*     */   }
/*     */   
/*     */   private Display.TextDisplay.CachedInfo splitLines(Component $$0, int $$1) {
/* 156 */     List<FormattedCharSequence> $$2 = this.font.split((FormattedText)$$0, $$1);
/* 157 */     List<Display.TextDisplay.CachedLine> $$3 = new ArrayList<>($$2.size());
/*     */     
/* 159 */     int $$4 = 0;
/* 160 */     for (FormattedCharSequence $$5 : $$2) {
/* 161 */       int $$6 = this.font.width($$5);
/* 162 */       $$4 = Math.max($$4, $$6);
/* 163 */       $$3.add(new Display.TextDisplay.CachedLine($$5, $$6));
/*     */     } 
/*     */     
/* 166 */     return new Display.TextDisplay.CachedInfo($$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Display.TextDisplay.TextRenderState getSubState(Display.TextDisplay $$0) {
/* 172 */     return $$0.textRenderState();
/*     */   }
/*     */   
/*     */   public void renderInner(Display.TextDisplay $$0, Display.TextDisplay.TextRenderState $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, float $$5) {
/*     */     int $$14;
/* 177 */     byte $$6 = $$1.flags();
/*     */     
/* 179 */     boolean $$7 = (($$6 & 0x2) != 0);
/* 180 */     boolean $$8 = (($$6 & 0x4) != 0);
/* 181 */     boolean $$9 = (($$6 & 0x1) != 0);
/* 182 */     Display.TextDisplay.Align $$10 = Display.TextDisplay.getAlign($$6);
/* 183 */     byte $$11 = (byte)$$1.textOpacity().get($$5);
/*     */     
/* 185 */     if ($$8) {
/* 186 */       float $$12 = (Minecraft.getInstance()).options.getBackgroundOpacity(0.25F);
/* 187 */       int $$13 = (int)($$12 * 255.0F) << 24;
/*     */     } else {
/* 189 */       $$14 = $$1.backgroundColor().get($$5);
/*     */     } 
/*     */     
/* 192 */     float $$15 = 0.0F;
/* 193 */     Matrix4f $$16 = $$2.last().pose();
/*     */     
/* 195 */     $$16.rotate(3.1415927F, 0.0F, 1.0F, 0.0F);
/* 196 */     $$16.scale(-0.025F, -0.025F, -0.025F);
/* 197 */     Display.TextDisplay.CachedInfo $$17 = $$0.cacheDisplay(this::splitLines);
/*     */ 
/*     */     
/* 200 */     Objects.requireNonNull(this.font); int $$18 = 9 + 1;
/*     */     
/* 202 */     int $$19 = $$17.width();
/* 203 */     int $$20 = $$17.lines().size() * $$18;
/* 204 */     $$16.translate(1.0F - $$19 / 2.0F, -$$20, 0.0F);
/*     */     
/* 206 */     if ($$14 != 0) {
/* 207 */       VertexConsumer $$21 = $$3.getBuffer($$7 ? RenderType.textBackgroundSeeThrough() : RenderType.textBackground());
/* 208 */       $$21.vertex($$16, -1.0F, -1.0F, 0.0F).color($$14).uv2($$4).endVertex();
/* 209 */       $$21.vertex($$16, -1.0F, $$20, 0.0F).color($$14).uv2($$4).endVertex();
/* 210 */       $$21.vertex($$16, $$19, $$20, 0.0F).color($$14).uv2($$4).endVertex();
/* 211 */       $$21.vertex($$16, $$19, -1.0F, 0.0F).color($$14).uv2($$4).endVertex();
/*     */     } 
/*     */     
/* 214 */     for (Display.TextDisplay.CachedLine $$22 : $$17.lines()) {
/* 215 */       switch (DisplayRenderer.null.$SwitchMap$net$minecraft$world$entity$Display$TextDisplay$Align[$$10.ordinal()]) { default: throw new IncompatibleClassChangeError();
/*     */         case 1: 
/*     */         case 2: 
/* 218 */         case 3: break; }  float $$23 = $$19 / 2.0F - $$22.width() / 2.0F;
/*     */       
/* 220 */       this.font.drawInBatch($$22.contents(), $$23, $$15, $$11 << 24 | 0xFFFFFF, $$9, $$16, $$3, $$7 ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.POLYGON_OFFSET, 0, $$4);
/* 221 */       $$15 += $$18;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\DisplayRenderer$TextDisplayRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */