/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.SegmentedAnglePrecision;
/*    */ 
/*    */ public class RotationSegment
/*    */ {
/*  9 */   private static final SegmentedAnglePrecision SEGMENTED_ANGLE16 = new SegmentedAnglePrecision(4);
/*    */   
/* 11 */   private static final int MAX_SEGMENT_INDEX = SEGMENTED_ANGLE16.getMask();
/*    */   
/*    */   private static final int NORTH_0 = 0;
/*    */   private static final int EAST_90 = 4;
/*    */   private static final int SOUTH_180 = 8;
/*    */   private static final int WEST_270 = 12;
/*    */   
/*    */   public static int getMaxSegmentIndex() {
/* 19 */     return MAX_SEGMENT_INDEX;
/*    */   }
/*    */   
/*    */   public static int convertToSegment(Direction $$0) {
/* 23 */     return SEGMENTED_ANGLE16.fromDirection($$0);
/*    */   }
/*    */   
/*    */   public static int convertToSegment(float $$0) {
/* 27 */     return SEGMENTED_ANGLE16.fromDegrees($$0);
/*    */   }
/*    */   
/*    */   public static Optional<Direction> convertToDirection(int $$0) {
/* 31 */     switch ($$0) { case 0: 
/*    */       case 4: 
/*    */       case 8:
/*    */       
/*    */       case 12:
/* 36 */        }  Direction $$1 = null;
/*    */ 
/*    */     
/* 39 */     return Optional.ofNullable($$1);
/*    */   }
/*    */   
/*    */   public static float convertToDegrees(int $$0) {
/* 43 */     return SEGMENTED_ANGLE16.toDegrees($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\RotationSegment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */