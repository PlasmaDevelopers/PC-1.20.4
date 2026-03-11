/*     */ package net.minecraft;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class BlockUtil
/*     */ {
/*     */   public static class IntBounds {
/*     */     public final int min;
/*     */     public final int max;
/*     */     
/*     */     public IntBounds(int $$0, int $$1) {
/*  22 */       this.min = $$0;
/*  23 */       this.max = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  28 */       return "IntBounds{min=" + this.min + ", max=" + this.max + "}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FoundRectangle
/*     */   {
/*     */     public final BlockPos minCorner;
/*     */     
/*     */     public final int axis1Size;
/*     */     public final int axis2Size;
/*     */     
/*     */     public FoundRectangle(BlockPos $$0, int $$1, int $$2) {
/*  41 */       this.minCorner = $$0;
/*  42 */       this.axis1Size = $$1;
/*  43 */       this.axis2Size = $$2;
/*     */     }
/*     */   }
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
/*     */   public static FoundRectangle getLargestRectangleAround(BlockPos $$0, Direction.Axis $$1, int $$2, Direction.Axis $$3, int $$4, Predicate<BlockPos> $$5) {
/*  61 */     BlockPos.MutableBlockPos $$6 = $$0.mutable();
/*     */     
/*  63 */     Direction $$7 = Direction.get(Direction.AxisDirection.NEGATIVE, $$1);
/*  64 */     Direction $$8 = $$7.getOpposite();
/*     */     
/*  66 */     Direction $$9 = Direction.get(Direction.AxisDirection.NEGATIVE, $$3);
/*  67 */     Direction $$10 = $$9.getOpposite();
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
/*  83 */     int $$11 = getLimit($$5, $$6.set((Vec3i)$$0), $$7, $$2);
/*  84 */     int $$12 = getLimit($$5, $$6.set((Vec3i)$$0), $$8, $$2);
/*     */     
/*  86 */     int $$13 = $$11;
/*  87 */     IntBounds[] $$14 = new IntBounds[$$13 + 1 + $$12];
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
/* 103 */     $$14[$$13] = new IntBounds(
/* 104 */         getLimit($$5, $$6.set((Vec3i)$$0), $$9, $$4), 
/* 105 */         getLimit($$5, $$6.set((Vec3i)$$0), $$10, $$4));
/*     */ 
/*     */     
/* 108 */     int $$15 = ($$14[$$13]).min;
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
/* 128 */     for (int $$16 = 1; $$16 <= $$11; $$16++) {
/* 129 */       IntBounds $$17 = $$14[$$13 - $$16 - 1];
/* 130 */       $$14[$$13 - $$16] = new IntBounds(
/* 131 */           getLimit($$5, $$6.set((Vec3i)$$0).move($$7, $$16), $$9, $$17.min), 
/* 132 */           getLimit($$5, $$6.set((Vec3i)$$0).move($$7, $$16), $$10, $$17.max));
/*     */     } 
/*     */ 
/*     */     
/* 136 */     for (int $$18 = 1; $$18 <= $$12; $$18++) {
/* 137 */       IntBounds $$19 = $$14[$$13 + $$18 - 1];
/* 138 */       $$14[$$13 + $$18] = new IntBounds(
/* 139 */           getLimit($$5, $$6.set((Vec3i)$$0).move($$8, $$18), $$9, $$19.min), 
/* 140 */           getLimit($$5, $$6.set((Vec3i)$$0).move($$8, $$18), $$10, $$19.max));
/*     */     } 
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
/* 158 */     int $$20 = 0;
/* 159 */     int $$21 = 0;
/* 160 */     int $$22 = 0;
/* 161 */     int $$23 = 0;
/*     */     
/* 163 */     int[] $$24 = new int[$$14.length];
/*     */     
/* 165 */     for (int $$25 = $$15; $$25 >= 0; $$25--) {
/* 166 */       for (int $$26 = 0; $$26 < $$14.length; $$26++) {
/* 167 */         IntBounds $$27 = $$14[$$26];
/* 168 */         int $$28 = $$15 - $$27.min;
/* 169 */         int $$29 = $$15 + $$27.max;
/*     */         
/* 171 */         $$24[$$26] = ($$25 >= $$28 && $$25 <= $$29) ? ($$29 + 1 - $$25) : 0;
/*     */       } 
/*     */       
/* 174 */       Pair<IntBounds, Integer> $$30 = getMaxRectangleLocation($$24);
/* 175 */       IntBounds $$31 = (IntBounds)$$30.getFirst();
/* 176 */       int $$32 = 1 + $$31.max - $$31.min;
/* 177 */       int $$33 = ((Integer)$$30.getSecond()).intValue();
/*     */       
/* 179 */       if ($$32 * $$33 > $$22 * $$23) {
/* 180 */         $$20 = $$31.min;
/* 181 */         $$21 = $$25;
/* 182 */         $$22 = $$32;
/* 183 */         $$23 = $$33;
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     return new FoundRectangle($$0
/* 188 */         .relative($$1, $$20 - $$13).relative($$3, $$21 - $$15), $$22, $$23);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getLimit(Predicate<BlockPos> $$0, BlockPos.MutableBlockPos $$1, Direction $$2, int $$3) {
/* 195 */     int $$4 = 0;
/* 196 */     while ($$4 < $$3 && $$0.test($$1.move($$2))) {
/* 197 */       $$4++;
/*     */     }
/* 199 */     return $$4;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static Pair<IntBounds, Integer> getMaxRectangleLocation(int[] $$0) {
/* 204 */     int $$1 = 0;
/* 205 */     int $$2 = 0;
/* 206 */     int $$3 = 0;
/*     */     
/* 208 */     IntArrayList intArrayList = new IntArrayList();
/* 209 */     intArrayList.push(0);
/* 210 */     for (int $$5 = 1; $$5 <= $$0.length; $$5++) {
/* 211 */       int $$6 = ($$5 == $$0.length) ? 0 : $$0[$$5];
/* 212 */       while (!intArrayList.isEmpty()) {
/* 213 */         int $$7 = $$0[intArrayList.topInt()];
/* 214 */         if ($$6 >= $$7) {
/* 215 */           intArrayList.push($$5);
/*     */           
/*     */           break;
/*     */         } 
/* 219 */         intArrayList.popInt();
/* 220 */         int $$8 = intArrayList.isEmpty() ? 0 : (intArrayList.topInt() + 1);
/*     */         
/* 222 */         if ($$7 * ($$5 - $$8) > $$3 * ($$2 - $$1)) {
/* 223 */           $$2 = $$5;
/* 224 */           $$1 = $$8;
/* 225 */           $$3 = $$7;
/*     */         } 
/*     */       } 
/*     */       
/* 229 */       if (intArrayList.isEmpty()) {
/* 230 */         intArrayList.push($$5);
/*     */       }
/*     */     } 
/*     */     
/* 234 */     return new Pair(new IntBounds($$1, $$2 - 1), Integer.valueOf($$3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Optional<BlockPos> getTopConnectedBlock(BlockGetter $$0, BlockPos $$1, Block $$2, Direction $$3, Block $$4) {
/*     */     BlockState $$6;
/* 243 */     BlockPos.MutableBlockPos $$5 = $$1.mutable();
/*     */     
/*     */     do {
/* 246 */       $$5.move($$3);
/* 247 */       $$6 = $$0.getBlockState((BlockPos)$$5);
/* 248 */     } while ($$6.is($$2));
/*     */     
/* 250 */     if ($$6.is($$4)) {
/* 251 */       return (Optional)Optional.of($$5);
/*     */     }
/* 253 */     return Optional.empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\BlockUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */