/*     */ package net.minecraft.client.gui.screens.advancements;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementNode;
/*     */ import net.minecraft.advancements.DisplayInfo;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class AdvancementTab
/*     */ {
/*     */   private final Minecraft minecraft;
/*     */   private final AdvancementsScreen screen;
/*     */   private final AdvancementTabType type;
/*     */   private final int index;
/*     */   private final AdvancementNode rootNode;
/*     */   private final DisplayInfo display;
/*     */   private final ItemStack icon;
/*     */   private final Component title;
/*     */   private final AdvancementWidget root;
/*  29 */   private final Map<AdvancementHolder, AdvancementWidget> widgets = Maps.newLinkedHashMap();
/*     */   private double scrollX;
/*     */   private double scrollY;
/*  32 */   private int minX = Integer.MAX_VALUE;
/*  33 */   private int minY = Integer.MAX_VALUE;
/*  34 */   private int maxX = Integer.MIN_VALUE;
/*  35 */   private int maxY = Integer.MIN_VALUE;
/*     */   private float fade;
/*     */   private boolean centered;
/*     */   
/*     */   public AdvancementTab(Minecraft $$0, AdvancementsScreen $$1, AdvancementTabType $$2, int $$3, AdvancementNode $$4, DisplayInfo $$5) {
/*  40 */     this.minecraft = $$0;
/*  41 */     this.screen = $$1;
/*  42 */     this.type = $$2;
/*  43 */     this.index = $$3;
/*  44 */     this.rootNode = $$4;
/*  45 */     this.display = $$5;
/*  46 */     this.icon = $$5.getIcon();
/*  47 */     this.title = $$5.getTitle();
/*  48 */     this.root = new AdvancementWidget(this, $$0, $$4, $$5);
/*  49 */     addWidget(this.root, $$4.holder());
/*     */   }
/*     */   
/*     */   public AdvancementTabType getType() {
/*  53 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  57 */     return this.index;
/*     */   }
/*     */   
/*     */   public AdvancementNode getRootNode() {
/*  61 */     return this.rootNode;
/*     */   }
/*     */   
/*     */   public Component getTitle() {
/*  65 */     return this.title;
/*     */   }
/*     */   
/*     */   public DisplayInfo getDisplay() {
/*  69 */     return this.display;
/*     */   }
/*     */   
/*     */   public void drawTab(GuiGraphics $$0, int $$1, int $$2, boolean $$3) {
/*  73 */     this.type.draw($$0, $$1, $$2, $$3, this.index);
/*     */   }
/*     */   
/*     */   public void drawIcon(GuiGraphics $$0, int $$1, int $$2) {
/*  77 */     this.type.drawIcon($$0, $$1, $$2, this.index, this.icon);
/*     */   }
/*     */   
/*     */   public void drawContents(GuiGraphics $$0, int $$1, int $$2) {
/*  81 */     if (!this.centered) {
/*  82 */       this.scrollX = (117 - (this.maxX + this.minX) / 2);
/*  83 */       this.scrollY = (56 - (this.maxY + this.minY) / 2);
/*  84 */       this.centered = true;
/*     */     } 
/*     */     
/*  87 */     $$0.enableScissor($$1, $$2, $$1 + 234, $$2 + 113);
/*  88 */     $$0.pose().pushPose();
/*  89 */     $$0.pose().translate($$1, $$2, 0.0F);
/*     */     
/*  91 */     ResourceLocation $$3 = this.display.getBackground().orElse(TextureManager.INTENTIONAL_MISSING_TEXTURE);
/*  92 */     int $$4 = Mth.floor(this.scrollX);
/*  93 */     int $$5 = Mth.floor(this.scrollY);
/*  94 */     int $$6 = $$4 % 16;
/*  95 */     int $$7 = $$5 % 16;
/*     */     
/*  97 */     for (int $$8 = -1; $$8 <= 15; $$8++) {
/*  98 */       for (int $$9 = -1; $$9 <= 8; $$9++) {
/*  99 */         $$0.blit($$3, $$6 + 16 * $$8, $$7 + 16 * $$9, 0.0F, 0.0F, 16, 16, 16, 16);
/*     */       }
/*     */     } 
/*     */     
/* 103 */     this.root.drawConnectivity($$0, $$4, $$5, true);
/* 104 */     this.root.drawConnectivity($$0, $$4, $$5, false);
/* 105 */     this.root.draw($$0, $$4, $$5);
/*     */     
/* 107 */     $$0.pose().popPose();
/* 108 */     $$0.disableScissor();
/*     */   }
/*     */   
/*     */   public void drawTooltips(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 112 */     $$0.pose().pushPose();
/* 113 */     $$0.pose().translate(0.0F, 0.0F, -200.0F);
/*     */     
/* 115 */     $$0.fill(0, 0, 234, 113, Mth.floor(this.fade * 255.0F) << 24);
/*     */     
/* 117 */     boolean $$5 = false;
/* 118 */     int $$6 = Mth.floor(this.scrollX);
/* 119 */     int $$7 = Mth.floor(this.scrollY);
/* 120 */     if ($$1 > 0 && $$1 < 234 && $$2 > 0 && $$2 < 113) {
/* 121 */       for (AdvancementWidget $$8 : this.widgets.values()) {
/* 122 */         if ($$8.isMouseOver($$6, $$7, $$1, $$2)) {
/* 123 */           $$5 = true;
/* 124 */           $$8.drawHover($$0, $$6, $$7, this.fade, $$3, $$4);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 130 */     $$0.pose().popPose();
/*     */     
/* 132 */     if ($$5) {
/* 133 */       this.fade = Mth.clamp(this.fade + 0.02F, 0.0F, 0.3F);
/*     */     } else {
/* 135 */       this.fade = Mth.clamp(this.fade - 0.04F, 0.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isMouseOver(int $$0, int $$1, double $$2, double $$3) {
/* 140 */     return this.type.isMouseOver($$0, $$1, this.index, $$2, $$3);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static AdvancementTab create(Minecraft $$0, AdvancementsScreen $$1, int $$2, AdvancementNode $$3) {
/* 145 */     Optional<DisplayInfo> $$4 = $$3.advancement().display();
/* 146 */     if ($$4.isEmpty()) {
/* 147 */       return null;
/*     */     }
/* 149 */     for (AdvancementTabType $$5 : AdvancementTabType.values()) {
/* 150 */       if ($$2 >= $$5.getMax()) {
/* 151 */         $$2 -= $$5.getMax();
/*     */       } else {
/*     */         
/* 154 */         return new AdvancementTab($$0, $$1, $$5, $$2, $$3, $$4.get());
/*     */       } 
/* 156 */     }  return null;
/*     */   }
/*     */   
/*     */   public void scroll(double $$0, double $$1) {
/* 160 */     if (this.maxX - this.minX > 234) {
/* 161 */       this.scrollX = Mth.clamp(this.scrollX + $$0, -(this.maxX - 234), 0.0D);
/*     */     }
/* 163 */     if (this.maxY - this.minY > 113) {
/* 164 */       this.scrollY = Mth.clamp(this.scrollY + $$1, -(this.maxY - 113), 0.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addAdvancement(AdvancementNode $$0) {
/* 169 */     Optional<DisplayInfo> $$1 = $$0.advancement().display();
/* 170 */     if ($$1.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     AdvancementWidget $$2 = new AdvancementWidget(this, this.minecraft, $$0, $$1.get());
/* 175 */     addWidget($$2, $$0.holder());
/*     */   }
/*     */   
/*     */   private void addWidget(AdvancementWidget $$0, AdvancementHolder $$1) {
/* 179 */     this.widgets.put($$1, $$0);
/* 180 */     int $$2 = $$0.getX();
/* 181 */     int $$3 = $$2 + 28;
/* 182 */     int $$4 = $$0.getY();
/* 183 */     int $$5 = $$4 + 27;
/* 184 */     this.minX = Math.min(this.minX, $$2);
/* 185 */     this.maxX = Math.max(this.maxX, $$3);
/* 186 */     this.minY = Math.min(this.minY, $$4);
/* 187 */     this.maxY = Math.max(this.maxY, $$5);
/*     */     
/* 189 */     for (AdvancementWidget $$6 : this.widgets.values()) {
/* 190 */       $$6.attachToParent();
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AdvancementWidget getWidget(AdvancementHolder $$0) {
/* 196 */     return this.widgets.get($$0);
/*     */   }
/*     */   
/*     */   public AdvancementsScreen getScreen() {
/* 200 */     return this.screen;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\advancements\AdvancementTab.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */