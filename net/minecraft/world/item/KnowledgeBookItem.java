/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.crafting.RecipeHolder;
/*    */ import net.minecraft.world.item.crafting.RecipeManager;
/*    */ import net.minecraft.world.level.Level;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class KnowledgeBookItem
/*    */   extends Item
/*    */ {
/*    */   private static final String RECIPE_TAG = "Recipes";
/* 23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public KnowledgeBookItem(Item.Properties $$0) {
/* 26 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 31 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 32 */     CompoundTag $$4 = $$3.getTag();
/*    */     
/* 34 */     if (!($$1.getAbilities()).instabuild) {
/* 35 */       $$1.setItemInHand($$2, ItemStack.EMPTY);
/*    */     }
/*    */     
/* 38 */     if ($$4 == null || !$$4.contains("Recipes", 9)) {
/* 39 */       LOGGER.error("Tag not valid: {}", $$4);
/* 40 */       return InteractionResultHolder.fail($$3);
/*    */     } 
/*    */     
/* 43 */     if (!$$0.isClientSide) {
/* 44 */       ListTag $$5 = $$4.getList("Recipes", 8);
/* 45 */       List<RecipeHolder<?>> $$6 = Lists.newArrayList();
/*    */       
/* 47 */       RecipeManager $$7 = $$0.getServer().getRecipeManager();
/* 48 */       for (int $$8 = 0; $$8 < $$5.size(); $$8++) {
/* 49 */         String $$9 = $$5.getString($$8);
/* 50 */         Optional<RecipeHolder<?>> $$10 = $$7.byKey(new ResourceLocation($$9));
/* 51 */         if ($$10.isPresent()) {
/* 52 */           $$6.add($$10.get());
/*    */         } else {
/* 54 */           LOGGER.error("Invalid recipe: {}", $$9);
/* 55 */           return InteractionResultHolder.fail($$3);
/*    */         } 
/*    */       } 
/*    */       
/* 59 */       $$1.awardRecipes($$6);
/* 60 */       $$1.awardStat(Stats.ITEM_USED.get(this));
/*    */     } 
/*    */     
/* 63 */     return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\KnowledgeBookItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */