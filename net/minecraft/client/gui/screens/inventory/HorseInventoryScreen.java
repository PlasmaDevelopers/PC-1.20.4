/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.HorseInventoryMenu;
/*    */ 
/*    */ public class HorseInventoryScreen extends AbstractContainerScreen<HorseInventoryMenu> {
/* 12 */   private static final ResourceLocation CHEST_SLOTS_SPRITE = new ResourceLocation("container/horse/chest_slots");
/* 13 */   private static final ResourceLocation SADDLE_SLOT_SPRITE = new ResourceLocation("container/horse/saddle_slot");
/* 14 */   private static final ResourceLocation LLAMA_ARMOR_SLOT_SPRITE = new ResourceLocation("container/horse/llama_armor_slot");
/* 15 */   private static final ResourceLocation ARMOR_SLOT_SPRITE = new ResourceLocation("container/horse/armor_slot");
/* 16 */   private static final ResourceLocation HORSE_INVENTORY_LOCATION = new ResourceLocation("textures/gui/container/horse.png");
/*    */   
/*    */   private final AbstractHorse horse;
/*    */   private float xMouse;
/*    */   private float yMouse;
/*    */   
/*    */   public HorseInventoryScreen(HorseInventoryMenu $$0, Inventory $$1, AbstractHorse $$2) {
/* 23 */     super($$0, $$1, $$2.getDisplayName());
/* 24 */     this.horse = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 29 */     int $$4 = (this.width - this.imageWidth) / 2;
/* 30 */     int $$5 = (this.height - this.imageHeight) / 2;
/* 31 */     $$0.blit(HORSE_INVENTORY_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*    */     
/* 33 */     AbstractHorse abstractHorse = this.horse; if (abstractHorse instanceof AbstractChestedHorse) { AbstractChestedHorse $$6 = (AbstractChestedHorse)abstractHorse;
/* 34 */       if ($$6.hasChest()) {
/* 35 */         $$0.blitSprite(CHEST_SLOTS_SPRITE, 90, 54, 0, 0, $$4 + 79, $$5 + 17, $$6.getInventoryColumns() * 18, 54);
/*    */       } }
/*    */ 
/*    */     
/* 39 */     if (this.horse.isSaddleable()) {
/* 40 */       $$0.blitSprite(SADDLE_SLOT_SPRITE, $$4 + 7, $$5 + 35 - 18, 18, 18);
/*    */     }
/*    */     
/* 43 */     if (this.horse.canWearArmor()) {
/* 44 */       if (this.horse instanceof net.minecraft.world.entity.animal.horse.Llama) {
/* 45 */         $$0.blitSprite(LLAMA_ARMOR_SLOT_SPRITE, $$4 + 7, $$5 + 35, 18, 18);
/*    */       } else {
/* 47 */         $$0.blitSprite(ARMOR_SLOT_SPRITE, $$4 + 7, $$5 + 35, 18, 18);
/*    */       } 
/*    */     }
/*    */     
/* 51 */     InventoryScreen.renderEntityInInventoryFollowsMouse($$0, $$4 + 26, $$5 + 18, $$4 + 78, $$5 + 70, 17, 0.25F, this.xMouse, this.yMouse, (LivingEntity)this.horse);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 56 */     this.xMouse = $$1;
/* 57 */     this.yMouse = $$2;
/*    */     
/* 59 */     super.render($$0, $$1, $$2, $$3);
/* 60 */     renderTooltip($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\HorseInventoryScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */