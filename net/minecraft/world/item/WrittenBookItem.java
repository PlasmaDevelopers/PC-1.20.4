/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.StringUtil;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LecternBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrittenBookItem
/*     */   extends Item
/*     */ {
/*     */   public static final int TITLE_LENGTH = 16;
/*     */   public static final int TITLE_MAX_LENGTH = 32;
/*     */   public static final int PAGE_EDIT_LENGTH = 1024;
/*     */   public static final int PAGE_LENGTH = 32767;
/*     */   public static final int MAX_PAGES = 100;
/*     */   public static final int MAX_GENERATION = 2;
/*     */   
/*     */   public WrittenBookItem(Item.Properties $$0) {
/*  43 */     super($$0);
/*     */   }
/*     */   public static final String TAG_TITLE = "title"; public static final String TAG_FILTERED_TITLE = "filtered_title"; public static final String TAG_AUTHOR = "author"; public static final String TAG_PAGES = "pages"; public static final String TAG_FILTERED_PAGES = "filtered_pages"; public static final String TAG_GENERATION = "generation"; public static final String TAG_RESOLVED = "resolved";
/*     */   public static boolean makeSureTagIsValid(@Nullable CompoundTag $$0) {
/*  47 */     if (!WritableBookItem.makeSureTagIsValid($$0)) {
/*  48 */       return false;
/*     */     }
/*     */     
/*  51 */     if (!$$0.contains("title", 8)) {
/*  52 */       return false;
/*     */     }
/*  54 */     String $$1 = $$0.getString("title");
/*  55 */     if ($$1.length() > 32) {
/*  56 */       return false;
/*     */     }
/*     */     
/*  59 */     return $$0.contains("author", 8);
/*     */   }
/*     */   
/*     */   public static int getGeneration(ItemStack $$0) {
/*  63 */     return $$0.getTag().getInt("generation");
/*     */   }
/*     */   
/*     */   public static int getPageCount(ItemStack $$0) {
/*  67 */     CompoundTag $$1 = $$0.getTag();
/*  68 */     return ($$1 != null) ? $$1.getList("pages", 8).size() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName(ItemStack $$0) {
/*  73 */     CompoundTag $$1 = $$0.getTag();
/*  74 */     if ($$1 != null) {
/*  75 */       String $$2 = $$1.getString("title");
/*  76 */       if (!StringUtil.isNullOrEmpty($$2)) {
/*  77 */         return (Component)Component.literal($$2);
/*     */       }
/*     */     } 
/*  80 */     return super.getName($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/*  85 */     if ($$0.hasTag()) {
/*  86 */       CompoundTag $$4 = $$0.getTag();
/*     */       
/*  88 */       String $$5 = $$4.getString("author");
/*  89 */       if (!StringUtil.isNullOrEmpty($$5)) {
/*  90 */         $$2.add(Component.translatable("book.byAuthor", new Object[] { $$5 }).withStyle(ChatFormatting.GRAY));
/*     */       }
/*     */       
/*  93 */       $$2.add(Component.translatable("book.generation." + $$4.getInt("generation")).withStyle(ChatFormatting.GRAY));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  99 */     Level $$1 = $$0.getLevel();
/* 100 */     BlockPos $$2 = $$0.getClickedPos();
/* 101 */     BlockState $$3 = $$1.getBlockState($$2);
/*     */     
/* 103 */     if ($$3.is(Blocks.LECTERN)) {
/* 104 */       return LecternBlock.tryPlaceBook((Entity)$$0.getPlayer(), $$1, $$2, $$3, $$0.getItemInHand()) ? InteractionResult.sidedSuccess($$1.isClientSide) : InteractionResult.PASS;
/*     */     }
/*     */     
/* 107 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 112 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 113 */     $$1.openItemGui($$3, $$2);
/* 114 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 115 */     return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*     */   }
/*     */   
/*     */   public static boolean resolveBookComponents(ItemStack $$0, @Nullable CommandSourceStack $$1, @Nullable Player $$2) {
/* 119 */     CompoundTag $$3 = $$0.getTag();
/* 120 */     if ($$3 == null || $$3.getBoolean("resolved")) {
/* 121 */       return false;
/*     */     }
/* 123 */     $$3.putBoolean("resolved", true);
/* 124 */     if (!makeSureTagIsValid($$3)) {
/* 125 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 129 */     ListTag $$4 = $$3.getList("pages", 8);
/* 130 */     ListTag $$5 = new ListTag();
/* 131 */     for (int $$6 = 0; $$6 < $$4.size(); $$6++) {
/* 132 */       String $$7 = resolvePage($$1, $$2, $$4.getString($$6));
/* 133 */       if ($$7.length() > 32767) {
/* 134 */         return false;
/*     */       }
/* 136 */       $$5.add($$6, (Tag)StringTag.valueOf($$7));
/*     */     } 
/*     */     
/* 139 */     if ($$3.contains("filtered_pages", 10)) {
/* 140 */       CompoundTag $$8 = $$3.getCompound("filtered_pages");
/* 141 */       CompoundTag $$9 = new CompoundTag();
/* 142 */       for (String $$10 : $$8.getAllKeys()) {
/* 143 */         String $$11 = resolvePage($$1, $$2, $$8.getString($$10));
/* 144 */         if ($$11.length() > 32767) {
/* 145 */           return false;
/*     */         }
/* 147 */         $$9.putString($$10, $$11);
/*     */       } 
/* 149 */       $$3.put("filtered_pages", (Tag)$$9);
/*     */     } 
/* 151 */     $$3.put("pages", (Tag)$$5);
/* 152 */     return true;
/*     */   }
/*     */   
/*     */   private static String resolvePage(@Nullable CommandSourceStack $$0, @Nullable Player $$1, String $$2) {
/*     */     MutableComponent mutableComponent;
/*     */     try {
/* 158 */       mutableComponent = Component.Serializer.fromJsonLenient($$2);
/* 159 */       mutableComponent = ComponentUtils.updateForEntity($$0, (Component)mutableComponent, (Entity)$$1, 0);
/* 160 */     } catch (Exception $$4) {
/* 161 */       mutableComponent = Component.literal($$2);
/*     */     } 
/* 163 */     return Component.Serializer.toJson((Component)mutableComponent);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFoil(ItemStack $$0) {
/* 168 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\WrittenBookItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */