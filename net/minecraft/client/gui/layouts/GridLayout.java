/*     */ package net.minecraft.client.gui.layouts;
/*     */ 
/*     */ import com.mojang.math.Divisor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class GridLayout
/*     */   extends AbstractLayout {
/*  12 */   private final List<LayoutElement> children = new ArrayList<>();
/*  13 */   private final List<CellInhabitant> cellInhabitants = new ArrayList<>();
/*  14 */   private final LayoutSettings defaultCellSettings = LayoutSettings.defaults();
/*  15 */   private int rowSpacing = 0;
/*  16 */   private int columnSpacing = 0;
/*     */   
/*     */   public GridLayout() {
/*  19 */     this(0, 0);
/*     */   }
/*     */   
/*     */   public GridLayout(int $$0, int $$1) {
/*  23 */     super($$0, $$1, 0, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrangeElements() {
/*  28 */     super.arrangeElements();
/*     */     
/*  30 */     int $$0 = 0;
/*  31 */     int $$1 = 0;
/*     */     
/*  33 */     for (CellInhabitant $$2 : this.cellInhabitants) {
/*  34 */       $$0 = Math.max($$2.getLastOccupiedRow(), $$0);
/*  35 */       $$1 = Math.max($$2.getLastOccupiedColumn(), $$1);
/*     */     } 
/*     */     
/*  38 */     int[] $$3 = new int[$$1 + 1];
/*  39 */     int[] $$4 = new int[$$0 + 1];
/*     */     
/*  41 */     for (CellInhabitant $$5 : this.cellInhabitants) {
/*  42 */       int $$6 = $$5.getHeight() - ($$5.occupiedRows - 1) * this.rowSpacing;
/*  43 */       Divisor $$7 = new Divisor($$6, $$5.occupiedRows);
/*  44 */       for (int $$8 = $$5.row; $$8 <= $$5.getLastOccupiedRow(); $$8++) {
/*  45 */         $$4[$$8] = Math.max($$4[$$8], $$7.nextInt());
/*     */       }
/*  47 */       int $$9 = $$5.getWidth() - ($$5.occupiedColumns - 1) * this.columnSpacing;
/*  48 */       Divisor $$10 = new Divisor($$9, $$5.occupiedColumns);
/*  49 */       for (int $$11 = $$5.column; $$11 <= $$5.getLastOccupiedColumn(); $$11++) {
/*  50 */         $$3[$$11] = Math.max($$3[$$11], $$10.nextInt());
/*     */       }
/*     */     } 
/*     */     
/*  54 */     int[] $$12 = new int[$$1 + 1];
/*  55 */     int[] $$13 = new int[$$0 + 1];
/*     */     
/*  57 */     $$12[0] = 0;
/*  58 */     for (int $$14 = 1; $$14 <= $$1; $$14++) {
/*  59 */       $$12[$$14] = $$12[$$14 - 1] + $$3[$$14 - 1] + this.columnSpacing;
/*     */     }
/*  61 */     $$13[0] = 0;
/*  62 */     for (int $$15 = 1; $$15 <= $$0; $$15++) {
/*  63 */       $$13[$$15] = $$13[$$15 - 1] + $$4[$$15 - 1] + this.rowSpacing;
/*     */     }
/*     */     
/*  66 */     for (CellInhabitant $$16 : this.cellInhabitants) {
/*  67 */       int $$17 = 0;
/*  68 */       for (int $$18 = $$16.column; $$18 <= $$16.getLastOccupiedColumn(); $$18++) {
/*  69 */         $$17 += $$3[$$18];
/*     */       }
/*  71 */       $$17 += this.columnSpacing * ($$16.occupiedColumns - 1);
/*  72 */       $$16.setX(getX() + $$12[$$16.column], $$17);
/*     */       
/*  74 */       int $$19 = 0;
/*  75 */       for (int $$20 = $$16.row; $$20 <= $$16.getLastOccupiedRow(); $$20++) {
/*  76 */         $$19 += $$4[$$20];
/*     */       }
/*  78 */       $$19 += this.rowSpacing * ($$16.occupiedRows - 1);
/*  79 */       $$16.setY(getY() + $$13[$$16.row], $$19);
/*     */     } 
/*     */     
/*  82 */     this.width = $$12[$$1] + $$3[$$1];
/*  83 */     this.height = $$13[$$0] + $$4[$$0];
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, int $$1, int $$2) {
/*  87 */     return addChild($$0, $$1, $$2, newCellSettings());
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, int $$1, int $$2, LayoutSettings $$3) {
/*  91 */     return addChild($$0, $$1, $$2, 1, 1, $$3);
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, int $$1, int $$2, Consumer<LayoutSettings> $$3) {
/*  95 */     return addChild($$0, $$1, $$2, 1, 1, (LayoutSettings)Util.make(newCellSettings(), $$3));
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, int $$1, int $$2, int $$3, int $$4) {
/*  99 */     return addChild($$0, $$1, $$2, $$3, $$4, newCellSettings());
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, int $$1, int $$2, int $$3, int $$4, LayoutSettings $$5) {
/* 103 */     if ($$3 < 1) {
/* 104 */       throw new IllegalArgumentException("Occupied rows must be at least 1");
/*     */     }
/* 106 */     if ($$4 < 1) {
/* 107 */       throw new IllegalArgumentException("Occupied columns must be at least 1");
/*     */     }
/* 109 */     this.cellInhabitants.add(new CellInhabitant((LayoutElement)$$0, $$1, $$2, $$3, $$4, $$5));
/* 110 */     this.children.add((LayoutElement)$$0);
/* 111 */     return $$0;
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, int $$1, int $$2, int $$3, int $$4, Consumer<LayoutSettings> $$5) {
/* 115 */     return addChild($$0, $$1, $$2, $$3, $$4, (LayoutSettings)Util.make(newCellSettings(), $$5));
/*     */   }
/*     */   
/*     */   public GridLayout columnSpacing(int $$0) {
/* 119 */     this.columnSpacing = $$0;
/* 120 */     return this;
/*     */   }
/*     */   
/*     */   public GridLayout rowSpacing(int $$0) {
/* 124 */     this.rowSpacing = $$0;
/* 125 */     return this;
/*     */   }
/*     */   
/*     */   public GridLayout spacing(int $$0) {
/* 129 */     return columnSpacing($$0).rowSpacing($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitChildren(Consumer<LayoutElement> $$0) {
/* 134 */     this.children.forEach($$0);
/*     */   }
/*     */   
/*     */   public LayoutSettings newCellSettings() {
/* 138 */     return this.defaultCellSettings.copy();
/*     */   }
/*     */   
/*     */   public LayoutSettings defaultCellSetting() {
/* 142 */     return this.defaultCellSettings;
/*     */   }
/*     */   
/*     */   public RowHelper createRowHelper(int $$0) {
/* 146 */     return new RowHelper($$0);
/*     */   }
/*     */   
/*     */   private static class CellInhabitant extends AbstractLayout.AbstractChildWrapper {
/*     */     final int row;
/*     */     final int column;
/*     */     final int occupiedRows;
/*     */     final int occupiedColumns;
/*     */     
/*     */     CellInhabitant(LayoutElement $$0, int $$1, int $$2, int $$3, int $$4, LayoutSettings $$5) {
/* 156 */       super($$0, $$5.getExposed());
/* 157 */       this.row = $$1;
/* 158 */       this.column = $$2;
/* 159 */       this.occupiedRows = $$3;
/* 160 */       this.occupiedColumns = $$4;
/*     */     }
/*     */     
/*     */     public int getLastOccupiedRow() {
/* 164 */       return this.row + this.occupiedRows - 1;
/*     */     }
/*     */     
/*     */     public int getLastOccupiedColumn() {
/* 168 */       return this.column + this.occupiedColumns - 1;
/*     */     }
/*     */   }
/*     */   
/*     */   public final class RowHelper {
/*     */     private final int columns;
/*     */     private int index;
/*     */     
/*     */     RowHelper(int $$1) {
/* 177 */       this.columns = $$1;
/*     */     }
/*     */     
/*     */     public <T extends LayoutElement> T addChild(T $$0) {
/* 181 */       return addChild($$0, 1);
/*     */     }
/*     */     
/*     */     public <T extends LayoutElement> T addChild(T $$0, int $$1) {
/* 185 */       return addChild($$0, $$1, defaultCellSetting());
/*     */     }
/*     */     
/*     */     public <T extends LayoutElement> T addChild(T $$0, LayoutSettings $$1) {
/* 189 */       return addChild($$0, 1, $$1);
/*     */     }
/*     */     
/*     */     public <T extends LayoutElement> T addChild(T $$0, int $$1, LayoutSettings $$2) {
/* 193 */       int $$3 = this.index / this.columns;
/* 194 */       int $$4 = this.index % this.columns;
/*     */       
/* 196 */       if ($$4 + $$1 > this.columns) {
/* 197 */         $$3++;
/* 198 */         $$4 = 0;
/* 199 */         this.index = Mth.roundToward(this.index, this.columns);
/*     */       } 
/* 201 */       this.index += $$1;
/*     */       
/* 203 */       return GridLayout.this.addChild($$0, $$3, $$4, 1, $$1, $$2);
/*     */     }
/*     */     
/*     */     public GridLayout getGrid() {
/* 207 */       return GridLayout.this;
/*     */     }
/*     */     
/*     */     public LayoutSettings newCellSettings() {
/* 211 */       return GridLayout.this.newCellSettings();
/*     */     }
/*     */     
/*     */     public LayoutSettings defaultCellSetting() {
/* 215 */       return GridLayout.this.defaultCellSetting();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\GridLayout.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */