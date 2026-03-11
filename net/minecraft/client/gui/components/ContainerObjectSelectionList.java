/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.events.ContainerEventHandler;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.narration.NarratedElementType;
/*     */ import net.minecraft.client.gui.narration.NarrationElementOutput;
/*     */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*     */ import net.minecraft.client.gui.navigation.ScreenAxis;
/*     */ import net.minecraft.client.gui.navigation.ScreenDirection;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public abstract class ContainerObjectSelectionList<E extends ContainerObjectSelectionList.Entry<E>>
/*     */   extends AbstractSelectionList<E> {
/*     */   public ContainerObjectSelectionList(Minecraft $$0, int $$1, int $$2, int $$3, int $$4) {
/*  22 */     super($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/*  28 */     if (getItemCount() == 0)
/*     */     {
/*  30 */       return null;
/*     */     }
/*  32 */     if ($$0 instanceof FocusNavigationEvent.ArrowNavigation) { FocusNavigationEvent.ArrowNavigation $$1 = (FocusNavigationEvent.ArrowNavigation)$$0;
/*  33 */       Entry entry1 = (Entry)getFocused();
/*  34 */       if ($$1.direction().getAxis() == ScreenAxis.HORIZONTAL && entry1 != null) {
/*  35 */         return ComponentPath.path(this, entry1.nextFocusPath($$0));
/*     */       }
/*     */ 
/*     */       
/*  39 */       int $$3 = -1;
/*  40 */       ScreenDirection $$4 = $$1.direction();
/*  41 */       if (entry1 != null)
/*     */       {
/*  43 */         $$3 = entry1.children().indexOf(entry1.getFocused());
/*     */       }
/*  45 */       if ($$3 == -1) {
/*  46 */         switch ($$4) {
/*     */           
/*     */           case LEFT:
/*  49 */             $$3 = Integer.MAX_VALUE;
/*  50 */             $$4 = ScreenDirection.DOWN;
/*     */             break;
/*     */           
/*     */           case RIGHT:
/*  54 */             $$3 = 0;
/*  55 */             $$4 = ScreenDirection.DOWN; break;
/*     */           default:
/*  57 */             $$3 = 0;
/*     */             break;
/*     */         } 
/*     */       }
/*  61 */       Entry entry2 = entry1;
/*     */       while (true) {
/*  63 */         entry2 = (Entry)nextEntry($$4, $$0 -> !$$0.children().isEmpty(), (E)entry2);
/*  64 */         if (entry2 == null) {
/*  65 */           return null;
/*     */         }
/*  67 */         ComponentPath $$6 = entry2.focusPathAtIndex((FocusNavigationEvent)$$1, $$3);
/*  68 */         if ($$6 != null) {
/*  69 */           return ComponentPath.path(this, $$6);
/*     */         }
/*     */       }  }
/*     */     
/*  73 */     return super.nextFocusPath($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFocused(@Nullable GuiEventListener $$0) {
/*  78 */     super.setFocused($$0);
/*  79 */     if ($$0 == null) {
/*  80 */       setSelected((E)null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public NarratableEntry.NarrationPriority narrationPriority() {
/*  86 */     if (isFocused())
/*     */     {
/*  88 */       return NarratableEntry.NarrationPriority.FOCUSED;
/*     */     }
/*  90 */     return super.narrationPriority();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isSelectedItem(int $$0) {
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWidgetNarration(NarrationElementOutput $$0) {
/* 100 */     Entry entry = (Entry)getHovered();
/* 101 */     if (entry != null) {
/* 102 */       entry.updateNarration($$0.nest());
/* 103 */       narrateListElementPosition($$0, (E)entry);
/*     */     } else {
/* 105 */       Entry entry1 = (Entry)getFocused();
/* 106 */       if (entry1 != null) {
/* 107 */         entry1.updateNarration($$0.nest());
/* 108 */         narrateListElementPosition($$0, (E)entry1);
/*     */       } 
/*     */     } 
/* 111 */     $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.component_list.usage"));
/*     */   }
/*     */   
/*     */   public static abstract class Entry<E extends Entry<E>>
/*     */     extends AbstractSelectionList.Entry<E>
/*     */     implements ContainerEventHandler
/*     */   {
/*     */     @Nullable
/*     */     private GuiEventListener focused;
/*     */     @Nullable
/*     */     private NarratableEntry lastNarratable;
/*     */     private boolean dragging;
/*     */     
/*     */     public boolean isDragging() {
/* 125 */       return this.dragging;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDragging(boolean $$0) {
/* 130 */       this.dragging = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 135 */       return super.mouseClicked($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setFocused(@Nullable GuiEventListener $$0) {
/* 140 */       if (this.focused != null) {
/* 141 */         this.focused.setFocused(false);
/*     */       }
/* 143 */       if ($$0 != null) {
/* 144 */         $$0.setFocused(true);
/*     */       }
/* 146 */       this.focused = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public GuiEventListener getFocused() {
/* 152 */       return this.focused;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public ComponentPath focusPathAtIndex(FocusNavigationEvent $$0, int $$1) {
/* 157 */       if (children().isEmpty()) {
/* 158 */         return null;
/*     */       }
/* 160 */       ComponentPath $$2 = ((GuiEventListener)children().get(Math.min($$1, children().size() - 1))).nextFocusPath($$0);
/* 161 */       return ComponentPath.path(this, $$2);
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
/*     */     @Nullable
/*     */     public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_1
/*     */       //   1: instanceof net/minecraft/client/gui/navigation/FocusNavigationEvent$ArrowNavigation
/*     */       //   4: ifeq -> 178
/*     */       //   7: aload_1
/*     */       //   8: checkcast net/minecraft/client/gui/navigation/FocusNavigationEvent$ArrowNavigation
/*     */       //   11: astore_2
/*     */       //   12: getstatic net/minecraft/client/gui/components/ContainerObjectSelectionList$1.$SwitchMap$net$minecraft$client$gui$navigation$ScreenDirection : [I
/*     */       //   15: aload_2
/*     */       //   16: invokevirtual direction : ()Lnet/minecraft/client/gui/navigation/ScreenDirection;
/*     */       //   19: invokevirtual ordinal : ()I
/*     */       //   22: iaload
/*     */       //   23: tableswitch default -> 52, 1 -> 64, 2 -> 68, 3 -> 60, 4 -> 60
/*     */       //   52: new java/lang/IncompatibleClassChangeError
/*     */       //   55: dup
/*     */       //   56: invokespecial <init> : ()V
/*     */       //   59: athrow
/*     */       //   60: iconst_0
/*     */       //   61: goto -> 69
/*     */       //   64: iconst_m1
/*     */       //   65: goto -> 69
/*     */       //   68: iconst_1
/*     */       //   69: istore_3
/*     */       //   70: iload_3
/*     */       //   71: ifne -> 76
/*     */       //   74: aconst_null
/*     */       //   75: areturn
/*     */       //   76: iload_3
/*     */       //   77: aload_0
/*     */       //   78: invokevirtual children : ()Ljava/util/List;
/*     */       //   81: aload_0
/*     */       //   82: invokevirtual getFocused : ()Lnet/minecraft/client/gui/components/events/GuiEventListener;
/*     */       //   85: invokeinterface indexOf : (Ljava/lang/Object;)I
/*     */       //   90: iadd
/*     */       //   91: iconst_0
/*     */       //   92: aload_0
/*     */       //   93: invokevirtual children : ()Ljava/util/List;
/*     */       //   96: invokeinterface size : ()I
/*     */       //   101: iconst_1
/*     */       //   102: isub
/*     */       //   103: invokestatic clamp : (III)I
/*     */       //   106: istore #4
/*     */       //   108: iload #4
/*     */       //   110: istore #5
/*     */       //   112: iload #5
/*     */       //   114: iflt -> 178
/*     */       //   117: iload #5
/*     */       //   119: aload_0
/*     */       //   120: invokevirtual children : ()Ljava/util/List;
/*     */       //   123: invokeinterface size : ()I
/*     */       //   128: if_icmpge -> 178
/*     */       //   131: aload_0
/*     */       //   132: invokevirtual children : ()Ljava/util/List;
/*     */       //   135: iload #5
/*     */       //   137: invokeinterface get : (I)Ljava/lang/Object;
/*     */       //   142: checkcast net/minecraft/client/gui/components/events/GuiEventListener
/*     */       //   145: astore #6
/*     */       //   147: aload #6
/*     */       //   149: aload_1
/*     */       //   150: invokeinterface nextFocusPath : (Lnet/minecraft/client/gui/navigation/FocusNavigationEvent;)Lnet/minecraft/client/gui/ComponentPath;
/*     */       //   155: astore #7
/*     */       //   157: aload #7
/*     */       //   159: ifnull -> 169
/*     */       //   162: aload_0
/*     */       //   163: aload #7
/*     */       //   165: invokestatic path : (Lnet/minecraft/client/gui/components/events/ContainerEventHandler;Lnet/minecraft/client/gui/ComponentPath;)Lnet/minecraft/client/gui/ComponentPath;
/*     */       //   168: areturn
/*     */       //   169: iload #5
/*     */       //   171: iload_3
/*     */       //   172: iadd
/*     */       //   173: istore #5
/*     */       //   175: goto -> 112
/*     */       //   178: aload_0
/*     */       //   179: aload_1
/*     */       //   180: invokespecial nextFocusPath : (Lnet/minecraft/client/gui/navigation/FocusNavigationEvent;)Lnet/minecraft/client/gui/ComponentPath;
/*     */       //   183: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #167	-> 0
/*     */       //   #168	-> 12
/*     */       //   #169	-> 60
/*     */       //   #170	-> 64
/*     */       //   #171	-> 68
/*     */       //   #173	-> 70
/*     */       //   #174	-> 74
/*     */       //   #176	-> 76
/*     */       //   #177	-> 108
/*     */       //   #178	-> 131
/*     */       //   #179	-> 147
/*     */       //   #180	-> 157
/*     */       //   #181	-> 162
/*     */       //   #177	-> 169
/*     */       //   #186	-> 178
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	184	0	this	Lnet/minecraft/client/gui/components/ContainerObjectSelectionList$Entry;
/*     */       //   0	184	1	$$0	Lnet/minecraft/client/gui/navigation/FocusNavigationEvent;
/*     */       //   12	166	2	$$1	Lnet/minecraft/client/gui/navigation/FocusNavigationEvent$ArrowNavigation;
/*     */       //   70	108	3	$$2	I
/*     */       //   108	70	4	$$3	I
/*     */       //   112	66	5	$$4	I
/*     */       //   147	22	6	$$5	Lnet/minecraft/client/gui/components/events/GuiEventListener;
/*     */       //   157	12	7	$$6	Lnet/minecraft/client/gui/ComponentPath;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	184	0	this	Lnet/minecraft/client/gui/components/ContainerObjectSelectionList$Entry<TE;>;
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
/*     */     void updateNarration(NarrationElementOutput $$0) {
/* 192 */       List<? extends NarratableEntry> $$1 = narratables();
/* 193 */       Screen.NarratableSearchResult $$2 = Screen.findNarratableWidget($$1, this.lastNarratable);
/* 194 */       if ($$2 != null) {
/* 195 */         if ($$2.priority.isTerminal()) {
/* 196 */           this.lastNarratable = $$2.entry;
/*     */         }
/* 198 */         if ($$1.size() > 1) {
/* 199 */           $$0.add(NarratedElementType.POSITION, (Component)Component.translatable("narrator.position.object_list", new Object[] { Integer.valueOf($$2.index + 1), Integer.valueOf($$1.size()) }));
/* 200 */           if ($$2.priority == NarratableEntry.NarrationPriority.FOCUSED) {
/* 201 */             $$0.add(NarratedElementType.USAGE, (Component)Component.translatable("narration.component_list.usage"));
/*     */           }
/*     */         } 
/* 204 */         $$2.entry.updateNarration($$0.nest());
/*     */       } 
/*     */     }
/*     */     
/*     */     public abstract List<? extends NarratableEntry> narratables();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\ContainerObjectSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */