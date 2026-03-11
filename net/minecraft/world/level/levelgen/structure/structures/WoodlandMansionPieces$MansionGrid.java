/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.Tuple;
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
/*     */ class MansionGrid
/*     */ {
/*     */   private static final int DEFAULT_SIZE = 11;
/*     */   private static final int CLEAR = 0;
/*     */   private static final int CORRIDOR = 1;
/*     */   private static final int ROOM = 2;
/*     */   private static final int START_ROOM = 3;
/*     */   private static final int TEST_ROOM = 4;
/*     */   private static final int BLOCKED = 5;
/*     */   private static final int ROOM_1x1 = 65536;
/*     */   private static final int ROOM_1x2 = 131072;
/*     */   private static final int ROOM_2x2 = 262144;
/*     */   private static final int ROOM_ORIGIN_FLAG = 1048576;
/*     */   private static final int ROOM_DOOR_FLAG = 2097152;
/*     */   private static final int ROOM_STAIRS_FLAG = 4194304;
/*     */   private static final int ROOM_CORRIDOR_FLAG = 8388608;
/*     */   private static final int ROOM_TYPE_MASK = 983040;
/*     */   private static final int ROOM_ID_MASK = 65535;
/*     */   private final RandomSource random;
/*     */   final WoodlandMansionPieces.SimpleGrid baseGrid;
/*     */   final WoodlandMansionPieces.SimpleGrid thirdFloorGrid;
/*     */   final WoodlandMansionPieces.SimpleGrid[] floorRooms;
/*     */   final int entranceX;
/*     */   final int entranceY;
/*     */   
/*     */   public MansionGrid(RandomSource $$0) {
/* 720 */     this.random = $$0;
/*     */     
/* 722 */     int $$1 = 11;
/* 723 */     this.entranceX = 7;
/* 724 */     this.entranceY = 4;
/*     */     
/* 726 */     this.baseGrid = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
/* 727 */     this.baseGrid.set(this.entranceX, this.entranceY, this.entranceX + 1, this.entranceY + 1, 3);
/* 728 */     this.baseGrid.set(this.entranceX - 1, this.entranceY, this.entranceX - 1, this.entranceY + 1, 2);
/* 729 */     this.baseGrid.set(this.entranceX + 2, this.entranceY - 2, this.entranceX + 3, this.entranceY + 3, 5);
/* 730 */     this.baseGrid.set(this.entranceX + 1, this.entranceY - 2, this.entranceX + 1, this.entranceY - 1, 1);
/* 731 */     this.baseGrid.set(this.entranceX + 1, this.entranceY + 2, this.entranceX + 1, this.entranceY + 3, 1);
/* 732 */     this.baseGrid.set(this.entranceX - 1, this.entranceY - 1, 1);
/* 733 */     this.baseGrid.set(this.entranceX - 1, this.entranceY + 2, 1);
/*     */     
/* 735 */     this.baseGrid.set(0, 0, 11, 1, 5);
/* 736 */     this.baseGrid.set(0, 9, 11, 11, 5);
/*     */     
/* 738 */     recursiveCorridor(this.baseGrid, this.entranceX, this.entranceY - 2, Direction.WEST, 6);
/* 739 */     recursiveCorridor(this.baseGrid, this.entranceX, this.entranceY + 3, Direction.WEST, 6);
/* 740 */     recursiveCorridor(this.baseGrid, this.entranceX - 2, this.entranceY - 1, Direction.WEST, 3);
/* 741 */     recursiveCorridor(this.baseGrid, this.entranceX - 2, this.entranceY + 2, Direction.WEST, 3);
/* 742 */     while (cleanEdges(this.baseGrid));
/*     */ 
/*     */     
/* 745 */     this.floorRooms = new WoodlandMansionPieces.SimpleGrid[3];
/* 746 */     this.floorRooms[0] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
/* 747 */     this.floorRooms[1] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
/* 748 */     this.floorRooms[2] = new WoodlandMansionPieces.SimpleGrid(11, 11, 5);
/* 749 */     identifyRooms(this.baseGrid, this.floorRooms[0]);
/* 750 */     identifyRooms(this.baseGrid, this.floorRooms[1]);
/*     */ 
/*     */     
/* 753 */     this.floorRooms[0].set(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 8388608);
/* 754 */     this.floorRooms[1].set(this.entranceX + 1, this.entranceY, this.entranceX + 1, this.entranceY + 1, 8388608);
/*     */     
/* 756 */     this.thirdFloorGrid = new WoodlandMansionPieces.SimpleGrid(this.baseGrid.width, this.baseGrid.height, 5);
/* 757 */     setupThirdFloor();
/* 758 */     identifyRooms(this.thirdFloorGrid, this.floorRooms[2]);
/*     */   }
/*     */   
/*     */   public static boolean isHouse(WoodlandMansionPieces.SimpleGrid $$0, int $$1, int $$2) {
/* 762 */     int $$3 = $$0.get($$1, $$2);
/* 763 */     return ($$3 == 1 || $$3 == 2 || $$3 == 3 || $$3 == 4);
/*     */   }
/*     */   
/*     */   public boolean isRoomId(WoodlandMansionPieces.SimpleGrid $$0, int $$1, int $$2, int $$3, int $$4) {
/* 767 */     return ((this.floorRooms[$$3].get($$1, $$2) & 0xFFFF) == $$4);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Direction get1x2RoomDirection(WoodlandMansionPieces.SimpleGrid $$0, int $$1, int $$2, int $$3, int $$4) {
/* 772 */     for (Direction $$5 : Direction.Plane.HORIZONTAL) {
/* 773 */       if (isRoomId($$0, $$1 + $$5.getStepX(), $$2 + $$5.getStepZ(), $$3, $$4)) {
/* 774 */         return $$5;
/*     */       }
/*     */     } 
/* 777 */     return null;
/*     */   }
/*     */   
/*     */   private void recursiveCorridor(WoodlandMansionPieces.SimpleGrid $$0, int $$1, int $$2, Direction $$3, int $$4) {
/* 781 */     if ($$4 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 785 */     $$0.set($$1, $$2, 1);
/* 786 */     $$0.setif($$1 + $$3.getStepX(), $$2 + $$3.getStepZ(), 0, 1);
/*     */     
/* 788 */     for (int $$5 = 0; $$5 < 8; $$5++) {
/* 789 */       Direction $$6 = Direction.from2DDataValue(this.random.nextInt(4));
/* 790 */       if ($$6 != $$3.getOpposite())
/*     */       {
/*     */         
/* 793 */         if ($$6 != Direction.EAST || !this.random.nextBoolean()) {
/*     */ 
/*     */ 
/*     */           
/* 797 */           int $$7 = $$1 + $$3.getStepX();
/* 798 */           int $$8 = $$2 + $$3.getStepZ();
/* 799 */           if ($$0.get($$7 + $$6.getStepX(), $$8 + $$6.getStepZ()) == 0 && $$0.get($$7 + $$6.getStepX() * 2, $$8 + $$6.getStepZ() * 2) == 0) {
/* 800 */             recursiveCorridor($$0, $$1 + $$3.getStepX() + $$6.getStepX(), $$2 + $$3.getStepZ() + $$6.getStepZ(), $$6, $$4 - 1); break;
/*     */           } 
/*     */         }  } 
/*     */     } 
/* 804 */     Direction $$9 = $$3.getClockWise();
/* 805 */     Direction $$10 = $$3.getCounterClockWise();
/* 806 */     $$0.setif($$1 + $$9.getStepX(), $$2 + $$9.getStepZ(), 0, 2);
/* 807 */     $$0.setif($$1 + $$10.getStepX(), $$2 + $$10.getStepZ(), 0, 2);
/*     */     
/* 809 */     $$0.setif($$1 + $$3.getStepX() + $$9.getStepX(), $$2 + $$3.getStepZ() + $$9.getStepZ(), 0, 2);
/* 810 */     $$0.setif($$1 + $$3.getStepX() + $$10.getStepX(), $$2 + $$3.getStepZ() + $$10.getStepZ(), 0, 2);
/* 811 */     $$0.setif($$1 + $$3.getStepX() * 2, $$2 + $$3.getStepZ() * 2, 0, 2);
/* 812 */     $$0.setif($$1 + $$9.getStepX() * 2, $$2 + $$9.getStepZ() * 2, 0, 2);
/* 813 */     $$0.setif($$1 + $$10.getStepX() * 2, $$2 + $$10.getStepZ() * 2, 0, 2);
/*     */   }
/*     */   
/*     */   private boolean cleanEdges(WoodlandMansionPieces.SimpleGrid $$0) {
/* 817 */     boolean $$1 = false;
/* 818 */     for (int $$2 = 0; $$2 < $$0.height; $$2++) {
/* 819 */       for (int $$3 = 0; $$3 < $$0.width; $$3++) {
/* 820 */         if ($$0.get($$3, $$2) == 0) {
/* 821 */           int $$4 = 0;
/* 822 */           $$4 += isHouse($$0, $$3 + 1, $$2) ? 1 : 0;
/* 823 */           $$4 += isHouse($$0, $$3 - 1, $$2) ? 1 : 0;
/* 824 */           $$4 += isHouse($$0, $$3, $$2 + 1) ? 1 : 0;
/* 825 */           $$4 += isHouse($$0, $$3, $$2 - 1) ? 1 : 0;
/*     */           
/* 827 */           if ($$4 >= 3) {
/*     */             
/* 829 */             $$0.set($$3, $$2, 2);
/* 830 */             $$1 = true;
/* 831 */           } else if ($$4 == 2) {
/*     */             
/* 833 */             int $$5 = 0;
/* 834 */             $$5 += isHouse($$0, $$3 + 1, $$2 + 1) ? 1 : 0;
/* 835 */             $$5 += isHouse($$0, $$3 - 1, $$2 + 1) ? 1 : 0;
/* 836 */             $$5 += isHouse($$0, $$3 + 1, $$2 - 1) ? 1 : 0;
/* 837 */             $$5 += isHouse($$0, $$3 - 1, $$2 - 1) ? 1 : 0;
/* 838 */             if ($$5 <= 1) {
/* 839 */               $$0.set($$3, $$2, 2);
/* 840 */               $$1 = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 846 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupThirdFloor() {
/* 851 */     List<Tuple<Integer, Integer>> $$0 = Lists.newArrayList();
/* 852 */     WoodlandMansionPieces.SimpleGrid $$1 = this.floorRooms[1];
/* 853 */     for (int $$2 = 0; $$2 < this.thirdFloorGrid.height; $$2++) {
/* 854 */       for (int $$3 = 0; $$3 < this.thirdFloorGrid.width; $$3++) {
/* 855 */         int $$4 = $$1.get($$3, $$2);
/* 856 */         int $$5 = $$4 & 0xF0000;
/* 857 */         if ($$5 == 131072 && ($$4 & 0x200000) == 2097152) {
/* 858 */           $$0.add(new Tuple(Integer.valueOf($$3), Integer.valueOf($$2)));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 863 */     if ($$0.isEmpty()) {
/*     */       
/* 865 */       this.thirdFloorGrid.set(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
/*     */       
/*     */       return;
/*     */     } 
/* 869 */     Tuple<Integer, Integer> $$6 = $$0.get(this.random.nextInt($$0.size()));
/* 870 */     int $$7 = $$1.get(((Integer)$$6.getA()).intValue(), ((Integer)$$6.getB()).intValue());
/* 871 */     $$1.set(((Integer)$$6.getA()).intValue(), ((Integer)$$6.getB()).intValue(), $$7 | 0x400000);
/* 872 */     Direction $$8 = get1x2RoomDirection(this.baseGrid, ((Integer)$$6.getA()).intValue(), ((Integer)$$6.getB()).intValue(), 1, $$7 & 0xFFFF);
/* 873 */     int $$9 = ((Integer)$$6.getA()).intValue() + $$8.getStepX();
/* 874 */     int $$10 = ((Integer)$$6.getB()).intValue() + $$8.getStepZ();
/*     */     
/* 876 */     for (int $$11 = 0; $$11 < this.thirdFloorGrid.height; $$11++) {
/* 877 */       for (int $$12 = 0; $$12 < this.thirdFloorGrid.width; $$12++) {
/* 878 */         if (!isHouse(this.baseGrid, $$12, $$11)) {
/* 879 */           this.thirdFloorGrid.set($$12, $$11, 5);
/* 880 */         } else if ($$12 == ((Integer)$$6.getA()).intValue() && $$11 == ((Integer)$$6.getB()).intValue()) {
/* 881 */           this.thirdFloorGrid.set($$12, $$11, 3);
/* 882 */         } else if ($$12 == $$9 && $$11 == $$10) {
/* 883 */           this.thirdFloorGrid.set($$12, $$11, 3);
/* 884 */           this.floorRooms[2].set($$12, $$11, 8388608);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 889 */     List<Direction> $$13 = Lists.newArrayList();
/* 890 */     for (Direction $$14 : Direction.Plane.HORIZONTAL) {
/* 891 */       if (this.thirdFloorGrid.get($$9 + $$14.getStepX(), $$10 + $$14.getStepZ()) == 0) {
/* 892 */         $$13.add($$14);
/*     */       }
/*     */     } 
/*     */     
/* 896 */     if ($$13.isEmpty()) {
/*     */       
/* 898 */       this.thirdFloorGrid.set(0, 0, this.thirdFloorGrid.width, this.thirdFloorGrid.height, 5);
/* 899 */       $$1.set(((Integer)$$6.getA()).intValue(), ((Integer)$$6.getB()).intValue(), $$7);
/*     */       return;
/*     */     } 
/* 902 */     Direction $$15 = $$13.get(this.random.nextInt($$13.size()));
/* 903 */     recursiveCorridor(this.thirdFloorGrid, $$9 + $$15.getStepX(), $$10 + $$15.getStepZ(), $$15, 4);
/* 904 */     while (cleanEdges(this.thirdFloorGrid));
/*     */   }
/*     */ 
/*     */   
/*     */   private void identifyRooms(WoodlandMansionPieces.SimpleGrid $$0, WoodlandMansionPieces.SimpleGrid $$1) {
/* 909 */     ObjectArrayList<Tuple<Integer, Integer>> $$2 = new ObjectArrayList();
/* 910 */     for (int $$3 = 0; $$3 < $$0.height; $$3++) {
/* 911 */       for (int $$4 = 0; $$4 < $$0.width; $$4++) {
/* 912 */         if ($$0.get($$4, $$3) == 2) {
/* 913 */           $$2.add(new Tuple(Integer.valueOf($$4), Integer.valueOf($$3)));
/*     */         }
/*     */       } 
/*     */     } 
/* 917 */     Util.shuffle((List)$$2, this.random);
/*     */     
/* 919 */     int $$5 = 10;
/* 920 */     for (ObjectListIterator<Tuple<Integer, Integer>> objectListIterator = $$2.iterator(); objectListIterator.hasNext(); ) { Tuple<Integer, Integer> $$6 = objectListIterator.next();
/* 921 */       int $$7 = ((Integer)$$6.getA()).intValue();
/* 922 */       int $$8 = ((Integer)$$6.getB()).intValue();
/*     */       
/* 924 */       if ($$1.get($$7, $$8) == 0) {
/* 925 */         int $$9 = $$7;
/* 926 */         int $$10 = $$7;
/* 927 */         int $$11 = $$8;
/* 928 */         int $$12 = $$8;
/* 929 */         int $$13 = 65536;
/* 930 */         if ($$1.get($$7 + 1, $$8) == 0 && $$1.get($$7, $$8 + 1) == 0 && $$1.get($$7 + 1, $$8 + 1) == 0 && $$0
/* 931 */           .get($$7 + 1, $$8) == 2 && $$0.get($$7, $$8 + 1) == 2 && $$0.get($$7 + 1, $$8 + 1) == 2) {
/*     */           
/* 933 */           $$10++;
/* 934 */           $$12++;
/* 935 */           $$13 = 262144;
/* 936 */         } else if ($$1.get($$7 - 1, $$8) == 0 && $$1.get($$7, $$8 + 1) == 0 && $$1.get($$7 - 1, $$8 + 1) == 0 && $$0
/* 937 */           .get($$7 - 1, $$8) == 2 && $$0.get($$7, $$8 + 1) == 2 && $$0.get($$7 - 1, $$8 + 1) == 2) {
/*     */           
/* 939 */           $$9--;
/* 940 */           $$12++;
/* 941 */           $$13 = 262144;
/* 942 */         } else if ($$1.get($$7 - 1, $$8) == 0 && $$1.get($$7, $$8 - 1) == 0 && $$1.get($$7 - 1, $$8 - 1) == 0 && $$0
/* 943 */           .get($$7 - 1, $$8) == 2 && $$0.get($$7, $$8 - 1) == 2 && $$0.get($$7 - 1, $$8 - 1) == 2) {
/*     */           
/* 945 */           $$9--;
/* 946 */           $$11--;
/* 947 */           $$13 = 262144;
/* 948 */         } else if ($$1.get($$7 + 1, $$8) == 0 && $$0.get($$7 + 1, $$8) == 2) {
/* 949 */           $$10++;
/* 950 */           $$13 = 131072;
/* 951 */         } else if ($$1.get($$7, $$8 + 1) == 0 && $$0.get($$7, $$8 + 1) == 2) {
/* 952 */           $$12++;
/* 953 */           $$13 = 131072;
/* 954 */         } else if ($$1.get($$7 - 1, $$8) == 0 && $$0.get($$7 - 1, $$8) == 2) {
/* 955 */           $$9--;
/* 956 */           $$13 = 131072;
/* 957 */         } else if ($$1.get($$7, $$8 - 1) == 0 && $$0.get($$7, $$8 - 1) == 2) {
/* 958 */           $$11--;
/* 959 */           $$13 = 131072;
/*     */         } 
/*     */ 
/*     */         
/* 963 */         int $$14 = this.random.nextBoolean() ? $$9 : $$10;
/* 964 */         int $$15 = this.random.nextBoolean() ? $$11 : $$12;
/* 965 */         int $$16 = 2097152;
/* 966 */         if (!$$0.edgesTo($$14, $$15, 1)) {
/* 967 */           $$14 = ($$14 == $$9) ? $$10 : $$9;
/* 968 */           $$15 = ($$15 == $$11) ? $$12 : $$11;
/* 969 */           if (!$$0.edgesTo($$14, $$15, 1)) {
/* 970 */             $$15 = ($$15 == $$11) ? $$12 : $$11;
/* 971 */             if (!$$0.edgesTo($$14, $$15, 1)) {
/* 972 */               $$14 = ($$14 == $$9) ? $$10 : $$9;
/* 973 */               $$15 = ($$15 == $$11) ? $$12 : $$11;
/* 974 */               if (!$$0.edgesTo($$14, $$15, 1)) {
/*     */                 
/* 976 */                 $$16 = 0;
/* 977 */                 $$14 = $$9;
/* 978 */                 $$15 = $$11;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 983 */         for (int $$17 = $$11; $$17 <= $$12; $$17++) {
/* 984 */           for (int $$18 = $$9; $$18 <= $$10; $$18++) {
/* 985 */             if ($$18 == $$14 && $$17 == $$15) {
/* 986 */               $$1.set($$18, $$17, 0x100000 | $$16 | $$13 | $$5);
/*     */             } else {
/* 988 */               $$1.set($$18, $$17, $$13 | $$5);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 993 */         $$5++;
/*     */       }  }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\WoodlandMansionPieces$MansionGrid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */