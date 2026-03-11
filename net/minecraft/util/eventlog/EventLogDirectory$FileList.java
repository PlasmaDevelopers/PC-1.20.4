/*     */ package net.minecraft.util.eventlog;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.time.LocalDate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileList
/*     */   implements Iterable<EventLogDirectory.File>
/*     */ {
/*     */   private final List<EventLogDirectory.File> files;
/*     */   
/*     */   FileList(List<EventLogDirectory.File> $$0) {
/* 130 */     this.files = new ArrayList<>($$0);
/*     */   }
/*     */   
/*     */   public FileList prune(LocalDate $$0, int $$1) {
/* 134 */     this.files.removeIf($$2 -> {
/*     */           EventLogDirectory.FileId $$3 = $$2.id();
/*     */           LocalDate $$4 = $$3.date().plusDays($$0);
/*     */           if (!$$1.isBefore($$4)) {
/*     */             try {
/*     */               Files.delete($$2.path());
/*     */               return true;
/* 141 */             } catch (IOException $$5) {
/*     */               EventLogDirectory.LOGGER.warn("Failed to delete expired event log file: {}", $$2.path(), $$5);
/*     */             } 
/*     */           }
/*     */           return false;
/*     */         });
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public FileList compressAll() {
/* 151 */     ListIterator<EventLogDirectory.File> $$0 = this.files.listIterator();
/* 152 */     while ($$0.hasNext()) {
/* 153 */       EventLogDirectory.File $$1 = $$0.next();
/*     */       try {
/* 155 */         $$0.set($$1.compress());
/* 156 */       } catch (IOException $$2) {
/* 157 */         EventLogDirectory.LOGGER.warn("Failed to compress event log file: {}", $$1.path(), $$2);
/*     */       } 
/*     */     } 
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<EventLogDirectory.File> iterator() {
/* 165 */     return this.files.iterator();
/*     */   }
/*     */   
/*     */   public Stream<EventLogDirectory.File> stream() {
/* 169 */     return this.files.stream();
/*     */   }
/*     */   
/*     */   public Set<EventLogDirectory.FileId> ids() {
/* 173 */     return (Set<EventLogDirectory.FileId>)this.files.stream().map(EventLogDirectory.File::id).collect(Collectors.toSet());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\eventlog\EventLogDirectory$FileList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */