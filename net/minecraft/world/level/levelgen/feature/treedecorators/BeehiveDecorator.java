/*    */ package net.minecraft.world.level.levelgen.feature.treedecorators;
/*    */ import com.mojang.serialization.Codec;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class BeehiveDecorator extends TreeDecorator {
/*    */   static {
/* 23 */     CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(BeehiveDecorator::new, $$0 -> Float.valueOf($$0.probability)).codec();
/*    */   }
/* 25 */   public static final Codec<BeehiveDecorator> CODEC; private static final Direction WORLDGEN_FACING = Direction.SOUTH; private static final Direction[] SPAWN_DIRECTIONS; private final float probability; static {
/* 26 */     SPAWN_DIRECTIONS = (Direction[])Direction.Plane.HORIZONTAL.stream().filter($$0 -> ($$0 != WORLDGEN_FACING.getOpposite())).toArray($$0 -> new Direction[$$0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public BeehiveDecorator(float $$0) {
/* 31 */     this.probability = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TreeDecoratorType<?> type() {
/* 36 */     return TreeDecoratorType.BEEHIVE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void place(TreeDecorator.Context $$0) {
/* 41 */     RandomSource $$1 = $$0.random();
/*    */     
/* 43 */     if ($$1.nextFloat() >= this.probability) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 50 */     ObjectArrayList<BlockPos> objectArrayList1 = $$0.leaves();
/* 51 */     ObjectArrayList<BlockPos> objectArrayList2 = $$0.logs();
/*    */     
/* 53 */     int $$4 = !objectArrayList1.isEmpty() ? Math.max(((BlockPos)objectArrayList1.get(0)).getY() - 1, ((BlockPos)objectArrayList2.get(0)).getY() + 1) : Math.min(((BlockPos)objectArrayList2.get(0)).getY() + 1 + $$1.nextInt(3), ((BlockPos)objectArrayList2.get(objectArrayList2.size() - 1)).getY());
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     List<BlockPos> $$5 = (List<BlockPos>)objectArrayList2.stream().filter($$1 -> ($$1.getY() == $$0)).flatMap($$0 -> { Objects.requireNonNull($$0); return Stream.<Direction>of(SPAWN_DIRECTIONS).map($$0::relative); }).collect(Collectors.toList());
/* 59 */     if ($$5.isEmpty()) {
/*    */       return;
/*    */     }
/* 62 */     Collections.shuffle($$5);
/*    */ 
/*    */     
/* 65 */     Optional<BlockPos> $$6 = $$5.stream().filter($$1 -> ($$0.isAir($$1) && $$0.isAir($$1.relative(WORLDGEN_FACING)))).findFirst();
/* 66 */     if ($$6.isEmpty()) {
/*    */       return;
/*    */     }
/*    */     
/* 70 */     $$0.setBlock($$6.get(), (BlockState)Blocks.BEE_NEST.defaultBlockState().setValue((Property)BeehiveBlock.FACING, (Comparable)WORLDGEN_FACING));
/* 71 */     $$0.level().getBlockEntity($$6.get(), BlockEntityType.BEEHIVE).ifPresent($$1 -> {
/*    */           int $$2 = 2 + $$0.nextInt(2);
/*    */           for (int $$3 = 0; $$3 < $$2; $$3++) {
/*    */             CompoundTag $$4 = new CompoundTag();
/*    */             $$4.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.BEE).toString());
/*    */             $$1.storeBee($$4, $$0.nextInt(599), false);
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\treedecorators\BeehiveDecorator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */