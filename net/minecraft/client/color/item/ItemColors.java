/*    */ package net.minecraft.client.color.item;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.client.color.block.BlockColors;
/*    */ import net.minecraft.core.IdMapper;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.item.BlockItem;
/*    */ import net.minecraft.world.item.DyeableLeatherItem;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.item.MapItem;
/*    */ import net.minecraft.world.item.SpawnEggItem;
/*    */ import net.minecraft.world.item.alchemy.PotionUtils;
/*    */ import net.minecraft.world.level.FoliageColor;
/*    */ import net.minecraft.world.level.GrassColor;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ public class ItemColors
/*    */ {
/*    */   private static final int DEFAULT = -1;
/* 26 */   private final IdMapper<ItemColor> itemColors = new IdMapper(32);
/*    */   
/*    */   public static ItemColors createDefault(BlockColors $$0) {
/* 29 */     ItemColors $$1 = new ItemColors();
/*    */     
/* 31 */     $$1.register(($$0, $$1) -> ($$1 > 0) ? -1 : ((DyeableLeatherItem)$$0.getItem()).getColor($$0), new ItemLike[] { (ItemLike)Items.LEATHER_HELMET, (ItemLike)Items.LEATHER_CHESTPLATE, (ItemLike)Items.LEATHER_LEGGINGS, (ItemLike)Items.LEATHER_BOOTS, (ItemLike)Items.LEATHER_HORSE_ARMOR });
/* 32 */     $$1.register(($$0, $$1) -> GrassColor.get(0.5D, 1.0D), new ItemLike[] { (ItemLike)Blocks.TALL_GRASS, (ItemLike)Blocks.LARGE_FERN });
/* 33 */     $$1.register(($$0, $$1) -> {
/*    */           if ($$1 != 1) {
/*    */             return -1;
/*    */           }
/*    */           
/*    */           CompoundTag $$2 = $$0.getTagElement("Explosion");
/* 39 */           int[] $$3 = ($$2 != null && $$2.contains("Colors", 11)) ? $$2.getIntArray("Colors") : null;
/*    */           
/*    */           if ($$3 == null || $$3.length == 0) {
/*    */             return 9079434;
/*    */           }
/*    */           
/*    */           if ($$3.length == 1) {
/*    */             return $$3[0];
/*    */           }
/*    */           
/*    */           int $$4 = 0;
/*    */           
/*    */           int $$5 = 0;
/*    */           
/*    */           int $$6 = 0;
/*    */           
/*    */           for (int $$7 : $$3) {
/*    */             $$4 += ($$7 & 0xFF0000) >> 16;
/*    */             $$5 += ($$7 & 0xFF00) >> 8;
/*    */             $$6 += ($$7 & 0xFF) >> 0;
/*    */           } 
/*    */           $$4 /= $$3.length;
/*    */           $$5 /= $$3.length;
/*    */           $$6 /= $$3.length;
/*    */           return $$4 << 16 | $$5 << 8 | $$6;
/*    */         }new ItemLike[] { (ItemLike)Items.FIREWORK_STAR });
/* 65 */     $$1.register(($$0, $$1) -> ($$1 > 0) ? -1 : PotionUtils.getColor($$0), new ItemLike[] { (ItemLike)Items.POTION, (ItemLike)Items.SPLASH_POTION, (ItemLike)Items.LINGERING_POTION });
/*    */     
/* 67 */     for (Iterator<SpawnEggItem> iterator = SpawnEggItem.eggs().iterator(); iterator.hasNext(); ) { SpawnEggItem $$2 = iterator.next();
/* 68 */       $$1.register(($$1, $$2) -> $$0.getColor($$2), new ItemLike[] { (ItemLike)$$2 }); }
/*    */ 
/*    */     
/* 71 */     $$1.register(($$1, $$2) -> { BlockState $$3 = ((BlockItem)$$1.getItem()).getBlock().defaultBlockState(); return $$0.getColor($$3, null, null, $$2); }new ItemLike[] { (ItemLike)Blocks.GRASS_BLOCK, (ItemLike)Blocks.SHORT_GRASS, (ItemLike)Blocks.FERN, (ItemLike)Blocks.VINE, (ItemLike)Blocks.OAK_LEAVES, (ItemLike)Blocks.SPRUCE_LEAVES, (ItemLike)Blocks.BIRCH_LEAVES, (ItemLike)Blocks.JUNGLE_LEAVES, (ItemLike)Blocks.ACACIA_LEAVES, (ItemLike)Blocks.DARK_OAK_LEAVES, (ItemLike)Blocks.LILY_PAD });
/*    */ 
/*    */ 
/*    */     
/* 75 */     $$1.register(($$0, $$1) -> FoliageColor.getMangroveColor(), new ItemLike[] { (ItemLike)Blocks.MANGROVE_LEAVES });
/*    */     
/* 77 */     $$1.register(($$0, $$1) -> ($$1 == 0) ? PotionUtils.getColor($$0) : -1, new ItemLike[] { (ItemLike)Items.TIPPED_ARROW });
/* 78 */     $$1.register(($$0, $$1) -> ($$1 == 0) ? -1 : MapItem.getColor($$0), new ItemLike[] { (ItemLike)Items.FILLED_MAP });
/*    */     
/* 80 */     return $$1;
/*    */   }
/*    */   
/*    */   public int getColor(ItemStack $$0, int $$1) {
/* 84 */     ItemColor $$2 = (ItemColor)this.itemColors.byId(BuiltInRegistries.ITEM.getId($$0.getItem()));
/*    */     
/* 86 */     return ($$2 == null) ? -1 : $$2.getColor($$0, $$1);
/*    */   }
/*    */   
/*    */   public void register(ItemColor $$0, ItemLike... $$1) {
/* 90 */     for (ItemLike $$2 : $$1)
/* 91 */       this.itemColors.addMapping($$0, Item.getId($$2.asItem())); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\color\item\ItemColors.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */