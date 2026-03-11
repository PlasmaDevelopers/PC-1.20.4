/*    */ package net.minecraft.world.level.block;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Map;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.monster.Silverfish;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.enchantment.Enchantments;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class InfestedBlock extends Block {
/*    */   static {
/* 22 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("host").forGetter(InfestedBlock::getHostBlock), (App)propertiesCodec()).apply((Applicative)$$0, InfestedBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<InfestedBlock> CODEC;
/*    */   private final Block hostBlock;
/*    */   
/*    */   public MapCodec<? extends InfestedBlock> codec() {
/* 29 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 34 */   private static final Map<Block, Block> BLOCK_BY_HOST_BLOCK = Maps.newIdentityHashMap();
/*    */   
/* 36 */   private static final Map<BlockState, BlockState> HOST_TO_INFESTED_STATES = Maps.newIdentityHashMap();
/* 37 */   private static final Map<BlockState, BlockState> INFESTED_TO_HOST_STATES = Maps.newIdentityHashMap();
/*    */   
/*    */   public InfestedBlock(Block $$0, BlockBehaviour.Properties $$1) {
/* 40 */     super($$1.destroyTime($$0.defaultDestroyTime() / 2.0F).explosionResistance(0.75F));
/* 41 */     this.hostBlock = $$0;
/* 42 */     BLOCK_BY_HOST_BLOCK.put($$0, this);
/*    */   }
/*    */   
/*    */   public Block getHostBlock() {
/* 46 */     return this.hostBlock;
/*    */   }
/*    */   
/*    */   public static boolean isCompatibleHostBlock(BlockState $$0) {
/* 50 */     return BLOCK_BY_HOST_BLOCK.containsKey($$0.getBlock());
/*    */   }
/*    */   
/*    */   private void spawnInfestation(ServerLevel $$0, BlockPos $$1) {
/* 54 */     Silverfish $$2 = (Silverfish)EntityType.SILVERFISH.create((Level)$$0);
/* 55 */     if ($$2 != null) {
/* 56 */       $$2.moveTo($$1.getX() + 0.5D, $$1.getY(), $$1.getZ() + 0.5D, 0.0F, 0.0F);
/* 57 */       $$0.addFreshEntity((Entity)$$2);
/*    */       
/* 59 */       $$2.spawnAnim();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void spawnAfterBreak(BlockState $$0, ServerLevel $$1, BlockPos $$2, ItemStack $$3, boolean $$4) {
/* 65 */     super.spawnAfterBreak($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 67 */     if ($$1.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && 
/* 68 */       EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, $$3) == 0) {
/* 69 */       spawnInfestation($$1, $$2);
/*    */     }
/*    */   }
/*    */   
/*    */   public static BlockState infestedStateByHost(BlockState $$0) {
/* 74 */     return getNewStateWithProperties(HOST_TO_INFESTED_STATES, $$0, () -> ((Block)BLOCK_BY_HOST_BLOCK.get($$0.getBlock())).defaultBlockState());
/*    */   }
/*    */   
/*    */   public BlockState hostStateByInfested(BlockState $$0) {
/* 78 */     return getNewStateWithProperties(INFESTED_TO_HOST_STATES, $$0, () -> getHostBlock().defaultBlockState());
/*    */   }
/*    */   
/*    */   private static BlockState getNewStateWithProperties(Map<BlockState, BlockState> $$0, BlockState $$1, Supplier<BlockState> $$2) {
/* 82 */     return $$0.computeIfAbsent($$1, $$1 -> {
/*    */           BlockState $$2 = $$0.get();
/*    */           for (Property $$3 : $$1.getProperties())
/*    */             $$2 = $$2.hasProperty($$3) ? (BlockState)$$2.setValue($$3, $$1.getValue($$3)) : $$2; 
/*    */           return $$2;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\InfestedBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */