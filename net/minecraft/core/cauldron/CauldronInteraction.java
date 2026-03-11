/*     */ package net.minecraft.core.cauldron;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.DyeableLeatherItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.ItemUtils;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LayeredCauldronBlock;
/*     */ import net.minecraft.world.level.block.entity.BannerBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ public interface CauldronInteraction {
/*     */   public static final class InteractionMap extends Record { private final String name;
/*     */     private final Map<Item, CauldronInteraction> map;
/*     */     
/*  35 */     public InteractionMap(String $$0, Map<Item, CauldronInteraction> $$1) { this.name = $$0; this.map = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #35	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  35 */       //   0	7	0	this	Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #35	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #35	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;
/*  35 */       //   0	8	1	$$0	Ljava/lang/Object; } public Map<Item, CauldronInteraction> map() { return this.map; }
/*     */      }
/*  37 */   public static final Map<String, InteractionMap> INTERACTIONS = (Map<String, InteractionMap>)new Object2ObjectArrayMap();
/*  38 */   public static final Codec<InteractionMap> CODEC = ExtraCodecs.stringResolverCodec(InteractionMap::name, INTERACTIONS::get); static { Objects.requireNonNull(INTERACTIONS); }
/*     */   
/*  40 */   public static final InteractionMap EMPTY = newInteractionMap("empty");
/*  41 */   public static final InteractionMap WATER = newInteractionMap("water");
/*  42 */   public static final InteractionMap LAVA = newInteractionMap("lava");
/*  43 */   public static final InteractionMap POWDER_SNOW = newInteractionMap("powder_snow"); public static final CauldronInteraction FILL_WATER; public static final CauldronInteraction FILL_LAVA; public static final CauldronInteraction FILL_POWDER_SNOW; public static final CauldronInteraction SHULKER_BOX; public static final CauldronInteraction BANNER; public static final CauldronInteraction DYED_ITEM;
/*     */   
/*     */   static InteractionMap newInteractionMap(String $$0) {
/*  46 */     Object2ObjectOpenHashMap<Item, CauldronInteraction> $$1 = new Object2ObjectOpenHashMap();
/*  47 */     $$1.defaultReturnValue(($$0, $$1, $$2, $$3, $$4, $$5) -> InteractionResult.PASS);
/*  48 */     InteractionMap $$2 = new InteractionMap($$0, (Map<Item, CauldronInteraction>)$$1);
/*  49 */     INTERACTIONS.put($$0, $$2);
/*  50 */     return $$2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void bootStrap() {
/*  57 */     Map<Item, CauldronInteraction> $$0 = EMPTY.map();
/*  58 */     addDefaultInteractions($$0);
/*     */     
/*  60 */     $$0.put(Items.POTION, ($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*     */           if (PotionUtils.getPotion($$5) != Potions.WATER) {
/*     */             return InteractionResult.PASS;
/*     */           }
/*     */           
/*     */           if (!$$1.isClientSide) {
/*     */             Item $$6 = $$5.getItem();
/*     */             
/*     */             $$3.setItemInHand($$4, ItemUtils.createFilledResult($$5, $$3, new ItemStack((ItemLike)Items.GLASS_BOTTLE)));
/*     */             
/*     */             $$3.awardStat(Stats.USE_CAULDRON);
/*     */             
/*     */             $$3.awardStat(Stats.ITEM_USED.get($$6));
/*     */             
/*     */             $$1.setBlockAndUpdate($$2, Blocks.WATER_CAULDRON.defaultBlockState());
/*     */             $$1.playSound(null, $$2, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */             $$1.gameEvent(null, GameEvent.FLUID_PLACE, $$2);
/*     */           } 
/*     */           return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */         });
/*  80 */     Map<Item, CauldronInteraction> $$1 = WATER.map();
/*  81 */     addDefaultInteractions($$1);
/*     */     
/*  83 */     $$1.put(Items.BUCKET, ($$0, $$1, $$2, $$3, $$4, $$5) -> fillBucket($$0, $$1, $$2, $$3, $$4, $$5, new ItemStack((ItemLike)Items.WATER_BUCKET), (), SoundEvents.BUCKET_FILL));
/*     */ 
/*     */ 
/*     */     
/*  87 */     $$1.put(Items.GLASS_BOTTLE, ($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*     */           if (!$$1.isClientSide) {
/*     */             Item $$6 = $$5.getItem();
/*     */             
/*     */             $$3.setItemInHand($$4, ItemUtils.createFilledResult($$5, $$3, PotionUtils.setPotion(new ItemStack((ItemLike)Items.POTION), Potions.WATER)));
/*     */             $$3.awardStat(Stats.USE_CAULDRON);
/*     */             $$3.awardStat(Stats.ITEM_USED.get($$6));
/*     */             LayeredCauldronBlock.lowerFillLevel($$0, $$1, $$2);
/*     */             $$1.playSound(null, $$2, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */             $$1.gameEvent(null, GameEvent.FLUID_PICKUP, $$2);
/*     */           } 
/*     */           return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */         });
/* 100 */     $$1.put(Items.POTION, ($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*     */           if (((Integer)$$0.getValue((Property)LayeredCauldronBlock.LEVEL)).intValue() == 3 || PotionUtils.getPotion($$5) != Potions.WATER) {
/*     */             return InteractionResult.PASS;
/*     */           }
/*     */           
/*     */           if (!$$1.isClientSide) {
/*     */             $$3.setItemInHand($$4, ItemUtils.createFilledResult($$5, $$3, new ItemStack((ItemLike)Items.GLASS_BOTTLE)));
/*     */             
/*     */             $$3.awardStat(Stats.USE_CAULDRON);
/*     */             
/*     */             $$3.awardStat(Stats.ITEM_USED.get($$5.getItem()));
/*     */             $$1.setBlockAndUpdate($$2, (BlockState)$$0.cycle((Property)LayeredCauldronBlock.LEVEL));
/*     */             $$1.playSound(null, $$2, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */             $$1.gameEvent(null, GameEvent.FLUID_PLACE, $$2);
/*     */           } 
/*     */           return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */         });
/* 117 */     $$1.put(Items.LEATHER_BOOTS, DYED_ITEM);
/* 118 */     $$1.put(Items.LEATHER_LEGGINGS, DYED_ITEM);
/* 119 */     $$1.put(Items.LEATHER_CHESTPLATE, DYED_ITEM);
/* 120 */     $$1.put(Items.LEATHER_HELMET, DYED_ITEM);
/* 121 */     $$1.put(Items.LEATHER_HORSE_ARMOR, DYED_ITEM);
/*     */     
/* 123 */     $$1.put(Items.WHITE_BANNER, BANNER);
/* 124 */     $$1.put(Items.GRAY_BANNER, BANNER);
/* 125 */     $$1.put(Items.BLACK_BANNER, BANNER);
/* 126 */     $$1.put(Items.BLUE_BANNER, BANNER);
/* 127 */     $$1.put(Items.BROWN_BANNER, BANNER);
/* 128 */     $$1.put(Items.CYAN_BANNER, BANNER);
/* 129 */     $$1.put(Items.GREEN_BANNER, BANNER);
/* 130 */     $$1.put(Items.LIGHT_BLUE_BANNER, BANNER);
/* 131 */     $$1.put(Items.LIGHT_GRAY_BANNER, BANNER);
/* 132 */     $$1.put(Items.LIME_BANNER, BANNER);
/* 133 */     $$1.put(Items.MAGENTA_BANNER, BANNER);
/* 134 */     $$1.put(Items.ORANGE_BANNER, BANNER);
/* 135 */     $$1.put(Items.PINK_BANNER, BANNER);
/* 136 */     $$1.put(Items.PURPLE_BANNER, BANNER);
/* 137 */     $$1.put(Items.RED_BANNER, BANNER);
/* 138 */     $$1.put(Items.YELLOW_BANNER, BANNER);
/*     */     
/* 140 */     $$1.put(Items.WHITE_SHULKER_BOX, SHULKER_BOX);
/* 141 */     $$1.put(Items.GRAY_SHULKER_BOX, SHULKER_BOX);
/* 142 */     $$1.put(Items.BLACK_SHULKER_BOX, SHULKER_BOX);
/* 143 */     $$1.put(Items.BLUE_SHULKER_BOX, SHULKER_BOX);
/* 144 */     $$1.put(Items.BROWN_SHULKER_BOX, SHULKER_BOX);
/* 145 */     $$1.put(Items.CYAN_SHULKER_BOX, SHULKER_BOX);
/* 146 */     $$1.put(Items.GREEN_SHULKER_BOX, SHULKER_BOX);
/* 147 */     $$1.put(Items.LIGHT_BLUE_SHULKER_BOX, SHULKER_BOX);
/* 148 */     $$1.put(Items.LIGHT_GRAY_SHULKER_BOX, SHULKER_BOX);
/* 149 */     $$1.put(Items.LIME_SHULKER_BOX, SHULKER_BOX);
/* 150 */     $$1.put(Items.MAGENTA_SHULKER_BOX, SHULKER_BOX);
/* 151 */     $$1.put(Items.ORANGE_SHULKER_BOX, SHULKER_BOX);
/* 152 */     $$1.put(Items.PINK_SHULKER_BOX, SHULKER_BOX);
/* 153 */     $$1.put(Items.PURPLE_SHULKER_BOX, SHULKER_BOX);
/* 154 */     $$1.put(Items.RED_SHULKER_BOX, SHULKER_BOX);
/* 155 */     $$1.put(Items.YELLOW_SHULKER_BOX, SHULKER_BOX);
/*     */ 
/*     */     
/* 158 */     Map<Item, CauldronInteraction> $$2 = LAVA.map();
/* 159 */     $$2.put(Items.BUCKET, ($$0, $$1, $$2, $$3, $$4, $$5) -> fillBucket($$0, $$1, $$2, $$3, $$4, $$5, new ItemStack((ItemLike)Items.LAVA_BUCKET), (), SoundEvents.BUCKET_FILL_LAVA));
/*     */ 
/*     */ 
/*     */     
/* 163 */     addDefaultInteractions($$2);
/*     */     
/* 165 */     Map<Item, CauldronInteraction> $$3 = POWDER_SNOW.map();
/* 166 */     $$3.put(Items.BUCKET, ($$0, $$1, $$2, $$3, $$4, $$5) -> fillBucket($$0, $$1, $$2, $$3, $$4, $$5, new ItemStack((ItemLike)Items.POWDER_SNOW_BUCKET), (), SoundEvents.BUCKET_FILL_POWDER_SNOW));
/*     */ 
/*     */ 
/*     */     
/* 170 */     addDefaultInteractions($$3);
/*     */   }
/*     */   
/*     */   static void addDefaultInteractions(Map<Item, CauldronInteraction> $$0) {
/* 174 */     $$0.put(Items.LAVA_BUCKET, FILL_LAVA);
/* 175 */     $$0.put(Items.WATER_BUCKET, FILL_WATER);
/* 176 */     $$0.put(Items.POWDER_SNOW_BUCKET, FILL_POWDER_SNOW);
/*     */   }
/*     */   
/*     */   static InteractionResult fillBucket(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, ItemStack $$5, ItemStack $$6, Predicate<BlockState> $$7, SoundEvent $$8) {
/* 180 */     if (!$$7.test($$0)) {
/* 181 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 184 */     if (!$$1.isClientSide) {
/* 185 */       Item $$9 = $$5.getItem();
/* 186 */       $$3.setItemInHand($$4, ItemUtils.createFilledResult($$5, $$3, $$6));
/* 187 */       $$3.awardStat(Stats.USE_CAULDRON);
/* 188 */       $$3.awardStat(Stats.ITEM_USED.get($$9));
/*     */       
/* 190 */       $$1.setBlockAndUpdate($$2, Blocks.CAULDRON.defaultBlockState());
/* 191 */       $$1.playSound(null, $$2, $$8, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 192 */       $$1.gameEvent(null, GameEvent.FLUID_PICKUP, $$2);
/*     */     } 
/* 194 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */   
/*     */   static InteractionResult emptyBucket(Level $$0, BlockPos $$1, Player $$2, InteractionHand $$3, ItemStack $$4, BlockState $$5, SoundEvent $$6) {
/* 198 */     if (!$$0.isClientSide) {
/* 199 */       Item $$7 = $$4.getItem();
/* 200 */       $$2.setItemInHand($$3, ItemUtils.createFilledResult($$4, $$2, new ItemStack((ItemLike)Items.BUCKET)));
/* 201 */       $$2.awardStat(Stats.FILL_CAULDRON);
/* 202 */       $$2.awardStat(Stats.ITEM_USED.get($$7));
/*     */       
/* 204 */       $$0.setBlockAndUpdate($$1, $$5);
/* 205 */       $$0.playSound(null, $$1, $$6, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 206 */       $$0.gameEvent(null, GameEvent.FLUID_PLACE, $$1);
/*     */     } 
/* 208 */     return InteractionResult.sidedSuccess($$0.isClientSide);
/*     */   }
/*     */   
/*     */   static {
/* 212 */     FILL_WATER = (($$0, $$1, $$2, $$3, $$4, $$5) -> emptyBucket($$1, $$2, $$3, $$4, $$5, (BlockState)Blocks.WATER_CAULDRON.defaultBlockState().setValue((Property)LayeredCauldronBlock.LEVEL, Integer.valueOf(3)), SoundEvents.BUCKET_EMPTY));
/* 213 */     FILL_LAVA = (($$0, $$1, $$2, $$3, $$4, $$5) -> emptyBucket($$1, $$2, $$3, $$4, $$5, Blocks.LAVA_CAULDRON.defaultBlockState(), SoundEvents.BUCKET_EMPTY_LAVA));
/* 214 */     FILL_POWDER_SNOW = (($$0, $$1, $$2, $$3, $$4, $$5) -> emptyBucket($$1, $$2, $$3, $$4, $$5, (BlockState)Blocks.POWDER_SNOW_CAULDRON.defaultBlockState().setValue((Property)LayeredCauldronBlock.LEVEL, Integer.valueOf(3)), SoundEvents.BUCKET_EMPTY_POWDER_SNOW));
/*     */ 
/*     */     
/* 217 */     SHULKER_BOX = (($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*     */         Block $$6 = Block.byItem($$5.getItem());
/*     */         
/*     */         if (!($$6 instanceof net.minecraft.world.level.block.ShulkerBoxBlock)) {
/*     */           return InteractionResult.PASS;
/*     */         }
/*     */         
/*     */         if (!$$1.isClientSide) {
/*     */           ItemStack $$7 = new ItemStack((ItemLike)Blocks.SHULKER_BOX);
/*     */           
/*     */           if ($$5.hasTag()) {
/*     */             $$7.setTag($$5.getTag().copy());
/*     */           }
/*     */           
/*     */           $$3.setItemInHand($$4, $$7);
/*     */           $$3.awardStat(Stats.CLEAN_SHULKER_BOX);
/*     */           LayeredCauldronBlock.lowerFillLevel($$0, $$1, $$2);
/*     */         } 
/*     */         return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */       });
/* 237 */     BANNER = (($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*     */         if (BannerBlockEntity.getPatternCount($$5) <= 0) {
/*     */           return InteractionResult.PASS;
/*     */         }
/*     */         
/*     */         if (!$$1.isClientSide) {
/*     */           ItemStack $$6 = $$5.copyWithCount(1);
/*     */           
/*     */           BannerBlockEntity.removeLastPattern($$6);
/*     */           
/*     */           if (!($$3.getAbilities()).instabuild) {
/*     */             $$5.shrink(1);
/*     */           }
/*     */           
/*     */           if ($$5.isEmpty()) {
/*     */             $$3.setItemInHand($$4, $$6);
/*     */           } else if ($$3.getInventory().add($$6)) {
/*     */             $$3.inventoryMenu.sendAllDataToRemote();
/*     */           } else {
/*     */             $$3.drop($$6, false);
/*     */           } 
/*     */           
/*     */           $$3.awardStat(Stats.CLEAN_BANNER);
/*     */           
/*     */           LayeredCauldronBlock.lowerFillLevel($$0, $$1, $$2);
/*     */         } 
/*     */         
/*     */         return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */       });
/*     */     
/* 267 */     DYED_ITEM = (($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*     */         Item $$6 = $$5.getItem();
/*     */         if (!($$6 instanceof DyeableLeatherItem))
/*     */           return InteractionResult.PASS; 
/*     */         DyeableLeatherItem $$7 = (DyeableLeatherItem)$$6;
/*     */         if (!$$7.hasCustomColor($$5))
/*     */           return InteractionResult.PASS; 
/*     */         if (!$$1.isClientSide) {
/*     */           $$7.clearColor($$5);
/*     */           $$3.awardStat(Stats.CLEAN_ARMOR);
/*     */           LayeredCauldronBlock.lowerFillLevel($$0, $$1, $$2);
/*     */         } 
/*     */         return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */       });
/*     */   }
/*     */   
/*     */   InteractionResult interact(BlockState paramBlockState, Level paramLevel, BlockPos paramBlockPos, Player paramPlayer, InteractionHand paramInteractionHand, ItemStack paramItemStack);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\cauldron\CauldronInteraction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */