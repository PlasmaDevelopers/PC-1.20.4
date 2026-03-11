/*     */ package net.minecraft.server.gui;
/*     */ import com.mojang.logging.LogQueues;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.EtchedBorder;
/*     */ import javax.swing.border.TitledBorder;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import net.minecraft.DefaultUncaughtExceptionHandler;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.dedicated.DedicatedServer;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class MinecraftServerGui extends JComponent {
/*  36 */   private static final Font MONOSPACED = new Font("Monospaced", 0, 12);
/*  37 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final String TITLE = "Minecraft server";
/*     */   private static final String SHUTDOWN_TITLE = "Minecraft server - shutting down!";
/*     */   private final DedicatedServer server;
/*     */   private Thread logAppenderThread;
/*  43 */   private final Collection<Runnable> finalizers = Lists.newArrayList();
/*  44 */   final AtomicBoolean isClosing = new AtomicBoolean();
/*     */   
/*     */   public static MinecraftServerGui showFrameFor(final DedicatedServer server) {
/*     */     try {
/*  48 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*  49 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  52 */     final JFrame frame = new JFrame("Minecraft server");
/*  53 */     final MinecraftServerGui gui = new MinecraftServerGui(server);
/*  54 */     $$1.setDefaultCloseOperation(2);
/*  55 */     $$1.add($$2);
/*  56 */     $$1.pack();
/*  57 */     $$1.setLocationRelativeTo((Component)null);
/*  58 */     $$1.setVisible(true);
/*  59 */     $$1.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent $$0) {
/*  62 */             if (!gui.isClosing.getAndSet(true)) {
/*  63 */               frame.setTitle("Minecraft server - shutting down!");
/*  64 */               server.halt(true);
/*  65 */               gui.runFinalizers();
/*     */             } 
/*     */           }
/*     */         });
/*  69 */     Objects.requireNonNull($$1); $$2.addFinalizer($$1::dispose);
/*  70 */     $$2.start();
/*  71 */     return $$2;
/*     */   }
/*     */   
/*     */   private MinecraftServerGui(DedicatedServer $$0) {
/*  75 */     this.server = $$0;
/*  76 */     setPreferredSize(new Dimension(854, 480));
/*     */     
/*  78 */     setLayout(new BorderLayout());
/*     */     try {
/*  80 */       add(buildChatPanel(), "Center");
/*  81 */       add(buildInfoPanel(), "West");
/*  82 */     } catch (Exception $$1) {
/*  83 */       LOGGER.error("Couldn't build server GUI", $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addFinalizer(Runnable $$0) {
/*  88 */     this.finalizers.add($$0);
/*     */   }
/*     */   
/*     */   private JComponent buildInfoPanel() {
/*  92 */     JPanel $$0 = new JPanel(new BorderLayout());
/*  93 */     StatsComponent $$1 = new StatsComponent((MinecraftServer)this.server);
/*  94 */     Objects.requireNonNull($$1); this.finalizers.add($$1::close);
/*  95 */     $$0.add($$1, "North");
/*  96 */     $$0.add(buildPlayerPanel(), "Center");
/*  97 */     $$0.setBorder(new TitledBorder(new EtchedBorder(), "Stats"));
/*  98 */     return $$0;
/*     */   }
/*     */   
/*     */   private JComponent buildPlayerPanel() {
/* 102 */     JList<?> $$0 = new PlayerListComponent((MinecraftServer)this.server);
/* 103 */     JScrollPane $$1 = new JScrollPane($$0, 22, 30);
/* 104 */     $$1.setBorder(new TitledBorder(new EtchedBorder(), "Players"));
/*     */     
/* 106 */     return $$1;
/*     */   }
/*     */   
/*     */   private JComponent buildChatPanel() {
/* 110 */     JPanel $$0 = new JPanel(new BorderLayout());
/* 111 */     JTextArea $$1 = new JTextArea();
/* 112 */     JScrollPane $$2 = new JScrollPane($$1, 22, 30);
/* 113 */     $$1.setEditable(false);
/* 114 */     $$1.setFont(MONOSPACED);
/*     */     
/* 116 */     JTextField $$3 = new JTextField();
/* 117 */     $$3.addActionListener($$1 -> {
/*     */           String $$2 = $$0.getText().trim();
/*     */           
/*     */           if (!$$2.isEmpty()) {
/*     */             this.server.handleConsoleInput($$2, this.server.createCommandSourceStack());
/*     */           }
/*     */           $$0.setText("");
/*     */         });
/* 125 */     $$1.addFocusListener(new FocusAdapter()
/*     */         {
/*     */           public void focusGained(FocusEvent $$0) {}
/*     */         });
/*     */ 
/*     */     
/* 131 */     $$0.add($$2, "Center");
/* 132 */     $$0.add($$3, "South");
/* 133 */     $$0.setBorder(new TitledBorder(new EtchedBorder(), "Log and chat"));
/*     */     
/* 135 */     this.logAppenderThread = new Thread(() -> {
/*     */           String $$2;
/*     */           while (($$2 = LogQueues.getNextLogEvent("ServerGuiConsole")) != null) {
/*     */             print($$0, $$1, $$2);
/*     */           }
/*     */         });
/* 141 */     this.logAppenderThread.setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new DefaultUncaughtExceptionHandler(LOGGER));
/* 142 */     this.logAppenderThread.setDaemon(true);
/* 143 */     return $$0;
/*     */   }
/*     */   
/*     */   public void start() {
/* 147 */     this.logAppenderThread.start();
/*     */   }
/*     */   
/*     */   public void close() {
/* 151 */     if (!this.isClosing.getAndSet(true)) {
/* 152 */       runFinalizers();
/*     */     }
/*     */   }
/*     */   
/*     */   void runFinalizers() {
/* 157 */     this.finalizers.forEach(Runnable::run);
/*     */   }
/*     */   
/*     */   public void print(JTextArea $$0, JScrollPane $$1, String $$2) {
/* 161 */     if (!SwingUtilities.isEventDispatchThread()) {
/* 162 */       SwingUtilities.invokeLater(() -> print($$0, $$1, $$2));
/*     */       
/*     */       return;
/*     */     } 
/* 166 */     Document $$3 = $$0.getDocument();
/* 167 */     JScrollBar $$4 = $$1.getVerticalScrollBar();
/* 168 */     boolean $$5 = false;
/*     */     
/* 170 */     if ($$1.getViewport().getView() == $$0) {
/* 171 */       $$5 = ($$4.getValue() + $$4.getSize().getHeight() + (MONOSPACED.getSize() * 4) > $$4.getMaximum());
/*     */     }
/*     */     
/*     */     try {
/* 175 */       $$3.insertString($$3.getLength(), $$2, null);
/* 176 */     } catch (BadLocationException badLocationException) {}
/*     */ 
/*     */     
/* 179 */     if ($$5)
/* 180 */       $$4.setValue(2147483647); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\gui\MinecraftServerGui.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */