/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.blaze3d.platform.Lighting;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.List;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.model.BookModel;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.EnchantmentMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ 
/*     */ public class EnchantmentScreen
/*     */   extends AbstractContainerScreen<EnchantmentMenu> {
/*  28 */   private static final ResourceLocation[] ENABLED_LEVEL_SPRITES = new ResourceLocation[] { new ResourceLocation("container/enchanting_table/level_1"), new ResourceLocation("container/enchanting_table/level_2"), new ResourceLocation("container/enchanting_table/level_3") };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final ResourceLocation[] DISABLED_LEVEL_SPRITES = new ResourceLocation[] { new ResourceLocation("container/enchanting_table/level_1_disabled"), new ResourceLocation("container/enchanting_table/level_2_disabled"), new ResourceLocation("container/enchanting_table/level_3_disabled") };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static final ResourceLocation ENCHANTMENT_SLOT_DISABLED_SPRITE = new ResourceLocation("container/enchanting_table/enchantment_slot_disabled");
/*  39 */   private static final ResourceLocation ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE = new ResourceLocation("container/enchanting_table/enchantment_slot_highlighted");
/*  40 */   private static final ResourceLocation ENCHANTMENT_SLOT_SPRITE = new ResourceLocation("container/enchanting_table/enchantment_slot");
/*  41 */   private static final ResourceLocation ENCHANTING_TABLE_LOCATION = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*  42 */   private static final ResourceLocation ENCHANTING_BOOK_LOCATION = new ResourceLocation("textures/entity/enchanting_table_book.png");
/*     */   
/*  44 */   private final RandomSource random = RandomSource.create();
/*     */   
/*     */   private BookModel bookModel;
/*     */   
/*     */   public int time;
/*     */   public float flip;
/*     */   public float oFlip;
/*     */   public float flipT;
/*     */   public float flipA;
/*     */   public float open;
/*     */   public float oOpen;
/*  55 */   private ItemStack last = ItemStack.EMPTY;
/*     */   
/*     */   public EnchantmentScreen(EnchantmentMenu $$0, Inventory $$1, Component $$2) {
/*  58 */     super($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  63 */     super.init();
/*  64 */     this.bookModel = new BookModel(this.minecraft.getEntityModels().bakeLayer(ModelLayers.BOOK));
/*     */   }
/*     */ 
/*     */   
/*     */   public void containerTick() {
/*  69 */     super.containerTick();
/*  70 */     tickBook();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  75 */     int $$3 = (this.width - this.imageWidth) / 2;
/*  76 */     int $$4 = (this.height - this.imageHeight) / 2;
/*  77 */     for (int $$5 = 0; $$5 < 3; $$5++) {
/*  78 */       double $$6 = $$0 - ($$3 + 60);
/*  79 */       double $$7 = $$1 - ($$4 + 14 + 19 * $$5);
/*  80 */       if ($$6 >= 0.0D && $$7 >= 0.0D && $$6 < 108.0D && $$7 < 19.0D && 
/*  81 */         this.menu.clickMenuButton((Player)this.minecraft.player, $$5)) {
/*  82 */         this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, $$5);
/*  83 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/*  92 */     int $$4 = (this.width - this.imageWidth) / 2;
/*  93 */     int $$5 = (this.height - this.imageHeight) / 2;
/*  94 */     $$0.blit(ENCHANTING_TABLE_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*     */     
/*  96 */     renderBook($$0, $$4, $$5, $$1);
/*     */     
/*  98 */     EnchantmentNames.getInstance().initSeed(this.menu.getEnchantmentSeed());
/*     */     
/* 100 */     int $$6 = this.menu.getGoldCount();
/*     */     
/* 102 */     for (int $$7 = 0; $$7 < 3; $$7++) {
/* 103 */       int $$8 = $$4 + 60;
/* 104 */       int $$9 = $$8 + 20;
/*     */       
/* 106 */       int $$10 = this.menu.costs[$$7];
/* 107 */       if ($$10 == 0) {
/* 108 */         $$0.blitSprite(ENCHANTMENT_SLOT_DISABLED_SPRITE, $$8, $$5 + 14 + 19 * $$7, 108, 19);
/*     */       } else {
/*     */         
/* 111 */         String $$11 = "" + $$10;
/* 112 */         int $$12 = 86 - this.font.width($$11);
/* 113 */         FormattedText $$13 = EnchantmentNames.getInstance().getRandomName(this.font, $$12);
/* 114 */         int $$14 = 6839882;
/*     */         
/* 116 */         if (($$6 < $$7 + 1 || this.minecraft.player.experienceLevel < $$10) && !(this.minecraft.player.getAbilities()).instabuild) {
/* 117 */           $$0.blitSprite(ENCHANTMENT_SLOT_DISABLED_SPRITE, $$8, $$5 + 14 + 19 * $$7, 108, 19);
/* 118 */           $$0.blitSprite(DISABLED_LEVEL_SPRITES[$$7], $$8 + 1, $$5 + 15 + 19 * $$7, 16, 16);
/* 119 */           $$0.drawWordWrap(this.font, $$13, $$9, $$5 + 16 + 19 * $$7, $$12, ($$14 & 0xFEFEFE) >> 1);
/* 120 */           $$14 = 4226832;
/*     */         } else {
/* 122 */           int $$15 = $$2 - $$4 + 60;
/* 123 */           int $$16 = $$3 - $$5 + 14 + 19 * $$7;
/* 124 */           if ($$15 >= 0 && $$16 >= 0 && $$15 < 108 && $$16 < 19) {
/* 125 */             $$0.blitSprite(ENCHANTMENT_SLOT_HIGHLIGHTED_SPRITE, $$8, $$5 + 14 + 19 * $$7, 108, 19);
/* 126 */             $$14 = 16777088;
/*     */           } else {
/* 128 */             $$0.blitSprite(ENCHANTMENT_SLOT_SPRITE, $$8, $$5 + 14 + 19 * $$7, 108, 19);
/*     */           } 
/* 130 */           $$0.blitSprite(ENABLED_LEVEL_SPRITES[$$7], $$8 + 1, $$5 + 15 + 19 * $$7, 16, 16);
/* 131 */           $$0.drawWordWrap(this.font, $$13, $$9, $$5 + 16 + 19 * $$7, $$12, $$14);
/* 132 */           $$14 = 8453920;
/*     */         } 
/* 134 */         $$0.drawString(this.font, $$11, $$9 + 86 - this.font.width($$11), $$5 + 16 + 19 * $$7 + 7, $$14);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void renderBook(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 139 */     float $$4 = Mth.lerp($$3, this.oOpen, this.open);
/* 140 */     float $$5 = Mth.lerp($$3, this.oFlip, this.flip);
/*     */     
/* 142 */     Lighting.setupForEntityInInventory();
/* 143 */     $$0.pose().pushPose();
/* 144 */     $$0.pose().translate($$1 + 33.0F, $$2 + 31.0F, 100.0F);
/*     */     
/* 146 */     float $$6 = 40.0F;
/* 147 */     $$0.pose().scale(-40.0F, 40.0F, 40.0F);
/*     */     
/* 149 */     $$0.pose().mulPose(Axis.XP.rotationDegrees(25.0F));
/* 150 */     $$0.pose().translate((1.0F - $$4) * 0.2F, (1.0F - $$4) * 0.1F, (1.0F - $$4) * 0.25F);
/* 151 */     float $$7 = -(1.0F - $$4) * 90.0F - 90.0F;
/* 152 */     $$0.pose().mulPose(Axis.YP.rotationDegrees($$7));
/* 153 */     $$0.pose().mulPose(Axis.XP.rotationDegrees(180.0F));
/*     */     
/* 155 */     float $$8 = Mth.clamp(Mth.frac($$5 + 0.25F) * 1.6F - 0.3F, 0.0F, 1.0F);
/* 156 */     float $$9 = Mth.clamp(Mth.frac($$5 + 0.75F) * 1.6F - 0.3F, 0.0F, 1.0F);
/*     */     
/* 158 */     this.bookModel.setupAnim(0.0F, $$8, $$9, $$4);
/* 159 */     VertexConsumer $$10 = $$0.bufferSource().getBuffer(this.bookModel.renderType(ENCHANTING_BOOK_LOCATION));
/* 160 */     this.bookModel.renderToBuffer($$0.pose(), $$10, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 162 */     $$0.flush();
/* 163 */     $$0.pose().popPose();
/* 164 */     Lighting.setupFor3DItems();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 169 */     $$3 = this.minecraft.getFrameTime();
/*     */     
/* 171 */     super.render($$0, $$1, $$2, $$3);
/* 172 */     renderTooltip($$0, $$1, $$2);
/*     */     
/* 174 */     boolean $$4 = (this.minecraft.player.getAbilities()).instabuild;
/* 175 */     int $$5 = this.menu.getGoldCount();
/*     */     
/* 177 */     for (int $$6 = 0; $$6 < 3; $$6++) {
/* 178 */       int $$7 = this.menu.costs[$$6];
/*     */       
/* 180 */       Enchantment $$8 = Enchantment.byId(this.menu.enchantClue[$$6]);
/* 181 */       int $$9 = this.menu.levelClue[$$6];
/*     */       
/* 183 */       int $$10 = $$6 + 1;
/* 184 */       if (isHovering(60, 14 + 19 * $$6, 108, 17, $$1, $$2) && $$7 > 0 && $$9 >= 0 && $$8 != null) {
/* 185 */         List<Component> $$11 = Lists.newArrayList();
/*     */         
/* 187 */         $$11.add(Component.translatable("container.enchant.clue", new Object[] { $$8.getFullname($$9) }).withStyle(ChatFormatting.WHITE));
/*     */         
/* 189 */         if (!$$4) {
/* 190 */           $$11.add(CommonComponents.EMPTY);
/* 191 */           if (this.minecraft.player.experienceLevel < $$7) {
/* 192 */             $$11.add(Component.translatable("container.enchant.level.requirement", new Object[] { Integer.valueOf(this.menu.costs[$$6]) }).withStyle(ChatFormatting.RED));
/*     */           } else {
/*     */             MutableComponent $$13, $$15;
/* 195 */             if ($$10 == 1) {
/* 196 */               MutableComponent $$12 = Component.translatable("container.enchant.lapis.one");
/*     */             } else {
/* 198 */               $$13 = Component.translatable("container.enchant.lapis.many", new Object[] { Integer.valueOf($$10) });
/*     */             } 
/* 200 */             $$11.add($$13.withStyle(($$5 >= $$10) ? ChatFormatting.GRAY : ChatFormatting.RED));
/*     */ 
/*     */             
/* 203 */             if ($$10 == 1) {
/* 204 */               MutableComponent $$14 = Component.translatable("container.enchant.level.one");
/*     */             } else {
/* 206 */               $$15 = Component.translatable("container.enchant.level.many", new Object[] { Integer.valueOf($$10) });
/*     */             } 
/* 208 */             $$11.add($$15.withStyle(ChatFormatting.GRAY));
/*     */           } 
/*     */         } 
/*     */         
/* 212 */         $$0.renderComponentTooltip(this.font, $$11, $$1, $$2);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tickBook() {
/* 219 */     ItemStack $$0 = this.menu.getSlot(0).getItem();
/*     */     
/* 221 */     if (!ItemStack.matches($$0, this.last)) {
/* 222 */       this.last = $$0;
/*     */       do {
/* 224 */         this.flipT += (this.random.nextInt(4) - this.random.nextInt(4));
/* 225 */       } while (this.flip <= this.flipT + 1.0F && this.flip >= this.flipT - 1.0F);
/*     */     } 
/*     */     
/* 228 */     this.time++;
/* 229 */     this.oFlip = this.flip;
/* 230 */     this.oOpen = this.open;
/*     */     
/* 232 */     boolean $$1 = false;
/* 233 */     for (int $$2 = 0; $$2 < 3; $$2++) {
/* 234 */       if (this.menu.costs[$$2] != 0) {
/* 235 */         $$1 = true;
/*     */       }
/*     */     } 
/*     */     
/* 239 */     if ($$1) {
/* 240 */       this.open += 0.2F;
/*     */     } else {
/* 242 */       this.open -= 0.2F;
/*     */     } 
/* 244 */     this.open = Mth.clamp(this.open, 0.0F, 1.0F);
/*     */     
/* 246 */     float $$3 = (this.flipT - this.flip) * 0.4F;
/* 247 */     float $$4 = 0.2F;
/* 248 */     $$3 = Mth.clamp($$3, -0.2F, 0.2F);
/* 249 */     this.flipA += ($$3 - this.flipA) * 0.9F;
/*     */     
/* 251 */     this.flip += this.flipA;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\EnchantmentScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */