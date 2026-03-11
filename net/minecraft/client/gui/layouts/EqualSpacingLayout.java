/*     */ package net.minecraft.client.gui.layouts;
/*     */ 
/*     */ import com.mojang.math.Divisor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EqualSpacingLayout
/*     */   extends AbstractLayout
/*     */ {
/*     */   private final Orientation orientation;
/*  21 */   private final List<ChildContainer> children = new ArrayList<>();
/*     */   
/*  23 */   private final LayoutSettings defaultChildLayoutSettings = LayoutSettings.defaults();
/*     */   
/*     */   public EqualSpacingLayout(int $$0, int $$1, Orientation $$2) {
/*  26 */     this(0, 0, $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public EqualSpacingLayout(int $$0, int $$1, int $$2, int $$3, Orientation $$4) {
/*  30 */     super($$0, $$1, $$2, $$3);
/*  31 */     this.orientation = $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void arrangeElements() {
/*  36 */     super.arrangeElements();
/*     */     
/*  38 */     if (this.children.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  42 */     int $$0 = 0;
/*  43 */     int $$1 = this.orientation.getSecondaryLength(this);
/*     */     
/*  45 */     for (ChildContainer $$2 : this.children) {
/*  46 */       $$0 += this.orientation.getPrimaryLength($$2);
/*  47 */       $$1 = Math.max($$1, this.orientation.getSecondaryLength($$2));
/*     */     } 
/*     */     
/*  50 */     int $$3 = this.orientation.getPrimaryLength(this) - $$0;
/*     */     
/*  52 */     int $$4 = this.orientation.getPrimaryPosition(this);
/*     */     
/*  54 */     Iterator<ChildContainer> $$5 = this.children.iterator();
/*  55 */     ChildContainer $$6 = $$5.next();
/*  56 */     this.orientation.setPrimaryPosition($$6, $$4);
/*  57 */     $$4 += this.orientation.getPrimaryLength($$6);
/*  58 */     if (this.children.size() >= 2) {
/*  59 */       Divisor $$7 = new Divisor($$3, this.children.size() - 1);
/*  60 */       while ($$7.hasNext()) {
/*  61 */         $$4 += $$7.nextInt();
/*  62 */         ChildContainer $$8 = $$5.next();
/*  63 */         this.orientation.setPrimaryPosition($$8, $$4);
/*  64 */         $$4 += this.orientation.getPrimaryLength($$8);
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     int $$9 = this.orientation.getSecondaryPosition(this);
/*  69 */     for (ChildContainer $$10 : this.children) {
/*  70 */       this.orientation.setSecondaryPosition($$10, $$9, $$1);
/*     */     }
/*     */     
/*  73 */     switch (this.orientation) { case HORIZONTAL:
/*  74 */         this.height = $$1; break;
/*  75 */       case VERTICAL: this.width = $$1;
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   public void visitChildren(Consumer<LayoutElement> $$0) {
/*  81 */     this.children.forEach($$1 -> $$0.accept($$1.child));
/*     */   }
/*     */   
/*     */   public LayoutSettings newChildLayoutSettings() {
/*  85 */     return this.defaultChildLayoutSettings.copy();
/*     */   }
/*     */   
/*     */   public LayoutSettings defaultChildLayoutSetting() {
/*  89 */     return this.defaultChildLayoutSettings;
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0) {
/*  93 */     return addChild($$0, newChildLayoutSettings());
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, LayoutSettings $$1) {
/*  97 */     this.children.add(new ChildContainer((LayoutElement)$$0, $$1));
/*  98 */     return $$0;
/*     */   }
/*     */   
/*     */   public <T extends LayoutElement> T addChild(T $$0, Consumer<LayoutSettings> $$1) {
/* 102 */     return addChild($$0, (LayoutSettings)Util.make(newChildLayoutSettings(), $$1));
/*     */   }
/*     */   
/*     */   public enum Orientation {
/* 106 */     HORIZONTAL, VERTICAL;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getPrimaryLength(LayoutElement $$0) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/EqualSpacingLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 36, 1 -> 44, 2 -> 53
/*     */       //   36: new java/lang/IncompatibleClassChangeError
/*     */       //   39: dup
/*     */       //   40: invokespecial <init> : ()V
/*     */       //   43: athrow
/*     */       //   44: aload_1
/*     */       //   45: invokeinterface getWidth : ()I
/*     */       //   50: goto -> 59
/*     */       //   53: aload_1
/*     */       //   54: invokeinterface getHeight : ()I
/*     */       //   59: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #109	-> 0
/*     */       //   #110	-> 44
/*     */       //   #111	-> 53
/*     */       //   #109	-> 59
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	60	0	this	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$Orientation;
/*     */       //   0	60	1	$$0	Lnet/minecraft/client/gui/layouts/LayoutElement;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getPrimaryLength(EqualSpacingLayout.ChildContainer $$0) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/EqualSpacingLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 36, 1 -> 44, 2 -> 51
/*     */       //   36: new java/lang/IncompatibleClassChangeError
/*     */       //   39: dup
/*     */       //   40: invokespecial <init> : ()V
/*     */       //   43: athrow
/*     */       //   44: aload_1
/*     */       //   45: invokevirtual getWidth : ()I
/*     */       //   48: goto -> 55
/*     */       //   51: aload_1
/*     */       //   52: invokevirtual getHeight : ()I
/*     */       //   55: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #116	-> 0
/*     */       //   #117	-> 44
/*     */       //   #118	-> 51
/*     */       //   #116	-> 55
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	56	0	this	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$Orientation;
/*     */       //   0	56	1	$$0	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$ChildContainer;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getSecondaryLength(LayoutElement $$0) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/EqualSpacingLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 36, 1 -> 44, 2 -> 53
/*     */       //   36: new java/lang/IncompatibleClassChangeError
/*     */       //   39: dup
/*     */       //   40: invokespecial <init> : ()V
/*     */       //   43: athrow
/*     */       //   44: aload_1
/*     */       //   45: invokeinterface getHeight : ()I
/*     */       //   50: goto -> 59
/*     */       //   53: aload_1
/*     */       //   54: invokeinterface getWidth : ()I
/*     */       //   59: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #123	-> 0
/*     */       //   #124	-> 44
/*     */       //   #125	-> 53
/*     */       //   #123	-> 59
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	60	0	this	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$Orientation;
/*     */       //   0	60	1	$$0	Lnet/minecraft/client/gui/layouts/LayoutElement;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getSecondaryLength(EqualSpacingLayout.ChildContainer $$0) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/EqualSpacingLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 36, 1 -> 44, 2 -> 51
/*     */       //   36: new java/lang/IncompatibleClassChangeError
/*     */       //   39: dup
/*     */       //   40: invokespecial <init> : ()V
/*     */       //   43: athrow
/*     */       //   44: aload_1
/*     */       //   45: invokevirtual getHeight : ()I
/*     */       //   48: goto -> 55
/*     */       //   51: aload_1
/*     */       //   52: invokevirtual getWidth : ()I
/*     */       //   55: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #130	-> 0
/*     */       //   #131	-> 44
/*     */       //   #132	-> 51
/*     */       //   #130	-> 55
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	56	0	this	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$Orientation;
/*     */       //   0	56	1	$$0	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$ChildContainer;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void setPrimaryPosition(EqualSpacingLayout.ChildContainer $$0, int $$1) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/EqualSpacingLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 57, 1 -> 36, 2 -> 48
/*     */       //   36: aload_1
/*     */       //   37: iload_2
/*     */       //   38: aload_1
/*     */       //   39: invokevirtual getWidth : ()I
/*     */       //   42: invokevirtual setX : (II)V
/*     */       //   45: goto -> 57
/*     */       //   48: aload_1
/*     */       //   49: iload_2
/*     */       //   50: aload_1
/*     */       //   51: invokevirtual getHeight : ()I
/*     */       //   54: invokevirtual setY : (II)V
/*     */       //   57: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #137	-> 0
/*     */       //   #138	-> 36
/*     */       //   #139	-> 48
/*     */       //   #141	-> 57
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	58	0	this	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$Orientation;
/*     */       //   0	58	1	$$0	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$ChildContainer;
/*     */       //   0	58	2	$$1	I
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void setSecondaryPosition(EqualSpacingLayout.ChildContainer $$0, int $$1, int $$2) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/EqualSpacingLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 51, 1 -> 36, 2 -> 45
/*     */       //   36: aload_1
/*     */       //   37: iload_2
/*     */       //   38: iload_3
/*     */       //   39: invokevirtual setY : (II)V
/*     */       //   42: goto -> 51
/*     */       //   45: aload_1
/*     */       //   46: iload_2
/*     */       //   47: iload_3
/*     */       //   48: invokevirtual setX : (II)V
/*     */       //   51: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #144	-> 0
/*     */       //   #145	-> 36
/*     */       //   #146	-> 45
/*     */       //   #148	-> 51
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	52	0	this	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$Orientation;
/*     */       //   0	52	1	$$0	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$ChildContainer;
/*     */       //   0	52	2	$$1	I
/*     */       //   0	52	3	$$2	I
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int getPrimaryPosition(LayoutElement $$0) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/EqualSpacingLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 36, 1 -> 44, 2 -> 53
/*     */       //   36: new java/lang/IncompatibleClassChangeError
/*     */       //   39: dup
/*     */       //   40: invokespecial <init> : ()V
/*     */       //   43: athrow
/*     */       //   44: aload_1
/*     */       //   45: invokeinterface getX : ()I
/*     */       //   50: goto -> 59
/*     */       //   53: aload_1
/*     */       //   54: invokeinterface getY : ()I
/*     */       //   59: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #151	-> 0
/*     */       //   #152	-> 44
/*     */       //   #153	-> 53
/*     */       //   #151	-> 59
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	60	0	this	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$Orientation;
/*     */       //   0	60	1	$$0	Lnet/minecraft/client/gui/layouts/LayoutElement;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     int getSecondaryPosition(LayoutElement $$0) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/client/gui/layouts/EqualSpacingLayout$1.$SwitchMap$net$minecraft$client$gui$layouts$EqualSpacingLayout$Orientation : [I
/*     */       //   3: aload_0
/*     */       //   4: invokevirtual ordinal : ()I
/*     */       //   7: iaload
/*     */       //   8: lookupswitch default -> 36, 1 -> 44, 2 -> 53
/*     */       //   36: new java/lang/IncompatibleClassChangeError
/*     */       //   39: dup
/*     */       //   40: invokespecial <init> : ()V
/*     */       //   43: athrow
/*     */       //   44: aload_1
/*     */       //   45: invokeinterface getY : ()I
/*     */       //   50: goto -> 59
/*     */       //   53: aload_1
/*     */       //   54: invokeinterface getX : ()I
/*     */       //   59: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #158	-> 0
/*     */       //   #159	-> 44
/*     */       //   #160	-> 53
/*     */       //   #158	-> 59
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	60	0	this	Lnet/minecraft/client/gui/layouts/EqualSpacingLayout$Orientation;
/*     */       //   0	60	1	$$0	Lnet/minecraft/client/gui/layouts/LayoutElement;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ChildContainer
/*     */     extends AbstractLayout.AbstractChildWrapper
/*     */   {
/*     */     protected ChildContainer(LayoutElement $$0, LayoutSettings $$1) {
/* 167 */       super($$0, $$1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\layouts\EqualSpacingLayout.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */