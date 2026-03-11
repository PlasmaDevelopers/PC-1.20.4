/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
/*    */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArrayVoxelShape
/*    */   extends VoxelShape
/*    */ {
/*    */   private final DoubleList xs;
/*    */   private final DoubleList ys;
/*    */   private final DoubleList zs;
/*    */   
/*    */   protected ArrayVoxelShape(DiscreteVoxelShape $$0, double[] $$1, double[] $$2, double[] $$3) {
/* 19 */     this($$0, 
/*    */         
/* 21 */         (DoubleList)DoubleArrayList.wrap(Arrays.copyOf($$1, $$0.getXSize() + 1)), 
/* 22 */         (DoubleList)DoubleArrayList.wrap(Arrays.copyOf($$2, $$0.getYSize() + 1)), 
/* 23 */         (DoubleList)DoubleArrayList.wrap(Arrays.copyOf($$3, $$0.getZSize() + 1)));
/*    */   }
/*    */ 
/*    */   
/*    */   ArrayVoxelShape(DiscreteVoxelShape $$0, DoubleList $$1, DoubleList $$2, DoubleList $$3) {
/* 28 */     super($$0);
/* 29 */     int $$4 = $$0.getXSize() + 1;
/* 30 */     int $$5 = $$0.getYSize() + 1;
/* 31 */     int $$6 = $$0.getZSize() + 1;
/* 32 */     if ($$4 != $$1.size() || $$5 != $$2.size() || $$6 != $$3.size()) {
/* 33 */       throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape."));
/*    */     }
/* 35 */     this.xs = $$1;
/* 36 */     this.ys = $$2;
/* 37 */     this.zs = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected DoubleList getCoords(Direction.Axis $$0) {
/* 42 */     switch ($$0) {
/*    */       case X:
/* 44 */         return this.xs;
/*    */       case Y:
/* 46 */         return this.ys;
/*    */       case Z:
/* 48 */         return this.zs;
/*    */     } 
/* 50 */     throw new IllegalArgumentException();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\ArrayVoxelShape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */