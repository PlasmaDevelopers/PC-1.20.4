/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ public enum FaceInfo {
/*  7 */   DOWN(new VertexInfo[] { new VertexInfo(Constants.MIN_X, Constants.MIN_Y, Constants.MAX_Z), new VertexInfo(Constants.MIN_X, Constants.MIN_Y, Constants.MIN_Z), new VertexInfo(Constants.MAX_X, Constants.MIN_Y, Constants.MIN_Z), new VertexInfo(Constants.MAX_X, Constants.MIN_Y, Constants.MAX_Z)
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 12 */   UP(new VertexInfo[] { new VertexInfo(Constants.MIN_X, Constants.MAX_Y, Constants.MIN_Z), new VertexInfo(Constants.MIN_X, Constants.MAX_Y, Constants.MAX_Z), new VertexInfo(Constants.MAX_X, Constants.MAX_Y, Constants.MAX_Z), new VertexInfo(Constants.MAX_X, Constants.MAX_Y, Constants.MIN_Z)
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 17 */   NORTH(new VertexInfo[] { new VertexInfo(Constants.MAX_X, Constants.MAX_Y, Constants.MIN_Z), new VertexInfo(Constants.MAX_X, Constants.MIN_Y, Constants.MIN_Z), new VertexInfo(Constants.MIN_X, Constants.MIN_Y, Constants.MIN_Z), new VertexInfo(Constants.MIN_X, Constants.MAX_Y, Constants.MIN_Z)
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 22 */   SOUTH(new VertexInfo[] { new VertexInfo(Constants.MIN_X, Constants.MAX_Y, Constants.MAX_Z), new VertexInfo(Constants.MIN_X, Constants.MIN_Y, Constants.MAX_Z), new VertexInfo(Constants.MAX_X, Constants.MIN_Y, Constants.MAX_Z), new VertexInfo(Constants.MAX_X, Constants.MAX_Y, Constants.MAX_Z)
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 27 */   WEST(new VertexInfo[] { new VertexInfo(Constants.MIN_X, Constants.MAX_Y, Constants.MIN_Z), new VertexInfo(Constants.MIN_X, Constants.MIN_Y, Constants.MIN_Z), new VertexInfo(Constants.MIN_X, Constants.MIN_Y, Constants.MAX_Z), new VertexInfo(Constants.MIN_X, Constants.MAX_Y, Constants.MAX_Z)
/*    */ 
/*    */ 
/*    */     
/*    */     }),
/* 32 */   EAST(new VertexInfo[] { new VertexInfo(Constants.MAX_X, Constants.MAX_Y, Constants.MAX_Z), new VertexInfo(Constants.MAX_X, Constants.MIN_Y, Constants.MAX_Z), new VertexInfo(Constants.MAX_X, Constants.MIN_Y, Constants.MIN_Z), new VertexInfo(Constants.MAX_X, Constants.MAX_Y, Constants.MIN_Z) });
/*    */   
/*    */   private static final FaceInfo[] BY_FACING;
/*    */   
/*    */   private final VertexInfo[] infos;
/*    */ 
/*    */   
/*    */   public static final class Constants
/*    */   {
/* 41 */     public static final int MAX_Z = Direction.SOUTH.get3DDataValue();
/* 42 */     public static final int MAX_Y = Direction.UP.get3DDataValue();
/* 43 */     public static final int MAX_X = Direction.EAST.get3DDataValue();
/* 44 */     public static final int MIN_Z = Direction.NORTH.get3DDataValue();
/* 45 */     public static final int MIN_Y = Direction.DOWN.get3DDataValue();
/* 46 */     public static final int MIN_X = Direction.WEST.get3DDataValue(); }
/*    */   
/*    */   static {
/* 49 */     BY_FACING = (FaceInfo[])Util.make(new FaceInfo[6], $$0 -> {
/*    */           $$0[Constants.MIN_Y] = DOWN;
/*    */           $$0[Constants.MAX_Y] = UP;
/*    */           $$0[Constants.MIN_Z] = NORTH;
/*    */           $$0[Constants.MAX_Z] = SOUTH;
/*    */           $$0[Constants.MIN_X] = WEST;
/*    */           $$0[Constants.MAX_X] = EAST;
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public static FaceInfo fromFacing(Direction $$0) {
/* 61 */     return BY_FACING[$$0.get3DDataValue()];
/*    */   }
/*    */   
/*    */   FaceInfo(VertexInfo... $$0) {
/* 65 */     this.infos = $$0;
/*    */   }
/*    */   
/*    */   public VertexInfo getVertexInfo(int $$0) {
/* 69 */     return this.infos[$$0];
/*    */   }
/*    */   
/*    */   public static class VertexInfo {
/*    */     public final int xFace;
/*    */     public final int yFace;
/*    */     public final int zFace;
/*    */     
/*    */     VertexInfo(int $$0, int $$1, int $$2) {
/* 78 */       this.xFace = $$0;
/* 79 */       this.yFace = $$1;
/* 80 */       this.zFace = $$2;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\FaceInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */