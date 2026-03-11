/*     */ package net.minecraft.client.gui.layouts;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LinearLayout
/*     */   implements Layout
/*     */ {
/*     */   private final GridLayout wrapped;
/*     */   private final Orientation orientation;
/*  13 */   private int nextChildIndex = 0;
/*     */   
/*     */   private LinearLayout(Orientation $$0) {
/*  16 */     this(0, 0, $$0);
/*     */   }
/*     */   
/*     */   public LinearLayout(int $$0, int $$1, Orientation $$2) {
/*  20 */     this.wrapped = new GridLayout($$0, $$1);
/*  21 */     this.orientation = $$2;
/*     */   }
/*     */   
/*     */   public LinearLayout spacing(int $$0) {
/*  25 */     this.orientation.setSpacing(this.wrapped, $$0);
/*  26 */     return this;
/*     */   }
/*     */   
/*     */   public LayoutSettings newCellSettings() {
/*  30 */     return this.wrapped.newCellSettings();
/*     */   }
/*     */   
/*     */   public LayoutSettings defaultCellSetting() {
/*  34 */     return this.wrapped.defaultCellSetting();
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, LayoutSettings $$1) {
/*  38 */     return this.orientation.addChild(this.wrapped, $$0, this.nextChildIndex++, $$1);
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0) {
/*  42 */     return addChild($$0, newCellSettings());
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, Consumer<LayoutSettings> $$1) {
/*  46 */     return this.orientation.addChild(this.wrapped, $$0, this.nextChildIndex++, (LayoutSettings)Util.make(newCellSettings(), $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitChildren(Consumer<LayoutElement> $$0) {
/*  51 */     this.wrapped.visitChildren($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrangeElements() {
/*  56 */     this.wrapped.arrangeElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  61 */     return this.wrapped.getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  66 */     return this.wrapped.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setX(int $$0) {
/*  71 */     this.wrapped.setX($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setY(int $$0) {
/*  76 */     this.wrapped.setY($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getX() {
/*  81 */     return this.wrapped.getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getY() {
/*  86 */     return this.wrapped.getY();
/*     */   }
/*     */   
/*     */   public static LinearLayout vertical() {
/*  90 */     return new LinearLayout(Orientation.VERTICAL);
/*     */   }
/*     */   
/*     */   public static LinearLayout horizontal() {
/*  94 */     return new LinearLayout(Orientation.HORIZONTAL);
/*     */   }
/*     */   
/*     */   public enum Orientation {
/*  98 */     HORIZONTAL, VERTICAL;
/*     */     
/*     */     void setSpacing(GridLayout $$0, int $$1) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/LinearLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$LinearLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 51, 1 -> 36, 2 -> 45
/*     */       //   36: aload_1
/*     */       //   37: iload_2
/*     */       //   38: invokevirtual columnSpacing : (I)Lnet/minecraft/client/gui/layouts/GridLayout;
/*     */       //   41: pop
/*     */       //   42: goto -> 51
/*     */       //   45: aload_1
/*     */       //   46: iload_2
/*     */       //   47: invokevirtual rowSpacing : (I)Lnet/minecraft/client/gui/layouts/GridLayout;
/*     */       //   50: pop
/*     */       //   51: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #101	-> 0
/*     */       //   #102	-> 36
/*     */       //   #103	-> 45
/*     */       //   #105	-> 51
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	52	0	this	Lnet/minecraft/client/gui/layouts/LinearLayout$Orientation;
/*     */       //   0	52	1	$$0	Lnet/minecraft/client/gui/layouts/GridLayout;
/*     */       //   0	52	2	$$1	I
/*     */     }
/*     */     
/*     */     public <T extends LayoutElement> T addChild(GridLayout $$0, T $$1, int $$2, LayoutSettings $$3) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/LinearLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$LinearLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 36, 1 -> 44, 2 -> 56
/*     */       //   36: new java/lang/IncompatibleClassChangeError
/*     */       //   39: dup
/*     */       //   40: invokespecial <init> : ()V
/*     */       //   43: athrow
/*     */       //   44: aload_1
/*     */       //   45: aload_2
/*     */       //   46: iconst_0
/*     */       //   47: iload_3
/*     */       //   48: aload #4
/*     */       //   50: invokevirtual addChild : (Lnet/minecraft/client/gui/layouts/LayoutElement;IILnet/minecraft/client/gui/layouts/LayoutSettings;)Lnet/minecraft/client/gui/layouts/LayoutElement;
/*     */       //   53: goto -> 65
/*     */       //   56: aload_1
/*     */       //   57: aload_2
/*     */       //   58: iload_3
/*     */       //   59: iconst_0
/*     */       //   60: aload #4
/*     */       //   62: invokevirtual addChild : (Lnet/minecraft/client/gui/layouts/LayoutElement;IILnet/minecraft/client/gui/layouts/LayoutSettings;)Lnet/minecraft/client/gui/layouts/LayoutElement;
/*     */       //   65: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #108	-> 0
/*     */       //   #109	-> 44
/*     */       //   #110	-> 56
/*     */       //   #108	-> 65
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	66	0	this	Lnet/minecraft/client/gui/layouts/LinearLayout$Orientation;
/*     */       //   0	66	1	$$0	Lnet/minecraft/client/gui/layouts/GridLayout;
/*     */       //   0	66	2	$$1	Lnet/minecraft/client/gui/layouts/LayoutElement;
/*     */       //   0	66	3	$$2	I
/*     */       //   0	66	4	$$3	Lnet/minecraft/client/gui/layouts/LayoutSettings;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	66	2	$$1	TT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\LinearLayout.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */