/*     */ package net.minecraft.client;
/*     */ 
/*     */ import com.mojang.blaze3d.Blaze3D;
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.SmoothDouble;
/*     */ import org.lwjgl.glfw.GLFWDropCallback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MouseHandler
/*     */ {
/*     */   private final Minecraft minecraft;
/*     */   private boolean isLeftPressed;
/*     */   private boolean isMiddlePressed;
/*     */   private boolean isRightPressed;
/*     */   private double xpos;
/*     */   private double ypos;
/*     */   private int fakeRightMouse;
/*  27 */   private int activeButton = -1;
/*     */   
/*     */   private boolean ignoreFirstMove = true;
/*     */   
/*     */   private int clickDepth;
/*     */   
/*     */   private double mousePressedTime;
/*  34 */   private final SmoothDouble smoothTurnX = new SmoothDouble();
/*  35 */   private final SmoothDouble smoothTurnY = new SmoothDouble();
/*     */   
/*     */   private double accumulatedDX;
/*     */   private double accumulatedDY;
/*     */   private double accumulatedScrollX;
/*     */   private double accumulatedScrollY;
/*  41 */   private double lastMouseEventTime = Double.MIN_VALUE;
/*     */   
/*     */   public MouseHandler(Minecraft $$0) {
/*  44 */     this.minecraft = $$0;
/*     */   }
/*     */   private boolean mouseGrabbed;
/*     */   private void onPress(long $$0, int $$1, int $$2, int $$3) {
/*  48 */     if ($$0 != this.minecraft.getWindow().getWindow()) {
/*     */       return;
/*     */     }
/*     */     
/*  52 */     if (this.minecraft.screen != null) {
/*  53 */       this.minecraft.setLastInputType(InputType.MOUSE);
/*     */     }
/*     */     
/*  56 */     boolean $$4 = ($$2 == 1);
/*     */ 
/*     */     
/*  59 */     if (Minecraft.ON_OSX && $$1 == 0) {
/*  60 */       if ($$4) {
/*  61 */         if (($$3 & 0x2) == 2) {
/*  62 */           $$1 = 1;
/*  63 */           this.fakeRightMouse++;
/*     */         } 
/*  65 */       } else if (this.fakeRightMouse > 0) {
/*  66 */         $$1 = 1;
/*  67 */         this.fakeRightMouse--;
/*     */       } 
/*     */     }
/*     */     
/*  71 */     int $$5 = $$1;
/*  72 */     if ($$4) {
/*  73 */       if (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue() && this.clickDepth++ > 0) {
/*     */         return;
/*     */       }
/*  76 */       this.activeButton = $$5;
/*  77 */       this.mousePressedTime = Blaze3D.getTime();
/*  78 */     } else if (this.activeButton != -1) {
/*  79 */       if (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue() && --this.clickDepth > 0) {
/*     */         return;
/*     */       }
/*  82 */       this.activeButton = -1;
/*     */     } 
/*  84 */     boolean[] $$6 = { false };
/*     */     
/*  86 */     if (this.minecraft.getOverlay() == null)
/*     */     {
/*  88 */       if (this.minecraft.screen == null) {
/*  89 */         if (!this.mouseGrabbed && $$4) {
/*  90 */           grabMouse();
/*     */         }
/*     */       } else {
/*  93 */         double $$7 = this.xpos * this.minecraft.getWindow().getGuiScaledWidth() / this.minecraft.getWindow().getScreenWidth();
/*  94 */         double $$8 = this.ypos * this.minecraft.getWindow().getGuiScaledHeight() / this.minecraft.getWindow().getScreenHeight();
/*  95 */         Screen $$9 = this.minecraft.screen;
/*     */         
/*  97 */         if ($$4) {
/*  98 */           $$9.afterMouseAction();
/*  99 */           Screen.wrapScreenError(() -> $$0[0] = $$1.mouseClicked($$2, $$3, $$4), "mouseClicked event handler", $$9.getClass().getCanonicalName());
/*     */         } else {
/* 101 */           Screen.wrapScreenError(() -> $$0[0] = $$1.mouseReleased($$2, $$3, $$4), "mouseReleased event handler", $$9.getClass().getCanonicalName());
/*     */         } 
/*     */       }  } 
/* 104 */     if (!$$6[0] && this.minecraft.screen == null && this.minecraft.getOverlay() == null) {
/* 105 */       if ($$5 == 0) {
/* 106 */         this.isLeftPressed = $$4;
/* 107 */       } else if ($$5 == 2) {
/* 108 */         this.isMiddlePressed = $$4;
/* 109 */       } else if ($$5 == 1) {
/* 110 */         this.isRightPressed = $$4;
/*     */       } 
/*     */       
/* 113 */       KeyMapping.set(InputConstants.Type.MOUSE.getOrCreate($$5), $$4);
/* 114 */       if ($$4) {
/* 115 */         if (this.minecraft.player.isSpectator() && $$5 == 2) {
/* 116 */           this.minecraft.gui.getSpectatorGui().onMouseMiddleClick();
/*     */         } else {
/* 118 */           KeyMapping.click(InputConstants.Type.MOUSE.getOrCreate($$5));
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onScroll(long $$0, double $$1, double $$2) {
/* 125 */     if ($$0 == Minecraft.getInstance().getWindow().getWindow()) {
/* 126 */       boolean $$3 = ((Boolean)this.minecraft.options.discreteMouseScroll().get()).booleanValue();
/* 127 */       double $$4 = ((Double)this.minecraft.options.mouseWheelSensitivity().get()).doubleValue();
/* 128 */       double $$5 = ($$3 ? Math.signum($$1) : $$1) * $$4;
/* 129 */       double $$6 = ($$3 ? Math.signum($$2) : $$2) * $$4;
/* 130 */       if (this.minecraft.getOverlay() == null)
/*     */       {
/* 132 */         if (this.minecraft.screen != null) {
/* 133 */           double $$7 = this.xpos * this.minecraft.getWindow().getGuiScaledWidth() / this.minecraft.getWindow().getScreenWidth();
/* 134 */           double $$8 = this.ypos * this.minecraft.getWindow().getGuiScaledHeight() / this.minecraft.getWindow().getScreenHeight();
/* 135 */           this.minecraft.screen.mouseScrolled($$7, $$8, $$5, $$6);
/* 136 */           this.minecraft.screen.afterMouseAction();
/* 137 */         } else if (this.minecraft.player != null) {
/* 138 */           if (this.accumulatedScrollX != 0.0D && Math.signum($$5) != Math.signum(this.accumulatedScrollX)) {
/* 139 */             this.accumulatedScrollX = 0.0D;
/*     */           }
/* 141 */           if (this.accumulatedScrollY != 0.0D && Math.signum($$6) != Math.signum(this.accumulatedScrollY)) {
/* 142 */             this.accumulatedScrollY = 0.0D;
/*     */           }
/* 144 */           this.accumulatedScrollX += $$5;
/* 145 */           this.accumulatedScrollY += $$6;
/*     */           
/* 147 */           int $$9 = (int)this.accumulatedScrollX;
/* 148 */           int $$10 = (int)this.accumulatedScrollY;
/* 149 */           if ($$9 == 0 && $$10 == 0) {
/*     */             return;
/*     */           }
/*     */           
/* 153 */           this.accumulatedScrollX -= $$9;
/* 154 */           this.accumulatedScrollY -= $$10;
/*     */           
/* 156 */           int $$11 = ($$10 == 0) ? -$$9 : $$10;
/* 157 */           if (this.minecraft.player.isSpectator()) {
/* 158 */             if (this.minecraft.gui.getSpectatorGui().isMenuActive()) {
/* 159 */               this.minecraft.gui.getSpectatorGui().onMouseScrolled(-$$11);
/*     */             } else {
/* 161 */               float $$12 = Mth.clamp(this.minecraft.player.getAbilities().getFlyingSpeed() + $$10 * 0.005F, 0.0F, 0.2F);
/* 162 */               this.minecraft.player.getAbilities().setFlyingSpeed($$12);
/*     */             } 
/*     */           } else {
/* 165 */             this.minecraft.player.getInventory().swapPaint($$11);
/*     */           } 
/*     */         }  } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onDrop(long $$0, List<Path> $$1) {
/* 172 */     if (this.minecraft.screen != null) {
/* 173 */       this.minecraft.screen.onFilesDrop($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setup(long $$0) {
/* 178 */     InputConstants.setupMouseCallbacks($$0, ($$0, $$1, $$2) -> this.minecraft.execute(()), ($$0, $$1, $$2, $$3) -> this.minecraft.execute(()), ($$0, $$1, $$2) -> this.minecraft.execute(()), ($$0, $$1, $$2) -> {
/*     */           Path[] $$3 = new Path[$$1];
/*     */           for (int $$4 = 0; $$4 < $$1; $$4++) {
/*     */             $$3[$$4] = Paths.get(GLFWDropCallback.getName($$2, $$4), new String[0]);
/*     */           }
/*     */           this.minecraft.execute(());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onMove(long $$0, double $$1, double $$2) {
/* 193 */     if ($$0 != Minecraft.getInstance().getWindow().getWindow()) {
/*     */       return;
/*     */     }
/* 196 */     if (this.ignoreFirstMove) {
/* 197 */       this.xpos = $$1;
/* 198 */       this.ypos = $$2;
/* 199 */       this.ignoreFirstMove = false;
/*     */     } 
/*     */     
/* 202 */     Screen $$3 = this.minecraft.screen;
/* 203 */     if ($$3 != null && this.minecraft.getOverlay() == null) {
/* 204 */       double $$4 = $$1 * this.minecraft.getWindow().getGuiScaledWidth() / this.minecraft.getWindow().getScreenWidth();
/* 205 */       double $$5 = $$2 * this.minecraft.getWindow().getGuiScaledHeight() / this.minecraft.getWindow().getScreenHeight();
/*     */       
/* 207 */       Screen.wrapScreenError(() -> $$0.mouseMoved($$1, $$2), "mouseMoved event handler", $$3.getClass().getCanonicalName());
/*     */       
/* 209 */       if (this.activeButton != -1 && this.mousePressedTime > 0.0D) {
/* 210 */         double $$6 = ($$1 - this.xpos) * this.minecraft.getWindow().getGuiScaledWidth() / this.minecraft.getWindow().getScreenWidth();
/* 211 */         double $$7 = ($$2 - this.ypos) * this.minecraft.getWindow().getGuiScaledHeight() / this.minecraft.getWindow().getScreenHeight();
/* 212 */         Screen.wrapScreenError(() -> $$0.mouseDragged($$1, $$2, this.activeButton, $$3, $$4), "mouseDragged event handler", $$3.getClass().getCanonicalName());
/*     */       } 
/* 214 */       $$3.afterMouseMove();
/*     */     } 
/*     */     
/* 217 */     this.minecraft.getProfiler().push("mouse");
/*     */     
/* 219 */     if (isMouseGrabbed() && this.minecraft.isWindowActive()) {
/* 220 */       this.accumulatedDX += $$1 - this.xpos;
/* 221 */       this.accumulatedDY += $$2 - this.ypos;
/*     */     } 
/*     */     
/* 224 */     turnPlayer();
/*     */     
/* 226 */     this.xpos = $$1;
/* 227 */     this.ypos = $$2;
/*     */     
/* 229 */     this.minecraft.getProfiler().pop();
/*     */   }
/*     */ 
/*     */   
/*     */   public void turnPlayer() {
/* 234 */     double $$11, $$12, $$0 = Blaze3D.getTime();
/* 235 */     double $$1 = $$0 - this.lastMouseEventTime;
/* 236 */     this.lastMouseEventTime = $$0;
/*     */     
/* 238 */     if (!isMouseGrabbed() || !this.minecraft.isWindowActive()) {
/* 239 */       this.accumulatedDX = 0.0D;
/* 240 */       this.accumulatedDY = 0.0D;
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 248 */     double $$2 = ((Double)this.minecraft.options.sensitivity().get()).doubleValue() * 0.6000000238418579D + 0.20000000298023224D;
/* 249 */     double $$3 = $$2 * $$2 * $$2;
/* 250 */     double $$4 = $$3 * 8.0D;
/*     */     
/* 252 */     if (this.minecraft.options.smoothCamera) {
/* 253 */       double $$5 = this.smoothTurnX.getNewDeltaValue(this.accumulatedDX * $$4, $$1 * $$4);
/* 254 */       double $$6 = this.smoothTurnY.getNewDeltaValue(this.accumulatedDY * $$4, $$1 * $$4);
/* 255 */       double $$7 = $$5;
/* 256 */       double $$8 = $$6;
/* 257 */     } else if (this.minecraft.options.getCameraType().isFirstPerson() && this.minecraft.player.isScoping()) {
/* 258 */       this.smoothTurnX.reset();
/* 259 */       this.smoothTurnY.reset();
/*     */       
/* 261 */       double $$9 = this.accumulatedDX * $$3;
/* 262 */       double $$10 = this.accumulatedDY * $$3;
/*     */     } else {
/* 264 */       this.smoothTurnX.reset();
/* 265 */       this.smoothTurnY.reset();
/*     */       
/* 267 */       $$11 = this.accumulatedDX * $$4;
/* 268 */       $$12 = this.accumulatedDY * $$4;
/*     */     } 
/*     */     
/* 271 */     this.accumulatedDX = 0.0D;
/* 272 */     this.accumulatedDY = 0.0D;
/*     */     
/* 274 */     int $$13 = 1;
/* 275 */     if (((Boolean)this.minecraft.options.invertYMouse().get()).booleanValue()) {
/* 276 */       $$13 = -1;
/*     */     }
/*     */     
/* 279 */     this.minecraft.getTutorial().onMouse($$11, $$12);
/* 280 */     if (this.minecraft.player != null) {
/* 281 */       this.minecraft.player.turn($$11, $$12 * $$13);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isLeftPressed() {
/* 286 */     return this.isLeftPressed;
/*     */   }
/*     */   
/*     */   public boolean isMiddlePressed() {
/* 290 */     return this.isMiddlePressed;
/*     */   }
/*     */   
/*     */   public boolean isRightPressed() {
/* 294 */     return this.isRightPressed;
/*     */   }
/*     */   
/*     */   public double xpos() {
/* 298 */     return this.xpos;
/*     */   }
/*     */   
/*     */   public double ypos() {
/* 302 */     return this.ypos;
/*     */   }
/*     */   
/*     */   public void setIgnoreFirstMove() {
/* 306 */     this.ignoreFirstMove = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMouseGrabbed() {
/* 312 */     return this.mouseGrabbed;
/*     */   }
/*     */   
/*     */   public void grabMouse() {
/* 316 */     if (!this.minecraft.isWindowActive()) {
/*     */       return;
/*     */     }
/* 319 */     if (this.mouseGrabbed) {
/*     */       return;
/*     */     }
/* 322 */     if (!Minecraft.ON_OSX) {
/* 323 */       KeyMapping.setAll();
/*     */     }
/* 325 */     this.mouseGrabbed = true;
/* 326 */     this.xpos = (this.minecraft.getWindow().getScreenWidth() / 2);
/* 327 */     this.ypos = (this.minecraft.getWindow().getScreenHeight() / 2);
/* 328 */     InputConstants.grabOrReleaseMouse(this.minecraft.getWindow().getWindow(), 212995, this.xpos, this.ypos);
/* 329 */     this.minecraft.setScreen((Screen)null);
/* 330 */     this.minecraft.missTime = 10000;
/* 331 */     this.ignoreFirstMove = true;
/*     */   }
/*     */   
/*     */   public void releaseMouse() {
/* 335 */     if (!this.mouseGrabbed) {
/*     */       return;
/*     */     }
/* 338 */     this.mouseGrabbed = false;
/* 339 */     this.xpos = (this.minecraft.getWindow().getScreenWidth() / 2);
/* 340 */     this.ypos = (this.minecraft.getWindow().getScreenHeight() / 2);
/* 341 */     InputConstants.grabOrReleaseMouse(this.minecraft.getWindow().getWindow(), 212993, this.xpos, this.ypos);
/*     */   }
/*     */   
/*     */   public void cursorEntered() {
/* 345 */     this.ignoreFirstMove = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\MouseHandler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */