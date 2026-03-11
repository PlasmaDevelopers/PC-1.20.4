/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Clearable;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.CampfireCookingRecipe;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.RecipeManager;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.CampfireBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ public class CampfireBlockEntity
/*     */   extends BlockEntity implements Clearable {
/*     */   private static final int BURN_COOL_SPEED = 2;
/*     */   private static final int NUM_SLOTS = 4;
/*  36 */   private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
/*  37 */   private final int[] cookingProgress = new int[4];
/*  38 */   private final int[] cookingTime = new int[4];
/*     */   
/*  40 */   private final RecipeManager.CachedCheck<Container, CampfireCookingRecipe> quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
/*     */   
/*     */   public CampfireBlockEntity(BlockPos $$0, BlockState $$1) {
/*  43 */     super(BlockEntityType.CAMPFIRE, $$0, $$1);
/*     */   }
/*     */   
/*     */   public static void cookTick(Level $$0, BlockPos $$1, BlockState $$2, CampfireBlockEntity $$3) {
/*  47 */     boolean $$4 = false;
/*  48 */     for (int $$5 = 0; $$5 < $$3.items.size(); $$5++) {
/*  49 */       ItemStack $$6 = (ItemStack)$$3.items.get($$5);
/*  50 */       if (!$$6.isEmpty()) {
/*     */ 
/*     */ 
/*     */         
/*  54 */         $$4 = true;
/*  55 */         $$3.cookingProgress[$$5] = $$3.cookingProgress[$$5] + 1;
/*  56 */         if ($$3.cookingProgress[$$5] >= $$3.cookingTime[$$5]) {
/*  57 */           SimpleContainer simpleContainer = new SimpleContainer(new ItemStack[] { $$6 });
/*     */           
/*  59 */           ItemStack $$8 = $$3.quickCheck.getRecipeFor((Container)simpleContainer, $$0).map($$2 -> ((CampfireCookingRecipe)$$2.value()).assemble($$0, $$1.registryAccess())).orElse($$6);
/*  60 */           if ($$8.isItemEnabled($$0.enabledFeatures())) {
/*  61 */             Containers.dropItemStack($$0, $$1.getX(), $$1.getY(), $$1.getZ(), $$8);
/*  62 */             $$3.items.set($$5, ItemStack.EMPTY);
/*  63 */             $$0.sendBlockUpdated($$1, $$2, $$2, 3);
/*  64 */             $$0.gameEvent(GameEvent.BLOCK_CHANGE, $$1, GameEvent.Context.of($$2));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*  69 */     if ($$4) {
/*  70 */       setChanged($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void cooldownTick(Level $$0, BlockPos $$1, BlockState $$2, CampfireBlockEntity $$3) {
/*  75 */     boolean $$4 = false;
/*     */     
/*  77 */     for (int $$5 = 0; $$5 < $$3.items.size(); $$5++) {
/*  78 */       if ($$3.cookingProgress[$$5] > 0) {
/*  79 */         $$4 = true;
/*  80 */         $$3.cookingProgress[$$5] = Mth.clamp($$3.cookingProgress[$$5] - 2, 0, $$3.cookingTime[$$5]);
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     if ($$4) {
/*  85 */       setChanged($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void particleTick(Level $$0, BlockPos $$1, BlockState $$2, CampfireBlockEntity $$3) {
/*  90 */     RandomSource $$4 = $$0.random;
/*     */     
/*  92 */     if ($$4.nextFloat() < 0.11F) {
/*  93 */       for (int $$5 = 0; $$5 < $$4.nextInt(2) + 2; $$5++) {
/*  94 */         CampfireBlock.makeParticles($$0, $$1, ((Boolean)$$2.getValue((Property)CampfireBlock.SIGNAL_FIRE)).booleanValue(), false);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*  99 */     int $$6 = ((Direction)$$2.getValue((Property)CampfireBlock.FACING)).get2DDataValue();
/* 100 */     for (int $$7 = 0; $$7 < $$3.items.size(); $$7++) {
/* 101 */       if (!((ItemStack)$$3.items.get($$7)).isEmpty() && $$4.nextFloat() < 0.2F) {
/* 102 */         Direction $$8 = Direction.from2DDataValue(Math.floorMod($$7 + $$6, 4));
/* 103 */         float $$9 = 0.3125F;
/*     */         
/* 105 */         double $$10 = $$1.getX() + 0.5D - ($$8.getStepX() * 0.3125F) + ($$8.getClockWise().getStepX() * 0.3125F);
/* 106 */         double $$11 = $$1.getY() + 0.5D;
/* 107 */         double $$12 = $$1.getZ() + 0.5D - ($$8.getStepZ() * 0.3125F) + ($$8.getClockWise().getStepZ() * 0.3125F);
/*     */         
/* 109 */         for (int $$13 = 0; $$13 < 4; $$13++) {
/* 110 */           $$0.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$10, $$11, $$12, 0.0D, 5.0E-4D, 0.0D);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public NonNullList<ItemStack> getItems() {
/* 117 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 122 */     super.load($$0);
/*     */     
/* 124 */     this.items.clear();
/* 125 */     ContainerHelper.loadAllItems($$0, this.items);
/*     */     
/* 127 */     if ($$0.contains("CookingTimes", 11)) {
/* 128 */       int[] $$1 = $$0.getIntArray("CookingTimes");
/* 129 */       System.arraycopy($$1, 0, this.cookingProgress, 0, Math.min(this.cookingTime.length, $$1.length));
/*     */     } 
/*     */     
/* 132 */     if ($$0.contains("CookingTotalTimes", 11)) {
/* 133 */       int[] $$2 = $$0.getIntArray("CookingTotalTimes");
/* 134 */       System.arraycopy($$2, 0, this.cookingTime, 0, Math.min(this.cookingTime.length, $$2.length));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 140 */     super.saveAdditional($$0);
/*     */     
/* 142 */     ContainerHelper.saveAllItems($$0, this.items, true);
/*     */     
/* 144 */     $$0.putIntArray("CookingTimes", this.cookingProgress);
/* 145 */     $$0.putIntArray("CookingTotalTimes", this.cookingTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 150 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 155 */     CompoundTag $$0 = new CompoundTag();
/* 156 */     ContainerHelper.saveAllItems($$0, this.items, true);
/* 157 */     return $$0;
/*     */   }
/*     */   
/*     */   public Optional<RecipeHolder<CampfireCookingRecipe>> getCookableRecipe(ItemStack $$0) {
/* 161 */     if (this.items.stream().noneMatch(ItemStack::isEmpty)) {
/* 162 */       return Optional.empty();
/*     */     }
/*     */     
/* 165 */     return this.quickCheck.getRecipeFor((Container)new SimpleContainer(new ItemStack[] { $$0 }, ), this.level);
/*     */   }
/*     */   
/*     */   public boolean placeFood(@Nullable Entity $$0, ItemStack $$1, int $$2) {
/* 169 */     for (int $$3 = 0; $$3 < this.items.size(); $$3++) {
/* 170 */       ItemStack $$4 = (ItemStack)this.items.get($$3);
/* 171 */       if ($$4.isEmpty()) {
/* 172 */         this.cookingTime[$$3] = $$2;
/* 173 */         this.cookingProgress[$$3] = 0;
/*     */         
/* 175 */         this.items.set($$3, $$1.split(1));
/* 176 */         this.level.gameEvent(GameEvent.BLOCK_CHANGE, getBlockPos(), GameEvent.Context.of($$0, getBlockState()));
/*     */         
/* 178 */         markUpdated();
/*     */         
/* 180 */         return true;
/*     */       } 
/*     */     } 
/* 183 */     return false;
/*     */   }
/*     */   
/*     */   private void markUpdated() {
/* 187 */     setChanged();
/* 188 */     getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 193 */     this.items.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dowse() {
/* 198 */     if (this.level != null)
/* 199 */       markUpdated(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\CampfireBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */