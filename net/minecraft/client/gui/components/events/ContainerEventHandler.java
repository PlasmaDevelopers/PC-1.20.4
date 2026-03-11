/*     */ package net.minecraft.client.gui.components.events;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.ComponentPath;
/*     */ import net.minecraft.client.gui.navigation.FocusNavigationEvent;
/*     */ import net.minecraft.client.gui.navigation.ScreenAxis;
/*     */ import net.minecraft.client.gui.navigation.ScreenDirection;
/*     */ import net.minecraft.client.gui.navigation.ScreenPosition;
/*     */ import net.minecraft.client.gui.navigation.ScreenRectangle;
/*     */ import org.joml.Vector2i;
/*     */ 
/*     */ 
/*     */ public interface ContainerEventHandler
/*     */   extends GuiEventListener
/*     */ {
/*     */   default Optional<GuiEventListener> getChildAt(double $$0, double $$1) {
/*  27 */     for (GuiEventListener $$2 : children()) {
/*  28 */       if ($$2.isMouseOver($$0, $$1)) {
/*  29 */         return Optional.of($$2);
/*     */       }
/*     */     } 
/*  32 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   default boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  37 */     for (GuiEventListener $$3 : children()) {
/*  38 */       if ($$3.mouseClicked($$0, $$1, $$2)) {
/*  39 */         setFocused($$3);
/*  40 */         if ($$2 == 0) {
/*  41 */           setDragging(true);
/*     */         }
/*  43 */         return true;
/*     */       } 
/*     */     } 
/*  46 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   default boolean mouseReleased(double $$0, double $$1, int $$2) {
/*  51 */     setDragging(false);
/*     */     
/*  53 */     return getChildAt($$0, $$1).filter($$3 -> $$3.mouseReleased($$0, $$1, $$2)).isPresent();
/*     */   }
/*     */ 
/*     */   
/*     */   default boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/*  58 */     if (getFocused() != null && isDragging() && $$2 == 0) {
/*  59 */       return getFocused().mouseDragged($$0, $$1, $$2, $$3, $$4);
/*     */     }
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/*  70 */     return getChildAt($$0, $$1).filter($$4 -> $$4.mouseScrolled($$0, $$1, $$2, $$3)).isPresent();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean keyPressed(int $$0, int $$1, int $$2) {
/*  76 */     return (getFocused() != null && getFocused().keyPressed($$0, $$1, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   default boolean keyReleased(int $$0, int $$1, int $$2) {
/*  81 */     return (getFocused() != null && getFocused().keyReleased($$0, $$1, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   default boolean charTyped(char $$0, int $$1) {
/*  86 */     return (getFocused() != null && getFocused().charTyped($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void setFocused(boolean $$0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean isFocused() {
/* 101 */     return (getFocused() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default ComponentPath getCurrentFocusPath() {
/* 107 */     GuiEventListener $$0 = getFocused();
/* 108 */     if ($$0 != null) {
/* 109 */       return ComponentPath.path(this, $$0.getCurrentFocusPath());
/*     */     }
/* 111 */     return null;
/*     */   }
/*     */   
/*     */   default void magicalSpecialHackyFocus(@Nullable GuiEventListener $$0) {
/* 115 */     setFocused($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   default ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
/* 121 */     GuiEventListener $$1 = getFocused();
/*     */ 
/*     */     
/* 124 */     if ($$1 != null) {
/* 125 */       ComponentPath $$2 = $$1.nextFocusPath($$0);
/* 126 */       if ($$2 != null) {
/* 127 */         return ComponentPath.path(this, $$2);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 132 */     if ($$0 instanceof FocusNavigationEvent.TabNavigation) { FocusNavigationEvent.TabNavigation $$3 = (FocusNavigationEvent.TabNavigation)$$0;
/* 133 */       return handleTabNavigation($$3); }
/*     */     
/* 135 */     if ($$0 instanceof FocusNavigationEvent.ArrowNavigation) { FocusNavigationEvent.ArrowNavigation $$4 = (FocusNavigationEvent.ArrowNavigation)$$0;
/* 136 */       return handleArrowNavigation($$4); }
/*     */ 
/*     */     
/* 139 */     return null;
/*     */   }
/*     */   @Nullable
/*     */   private ComponentPath handleTabNavigation(FocusNavigationEvent.TabNavigation $$0) {
/*     */     int $$7;
/* 144 */     boolean $$1 = $$0.forward();
/* 145 */     GuiEventListener $$2 = getFocused();
/* 146 */     List<? extends GuiEventListener> $$3 = new ArrayList<>(children());
/* 147 */     Collections.sort($$3, Comparator.comparingInt($$0 -> $$0.getTabOrderGroup()));
/*     */ 
/*     */     
/* 150 */     int $$4 = $$3.indexOf($$2);
/* 151 */     if ($$2 != null && $$4 >= 0) {
/* 152 */       int $$5 = $$4 + ($$1 ? 1 : 0);
/*     */     }
/* 154 */     else if ($$1) {
/* 155 */       int $$6 = 0;
/*     */     } else {
/* 157 */       $$7 = $$3.size();
/*     */     } 
/*     */ 
/*     */     
/* 161 */     ListIterator<? extends GuiEventListener> $$8 = $$3.listIterator($$7);
/*     */     
/* 163 */     Objects.requireNonNull($$8); Objects.requireNonNull($$8); BooleanSupplier $$9 = $$1 ? $$8::hasNext : $$8::hasPrevious;
/* 164 */     Objects.requireNonNull($$8); Objects.requireNonNull($$8); Supplier<? extends GuiEventListener> $$10 = $$1 ? $$8::next : $$8::previous;
/*     */     
/* 166 */     while ($$9.getAsBoolean()) {
/* 167 */       GuiEventListener $$11 = $$10.get();
/* 168 */       ComponentPath $$12 = $$11.nextFocusPath((FocusNavigationEvent)$$0);
/* 169 */       if ($$12 != null) {
/* 170 */         return ComponentPath.path(this, $$12);
/*     */       }
/*     */     } 
/* 173 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ComponentPath handleArrowNavigation(FocusNavigationEvent.ArrowNavigation $$0) {
/* 178 */     GuiEventListener $$1 = getFocused();
/* 179 */     if ($$1 == null) {
/* 180 */       ScreenDirection $$2 = $$0.direction();
/* 181 */       ScreenRectangle $$3 = getRectangle().getBorder($$2.getOpposite());
/* 182 */       return ComponentPath.path(this, nextFocusPathInDirection($$3, $$2, null, (FocusNavigationEvent)$$0));
/*     */     } 
/* 184 */     ScreenRectangle $$4 = $$1.getRectangle();
/* 185 */     return ComponentPath.path(this, nextFocusPathInDirection($$4, $$0.direction(), $$1, (FocusNavigationEvent)$$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ComponentPath nextFocusPathInDirection(ScreenRectangle $$0, ScreenDirection $$1, @Nullable GuiEventListener $$2, FocusNavigationEvent $$3) {
/* 190 */     ScreenAxis $$4 = $$1.getAxis();
/* 191 */     ScreenAxis $$5 = $$4.orthogonal();
/* 192 */     ScreenDirection $$6 = $$5.getPositive();
/* 193 */     int $$7 = $$0.getBoundInDirection($$1.getOpposite());
/*     */     
/* 195 */     List<GuiEventListener> $$8 = new ArrayList<>();
/* 196 */     for (GuiEventListener $$9 : children()) {
/* 197 */       if ($$9 == $$2) {
/*     */         continue;
/*     */       }
/* 200 */       ScreenRectangle $$10 = $$9.getRectangle();
/* 201 */       if ($$10.overlapsInAxis($$0, $$5)) {
/* 202 */         int $$11 = $$10.getBoundInDirection($$1.getOpposite());
/* 203 */         if ($$1.isAfter($$11, $$7)) {
/* 204 */           $$8.add($$9); continue;
/* 205 */         }  if ($$11 == $$7 && 
/* 206 */           $$1.isAfter($$10.getBoundInDirection($$1), $$0.getBoundInDirection($$1))) {
/* 207 */           $$8.add($$9);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 213 */     Comparator<GuiEventListener> $$12 = Comparator.comparing($$1 -> Integer.valueOf($$1.getRectangle().getBoundInDirection($$0.getOpposite())), (Comparator<?>)$$1.coordinateValueComparator());
/* 214 */     Comparator<GuiEventListener> $$13 = Comparator.comparing($$1 -> Integer.valueOf($$1.getRectangle().getBoundInDirection($$0.getOpposite())), (Comparator<?>)$$6.coordinateValueComparator());
/* 215 */     $$8.sort($$12.thenComparing($$13));
/* 216 */     for (GuiEventListener $$14 : $$8) {
/* 217 */       ComponentPath $$15 = $$14.nextFocusPath($$3);
/* 218 */       if ($$15 != null) {
/* 219 */         return $$15;
/*     */       }
/*     */     } 
/* 222 */     return nextFocusPathVaguelyInDirection($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ComponentPath nextFocusPathVaguelyInDirection(ScreenRectangle $$0, ScreenDirection $$1, @Nullable GuiEventListener $$2, FocusNavigationEvent $$3) {
/* 227 */     ScreenAxis $$4 = $$1.getAxis();
/* 228 */     ScreenAxis $$5 = $$4.orthogonal();
/*     */     
/* 230 */     List<Pair<GuiEventListener, Long>> $$6 = new ArrayList<>();
/*     */     
/* 232 */     ScreenPosition $$7 = ScreenPosition.of($$4, $$0.getBoundInDirection($$1), $$0.getCenterInAxis($$5));
/* 233 */     for (GuiEventListener $$8 : children()) {
/* 234 */       if ($$8 == $$2) {
/*     */         continue;
/*     */       }
/* 237 */       ScreenRectangle $$9 = $$8.getRectangle();
/* 238 */       ScreenPosition $$10 = ScreenPosition.of($$4, $$9.getBoundInDirection($$1.getOpposite()), $$9.getCenterInAxis($$5));
/*     */       
/* 240 */       if ($$1.isAfter($$10.getCoordinate($$4), $$7.getCoordinate($$4))) {
/* 241 */         long $$11 = Vector2i.distanceSquared($$7.x(), $$7.y(), $$10.x(), $$10.y());
/* 242 */         $$6.add(Pair.of($$8, Long.valueOf($$11)));
/*     */       } 
/*     */     } 
/* 245 */     $$6.sort(Comparator.comparingDouble(Pair::getSecond));
/* 246 */     for (Pair<GuiEventListener, Long> $$12 : $$6) {
/* 247 */       ComponentPath $$13 = ((GuiEventListener)$$12.getFirst()).nextFocusPath($$3);
/* 248 */       if ($$13 != null) {
/* 249 */         return $$13;
/*     */       }
/*     */     } 
/* 252 */     return null;
/*     */   }
/*     */   
/*     */   List<? extends GuiEventListener> children();
/*     */   
/*     */   boolean isDragging();
/*     */   
/*     */   void setDragging(boolean paramBoolean);
/*     */   
/*     */   @Nullable
/*     */   GuiEventListener getFocused();
/*     */   
/*     */   void setFocused(@Nullable GuiEventListener paramGuiEventListener);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\events\ContainerEventHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */