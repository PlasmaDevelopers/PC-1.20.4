/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.ComposterBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class WorkAtComposter extends WorkAtPoi {
/*  22 */   private static final List<Item> COMPOSTABLE_ITEMS = (List<Item>)ImmutableList.of(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void useWorkstation(ServerLevel $$0, Villager $$1) {
/*  29 */     Optional<GlobalPos> $$2 = $$1.getBrain().getMemory(MemoryModuleType.JOB_SITE);
/*  30 */     if ($$2.isEmpty()) {
/*     */       return;
/*     */     }
/*  33 */     GlobalPos $$3 = $$2.get();
/*  34 */     BlockState $$4 = $$0.getBlockState($$3.pos());
/*     */     
/*  36 */     if ($$4.is(Blocks.COMPOSTER)) {
/*  37 */       makeBread($$1);
/*     */       
/*  39 */       compostItems($$0, $$1, $$3, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void compostItems(ServerLevel $$0, Villager $$1, GlobalPos $$2, BlockState $$3) {
/*  45 */     BlockPos $$4 = $$2.pos();
/*  46 */     if (((Integer)$$3.getValue((Property)ComposterBlock.LEVEL)).intValue() == 8) {
/*  47 */       $$3 = ComposterBlock.extractProduce((Entity)$$1, $$3, (Level)$$0, $$4);
/*     */     }
/*     */ 
/*     */     
/*  51 */     int $$5 = 20;
/*  52 */     int $$6 = 10;
/*     */     
/*  54 */     int[] $$7 = new int[COMPOSTABLE_ITEMS.size()];
/*     */     
/*  56 */     SimpleContainer $$8 = $$1.getInventory();
/*  57 */     int $$9 = $$8.getContainerSize();
/*     */     
/*  59 */     BlockState $$10 = $$3;
/*     */     
/*  61 */     for (int $$11 = $$9 - 1; $$11 >= 0 && $$5 > 0; $$11--) {
/*  62 */       ItemStack $$12 = $$8.getItem($$11);
/*  63 */       int $$13 = COMPOSTABLE_ITEMS.indexOf($$12.getItem());
/*  64 */       if ($$13 != -1) {
/*     */ 
/*     */ 
/*     */         
/*  68 */         int $$14 = $$12.getCount();
/*  69 */         int $$15 = $$7[$$13] + $$14;
/*  70 */         $$7[$$13] = $$15;
/*     */         
/*  72 */         int $$16 = Math.min(Math.min($$15 - 10, $$5), $$14);
/*  73 */         if ($$16 > 0) {
/*  74 */           $$5 -= $$16;
/*  75 */           for (int $$17 = 0; $$17 < $$16; $$17++) {
/*  76 */             $$10 = ComposterBlock.insertItem((Entity)$$1, $$10, $$0, $$12, $$4);
/*  77 */             if (((Integer)$$10.getValue((Property)ComposterBlock.LEVEL)).intValue() == 7) {
/*  78 */               spawnComposterFillEffects($$0, $$3, $$4, $$10);
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  85 */     spawnComposterFillEffects($$0, $$3, $$4, $$10);
/*     */   }
/*     */   
/*     */   private void spawnComposterFillEffects(ServerLevel $$0, BlockState $$1, BlockPos $$2, BlockState $$3) {
/*  89 */     $$0.levelEvent(1500, $$2, ($$3 != $$1) ? 1 : 0);
/*     */   }
/*     */   
/*     */   private void makeBread(Villager $$0) {
/*  93 */     SimpleContainer $$1 = $$0.getInventory();
/*  94 */     if ($$1.countItem(Items.BREAD) > 36) {
/*     */       return;
/*     */     }
/*     */     
/*  98 */     int $$2 = $$1.countItem(Items.WHEAT);
/*  99 */     int $$3 = 3;
/* 100 */     int $$4 = 3;
/* 101 */     int $$5 = Math.min(3, $$2 / 3);
/* 102 */     if ($$5 == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     int $$6 = $$5 * 3;
/* 107 */     $$1.removeItemType(Items.WHEAT, $$6);
/* 108 */     ItemStack $$7 = $$1.addItem(new ItemStack((ItemLike)Items.BREAD, $$5));
/* 109 */     if (!$$7.isEmpty())
/* 110 */       $$0.spawnAtLocation($$7, 0.5F); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\WorkAtComposter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */