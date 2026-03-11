/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.RegistryCodecs;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class BlockRotProcessor extends StructureProcessor {
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RegistryCodecs.homogeneousList(Registries.BLOCK).optionalFieldOf("rottable_blocks").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("integrity").forGetter(())).apply((Applicative)$$0, BlockRotProcessor::new));
/*    */   }
/*    */   
/*    */   public static final Codec<BlockRotProcessor> CODEC;
/*    */   private final Optional<HolderSet<Block>> rottableBlocks;
/*    */   private final float integrity;
/*    */   
/*    */   public BlockRotProcessor(HolderSet<Block> $$0, float $$1) {
/* 25 */     this(Optional.of($$0), $$1);
/*    */   }
/*    */   
/*    */   public BlockRotProcessor(float $$0) {
/* 29 */     this(Optional.empty(), $$0);
/*    */   }
/*    */   
/*    */   private BlockRotProcessor(Optional<HolderSet<Block>> $$0, float $$1) {
/* 33 */     this.integrity = $$1;
/* 34 */     this.rottableBlocks = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/* 40 */     RandomSource $$6 = $$5.getRandom($$4.pos());
/*    */     
/* 42 */     if ((this.rottableBlocks.isPresent() && !$$3.state().is(this.rottableBlocks.get())) || $$6.nextFloat() <= this.integrity) {
/* 43 */       return $$4;
/*    */     }
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureProcessorType<?> getType() {
/* 50 */     return StructureProcessorType.BLOCK_ROT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\BlockRotProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */