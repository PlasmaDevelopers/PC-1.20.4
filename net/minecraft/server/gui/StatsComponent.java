/*    */ package net.minecraft.server.gui;
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.DecimalFormatSymbols;
/*    */ import java.util.Locale;
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.Timer;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.util.TimeUtil;
/*    */ 
/*    */ public class StatsComponent extends JComponent {
/*    */   static {
/* 17 */     DECIMAL_FORMAT = (DecimalFormat)Util.make(new DecimalFormat("########0.000"), $$0 -> $$0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
/*    */   }
/* 19 */   private final int[] values = new int[256]; private static final DecimalFormat DECIMAL_FORMAT;
/*    */   private int vp;
/* 21 */   private final String[] msgs = new String[11];
/*    */   private final MinecraftServer server;
/*    */   private final Timer timer;
/*    */   
/*    */   public StatsComponent(MinecraftServer $$0) {
/* 26 */     this.server = $$0;
/* 27 */     setPreferredSize(new Dimension(456, 246));
/* 28 */     setMinimumSize(new Dimension(456, 246));
/* 29 */     setMaximumSize(new Dimension(456, 246));
/* 30 */     this.timer = new Timer(500, $$0 -> tick());
/* 31 */     this.timer.start();
/* 32 */     setBackground(Color.BLACK);
/*    */   }
/*    */   
/*    */   private void tick() {
/* 36 */     long $$0 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
/* 37 */     this.msgs[0] = "Memory use: " + $$0 / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
/* 38 */     this.msgs[1] = "Avg tick: " + DECIMAL_FORMAT.format(this.server.getAverageTickTimeNanos() / TimeUtil.NANOSECONDS_PER_MILLISECOND) + " ms";
/* 39 */     this.values[this.vp++ & 0xFF] = (int)($$0 * 100L / Runtime.getRuntime().maxMemory());
/* 40 */     repaint();
/*    */   }
/*    */ 
/*    */   
/*    */   public void paint(Graphics $$0) {
/* 45 */     $$0.setColor(new Color(16777215));
/* 46 */     $$0.fillRect(0, 0, 456, 246);
/*    */     
/* 48 */     for (int $$1 = 0; $$1 < 256; $$1++) {
/* 49 */       int $$2 = this.values[$$1 + this.vp & 0xFF];
/* 50 */       $$0.setColor(new Color($$2 + 28 << 16));
/* 51 */       $$0.fillRect($$1, 100 - $$2, 1, $$2);
/*    */     } 
/* 53 */     $$0.setColor(Color.BLACK);
/* 54 */     for (int $$3 = 0; $$3 < this.msgs.length; $$3++) {
/* 55 */       String $$4 = this.msgs[$$3];
/* 56 */       if ($$4 != null) {
/* 57 */         $$0.drawString($$4, 32, 116 + $$3 * 16);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void close() {
/* 63 */     this.timer.stop();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\gui\StatsComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */