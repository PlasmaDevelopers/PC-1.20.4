/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.effect.MobEffect;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class FlowerBlock extends BushBlock implements SuspiciousEffectHolder {
/* 17 */   protected static final MapCodec<List<SuspiciousEffectHolder.EffectEntry>> EFFECTS_FIELD = SuspiciousEffectHolder.EffectEntry.LIST_CODEC.fieldOf("suspicious_stew_effects"); public static final MapCodec<FlowerBlock> CODEC;
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)EFFECTS_FIELD.forGetter(FlowerBlock::getSuspiciousEffects), (App)propertiesCodec()).apply((Applicative)$$0, FlowerBlock::new));
/*    */   }
/*    */ 
/*    */   
/*    */   protected static final float AABB_OFFSET = 3.0F;
/*    */   
/*    */   public MapCodec<? extends FlowerBlock> codec() {
/* 26 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/* 30 */   protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
/*    */   private final List<SuspiciousEffectHolder.EffectEntry> suspiciousStewEffects;
/*    */   
/*    */   public FlowerBlock(MobEffect $$0, int $$1, BlockBehaviour.Properties $$2) {
/* 34 */     this(makeEffectList($$0, $$1), $$2);
/*    */   }
/*    */   
/*    */   public FlowerBlock(List<SuspiciousEffectHolder.EffectEntry> $$0, BlockBehaviour.Properties $$1) {
/* 38 */     super($$1);
/* 39 */     this.suspiciousStewEffects = $$0;
/*    */   }
/*    */   
/*    */   protected static List<SuspiciousEffectHolder.EffectEntry> makeEffectList(MobEffect $$0, int $$1) {
/*    */     int $$3;
/* 44 */     if ($$0.isInstantenous()) {
/* 45 */       int $$2 = $$1;
/*    */     } else {
/* 47 */       $$3 = $$1 * 20;
/*    */     } 
/* 49 */     return List.of(new SuspiciousEffectHolder.EffectEntry($$0, $$3));
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 54 */     Vec3 $$4 = $$0.getOffset($$1, $$2);
/* 55 */     return SHAPE.move($$4.x, $$4.y, $$4.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<SuspiciousEffectHolder.EffectEntry> getSuspiciousEffects() {
/* 60 */     return this.suspiciousStewEffects;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FlowerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */