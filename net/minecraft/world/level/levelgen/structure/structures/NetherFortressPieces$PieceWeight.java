/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PieceWeight
/*    */ {
/*    */   public final Class<? extends NetherFortressPieces.NetherBridgePiece> pieceClass;
/*    */   public final int weight;
/*    */   public int placeCount;
/*    */   public final int maxPlaceCount;
/*    */   public final boolean allowInRow;
/*    */   
/*    */   public PieceWeight(Class<? extends NetherFortressPieces.NetherBridgePiece> $$0, int $$1, int $$2, boolean $$3) {
/* 45 */     this.pieceClass = $$0;
/* 46 */     this.weight = $$1;
/* 47 */     this.maxPlaceCount = $$2;
/* 48 */     this.allowInRow = $$3;
/*    */   }
/*    */   
/*    */   public PieceWeight(Class<? extends NetherFortressPieces.NetherBridgePiece> $$0, int $$1, int $$2) {
/* 52 */     this($$0, $$1, $$2, false);
/*    */   }
/*    */   
/*    */   public boolean doPlace(int $$0) {
/* 56 */     return (this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount);
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 60 */     return (this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\NetherFortressPieces$PieceWeight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */