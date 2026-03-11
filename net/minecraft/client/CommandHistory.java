/*    */ package net.minecraft.client;
/*    */ 
/*    */ import com.google.common.base.Charsets;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.util.ArrayListDeque;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class CommandHistory
/*    */ {
/* 16 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private static final int MAX_PERSISTED_COMMAND_HISTORY = 50;
/*    */   
/*    */   private static final String PERSISTED_COMMANDS_FILE_NAME = "command_history.txt";
/*    */   private final Path commandsPath;
/* 22 */   private final ArrayListDeque<String> lastCommands = new ArrayListDeque(50);
/*    */   
/*    */   public CommandHistory(Path $$0) {
/* 25 */     this.commandsPath = $$0.resolve("command_history.txt");
/* 26 */     if (Files.exists(this.commandsPath, new java.nio.file.LinkOption[0])) {
/* 27 */       try { BufferedReader $$1 = Files.newBufferedReader(this.commandsPath, Charsets.UTF_8); 
/* 28 */         try { this.lastCommands.addAll($$1.lines().toList());
/* 29 */           if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null) try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$2)
/* 30 */       { LOGGER.error("Failed to read {}, command history will be missing", "command_history.txt", $$2); }
/*    */     
/*    */     }
/*    */   }
/*    */   
/*    */   public void addCommand(String $$0) {
/* 36 */     if (!$$0.equals(this.lastCommands.peekLast())) {
/* 37 */       if (this.lastCommands.size() >= 50) {
/* 38 */         this.lastCommands.removeFirst();
/*    */       }
/* 40 */       this.lastCommands.addLast($$0);
/* 41 */       save();
/*    */     } 
/*    */   }
/*    */   private void save() {
/*    */     
/* 46 */     try { BufferedWriter $$0 = Files.newBufferedWriter(this.commandsPath, Charsets.UTF_8, new java.nio.file.OpenOption[0]); 
/* 47 */       try { for (String $$1 : this.lastCommands) {
/* 48 */           $$0.write($$1);
/* 49 */           $$0.newLine();
/*    */         } 
/* 51 */         if ($$0 != null) $$0.close();  } catch (Throwable throwable) { if ($$0 != null) try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$2)
/* 52 */     { LOGGER.error("Failed to write {}, command history will be missing", "command_history.txt", $$2); }
/*    */   
/*    */   }
/*    */   
/*    */   public Collection<String> history() {
/* 57 */     return (Collection<String>)this.lastCommands;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\CommandHistory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */