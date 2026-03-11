/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.ImmutableBiMap;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SignText;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ public class HoneycombItem
/*     */   extends Item implements SignApplicator {
/*  25 */   public static final Supplier<BiMap<Block, Block>> WAXABLES = (Supplier<BiMap<Block, Block>>)Suppliers.memoize(() -> ImmutableBiMap.builder().put(Blocks.COPPER_BLOCK, Blocks.WAXED_COPPER_BLOCK).put(Blocks.EXPOSED_COPPER, Blocks.WAXED_EXPOSED_COPPER).put(Blocks.WEATHERED_COPPER, Blocks.WAXED_WEATHERED_COPPER).put(Blocks.OXIDIZED_COPPER, Blocks.WAXED_OXIDIZED_COPPER).put(Blocks.CUT_COPPER, Blocks.WAXED_CUT_COPPER).put(Blocks.EXPOSED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER).put(Blocks.WEATHERED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER).put(Blocks.OXIDIZED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER).put(Blocks.CUT_COPPER_SLAB, Blocks.WAXED_CUT_COPPER_SLAB).put(Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB).put(Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB).put(Blocks.OXIDIZED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB).put(Blocks.CUT_COPPER_STAIRS, Blocks.WAXED_CUT_COPPER_STAIRS).put(Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS).put(Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS).put(Blocks.OXIDIZED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS).put(Blocks.CHISELED_COPPER, Blocks.WAXED_CHISELED_COPPER).put(Blocks.EXPOSED_CHISELED_COPPER, Blocks.WAXED_EXPOSED_CHISELED_COPPER).put(Blocks.WEATHERED_CHISELED_COPPER, Blocks.WAXED_WEATHERED_CHISELED_COPPER).put(Blocks.OXIDIZED_CHISELED_COPPER, Blocks.WAXED_OXIDIZED_CHISELED_COPPER).put(Blocks.COPPER_DOOR, Blocks.WAXED_COPPER_DOOR).put(Blocks.EXPOSED_COPPER_DOOR, Blocks.WAXED_EXPOSED_COPPER_DOOR).put(Blocks.WEATHERED_COPPER_DOOR, Blocks.WAXED_WEATHERED_COPPER_DOOR).put(Blocks.OXIDIZED_COPPER_DOOR, Blocks.WAXED_OXIDIZED_COPPER_DOOR).put(Blocks.COPPER_TRAPDOOR, Blocks.WAXED_COPPER_TRAPDOOR).put(Blocks.EXPOSED_COPPER_TRAPDOOR, Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR).put(Blocks.WEATHERED_COPPER_TRAPDOOR, Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR).put(Blocks.OXIDIZED_COPPER_TRAPDOOR, Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR).put(Blocks.COPPER_GRATE, Blocks.WAXED_COPPER_GRATE).put(Blocks.EXPOSED_COPPER_GRATE, Blocks.WAXED_EXPOSED_COPPER_GRATE).put(Blocks.WEATHERED_COPPER_GRATE, Blocks.WAXED_WEATHERED_COPPER_GRATE).put(Blocks.OXIDIZED_COPPER_GRATE, Blocks.WAXED_OXIDIZED_COPPER_GRATE).put(Blocks.COPPER_BULB, Blocks.WAXED_COPPER_BULB).put(Blocks.EXPOSED_COPPER_BULB, Blocks.WAXED_EXPOSED_COPPER_BULB).put(Blocks.WEATHERED_COPPER_BULB, Blocks.WAXED_WEATHERED_COPPER_BULB).put(Blocks.OXIDIZED_COPPER_BULB, Blocks.WAXED_OXIDIZED_COPPER_BULB).build());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final Supplier<BiMap<Block, Block>> WAX_OFF_BY_BLOCK = (Supplier<BiMap<Block, Block>>)Suppliers.memoize(() -> ((BiMap)WAXABLES.get()).inverse());
/*     */   
/*     */   public HoneycombItem(Item.Properties $$0) {
/*  76 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  81 */     Level $$1 = $$0.getLevel();
/*  82 */     BlockPos $$2 = $$0.getClickedPos();
/*  83 */     BlockState $$3 = $$1.getBlockState($$2);
/*     */     
/*  85 */     return getWaxed($$3).<InteractionResult>map($$3 -> {
/*     */           Player $$4 = $$0.getPlayer();
/*     */           
/*     */           ItemStack $$5 = $$0.getItemInHand();
/*     */           if ($$4 instanceof ServerPlayer) {
/*     */             CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)$$4, $$1, $$5);
/*     */           }
/*     */           $$5.shrink(1);
/*     */           $$2.setBlock($$1, $$3, 11);
/*     */           $$2.gameEvent(GameEvent.BLOCK_CHANGE, $$1, GameEvent.Context.of((Entity)$$4, $$3));
/*     */           $$2.levelEvent($$4, 3003, $$1, 0);
/*     */           return InteractionResult.sidedSuccess($$2.isClientSide);
/*  97 */         }).orElse(InteractionResult.PASS);
/*     */   }
/*     */   
/*     */   public static Optional<BlockState> getWaxed(BlockState $$0) {
/* 101 */     return Optional.<Block>ofNullable((Block)((BiMap)WAXABLES.get()).get($$0.getBlock())).map($$1 -> $$1.withPropertiesOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean tryApplyToSign(Level $$0, SignBlockEntity $$1, boolean $$2, Player $$3) {
/* 106 */     if ($$1.setWaxed(true)) {
/* 107 */       $$0.levelEvent(null, 3003, $$1.getBlockPos(), 0);
/* 108 */       return true;
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canApplyToSign(SignText $$0, Player $$1) {
/* 115 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\HoneycombItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */