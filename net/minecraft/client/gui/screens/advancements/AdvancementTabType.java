/*     */ package net.minecraft.client.gui.screens.advancements;
/*     */ 
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.ItemStack;
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
/*     */ enum AdvancementTabType
/*     */ {
/*  18 */   ABOVE(new Sprites(new ResourceLocation("advancements/tab_above_left_selected"), new ResourceLocation("advancements/tab_above_middle_selected"), new ResourceLocation("advancements/tab_above_right_selected")), new Sprites(new ResourceLocation("advancements/tab_above_left"), new ResourceLocation("advancements/tab_above_middle"), new ResourceLocation("advancements/tab_above_right")), 28, 32, 8),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   BELOW(new Sprites(new ResourceLocation("advancements/tab_below_left_selected"), new ResourceLocation("advancements/tab_below_middle_selected"), new ResourceLocation("advancements/tab_below_right_selected")), new Sprites(new ResourceLocation("advancements/tab_below_left"), new ResourceLocation("advancements/tab_below_middle"), new ResourceLocation("advancements/tab_below_right")), 28, 32, 8),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   LEFT(new Sprites(new ResourceLocation("advancements/tab_left_top_selected"), new ResourceLocation("advancements/tab_left_middle_selected"), new ResourceLocation("advancements/tab_left_bottom_selected")), new Sprites(new ResourceLocation("advancements/tab_left_top"), new ResourceLocation("advancements/tab_left_middle"), new ResourceLocation("advancements/tab_left_bottom")), 32, 28, 5),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   RIGHT(new Sprites(new ResourceLocation("advancements/tab_right_top_selected"), new ResourceLocation("advancements/tab_right_middle_selected"), new ResourceLocation("advancements/tab_right_bottom_selected")), new Sprites(new ResourceLocation("advancements/tab_right_top"), new ResourceLocation("advancements/tab_right_middle"), new ResourceLocation("advancements/tab_right_bottom")), 32, 28, 5);
/*     */ 
/*     */   
/*     */   private final Sprites selectedSprites;
/*     */ 
/*     */   
/*     */   private final Sprites unselectedSprites;
/*     */ 
/*     */   
/*     */   private final int width;
/*     */ 
/*     */   
/*     */   private final int height;
/*     */   
/*     */   private final int max;
/*     */ 
/*     */   
/*     */   AdvancementTabType(Sprites $$0, Sprites $$1, int $$2, int $$3, int $$4) {
/*  63 */     this.selectedSprites = $$0;
/*  64 */     this.unselectedSprites = $$1;
/*  65 */     this.width = $$2;
/*  66 */     this.height = $$3;
/*  67 */     this.max = $$4;
/*     */   }
/*     */   
/*     */   public int getMax() {
/*  71 */     return this.max;
/*     */   }
/*     */   public void draw(GuiGraphics $$0, int $$1, int $$2, boolean $$3, int $$4) {
/*     */     ResourceLocation $$8;
/*  75 */     Sprites $$5 = $$3 ? this.selectedSprites : this.unselectedSprites;
/*     */     
/*  77 */     if ($$4 == 0) {
/*  78 */       ResourceLocation $$6 = $$5.first();
/*  79 */     } else if ($$4 == this.max - 1) {
/*  80 */       ResourceLocation $$7 = $$5.last();
/*     */     } else {
/*  82 */       $$8 = $$5.middle();
/*     */     } 
/*  84 */     $$0.blitSprite($$8, $$1 + getX($$4), $$2 + getY($$4), this.width, this.height);
/*     */   }
/*     */   
/*     */   public void drawIcon(GuiGraphics $$0, int $$1, int $$2, int $$3, ItemStack $$4) {
/*  88 */     int $$5 = $$1 + getX($$3);
/*  89 */     int $$6 = $$2 + getY($$3);
/*  90 */     switch (this) {
/*     */       case ABOVE:
/*  92 */         $$5 += 6;
/*  93 */         $$6 += 9;
/*     */         break;
/*     */       case BELOW:
/*  96 */         $$5 += 6;
/*  97 */         $$6 += 6;
/*     */         break;
/*     */       case LEFT:
/* 100 */         $$5 += 10;
/* 101 */         $$6 += 5;
/*     */         break;
/*     */       case RIGHT:
/* 104 */         $$5 += 6;
/* 105 */         $$6 += 5;
/*     */         break;
/*     */     } 
/* 108 */     $$0.renderFakeItem($$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public int getX(int $$0) {
/* 112 */     switch (this) {
/*     */       case ABOVE:
/* 114 */         return (this.width + 4) * $$0;
/*     */       case BELOW:
/* 116 */         return (this.width + 4) * $$0;
/*     */       case LEFT:
/* 118 */         return -this.width + 4;
/*     */       case RIGHT:
/* 120 */         return 248;
/*     */     } 
/* 122 */     throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
/*     */   }
/*     */   
/*     */   public int getY(int $$0) {
/* 126 */     switch (this) {
/*     */       case ABOVE:
/* 128 */         return -this.height + 4;
/*     */       case BELOW:
/* 130 */         return 136;
/*     */       case LEFT:
/* 132 */         return this.height * $$0;
/*     */       case RIGHT:
/* 134 */         return this.height * $$0;
/*     */     } 
/* 136 */     throw new UnsupportedOperationException("Don't know what this tab type is!" + this);
/*     */   }
/*     */   
/*     */   public boolean isMouseOver(int $$0, int $$1, int $$2, double $$3, double $$4) {
/* 140 */     int $$5 = $$0 + getX($$2);
/* 141 */     int $$6 = $$1 + getY($$2);
/* 142 */     return ($$3 > $$5 && $$3 < ($$5 + this.width) && $$4 > $$6 && $$4 < ($$6 + this.height));
/*     */   }
/*     */   private static final class Sprites extends Record { private final ResourceLocation first; private final ResourceLocation middle; private final ResourceLocation last;
/* 145 */     Sprites(ResourceLocation $$0, ResourceLocation $$1, ResourceLocation $$2) { this.first = $$0; this.middle = $$1; this.last = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/screens/advancements/AdvancementTabType$Sprites;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #145	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 145 */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/advancements/AdvancementTabType$Sprites; } public ResourceLocation first() { return this.first; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/screens/advancements/AdvancementTabType$Sprites;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #145	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/gui/screens/advancements/AdvancementTabType$Sprites; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/screens/advancements/AdvancementTabType$Sprites;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #145	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/screens/advancements/AdvancementTabType$Sprites;
/* 145 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation middle() { return this.middle; } public ResourceLocation last() { return this.last; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\advancements\AdvancementTabType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */