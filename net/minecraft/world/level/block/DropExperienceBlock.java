/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ 
/*    */ public class DropExperienceBlock extends Block {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)IntProvider.codec(0, 10).fieldOf("experience").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, DropExperienceBlock::new));
/*    */   }
/*    */   
/*    */   public static final MapCodec<DropExperienceBlock> CODEC;
/*    */   private final IntProvider xpRange;
/*    */   
/*    */   public MapCodec<? extends DropExperienceBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public DropExperienceBlock(IntProvider $$0, BlockBehaviour.Properties $$1) {
/* 25 */     super($$1);
/* 26 */     this.xpRange = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void spawnAfterBreak(BlockState $$0, ServerLevel $$1, BlockPos $$2, ItemStack $$3, boolean $$4) {
/* 31 */     super.spawnAfterBreak($$0, $$1, $$2, $$3, $$4);
/* 32 */     if ($$4)
/* 33 */       tryDropExperience($$1, $$2, $$3, this.xpRange); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DropExperienceBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */